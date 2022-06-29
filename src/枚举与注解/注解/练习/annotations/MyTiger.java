package 枚举与注解.注解.练习.annotations;


import java.lang.annotation.*;

//元注解,注解类得的注解

@Repeatable(MyTigers.class) //声明@Repeatable可重复注解后需要放进注解对象库注解类的字节码对象
@Retention(RetentionPolicy.RUNTIME)// 设置成运行时有效,反射可以读取到
@Target(ElementType.METHOD) //允许使用范围,此处只允许使用在方法上面
public @interface MyTiger {
        String value() ;

}
