package com.example.modules.creationModel.signleton;


/**
 * 结构如下：
 * 私有的静态的对象 为空 不new
 * 私有的无参构造方法
 * 共有的静态的实例方法，在判断对象是否为空时加上synchronize修饰
 * <p>
 * 单例模式之 加锁
 * 线程安全 缺点是效率低 受synchronized锁升级的影响
 *
 * 结果
 *  我们可以看到，执行结果并不如人意，为什么呢，这是因为在执行到synchronized代码快的时候，有线程已经获取到了对象，从而导致获取的对象不一致的情况，那么如何解决这个问呢
 */
public class SingletonThread {

    //1. 私有的静态的对象
    private static SingletonThread singletonThread;

    //2. 私有的构造方法
    private SingletonThread() {
    }

    //3. 公共的静态的实例方法 在if里面加上锁synchronized
    public static SingletonThread getInstance() {
        if (singletonThread == null) {
            synchronized (SingletonThread.class) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                singletonThread = new SingletonThread();
            }
        }
        return singletonThread;
    }

    //测试方法
    public static void main(String[] args) {
        //利用for循环 模拟多线程环境调用
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                //看每次获取对象的hashcode是否一致 判断是否获取了同一个对象
                System.out.println("获取的hashCode是： " + SingletonThread.getInstance().hashCode());
            }).start();
        }
    }

}
