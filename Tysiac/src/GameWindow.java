import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GameWindow extends JFrame implements ActionListener
{
	private JButton c1,c2,c3,c4,c5,c6,c7,c8;
	private JButton c9,c10,c11,c12,c13,c14,c15,c16;
	private JButton c17,c18,c19,c20,c21,c22,c23,c24, cGame1, cGame2, cGame3;
	private JButton l110, l120, l130, l140, l150, l160, l170;
	private JButton l180, l190, l200, l210, l220, l230, l240;
	private JButton l250, l260, l270, l280, l290, l300, lPass, Bomba;
	private JLabel p1,p2,p3,p4;
	private int auction;
	
	int check1 = 0;
	
	public GameWindow()
	{	
		Toolkit kit = Toolkit.getDefaultToolkit(); //zbadanie wymiar�w ekranu do ustawienia okna
		Dimension screenSize = kit.getScreenSize();
		int sHeight = screenSize.height;
		int sWidth = screenSize.width;
		
		setBounds((sWidth/2)-1000/2,(sHeight/2)-700/2,1000,700);
		setLayout(null);
		
		c1 = new JButton(""); //dol
		c2 = new JButton();
		c3 = new JButton();
		c4 = new JButton();
		c5 = new JButton();
		c6 = new JButton();
		c7 = new JButton();
		c8 = new JButton();
		c9 = new JButton(); //lewo
		c10 = new JButton();
		c11 = new JButton();
		c12 = new JButton();
		c13 = new JButton();
		c14 = new JButton();
		c15 = new JButton();
		c16 = new JButton();
		c17 = new JButton();  //prawo
		c18 = new JButton();
		c19 = new JButton();
		c20 = new JButton();
		c21 = new JButton();
		c22 = new JButton();
		c23 = new JButton();
		c24 = new JButton();
	
		cGame1 = new JButton();
		cGame2 = new JButton();
		cGame3 = new JButton();
		
		l110 = new JButton("110");
		l120 = new JButton("120");
		l130 = new JButton("130");
		l140 = new JButton("140");
		l150 = new JButton("150");
		l160 = new JButton("160");
		l170 = new JButton("170");
		l180 = new JButton("180");
		l190 = new JButton("190");
		l200 = new JButton("200");
		l210 = new JButton("210");
		l220 = new JButton("220");
		l230 = new JButton("230");
		l240 = new JButton("240");
		l250 = new JButton("250");
		l260 = new JButton("260");
		l270 = new JButton("270");
		l280 = new JButton("280");
		l290 = new JButton("290");
		l300 = new JButton("300");	

		lPass = new JButton("PASS");
		Bomba = new JButton("Bomba");
		
		p1 = new JLabel("Player 1: 1111");
		p2 = new JLabel("Player 2: 1111");
		p3 = new JLabel("Player 3: 1111");
		p4 = new JLabel("Player 4: 1111");
		
		p1.setBounds(780,100,200,30);
		p2.setBounds(780,150,200,30);
		p3.setBounds(780,200,200,30);
		p4.setBounds(780,250,200,30);
		
		l110.setBounds(715,445,60,30);  //ustaw przyciski licytacji
		l120.setBounds(785,445,60,30);
		l130.setBounds(855,445,60,30);
		l140.setBounds(925,445,60,30);
		l150.setBounds(715,480,60,30);
		l160.setBounds(785,480,60,30);
		l170.setBounds(855,480,60,30);
		l180.setBounds(925,480,60,30);
		l190.setBounds(715,515,60,30);
		l200.setBounds(785,515,60,30);
		l210.setBounds(855,515,60,30);
		l220.setBounds(925,515,60,30);
		l230.setBounds(715,550,60,30);
		l240.setBounds(785,550,60,30);
		l250.setBounds(855,550,60,30);
		l260.setBounds(925,550,60,30);
		l270.setBounds(715,585,60,30);
		l280.setBounds(785,585,60,30);
		l290.setBounds(855,585,60,30);
		l300.setBounds(925,585,60,30);
		
		lPass.setBounds(715,620,130,30);
		Bomba.setBounds(855,620,130,30);
		
		cGame2.setBounds(239,230,70,42);
		cGame1.setBounds(319,250,42,72);
		cGame3.setBounds(371,230,70,42);
		
		add(cGame1);
		add(cGame2);
		add(cGame3);
		
		cGame1.setVisible(false);
		cGame2.setVisible(false);
		cGame3.setVisible(false);
		
		add(l110);
		add(l120);
		add(l130);
		add(l140);
		add(l150);
		add(l160);
		add(l170);
		add(l180);
		add(l190);
		add(l200);
		add(l210);
		add(l220);
		add(l230);
		add(l240);
		add(l250);
		add(l260);
		add(l270);
		add(l280);
		add(l290);
		add(l300);
		
		add(lPass);
		add(Bomba);
		
		p1.setForeground(Color.white);
		p2.setForeground(Color.white);
		p3.setForeground(Color.white);
		p4.setForeground(Color.white);
		
		p1.setFont(new Font("Dejavu Sans", Font.BOLD, 20));
		p2.setFont(new Font("Dejavu Sans", Font.BOLD, 20)); 
		p3.setFont(new Font("Dejavu Sans", Font.BOLD, 20)); 
		p4.setFont(new Font("Dejavu Sans", Font.BOLD, 20)); 
		
		add(p1);
		add(p2);
		add(p3);
		add(p4);
		
		c1.setBounds(75,500,60,100);
		c2.setBounds(145,500,60,100);
		c3.setBounds(215,500,60,100);
		c4.setBounds(285,500,60,100);
		c5.setBounds(355,500,60,100);
		c6.setBounds(425,500,60,100);         
		c7.setBounds(495,500,60,100);
		c8.setBounds(565,500,60,100);
		c9.setBounds(30,420,70,42);
		c10.setBounds(30,370,70,42);
		c11.setBounds(30,320,70,42);
		c12.setBounds(30,270,70,42);
		c13.setBounds(30,220,70,42);
		c14.setBounds(30,170,70,42);         
		c15.setBounds(30,120,70,42);
		c16.setBounds(30,70,70,42);
		c17.setBounds(628,420,70,42);
		c18.setBounds(628,370,70,42);
		c19.setBounds(628,320,70,42);
		c20.setBounds(628,270,70,42);
		c21.setBounds(628,220,70,42);
		c22.setBounds(628,170,70,42);         
		c23.setBounds(628,120,70,42);
		c24.setBounds(628,70,70,42);
		
		add(c1);
		add(c2);
		add(c3);
		add(c4);
		add(c5);
		add(c6);
		add(c7);
		add(c8);
		add(c9);
		add(c10);
		add(c11);
		add(c12);
		add(c13);
		add(c14);
		add(c15);
		add(c16);
		add(c17);
		add(c18);
		add(c19);
		add(c20);
		add(c21);
		add(c22);
		add(c23);
		add(c24);
		
		c1.addActionListener(this);
		c2.addActionListener(this);
		c3.addActionListener(this);
		c4.addActionListener(this);
		c5.addActionListener(this);
		c6.addActionListener(this);
		c7.addActionListener(this);
		c8.addActionListener(this);
		c9.addActionListener(this);
		c10.addActionListener(this);
		c11.addActionListener(this);
		c12.addActionListener(this);
		c13.addActionListener(this);
		c14.addActionListener(this);
		c15.addActionListener(this);
		c16.addActionListener(this);
		c17.addActionListener(this);
		c18.addActionListener(this);
		c19.addActionListener(this);
		c20.addActionListener(this);
		c21.addActionListener(this);
		c22.addActionListener(this);
		c23.addActionListener(this);
		c24.addActionListener(this);
		
		l110.addActionListener(this);
		l120.addActionListener(this);
		l130.addActionListener(this);
		l140.addActionListener(this);
		l150.addActionListener(this);
		l160.addActionListener(this);
		l170.addActionListener(this);
		l180.addActionListener(this);
		l190.addActionListener(this);
		l200.addActionListener(this);
		l210.addActionListener(this);
		l220.addActionListener(this);
		l230.addActionListener(this);
		l240.addActionListener(this);
		l250.addActionListener(this);
		l260.addActionListener(this);
		l270.addActionListener(this);
		l280.addActionListener(this);
		l290.addActionListener(this);
		l300.addActionListener(this);
		lPass.addActionListener(this);
		Bomba.addActionListener(this);	
	}

	public static void main(String[] args)
	{
		GameWindow tysiac = new GameWindow();
		Color stol = new Color(0,102,0);
		
		tysiac.setVisible(true);
		tysiac.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tysiac.getContentPane().setBackground(stol);
		tysiac.setTitle("Tysiąc");
		tysiac.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if(source==c1){
			c1.setVisible(false);
			
			try 
			{
				changeLayoutCard(2);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(2);
			
			/*Container parent = c1.getParent();
			parent.remove(c1);
			parent.revalidate();
			parent.repaint();*/
		}
		else if(source==c2){
			c2.setVisible(false);
			
			try {
				changeLayoutCard(2);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(2);
		}
		else if(source==c3){
			c3.setVisible(false);
			
			try 
			{
				changeLayoutCard(2);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(2);
		}
		else if(source==c4)
		{
			c4.setVisible(false);
			
			try 
			{
				changeLayoutCard(2);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(2);
		}
		else if(source==c5){
			c5.setVisible(false);
			
			try 
			{
				changeLayoutCard(2);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(2);
		}
		else if(source==c6){
			c6.setVisible(false);
			
			try 
			{
				changeLayoutCard(2);
			} 
			catch (InterruptedException e1) 
			{

				e1.printStackTrace();
			}
			setDisableButton(2);
		}
		else if(source==c7){
			c7.setVisible(false);
			
			try 
			{
				changeLayoutCard(2);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(2);
		}
		else if(source==c8){
			c8.setVisible(false);
			
			try 
			{
				changeLayoutCard(2);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(2);
		}
		else if(source==c9){
			c9.setVisible(false);
			
			try 
			{
				changeLayoutCard(1);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(1);
		}
		else if(source==c10){
			c10.setVisible(false);
			
			try 
			{
				changeLayoutCard(1);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(1);
		}
		else if(source==c11){
			c11.setVisible(false);
			
			try 
			{
				changeLayoutCard(1);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(1);
		}
		else if(source==c12){
			c12.setVisible(false);
			
			try 
			{
				changeLayoutCard(1);
			} catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(1);
		}
		else if(source==c13){
			c13.setVisible(false);
			
			try 
			{
				changeLayoutCard(1);
			} catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(1);
		}
		else if(source==c14){
			c14.setVisible(false);
			
			try 
			{
				changeLayoutCard(1);
			} 
			catch (InterruptedException e1) 
			{		
				e1.printStackTrace();
			}
			setDisableButton(1);
		}
		else if(source==c15){
			c15.setVisible(false);
			
			try 
			{
				changeLayoutCard(1);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(1);
		}
		else if(source==c16){
			c16.setVisible(false);
			
			try 
			{
				changeLayoutCard(1);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(1);
		}
		else if(source==c17){
			c17.setVisible(false);
			
			try {
				changeLayoutCard(3);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(3);
		}
		else if(source==c18){
			c18.setVisible(false);
			
			try 
			{
				changeLayoutCard(3);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(3);
		}
		else if(source==c19){
			c19.setVisible(false);
			
			try 
			{
				changeLayoutCard(3);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(3);
		}
		else if(source==c20){
			c20.setVisible(false);
			
			try 
			{
				changeLayoutCard(3);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(3);
		}
		else if(source==c21){
			c21.setVisible(false);
			
			try 
			{
				changeLayoutCard(3);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(3);
		}
		else if(source==c22){
			c22.setVisible(false);
			
			try 
			{
				changeLayoutCard(3);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(3);
		}
		else if(source==c23){
			c23.setVisible(false);
			
			try 
			{
				changeLayoutCard(3);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(3);
		}
		else if(source==c24){
			c24.setVisible(false);
			
			try 
			{
				changeLayoutCard(3);
			} 
			catch (InterruptedException e1) 
			{
				e1.printStackTrace();
			}
			setDisableButton(3);
		}
		else if(source==l110){
			setAuction(110);
		}
		else if(source==l120){
			setAuction(120);
		}
		else if(source==l130){
			setAuction(130);
		}
		else if(source==l140){
			setAuction(140);
		}
		else if(source==l150){
			setAuction(150);
		}
		else if(source==l160){
			setAuction(160);
		}
		else if(source==l170){
			setAuction(170);
		}
		else if(source==l180){
			setAuction(180);
		}
		else if(source==l190){
			setAuction(190);
		}
		else if(source==l200){
			setAuction(200);
		}
		else if(source==l210){
			setAuction(210);
		}
		else if(source==l220){
			setAuction(220);
		}
		else if(source==l230){
			setAuction(230);
		}
		else if(source==l240){
			setAuction(240);
		}
		else if(source==l250){
			setAuction(250);
		}
		else if(source==l260){
			setAuction(260);
		}
		else if(source==l270){
			setAuction(270);
		}
		else if(source==l280){
			setAuction(280);
		}
		else if(source==l290){
			setAuction(290);
		}
		else if(source==l300){
			setAuction(300);
		}
		else if(source==lPass){
			setAuction(1);
		}
		else if(source==Bomba){
			bomb();
		}
		
	}
	
	public void changeLayoutCard(int checkC) throws InterruptedException
	{
		if(checkC==1)
		{
			cGame1.setVisible(true);
		}
		else if(checkC==2)
		{
			cGame2.setVisible(true);
		}
		else if(checkC==3)
		{
			cGame3.setVisible(true);
		}
		check1++;
		
		if(check1==3)
		{
			check1 = 0;
			notVisibleCard();
		}
	}
	
	public void notVisibleCard() throws InterruptedException
	{
		TimeUnit.MILLISECONDS.sleep(2000);
		
		cGame1.setVisible(false);
		cGame2.setVisible(false);
		cGame3.setVisible(false);
		
		setEnableButton();
		
	}
	
	public void setAuction(int amount) 
	{
		this.auction = amount;
	}
	
	public int getAuction() 
	{
		return this.auction;
	}
	
	public void bomb()
	{
		//co to za gracz
		//brak mozliwosci kolejnej bomby
		//dodaj po 60 pkt innnym
		Bomba.setVisible(false);
	}
	
	public void setDisableButton(int checkB)
	{
		if(checkB==2)
		{
			c1.setEnabled(false);
			c2.setEnabled(false);
			c3.setEnabled(false);
			c4.setEnabled(false);
			c5.setEnabled(false);
			c6.setEnabled(false);
			c7.setEnabled(false);
			c8.setEnabled(false);
		}	
		else if(checkB==1)
		{
			c9.setEnabled(false);
			c10.setEnabled(false);
			c11.setEnabled(false);
			c12.setEnabled(false);
			c13.setEnabled(false);
			c14.setEnabled(false);
			c15.setEnabled(false);
			c16.setEnabled(false);	
		}
		else if(checkB==3)
		{
			c17.setEnabled(false);
			c18.setEnabled(false);
			c19.setEnabled(false);
			c20.setEnabled(false);
			c21.setEnabled(false);
			c22.setEnabled(false);
			c23.setEnabled(false);
			c24.setEnabled(false);	
		}
	}
	
	public void setEnableButton()
	{
		c1.setEnabled(true);
		c2.setEnabled(true);
		c3.setEnabled(true);
		c4.setEnabled(true);
		c5.setEnabled(true);
		c6.setEnabled(true);
		c7.setEnabled(true);
		c8.setEnabled(true);
		c9.setEnabled(true);
		c10.setEnabled(true);
		c11.setEnabled(true);
		c12.setEnabled(true);
		c13.setEnabled(true);
		c14.setEnabled(true);
		c15.setEnabled(true);
		c16.setEnabled(true);
		c17.setEnabled(true);
		c18.setEnabled(true);
		c19.setEnabled(true);
		c20.setEnabled(true);
		c21.setEnabled(true);
		c22.setEnabled(true);
		c23.setEnabled(true);
		c24.setEnabled(true);
	}
}
