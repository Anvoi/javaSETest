package 反射.reflection;


import org.junit.Test;
import 反射.pojo.User;
import 反射.pojo.User2;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.*;
import java.util.Arrays;

/**反射获取
 *
 */
public class ReflectionTest {
    /**Object 下定义的getClass()方法
     * 获取字节码对象，此方法被所有Object的子类继承
     * 基本所有继承Object的类都继承了getClass()获取字节码对象方法
     * getClass()返回Class类对象，Class对应字节码对象
     */


    /**关于Class
     * Class本身是一个类
     * 《Class对象只能由系统创建》
     * 一个加载的类在JVM中存在《唯一一个Class实例》
     * Class对象对应的是一个加载到JVM中的一个.class字节码文件
     * 每一个类的实例都会记得自己是由哪一个Class实例生成的
     * 通过Class	 可以得到一个类中的所有被加载的结构
     * Class是Reflection的根源，想动态得到，动态加载，运行的类，
     * 只能先获取Class对象
     *
     * 要反射，因为反射是直接拿字节码的，要拿字节码，必须借助
     * Class来接受Object的getClass()返回的Class对象
     *
     */



    @SuppressWarnings("unchecked")
    /**Class常用方法
     *
     */
    @Test
    public void reflectiontest1(){
        //根据全类名获取对应的类的字节码Class 字节码对象
        String classPath = "反射.pojo.User";

        try {
            //动态获取Class对象，以全类名为参数
            //可以用占位符？，后面再强转，也可以当前就声明+强转
            Class<User> clazz = (Class<User>) Class.forName(classPath);

            //已经知道该类的实例，获取实例的Class对象,使用getClass方法
//					new User().getClass();

            //获取当前类的类加载器，使用类加载器根据全类名  来获取Class对象
//				ClassLoader cl2 = this.getClass().getClassLoader();
//			   Class<?> clazz2 = cl2.loadClass("全类名");


            //getName() 获取当前Class对象的实体name的
            String name = clazz.getName();
            System.out.println("Class类对象表示的实体是："+name);//String  pojo.User




            //因为上面的Class<User> 初始化了泛型，所以这里不用强转，直接获取User对象
            User newInstance = clazz.newInstance();

            System.out.println("对象"+newInstance);//User [name=null, password=null]





            //获取当前 类对象的 接口的Class对象    		Class数组接收
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> class1 : interfaces) {
                System.out.println("获取类实现的接口的字节码对象:"+class1.getName());//接口.UserI
            }

            /***
             * User.class == clazz
             * 因为直接类.class 也是一个Class对象
             * Class.forName(path) 和 User.class 是一样的，都是返回对应的Class 字节码对象
             * 字节码对象才可以用Class的API,字节码对象才可以获取类加载器
             * User.class是硬编码的方式，耦合太高，Class.forName(path) 几乎解耦
             *
             *
             *  首先类加载的顺序: 加载----->链接(校验，准备，解析)------>初始化
             * 区别:
             * 　Class.forName得到的class是已经初始化完成的，该方法适合那些在类加载时需要初始化一些东西的情况。比如，加载数据库驱动。
             * 　Classloder.loaderClass得到的class是 还没有链接的，该方法适合在类加载时不需要一些初始化的情况。
             *
             * Class.forName()方式不会触发懒加载
             *
             * 类加载器方式：会触发懒加载，相比之下比forName更快,但是 没有链接和初始化
             * 对象.getClass().getClassLoader()
             *  cl.load("..");
             *
             *
             *
             */
            //返回该类的类加载器   一般获取的类加载器是唯一的
//			ClassLoader classLoader = User.class.getClassLoader();
            ClassLoader classLoader = clazz.getClassLoader();
            System.out.println("User.class类的类加载 器    "+classLoader);//sun.misc.Launcher$AppClassLoader@1372a1a



            //获取 <当前Class的 《对象》>   父类的     Class对象
            Class<? super User> superclass = clazz.getSuperclass();//这里User的父类就是Object
            System.out.println("getSuperClass()获取的："+superclass.getName());//java.lang.Object

            //getSuperclass() 和 getSuperClass() 没有区别
            Class<? super User> superclass2 = clazz.getSuperclass();
            System.out.println("getSuperclass()获取的："+superclass2.getName());//java.lang.Object



            //获取对象
            User newInstance2 = clazz.newInstance();
            //返回Class的对象的类中的成员变量的属性 制定成员变量名获取其值
			Field field2 = clazz.getDeclaredField("name");
            /**首先，
             * 这里的clazz是属于Class直接拿类字节码创建的，是类的类对象，只能由系统创建，clazz只是从堆的方法区中"拿出来Class对象的而已
             *
             * 凡是类拥有的属性和方法，clazz都拥有，
             *
             * 这里访问属性的关键区别在于
             * getField 方法和 getDeclaredField 方法
             *
             * getField:只能访问公共（public）的属性
             * getDeclaredField：可以访问所有的属性，当然，私有的最好先获取权限再使用
             *
             *
             *类中的静态属性可以被反射修改(类可修改(直接修改字节码中的资源))
             * 类中的非静态属性，如果反射去获取是没有的，因为非静态属性类中没加载，只是等实例化在实例中才会加载，这个类是没有加载的
             *
             *User类使用的也不是直接用字节码的，而是使用伴随类字节码产生而出现Class对象为模板
             *
             */


            //使用clazz获取的对象，用对象来获取属性,当然，最后还是操作对象的属性的，没理由去操作类的属性
//            Field field2 = newInstance2.getClass().getDeclaredField("name");
            //暴力获取权限
            field2.setAccessible(true);
            field2.set(newInstance2, "黄进");//投进对象，指定修改此对象里面的这个属性
//            System.out.println("类中的静态属性可以被反射修改, 修改后name="+User.getName());


            Object name2 = field2.get(newInstance2);
            System.out.println("设置后的属性       	"+name2);


            //可以获取Class对象的类型
            Class c1 = Object.class; //类
            Class c2 = Comparable.class;//接口
            Class c3 = String[].class;//引用类型数组
            Class c4 = int[][].class;//基本数据类型数组
            Class c5 = ElementType.class;//枚举
            Class c6 = Override.class; //注解
            Class c7 = int.class;//基本数据类型
            Class c8 = void.class;//void
            Class c9 = Class.class;//Class本身就是一个类，也可获取Class对象

            int[] a = new int[10];
            int[] b = new int[100];
            Class<? extends int[]> class1 = a.getClass();
            Class<? extends int[]> class2 = b.getClass();

//			基本数据类型数组或基本数据类型的Class只是和其声明有关，声明一样，Class对象一样
            System.out.println(class1==class2);//true



        } catch (ClassNotFoundException e) {
            System.out.println("此全类名不存在，无法创建此类");
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




    }


