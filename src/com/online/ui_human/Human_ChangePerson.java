package com.online.ui_human;

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

import org.omg.CORBA.PUBLIC_MEMBER;

import com.online.database_option.DBoption_change;
import com.online.database_option.DBoption_save;
import com.online.database_option.DBoption_search;
import com.online.tool.KeyNumLim;
import com.online.tool.KeySizeLim;



public class Human_ChangePerson extends JFrame
{
	//驱动名、URL、user name、password
	String driver=com.online.util.Data.driver;
	String url=com.online.util.Data.url;
	String user=com.online.util.Data.user;
	String password=com.online.util.Data.password;
	//主面板
	JPanel allPanel;
	//组件面板
	JPanel changePeople,buttonPanel;
	
	//操作id
	String id="";
	String idn="";
	String name;
	String sex;
	String phone;
	String qq;
	String email;
	String district,department,position;
	Date joindate;
	
	String idf="";
	String namef;
	String sexf;
	String phonef;
	String qqf;
	String emailf;
	String districtf,departmentf,positionf;
	String joindatef;
	
	
	
	//组件
	JLabel jLable_id,jLable_name,jLable_sex,jLable_phone,jLable_qq,jLable_email,jLable_district,jLable_department,jLable_position,jLable_joindate;
	JTextField jTextField_id,jTextField_name,jTextField_phone,jTextField_qq,jTextField_email;
	JPanel jPanel_id,jPanel_name,jPanel_sex,jPanel_phone,jPanel_qq,jPanel_email,jPanel_district,jPanel_department,jPanel_position,jPanel_joindate;
	JComboBox jComboBox_sex,jComboBox_district,jComboBox_position,jComboBox_department;
	JFormattedTextField jFormattedTextField_joindate;
	JButton jButton_ensure,jButton_clear;
	
	//
	DBoption_search dBSearch;
	DBoption_change dBchange;
	
	//构造方法构建界面-------------------------------------------------------------------------------------
	public Human_ChangePerson()
	{
		//面板
		allPanel=new JPanel();
		changePeople=new JPanel();
		buttonPanel=new JPanel();
		allPanel.setPreferredSize(new Dimension (1220, 250));
		allPanel.setLayout(new BorderLayout());
		
		dBSearch=new DBoption_search();
		dBchange=new DBoption_change();
		
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
			jTextField_id.addKeyListener(new KeySizeLim(jTextField_id,com.online.util.Data.inputSizeId));//限制字数
			jTextField_id.addKeyListener(new KeyNumLim());
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
			jButton_ensure=new JButton("确定修改");
			jButton_clear=new JButton("还原输入");
			jButton_ensure.addActionListener(new ensureListener());
			jButton_clear.addActionListener(new rollbackListener());
			
			//关闭界面
			result2.close();
			result1.close();
			connection.close();
			
		} 
		catch (Exception e) 
		{e.printStackTrace();}
		
		//添加组件
		//input添加啊到面板1
		changePeople.add(jPanel_id);
		changePeople.add(jPanel_name);
		changePeople.add(jPanel_sex);
		changePeople.add(jPanel_phone);
		changePeople.add(jPanel_qq);
		changePeople.add(jPanel_email);
		changePeople.add(jPanel_district);
		changePeople.add(jPanel_department);
		changePeople.add(jPanel_position);
		changePeople.add(jPanel_joindate);
		//按钮添加到面板2
		buttonPanel.add(jButton_ensure);
		buttonPanel.add(jButton_clear);
		//面板添加到主面板
		allPanel.add(changePeople,BorderLayout.CENTER);
		allPanel.add(buttonPanel,BorderLayout.SOUTH);
		add(allPanel);
		
		//善后
		setResizable(true);
		setVisible(true);//可见
		pack();//初始大小合适

