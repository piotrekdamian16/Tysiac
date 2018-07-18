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
		BOTGame game = null;
		try 
		{
			game = new BOTGame();
			
			game.BOTSitToTable();
			game.displayGameWindow();
			
			
			do
			{
				game.setStatus();
				game.GameW.setStatus(game.getStatus());
				
				if(game.getStatus().equals("Czekanie na graczy"))
				{
					//Czekam na graczy
				}
				else if(game.getStatus().equals("Komplet graczy"))
				{
					//Pobieranie Danych o graczach i o stole
					game.setPlayerInfo();
					
					game.setMovement();
					game.setMust();
					game.setTrio();
					game.setStarted();
				}
				else if(game.getStatus().equals("Rozdawanie Kart"))
				{
					//Czekam na rozdanie kart
				}
				else if(game.getStatus().equals("Licytacja"))
				{
					game.updateStacks(); //przypisuje karty z bazy do graczy

					if(game.getBOTShowGameWindow() == true) 
					{
						game.GameW.displayCard(false);
					}
					game.GameW.displayMovementArrow();
					
					do
					{
						game.GameW.displayMovementArrow();
						game.setAuctionPlayer();
						game.setAuction();
						game.setStarted();
						game.setMovement();
						
						if(game.getAuctionSurrender() == true)
						{
							game.nextPlayer();
						}
						
						if(game.getMovement() == game.getPlace())
						{
							game.GameW.displayAuction(true);
							game.GameW.enableAuctionOver(game.getAuction());
							game.GameW.enableAuctionMore120(game.checkReports(game.getPlace()));
						}
						else
						{
							game.GameW.displayAuction(false);
						}
					}
					while(game.getStatus().equals("Pokaz 3 kart"));
				}
				else if(game.getStatus().equals("Pokaz 3 kart"))
				{
					//Czekam na rozdanie kart
				}
				else if(game.getStatus().equals("Gra"))
				{
					//Czekam na rozdanie kart
				}
				else if(game.getStatus().equals("Koniec"))
				{
					game.finished = true;
				}
					
				
				
				while(game.sql.getStatus().equals(game.getStatus()));
			}
			while(game.finished == false);
			


			
			
			
		} 
		catch (TysiacException e) 
		{
			JOptionPane.showMessageDialog(null,e.getErrorMessage());
			e.printStackTrace();
		}
		
    }


}
