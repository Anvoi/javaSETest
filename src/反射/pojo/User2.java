package 反射.pojo;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;

@MyAnnotation(value="hello")
public class User2 extends User<Cart> implements userIt,MyAnnotation{
    private String name;
    private String pwd;
    public String gkai;



    public User2() {
    }

    public User2(String name) {
        this.name = name;
    }

    private User2(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    @Override
    public String toString() {
        return "User2{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }


    private void show(){
        System.out.println(
                "User2私有方法"
        );
    }


    public void show2(){
        System.out.println(
                "User2公有方法"
        );
    }


    public void invok1(String str){
        System.out.println("反射调用有参数方法："+str);
    }

    public static void invok2(String str){
        System.out.println("反射调用有参[静态]方法："+str);
    }


    private void invokShi(){
        System.out.println("私有无参数方法被调用");
    }

    @Override
    public String value() {
        return "asdaw";
    }
}
