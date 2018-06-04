import javax.swing.JOptionPane;

public class BOTGame extends GameData
{	
	//Wszystkie zmienne i Funkcje są w pliku GameData.java
	
	public BOTGame() throws TysiacException
	{
		super();
	}
	
	public static void main(String[] args)
	{		
		BOTGame BOT = null;
		try 
		{
			BOT = new BOTGame();
			
			BOT.BOTSitToTable();
			while(BOT.sql.getStatus() == "Tworzenie stołu");
			
			if(BOT.getBOTShowGameWindow())BOT.displayGameWindow();
			
			BOT.setStatus();
			BOT.GameW.setStatus(BOT.getStatus());
			while(BOT.sql.getStatus() == "Czekanie na graczy");
			
			
			BOT.setPlayerInfo();
			
			BOT.setMovement();
			BOT.setMust();
			BOT.setTrio();
			BOT.setStarted();
			
			
			BOT.setStatus();
			BOT.GameW.setStatus(BOT.getStatus());
			while(BOT.sql.getStatus() == "Rozdawanie Kart");

			BOT.updateStacks(); //przypisuje karty z bazy do graczy


			
			BOT.setStatus();
			BOT.GameW.setStatus(BOT.getStatus());
			
			
			BOT.GameW.displayCard(true);
			BOT.GameW.displayMovementArrow();
			
			
			BOT.GameW.enableAuctionMore120(false);
			while(BOT.sql.getStatus() == "TEST");
			
		} 
		catch (TysiacException e) 
		{
			JOptionPane.showMessageDialog(null,e.getErrorMessage());
			e.printStackTrace();
		}
		
    }


}
