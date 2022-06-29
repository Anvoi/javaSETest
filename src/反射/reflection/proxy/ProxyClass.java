package 反射.reflection.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//代理类
public class ProxyClass implements InvocationHandler {
    private Object target;
    private TongYon tongYon ;



    public ProxyClass() {
    }


    public ProxyClass(Object target) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        this.target = target;
        tongYon = (TongYon) ClassLoader.getSystemClassLoader().loadClass("反射.reflection.proxy.TongYon").newInstance();



    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {



        //通用代码1
        tongYon.init();

        //动态代码   //这里放object完全没问题，因为invoke本身接受参数时就是接受Object的
        method.invoke(target,args);

        //通用代码2
        tongYon.del();



        return null;
    }





}
