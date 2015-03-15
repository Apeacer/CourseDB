package com.online.ui_manager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.online.database_option.DBoption_change;
import com.online.database_option.DBoption_save;
import com.online.database_option.DBoption_search;
import com.online.tool.KeyNumLim;
import com.online.tool.KeySizeLim;

public class Manager_password_change extends JFrame
{
	//声名
	JLabel jLabel_opassword;
	JLabel jLabel_npassword;
	JLabel jLabel_apassword;

	JPasswordField jTextField_opassword;
	JPasswordField jTextField_npassword;
	JPasswordField jTextField_apassword;

	JPanel jPanel_opassword;
	JPanel jPanel_npassword;
	JPanel jPanel_apassword;

	JButton jButton_login;
	
	DBoption_search dBsearch;
	DBoption_change dBchange;
	
	public Manager_password_change()
	{
		//登陆界面
		JPanel allJPanel=new JPanel();
		
		jLabel_opassword=new JLabel("请输入旧密码：");
		jLabel_npassword=new JLabel("请输入新密码：");
		jLabel_apassword=new JLabel("再确认新密码：");
		
		jTextField_opassword=new JPasswordField(12);
		jTextField_opassword.addKeyListener(new KeyNumLim());
		jTextField_opassword.addKeyListener(new KeySizeLim(jTextField_opassword, com.online.util.Data.inputSizeId));
		
		jTextField_npassword=new JPasswordField(12);
		jTextField_npassword.addKeyListener(new KeyNumLim());
		jTextField_npassword.addKeyListener(new KeySizeLim(jTextField_npassword, com.online.util.Data.inputSizeId));
		
		jTextField_apassword=new JPasswordField(12);
		jTextField_apassword.addKeyListener(new KeyNumLim());
		jTextField_apassword.addKeyListener(new KeySizeLim(jTextField_apassword, com.online.util.Data.inputSizeId));
		
		jPanel_opassword=new JPanel();
		jPanel_opassword.setLayout(new BorderLayout());
		jPanel_npassword=new JPanel();
		jPanel_npassword.setLayout(new BorderLayout());
		jPanel_apassword=new JPanel();
		jPanel_apassword.setLayout(new BorderLayout());
		
		jButton_login=new JButton("确认");
		jButton_login.addActionListener(new pcListener());
		
		jPanel_opassword.add(jLabel_opassword,BorderLayout.WEST);
		jPanel_opassword.add(jTextField_opassword,BorderLayout.EAST);
		jPanel_npassword.add(jLabel_npassword,BorderLayout.WEST);
		jPanel_npassword.add(jTextField_npassword,BorderLayout.EAST);
		jPanel_apassword.add(jLabel_apassword,BorderLayout.WEST);
		jPanel_apassword.add(jTextField_apassword,BorderLayout.EAST);

		dBsearch=new DBoption_search();
		dBchange=new DBoption_change();
		
		//添加
		allJPanel.add(jPanel_opassword);
		allJPanel.add(jPanel_npassword);
		allJPanel.add(jPanel_apassword);
		allJPanel.add(jButton_login);
		this.add(allJPanel);
		//善后
		setResizable(true);
		setVisible(true);//可见
		pack();//初始大小合适
		
	}
	
	private class pcListener implements ActionListener
	{
		//search
		DBoption_search dBsearch=new DBoption_search();
		
		public void actionPerformed(ActionEvent e) 
		{
			//得到信息
			String passwordOld=jTextField_opassword.getText();
			String passwordNew=jTextField_npassword.getText();
			String passwordAgain=jTextField_apassword.getText();
			
			//输入不为空判断
			if(passwordOld.equals("")||passwordNew.equals("")||passwordAgain.equals(""))
			{
				JOptionPane.showMessageDialog(null, "输入不能为空***", "不能这样做~", JOptionPane.NO_OPTION);
			}
			else
			{
				if(!passwordNew.equals(passwordAgain))
				{
					JOptionPane.showMessageDialog(null, "两次不一样***", "不能这样做~", JOptionPane.NO_OPTION);
				}
				else
				{
					//登陆判断
					if(dBsearch.Login(com.online.util.Data.userId, passwordOld))
					{
						
						dBchange.PasswordToNpassword(passwordNew);
						JOptionPane.showMessageDialog(null, "*操作成功*", "不能这样做~", JOptionPane.NO_OPTION);
						com.online.util.Data.userPassword=passwordNew;
						
					}
					else 
					{
						JOptionPane.showMessageDialog(null, "是你操作么？", "不能这样做~", JOptionPane.NO_OPTION);
					}
					
				}
				
			}
			
		}
		
	}
}
