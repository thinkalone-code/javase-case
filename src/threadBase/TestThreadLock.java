package threadBase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author thinkalone-code
 * @decription 1.线程同步，可以通过锁的机制来实现。Java实现了共享锁（readLock）和排它锁（writeLock）
 * 2.
 * @date 2019/12/7
 */
public class TestThreadLock {
    //存储数据
    Map<String,String> cache = new HashMap<>();
    //创建对象
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    //获取读锁
    Lock readLock = lock.readLock();
    //获取写锁
    Lock writeLock = lock.writeLock();

    //共享锁，多个线程同时使用时，不会互斥
    String get(String key){
       String value;
        readLock.lock();
        value = cache.get(key);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        readLock.unlock();
        return value;
    }

    //排它锁，多个线程同时使用时，会互斥。
    void set(String key,String value){
        writeLock.lock();
        cache.put(key,value);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        writeLock.unlock();
    }

    public static void main(String[] args) {
        TestThreadLock testLock = new TestThreadLock();
        long startTime = System.currentTimeMillis();
        //先缓存写1000次，每次写需要耗时大概10ms
        for(int i=0;i<1000;i++){
            new Thread(()->{
                testLock.set("a","a");
            }).start();
        }
        while (true){
            if(Thread.activeCount() == 2){
                long endTime = System.currentTimeMillis();
                System.out.println("向缓存中写1000次耗时："+(endTime-startTime)+"ms");
                break;
            }
        }

        startTime = System.currentTimeMillis();

        //向缓存读100次，每次读需要耗时大概1ms
        for(int j=0;j<1000;j++){
            new Thread(()->{
                testLock.get("a");
            }).start();
        }
        while (true){
            if(Thread.activeCount() == 2){
                long endTime = System.currentTimeMillis();
                System.out.println("向缓存中读1000次耗时："+(endTime-startTime)+"ms");
                break;
            }
        }

    }
}
