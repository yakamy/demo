package com.example.arithmetic;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10.
 */
public class CBobsMap {

    //保存地图用的存储器
    private static int map[][];
    private static int m_iMapWidth;   //地图的宽度
    private static int m_iMapHeight;  //地图的高度

    //起始点在数组中的下标
    //起始点在数组中的下标
    public static int m_iStartX;
    public static int m_iStartY;

    //终点的数组下标
    private static int m_iEndX;
    private static int m_iEndY;

    //你可以利用这一数组作为 Bob 存储器，如果需要的话
    int memory[][];

    CBobsMap() {
        m_iStartX = 0;
        m_iStartY = 2;
        m_iEndX = 14;
        m_iEndY = 7;
        map = new int[][]{{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1},
                {5, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1},
                {1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1},
                {1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 8},
                {1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
        ResetMemory();
    }

    //利用一个字符串来记录Bob行进的方向，其中每一个字符代表
    //Bob所走的一步；检查Bob离开出口还有多远；
    //返回一个与到达出口距离成正比的适应性分数
    double TestRoute(List<Integer> vecPath) {
        ResetMemory();
        for (Integer vec : vecPath) {
            if (vec == 0) {     //north
                m_iStartY--;
                int value = map[m_iStartY][m_iStartX];
                if (value == 1 || value == 5) {
                    m_iStartY++;
                } else if (value == 0) {
                    this.memory[m_iStartY][m_iStartX] = 2;
                } else if (value == 8) {

                    return 1;
                }
            } else if (vec == 1) {  //south
                m_iStartY++;
                int value = map[m_iStartY][m_iStartX];
                if (value == 1 || value == 5) {
                    m_iStartY--;
                } else if (value == 0) {
                    this.memory[m_iStartY][m_iStartX] = 2;
                } else if (value == 8) {
                    return 1;
                }
            } else if (vec == 2) {  //east
                m_iStartX--;
                if (m_iStartX <= 0) {
                    m_iStartX++;
                } else {
                    int value = map[m_iStartY][m_iStartX];
                    if (value == 1 || value == 5) {
                        m_iStartX++;
                    } else if (value == 0) {
                        this.memory[m_iStartY][m_iStartX] = 2;
                    } else if (value == 8) {
                        return 1;
                    }
                }

            } else if (vec == 3) {  //west
                m_iStartX++;
                int value = map[m_iStartY][m_iStartX];
                if (value == 1 || value == 5) {
                    m_iStartX--;
                } else if (value == 0) {
                    this.memory[m_iStartY][m_iStartX] = 2;
                } else if (value == 8) {
                    return 1;
                }
            }
        }
        int diffX = Math.abs(m_iEndX - m_iStartX);
        int diffY = Math.abs(m_iEndY - m_iStartY);
        return 1 / (double) (diffX + diffY + 1);
    }

    void ResetMemory() {
        m_iStartX = 0;
        m_iStartY = 2;

        memory = new int[][]{{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1},
                {5, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1},
                {1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1},
                {1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 8},
                {1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};
    }


}
