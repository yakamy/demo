package com.example.arithmetic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */
public class SGenome {      //染色体

    public List<Integer> vecBits;   //  00 0 北  01 1 南  10 2 东 11 3 西
    public double dFitness;         //适应性分数

    /**
     * 创建一个长度为 num_bits 的二进制串
     *
     * @param num_bits
     */
    public SGenome(int num_bits) {
        vecBits = new ArrayList<>();
        for (int i = 0; i < num_bits; i++) {
            vecBits.add((int) Math.floor(Math.random() + 0.5f));
        }
        dFitness = 0;
    }

}
