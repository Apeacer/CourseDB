package com.online.tool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//次监听只允许输入数字
public class KeyNumLim implements KeyListener
{
	
	public void keyTyped(KeyEvent e) 
	{
		//敲键盘
		 int keyChar = e.getKeyChar();                 
         if(keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)
         {  
               
         }
         else
         {  
             e.consume(); //、屏蔽掉非法输入  
         }  
	}

	public void keyPressed(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {}
	
}
