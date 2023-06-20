package org.springframework.beans.factory.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
//помечаются те поля которые являются зависимостями
public @interface Autowired {
}
