
//给你两个数组，arr1 和 arr2， 
//
// 
// arr2 中的元素各不相同 
// arr2 中的每个元素都出现在 arr1 中 
// 
//
// 对 arr1 中的元素进行排序，使 arr1 中项的相对顺序和 arr2 中的相对顺序相同。未在 arr2 中出现过的元素需要按照升序放在 arr1 的末
//尾。 
//
// 
//
// 示例： 
//
// 输入：arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
//输出：[2,2,2,1,4,3,3,9,6,7,19]
// 
//
// 
//
// 提示： 
//
// 
// arr1.length, arr2.length <= 1000 
// 0 <= arr1[i], arr2[i] <= 1000 
// arr2 中的元素 arr2[i] 各不相同 
// arr2 中的每个元素 arr2[i] 都出现在 arr1 中 
// 
// Related Topics 排序 数组 
// 👍 74 👎 0


package com.cute.leetcode.editor.cn;

public class RelativeSortArray {

    public static void main(String[] args) {

        Solution solution = new RelativeSortArray().new Solution();

    }

    //leetcode submit region begin(Prohibit modification and deletion)
    class Solution {
        public int[] relativeSortArray(int[] arr1, int[] arr2) {
            int[] temp = new int[1001];
            for(int num : arr1){
                temp[num]++;//先将arr1中的数字存储在数组中
            }
            int[] ans = new int[arr1.length];
            int t = 0;
            for(int i = 0;i < arr2.length;i++){
                while(temp[arr2[i]] > 0){
                    ans[t++] = arr2[i];//将arr2中的数字先存在结果数组中，如果在arr1中此数字出现不止一次，要将其所有的都存入结果数组
                    temp[arr2[i]]--;
                }
            }
            for(int i = 0;i < temp.length;i++){//再次遍历临时数组，此时存储的是arr2中没有出现的数字
                while(temp[i] > 0){
                    ans[t++] = i;
                    temp[i]--;
                }
            }
            return ans;
        }
    }
//leetcode submit region end(Prohibit modification and deletion)


}