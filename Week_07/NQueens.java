
//n 皇后问题研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。 
//
// 
//
// 上图为 8 皇后问题的一种解法。 
//
// 给定一个整数 n，返回所有不同的 n 皇后问题的解决方案。 
//
// 每一种解法包含一个明确的 n 皇后问题的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。 
//
// 示例: 
//
// 输入: 4
//输出: [
// [".Q..",  // 解法 1
//  "...Q",
//  "Q...",
//  "..Q."],
//
// ["..Q.",  // 解法 2
//  "Q...",
//  "...Q",
//  ".Q.."]
//]
//解释: 4 皇后问题存在两个不同的解法。
// 
//
// 
//
// 提示： 
//
// 
// 皇后，是国际象棋中的棋子，意味着国王的妻子。皇后只做一件事，那就是“吃子”。当她遇见可以吃的棋子时，就迅速冲上去吃掉棋子。当然，她横、竖、斜都可走一到七步
//，可进可退。（引用自 百度百科 - 皇后 ） 
// 
// Related Topics 回溯算法 
// 👍 494 👎 0


package com.cute.leetcode.editor.cn;

public class NQueens {

    public static void main(String[] args) {

        Solution solution = new NQueens().new Solution();

    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        private List<List<String>> ans = new ArrayList<>();

        public List<List<String>> solveNQueens(int n) {
            char[][] curr = new char[n][n];
            boolean[] cols = new boolean[n];
            boolean[] pie = new boolean[2 * n - 1];
            boolean[] na = new boolean[2 * n - 1];
            for (int i = 0; i < n; i ++) {
                for (int j = 0; j < n; j ++) {
                    curr[i][j] = '.';
                }
            }
            dfs(n, curr, 0, cols, pie, na);
            return ans;
        }

        public void dfs(int n, char[][] curr, int row, boolean[] cols, boolean[] pie, boolean[] na) {
            if (row == n) {
                List<String> state = new ArrayList<>();
                for (int i = 0; i < n; i ++) {
                    state.add(String.valueOf(curr[i]));
                }
                ans.add(state);
                return;
            }

            for (int col = 0; col < n; col ++) {
                if (curr[row][col] == 'Q' || cols[col] || pie[row + col] || na[row - col + n - 1])
                    continue;
                curr[row][col] = 'Q';
                cols[col] = true;
                pie[row + col] = true;
                na[row - col + n - 1] = true;
                dfs(n, curr, row + 1, cols, pie, na);
                curr[row][col] = '.';
                cols[col] = false;
                pie[row + col] = false;
                na[row - col + n - 1] = false;
            }
        }
    }
//leetcode submit region end(Prohibit modification and deletion)


}