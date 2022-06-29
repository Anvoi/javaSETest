package 枚举与注解.枚举.EnumClass;

/**自定义枚举类
 * 1. [私有化]类的[构造器]，保证[不能在类的[外部]创建 其 对象]
 * 2. 在【类的内部创建枚举类的实例】。声明为：public static final
 * 3. 【对象如果有实例变量】，【应该声明为private final】，【并在构造器中初始化】
 */
public class Season {


    //对于成员属性必须是私有的，不能对外暴露，不能被修改
//    final声明的属性必须赋值，不能不赋值,final没有默认的值,JVM不会给常量初始值，一定要手动给
    private final String SEASONNAME;//季节的名称
    private final String SEASONDESC;//季节的描述

// 构造器也必须是私有的，不能被外部的拿去造对象
    private Season(String SEASONNAME,String SEASONDESC){
        this.SEASONNAME = SEASONNAME;
        this.SEASONDESC = SEASONDESC;
    }


//    枚举不允许在外部造对象，只能在内部造好，让外部的来调用
//    因为是让外部来调用的对象成员属性，所以理应要设置成public ，鼓励外部来调用
    public static final Season SPRING = new Season("春天","春天多骚吊");
    public static final Season SUMMER = new Season("夏天","夏天多骚逼");
    public static final Season AUTUMN = new Season("秋天","秋天第一杯奶茶");
    public static final Season WINTER = new Season("冬天","冬天很冷");



    //可以放方法的，但是只能放get方法获取枚举属性，因为final不能就该
    // 因为构造器已经私有化了，所以外部肯定是通过当前类的静态实例属性来调用这些方法的
//    调用这些方法外部就可以获取属性,没get方法外部类就算拿到了当前的实例也调不出私有属性
    public String getSEASONNAME() {
        return SEASONNAME;
    }

    public String getSEASONDESC() {
        return SEASONDESC;
    }
}
