package com.online.ui_project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.online.database_option.DBoption_change;
import com.online.database_option.DBoption_search;
import com.online.tool.KeyNumLim;
import com.online.tool.KeySizeLim;
import com.online.ui_human.Human_ChangePerson;


public class Project_ChangeWork extends JFrame
{
	//驱动名、URL、user name、password
	String driver=com.online.util.Data.driver;
	String url=com.online.util.Data.url;
	String user=com.online.util.Data.user;
	String password=com.online.util.Data.password;
	//主面板
	JPanel allPanel;
	//组件面板
	JPanel changePanel,buttonPanel;
	
	//操作id
	String idr="";
	String wid="";
	String idn="";
	String wname;
//	String startDate;
	String deadline;
	int reward;
	
	String idf="";
	String widf="";
	String wnamef;
	String deadlinef;
	int rewardf;
	
	//组件
	JLabel jLable_wid,jLable_wname,jLable_deadline,jLable_reward;
	JTextField jTextField_wid,jTextField_wname,jTextField_reward;
	JPanel jPanel_wid,jPanel_wname,jPanel_deadline,jPanel_reward;
	JFormattedTextField jFormattedTextField_deadline;
	JButton jButton_ensure,jButton_clear;
	
	DBoption_search dBsearch;
	DBoption_change dBchange;
	
	//构造方法构建界面-------------------------------------------------------------------------------------
	public Project_ChangeWork()
	{
		//面板
		allPanel=new JPanel();
		changePanel=new JPanel();
		buttonPanel=new JPanel();
		allPanel.setPreferredSize(new Dimension (1220, 250));
		allPanel.setLayout(new BorderLayout());
		
		dBsearch=new DBoption_search();
		dBchange=new DBoption_change();
		
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
			
			//组件id
			jPanel_wid=new JPanel();
			jPanel_wid.setLayout(new BorderLayout());
			jLable_wid=new JLabel("项目编号:");
			jTextField_wid=new JTextField(10);
			jTextField_wid.addKeyListener(new KeyNumLim());
			jTextField_wid.addKeyListener(new KeySizeLim(jTextField_wid, com.online.util.Data.inputSizeWId));
			jPanel_wid.add(jLable_wid,BorderLayout.WEST);
			jPanel_wid.add(jTextField_wid,BorderLayout.CENTER);
			
			//组件name
			jPanel_wname=new JPanel();
			jPanel_wname.setLayout(new BorderLayout());
			jLable_wname=new JLabel("项目姓名:");
			jTextField_wname=new JTextField(10);
			jTextField_wname.addKeyListener(new KeySizeLim(jTextField_wname, com.online.util.Data.inputSizeWname));
			jPanel_wname.add(jLable_wname,BorderLayout.WEST);
			jPanel_wname.add(jTextField_wname,BorderLayout.CENTER);
			
			//组件deadline
			jPanel_deadline=new JPanel();
			jPanel_deadline.setLayout(new BorderLayout());
			jLable_deadline=new JLabel("截止日期：");
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd");//设置日期格式
			jFormattedTextField_deadline=new JFormattedTextField(dateFormat.getDateInstance());
			jFormattedTextField_deadline.setValue(new java.util.Date());
			jFormattedTextField_deadline.setColumns(7);
			jPanel_deadline.add(jLable_deadline,BorderLayout.WEST);
			jPanel_deadline.add(jFormattedTextField_deadline,BorderLayout.CENTER);
			
			//组件reward
			jPanel_reward=new JPanel();
			jPanel_reward.setLayout(new BorderLayout());
			jLable_reward=new JLabel("资金:");
			jTextField_reward=new JTextField(10);
			jTextField_reward.addKeyListener(new KeyNumLim());
			jTextField_reward.addKeyListener(new KeySizeLim(jTextField_reward, com.online.util.Data.inputSizeReward));
			jPanel_reward.add(jLable_reward,BorderLayout.WEST);
			jPanel_reward.add(jTextField_reward,BorderLayout.CENTER);
			
			
			

			
			//两个按钮的声明和监听
			jButton_ensure=new JButton("确定修改");
			jButton_clear=new JButton("还原输入");
			jButton_ensure.addActionListener(new ensureListener());
			jButton_clear.addActionListener(new rollbackListener());
			
			//关闭界面
			connection.close();
			
		} 
		catch (Exception e) 
		{e.printStackTrace();}
		
		//添加组件
		//input添加啊到面板1
		changePanel.add(jPanel_wid);
		changePanel.add(jPanel_wname);
		changePanel.add(jPanel_deadline);
		changePanel.add(jPanel_reward);

		//按钮添加到面板2
		buttonPanel.add(jButton_ensure);
		buttonPanel.add(jButton_clear);
		//面板添加到主面板
		allPanel.add(changePanel,BorderLayout.CENTER);
		allPanel.add(buttonPanel,BorderLayout.SOUTH);
		add(allPanel);
		
