package com.online.database_option;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.online.tool.MD5;
import com.online.ui_manager.Manager.completeListener;

public class DBoption_search 
{
	//驱动名、URL、user name、password
	String driver=com.online.util.Data.driver;
	String url=com.online.util.Data.url;
	String user=com.online.util.Data.user;
	String password=com.online.util.Data.password;
	
	String userId=com.online.util.Data.userId;
	
	//构造方法
	public DBoption_search() 
	{
	}
	
	//用于查询基本信息
//	public ArrayList<Object> aStaffAll(String id)
//	{
//		//结果储存
//		ArrayList<Object> results=new ArrayList<>();
//		
//		try 
//		{
//			//加载驱动
//			Class.forName(driver);
//			//连接数据库
//			Connection connection=DriverManager.getConnection(url,user,password);
//	
//			//执行sql语句					
//			PreparedStatement ps =connection.prepareStatement("select * from online_staff where id=? and leavedate is null");
//			ps.setString(1, id);
//			ResultSet result= ps.executeQuery();
//			
//			String name,sex,phone,qq,email,district,department,position = "";
//			Date joindate;
//			while(result.next())
//			{
//				//从结果集中得到各种结果
//				name=result.getString("name");
//				sex=result.getString("sex");
//				phone=result.getString("phone");
//				qq=result.getString("qq");
//				email=result.getString("email");
//				district=result.getString("district");
//				joindate=result.getDate("joindate");
//				
//				result
//				
//			}
//			
//			result.close();
//			connection.close();
//		} 
//		catch (Exception e) 
//		{
//			System.out.println("aStaffAll select error!");
//		}
//		
//		return result;
//	}
	
//用id查询部门编号----------------------------------------------------------------------------------------
	public String IdToDepartmentId(String id)
	{
		String did="";
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
	
			//查询sql
			PreparedStatement ps =connection.prepareStatement("select d_id from online_department_in where id=?");
			ps.setString(1, id);
			ResultSet result1=ps.executeQuery();
			while(result1.next())
			{
				did=result1.getString("d_id");	
			}
			
			result1.close();
			connection.close();
		} 
		catch (Exception e) 
		{
			System.out.println("id to departmentid select error");
		}
		return did;
	}
	
//用id查职务编号------------------------------------------------------------------------------------------
	public String IdToPositionId(String id)
	{
		String pid="";
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
	
			//查询sql
			PreparedStatement ps =connection.prepareStatement("select p_id from online_position_in where id=?");
			ps.setString(1, id);
			ResultSet result1=ps.executeQuery();
			while(result1.next())
			{
				pid=result1.getString("p_id");	
			}
			
			result1.close();
			connection.close();
		} 
		catch (Exception e) 
		{
			System.out.println("id to positionId select error");
		}
		return pid;
	}
	
//用部门名字查询部门编号-------------------------------------------------------------------------------------
	public String DepartmentToDepartmentId(String department)
	{
		String did="";
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
	
			//保存名字
			PreparedStatement ps =connection.prepareStatement("select d_id from online_department where d_name=?");
			ps.setString(1, department);
			ResultSet result1=ps.executeQuery();
			while(result1.next())
			{
				did=result1.getString("d_id");	
			}
			
			result1.close();
			connection.close();
		} 
		catch (Exception e) 
		{
			System.out.println("department select error");
		}
		return did;
	}
	
	//用部门编号查询部门名字-------------------------------------------------------------------------------------
		public String DepartmentIdToDepartmetn(String d_id)
		{
			String d_name="";
			try 
			{
				//加载驱动
				Class.forName(driver);
				//连接数据库
				Connection connection=DriverManager.getConnection(url,user,password);
		
				//保存名字
				PreparedStatement ps =connection.prepareStatement("select d_name from online_department where d_id=?");
				ps.setString(1, d_id);
				ResultSet result1=ps.executeQuery();
				while(result1.next())
				{
					d_name=result1.getString("d_name");	
				}
				
				result1.close();
				connection.close();
			} 
			catch (Exception e) 
			{
				System.out.println("did to department select error");
			}
			return d_name;
		}
	
//用职位名字查职位编号--------------------------------------------------------------------------------------
	public String PositionToPositionId(String position)
	{
		String pid="";
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
	
			//保存名字
			PreparedStatement ps =connection.prepareStatement("select p_id from online_position where p_name=?");
			ps.setString(1, position);
			ResultSet result1=ps.executeQuery();
			while(result1.next())
			{
				pid=result1.getString("p_id");	
			}
			
			result1.close();
			connection.close();
		} 
		catch (Exception e) 
		{
			System.out.println("position select error");
		}
		return pid;
	}
	
