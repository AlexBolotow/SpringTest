package org.springframework.beans.factory;

import com.bolotov.service.KitItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.stereotype.Component;
import org.springframework.beans.factory.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class BeanFactory {
    private Map<String, Object> singletons = new HashMap<>();
    private List<BeanPostProcessor> postProcessors = new ArrayList<>();
    private ProxyBeanFactory proxyBeanFactory = new ProxyBeanFactory();

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
                            Object instance = classObject.newInstance();
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

/*    private <T> T getBeanSingleton(Class<T> beanType) {
        for (Object item : singletons.values()) {
            if (beanType.isInstance(item)) {
                return (T) item;
            }
        }
        return null;
    }*/

    public Object getBean(String beanName) {
        return proxyBeanFactory.getProxy(beanName);
    }

    public Object getBean(Class<?> beanType) {
        for (Object item : singletons.values()) {
            if (beanType.isInstance(item)) {
                //возвращаем proxy
                if (beanType.isInterface()) {
                    return proxyBeanFactory.getProxy(beanType);
                }
                //возвращаем bean
                else {
                    return item;
                }
            }
        }
        return null;
    }

    public void injectDependenciesFields(Object object) throws IllegalAccessException {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                for (Object dependency : singletons.values()) {
                    if (field.getType().isAssignableFrom(dependency.getClass())) {
                        field.setAccessible(true);
                       /* System.out.println(dependency.getClass());
                        System.out.println(proxyBeanFactory.getProxy(dependency.getClass()));*/
                        field.set(object, proxyBeanFactory.getProxy(dependency.getClass()));
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

                        constructorDependencies.add(proxyBeanFactory.getProxy(parameter.getType()));
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
        proxyBeanFactory.initialize("com.bolotov.aspects", singletons);

        for (String name : singletons.keySet()) {
            Object bean = getBean(name);
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