		//善后
		setResizable(true);
		setVisible(true);//可见
		pack();//初始大小合适

		//弹出判断更改人员的对话框
		this.setEnabled(false);
		ifIdWindow ifdid=new ifIdWindow();
		ifdid.setBounds(510, 350, 400, 100);
		ifdid.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e)
			{
				Project_ChangeWork.this.setVisible(false);
			}
			
		});
	}

	
	
	
	
	//确定按钮监听
	private class ensureListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
			
			
			//获得新的各种数据
			idn=jTextField_wid.getText();
			String namen=jTextField_wname.getText();
			String deadlinen=jFormattedTextField_deadline.getText().toString();
			int rewardn=Integer.parseInt(jTextField_reward.getText());
			
			System.out.println(1+" "+idn+" "+namen);
			
			//判断不能为空项
			if(idn.equals("")||namen.equals(""))
			{
				JOptionPane.showMessageDialog(null, "项目编号、项目姓名不能为空~", "不能这样做~", JOptionPane.NO_OPTION); 
			}
			else 
			{
				//更新
				System.out.println(2+" "+idn+" "+namen);

				dBchange.workUpdate(wid, idn, namen, deadlinen, rewardn);	
				wid=idn;
			}
			
		}
		
	}
	
	
	
	
	
	//撤销按钮监听
	private class rollbackListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
			//滚回结果
			jTextField_wid.setText(widf);
			jTextField_wname.setText(wnamef);
			jFormattedTextField_deadline.setText(deadlinef);
			jTextField_reward.setText(rewardf+"");
			
		}
		
	}
	
	
	
	
	
	//先弹出窗口------------------------------------------------------------------------------------
	private class ifIdWindow extends JFrame
	{
		JPanel jPanel_select;//主面板
		JButton jButton_selectButton;//按钮离开
		JLabel jLabel_enter;
		JTextField jTextField_inpu;

		
		public ifIdWindow() 
		{
			
			//
			Project_ChangeWork.this.setEnabled(false);
			//组件各种声明
			jPanel_select=new JPanel();
			jTextField_inpu=new JTextField(12);
			jTextField_inpu.addKeyListener(new KeyNumLim());
			jTextField_inpu.addKeyListener(new KeySizeLim(jTextField_inpu, com.online.util.Data.inputSizeWId));
			jLabel_enter=new JLabel("请输入要更改信息的项目编号：");

			jButton_selectButton=new JButton("确定");
			jButton_selectButton.addActionListener(new selectListener());
			
			
			//添加组件
			jPanel_select.add(jLabel_enter);
			jPanel_select.add(jTextField_inpu);
			jPanel_select.add(jButton_selectButton);
			add(jPanel_select);
			
			//善后
			setResizable(true);
			setVisible(true);//可见
			pack();//初始大小合适
		}
		
		//用于弹出框按钮的监听
		private class selectListener implements ActionListener
		{
			//声明寻找类
			
			
			public void actionPerformed(ActionEvent e) 
			{
				//得到输入的id号
	 			wid=jTextField_inpu.getText();
	 			idr=wid;
	 			
	 			//判断id是否存在
	 			if(!dBsearch.ExistWID(wid))
	 			{
	 				JOptionPane.showMessageDialog(null, "抱歉，没找到你说的未完成项目...", "不能这样做~", JOptionPane.NO_OPTION); 
	 			}
	 			else
	 			{
	 				//预填信息！！！！1
	 				try 
	 				{
						//加载驱动
						Class.forName(driver);
						//连接数据库
						Connection connection=DriverManager.getConnection(url,user,password);
	 					//执行sql语句					
	 					PreparedStatement ps =connection.prepareStatement("select * from online_work where w_id=?");
	 					ps.setString(1, wid);
	 					ResultSet result= ps.executeQuery();
	 					
	 					while(result.next())
	 					{
	 						//从结果集中得到各种基本结果
							wname=result.getString("w_name");
//							startDate=result.getString("start");
							deadline=result.getString("deadline");
							reward=result.getInt("reward");
							
							
							//将得到的结果反映到修改框中
							jTextField_wid.setText(wid);
							jTextField_wname.setText(wname);
							jTextField_reward.setText(reward+"");
							jFormattedTextField_deadline.setValue(deadline);
//							System.out.println(reward);
							
	 					}
	 					
	 					result.close();
	 					connection.close();
						
					} 
	 				catch (Exception e2) {}
	 				
	 				//得到原来数据
//	 				idf=id;
	 				widf=wid;
	 				wnamef=wname;
	 				deadlinef=deadline;
	 				int rewardf;
	 				//使父窗口可用
	 				Project_ChangeWork.this.setEnabled(true);
	 				//并关掉该窗口
	 				dispose();
				}
				
			}
			
		

		}
		
	}

}




