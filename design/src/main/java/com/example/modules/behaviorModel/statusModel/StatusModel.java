package com.example.modules.behaviorModel.statusModel;

/**
 * 状态模式
 * 定义对象间的一种一对多的依赖关系,当一个对象的状态发生改变时,所有依赖于它的对象都得到通知并被自动更新。允许一个对象在其内部状态改变时改变它的行为。对象看起来似乎修改了它的类
 * <p>
 * uml
 * Context：上下文环境，定义客户感兴趣的接口，维护一个State子类的实例，该实例定义了对象的当前状态
 * State：抽象状态，定义一个接口以封装与 Context 的一个特定状态相关的行为。
 * ConcreteState：具体状态，实现抽象状态中定义的接口，从而达到不同状态下的不同行为。
 * <p>
 * 使用场景
 * 1.对象的行为依赖于它的状态(属性)并且可以根据它的状态改变而改变它的相关行为；
 * 2.代码中包含大量与对象状态有关的条件语句
 * <p>
 * 优缺点
 * 优点
 * 1.简化应用逻辑控制，减少依赖于状态的if-else
 * 2.更好的分离状态和行为
 * 3.更好的扩展性，扩展新的状态只需增加实现类，在需要维护的地方设置新状态即可
 * 4.显式化进行状态转换
 * 缺点
 * 1.类文件增加
 * 2.逻辑分散，无法再一个地方就看出整个状态机的逻辑
 */
public class StatusModel {
    /*
     * 抽象文件状态类
     * */
    public interface State {
        void fileState();
    }
    public static class NotDealState implements State {
        @Override
        public void fileState() {
            System.out.println("文件状态未处理状态");
        }
    }
    public static class DealingState implements State {
        @Override
        public void fileState() {
            System.out.println("文件正在处理状态");
        }
    }
    public static class HasDealState implements State {
        @Override
        public void fileState() {
            System.out.println("文件已经处理状态");
        }
    }
    public static class FileContext {
        //默认情况下文件是未处理状态
        private State fileState = new NotDealState();

        /*
         * 文件状态切换为未处理状态
         * */
        public void notDeal(){
            this.fileState = new NotDealState();
        }

        /*
         * 文件状态切换为正在处理状态
         * */
        public void Dealing(){
            this.fileState = new DealingState();
        }

        /*
         * 文件状态切换为已经处理状态
         * */
        public void HasDeal(){
            this.fileState = new HasDealState();
        }
        /*
         * 获取当前文件的状态
         * */
        public void getFileState(){
            fileState.fileState();
        }
    }
    public static class Client {
        public static void main(String[] args) {
            FileContext fileContext = new FileContext();
            fileContext.getFileState();
            //切换为正在处理状态
            fileContext.Dealing();
            fileContext.getFileState();
            //切换为已经处理状态
            fileContext.HasDeal();
            fileContext.getFileState();
        }
    }
}
