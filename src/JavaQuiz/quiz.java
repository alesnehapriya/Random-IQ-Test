package JavaQuiz;

import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

import javax.swing.*;

public class quiz extends JFrame implements Runnable,ActionListener {
	JLabel Time,equation,Points_Label,pointsll;
	protected Thread clockThread = null;
	int i=0,a=0,b=0,c=0,option=0,l=0,non_ans=0,range=0,points=0,record_second=0,timer=0;
	JButton button1,button2,button3,button4,answer,not_answer,exit,start;
	ArrayList<JButton> list = new ArrayList<JButton>();
	Calendar calendar1,calendar2;
	protected Font font = new Font("Arial Rounded MT Bold", Font.BOLD,30);
	protected Font font1 = new Font("Arial Rounded MT Bold", Font.BOLD,25);
	Connection connect=null;

	/**
	 * Create the applet.
	 * @return 
	 */
	public quiz() {
		connect = databaseConnection.connector();
		try {
			String query1="delete from Data";
			PreparedStatement statement1 = connect.prepareStatement(query1);
			statement1.execute();
			statement1.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.white);
		button1 = new JButton("");
		button1.setBounds(0, 80, 241, 44);
		getContentPane().add(button1);
		button1.addActionListener(this);
		
		button2 = new JButton("");
		button2.setBounds(0, 122, 241, 42);
		getContentPane().add(button2);
		button2.addActionListener(this);
		
		button3 = new JButton("");
		button3.setBounds(0, 162, 241, 44);
		getContentPane().add(button3);
		button3.addActionListener(this);
		
		button4 = new JButton("");
		button4.setBounds(0, 202, 241, 42);
		getContentPane().add(button4);
		button4.addActionListener(this);
		
		equation = new JLabel("");
		equation.setBounds(0, 37, 241, 42);
		getContentPane().add(equation);
		
		pointsll = new JLabel("Points");
		pointsll.setBounds(239, 37, 129, 42);
		getContentPane().add(pointsll);
		
		Points_Label = new JLabel("");
		Points_Label.setBounds(372, 37, 56, 42);
		getContentPane().add(Points_Label);
		
		Time = new JLabel("");
		Time.setBounds(0, 0, 124, 37);
		getContentPane().add(Time);	
		
		list.add(button1);
		list.add(button2);
		list.add(button3);
		list.add(button4);
		
		exit = new JButton(new ImageIcon("exit.png"));
//		exit = new JButton("Exit");
		exit.setBounds(275, -3, 111, 40);
		getContentPane().add(exit);
		exit.setFont(font1);
		exit.addActionListener(this);
		
		 calendar2= Calendar.getInstance();
		 record_second= calendar2.get(Calendar.SECOND);
		

	}
	public void start(){
		setSize(300,300);
			if(clockThread == null){
				clockThread = new Thread(this);
				clockThread.start();
			}
	}
	public void stop(){
		clockThread = null;
	}
	public void run(){
		while(Thread.currentThread()== clockThread){
			repaint();
			try{
				Thread.currentThread().sleep(1000);
			}
			catch(InterruptedException e){
				
			}
			
		}
	}
	
	public void paint(Graphics g){
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		String operator="";
			Time.setText("00:"+(timer++));
			Time.setFont(font1);
			l=0;
			pointsll.setText("Points");
			pointsll.setFont(font1);
			Points_Label.setText(String.valueOf(points));
			Points_Label.setFont(font1);
		if(second%5==(record_second%5)){
			int j = (int)(Math.random()*4);
			String quiz_eq;
			switch(j){
			case 1:
				a= (int)(Math.random()*100);
				b= (int)(Math.random()*100);
				c= a+b;
				quiz_eq = (String.valueOf(a))+"+"+(String.valueOf(b));
				equation.setText(quiz_eq);
				equation.setFont(font);
				operator="+";
				break;
			case 2:
				a= (int)(Math.random()*100);
				b= (int)(Math.random()*100);
				c= a-b;
				quiz_eq = (String.valueOf(a))+"-"+(String.valueOf(b));
				equation.setText(quiz_eq);
				equation.setFont(font);
				operator="-";
				break;
			case 3:
				a= (int)(Math.random()*100);
				b= (int)(Math.random()*100);
				c= a*b;
				quiz_eq = (String.valueOf(a))+"*"+(String.valueOf(b));
				equation.setText(quiz_eq);
				equation.setFont(font);
				operator="*";
				break;
			case 4:
				a= (int)(Math.random()*100);
				b= (int)(Math.random()*100);
				c= a/b;
				quiz_eq = (String.valueOf(a))+"/"+(String.valueOf(b));
				equation.setText(quiz_eq);
				equation.setFont(font);
				operator="/";
				break;
				}
			option= (int)(Math.random()* list.size());
			answer= list.get(option);
			answer.setText((String.valueOf(c)));
			answer.setFont(font);
			answer.setActionCommand("correctanswer");
				while(l<4){
					if(l!=option){
						not_answer=list.get(l);
						non_ans=(int)(Math.random()*100)+c;
						not_answer.setText(String.valueOf(non_ans));
						not_answer.setFont(font);
						not_answer.setActionCommand("incorrectanswer");
						}
					l++;
					}
				try {
					
					String query ="insert into Data (Num1,Operator,Num2,answer) Values(?,?,?,?)";
					PreparedStatement statement = connect.prepareStatement(query);

					statement.setString(1,Integer.toString(a));
					statement.setString(2,operator);
					statement.setString(3,Integer.toString(b));
					statement.setString(4,Integer.toString(c));
					statement.execute();
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println(e);
				}
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		String cmd= ae.getActionCommand();
		if("correctanswer".equals(cmd)){
			points = points+1;
		}else if("incorrectanswer".equals(cmd)){
			points=points-1;
		}
		else{
			JOptionPane.showMessageDialog(null, "You scored "+points+" points.");
			System.exit(0);
		}
	}
	
	public static void main(String[] args)
	{
	quiz f = new quiz ();
	f.start();
	f.setSize(450,300);
	f.setTitle("QUIZ");
	f.setVisible(true);
	}
}
