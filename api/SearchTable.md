###有序符号表(包括BST、RedBlackBST)
#####public class BST<Key extends Comparable, Value> 
返回值类型 | 方法 | 描述
--- | --- | ---
void | put(Key, Value val) | 将键值对存入表中(值为空则将Key从表中删除)
Value | get(Key key) | 获取键key对应的值(Key不存在则返回空)
void | delete(Key key) | 从表中删除键key及其对应的值
boolean | contains(Key key) | 键key是否存在于表中
boolean | isEmpty() | 表是否为空
int | size() | 表中的键值对数量
Key | min() | 最小的键
Key | max() | 最大的键
Key | floor(Key key) | 小于等于key的最大键
Key | ceiling(Key key) | 大于等于key的最小键
int | rank(Key key) | 小于key的键的数量
Key | select(int k) | 排名为k的键
void | deleteMin() | 从表中删除最小键及其对应的值
void | deleteMax() | 从表中删除最大键及其对应的值
Iterable\<Key\> | keys(Key lo, Key hi) | 按中序遍历的顺序将[lo, hi]之间的所有键按照顺序打印出来
Iterable\<Key\> | keys() | 按中序遍历的顺序将所有键按照顺序打印出来