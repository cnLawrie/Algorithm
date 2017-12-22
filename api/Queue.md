###FIFO队列
#####public class bag\<E> implements iterable\<E>
返回值类型 | 方法 | 描述
--- | --- | ---
-| Queue() | 创建一个空队列
void | enqueue(E e) | 添加一个元素
E | dequeue(E e) | 删除最早添加的元素
boolean | isempty() | 队列是否为空
int | size() | 队列中的元素数量
iterator | iterator() | 迭代