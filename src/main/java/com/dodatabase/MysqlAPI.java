package com.dodatabase;

/**
 * Created by Administrator on 2017/3/6.
 */
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

//import org.apache.taglibs.standard.lang.jstl.test.beans.PublicInterface2;


import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MysqlAPI {

    private Mysql mysql;
    private Properties prs;

    /**
     * 构造函数，初始化sql.properties
     *
     * @throws IOException
     */
    public MysqlAPI() throws IOException {
        prs = new Properties();
        InputStream inputStream = MysqlAPI.class.getResourceAsStream("/sql.properties");
        mysql = Mysql.getInstance();
        prs.load(inputStream);
        inputStream.close();

    }

    /**
     * 向数据库插入一条数据
     *
     * @param sqlKey
     *            key的值，通过寻找sql.propertie文件来获得对应的sql
     * @param params
     *            插入的数据
     * @return 插入失败的返回值
     */
    public String InsertData(String sqlKey, List<Object> params) {
        String sql = prs.getProperty(sqlKey);

        String error = null;
        try {
            mysql.insertData(sql, params);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            error = "插入失败";
        }
        return error;
    }

    /**
     * 向数据库更新一条数据
     *
     * @param sqlKey
     *            key的值，通过寻找sql.propertie文件来获得对应的sql
     * @param params
     *            更新的数据
     * @return 更新失败的返回值
     */
    public String UpdateData(String sqlKey, List<Object> params) {
        String sql = prs.getProperty(sqlKey);
        String error = null;
        try {
            mysql.updateData(sql, params);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            error = "更新失败";
        }
        return error;
    }

    /**
     * 向数据库删除数据
     *
     * @param sqlKey
     *            key的值，通过寻找sql.propertie文件来获得对应的sql
     * @param params
     *            删除的数据
     * @return 删除失败的返回值
     */
    public String DeleteData(String sqlKey, List<Object> params) {
        String sql = prs.getProperty(sqlKey);
        String error = null;
        try {
            mysql.deleteData(sql, params);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            error = "删除失败";
        }
        return error;
    }

    /**
     * 向数据库查询符合要求记录的数量
     *
     * @param sqlKey
     *            key的值，通过寻找sql.propertie文件来获得对应的sql
     * @param params
     *            查询的数据
     * @return 所查数据的数量
     */
    public int queryCount(String sqlKey, List<Object> params) {
        String sql = prs.getProperty(sqlKey);
        int count   = 0;
        try {
            count   = mysql.queryCount(sql, params);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 查询一条数据，并返回json对象
     *
     * @param sqlKey
     *            sql.properties文件里面的key
     * @param params
     *            要查询的参数
     * @return json对象
     */
    public JSONObject queryOne(String sqlKey, List<Object> params) {
        JSONObject json = null;
        String sql = prs.getProperty(sqlKey);
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map = mysql.queryOne(sql, params);
            json = JSONObject.fromObject(map);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 查询多条数据，并返回json对象
     *
     * @param sqlKey
     *            sql.properties文件里面的key
     * @param params
     *            要查询的参数
     * @return json对象
     */
    public JSONArray queryMore(String sqlKey, List<Object> params) {
        JSONArray json = null;
        String sql = prs.getProperty(sqlKey);
        try {
            List<Map<String, Object>> list = mysql.queryMore(sql, params);
            json = JSONArray.fromObject(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return json;
    }

    public String close(){
        String error=null;
        try {
            mysql.finalize();
        }catch (Throwable e){
            e.printStackTrace();
            error="断开连接失败";
        }
        return error;
    }
}