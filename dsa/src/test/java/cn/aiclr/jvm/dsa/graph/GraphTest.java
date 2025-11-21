package cn.aiclr.jvm.dsa.graph;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("图")
class GraphTest {

    /**
     * <pre>
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
     */
    @DisplayName("邻接矩阵（二维数组）表示图")
    @Test
    void graphTest() {
        //vertexes 数组索引
        String[] vertexes = {"A", "B", "C", "D", "E"};
        Graph graph = new Graph(vertexes.length);
        //保存顶点
        for (String vertex : vertexes) {
            graph.insertVertex(vertex);
        }

        //保存顶点关系 A-B
        graph.insertEdges(0, 1, 1);
        //保存顶点关系 A-C
        graph.insertEdges(0, 2, 1);
        //保存顶点关系 B-C
        graph.insertEdges(1, 2, 1);
        //保存顶点关系 B-D
        graph.insertEdges(1, 3, 1);
        //保存顶点关系 B-E
        graph.insertEdges(1, 4, 1);

        //5条边
        Assertions.assertEquals(5,graph.getNumOfEdges());
        //5个顶点
        Assertions.assertEquals(vertexes.length,graph.getVertexNum());
        //根据下标获取数据
        Assertions.assertEquals(vertexes[3],graph.getVertexByIndex(3));
        //vertexes[3] 到 vertexes[1] 的权值
        Assertions.assertEquals(1, graph.getWeight(3, 1));

        graph.display();
    }

    /**
     * <pre>
     * 图的遍历
     * eg
     *       A
     *    /    \
     *   B     C
     *  /\    /\
     * D  E  F  G
     *  \/
     *  H
     */
    @DisplayName("广度遍历与深度遍历")
    @Test
    void foreachGraph(){
        String[] vertexes = {"A", "B", "C", "D", "E", "F", "G", "H"};

        Graph graph = new Graph(vertexes.length);

        for (String vertex : vertexes) {
            graph.insertVertex(vertex);
        }

        //A-B
        graph.insertEdges(0, 1, 1);
        //A-C
        graph.insertEdges(0, 2, 1);
        //B-D
        graph.insertEdges(1, 3, 1);
        //B-E
        graph.insertEdges(1, 4, 1);
        //C-F
        graph.insertEdges(2, 5, 1);
        //C-G
        graph.insertEdges(2, 6, 1);
        //D-H
        graph.insertEdges(3, 7, 1);
        //D-H
        graph.insertEdges(4, 7, 1);

        //5条边
        Assertions.assertEquals(8,graph.getNumOfEdges());
        //5个顶点
        Assertions.assertEquals(vertexes.length,graph.getVertexNum());
        //根据下标获取数据
        Assertions.assertEquals(vertexes[3],graph.getVertexByIndex(3));
        //vertexes[3] 到 vertexes[1] 的权值
        Assertions.assertEquals(1, graph.getWeight(3, 1));

        graph.display();
        //深度遍历 DFS Depth first search
        graph.dfs();
        //广度遍历 BFS Broad first search
        graph.bsf();
    }

}
