package 枚举与注解.注解.javadoc;


/**
 * @author Anvoi
 * @version 2.0
 * @see 枚举与注解.注解.AnnotationTest
 */
public class JavadocTest {

    /**
     *程序的主方法程序入口
     * @param args String[] 命令行参数
     */
    public static void main(String[] args) {

    }

    /**
     *
     * @param radius double 圆的半径
     * @return double 圆的面积
     */
    public static double getArea(double radius){
        show();
        return Math.PI*radius*radius;
    }




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




    @Deprecated    //表示过时的方法
    @SuppressWarnings("unused")
//    @Override  //只能用于重写父类的方法，父类无此方法就不能写此注解
    public static void show(){
        //抑制相应的警告需要输入此注解需要的value字符串,有规定，不能乱输
        @SuppressWarnings("unused")
        int i = 0;
    }


    //重写Object中的toString方法
    @Override
    public String toString() {
        return "JavadocTest{}";
    }
}
