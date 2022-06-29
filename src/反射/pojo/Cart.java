package 反射.pojo;

public class Cart {
    private String jik;


    public Cart() {
    }


    public Cart(String jik) {
        this.jik = jik;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "jik='" + jik + '\'' +
                '}';
    }
}
