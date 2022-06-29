package 反射.reflection;


import org.junit.Test;
import proxy.ProxyTest01;
import 反射.pojo.User2;
import 反射.pojo.userIt;
import 反射.接口.UserI;

import java.lang.reflect.*;

/**反射运行
 *
 */
public class ReflectionRunTimeTest {

    /**调用指定方法
     *
     */
    @Test
    public void test01() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        /**通过反射，调用类中的方法，通过Method类完成。步骤：
         * 1.通过Class类的getMethod(String name,Class…parameterTypes)方法取得
         * 一个Method对象，并设置此方法操作时所需要的参数类型。
         * 2.之后使用Object invoke(Object obj, Object[] args)进行调用，并向方法中
         * 传递要设置的obj对象的参数信息。
         *
         *
         * Method类中有重载的invoke方法，"一个是可以投入一个Object[]参数数组"
         *                            "一个是可以投入可变形参"
         *
         *
         * Object invoke(Object obj, Object[] args)
         * Object invoke(Object obj, Object … args)
         *
         * Object返回值，如果没有返回值，就是null
         * 如果此方法是静态的方法，拿前面的调用者参数可以为null
         *
         *
         *说明：
         * 1.Object 对应原方法的返回值，[若原方法无返回值]，[此时返回null]
         * 2.若原方法若为静态方法，此时形参Object obj可为null
         * 3.若原方法形参列表为空，则Object[] args为null
         * 4.若原方法声明为private,则需要在调用此invoke()方法前，显式调用
         * 方法对象的setAccessible(true)方法，将可访问private的方法。
         */
        String path = "反射.pojo.User2";
        Class<?> clazz = Class.forName(path);


        //1，先取得此Class对象中的方法，封装进方法对象Method中
        //根据<方法名获取对应的方法对象>
        Method invok1 = clazz.getMethod("invok1", String.class);


        //非静态方法需要实例才可以调用，类是不知道此方法的
        //使用Method对象的方法进行调用此方法  方法对象.invoke(此方法的调用者,此方法需要的参数)
        invok1.invoke(clazz.newInstance(),"张玮伽");

        //调用静态方法(静态方法伴随类的加载而加载，不需要对象也可以调用,因为可以直接通过类调用)
        //毕竟这里的方法对象就是从Class的对象clazz中拿来的，
        // Method对象当然知道其类，故其类中的静态方法也可以不用对象就直接调用
        Method invok2 = clazz.getMethod("invok2", String.class);
        invok2.invoke(null,"静态方法参数");
        /**注意了：这里的就算是静态方法，不放实例，直接使用clazz（类）去调用，这里因为参数列表的限制
         * 所以第一个参数还是要放，不过可以放null,代表不用实例来，那毕竟是clazz获取来的Method实例
         * 所以拿自己有的类是可以拿到的，然后直接调用类里面的静态
         * */


        //调用私有（private）方法
        // 如果要获取得到私有的方法，需要用Method的getDeclaredMethod方法,老规矩了
        Method invokShi = clazz.getDeclaredMethod("invokShi");

        //需要先暴力获取权限
        invokShi.setAccessible(true);

