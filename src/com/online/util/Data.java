package com.online.util;

public class Data 
{
	//用户信息
	public static String userId;
	public static String userPassword;
	
	//数据库连接参数
	public  static String driver="com.mysql.jdbc.Driver";
	public  static String url="jdbc:mysql://127.0.0.1:3306/online_system?characterEncoding=utf-8";
	public  static String user="root";
	public  static String password="";
	//查询线程睡眠时间
	public static int selectSleep=2;
	
	//JTextFile规格
	public  static int inputSizeId=12;
	public  static int inputSizeName=20;
	public  static int inputSizePhone=11;
	public  static int inputSizeQQ=15;
	public  static int inputSizeEmail=100;
	
	public  static int inputSizeWId=10;
	public  static int inputSizeWname=20;
	public  static int inputSizeReward=5;
	
	public  static int inputSizeJob=3;

	
}
