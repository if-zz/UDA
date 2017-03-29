package com.dodatabase;

import java.net.UnknownHostException;
import java.util.*;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.util.JSON;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.QueryOperators;
import com.mongodb.util.JSON;


public class MongoAPI {

    private static final String host = "localhost";
    private static final int port = 27017;
    private static final String userName = "";
    private static final String password = "";
    private static final String dataBaseName = "mydb";
    private static final String tableName = "minedb";
    //查询存在的数据库
    public static void querydbAll(Mongo mg) {
        System.out.println("查询到存在的数据库：");
        for (String name : mg.getDatabaseNames()) {
            System.out.println("dbName: " + name);
        }
    }
    //查询一个指定表的所有数据
    public static void querytableAll(DBCollection table) {
        DBCursor cur = table.find();
        System.out.println("查询到" + cur.count() + "条数据：");
        while (cur.hasNext()) {
            System.out.println(cur.next());
        }
        //System.out.println(cur.count());
        //System.out.println(cur.getCursorId());
        //System.out.println(JSON.serialize(cur));
    }

    //查询某一条数据
    public static void querytableOne(DBCollection table,int count) {
        BasicDBObject query = new BasicDBObject();
        query.put("count", count);
        DBCursor cur = table.find(query);
        try {
            while(cur.hasNext()) {
                System.out.println(cur.next());
            }
        } finally {
            cur.close();
        }
    }

    //查询count在count1和count2之间的数据 count1 < count <= count2
    public static void querytableMore(DBCollection table,int count1,int count2) {
        //查找 20<i<=30
        BasicDBObject query = new BasicDBObject();
        query.put("count", new BasicDBObject("$gt", count1).append("$lte", count2));
        DBCursor cursor = table.find(query);
        try {
            while(cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }
    }


    //插入函数
    public static void insert(DBCollection table) {

        /*BasicDBObject doc = new BasicDBObject();
        doc.put("name", "MongoDB");
        doc.put("type", "database");
        doc.put("count", 0);

        BasicDBObject info = new BasicDBObject();
        info.put("x", 203);
        info.put("y", 102);
        doc.put("info", info);

        table.insert(doc);*/

        //插入多条数据
        /*for (int i=1; i < 100; i++) {
            table.insert(new BasicDBObject().append("count", i));
        }*/
    }


    public static void update(DBCollection table) {
        //修改 i=71的一项
        BasicDBObject query = new BasicDBObject();
        query.put("count", 71);
        BasicDBObject update = new BasicDBObject();
        update.put("count", 710);
        DBObject dbobj = table.findAndModify(query, update);
        System.out.println(dbobj);
    }


    public static void remove(DBCollection table,int count) {
        //删除i=61
        BasicDBObject query = new BasicDBObject();
        query.put("count", count);
        table.remove(query);
    }

    public static void main(String[] args) throws UnknownHostException, MongoException {
        Mongo mg = new Mongo();
        //查询所有的Database
        querydbAll(mg);
        /*for (String name : mg.getDatabaseNames()) {
            System.out.println("dbName: " + name);
        }*/

        DB db = mg.getDB("mydb");

        //查询所有的聚集集合
        /*for (String name : db.getCollectionNames()) {
            System.out.println("shengxiao");
            System.out.println("collectionName: " + name);
        }*/

        Set<String>  colls = db.getCollectionNames();

        for(String s : colls) {
            System.out.println("collectionName: " + s);
        }

        DBCollection table = db.getCollection("minedb");

        //update(table);
        //remove(table,61);
        //querytableAll(table);
        //querytableOne(table,0);
    }
}
