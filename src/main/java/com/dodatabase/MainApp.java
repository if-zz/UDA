package com.dodatabase;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.camel.main.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Camel Application
 */
public class MainApp {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
////        Main main = new Main();
////        main.addRouteBuilder(new MyRouteBuilder());
////        main.run(args);
//        Mysql mysql=new Mysql();
//        int count=mysql.queryCount("select* from complain_table where driver='苟朝华'",null);
//        Map<String, Object> map = new HashMap<String, Object>();
//        map=mysql.queryOne("select* from complain_table where ID=1;",null);
//        List<Map<String, Object>> list=mysql.queryMore("select* from complain_table where driver='苟朝华'",null);
//        List<Object> data=new ArrayList<>();
////        data.add("zif");
////        data.add("绕路");
////        mysql.insertData("insert into complain_table(driver,category) values(?,?)",data);
////        data.add("100");
////        data.add("解决误会");
////        mysql.updateData("update complain_table set RegTime=?,punishment=? where driver='zif'",data);
//        mysql.deleteData("delete from complain_table where driver='zif'",null);
//        System.out.print(count);
//        System.out.print(map.toString());
//        for (int i=0;i<list.size();i++){
//            System.out.println(list.get(i));
//        }
        MysqlAPI mysqlAPI=new MysqlAPI();
        int count=mysqlAPI.queryCount("queryCount",null);
        JSONArray json1=mysqlAPI.queryMore("queryCount",null);
        System.out.println(count);
        for (int i=0;i<json1.size();i++){
            System.out.println(json1.get(i));
        }
        mysqlAPI.close();
    }

}

