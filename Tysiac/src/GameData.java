import java.awt.Color;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameData 
{
	//===================================== Zmienne ==============================
	protected volatile int idTable; // numer id stołu w bazie danych -> często potrzebne 
	protected volatile String accessCode; // kod dostępu do stołu -> generowane przez Tworzącego stół... potrzebne by dołączyć do stołu... tworzone ze stringu z datą, więc nie powinno być powtórek
	protected volatile int typeTable;
	
	protected String login;
	protected String password;
	
	protected int idUser; //identifikator użytkownika w bazie
	protected int place;  //miejsce przy stole  

	protected User player1, player2, player3, player4; // user/ bot lub musek 
	protected User player;

	protected int must; // kto ma musek (1-4)
	protected int movement; // kto wykonuje ruch
	protected int started; // kto wykonuje ruch
	protected int trio; // kto ma 3 karty (jeżeli stół na 3 osoby to gracz 4)

	protected int auction; // wartość licytacji
	protected int auctionPlayer; // kto się licytuje i wygrywa (potem kto wygrał jak reszta spasuje)
	protected int auctionSurrender; //czy spasował licytację 
	

	protected Card[] cards = new Card[25];
	protected SQL sql = null;

	
	

	LoginWindow LogW;
	GameWindow GameW;
	JoinCreateTWindow JCWin;
	BOTJoinWindow BOTW;
	Color stol;

	protected volatile int loginClick = 0; // flaga czy był kliknięty guzik Logowania, 
	protected volatile int playClick = 0;
	protected volatile int playClick2 = 0; //0 - nothing, 1 - Create, 2 - JOIN
	

	
	protected int [] cp1; //= {10, 7, 9, 8, 18, 17, 15};
	protected int [] cp2; //= {11, 21, 4, 12, 0, 16, 23};
	protected int [] cp3; //= {20, 5, 2, 13, 19, 3, 22};
	protected int [] cp4; //= {14, 6, 1};
	
	protected int Round = 0,tmpRound = 0;
	
	//int checkL;
	
	//=============================================================================
	public GameData() throws TysiacException
	{
		
		sql = new SQL(this);
		
		cards = sql.getCards();
		
		LogW = new LoginWindow(this);
		JCWin = new JoinCreateTWindow(this);
		BOTW = new BOTJoinWindow(this);
		GameW = new GameWindow(this);
		stol = new Color(0,102,0);
	}
	public void close()
	{
		sql.close(); // kończenie połączenia z bazą danych 
		LogW.closeWindow();
		BOTW.closeWindow();
		GameW.closeWindow();
		JCWin.closeWindow();
	}
	//------------------------------------------------------------------------------
	protected void displayLoginWindow()
	{
		
		LogW.setVisible(true);
		LogW.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		LogW.getContentPane().setBackground(stol);
		LogW.setTitle("Tysiąc");
		LogW.setResizable(false);
		
		LogW.addWindowListener(new java.awt.event.WindowAdapter() 
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) 
			{
				if (JOptionPane.showConfirmDialog(LogW, "Chcesz zakończyć grę?", "Koniec?", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
					LogW.getGameData().close();
					System.exit(0);
				}	
			}
		});
	}
	protected void displayJoinCreateTWindow()
	{
		JCWin.setVisible(true);
		JCWin.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JCWin.getContentPane().setBackground(stol);
		JCWin.setTitle("Tysiąc");
		JCWin.setResizable(false);
		
		JCWin.addWindowListener(new java.awt.event.WindowAdapter() 
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) 
			{
				if (JOptionPane.showConfirmDialog(JCWin, "Chcesz zakończyć grę?", "Koniec?", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
					JCWin.getGameData().close();
					System.exit(0);
				}	
			}
		});
	}
	protected void displayBOTJoinWindow()
	{
		BOTW.setVisible(true);
		BOTW.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		BOTW.getContentPane().setBackground(stol);
		BOTW.setTitle("BOT - Tysiąc");
		BOTW.setResizable(false);
		
		BOTW.addWindowListener(new java.awt.event.WindowAdapter() 
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) 
			{
				if (JOptionPane.showConfirmDialog(BOTW, "Chcesz zakończyć grę?", "Koniec?", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
					BOTW.getGameData().close();
					System.exit(0);
				}	
			}
		});
	}
	protected void displayGameWindow()
	{
		GameW.setVisible(true);
		GameW.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		GameW.getContentPane().setBackground(stol);
		GameW.setTitle("Tysiąc");
		GameW.setResizable(false);
		
		GameW.addWindowListener(new java.awt.event.WindowAdapter() 
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) 
			{
				if (JOptionPane.showConfirmDialog(GameW, "Chcesz zakończyć grę?", "Koniec?", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				{
					GameW.getGameData().close();
					System.exit(0);
				}	
			}
		});
	}
	

	

	

	

	//===================================== Funkcje ==============================

	protected int getIdUser() 
	{
		return idUser;
	}
	protected int getIdTable() 
	{
		return idTable;
	}
	protected String getLogin() 
	{
		return login;
	}
	protected String getPassword() 
	{
		return password;
	}
	protected String getAccessCode() 
	{
		return accessCode;
	}
	protected int getPlace() 
	{
		return place;
	}
	protected User getPlayer1() 
	{
		return player1;
	}
	protected User getPlayer2() 
	{
		return player2;
	}
	protected User getPlayer3() 
	{
		return player3;
	}
	protected User getPlayer4() 
	{
		return player4;
	}
	protected int getMust() 
	{
		return must;
	}
	protected int getMovement() 
	{
		return movement;
	}
	protected int getStarted() 
	{
		return started;
	}
	protected int getTrio() 
	{
		return trio;
	}
	protected int getAuction() 
	{
		return auction;
	}
	protected int getAuctionPlayer() 
	{
		return auctionPlayer;
	}
	protected int getAuctionSurrender() 
	{
		return auctionSurrender;
	}
	protected int getLoginClick() 
	{
		return loginClick;
	}
	protected int getPlayClick() 
	{
		return playClick;
	}
	protected int getPlayClick2() 
	{
		return playClick2;
	}
	protected int getTypeTable() 
	{
		return typeTable;
	}
	
	
	
	

	protected void setIdUser(int idUser) 
	{
		this.idUser = idUser;
	}
	protected void setIdTable(int idTable) 
	{
		this.idTable = idTable;
	}
	protected void setLogin(String l) 
	{
		login = l;
	}
	protected void setPassword(String p) 
	{
		password = p;
	}
	protected void setAccessCode(String accessCode) 
	{
		this.accessCode = accessCode;
	}
	protected void setPlace(int place) 
	{
		this.place = place;
	}
	protected void setPlayer1(User player1) 
	{
		this.player1 = player1;
	}
	protected void setPlayer2(User player2) 
	{
		this.player2 = player2;
	}
	protected void setPlayer3(User player3) 
	{
		this.player3 = player3;
	}
	protected void setPlayer4(User player4) 
	{
		this.player4 = player4;
	}
	protected void setMust(int must) 
	{
		this.must = must;
	}
	protected void setMovement(int movement) 
	{
		this.movement = movement;
	}
	protected void setStarted(int started) 
	{
		this.started = started;
	}
	protected void setTrio(int trio) 
	{
		this.trio = trio;
	}
	protected void setAuction(int auction) 
	{
		this.auction = auction;
	}
	protected void setAuctionPlayer(int auctionPlayer) 
	{
		this.auctionPlayer = auctionPlayer;
	}
	protected void setAuctionSurrender(int auctionSurrender) 
	{
		this.auctionSurrender = auctionSurrender;
	}
	protected void setLoginClick(int loginClick) 
	{
		this.loginClick = loginClick;
	}
	protected void setPlayClick(int playClick) 
	{
		this.playClick = playClick;
	}
	protected void setPlayClick2(int playClick) 
	{
		this.playClick2 = playClick;
	}
	protected void setTypeTable(int typeTable) 
	{
		this.typeTable = typeTable;
	}
	
	
	
	
	
	
	
	
	
	
	
	protected void SignIn() throws TysiacException
	{
		int tmp=0;
		do
		{
			this.setLoginClick(0);
			this.displayLoginWindow();
			while(this.getLoginClick() == 0);
			this.LogW.setVisible(false);
			tmp = sql.checkLogin(getLogin());
			if(tmp == -2) 
			{
				JOptionPane.showMessageDialog(null,"Użyto niepoprawnego symbolu w loginie.");
			}
			else if(tmp == -1) 
			{
				JOptionPane.showMessageDialog(null,"Login zbyt krótki, min 5 znaków");
			}
			else if(tmp == 0) 
			{
				JOptionPane.showMessageDialog(null,"Brak podanego loginu w bazie");
			}
			else
			{
				tmp = sql.loginToGame(getLogin(), getPassword());
				if(tmp <= 0)
				{
					JOptionPane.showMessageDialog(null,"Błędny Login lub Hasło");
				}
			}
			this.LogW.resetPasswordField();
			
		}
		while(tmp<=0);
		
		this.setIdUser(tmp);
	}

	
	protected void SitToTable() throws TysiacException
	{
		int tmp=0;
		do
		{

			this.setPlayClick(0);
			this.displayJoinCreateTWindow();
			while(this.getPlayClick() == 0);
			
			if(this.getPlayClick2() == 1)
			{
				sql.createTable(typeTable, idUser, 1, 2, 4);
				tmp = this.getIdTable();
				while(this.getAccessCode() == null);
				if(tmp <=0 )
				{
					JOptionPane.showMessageDialog(null,"Niepowodzenie przy tworzeniu stołu");
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Kod dostępu do stołu to: " + this.getAccessCode());
				}
			}
			else if(this.getPlayClick2() == 2)
			{
				sql.joinTable(idUser, accessCode);
				tmp = this.getIdTable();
				if(tmp <=0 )
				{
					JOptionPane.showMessageDialog(null,"Niepowodzenie przy dołączaniu do stołu");
				}
			}

			this.JCWin.setVisible(false);
		}
		while(tmp<=0);
		

		GameW.setTitle("Tysiąc - " + this.getAccessCode());
	}

	
	protected void BOTSitToTable() throws TysiacException
	{
		int tmp=0;
		do
		{

			this.setPlayClick(0);
			this.displayBOTJoinWindow();
			while(this.getPlayClick() == 0);
			
			sql.BOTjoinTable(accessCode);
			tmp = this.getIdTable();
			if(tmp <=0 )
			{
				JOptionPane.showMessageDialog(null,"Niepowodzenie przy dołączaniu do stołu");
			}
			

			this.BOTW.setVisible(false);
		}
		while(tmp<=0);
		

		GameW.setTitle("BOT - Tysiąc - " + this.getAccessCode());
	}


	
	
	
	
	
	
	
	protected void setPlayerInfo() throws TysiacException
	{
		this.setPlayer1(sql.selectUser(1)); //ustawianie class User Player1 <- SQL zwraca wszystkie informacje o użytkowiku 
		this.setPlayer2(sql.selectUser(2));
		this.setPlayer3(sql.selectUser(3));
		this.setPlayer4(sql.selectUser(4));
		
		GameW.setPlayerName(this.getPlayer1().getUserName(), this.getPlayer2().getUserName(), this.getPlayer3().getUserName(), this.getPlayer4().getUserName());
	}
	
	
	//============================= FUNKCJE od PIOTRKA ==========================

	
	protected void startMove()  //czyj pierwszy ruch
	{	
		try 
		{
			sql.createTable(this.getTypeTable(), this.getIdUser(), 1, 2, 3);
		} 
		catch (TysiacException e) 
		{
			e.printStackTrace();
		}
	}
	
	protected void check18()  //sprawdza czy jest 18 lub wiecej w kartach jeśli nie to losuje od nowa
	{
		int sumCardHand;
		for(int i = 1; i < 5; i++)
		{
			try 
			{
				sumCardHand = sql.getSumCardValue(1);
				
				if(sumCardHand < 18)
				{
					giveCards();
				}
			} 
			catch (TysiacException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	protected void checkFour9()  // sprawdza czy sa 4 dziewiatki jesli sa to od nowa losuje
	{
		int tmp1 = 0;
		int tmp2 = 0;
		int tmp3 = 0;
		int tmp4 = 0;
		
		for(int i=0; i<8; i++)
		{
			if(cp1[i] == 5 || cp1[i] == 11 || cp1[i] == 17 || cp1[i] == 23)
			{
				tmp1++;
			}
			if(cp2[i] == 5 || cp2[i] == 11 || cp2[i] == 17 || cp2[i] == 23)
			{
				tmp2++;
			}
			if(cp3[i] == 5 || cp3[i] == 11 || cp3[i] == 17 || cp3[i] == 23)
			{
				tmp3++;
			}
			if(cp4[i] == 5 || cp4[i] == 11 || cp4[i] == 17 || cp4[i] == 23)
			{
				tmp4++;
			}
		}
		
		if(tmp1 == 4 || tmp2 == 4 || tmp3 == 4 || tmp4 == 4)
		{
			giveCards();
		}
	}
	
	protected void giveCards()  //przypisuje karty graczom
	{
		int[] tab = new int[24];
		
		for(int i=0; i<24; i++)
		{
			tab[i] = randomCards(tab,i);
		}
		
		for(int k=0; k<7; k++)
		{
			cp1[k] = tab[k]; 
			cp2[k] = tab[k+7]; 
			cp3[k] = tab[k+14];
			
			if(k<3)
			{
				cp4[k] = tab[k+21];
			}
		}
	}
	
	protected int randomCards(int [] tab, int i)  // losuje karte
	{
		int tmp;

        Random rand = new Random();
            
        tmp = rand.nextInt(24);
        checkCard(tmp,tab,i);

        return  tmp;
	}
	
	protected void checkCard(int tmp, int [] tab, int i)  // sprawdza czy sie powtórzyły karty
	{
		for(int spr=0; spr<i; spr++)
		{
			if(tmp == tab[spr])
			{
				randomCards(tab,i);
			}
		}
	}
	
	protected void checkReports()  // przypisuje meldunkowi numer gracza jesli ma
	{	
		int [][] cardP = new int [4][8];
		cardP = getCardsPlayers();
		
		for(int i=0; i<player1.getScHand().getCount();i++)
		{
			if(cardP[0][i] == 2 && cardP[0][i] == 3) player.setReports('C',1);		//meldunek 100 czerwo gracz1
			if(cardP[0][i] == 8 && cardP[0][i] == 9) player.setReports('Z',1);		//meldunek 40 żołądź gracz1
			if(cardP[0][i] == 14 && cardP[0][i] == 15) player.setReports('D',1);	//meldunek 80 dzwonek gracz1
			if(cardP[0][i] == 20 && cardP[0][i] == 21) player.setReports('W',1);	//meldunek 60  gracz1
			
			if(cardP[1][i] == 2 && cardP[1][i] == 3) player.setReports('C',2);		//meldunek 100 czerwo gracz2
			if(cardP[1][i] == 8 && cardP[1][i] == 9) player.setReports('Z',2);		//meldunek 40 żołądź gracz2
			if(cardP[1][i] == 14 && cardP[1][i] == 15) player.setReports('D',2);	//meldunek 80 dzwonek gracz2
			if(cardP[2][i] == 20 && cardP[2][i] == 21) player.setReports('W',2);	//meldunek 60 WINO gracz2
			
			if(cardP[2][i] == 2 && cardP[2][i] == 3) player.setReports('C',3);		//meldunek 100 czerwo gracz3			
			if(cardP[2][i] == 8 && cardP[2][i] == 9) player.setReports('Z',3);		//meldunek 40 żołądź gracz3			
			if(cardP[2][i] == 14 && cardP[2][i] == 15) player.setReports('D',3);	//meldunek 80 dzwonek gracz3			
			if(cardP[2][i] == 20 && cardP[2][i] == 21) player.setReports('W',3);	//meldunek 60 WINO gracz3
						
			if(cardP[3][i] == 2 && cardP[3][i] == 3) player.setReports('C',4);		//meldunek 100 czerwo gracz4			
			if(cardP[3][i] == 8 && cardP[3][i] == 9) player.setReports('Z',4);		//meldunek 40 żołądź gracz4			
			if(cardP[3][i] == 14 && cardP[3][i] == 15) player.setReports('D',4);	//meldunek 80 dzwonek gracz4			
			if(cardP[3][i] == 20 && cardP[3][i] == 21) player.setReports('W',4);		//meldunek 60 WINO gracz4				
		}		
	}
	
	protected int[][] getCardsPlayers()  //pobiera karty graczy
	{
		int [][] cardP = new int [4][8];

		for(int i=0; i<player1.getScHand().getCount(); i++)
		{
			cardP[0][i] = player1.getScHand().getCard(i);
			cardP[1][i] = player2.getScHand().getCard(i); 
			cardP[2][i] = player3.getScHand().getCard(i); 
			cardP[3][i] = player4.getScHand().getCard(i); 
		}
		
		return cardP;
	}
	
	protected int checkCards(int c1, int c2, int c3,int c4) throws TysiacException // sprawdza karty do kogo po tym jak sie wyrzuci z po meldunku  
	{																			// c1 to ruch pierwszy
		int whoCards = 0;
		int [] tab = new int [4]; 
		int min, nr = 0;
		char colorR = sql.getColor();
		
		tab[0] = c1;
		
		if(getColorC(c1) != getColorC(c2)) tab[1] = 100;
		else tab[1] = c2;
		
		if(getColorC(c1) != getColorC(c3)) tab[2] = 100;
		else tab[2] = c3;
		
		if(getColorC(c1) != getColorC(c4)) tab[3] = 100;
		else tab[3] = c4;
		
		if(getColorC(c1) == getColorC(c2) || getColorC(c1) == getColorC(c3) || getColorC(c1) == getColorC(c4))
		{
			min = tab[0];
			
			for(int i = 1; i<4; i++)
			{
				if(min > tab[i])
				{
					min = tab[i];
					nr = i; 
				}
			}
			whoCards = nr;
		}
		else whoCards = 1;
		
		if(getColorC(c2) == colorR || getColorC(c3) == colorR || getColorC(c4) == colorR)
		{
			whoCards = checkCardsReports(c1,c2,c3,c4);
		}
		
		return whoCards;
	}
	
	protected char getColorC(int card) //sprawdza kolor danej korty
	{
		char color = 0;
		
		if(card >= 0 && card < 6)
		{
			color = 'C';
		}
		else if(card > 5 && card < 12)
		{
			color = 'Z';
		}
		else if(card > 11 && card < 18)
		{
			color = 'D';
		}
		else if(card > 17 && card < 24)
		{
			color = 'W';
		}
		
		return color;
	}
	
	protected int checkCardsReports(int c1, int c2, int c3, int c4) //c1 to pierwszy ruch // sprawdza czyj ruch jak po meldunku
	{
		int whoCards = 0;
		int [] tab = new int [4]; 
		int min, nr = 0;
		
		try 
		{
			char colorR = sql.getColor();
			
			if(getColorC(c1) != colorR) tab[0] = 100;
			else tab[0] = c1;
			
			if(getColorC(c2) != colorR) tab[1] = 100;
			else tab[1] = c2;
			
			if(getColorC(c3) != colorR) tab[2] = 100;
			else tab[2] = c3;
			
			if(getColorC(c3) != colorR) tab[3] = 100;
			else tab[3] = c3;
			
			min = tab[0];
			
			for(int i = 1; i<4; i++)
			{
				if(min > tab[i])
				{
					min = tab[i];
					nr = i; 
				}
			}
			
			whoCards = nr;
		} 
		catch (TysiacException e) 
		{
			e.printStackTrace();
		}
	
		return whoCards;
	}
	
	protected int[][] checkSameColor(int c1) //sprawdza w tym samym kolorze jakie karty sa
	{
		int [][] cardsP = new int[4][8];
		int [][] cards = new int[4][8];
		cards = getCardsPlayers();
		
		char color = getColorC(c1);

		for(int i=0; i<player1.getScHand().getCount();i++)
		{
			if(getColorC(cardsP[0][i]) == color)
			{
				cards[0][i] = cardsP[0][i];
			}
			else cards[0][i] = -1;
			if(getColorC(cardsP[1][i]) == color)
			{
				cards[1][i] = cardsP[1][i];
			}
			else cards[1][i] = -1;
			if(getColorC(cardsP[2][i]) == color)
			{
				cards[2][i] = cardsP[2][i];
			}
			else cards[2][i] = -1;
			if(getColorC(cardsP[3][i]) == color)
			{
				cards[3][i] = cardsP[3][i];
			}
			else cards[3][i] = -1;
		}
		
		return cards;
	}
	
	protected int checkAuction800(int player) throws TysiacException // zwraca 1 jeśli można dodawać punkty inne niz w licytacji; 0 jesli nie mozna
	{
		int sumPoints;
		int flagAddP = 1;
		
		sumPoints = sql.getSumPoint(player);
		
		if(sumPoints < 800) 
		{
			flagAddP = 1;
		}
		else
		{
			flagAddP = 0;
		}
		
		return flagAddP;
	}
	
	protected int setRound() // ktora runda
	{
		tmpRound();
		return Round++;
	}
	
	protected void tmpRound()
	{
		int tmp = 0;
		tmp++;
		
		if(tmp > 4)
		{
			tmp = 0;
		}
		
		tmpRound = tmp;
	}
	
	protected void whose100() throws TysiacException // kto jest na 100 i kto na musku
	{
		if(tmpRound == 1)
		{
			trio = 4;
			must = 3;
			movement = 1;
			started = 3;
		}
		else if(tmpRound ==2)
		{
			trio = 3;
			must = 1;
			movement = 2;
			started = 3;
		}
		else if(tmpRound ==3)
		{
			trio = 1;
			must = 2;
			movement = 4;
			started = 2;
		}
		else if(tmpRound ==4)
		{
			trio = 2;
			must = 4;
			movement = 3;
			started = 1;
		}
		
		sql.setTrio(trio);
		sql.setMust(must);
		sql.setMovement(movement);
		sql.setStarted(started);
	}
	
	/*protected int setStartMovementRound() // kto zaczyna dana runde (ten co wylicytował najwiecej)
	{
		int who;
		
		who = checkMaxAuctioner();
		
		return who;
	}*/
	
	protected int checkMaxAuctioner(int gamer1, int gamer2, int gamer3, int gamer4) // kto wylicytował najwiecej
	{
		int tab[] = new int[4];
		int max, tmp = 0;
		
		tab[0] = gamer1;
		tab[1] = gamer2;
		tab[2] = gamer3;
		tab[3] = gamer4;
		
		max = tab[0];
		
		for(int i=1; i<4; i++)
		{
			if(max < tab[i])
			{
				max = tab[i];
				tmp = i;
			}
		}
		
		return tmp;
	}
}
