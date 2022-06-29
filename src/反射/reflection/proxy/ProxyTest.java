package 反射.reflection.proxy;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

//代理测试
public class ProxyTest {



    @Test
    public void test01() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //目标类的Class对象
        Class<?> clazz = ClassLoader.getSystemClassLoader().loadClass("反射.reflection.proxy.BeanP");

        //Class对象创建出来的实例
        BeanP beanP = (BeanP)clazz.newInstance();

        //造工厂对象
        MyProxyFactory myProxyFactory = new MyProxyFactory("反射.reflection.proxy.ProxyClass");


//        //代理类的Class对象
//        Class<?> clazzProxy = ClassLoader.getSystemClassLoader().loadClass("反射.reflection.proxy.ProxyClass");
//
//        //拿到有参构造器
//        Constructor<?> declaredConstructor = clazzProxy.getDeclaredConstructor(Object.class);
//
//        //有参构造代理类对象
//        ProxyClass proxy = (ProxyClass)declaredConstructor.newInstance(beanP);
//
//
//        //直接创造动态代理对象(返回对象给对应接口引用)
//        BeanI beanI = (BeanI)Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),proxy);

        //执行工厂类对象中的create方法来造代理类对象返回对象
        Object proxy = myProxyFactory.createProxy(beanP);
        BeanI beanI = (BeanI)proxy;

        //无论执行什么，都是在执行代理类的invoke方法
        beanI.createHandGrenades();



    }




}
