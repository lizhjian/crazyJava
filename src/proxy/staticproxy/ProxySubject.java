package proxy.staticproxy;

/**
 * <pre>
 * desc ：TODO
 * author ：lizj
 * date ：2020-06-09 08:48
 * </pre>
 */
public class ProxySubject implements ISubject {
    ISubject mRealSubject;

    public ProxySubject(ISubject mRealSubject) {
        super();
        this.mRealSubject = mRealSubject;
    }


    @Override
    public void doAction(String action) {
        preRequest();
        mRealSubject.doAction(action);
        postRequest();
    }
    protected void postRequest(){
        System.out.println("postRequest");

    }

    protected void preRequest() {
        System.out.println("preRequest");

    }
    public static void main(String[] args) {
        // 被代理对象
        RealSubject realSubject = new RealSubject();
        // 代理类只有被代理对象
        ProxySubject proxySubject = new ProxySubject(realSubject);
        proxySubject.doAction("play");
    }

}
