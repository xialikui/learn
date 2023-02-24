package com.example.modules.behaviorModel.visitorModel;

/**
 * Java设计模式-访问者模式Visitor
 * 介绍
 * 访问者模式（Visitor Pattern），封装一些作用于某种数据结构的各元素的操作，它可以在不改变数据结构的前 提下定义作用于这些元素的新的操作。
 * 主要将数据结构与数据操作分离，解决 数据结构和操作耦合性问题
 * 访问者模式的基本工作原理是：在被访问的类里面加一个对外提供接待访问者的接口
 * 访问者模式主要应用场景是：需要对一个对象结构中的对象进行很多不同操作(这些操作彼此没有关联)，同时需要避免让这些操作"污染"这些对象的类，可以选用访问者模式解决。
 * <p>
 * 原理类图
 * Visitor 是抽象访问者，为该对象结构中的 ConcreteElement 的每一个类声明一个 visit 操作
 * ConcreteVisitor ：是一个具体的访问值 实现每个有 Visitor 声明的操作，是每个操作实现的部分.
 * ObjectStructure 能枚举它的元素， 可以提供一个高层的接口，用来允许访问者访问元素
 * Element 定义一个 accept 方法，接收一个访问者对象
 * ConcreteElement 为具体元素，实现了 accept 方法
 * <p>
 * 实战案例
 * 我们将创建一个定义接受操作的ComputerPart接口。键盘、鼠标、监视器和计算机是实现ComputerPart接口的具体类。我们将定义另一个接口ComputerPartVisitor，它将定义访问者类操作。计算机用具体的访问者做相应的动作。
 * 我们的演示类VisitorPatternMo将使用Computer和ComputerPartVisitor类来演示访问者模式的使用。
 */
public class VisitorModel {

    //代码实现
    //定义元素接口：ComputerPart.java
    public interface ComputerPart {
        public void accept(ComputerPartVisitor computerPartVisitor);
    }

    //创建具体的元素类，实现上面的接口

    public static class Keyboard implements ComputerPart {

        @Override
        public void accept(ComputerPartVisitor computerPartVisitor) {
            computerPartVisitor.visit(this);
        }
    }

    public static class Monitor implements ComputerPart {

        @Override
        public void accept(ComputerPartVisitor computerPartVisitor) {
            computerPartVisitor.visit(this);
        }
    }

    public static class Mouse implements ComputerPart {

        @Override
        public void accept(ComputerPartVisitor computerPartVisitor) {
            computerPartVisitor.visit(this);
        }
    }

    public static class Computer implements ComputerPart {

        ComputerPart[] parts;

        public Computer() {
            parts = new ComputerPart[]{new Mouse(), new Keyboard(), new Monitor()};
        }


        @Override
        public void accept(ComputerPartVisitor computerPartVisitor) {
            for (int i = 0; i < parts.length; i++) {
                parts[i].accept(computerPartVisitor);
            }
            computerPartVisitor.visit(this);
        }
    }


    //定义访问者接口：ComputerPartVisitor.java
    public interface ComputerPartVisitor {
        public void visit(Computer computer);

        public void visit(Mouse mouse);

        public void visit(Keyboard keyboard);

        public void visit(Monitor monitor);
    }

    //定义具体的访问者，实现访问者接口。


    public static class ComputerPartDisplayVisitor implements ComputerPartVisitor {

        @Override
        public void visit(Computer computer) {
            System.out.println("Displaying Computer.");
        }

        @Override
        public void visit(Mouse mouse) {
            System.out.println("Displaying Mouse.");
        }

        @Override
        public void visit(Keyboard keyboard) {
            System.out.println("Displaying Keyboard.");
        }

        @Override
        public void visit(Monitor monitor) {
            System.out.println("Displaying Monitor.");
        }
    }


    //创建示例类：VisitorPatternDemo.java，用于展示Computer的各个组件
    public static class VisitorPatternDemo {
        public static void main(String[] args) {
            ComputerPart computer = new Computer();
            computer.accept(new ComputerPartDisplayVisitor());
        }
    }

}
