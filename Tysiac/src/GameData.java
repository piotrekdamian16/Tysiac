
public class GameData 
{
	//===================================== Zmienne ==============================
	protected static int idTable; // numer id stołu w bazie danych -> często potrzebne 
	protected static String accessCode; // kod dostępu do stołu -> generowane przez Tworzącego stół... potrzebne by dołączyć do stołu... tworzone ze stringu z datą, więc nie powinno być powtórek
	
	protected static int idUser; //identifikator użytkownika w bazie
	protected static int place;  //miejsce przy stole  

	protected static User player1; // zmienna przechowująca dane o użytkowniku -> Hostujący stół
	protected static User player2; // user zwykły lub bot
	protected static User player3;
	protected static User player4; // user/ bot lub musek 

	protected static int must; // kto ma musek (1-4)
	protected static int movement; // kto wykonuje ruch
	protected static int trio; // kto ma 3 karty (jeżeli stół na 3 osoby to gracz 4)

	protected static int auction; // wartość licytacji
	protected static int auctionPlayer; // kto się licytuje i wygrywa (potem kto wygrał jak reszta spasuje)
	protected static int auctionSurrender; //czy spasował licytację 
	

	protected static Card[] cards = new Card[25];
	protected static SQL sql = null;
	
	
	
	
	
	
	

	//===================================== Funkcje ==============================

	protected static int getIdUser() 
	{
		return idUser;
	}
	protected static int getIdTable() 
	{
		return idTable;
	}
	protected static String getAccessCode() 
	{
		return accessCode;
	}
	protected static int getPlace() 
	{
		return place;
	}
	protected static User getPlayer1() 
	{
		return player1;
	}
	protected static User getPlayer2() 
	{
		return player2;
	}
	protected static User getPlayer3() 
	{
		return player3;
	}
	protected static User getPlayer4() 
	{
		return player4;
	}
	protected static int getMust() 
	{
		return must;
	}
	protected static int getMovement() 
	{
		return movement;
	}
	protected static int getTrio() 
	{
		return trio;
	}
	protected static int getAuction() 
	{
		return auction;
	}
	protected static int getAuctionPlayer() 
	{
		return auctionPlayer;
	}
	protected static int getAuctionSurrender() 
	{
		return auctionSurrender;
	}

	
	

	protected static void setIdUser(int idUser) 
	{
		GameData.idUser = idUser;
	}
	protected static void setIdTable(int idTable) 
	{
		GameData.idTable = idTable;
	}
	protected static void setAccessCode(String accessCode) 
	{
		GameData.accessCode = accessCode;
	}
	protected static void setPlace(int place) 
	{
		GameData.place = place;
	}
	protected static void setPlayer1(User player1) 
	{
		GameData.player1 = player1;
	}
	protected static void setPlayer2(User player2) 
	{
		GameData.player2 = player2;
	}
	protected static void setPlayer3(User player3) 
	{
		GameData.player3 = player3;
	}
	protected static void setPlayer4(User player4) 
	{
		GameData.player4 = player4;
	}
	protected static void setMust(int must) 
	{
		GameData.must = must;
	}
	protected static void setMovement(int movement) 
	{
		GameData.movement = movement;
	}
	protected static void setTrio(int trio) 
	{
		GameData.trio = trio;
	}
	protected static void setAuction(int auction) 
	{
		GameData.auction = auction;
	}
	protected static void setAuctionPlayer(int auctionPlayer) 
	{
		GameData.auctionPlayer = auctionPlayer;
	}
	protected static void setAuctionSurrender(int auctionSurrender) 
	{
		GameData.auctionSurrender = auctionSurrender;
	}
}