        //调用
        invokShi.invoke(clazz.newInstance());


    }


    /**调用指定属性
     *
     */
    @Test
    public void test02() throws ClassNotFoundException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        /**在反射机制中
         * 方法对象/属性对象/构造器对象.setAccessible(true \ false);//关闭或开启java语言访问检查
         *
         *，可以直接通过Field类操作类中的属性，通过Field类提供的set()和get()方法就可以完成设置和取得属性内容的操作。
         *
         *       public Field getField(String name) 返回此Class对象表示的类或接口的指定的public的Field。
         *       public Field getDeclaredField(String name)返回此Class对象表示的类或接口的指定的Field。
         *
         *
         *  在Field中：
         *       public Object get(Object obj) 取得指定对象obj上此Field的属性内容
         *       public void set(Object obj,Object value) 设置指定对象obj上此Field的属性内容
         */

        //首先有Class对象
        String path = "反射.pojo.User2";
        Class<?> clazz = Class.forName(path);
        User2 user2 = (User2)clazz.newInstance();

        //获取公开的属性对象 （此方法也只能获取公开的）
        Field gkaiField = clazz.getField("gkai");

        //设置实例的，公开属性的值
        gkaiField.set(user2,"公开属性");

        //获取对应实例的此属性
        Object strObj = gkaiField.get(user2);

        System.out.println("获取实例中的公开属性: "+strObj);//获取实例中的公开属性: 公开属性



        //获取实例的私有属性
        Field name = clazz.getDeclaredField("name");

        //凡是私有属性，直接反射此操作，就需要先获取权限
        name.setAccessible(true);

        //设置实例中私有属性的值
        name.set(user2,"私有属性");

        Object nameObj = name.get(user2);
        System.out.println("获取实例的私有属性: "+nameObj);//获取实例的私有属性: 私有属性


        /**关于setAccessible方法的使用
         *  Method和Field、Constructor对象都有setAccessible()方法。
         *  setAccessible启动和禁用访问安全检查的开关。
         *  参数值为true则指示反射的对象在使用时应该<取消Java语言访问检查。>
         *       提高反射的效率。如果代码中必须用反射，而该句代码需要频繁的被
         *      调用，那么请设置为true。
         *       使得原本无法访问的私有成员也可以访问
         *
         *  参数值为false则指反射的对象应该<实施Java语言访问检查。>
         */


    }


    /**动态代理
     * 代理设计模式的原理:
     *      使用一个代理将对象包装起来, 然后用该代理对象取代原始对象。
     *      任何对原始对象的调用都要通过代理。
     *      代理对象决定是否以及何时将方法调用转到原始对象上。
     *   之前为大家讲解过代理机制的操作，属于"静态代理"，"特征是代理类和目标"
     *      对象的类都是在编译期间确定下来，"不利于程序的扩展。"
     *      同时，'每一个代理类只能为一个接口服务'，这样一来程序开发中必然"产生过多的代理"。
     *      <>最好可以通过一个代理类完成全部的代理功能。
     *
     *
     *
     * 动态代理是指客户通过代理类来调用其它对象的方法，并且是在程序运行时
     *      根据需要动态创建目标类的代理对象。
     * 动态代理使用场合:
     *      调试
     *      远程方法调用
     * 动态代理相比于静态代理的优点：
     *      抽象角色中（接口）声明的所有方法都被转移到调用处理器一个集中的方法中
     *      处理，这样，我们可以更加灵活和统一的处理众多的方法。
     *
     *
     *  Java动态代理相关API:
     *  Proxy 类：专门完成代理的“操作类”，是“所有动态代理类的父类”。通过此类为一个或多个接口动态地“生成实现类”。
     *  提供用于【创建动态代理类】和【动态代理对象】的【静态方法】
     *      static Class<?> getProxyClass(ClassLoader loader, Class<?>... interfaces) 创建一个动态代理类所对应的Class对象
     *      static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces,InvocationHandler h) 直接创建一个动态代理对象
     *                                     ( 类加载器          , 得到被代理类实现的全部接口 , 得到InvocationHandler接 口的实现类实例)
     *
     * */
    @Test
    public void test03() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {

        /**通过Proxy静态方法来直接获取动态代理对象*/
//        User2  target = new User2();
        Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass("反射.pojo.User2");
        Class<?>[] interfaces = clazz.getInterfaces();

        //使用此对象注册测试代理类
        Constructor<ProxyTest01> declaredConstructor = ProxyTest01.class.getDeclaredConstructor(Object.class);
        ProxyTest01 proxy = declaredConstructor.newInstance(clazz.newInstance());


//        通过主题接口获取  代理的引用  现在只是用（类加载器+接口类型+代理类的实例（代理类是已经被User2实例注册过的））
        userIt userIt1 = (userIt)Proxy.newProxyInstance(clazz.getClassLoader(),interfaces,proxy);


        //通过代理类返回的
        userIt1.invok1("使用代理类的静态方法返回的对象执行所有的方法，都是执行代理类中的invoke方法");

        //两个问题 1，使用这样的代理有什么好处
//                2，Proxy的这两个方法是怎么做到无论此对象无论执行什么方法都是执行代理类中的invoke



    }




}
