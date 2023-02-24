package com.example.modules.creationModel.signleton;

/**
 * 结构如下：
 * 私有的静态的对象 为空 不new
 * 私有的无参构造方法
 * 共有的静态的实例方法
 *
 * 单例模式 懒汉式
 * 调用实例方法时才new对象
 * 节省空间 缺点是线程不安全
 *
 * 结果
 *为了避免上述所说的饿汉式的缺点，延伸出了懒汉式的单例写法，在定义对象时，并不直接进行初始化，在实际的实例化方法里面，才进行初始化操作，这样就节省了一定的资源
 */
public class SingletonFull {
    //1. 私有的静态的对象 先不new 默认为null值
    private static SingletonFull singletonFull;

    //2. 私有的无参构造器
    private SingletonFull() {
    }

    //3. 公共的静态的方法
    public static SingletonFull getInstance() throws InterruptedException {
        if (singletonFull == null) {
            Thread.sleep(1000);
            singletonFull = new SingletonFull();
        }
        return singletonFull;
    }

    //测试方法
    public static void main(String[] args) {
        //利用for循环 模拟多线程环境调用
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                //看每次获取对象的hashcode是否一致 判断是否获取了同一个对象
                try {
                    System.out.println("获取的hashCode是： " + singletonFull.getInstance().hashCode());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }


}
