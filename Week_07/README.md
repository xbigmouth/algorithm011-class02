#### 字典树

字典树，即 Trie 树，又称单词查找树或键树，是一种树形结构。典型应用是用于统计和排序大量的字符串（但不仅限于字符串）， 所以经常被搜索引擎系统用于文本词频统计。 它的优点是：最大限度地减少无谓的字符串比较，查询效率比哈希表高。

基本性质：

1. 结点本身不存完整单词；

2. 从根结点到某一结点，路径上经过的字符连接起来，为该结点对应的字符串；

3. 每个结点的所有子结点路径代表的字符都不相同。

核心思想：Trie 树的核心思想是空间换时间。利用字符串的公共前缀来降低查询时间的开销以达到提高效率的目的。 

代码模板：

```
class Trie {
    private boolean isEnd;
    private Trie[] next;
    /** Initialize your data structure here. */
    public Trie() {
        isEnd = false;
        next = new Trie[26];
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        if (word == null || word.length() == 0) return;
        Trie curr = this;
        char[] words = word.toCharArray();
        for (int i = 0;i < words.length;i++) {
            int n = words[i] - 'a';
            if (curr.next[n] == null) curr.next[n] = new Trie();
            curr = curr.next[n];
        }
        curr.isEnd = true;
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        Trie node = searchPrefix(word);
        return node != null && node.isEnd;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        Trie node = searchPrefix(prefix);
        return node != null;
    }

    private Trie searchPrefix(String word) {
        Trie node = this;
        char[] words = word.toCharArray();
        for (int i = 0;i < words.length;i++) {
            node = node.next[words[i] - 'a'];
            if (node == null) return null;
        }
        return node;
    }
}
```

 单词搜索 II 时间复杂度分析

假设 M 是二维网格中的单元格数，L 是单词的最大长度。从一个单元格开始，最初最多可以探索4个方向。假设每个方向都是有效的（即最坏情况），在接下来的探索中，最多有3个相邻的单元（不包括来的单元）要探索。 因此，在回溯探索期间，我们最多遍历 4 * 3 ^(L-1)个单元格，因此时间复杂度为：O(4*3^(L-1))。 

#### 并查集 

基本操作：

1. makeSet(s)：建立一个新的并查集，其中包含 s 个单元素集合；

2. unionSet(x, y)：把元素 x 和元素 y 所在的集合合并，要求 x 和 y 所在的集合不相交，如果相交则不合并；

3. ﬁnd(x)：找到元素 x 所在的集合的代表，该操作也可以用于判断两个元素是否位于同一个集合，只要将它们各自的代表比较一下就可以了。 

代码模板：

```
class UnionFind {
    private int count = 0;
    private int[] parent;

    public UnionFind(int n) {
        count = n;
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public int find(int p) {
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;
        parent[rootP] = rootQ;
        count--;
    }
}
```

#### 高级搜索 

##### 剪枝 

在进行状态树搜索时，如果发现这个分支是已经处理过的，就把它暂存在缓存里面，整个分支就可以剪掉。 或者可以剪掉比较差的分支或次优的分支。 

##### 双向BFS

两端同时进行BFS，第一次重合的地方即为两者之间最短路径，左边步数加上右边步数即为总步数。

 代码模板：

