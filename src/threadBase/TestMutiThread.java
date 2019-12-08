package threadBase;

/**
 * @author thinkalone-code
 * @decription 测试多线程的执行。多线程是多个任务同时执行，对于cup时间片是共享的，多个任务是交替执行的。
 * @date 2019/12/7
 */
public class TestMutiThread {
    /**
     * 现在有三个打印任务，a线程打印字母A，b线程打印字母B，c线程打印字母C
     * @param args
     */
    public static void main(String[] args) {

        PrintLetter taskA = new PrintLetter('A',1000);
        PrintLetter taskB = new PrintLetter('B',1000);
        PrintLetter taskC = new PrintLetter('C',1000);

        Thread threadA = new Thread(taskA);
        Thread threadB = new Thread(taskB);
        Thread threadC = new Thread(taskC);

        threadA.start();
        threadB.start();
        threadC.start();
    }

}

/**
 * 定义一个实现Runnale接口的类，该类可生成可运行对象；run()方法中用于打印字母
 */
class PrintLetter implements Runnable{

    //打印的字母
    private char letter;

    //打印的次数
    private int num;

    @Override
    public void run() {
        for (int i=0;i<num;i++){
            System.out.print(letter+" ");
        }
    }

    public PrintLetter(char letter, int num) {
        this.letter = letter;
        this.num = num;
    }
}
