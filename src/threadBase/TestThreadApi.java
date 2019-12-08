package threadBase;

/**
 * @author thinkalone-code
 * @decription 多个线程共享cpu时间片，线程在cpu的调度下执行。thread的一些方法可以影响cpu对线程的调度
 * @date 2019/12/7
 */
public class TestThreadApi {

    public static void main(String[] args) {
        PrintLetter1 taskA = new PrintLetter1('A',10);
        PrintLetter1 taskB = new PrintLetter1('B',10);
        PrintLetter1 taskC = new PrintLetter1('C',10);
        PrintLetter1 taskD = new PrintLetter1('D',10);
        PrintLetter1 taskE = new PrintLetter1('E',10);

        Thread threadA = new Thread(taskA,"A");
        Thread threadB = new Thread(taskB,"B");
        taskC.setThread(threadB);
        Thread threadC = new Thread(taskC,"C");
        Thread threadD = new Thread(taskD,"D");
        Thread threadE = new Thread(taskE,"E");
        //E线程比D线程的优先级高，E线程获取cpu时间片的机会比D线程大
        threadE.setPriority(Thread.MAX_PRIORITY);

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
        threadE.start();
    }

}




/**
 * 定义一个实现Runnale接口的类，该类可生成可运行对象；run()方法中用于打印字母
 * 通过Thread的方法影响cpu的调度。影响线程获取cpu的时间片
 */
class PrintLetter1 implements Runnable{

    //打印的字母
    private char letter;

    //打印的次数
    private int num;

    //线程
    private Thread thread;


    public PrintLetter1(char letter, int num) {
        this.letter = letter;
        this.num = num;
    }

    @Override
    public void run() {
        for (int i=0;i<num;i++){
            if(Thread.currentThread().getName().equals("A")){
                //yield()线程在执行时为其他线程让出CPU时间。线程在每次打印字母的时候都让出CPU时间，所以A线程比正常调度执行的线程慢
                Thread.yield();
                System.out.print(letter+" ");
            }else if(Thread.currentThread().getName().equals("B")){
                try {
                    //sleep() 线程在执行打印任务时，先休眠一段时间，不参与cpu的调度，让其他线程可以执行。所以B线程的打印任务会比正常调度的慢。
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //sleep方法在执行中被中断会抛出异常
                    e.printStackTrace();
                }
                System.out.print(letter+" ");
            }else if(Thread.currentThread().getName().equals("C")){
                try {
                    //使用join()线程会等待该线程执行完之后再执行，所以线程C的打印任务要等待thread（thread设置成B线程）线程执行完之后执行
                    if(thread !=null){
                        thread.join();
                    }
                    System.out.print(letter+" ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.print(letter+" ");
            }

        }
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public Thread getThread() {
        return thread;
    }
}

