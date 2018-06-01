

public class Game extends GameData
{	
	//Wszystkie zmienne i Funkcje są w pliku GameData.java
	
	public Game() throws TysiacException
	{
		super();
	}
	
	public static void main(String[] args)
	{		
		Game game = null;
		try 
		{
			game = new Game();
			
			//game.SignIn();
			//game.SitToTable();
			
			//game.BOTSitToTable();
			
			game.setAccessCode("59ca633cdd");
			game.setIdTable(1);
			game.setTypeTable(4);
			
			game.setIdUser(5);
			game.setPlace(1);
			
			game.setPlayer1(game.sql.selectUser(1)); //ustawianie class User Player1 <- SQL zwraca wszystkie informacje o użytkowiku 
			game.setPlayer2(game.sql.selectUser(2));
			game.setPlayer3(game.sql.selectUser(3));
			game.setPlayer4(game.sql.selectUser(4));

			game.player1.updateStacks(game.sql.getStackCards(1, 'r'), game.sql.getStackCards(1, 's'), game.sql.getStackCards(1, 'z'));
			game.player2.updateStacks(game.sql.getStackCards(2, 'r'), game.sql.getStackCards(2, 's'), game.sql.getStackCards(2, 'z'));
			game.player3.updateStacks(game.sql.getStackCards(3, 'r'), game.sql.getStackCards(3, 's'), game.sql.getStackCards(3, 'z'));
			game.player4.updateStacks(game.sql.getStackCards(4, 'r'), game.sql.getStackCards(4, 's'), game.sql.getStackCards(4, 'z'));
			
			game.displayGameWindow();
			
			game.GameW.displayCard();
			game.GameW.enableAuctionMore120(false);


			game.player1.display(); // wyświetla dane o użytkowniku  -> funkcja do testów

			for(int i=0; i<game.player1.getScHand().getCount(); i++)
			{
				game.cards[game.player1.getScHand().getCard(i)].display(); // wyświutla karty -> funkcja do testów
			}
			
			game.player2.display();

			for(int i=0; i<game.player2.getScHand().getCount(); i++)
			{
				game.cards[game.player2.getScHand().getCard(i)].display(); // wyświutla karty -> funkcja do testów
			}
			game.player3.display();

			for(int i=0; i<game.player3.getScHand().getCount(); i++)
			{
				game.cards[game.player3.getScHand().getCard(i)].display(); // wyświutla karty -> funkcja do testów
			}
			game.player4.display(); // jeżeli typ_stołu jest 3 -> to jest to 'gracz' MUSEK, inaczej normalny użytkownik 

			for(int i=0; i<game.player4.getScHand().getCount(); i++)
			{
				game.cards[game.player4.getScHand().getCard(i)].display(); // wyświutla karty -> funkcja do testów
			}
			
			/*

			
			
			
			
			System.out.println("ID Gracza na miejscu 1: " + sql.checkUser(1)); // funkcja sql sprawdza czy na danym miejscu jest już jakiś gracz... 0 -> nie... inne to ID_user
			
			*/
		} 
		catch (TysiacException e) 
		{
			System.err.println(e.getErrorMessage());
			e.printStackTrace();
		}
		finally
		{
			//game.close();
			
			//System.exit(0);
		}
		
    }


}
