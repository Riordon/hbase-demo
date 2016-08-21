package com.glsx.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by xiaolong on 2016/8/19.
 * 本类主要用于实现常见排序算法及运行时间比较
 */
public class RunMain {
    private static final Logger LOG = LoggerFactory.getLogger(RunMain.class);

    public static void main(String[] args) {
        int numCount = 100000;
        Random random = new Random();
        ArrayList<Integer> list = new ArrayList<Integer>(numCount);
        for (int i = 0; i < numCount; i++) {
            list.add(i, random.nextInt(500000));
        }

//        printSort(list);

        long beginTime = System.currentTimeMillis();
        ArrayList<Integer> result = insertIntoSort(list);
        long costTime = System.currentTimeMillis() - beginTime;
        LOG.info("insertIntoSort：costTime=" + costTime + " ms");

//        printSort(result);
    }

    /**
     * 插入排序
     *      1.从第一元素开始，可以认为该元素已经被排序
     *      2.取出下一元素，在已排序的元素序列中从后往前扫描比较
     *      3.如果取出元素小于扫描元素，将扫描元素移动下一元素
     *      4.重复3，直到取出元素大于扫描元素，将取出元素插入扫描元素下一位置
     *      5.重复2~5
     * 时间复杂度：
     *      最好:输入数组按升序排序，Tn=O(n)
     *      最坏:输入数组按降序排序，Tn=O(n^2)
     *      平均:Tn=O(n^2)
     * @param list
     */
    private static ArrayList<Integer> insertIntoSort(ArrayList<Integer> list) {
        int size = list.size();
        for (int i = 1; i < size; i++) {
            int take = list.get(i);
            int scanIndex = i -1;
            while (scanIndex >= 0 && take < list.get(scanIndex)) {
                list.set(scanIndex+1,list.get(scanIndex));
                scanIndex --;
            }
            list.set(scanIndex+1,take);
        }

        return list;
    }

    /**
     * 打印输出
     * @param list
     */
    private static void printSort(ArrayList<Integer> list) {
        for (Integer i : list) {
            LOG.info(i.toString());
        }
    }
}
