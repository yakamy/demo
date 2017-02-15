package com.example.arithmetic;

/**
 * Created by Administrator on 2017/2/15.
 */
public class MainTask {
    private static final double CROSSOVER_RATE = 0.7;       //杂交率
    private static final double MUTATION_RATE = 0.001;      //变异率
    private static final int POP_SIZE = 140;                //基因组的数目
    private static final int CHROMO_LENGTH = 70;            //染色体长度
    private static final int time = 100;                    //迭代次数

    public static void main(String[] args) {
        CgaBob task = new CgaBob(CROSSOVER_RATE, MUTATION_RATE, POP_SIZE, CHROMO_LENGTH, time);
        task.Run();
    }
}
