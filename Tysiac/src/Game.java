import java.awt.Color;
import javax.swing.JFrame;

public class Game extends GameData
{	
	//Wszystkie zmienne i Funkcje są w pliku GameData.java
	
	public static void main(String[] args)
	{
		
		try 
		{
			
			GameWindow gw = new GameWindow();
			Color stol = new Color(0,102,0);
			
			gw.setVisible(true);
			gw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			gw.getContentPane().setBackground(stol);
			gw.setTitle("Tysiąc");
			gw.setResizable(false);
			
			sql = new SQL(); //Łączy się z bazą danych... jeżeli będzie zmiana bazdy danych to użyć konstruktora parametrowego 
			
			cards = sql.getCards(); // Pobiera informacje o kartach 

			sql.createTable(3, 5, 1, 2, 4);
			
			GameData.setAccessCode(sql.getAccessCode());
			
			sql.BOTjoinTable(GameData.getAccessCode()); //dodawanie BOTa do stołu o danym kodzie dostępu... 
			sql.BOTjoinTable(GameData.getAccessCode());

			setPlayer1(sql.selectUser(1)); //ustawianie class User Player1 <- SQL zwraca wszystkie informacje o użytkowiku 
			setPlayer2(sql.selectUser(2));
			setPlayer3(sql.selectUser(3));
			setPlayer4(sql.selectUser(4));
			
			int [] cp1 = {10, 7, 9, 8, 18, 17, 15};
			int [] cp2 = {11, 21, 4, 12, 0, 16, 23};
			int [] cp3 = {20, 5, 2, 13, 19, 3, 22};
			int [] cp4 = {14, 6, 1};
			
			sql.setNewStacksCards(cp1, cp2, cp3, cp4);

			player1.updateStacks(sql.getStackCards(1, 'r'), sql.getStackCards(1, 's'), sql.getStackCards(1, 'z'));
			player2.updateStacks(sql.getStackCards(2, 'r'), sql.getStackCards(2, 's'), sql.getStackCards(2, 'z'));
			player3.updateStacks(sql.getStackCards(3, 'r'), sql.getStackCards(3, 's'), sql.getStackCards(3, 'z'));
			player4.updateStacks(sql.getStackCards(4, 'r'), sql.getStackCards(4, 's'), sql.getStackCards(4, 'z'));
			
			
			
			player1.display(); // wyświetla dane o użytkowniku  -> funkcja do testów

			for(int i=0; i<player1.getScHand().getCount(); i++)
			{
				cards[player1.getScHand().getCard(i)].display(); // wyświutla karty -> funkcja do testów
			}
			
			player2.display();

			for(int i=0; i<player2.getScHand().getCount(); i++)
			{
				cards[player2.getScHand().getCard(i)].display(); // wyświutla karty -> funkcja do testów
			}
			player3.display();

			for(int i=0; i<player3.getScHand().getCount(); i++)
			{
				cards[player3.getScHand().getCard(i)].display(); // wyświutla karty -> funkcja do testów
			}
			player4.display(); // jeżeli typ_stołu jest 3 -> to jest to 'gracz' MUSEK, inaczej normalny użytkownik 

			for(int i=0; i<player4.getScHand().getCount(); i++)
			{
				cards[player4.getScHand().getCard(i)].display(); // wyświutla karty -> funkcja do testów
			}
			
			System.out.println("ID Gracza na miejscu 1: " + sql.checkUser(1)); // funkcja sql sprawdza czy na danym miejscu jest już jakiś gracz... 0 -> nie... inne to ID_user
			
			sql.close(); // kończenie połączenia z bazą danych 
			
		} 
		catch (TysiacException e) 
		{
			System.err.println(e.getErrorMessage());
			e.printStackTrace();
		}
		
    }


}