//用职位编号查职位名字----------------------------------------------------------------------------------
	public String PositionIdToPosition(String p_id)
	{
		String p_name="";
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
	
			//保存名字
			PreparedStatement ps =connection.prepareStatement("select p_name from online_position where p_id=?");
			ps.setString(1, p_id);
			ResultSet result1=ps.executeQuery();
			while(result1.next())
			{
				p_name=result1.getString("p_name");	
			}
			
			result1.close();
			connection.close();
		} 
		catch (Exception e) 
		{
			System.out.println("position select error");
		}
		return p_name;
	}
	
	
//登陆-------------------------------------------------------------------------------------
		public boolean Login(String id,String pass)
		{
			boolean isLogin = false;
			
			try 
			{
				//加载驱动
				Class.forName(driver);
				//连接数据库
				Connection connection=DriverManager.getConnection(url,user,password);
		
				if(!ExistID(id))
				{
					JOptionPane.showMessageDialog(null, "不好意思，学号有误~", "不能这样做~", JOptionPane.NO_OPTION);
					return false;
				}
				else 
				{
					//保存名字
					PreparedStatement ps =connection.prepareStatement("select password from online_staff where id=?");
					ps.setString(1, id);
					ResultSet result1=ps.executeQuery();
					
					//判断是否空
					while(result1.next())
					{
						String code=result1.getString("password");
						
						MD5 md5=new MD5();
						String inputCode=md5.GetMD5Code(pass);
						
						if(inputCode.equals(code))
						{
							System.out.println();
							return true;
						}
						else
						{
							System.out.println(inputCode);
							JOptionPane.showMessageDialog(null, "不好意思，密码有误~", "不能这样做~", JOptionPane.NO_OPTION);
						}
					}
					
					//关流
					result1.close();
					connection.close();
				} 
			}
			catch (Exception e) 
			{
				System.out.println("login select error");
			}
				
				return isLogin;
					
				
				
		}
	
//查询学号是否存在-------------------------------------------------------------------------------------
	public boolean ExistID(String id)
	{
		boolean isempty = false;
		
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
	
			//保存名字
			PreparedStatement ps =connection.prepareStatement("select id from online_staff where id=? and leavedate is null");
			ps.setString(1, id);
			ResultSet result1=ps.executeQuery();

			//判断是否空
			isempty=result1.next();
			
			//关流
			result1.close();
			connection.close();
		} 
		catch (Exception e) 
		{
			System.out.println("id select error");
		}
		
		return isempty;
	}
	
//查询项目编号是否存在-------------------------------------------------------------------------------------
		public boolean ExistWID(String wid)
		{
			boolean isempty = false;
			
			try 
			{
				//加载驱动
				Class.forName(driver);
				//连接数据库
				Connection connection=DriverManager.getConnection(url,user,password);
		
				//保存名字
				PreparedStatement ps =connection.prepareStatement("select w_id from online_work where w_id=? and ifend<>100");
				ps.setString(1, wid);
				ResultSet result1=ps.executeQuery();

				//判断是否空
				isempty=result1.next();
				
				//关流
				result1.close();
				connection.close();
			} 
			catch (Exception e) 
			{
				System.out.println("w_id select error");
			}
			
			return isempty;
		}
		
		
//查询未领项目编号是否存在-------------------------------------------------------------------------------------
				public boolean ExistWIDnobody(String wid)
				{
					boolean isempty = false;
					
					try 
					{
						//加载驱动
						Class.forName(driver);
						//连接数据库
						Connection connection=DriverManager.getConnection(url,user,password);
				
						//保存名字
						PreparedStatement ps =connection.prepareStatement("select * from online_work where w_id=? and ifdo='未接领'");
						ps.setString(1, wid);
						ResultSet result1=ps.executeQuery();

						//判断是否空
						isempty=result1.next();
						
						//关流
						result1.close();
						connection.close();
					} 
					catch (Exception e) 
					{
						System.out.println("w_id select error");
					}
					
					return isempty;
				}
		
