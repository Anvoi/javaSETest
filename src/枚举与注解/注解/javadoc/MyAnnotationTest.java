package 枚举与注解.注解.javadoc;

import 枚举与注解.注解.myAnnotations.MyAnnotation01;

import java.lang.annotation.*;

//测试类声明一个自定义的注解，并且给注解中的变量赋值
@MyAnnotation01("黄进的注解")//始终来说，注解中的元数据还是需要给值的，
public class MyAnnotationTest {

    public static void main(String[] args) {
        //拿到当前类的Class字节码对象
        Class clazz = MyAnnotationTest.class;

        //利用当前类字节码对象,获取当前类对应注解的代理'对象',多态给Annotation接口
//        此处根据MyAnnotation01.class参数找到当前类对应的注解
        Annotation[] a = clazz.getDeclaredAnnotations(); //这里直接是返回接口数组

        //强转会对应的具体的自定义的注解类
        MyAnnotation01 m = (MyAnnotation01) a[0];

//          由此可以看出clazz.getDeclaredAnnotations()等【返回的是代理的注解的实例】，
//        返回的确实是[注解类的实例对象]
//        System.out.println(m.getClass().getName());

        //拿到对应注解对象的属性
        System.out.println(m.value());//我的自定义注解


    }


}

/**JDK 中的元注解
 *
 * JDK 的元 Annotation 用于修饰其他 Annotation 定义
 * JDK5.0提供了4个标准的meta-annotation类型，分别是：
 *      Retention
 *      Target
 *      Documented
 *      Inherited
 *
 * 元数据的理解：
 * String name = “atguigu”;
 */

/**
 *
 /**元注解
 *
 * @Retention: 只能用于修饰一个 Annotation 定义, 用于指定该 Annotation 的生命
 * 周期,
 * @Rentention 包含一个 RetentionPolicy 类型的成员变量, 使用
 * @Rentention 时必须为该 value 成员变量指定值:
 *
 * RetentionPolicy.SOURCE:在源文件中有效（即源文件保留），编译器直接丢弃这种策略的
 * 注释
 * RetentionPolicy.CLASS:在class文件中有效（即class保留） ， 当运行 Java 程序时, JVM
 * 不会保留注解。 这是默认值
 * RetentionPolicy.RUNTIME:在运行时有效（即运行时保留），当运行 Java 程序时, JVM 会
 * 保留注释。程序可以通过反射获取该注释。
 *
 */




//注意，不用手动继承Annotation接口,系统自动将@interface去继承Annotaion接口

    //整个周期； 【（源文件期间）SOURCE -----> （编译期）CLASS ---（类加载）--->（运行期）RUNTIME】
    // 就是类加载周期:   java源文件编译------>JVM通过编译得到的字节码文件来加载类----->加载好类之后才开始运行
    //Retention生命周期
    // RetentionPolicy.SOURCE（源文件有效，编译期间就丢弃了）
    // RetentionPolicy.CLASS 编译期有效，运行期丢弃,反射是在运行该期的行为（运行程序的时候才执行反射代码的），
    // 如果只是编译期有效，到反射的时候就是运行期间，运行期间反射看不到这个注解，获取不到
//    RetentionPolicy.RUNTIME运行时有效，运行时期可以看到此注解，RUNTIME使反射可以拿到注解


    /**想要用反射获取对应的注解，就需要声明Retention是RUNTIME,运行时有效
     * 注意：反射是运行的时候运行到才运行的运行时代码，所以注解只能声明成运行时才可以被反射拿到
     *
     * */


/**
 *@Target: 用于修饰 Annotation 定义, 用于指定被修饰的 Annotation 能用于
 * 修饰哪些程序元素。 @Target 也包含一个名为 value 的成员变量。
 *
 *
 * 它的value的值决定允许用当前注解来声明哪一些结构
 * @Target(ElementType.TYPE)——接口、类、枚举、注解
 * @Target(ElementType.FIELD)——字段、枚举的常量
 * @Target(ElementType.METHOD)——方法
 * @Target(ElementType.PARAMETER)——方法参数
 * @Target(ElementType.CONSTRUCTOR) ——构造函数
 * @Target(ElementType.LOCAL_VARIABLE)——局部变量
 * @Target(ElementType.ANNOTATION_TYPE)——注解
 * @Target(ElementType.PACKAGE)——包
 */




/**
 * @Documented: 用于指定被该元 Annotation 修饰的 Annotation 类将被
 * javadoc 工具提取成文档。默认情况下，javadoc是不包括注解的。 定义为Documented的注解必须设置Retention值为RUNTIME。 @Inherited: 被它修饰的 Annotation 将具有继承性。如果某个类使用了被
 * @Inherited 修饰的 Annotation, 则其子类将自动具有该注解。
 * 比如：如果把标有@Inherited注解的自定义的注解标注在类级别上，子类则可以
 * 继承父类类级别的注解
 * 实际应用中，使用较少
 */


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface MyAnnotation{
//    类型【只能是八种基本数据类型、String类型、Class类型、enum类型、Annotation类型、】
//    以上所有类型的【数组】。
     String value() default "我的自定义注解";//元数据

}