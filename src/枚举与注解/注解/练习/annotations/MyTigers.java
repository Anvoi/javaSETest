package 枚举与注解.注解.练习.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 前面说过了@interface自定义注解和接口一样的风格,public static final,不可以更改
 * 像重复注解更改值是不可能的,final值不能修改,可以重复注解,就是声明多个注解对象储存在
 * 另一个自定义注解类库中
 */


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyTigers {
    MyTiger[] value();


}
