package com.online.ui_human;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.online.database_option.DBoption_change;
import com.online.database_option.DBoption_save;
import com.online.database_option.DBoption_search;
import com.online.tool.KeyNumLim;
import com.online.tool.KeySizeLim;

public class Human_LeavePerson extends JFrame
{
	JPanel jPanel_leave;//主面板
	JButton jButton_leaveButton;//按钮离开
	JLabel jLabel_enter;
	JTextField jTextField_inpu;
	DBoption_search dBSearch;
	DBoption_change dBChange;
	
	public Human_LeavePerson()
	{
		//组件声明
		jPanel_leave=new JPanel();
		jButton_leaveButton=new JButton("确定离职");
		jButton_leaveButton.addActionListener(new leaveListener());
		jLabel_enter=new JLabel("请输入离职人的学号：");
		jTextField_inpu=new JTextField(12);
		jTextField_inpu.addKeyListener(new KeySizeLim(jTextField_inpu,com.online.util.Data.inputSizeId));
		jTextField_inpu.addKeyListener(new KeyNumLim());
		
		dBSearch=new DBoption_search();
		dBChange=new DBoption_change();
		
		//添加组件
		jPanel_leave.add(jLabel_enter);
		jPanel_leave.add(jTextField_inpu);
		jPanel_leave.add(jButton_leaveButton);
		add(jPanel_leave);
		
		//善后
		setResizable(true);
		setVisible(true);//可见
		pack();//初始大小合适
		
	}
	
	
	private class leaveListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
 			String id=jTextField_inpu.getText();
 			
 			if(!dBSearch.ExistID(id))
 			{
 				JOptionPane.showMessageDialog(null, "抱歉，没找到你说的在职人员...", "不能这样做~", JOptionPane.NO_OPTION); 
 			}
 			else
 			{
				dBChange.leaveUpdate(id);
				JOptionPane.showMessageDialog(null, "Option Success!", "success", JOptionPane.DEFAULT_OPTION); 
			}
			
		}
		
	}
}
