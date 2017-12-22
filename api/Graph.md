###无向图
#####public class Graph
返回值类型 | 方法 | 描述
- | Graph(int v) | 创建一个含有v个顶点但不含有边的图
- | Graph(In in) | 从标准输入流in读入一幅图
int | V() | 顶点数
int | E() | 边数
void | addEdge(int v, int w) | 向图中添加一条边v-w
Iterable<Integer> |　adj(int v) | 返回与v相邻的所有边
String |　toString() | 对象的字符串表示

####深度优先搜索
#####public class DepthFirstSearch
返回值类型 | 方法 | 描述
- | DepthFirstSearch(Graph G, int s) | 以s为起点进行深度搜索 
boolean | marked(int w) | w节点是否被访问了
int | count() | s连通分量的节点数量

####深度优先搜索的寻找路径
#####public class DepthFirstPaths
返回值类型 | 方法 | 描述
- | DepthFirstPaths(Graph G, int s) | 在G中找出所有起点为s的路径
boolean | hasPathTo(int v) | 是否存在从s到v的路径
Iterable<Integer> | pathTo(int v) | s到v的路径，如果不存在则返回null

####广度优先搜索的寻找路径
#####public class BreadthFirstPaths
返回值类型 | 方法 | 描述
- | BreadthFirstPaths(Graph G, int s) | 在G中找出所有起点为s的路径
boolean | hasPathTo(int v) | 是否存在从s到v的路径
Iterable<Integer> | pathTo(int v) | s到v的路径，如果不存在则返回null

####符号图
顶点名为字符串，用指定的分隔符来隔开顶点名
#####public class SymbolGraph
返回值类型 | 方法 | 描述
- | SymbolGraph(String filename, String split) | 根据filename指定的文件构造图，使用split来分隔顶点名
boolean | contains(String key) | key是一个顶点吗
int | index(String key) | key的索引
String | name(int v) | 索引v的顶点名
Graph | G() | 返回隐藏的Graph对象

