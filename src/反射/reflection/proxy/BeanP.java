package 反射.reflection.proxy;

public class BeanP implements BeanI{
    private String name;


    public BeanP() {
    }

    public BeanP(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "BeanP{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void show(){
        System.out.println(
                "组装手枪零件"
        );
    }



    @Override
    public void createHandGrenades() {
        System.out.println("组装手雷");
    }


}
