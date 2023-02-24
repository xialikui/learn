package com.example.modules.behaviorModel.emptyModel;

/**
 * 空对象模式
 * 在空对象模式(Null Object Pattern)中，一个空对象取代NULL对象实例的检查。Null对象不是检查空值，而是反应一个不做任何动作的关系。这样的Null对象也可以在数据不可用的时候提供默认的行为。
 * 在空对象模式中，我们创建一个指定各种要执行的操作的抽象类和扩展该类的实体类，还创建一个未对该类做任何实现的空对象类，该空对象类将无缝地使用在需要检查控制的地方。
 * <p>
 * 实现：
 * 我们将创建一个定义操作的AbstractCustomer抽象类，和扩展了AbstractCustomer类的实体类。工厂类CustomerFactory基于客户传递的名字来返回RealCustomer或NullCustomer对象。NullPatternDemo使用CustomerFactory来演示空对象模式的用法。
 */
public class EmptyModel {
    //创建一个抽象类：
    public abstract static class AbstractCustomer {
        protected String name;
        public abstract boolean isNil();
        public abstract String getName();
    }
    //扩展抽象类的实体类：

    public static class RealCustomer extends AbstractCustomer{
        public RealCustomer(String name){
            this.name = name;
        }
        @Override
        public boolean isNil() {
            return false;
        }
        @Override
        public String getName() {
            return name;
        }
    }
    public static class NullCustomer extends AbstractCustomer{
        @Override
        public boolean isNil() {
            return true;
        }
        @Override
        public String getName() {
            return "not available in customer database";
        }
    }
    //创建工厂类：

    public static class CustomerFactory {
        public static final String[] names = {"Rob","Joe","Julie"};
        public static AbstractCustomer getCustomer(String name){
            for (int i=0;i<names.length;i++){
                if (names[i].equalsIgnoreCase(name)){
                    return new RealCustomer(name);
                }
            }
            return new NullCustomer();
        }
    }
   //基于客户传递的名字，来获取RealCustomer或NullCustomer对象：

    public static class NullPatternDemo {
        public static void main(String[] args){
            AbstractCustomer customer1 = CustomerFactory.getCustomer("rob");
            AbstractCustomer customer2 = CustomerFactory.getCustomer("bob");
            AbstractCustomer customer3 = CustomerFactory.getCustomer("julie");
            AbstractCustomer customer4 = CustomerFactory.getCustomer("tom");
            //客户名称
            System.out.println("customers");
            System.out.println(customer1.getName());
            System.out.println(customer2.getName());
            System.out.println(customer3.getName());
            System.out.println(customer4.getName());
        }
    }
}
