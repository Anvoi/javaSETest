package 枚举与注解.注解.javadoc;

import 枚举与注解.注解.myAnnotations.MyAnnotation01;

import java.lang.annotation.Annotation;

public class testSon extends MyAnnotationTest{

    public static void main(String[] args) {
        Class clazz = testSon.class;

        //注意，虽然自定义注解声明元注解@Inherited ,子类继承了此注解
//        但是反射的getDeclare开头的方法都只能获取当前类的东西，不能获取父类的东西
//        需要用getAnnotations方法直接连父类的东西一起获取
        Annotation[] a = clazz.getAnnotations();
        MyAnnotation01 m = (MyAnnotation01)a[0];

        System.out.println(m.value());//黄进的注解


    }


}
