package com.example.modules.behaviorModel.mediatorModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 中介者模式Mediator
 * 介绍
 * 中介者模式（Mediator Pattern），用一个中介对象来封装一系列的对象交互。中介者使各个对象不需要显式地相互引用，从而使其耦合松散，而且可以独立地改变它们之间的交互。
 * 中介者模式属于行为型模式，使代码易于维护。
 * 比如 MVC 模式，C（Controller 控制器）是 M（Model 模型）和 V（View 视图）的中介者，在前后端交互时起到了中间人的作用。
 * 原理类图
 * Mediator 就是抽象中介者,定义了同事对象到中介者对象的接口
 * Colleague 是抽象同事类
 * ConcreteMediator 具体的中介者对象, 实现抽象方法, 他需要知道所有的具体的同事类,即以一个集合来管理
 * HashMap,并接受某个同事对象消息，完成相应的任务
 * ConcreteColleague 具体的同事类，会有很多, 每个同事只知道自己的行为，而不了解其他同事类的行为(方法)，但 是他们都依赖中介者对象
 * <p>
 * <p>
 * JDK中的案例
 * java.util.Timer类scheduleXXX（）方法
 * java并发执行器execute（）方法。
 * java.lang.reflect.Method invoke（）方法。
 * <p>
 * 注意事项
 * 多个类相互耦合，会形成网状结构, 使用中介者模式将网状结构分离为星型结构，进行解耦
 * 减少类间依赖，降低了耦合，符合迪米特原则
 * 中介者承担了较多的责任，一旦中介者出现了问题，整个系统就会受到影响
 * 如果设计不当，中介者对象本身变得过于复杂，这点在实际使用时，要特别注意
 */
public class MediatorModel {
    //首先，我们将创建Mediator接口，该接口将定义具体中介者接口
    public interface ChatMediator {

        public void sendMessage(String msg, User user);

        void addUser(User user);
    }

    //用户可以发送和接收消息，所以我们可以有用户界面或抽象类。我正在创建User作为如下抽象类。User.java
    public abstract static class User {
        protected ChatMediator mediator;
        protected String name;

        public User(ChatMediator med, String name){
            this.mediator=med;
            this.name=name;
        }

        public abstract void send(String msg);

        public abstract void receive(String msg);
    }

    //现在我们将创建一个具体的中介类，它将包含组中的用户列表，并为用户之间的通信提供逻辑：
    public static class ChatMediatorImpl implements ChatMediator {

        private List<User> users;

        public ChatMediatorImpl(){
            this.users=new ArrayList<>();
        }

        @Override
        public void addUser(User user){
            this.users.add(user);
        }

        @Override
        public void sendMessage(String msg, User user) {
            for(User u : this.users){
                //message should not be received by the user sending it
                if(u != user){
                    u.receive(msg);
                }
            }
        }

    }

    //现在我们可以创建客户端系统使用的具体User类：
    public static class UserImpl extends User {

        public UserImpl(ChatMediator med, String name) {
            super(med, name);
        }

        @Override
        public void send(String msg){
            System.out.println(this.name+": Sending Message="+msg);
            mediator.sendMessage(msg, this);
        }
        @Override
        public void receive(String msg) {
            System.out.println(this.name+": Received Message:"+msg);
        }

    }


    //让我们用一个简单的程序来测试我们的聊天应用程序，我们将在其中创建调解器并将用户添加到组中，其中一个用户将发送消息：
    public static class ChatClient {

        public static void main(String[] args) {
            ChatMediator mediator = new ChatMediatorImpl();
            User user1 = new UserImpl(mediator, "Pankaj");
            User user2 = new UserImpl(mediator, "Lisa");
            User user3 = new UserImpl(mediator, "Saurabh");
            User user4 = new UserImpl(mediator, "David");
            mediator.addUser(user1);
            mediator.addUser(user2);
            mediator.addUser(user3);
            mediator.addUser(user4);

            user1.send("Hi All");

        }

    }


}
