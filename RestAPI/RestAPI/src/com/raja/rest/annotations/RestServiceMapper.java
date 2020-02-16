package com.raja.rest.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RestServiceMapper {

    String serviceName() default  "";
    
    String contentType() default "text/plain";
}
