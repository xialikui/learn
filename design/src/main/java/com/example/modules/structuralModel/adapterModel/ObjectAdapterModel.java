package com.example.modules.structuralModel.adapterModel;

/**
 * 适配器模式
 * 将一个类的接口转换成客户希望的另外一个接口。Adapter模式使得原本由于接口不兼容而不能一起工作的那些类可以在一起工作
 * 所谓适配器，就是将不同类型的接口利用某种方法组装在一起，例如我们生活中遇到的数据线转化器，能将不同类型的接口转换为我们需要的接口，适配器模式一般应用于新老项目并存的情况，能将新老项目中不同写法的代码转换。
 * <p>
 * 对象适配器模式
 * 注入对象，完成适配器转换，代码结构如下：
 * 定义接口TYPEC
 * 定义类TYPEA
 * 定义适配类，实现接口TYPEC
 * 填充TypeA的对象为属性，重写接口的方法
 * 在接口的方法中可以调用typeA的方法
 * <p>
 * <p>
 * 可以看到，由于注入了typA对象，在调用v5的方法时，本应该输出5V电压，现在我们可以输出220V电压，这就是对象的适配器模式。
 */
public class ObjectAdapterModel {


    /**
     * 定义typeC接口
     **/
    public interface TypeCInterface {

        //输出5V的电压
        public void v5();
    }


    /**
     * 定义typeA类：
     *
     * @Version 1.0
     **/
    public static class TypeA {
        public void v220() {
            System.out.println("输出220V电压");
        }
    }


    /**
     * 定义适配器类，实现typeC接口，注入TypeA对象：
     *
     * @Version 1.0
     * 定义适配器类 实现typeC接口
     **/
    public static class PowerAdapter implements TypeCInterface {

        public TypeA typeA = new TypeA();

        public PowerAdapter(TypeA typeA) {
            this.typeA = typeA;
        }

        @Override
        public void v5() {
            typeA.v220();
        }
    }


    /**
     * @Version 1.0
     * 测试类：
     **/
    public static class TestAdapter {
        public static void main(String[] args) {
            PowerAdapter powerAdapter = new PowerAdapter(new TypeA());
            powerAdapter.v5();
        }
    }

}
