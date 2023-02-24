package com.example.modules.behaviorModel.templateMethodPattern;

/**
 * 模板方法模式
 * 定义一个操作中的算法的框架，而将一些步骤延迟到子类中。使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。
 * <p>
 * 应用场景：
 * 例如生成PDF模板，有固定的logo位置、固定的表格排版，不固定的就是数据的解析，可以将logo的位置、基础样式放置在父类，将数据的解析放置在子类。
 * <p>
 * 代码结构如下：
 * 定义抽象父类，在其中定义属性与方法，把不变的格式封装起来。
 * 定义子类，继承父类，重写某些方法，加入自己的逻辑，调用父类的总方法，完成处理。
 * 定义测试类，通过子类调用父类的方法完成逻辑
 * <p>
 * 通过模板方法模式，可以把认为是不变部分的算法封装到父类实现，而可变部分的则可以通过继承来继续扩展，行为由父类控制，子类实现。基本方法是由子类实现的，因此子类可以通过扩展的方式增加相应的功能，符合开闭原则。
 */
public class Template {

    /**
     * 定义模板类：
     *
     * @Version 1.0
     * 模板类
     **/
    public abstract class TempleteClass {
        //基本方法
        protected abstract void doA();

        protected abstract void doB();

        //模板方法
        public void templeteMethod() {
            doA();
            if (isDoAnything()) {
                doB();
            }
        }

        //返回布尔值
        public boolean isDoAnything() {
            return true;
        }

    }


    /**
     * @{NAME}
     * @Description TODO
     * @Author luocong
     * 定义子类的方法实现：
     * @Version 1.0
     * 子类
     **/
    public class TempleteChild extends TempleteClass {

        @Override
        protected void doA() {
            System.out.println("进入了A方法");
        }

        @Override
        protected void doB() {
            System.out.println("进入了B方法");
        }

        @Override
        public boolean isDoAnything() {
            return true;
        }

        public void doAllthings() {
            super.templeteMethod();
        }
    }

}
