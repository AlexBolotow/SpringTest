package org.springframework.beans.factory;

import org.springframework.beans.factory.annotation.After;
import org.springframework.beans.factory.annotation.Around;
import org.springframework.beans.factory.annotation.Before;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyBean implements InvocationHandler {
    Object bean;
    private ProxyBeanFactory proxyBeanFactory;

    public ProxyBean(Object bean, ProxyBeanFactory proxyBeanFactory) {
        this.bean = bean;
        this.proxyBeanFactory = proxyBeanFactory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;

        for (Object aspect : proxyBeanFactory.getAspects()) {
            for (Method advice : aspect.getClass().getDeclaredMethods()) {
                //есть ли у advice аннотация и pointcut соответсвующий имени вызываемого метода
                if (advice.isAnnotationPresent(Before.class)
                        && advice.getAnnotation(Before.class).value().equals(method.getName())) {
                    advice.invoke(aspect);
                }

                if (advice.isAnnotationPresent(Around.class)
                        && advice.getAnnotation(Around.class).value().equals(method.getName())) {
                    advice.invoke(aspect);
                }
            }

            result = method.invoke(bean, args);

            for (Method advice : aspect.getClass().getDeclaredMethods()) {
                if (advice.isAnnotationPresent(Around.class)
                        && advice.getAnnotation(Around.class).value().equals(method.getName())) {
                    advice.invoke(aspect);
                }

                if (advice.isAnnotationPresent(After.class)
                        && advice.getAnnotation(After.class).value().equals(method.getName())) {
                    advice.invoke(aspect);
                }
            }
        }

        return result;
    }
}
