package com.dodatabase;

/**
 * Created by Administrator on 2017/3/6.
 */
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

//import org.hibernate.hql.internal.ast.tree.IsNotNullLogicOperatorNode;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import static sun.misc.Version.print;

public class Mysql {

    private static volatile Mysql instance = null;
    public static final String url ;
    public static final String driver;
    public static final String user ;
//    public String db = "complaint";
    public static final String password ;
    public Connection connection = null;
    public PreparedStatement pstmt = null;
    public ResultSet resultSet = null;

    static{
        try {
            //1.获得字节码对象
            Class clazz = Mysql.class;

            //2.调用getResourceAsStream获取路径
            InputStream inputStream = clazz.getResourceAsStream("/mysql.properties");
            Properties pro = new Properties();
            pro.load(inputStream);

            //3.读取参数
            url=pro.getProperty("url");
            password=pro.getProperty("password");
            user=pro.getProperty("user");
            driver=pro.getProperty("driverClass");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("注册失败！" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public static Mysql getInstance() {
        if (instance == null) {
            synchronized (Mysql.class) {
                if (instance == null) {
                    instance = new Mysql();
                }
            }
        }
        return instance;
    }

    public Mysql() {
        try {
            // 指定连接类型
            Class.forName(driver);
            // 获取连接
            connection = (Connection) DriverManager.getConnection(url, user, password);
            System.out.println("Connect MySql SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int queryCount(String sql,List<Object> params) throws SQLException{
        int count = 0;
        int index = 1;
        pstmt = (PreparedStatement) connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        // 返回查询结果
        resultSet= pstmt.executeQuery();
        resultSet.last();
        count=resultSet.getRow();
        return count;
    }
    public Map<String, Object> queryOne(String sql, List<Object> params) throws SQLException {
        Map<String, Object> map = new HashMap<String, Object>();
        int index = 1;
        pstmt = (PreparedStatement) connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();// 返回查询结果
        ResultSetMetaData metaData = resultSet.getMetaData();
        int col_len = metaData.getColumnCount();
        while (resultSet.next()) {
            for (int i = 0; i < col_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
        }
        return map;
    }

    public void insertData(String sql, List<Object> params) throws SQLException {
        int index = 1;
        pstmt = (PreparedStatement) connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        pstmt.executeUpdate();
    }
    public void updateData(String sql, List<Object> params) throws SQLException {
        int index = 1;
        pstmt = (PreparedStatement) connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        pstmt.executeUpdate();
    }
    public void deleteData(String sql, List<Object> params) throws SQLException{
        int index = 1;
        pstmt = (PreparedStatement) connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        pstmt.executeUpdate();
    }

    public List<Map<String, Object>> queryMore(String sql, List<Object> params) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int index = 1;
        pstmt = (PreparedStatement) connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();

        int cols_len = metaData.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < cols_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
            list.add(map);
        }
        return list;
    }


    protected void finalize() throws Throwable {
        super.finalize();
        pstmt.close();
        connection.close();
    }
}