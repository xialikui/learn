package com.example.modules.structuralModel.compositeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 与安全式的合成模式不同的是，透明式的合成模式要求所有的具体构件类，不论树枝构件还是树叶构件，均符合一个固定接口。
 * 可以看出，客户端无需再区分操作的是树枝对象(Composite)还是树叶对象(Leaf)了；对于客户端而言，操作的都是Component对象。
 * <p>
 * 两种实现方法的选择
 * 这里所说的安全性合成模式是指：从客户端使用合成模式上看是否更安全，如果是安全的，那么就不会有发生误操作的可能，能访问的方法都是被支持的。
 * 这里所说的透明性合成模式是指：从客户端使用合成模式上，是否需要区分到底是“树枝对象”还是“树叶对象”。如果是透明的，那就不用区分，对于客户而言，都是Compoent对象，具体的类型对于客户端而言是透明的，是无须关心的。
 * 对于合成模式而言，在安全性和透明性上，会更看重透明性，毕竟合成模式的目的是：让客户端不再区分操作的是树枝对象还是树叶对象，而是以一个统一的方式来操作。
 * 而且对于安全性的实现，需要区分是树枝对象还是树叶对象。有时候，需要将对象进行类型转换，却发现类型信息丢失了，只好强行转换，这种类型转换必然是不够安全的。
 * 因此在使用合成模式的时候，建议多采用透明性的实现方式。
 */
public class TransparencyComposite {

    // 抽象构件角色类
    public abstract static class Component {
        /**
         * 输出组建自身的名称
         */
        public abstract void printStruct(String preStr);

        /**
         * 聚集管理方法，增加一个子构件对象
         *
         * @param child 子构件对象
         */
        public void addChild(Component child) {
            /**
             * 缺省实现，抛出异常，因为叶子对象没有此功能
             * 或者子组件没有实现这个功能
             */
            throw new UnsupportedOperationException("对象不支持此功能");
        }

        /**
         * 聚集管理方法，删除一个子构件对象
         *
         * @param index 子构件对象的下标
         */
        public void removeChild(int index) {
            /**
             * 缺省实现，抛出异常，因为叶子对象没有此功能
             * 或者子组件没有实现这个功能
             */
            throw new UnsupportedOperationException("对象不支持此功能");
        }

        /**
         * 聚集管理方法，返回所有子构件对象
         */
        public List<Component> getChild() {
            /**
             * 缺省实现，抛出异常，因为叶子对象没有此功能
             * 或者子组件没有实现这个功能
             */
            throw new UnsupportedOperationException("对象不支持此功能");
        }
    }
    //树枝构件角色类，此类将implements Conponent改为extends Conponent，其他地方无变化。

    public static class Composite extends Component {
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

    //树叶构件角色类，此类将implements Conponent改为extends Conponent，其他地方无变化。

    public static class Leaf extends Component {
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

    //客户端类的主要变化是不再区分Composite对象和Leaf对象。

    public static class Client {
        public static void main(String[] args) {
            Component root = new Composite("服装");
            Component c1 = new Composite("男装");
            Component c2 = new Composite("女装");

            Component leaf1 = new Leaf("衬衫");
            Component leaf2 = new Leaf("夹克");
            Component leaf3 = new Leaf("裙子");
            Component leaf4 = new Leaf("套装");

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