    /**类的加载 & ClassLoader（类加载器）的理解
     *
     *
     * 类的加载
     *加载：<将class文件字节码内容加载到内存中>，并将这些,<静态数据转换成方法区的运行时
     * 数据结构，然后生成一个"代表这个类"的java.lang.Class对象>，作为方法区中类数据的访问
     * 入口（即引用地址）。所有需要访问和使用类数据<只能通过这个Class对象>。
     * 这个<加载的过程需要类加载器参与>。
     *
     *
     * 链接：<将Java类的"二进制代码"合并到"JVM的运行状态之中"的过程>
     * 验证：确保加载的类信息符合JVM规范，例如：以cafe开头，没有安全方面的问题
     * 准备：正式为<类变量（static）分配内存并设置类变量默认初始值的阶段>，这些内存
     * 都<将在方法区中进行分配>。
     * 解析：虚拟机常量池内的符号引用（常量名）替换为直接引用（地址）的过程。
     *
     * 初始化：
     * 执行类构造器<clinit>()方法的过程。
     * 类构造器<clinit>()方法是由"编译期"<自动收集类中所有类变量的赋值动作和静态代码块中的语句合并产生的>
     * （<类构造器<clinit>()是构造类信息的，不是构造该类对象的构造器>）。
     * 当初始化一个类的时候，如果发现其父类还没有进行初始化，则需要先触发其父类
     * 的初始化。
     * 虚拟机会保证一个类的<clinit>()方法在<多线程环境中被正确加锁和同步>
     *
      */
    /***例子理解
     * public class ClassLoadingTest {
     *      public static void main(String[] args) {
     *              System.out.println(A.m);
     *      }
     *
     * }
     * class A {
     *      static {
     *          m = 300;
     *      }
     *     static int m = 100;
     * }
     * //第二步：链接结束后m=0
     * //第三步：初始化后，m的值由<clinit>()方法执行决定
     * // 这个A的类构造器<clinit>()方法由类变量的赋值和静态代码块中的语句按照顺序合并
     * 产生，类似于
     *  <clinit>(){
     *       m = 300;
     *       m = 100;
     * }
     *
     */

