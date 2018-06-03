

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
			
			game.setIdUser(4);
			game.setPlace(4);
			
			game.setPlayerInfo();

			game.player1.updateStacks(game.sql.getStackCards(1, 'r'), game.sql.getStackCards(1, 's'), game.sql.getStackCards(1, 'z'));
			game.player2.updateStacks(game.sql.getStackCards(2, 'r'), game.sql.getStackCards(2, 's'), game.sql.getStackCards(2, 'z'));
			game.player3.updateStacks(game.sql.getStackCards(3, 'r'), game.sql.getStackCards(3, 's'), game.sql.getStackCards(3, 'z'));
			game.player4.updateStacks(game.sql.getStackCards(4, 'r'), game.sql.getStackCards(4, 's'), game.sql.getStackCards(4, 'z'));



			game.displayGameWindow();
			
			game.setMovement(game.sql.getMovement());
			game.setMust(game.sql.getMust());
			game.setTrio(game.sql.getTrio());
			game.setStarted(game.sql.getStarted());
			
			game.GameW.displayCard(false);
			game.GameW.enableAuctionMore120(false);
			game.GameW.displayMovementArrow();


			
		} 
		catch (TysiacException e) 
		{
			//System.err.println(e.getErrorMessage());
			e.printStackTrace();
		}
		finally
		{
			//game.close();
			
			//System.exit(0);
		}
		
    }


}
