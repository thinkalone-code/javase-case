package threadBase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author thinkalone-code
 * @decription 1.线程的创建和销毁比较消耗电脑资源。尤其时对哪些执行任务短的线程，频繁的创建和销毁线程会影响程序的性能。
 * 2.引入线程池，线程池可以管理线程的创建和销毁。线程的任务在执行完之后不用对线程销毁，下一个任务可以继续使用线程。
 * @date 2019/12/7
 */
public class TestThreadPool3 {
    //测试不用线程池和用线程池的性能,同时也测试不同线程池的性能 1000个打印任务
    public static void main(String[] args) {
        //它会在必要的时候创建新的线程，如果之前的线程可用，则先重用之前的线程
        ExecutorService executor = Executors.newCachedThreadPool();
        long startTime = System.currentTimeMillis();
        for(int i=0;i<50000;i++){
            PrintWord task = new PrintWord("A"+i,1);
            executor.execute(task);
        }
        //关闭线程池，所有线程执行完之后线程池才会关闭
        executor.shutdown();
        //判断线程池是否关闭
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



