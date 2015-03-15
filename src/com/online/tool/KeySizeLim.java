package com.online.tool;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

//限制输入的字数方法类
public class KeySizeLim implements KeyListener
{
	int current_size;
	JTextField jTextField;
	int size;
	
	public KeySizeLim (JTextField jTextField,int size)
	{
		this.jTextField=jTextField;
		this.size=size-1;
	}

	public void keyTyped(KeyEvent e) 
	{
		current_size=jTextField.getText().length();
		if(current_size<=size)
		{
			
		}
		else 
		{
			e.consume();
		}
			
		
	}

	public void keyPressed(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}
	 

}
