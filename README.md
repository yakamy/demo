# demo

## package com.example.train
## a simple demo with tess4j

## package com.example.arithmetic
## a simple demo for genetic algorithm       referenc--遗传算法入门
本demo模拟迷宫中寻找出口。
模仿生物进化机制，遗传算法是用染色体Sgenome作为一个基本体，sgenome中存有二进制路径信息和适应性分数，适应性分数越大，表示离结果越接近（为1时，位于出口处），在群体m_vecGenomes中，通过轮盘赌法选出父母代，之后进行杂交、变异操作获得两个子代，生成新的一代群体。通过染色体的路径模拟在地图中行走，然后计算出相应的适应性分数。不断迭代，知道找出一个合适的路径。
