package com.online.ui_project;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.online.ui_human.Human_ChangePerson;
import com.online.ui_human.Human_LeavePerson;


public class Project extends JPanel
{
	JComboBox jBox_select;//选择完成否
	JTextArea jTextArea_selec;//显示屏
	JButton jButton_add;//添加项目
	JButton jButton_change;//改变信息
	JButton jButton_delete;//删除项目
	SelectAll selectAll;//查询线程
	//驱动名、URL、user name、password
	String driver=com.online.util.Data.driver;
	String url=com.online.util.Data.url;
	String user=com.online.util.Data.user;
	String password=com.online.util.Data.password;
	
	//布局板
	JPanel pu,pall,pd,p4;
	
	//休眠秒数
	int selectSleep=com.online.util.Data.selectSleep;
	
	//构造方法，建立界面
	public Project()
	{
		
		//布局
		setLayout (new BorderLayout());
		pd=new JPanel();
		
		//线程
		selectAll=new SelectAll();
		
		//尺寸
		setPreferredSize(new Dimension (1000,500));
		
		//选择框
		jBox_select=new JComboBox();
		jBox_select.addItem("未完成");
		jBox_select.addItem("已完成");
		
		//显示屏
		jTextArea_selec=new JTextArea();
		jTextArea_selec.setEditable(false);
		JScrollPane sp=new JScrollPane(jTextArea_selec);
		
		//添加项目按钮
		jButton_add=new JButton("加入项目!~");
		jButton_add.addActionListener(new addListener());
		//改变信息按钮
		jButton_change=new JButton("改变项目信息");
		jButton_change.addActionListener(new changeListener());
		//删除项目按钮
		jButton_delete=new JButton("删除项目");
		jButton_delete.addActionListener(new deleteListener());
		
		//添加组件
		this.add(jBox_select,BorderLayout.NORTH);
		this.add(sp,BorderLayout.CENTER);
		this.add(pd,BorderLayout.SOUTH);
		pd.add(jButton_add,BorderLayout.WEST);
		pd.add(jButton_change,BorderLayout.CENTER);
		pd.add(jButton_delete,BorderLayout.EAST);
		
		//线程启动
		selectAll.start();
	}
	
	//查询当前项目线程内部类--------------------------------------------------------------------------------
	private class SelectAll extends Thread
	{
		public SelectAll()
		{
			
		}
		public void run()
		{
			while(true)
			{
				try 
				{
					//获得选项
					int select=jBox_select.getSelectedIndex();
					//加载驱动
					Class.forName(driver);
					//连接数据库
					Connection connection=DriverManager.getConnection(url,user,password);
					//链接失败
					if(!connection.isClosed())
						System.out.println("success connecting");///////////////////////////////
					
					//sql 得String声明
					String sqlString;
					if(select==1)
						sqlString="select * from online_work where ifend=100";
					else 
						sqlString="select * from online_work where ifend<>100";
					
					//执行sql语句					
					PreparedStatement ps =connection.prepareStatement(sqlString);
					ResultSet result= ps.executeQuery();
					
					//显示数据的声明
					String w_id,w_name,ifdo,ifend;
					Date s_date,deadline;
					int reward;
					
					//先清空
					jTextArea_selec.setText("");
					jTextArea_selec.append("项目编号\t项目名称\t发布时间\t截止时间\t接领状态\t完成状态\t项目资金\n");
					jTextArea_selec.append("————————————————————————————————————————————————————————————————————————————\n");
				            	
					//再显示，游标查询
					while(result.next())
					{
						//赋值信息
						w_id=result.getString("w_id");
						w_name=result.getString("w_name");
						s_date=result.getDate("s_date");
						deadline=result.getDate("deadline");
						ifdo=result.getString("ifdo");
						ifend=result.getString("ifend");
						reward=result.getInt("reward");
						
						//显示数据
						jTextArea_selec.append(w_id+"\t"+w_name+"\t"+s_date+"\t"+deadline+"\t"+ifdo+"\t"+ifend+"%"+
								"\t"+reward+"\n");
						jTextArea_selec.append("————————————————————————————————————————————————————————————————————————————\n");
						
					}
					
					result.close();
					connection.close();
				} 
				catch (Exception e) 
				{e.printStackTrace();}
				
				//每几秒就一刷新
				try 
				{sleep(selectSleep*1000);} 
				catch (InterruptedException e) 
				{e.printStackTrace();}
			}
		}
	}
	
	//addButtonListener-------------------------------------------------------------------------------
	private class addListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//启动对话框
			Project_AddWork addpProject=new Project_AddWork();
			
			addpProject.setBounds(300, 350, 810, 120);//窗口位置
		}
		
	}
	

	//deleteButtonListener-------------------------------------------------------------------------------
	private class deleteListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
//			//启动对话框
			Project_DeleteWork deleteWindow=new Project_DeleteWork();
			
			deleteWindow.setBounds(400, 350, 620, 100);//窗口位置
		}
		
	}
	
	//changeButtonListener
	private  class changeListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//启动查询对话框
			Project_ChangeWork changewindow=new Project_ChangeWork();
			
			changewindow.setBounds(100, 350, 1220, 120);
			
		}
	}
	
}