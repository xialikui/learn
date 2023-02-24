package com.example.modules.behaviorModel.policyPattern;

public class PolicyPattern {
    public static abstract class penguin {
        public void eating() {
            System.out.println("吃饭");
        }

        public void sleeping() {
            System.out.println("睡觉");
        }

        public abstract void beating();
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

    public static class behaviorContext {
        private penguin _penguin;

        public behaviorContext(penguin newPenguin) {
            _penguin = newPenguin;
        }

        public void setPenguin(penguin newPenguin) {
            _penguin = newPenguin;
        }

        public void everyDay() {
            _penguin.eating();
            _penguin.sleeping();
            _penguin.beating();
        }
    }

    public static void main(String[] args) {
        behaviorContext behavior = new behaviorContext(new littlePenguin());
        behavior.everyDay();

        behavior.setPenguin(new middlePenguin());
        behavior.everyDay();

        behavior.setPenguin(new bigPenguin());
        behavior.everyDay();
    }
}
