
//给定一个二维网格 board 和一个字典中的单词列表 words，找出所有同时在二维网格和字典中出现的单词。 
//
// 单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母在一个单词中不允许被重复使用。
// 
//
// 示例: 
//
// 输入: 
//words = ["oath","pea","eat","rain"] and board =
//[
//  ['o','a','a','n'],
//  ['e','t','a','e'],
//  ['i','h','k','r'],
//  ['i','f','l','v']
//]
//
//输出: ["eat","oath"] 
//
// 说明: 
//你可以假设所有输入都由小写字母 a-z 组成。 
//
// 提示: 
//
// 
// 你需要优化回溯算法以通过更大数据量的测试。你能否早点停止回溯？ 
// 如果当前单词不存在于所有单词的前缀中，则可以立即停止回溯。什么样的数据结构可以有效地执行这样的操作？散列表是否可行？为什么？ 前缀树如何？如果你想学习如何
//实现一个基本的前缀树，请先查看这个问题： 实现Trie（前缀树）。 
// 
// Related Topics 字典树 回溯算法 
// 👍 209 👎 0


package com.cute.leetcode.editor.cn;

public class WordSearchIi {

    public static void main(String[] args) {

        Solution solution = new WordSearchIi().new Solution();

    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        private int rows, cols;
        private char[][] board;
        // Trie的根节点
        private Node root;
        // DFS遍历方向
        private int[][] direct = {{0,1,0,-1},{-1,0,1,0}};
        private LinkedList<String> ans;
        // 标记是否已经访问
        private boolean[][]marked;

        // Trie需要的节点
        class Node{
            HashMap<Character,Node> children;
            String word;
            public Node(String word){
                this.children = new HashMap<>();
                this.word = word;
            }
            public Node(){
                this.children = new HashMap<>();
                this.word = null;
            }
        }

        // Trie的操作
        private void delete(String str){
            delete(root,str,0);
        }
        private Node delete(Node current,String str, int d){
            if(current==null)return null;
            if(d==str.length()){
                if(current.children.isEmpty()) return null;
                current.word = null;
                return current;
            }
            char c = str.charAt(d);
            Node next = delete(current.children.get(c),str,d+1);
            current.children.put(c,next);
            if(next==null&&current.word==null&&current.children.size()<=1) return null;
            return current;
        }
        private void insert(String str){
            insert(root,str,0);
        }
        private Node insert(Node current,String str, int d){
            if(current==null)current = new Node();
            if(d==str.length()){
                current.word = str;
                return current;
            }
            char c = str.charAt(d);
            current.children.put(c, insert(current.children.get(c),str,d+1));
            return current;
        }

        public List<String> findWords(char[][]board,String[]words){
            this.rows = board.length;
            this.cols = board[0].length;
            this.board = board;
            this.root = new Node();
            this.ans = new LinkedList<>();
            this.marked = new boolean[rows][cols];
            int len = words.length;

            for(int i=0;i<len;i++){
                insert(words[i]);
            }
            for(int i=0;i<rows;i++){
                for(int j=0;j<cols;j++){
                    char c = board[i][j];
                    if(root.children.get(c)!=null)dfs(i,j,root.children.get(c));
                }
            }
            return this.ans;
        }
        private void dfs(int row, int col, Node current){
            if(current==null)return;
            if(current.word!=null){
                this.ans.add(current.word);
                delete(current.word);
            }
            if(current.children.isEmpty())return;
            marked[row][col] = true;
            for(int i=0;i<4;i++){
                int newRow = row+direct[0][i];
                int newCol = col+direct[1][i];
                if(valid(newRow,newCol)){
                    char c =board[newRow][newCol];
                    if(current.children.get(c)!=null)dfs(newRow,newCol,current.children.get(c));
                }
            }
            marked[row][col]=false;
        }
        private boolean valid(int row, int col){
            return row<rows&&row>=0&&col<cols&&col>=0&&!marked[row][col];
        }

    }
}
//leetcode submit region end(Prohibit modification and deletion)


}