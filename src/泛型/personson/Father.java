package 泛型.personson;

import org.junit.Test;


public class Father<T1,T2> {
    @Test
    public  void test01(){

        Son33<String, String, String, String> son33 = new Son33<>();

        son33.setT1("黄进");

        //黄进 null ,,没有给泛型实参就会擦除,后面会当作Object类型处理,不等价于Object
        System.out.println(son33.getT1()+" "+getT2());

    }


    T1 t1;
    T2 t2;


    public T1 getT1() {
        return t1;
    }

    public void setT1(T1 t1) {
        this.t1 = t1;
    }

    public T2 getT2() {
        return t2;
    }

    public void setT2(T2 t2) {
        this.t2 = t2;
    }

}

/**子类不保留父类的泛型([不给类型]和[具体类型])
 *
 */
//子类全不保留父类的泛型
class Son1 extends Father{

}

//子类全不保留父类的泛型
class Son11<A,B> extends Father{//等价于class Son11 extends Father<Object,Object>

}

//子类不保留父类的泛型,换成具体的类型
class Son2 extends Father<String,Integer>{

}

//具体类型(不保留父类泛型)
class Son22<A,B> extends Father<Integer,String>{

}



/**子类[保留父类的泛型],[全部保留]和[部分保留]*/
//全部保留
class Son3<T1,T2> extends  Father<T1,T2>{

}

//全部保留并且增强
class Son33<T1,T2,A,B> extends  Father<T1,T2>{

}

//部分保留,并增强
class Son4<T2,A,B> extends Father<Integer,T2>{

}




class ytest {


}