    /**基于例子的理解：
     * 第一步：加载; 此A类被类加载器加载，在堆的方法区中产生唯一的一个java.lang.Class对象对应此类的访问入口
     *
     * 第二步：链接; 此A类的二进制被合并到JVM运行时状态，正式为类变量(static变量) 分配空间并处理默认初始值，
     * 就是这里的 static int m;------->分配内存&设置初始值------>static int m=0;
     *
     * 第三步：初始化; 此A类中的，类构造器（构造类信息的构造器）<clinit>()从类的上往下捕捉赋值动作
     * 这里先是代码块赋值，    m=300
     *  然后是属性直接赋值     m=100
     *
     *
     *  main中输出是100
     *
     *
     *
     */

    /**什么时候会发生类初始化？
     *   类的主动引用（一定会发生类的初始化）
     *       当虚拟机启动，先初始化main方法所在的类
     *       new一个类的对象
             调用类的静态成员（除了final常量）和静态方法
             使用java.lang.reflect包的方法对类进行反射调用
             当初始化一个类，如果其父类没有被初始化，则先会初始化它的父类

         类的被动引用（不会发生类的初始化）
             当访问一个静态域时，只有真正声明这个域的类才会被初始化
                 当通过子类引用父类的静态变量，不会导致子类初始化
             通过数组定义类引用，不会触发此类的初始化
             引用常量不会触发此类的初始化（<常量在链接阶段就存入调用类的常量池中了>）

     *
     */


    /**例子描述什么时候会发生类初始化
     public class ClassLoadingTest {
     public static void main(String[] args) {
     // 主动引用：一定会导致A和Father的初始化
     // A a = new A();
     // System.out.println(A.m);
     // Class.forName("com.atguigu.java2.A");
     // 被动引用
     //这里只是被动的定义此对象的数组和数组长度而已，没有用到类中的成员
     A[] array = new A[5];//不会导致A和Father的
     初始化
     // System.out.println(A.b);//只会初始化
     Father
     // System.out.println(A.M);//不会导致A和
     Father的初始化
     }
     static {
     System.out.println("main所在的类");
     } }
     *
     *
     *class Father {
     * static int b = 2;
     * static {
     * System.out.println("父类被加载");
     * } }
     * class A extends Father {
     * static {
     * System.out.println("子类被加载");
     * m = 300;
     * }
     * static int m = 100;
     * static final int M = 1;
     *
     * //特殊的：
     * //常量在 链接阶段 已经被存进方法区，直接可以进方法区拿，没有要加载到类中其他成员，所以类不会被初始化
     * }
     *
     *
     *
     *
     *
     *
     */

    /***ClassLoader
     * 引导类加载器：
     * 【用C++编写的，是JVM自带的类加载器，负责Java平台核心库，用来“装载核心类库”。该加载器“无法直接获取”】
     *
     *扩展类加载器：
     * 【负责jre/lib/ext目录下的jar包或 “– D java.ext.dirs” “指定目录下的jar包”装入工作库】
     *
     *系统类加载器：
     * 【负责java –classpath 或 “–D java.class.path”所指的目录下的类与jar包装入工作 ，是最常用的加载器】
     *
     *
     */
    @Test
    public void reflectionClassLoader() throws ClassNotFoundException {
        //获取系统类加载器
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        System.out.println("[系统类加载器]"+classLoader);//可以拿到引用地址

        //获取系统类加载器的父类加载器，即扩展类加载器 （）
        classLoader = classLoader.getParent();
        System.out.println("[系统类加载器的父类（扩展类加载器）]"+classLoader);//可以拿到引用地址

        //获取扩展类加载器的父类，即引导类加载器
        classLoader = classLoader.getParent();
        System.out.println("[扩展类加载器的父类，引导类加载器]"+classLoader);//引导类加载器无法直接拿到，null

        //看当前的类是由哪一个加载器加载
        ClassLoader classLoader1 = Class.forName("反射.reflection.ReflectionTest").getClassLoader();
        System.out.println(classLoader1);//打印的类加载器地址和系统加载器一样
/**        所以当前的类是由“系统类加载器加载”的*/


        //测试JDK提供的Object类是由哪一个加载器加载器的
        ClassLoader classLoaderObj = Class.forName("java.lang.Object").getClassLoader();
        System.out.println(classLoaderObj);
        /**发现是null，
         * 其实就是[引导类加载器]，C++写的加载核心类库的类加载器,Object属于和兴类库
         * 因为无法直接获取[引导类加载器]，所以显示null
         * */


        /**6.关于类加载器的一个"主要方法"：
         *      getResourceAsStream(String str):获取类路径下的指定文件的输入流
         */
        InputStream in = null;
        in = this.getClass().getClassLoader().getResourceAsStream("test.properties");
        System.out.println("[使用系统类加载器加载的配置文件流]:"+in);


    }


