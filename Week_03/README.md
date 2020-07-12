#### 分治

1. 在计算机科学中，分治法是建基于多项分支递归的一种很重要的算法范式。字面上的解释是“分而治之”，就是把一个复杂的问题分成两个或更多的相同或相似的子问题，直到最后子问题可以简单的直接求解，愿问题的解即子问题的解的合并。

2. 是很多高效算法的基础，如排序算法（归并排序、快速排序）、傅立叶变换（快速傅立叶变换）。

```java
def divide_conquer(problem, param1, param2):
    # recursion terminator
    if problem is None:
        print_result
        return
    # prepare data
    data = prepare_data(problem)
    subproblems = split_problem(problem, data)
    # conquer subproblems
    subresult1 = self.divide_conquer(subproblems[0], p1, …)
    subresult2 = self.divide_conquer(subproblems[1], p1, …)
    subresult3 = self.divide_conquer(subproblems[2], p1, …)
    …
    # process and generate the final result
    result = process_result(subresult1, subresult2, subresult3, ...)
    # revert the current level states
```

#### 回溯

1. 回溯法是暴力搜索法中的一种。

2. 对于某些计算问题而言，回溯法是一种可以找出所有（或一部分）解的一般性算法，尤其适用于约束满足问题（在解决约束满足问题时，我们逐步构造更多的候选解，并且在确定某一部分候选解不可能补全成正确解之后放弃继续搜索这个部分候选解本身及其可以拓展出的子候选解，转而测试其他的部分候选解）

3. 回溯法采用试错的思想，它尝试分步的去解决一个问题。在分步解决问题的过程中，当它通过尝试发现现有的分步答案不能得到有效的正确的解答的时候，它将取消上一步甚至是几步的计算，再通过其它的可能的分步解答再次尝试寻找问题的答案。

4. 回溯法通常用最简单的递归方法来实现，在反复重复上述的步骤后可能出现两种情况：找到一个可能存在的正确的答案；  在尝试了所有可能的分步法后宣告该问题没有答案在最坏的情况下，回溯法会导致一次复杂度为指数时间的计算。

   

   #### 总结

   分治和回溯，本质上就是**递归，**是递归的一个细分类。可以理解为分治和回溯，就是一种特殊的递归，或者是较为复杂的递归。分治的思想本质是递归，在递归状态树的时候，对一个问题会化解成好几个子问题；回溯可以理解为**递归**的一种问题，不断地在每一层去试，每一层有不同的办法，类似于一个一个去试，看这个方法是否可行。

