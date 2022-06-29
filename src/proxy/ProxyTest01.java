package proxy;

import 反射.pojo.User2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public class ProxyTest01 implements InvocationHandler {
    private Object target;

    public ProxyTest01() {

    }

    public ProxyTest01(Object target) {
        this.target = target;
    }

    //重写invoke
    @Override                                         //形参数组
    public Object invoke(Object proxy, Method method, Object[] params) throws Throwable {

        Object  retval = method.invoke(target,params);

        return retval+"使用代理类的静态方法返回的对执行象所有的方法，都是执行代理类中的invoke方法";
    }




}
