package com.example.modules.structuralModel.doorModel;

/**
 * 门面模式
 * 门面模式（Facade Pattern）:也叫外观模式，要求一个子系统的外部与其内部的通信必须通过一个统一的对象进行。 门面模式提供一个高层次的接口，使得子系统更易于使用。
 * 适用场景：
 * 多个子系统类相互之间存在强依赖的关系，通过门面类统一处理复杂关系的调用，外部类调用时，可以调用门面类，无需再调用子系统类。
 * 门面模式相对来说比较简单，代码实现如下：
 * <p>
 * <p>
 * <p>
 * 我们可以看到，通过最后的control类，我们可以在其中控制系统A、B、C之间相互调用的复杂场景，而在外部的类去调用A、B、C之间实现业务的时候，可以直接调用门面类去实现，无需在A、B、C中定义复杂代码。
 */
public class DoorModel {

    /**
     * @Version 1.0
     * 模拟系统类A
     * 假设A系统类中有方法doA
     **/
    public class SystemA {
        public String doA() {
            return "A";
        }
    }


    /**
     * @Version 1.0
     * 模拟系统类B
     * 假设A系统类中有方法doB
     **/
    public class SystemB {
        public String doB() {
            return "B";
        }
    }


    /**
     * @Version 1.0
     * 模拟系统类C
     * 假设A系统类中有方法doC
     **/
    public class SystemC {
        public String doC() {
            return "C";
        }
    }


    /**
     * @Version 1.0
     * 门面模式中的门面类 可以通过此类实现系统A、B、C之间的复杂关系调用
     **/
    public class ControlClass {

        //私有化三个系统的类
        private SystemA systemA = new SystemA();
        private SystemB systemB = new SystemB();
        private SystemC systemC = new SystemC();

        //通过此方法实现A、B、C之间的复杂调用
        public void doSomething() {
            //自定义逻辑
            systemA.doA();
            //自定义逻辑
            systemB.doB();
            //自定义逻辑
            systemC.doC();
        }

    }

}
