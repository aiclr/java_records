package cn.aiclr.jvm.dsa.graph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * <pre>图 --- 多对多的关系
 * 有向图：顶点之间有方向，A到B A->B  不能B->A
 * 无向图：顶点之间链接是没有方向的 A到B,B也可到A
 * 带权图：也叫网，比如地图，城市之间的距离可以作为权，这样的地图就是带权图
 *
 * 图的表示方式两种：
 * 1.邻接矩阵（二维数组）
 *      1.1）邻接矩阵需要为每个顶点都分配 n 个边的空间，其实很多边可能都不存在，会造成空间的一定损失
 * 2.邻接表（HashTable），数组加链表、数组加树
 *      2.1）邻接表的实现只关心存在的边，不关心不存在的边，没有空间浪费，
 *      2.2）eg
 *         数据 数组  链表
 *          A   0    1-2
 *          B   1    0-2-3-4
 *          C   2    0-1
 *          D   3    1
 *          E   4    1
 *
 * 地铁网
 * 图的顶点 vertex 可以有 0 个或多个相邻顶点
 * 顶点之间的链接是边 edge
 * 路径
 * eg 无向图
 *       B
 *   /   \   \
 * C    /  \   E
 *  \  /    \
 *   A       D
 * A到E的路径
 * A-B-E
 * A-C-B-E
 */
public class Graph {

    private static final Logger log = LoggerFactory.getLogger(Graph.class);

    //顶点，此处使用字符串
    private final List<String> vertexList;
    //使用矩阵保存顶点之间关系
    private final int[][] edges;
    //边总数
    private int numOfEdges;
    //遍历，记录某个点是否被访问
    private boolean[] isVisited;

    /**
     * 构造器初始化图
     *
     * @param n 顶点个数
     */
    public Graph(int n) {
        //n*n的矩阵，二维数组
        edges = new int[n][n];
        //保存顶点数据
        vertexList = new ArrayList<>(n);
        //边的数量初始化
        numOfEdges = 0;
        isVisited = new boolean[n];
    }

    /**
     * 保存顶点
     *
     * @param vertex 顶点数据
     */
    public void insertVertex(String vertex) {
        vertexList.add(vertex);
    }

    /**
     * 记录顶点之间关系
     *
     * @param vertex1 表示数据 1 在 vertexList 内的下标 第几个数据
     * @param vertex2 表示数据 2 在 vertexList 内的下标 第几个数据
     * @param weight  表示 vertex1 到 vertex2 的路径的权重
     *                比如 vertex1 = 广州，vertex2 = 北京，weight可以用来保存广州到北京的距离
     */
    public void insertEdges(int vertex1, int vertex2, int weight) {
        edges[vertex1][vertex2] = weight;
        edges[vertex2][vertex1] = weight;
        numOfEdges++;
    }

    /**
     * @return 得到边数
     */
    public int getNumOfEdges() {
        return numOfEdges;
    }

    /**
     * @return 得到顶点数
     */
    public int getVertexNum() {
        return vertexList.size();
    }

    /**
     * 根据下标获取数据
     *
     * @param index 下标，第几个数据
     * @return 数据
     */
    public String getVertexByIndex(int index) {
        return vertexList.get(index);
    }

    /**
     * <pre>返回 vertex1 和 vertex2 的权值
     *
     *       B
     *   /   \   \
     * C    /  \   E
     *  \  /    \
     *   A       D
     * 矩阵表示边如下
     *    A  B  C  D  E
     * A [0, 1, 1, 0, 0]
     * B [1, 0, 1, 1, 1]
     * C [1, 1, 0, 0, 0]
     * D [0, 1, 0, 0, 0]
     * E [0, 1, 0, 0, 0]
     *
     * @param vertex1 顶点 1 的下标
     * @param vertex2 顶点 2 的下标
     * @return vertex1 与 vertex2 这条边的权值 如果为 0 则表示 vertex1,vertex2 之间没有边
     */
    public int getWeight(int vertex1, int vertex2) {
        return edges[vertex1][vertex2];
        //观察矩阵是个对称矩阵，所以与顺序无关
//        return edges[vertex2][vertex1];
    }


    public void display() {
        for (int[] link : edges) {
            log.info("{}", link);
        }
    }

// 深度遍历 DFS Depth first search
// 广度遍历 BFS Broad first search

