package threadBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author thinkalone-code
 * @decription 使用fork/join框架实现并行查找线性表最大值。
 *  并行的处理步骤。提交一个任务，如果任务可分割，创建异步线程继续提交分割后的任务。如果任务不可分割，执行任务。（有递归的思想）
 * @date 2019/12/8
 */
public class ParallelMax {

    public static void main(String[] args){
        final int N = 9000000;
        int[] list1 = new int[N];
        int[] list2 = new int[N];
        for(int i=0;i<list1.length;i++){
            list1[i] = list2[i] = i;
        }
        long startTime = System.currentTimeMillis();
        System.out.println("list的最大值："+parallelMax(list1));
        long endTime = System.currentTimeMillis();
        System.out.println("并行度："+Runtime.getRuntime().availableProcessors()+";耗时："+(endTime-startTime)+"ms");
        startTime = System.currentTimeMillis();
        System.out.println("list的最大值："+ max(list2));
        endTime = System.currentTimeMillis();
        System.out.println("顺序执行的max()耗时："+(endTime-startTime)+"ms");
    }

    public static int parallelMax(int[] list){
        RecursiveTask<Integer> task = new MaxTask(list,0,list.length);
        ForkJoinPool pool = new ForkJoinPool();
        //提交任务，开始执行
        return pool.invoke(task);
    }

    public static int max(int[] list){
        //选取一个最大数
        int max = list[0];
        for(int i=1;i<list.length;i++){
            if(list[i] > max){
                max = list[i];
            }
        }
        return max;
    }




    //定义任务,实现RecursiveTask<V>类
    private static class MaxTask extends RecursiveTask<Integer>{
        //定义阈值，即一个任务最小单位。如数组，没THRESHOLD个元素生成一个任务
        private final static int THRESHOLD = 1000;
        private int[] list;
        private int low;
        private int high;

        public MaxTask(int[] list, int low, int high) {
            this.list = list;
            this.low = low;
            this.high = high;
        }

        @Override
        protected Integer compute() {
            if(high-low < THRESHOLD){
                //定义怎么执行任务,一个任务需要执行的代码

                //选取一个最大数
                int max = list[0];
                for(int i=low;i<high;i++){
                    if(list[i] > max){
                        max = list[i];
                    }
                }
                return new Integer(max);
            }else {
                //定义怎么分割任务。创建任务子线程。

                int mid = (low + high) / 2;
                RecursiveTask<Integer> left = new MaxTask(list, low, mid);
                RecursiveTask<Integer> right = new MaxTask(list, mid, high);
                //fork()安排异步执行任务。
                right.fork();
                left.fork();
                //join()计算完成时，返回计算结果
                return new Integer(Math.max(left.join().intValue(), right.join().intValue()));
            }
        }
    }
}
