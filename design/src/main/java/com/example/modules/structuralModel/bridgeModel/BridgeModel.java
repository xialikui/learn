package com.example.modules.structuralModel.bridgeModel;

/**
 * 一、什么是桥接模式
 * 桥接(Bridge)模式属于结构型设计模式。通过提供抽象化和实现化之间的桥接结构，来实现二者的解耦。把抽象(abstraction)与行为实现(implementation)分离开来，
 * 从而可以保持各部分的独立性以及应对它们的功能扩展。该UML结构图如下：
 * 主要角色
 * （1）Abstraction：抽象类。
 * （2）RefinedAbstraction：扩充抽象类。
 * （3）Implementor：实现类接口。
 * （4）ConcreteImplementor：具体实现类
 * <p>
 * 二、适用场景
 * 当一个类存在两个独立变化的维度，且这两个维度都需要进行扩展时
 * 当一个系统不希望使用继承或因为多层次继承导致系统类的个数急剧增加时
 * 当一个系统需要在构件的抽象化角色和具体化角色之间增加更多的灵活性时
 * <p>
 * 四、桥接模式优缺点
 * 1、优点：
 * 桥接模式可以取代多层继承的方案。 多层继承违背了单一职责原则，复用性较差，类的个数也非常多。桥接模式可以极大的减少子类的个数，从而降低管理和维护的成本。
 * 桥接模式极大的提高了系统可扩展性，在两个变化维度中任意扩展一个维度，都不需要修改原有的系统，符合开闭原则。
 * 2、缺点
 * 会增加系统的理解与设计难度，由于关联关系建立在抽象层，要求开发者一开始就针对抽象层进行设计与编程
 * 正确识别出系统中两个独立变化的维度并不是一件容易的事情
 */
public class BridgeModel {

    //创建食品类，该类为抽象类，主要提供添加add的方法；

    public abstract static class Food {
        //水果
        Fruit fruit;

        public void setFruit(Fruit fruit) {
            this.fruit = fruit;
        }

        /**
         * 制作时添加
         */
        public abstract void add();
    }

    //1.1、食品类的子类一：蛋糕
    public static class Cake extends Food {

        @Override
        public void add() {
            fruit.beAdd("蛋糕");
        }
    }

    //1.2、食品类的子类二：牛奶
    public static class Milk extends Food {
        @Override
        public void add() {
            fruit.beAdd("牛奶");
        }
    }

    //1.3、食品类的子类三：奶茶
    public class MilkTea extends Food {
        @Override
        public void add() {
            fruit.beAdd("奶茶");
        }
    }

    //2、创建水果接口类
    public interface Fruit {
        /**
         * 被添加
         *
         * @param food
         */
        public void beAdd(String food);
    }

    //2.1、水果接口实现类一：香蕉
    public class Banana implements Fruit {
        @Override
        public void beAdd(String food) {
            System.out.println("香蕉" + food);
        }
    }

    //2.2、水果接口实现类二：草莓
    public class Strawberry implements Fruit {
        @Override
        public void beAdd(String food) {
            System.out.println("草莓" + food);
        }
    }

    //2.3、水果接口实现类三：芒果
    public static class Mango implements Fruit {
        @Override
        public void beAdd(String food) {
            System.out.println("芒果" + food);
        }
    }

    //3、主方法测试
    public static class TestMain {
        public static void main(String[] args) {
            //蛋糕
            Fruit mango = new Mango();
            Food cake = new Cake();
            cake.setFruit(mango);
            cake.add();
            //牛奶
            Food milk = new Milk();
            milk.setFruit(mango);
            milk.add();
        }
    }

}
