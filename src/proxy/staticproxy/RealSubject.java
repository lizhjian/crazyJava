package proxy.staticproxy;

/**
 * <pre>
 * desc ：TODO
 * author ：lizj
 * date ：2020-06-09 08:47
 * </pre>
 */
public class RealSubject implements ISubject {
    @Override
    public void doAction(String action) {
        System.out.println("I am RealSubject, do action "+ action);
    }
}
