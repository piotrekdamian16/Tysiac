import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GameWindow extends JFrame implements ActionListener
{
	private JButton [] bAuction;
	private JButton lPass, Bomba;
	private JButton line;
	private JLabel p1,p2,p3,p4;
	private JLabel LabelB, LabelL, LabelR, LabelT;
	private JLabel arrowB, arrowL, arrowR, arrowT;
	private int auction;
	private GameData GaD;
	
	

	private JButton [] pbCardHand = new JButton[24];
	private JButton [] pbCardTable = new JButton[24];
	private JButton [] pbCardWinning = new JButton[24];

	private JButton [] plCardHand = new JButton[24];
	private JButton [] plCardHandB = new JButton[24];
	private JButton [] plCardTable = new JButton[24];

	private JButton [] prCardHand = new JButton[24];
	private JButton [] prCardHandB = new JButton[24];
	private JButton [] prCardTable = new JButton[24];

	private JButton [] ptCardHand = new JButton[24];
	private JButton [] ptCardHandB = new JButton[24];
	private JButton [] ptCardTable = new JButton[24];
	
	
	private Font f100 = new Font("Monospaced", Font.BOLD, 80);
	private Font f70 = new Font("Monospaced", Font.BOLD, 48);
	private Font f40 = new Font("Monospaced", Font.BOLD, 30);
	private Dimension playerBottomHand = new Dimension(65,100);
	private Dimension playerBottomTable = new Dimension(65,100);
	private Dimension playerBottomWinning = new Dimension(26,40);
	
	private Dimension playerLeftHand = new Dimension(65,42);
	private Dimension playerLeftTable = new Dimension(100,65);
	
	private Dimension playerRightHand = new Dimension(65,42);
	private Dimension playerRightTable = new Dimension(100,65);
	
	private Dimension playerTopHand = new Dimension(42,65);
	private Dimension playerTopTable = new Dimension(65,100);

	int check1 = 0;
	
	public GameWindow(GameData a)
	{	
		GaD = a;
		
		Toolkit kit = Toolkit.getDefaultToolkit(); //zbadanie wymiar�w ekranu do ustawienia okna
		Dimension screenSize = kit.getScreenSize();
		int sHeight = screenSize.height;
		int sWidth = screenSize.width;
		
		setBounds((sWidth/2)-1000/2,(sHeight/2)-700/2,1000,700);
		setLayout(null);

		//Separator okna 
		line = new JButton();
		line.setBounds(700, 0, 5, 700);
		add(line);
		line.setEnabled(false);
		
		
		this.createCardsButton();
		this.createAuctionButton();
		this.createPlayerBaner();
		


		
		
		

		

	
	}
	
	private void createCardsButton()
	{

		TextIcon t1;
		RotatedIcon r1;
		
		for(int i=0; i<24; i++)
		{
			
			//Karty gracza na dole HAND
			pbCardHand[i] = new JButton(GaD.cards[i].getUnicodeValue());
			pbCardHand[i].setFont(f100);
			pbCardHand[i].setSize(playerBottomHand);
			pbCardHand[i].addActionListener(this);
			pbCardHand[i].setMargin(new Insets(-5, -30, -5, -30));

			//Karty gracza na dole TABLE
			pbCardTable[i] = new JButton(GaD.cards[i].getUnicodeValue());
			pbCardTable[i].setFont(f100);
			pbCardTable[i].setSize(playerBottomTable);
			pbCardTable[i].setMargin(new Insets(-5, -30, -5, -30));
			
			//Karty gracza na dole WINNING
			pbCardWinning[i] = new JButton(GaD.cards[i].getUnicodeValue());
			pbCardWinning[i].setFont(f40);
			pbCardWinning[i].setSize(playerBottomWinning);
			pbCardWinning[i].setMargin(new Insets(-5, -30, -5, -30));
			
			
			//Karty grzacza LEWO HAND 
			plCardHand[i] = new JButton();
			t1 = new TextIcon(plCardHand[i], GaD.cards[i].getUnicodeValue(), TextIcon.Layout.HORIZONTAL);
			r1 = new RotatedIcon(t1, RotatedIcon.Rotate.DOWN);
			plCardHand[i].setIcon( r1 );
			plCardHand[i].setFont(f70);
			plCardHand[i].setSize(playerLeftHand);
			plCardHand[i].setMargin(new Insets(0,-90,0,0));

			//Karty grzacza LEWO HAND TYŁEM
			plCardHandB[i] = new JButton();
			t1 = new TextIcon(plCardHandB[i], GaD.cards[24].getUnicodeValue(), TextIcon.Layout.HORIZONTAL);
			r1 = new RotatedIcon(t1, RotatedIcon.Rotate.DOWN);
			plCardHandB[i].setIcon( r1 );
			plCardHandB[i].setFont(f70);
			plCardHandB[i].setSize(playerLeftHand);
			plCardHandB[i].setMargin(new Insets(0,-90,0,0));
			
			//Karty grzacza LEWO TABLE 
			plCardTable[i] = new JButton();
			t1 = new TextIcon(plCardTable[i], GaD.cards[i].getUnicodeValue(), TextIcon.Layout.HORIZONTAL);
			r1 = new RotatedIcon(t1, RotatedIcon.Rotate.DOWN);
			plCardTable[i].setIcon( r1 );
			plCardTable[i].setFont(f100);
			plCardTable[i].setSize(playerLeftTable);
			plCardTable[i].setLocation(200, 270);
			plCardTable[i].setMargin(new Insets(0,-150,0,0));
			
			
			

			//Karty grzacza PRAWO HAND 
			prCardHand[i] = new JButton();
			t1 = new TextIcon(prCardHand[i], GaD.cards[i].getUnicodeValue(), TextIcon.Layout.HORIZONTAL);
			r1 = new RotatedIcon(t1, RotatedIcon.Rotate.UP);
			prCardHand[i].setIcon( r1 );
			prCardHand[i].setFont(f70);
			prCardHand[i].setSize(playerRightHand);
			prCardHand[i].setMargin(new Insets(0,90,0,0));

			//Karty grzacza PRAWO HAND TYŁEM
			prCardHandB[i] = new JButton();
			t1 = new TextIcon(prCardHandB[i], GaD.cards[24].getUnicodeValue(), TextIcon.Layout.HORIZONTAL);
			r1 = new RotatedIcon(t1, RotatedIcon.Rotate.UP);
			prCardHandB[i].setIcon( r1 );
			prCardHandB[i].setFont(f70);
			prCardHandB[i].setSize(playerRightHand);
			prCardHandB[i].setMargin(new Insets(0,90,0,0));

			//Karty grzacza PRAWO TABLE 
			prCardTable[i] = new JButton();
			t1 = new TextIcon(prCardTable[i], GaD.cards[i].getUnicodeValue(), TextIcon.Layout.HORIZONTAL);
			r1 = new RotatedIcon(t1, RotatedIcon.Rotate.UP);
			prCardTable[i].setIcon( r1 );
			prCardTable[i].setFont(f100);
			prCardTable[i].setSize(playerRightTable);
			prCardTable[i].setLocation(400, 270);
			prCardTable[i].setMargin(new Insets(0,150,0,0));
			

			//Karty grzacza GÓRA HAND 
			ptCardHand[i] = new JButton();
			t1 = new TextIcon(ptCardHand[i], GaD.cards[i].getUnicodeValue(), TextIcon.Layout.HORIZONTAL);
			r1 = new RotatedIcon(t1, RotatedIcon.Rotate.UPSIDE_DOWN);
			ptCardHand[i].setIcon( r1 );
			ptCardHand[i].setFont(f70);
			ptCardHand[i].setSize(playerTopHand);
			ptCardHand[i].setMargin(new Insets(-175,0,0,0));

			//Karty grzacza GÓRA HAND TYŁEM
			ptCardHandB[i] = new JButton();
			t1 = new TextIcon(ptCardHandB[i], GaD.cards[24].getUnicodeValue(), TextIcon.Layout.HORIZONTAL);
			r1 = new RotatedIcon(t1, RotatedIcon.Rotate.UPSIDE_DOWN);
			ptCardHandB[i].setIcon( r1 );
			ptCardHandB[i].setFont(f70);
			ptCardHandB[i].setSize(playerTopHand);
			ptCardHandB[i].setMargin(new Insets(-175,0,0,0));

			//Karty grzacza GÓRA TABLE 
			ptCardTable[i] = new JButton();
			t1 = new TextIcon(ptCardTable[i], GaD.cards[i].getUnicodeValue(), TextIcon.Layout.HORIZONTAL);
			r1 = new RotatedIcon(t1, RotatedIcon.Rotate.UPSIDE_DOWN);
			ptCardTable[i].setIcon( r1 );
			ptCardTable[i].setFont(f100);
			ptCardTable[i].setSize(playerTopTable);
			ptCardTable[i].setLocation(320, 150);
			ptCardTable[i].setMargin(new Insets(-295,0,0,0));
			
			
			
			if(GaD.cards[i].getColor() == 'C' || GaD.cards[i].getColor() == 'D')
			{
				pbCardHand[i].setForeground(Color.RED);
				pbCardTable[i].setForeground(Color.RED);
				pbCardWinning[i].setForeground(Color.RED);

				plCardHand[i].setForeground(Color.RED);
				plCardTable[i].setForeground(Color.RED);

				prCardHand[i].setForeground(Color.RED);
				prCardTable[i].setForeground(Color.RED);

				ptCardHand[i].setForeground(Color.RED);
				ptCardTable[i].setForeground(Color.RED);
			}
			else
			{
				pbCardHand[i].setForeground(Color.BLACK);
				pbCardTable[i].setForeground(Color.BLACK);
				pbCardWinning[i].setForeground(Color.BLACK);

				plCardHand[i].setForeground(Color.BLACK);
				plCardTable[i].setForeground(Color.BLACK);

				prCardHand[i].setForeground(Color.BLACK);
				prCardTable[i].setForeground(Color.BLACK);

				ptCardHand[i].setForeground(Color.BLACK);
				ptCardTable[i].setForeground(Color.BLACK);
			}

			plCardHandB[i].setForeground(Color.BLUE);
			prCardHandB[i].setForeground(Color.BLUE);
			ptCardHandB[i].setForeground(Color.BLUE);


			add(pbCardHand[i]);
			add(pbCardTable[i]);
			add(pbCardWinning[i]);


			add(plCardHand[i]);
			add(plCardHandB[i]);
			add(plCardTable[i]);

			add(prCardHand[i]);
			add(prCardHandB[i]);
			add(prCardTable[i]);

			add(ptCardHand[i]);
			add(ptCardHandB[i]);
			add(ptCardTable[i]);
		}
	}
	private void createAuctionButton()
	{
		bAuction = new JButton[20];
		lPass = new JButton("PASS");
		Bomba = new JButton("Bomba");
		
		for(int i=0; i<20; i++)
		{
			bAuction[i] = new JButton(Integer.toString(100+(i+1)*10));
			
			add(bAuction[i]);
			bAuction[i].addActionListener(this);
		}
		add(lPass);
		add(Bomba);
		lPass.addActionListener(this);
		Bomba.addActionListener(this);
		
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
		lPass.setBounds(715,620,130,30);
		Bomba.setBounds(855,620,130,30);
		
	}
	private void createPlayerBaner()
	{

		
		p1 = new JLabel();
		p2 = new JLabel();
		p3 = new JLabel();
		p4 = new JLabel();
		
		p1.setBounds(710,60,280,60);
		p2.setBounds(710,120,280,60);
		p3.setBounds(710,190,280,60);
		p4.setBounds(710,250,280,60);	
		
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
		
		LabelB = new JLabel();
		LabelL = new JLabel();
		LabelR = new JLabel();
		LabelT = new JLabel();
		
		LabelB.setBounds(150,500,80,25);
		LabelL.setBounds(25,25,80,25);
		LabelR.setBounds(610,25,80,25);
		LabelT.setBounds(150,0,80,25);	
		
		LabelB.setForeground(Color.white);
		LabelL.setForeground(Color.white);
		LabelR.setForeground(Color.white);
		LabelT.setForeground(Color.white);
		
		LabelB.setFont(new Font("Dejavu Sans", Font.BOLD, 10));
		LabelL.setFont(new Font("Dejavu Sans", Font.BOLD, 10)); 
		LabelR.setFont(new Font("Dejavu Sans", Font.BOLD, 10)); 
		LabelT.setFont(new Font("Dejavu Sans", Font.BOLD, 10)); 
		
		add(LabelB);
		add(LabelL);
		add(LabelR);
		add(LabelT);
		


		arrowB = new JLabel("\u25BC"); 
		arrowL = new JLabel("\u25C0"); 
		arrowR = new JLabel("\u25B6"); 
		arrowT = new JLabel("\u25B2"); 

		arrowB.setBounds(335,460,35,40);
		arrowL.setBounds(145,280,35,40);
		arrowR.setBounds(525,280,35,40);
		arrowT.setBounds(335,100,35,40);	
		
		arrowB.setForeground(Color.white);
		arrowL.setForeground(Color.white);
		arrowR.setForeground(Color.white);
		arrowT.setForeground(Color.white);
		
		
		arrowB.setVisible(false);
		arrowL.setVisible(false);
		arrowR.setVisible(false);
		arrowT.setVisible(false);
		
		arrowB.setFont(new Font("Dejavu Sans", Font.BOLD, 40));
		arrowL.setFont(new Font("Dejavu Sans", Font.BOLD, 40)); 
		arrowR.setFont(new Font("Dejavu Sans", Font.BOLD, 40)); 
		arrowT.setFont(new Font("Dejavu Sans", Font.BOLD, 40)); 
		
		add(arrowB);
		add(arrowL);
		add(arrowR);
		add(arrowT);
	}
	
	
	public void displayMovementArrow()
	{
		if(GaD.getTypeTable() == 3 && GaD.getPlace() == 1)
		{
			if(GaD.getMovement() == 1) arrowB.setVisible(true);
			else if(GaD.getMovement() == 2) arrowL.setVisible(true);
			else if(GaD.getMovement() == 3) arrowR.setVisible(true);
			else if(GaD.getMovement() == 4) arrowT.setVisible(true);
		}
		else if(GaD.getTypeTable() == 3 && GaD.getPlace() == 2)
		{

			if(GaD.getMovement() == 1) arrowR.setVisible(true);
			else if(GaD.getMovement() == 2) arrowB.setVisible(true);
			else if(GaD.getMovement() == 3) arrowL.setVisible(true);
			else if(GaD.getMovement() == 4) arrowT.setVisible(true);
		}
		else if(GaD.getTypeTable() == 3 && GaD.getPlace() == 3)
		{

			if(GaD.getMovement() == 1) arrowL.setVisible(true);
			else if(GaD.getMovement() == 2) arrowR.setVisible(true);
			else if(GaD.getMovement() == 3) arrowB.setVisible(true);
			else if(GaD.getMovement() == 4) arrowT.setVisible(true);
		}
		else if(GaD.getTypeTable() == 4 && GaD.getPlace() == 1)
		{

			if(GaD.getMovement() == 1) arrowB.setVisible(true);
			else if(GaD.getMovement() == 2) arrowL.setVisible(true);
			else if(GaD.getMovement() == 3) arrowR.setVisible(true);
			else if(GaD.getMovement() == 4) arrowT.setVisible(true);
		}
		else if(GaD.getTypeTable() == 4 && GaD.getPlace() == 2)
		{

			if(GaD.getMovement() == 1) arrowR.setVisible(true);
			else if(GaD.getMovement() == 2) arrowB.setVisible(true);
			else if(GaD.getMovement() == 3) arrowT.setVisible(true);
			else if(GaD.getMovement() == 4) arrowR.setVisible(true);
		}
		else if(GaD.getTypeTable() == 4 && GaD.getPlace() == 3)
		{

			if(GaD.getMovement() == 1) arrowL.setVisible(true);
			else if(GaD.getMovement() == 2) arrowT.setVisible(true);
			else if(GaD.getMovement() == 3) arrowB.setVisible(true);
			else if(GaD.getMovement() == 4) arrowR.setVisible(true);
		}
		else if(GaD.getTypeTable() == 4 && GaD.getPlace() == 4)
		{

			if(GaD.getMovement() == 1) arrowT.setVisible(true);
			else if(GaD.getMovement() == 2) arrowR.setVisible(true);
			else if(GaD.getMovement() == 3) arrowL.setVisible(true);
			else if(GaD.getMovement() == 4) arrowB.setVisible(true);
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
	
	public void displayCard(boolean showCard)
	{
		User pb = null, pl = null, pr = null, pt = null;	
		
		for(int i=0; i<24; i++)
		{
			pbCardHand[i].setVisible(false);
			pbCardTable[i].setVisible(false);
			pbCardWinning[i].setVisible(false);


			plCardHand[i].setVisible(false);
			plCardHandB[i].setVisible(false);
			plCardTable[i].setVisible(false);

			prCardHand[i].setVisible(false);
			prCardHandB[i].setVisible(false);
			prCardTable[i].setVisible(false);

			ptCardHand[i].setVisible(false);
			ptCardHandB[i].setVisible(false);
			ptCardTable[i].setVisible(false);
		}
		
		
		if(GaD.getTypeTable() == 3 && GaD.getPlace() == 1)
		{
			pb = GaD.getPlayer1();
			pl = GaD.getPlayer2();
			pr = GaD.getPlayer3();
			pt = GaD.getPlayer4();
		}
		else if(GaD.getTypeTable() == 3 && GaD.getPlace() == 2)
		{
			pb = GaD.getPlayer2();
			pl = GaD.getPlayer3();
			pr = GaD.getPlayer1();
			pt = GaD.getPlayer4();
		}
		else if(GaD.getTypeTable() == 3 && GaD.getPlace() == 3)
		{
			pb = GaD.getPlayer3();
			pl = GaD.getPlayer1();
			pr = GaD.getPlayer2();
			pt = GaD.getPlayer4();
		}
		else if(GaD.getTypeTable() == 4 && GaD.getPlace() == 1)
		{
			pb = GaD.getPlayer1();
			pl = GaD.getPlayer2();
			pr = GaD.getPlayer3();
			pt = GaD.getPlayer4();
		}
		else if(GaD.getTypeTable() == 4 && GaD.getPlace() == 2)
		{
			pb = GaD.getPlayer2();
			pl = GaD.getPlayer4();
			pr = GaD.getPlayer1();
			pt = GaD.getPlayer3();
		}
		else if(GaD.getTypeTable() == 4 && GaD.getPlace() == 3)
		{
			pb = GaD.getPlayer3();
			pl = GaD.getPlayer1();
			pr = GaD.getPlayer4();
			pt = GaD.getPlayer2();
		}
		else if(GaD.getTypeTable() == 4 && GaD.getPlace() == 4)
		{
			pb = GaD.getPlayer4();
			pl = GaD.getPlayer3();
			pr = GaD.getPlayer2();
			pt = GaD.getPlayer1();
		}
		
		
		
		int [] pb_h = pb.getScHand().getCards();
		int [] pb_t = pb.getScTable().getCards();
		int [] pb_w = pb.getScWinnings().getCards();

		int [] pl_h = pl.getScHand().getCards();
		int [] pl_t = pl.getScTable().getCards();

		int [] pr_h = pr.getScHand().getCards();
		int [] pr_t = pr.getScTable().getCards();

		int [] pt_h = pt.getScHand().getCards();
		int [] pt_t = pt.getScTable().getCards();
		
		
		//Karty gracza na dole Hand table i Winning
		for(int i=0; i<pb_h.length; i++)
		{
			pbCardHand[pb_h[i]].setLocation(25+i*70 +((10-pb_h.length)*35), 525);
			pbCardHand[pb_h[i]].setVisible(true);
		}
		for(int i=0; i<pb_t.length; i++)
		{
			pbCardTable[pb_t[i]].setLocation(320, 350);
			pbCardTable[pb_t[i]].setVisible(true);
		}
		for(int i=0; i<pb_w.length; i++)
		{
			pbCardWinning[pb_w[i]].setLocation(10+i*28, 630);
			pbCardWinning[pb_w[i]].setVisible(true);
		}
		
	
		//Karty po lewo Hand i table
		for(int i=0; i<pl_h.length; i++)
		{
			if(showCard == true)
			{
				plCardHand[pl_h[i]].setLocation(25, 70+i*45 +((10-pl_h.length)*22));
				plCardHand[pl_h[i]].setVisible(true);
			}
			else
			{
				plCardHandB[pl_h[i]].setLocation(25, 70+i*45 +((10-pl_h.length)*22));
				plCardHandB[pl_h[i]].setVisible(true);
			}
		}
		for(int i=0; i<pl_t.length; i++)
		{
			plCardTable[pl_t[i]].setVisible(true);
		}
		

		//Karty po prawo Hand i table
		for(int i=0; i<pr_h.length; i++)
		{
			if(showCard == true)
			{
				prCardHand[pr_h[i]].setLocation(605, 70+i*45 +((10-pr_h.length)*22));
				prCardHand[pr_h[i]].setVisible(true);
			}
			else
			{
				prCardHandB[pr_h[i]].setLocation(605, 70+i*45 +((10-pr_h.length)*22));
				prCardHandB[pr_h[i]].setVisible(true);
			}
		}
		for(int i=0; i<pr_t.length; i++)
		{
			prCardTable[pr_t[i]].setVisible(true);
		}
		


		//Karty po góra Hand i table
		for(int i=0; i<pt_h.length; i++)
		{
			if(showCard == true)
			{
				ptCardHand[pt_h[i]].setLocation(125+i*45 +((10-pt_h.length)*22), 25);
				ptCardHand[pt_h[i]].setVisible(true);
			}
			else
			{
				ptCardHandB[pt_h[i]].setLocation(125+i*45 +((10-pt_h.length)*22), 25);
				ptCardHandB[pt_h[i]].setVisible(true);
			}
		}
		for(int i=0; i<pt_t.length; i++)
		{
			ptCardTable[pt_t[i]].setVisible(true);
		}

		
	}
	
	
	

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		if(source == lPass)
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
	
	public void setPlayerName(String player1, String player2, String player3, String player4)
	{
		p1.setText("<html>Player1: <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + player1 + "</html>");
		p2.setText("<html>Player2: <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + player2 + "</html>");
		p3.setText("<html>Player3: <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + player3 + "</html>");
		p4.setText("<html>Player4: <br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + player4 + "</html>");
		
		

		
		if(GaD.getTypeTable() == 3 && GaD.getPlace() == 1)
		{
			LabelB.setText(player1);
			LabelL.setText(player2);
			LabelR.setText(player3);
			LabelT.setText(player4);
		}
		else if(GaD.getTypeTable() == 3 && GaD.getPlace() == 2)
		{
			LabelB.setText(player2);
			LabelL.setText(player3);
			LabelR.setText(player1);
			LabelT.setText(player4);
		}
		else if(GaD.getTypeTable() == 3 && GaD.getPlace() == 3)
		{
			LabelB.setText(player3);
			LabelL.setText(player1);
			LabelR.setText(player2);
			LabelT.setText(player4);
		}
		else if(GaD.getTypeTable() == 4 && GaD.getPlace() == 1)
		{
			LabelB.setText(player1);
			LabelL.setText(player2);
			LabelR.setText(player3);
			LabelT.setText(player4);
		}
		else if(GaD.getTypeTable() == 4 && GaD.getPlace() == 2)
		{
			LabelB.setText(player2);
			LabelL.setText(player4);
			LabelR.setText(player1);
			LabelT.setText(player3);
		}
		else if(GaD.getTypeTable() == 4 && GaD.getPlace() == 3)
		{
			LabelB.setText(player3);
			LabelL.setText(player1);
			LabelR.setText(player4);
			LabelT.setText(player2);
		}
		else if(GaD.getTypeTable() == 4 && GaD.getPlace() == 4)
		{
			LabelB.setText(player4);
			LabelL.setText(player3);
			LabelR.setText(player2);
			LabelT.setText(player1);
		}
		
	}

	public GameData getGameData() 
	{
		return GaD;
	}
	public void closeWindow()
	{
		dispose();
	}
}
