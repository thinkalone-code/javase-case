package threadBase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author thinkalone-code
 * @decription 1.线程的创建和销毁比较消耗电脑资源。尤其时对哪些执行任务短的线程，频繁的创建和销毁线程会影响程序的性能。
 * 2.引入线程池，线程池可以管理线程的创建和销毁。线程的任务在执行完之后不用对线程销毁，下一个任务可以继续使用线程。
 * @date 2019/12/7
 */
public class TestThreadPool2 {
    //测试不用线程池和用线程池的性能,同时也测试不同线程池的性能 1000个打印任务
    public static void main(String[] args) {
        //创建固定大小线程的线程池。当前任务已执行的情况下，可以被下一个任务重用。性能不一定能提高，但是可以控制资源的使用，创建过多线程会耗尽电脑资源。
        ExecutorService executor = Executors.newFixedThreadPool(12);
        long startTime = System.currentTimeMillis();
        for(int i=0;i<50000;i++){
            PrintWord task = new PrintWord("A"+i,1);
            executor.execute(task);
        }
        //关闭线程池，所有提交的任务完成后关闭，新提交的任务将不会被接受
        executor.shutdown();
        while(true){
            //判断线程池所有任务是否执行完成
            if(executor.isTerminated()){
                System.out.println("子线程已经全部执行完成！！！");
                long endTime = System.currentTimeMillis();
                System.out.println("程序执行的时长是:"+(endTime-startTime)+"ms");
                break;
            }

        }
    }
}



