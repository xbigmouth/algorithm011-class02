##  HashMap 小结

1. 在1.7中，HashMap 底层是基于 数组 + 链表的结构组成。在1.8中，如果链表的长度大于一定的值，链表会转成红黑树。

   DEFAULT_INITIAL_CAPACITY:16，数组的初始值。
   MAXIMUM_CAPACITY：最大值，基本上不会达到这个值。
   DEFAULT_LOAD_FACTOR:size / capacity(DEFAULT_INITIAL_CAPACITY)
   threshold: capacity * load factor，超过这个阈值会rehash。

   一个桶的树化阈值
   当桶中元素个数超过这个值时，需要使用红黑树节点替换链表节点
   这个值必须为 8，要不然频繁转换效率也不高
   static final int TREEIFY_THRESHOLD = 8;

   一个树的链表还原阈值
   当扩容时，桶中元素个数小于这个值，就会把树形的桶元素 还原（切分）为链表结构
   这个值应该比上面那个小，至少为 6，避免频繁转换
   static final int UNTREEIFY_THRESHOLD = 6;

   哈希表的最小树形化容量
   哈希表中的容量大于这个值时，表中的桶才能进行树形化
   否则桶内元素太多时会扩容，而不是树形化
   为了避免进行扩容、树形化选择的冲突，这个值不能小于 4 * TREEIFY_THRESHOLD
   static final int MIN_TREEIFY_CAPACITY = 64;

2. HashMap中DEFAULT_LOAD_FACTOR为什么是0.75
   如果该值设为0.5，会在HashMap容量达到一半时候就扩容。比如从16扩大32，从32到64，64到128，浪费的空间会越来越大。
   而如果该值设置为1，则每次空间使用完毕才会扩容，put时候操作耗时会增加。
   所以0.75是时间与空间的一个平衡。

3. put时候操作
   1.7插入元素到单链表中采用头插入法，1.8采用的是尾插入法

4. HashMap死循环
   HashMap在并发执行put操作时会引起死循环，是因为多线程会导致HashMap的Entry链表形成环形数据结构。