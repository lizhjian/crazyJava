import java.lang.reflect.Field;

/**
 * <pre>
 * desc ：TODO
 * author ：lizj
 * date ：2020-06-09 07:16
 * </pre>
 */
class Person{
    private String name;

    private int age;

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
public class FieldTest {
    public static void main(String[] args) throws Exception {
        //创建Person对象
        Person p = new Person();
        Class<Person> personClass = Person.class;
        //获取名为name的成员变量
        Field field1 = personClass.getDeclaredField("name");
        field1.setAccessible(true);
        field1.set(p, "my name is Tom");

        Field field2 = personClass.getDeclaredField("age");
        // 设置为可访问权限
        field2.setAccessible(true);
        field2.setInt(p, 20);
        System.out.println(p);

        System.out.println(field1.get(p));
        System.out.println(field2.get(p));
    }
}

