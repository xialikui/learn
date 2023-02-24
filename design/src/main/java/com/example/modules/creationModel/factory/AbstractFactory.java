package com.example.modules.creationModel.factory;

/**
 * 应⽤场景：
 * <p>
 * 解耦：分离职责，把复杂对象的创建和使⽤的过程分开。
 * 复⽤代码 降低维护成本：如果对象创建复杂且多处需⽤到，如果每处都进⾏编写，则很多重复代码，如果业务逻辑发⽣了改 变，需⽤四处修改；使⽤⼯⼚模式统⼀创建，则只要修改⼯⼚类即可， 降低成本。
 * 代码结构如下：
 * <p>
 * 以新能源汽车为例，五菱和特斯拉。
 * 定义汽车接口，汽车接口中定义了一些方法，比如启动、运行、关闭。
 * 定义特斯拉和五菱的汽车类，重写汽车接口的这些方法。
 * 定义生产汽车的工厂接口，接口中有生产汽车的方法，返回值类型为汽车接口。
 * 分别定义特斯拉和五菱的工厂类，实现生产汽车的工厂接口，重写生产汽车的方法。
 * 测试类中直接new五菱和特斯拉的工厂类，生产出相关的产品，调用启动、运行、关闭方法。
 * <p>
 * <p>
 * <p>
 * 通过抽象工厂我们可以减少代码之间的耦合度，比如后续再新增小鹏汽车类的话，直接新增小鹏汽车类和小鹏汽车类的工厂类即可，这种实现方式有利于业务的扩展。
 */
public class AbstractFactory {


    /**
     * @return 定义汽车的接口 汽车可以启动、跑、关闭
     **/
    public interface CarProduct {
        //启动
        void start();

        //跑
        void run();

        //关闭
        void shutDown();
    }

    /**
     * @Version 1.0
     * 定义具体的实现类
     * 特斯拉汽车
     **/
    public static class TeslaCar implements CarProduct {
        @Override
        public void start() {
            System.out.println("特斯拉启动了");
        }

        @Override
        public void run() {
            System.out.println("特斯拉跑了");
        }

        @Override
        public void shutDown() {
            System.out.println("特斯拉关闭了");
        }
    }

    /**
     * @Version 1.0
     * 定义具体的实现类
     * 五菱汽车
     **/
    public static class WulingCar implements CarProduct {
        @Override
        public void start() {
            System.out.println("五菱启动了");
        }

        @Override
        public void run() {
            System.out.println("五菱开始跑了");
        }

        @Override
        public void shutDown() {
            System.out.println("五菱关闭了");
        }
    }


    /**
     * @Version 1.0
     * 定义生产汽车的工厂类
     **/
    public interface CarproductFactory {

        CarProduct productCar();
    }


    /**
     * @Version 1.0
     * 定义生产五菱的汽车工厂
     * 生成五菱汽车的对象
     **/
    public static class WuLingFactory implements CarproductFactory {
        @Override
        public CarProduct productCar() {
            return new WulingCar();
        }
    }


    /**
     * @Version 1.0
     * 定义特斯拉汽车的工厂
     * 生产特斯拉汽车
     **/
    public static class TeslaFactory implements CarproductFactory {
        @Override
        public CarProduct productCar() {
            return new TeslaCar();
        }
    }


    /**
     * @Version 1.0
     * 测试类
     **/
    public static class TestFactory {

        public static void main(String[] args) {
            //生产五菱汽车
            WuLingFactory wuLingFactory = new WuLingFactory();
            CarProduct carProduct = wuLingFactory.productCar();
            carProduct.start();
            carProduct.run();
            carProduct.shutDown();
            System.out.println("*******************************");
            //生产特斯拉汽车
            TeslaFactory teslaFactory = new TeslaFactory();
            CarProduct carProduct1 = teslaFactory.productCar();
            carProduct1.start();
            carProduct1.run();
            carProduct1.shutDown();
        }
    }

}