		//弹出判断更改人员的对话框
		this.setEnabled(false);
		ifIdWindow ifid=new ifIdWindow();
		ifid.setBounds(510, 350, 400, 100);
		ifid.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e)
			{
				Human_ChangePerson.this.setVisible(false);
			}
			
		});


	}
	
	
	
	
	
	//确定按钮监听
	private class ensureListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
			
			
			//获得新的各种数据
			idn=jTextField_id.getText();
			String namen=jTextField_name.getText();
			String sexn=jComboBox_sex.getSelectedItem().toString();
			String phonen=jTextField_phone.getText();
			String qqn=jTextField_qq.getText();
			String emailn=jTextField_email.getText();
			String districtn=jComboBox_district.getSelectedItem().toString();
			String departmentn=jComboBox_department.getSelectedItem().toString();
			String positionn=jComboBox_position.getSelectedItem().toString();
			String joindaten=jFormattedTextField_joindate.getText();
			
			System.out.println(idn+" "+namen+" "+phonen);
			//判断不能为空项
			if(idn.equals("")||namen.equals("")||phonen.equals("")||joindaten.equals(""))
			{
				JOptionPane.showMessageDialog(null, "学号、姓名、电话和加入日期不能为空呀~", "不能这样做~", JOptionPane.NO_OPTION); 
			}
			else 
			{
				System.out.println(idn+" "+namen+" "+phonen);

				//更新
				dBchange.staffUpdate(id,idn, namen, sexn, phonen, qqn, emailn, districtn, joindaten);	
				dBchange.departmentUpdate(idn, dBSearch.DepartmentToDepartmentId(departmentn));
				dBchange.positionUpdate(idn, dBSearch.PositionToPositionId(positionn));
				id=idn;
			}
			
		}
		
	}
	
	
	
	
	
	//撤销按钮监听
	private class rollbackListener implements ActionListener
	{

		public void actionPerformed(ActionEvent e) 
		{
			//滚回结果
			jTextField_id.setText(idf);
			jTextField_name.setText(namef);
			jTextField_phone.setText(phonef);
			jTextField_qq.setText(qqf);
			jTextField_email.setText(emailf);
			jComboBox_sex.setSelectedItem(sexf);
			jComboBox_district.setSelectedItem(districtf);
			jComboBox_position.setSelectedItem(positionf);
			jComboBox_department.setSelectedItem(departmentf);
			jFormattedTextField_joindate.setText(joindatef);
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
			Human_ChangePerson.this.setEnabled(false);
			//组件各种声明
			jPanel_select=new JPanel();
			jTextField_inpu=new JTextField(12);
			jTextField_inpu.addKeyListener(new KeyNumLim());
			jTextField_inpu.addKeyListener(new KeySizeLim(jTextField_inpu,com.online.util.Data.inputSizeId));//限制字数
			jLabel_enter=new JLabel("请输入要更改信息的人的学号：");

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
			
			public void actionPerformed(ActionEvent e) 
			{
				//得到输入的id号
	 			id=jTextField_inpu.getText();
	 			
	 			//判断id是否存在
	 			if(!dBSearch.ExistID(id))
	 			{
	 				JOptionPane.showMessageDialog(null, "抱歉，没找到你说的在职人员...", "不能这样做~", JOptionPane.NO_OPTION); 
	 			}
	 			else
	 			{
	 				
	 				try 
	 				{
						//加载驱动
						Class.forName(driver);
						//连接数据库
						Connection connection=DriverManager.getConnection(url,user,password);
	 					//执行sql语句					
	 					PreparedStatement ps =connection.prepareStatement("select * from online_staff where id=? and leavedate is null");
	 					ps.setString(1, id);
	 					ResultSet result= ps.executeQuery();
	 					
	 					while(result.next())
	 					{
	 						//从结果集中得到各种基本结果
							name=result.getString("name");
							sex=result.getString("sex");
							phone=result.getString("phone");
							qq=result.getString("qq");
							email=result.getString("email");
							district=result.getString("district");
							joindate=result.getDate("joindate");
							
							//获得d、p结果
							department=dBSearch.DepartmentIdToDepartmetn(dBSearch.IdToDepartmentId(id));
							position=dBSearch.PositionIdToPosition(dBSearch.IdToPositionId(id));
							
							//将得到的结果反映到修改框中
							jTextField_id.setText(id);
							jTextField_name.setText(name);
							jTextField_phone.setText(phone);
							jTextField_qq.setText(qq);
							jTextField_email.setText(email);
							jComboBox_sex.setSelectedItem(sex);
							jComboBox_district.setSelectedItem(district);
							jComboBox_position.setSelectedItem(position);
							jComboBox_department.setSelectedItem(department);
							jFormattedTextField_joindate.setValue(joindate);
	 					}
					
	 					result.close();
	 					connection.close();
	 					
					} 
	 				catch (Exception e2) {}
	 				
	 				
	 				//可还原的数据
	 				
	 				idf=id;
	 				namef=jTextField_name.getText();
	 				sexf=jComboBox_sex.getSelectedItem().toString();
	 				phonef=jTextField_phone.getText();
	 				qqf=jTextField_qq.getText();
	 				emailf=jTextField_email.getText();
	 				districtf=jComboBox_district.getSelectedItem().toString();
	 				departmentf=jComboBox_department.getSelectedItem().toString();
	 				positionf=jComboBox_position.getSelectedItem().toString();
	 				joindatef=jFormattedTextField_joindate.getText();
	 				//使父窗口可用
	 				Human_ChangePerson.this.setEnabled(true);
	 				//并关掉该窗口
	 				dispose();
				}
				
			}
			
		

		}
		
	}

}




