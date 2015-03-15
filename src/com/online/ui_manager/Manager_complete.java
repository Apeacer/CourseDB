package com.online.ui_manager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.online.database_option.DBoption_change;
import com.online.database_option.DBoption_search;
import com.online.tool.KeyNumLim;
import com.online.tool.KeySizeLim;

public class Manager_complete extends JFrame
{
	JPanel jPanel_complete;//主面板
	JButton jButton_completeButton;//按钮离开
	JLabel jLabel_enter;
	JTextField jTextField_inpu;
	DBoption_search dBSearch;
	DBoption_change dBChange;
	
	public Manager_complete()
	{
		//组件声明
		jPanel_complete=new JPanel();
		jButton_completeButton=new JButton("确定完成");
		jButton_completeButton.addActionListener(new completeListener());
		jLabel_enter=new JLabel("请输入完成的任务编号：");
		jTextField_inpu=new JTextField(10);
		jTextField_inpu.addKeyListener(new KeySizeLim(jTextField_inpu,com.online.util.Data.inputSizeJob));
		jTextField_inpu.addKeyListener(new KeyNumLim());
		
		dBSearch=new DBoption_search();
		dBChange=new DBoption_change();
		
		//添加组件
		jPanel_complete.add(jLabel_enter);
		jPanel_complete.add(jTextField_inpu);
		jPanel_complete.add(jButton_completeButton);
		add(jPanel_complete);
		
		//善后
		setResizable(true);
		setVisible(true);//可见
		pack();//初始大小合适
		
	}
	
	
	private class completeListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
 			int jobid=Integer.parseInt(jTextField_inpu.getText());
 			
 			if(!dBSearch.ExistJID(jobid))
 			{
 				JOptionPane.showMessageDialog(null, "抱歉，没找到你的分工...", "不能这样做~", JOptionPane.NO_OPTION); 
 			}
 			else
 			{
				dBChange.completeUpdate(jobid);
				JOptionPane.showMessageDialog(null, "Option Success!", "success", JOptionPane.DEFAULT_OPTION); 
			}
			
		}
		
	}
}
