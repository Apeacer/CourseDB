package com.online.ui_manager;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
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
import com.online.ui_human.Human_ChangePerson;



public class Manager_orgin extends JFrame
{
	
	//驱动名、URL、user name、password
	String driver=com.online.util.Data.driver;
	String url=com.online.util.Data.url;
	String user=com.online.util.Data.user;
	String password=com.online.util.Data.password;
	//主面板
	JPanel allPanel;
	//组件面板
	JPanel addTaskPanel;
	JPanel buttonPanel;
	
	//组件
	JPanel jPanel_id,jPanel_d_work,jPanel_reward;
	JLabel jLable_id,jLable_wid,jLable_d_work,jLable_reward;
	JTextField jTextField_id,jTextField_reward,jTextField_d_work;
	JButton jButton_ensure,jButton_clear;
//	TextField jTextField_wid;
	//jPanoel_wid,
	
	DBoption_change dBchange;
	DBoption_search dBsearch;
	DBoption_save dBsave;
	String id="";
	String wid="";
	
	
	JTextField task;
	//构造类
	public Manager_orgin()
	{
		//面板
		allPanel=new JPanel();
		addTaskPanel=new JPanel();
		buttonPanel=new JPanel();
		allPanel.setPreferredSize(new Dimension (1220, 250));
		allPanel.setLayout(new BorderLayout());
		task=new JTextField(12);
		task.setEditable(false);
		
		dBchange=new DBoption_change();
		dBsearch=new DBoption_search();
		dBsave=new DBoption_save();
		
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
		
			//组件id
			jPanel_id=new JPanel();
			jPanel_id.setLayout(new BorderLayout());
			jLable_id=new JLabel("人员学号:");
			jTextField_id=new JTextField(10);
			jTextField_id.addKeyListener(new KeySizeLim(jTextField_id,com.online.util.Data.inputSizeId));
			jTextField_id.addKeyListener(new KeyNumLim());
			jPanel_id.add(jLable_id,BorderLayout.WEST);
			jPanel_id.add(jTextField_id,BorderLayout.CENTER);
			
//			//组件wid
//			jPanel_wid=new JPanel();
//			jPanel_wid.setLayout(new BorderLayout());
//			jLable_wid=new JLabel("项目编号:");
//			jTextField_wid=new JTextField(10);
//			jTextField_wid.addKeyListener(new KeySizeLim(jTextField_wid,com.online.util.Data.inputSizeWId));
//			jTextField_wid.addKeyListener(new KeyNumLim());
//			jPanel_wid.add(jLable_wid,BorderLayout.WEST);
//			jPanel_wid.add(jTextField_wid,BorderLayout.CENTER);
			
			//组件dwork
			jPanel_d_work=new JPanel();
			jPanel_d_work.setLayout(new BorderLayout());
			jLable_d_work=new JLabel("分工:");
			jTextField_d_work=new JTextField(10);
			jTextField_d_work.addKeyListener(new KeySizeLim(jTextField_d_work,com.online.util.Data.inputSizeWname));
			jPanel_d_work.add(jLable_d_work,BorderLayout.WEST);
			jPanel_d_work.add(jTextField_d_work,BorderLayout.CENTER);


			
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
		addTaskPanel.add(task);
		addTaskPanel.add(jPanel_id);
//		addTaskPanel.add(jPanel_wid);
		addTaskPanel.add(jPanel_d_work);
		addTaskPanel.add(jPanel_reward);

		//按钮添加到面板2
		buttonPanel.add(jButton_ensure);
		buttonPanel.add(jButton_clear);
		//面板添加到主面板
		allPanel.add(addTaskPanel,BorderLayout.CENTER);
		allPanel.add(buttonPanel,BorderLayout.SOUTH);
		add(allPanel);
		
		//善后
		setResizable(true);
		setVisible(true);//可见
		pack();//初始大小合适
		
		 searchWork sWork=new searchWork();
		 sWork.setBounds(510,350,400,90);
		 
		 sWork.addWindowListener(new WindowAdapter() {
				
				public void windowClosing(WindowEvent e)
				{
					Manager_orgin.this.setVisible(false);
				}
				
			});
		 
		
	}
	
	
//弹出窗口
	private class searchWork extends JFrame
	{
		JPanel jPanel_select;//主面板
		JButton jButton_selectButton;//按钮离开
		JLabel jLabel_enter;
		JTextField jTextField_inpu;
		
		
		
		public searchWork()
		{
			
			
			
			//
			Manager_orgin.this.setEnabled(false);
			//组件各种声明
			jPanel_select=new JPanel();
			jTextField_inpu=new JTextField(12);
			jTextField_inpu.addKeyListener(new KeyNumLim());
			jTextField_inpu.addKeyListener(new KeySizeLim(jTextField_inpu, com.online.util.Data.inputSizeWId));
			jLabel_enter=new JLabel("请输入要做的项目编号：");
			
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
	 			System.out.println(wid);
	 			//判断id是否存在
	 			if(!dBsearch.ExistWIDnobody(wid))
	 			{
	 				JOptionPane.showMessageDialog(null, "抱歉，没找到你说的未认领项目...", "不能这样做~", JOptionPane.NO_OPTION); 
	 			}
	 			else
	 			{
	 				Manager_orgin.this.setEnabled(true);
	 				task.setText(wid);
	 				dispose();

				}
				
			}
			
		

		}
		
	}//--------------------------------------------------------------
	
	//确定添加按钮的监听
	private class ensureListener implements ActionListener
	{
		
		public void actionPerformed(ActionEvent e) 
		{
			//获得各种数据
			String id=jTextField_id.getText();
			
			if(id.equals(""))
			{
				JOptionPane.showMessageDialog(null, "你自己做吧-_-'","不能这样做",JOptionPane.NO_OPTION);
			}
			else
			{
				if(!dBsearch.ExistID(id))
				{
					JOptionPane.showMessageDialog(null, "学号不存在-_-'","不能这样做",JOptionPane.NO_OPTION);
				}
				else
				{
//					if(dBsearch.ExistIDjod(wid))
//					{
//						JOptionPane.showMessageDialog(null, "他已经在你身边了-_-'","不能这样做",JOptionPane.NO_OPTION);
//					}
//					else 
//					{
						String d_work=jTextField_d_work.getText();
//						String dwork=jTextField_d_work.getText();
						int reward=Integer.parseInt(jTextField_reward.getText());
						int jobid=(dBsearch.maxJobId(wid)+1);
						
						dBsave.jobSave(id, wid, d_work, reward, jobid);
						dBchange.ifdoUpdate(wid);
//					}
					
				}
				
			}

			
		}
		
	}
	//清空按钮的监听
	private class clearListener implements ActionListener
	{
		
		public void actionPerformed(ActionEvent e) 
		{
			jTextField_d_work.setText("");
			jTextField_id.setText("");
			jTextField_reward.setText("");
		}
		
	}
}

	
	
	
	
