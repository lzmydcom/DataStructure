package example.collection.map;

import org.junit.Assert;
import org.junit.Test;
import structure.treeStructure.collection.map.HashMap;
import structure.treeStructure.collection.operation.Visitor;

import java.io.FileReader;
import java.io.IOException;

public class HashMapTest {

    @Test
    public void HashMapTest01() {
        HashMap<Integer, String> map = new HashMap<>();
        Integer[] integers = {60, 1, 55, 96, 5, 78, 62, 90, 20,
                72, 67, 64, 28, 95, 27, 3, 19, 33, 68, 41, 94,
                79, 38, 7, 24, 80, 9, 13, 4, 40, 11, 58, 99, 16,
                93, 71, 46, 65, 74, 76, 44, 47, 15};

        for (Integer integer:integers){
            if (integer == 68){
                map.put(integer, "hello");
            } else if (integer == 44) {
                map.put(integer,"i love you");
            } else map.put(integer,"Same element");
        }
        Assert.assertEquals(map.get(44),"i love you");
        Assert.assertTrue(map.containsKey(47));
        Assert.assertTrue(map.containsValue("i love you"));
        Assert.assertFalse(map.containsKey(100));

        for (Integer integer:integers){
            //这样写用于调试
            if (integer == 68){
                Assert.assertEquals(map.remove(integer),"hello");
            }else {
                map.remove(integer);
            }
        }



    }

    @Test
    public void HashMapTraversal(){
        HashMap<Integer, String> map = new HashMap<>();
        Integer[] integers = {60, 1, 55, 96, 5, 78, 62, 90, 20,
                72, 67, 64, 28, 95, 27, 3, 19, 33, 68, 41, 94,
                79, 38, 7, 24, 80, 9, 13, 4, 40, 11, 58, 99, 16,
                93, 71, 46, 65, 74, 76, 44, 47, 15};

        for (Integer integer:integers){
            if (integer == 68){
                map.put(integer, "hello");
            } else if (integer == 44) {
                map.put(integer,"i love you");
            } else map.put(integer,"Same element");
        }

        map.printTree();


        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

        map.traversal(new Visitor<Integer, String>() {
            @Override
            public boolean visit(Integer key, String value) {
                System.out.println("key:" + key + "value:" + value);

                //当遍历到58时停止遍历...
                return key == 58;
            }
        });

    }

    @Test
    public void HashMapLastTest() throws IOException {
        HashMap<Object, Object> map = new HashMap<>();
        FileReader reader = null;
        try {
            reader = new FileReader("D:\\leetcode\\数据结构");

            char[] chars = new char[100];
            int readCount = 0;
            while ((readCount = reader.read(chars)) != -1){

            }
        } finally {
            if (reader != null){
                reader.close();
            }
        }


    }

}
