import javax.swing.JOptionPane;

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
			
			game.SignIn();
			game.SitToTable();
			
			game.displayGameWindow();

			if(game.getPlace() == 1) game.sql.setStatus("Czekanie na graczy");
			game.setStatus();
			game.GameW.setStatus(game.getStatus());
			
			while(game.sql.checkUser(game.getTypeTable()) == 0);			
			
			game.setPlayerInfo();
			
			game.setMovement();
			game.setMust();
			game.setTrio();
			game.setStarted();
			
			
			if(game.getPlace() == 1) game.sql.setStatus("Rozdawanie Kart");
			game.setStatus();
			game.GameW.setStatus(game.getStatus());
			
			if(game.getPlace() == 1) game.giveCards(); //rozdaje karty
			

			
			if(game.getPlace() == 1) game.sql.setStatus("TEST"); //ala ma kota 
			game.setStatus();
			game.GameW.setStatus(game.getStatus());
			game.updateStacks(); //przypisuje karty z bazy do graczy


			game.GameW.displayCard(true);
			game.GameW.displayMovementArrow();
			
			
			game.GameW.enableAuctionMore120(false);
			
		} 
		catch (TysiacException e) 
		{
			JOptionPane.showMessageDialog(null,e.getErrorMessage());
			e.printStackTrace();
		}
		
    }


}
