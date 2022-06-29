package 枚举与注解.枚举.EnumClass;

//enum关键字声明枚举类，enum类不能再继承其他类，因为默认继承了java.lang.Enum类
public enum enumClass {
    /**使用说明
     * 使用 enum 定义的枚举类默认继承了 java.lang.Enum类，因此不能再
     * 继承其他类
     * 枚举类的构造器只能使用 private 权限修饰符
     * 枚举类的所有实例必须在枚举类中显式列出(, 分隔 ; 结尾)。列出的
     * 实例系统会自动添加 public static final 修饰
     * 必须在枚举类的第一行声明枚举类对象
     *
     * JDK 1.5 中可以在 switch 表达式中使用Enum定义的枚举类的对象
     * 作为表达式, case 子句可以直接使用枚举值的名字, 无需添加枚举
     * 类作为限定。
     */


    //不同的枚举实例构造器之间需要用,隔开，最后一个使用分号
    /**系统自动添加public static final声明在枚举实例前*/

    /**前面的名字作为此枚举值的引用名
     * 后买你的括号就是此枚举类的构造器,构造器名省略,两个组合就造出枚举类对象，引用前面的名称
     * */
    SPRING("春天","春风又绿江南岸"),
    SUMMER("夏天","映日荷花别样红"),
    AUTUMN("秋天","秋水共长天一色"),
    WINTER("冬天","窗含西岭千秋雪");

    private final String seasonName;
    private final String seasonDesc;


    //私有构造器
    /**系统会自动设置构造器为私有构造器*/
     enumClass(String seasonName,String seasonDesc){
        this.seasonName = seasonName;
        this.seasonDesc = seasonDesc;

    }


    public String getSeasonName() {
        return seasonName;
    }

    public String getSeasonDesc() {
        return seasonDesc;
    }
}
