package 泛型;

import org.junit.Test;
import 泛型.personson.DAO;
import 泛型.泛型接口.Comparable;
import 泛型.泛型类.GenTest;

import java.util.*;

public class GenericTest {
    /** 泛型：标签
     *
     *  举例：
     *  中药店，每个抽屉外面贴着标签
     *  超市购物架上很多瓶子，每个瓶子装的是什么，有标签
     *
     *  泛型的设计背景
     * 集合容器类在设计阶段/声明阶段[不能确定]这个[容器到底实际存的是什么类型]的
     * 对象，
     * 所以在[JDK1.5之前只能把元素类型设计为Object]，
     * [JDK1.5之后使用泛型]来解决。
     *
     * 因为这个时候[除了元素的类型不确定]，[其他的部分是确定的，例如关于这个元素如何保存，如何管理等是确定的]
     * ，因此此时把元素的类型设计成一个参数，这个类型参数叫做泛型。
     * Collection<E>，List<E>，ArrayList<E> 这个<E>就是类型参数，即泛型。
     *
     */


    /**泛型的概念
     *  所谓泛型，就是【允许在定义类、接口时】通过一个【标识表示类中某个【属性的类型】或者是某个【方法的返回值及参数类型】。
     * 这个类型参数[将在使用时]（例如，[继承或实现这个接口，用这个类型声明变量、创建对象时）]确定（即传入【实际的类型参数，也称为【类型实参】】）。
     *
     *  从【JDK1.5】以后，Java引入了“【参数化类型（Parameterized type）】(将类型当参数用)”的概念，
     * 允许我们 【运行时传参给予类型】【在创建集合时再指定集合元素的类型】，正如：List<String>，这表明
     * 该List【只能】保存字符串类型的对象。
     *
     *  JDK1.5【改写了【集合框架】中的全部接口和类】，为这些接口、【类增加了【泛型支持】，
     * 从而可以在【声明集合变量、创建集合对象时】【传入类型实参】。
     */


    /**那么为什么要有泛型呢，直接Object不是也可以存储数据吗？
     * 1. 解决元素存储的安全性问题，好比商品、药品标签，不会弄错。
     * 2. 解决获取数据元素时，需要类型强制转换的问题，好比不用每回拿商品、药
     * 品都要辨别。
     *
     * Object太没有限制了，什么都能往里面放，
     * 1，当取出的时候要看是什么类型
     * 2，取出时候可能会用错，有时强转等，调用等调用错，非常不安全
     *
     *泛型在给予类型参数初始化后统一只能放此类型，[有明确类型限制]
     *      放的时候统一定要放此类型的，拿的时候就[不用一个一个看是什么类型]，直接拿来用就行,安全性比较高
     *
     *Java【泛型可以保证】如果程序  【在编译时没有发出警告】----【运行时就不会产生】
     *      ClassCastException异常。同时，代码更加简洁、健壮。
     */


    @Test
    public void ListGeneric(){
        //比如此ArrayList集合要给泛型，你既然要用，就要给类型参数，不给不行，不给就不要用，
        ArrayList<String> list = new ArrayList<>();

        list.add("黄进");
        list.add("陈锋");
        list.add("张玮伽");
        list.add("蔡锦涛");


//        遍历方式一 //确定就时需要知道类型，就是要硬编码
//        for (String s : list) {
//            System.out.println(s);
//        }

//        遍历方式二，因为集合基本实现了iterator接口，重写了其迭代的方法
//        用此遍历只要求你实现并重写了iterator的方法，不用知道你是什么类型
//        不用知道类型就可以达到迭代代理的层次，就是不同类型传进来
//        都可以用这同一套迭代器和迭代方法，不用改代码来适配不同类型，不用写多份代码

        Iterator<String> iterator = list.iterator();
//        iterator.hasNext()往下读，判断下一个还有没有
        while(iterator.hasNext()){
//            next() 将下一个索引的返回
            System.out.println(iterator.next());

        }

        HashMap<String, Integer> map = new HashMap<>();
        map.put("张玮伽",21);
        map.put("张俊豪",21);
        map.put("陈锋",21);
        map.put("张俊豪",21);

//        添加失败
//        map.put(12,"asda");

        //将map的键值对返回Entry键值对集合多态给Set接口
        Set<Map.Entry<String, Integer>> entries = map.entrySet();

        //返回它的迭代器
        Iterator<Map.Entry<String, Integer>> iterator1 = entries.iterator();

        while(iterator1.hasNext()){
            Map.Entry<String, Integer> entry = iterator1.next();

            System.out.println(entry.getKey()+","+entry.getValue());


        }


    }


