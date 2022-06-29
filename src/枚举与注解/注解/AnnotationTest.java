package 枚举与注解.注解;

/**注解*/
public class AnnotationTest {


    /**
     *主要内容：
     *  注解(Annotation)概述
     *  常见的Annotation示例
     *  自定义Annotation
     *  JDK中的元注解
     *  利用反射获取注解信息（在反射部分涉及）
     *  JDK 8中注解的新特性
     *
     */


/** 注解(Annotation)概述
 *
 * 注解是一种趋势
 *
 *从 JDK 5.0 开始, Java 增加了对[元数据](MetaData) 的支持, 也就是
 * Annotation(注解)
 *
 * Annotation 其实就是【代码里的特殊标记】, 这些标记【可以在【编译】, 【类加
 * 载】, 【运行时】被读取, 【并执行相应的处理】。通过使用 【Annotation】, 程序员
 * 可以在【不改变原有逻辑】的情况下, 在【源文件中嵌入一些补充信息】。
 *
 * 【代码分析工具】、【开发工具】和【部署工具】可以通过这些补充信息【进行验证】或者【进行部署】。
 *
 *
 *
 * Annotation 可以【像修饰符】一样被使用, 可用于【修饰【包】,【类】, 【构造器】, 【方 法】, 【成员变量】, 【参数】, 【局部变量】的声明,
 *这些信息【被保存在 Annotation】的 [“name=value”] 对中
 *
 *
 *
 * 在[JavaSE]中，注解的使用目的比较简单，例如[标记过时的功能]，
 * [忽略警告]等。
 *
 * 在【JavaEE/Android】中注解占据了【更重要的角色】，例如
 * 【用来配置应用程序的【任何切面】】，【代替JavaEE旧版中所遗留的【繁冗】
 * 【代码】和【XML配置】等。
 *
 * 【未来的开发模式】 【都是基于注解】的，【JPA】是【基于注解】的，
 * 【Spring2.5以上都是基于注解的】，【Hibernate3.x以后也是基于注解】的，
 * 【现在的Struts2有一部分也是基于注解】的了，【注解是一种趋势】，一定程度上
 *
 * 可以说：【框架】 = 【注解 + 反射 + 设计模式】。
 * */


/*** 常见Annotation示例:
 * 使用 Annotation 时要在其前面增加 @ 符号, 并把该 Annotation [当成
 * 一个[修饰符]使用。用于[修饰它支持的程序元素]
 */

 /**
 * 示例一：生成【[文档]相关的注解】
 * @author 标明开发该类模块的作者，多个作者之间使用,分割
 * @version 标明该类模块的版本
 * @see [参考转向，也【就是相关主题】(可以实现链接跳转) ，可联合不同的文档引入
 * 主要是引用对应的其他类或其他信息，让读者获取到更多更详细的联合文档
 * @since 从[哪个版本开始增加]的
 * @param 对方法中某参数的说明，如果[【没有参数】就不能写]
 * @return 对方法返回值的说明，如果【方法的返回值类型【是void】就【不能写】
 * @exception 对方法可能抛出的异常进行说明 ，【如果方法没有用throws显式抛出的异常】就【不能写】
 * 其中
 * 【@param @return 和 @exception】 这【三个标记】都是【[只]用于方法】的。
 * @param的格式要求：@param 【形参名】 【形参类型】 【形参说明】
 * @return 的格式要求：@return 【返回值类型】 【返回值说明】
 * @exception的格式要求：@exception 【异常类型】 【异常说明】
 * 【@param和@exception可以并列多个】
 */


    /**示例二:在编译时进行格式检查(JDK内置的三个基本注解)
     * @Override: 限定重写父类方法, 该注解只能用于方法
     *
     * //标记过时元素，有结构危险或有更好的选择
     * @Deprecated: 用于表示所修饰的【元素】(类, 方法等)已过时。通常是因为
     * 所修饰的[结构危险]或[存在更好的选择]
     *
     * //抑制编译器警告
     * @SuppressWarnings: 抑制编译器警告
     *
     */


    /**
     *  示例三：跟踪代码依赖性，实现替代配置文件功能
     *  Servlet3.0提供了注解(annotation),使得不再需要在web.xml文件中进行Servlet的部署。
     * @WebServlet("/login")
     * public class LoginServlet extends HttpServlet {
     * private static final long serialVersionUID = 1L;
     * protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
     * ServletException, IOException { }
     * protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
     * ServletException, IOException {
     * doGet(request, response);
     * } } <servlet> <servlet-name>LoginServlet</servlet-name> <servlet-class>com.servlet.LoginServlet</servlet-class>
     * </servlet> <servlet-mapping> <servlet-name>LoginServlet</servlet-name> <url-pattern>/login</url-pattern>
     * </servlet-mapping>
     */

