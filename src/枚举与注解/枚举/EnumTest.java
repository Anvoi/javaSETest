package 枚举与注解.枚举;


import org.junit.Test;
import 枚举与注解.枚举.EnumClass.Season;
import 枚举与注解.枚举.EnumClass.enumClass;
import 枚举与注解.枚举.EnumClass.pojo;

/**枚举类*/
public class EnumTest {
    /**主要内容:
     *  如何自定义枚举类
     *  如何使用关键字enum定义枚举类
     *  Enum类的主要方法
     *  实现接口的枚举类
     */


    /** 类的对象只有有限个，确定的。
     * 举例如下：
     *  星期：Monday(星期一)、......、Sunday(星期天)  性别：Man(男)、Woman(女)  季节：Spring(春节)......Winter(冬天)
     *  支付方式：Cash（现金）、WeChatPay（微信）、Alipay(支付宝)、BankCard(银
     * 行卡)、CreditCard(信用卡)
     *  就职状态：Busy、Free、Vocation、Dimission
     *  订单状态：Nonpayment（未付款）、Paid（已付款）、Delivered（已发货）、
     * Return（退货）、Checked（已确认）Fulfilled（已配货）、
     *  线程状态：创建、就绪、运行、阻塞、死亡
     *  当需要定义一组常量时，强烈建议使用枚举类
     */


    /** 枚举类的要求
     * 枚举类的实现
     *      JDK1.5之前需要自定义枚举类
     *      JDK 1.5 新增的 enum 关键字用于定义枚举类
     *
     * 若枚举只有一个对象, 则可以作为一种单例模式的实现方
     * 式。
     *
     *  枚举类的属性
     *      枚举类对象的属性不应允许被改动, 所以应该使用 private final 修饰
     *      枚举类的使用 private final 修饰的属性应该在构造器中为其赋值
     *      若枚举类显式的定义了带参数的构造器, 则在列出枚举值时也必须对应的
     *      传入参数
     *
     */
    @Test
    public void enumTest01(){
        //手撸原生枚举类
        System.out.println(Season.SUMMER.getSEASONNAME());//夏天
        System.out.println(Season.SUMMER.getSEASONDESC());//夏天多骚逼



        /**enum关键字创建枚举类*/
        System.out.println(enumClass.SUMMER.getSeasonName());//夏天
        System.out.println(enumClass.SUMMER.getSeasonDesc());//映日荷花别样红


//        JDK 1.5 中可以在 switch 表达式中使用Enum定义的枚举类的对象
//                * 作为表达式, case 子句可以直接使用枚举值的名字, 无需添加枚举
//                * 类作为限定。

        enumClass  enu= enumClass.SPRING; //枚举必须用其自己的类接受枚举常量对象
        switch (enu){//常量表达式
            case SPRING: //switch的case支持枚举类对象,因为也是常量声明的
                System.out.println(enumClass.SPRING.getSeasonDesc());
                break;
            case SUMMER:
                System.out.println(enumClass.SUMMER.getSeasonDesc());
                break;
            case AUTUMN:
                System.out.println(enumClass.AUTUMN.getSeasonDesc());
                break;
            case WINTER:
                System.out.println(enumClass.WINTER.getSeasonDesc());
                break;
            default:  //前面都不执行就执行此处
                break;
        }







    }



  /**enum枚举类的父类Enum的常用方法，enum枚举类可以使用,因为继承了
   *
   *  values()方法：返回枚举类型的对象数组。该方法可以很方便地遍历所有的
   * 枚举值。
   *
   *  valueOf(String str)：可以把一个字符串转为对应的枚举类对象。要求字符
   * 串必须是枚举类对象的“名字”。如不是，会有运行时异常：
   * IllegalArgumentException。
   *
   *  toString()：返回当前枚举类对象常量的名称
   *
   * 和普通 Java 类一样，枚举类可以实现一个或多个接口
   * 若每个枚举值在调用实现的接口方法呈现相同的行为方式，则只
   * 要统一实现该方法即可。
   * 若需要每个枚举值在调用实现的接口方法呈现出不同的行为方式,
   * 则可以让每个枚举值分别来实现该方法 (匿名对象重写方法)
   *
   * */
    @Test
    public void test02(){
//        values
        //返回枚举类的枚举对象数组,【遍历枚举值】
        enumClass[] values = enumClass.values();
        for (enumClass value : values) {
            System.out.println(value.getSeasonName());
        }

//        valueOf  (注意，字符串一定要和枚举值的引用名一样，不然找不到此枚举，获取失败,抛异常)
//        大小写也要一样
        //根据String字符串获取对应的枚举值（枚举对象）
        enumClass summer = enumClass.valueOf("SUMMER");
        System.out.println(summer.getSeasonName());


//        toString  返回当前【枚举类中的枚举对象常量】的 String 名称
//        将枚举对象的引用名称字符串返回
        String s = enumClass.SUMMER.toString();
        System.out.println(s);


    }

}