    /**
     * <pre>获取第一个邻接节点的下标
     * 遍历当前行，找到下一个邻接节点
     * @param index 矩阵某一行
     * @return 第一个邻接节点的下标, 否则返回-1
     */
    public int getFistNeighbor(int index) {
        for (int i = 0; i < vertexList.size(); i++) {
            //如果当前节点的下一个节点存在（值大于0）
            if (edges[index][i] > 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * <pre>根据前一个邻接节点下标，获取下一个邻接节点
     * 遍历 vertex1 行，从 vertex2+1 列开始
     * @param vertex1 前一个邻接节点下标
     * @param vertex2 前一个邻接节点下标
     * @return
     */
    public int getNextNeighbor(int vertex1, int vertex2) {
        for (int i = vertex2 + 1; i < vertexList.size(); i++) {
            if (edges[vertex1][i] > 0) {
                return i;
            }
        }
        return -1;
    }


    /**
     * <pre>深度遍历 DFS Depth first search
     * 深度优先:
     * 递归
     * 从 A 开始遍历，先输出 A，isVisited[] 记录 A 已遍历过
     * 然后找下一个相邻节点得 B，然后输出 B，isVisited[] 记录 B 已遍历过
     * 然后找 B 的下一个相邻节点得 A
     *      但是 A 已经遍历过（isVisited[]有记录），
     *      再找 B 的下下个相邻节点，即继续遍历 B 行，得 D
     * 然后找 D 的下一个相邻节点，先得到已经输出过的 B 节点，再找的到 H
     * 找 H 的相邻节点，D 已输出，所以得到 E
     * 找 E 的相邻节点 B,H 都输出过，然后回溯当前栈到 D，再回溯到 B,B 会找到 E 已输出, B 的相邻节点递归结束
     * 继续回溯到 A,找 A 的另一个相邻节点得 C
     *
     * 至于重载 bsf 方法加了个循环，是因为图不一定是连续的，如没有A节点，图变成独立的两部分
     *
     * A->B->D->H->E->C->F->G
     *       A
     *    /    \
     *   B     C
     *  /\    /\
     * D  E  F  G
     *  \/
     *  H
     * @param isVisited 记录是否被访问过
     * @param i 开始节点
     */
    public void dfs(boolean[] isVisited, int i) {
        System.err.print(getVertexByIndex(i) + "->");
        isVisited[i] = true;
        int w = getFistNeighbor(i);
        while (w != -1) {
            if (!isVisited[w]) {
                dfs(isVisited, w);
            }
            w = getNextNeighbor(i, w);
        }
    }

    /**
     * 重载
     */
    public void dfs() {
        //回溯，每个节点都深度遍历一遍 可能整个图不是连续的
        for (int i = 0; i < getVertexNum(); i++) {
            if (!isVisited[i]) {
                dfs(isVisited, i);
            }
        }
        System.err.println();
        isVisited=new boolean[isVisited.length];
    }


    /**
     * <pre>广度遍历 BFS Broad first search
     * 广度优先 BroadFirstSearch
     * 一层一层遍历
     *
     *       A
     *    /    \
     *   B     C
     *  /\    /\
     * D  E  F  G
     *  \/
     *  H
     *  如从 A 开始，输出 A 并记录已访问
     *  将 A 加入一个队列（先进先出）
     *  队列非空继续执行，否则算法结束
     *  出队列得 A,找 A 的第一个邻接点，B 记录已访问并将 B 入队
     *          找 A 的第二个邻接点，C 记录已访问并将 C 入队
     *  出队得 B,D 入队，E 入队
     *  出队得 C,F 入队，G 入队
     *  出队得 D,H 入队
     *  出队得 E,出队得 F,出队得 G,出队得 H
     *  队列空算法结束
     */
    private void bsf(boolean[] isVisited, int i) {
        int u;//队列的头节点对应的下标
        int w;//邻接点下标
        Queue<Integer> queue = new ArrayBlockingQueue<>(getVertexNum() - 1);
        System.err.printf("%s=>", getVertexByIndex(i));
        isVisited[i] = true;
        queue.add(i);
        while (!queue.isEmpty()) {
            u = queue.poll();
            w = getFistNeighbor(u);
            while (w != -1) {
                if (!isVisited[w]) {
                    System.err.printf("%s=>", getVertexByIndex(w));
                    isVisited[w] = true;
                    queue.add(w);
                }
                //同层的下一个节点
                w = getNextNeighbor(u, w);//广度

            }
        }
    }

    /**
     * 重载用for包一下
     */
    public void bsf() {
        for (int i = 0; i < vertexList.size(); i++) {
            if (!isVisited[i]) {
                bsf(isVisited, i);
            }
        }
        System.err.println();
        isVisited=new boolean[isVisited.length];
    }
}
