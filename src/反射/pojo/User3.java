package 反射.pojo;

import java.io.Externalizable;
import java.io.Serializable;

public class User3 implements Serializable {
// 会有隐藏的serialVersionUID校验序列和反序列的UID是否一致
//    此类变化了，UID就会改变，如果改变了，反序列化就会失败
//    比如在序列化写出对象后，把原来的可序列化类改变了，导致类的UID改变
//    在反序列化读入时就需要用这个类去接读入的对象，因为UID不同了，会抛异常反序列化失败
//    除非你把java中的类改回和目标一样的，UID就会一样,就可以接受
    /**如果反序列化之前，此对象的原类被修改，UID改变，JVM校验时发现UID不等就不让反序列
     * 值方面怎么样都可以，但是代码一分不准变，不准改原来代码，不准添加或者删除任何代码
     *除了值 和 注释，其他的分毫都要一样
     *
     */


        private static String name="黄进";
        private String pwd = "hjlahd"+name;


    @Override
    public String toString() {
        System.out.println("修改了方法内部");
        return "User3{" +
                "name1='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }


}
