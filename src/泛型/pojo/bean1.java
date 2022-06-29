package 泛型.pojo;

public class bean1 {

    private String name = "张玮伽";
    private String password = "蔡锦涛";

    public bean1() {
    }

    @Override
    public String toString() {
        return "pojo{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
