package org.lemon.annotation;

import org.lemon.spring.CustomScannerRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Import(CustomScannerRegister.class)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RpcScan {
    String[] basePackage();
}
