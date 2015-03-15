package com.online.database_option;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBoption_delete 
{
	//驱动名、URL、user name、password
	String driver=com.online.util.Data.driver;
	String url=com.online.util.Data.url;
	String user=com.online.util.Data.user;
	String password=com.online.util.Data.password;
	
	public DBoption_delete()
	{

	}
	
	
	
//项目删除----------------------------------------------------------------------------------------------
		public void workDelete(String wid)
		{
			try 
			{
				//加载驱动
				Class.forName(driver);
				//连接数据库
				Connection connection=DriverManager.getConnection(url,user,password);
				
				//加入离职时间

				PreparedStatement ps =connection.prepareStatement("delete from online_work  where w_id=?");
				ps.setString(1, wid );
				ps.execute();
				
				connection.close();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("work delete error!");
			}
		}
}
