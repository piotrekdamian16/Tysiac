import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class Game extends GameData
{	
	//Wszystkie zmienne i Funkcje są w pliku GameData.java
	
	public static void main(String[] args)
	{
		
		try {
			sql = new SQL(); //Łączy się z bazą danych... jeżeli będzie zmiana bazdy danych to użyć konstruktora parametrowego 
			
			cards = sql.getCards(); // Pobiera informacje o kartach 
			
			for(int i=0; i<25; i++)
			{
				cards[i].display(); // wyświutla karty -> funkcja do testów
			}

			sql.createTable(3, 4, 0, 0, 0);
			
			GameData.setAccessCode(sql.getAccessCode());
			
			sql.BOTjoinTable(GameData.getAccessCode()); //dodawanie BOTa do stołu o danym kodzie dostępu... 
			sql.BOTjoinTable(GameData.getAccessCode());

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
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }


}
