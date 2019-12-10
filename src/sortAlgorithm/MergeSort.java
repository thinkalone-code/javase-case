package sortAlgorithm;

/**
 * @author thinkalone-code
 * @decription 归并排序算法：归并排序算法将数组分为两半，对每部分递归的应用归并排序。在两部分都排好序之后，对它们进行归并
 * @date 2019/12/8
 */
public class MergeSort {

    //测试归并排序算法
    public static void main(String[] args) {
        int[] list = {1,4,5,3,8,6,8,4,7,8,9,11,43,17,89,34,68,2,5,7,8,26};
        mergeSort(list);
        for(int i=0;i<list.length;i++){
            System.out.print(list[i]+" ");
        }
    }




    /**
     * 归并排序
     * 1.当数组长度大于1时，进行递归调用
     * 2.将数组分为两半，每一部分都进行递归调用
     * 3.当数组长度小于等于1的时候递归中止
     * 4.在递归返回时，对左右数组进行排序merge()
     * @param list 传入要排序的数组
     */
    public static void mergeSort(int[] list){
        if(list.length > 1){
            //初始化leftList数组，并且赋值
            int[] leftList = new int[list.length/2];
            System.arraycopy(list,0,leftList,0,leftList.length);
            mergeSort(leftList);

            //初始化rigthList数组，并且赋值
            int rightLength = list.length - leftList.length;
            int[] rightList = new int[rightLength];
            System.arraycopy(list,leftList.length,rightList,0,rightList.length);
            mergeSort(rightList);

            //在递归返回时，对leftList和rightList进行排序。第一次递归返回时，leftList和rightList的长度为1。
            merge(leftList,rightList,list);
        }
    }


    /**
     *
     * @param list1 左边排好序的数组
     * @param list2 右边排好序的数组
     * @param temp  将排好序的数组归并之后放入的数组
     */
    public static void merge(int[] list1,int[] list2,int[] temp){
        //定义索引，存储当前访问索引的位置
        int currentIndex1 = 0;
        int currentIndex2 = 0;
        int currentIndex3 = 0;

        //遍历比较list1和list2，谁小谁放入tmp,然后当前位置前移(currentIndex加1)
        while (currentIndex1 < list1.length && currentIndex2 < list2.length){
            if(list1[currentIndex1] < list2[currentIndex2]){
                temp[currentIndex3++] = list1[currentIndex1++];
            }else{
                temp[currentIndex3++] = list2[currentIndex2++];
            }
        }

        //list1和list2长度不相等时，将余下部分直接放入到tmp中
        while(currentIndex1 < list1.length){
            temp[currentIndex3++] = list1[currentIndex1++];
        }
        while(currentIndex2 < list2.length){
            temp[currentIndex3++] = list2[currentIndex2++];
        }

    }

}
