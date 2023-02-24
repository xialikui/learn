package com.example.modules.creationModel.signleton;

/**
 * 结构如下：
 * 私有的静态的对象 为空 不new
 * 私有的无参构造方法
 * 共有的静态的实例方法，在判断对象是否为空时加上synchronize修饰
 * 在判断里面再次判断是否为空
 * <p>
 * <p>
 * 单例写法
 * 双重判断式
 * <p>
 * 结果
 * 虽然执行结果是我们想要的，但是由于引入了synchronized代码块，所以也引入了轻量级锁、重量级锁的概念，虽然保障了线程安全，但是却失去了性能加成并且容易导致死锁，
 * 所以，有没有什么办法，既能线程安全，又能保障效率呢？
 */
public class SingletonThreadTwo {
    //1. 私有的静态的对象
    private static SingletonThreadTwo singletonThreadTwo;

    //2. 私有的构造方法
    private SingletonThreadTwo() {
    }

    //3. 公共的静态的实例方法 在if里面加上锁synchronized 在锁块中继续判断是否为空
    public static SingletonThreadTwo getInstance() {
        if (singletonThreadTwo == null) {
            synchronized (SingletonThreadTwo.class) {
                if (singletonThreadTwo == null) {
                    singletonThreadTwo = new SingletonThreadTwo();
                }
            }
        }
        return singletonThreadTwo;
    }

    //测试方法
    public static void main(String[] args) {
        //利用for循环 模拟多线程环境调用
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                //看每次获取对象的hashcode是否一致 判断是否获取了同一个对象
                System.out.println("获取的hashCode是： " + SingletonThreadTwo.getInstance().hashCode());
            }).start();
        }
    }


}
