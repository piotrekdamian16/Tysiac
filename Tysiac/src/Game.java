import java.sql.SQLException;

public class Game 
{	
	private static int idTable; // numer id stołu w bazie danych -> często potrzebne 
	private static String accessCode; // kod dostępu do stołu -> generowane przez Tworzącego stół... potrzebne by dołączyć do stołu... tworzone ze stringu z datą, więc nie powinno być powtórek
	
	private static int idUser; //identifikator użytkownika w bazie
	private static int place;  //miejsce przy stole  

	private static User player1; // zmienna przechowująca dane o użytkowniku -> Hostujący stół
	private static User player2; // user zwykły lub bot
	private static User player3;
	private static User player4; // user/ bot lub musek 

	private static int must; // kto ma musek (1-4)
	private static int movement; // kto wykonuje ruch
	private static int trio; // kto ma 3 karty (jeżeli stół na 3 osoby to gracz 4)

	private static int auction; // wartość licytacji
	private static int auctionPlayer; // kto się licytuje i wygrywa (potem kto wygrał jak reszta spasuje)
	private static int auctionSurrender; //czy spasował licytację 
	
	public static void main(String[] args)
	{
		Card[] cards = new Card[25];
		
		try {
			SQL sql = new SQL(); //Łączy się z bazą danych... jeżeli będzie zmiana bazdy danych to użyć konstruktora parametrowego 
			
			cards = sql.getCards(); // Pobiera informacje o kartach 
			
			for(int i=0; i<25; i++)
			{
				cards[i].display(); // wyświutla karty -> funkcja do testów
			}

			sql.BOTjoinTable("1d81beaeed"); //dodawanie BOTa do stołu o danym kodzie dostępu... 
			sql.BOTjoinTable("1d81beaeed");

			setPlayer1(sql.selectUser(1)); //ustawianie class User Player1 <- SQL zwraca wszystkie informacje o użytkowiku 
			setPlayer2(sql.selectUser(2));
			setPlayer3(sql.selectUser(3));
			setPlayer4(sql.selectUser(4));
			
			player1.display(); // wyświetla dane o użytkowniku  -> funkcja do testów
			player2.display();
			player3.display();
			player4.display(); // jeżeli typ_stołu jest 3 -> to jest to 'gracz' MUSEK, inaczej normalny użytkownik 
			
			System.out.println("ID Gracza na miejscu 1: " + sql.checkUser(1)); // funkcja sql sprawdza czy na danym miejscu jest już jakiś gracz... 0 -> nie... inne to ID_user
			
			sql.close(); // kończenie połączenia z bazą danych 
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }


	public static int getIdUser() 
	{
		return idUser;
	}
	public static int getIdTable() 
	{
		return idTable;
	}
	public static String getAccessCode() 
	{
		return accessCode;
	}
	public static int getPlace() 
	{
		return place;
	}
	public static User getPlayer1() 
	{
		return player1;
	}
	public static User getPlayer2() 
	{
		return player2;
	}
	public static User getPlayer3() 
	{
		return player3;
	}
	public static User getPlayer4() 
	{
		return player4;
	}
	public static int getMust() 
	{
		return must;
	}
	public static int getMovement() 
	{
		return movement;
	}
	public static int getTrio() 
	{
		return trio;
	}
	public static int getAuction() 
	{
		return auction;
	}
	public static int getAuctionPlayer() 
	{
		return auctionPlayer;
	}
	public static int getAuctionSurrender() 
	{
		return auctionSurrender;
	}

	
	

	public static void setIdUser(int idUser) 
	{
		Game.idUser = idUser;
	}
	public static void setIdTable(int idTable) 
	{
		Game.idTable = idTable;
	}
	public static void setAccessCode(String accessCode) 
	{
		Game.accessCode = accessCode;
	}
	public static void setPlace(int place) 
	{
		Game.place = place;
	}
	public static void setPlayer1(User player1) 
	{
		Game.player1 = player1;
	}
	public static void setPlayer2(User player2) 
	{
		Game.player2 = player2;
	}
	public static void setPlayer3(User player3) 
	{
		Game.player3 = player3;
	}
	public static void setPlayer4(User player4) 
	{
		Game.player4 = player4;
	}
	public static void setMust(int must) 
	{
		Game.must = must;
	}
	public static void setMovement(int movement) 
	{
		Game.movement = movement;
	}
	public static void setTrio(int trio) 
	{
		Game.trio = trio;
	}
	public static void setAuction(int auction) 
	{
		Game.auction = auction;
	}
	public static void setAuctionPlayer(int auctionPlayer) 
	{
		Game.auctionPlayer = auctionPlayer;
	}
	public static void setAuctionSurrender(int auctionSurrender) 
	{
		Game.auctionSurrender = auctionSurrender;
	}


}
