package com.xzg.javadesign;

import java.sql.Connection;
import java.util.Vector;

public class Abstrictfactory {
	private Vector<Connection> pool;
	public static void  main(String[] args) {
		/*Factory factory = new SendMail();
		factory.produce().sender();*/
	}
}
	
interface Sender{
	void sender();
}
class MailSender implements Sender{
	public void sender(){
		System.out.println("mail");
	}
}
class SmsSender implements Sender{
	public void sender(){
		System.out.println("Smsender");
	}
}
interface Factory{
	public Sender produce();
}
class SendMail implements Factory{
	public Sender produce(){
		return new MailSender();
	}
}
class SendSms implements Factory{
	public Sender produce(){
		return new SmsSender();
	}
}

