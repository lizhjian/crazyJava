import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * <pre>
 * desc ：TODO
 * author ：lizj
 * date ：2020-06-05 13:08
 * </pre>
 */
public class ExtendedObjectPoolFactory {

    private Map<String, Object> objectPoool = new HashMap<>();
    private Properties config = new Properties();

    private Object createObject(String clazzName) throws Exception {
        Class<?> clazz = Class.forName(clazzName);
        return clazz.getConstructor().newInstance();
    }

    public void initPool(String fileName) throws  Exception{
        try {
            FileInputStream fis = new FileInputStream(fileName);

            //Properties props = new Properties();

            config.load(fis);

            for (String name : config.stringPropertyNames()){
                if(!name.contains("%")){
                    objectPoool.put(name, createObject(config.getProperty(name)));
                }
            }
        }catch (Exception ex){
            System.out.println("读取异常");
        }
    }
    public void  initProperty() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException{
        for(String name : config.stringPropertyNames()){
            if(name.contains("%")){
               //将配置文件中的key按照%分割
                String[] objAndProp = name.split("%");
                Object target = getObject(objAndProp[0]);
                // 获取setXxx方法字符串
                String mtdName = "set"+objAndProp[1].substring(0, 1).toUpperCase()
                        + objAndProp[1].substring(1);

                Class<?> targetClass = target.getClass();
                Method mtd = targetClass.getMethod(mtdName, String.class);
                mtd.invoke(target, config.getProperty(name));
            }
        }
    }


    public Object getObject(String name){
        return objectPoool.get(name);
    }

    public static void main(String[] args) throws  Exception{
        ExtendedObjectPoolFactory pf = new ExtendedObjectPoolFactory();
        pf.initPool("objExt.txt");
        pf.initProperty();

        System.out.println(pf.getObject("a"));
    }

}
