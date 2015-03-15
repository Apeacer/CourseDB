package com.online.database_option;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.online.tool.MD5;
import com.online.ui_manager.Manager.completeListener;

public class DBoption_change 
{
	//驱动名、URL、user name、password
	String driver=com.online.util.Data.driver;
	String url=com.online.util.Data.url;
	String user=com.online.util.Data.user;
	String password=com.online.util.Data.password;
	String userid=com.online.util.Data.userId;
	
	public DBoption_change()
	{

	}
	
	
	
	
	
	//更新基本信息
	public void staffUpdate(String oid,String id,String name,String sex,String phone,String qq,String email,String district,String joindate)
	{
		java.sql.Date njoindate=java.sql.Date.valueOf(joindate);
		
//		System.out.println(id+" "+name+" "+sex+" "+phone+" "+qq+" "+email+" "+district+" "+joindate);
		
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
			
			//更新新程序
			PreparedStatement ps =connection.prepareStatement
			("update online_staff set id=?,name=?,sex=?,phone=?,qq=?,email=?,district=?,joindate=? where id=?");
			ps.setString(1,id );
			ps.setString(2,name);
			ps.setString(3,sex);
			ps.setString(4,phone);
			ps.setString(5,qq);
			ps.setString(6,email);
			ps.setString(7,district);
			ps.setDate(8, njoindate);
			ps.setString(9, oid);
			ps.execute();
			
			connection.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("staff Update error!");
		}
	}
	
	
	
	
	
	//更新密码
	public void passwordUpdate(String id,String password)
	{
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
			
			MD5 md5=new MD5();
			String code=md5.GetMD5Code(password);
			
			PreparedStatement ps =connection.prepareStatement
			("update online_staff set password=? where id=?");
			ps.setString(1, code );
			ps.setString(2, id);
			ps.execute();
			
			connection.close();
		} 
		catch (Exception e) 
		{}
	}
	
	
	
	
	
	//更新基本信息
	public void workUpdate(String oid,String wid,String wname,String deadline,int reward)
	{
//		java.sql.Date njoindate=java.sql.Date.valueOf(joindate);
		
//		System.out.println(id+" "+name+" "+sex+" "+phone+" "+qq+" "+email+" "+district+" "+joindate);
//		System.out.println(3+" "+oid+" "+wid+" "+wname);
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
			
			//更新新程序
			PreparedStatement ps =connection.prepareStatement
			("update online_work set w_id=?,w_name=?,deadline=?,reward=? where w_id=?");
			ps.setString(1,wid );
			ps.setString(2,wname);
			ps.setString(3,deadline);
			ps.setInt(4,reward);
			ps.setString(5,oid);

			ps.execute();
			
			connection.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("work Update error!");
		}
	}
	
	
	
	
	
//离职申请----------------------------------------------------------------------------------------------
	public void leaveUpdate(String id)
	{
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
			
			//加入离职时间
			java.sql.Date leaveDate=new java.sql.Date(new java.util.Date().getTime());
//			Date leaveDate=new java.util.Date();
			PreparedStatement ps =connection.prepareStatement("update online_staff set leavedate=? where id=?");
			ps.setDate(1,leaveDate );
			ps.setString(2, id);
			ps.execute();
			
			connection.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("leaveUpdate error!");
		}
	}
	
	
	
	
	
//任务完成-------------------------------------------------------------------------------------
	public void completeUpdate(int job_id)
	{
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
			
			//加入离职时间
			java.sql.Date completeDate=new java.sql.Date(new java.util.Date().getTime());
//			Date leaveDate=new java.util.Date();
			PreparedStatement ps =connection.prepareStatement("update online_work_on set com_date=? , ifend='已完成' where job_id=? and id=?");
			ps.setDate(1,completeDate );
			ps.setInt(2, job_id);
			ps.setString(3, com.online.util.Data.userId);
			ps.execute();
			
			//计算百分比
			int com = 0,tot=1;
			//找出分子
			PreparedStatement ps1 =connection.prepareStatement("select count(*) a from online_work_on where w_id in(select w_id from online_work_on where job_id=? and id=?)");
			ps1.setInt(1,job_id );
			ps1.setString(2, userid);
			ResultSet resultSet1=ps1.executeQuery();
			while(resultSet1.next())
			{
				tot=resultSet1.getInt("a");
			}
			PreparedStatement ps2=connection.prepareStatement("select count(*) b from online_work_on where com_date is not null and  w_id in(select w_id from online_work_on where job_id=? and id=?)");
			ps2.setInt(1,job_id );
			ps2.setString(2, userid);
			ResultSet resultSet2=ps2.executeQuery();

			while(resultSet2.next())
			{
				com=resultSet2.getInt("b");
				
				
				
				
//				com=resultSet2.getInt(1);				
//				System.out.println("-------------"+com+ "-------------"+resultSet2.getMetaData().getColumnName(1));
			}

			double a=com;
			double b=tot;
			double c=a/b*100;
			int p=(int) c;
			
//			System.out.println(job_id+" "+userid+" "+tot+" "+com+" "+p+" "+c);
//			System.out.println(p);
			
			
			PreparedStatement ps3=connection.prepareStatement("update online_work set ifend=? where w_id in(select w_id from online_work_on where job_id=? and id=?)");
			ps3.setInt(1, p);
			ps3.setInt(2,job_id );
			ps3.setString(3, userid);
			ps3.execute();
			
			
			if(p==100)
			{
				PreparedStatement ps4=connection.prepareStatement("update online_work set ifdo='已完成' where w_id in(select w_id from online_work_on where job_id=? and id=?)");
				ps4.setInt(1,job_id );
				ps4.setString(2, userid);
				ps4.execute();
			}
			
			resultSet1.close();
			resultSet2.close();
			connection.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("job cpmplete Update error!");
		}
	}
	
	
	
	
	
//用于更新部门信息--------------------------------------------------------------------------------
	public void departmentUpdate(String id,String d_id)
	{
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
	
			//保存信息
			PreparedStatement ps =connection.prepareStatement("update online_department_in set d_id=? where id=?");
			ps.setString(1, d_id);
			ps.setString(2, id);
			ps.execute();

			connection.close();
		} 
		catch (Exception e) 
		{
			System.out.println("Department update error!");
		}
	}
	
	
	
	
	
//用于更新职务信息--------------------------------------------------------------------------------
	public void positionUpdate(String id,String p_id)
	{
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
	
			//保存信息
			PreparedStatement ps =connection.prepareStatement("update online_position_in set p_id=? where id=?");
			ps.setString(1, p_id);
			ps.setString(2, id);
			ps.execute();

			connection.close();
		} 
		catch (Exception e) 
		{
			System.out.println("position update error!");
		}
	}
	
	//让work变进行
	public void ifdoUpdate(String wid)
	{
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
	
			//保存信息
			PreparedStatement ps =connection.prepareStatement("update online_work set ifdo='进行中' where w_id=?");
			ps.setString(1, wid);
			ps.execute();

			connection.close();
		} 
		catch (Exception e) 
		{
			System.out.println("if do update error!");
		}
	}
	
	//改密码
	public void PasswordToNpassword(String pass)
	{
		MD5 md5=new MD5();
		
		String code=md5.GetMD5Code(pass);
		
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
	
			//保存信息
			PreparedStatement ps =connection.prepareStatement("update online_staff set password=? where id=?");
			ps.setString(1,code);
			ps.setString(2, com.online.util.Data.userId);
			ps.execute();

			connection.close();
		} 
		catch (Exception e) 
		{
			System.out.println("password update error!");
		}
	}
	
	
}
