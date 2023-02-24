package com.example.modules.behaviorModel.policyPattern;

/**
 * 代码结构如下：
 * <p>
 * 定义策略接口，定义通用方法。
 * 定义N个实现类，实现接口，重新方法。
 * 定义Context上下文类，利用多态进行封装。
 * 使用时通过Context上下文类进行调用，在构造函数中传入实现类的对象。
 * <p>
 * <p>
 * 策略模式：策略模式是一种行为型模式，它将对象和行为分开，将行为定义为 一个行为接口 和 具体行为的实现。策略模式最大的特点是行为的变化，行为之间可以相互替换。
 * 每个if判断都可以理解为就是一个策略，可以使得算法可独立于使用它的用户而变化。
 * <p>
 * 使用场景：
 * 1. 假设现在某超市有三个等级的会员，普通会员，VIP1，VIP2。
 * 2. 在结账的时候，三个登记的会员购买了同一种商品，普通会员不打折，VIP1打9折，VIP2打8折
 */
public class PolicyPattern1 {

    /**
     * @return 定义策略接口
     * 案例场景：
     * 有三种会员 购买相同数量和单价的产品时 需要打不同的折扣
     **/
    public interface StrategyInt {

        //price价格 n数量
        public double getPrice(double price, int n);

    }

    /**
     * 实现类1 实现接口中定义的计算价格方法
     * 普通会员类 不打折
     **/
    public static class NormalPerson implements StrategyInt {

        //普通会员不打折
        @Override
        public double getPrice(double price, int n) {
            System.out.println("普通会员不打折.....");
            return (price * n);
        }

    }

    /**
     * 实现类2 实现接口中定义的计算价格方法
     * VIP1会员 打9折
     **/
    public static class Vip1Person implements StrategyInt {

        //VIP1客户 打9折
        @Override
        public double getPrice(double price, int n) {
            System.out.println("VIP1打9折.....");
            return (price * n) * 0.9;
        }
    }

    /**
     * @Version 1.0
     * 实现类2 实现接口中定义的计算价格方法
     * VIP2会员类 打8折
     **/
    public static class Vip2Person implements StrategyInt {

        @Override
        public double getPrice(double price, int n) {
            System.out.println("VIP2打8折.....");
            return (price * n) * 0.8;
        }

    }


    /**
     * @Version 1.0
     * 上下文类 对实现类和接口进行封装
     **/
    public static class PersonContext {
        //1. 定义私有对象
        private StrategyInt strategyInt;

        //2. 定义有参构造方法
        public PersonContext(StrategyInt strategyInt) {
            this.strategyInt = strategyInt;
        }

        //3. 定义计算价格的方法
        public double getPrice(double price, int n) {
            return strategyInt.getPrice(price, n);
        }


    }


    /**
     * @Version 1.0
     * 测试类  演示策略模式的使用场景
     **/
    public static class StrategyTest {

        public static void main(String[] args) {
            //定义三个类型的对象
            NormalPerson normalPerson = new NormalPerson();
            Vip1Person vip1Person = new Vip1Person();
            Vip2Person vip2Person = new Vip2Person();
            //new context类对象 将三个类型的对象传入
            PersonContext normalPersonContext = new PersonContext(normalPerson);
            PersonContext v1personContext = new PersonContext(vip1Person);
            PersonContext v2personContext = new PersonContext(vip2Person);
            //利用多态 通过调用context类对象的计算价格方法 实际上调用的子类的计算价格方法 得到最终价格
            System.out.println("普通会员: " + normalPersonContext.getPrice(300, 20));
            System.out.println("VIP1: " + v1personContext.getPrice(300, 20));
            System.out.println("VIP2: " + v2personContext.getPrice(300, 20));
        }

    }


}
