package invoke;

import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

/**
 * 1.加载配置文件读取key-value
 * 2.将value实例化对应的实体
 * 3.拼setTitle字符串
 * 4.根据setTitle字符串找到对应的setTitle的方法
 * 5.取出a对应的实例 及a%setitle对应的value 调用setTitle.invoke(a, value)
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