    /*** 创建运行时类的对象
     *创建类的对象：调用Class对象的newInstance()方法
     *      要 求：  1）类"必须有一个无参数的构造器"。
     *              2）类的"构造器的访问权限需要足够"。
     *
     * 难道没有无参的构造器就不能创建对象了吗？
     *  不是！只要在操作的时候明确的调用类中的构造器，并将参数传递进去之后，才可以实例化操作。
     * 步骤如下：
     *      1）通过Class类的getDeclaredConstructor(Class … parameterTypes)取得本类的指定形参类型的构造器
     *      2）向构造器的形参中传递一个"对象
     *
     *      数组进去"，里面包含了构造器中所需的各个参数。
     *      3）通过Constructor实例化对象。
     */
    @Test
    public void runtext() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //1,根据全类名获取对象
        String name = "反射.pojo.User2";
        Class<?> clazz = Class.forName(name);

        Object userObj = clazz.newInstance();

        User2 user2 = (User2)userObj;

        System.out.println(user2);//User2{name='null'}





        /**就算没有无参构造器，可以用有参构造器，
         * 先获取有参构造器的Constructor对象（专门封装构造器的类），再用此对象来造对应的类的对象*/
        //2,调用指定参数的构造器，生成此类的  "构造器(Constructor)实例"  "这里需要知道参数类型是什么"
        Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(String.class);

//         3,使用对应的有参构造器实例来构建对应的类对象(这时候可以传实际参数了)
        Object user0021 = declaredConstructor.newInstance("User0021");

        User2 user21 = (User2)user0021;

        System.out.println(user21);//User2{name='User0021'}


    }


    /**凡是获取返回方法数组的索引顺序，都是 按 有返回值--->多参数--->少参数*/


    /** 通过反射获取【运行时类】的【完整结构】(1)[构造方法]
        Field、Method、Constructor、Superclass、Interface、Annotation
             实现的全部接口
             所继承的父类
             全部的构造器   (不会去获取对应的父类的构造器，构造器比较特殊)
             全部的方法
             全部的Field
     *
     */

    /**注意: 子类加载的时候，虽然会加载其父类的成员，但是注意，不会加载父类的构造器
            子类本身的构造器中就隐式调用了父类的构造器，注意，只是在子类构造方法内部调用父类构造方法
            并没有加载父类的构造方法，
            所以在获取所有构造方法的时候不会获取到父类的构造方法，因为子类根本不会继承加载父类构造器，只是隐式调用父类构造方法


     */
    @Test
    public void runtextwan() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        //反射
        //获取全部的接口
        /**无论怎么用获取全部构造器，反射都不会去把父类的构造器也拿过来，构造器特殊，返回的都是此Class对象的构造器
         * 不会拿父类的构造器
         *
         */

        Class<?> clazz = Class.forName("反射.pojo.User2");
        //获取此类继承的所有接口的字节码Class对象
        Class<?>[] interfaces = clazz.getInterfaces();
