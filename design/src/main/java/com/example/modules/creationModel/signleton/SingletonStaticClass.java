package com.example.modules.creationModel.signleton;

/**
 * 结构如下：
 * 私有的无参构造器
 * 私有的静态的内部类
 * 在内部类中定义私有的最终的静态的对象，new一个
 * 定义公共的实例方法，返回内部类.对象
 * <p>
 * <p>
 * 单例模式
 * 通过静态内部类实现懒加载与线程安全
 * 利用JVN特性实现 JVM在加载类和内部类的时候 只会在运行的时候加载一次 从而保证线程安全和懒加载
 *
 *
 * 结果
 * 在执行验证代码时，我们可以看到，通过此写法保障了线程安全和效率，这个写法的原理类似于饿汉式，利用JVN特性实现 JVM在加载类和内部类的时候 只会在运行的时候加载一次 从而保证线程安全和懒加载。
 */
public class SingletonStaticClass {

    //1. 私有的无参构造器
    private SingletonStaticClass() {
    }

    //2. 私有的静态的内部类
    private static class SingletonStatic {
        //3. 在私有的内部类中定义私有的 最终的 静态的对象
        private final static SingletonStaticClass singletonStaticClass = new SingletonStaticClass();
    }

    //4. 公共的静态的实例方法
    public static SingletonStaticClass getInstance() {
        return SingletonStatic.singletonStaticClass;
    }

    //测试方法
    public static void main(String[] args) {
        //利用for循环 模拟多线程环境调用
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                //看每次获取对象的hashcode是否一致 判断是否获取了同一个对象
                System.out.println("获取的hashCode是： " + SingletonStaticClass.getInstance().hashCode());
            }).start();
        }
    }


}
