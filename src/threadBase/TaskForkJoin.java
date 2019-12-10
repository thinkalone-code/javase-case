package threadBase;

import com.sun.scenario.effect.Merge;
import sortAlgorithm.MergeSort;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * @author thinkalone-code
 * @decription java并行编程，多处理器可以处理多个任务。JavaForkJoin编程可以实现任务的并行处理。
 * fork()生成异步的任务，join（）等待任务执行完成返回结果。
 * @date 2019/12/8
 */
public class TaskForkJoin {

    //测试并行归并排序和普通的归并排序的效率
    public static void main(String[] args) {
        final int SIZE = 70000000;
        int[] list1 = new int[SIZE];
        int[] list2 = new int[SIZE];

        for(int i=0;i<list1.length;i++){
            list1[i] = list2[i]=(int)(Math.random() * 100000000);
        }

        long startTime = System.currentTimeMillis();
        paralleMergeSort(list1);
        long endTime = System.currentTimeMillis();
        System.out.println("并行归并排序算法，并行度："+Runtime.getRuntime().availableProcessors()+";耗时："+(endTime-startTime)+"ms");


        startTime = System.currentTimeMillis();
        MergeSort.mergeSort(list2);
        endTime = System.currentTimeMillis();
        System.out.println("普通归并排序算法，耗时："+(endTime-startTime)+"ms");

    }

    public static void paralleMergeSort(int[] list){
        RecursiveAction mainTask = new SortTask(list);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(mainTask);
    }

    /**
     * 1.定义任务如何执行，需要实现RecursiveAction类
     * 2.RecursiveTask<V>,定义任务如何执行，当任务完成时返回值。
     */
    private static class SortTask extends RecursiveAction{

        //阈值，数据量小的排序不使用并行排序，小数据并行排序还不如直接排序。
        private final int THRESHOLD = 500;
        //需要排序的数组
        private int[] list;

        public SortTask(int[] list) {
            this.list = list;
        }

        @Override
        protected void compute() {
            //递归调用返回的临界条件，如果小于THRESHOLD,任务将不在递归，执行递归返回操作。
            if(list.length < THRESHOLD){
                Arrays.sort(list);
            }else{
                //将数组分成两份
                //初始化leftList数组，并且赋值
                int[] leftList = new int[list.length/2];
                System.arraycopy(list,0,leftList,0,leftList.length);

                //初始化rigthList数组，并且赋值
                int rightLength = list.length - leftList.length;
                int[] rightList = new int[rightLength];
                System.arraycopy(list,leftList.length,rightList,0,rightList.length);

                //分解给定的任务，并且在所有任务都完成的时候返回
                invokeAll(new SortTask(leftList),new SortTask(rightList));

                MergeSort.merge(leftList,rightList,list);
            }
        }
    }
}



