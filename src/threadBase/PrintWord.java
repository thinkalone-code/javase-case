package threadBase;

/**
 * @author thinkalone-code
 * @decription 定义一个实现Runnale接口的类，该类可生成可运行对象；run()方法中用于打印单词
 * @date 2019/12/7
 */
class PrintWord implements Runnable{

    //打印的字母
    private String letter;

    //打印的次数
    private int num;

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i=0;i<num;i++){
            System.out.print(letter+" ");
        }
    }

    public PrintWord(String letter, int num) {
        this.letter = letter;
        this.num = num;
    }
}