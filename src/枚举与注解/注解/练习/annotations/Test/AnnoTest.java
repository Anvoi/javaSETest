package 枚举与注解.注解.练习.annotations.Test;

import org.junit.Test;
import 枚举与注解.注解.练习.annotations.MyTiger;
import 枚举与注解.注解.练习.annotations.MyTigers;
import 枚举与注解.注解.练习.annotations.pojo.Person;

import java.lang.annotation.Annotation;

public class AnnoTest {
    /**
     * 利用反射获取注解信息
     * JDK 5.0 在 java.lang.reflect 包下新增了 AnnotatedElement 接口, 该接口代
     * 表程序中可以接受注解的程序元素
     *
     * 当一个 [Annotation 类型被定义为运行时 Annotation](就是元注解@Retention(RetentionPolicy.RUNTIME)) 后, 该注解才是运行时
     * 可见, 当 class 文件被载入时保存在 class 文件中的[ Annotation ]才会被虚拟
     * 机读取
     *
     * 程序[可以调用 AnnotatedElement对象]的如下方法来访问 Annotation 信息
     *
     * public <A extends Annotation> A getAnnotation(Class<A> annotationClass)
     * public Annotation[] getAnnotations()
     *
     * public <A extends Annotation> A getDeclaredAnnotation(Class<A> annotationClass)
     * public Annotation[] getDeclaredAnnotations()
     *
     * [注意一点,老规矩,反射中getDeclared开头的基本可以读取当前类的所有信息(包括私有),不能读取其父类继承来的成员]
     * [直接get反射拿的,可以获取所有公开的成员,包括继承过来的,但是也只能获取公开的,低于空开权限的都拿不了]
     *
     *
     *平时反射使用的获取时要注意注解拿时如果此注解是从父类继承过来的,getDeclared...方法是拿不了的
     * 这些方法是在Class
     *public final class Class<T> implements java.io.Serializable,
     *                               GenericDeclaration,
     *                               Type,
     *                               AnnotatedElement {
     *
     *                               [注意看,实现了了AnnotatedElement接口,遵守了此接口的规范
     *                               重写了这些获取注解的方法.平常用的获取注解的方法虽然借用了
     *                               Class类实现功能,但是源头接口是AnnotatedElement接口]
     *
     */


    @Test
    public void test01() throws NoSuchMethodException {
        Class<Person> clazz = Person.class;

        //类的方法上的注解, 反射得到类的Class对象后,利用字节码对象根据方法名获取到对应的方法的Method对象,
        // 然后直接Method对象.getDeclaredAnnotations()获取到其方法上的注解对象数组
        Annotation[] a = clazz.getDeclaredMethod("toString").getDeclaredAnnotations();

        for (Annotation annotation : a) {
           MyTigers my =  (MyTigers)annotation;
            for (MyTiger myTiger : my.value()) {
                System.out.println(myTiger.value());
            }


        }

        /**
         * 注意:
         *  使用 @Repeatable  可重复注解,使用会投入另一个注解库专门存放此注解的多个对象(重复的时候,都存到注解库注解类中)
         *  使用了此注解之后,虽然用的注解还是被@Repeatable修饰的注解,但是反射获取到的是放进@Repeatable参数的注解库类对象
         *  所以想要拿到对应的注解对象的值,需要先把反射返回的注解库注解对象的value拿到,
         *  再迭代此注解库value(此value是装载具体注解对象的对象数组)
         *
         */



    }





}
