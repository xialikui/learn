package com.example.modules.structuralModel.adapterModel;

/**
 * 利用继承和实现完成适配器的转换，代码结构如下：
 * 定义接口A
 * 定义类B
 * 定义适配器类C 继承B 实现接口A
 * 在适配器类中既可以实现接口A的方法 也可以重写B类的方法
 * <p>
 * 可以看到，这两种实现模式最大的差别在于，一个是注入对象实现接口，一个是继承类实现接口，在使用过程中，一般推荐第一种方式，继承在某种程度上来说，会加重类之间的耦合度。
 */
public class ClassAdapterModel {


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
     * @Version 1.0
     * 定义类适配器 实现接口并继承类 在重写的方法中可以输出220v电压
     * 并且可以定义输出5v电压的逻辑
     **/
    public static class PowerAdapter extends TypeA implements TypeCInterface {


        public PowerAdapter(TypeA typeA) {
            super();
        }

        @Override
        public void v5() {
            this.v220();
        }

        @Override
        public void v220() {
            super.v220();
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