    /** spring框架中关于“事务”的管理
     * @Transactional(propagation=Propagation.REQUIRES_NEW,
     * isolation=Isolation.READ_COMMITTED,readOnly=false,timeout=3)
     * public void buyBook(String username, String isbn) {
     * //1.查询书的单价
     * int price = bookShopDao.findBookPriceByIsbn(isbn);
     * //2. 更新库存
     * bookShopDao.updateBookStock(isbn);
     * //3. 更新用户的余额
     * bookShopDao.updateUserAccount(username, price); }
     * <!-- 配置事务属性 --> <tx:advice transaction-manager="dataSourceTransactionManager" id="txAdvice">
     * <tx:attributes>
     * <!-- 配置每个方法使用的事务属性 --> <tx:method name="buyBook" propagation="REQUIRES_NEW"
     * isolation="READ_COMMITTED" read-only="false" timeout="3" />
     * </tx:attributes>
     * </tx:advice>
     */


    /** 自定义 Annotation
     *  定义新的 Annotation 类型[使用 @interface 关键字]
     *
     *  【自定义注解】【自动继承了【java.lang.annotation.Annotation】【接口】
     *
     *  Annotation 的【成员变量】在 Annotation 定义中【以无参数方法的形式来声明】。其
     * 【方法名和返回值】定义了该成员的【名字和类型】。我们称为【配置参数】。
     *
     * 类型【只能是八种基本数据类型、String类型、Class类型、enum类型、Annotation类型、】
     * 以上所有类型的【数组】。
     *
     *  可以在【定义 Annotation 的成员变量时为其指定初始值】,
     * 指定成员变量的初始值可使用 【default 关键字】
     *
     *  如果只有【一个】【参数成员】，建议使用【参数名为value】
     *
     *  如果定义的注解【含有配置参数】，那么【使用时必须指定参数值】，【除非它有默认
     * 值】。【格式】是【“参数名 = 参数值”】，如果【只有一个参数成员】，且【名称为value】，
     * 【可以省略“value=”】
     *
     *  【没有成员定义的 Annotation】 称为【标记】; 【包含成员变量的 Annotation】 称为【元数据 Annotation】
     *
     *
     * 注意：[自定义注解][必须配上【注解的信息处理流程】]才有意义。
     */


    /**JDK 中的元注解
     *
     * JDK 的元 Annotation 用于修饰其他 Annotation 定义
     *
     * JDK5.0提供了4个标准的meta-annotation类型，分别是：
     *      Retention
     *      Target
     *      Documented
     *      Inherited
     *
     * 元数据的理解：
     * String name = “atguigu”;
     */


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
     *
     */

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


    /**Java 8对注解处理提供了两点改进：可重复的注解及可用于类型的注解。此外，
     * 反射也得到了加强，在Java8中能够得到方法参数的名称。这会简化标注在方法
     * 参数上的注解
     *
     * (功能如其名)
     *
     * 实现步骤:
     * 1,首先在你的自定注解中声明 [可重复的] 元注解@Repeatable
     * 2,然后@Repeatable(MyTigers.class)  将另一个对应的自定义的注解库类的字节码放进去作为仓库
     *
     *反射时候获取到的不是哪个注解,而是存放此注解的库MyTigers注解的对象
     *
     */


    /**类型注解：@Target中
     *  JDK1.8之后，关于元注解@Target的参数类型ElementType枚举值多了两个：
     * TYPE_PARAMETER,TYPE_USE。
     *
     *  在Java 8之前，注解只能是在声明的地方所使用，Java8开始，注解[可以应用
     * 在任何地方。]
     *
     *  ElementType.TYPE_PARAMETER 表示该[注解能写在类型变量]的声明语
     * 句中（如：[泛型声明]）。
     *  ElementType.TYPE_USE [表示该注解能写在使用类型的任何语句中]。
     *
     * @Target中
     * [ElementType.TYPE_PARAMETER]-----[可以声明语句中使用]
     *
     * 比如:public static <@MyAnnotation T> void method(T t) { }
     * 比如: int a = (@MyAnnotation int) 2L;
     *
     *
     * [ElementType.TYPE_USE]-----[可以在任何位置使用]
     *
     *
     *
     */






}
