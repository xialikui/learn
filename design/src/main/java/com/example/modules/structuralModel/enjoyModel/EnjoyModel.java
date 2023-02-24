package com.example.modules.structuralModel.enjoyModel;

import java.util.HashMap;

/**
 * 享元模式： 通过共享已经存在的对象来大幅度减少需要创建的对象数量、避免大量相似对象的开销，从而提高系统资源的利用率，将重复出现的内容作为共享部分取出，由多个对象共享一份，从而减轻内存的压力
 * <p>
 * 应用场景：
 * String的实现就是享元模式，底层有针对各种字符的常量池，有变量引用到常量时，就直接引用常量池中的常量，又例如数据库连接池，也是利用享元模式。
 * <p>
 * <p>
 * 可以看到，享元模式的主旨在于构建一个池子的概念，需要使用对象的时候就从池子中去拿，无需多次创建对象，在数据库连接池的实现中可以看到其身影。
 */
public class EnjoyModel {

    /**
     * @Version 1.0
     * 定义数据库连接资源基类 其他类需要继承此类
     **/
    public abstract static class DataSource {
        String dataId;
        String dataName;

        public String getDataId() {
            return dataId;
        }

        public String getDataName() {
            return dataName;
        }

        public void setDataId(String dataId) {
            this.dataId = dataId;
        }

        public void setDataName(String dataName) {
            this.dataName = dataName;
        }

        public DataSource(String dataId, String dataName) {
            this.dataId = dataId;
            this.dataName = dataName;
        }

        public abstract void method();

        @Override
        public String toString() {
            return "DataSource{" +
                    "dataId='" + dataId + '\'' +
                    ", dataName='" + dataName + '\'' +
                    '}';
        }
    }

    /**
     * 定义数据库对象生产者类
     *
     * @Version 1.0
     * 数据库连接生产者类1
     **/
    public static class DataSourceMaker extends DataSource {

        public DataSourceMaker(String dataId, String dataName) {
            super(dataId, dataName);
        }

        @Override
        public void method() {
            System.out.println("使用DataSourceMaker1生产数据库连接对象.....");
        }
    }


    /**
     * 定义工厂类，生产数据库连接对象
     *
     * @Version 1.0
     * 数据库连接工厂 生产数据库连接对象
     **/
    public static class DataSourceFactory {
        private static HashMap<String, DataSource> hashMap = new HashMap<>();

        public DataSourceFactory() {
            for (int i = 0; i < 10; i++) {
                DataSourceMaker dataSourceMaker = new DataSourceMaker(String.valueOf(i), "DataSource" + i);
                hashMap.put(dataSourceMaker.getDataId(), dataSourceMaker);
            }
        }


        public DataSource getDataSourceFactory(String datasourceName) {
            if (hashMap.containsKey(datasourceName)) {
                return hashMap.get(datasourceName);
            }
            return null;
        }

    }


    /**
     * 测试类
     *
     * @Version 1.0
     **/
    public static class TestDataSource {

        public static void main(String[] args) {
            //取出数据库连接对象
            DataSourceFactory dataSourceFactory = new DataSourceFactory();
            for (int i = 0; i < 10; i++) {
                System.out.println("i: " + dataSourceFactory.getDataSourceFactory(String.valueOf(i)).toString());
            }
        }
    }

}