//        该类实现的接口:[interface 反射.pojo.userIt]
        System.out.println("该类实现的接口:"+Arrays.toString(interfaces));

        //获取所继承的父类
        System.out.println("继承的父类是;"+clazz.getSuperclass().getName());



        /**获取"全部的构造器"的Constructor对象
         *
         * 1,返回此 Class 对象所表示的类的所有public构造方法。
         * 注意，只能获取所有的public构造方法，和getField一样只能获取到public的类的属性
         * 不会拿父类的构造器
         * 注意：只能拿public，只能拿public，其他所有权限的都拿不了
         * */
        Constructor<?>[] constructors = clazz.getConstructors();

        System.out.println("获取到的构造器数组：[权限 全类名(参数类型)]");
        System.out.println(Arrays.toString(constructors)+"\n");
        /**
         [  数组中构造器的索引排列是，从多参到少参构造器
            public 反射.pojo.User2(java.lang.String),
            public 反射.pojo.User2()]
         */
                                    //使用其中一个构造器传参数造对象
        Object userhuang = constructors[0].newInstance("黄进");
        User2 user2huang = (User2)userhuang;

        System.out.println(user2huang);//User2{name='黄进', pwd='null'}


        System.out.println("\n\n\ngetDeclaredConstructors()方法,获取所有，包括私有的构造器");
        /***2，返回此 Class 对象表示的类声明的所有构造方法。
         *获取所有，包括私有的构造器(当然，私有的要先获取一下权限才可以调用)
         * 注意：getDeclaredConstructors方法不拿父类的方法
         */
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();


        System.out.println(Arrays.toString(declaredConstructors));
        /**[private 反射.pojo.User2(java.lang.String,java.lang.String),
         *  public 反射.pojo.User2(java.lang.String),
         *  public 反射.pojo.User2()]
                数组中构造器的索引排列是，从多参到少参构造器
         *
         */
        System.out.println("Constructor构造器对象类里面的方法演示:");

//          获取构造方法名
        System.out.println("构造方法名："+declaredConstructors[0].getName());//反射.pojo.User2

//          获取修饰符
        System.out.println(declaredConstructors[0].getModifiers());//2
        /** 2代表private          (当前类范围)
         *  0代表缺省             (当前包范围)
         *  4代表protected       (私有的，本类可用，但是给跨包的子类开特权，跨包子类可用)
         *  1代表public           (公开的)
         * */

//        取得参数类型
        Class<?>[] parameterTypes = declaredConstructors[0].getParameterTypes();//返回参数类型数组
        System.out.println(Arrays.toString(parameterTypes));
        //参数类型:[class java.lang.String, class java.lang.String]

        //如果对应的构造器私有，使用需要先获取权限
//        declaredConstructors[2].setAccessible(true);

