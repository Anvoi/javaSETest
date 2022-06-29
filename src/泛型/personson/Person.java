package 泛型.personson;

public class Person<T> {
    private T info;


    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }


    public Person(T info) {
        this.info = info;
    }


    public Person() {
    }

    // static的方法中不能声明泛型
//public static void show(T t) {
//
//}
// 不能在try-catch中使用泛型定义
//public void test() {
//try {
//
//} catch (MyException<T> ex) {
//
//}
//}


}
