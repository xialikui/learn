package com.example.modules.structuralModel.poxyModel;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理模式
 * 动态代理就是JDK帮我们实现的，其原理是基于类的反射，动态的为我们生成一个代理类，帮我们执行方法，在执行方法的前后位置我们可以定义一些自定义的方法，下面我们来看代码结构：
 * <p>
 * 定义汽车接口
 * 定义汽车接口的实现类，实现汽车接口。
 * 定义代理类，注入接口类型的对象，调用Proxy的newProxyInstence方法，传入对象.getclass.getclassloader，传入对象.getClass.getInterfaces，传入内部类InvocationHandler，在内部类中实现调用
 * <p>
 * 执行结果和静态代理的结果一致，但是我们可以通过代码了解，唯一的不同就是动态代理无需我们自定义代理类，二是通过java的反射机制为我们动态的生成了一个代理类去执行我们定义的方法。
 */
public class MovePoxy {
    /**
     * 定义汽车接口
     **/
    public interface CarInterface {
        public void run();
    }


    /**
     * 定义汽车类，实现接口：
     *
     * @Version 1.0
     * 汽车类实现了汽车接口 重写run方法
     **/
    public static class Car implements CarInterface {

        @Override
        public void run() {
            System.out.println("汽车跑起来了......");
        }
    }


    /**
     * 定义汽车类的代理类（也可以直接在测试类中实现，作用主要就是封装代理过程）：
     *
     * @Version 1.0
     * 定义汽车的动态代理类
     **/
    public static class CarMoveProxy {


        //利用JDK的动态代理实现代理模式
        public void doRun(Car car, InvocationHandler invocationHandler) {
            //第一个参数 对象.getClass.getClassLoder 第二个参数 对象.getCalss.getInterfaces 第三个参数 invocationHandler内部类
            CarInterface car1 = (CarInterface) Proxy.newProxyInstance(car.getClass().getClassLoader(),
                    car.getClass().getInterfaces(),
                    invocationHandler);
            car1.run();
        }
    }


    /**
     * @Version 1.0
     * 测试类
     **/
    public static class TestProxy {
        public static void main(String[] args) {
            CarMoveProxy carMoveProxy = new CarMoveProxy();
            Car car = new Car();
            carMoveProxy.doRun(car, new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    System.out.println("汽车启动...");
                    Object invoke = method.invoke(car, objects);
                    System.out.println("汽车关闭...");
                    return invoke;
                }
            });
        }
    }

}