//查询项目号是否存在-------------------------------------------------------------------------------------
	public boolean ExistJID(int jobid)
	{
		boolean exist = false;
		
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
	
			//保存名字
			PreparedStatement ps =connection.prepareStatement("select id from online_work_on where job_id=? and id=? and com_date is null");
			ps.setInt(1, jobid);
			ps.setString(2, userId);
			ResultSet result1=ps.executeQuery();

			//判断是否空
			exist=result1.next();
			
			//关流
			result1.close();
			connection.close();
		} 
		catch (Exception e) 
		{
			System.out.println("id select error");
		}
		
		return exist;
	}
	
	
	//查询学号是否存在-------------------------------------------------------------------------------------
		public boolean ExistIDjod(String wid)
		{
			boolean isempty = false;
			
			try 
			{
				//加载驱动
				Class.forName(driver);
				//连接数据库
				Connection connection=DriverManager.getConnection(url,user,password);
		
				//保存名字
				PreparedStatement ps =connection.prepareStatement("select id from online_work_on where wid=? ");
				ps.setString(1, wid);
				ResultSet result1=ps.executeQuery();

				//判断是否空
				isempty=result1.next();
				
				//关流
				result1.close();
				connection.close();
			} 
			catch (Exception e) 
			{
				System.out.println("id jib select error");
			}
			
			return isempty;
		}
	
		
		//查询最大jobid-------------------------------------------------------------------------------------
		public int maxJobId(String wid)
		{
			int jobid = 0;
		
			try 
			{
				//加载驱动
				Class.forName(driver);
				//连接数据库
				Connection connection=DriverManager.getConnection(url,user,password);
				
				//保存名字
				PreparedStatement ps =connection.prepareStatement("select max(job_id) a from online_work_on where w_id=? and id=? ");
				ps.setString(1, wid);
				ps.setString(2, com.online.util.Data.userId);
				ResultSet result1=ps.executeQuery();
				
				//判断是否空
				while(result1.next())
				{
					System.out.println(1+"    "+jobid);
					jobid=result1.getInt("a");
				}
				
				//关流
				result1.close();
				connection.close();
			} 
			catch (Exception e) 
			{
				System.out.println("id jib select error");
			}
			
			return jobid;
		}
		
//-----------------------------------------------------------------------------------
		public String WIdToWork(String wid)
		{
			String w_name = null;
			
			try 
			{
				//加载驱动
				Class.forName(driver);
				//连接数据库
				Connection connection=DriverManager.getConnection(url,user,password);
		
				PreparedStatement ps2 =connection.prepareStatement
				( "SELECT w_name FROM online_work WHERE w_id=?");
				ps2.setString(1, wid);
				ResultSet result2= ps2.executeQuery();
				while (result2.next()) 
				{
					w_name=result2.getString("w_name");
				}
				
				result2.close();
				connection.close();

			} 
			catch (Exception e) 
			{
				System.out.println("w_id select error");
			}
			
			return w_name;
			
		}
		//-------------------------------------------------------------------------------------
		public String WIdToDeadline(String wid)
		{
			String deadline = null;
			
			try 
			{
				//加载驱动
				Class.forName(driver);
				//连接数据库
				Connection connection=DriverManager.getConnection(url,user,password);
		
				PreparedStatement ps2 =connection.prepareStatement
				( "SELECT deadline FROM online_work WHERE w_id=?");
				ps2.setString(1, wid);
				ResultSet result2= ps2.executeQuery();
				while (result2.next()) 
				{
					deadline=result2.getString("deadline");
				}
				result2.close();
				connection.close();
			} 
			catch (Exception e) 
			{
				System.out.println("w_id select error");
			}
			
			return deadline;
			
		}
	
		
		
		
		
		
		//编号查姓名-------------------------------------------------------------------------------------
		public String WIdToName(String wid)
		{
			String name = null;
			
			try 
			{
				//加载驱动
				Class.forName(driver);
				//连接数据库
				Connection connection=DriverManager.getConnection(url,user,password);
		
				PreparedStatement ps4 =connection.prepareStatement
				( "SELECT name FROM online_staff WHERE id in (select id from online_work_on where w_id=?)");
				ps4.setString(1, wid);
				ResultSet result4= ps4.executeQuery();
				while (result4.next()) 
				{
					name=result4.getString("name");
				}
				result4.close();
				connection.close();
			} 
			catch (Exception e) 
			{
				System.out.println("w_id to name select error");
			}
			
			return name;
		}
		
		// power search-0-------------------------------------------------------------------		
		public int IdToPower(String id)
		{
			int power = 0;
			
			try 
			{
				//加载驱动
				Class.forName(driver);
				//连接数据库
				Connection connection=DriverManager.getConnection(url,user,password);
		
				PreparedStatement ps4 =connection.prepareStatement
				( "SELECT power FROM online_position WHERE p_id in (select p_id from online_position_in where id=?)");
				ps4.setString(1, com.online.util.Data.userId);
				ResultSet result4= ps4.executeQuery();
				while (result4.next()) 
				{
					power=result4.getInt("power");
				}
				result4.close();
				connection.close();
			} 
			catch (Exception e) 
			{
				System.out.println("power select error");
			}
			
			return power;
		}
}
