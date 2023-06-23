package org.springframework.beans.factory;

import com.bolotov.aspects.LoggingAspect;
import com.bolotov.aspects.LoggingAspectImpl;
import org.springframework.beans.factory.annotation.After;
import org.springframework.beans.factory.annotation.Around;
import org.springframework.beans.factory.annotation.Before;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyBean implements InvocationHandler {
    Object bean;
    LoggingAspect aspect = new LoggingAspectImpl();

    public ProxyBean(Object bean) {
        this.bean = bean;
    }

    public Object getBean() {
        return bean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //Object result = proxy;

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

            method.invoke(bean, args);

            if (advice.isAnnotationPresent(Around.class)
                    && advice.getAnnotation(Around.class).value().equals(method.getName())) {
                advice.invoke(aspect);
            }

            if (advice.isAnnotationPresent(After.class)
                    && advice.getAnnotation(After.class).value().equals(method.getName())) {
                advice.invoke(aspect);
            }
        }

        return proxy;
    }
}
