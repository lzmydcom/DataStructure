package example.trie;

import org.junit.Assert;
import org.junit.Test;
import structure.tree.trie.prefixTree.PrefixTree;

public class PrefixTreeTest {
    @Test
    public void trieTest(){
        PrefixTree<Object> prefixTree = new PrefixTree<>();
        //通讯录更适合使用HashMap数据结构，此处使用PrefixTree是为了测试
        prefixTree.add("jack","18170312506");
        prefixTree.add("juli","16789764567");
        prefixTree.add("jimi","17898768976");
        prefixTree.add("rose","12178997665");
        prefixTree.add("ais","16789765678");
        prefixTree.add("tom","14789976546");
        prefixTree.add("tomi","15867886655");
        prefixTree.add("ailis","14678889655");
        prefixTree.add("toni","18778756556");

        Assert.assertTrue(prefixTree.contains("toni"));
        Assert.assertFalse(prefixTree.contains("lisi"));
        Assert.assertEquals(prefixTree.get("tom"),"14789976546");
        Assert.assertTrue(prefixTree.starsWith("jac"));
        Assert.assertTrue(prefixTree.starsWith("ro"));
        Assert.assertTrue(prefixTree.starsWith("tom"));
        Assert.assertTrue(prefixTree.starsWith("aili"));
        Assert.assertFalse(prefixTree.starsWith("tonio"));

        prefixTree.remove("toni");
        Assert.assertFalse(prefixTree.contains("toni"));
        Assert.assertFalse(prefixTree.contains("to"));
        Assert.assertTrue(prefixTree.starsWith("to"));
        prefixTree.remove("tom");
        Assert.assertTrue(prefixTree.starsWith("tom"));
        Assert.assertFalse(prefixTree.contains("tom"));
        System.out.println(prefixTree.get("tomi"));
    }
}
