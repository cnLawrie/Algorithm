###大根堆
#####public class MaxPQ\<Key extends Comparable<Key>>
返回值类型 | 方法 | 描述
--- | --- | ---
-| MaxPQ() | 创建一个优先队列
-| MaxPQ(int max) | 创建一个初始容量为max的优先队列
-| MaxPQ(Key[] a) | 用a[]中的元素创建一个优先队列
void | insert(Key v) | 向优先队列插入一个元素
Key | max() | 返回最大元素
Key | delMax() | 删除并返回最大元素
boolean | isempty() | 背包是否为空
int | size() | 背包中的元素数量