//        指定参数列表的，指定单独拿目标私有构造器
        Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(String.class);

        System.out.println(declaredConstructor);
    }


    /**通过反射获取【运行时类】的【完整结构】(2) [方法]
     *
     */
    @Test
    public void showMethod() throws ClassNotFoundException {
        /**注意：
         * 方法和构造方法是两回事，方法中只能拿方法，不能拿构造方法，构造方法中只能拿构造方法，不能拿方法
         * */

//        获取所有公有的方法 （包括父类的）
        String classPath = "反射.pojo.User2";
        Class<?> clazz = Class.forName(classPath);

        Method[] methods = clazz.getMethods();

        for (Method method : methods) {
            System.out.println(method);
        }
        /**这样拿会直接把所有的public的方法拿到，包括从父类中继承加载过来的发，从间接父类中继承加载过来的方法，
         * 父类的get，set等等
         * 注意，是本类继承加载过来的，奔类根据父类独立再加载一份的自己的才会有，和其他的类无关
         * 还是在本类 ，后面将父类继承的独立复制一份给本类的才是这里输出的内容
         * 并不是说父类有的，子类一定会有，很多父类的是不一会给子类继承的
         * 比如父类重写Object的toString方法，重写方法不会给子类继承的,所以这里打印的也没有父类的toString方法
         *
         * 而且注意: <当前此方法只能拿到公开的(public)的方法，私有的拿不到,当然，缺省和保护的也拿不了>
         *      注意：只能拿public，只能拿public，其他所有权限的都拿不了
         *
         *
         *
         * [public java.lang.String 反射.pojo.User2.toString(),
         * public void 反射.pojo.User2.show2(),
         * public java.lang.String 反射.pojo.User.getName(),
         * public void 反射.pojo.User.setName(java.lang.String),
         * public java.lang.String 反射.pojo.User.getPassword(),
         * public void 反射.pojo.User.setPassword(java.lang.String),
         * public final void java.lang.Object.wait() throws java.lang.InterruptedException,
         * public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException,
         * public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException,
         * public boolean java.lang.Object.equals(java.lang.Object),
         * public native int java.lang.Object.hashCode(),
         * public final native java.lang.Class java.lang.Object.getClass(),
         * public final native void java.lang.Object.notify(),
         * public final native void java.lang.Object.notifyAll()]

         *
         */


        //只拿本类的，不拿父类的，拿本Class中所有的方法，包括私有方法
        Method[] declaredMethods = clazz.getDeclaredMethods();

        System.out.println("\n获取所有本类的（不拿父类），所有方法（包括私有）");
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod);
        }


        //获取方法名
        System.out.println(declaredMethods[0].getName());
        //获取权限修饰符
        System.out.println(declaredMethods[0].getModifiers());
        //获取返回类型
        System.out.println(declaredMethods[0].getReturnType());
        //获取抛出(声明)异常的类型
        Class<?>[] exceptionTypes = declaredMethods[0].getExceptionTypes();
        for (Class<?> exceptionType : exceptionTypes) {
            System.out.println(exceptionType);
        }



    }


    /**获取所有属性
     *
     */
    @Test
    public void testGetFileds() throws ClassNotFoundException {
        String classPath = "反射.pojo.User2";
        Class<?> clazz = Class.forName(classPath);

        //获取所有public的属性，包括从父类继承而来的属性
        Field[] fields = clazz.getFields();

        for (Field field : fields) {
            System.out.println("属性名:"+field.getName());
        }
        //只能获取到此类public的属性和从父类中public继承来的属性


        Field[] declaredFields = clazz.getDeclaredFields();

        /**老规矩，getDeclaredFields可以得到此类的所有成员属性，但是不能获取此类继承的父类属性
         *
         *
         */
        for (Field declaredField : declaredFields) {

            System.out.println("属性的权限修饰:  "+declaredField.getModifiers());
            System.out.println("属性的类型(返回类型的Class对象): "+declaredField.getType());
            System.out.println("属性名:"+declaredField.getName());
            //一般获取类型的，都是返回java.lang.Class对象,该类对应的唯一的在堆中方法区的Class对象
        }


    }


    /**获取类所在的包 & 获取注解(?) & 获取泛型
     *
     */
    @Test
    public void testGetAnnotation() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        String classPath = "反射.pojo.User2";
        Class<?> clazz = Class.forName(classPath);

        //获取此类所在的包
        System.out.println(clazz.getPackage());//package 反射.pojo



        //获取注解
        /**6. Annotation相关
         *  get Annotation(Class<T> annotationClass)
             getDeclaredAnnotations()
         *
         */
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            System.out.println("获取注解："+annotation);//获取注解：@反射.pojo.MyAnnotation(value=hello)
        }

//public class User2 extends User<String>作为当前的clazz

        //获取运行时类的带泛型的父类
        /**7.泛型相关
                获取父类泛型类型：Type getGenericSuperclass()
                泛型类型：ParameterizedType
                获取实际的泛型类型参数数组：getActualTypeArguments()
         */

        //获取父类的“泛型” ，此时是多态给Type接口
        Type genericSuperclass = clazz.getGenericSuperclass();//因为泛型可能不止一个，所以用Type接受了
        System.out.println("父类泛型:"+genericSuperclass);



        //将当前类extends的父类的泛型类型的Class拿到（这里是拿到String的Class对象）


        //先将Type类型的父类泛型强转回ParameterizedType子实现类
        ParameterizedType parameterizedType = (ParameterizedType)genericSuperclass;

        /**此子实现类parameterizedType的对象使用 getActualTypeArguments()方法
         * 获取当前clazz的类继承的父类<泛型>的<泛型> 就是把获取那个泛型类(已初始化的)的Class对象拿到
         * 返回"父类的泛型类"<-->(String) 的 Class对象
         *      getActualTypeArguments返回泛型的Class对象(这里是父类的泛型)
         */
        /** getActualTypeArguments返回泛型的Class对象(这里是父类的泛型) */
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();//返回数组，因为


        for (Type actualTypeArgument : actualTypeArguments) {
            System.out.println(actualTypeArgument.getTypeName());
        }
        /**
         * 父类泛型:反射.pojo.User<java.lang.String>
         * class java.lang.String
         */

        //将父类的 泛型类的 Class对象 进行操作
        Class clazzCart = (Class) actualTypeArguments[0];
        Object cart = clazzCart.newInstance();
        System.out.println(cart);


    }


}
