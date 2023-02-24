package com.example.modules.structuralModel.decorateModel;

/**
 * 装饰器模式是一种 对象结构型模式 ，它通过一种无须定义子类的方式来给对象动态增加职责/功能，使用对象之间的关联关系取代类之间的继承关系。
 * <p>
 * 应用场景如下：
 * <p>
 * 举例说明：
 * 例如五菱汽车，五菱汽车首先能跑，这种特性是属于不变的特性。
 * 而其他的型号则是不同的，例如续航不同、是否敞篷、是否有智能语音等等。
 * 针对不变的特性可以定义为接口，针对变化的特性可以定义装饰器，装饰器就是在原有的基础上增加了一些修饰。
 * <p>
 * 代码结构如下：
 * 定义五菱汽车抽象类，通用方法
 * 定义敞篷版、gameboy版本继承至五菱汽车抽象类，重写通用方法。
 * 定义五菱的装饰器类，继承至五菱汽车抽象类，传入五菱汽车抽象类型的对象，调用五菱汽车抽象类型的通用方法。
 * 分别定义五菱汽车的敞篷装饰器和续航增强装饰器，继承至五菱汽车的装饰器类，重写通用方法，增加装饰器功能。
 * 通过测试类调用，new 五菱敞篷对象，传入敞篷装饰器，调用装饰器的run方法。
 * <p>
 * 我们可以看到，装饰器模式的优势在于，封装不变的run方法，然后基于run方法进行增强，满足在不同场景下针对某些方法进行不同方式的增强，
 * 假如又新增了一个型号，那么只需要新增型号类和具体的装饰器类即可，大大减少了代码的耦合，同时又满足方法的增强
 */
public class DecorateModel {


    /**
     * @Version 1.0
     * 五菱新能源汽车
     **/
    public abstract static class WulingNewEngeryCar {

        abstract void run();
    }

    /**
     * @Version 1.0
     * 五菱gameBoy
     **/
    public static class WulingGameBoy extends WulingNewEngeryCar {
        @Override
        void run() {
            System.out.println("五菱gameBoy");
        }
    }

    /**
     * @Version 1.0
     * 五菱敞篷版
     **/
    public static class Wulingchangpeng extends WulingNewEngeryCar {
        @Override
        void run() {
            System.out.println("敞篷版五菱");
        }
    }


    /**
     * @Version 1.0
     * 定义五菱汽车的装饰器类
     **/
    public abstract static class WulingDecorate extends WulingNewEngeryCar {
        //私有的对象
        private WulingNewEngeryCar wulingNewEngeryCar;

        //公共的构造函数
        public WulingDecorate(WulingNewEngeryCar wulingNewEngeryCar) {
            this.wulingNewEngeryCar = wulingNewEngeryCar;
        }

        //重写汽车的能力
        @Override
        void run() {
            wulingNewEngeryCar.run();
        }
    }

    /**
     * @Version 1.0
     * 敞篷装饰器
     **/
    public static class NoDoorDecorate extends WulingDecorate {

        //调用父类的构造方法
        public NoDoorDecorate(WulingNewEngeryCar wulingNewEngeryCar) {
            super(wulingNewEngeryCar);
        }

        //增加装饰
        @Override
        void run() {
            super.run();
            System.out.println("增加敞篷功能");
        }
    }


    /**
     * @Version 1.0
     * 续航增强装饰器
     **/
    public static class RunLongDecorate extends WulingDecorate {
        public RunLongDecorate(WulingNewEngeryCar wulingNewEngeryCar) {
            super(wulingNewEngeryCar);
        }

        @Override
        void run() {
            super.run();
            System.out.println("续航增强");
        }

    }

    /**
     * @Version 1.0
     * 测试类
     **/
    public static class AppTestClass {
        public static void main(String[] args) {
            //new 一个wuling敞篷类
            Wulingchangpeng wulingchangpeng = new Wulingchangpeng();
            //调用敞篷装饰器 增加敞篷功能
            NoDoorDecorate noDoorDecorate = new NoDoorDecorate(wulingchangpeng);
            noDoorDecorate.run();
            RunLongDecorate runLongDecorate1 = new RunLongDecorate(wulingchangpeng);
            runLongDecorate1.run();
            System.out.println("*****************");
            WulingGameBoy wulingGameBoy = new WulingGameBoy();
            RunLongDecorate runLongDecorate = new RunLongDecorate(wulingGameBoy);
            runLongDecorate.run();
        }

    }
}
