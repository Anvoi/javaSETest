package 泛型.personson;

import java.util.Iterator;
import java.util.List;

public class DAO<T> {


    /**
     * 自定义泛型结构：泛型方法
     *  方法，也可以被泛型化，不管此时定义在其中的类是不是泛型类。在泛型
     * 方法中可以定义泛型参数，此时，参数的类型就是传入数据的类型。
     *
     *  泛型方法的格式：
     * [访问权限] <泛型> 返回类型 方法名([泛型标识 参数名称]) 抛出的异常
     *
     *  泛型方法声明泛型时也可以指定上限(在12.5中讲)
     */

//    泛型方法所在的类可以不是泛型类
//    需要有这个<T>泛型声明才可以使用泛型,除非类上下来的T泛型
//    <T> 声明此方法为泛型方法,根据传如的数据类型来确认泛型
    public  T show(T t){

        System.out.println(t.getClass().getName());


        return t;
    }



    public static <T> T show2(T t, List<T> tl){

        System.out.println(t.getClass().getName());
        tl.add(t);

        Iterator<T> iterator = tl.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        return t;
    }



}
