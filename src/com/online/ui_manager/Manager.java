package com.online.ui_manager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.omg.PortableServer.ThreadPolicy;

import com.online.database_option.DBoption_search;
import com.online.ui_project.Project_ChangeWork;

public class Manager extends JPanel
{
	//驱动名、URL、user name、password
	String driver=com.online.util.Data.driver;
	String url=com.online.util.Data.url;
	String user=com.online.util.Data.user;
	String password=com.online.util.Data.password;
	
	//显示工程
	JTextArea jTextArea_project;
	//我的任务
	JPanel jPanel_mywork;
	//所有的面板
	JPanel jPanel_allJPanel;
	
	JButton jButton_orgin;
	JButton jButton_complete;
	JButton jButton_change_password;
	
	JComboBox jComboBox_select;
	JComboBox jComboBox_com;
	
	public Manager()
	{
		//组件声名
		jTextArea_project=new JTextArea();
		jPanel_allJPanel=new JPanel();
		jPanel_mywork=new JPanel();
		jButton_complete=new JButton("确定完成");
		jButton_orgin=new JButton("接领项目");
		jButton_change_password=new JButton("更改密码");
		jComboBox_select=new JComboBox();
		jComboBox_select.addItem("我的分工");
		jComboBox_select.addItem("我的项目");
		jComboBox_select.addItem("现有项目");
		
		jComboBox_com=new JComboBox();
		jComboBox_com.addItem("未完成");
		jComboBox_com.addItem("进行中");
		
		
		JPanel select_panel =new JPanel();
		select_panel.setLayout(new BorderLayout());
		
		select_panel.add(jComboBox_select,BorderLayout.CENTER);
		select_panel.add(jComboBox_com,BorderLayout.EAST);
		
		this.setLayout(new BorderLayout());
		add(select_panel,BorderLayout.NORTH);
		add(jTextArea_project,BorderLayout.CENTER);
		add(jPanel_mywork,BorderLayout.SOUTH);
		
		jPanel_mywork.add(jButton_complete);
		jPanel_mywork.add(jButton_orgin);
		jPanel_mywork.add(jButton_change_password);
		
		jButton_complete.addActionListener(new completeListener());
		jButton_orgin.addActionListener(new orgListener());
		jButton_change_password.addActionListener(new passwordChangeListener());
		
		new MyWork().start();
		
	}
	//查询我现有的分工
	
