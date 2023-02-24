package com.example.modules.structuralModel.poxyModel;

/**
 * 代理模式
 * 代理模式是常用的java设计模式，他的特征是代理类与委托类有同样的接口，代理类主要负责为委托类预处理消息、过滤消息、把消息转发给委托类，以及事后处理消息等。
 * 静态代理模式
 * 静态代理就是通过自定义的类，去实现代理过程的一种模式，他只能代理这个类，要想代理其他类，要想代理其他类需要写新的代理方法。
 * <p>
 * 代码结构如下：
 * 定义汽车接口
 * 定义汽车接口的实现类，实现汽车接口。
 * 定义汽车的代理类，实现汽车接口。
 * 在代理类中注入接口类型的对象，调用run方法，在run方法前后实现新方法的调用
 * <p>
 * <p>
 * 通过静态代理，我们可以简单的实现对某个类、接口的代理，但是静态代理也有一定的局限性， 如果我们需要对某个新类进行代理时，又需要代理类实现新的接口去重写一些方法，
 * 这样显然是不太方便的，所以JDK给我们提供了动态代理的方法。
 */
public class staticPoxy {



    /***
     * 定义汽车接口：
     * @return
     * 定义汽车类
     **/
    public interface CarInterface {
        //汽车可以跑
        public void run();
    }




    /**
     * 定义汽车类，实现汽车接口：
     * @Version 1.0
     **/
    public static class Car implements CarInterface {

        @Override
        public void run() {
            System.out.println("汽车在跑.......");
        }
    }




    /**
     * 定义汽车的代理类，也实现汽车接口：
     * @Version 1.0
     * 定义汽车的代理对象 代理对象也实现汽车接口
     **/
    public static class Carproxy  implements CarInterface {
        //私有化汽车类
        private Car car;
        //创建构造函数
        public Carproxy(Car car) {
            this.car = car;
        }
        //调用汽车类的run方法 在run之前和之后可以定义新方法
        @Override
        public void run() {
            beforeRun();
            car.run();
            afterRun();
        }

        //汽车运行之前调用的方法
        private void afterRun() {
            System.out.println("汽车打开火了.....");
        }

        //汽车运行之后调用的方法
        private void beforeRun() {
            System.out.println("汽车熄火了.....");
        }
    }

    /**
     * @Version 1.0
     * 测试类
     **/
    public static class TestMain {
        public static void main(String[] args) {
            Carproxy carproxy = new Carproxy(new Car());
            carproxy.run();
        }
    }


}
