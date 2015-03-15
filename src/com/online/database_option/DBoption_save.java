package com.online.database_option;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.spi.DirStateFactory.Result;

import com.mysql.jdbc.Statement;

public class DBoption_save 
{
	//驱动名、URL、user name、password
	String driver=com.online.util.Data.driver;
	String url=com.online.util.Data.url;
	String user=com.online.util.Data.user;
	String password=com.online.util.Data.password;
	
	public DBoption_save()
	{
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
	
			connection.close();
		} 
		catch (Exception e){e.printStackTrace();}
	}
	
	//用于储存基本信息的方法----------------------------------------------------------------------------------------------------------
	public void staffSave(String id,String name,String sex,String phone,String qq,String email,String district,String joinDate )
	{
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
	
			//保存名字
			PreparedStatement ps =connection.prepareStatement("insert into online_staff (id,name,sex,phone,qq,email,district,joinDate)values(?,?,?,?,?,?,?,?)");
			ps.setString(1, id);
			ps.setString(2, name);
			ps.setString(3, sex);
			ps.setString(4, phone);
			ps.setString(5, qq);
			ps.setString(6,email);
			ps.setString(7,district);
			ps.setString(8,joinDate);



			ps.execute();
			
			connection.close();
		} 
		catch (Exception e){e.printStackTrace();System.out.println("save error");}
	}
	
	//用于储存项目信息的方法----------------------------------------------------------------------------------------------------------
	public void workSave(String wid,String wname,String startdate,String deadline,String reward)
	{
		try 
		{
			System.out.println("!!!!"+startdate+" "+deadline);
			
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
	
			//保存名字
			PreparedStatement ps =connection.prepareStatement("insert into online_work (w_id,w_name,s_date,deadline,reward,ifdo)values(?,?,?,?,?,'未接领')");
			ps.setString(1, wid);
			ps.setString(2, wname);
			ps.setString(3, startdate);
			ps.setString(4, deadline);
			ps.setString(5, reward);
			ps.execute();
			
			
			connection.close();
		} 
		catch (Exception e){e.printStackTrace();System.out.println("work save error");}
	}
	
	//用于储存部门信息--------------------------------------------------------------------------------
	public void departmentSave(String id,String d_id)
	{
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
	
			//保存信息
			PreparedStatement ps =connection.prepareStatement("insert into online_department_in (id,d_id)values(?,?)");
			ps.setString(1, id);
			ps.setString(2, d_id);
			ps.execute();

			connection.close();
		} 
		catch (Exception e) 
		{
			System.out.println("Department save error!");
		}
	}
	
	//用于储存职务信息--------------------------------------------------------------------------------
	public void positionSave(String id,String p_id)
	{
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
	
			//保存信息
			PreparedStatement ps =connection.prepareStatement("insert into online_position_in (id,p_id)values(?,?)");
			ps.setString(1, id);
			ps.setString(2, p_id);
			ps.execute();

			connection.close();
		} 
		catch (Exception e) 
		{
			System.out.println("position save error!");
		}
	}
	
	//存呵呵
	public void jobSave(String id,String wid,String dwork ,int reward,int jobid)
	{
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
	
			//保存信息
			PreparedStatement ps =connection.prepareStatement("insert into online_work_on (id,w_id,d_work,ifend,d_reward,job_id)values(?,?,?,'未完成',?,?)");
			ps.setString(1, id);
			ps.setString(2, wid);
			ps.setString(3, dwork);
			ps.setInt(4, reward);
			ps.setInt(5, jobid);
			ps.execute();

			connection.close();
		} 
		catch (Exception e) 
		{
			System.out.println("job search save error!");
		}
	}

	
	
}
