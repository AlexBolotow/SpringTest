package org.springframework.beans.factory;

import com.bolotov.aspects.LoggingAspect;
import com.bolotov.service.PromotionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Loggable;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.stereotype.Component;
import org.springframework.beans.factory.stereotype.Service;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class BeanFactory {
    private Map<String, Object> singletons = new HashMap<>();
    private List<BeanPostProcessor> postProcessors = new ArrayList<>();

    public Map<String, Object> getSingletons() {
        return singletons;
    }

    //сканирование и создание бинов
    public void instantiate(String basePackage) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        String path = basePackage.replace('.', '/');
        try {
            Enumeration<URL> resources = classLoader.getResources(path);

            //находи классы аннотированный @Component
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File file = new File(resource.toURI());
                for (File classFile : file.listFiles()) {
                    String fileName = classFile.getName();
                    //получаем имя файла без разшерения
                    if (fileName.endsWith("class")) {
                        String className = fileName.substring(0, fileName.lastIndexOf("."));
                        Class classObject = Class.forName(basePackage + "." + className);
                        if (classObject.isAnnotationPresent(Component.class) || classObject.isAnnotationPresent(Service.class)) {
                            Object instance;
                            //создаем proxy object
                            if (classObject.isAnnotationPresent(Loggable.class)) {
                                Object bean = classObject.newInstance();

                                instance = Proxy.newProxyInstance(bean.getClass().getClassLoader(),
                                        bean.getClass().getInterfaces(), new ProxyBean(bean));
                            }
                            else {
                                //System.out.println(Arrays.toString(classObject.getDeclaredAnnotations()) + " : " + classObject);
                                //создаем новый bean
                                instance = classObject.newInstance();
                            }

                            String beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
                            singletons.put(beanName, instance);
                        }
                    }
                }
            }
        } catch (IOException | URISyntaxException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Object getBean(String beanName) {
//        if (singletons.get(beanName) instanceof ProxyBean) {
//            return ((ProxyBean)singletons.get(beanName)).bean;
//        }

        System.out.println(singletons.get(beanName));
        return singletons.get(beanName);

    }

    public <T> T getBean(Class<T> beanType) {
        for (Object item : singletons.values()) {
            if (beanType.isInstance(item)) {
                return (T) item;
            }
        }
        return null;
    }

    public void injectDependenciesFields(Object object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                //находим тип который хочет взять bean
                for (Object dependency : singletons.values()) {
                    if (field.getType().isAssignableFrom(dependency.getClass())) {
                        field.setAccessible(true);
                        field.set(object, dependency);
                        field.setAccessible(false);
                    }
                }
            }
        }
    }

    public void injectDependenciesWithSetters(Object object) throws IllegalAccessException, InvocationTargetException {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Autowired.class) && method.getParameterCount() == 1) {
                Object dependency = getBean(method.getParameters()[0].getType());
                if (dependency != null) {
                    method.invoke(object, dependency);
                }
            }
        }
    }

    public Object injectDependenciesWithConstructor(Object object) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        for (Constructor constructor : object.getClass().getDeclaredConstructors()) {
            if (constructor.isAnnotationPresent(Autowired.class)) {
                Parameter[] parameters = constructor.getParameters();
                List<Object> constructorDependencies = new ArrayList<>();
                for (Parameter parameter : parameters) {
                    try {
                        if (getBean(parameter.getType()) == null) {
                            throw new RuntimeException("Autowired constructor error");
                        }

                        constructorDependencies.add(getBean(parameter.getType()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                return constructor.newInstance(constructorDependencies.toArray());
            }
        }

        return null;
    }

    public void injectBeanNames() {
        for (String name : singletons.keySet()) {
            Object bean = singletons.get(name);
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(name);
            }
        }
    }

    public void injectBeanFactory() {
        for (String name : singletons.keySet()) {
            Object bean = singletons.get(name);
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
        }
    }

    public void initializeBeans() throws IllegalAccessException, InvocationTargetException, InstantiationException {
        for (String name : singletons.keySet()) {
            Object bean = singletons.get(name);

            for (BeanPostProcessor postProcessor : postProcessors) {
                postProcessor.postProcessBeforeInitialization(bean, name);
            }

            Object temp = injectDependenciesWithConstructor(bean);
            if (temp != null) {

                bean = temp;
                singletons.put(name, bean);
            }

            injectDependenciesFields(bean);
            injectDependenciesWithSetters(bean);

            for (BeanPostProcessor postProcessor : postProcessors) {
                postProcessor.postProcessAfterInitialization(bean, name);
            }
        }
    }

    public void addPostProcessor(BeanPostProcessor postProcessor) {
        postProcessors.add(postProcessor);
    }
}
