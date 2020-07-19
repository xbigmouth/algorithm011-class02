//给定两个单词（beginWord 和 endWord）和一个字典，找到从 beginWord 到 endWord 的最短转换序列的长度。转换需遵循如下规则：
// 
//
// 
// 每次转换只能改变一个字母。 
// 转换过程中的中间单词必须是字典中的单词。 
// 
//
// 说明: 
//
// 
// 如果不存在这样的转换序列，返回 0。 
// 所有单词具有相同的长度。 
// 所有单词只由小写字母组成。 
// 字典中不存在重复的单词。 
// 你可以假设 beginWord 和 endWord 是非空的，且二者不相同。 
// 
//
// 示例 1: 
//
// 输入:
//beginWord = "hit",
//endWord = "cog",
//wordList = ["hot","dot","dog","lot","log","cog"]
//
//输出: 5
//
//解释: 一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog",
//     返回它的长度 5。
// 
//
// 示例 2: 
//
// 输入:
//beginWord = "hit"
//endWord = "cog"
//wordList = ["hot","dot","dog","lot","log"]
//
//输出: 0
//
//解释: endWord "cog" 不在字典中，所以无法进行转换。 
// Related Topics 广度优先搜索 


package leetcode.editor.cn;
//Java：单词接龙
public class WordLadder{
    public static void main(String[] args) {
        Solution solution = new WordLadder().new Solution();
        // TO TEST
    }
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        public int ladderLength(String beginWord, String endWord, List<String> wordList) {
            // 先将 wordList 放到哈希表里，便于判断某个单词是否在 wordList 里
            Set<String> wordSet = new HashSet<>(wordList);
            if (wordSet.size() == 0 || !wordSet.contains(endWord)) {
                return 0;
            }

            // 标准写法，总的 visited 数组
            Set<String> visited = new HashSet<>();

            // 分别用左边和右边扩散的哈希表代替单向 BFS 里的队列
            Set<String> beginVisited = new HashSet<>();
            beginVisited.add(beginWord);

            Set<String> endVisited = new HashSet<>();
            endVisited.add(endWord);

            int len = beginWord.length();
            int step = 1;
            while (!beginVisited.isEmpty() && !endVisited.isEmpty()) {
                // 打开以方便调试
                // System.out.println("beginVisited => " + beginVisited);
                // System.out.println("  endVisited => " + endVisited + "\n");

                // 优先选择小的哈希表进行扩散，考虑到的情况更少
                if (beginVisited.size() > endVisited.size()) {
                    Set<String> temp = beginVisited;
                    beginVisited = endVisited;
                    endVisited = temp;
                }

                // 逻辑到这里，保证 beginVisited 是相对较小的集合
                // nextLevelVisited 在扩散完成以后，会成为新的 beginVisited
                Set<String> nextLevelVisited = new HashSet<>();
                for (String word : beginVisited) {
                    char[] charArray = word.toCharArray();
                    for (int i = 0; i < len; i++) {
                        char originChar = charArray[i];
                        for (char c = 'a'; c <= 'z'; c++) {
                            if (originChar == c) {
                                continue;
                            }
                            charArray[i] = c;
                            String nextWord = String.valueOf(charArray);
                            if (wordSet.contains(nextWord)) {
                                if (endVisited.contains(nextWord)) {
                                    return step + 1;
                                }
                                if (!visited.contains(nextWord)) {
                                    nextLevelVisited.add(nextWord);
                                    visited.add(nextWord);
                                }
                            }
                        }
                        // 恢复，下次再用
                        charArray[i] = originChar;
                    }
                }

                // 这一行代表表示从 begin 这一侧向外扩散了一层
                beginVisited = nextLevelVisited;
                step++;
            }
            return 0;
        }

        public static void main(String[] args) {
            List<String> wordList = new ArrayList<>();
            String[] words = {"hot", "dot", "dog", "lot", "log", "cog"};
            Collections.addAll(wordList, words);

            Solution solution = new Solution();
            String beginWord = "hit";
            String endWord = "cog";
            int ladderLength = solution.ladderLength(beginWord, endWord, wordList);
            System.out.println(String.format("从 %s 到 %s 的最短转换序列的长度：%d。", beginWord, endWord, ladderLength));
        }
        
}
//leetcode submit region end(Prohibit modification and deletion)

}