package com.online.ui_human;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.TabableView;

import com.online.util.Data;


public class Human extends JPanel
{
	JComboBox jBox_select;//选择查询校区
	JComboBox jBox_select_lev;//选择离职否
	JTextArea jTextArea_selec;//显示屏
	JButton jButton_add;//添加人员
	JButton jButton_change;//改变信息
	JButton jButton_delete;//离职人员
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
	public Human()
	{
		
		//布局
		setLayout (new BorderLayout());
		pu=new JPanel();
		pu.setLayout(new BorderLayout());
		pd=new JPanel();
		
		//线程
		selectAll=new SelectAll();
		
		//尺寸
		setPreferredSize(new Dimension (1000,500));
		
		//选择框
		jBox_select=new JComboBox();
		jBox_select.addItem("中心校区");
		jBox_select.addItem("洪家楼校区");
		jBox_select.addItem("兴隆山校区");
		jBox_select.addItem("软件园校区");
		
		jBox_select_lev=new JComboBox();
		jBox_select_lev.addItem("在职人员");
		jBox_select_lev.addItem("老成员");
		
		//显示屏
		jTextArea_selec=new JTextArea();
		jTextArea_selec.setEditable(false);
		JScrollPane sp=new JScrollPane(jTextArea_selec);
		
		//添加人员按钮
		jButton_add=new JButton("有人加入!~");
		jButton_add.addActionListener(new addListener());
		//改变信息按钮
		jButton_change=new JButton("改变现有信息");
		jButton_change.addActionListener(new changeListener());
		//离职人员按钮
		jButton_delete=new JButton("有人离职...");
		jButton_delete.addActionListener(new deleteListener());
		
		//添加组件
		pu.add(jBox_select,BorderLayout.CENTER);
		pu.add(jBox_select_lev,BorderLayout.EAST);
		
		this.add(pu,BorderLayout.NORTH);
		this.add(sp,BorderLayout.CENTER);
		this.add(pd,BorderLayout.SOUTH);
		pd.add(jButton_add,BorderLayout.WEST);
		pd.add(jButton_change,BorderLayout.CENTER);
		pd.add(jButton_delete,BorderLayout.EAST);
		
		//线程启动
		selectAll.start();
	}
	
	//查询当前人员线程内部类--------------------------------------------------------------------------------
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
					String select=jBox_select.getSelectedItem().toString();
					int index=jBox_select_lev.getSelectedIndex();
					String sql_short = null;
					//加载驱动
					Class.forName(driver);
					//连接数据库
					Connection connection=DriverManager.getConnection(url,user,password);
					//链接失败
					if(!connection.isClosed())
						System.out.println("success connecting");///////////////////////////////
					
					//在职离职的分离
					if(index==0)
						sql_short="and leavedate is null";
					if(index==1)
						sql_short="and leavedate is not null";
					//执行sql语句					
					PreparedStatement ps =connection.prepareStatement("select * from online_staff where district=? "+sql_short);
					ps.setString(1, select);
//					ps.setString(2, sql_short);
					ResultSet result= ps.executeQuery();
					
					//显示数据的声明
					String id,name,sex,phone,qq,email,district,department = "",position = "";
					Date joindate,leavedate;
					
					//先清空
					jTextArea_selec.setText("");
					jTextArea_selec.append("学号\t姓名\t性别\t电话\tqq\t电子邮箱地址\t校区\t部门\t职务\t加入时间\t离职时间\n");
					jTextArea_selec.append("————————————————————————————————————————————————————————————————————————————\n");
					
					//再显示，游标查询
					while(result.next())
					{
						//赋值信息
						id=result.getString("id");
						name=result.getString("name");
						sex=result.getString("sex");
						phone=result.getString("phone");
						qq=result.getString("qq");
						email=result.getString("email");
						district=result.getString("district");
						joindate=result.getDate("joindate");
						leavedate=result.getDate("leavedate");
						
						System.out.println(1+"  "+joindate+"   "+leavedate);
						
						//查询部门
						PreparedStatement ps2 =connection.prepareStatement
						( "SELECT d_name FROM online_department,online_department_in WHERE online_department.d_id=online_department_in.d_id AND online_department_in.id=?");
						ps2.setString(1, id);
						ResultSet result2= ps2.executeQuery();
						while (result2.next()) 
						{
							department=result2.getString("d_name");
						}
						
						//查询职务
						PreparedStatement ps3 =connection.prepareStatement
					    ( "SELECT p_name FROM online_position,online_position_in WHERE online_position.p_id=online_position_in.p_id AND online_position_in.id=?");
						ps3.setString(1, id);
						ResultSet result3= ps3.executeQuery();
						while (result3.next()) 
						{
							position=result3.getString("p_name");
						}
						
						//显示数据
						jTextArea_selec.append(id+"\t"+name+"\t"+sex+"\t"+phone+"\t"+qq+"\t"+email+
								"\t"+district+"\t"+department+"\t"+position+"\t"+joindate+"\t"+leavedate+"\n");
						jTextArea_selec.append("————————————————————————————————————————————————————————————————————————————\n");
						
						result2.close();
						result3.close();
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
			Human_AddPerson addWindows=new Human_AddPerson();
			
			addWindows.setBounds(50, 350, 1320, 120);//窗口位置
		}
		
	}
	

	//deleteButtonListener-------------------------------------------------------------------------------
	private class deleteListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//启动对话框
			Human_LeavePerson leaveWindow=new Human_LeavePerson();
			
			leaveWindow.setBounds(550, 50, 320, 120);//窗口位置
		}
		
	}
	
	//changeButtonListener
	private  class changeListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//启动查询对话框
			Human_ChangePerson changewindow=new Human_ChangePerson();
			
			changewindow.setBounds(50, 350, 1320, 120);
			
		}
	}
	
}