```
public int twoEndedBFS(String beginWord, String endWord, List<String> wordList) {
    // 先将 wordList 放到哈希表里，便于判断某个单词是否在 wordList 里
    Set<String> wordSet = new HashSet<>(wordList);
    if (wordSet.size() == 0 || !wordSet.contains(endWord)) {
        return 0;
    }
    // beginSet 和 endSet 交替使用，等价于单向 BFS 里使用队列，每次扩散都要加到总的 visited 里
    Set<String> beginSet = new HashSet<String>(), endSet = new HashSet<String>();
    // 包含起点，因此初始化的时候步数为 1
    int len = 1;
    int strLen = beginWord.length();
    Set<String> visited = new HashSet<String>();

    beginSet.add(beginWord);
    endSet.add(endWord);

    while (!beginSet.isEmpty() && !endSet.isEmpty()) {
        // 优先选择小的哈希表进行扩散，考虑到的情况更少
        if (beginSet.size() > endSet.size()) {
            Set<String> set = beginSet;
            beginSet = endSet;
            endSet = set;
        }
        // nextLevelVisited 在扩散完成以后，会成为新的 beginSet
        Set<String> nextLevelVisited = new HashSet<String>();
        for (String word : beginSet) {
            char[] chars = word.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char old = chars[i];
                for (char c = 'a'; c <= 'z'; c++) {
                    if (old == c) {
                        continue;
                    }
                    chars[i] = c;
                    String nextWord = String.valueOf(chars);
                    if (wordSet.contains(nextWord)) {
                        if (endSet.contains(nextWord)) {
                            return len + 1;
                        }
                        if (!visited.contains(nextWord) && wordList.contains(nextWord)) {
                            nextLevelVisited.add(nextWord);
                            visited.add(nextWord);
                        }
                    }
                    // 恢复状态
                    chars[i] = old;
                }
            }
        }
        // 表示从 begin 这一侧向外扩散了一层
        beginSet = nextLevelVisited;
        len++;
    }
    return 0;
} 
```

##### 启发式搜索（Heuristic Search)（A*) 

估价函数： 

启发式函数： h(n)，它用来评价哪些结点最有希望的是一个我们要找的结点，h(n) 会返回一个非负实数,也可以认为是从结点n的目标结点路径的估计成本。

启发式函数是一种告知搜索方向的方法。它提供了一种明智的方法来猜测哪个邻居结点会导向一个目标。

代码模板：

