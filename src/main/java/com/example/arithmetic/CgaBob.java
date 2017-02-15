package com.example.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/2/10.
 */
public class CgaBob {

    private static final double CROSSOVER_RATE = 0.7;       //杂交率
    private static final double MUTATION_RATE = 0.001;      //变异率
    private static final int POP_SIZE = 140;                //基因组的数目
    private static final int CHROMO_LENGTH = 70;            //染色体长度


    private List<SGenome> m_vecGenomes;  //基因组群体

    //群体大小
    private int m_iPopSize;
    private double m_dCrossoverRate;
    private double m_dMutationRate;

    //每个染色体含有多少bits
    private int m_iChromoLength;

    //每个基因有多少bits
    private int m_iGeneLength;
    private int m_iFittestGenome;
    private double m_dBestFitnessScore;
    private double m_dTotalFitnessScore;
    private int m_iGeneration;

    //为map类创建一个实例
    private CBobsMap m_BobsMap;

    //另一个CbobsMap对象用来保存每一代的最佳路径的一个记录，这是被访问笑个的一个数组，他仅仅是为了显示目的而使用的
    private CBobsMap m_BobsBrain;

    //让你知道运行是否仍在进行中
    private boolean m_bBusy;

    //变异
    void Mutate(List<Integer> vecBits) {
        for (int curBit = 0; curBit < vecBits.size(); curBit++) {
            //是否需要翻转此bit
            if (Math.random() < m_dMutationRate) {
                vecBits.set(curBit, 1 - vecBits.get(curBit));
            }
        }

    }

    //杂交操作
    void Crossover(List<Integer> mum, List<Integer> dad, List<Integer> baby1, List<Integer> baby2) {
        if ((Math.random() > m_dCrossoverRate) || Arrays.equals(mum.toArray(), dad.toArray())) {
            baby1 = mum;
            baby2 = dad;
            return;
        }
        //沿染色体长度随机选择一个点来杂交
        Random random = new Random();
        int cp = random.nextInt(m_iChromoLength);

        for (int i = 0; i < cp; i++) {
            baby1.set(i, mum.get(i));
            baby2.set(i, dad.get(i));
        }

        for (int i = cp; i < mum.size(); ++i) {
            baby1.set(i, dad.get(i));
            baby2.set(i, mum.get(i));
        }

    }

    //轮盘赌选择   从群体中选择一个基因组，选中的几率正比于基因组的适应性分数
    private SGenome RouletteWheelSelection() {
        double fSlice = Math.random() * m_dTotalFitnessScore;
        double cfTotal = 0;
        int selectedGenome = 0;
        for (int i = 0; i < m_iPopSize; ++i) {
            cfTotal += m_vecGenomes.get(i).dFitness;
            if (cfTotal > fSlice) {
                selectedGenome = i;
                break;
            }
        }
        return m_vecGenomes.get(selectedGenome);
    }

    //用新的适应性分数来更新基因组原油的适应性分数， 并计算群体的最高适应性分数和适应性分数最高的那个成员
    private void UpdateFitnessScore() {
        m_dTotalFitnessScore = 0;
        for (int i = 0; i < m_vecGenomes.size(); i++) {
            SGenome sGenome = m_vecGenomes.get(i);
            List<Integer> vecPath = Decode(sGenome.vecBits);
            double score = m_BobsMap.TestRoute(vecPath);
            sGenome.dFitness = score;
            if (score > m_dBestFitnessScore) {
                m_dBestFitnessScore = score;
            }
            if (score == 1) {
                this.m_BobsBrain.memory = this.m_BobsMap.memory;
            }
            m_dTotalFitnessScore += score;
        }

    }

    //把一个位向量译成为一个方向的（整数）向量
    List<Integer> Decode(List<Integer> bits) {
        List<Integer> result = new ArrayList<>();
        int size = bits.size();
        for (int i = 0; i < size - 1; i += 2) {
            List<Integer> bin = new ArrayList<>();
            bin.add(bits.get(i));
            bin.add(bits.get(i + 1));
            result.add(BinToInt(bin));
        }
        return result;
    }

    //把一个位向量变换为十进制数 用于译码
    int BinToInt(List<Integer> v) {
        if (v == null || v.size() != 2) {
            return 0;
        }
        if (v.get(0) == 0) {
            if (v.get(1) == 0) {
                return 0;
            }
            if (v.get(1) == 1) {
                return 1;
            }
        }
        if (v.get(0) == 1) {
            if (v.get(1) == 0) {
                return 2;
            }
            if (v.get(1) == 1) {
                return 3;
            }
        }
        return 0;
    }

    //创建一个随机的二进制为串的初始群体
    void CreateStartPopulation() {
        for (int i = 0; i < m_iPopSize; i++) {
            SGenome sGenome = new SGenome(m_iChromoLength);
            m_vecGenomes.add(sGenome);
        }

    }

    public CgaBob(double cross_rat, double mut_rat, int pop_size, int num_bits, int gene_len) {
        this.m_dCrossoverRate = cross_rat;      //杂交率
        this.m_dMutationRate = mut_rat;         //变异率
        this.m_iPopSize = pop_size;             //群体大小
        this.m_iChromoLength = num_bits;        //每个染色体的位数
        this.m_dTotalFitnessScore = 0.0d;
        this.m_dBestFitnessScore = 0.0d;
        this.m_iGeneration = 0;
        this.m_iGeneLength = gene_len;
        this.m_bBusy = false;
        m_vecGenomes = new ArrayList<>();
        CreateStartPopulation();
    }

    void Run() {
        m_BobsMap = new CBobsMap();
        m_BobsBrain = new CBobsMap();
        System.out.println("程序启动------------------------------");
        int time = 0;
        while (time < m_iGeneLength) {
            time++;
            Epoch();
            System.out.println("当前代数：" + m_iGeneration + ",最高适应性分数：" + m_dBestFitnessScore + ",总分数：" + m_dTotalFitnessScore);
            if (m_dBestFitnessScore == 1) {
                int[][] memory = m_BobsBrain.memory;
                for (int i = 0; i < memory.length; i++) {
                    for (int j = 0; j < memory[i].length; j++) {
                        System.out.print(memory[i][j] + "\t");
                    }
                    System.out.println("");
                }
                break;
            }
        }
    }

    void Epoch() {
        UpdateFitnessScore();

        int NewBabies = 0;
        List<SGenome> vecBabyGenomes = new ArrayList<>();

        //用轮盘赌法选择2个上辈
        while (NewBabies < m_iPopSize) {
            SGenome mum = RouletteWheelSelection();
            SGenome dad = RouletteWheelSelection();

            SGenome baby1 = new SGenome(m_iChromoLength);
            SGenome baby2 = new SGenome(m_iChromoLength);
            //杂交
            Crossover(mum.vecBits, dad.vecBits, baby1.vecBits, baby2.vecBits);
            //变异
            Mutate(baby1.vecBits);
            Mutate(baby2.vecBits);
            //将2个新生婴儿加入新群体
            vecBabyGenomes.add(baby1);
            vecBabyGenomes.add(baby2);
            NewBabies += 2;
        }
        //把所有婴儿复制到初始群体
        m_vecGenomes = vecBabyGenomes;
        //代数加1
        ++m_iGeneration;

    }

    void Stop() {
        m_bBusy = false;
    }
}
