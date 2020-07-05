//给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。 
//
// 示例: 
//
// 输入: n = 4, k = 2
//输出:
//[
//  [2,4],
//  [3,4],
//  [2,3],
//  [1,2],
//  [1,3],
//  [1,4],
//] 
// Related Topics 回溯算法


package leetcode.editor.cn;
//Java：组合
public class Combinations{
    public static void main(String[] args) {
        Solution solution = new Combinations().new Solution();
        // TO TEST
    }
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new LinkedList<>();
        for (int i = 0; i < 1 << n; i++) {
            if (Integer.bitCount(i)!=k) continue;
            int bit = 0;
            LinkedList<Integer> combination = new LinkedList<>();
            for (int j = 1; j <= n; j++) {
                if (((i >> bit++) & 1) == 1) {
                    combination.add(j);
                }
            }
            result.add(combination);
        }
        return result;

    }
}
//leetcode submit region end(Prohibit modification and deletion)

}