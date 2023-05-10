package example.unionFind;

import org.junit.Assert;
import org.junit.Test;
import structure.linear.array.unionFind.genericUnionFind.GenericQuickUnion;
import structure.linear.array.unionFind.quickFind.QuickFind;
import structure.linear.array.unionFind.quickUnion.*;

public class UnionFindTest {
    @Test
    public void test01(){
        QuickFind quickFind = new QuickFind(12);
        quickFind.union(0, 1);
        quickFind.union(0, 3);
        quickFind.union(0, 4);
        quickFind.union(2, 3);
        quickFind.union(2, 5);

        quickFind.union(6, 7);

        quickFind.union(8, 10);
        quickFind.union(9, 10);
        quickFind.union(9, 11);

        Assert.assertFalse(quickFind.isSame(0, 6));
        Assert.assertTrue(quickFind.isSame(0, 5));
        Assert.assertTrue(quickFind.isSame(9, 10));
        Assert.assertTrue(quickFind.isSame(6, 7));
        Assert.assertFalse(quickFind.isSame(7, 11));

        quickFind.union(6, 0);
        Assert.assertTrue(quickFind.isSame(0, 6));
    }

    @Test
    public void test02(){
        QuickUnion quickUnion = new QuickUnion(12);
        quickUnion.union(0, 1);
        quickUnion.union(0, 3);
        quickUnion.union(0, 4);
        quickUnion.union(2, 3);
        quickUnion.union(2, 5);

        quickUnion.union(6, 7);

        quickUnion.union(8, 10);
        quickUnion.union(9, 10);
        quickUnion.union(9, 11);

        Assert.assertFalse(quickUnion.isSame(0, 6));
        Assert.assertTrue(quickUnion.isSame(0, 5));
        Assert.assertTrue(quickUnion.isSame(9, 10));
        Assert.assertTrue(quickUnion.isSame(6, 7));
        Assert.assertFalse(quickUnion.isSame(7, 11));

        quickUnion.union(6, 0);
        Assert.assertTrue(quickUnion.isSame(0, 6));
    }
    @Test
    public void test03(){
        QuickUnionSize quickUnion = new QuickUnionSize(12);
        quickUnion.union(0, 1);
        quickUnion.union(0, 3);
        quickUnion.union(0, 4);
        quickUnion.union(2, 3);
        quickUnion.union(2, 5);

        quickUnion.union(6, 7);

        quickUnion.union(8, 10);
        quickUnion.union(9, 10);
        quickUnion.union(9, 11);

        Assert.assertFalse(quickUnion.isSame(0, 6));
        Assert.assertTrue(quickUnion.isSame(0, 5));
        Assert.assertTrue(quickUnion.isSame(9, 10));
        Assert.assertTrue(quickUnion.isSame(6, 7));
        Assert.assertFalse(quickUnion.isSame(7, 11));

        quickUnion.union(6, 0);
        Assert.assertTrue(quickUnion.isSame(0, 6));
    }

    @Test
    public void test04(){
        QuickUnionRank quickUnion_r = new QuickUnionRank(12);
        quickUnion_r.union(0, 1);
        quickUnion_r.union(0, 3);
        quickUnion_r.union(0, 4);
        quickUnion_r.union(2, 3);
        quickUnion_r.union(2, 5);

        quickUnion_r.union(6, 7);

        quickUnion_r.union(8, 10);
        quickUnion_r.union(9, 10);
        quickUnion_r.union(9, 11);

        Assert.assertFalse(quickUnion_r.isSame(0, 6));
        Assert.assertTrue(quickUnion_r.isSame(0, 5));
        Assert.assertTrue(quickUnion_r.isSame(9, 10));
        Assert.assertTrue(quickUnion_r.isSame(6, 7));
        Assert.assertFalse(quickUnion_r.isSame(7, 11));

        quickUnion_r.union(6, 0);
        Assert.assertTrue(quickUnion_r.isSame(0, 6));
    }
    @Test
    public void test05(){
        QuickUnionRankPathCompression quickUnionRankPathCompression = new QuickUnionRankPathCompression(12);
        quickUnionRankPathCompression.union(0, 1);
        quickUnionRankPathCompression.union(0, 3);
        quickUnionRankPathCompression.union(0, 4);
        quickUnionRankPathCompression.union(2, 3);
        quickUnionRankPathCompression.union(2, 5);

        quickUnionRankPathCompression.union(6, 7);

        quickUnionRankPathCompression.union(8, 10);
        quickUnionRankPathCompression.union(9, 10);
        quickUnionRankPathCompression.union(9, 11);

        Assert.assertFalse(quickUnionRankPathCompression.isSame(0, 6));
        Assert.assertTrue(quickUnionRankPathCompression.isSame(0, 5));
        Assert.assertTrue(quickUnionRankPathCompression.isSame(9, 10));
        Assert.assertTrue(quickUnionRankPathCompression.isSame(6, 7));
        Assert.assertFalse(quickUnionRankPathCompression.isSame(7, 11));

        quickUnionRankPathCompression.union(6, 0);
        Assert.assertTrue(quickUnionRankPathCompression.isSame(0, 6));
    }

    @Test
    public void test06(){
        QuickUnionRankPathHalving quickUnion_r_PH = new QuickUnionRankPathHalving(12);
        quickUnion_r_PH.union(0, 1);
        quickUnion_r_PH.union(0, 3);
        quickUnion_r_PH.union(0, 4);
        quickUnion_r_PH.union(2, 3);
        quickUnion_r_PH.union(2, 5);

        quickUnion_r_PH.union(6, 7);

        quickUnion_r_PH.union(8, 10);
        quickUnion_r_PH.union(9, 10);
        quickUnion_r_PH.union(9, 11);

        Assert.assertFalse(quickUnion_r_PH.isSame(0, 6));
        Assert.assertTrue(quickUnion_r_PH.isSame(0, 5));
        Assert.assertTrue(quickUnion_r_PH.isSame(9, 10));
        Assert.assertTrue(quickUnion_r_PH.isSame(6, 7));
        Assert.assertFalse(quickUnion_r_PH.isSame(7, 11));

        quickUnion_r_PH.union(6, 0);
        Assert.assertTrue(quickUnion_r_PH.isSame(0, 6));
    }
    @Test
    public void test07(){
        GenericQuickUnion<Integer> genericQuickUnion = new GenericQuickUnion<>();
        Integer[] ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        genericQuickUnion.initQuickUnion(ints);

        genericQuickUnion.union(0, 1);
        genericQuickUnion.union(0, 3);
        genericQuickUnion.union(0, 4);
        genericQuickUnion.union(2, 3);
        genericQuickUnion.union(2, 5);

        genericQuickUnion.union(6, 7);

        genericQuickUnion.union(8, 10);
        genericQuickUnion.union(9, 10);
        genericQuickUnion.union(9, 11);

        Assert.assertFalse(genericQuickUnion.isSame(0, 6));
        Assert.assertTrue(genericQuickUnion.isSame(0, 5));
        Assert.assertTrue(genericQuickUnion.isSame(9, 10));
        Assert.assertTrue(genericQuickUnion.isSame(6, 7));
        Assert.assertFalse(genericQuickUnion.isSame(7, 11));

        genericQuickUnion.union(6, 0);
        Assert.assertTrue(genericQuickUnion.isSame(0, 6));
    }

}