package com.sound;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatTime {
	Date date= new Date();
	public String date()
	{
		SimpleDateFormat ft=new SimpleDateFormat("dd MMMM yyyy");
		String d=ft.format(date).toString();
		return d;
	}
	public String time()
	{
		SimpleDateFormat ft1=new SimpleDateFormat("hh:mm a");
		String t=ft1.format(date);
		return t;
	}
//	public static void main(String[] args)
//	{
//		ChatTime c =new ChatTime();
//		c.time();
//	}

}
