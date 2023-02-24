package com.example.modules.creationModel.factory;

/**
 * 相信大家也没少使用工厂模式，这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式，在工厂模式中，我们在创建对象时不会对客户端暴露创建逻辑，并且是通过使用一个共同的接口来指向新创建的对象。
 * 在任何需要生成复杂对象的地方，都可以使用工厂方法模式。有一点需要注意的地方就是复杂对象适合使用工厂模式，而简单对象，特别是只需要通过 new 几次对象就不用了, 这种情况，
 * 无需使用工厂模式。如果使用工厂模式，就需要引入一个工厂类，会增加系统的复杂度。
 * <p>
 * 简单工厂
 * 简单工厂是工厂模式的简单实现的写法，这种写法比较简便，同时也带来了耦合的问题，我们来定义飞机和汽车以及生产他们的工厂类，主要实现代码如下：
 *
 * 这种简单工厂的写法 比较简单方便，但缺点也是显而易见的，就是定义的类耦合度太高，后续如果新增了对象，需要多次修改工厂类，怎么解决这个缺点呢？请看抽象工厂模式。
 */
public class SampleFactory {

    /**
     * @Version 1.0
     * 飞机类
     **/
    public static class Plane {
        public void go() {
            System.out.println("飞机嗖嗖嗖....");
        }
    }

    /**
     * @Version 1.0
     * 汽车类
     **/
    public static class Car {
        public void go() {
            System.out.println("汽车滴滴滴....");
        }
    }


    /**
     * @Version 1.0
     * 工厂类
     **/
    public static class ProductFactory {

        public Car getCar() {
            return new Car();
        }

        public Plane getPlane() {
            return new Plane();
        }
    }


    /**
     * @Version 1.0
     * 测试类
     **/
    public static class TestClass {

        public static void main(String[] args) {
            Car car = new ProductFactory().getCar();
            Plane plane = new ProductFactory().getPlane();
            car.go();
            plane.go();
        }
    }

}
