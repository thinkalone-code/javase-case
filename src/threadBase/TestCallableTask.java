package threadBase;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author thinkalone-code
 * @decription 获取线程返回值。使用定义一个实现Callable<V>接口的任务。Callable的任务必须由ExecutorService提交。
 * @date 2019/12/10
 */
public class TestCallableTask {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        //获取线程返回值
        ArrayList<Future<String>> results = new ArrayList<>();
        for(int i=0;i<8;i++){
            results.add(executorService.submit(new Task(i)));
        }

        for(Future<String> result : results){
            try {
                System.out.println(result.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }finally{
                executorService.shutdown();
            }
        }

    }
}

class Task implements Callable<String>{
    //任务Id号
    private int taskId;

    public Task(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public String call() throws Exception {
        return "获取任务Id号："+taskId;
    }
}
