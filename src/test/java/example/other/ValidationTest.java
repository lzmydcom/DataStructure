package example.other;

import org.junit.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Random;

public class ValidationTest {
    @Test
    public void test01() throws NoSuchFieldException, IllegalAccessException {
        //??private????????????????????????
        B b = new B("B??A");
        Class<A> aClass = A.class;
        Field a_name = aClass.getDeclaredField("A_name");
        a_name.setAccessible(true);
        Object o = a_name.get(b);
        System.out.println(o);
        Field age = aClass.getDeclaredField("age");
        Object o1 = age.get(b);
        System.out.println(o1);
    }

    @Test
    public void test02(){
        HashMap<String, Object> hashMap = new HashMap<>();
        Object put = hashMap.put("??", "hello");
        Object jack = hashMap.put("??", 333888);
        Object aNull = hashMap.put("null", null);
        System.out.println(hashMap.size());
        System.out.println(hashMap.get("??"));
        System.out.println(hashMap.get(null));
    }

   /** @Test
    public void test03(){
        Student student = new Student();
        Student stu = new Student();
        student.setAge(29);
        System.out.println(student.hashCode());
        System.out.println((Object)stu.hashCode());
        double a = 3920.98;
        System.out.println(Double.hashCode(a));
        System.out.println(student.hashCode() == student.hashCode());

    }*/

    @Test
    public void test04(){
        int i = 43;
        System.out.println((i << 5) - i);
        System.out.println(31 * i);
        System.out.println((i << 4) - i);
        System.out.println(15 * i);
        String str = "你好";
        System.out.println(str);
        
    }

    @Test
    public void test05(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("空",null);

        System.out.println(map.get("空") == map.get("根本不存在"));
    }

    @Test
    public void test06(){
        for (int i = 0; i < 16; i++){
            if ((i / 16.0) >= 0.75) {
                System.out.println(i);
            }
        }
    }

    @Test
    public void test07(){
        System.out.println((float) 3/4);
    }
    @Test
    public void test08(){
        System.out.println(1 << 5);
        System.out.println(5 << 1);
    }

    @Test
    public void test09(){
        int a = 9;
        int b = 20;
        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println(a);
        System.out.println(b);
    }

    @Test
    public void test10(){
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            System.out.println(rand.nextInt(101));
        }
    }
    @Test
    public void test11(){
        for (int i = 0; i < 1000; i++) {
            int v = (int) (Math.random() * 101);
            System.out.println(v);
        }

    }
}