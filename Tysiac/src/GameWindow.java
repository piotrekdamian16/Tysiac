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
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GameWindow extends JFrame implements ActionListener
{
	private JButton [] bAuction;
	private JLabel bAuctionValue, bAuctionPlayer;
	private JButton lPass, Bomba;
	private JButton line;
	private JLabel p1,p2,p3,p4;
	private JButton ptk1, ptk2, ptk3, ptk4;
	private JLabel LabelB, LabelL, LabelR, LabelT;
	private JLabel arrowB, arrowL, arrowR, arrowT;
	private JLabel statusText, status;
	private int auction;
	private GameData GaD;
	private JTextField accessCode;
	
	

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
		line.setBounds(300, 0, 5, 700);
		add(line);
		line.setEnabled(false);
		

		statusText = new JLabel("Status Gry:");
		statusText.setBounds(10, 300, 280, 25);
		statusText.setFont(new Font("Dejavu Sans", Font.BOLD, 15));
		statusText.setForeground(Color.white);
		statusText.setBackground(new Color(20,80,0));
		statusText.setOpaque(true);
		add(statusText);
		

		status = new JLabel();
		status.setBounds(10, 325, 280, 25);
		status.setFont(new Font("Dejavu Sans", Font.BOLD, 15));
		status.setForeground(Color.white);
		status.setBackground(new Color(20,80,0));
		status.setOpaque(true);
		add(status);
		
		
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
	}
	private void createAuctionButton()
	{
		bAuction = new JButton[20];
		lPass = new JButton("PASS");
		Bomba = new JButton("Bomba");
		bAuctionValue = new JLabel();
		bAuctionPlayer = new JLabel();

		bAuctionPlayer.setForeground(Color.WHITE);
		bAuctionValue.setForeground(Color.WHITE);
		
		for(int i=0; i<20; i++)
		{
			bAuction[i] = new JButton(Integer.toString(100+(i+1)*10));
			
			add(bAuction[i]);
			bAuction[i].addActionListener(this);
		}
		add(lPass);
		add(Bomba);
		add(bAuctionValue);
		add(bAuctionPlayer);
		lPass.addActionListener(this);
		Bomba.addActionListener(this);
		
		bAuction[0].setBounds(15,445,60,30);  //ustaw przyciski licytacji
		bAuction[1].setBounds(85,445,60,30);
		bAuction[2].setBounds(155,445,60,30);
		bAuction[3].setBounds(225,445,60,30);
		bAuction[4].setBounds(15,480,60,30);
		bAuction[5].setBounds(85,480,60,30);
		bAuction[6].setBounds(155,480,60,30);
		bAuction[7].setBounds(225,480,60,30);
		bAuction[8].setBounds(15,515,60,30);
		bAuction[9].setBounds(85,515,60,30);
		bAuction[10].setBounds(155,515,60,30);
		bAuction[11].setBounds(225,515,60,30);
		bAuction[12].setBounds(15,550,60,30);
		bAuction[13].setBounds(85,550,60,30);
		bAuction[14].setBounds(155,550,60,30);
		bAuction[15].setBounds(225,550,60,30);
		bAuction[16].setBounds(15,585,60,30);
		bAuction[17].setBounds(85,585,60,30);
		bAuction[18].setBounds(155,585,60,30);
		bAuction[19].setBounds(225,585,60,30);
		lPass.setBounds(15,620,130,30);
		Bomba.setBounds(155,620,130,30);

		bAuctionPlayer.setBounds(10, 350, 140, 25);
		bAuctionValue.setBounds(150, 350, 140, 25);
		
	}
	private void createPlayerBaner()
	{

		
		p1 = new JLabel();
		p2 = new JLabel();
		p3 = new JLabel();
		p4 = new JLabel();
		
		p1.setBounds(10,10,280,40);
		p2.setBounds(10,80,280,40);
		p3.setBounds(10,150,280,40);
		p4.setBounds(10,220,280,40);	
		
		p1.setForeground(Color.white);
		p2.setForeground(Color.white);
		p3.setForeground(Color.white);
		p4.setForeground(Color.white);
		
		p1.setBackground(new Color(20,80,0));
		p2.setBackground(new Color(20,80,0));
		p3.setBackground(new Color(20,80,0));
		p4.setBackground(new Color(20,80,0));

		p1.setOpaque(true);
		p2.setOpaque(true);
		p3.setOpaque(true);
		p4.setOpaque(true);
		
		p1.setFont(new Font("Dejavu Sans", Font.BOLD, 15));
		p2.setFont(new Font("Dejavu Sans", Font.BOLD, 15)); 
		p3.setFont(new Font("Dejavu Sans", Font.BOLD, 15)); 
		p4.setFont(new Font("Dejavu Sans", Font.BOLD, 15)); 
		
		add(p1);
		add(p2);
		add(p3);
		add(p4);
		

		
		ptk1 = new JButton("0");
		ptk2 = new JButton("0");
		ptk3 = new JButton("0");
		ptk4 = new JButton("0");
		
		ptk1.setBounds(10,50,280,20);
		ptk2.setBounds(10,120,280,20);
		ptk3.setBounds(10,190,280,20);
		ptk4.setBounds(10,260,280,20);	
		
		ptk1.setBackground(Color.white);
		ptk2.setBackground(Color.white);
		ptk3.setBackground(Color.white);
		ptk4.setBackground(Color.white);
		
		ptk1.setFont(new Font("Dejavu Sans", Font.BOLD, 14));
		ptk2.setFont(new Font("Dejavu Sans", Font.BOLD, 14)); 
		ptk3.setFont(new Font("Dejavu Sans", Font.BOLD, 14)); 
		ptk4.setFont(new Font("Dejavu Sans", Font.BOLD, 14)); 
		
		
		add(ptk1);
		add(ptk2);
		add(ptk3);
		add(ptk4);
	
		
		
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
	
	
	public void displayMovementArrow() throws TysiacException
	{
		GaD.setMovement();
		
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
	public void enableAuctionOver(int value)
	{
		int i=0; 
		if(value > 100) i = (value - 100)/10-1;
		for(int j=0; j<=i; j++)
		{
			bAuction[i].setEnabled(false);
		}
		for(; i<20; i++)
		{
			bAuction[i].setEnabled(true);
		}
	}
	public void enableCard(boolean show)
	{
		for(int i=0; i<24; i++)
		{
			pbCardHand[i].setEnabled(show);
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
			pbCardHand[pb_h[i]].setLocation(325+i*70 +((10-pb_h.length)*35), 525);
			pbCardHand[pb_h[i]].setVisible(true);
		}
		for(int i=0; i<pb_t.length; i++)
		{
			pbCardTable[pb_t[i]].setLocation(620, 350);
			pbCardTable[pb_t[i]].setVisible(true);
		}
		for(int i=0; i<pb_w.length; i++)
		{
			pbCardWinning[pb_w[i]].setLocation(310+i*28, 630);
			pbCardWinning[pb_w[i]].setVisible(true);
		}
		
	
		//Karty po lewo Hand i table
		for(int i=0; i<pl_h.length; i++)
		{
			if(showCard == true)
			{
				plCardHand[pl_h[i]].setLocation(325, 70+i*45 +((10-pl_h.length)*22));
				plCardHand[pl_h[i]].setVisible(true);
			}
			else
			{
				plCardHandB[pl_h[i]].setLocation(325, 70+i*45 +((10-pl_h.length)*22));
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
				prCardHand[pr_h[i]].setLocation(905, 70+i*45 +((10-pr_h.length)*22));
				prCardHand[pr_h[i]].setVisible(true);
			}
			else
			{
				prCardHandB[pr_h[i]].setLocation(905, 70+i*45 +((10-pr_h.length)*22));
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
				ptCardHand[pt_h[i]].setLocation(425+i*45 +((10-pt_h.length)*22), 25);
				ptCardHand[pt_h[i]].setVisible(true);
			}
			else
			{
				ptCardHandB[pt_h[i]].setLocation(425+i*45 +((10-pt_h.length)*22), 25);
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
		
		for(int i=0; i<24; i++)
		{
			if(source == pbCardHand[i])
			{
				try 
				{
					GaD.sql.setStackCard(i, 's');
					GaD.nextPlayer();
				} 
				catch (TysiacException e1) 
				{
					JOptionPane.showMessageDialog(null,e1.getErrorMessage());
					e1.printStackTrace();
				}
				
				this.enableCard(false);
			}
		}
		for(int i=0; i<20; i++)
		{
			if(source == bAuction[i])
			{
				try 
				{
					GaD.sql.setAuctionValue(100+(i+1)*10);
					GaD.sql.setAuctionPlayer(GaD.getPlace());
					GaD.sql.setStatus(GaD.sql.getStatus()+".");
					GaD.nextPlayer();
				} 
				catch (TysiacException e1) 
				{
					JOptionPane.showMessageDialog(null,e1.getErrorMessage());
					e1.printStackTrace();
				}
			}
		}

		if(source == lPass)
		{
			GaD.setAuctionSurrender(true);
			try 
			{
				GaD.nextPlayer();
			} 
			catch (TysiacException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(source == Bomba)
		{
			try 
			{
				GaD.sql.setBomb(GaD.getPlace(), 1);
				GaD.nextPlayer();
			} 
			catch (TysiacException e1) 
			{
				JOptionPane.showMessageDialog(null,e1.getErrorMessage());
				e1.printStackTrace();
			}
			if(GaD.getPlace() == 1) GaD.getPlayer1().setBomb(1);
			else if(GaD.getPlace() == 2) GaD.getPlayer2().setBomb(1);
			else if(GaD.getPlace() == 3) GaD.getPlayer3().setBomb(1);
			else if(GaD.getPlace() == 4) GaD.getPlayer4().setBomb(1);
			remove(Bomba);
		}
	}
	
	
	
	public void setAuction(int amount) 
	{
		this.auction = amount;
	}
	
	public int getAuction() 
	{
		return this.auction;
	}
	
	
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

	public void setPlayer1Point(int point)
	{
		ptk1.setText(Integer.toString(point));
	}
	public void setPlayer2Point(int point)
	{
		ptk2.setText(Integer.toString(point));
	}
	public void setPlayer3Point(int point)
	{
		ptk3.setText(Integer.toString(point));
	}
	public void setPlayer4Point(int point)
	{
		ptk4.setText(Integer.toString(point));
	}
	public void setbAuctionValue(int value) 
	{
		bAuctionValue.setText(Integer.toString(value));
	}
	public void setbAuctionPlayer(int player) 
	{
		bAuctionPlayer.setText("Player" + Integer.toString(player) + ": ");
	}

	public void setPlayersPoint(int point1, int point2, int point3)
	{
		this.setPlayer1Point(point1);
		this.setPlayer2Point(point2);
		this.setPlayer3Point(point3);
	}
	public void setPlayersPoint(int point1, int point2, int point3, int point4)
	{
		this.setPlayer1Point(point1);
		this.setPlayer2Point(point2);
		this.setPlayer3Point(point3);
		this.setPlayer4Point(point4);
	}
	
	public void setStatus(String s)
	{
		status.setText(s);
	}
	

	public GameData getGameData() 
	{
		return GaD;
	}
	public void closeWindow()
	{
		dispose();
	}

	public JTextField getAccessCode() {
		return accessCode;
	}

	public void displayAccessCode(String ac) 
	{
		accessCode = new JTextField(ac);
		accessCode.setFont(f100);
		accessCode.setBounds(100, 300, 500, 100);
		accessCode.setVisible(true);
		add(accessCode);
	}
	public void removeAccessCode()
	{
		remove(accessCode);
	}

}
