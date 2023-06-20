package org.springframework.beans.factory.stereotype;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//загружает в память во время работы программы
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {

}
