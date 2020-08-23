
//给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。 
//
// 示例 1: 
//
// 输入: "(()"
//输出: 2
//解释: 最长有效括号子串为 "()"
// 
//
// 示例 2: 
//
// 输入: ")()())"
//输出: 4
//解释: 最长有效括号子串为 "()()"
// 
// Related Topics 字符串 动态规划 
// 👍 921 👎 0


package com.cute.leetcode.editor.cn;

public class LongestValidParentheses {

    public static void main(String[] args) {

        Solution solution = new LongestValidParentheses().new Solution();

    }

    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
        public int longestValidParentheses(String s) {
            int max = 0;
            s = " " + s;
            int dp[] = new int[s.length()];
            for (int i = 2; i < s.length(); i++) {
                if (s.charAt(i) == ')') {
                    if (s.charAt(i - 1) == '(') {
                        dp[i] = dp[i - 2] + 2;
                    } else if (s.charAt(i - dp[i - 1] - 1) == '(') {
                        dp[i] = dp[i - 1] + 2 + dp[i - dp[i - 1] - 2];
                    }
                    max = Math.max(max, dp[i]);
                }
            }
            return max;
        }
        
}
//leetcode submit region end(Prohibit modification and deletion)


}