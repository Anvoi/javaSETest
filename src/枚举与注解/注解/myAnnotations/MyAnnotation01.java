package 枚举与注解.注解.myAnnotations;


import java.lang.annotation.*;

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
 *
 * // 最后运行时将对应的使用此元注解的注解类提取成文档，提取后的文档不会包含注解的信息(此【注解类必须声明为运行时】)
 * @Documented: 用于指定被该元 Annotation 修饰的 Annotation 类将被
 * javadoc 工具提取成文档。默认情况下，javadoc是不包括注解的。
 *
 * 定义为[Documented]的注解[必须设置Retention值为RUNTIME]。
 *
 * //注解自动继承性@Inherited ,一个类使用了此注解，其子类同样拥有此注解
 * @Inherited: 被它修饰的 Annotation 将具有继承性。如果某个类使用了被
 * @Inherited 修饰的 Annotation, 则其子类将自动具有该注解。
 * 比如：如果把标有@Inherited注解的自定义的注解标注在类级别上，子类则可以
 * 继承父类类级别的注解
 *
 * 实际应用中，使用较少
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited //此元注解使用之后，使用当前注解的类的子类也会继承到此注解，反射获取子类的注解也是和父类一样的
 public @interface MyAnnotation01 {
    String value();//默认值就加 default
    //就算这里不给值给元数据，在其他地方使用到此自定义注解的时候还是会强制要求赋值给元数据的
//    因为其实注解中的属性 相当于 interface中的一样，前面都是有默认的public static final声明的，所以始终还是要给值
//  看其声明就知道 @interface   像接口的风格

}
