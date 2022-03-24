package myssm.trans;

import myssm.basedao.ConnUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    //开启事务
    public static void beginTrans() throws SQLException {
        System.out.println("开启事务");
        ConnUtil.getConn().setAutoCommit(false);
    }
    //提交事务
    public static void commitTrans() throws SQLException {
        System.out.println("提交事务");
        Connection conn = ConnUtil.getConn();
        conn.commit();
        ConnUtil.close();
    }
    //回滚
    public static void rollback() throws SQLException {
        System.out.println("回滚事务");
        Connection conn = ConnUtil.getConn();
        conn.rollback();
        ConnUtil.close();
    }
}
