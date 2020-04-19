package com.zscat.mallplus.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cc
 * @Decription 砍价
 */
public class ReducePriceUtils {
    /**
     * 1.总金额不超过总共可砍的价格*100  单位是分
     * 2.每次砍价都能砍到金额，最低不能低于1分，最大金额不能超过（总共可砍的价）*100
     */
    private static final int MINMONEY = 1;
    private static final int MAXMONEY = 200 * 100;

    /**
     * 这里为了避免某一次砍价占用大量资金，我们需要设定非最后一次砍价的最大金额，
     * 我们把他设置为砍价金额平均值的N倍
     */
    private static final double TIMES = 3.1;

    /**
     * 砍价合法性校验
     *
     * @param money
     * @param count
     * @return
     */
    private static boolean isRight(int money, int count) {
        double avg = money / count;
        //小于最小金额
        if (avg < MINMONEY) {
            return false;
        } else if (avg > MAXMONEY) {
            return false;
        }
        return true;
    }

    /**
     * 随机分配一个金额
     *
     * @param money
     * @param minS：最小金额
     * @param maxS：最大金额
     * @param count
     * @return
     */
    private static int randomReducePrice(int money, int minS, int maxS, int count) {
        //若只有一个，直接返回
        if (count == 1) {
            return money;
        }
        //如果最大金额和最小金额相等，直接返回金额
        if (minS == maxS) {
            return minS;
        }
        int max = maxS > money ? money : maxS;
        //分配砍价正确情况，允许砍价的最大值
        int maxY = money - (count - 1) * minS;
        //分配砍价正确情况，允许砍价最小值
        int minY = money - (count - 1) * maxS;
        //随机产生砍价的最小值
        int min = minS > minY ? minS : minY;
        //随机产生砍价的最大值
        max = max > maxY ? maxY : max;
        //随机产生一个砍价
        return (int) Math.rint(Math.random() * (max - min) + min);
    }

    /**
     * 砍价
     *
     * @param money 可砍总价
     * @param count 个数
     * @return
     */
    public static List<Double> splitReducePrice(int money, int count) {
        //红包合法性分析
        if (!isRight(money, count)) {
            return new ArrayList<>();
        }
        //红包列表
        List<Double> list = new ArrayList<>();
        //每个红包的最大的金额为平均金额的TIMES倍
        int max = (int) (money * TIMES / count);
        max = max > MAXMONEY ? MAXMONEY : max;
        //分配红包
        int sum = 0;

        for (int i = 0; i < count; i++) {
            int one = randomReducePrice(money, MINMONEY, max, count - i);
            list.add(one / 100.0);
            money -= one;
            sum += one;
        }
        System.out.println("sum:" + sum);
        return list;
    }

    public static void main(String[] args) {
        List<Double> list = splitReducePrice(19799, 10);
        System.out.println(list);
    }
}
