package com.example.modules.behaviorModel.watcherModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式
 * 观察者模式（Observer），又叫发布-订阅模式（Publish/Subscribe），定义对象间一种一对多的依赖关系（注册），使得每当一个对象改变状态，
 * 则所有依赖于它的对象都会得到通知并自动更新（通知）。说白了就是个注册，通知的过程。
 * <p>
 * 代码结构如下：
 * 定义观察者接口和观察者方法。
 * 定义接口的实现类A和B
 * 定义主题类，将观察者传入一个集合内
 * 调用通知方法，循环观察者，传递参数给观察者做出响应。
 * 下面使用代码模拟股市在变动时， 游资和基金之间的动向，这种场景能形象生动的展示出观察者模式
 * <p>
 * <p>
 * 通过代码可以看到，观察者模式类似于责任链模式，所以观察者模式一般配合责任链模式使用。
 */
public class WatcherModel {
    /**
     * 定义股市观察者接口：
     *
     * @return 定义股票观察者父类
     **/
    public interface SharsObserver {

        //观察之后做出如何反映
        public void response(int i);
    }


    /**
     * 定义游资类，实现观察者接口：
     * @Version 1.0
     * 个人股民
     **/
    public static class PersonObserver implements SharsObserver {
        @Override
        public void response(int i) {
            if (i > 0) {
                System.out.println("游资： 涨了，快点投资投资.......");
            } else {
                System.out.println("游资： 跌了，快点撤资撤资.......");
            }
        }
    }


    /**
     * 定义基金类，实现观察者接口：
     * @Version 1.0
     **/
    public static class CompanyObserver implements SharsObserver {
        @Override
        public void response(int i) {
            if (i > 0) {
                System.out.println("机构： 涨了，再拉点投资吧.......");
            } else {
                System.out.println("机构： 跌了，稳一点，先不动.......");
            }

        }
    }


    /**
     * 定义主题类，将观察者的动态组装在一起：
     * @Version 1.0
     * 定义主题类型
     * 其中定义可以新增删除 通知观察者的方法
     **/
    public static class Subject {

        //储存游资和基金的对象
        List<SharsObserver> list = new ArrayList<SharsObserver>();

        //新增观察者
        public void addObserver(SharsObserver sharsObserver) {
            list.add(sharsObserver);
        }

        //通知观察者
        public void change(int j) {
            for (int i = 0; i < list.size(); i++) {
                SharsObserver sharsObserver = list.get(i);
                sharsObserver.response(j);
            }
        }
    }


    /**
     * @Version 1.0
     * 测试类
     **/
    public static class TestMain {

        public static void main(String[] args) {
            CompanyObserver companyObserver = new CompanyObserver();
            PersonObserver personObserver = new PersonObserver();
            Subject subject = new Subject();
            subject.addObserver(companyObserver);
            subject.addObserver(personObserver);
            subject.change(2);
            System.out.println("---------------------");
            subject.change(0);

        }
    }


}
