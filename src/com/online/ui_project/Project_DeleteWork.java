package com.online.ui_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.online.database_option.DBoption_change;
import com.online.database_option.DBoption_delete;
import com.online.database_option.DBoption_search;
import com.online.tool.KeyNumLim;
import com.online.tool.KeySizeLim;
public class Project_DeleteWork extends JFrame
{
	JPanel jPanel_delete;//按钮离开
	JButton jButton_deleteButton;//按钮离开
	JLabel jLabel_enter;
	JTextField jTextField_inpu;
	DBoption_search dBSearch;
	DBoption_delete dBDelete;
	
	
	public Project_DeleteWork()
	{
		//组件声明
		jPanel_delete=new JPanel();
		jButton_deleteButton=new JButton("确定删除");
		jButton_deleteButton.addActionListener(new deleteListener());
		jLabel_enter=new JLabel("请输入删除任务的编号：");
		jTextField_inpu=new JTextField(12);
		jTextField_inpu.addKeyListener(new KeySizeLim(jTextField_inpu, com.online.util.Data.inputSizeId));
		jTextField_inpu.addKeyListener(new KeyNumLim());
		
		dBSearch=new DBoption_search();
		dBDelete=new DBoption_delete();
		
		//添加组件
		jPanel_delete.add(jLabel_enter);
		jPanel_delete.add(jTextField_inpu);
		jPanel_delete.add(jButton_deleteButton);
		add(jPanel_delete);
		
		//善后
		setResizable(true);
		setVisible(true);//可见
		pack();//初始大小合适
		
	}
	
	
	private class deleteListener implements ActionListener
	{
		
		public void actionPerformed(ActionEvent e) 
		{
			String wid=jTextField_inpu.getText();
			
			if(!dBSearch.ExistWID(wid))
			{
				JOptionPane.showMessageDialog(null, "抱歉，没找到你说的待完成项目", "不能这样做~", JOptionPane.NO_OPTION); 
			}
			else
			{
				dBDelete.workDelete(wid);
				JOptionPane.showMessageDialog(null, "Option Success!", "success", JOptionPane.DEFAULT_OPTION); 
			}
			
		}
		
	}
}
