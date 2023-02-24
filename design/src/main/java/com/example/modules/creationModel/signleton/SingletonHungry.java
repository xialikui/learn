package com.example.modules.creationModel.signleton;


/**
 * 单例模式
 * 饿汉写法
 * 结构如下：
 * 私有的静态的 最终的 对象 直接new
 * 私有的 无参构造方法
 * 共有的 静态的 实例方法
 *
 * 优点：这种写法比较简单，就是在类装载的时候就完成实例化，避免了线程同步问题。
 * 缺点：但是因为在指定对象时就进行初始化，在类比较大的时候，也会造成一定的资源消耗。
 *
 * 结果
 * 可以看到执行结果是一致的，这种饿汉写法一般在实际项目中应用的也是最多的，
 */
public class SingletonHungry {
    private static final SingletonHungry singleton = new SingletonHungry();

    //2. 私有的无参构造函数
    private SingletonHungry() {

    }

    //3. 公共的静态的实例方法
    public static SingletonHungry getInstance() {
        return singleton;
    }

    //测试方法
    public static void main(String[] args) {
        //利用for循环 模拟多线程环境调用
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                //看每次获取对象的hashcode是否一致 判断是否获取了同一个对象
                System.out.println("获取的hashCode是： " + SingletonHungry.getInstance().hashCode());
            }).start();
        }
    }


}
