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
public class ShallowCopy {

    /**
     * 浅拷贝
     *
     * @Version 1.0
     * 猴王类 定义悟空的属性 重写克隆方法
     **/
    public class KingMonkey implements Cloneable {
        private String name = "孙悟空";
        private int age = 5000;
        private String skill = "七十二变";

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getSkill() {
            return skill;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public void setSkill(String skill) {
            this.skill = skill;
        }

        public KingMonkey(String name, int age, String skill) {
            this.name = name;
            this.age = age;
            this.skill = skill;
        }

        public KingMonkey() {
        }

        @Override
        public String toString() {
            return "KingMonkey{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", skill='" + skill + '\'' +
                    '}';
        }

        //重写clone方法
        @Override
        protected KingMonkey clone() throws CloneNotSupportedException {
            return (KingMonkey) super.clone();
        }
    }

}
