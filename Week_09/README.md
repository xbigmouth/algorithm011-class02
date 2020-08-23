#### 高级动态规划

 

  特点

1. 人肉递归低效、很累

2. 找到最近最简方法，将其拆解成可重复解决的问题

3. 数学归纳法思维

本质：寻找重复性 —> 计算机指令集

动态规划：

1. Simplifying a complicated problem by breaking it down into simpler sub-problems”  (in a recursive manner)
2. Divide & Conquer + Optimal substructure  分治 + 最优子结构
3. 顺推形式： 动态递推

DP顺推模板：

```
function DP():  
  dp = [][]     # ⼆维情况   
  for i = 0 .. M {     
      for j = 0 .. N {        
          dp[i][j] = _Function(dp[i’][j’]…)     
      }   
  }   
  return dp[M][N]; 
```

 

动态规划和递归或者分治没有根本上的区别（关键看有无最优的子结构）。

共性：找到重复子问题；

差异性：最优子结构、中途可以淘汰次优解。 

高阶的DP问题： 

复杂度来源： 

1. 状态拥有更多维度（二维、三维、或者更多、甚至需要压缩）；
2. 状态方程更加复杂。 

本质：内功、逻辑思维、数学。

 ### 不同路径2这道题目的状态转移方程

 

无障碍物，dp[i,j]=dp[i-1,j]+dp[i,j-1]; 有障碍物，dp[i,j]=0;

 

#### 字符串

Atoi代码示例：

```
public int myAtoi(String str) {
    int index = 0, sign = 1, total = 0;
    //1. Empty string
    if(str.length() == 0) return 0;

    //2. Remove Spaces
    while(str.charAt(index) == ' ' && index < str.length())
        index ++;

    //3. Handle signs
    if(str.charAt(index) == '+' || str.charAt(index) == '-'){
        sign = str.charAt(index) == '+' ? 1 : -1;
        index ++;
    }

    //4. Convert number and avoid overﬂow
    while(index < str.length()){
        int digit = str.charAt(index) - '0';
        if(digit < 0 || digit > 9) break;

        //check if total will be overﬂow after 10 times and add digit
        if(Integer.MAX_VALUE/10 < total ||
                Integer.MAX_VALUE/10 == total && Integer.MAX_VALUE %10 < digit)
            return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;

        total = 10 * total + digit;
        index ++;
    }
    return total * sign;
} 
```

#### 字符串匹配算法

暴力法代码示例： 

```
public static int forceSearch(String txt, String pat) {
    int M = txt.length();
    int N = pat.length();
    for (int i = 0; i <= M - N; i++) {
        int j;
        for (j = 0; j < N; j++) {
            if (txt.charAt(i + j) != pat.charAt(j)) break;
        }
        if (j == N) {
            return i;
        }
    }
    return -1;
}
```

 

Rabin-Karp算法： 

在朴素算法中，我们需要挨个比较所有字符，才知道目标字符串中是否包含子串。那么，是否有别的方法可以用来判断目标字符串是否包含子串呢？ 答案是肯定的，确实存在一种更快的方法。为了避免挨个字符对目标字符串和子串进行比较， 我们可以尝试一次性判断两者是否相等。因此，我们需要 一个好的哈希函数（hash function）。 通过哈希函数，我们可以算出子串的哈希值，然后将它和目标字符串中的子串的哈希值进行比较。 这个新方法 在速度上比暴力法有显著提升。

Rabin-Karp 算法的思想：

1. 假设子串的长度为 M (pat)，目标字符串的长度为 N (txt)；
2. 计算子串的 hash 值 hash_pat；
3. 计算目标字符串txt中每个长度为 M 的子串的 hash 值（共需要计算 N-M+1 次）；
4. 比较 hash 值：如果 hash 值不同，字符串必然不匹配; 如果 hash 值相同， 还需要使用朴素算法再次判断。

Rabin-Karp 代码示例：

```
public final static int D = 256;
public final static int Q = 9997;

public static int RabinKarpSerach(String txt, String pat) {
    int M = pat.length();
    int N = txt.length();
    int i, j;
    int patHash = 0, txtHash = 0;

    for (i = 0; i < M; i++) {
        patHash = (D * patHash + pat.charAt(i)) % Q;
        txtHash = (D * txtHash + txt.charAt(i)) % Q;
    }

    int highestPow = 1;  // pow(256, M-1)
    for (i = 0; i < M - 1; i++)
        highestPow = (highestPow * D) % Q;

    for (i = 0; i <= N - M; i++) { // 枚举起点
        if (patHash == txtHash) {
            for (j = 0; j < M; j++) {
                if (txt.charAt(i + j) != pat.charAt(j))
                    break;
            }
            if (j == M)
                return i;
        }
        if (i < N - M) {
            txtHash = (D * (txtHash - txt.charAt(i) * highestPow) + txt.charAt(i + M)) % Q;
            if (txtHash < 0)
                txtHash += Q;
        }
    }
    return -1;
}
```

KMP算法：

KMP算法（Knuth-Morris-Pratt）的思想就是，当子串与目标字符串不匹配时，其实你已经知道了前面已经匹配成功那一部分的字符（包括子串与目标字符串）。以阮一峰的文章为例，当空格与 D 不匹配时，你其实知道前面六个字符是 “ABCDAB”。
KMP 算法的想法是，设法利用这个已知信息，不要把“搜索位置”移回已经比较过的位置，继续把它向后移，这样就提高了效率。