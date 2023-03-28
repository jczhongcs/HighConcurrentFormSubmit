package com.high_con.grad.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.high_con.grad.entity.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class User_Util {

    private static void createUser(int count) throws Exception{
        List<User> users = new ArrayList<User>(count);
        //生成用户
        for(int i=0;i<count;i++) {
            User user = new User();
            user.setId(13000000000L+i);
            user.setLoginCount(1);
            user.setNickname("user"+i);
            user.setRegisterDate(new Date());
            user.setSalt("5zi7ng9");
            user.setRole(1);
            user.setPassword(MD5_Util.inputPassToDbPass("123456", user.getSalt()));
            users.add(user);
        }
       // System.out.println("create user");
		//插入数据库
		/*Connection conn = DB_Util.getConn();
		String sql = "insert into t_user( role, login_count, nickname, register_date, salt, password, id)values(?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		for(int i=0;i<users.size();i++) {
		User user = users.get(i);
        pstmt.setInt(1,user.getRole());
		pstmt.setInt(2, user.getLoginCount());
		pstmt.setString(3, user.getNickname());
		pstmt.setTimestamp(4, new Timestamp(user.getRegisterDate().getTime()));
		pstmt.setString(5, user.getSalt());
		pstmt.setString(6, user.getPassword());
		pstmt.setLong(7, user.getId());
		pstmt.addBatch();
		}
		pstmt.executeBatch();
		pstmt.close();
		conn.close();
		System.out.println("insert to db");*/
        //登录，生成token
        String urlString = "http://localhost:8080/login/dologin";
        File file = new File("D:/tokens.txt");
        if(file.exists()) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        file.createNewFile();
        raf.seek(0);
        for(int i=0;i<users.size();i++) {
            User user = users.get(i);
            URL url = new URL(urlString);
            HttpURLConnection co = (HttpURLConnection)url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);
            OutputStream out = co.getOutputStream();
           // System.out.println("role:"+user.getRole());
            String params = "phone="+user.getId()+"&pwd="+MD5_Util.inputPassFormPass("123456")+"&role="+user.getRole();
            //System.out.println("params: "+params);
            out.write(params.getBytes());
            out.flush();
            InputStream inputStream = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte buff[] = new byte[1024];
            int len = 0;
            while((len = inputStream.read(buff)) >= 0) {
                bout.write(buff, 0 ,len);
            }
            inputStream.close();
            bout.close();
            String response = new String(bout.toByteArray());
           // System.out.println(response);
            JSONObject jo = JSON.parseObject(response);
            String token = jo.getString("data");
            //System.out.println("create token : " + user.getId());
            //System.out.println("token: "+token);
            String row = user.getId()+","+token;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            //System.out.println("write to file : " + user.getId());
        }
        raf.close();

        System.out.println("over");
    }

    public static void main(String[] args)throws Exception {
        createUser(5000);
    }
    }


