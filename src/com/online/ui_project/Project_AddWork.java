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
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.online.database_option.DBoption_save;
import com.online.database_option.DBoption_search;
import com.online.tool.KeyNumLim;
import com.online.tool.KeySizeLim;

//添加项目对话框类------------------------------------------------------------------------------------------
public class Project_AddWork extends JFrame
{
	//驱动名、URL、user name、password
	String driver=com.online.util.Data.driver;
	String url=com.online.util.Data.url;
	String user=com.online.util.Data.user;
	String password=com.online.util.Data.password;
	//主面板
	JPanel allPanel;
	//组件面板
	JPanel addWork,buttonPanel;
	
	//组件
	JLabel jLable_wid,jLable_wname,jLable_startdate,jLable_deadline,jLable_reward;
	JTextField jTextField_wid,jTextField_wname,jTextField_reward;
	JPanel jPanel_wid,jPanel_wname,jPanel_startdate,jPanel_deadline,jPanel_reward;
	JFormattedTextField jFormattedTextField_startdate,jFormattedTextField_deadline;
	JButton jButton_ensure,jButton_clear;
	//构造类
	public Project_AddWork()
	{
		//面板
		allPanel=new JPanel();
		addWork=new JPanel();
		buttonPanel=new JPanel();
		allPanel.setPreferredSize(new Dimension (1220, 250));
		allPanel.setLayout(new BorderLayout());
		
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
		
			//组件wid
			jPanel_wid=new JPanel();
			jPanel_wid.setLayout(new BorderLayout());
			jLable_wid=new JLabel("项目编号:");
			jTextField_wid=new JTextField(10);
			jTextField_wid.addKeyListener(new KeySizeLim(jTextField_wid,com.online.util.Data.inputSizeWId));
			jTextField_wid.addKeyListener(new KeyNumLim());
			jPanel_wid.add(jLable_wid,BorderLayout.WEST);
			jPanel_wid.add(jTextField_wid,BorderLayout.CENTER);
			
			//组件name
			jPanel_wname=new JPanel();
			jPanel_wname.setLayout(new BorderLayout());
			jLable_wname=new JLabel("项目名称:");
			jTextField_wname=new JTextField(10);
			jTextField_wname.addKeyListener(new KeySizeLim(jTextField_wname,com.online.util.Data.inputSizeWname));
			jPanel_wname.add(jLable_wname,BorderLayout.WEST);
			jPanel_wname.add(jTextField_wname,BorderLayout.CENTER);

			//发布时间

			
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
			jTextField_reward=new JTextField(5);
			jTextField_reward.addKeyListener(new KeySizeLim(jTextField_reward,com.online.util.Data.inputSizeReward));
			jTextField_reward.addKeyListener(new KeyNumLim());
			jPanel_reward.add(jLable_reward,BorderLayout.WEST);
			jPanel_reward.add(jTextField_reward,BorderLayout.CENTER);
			


			
			//两个按钮的声明和监听
			jButton_ensure=new JButton("确定添加");
			jButton_clear=new JButton("清空输入");
			jButton_ensure.addActionListener(new ensureListener());
			jButton_clear.addActionListener(new clearListener());
			
			connection.close();
		
		} 
		catch (Exception e) 
		{e.printStackTrace();}
		
		//添加组件
		//input添加啊到面板1
		addWork.add(jPanel_wid);
		addWork.add(jPanel_wname);
		addWork.add(jPanel_deadline);
		addWork.add(jPanel_reward);

		//按钮添加到面板2
		buttonPanel.add(jButton_ensure);
		buttonPanel.add(jButton_clear);
		//面板添加到主面板
		allPanel.add(addWork,BorderLayout.CENTER);
		allPanel.add(buttonPanel,BorderLayout.SOUTH);
		add(allPanel);
		
		//善后
		setResizable(true);
		setVisible(true);//可见
		pack();//初始大小合适
	}
	
	//确定添加按钮的监听
	private class ensureListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
			//获得各种数据
			String wid=jTextField_wid.getText();
			String wname=jTextField_wname.getText();

//			String startDate = null;//=new java.util.Date().toString();
//			try {
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
//				java.util.Date data;
//				data = sdf.parse("2006-12-20");
//				data=new java.util.Date();
//				java.sql.Date ssdate=new java.sql.Date(data.getTime());
//				startDate=ssdate.toString();
//			} catch (ParseException e1) {e1.printStackTrace();			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String startDate=formatter.format(new java.util.Date());
			String deadline=jFormattedTextField_deadline.getText();
			String reward=jTextField_reward.getText();


//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟   
//			try 
//			{
//				java.util.Date joindate=sdf.parse(joindateS);
//			} 
//			catch (ParseException e1) {e1.printStackTrace();}
			
			if(wid.equals("")||wname.equals(""))
			{
				JOptionPane.showMessageDialog(null, "任务的编号和名称不能为空呀~", "不能这样做~", JOptionPane.NO_OPTION); 
			}
			else
			{
				if(new DBoption_search().ExistWID(wid))
				{
					//弹出对话框
					JOptionPane.showMessageDialog(null, "此项目编号已被用过~", "不能这样做~", JOptionPane.NO_OPTION); 
				}
				else 
				{
					//保存
					DBoption_save dBsave=new DBoption_save();
					DBoption_search dBsearch=new DBoption_search();
					
					dBsave.workSave(wid, wname, startDate, deadline, reward);
					
					//提示成功
					JOptionPane.showMessageDialog(null, "Option Success!", "success", JOptionPane.DEFAULT_OPTION); 
					
				}
				
			}
			
		}
		
	}
	//清空按钮的监听
	private class clearListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
			jTextField_wid.setText("");
			jTextField_wname.setText("");
			jFormattedTextField_deadline.setValue(new java.util.Date());
			jTextField_reward.setText("");
		}
		
	}
	
}
