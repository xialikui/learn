package com.example.modules.creationModel.builderModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 1.1定义
 * 维基百科定义
 * 生成器模式（英：Builder Pattern）是一种设计模式，又名：建造模式，是一种对象构建模式。
 * 它可以将复杂对象的建造过程抽象出来（抽象类别），使这个抽象过程的不同实现方法可以构造出不同表现（属性）的对象。
 * <p>
 * 简单理解
 * 将一个复杂对象的构建与它的表示分离,使得同样的构建过程,可以创建不同的表示.
 * 用户只需指定需要建造的类型就可以得到它们,建造过程及细节不需要知道。
 * <p>
 * 通用流程
 * 先创建一个初始对象，然后逐渐添加新东西，最后调用 build() 方法完成构建。
 * <p>
 * 1.2适用场景
 * 相同的方法，不同的执行顺序，产生不同的事件结果。
 * 对各部件或零件，都可以配到一个对象中，但是产生的运行结果又不相同时。
 * 产品类非常复杂，或者产品类中的调用顺序不同产生了不同的结果，这个时候使用创建者模式非常合适。
 * 当初始化一个对象特别复杂，如参数多，且很多参数都有默认值时（一个类有多个构造函数的时候，可以考虑使用建造者模式）
 * 需要生成的产品对象有复杂的内部结构，这些产品对象具备共性；
 * 隔离复杂对象的创建和使用，并使得相同的创建过程可以创建不同的产品。
 * 需要生成的对象内部属性本身相互依赖
 * <p>
 * 1.3 优缺点
 * 优点
 * 封装性好,创建和使用分离
 * 扩展性好、建造类之间独立、一定程度上解耦
 * 缺点
 * 产生多余的Builder对象
 * 产品内部发生变化,建造者都要修改，成本较大,所以需要精心设计
 * <p>
 * 建造者模式包含如下角色：
 * builder（抽象接口） : 为创建一个产品对象的各个部件指定抽象接口。
 * ConcreteBuilder（抽象接口的具体实现） : 实现Builder的接口以构造和装配该产品的各个部件，定义并明确它所创建的表示，并提供一个检索产品的接口。
 * Director（接口的构造者和使用者） : 构造一个使用Builder接口的对象。
 * Product（被构造的复杂对象） : ConcreteBuilder创建该产品的内部表示并定义它的装配过程，包含定义组成部件的类，包括将这些部件装配成最终产品的接口
 */
public class BuilderModel {
    /**
     * @Author charles.yao
     * @Description 户信息（product 产品角色）
     * @Date 2023/2/10 16:22
     */

    //@lombok.Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class UserInfo {
        private String id;
        private String name;
        private String age;
        private String phone;
    }


    /**
     * @Author
     * @Description 用户信息构建抽象类(builder)
     * @Date 2023/2/10 16:25
     */
    public abstract static class UserBuilder {
        public abstract void buildId(String id);

        public abstract void buildName(String name);

        public abstract void buildAge(String age);

        public abstract void buildPhone(String phone);

        public abstract UserInfo makeUserInfo();

    }


    /**
     * @Author
     * @Description 学生信息构建器（抽象接口的具体实现）
     * @Date 2023/2/10 16:29
     */
    public static class StudentUserInfoBuilder extends UserBuilder {
        private UserInfo userInfo = new UserInfo();

        public void setUserInfo(UserInfo userInfo) {
            this.userInfo = userInfo;
        }

        @Override
        public void buildId(String id) {
            userInfo.setId(id);
        }

        @Override
        public void buildName(String name) {
            userInfo.setName(name);
        }

        @Override
        public void buildAge(String age) {
            userInfo.setAge(age);
        }

        @Override
        public void buildPhone(String phone) {
            userInfo.setPhone(phone);
        }

        @Override
        public UserInfo makeUserInfo() {
            return userInfo;
        }
    }


    /**
     * @Author charles.yao
     * @Description 指导建造者建造
     * @Date 2023/2/10 16:35
     */
    public static class UserInfoDirector {
        private StudentUserInfoBuilder studentUserInfoBuilder;

        public void setStudentUserInfoBuilder(StudentUserInfoBuilder studentUserInfoBuilder) {
            this.studentUserInfoBuilder = studentUserInfoBuilder;
        }

        public UserInfo makeUserInfo(String id, String name, String age, String phone) {
            studentUserInfoBuilder.buildAge(age);
            studentUserInfoBuilder.buildId(id);
            studentUserInfoBuilder.buildName(name);
            studentUserInfoBuilder.buildPhone(phone);
            return studentUserInfoBuilder.makeUserInfo();
        }
    }



    /**
     * @Author charles.yao
     * @Description 生成器测试类
     * @Date 2023/2/10 16:46
     */
    public static class BuilderTest {
        public static void main(String[] args) {
            //userInfo 具体创建者
            StudentUserInfoBuilder studentUserInfoBuilder = new StudentUserInfoBuilder();
            //指导创建者
            UserInfoDirector userInfoDirector = new UserInfoDirector();
            userInfoDirector.setStudentUserInfoBuilder(studentUserInfoBuilder);
            //指导创建者生成userInfo
            UserInfo userInfo = userInfoDirector.makeUserInfo("1", "二狗", "20", "110");
            System.out.println("userInfo" + userInfo.toString());
            //===========================

        }
    }


}
