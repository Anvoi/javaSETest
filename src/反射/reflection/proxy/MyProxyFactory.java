package 反射.reflection.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

//代理工厂
public class MyProxyFactory {
    //默认代理类全类名
    private String proxyPath = "反射.reflection.proxy.ProxyClass";

    public MyProxyFactory() {
    }

    public MyProxyFactory(String proxyPath) {
        this.proxyPath = proxyPath;
    }

    //造代理
    public Object createProxy(Object target) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //代理类的Class对象
        Class<?> clazzProxy = ClassLoader.getSystemClassLoader().loadClass(proxyPath);

        //拿到代理的有参构造器
        Constructor<?> declaredConstructor = clazzProxy.getDeclaredConstructor(Object.class);

        //有参构造代理类对象
        ProxyClass proxy = (ProxyClass)declaredConstructor.newInstance(target);


        //直接创造动态代理对象(返回对象给对应接口引用)
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),proxy);



    }



}
