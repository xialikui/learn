package com.example.modules.behaviorModel.templateMethodPattern;

import java.util.HashMap;
import java.util.Map;

public class TemplateAndFactory {
    public static abstract class penguin {
        public void eating() {
            System.out.println("吃饭");
        }

        public void sleeping() {
            System.out.println("睡觉");
        }

        public abstract void beating();

        public void everyDay() {
            this.eating();
            this.sleeping();
            this.beating();
        }
    }

    public static class littlePenguin extends penguin {
        @Override
        public void beating() {
            System.out.println("用小翅膀打豆豆");
        }
    }

    public static class middlePenguin extends penguin {
        @Override
        public void beating() {
            System.out.println("用圆圆的肚子打豆豆");
        }
    }

    public static class bigPenguin extends penguin {
        @Override
        public void beating() {
            System.out.println("拿鸡毛掸子打豆豆");
        }
    }

    public static class penguinFactory {
        private static final Map<String, penguin> map = new HashMap<>();

        static {
            map.put("littlePenguin", new littlePenguin());
            map.put("middlePenguin", new middlePenguin());
            map.put("bigPenguin", new bigPenguin());
        }

        // 获取企鹅
        public static penguin getPenguin(String name) {
            return map.get(name);
        }
    }

    public static void main(String[] args) {
        penguin penguin_1 = penguinFactory.getPenguin("littlePenguin");
        penguin_1.everyDay();
        penguin penguin_2 = penguinFactory.getPenguin("middlePenguin");
        penguin_2.everyDay();
        penguin penguin_3 = penguinFactory.getPenguin("bigPenguin");
        penguin_3.everyDay();
    }

}