```
public class AStar {
    public final static int BAR = 1; // 障碍值
    public final static int PATH = 2; // 路径
    public final static int DIRECT_VALUE = 10; // 横竖移动代价
    public final static int OBLIQUE_VALUE = 14; // 斜移动代价

    Queue<Node> openList = new PriorityQueue<Node>(); // 优先队列(升序)
    List<Node> closeList = new ArrayList<Node>();

    /**
     * 开始算法
     */
    public void start(MapInfo mapInfo) {
        if (mapInfo == null) return;
        // clean
        openList.clear();
        closeList.clear();
        // 开始搜索
        openList.add(mapInfo.start);
        moveNodes(mapInfo);
    }

    /**
     * 移动当前结点
     */
    private void moveNodes(MapInfo mapInfo) {
        while (!openList.isEmpty()) {
            Node current = openList.poll();
            closeList.add(current);
            addNeighborNodeInOpen(mapInfo, current);
            if (isCoordInClose(mapInfo.end.coord)) {
                drawPath(mapInfo.maps, mapInfo.end);
                break;
            }
        }
    }

    /**
     * 在二维数组中绘制路径
     */
    private void drawPath(int[][] maps, Node end) {
        if (end == null || maps == null) return;
        System.out.println("总代价：" + end.G);
        while (end != null) {
            Coord c = end.coord;
            maps[c.y][c.x] = PATH;
            end = end.parent;
        }
    }

    /**
     * 添加所有邻结点到open表
     */
    private void addNeighborNodeInOpen(MapInfo mapInfo, Node current) {
        int x = current.coord.x;
        int y = current.coord.y;
        // 左
        addNeighborNodeInOpen(mapInfo, current, x - 1, y, DIRECT_VALUE);
        // 上
        addNeighborNodeInOpen(mapInfo, current, x, y - 1, DIRECT_VALUE);
        // 右
        addNeighborNodeInOpen(mapInfo, current, x + 1, y, DIRECT_VALUE);
        // 下
        addNeighborNodeInOpen(mapInfo, current, x, y + 1, DIRECT_VALUE);
        // 左上
        addNeighborNodeInOpen(mapInfo, current, x - 1, y - 1, OBLIQUE_VALUE);
        // 右上
        addNeighborNodeInOpen(mapInfo, current, x + 1, y - 1, OBLIQUE_VALUE);
        // 右下
        addNeighborNodeInOpen(mapInfo, current, x + 1, y + 1, OBLIQUE_VALUE);
        // 左下
        addNeighborNodeInOpen(mapInfo, current, x - 1, y + 1, OBLIQUE_VALUE);
    }

    /**
     * 添加一个邻结点到open表
     */
    private void addNeighborNodeInOpen(MapInfo mapInfo, Node current, int x, int y, int value) {
        if (canAddNodeToOpen(mapInfo, x, y)) {
            Node end = mapInfo.end;
            Coord coord = new Coord(x, y);
            int G = current.G + value; // 计算邻结点的G值
            Node child = findNodeInOpen(coord);
            if (child == null) {
                int H = calcH(end.coord, coord); // 计算H值
                if (isEndNode(end.coord, coord)) {
                    child = end;
                    child.parent = current;
                    child.G = G;
                    child.H = H;
                } else {
                    child = new Node(coord, current, G, H);
                }
                openList.add(child);
            } else if (child.G > G) {
                child.G = G;
                child.parent = current;
                openList.add(child);
            }
        }
    }

    /**
     * 从Open列表中查找结点
     */
    private Node findNodeInOpen(Coord coord) {
        if (coord == null || openList.isEmpty()) return null;
        for (Node node : openList) {
            if (node.coord.equals(coord)) {
                return node;
            }
        }
        return null;
    }

    /**
     * 计算H的估值：“曼哈顿”法，坐标分别取差值相加
     */
    private int calcH(Coord end, Coord coord) {
        return Math.abs(end.x - coord.x)
                + Math.abs(end.y - coord.y);
    }

    /**
     * 判断结点是否是最终结点
     */
    private boolean isEndNode(Coord end, Coord coord) {
        return coord != null && end.equals(coord);
    }

    /**
     * 判断结点能否放入Open列表
     */
    private boolean canAddNodeToOpen(MapInfo mapInfo, int x, int y) {
        // 是否在地图中
        if (x < 0 || x >= mapInfo.width || y < 0 || y >= mapInfo.hight) return false;
        // 判断是否是不可通过的结点
        if (mapInfo.maps[y][x] == BAR) return false;
        // 判断结点是否存在close表
        if (isCoordInClose(x, y)) return false;
        return true;
    }

    /**
     * 判断坐标是否在close表中
     */
    private boolean isCoordInClose(Coord coord) {
        return coord != null && isCoordInClose(coord.x, coord.y);
    }

    /**
     * 判断坐标是否在close表中
     */
    private boolean isCoordInClose(int x, int y) {
        if (closeList.isEmpty()) return false;
        for (Node node : closeList) {
            if (node.coord.x == x && node.coord.y == y) {
                return true;
            }
        }
        return false;
    }
}
```

#### AVL树

- 平衡二叉搜索树；
- Balance Factor（平衡因子）： 是它的左子树的高度减去它的右子树的高度（有时相反）。 balance factor = {-1, 0, 1}；
- 通过旋转操作来进行平衡（四种）：左旋（右右子树）、右旋（左左子树）、左右旋（左右子树）、右左旋（右左子树）。

不足：结点需要存储额外信息、且调整次数频繁。 

#### 红黑树

红黑树是一种近似平衡的二叉搜索树（BinarySearch Tree），它能够确保任何一个结点的左右子树的高度差小于两倍。 具体来说，红黑树是满足如下条件的二叉搜索树：

- 每个结点要么是红色，要么是黑色；
- 根结点是黑色；
- 每个叶结点（NIL结点，空结点）是黑色的；
- 不能有相邻接的两个红色结点；
- 从任一结点到其每个叶子的所有路径都包含相同数目的黑色结点。

关键性质：从根到叶子的最长的可能路径不多于最短的可能路径的两倍长。 

AVL树与红黑树对比：

- AVL trees provide faster lookups than Red Black Trees because they are more strictly balanced.
- Red Black Trees provide faster insertion and removal operations than AVL trees as fewer rotations are done due to relatively relaxed balancing.
- AVL trees store balance factors or heights with each node, thus requires storage for an integer per node whereas Red Black Tree requires only 1 bit of information per node.
- Red Black Trees are used in most of the language libraries like map,multimap,multi set in C++ whereas AVL trees are used in databases where faster retrievals are required.