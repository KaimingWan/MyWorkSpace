package algs;

/**
 * 写一下各种排序算法。总结记录见博客：http://kaimingwan.com/post/suan-fa/pai-xu-suan-fa-zong-jie-javashi-xian
 * @author Wan Kaiming on 2017/1/13
 * @version 1.0
 */
public class SortExample {
    public static void main(String[] args) {

    }

    /**
     * 冒泡排序
     * 原理概述（按照升序来解释）：a[i]和a[i+1]比较，如果a[i]比较大，就进行交换，这样不断比较，就可以把最大的值冒到最后。这样就完成了一轮冒泡。
     *
     * 稳定性：基于相邻比较，所以稳定
     * @param a
     */
    public static void bubbleSort(int[] a){
        int len = a.length;                     //标记数组长度
        for(int i=0;i<len-1;i++){
            for(int j=0;j<len-i-1;j++){
                if(a[j]>a[j+1]){                //交换函数,使用异或来比较效率更高
                    a[j]=a[j]^a[j+1];
                    a[j+1]=a[j]^a[j+1];
                    a[j]=a[j]^a[j+1];
                }
            }
        }
    }


    /**
     * 选择排序
     * 原理概述：第i次选择，选出第i小的数，放在第i个位置
     *
     * 稳定性：不是相邻交换，不稳定
     * @param a
     */
    public static void  selectionSort(int[] a){
        int len = a.length;

        for(int i=0;i<len;i++){
            int index = i;                            //用于记录一次循环找到的最小的值的小表，一开始默认为i
            for(int j=i;j<len;j++){
                if(a[j]<a[index]) index=j;                  //发现更小的值就记录其下标
            }

            if(index!=i) {     //如果发现i的值有过变化，就要进行交换
                a[i]=a[i]^a[index];
                a[index]=a[i]^a[index];
                a[i]=a[i]^a[index];
            }
        }
    }

    /** 插入排序
     * 原理概述：从前面的有序序列从尾到头进行比较，如果更小就交换，如果更大就直接break。通过不断比较实际上找到了插入的位置。（其实也是基于比较的）
     *
     * 稳定性：基于相邻比较，是稳定的
     * @param a
     */
    public static void insertionSort(int[] a){
        int len = a.length;
        for(int i=0;i<len-1;i++){
            for(int j=i;j>=0;j--){          //这里要注意是>=0，这样a[1]和a[0]的比较不会漏掉
                if(a[j+1]<a[j]){        //每次和前面的有序数列从后往前比较，如果更小则交换，其实在多次交换过程中完成了后移
                    a[j+1]=a[j+1]^a[j];
                    a[j]=a[j+1]^a[j];
                    a[j+1]=a[j+1]^a[j];
                }
                else break;
            }
        }
    }



    /**
     *   PS!!!:  实际使用中快排很常用，基于二分查找。如果工程上用的话，直接用guava的Ordering类就可以了
     *   原理概述：选定第一个数为基准值，设定left和right指针，相向移动，直到找到一对符合交换条件的值，进行交换，如此持续直到left>right的时候把基准值和a[right]值交换，将基准值两边的部分再调用递归函数来计算
     *  不是基于相邻交换的方式，不稳定。
     * @param a
     * @param left
     * @param right
     */
    public static void quickSort(int[] a, int left,int right){
        if(left>right)   return;     //出递归条件
        int start = left;          //记录开始的下标
        int end = right;            //记录结束的下标
        int baseValue = a[left];       //取第一个值为比较的基准值，比它小的在左边，比它大的在右边
        left++;                         //左边的指针从start+1开始
        while(left<=right){
            while(left<=right && a[left]<=baseValue) left++; //左边开始找到第一个大于基准值的值的下标

            while(left<=right && a[right]>=baseValue)   right--; //右边开始找到第一个小于基准值的值的下标

            if(left<right){          //互相交换
                a[right]=a[right]^a[left];
                a[left]=a[right]^a[left];
                a[right]=a[right]^a[left];
                left++;
                right--;
            }
        }
        //这时候将开始的第一个值，放到当前right下标处，当前a[right]是经过判断的肯定小于等于baseValue，如果等于则不交换
        if(a[right]!=a[start]) {
            a[right] = a[right] ^ a[start];
            a[start] = a[right] ^ a[start];
            a[right] = a[right] ^ a[start];
        }

        //将分出来的两个部分再递归调用函数本身,这时候就把刚才保存初始指针位置的start和end用起来了
        quickSort(a,start,right-1);
        quickSort(a,right+1,end);
    }

    /**         希尔排序
    也称作缩小增量排序，是插入排序的改进，因为插入排序对基本有序的数列，效率较高，
    希尔排序也是基于这种特点去优化插入排序的。
    原理：设定一个增量d，将n个数分成了d个组，同一个组内的数，保证由于。
    通过不断缩小d，这种“基本有序”的特征越来越接近“完全有序”。
    分析希尔排序的比较次数性能等还是数学难题，这里也不多介绍了。**/
    public static void shellSort(int[] a){
        int len = a.length;
        int d = len;
        while (d>1){
            d=(d+1)/2;                      //每次增量缩小一半

            for(int i=0;i<len-d;i++){       //注意上界是len-d
                if(a[i+d]<a[i]){        //交换，每次前面的都是有序序列
                    a[i+d]=a[i]^a[i+d];
                    a[i]=a[i]^a[i+d];
                    a[i+d]=a[i]^a[i+d];
                }
            }
        }
    }

    /**
    归并排序
    原理：基于分治法的思想，自顶向上慢慢的去合并排序好的有序子序列。**/
    //归并排序
    public static void mergeSort(int[] a,int low,int high){
        int mid = (low+high)/2;
        if(low<high){           //出递归条件
            mergeSort(a,low,mid);
            mergeSort(a,mid+1,high);
            merge(a,low,mid,high);
        }
    }

    //归并函数
    public static void merge(int[] a,int low,int mid ,int high){
        int[] aux = new int[high-low+1];            //每次归并需要一个辅助数组,辅助数组长度为low-high+1
        int left = low;                             //左边指针
        int right = mid+1;                          //右边指针
        int i = 0;                                  //标记辅助数组的位置


        while(left<=mid && right<=high){          //指针指向的数如果放入辅助数组则右移动
            aux[i++]=(a[left]<a[right])?a[left++]:a[right++];
        }


        //把左边多出来的数放入辅助数组
        while(left<=mid)    aux[i++] = a[left++];

        //如果是右边多出来的则把右边多出的数放入辅助数组
        while(right<=high)    aux[i++] = a[right++];


        //将辅助数组的有序内容覆盖，原数组中对应位置的数
        for(i = 0;i<aux.length;i++)   a[i+low] = aux[i];
    }

}
