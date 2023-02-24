package com.example.modules.structuralModel.compositeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合模式（Composite）的定义
 * 组合(Composite)模式是一种对象的行为模式。将对象组合成树形结构以表示“部分-整体”的层次结构。组合模式使得用户对单个对象和组合对象的使用具有一致性。
 * 组合模式的本质：统一叶子对象和组合对象。
 * 组合模式的目的：让客户端不再区分操作的是组合对象还是叶子对象，而是以一个统一的方式来操作。
 * <p>
 * 从上图可以看出，文件系统是一个树结构，树上长有节点。树的节点有两种，一种是树枝节点，即目录，有内部树结构，在图中涂有颜色；另一种是文件，即树叶节点，没有内部树结构。
 * 显然，可以把目录和文件当做同一种对象同等对待和处理，这也就是合成模式的应用。
 * 合成模式可以不提供父对象的管理方法，但是合成模式必须在合适的地方提供子对象的管理方法，诸如：add()、remove()、以及getChild()等。
 * 合成模式的实现根据所实现接口的区别分为两种形式，分别称为安全式和透明式。
 * <p>
 * 组合模式（Composite）优缺点
 * 组合模式是一种结构型设计模式。其主要优点如下：
 * 组合模式使得客户端代码可以一致地处理单个对象和组合对象，无须关心自己处理的是单个对象，还是组合对象，这简化了客户端代码；
 * 更容易在组合体内加入新的对象，客户端不会因为加入了新的对象而更改源代码，满足“开闭原则”；
 * 缺点：
 * 设计较复杂，客户端需要花更多时间理清类之间的层次关系；
 * 不容易限制容器中的构件；
 * 不容易用继承的方法来增加构件的新功能；
 * 适用环境：
 * 在现实生活中，存在很多“部分-整体”的关系。汽车与轮胎、发动机的关系。医院与科室、医生的关系。学校与学院、学生、老师的关系。
 * <p>
 * 安全式合成模式的结构
 * 安全模式的合成模式要求管理聚集的方法只出现在树枝构件类中，而不出现在树叶构件类中。
 * 这种形式涉及到三个角色：
 * 抽象构件(Component)角色：这是一个抽象角色，它给参加组合的对象定义出公共的接口及其默认行为，可以用来管理所有的子对象。合成对象通常把它所包含的子对象当做类型为Component的对象。在安全式的合成模式里，构件角色并不定义出管理子对象的方法，这一定义由树枝构件对象给出。
 * 树叶构件(Leaf)角色：树叶对象是没有下级子对象的对象，定义出参加组合的原始对象的行为。
 * 树枝构件(Composite)角色：代表参加组合的有下级子对象的对象。树枝构件类给出所有的管理子对象的方法，如add()、remove()以及getChild()。
 * <p>
 * 可以看出，树枝构件类(Composite)给出了addChild()、removeChild()以及getChild()等方法的声明和实现，而树叶构件类则没有给出这些方法的声明或实现。
 * 这样的做法是安全的做法，由于这个特点，客户端应用程序不可能错误地调用树叶构件的聚集方法，因为树叶构件没有这些方法，调用会导致编译错误。
 * 安全式合成模式的缺点是不够透明，因为树叶类和树枝类将具有不同的接口。
 */
public class SafeComposite {

    // 抽象构件角色类
    public interface Component {
        /**
         * 输出组建自身的名称
         */
        public void printStruct(String preStr);
    }

    // 树枝构件角色类
    public static class Composite implements Component {
        /**
         * 用来存储组合对象中包含的子组件对象
         */
        private List<Component> childComponents = new ArrayList<Component>();
        /**
         * 组合对象的名字
         */
        private String name;

        /**
         * 构造方法，传入组合对象的名字
         *
         * @param name 组合对象的名字
         */
        public Composite(String name) {
            this.name = name;
        }

        /**
         * 聚集管理方法，增加一个子构件对象
         *
         * @param child 子构件对象
         */
        public void addChild(Component child) {
            childComponents.add(child);
        }

        /**
         * 聚集管理方法，删除一个子构件对象
         *
         * @param index 子构件对象的下标
         */
        public void removeChild(int index) {
            childComponents.remove(index);
        }

        /**
         * 聚集管理方法，返回所有子构件对象
         */
        public List<Component> getChild() {
            return childComponents;
        }

        /**
         * 输出对象的自身结构
         *
         * @param preStr 前缀，主要是按照层级拼接空格，实现向后缩进
         */
        @Override
        public void printStruct(String preStr) {
            // 先把自己输出
            System.out.println(preStr + "+" + this.name);
            //如果还包含有子组件，那么就输出这些子组件对象
            if (this.childComponents != null) {
                //添加两个空格，表示向后缩进两个空格
                preStr += "  ";
                //输出当前对象的子对象
                for (Component c : childComponents) {
                    //递归输出每个子对象
                    c.printStruct(preStr);
                }
            }

        }

    }

    // 树叶构件角色类
    public static class Leaf implements Component {
        /**
         * 叶子对象的名字
         */
        private String name;

        /**
         * 构造方法，传入叶子对象的名称
         *
         * @param name 叶子对象的名字
         */
        public Leaf(String name) {
            this.name = name;
        }

        /**
         * 输出叶子对象的结构，叶子对象没有子对象，也就是输出叶子对象的名字
         *
         * @param preStr 前缀，主要是按照层级拼接的空格，实现向后缩进
         */
        @Override
        public void printStruct(String preStr) {
            // TODO Auto-generated method stub
            System.out.println(preStr + "-" + name);
        }

    }

    // 客户端类
    public static class Client {
        public static void main(String[] args) {
            Composite root = new Composite("服装");
            Composite c1 = new Composite("男装");
            Composite c2 = new Composite("女装");

            Leaf leaf1 = new Leaf("衬衫");
            Leaf leaf2 = new Leaf("夹克");
            Leaf leaf3 = new Leaf("裙子");
            Leaf leaf4 = new Leaf("套装");

            root.addChild(c1);
            root.addChild(c2);
            c1.addChild(leaf1);
            c1.addChild(leaf2);
            c2.addChild(leaf3);
            c2.addChild(leaf4);

            root.printStruct("");
        }
    }

}