	private class MyWork extends Thread
	{
		public MyWork()
		{
			
		}
		public void run()
		{
			while(true)
			{
				try 
				{
					//获得选项
					int select=jComboBox_select.getSelectedIndex();
					int select_com=jComboBox_com.getSelectedIndex();
					//加载驱动
					Class.forName(driver);
					//连接数据库
					Connection connection=DriverManager.getConnection(url,user,password);
					//链接失败
					if(!connection.isClosed())
						System.out.println("success connecting");///////////////////////////////
					
					
					
					
					
					//我的分工
					if (select==0)
					{
						String sql_short = null;
						if(select_com==0)
							sql_short="and com_date is null";
						if(select_com==1)
							sql_short="and com_date is not null";
						//执行sql语句					
						PreparedStatement ps =connection.prepareStatement("select * from online_work_on where id=? "+sql_short);
						ps.setString(1, com.online.util.Data.userId);
						ResultSet result= ps.executeQuery();
						
						//显示数据的声明
						int job_id;
						String wid,w_name = null,d_work,ifend,d_reward,deadline;
						
						//先清空
						jTextArea_project.setText("");
						jTextArea_project.append("分工编号\t项目编号\t项目名称\t工作\t状态\t截止日期\t奖金\n");
						jTextArea_project.append("————————————————————————————————————————————————————————————————————————————\n");
						
						//再显示，游标查询
						while(result.next())
						{
							job_id=result.getInt("job_id");
							wid=result.getString("w_id");
							d_work=result.getString("d_work");
							ifend=result.getString("ifend");
							d_reward=result.getString("d_reward");
							
							DBoption_search dBsearch=new DBoption_search();
							
							//查询项目名
							w_name=dBsearch.WIdToWork(wid);
							
							//查询期限
							deadline=dBsearch.WIdToDeadline(wid);
							
							//显示数据
							jTextArea_project.append(job_id+"\t"+wid+"\t"+w_name+"\t"+d_work+"\t"+ifend+"\t"+deadline+"\t"+d_reward+"\n");
							jTextArea_project.append("————————————————————————————————————————————————————————————————————————————\n");
							
						}
						
						result.close();
						connection.close();
					}
					
					
					
					
					
					//我的项目
					if(select==1)
					{
						String sql_short = null;
						if(select_com==0)
							sql_short="and com_date is null";
						if(select_com==1)
							sql_short="and com_date is not null";
						
						PreparedStatement ps1 =connection.prepareStatement("select * from online_staff a,online_work_on b where a.id=b.id "+sql_short+" and w_id in (select w_id from online_work_on where w_id in(select w_id from online_work_on where id=?))");
						ps1.setString(1, com.online.util.Data.userId);
						ResultSet result1= ps1.executeQuery();
						
						//显示数据的声明
						String wid,w_name = null,d_work,ifend,d_reward,deadline;
						
						//先清空
						jTextArea_project.setText("");
						jTextArea_project.append("姓名\t项目编号\t项目名称\t工作\t状态\t截止日期\t奖金\n");
						jTextArea_project.append("————————————————————————————————————————————————————————————————————————————\n");
						
						//再显示，游标查询
						while(result1.next())
						{
							wid=result1.getString("w_id");
							d_work=result1.getString("d_work");
							ifend=result1.getString("ifend");
							d_reward=result1.getString("d_reward");
							String name=result1.getString("name");
//							String name =new DBoption_search().WIdToName(wid);

							DBoption_search dBsearch=new DBoption_search();
							
							//查询项目名
							w_name=dBsearch.WIdToWork(wid);
							
							//查询期限
							deadline=dBsearch.WIdToDeadline(wid);
							
							//显示数据
							jTextArea_project.append(name+"\t"+wid+"\t"+w_name+"\t"+d_work+"\t"+ifend+"\t"+deadline+"\t"+d_reward+"\n");
							jTextArea_project.append("————————————————————————————————————————————————————————————————————————————\n");
							
						}
						
						result1.close();
						connection.close();
					}
					
					System.out.println(select);
					
					//总的项目
					if(select==2)
					{
						
						String sql_short = null;
						if(select_com==0)
							sql_short="where ifend<>100";
						if(select_com==1)
							sql_short="where ifend=100";
						
						//执行sql语句					
						PreparedStatement ps2 =connection.prepareStatement("select * from online_work "+sql_short);
//						ps2.setString(1, com.online.util.Data.userId);
						ResultSet result2= ps2.executeQuery();
						
						//显示数据的声明
						String wid,w_name = null,s_date,deadline,reward,ifdo,ifend;

						
						//先清空
						jTextArea_project.setText("");
						jTextArea_project.append("项目编号\t项目名称\t发布时间\t截止时间\t奖金\t状态\t完成度\n");
						jTextArea_project.append("————————————————————————————————————————————————————————————————————————————\n");
						
						//再显示，游标查询
						while(result2.next())
						{
							wid=result2.getString("w_id");
							w_name=result2.getString("w_name");
							s_date=result2.getString("s_date");
							deadline=result2.getString("deadline");
							reward=result2.getString("reward");
							ifdo=result2.getString("ifdo");
							ifend=result2.getString("ifend");

							DBoption_search dBsearch=new DBoption_search();
							
							//查询项目名
							w_name=dBsearch.WIdToWork(wid);
							
							//查询期限
							deadline=dBsearch.WIdToDeadline(wid);
							
							//显示数据
							jTextArea_project.append(wid+"\t"+w_name+"\t"+s_date+"\t"+deadline+"\t"+reward+"\t"+ifdo+"\t"+ifend+"%\n");
							jTextArea_project.append("————————————————————————————————————————————————————————————————————————————\n");
							
						}
						
						result2.close();
						connection.close();
					}
					
					
						
						
					}
				catch (Exception e) 
				{e.printStackTrace();}
				
				//每几秒就一刷新
				try 
				{sleep(com.online.util.Data.selectSleep*1000);} 
				catch (InterruptedException e) 
				{e.printStackTrace();}
			}
		}
	}
	
	//完成监听
	public class completeListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
			Manager_complete complete =new Manager_complete();
			
			complete.setBounds(400,300,620,100);
		}
		
	}
	
	//组织项目Listener
	private  class orgListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//启动查询对话框
			Manager_orgin orgwindow=new Manager_orgin();
			
			orgwindow.setBounds(300, 350, 820, 120);
			
		}
	}
	
	
	//更改密码监听
	private  class passwordChangeListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//启动查询对话框
			Manager_password_change passwordWindow=new Manager_password_change();
			
			passwordWindow.setBounds(300, 350, 820, 180);
			
		}
	}
	
	
	

}
