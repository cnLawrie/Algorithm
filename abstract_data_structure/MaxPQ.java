package ADT;



import java.util.Comparator;
import java.util.NoSuchElementException;

//队能保证在对数时间内完成insert、delete最大元素
public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] pq;
    private int N = 0;
    private Comparator<Key> comparator;

    
    
    public MaxPQ(int maxN) {
        pq = (Key[]) new Comparable[maxN + 1];
    }
    
    public MaxPQ(){this(1);}
    
    public MaxPQ(int maxN, Comparator<Key> comparator){
        this.comparator = comparator;
        pq = (Key[]) new Comparable[maxN];
    }
    
    public MaxPQ(Comparator<Key> comparator){
        this(1, comparator);
    }
    
    public MaxPQ(Key[] keys){
        this.N = keys.length;
        pq = (Key[]) new Comparable[N + 1];
        for (int i = 0; i < N; i++)
        pq[i] = keys[i];
        //建堆 O(2N)
        for(int k = N/2; k >= 1; k--)
        sink(k);
    }
    
    public void insert(Key v) throws Exception {
        if(N == pq.length - 1)
            resize(2 * pq.length);

        pq[++N] = v;
        swim(N);
    }

    public Key delMax(){
        if(isEmpty())
            throw new NoSuchElementException("Priority queue underflow");

        Key max = pq[1];
        exch(1, N--);
        sink(1);
        pq[N + 1] = null;
        //如果使用率不足1/4, 缩小数组
        if((N > 0) && N == (pq.length-1) / 4)
            resize(pq.length / 2);
        return max;
    }

    public boolean isEmpty(){
        return N == 0;
    }
    
    public int size(){
        return N;
    }
    
    //由下至上的堆有序化（上浮）
    //如果某个节点比它的父节点更大就会打破堆的有序状态，
    //这时只要交换它与它的父节点(位置[k/2])，并一遍遍地用同样的方法恢复秩序，
    //直到遇到一个更大的父节点，堆的有序状态就恢复了
    private void swim(int k){
        while(k > 1 && less(k/2,k)){
            exch(k, k/2);
            k /= 2;
        }
    }
    
    //由下至上的对有序化（下沉）
    //对于路径上的每个节点，删除最大元素需要两次比较
    private void sink(int k){
        while(2 * k <= N){
            int j = 2 * k;
            if(j < N && less(j, j+1)) j++; //一次用来找出两个子节点的较大元素
            if(! less(k, j)) break;   //一次用来判断是否需要下沉
            exch(k, j);
            k = j;
        }
    }
    
    private boolean less(int i, int j){
        if(this.comparator != null){
            return this.comparator.compare(pq[i], pq[j]) < 0;
        } else {
            return pq[i].compareTo(pq[j])  == -1;
        }
    }
    
    private void exch(int i, int j){
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }
    
    private void resize(int capacity){
        assert capacity > N;
        Key[] temp = (Key[]) new Comparable[capacity];
        for (int i = 1; i <= N ; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }
   
}
