package com.example.modules.behaviorModel.commandModel;

/**
 * 一、前言
 * 命令模式应用的场景比较广泛，智能家居控制，操作比较复杂的命令，新建一个菜单，系统点击按钮都是发出命令，系统接收命令会执行命令进行反馈，点击查询，后台接收请求返回请求数据等等。
 * <p>
 * 二、命令模式
 * 1.命令模式是什么？
 * 将一个请求封装成一个对象，从而使您可以用不同的请求对客户进行参数化。
 * <p>
 * 2.命令模式解决什么问题？
 * 主要解决:行为请求者与行为实现者通常是一种紧耦合的关系
 * <p>
 * 3.命令模式怎么使用？
 * 关键定义三个角色：
 * 1、received 真正的命令执行对象。
 * 2、Command 命令，需要我执行的所有命令都这里声明。
 * 3、invoker 调用者，接收到命令，并执行命令。
 * <p>
 * 命令模式在spring中jdbcTemplate中有使用，execute方法，接收sql命令进行执行语句。
 */
public class CommandModel {

    //1.定义命令抽象类


    /**
     * 命令接口
     */
    public abstract static class Command {
        private String sql;

        public Command(String sql) {
            this.sql = sql;
        }

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public abstract void execute(String sql);
    }

    //2. 具体的命令类
    //查询命令：


    public static class QueryCommand extends Command {
        public QueryCommand(String sql) {
            super(sql);
        }

        @Override
        public void execute(String sql) {
            System.out.println("执行查询命令sql=>" + sql);
        }
    }


    //新增命令：

    public static class InsertCommand extends Command {

        public InsertCommand(String sql) {
            super(sql);
        }

        @Override
        public void execute(String sql) {
            System.out.println("执行插入命令sql=>" + sql);
        }
    }

    //修改命令：

    public static class UpdateCommand extends Command {
        public UpdateCommand(String sql) {
            super(sql);
        }

        @Override
        public void execute(String sql) {
            System.out.println("执行更新命令sql=>" + sql);
        }
    }

    //删除命令：

    public static class DeleteCommand extends Command {

        public DeleteCommand(String sql) {
            super(sql);
        }

        @Override
        public void execute(String sql) {
            System.out.println("执行删除命令sql=>" + sql);
        }
    }


    //3.定义命令调用者
    public static class Invoker {
        //什么命令
        private Command command;

        //客户发出命令
        public void setCommand(Command command) {
            this.command = command;
        }

        //执行客户的命令
        public void executeSql() {
            this.command.execute(command.getSql());
        }
    }

    //4.测试
    public static class CommandPatternDemo {
        public static void main(String[] args) {
            Invoker invoker = new Invoker();
            Command query = new QueryCommand("select * from table where condition");
            //设置命令
            invoker.setCommand(query);
            //执行命令
            invoker.executeSql();

            Command insert = new InsertCommand("insert into table() values()");
            //设置命令
            invoker.setCommand(insert);
            //执行命令
            invoker.executeSql();

            Command update = new UpdateCommand("update table set filed = value where condition ");
            //设置命令
            invoker.setCommand(update);
            //执行命令
            invoker.executeSql();
            Command delete = new DeleteCommand("delete from table where condition ");
            //设置命令
            invoker.setCommand(delete);
            //执行命令
            invoker.executeSql();
        }
    }

}
