package me.jim.wx.javamodule.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2019/6/25
 * Name: wx
 * Description:
 *
 * 格雷编码是一个二进制数字系统，在该系统中，两个连续的数值仅有一个位数的差异。
 *
 * 给定一个代表编码总位数的非负整数 n，打印其格雷编码序列。格雷编码序列必须以 0 开头。
 *
 * 示例 1:
 *
 * 输入: 2
 * 输出: [0,1,3,2]
 * 解释:
 * 00 - 0
 * 01 - 1
 * 11 - 3
 * 10 - 2
 *
 * 对于给定的 n，其格雷编码序列并不唯一。
 * 例如，[0,2,3,1] 也是一个有效的格雷编码序列。
 *
 * 00 - 0
 * 10 - 2
 * 11 - 3
 * 01 - 1
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/gray-code
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 */
public class GrayCode {
    public static void main(String[] args) {
        List<Integer> list = new GrayCode().grayCode(3);
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }
    /**
     * 3
     *
     * 000
     * 001
     *
     * 010
     * 011
     *
     * 100
     * 101
     *
     * 110
     * 111
     */
    public List<Integer> grayCode(int n) {
        if (n == 0) {
            return null;
        }
        ArrayList<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        if (n == 1) {
            return list;
        }

        int mask = 1 << 1;
        for (int i = 1; i < n; i++) {
            int size = list.size();
            for (int i1 = 0; i1 < size; i1++) {
                Integer num = list.get(i1);
                num = num | mask;
                list.add(num);
            }
            mask = mask << 1;
        }
        return list;
    }
}
