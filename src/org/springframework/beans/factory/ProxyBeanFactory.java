package org.springframework.beans.factory;

import org.springframework.beans.factory.annotation.Aspect;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.stereotype.Component;
import org.springframework.beans.factory.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class ProxyBeanFactory {
    private Map<String, Object> proxies = new HashMap<>();
    private List<Object> aspects = new ArrayList<>();

    public void initialize(String basePackage, Map<String, Object> singletons) {
        instantiateAspects(basePackage);
        instantiateProxies(singletons);
    }

    //сканирование и создание aspects, создание proxy
    private void instantiateAspects(String basePackage) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        String path = basePackage.replace('.', '/');
        try {
            Enumeration<URL> resources = classLoader.getResources(path);

            //находи классы аннотированный @Aspect
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File file = new File(resource.toURI());
                for (File classFile : file.listFiles()) {
                    String fileName = classFile.getName();
                    //получаем имя файла без разшерения
                    if (fileName.endsWith("class")) {
                        String className = fileName.substring(0, fileName.lastIndexOf("."));
                        Class classObject = Class.forName(basePackage + "." + className);
                        if (classObject.isAnnotationPresent(Aspect.class)) {

                            Object instance = classObject.newInstance();
                            aspects.add(instance);
                        }
                    }
                }
            }
        } catch (IOException | URISyntaxException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void instantiateProxies(Map<String, Object> singletons) {
        for (Map.Entry<String, Object> singleton : singletons.entrySet()) {
            Object proxy = Proxy.newProxyInstance(singleton.getValue().getClass().getClassLoader(),
                    singleton.getValue().getClass().getInterfaces(), new ProxyBean(singleton.getValue(), this));

            proxies.put(singleton.getKey(),proxy);
        }
    }

    public Object getProxy(String proxyName) {
        return proxies.get(proxyName);
    }

    public List<Object> getAspects() {
        return aspects;
    }

    //public <T> T getProxy(Class<T> beanType)
}
