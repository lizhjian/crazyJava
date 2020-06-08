import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <pre>
 * desc ：TODO
 * author ：lizj
 * date ：2020-06-05 13:08
 * </pre>
 */
public class ObjectPoolFactory {

    private Map<String, Object> objectPoool = new HashMap<>();

    private Object createObject(String clazzName) throws Exception {
        Class<?> clazz = Class.forName(clazzName);
        return clazz.getConstructor().newInstance();
    }

    public void initPool(String fileName) throws  Exception{
        try {
            FileInputStream fis = new FileInputStream(fileName);

            Properties props = new Properties();

            props.load(fis);
            for (String name : props.stringPropertyNames()){
                objectPoool.put(name, createObject(props.getProperty(name)));
            }
        }catch (Exception ex){
            System.out.println("读取异常");
        }
    }


    public Object getObject(String name){
        return objectPoool.get(name);
    }

    public static void main(String[] args) throws  Exception{
        ObjectPoolFactory pf = new ObjectPoolFactory();
        pf.initPool("obj.txt");

        System.out.println(pf.getObject("a"));
        System.out.println(pf.getObject("b"));
    }

}