    /**自定义泛型结构
     *       自定义泛型类
     *       自定义泛型接口
     *       自定义泛型方法
     *
     *
     * 1.泛型的声明
     * interface List<T> 和 class GenTest<K,V>
     * 其中，T,K,V不代表值，而是表示类型。这里使用任意字母都可以。
     * 常用T表示，是Type的缩写。
     *
     *
     * 2.泛型的实例化：
     * 一定要在类名后面指定类型参数的值（类型）。
     * 如：
     * List<String> strList = new ArrayList<String>();
     * Iterator<Customer> iterator = customers.iterator();
     *       T只能是类，不能用基本数据类型填充。但可以使用包装类填充
     *       把一个集合中的内容限制为一个特定的数据类型，这就是generics背后
     *      的核心思想
     *
     *
     *
     *
     *
     *
     *JDK 1.5 之前
     *      Comparable c = new Date();
     * System.out.println(c.compareTo("red"));
     *
     *
     * JDK 1.5
     * Comparable<Date> c = new Date();
     * System.out.println(c.compareTo("red"));
     *
     * 体会：使用泛型的主要优点是能够在编译时而不是在运行时检测错误
     *
     *
     *
     *
     /**
     *
     * 自定义泛型结构：泛型类、泛型接口
     * 1. 泛型类[可能有多个参数]，此时应将多个参数一起放在尖括号内。比如：
     * <E1,E2,E3>
     *
     * 2. 泛型类的构造器如下：public GenericClass(){}。
     * 而下面是[错误的]：public GenericClass<E>(){}
     *
     * 3. 实例化后，操作原来泛型位置的结构必须与指定的泛型类型一致。
     *
     * 4. 泛型不同的引用不能相互赋值。
     * >尽管在编译时ArrayList<String>和ArrayList<Integer>是两种类型，但是，在运行时只有
     * 一个ArrayList被加载到JVM中。
     *
     * 5. 泛型[如果不指定，将被擦除,[指的是编译期不给],[运行期就算给了也擦除]],
     * [因为当前类的泛型在编译期给是没有意义的,JVM也不会允许你在编译期给当前类实参泛型,所以当前类的泛型在运行期给不给都会被擦除]
     * [对于父类和实现的接口而言,是可以在编译期给泛型实参的,所以会保留实参泛型]
     * 泛型对应的类型均按照Object处理，但不等价
     * 于Object。经验：泛型要使用一路都用。要不用，一路都不要用。
     *
     *
     * 6. 如果泛型结构是一个接口或抽象类，则不可创建泛型类的对象。
     *
     * 7. jdk1.7，泛型的简化操作：ArrayList<Fruit> flist = new ArrayList<>();
     *
     * 8. 约定:泛型本身就是针对引用数据类型的,泛型的指定中[不能使用基本数据类型]，[可以使用包装类替换]。
     *
     * 9. 在类/接口上声明的泛型，在本类或本接口中即代表某种类型，可以作为[非静态
     * 属性]的类型、[非静态方法]的参数类型、[非静态方法的返回值类型]。但[在静态方法
     * 中[不能]使用类的泛型]。
     *
     * 10. 异常类不能是泛型的[报错不是按你指定的类型报的,报错是先出错后才抛出异常,没有你指定的份]
     *
     * //可以用来强转成此泛型类型,不能用来直接new 对象,new要硬编码确认类型才能造,不能加泛型
     * 11. 不能使用new E[]。但是可以：E[] elements = ])(E[new Object[capacity];
     * 参考：[ArrayList源码中声明：Object[] elementData，而非泛型参数类型数组]。
     *
     * 12.[父类有泛型，子类可以选择保留泛型也可以选择指定泛型类型]：
     *  [子类不保留父类的泛型]:[按需实现]
     *       没有给类型 --->擦除--->当Object处理,但不等价于Object
     *       具体类型 ---->父类不擦除,当前类的泛型照样擦除---->可反射获取父类泛型
     *  [子类保留父类的泛型]:[泛型子类]
     *       [全部保留]
     *       [部分保留]
     * 结论：子类必须是“富二代”，[子类除了指定或保留父类的泛型]，还[可以增加自己的泛型]
     *
     *
     *
     * 体会：使用泛型的主要优点是能够在编译时而不是在运行时检测错误。
     *
     * //如果不指定泛型，就会被擦除，换成Object处理，但不等价于Object
     *
     *
     * 约定;泛型的指定中不能使用基本数据类型，可以使用包装类替换
     */



    @Test
    public void GenTest2(){

        //如果不指定泛型，就会被擦除，换成Object处理，但不等价于Object
        Comparable c =new GenTest<String,String>();


        //就算多态了，获取的class对象还是原来的类的字节码对象
        System.out.println(c.getClass().toGenericString());//public class 泛型.泛型类.GenTest<K,V>


        c.show();
         GenTest g= (GenTest)c;
         g.setK("黄进");
        System.out.println(g.getK());

    }




