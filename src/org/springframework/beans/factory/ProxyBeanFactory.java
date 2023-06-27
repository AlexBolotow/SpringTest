package org.springframework.beans.factory;

import org.springframework.beans.factory.annotation.Aspect;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class ProxyBeanFactory {
    private Map<String, Object> proxiesByName = new HashMap<>();
    private Map<Class<?>, Object> proxiesByType = new HashMap<>();
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

    public void instantiateProxies(Map<String, Object> singletons) {
        for (Map.Entry<String, Object> singleton : singletons.entrySet()) {
            Object proxy = Proxy.newProxyInstance(singleton.getValue().getClass().getClassLoader(),
                    singleton.getValue().getClass().getInterfaces(), new ProxyBean(singleton.getValue(), this));

            proxiesByName.put(singleton.getKey(), proxy);
            proxiesByType.put(singleton.getValue().getClass(), proxy);
        }

    }

    public Object getProxy(String proxyName) {
        return proxiesByName.get(proxyName);
    }

    public Object getProxy(Class<?> proxyType) {
        for (Map.Entry<Class<?>, Object> proxy : proxiesByType.entrySet()) {
            {
                if (proxyType.isAssignableFrom(proxy.getKey())) {
                    return proxy.getValue();
                }
            }
        }

        return null;
    }

    public List<Object> getAspects() {
        return aspects;
    }
}
