package com.example.modules.creationModel.prototypeModel;

/**
 * 原型模式
 * 原型模式(Prototype 模式)是指：用原型实例指定创建对象的种类，并且通过拷贝这些原型，创建新的对象，而拷贝又分为浅拷贝和深拷贝，
 * 所谓浅拷贝就是将一个对象拷贝成另外一个对象，但是对象中属性的指向是一样的，改变任意一个对象，两个对象的属性跟着变化。
 * 而深拷贝就是利用IO流将对象copy一份出来，在修改任意对象的属性时，另一对象的属性不会发生变化。
 * <p>
 * 适用场景：
 * 需要大量使用某个对象
 * <p>
 * 浅拷贝的实现结构如下：
 * 定义悟空类，定义属性、构造方法、tostring方法、实现cloneable接口
 * 调用super.clone方法，实现克隆对象
 * 此时修改对象2，对象1的属性跟着变化
 * <p>
 * 深拷贝的实现结构如下：
 * 定义悟空类，定义属性、构造方法、tostring方法、实现cloneable接口
 * 将当前对象写入流中，并通过流再次写出对象。
 * 此时修改对象2.对象1的属性不会跟着变化了。
 */

import java.io.*;

public class DeepCopy {


    /**
     * 深拷贝
     *
     * @Version 1.0
     * 新的猴王类 将param封装为自己的属性
     **/
    public class KingMonkey1 implements Cloneable, Serializable {
        private String name = "孙悟空";
        private int age = 5000;
        private String skill = "七十二变";

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setSkill(String skill) {
            this.skill = skill;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getSkill() {
            return skill;
        }

        @Override
        public String toString() {
            return "KingMonkey1{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", skill='" + skill + '\'' +
                    '}';
        }

        public KingMonkey1() {
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                //将对象写入字节流
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(this);
                //把字节流转化为对象
                ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                ObjectInputStream ois = new ObjectInputStream(bis);
                System.out.println("走进来了.....");
                KingMonkey1 kingMonkey1 = (KingMonkey1) ois.readObject();
                return kingMonkey1;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