    @Test
    public void showTest(){
        DAO<Integer> dao = new DAO();

        //泛型方法传基本数据类型的话就会转化成对应的包装类
        System.out.println(dao.show(123));


//        java.lang.Integer
//        123


        DAO.show2("黄进",new ArrayList<String>());

    }


//    如果B是A的一个子类型（子类或者子接口），而G是具有泛型声明的
//类或接口，G<B>并不是G<A>的子类型！
//比如：[String是Object的子类]，但是:[ List<String >并不是List<Object> ]
//的子类。  只是借用类型来初始化一些具体成员，但是两个泛型类之间没有关系，
// 用了父子类型，不代表两泛型类也是父子,


    /**通配符的使用
     *1.使用类型通配符：？
     * 比如：List<?> ，Map<?,?>
     * [List<?>]是[List<String>、List<Object>]等[各种泛型List的[父类]]。
     *
     * 2.[读取List<?>的对象list中的元素]时，[永远是安全的]，因为[不管list的真实类型]
     * 是什么，它[包含的都是Object]。
     *
     * 3.[写入list中的元素时，不行]。[因为我们不知道c的元素类型]，我们[不能向其中
     * 添加对象]。
     *  [唯一的例外是null]，[它是所有类型的成员]
     *
     *
     *  将 [任意元素加入到其中] [不是类型安全] 的：
     * Collection<?> c = new ArrayList<String>();
     * c.add(new Object()); // 编译时错误
     * [因为我们不知道c的元素类型]，我们[不能向其中添加对象]。
     * add方法有类型参数E作为集合的元素类型。
     * 我们传给add的任何参数都必须是一个未知类型的子类。
     * 因为我们不知道那是什么类型，所以我们无法传任何东西进去。
     *
     *  唯一的例外的是null，它是所有类型的成员。
     *
     *  另一方面，我们可以调用get()方法并使用其返回值。返回值是一个未知的
     * 类型，但是我们知道，它[总是一个Object]
     *
     *
     *
     *
     *
     * //注意点1：编译错误：不能用在泛型方法声明上，[返回值类型前面<>不能使用?]
     * //占位符使用是占位符，不像普通的T泛型，会被初始化，占位符?是不会被初始化的，一直？占着
     * //所以放返回值也不行
     * public static <?> void test(ArrayList<?> list){
     * }
     *
     * //注意点2：编译错误：[不能用在泛型类的声明上]
     * //类先加载，再运行，加载的时候如果是T泛型,没给类型参数时还可以借Object缓冲一下
     * //但是?不会默认为Object,如果没给是真不知道是什么类型。
     * class GenericTypeClass<?>{
     * }
     *
     * //注意点3：编译错误：[不能用在创建对象上]，[右边属于创建集合对象]
     * //创建对象必须是确定的，不能模糊
     * ArrayList<?> list2 = new ArrayList<?>();
     *
     *
     * 最主要的问题是: (里面会用Object处理，但是？不会被初始化，包括Object)
     *              ? 通配符是不会被初始化的，一直保持着通配符的状态
     *              不管真实的赋值的类型是什么，通配符里面包含的都是Object
     *
     *
     *
     *
     *
     *
     */

    @Test
    public void showList(){
        Collection<?> c = null;

//        c.add("awsdjhk");//编译报错，因为c引用是占位符，是不知道什么类型的，所以不能添加
//        c.add(null);//除了null以外,其它任何东西都传不进去

        //只能分开先做好子集合，然后把整个子集合的引用地址给其
        ArrayList<String> list = new ArrayList<>();
        list.add("黄进");
        c=list;//但是可以以把子集合引用给其，主要在子集合中确认泛型和添加具体值


        //可以用迭代器将其值取出
        Iterator<?> iterator = c.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        List<?> list1 = null;




    }



    /**通配符的使用：有限制的通配符
     *  <?>
     * 允许所有泛型的引用调用
     *
     *  通配符指定上限
     * 上限extends：使用时指定的类型必须是继承某个类，或者实现某个接口，即<=
     *
     *  通配符指定下限
     * 下限super：使用时指定的类型不能小于操作的类，即>=
     *
     *
     *  举例：
     *       <? extends Number> (无穷小 , Number]
     *      只允许泛型为Number及Number子类的引用调用
     *
     *       <? super Number> [Number , 无穷大) 只允许泛型为Number及Number父类的引用调用
     *
     *       <? extends Comparable>
     *      只允许泛型为实现Comparable接口的实现类的引用调用
     */

    @Test
    public void tongpei01(){
        ArrayList list = new ArrayList<String>();
        list.add("黄进");

        printColl(list);
        printCollection(list);
        printCollection2(list);



    }

    //泛型范围： ? <= Object
    public static void printColl(List<? extends Object> list){

        Iterator<?> iterator = list.iterator();

        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }



    }

    //泛型范围： ? >= ArrayListArrayList
    public static void printCollection(List<? super ArrayList> list){
        Iterator<? super ArrayList> iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }


    }

    //泛型范围： ? >= ArrayListArrayList
    public static void printCollection2(List<? super ArrayList<String>> list){
        Iterator<? super ArrayList<String>> iterator = list.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }


    }





}
