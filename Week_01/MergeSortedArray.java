
package leetcode.editor.cn;
//Java：合并两个有序数组
public class MergeSortedArray{
    public static void main(String[] args) {
        Solution solution = new P88MergeSortedArray().new Solution();
        // TO TEST
    }
    //leetcode submit region begin(Prohibit modification and deletion)
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
            if(l1 == null) {
                return l2;
            }
            if(l2 == null) {
                return l1;
            }

            if(l1.val < l2.val) {
                l1.next = mergeTwoLists(l1.next, l2);
                return l1;
            } else {
                l2.next = mergeTwoLists(l1, l2.next);
                return l2;
            }
        }
        

    }
}
//leetcode submit region end(Prohibit modification and deletion)

}