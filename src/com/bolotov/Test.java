package com.bolotov;

import com.bolotov.aspects.LoggingAspectImpl;
import com.bolotov.entity.Car;
import com.bolotov.entity.Promotion;
import com.bolotov.service.CarServiceImpl;
import org.springframework.beans.factory.annotation.Before;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) throws NoSuchMethodException {
        Class carServiceClass = CarServiceImpl.class;
        Method method = carServiceClass.getMethod("sale", Car.class, Promotion.class, DB.class);
        Object[] aspects = {new LoggingAspectImpl()};
        test(method, aspects);
    }

    public static void test(Method method, Object[] aspects) {
        for (Object object : aspects) {
            for (Method advice : object.getClass().getDeclaredMethods()) {
               /* if (advice.isAnnotationPresent(Before.class) && advice.getAnnotation(Before.class).toString().equals(method.getName())) {

                }*/
                System.out.println(advice.getAnnotation(Before.class).value());
                System.out.println(method.getName());
            }
        }
    }

    public static String getSignature(Method m){
        String sig;
        try {
            Field gSig = Method.class.getDeclaredField("signature");
            gSig.setAccessible(true);
            sig = (String) gSig.get(m);
            if(sig!=null) return sig;
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder("(");
        for(Class<?> c : m.getParameterTypes())
            sb.append((sig= Array.newInstance(c, 0).toString())
                    .substring(1, sig.indexOf('@')));
        return sb.append(')')
                .append(
                        m.getReturnType()==void.class?"V":
                                (sig=Array.newInstance(m.getReturnType(), 0).toString()).substring(1, sig.indexOf('@'))
                )
                .toString();
    }
}
