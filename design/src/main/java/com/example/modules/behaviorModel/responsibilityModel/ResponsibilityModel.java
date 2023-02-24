package com.example.modules.behaviorModel.responsibilityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 责任链设计模式是软件开发中常见的一种设计模式，在该模式中将一个请求进行递进处理，从而形成一条链，在链条的节点中处理请求，一个节点处理完自己的逻辑再传递到下一个节点中。
 * <p>
 * 应用场景：
 * 在应对网页端传输的报文信息时，会对报文信息进行处理，有一些类负责处理消息中的特殊字符，有一些类负责处理处理消息中的敏感词之类的信息，这种情况适用于责任链模式。
 * <p>
 * 代码结构如下：
 * 定义过滤的父类
 * 定义两个子类继承父类，重写过滤方法
 * 定义责任链类，将两个子类传递进去，调用子类的过滤方法。
 * 通过测试类将子类传递，调用过滤方法。
 * <p>
 * <p>
 * 我们可以看到，所谓的责任链模式，说白了就是利用了多态的特性，将对象封装成集合，在调用方法时循环调用对象的方法，利用循环进行调用，责任链模式的典型应用场景就是spring的filter类，有兴趣的可以看下源码。
 */
public class ResponsibilityModel {

    /**
     * @return 定义处理消息的接口
     **/
    public interface FatherFilter {
        //定义处理消息的方法
        public String doFilter(String msg);
    }

    /**
     * @Version 1.0
     * 定义过滤特殊字符的类，实现处理消息的接口：
     * 处理http消息的类
     * 过滤http消息
     **/
    public static class HttpFilter implements FatherFilter {

        @Override
        public String doFilter(String msg) {
            String replace = msg.replace("-", "");
            System.out.println("HttpFilter replace msg: " + replace);
            return replace;
        }
    }

    /**
     * 定义处理敏感词的类，实现处理消息的接口：
     *
     * @Version 1.0
     * 处理消息的类
     * 过滤消息
     **/
    public static class MsgFilter implements FatherFilter {

        @Override
        public String doFilter(String msg) {
            String replace = msg.replace("*", "");
            System.out.println("MsgFilter replace msg: " + replace);
            return replace;
        }
    }


    /**
     * 定义责任链基类，定义集合，集合中存放泛型为消息接口的对象 ，调用责任链类的时候循环集合，调用对象点过滤方法：
     *
     * @Version 1.0
     * 责任链基类
     **/
    public static class FatherFilterChain {

        //储存http和msg的filter
        List<FatherFilter> list = new ArrayList<FatherFilter>();

        //添加filter
        public void add(FatherFilter fatherFilter) {
            list.add(fatherFilter);
        }

        //定义执行过滤的方法
        public void doFilter(String msg) {
            for (int i = 0; i < list.size(); i++) {
                msg = list.get(i).doFilter(msg);

            }
        }


    }


    /**
     * 定义消息类，存放消息：
     *
     * @Version 1.0
     * 模拟传递消息的类
     **/
    public static class Msg {
        private String msg;

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }
    }


    /**
     * @Version 1.0
     * 测试类
     **/
    public static class TestResponsibility {

        public static void main(String[] args) {
            //定义消息类
            Msg msg = new Msg();
            msg.setMsg("你好，------,我是***琪琪.....");
            //new http处理对象和msg处理对象
            HttpFilter httpFilter = new HttpFilter();
            MsgFilter msgFilter = new MsgFilter();
            //new 责任链处理对象
            FatherFilterChain fatherFilterChain = new FatherFilterChain();
            //将http处理对象和msg处理对象加入责任链
            fatherFilterChain.add(httpFilter);
            fatherFilterChain.add(msgFilter);
            //传递消息 执行过滤
            fatherFilterChain.doFilter(msg.getMsg());

        }
    }

}
