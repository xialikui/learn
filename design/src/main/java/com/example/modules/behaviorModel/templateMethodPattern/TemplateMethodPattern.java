package com.example.modules.behaviorModel.templateMethodPattern;

public class TemplateMethodPattern {

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

    public static void main(String[] args) {
        System.out.println("littlePenguin:");
        littlePenguin penguin1 = new littlePenguin();
        penguin1.everyDay();
        System.out.println("middlePenguin:");
        middlePenguin penguin2 = new middlePenguin();
        penguin2.everyDay();
        System.out.println("bigPenguin:");
        bigPenguin penguin3 = new bigPenguin();
        penguin3.everyDay();
    }
}
