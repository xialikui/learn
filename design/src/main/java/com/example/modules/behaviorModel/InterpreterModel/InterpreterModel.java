package com.example.modules.behaviorModel.InterpreterModel;

import java.util.HashMap;
import java.util.Map;


/**
 * 解释器模式
 * 解释器模式属于行为型模式
 * 是按照一定的语义进行解释的方案,在日常编码中使用较少
 * 在java中expression4J spring的SpEL表达式
 * <p>
 * 代码实现
 * 用解释器模式实现对一个简单的加减法计算的解析
 * Context 解释器上下文
 * Expression 解释器接口
 * 非终结符表达式: 通常由其它表达式组成;
 * 终结符表达式: 一般为取值表达式,为递归树的叶子节点
 * <p>
 * 总结
 * 优势:
 * 扩展性极强也是最大的优势了
 * 劣势:
 * 类膨胀: 每个语法都需要一个实现类去对应解析,通常使用表达式都会比较复杂所以会产生很多实现类,为后期维护带来麻烦;
 * 实现使用了递归: 递归会造成调试异常复杂,不利于维护;
 * 效率问题: 循环和递归造成效率低下;
 */
public class InterpreterModel {
    interface Expression {
        public boolean evaluate(String context);
    }

    static class IsInExpression implements Expression {
        private String data;

        public IsInExpression(String data) {
            this.data = data;
        }

        @Override
        public boolean evaluate(String context) {
            if (context.contains(data)) {
                return true;
            }
            return false;
        }
    }

    static class OrExpression implements Expression {

        private Expression expr1 = null;
        private Expression expr2 = null;

        public OrExpression(Expression expr1, Expression expr2) {
            this.expr1 = expr1;
            this.expr2 = expr2;
        }

        @Override
        public boolean evaluate(String context) {
            return expr1.evaluate(context) || expr2.evaluate(context);
        }
    }

    static class AndExpression implements Expression {

        private Expression expr1 = null;
        private Expression expr2 = null;

        public AndExpression(Expression expr1, Expression expr2) {
            this.expr1 = expr1;
            this.expr2 = expr2;
        }

        @Override
        public boolean evaluate(String context) {
            return expr1.evaluate(context) && expr2.evaluate(context);
        }
    }

    public static class Main {

        public static void main(String[] args) {
            Expression select = new IsInExpression("Select");
            Expression from = new IsInExpression("From");
            Expression isSelectFrom = new AndExpression(select, from);

            Expression insert = new IsInExpression("Insert");
            Expression update = new IsInExpression("Update");
            Expression isInsertOrUpdate = new OrExpression(insert, update);

            System.out.println(isSelectFrom.evaluate("Select"));
            System.out.println(isInsertOrUpdate.evaluate("Insert"));

            System.out.println(isSelectFrom.evaluate("Select From"));
            System.out.println(isInsertOrUpdate.evaluate("Update"));
        }
    }


}
