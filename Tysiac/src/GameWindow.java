import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
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
	private JButton [] card;
	private JButton cGame1, cGame2, cGame3;
	private JButton [] bAuction;
	private JButton lPass, Bomba;
	private JLabel p1,p2,p3,p4;
	private int auction;
	private GameData GaD;

	private Font f100;
	private Font f70;
	private Font f50;
	private Font f35;

	private Dimension playerBottomHand = new Dimension(65,100);
	private Dimension playerBottomTable = new Dimension(42,70);
	private Dimension playerBottomWinning = new Dimension(30,50);
	
	private Dimension playerLeftHand = new Dimension(70,42);
	private Dimension playerLeftTable = new Dimension(100,60);
	private Dimension playerLeftWinning = new Dimension(35,21);
	
	private Dimension playerRightHand = new Dimension(70,42);
	private Dimension playerRightTable = new Dimension(100,60);
	private Dimension playerRightWinning = new Dimension(35,21);
	
	private Dimension playerTopHand = new Dimension(42,70);
	private Dimension playerTopTable = new Dimension(42,70);
	private Dimension playerTopWinning = new Dimension(30,50);
	
	
	int check1 = 0;
	
	public GameWindow(GameData a)
	{	
		GaD = a;

		f100 = new Font("Monospaced", Font.BOLD, 80);
		f70 = new Font("Monospaced", Font.BOLD, 35);
		f50 = new Font("Monospaced", Font.BOLD, 20);
		f35 = new Font("Monospaced", Font.BOLD, 20);
		
		Toolkit kit = Toolkit.getDefaultToolkit(); //zbadanie wymiar�w ekranu do ustawienia okna
		Dimension screenSize = kit.getScreenSize();
		int sHeight = screenSize.height;
		int sWidth = screenSize.width;
		
		setBounds((sWidth/2)-1000/2,(sHeight/2)-700/2,1000,700);
		setLayout(null);

		card = new JButton[25];
		bAuction = new JButton[20];

		for(int i=0; i<25; i++)
		{
			card[i] = new JButton("");
		}
		for(int i=0; i<20; i++)
		{
			bAuction[i] = new JButton(Integer.toString(100+(i+1)*10));
		}
		
		cGame1 = new JButton();
		cGame2 = new JButton();
		cGame3 = new JButton();

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
		
		bAuction[0].setBounds(715,445,60,30);  //ustaw przyciski licytacji
		bAuction[1].setBounds(785,445,60,30);
		bAuction[2].setBounds(855,445,60,30);
		bAuction[3].setBounds(925,445,60,30);
		bAuction[4].setBounds(715,480,60,30);
		bAuction[5].setBounds(785,480,60,30);
		bAuction[6].setBounds(855,480,60,30);
		bAuction[7].setBounds(925,480,60,30);
		bAuction[8].setBounds(715,515,60,30);
		bAuction[9].setBounds(785,515,60,30);
		bAuction[10].setBounds(855,515,60,30);
		bAuction[11].setBounds(925,515,60,30);
		bAuction[12].setBounds(715,550,60,30);
		bAuction[13].setBounds(785,550,60,30);
		bAuction[14].setBounds(855,550,60,30);
		bAuction[15].setBounds(925,550,60,30);
		bAuction[16].setBounds(715,585,60,30);
		bAuction[17].setBounds(785,585,60,30);
		bAuction[18].setBounds(855,585,60,30);
		bAuction[19].setBounds(925,585,60,30);
		

		/*
		card[0].setBounds(75,500,60,100);
		card[1].setBounds(145,500,60,100);
		card[2].setBounds(215,500,60,100);
		card[3].setBounds(285,500,60,100);
		card[4].setBounds(355,500,60,100);
		card[5].setBounds(425,500,60,100);         
		card[6].setBounds(495,500,60,100);
		card[7].setBounds(565,500,60,100);
		card[8].setBounds(30,420,70,42);
		card[9].setBounds(30,370,70,42);
		card[10].setBounds(30,320,70,42);
		card[11].setBounds(30,270,70,42);
		card[12].setBounds(30,220,70,42);
		card[13].setBounds(30,170,70,42);         
		card[14].setBounds(30,120,70,42);
		card[15].setBounds(30,70,70,42);
		card[16].setBounds(628,420,70,42);
		card[17].setBounds(628,370,70,42);
		card[18].setBounds(628,320,70,42);
		card[19].setBounds(628,270,70,42);
		card[20].setBounds(628,220,70,42);
		card[21].setBounds(628,170,70,42);         
		card[22].setBounds(628,120,70,42);
		card[23].setBounds(628,70,70,42);
		card[24].setBounds(628,70,150,42);
		*/
		
		
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
		

		for(int i=0; i<25; i++)
		{
			add(card[i]);
			card[i].addActionListener(this);
			card[i].setMargin(new Insets(-5, -30, -5, -30));
			
			if(GaD.cards[i].getColor() == 'C' || GaD.cards[i].getColor() == 'D' || GaD.cards[i].getColor() == 'L')
			{
				card[i].setForeground(Color.RED);
			}
		}
		for(int i=0; i<20; i++)
		{
			add(bAuction[i]);
			bAuction[i].addActionListener(this);
		}
		
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
		
		lPass.addActionListener(this);
		Bomba.addActionListener(this);	
		
		

		
		for(int i=0; i<25; i++)
		{
			card[i].setText(GaD.cards[i].getUnicodeValue());
		}
	
	}

	public void displayAuction(boolean show)
	{
		for(int i=0; i<20; i++)
		{
			bAuction[i].setVisible(show);
		}
	}

	public void enableAuctionMore120(boolean show)
	{
		for(int i=2; i<20; i++)
		{
			bAuction[i].setEnabled(show);
		}
	}
	
	public void displayCard()
	{
		int [] p1h = GaD.getPlayer1().getScHand().getCards();
		int [] p1t = GaD.getPlayer1().getScTable().getCards();
		int [] p1w = GaD.getPlayer1().getScWinnings().getCards();

		int [] p2h = GaD.getPlayer2().getScHand().getCards();
		int [] p2t = GaD.getPlayer2().getScTable().getCards();
		int [] p2w = GaD.getPlayer2().getScWinnings().getCards();

		int [] p3h = GaD.getPlayer3().getScHand().getCards();
		int [] p3t = GaD.getPlayer3().getScTable().getCards();
		int [] p3w = GaD.getPlayer3().getScWinnings().getCards();

		int [] p4h = GaD.getPlayer4().getScHand().getCards();
		int [] p4t = GaD.getPlayer4().getScTable().getCards();
		int [] p4w = GaD.getPlayer4().getScWinnings().getCards();
		
		for(int i=0; i<p1h.length; i++)
		{
			card[p1h[i]].setFont(f100);
			card[p1h[i]].setSize(playerBottomHand);
			card[p1h[i]].setLocation(75+i*70, 500);
		}
		
		for(int i=0; i<p1t.length; i++)
		{
			card[p1t[i]].setFont(f70);
			card[p1t[i]].setSize(playerBottomTable);
			card[p1t[i]].setLocation(319, 250);
		}
		
		for(int i=0; i<p1w.length; i++)
		{
			card[p1w[i]].setFont(f50);
			card[p1w[i]].setSize(playerBottomWinning);
			card[p1w[i]].setLocation(20+i*32, 520);
		}
		
		

		
		for(int i=0; i<p2h.length; i++)
		{
			card[p2h[i]].setFont(f70);
			card[p2h[i]].setSize(playerLeftHand);
			card[p2h[i]].setLocation(30, 70+i*45);
		}
		
		for(int i=0; i<p2t.length; i++)
		{
			card[p2t[i]].setFont(f70);
			card[p2t[i]].setSize(playerLeftTable);
			card[p2t[i]].setLocation(239, 230);
		}
		
		for(int i=0; i<p2w.length; i++)
		{
			card[p2w[i]].setFont(f35);
			card[p2w[i]].setSize(playerLeftWinning);
			card[p2w[i]].setLocation(319, 250);
		}
		
		

		
		for(int i=0; i<p3h.length; i++)
		{
			card[p3h[i]].setFont(f70);
			card[p3h[i]].setSize(playerRightHand);
			card[p3h[i]].setLocation(628, 70+i*45);
		}
		
		for(int i=0; i<p3t.length; i++)
		{
			card[p3t[i]].setFont(f70);
			card[p3t[i]].setSize(playerRightTable);
			card[p3t[i]].setLocation(371, 230);
		}
		
		for(int i=0; i<p3w.length; i++)
		{
			card[p3w[i]].setFont(f35);
			card[p3w[i]].setSize(playerRightWinning);
			card[p3w[i]].setLocation(319, 250);
		}
		
		

		
		for(int i=0; i<p4h.length; i++)
		{
			card[p4h[i]].setFont(f70);
			card[p4h[i]].setSize(playerTopHand);
			card[p4h[i]].setLocation(120+i*45, 20);
		}
		
		for(int i=0; i<p4t.length; i++)
		{
			card[p4t[i]].setFont(f70);
			card[p4t[i]].setSize(playerTopTable);
			card[p4t[i]].setLocation(319, 170);
		}
		
		for(int i=0; i<p1w.length; i++)
		{
			card[p4w[i]].setFont(f50);
			card[p4w[i]].setSize(playerTopWinning);
			card[p4w[i]].setLocation(319, 250);
		}
		
		
	}
	
	
	

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if(source == card[0])
		{
			
		}
	}
	
	/*
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
			
			//Container parent = c1.getParent();
			//parent.remove(c1);
			//parent.revalidate();
			//parent.repaint();
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
	*/
	
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
		
	//	setEnableButton();
		
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
	
	/*
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
*/

	public GameData getGameData() 
	{
		return GaD;
	}
	public void closeWindow()
	{
		dispose();
	}
}
