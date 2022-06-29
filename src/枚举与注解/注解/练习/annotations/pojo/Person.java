package 枚举与注解.注解.练习.annotations.pojo;

import 枚举与注解.注解.练习.annotations.MyTiger;

public class Person {



    //重写Object的方法,注意注解
    @Override
    @MyTiger("黄进确实是骚逼")
    @MyTiger("黄进欠草")
    public String toString() {
        return "Person{}"+":";
    }
}
