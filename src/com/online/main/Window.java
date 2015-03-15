package com.online.main;



import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.online.database_option.DBoption_save;
import com.online.database_option.DBoption_search;
import com.online.tool.KeyNumLim;
import com.online.tool.KeySizeLim;
import com.online.ui_human.*;
import com.online.ui_manager.Manager;
import com.online.ui_project.Project;

public class Window 
{
	
	public static void main (String[] arts)
	{
		
		
		LoginWindow loginWindow=new LoginWindow();
		loginWindow.setBounds(480, 300, 240, 120);

		
		
	}
	


}

class LoginWindow extends JFrame
{
	//声名
	JLabel jLabel_user;
	JLabel jLabel_passward;
	JTextField jTextField_user;
	JPasswordField jTextField_password;
	JPanel jPanel_user;
	JPanel jPanel_password;
	JButton jButton_login;
	
	DBoption_search dBsearch;
	
	public LoginWindow()
	{
		//登陆界面
		JPanel allJPanel=new JPanel();
		
		jLabel_user=new JLabel("请输入学号：");
		jLabel_passward=new JLabel("请输入密码：");
		
		jTextField_user=new JTextField(12);
		jTextField_user.addKeyListener(new KeyNumLim());
		jTextField_user.addKeyListener(new KeySizeLim(jTextField_user, com.online.util.Data.inputSizeId));
		
		jTextField_password=new JPasswordField(12);
		jTextField_password.addKeyListener(new KeyNumLim());
		jTextField_password.addKeyListener(new KeySizeLim(jTextField_password, com.online.util.Data.inputSizeId));
		
		jPanel_user=new JPanel();
		jPanel_user.setLayout(new BorderLayout());
		jPanel_password=new JPanel();
		jPanel_password.setLayout(new BorderLayout());
		
		jButton_login=new JButton("登陆");
		jButton_login.addActionListener(new enterListener());
		
		jPanel_user.add(jLabel_user,BorderLayout.WEST);
		jPanel_user.add(jTextField_user,BorderLayout.EAST);
		jPanel_password.add(jLabel_passward,BorderLayout.WEST);
		jPanel_password.add(jTextField_password,BorderLayout.EAST);

		dBsearch=new DBoption_search();
		
		//添加
		allJPanel.add(jPanel_user);
		allJPanel.add(jPanel_password);
		allJPanel.add(jButton_login);
		this.add(allJPanel);
		//善后
		setResizable(true);
		setVisible(true);//可见
		pack();//初始大小合适
		
		jTextField_user.setText("201200030002");
		jTextField_password.setText("123");
		
	}
	
	private class enterListener implements ActionListener
	{
		//search
		DBoption_search dBsearch=new DBoption_search();
		
		public void actionPerformed(ActionEvent e) 
		{
			//得到信息
			String userString=jTextField_user.getText();
			String passwordString=jTextField_password.getText();
			
			//输入不为空判断
			if(userString.equals("")||passwordString.equals(""))
			{
				JOptionPane.showMessageDialog(null, "学号密码必填***", "不能这样做~", JOptionPane.NO_OPTION);
			}
			else
			{
				//登陆判断
				if(dBsearch.Login(userString, passwordString))
				{
					com.online.util.Data.userId=userString;
					com.online.util.Data.userPassword=passwordString;
					
					int power= dBsearch.IdToPower(com.online.util.Data.userId);
					
					MainWindow window=new MainWindow(power);
					window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//叉子的功能
					window.setBounds(200, 100, 1000, 450);//窗口位置
					dispose();
				}
				
			}
			
		}
		
	}
}

class MainWindow extends JFrame
{
	int power;
	
	public MainWindow(int power)
	{
		this.power=power;
		
		JTabbedPane tab = new JTabbedPane();//多面板切换功能
		tab.addTab("  项目计划  ", new Manager());
		
		if(power==2||power==3)
		tab.addTab("人力资源管理", new Human());//
		if(power==1||power==3)
		tab.addTab("项目信息管理", new Project());//
		
		add(tab);//善后
		setResizable(true);//可变尺寸
		setVisible(true);//可见
		pack();//初始大小合适
	}
	
	
}