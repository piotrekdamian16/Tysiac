import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SQL 
{
	private static Connection con;
	private static Statement stmt;
	private int idTable;
	private String accessCode; 
	
	public SQL() throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");  
		con=DriverManager.getConnection( "jdbc:mysql://db4free.net:3306/dp1000?autoReconnect=true&useSSL=false","dp1000","baza1000");
		
		stmt=con.createStatement(); 
	}
	
	public SQL(String host, int port, String base, String user, String pass) throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver");  
		
		String port2 = String.valueOf(port); 
		con=DriverManager.getConnection( "jdbc:mysql://"+host+":"+port2+"/"+base+"?autoReconnect=true&useSSL=false",user,pass);
		
		stmt=con.createStatement(); 
	}
	
	public void close() throws SQLException
	{
		con.close();
	}

	/*Zwraca ID Użytkownika albo 0 gdy błąd logowania*/
	public int loginToGame(String login, String password) throws NoSuchAlgorithmException, SQLException
	{
		String hp = this.hashPassword(password);
		int idUser = 0;
		
		ResultSet rs=stmt.executeQuery("select id_user from USERS WHERE login = '"+login+"' && pass = '"+hp+"'");  
		if(rs.next())
		{
			idUser = rs.getInt(1);
		}
		
		return idUser;
	}
	
	/*Tworzy stół do gry, typeTable -> 3 lub 4
	 * 					  idPlayer1 -> id Gracza który zakłada stół 
	 * 					  movement -> 1-4 gracz który ma wykonać obecnie ruch 
	 * 					  must -> 1-4 gracz który ma muska
	 * 					  trio -> 1-4 gracz który ma trójkę... gdy stół na 3 osoby zawsze trójkę mam gracz 4 
	 * 
	 * Tworzenie stołu + kart na stole... uzupełnia zmienne globalne SQL idTable i accessCode*/
	public void createTable(int typeTable, int idPlayer1, int movement, int must, int trio) throws SQLException, NoSuchAlgorithmException
	{
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String code = this.hashPassword(dateFormat.format(date));
		this.setAccessCode(code.substring(0, 10));
		
		
		stmt.executeUpdate("INSERT INTO `Stoly`(`kod_dostepu`, `typ_stolu`, `id_gracz_1`, `id_gracz_2`, `id_gracz_3`, `id_gracz_4`, `pkt_1`, `pkt_2`, `pkt_3`, `pkt_4`, `ruch`, `musek`, `trojka`, `bomba_gracz_1`, `bomba_gracz_2`, `bomba_gracz_3`, `bomba_gracz_4`, `licytacja_ile`, `licytacja_gracz`) "
						 + "VALUES "
						 + "('"+this.getAccessCode()+"', "+typeTable+", "+idPlayer1+",NULL,NULL,NULL,0,0,0,0,"+movement+", "+must+", "+trio+", 0,0,0,0,0,0) ", Statement.RETURN_GENERATED_KEYS);
		
		ResultSet rs = stmt.getGeneratedKeys();
		
		if(rs.next())
		{
			setIdTable(rs.getInt(1));
		}
		
		stmt.executeUpdate("INSERT INTO `Karty_Stol`(`id_stolu`, `id_karty`, `gdzie`, `gracz`) "
						 + "VALUES "
						 + "("+this.getIdTable()+",  0, 's', 0),"
						 + "("+this.getIdTable()+",  1, 's', 0),"
						 + "("+this.getIdTable()+",  2, 's', 0),"
						 + "("+this.getIdTable()+",  3, 's', 0),"
						 + "("+this.getIdTable()+",  4, 's', 0),"
						 + "("+this.getIdTable()+",  5, 's', 0),"
						 + "("+this.getIdTable()+",  6, 's', 0),"
						 + "("+this.getIdTable()+",  7, 's', 0),"
						 + "("+this.getIdTable()+",  8, 's', 0),"
						 + "("+this.getIdTable()+",  9, 's', 0),"
						 + "("+this.getIdTable()+", 10, 's', 0),"
						 + "("+this.getIdTable()+", 11, 's', 0),"
						 + "("+this.getIdTable()+", 12, 's', 0),"
						 + "("+this.getIdTable()+", 13, 's', 0),"
						 + "("+this.getIdTable()+", 14, 's', 0),"
						 + "("+this.getIdTable()+", 15, 's', 0),"
						 + "("+this.getIdTable()+", 16, 's', 0),"
						 + "("+this.getIdTable()+", 17, 's', 0),"
						 + "("+this.getIdTable()+", 18, 's', 0),"
						 + "("+this.getIdTable()+", 19, 's', 0),"
						 + "("+this.getIdTable()+", 20, 's', 0),"
						 + "("+this.getIdTable()+", 21, 's', 0),"
						 + "("+this.getIdTable()+", 22, 's', 0),"
						 + "("+this.getIdTable()+", 23, 's', 0);", Statement.NO_GENERATED_KEYS);
		
		rs=stmt.executeQuery("SELECT kod_dostepu "
				 		   + "FROM Stoly "
				 		   + "WHERE id_stolu = '"+this.getIdTable()+"' "); 

		if(rs.next())
		{
			String ac = rs.getString(1);

			this.setAccessCode(ac);
		}
	}

	
	//Jeżeli 1 to udało się dołączyć do stołu, 0 to nie :P 
	public int joinTable(int ip, String ac) throws SQLException
	{
		int joined = stmt.executeUpdate("UPDATE Stoly "
						 + "SET id_gracz_2 = "+ip +" "
						 + "WHERE kod_dostepu = '"+ac+"' AND id_gracz_2 IS NULL", Statement.RETURN_GENERATED_KEYS);
		
		if(joined==0)
		{
			joined = stmt.executeUpdate("UPDATE Stoly "
					 + "SET id_gracz_3 = "+ip +" "
					 + "WHERE kod_dostepu = '"+ac+"' AND id_gracz_3 IS NULL", Statement.RETURN_GENERATED_KEYS);

			if(joined==0)
			{
				joined = stmt.executeUpdate("UPDATE Stoly "
						 + "SET id_gracz_4 = "+ip +" "
						 + "WHERE kod_dostepu = '"+ac+"' AND id_gracz_4 IS NULL AND typ_stolu = 4", Statement.RETURN_GENERATED_KEYS);
			}
		}
		
		if(joined == 1)
		{

			ResultSet rs=stmt.executeQuery("SELECT id_stolu "
										 + "FROM Stoly "
										 + "WHERE kod_dostepu = '"+ac+"' "); 

			if(rs.next())
			{
				int it = rs.getInt(1);
				
				this.setAccessCode(ac);
				this.setIdTable(it);
			}
			
		}
		
		return joined;
	}
	
	//Jeżeli 1 to udało się dołączyć do stołu, 0 to nie :P 
	public int BOTjoinTable(String ac) throws SQLException
	{
		int joined = stmt.executeUpdate("UPDATE Stoly "
						 + "SET id_gracz_2 = 1 "
						 + "WHERE kod_dostepu = '"+ac+"' AND id_gracz_2 IS NULL", Statement.RETURN_GENERATED_KEYS);
		
		if(joined==0)
		{
			joined = stmt.executeUpdate("UPDATE Stoly "
					 + "SET id_gracz_3 = 2 "
					 + "WHERE kod_dostepu = '"+ac+"' AND id_gracz_3 IS NULL", Statement.RETURN_GENERATED_KEYS);

			if(joined==0)
			{
				joined = stmt.executeUpdate("UPDATE Stoly "
						 + "SET id_gracz_4 = 3 "
						 + "WHERE kod_dostepu = '"+ac+"' AND id_gracz_4 IS NULL AND typ_stolu = 4", Statement.RETURN_GENERATED_KEYS);
			}
		}
		
		if(joined == 1)
		{

			ResultSet rs=stmt.executeQuery("SELECT id_stolu "
										 + "FROM Stoly "
										 + "WHERE kod_dostepu = '"+ac+"' "); 

			if(rs.next())
			{
				int it = rs.getInt(1);
				
				this.setAccessCode(ac);
				this.setIdTable(it);
			}
			
		}
		
		return joined;
	}
	
	//Zwraca dane użytkownika... 
	public User selectUser(int player) throws SQLException
	{
		ResultSet rs=stmt.executeQuery("SELECT id_gracz_"+player+", typ_stolu "
									 + "FROM Stoly "
									 + "where id_stolu =" + idTable); 
		
		int iu = 0;
		int tt = 0;
		String un = " ";
		
		if(rs.next())
		{
			iu = rs.getInt(1);
			tt = rs.getInt(2);
		}
		if(tt==3 && player==4)
		{
			User user = new User(idTable, 0, "MUSEK", player);
			return user;
		}
		if(iu > 0 )
		{
			ResultSet rs2=stmt.executeQuery("SELECT display_name "
					 					  + "FROM USERS "
					 					  + "where id_user =" + iu); 
			
			if(rs2.next())
			{
				un = rs2.getString(1);
			}
		}
		
		User user = new User(idTable, iu, un, player);
		return user;
	}
	
	// Funkcja srawdza czy na danym miejscu jest już gracz... Zwraca 0, jeżeli NULL (puste)...  innaczej id gracza
	public int checkUser(int player) throws SQLException
	{
		ResultSet rs=stmt.executeQuery("SELECT id_gracz_"+player+" "
									 + "FROM Stoly "
									 + "where id_stolu =" + idTable); 
		
		int iu = 0;
		if(rs.next())
		{
			iu = rs.getInt(1);
		}
		
		
		return iu;
	}
	
	
	public Card[] getCards() throws SQLException
	{
		Card [] cards = new Card[25];
		ResultSet rs=stmt.executeQuery("select * from Karty");  
		while(rs.next())
		{
			int id = rs.getInt(1);
			String s = rs.getString(2);
			char c = rs.getString(3).charAt(0);
			int v = rs.getInt(4);
			
			String k1 = rs.getString(5);
			String [] k2 = k1.split("Z");
			String uv = Character.toString((char) (int) Integer.decode(k2[0])) + Character.toString((char) (int)Integer.decode(k2[1]));
			
			cards[id] = new Card(s,c,v,uv);
		}  
		
		return cards;
	}

	public static int [] getStackCard(int it, int p, char w) throws SQLException
	{
		ResultSet rs=stmt.executeQuery("SELECT id_karty FROM Stoly_Karty WHERE id_stolu = "+ it +" && gracz = "+ p +" && gdzie = '"+ w +"' ");  

		rs.last();
		
		int [] sc = new int[rs.getRow()];
		int i=0;
		
		rs.beforeFirst();
		
		while(rs.next())
		{
			sc[i] = rs.getInt(1);
			
			i++;
		} 
		
		return sc;
	}
	
	
	
	private String hashPassword(String password) throws NoSuchAlgorithmException
	{
		MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        
        byte byteData[] = md.digest();
 
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) 
        {
        	sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
     
        return sb.toString();
	}

	public int getIdTable() 
	{
		return idTable;
	}

	public void setIdTable(int idTable) 
	{
		this.idTable = idTable;
	}
	

	public String getAccessCode() 
	{
		return accessCode;
	}

	public void setAccessCode(String AccessCode) 
	{
		this.accessCode = AccessCode;
	}
	
	
	
	//aaa
	
	
}
