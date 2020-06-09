package proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <pre>
 * desc ：我的动态代理
 * author ：lizj
 * date ：2020-06-09 21:57
 * </pre>
 */
public class MyDynamicProxy {
    public static void main(String[] args) {
        XiaoMing xiaoMing = new XiaoMing();
        InvocationHandler handler = new MyInvocationHandler(xiaoMing);
        Person p = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(), new Class[]{Person.class}, handler);
        //Person p = (Person)Proxy.newProxyInstance(Person.class.getClassLoader(), XiaoMing.class.getInterfaces(), handler);
        p.eat();
        p.say("abc");
    }
}

interface Person {
    void say(String word);

    void eat();
}


class MyInvocationHandler implements InvocationHandler {
    private Person p;

    public MyInvocationHandler(Person p) {
        this.p = p;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("方法名称:" + method);
        method.invoke(p, args);
        return null;
    }
}

class XiaoMing implements Person {
    @Override
    public void say(String word) {
        System.out.println("小明说话");
    }

    @Override
    public void eat() {
        System.out.println("小明吃饭😭");
    }
}