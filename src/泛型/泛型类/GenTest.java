package 泛型.泛型类;


import 泛型.pojo.bean1;
import 泛型.泛型接口.Comparable;

import java.lang.reflect.Type;

/**
 *
 * 自定义泛型结构：泛型类、泛型接口
 * 1. 泛型类可能有多个参数，此时应将多个参数一起放在尖括号内。比如：
 * <E1,E2,E3>
 * 2. 泛型类的构造器如下：public GenericClass(){}。
 * 而下面是错误的：public GenericClass<E>(){}
 * 3. 实例化后，操作原来泛型位置的结构必须与指定的泛型类型一致。
 * 4. 泛型不同的引用不能相互赋值。
 * >尽管在编译时ArrayList<String>和ArrayList<Integer>是两种类型，但是，在运行时只有
 * 一个ArrayList被加载到JVM中。
 * 5. 泛型如果不指定，将被擦除，泛型对应的类型均按照Object处理，但不等价
 * 于Object。经验：泛型要使用一路都用。要不用，一路都不要用。
 * 6. 如果泛型结构是一个接口或抽象类，则不可创建泛型类的对象。
 * 7. jdk1.7，泛型的简化操作：ArrayList<Fruit> flist = new ArrayList<>();
 * 8. 泛型的指定中不能使用基本数据类型，可以使用包装类替换。
 * 9. 在类/接口上声明的泛型，在本类或本接口中即代表某种类型，可以作为非静态
 * 属性的类型、非静态方法的参数类型、非静态方法的返回值类型。但在静态方法
 * 中不能使用类的泛型。
 * 10. 异常类不能是泛型的
 * 11. 不能使用new E[]。但是可以：E[] elements = (E[])new Object[capacity];
 * 参考：ArrayList源码中声明：Object[] elementData，而非泛型参数类型数组。
 * 12.父类有泛型，子类可以选择保留泛型也可以选择指定泛型类型：
 *  子类不保留父类的泛型：按需实现
 *  没有类型 擦除
 *  具体类型
 *  子类保留父类的泛型：泛型子类
 *  全部保留
 *  部分保留
 * 结论：子类必须是“富二代”，子类除了指定或保留父类的泛型，还可以增加自
 * 己的泛型
 *
 *
 * 体会：使用泛型的主要优点是能够在编译时而不是在运行时检测错误。
 *
 * @param <K>
 * @param <V>
 */

//如果不指定泛型，就会被擦除，换成Object处理，但不等价于Object
public class GenTest<K,V> implements Comparable<bean1> {

    private K k;
    private V v;

//错误的泛型构造器写法：public GenericClass<E>(){}
    public GenTest() {
    }



    @Override
    public bean1 show() {
        /**当前类在运行时才被赋予泛型实参，运行时得到泛型实参后初始化好后马上会把当前类的泛型擦除，后面的语句和调用反射都拿不到当前类的泛型*/
//        因为Java采用的泛型擦除，所以需要我们在编译期时便将泛型信息固定好，所以需要只能拿到父类或接口的相关泛型信息。
//        不能获取当前类的泛型，因为运行时泛型会被擦除，运行的时候才给的泛型实参，反射是读取不到的
//        过程：类加载时候，会把编译时 就给实参 的父类泛型或者接口的的泛型 保留，因为编译就硬编码确认类型了，所以保留也没有问题
//             而且当前类你也不能把泛型在编译就给实参，没有意义，而且也给不了
//        所以不能获取当前类的泛型类型
        System.out.println(this.getClass().getGenericSuperclass());
        Type[] genericSuperclass = this.getClass().getGenericInterfaces();
        for (Type superclass : genericSuperclass) {
            System.out.println(superclass.getTypeName());
        }
        return new bean1();
    }


    public K getK() {
        return k;
    }

    public void setK(K k) {
        this.k = k;
    }

    public V getV() {
        return v;
    }

    public void setV(V v) {
        this.v = v;
    }
}
