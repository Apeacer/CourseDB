package com.online.ui_human;

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

import com.mysql.jdbc.Util;
import com.online.database_option.DBoption_change;
import com.online.database_option.DBoption_save;
import com.online.database_option.DBoption_search;
import com.online.tool.KeyNumLim;
import com.online.tool.KeySizeLim;

//添加人员对话框类------------------------------------------------------------------------------------------
class Human_AddPerson extends JFrame
{
	//驱动名、URL、user name、password
	String driver=com.online.util.Data.driver;
	String url=com.online.util.Data.url;
	String user=com.online.util.Data.user;
	String password=com.online.util.Data.password;
	//主面板
	JPanel allPanel;
	//组件面板
	JPanel addPeople,buttonPanel;
	
	//组件
	JLabel jLable_id,jLable_name,jLable_sex,jLable_phone,jLable_qq,jLable_email,jLable_district,jLable_department,jLable_position,jLable_joindate;
	JTextField jTextField_id,jTextField_name,jTextField_phone,jTextField_qq,jTextField_email;
	JPanel jPanel_id,jPanel_name,jPanel_sex,jPanel_phone,jPanel_qq,jPanel_email,jPanel_district,jPanel_department,jPanel_position,jPanel_joindate;
	JComboBox jComboBox_sex,jComboBox_district,jComboBox_position,jComboBox_department;
	JFormattedTextField jFormattedTextField_joindate;
	JButton jButton_ensure,jButton_clear;
	//构造类
	public Human_AddPerson() 
	{
		//面板
		allPanel=new JPanel();
		addPeople=new JPanel();
		buttonPanel=new JPanel();
		allPanel.setPreferredSize(new Dimension (1220, 250));
		allPanel.setLayout(new BorderLayout());
		
		try 
		{
			//加载驱动
			Class.forName(driver);
			//连接数据库
			Connection connection=DriverManager.getConnection(url,user,password);
		
			//组件id
			jPanel_id=new JPanel();
			jPanel_id.setLayout(new BorderLayout());
			jLable_id=new JLabel("学号:");
			jTextField_id=new JTextField(10);
			jTextField_id.addKeyListener(new KeyNumLim());//限数字
			jTextField_id.addKeyListener(new KeySizeLim(jTextField_id,com.online.util.Data.inputSizeId));//限制字数
			jPanel_id.add(jLable_id,BorderLayout.WEST);
			jPanel_id.add(jTextField_id,BorderLayout.CENTER);
			
			//组件name
			jPanel_name=new JPanel();
			jPanel_name.setLayout(new BorderLayout());
			jLable_name=new JLabel("姓名:");
			jTextField_name=new JTextField(5);
			jTextField_name.addKeyListener(new KeySizeLim(jTextField_name, com.online.util.Data.inputSizeName));//限制字数
			jPanel_name.add(jLable_name,BorderLayout.WEST);
			jPanel_name.add(jTextField_name,BorderLayout.CENTER);
			
			//组件sex
			jPanel_sex=new JPanel();
			jComboBox_sex=new JComboBox();
			jPanel_sex.setLayout(new BorderLayout());
			jLable_sex=new JLabel("性别:");
			jComboBox_sex.addItem("男");
			jComboBox_sex.addItem("女");
			jPanel_sex.add(jLable_sex,BorderLayout.WEST);
			jPanel_sex.add(jComboBox_sex,BorderLayout.CENTER);
			
			//组件phone
			jPanel_phone=new JPanel();
			jPanel_phone.setLayout(new BorderLayout());
			jLable_phone=new JLabel("电话:");
			jTextField_phone=new JTextField(10);
			jTextField_phone.addKeyListener(new KeySizeLim(jTextField_phone, com.online.util.Data.inputSizePhone));//限制字数
			jTextField_phone.addKeyListener(new KeyNumLim());
			jTextField_phone.addKeyListener(new KeyNumLim());
			jPanel_phone.add(jLable_phone,BorderLayout.WEST);
			jPanel_phone.add(jTextField_phone,BorderLayout.CENTER);
			
			//组件qq
			jPanel_qq=new JPanel();
			jPanel_qq.setLayout(new BorderLayout());
			jLable_qq=new JLabel("qq号:");
			jTextField_qq=new JTextField(10);
			jTextField_qq.addKeyListener(new KeySizeLim(jTextField_qq, com.online.util.Data.inputSizeQQ));//限制字数
			jTextField_qq.addKeyListener(new KeyNumLim());
			jPanel_qq.add(jLable_qq,BorderLayout.WEST);
			jPanel_qq.add(jTextField_qq,BorderLayout.CENTER);
			
			//组件email
			jPanel_email=new JPanel();
			jPanel_email.setLayout(new BorderLayout());
			jLable_email=new JLabel("email号:");
			jTextField_email=new JTextField(10);
				jTextField_email.addKeyListener(new KeySizeLim(jTextField_email, com.online.util.Data.inputSizeEmail));//限制字数
				jPanel_email.add(jLable_email,BorderLayout.WEST);
			jPanel_email.add(jTextField_email,BorderLayout.CENTER);
			
			//组件district
			jPanel_district=new JPanel();
			jComboBox_district=new JComboBox();
			jPanel_district.setLayout(new BorderLayout());
			jLable_district=new JLabel("校区:");
			jComboBox_district.addItem("中心校区");
			jComboBox_district.addItem("洪家楼校区");
			jComboBox_district.addItem("兴隆山校区");
			jComboBox_district.addItem("软件园校区");
			jPanel_district.add(jLable_district,BorderLayout.WEST);
			jPanel_district.add(jComboBox_district,BorderLayout.CENTER);
			
			//组件department
			//sql
			PreparedStatement ps1 =connection.prepareStatement("select d_name from online_department");
			ResultSet result1= ps1.executeQuery();
			//组件
			jPanel_department=new JPanel();
			jComboBox_department=new JComboBox();
			jPanel_department.setLayout(new BorderLayout());
			jLable_department=new JLabel("部门:");
			while(result1.next())
			{
				jComboBox_department.addItem(result1.getString("d_name"));
			}
			jPanel_department.add(jLable_department,BorderLayout.WEST);
			jPanel_department.add(jComboBox_department,BorderLayout.CENTER);
			
			//组件position
			//sql
			PreparedStatement ps2 =connection.prepareStatement("select p_name from online_position");
			ResultSet result2= ps2.executeQuery();
			jPanel_position=new JPanel();
			jComboBox_position=new JComboBox();
			jPanel_position.setLayout(new BorderLayout());
			jLable_position=new JLabel("职务:");
			while(result2.next())
			{
				jComboBox_position.addItem(result2.getString("p_name"));
			}
			jPanel_position.add(jLable_position,BorderLayout.WEST);
			jPanel_position.add(jComboBox_position,BorderLayout.CENTER);
			
			//组件joindate
			jPanel_joindate=new JPanel();
			jPanel_joindate.setLayout(new BorderLayout());
			jLable_joindate=new JLabel("加入日期：");
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-mm-dd");//设置日期格式
			jFormattedTextField_joindate=new JFormattedTextField(dateFormat.getDateInstance());
			jFormattedTextField_joindate.addKeyListener(new KeyNumLim());
			jFormattedTextField_joindate.setValue(new java.util.Date());
			jFormattedTextField_joindate.setColumns(7);
			jPanel_joindate.add(jLable_joindate,BorderLayout.WEST);
			jPanel_joindate.add(jFormattedTextField_joindate,BorderLayout.CENTER);
			
			//两个按钮的声明和监听
			jButton_ensure=new JButton("确定入伙");
			jButton_clear=new JButton("清空输入");
			jButton_ensure.addActionListener(new ensureListener());
			jButton_clear.addActionListener(new clearListener());
			
			result2.close();
			result1.close();
			connection.close();
		
		} 
		catch (Exception e) 
		{e.printStackTrace();}
		
		//添加组件
		//input添加啊到面板1
		addPeople.add(jPanel_id);
		addPeople.add(jPanel_name);
		addPeople.add(jPanel_sex);
		addPeople.add(jPanel_phone);
		addPeople.add(jPanel_qq);
		addPeople.add(jPanel_email);
		addPeople.add(jPanel_district);
		addPeople.add(jPanel_department);
		addPeople.add(jPanel_position);
		addPeople.add(jPanel_joindate);
		//按钮添加到面板2
		buttonPanel.add(jButton_ensure);
		buttonPanel.add(jButton_clear);
		//面板添加到主面板
		allPanel.add(addPeople,BorderLayout.CENTER);
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
			String id=jTextField_id.getText();
			String name=jTextField_name.getText();
			String sex=jComboBox_sex.getSelectedItem().toString();
			String phone=jTextField_phone.getText();
			String qq=jTextField_qq.getText();
			String email=jTextField_email.getText();
			String district=jComboBox_district.getSelectedItem().toString();
			String department=jComboBox_department.getSelectedItem().toString();
			String position=jComboBox_position.getSelectedItem().toString();
			String joindate=jFormattedTextField_joindate.getText();
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟   
//			try 
//			{
//				java.util.Date joindate=sdf.parse(joindateS);
//			} 
//			catch (ParseException e1) {e1.printStackTrace();}
			if(id.equals("")||name.equals("")||phone.equals("")||joindate.equals(""))
			{
				JOptionPane.showMessageDialog(null, "学号、姓名、电话和加入日期不能为空呀~", "不能这样做~", JOptionPane.NO_OPTION); 
			}
			if(new DBoption_search().ExistID(id))
			{
				JOptionPane.showMessageDialog(null, "此人已到学线一游~", "不能这样做~", JOptionPane.NO_OPTION); 
			}
			else 
			{
				//保存
				DBoption_save dBsave=new DBoption_save();
				DBoption_search dBsearch=new DBoption_search();
				DBoption_change dBchange=new DBoption_change();
				
				dBsave.staffSave(id, name, sex, phone, qq, email, district, joindate);	
				dBsave.departmentSave(id, dBsearch.DepartmentToDepartmentId(department));
				dBsave.positionSave(id, dBsearch.PositionToPositionId(position));
				
				//默认学号密码
				dBchange.passwordUpdate(id, id);
				
				//提示成功
				JOptionPane.showMessageDialog(null, "Option Success!", "success", JOptionPane.DEFAULT_OPTION); 
				
			}
			
		}
		
	}
	//清空按钮的监听
	private class clearListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
			jTextField_id.setText("");
			jTextField_name.setText("");
			jTextField_phone.setText("");
			jTextField_qq.setText("");
			jTextField_email.setText("");
			jFormattedTextField_joindate.setValue(new java.util.Date());
		}
		
	}
	
}
