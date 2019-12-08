package threadBase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author thinkalone-code
 * @decription 多个任务需要对同一个对象操作时，多线程会出现不安全。
 * @date 2019/12/7
 */
public class TestThreadSync {
    //初始化1000张票
     static Ticket tickets1 = new Ticket(1000);
    //初始化1000张票，给同步的售票员卖
     static Ticket tickets2 = new Ticket(1000);

    public static void main(String[] args) {

        ExecutorService executorService1 = Executors.newCachedThreadPool();
        ExecutorService executorService2 = Executors.newCachedThreadPool();

        //1000个售票员，没人卖一张。如果线程安全最后剩余的票数是0
        for(int i=0;i<1000;i++){
                executorService1.execute(new Seller());
        }
        //1000个售票员，没人卖一张。如果线程安全最后剩余的票数是0
        for(int i=0;i<1000;i++){
            executorService2.execute(new SyncSeller());
        }
        executorService1.shutdown();
        executorService2.shutdown();

        while (!executorService1.isTerminated()){

        }
        System.out.println("1000个Seller出售门票最后剩余的票数："+tickets1.getTickets());
        while (!executorService2.isTerminated()){

        }
        System.out.println("1000个SyncSeller出售门票最后剩余的票数："+tickets2.getTickets());
    }

}

//存储剩余的票数
class Ticket {

    //总票数
    private int tickets;

    //一次出售一张票
    void sell(){
        tickets--;
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //synchronized可用于方法和代码块，多个线程执行synchronized的代码时，只要有一个线程执行，其它线程需要等待。
    synchronized void sellSync(){
        tickets--;
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getTickets() {
        return tickets;
    }

    public void setTickets(int tickets) {
        this.tickets = tickets;
    }

    public Ticket(int tickets) {
        this.tickets = tickets;
    }
}

//售票员，出售门票。可以多个售票员出售门票,不使用sychronized
class Seller implements Runnable{

    @Override
    public void run() {
        TestThreadSync.tickets1.sell();
    }
}

class SyncSeller implements Runnable{
    @Override
    public void run() {
        TestThreadSync.tickets2.sellSync();
    }
}
