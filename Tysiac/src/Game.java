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

			if(game.getPlace() == 1) 
			{
				do
				{
					game.setStatus();
					game.GameW.setStatus(game.getStatus());
					
					if(game.getStatus().equals("Czekanie na graczy"))
					{
						game.GameW.displayAccessCode(game.getAccessCode());
						
						while(game.sql.checkUser(game.getTypeTable()) == 0);	
						game.GameW.removeAccessCode();
						Thread.sleep(1000);
						game.sql.setStatus("Komplet graczy");
					}
					else if(game.getStatus().equals("Komplet graczy"))
					{
						//Pobieranie Danych o graczach i o stole
						game.setPlayerInfo();
						
						game.setMovement();
						game.setMust();
						game.setTrio();
						game.setStarted();
						
						game.sql.setStatus("Rozdawanie Kart");
					}
					else if(game.getStatus().equals("Rozdawanie Kart"))
					{

						game.newRound();
						game.giveCards(); //rozdaje karty
						
						game.sql.setStatus("Licytacja"); 
						
						
					}
					else if((game.getStatus().substring(0, 9)).equals("Licytacja"))
					{
						
						game.updateStacks(); //przypisuje karty z bazy do graczy
						
						
						game.GameW.displayCard(false);
						
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
						while(game.getAuctionPlayer() != game.getStarted());

						game.sql.setStatus("Pokaz 3 kart"); 
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
			else
			{

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

						game.GameW.displayCard(false);
						
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
								sgame.GameW.enableAuctionOver(game.getAuction());
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
			
		} 
		catch (TysiacException e) 
		{
			JOptionPane.showMessageDialog(null,e.getErrorMessage());
			e.printStackTrace();
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
/*
	
*/
}
