package com.assist;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.Random;


public class Email {
    //生成6位数  验证码
    public static String random(){
        String code = "";
        Random rd=new Random();

        //随机数最大值
        int ramdom=10;
        int lenth=6;
        for (int i = 0; i < lenth; i++) {
            int r = rd.nextInt(ramdom); //每次随机出一个数字（0-ramdom-1）
            code = code + r;  //把每次随机出的数字拼在一起
        }
        System.out.println(code);
        return code;
    }

    //发送					这里的参数为   qq   和验证码
    public static void send(String email,String yzm){
        HtmlEmail send = new HtmlEmail();//创建一个HtmlEmail实例对象
        // 获取随机验证码
        String resultCode = yzm;
        try {
            send.setHostName("smtp.qq.com");
            send.setAuthentication("1114748869@qq.com", "niuottjdcjyaghcg"); //第一个参数是发送者的QQEamil邮箱   第二个参数是刚刚获取的授权码

            send.setFrom("1114748869@qq.com", "滨滨商城");//发送人的邮箱为自己的，用户名可以随便填  记得是自己的邮箱不是qq
			//send.setSmtpPort(465); 	//端口号 可以不开
            send.setSSLOnConnect(true); //开启SSL加密
            send.setCharset("utf-8");
            send.addTo(email);  //设置收件人    email为你要发送给谁的邮箱账户   上方参数
            send.setSubject("滨滨商城验证码"); //邮箱标题
            send.setMsg("欢迎光临滨滨商城，注册验证:   " + resultCode); //Eamil发送的内容
            send.send();  //发送
            System.out.println("发送完");
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

    public static void senduppwd(String email,String url){
        HtmlEmail send = new HtmlEmail();//创建一个HtmlEmail实例对象
        // 获取随机验证码
        try {
            send.setHostName("smtp.qq.com");
            send.setAuthentication("1114748869@qq.com", "niuottjdcjyaghcg"); //第一个参数是发送者的QQEamil邮箱   第二个参数是刚刚获取的授权码
            send.setFrom("1114748869@qq.com", "滨滨商城");//发送人的邮箱为自己的，用户名可以随便填  记得是自己的邮箱不是qq
            //send.setSmtpPort(465); 	//端口号 可以不开
            send.setSSLOnConnect(true); //开启SSL加密
            send.setCharset("utf-8");
            send.addTo(email);  //设置收件人    email为你要发送给谁的邮箱账户   上方参数
            send.setSubject("滨滨商城修改密码"); //邮箱标题
            send.setContent("<a href='"+url+"'>点击修改密码</a>", "text/html;charset=UTF-8");
            send.send();  //发送
            System.out.println("发送完");
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}


