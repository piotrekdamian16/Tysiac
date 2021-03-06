import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class SQL 
{
	private static Connection con;
	private static Statement stmt;
	private GameData GaD;

	public GameData getGameData() 
	{
		return GaD;
	}
	
	// -----------------------------------------------------------------------------
	// ---------------- KONSTRUKTOR bez parametrów ---------------------------------
	// -----------------------------------------------------------------------------
	public SQL(GameData a) throws TysiacException
	{
		GaD = a;
		
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			
			try 
			{
				con=DriverManager.getConnection( "jdbc:mysql://db4free.net:3306/dp1000?autoReconnect=true&useSSL=false","dp1000","baza1000");
				stmt=con.createStatement(); 
			} 
			catch (SQLException e) 
			{
				throw new TysiacException(2, "SQL()");
			}
			
			
		} 
		catch (ClassNotFoundException e) 
		{
			throw new TysiacException(1, "SQL()");
		}  
		
	}

	
	// -----------------------------------------------------------------------------
	// ---------------- KONSTRUKTOR z parametrami ----------------------------------
	// -----------------------------------------------------------------------------
	public SQL(GameData a, String host, int port, String base, String user, String pass) throws TysiacException
	{
		GaD = a;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			
			try 
			{
				String port2 = String.valueOf(port); 
				con=DriverManager.getConnection( "jdbc:mysql://"+host+":"+port2+"/"+base+"?autoReconnect=true&useSSL=false",user,pass);
				stmt=con.createStatement(); 
			} 
			catch (SQLException e) 
			{
				throw new TysiacException(2, "SQL(...)");
			}
			
			
		} 
		catch (ClassNotFoundException e) 
		{
			throw new TysiacException(1, "SQL(...)");
		}  
	}
	
	// -----------------------------------------------------------------------------
	// ---------------- Zamykanie połączenia z bazą --------------------------------
	// -----------------------------------------------------------------------------
	public void close()
	{
		try 
		{
			con.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}

	

	
	/*Zwraca ID Użytkownika albo 0 gdy błąd logowania*/
	public int loginToGame(String login, String password) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "loginToGame()");
		if(stmt == null) throw new TysiacException(5, "loginToGame()");
		
		int idUser = 0;
		String hp;
		try 
		{
			hp = this.hashPassword(password);

			
			ResultSet rs=stmt.executeQuery("select id_user from USERS WHERE login = '"+login+"' && pass = '"+hp+"'");  
			if(rs.next())
			{
				idUser = rs.getInt(1);
			}
		}
		catch (SQLException e) 
		{
			throw new TysiacException(10, "loginToGame()");
		}
		
		return idUser;
	}
	
	/*Zwraca ID Użytkownika albo 
	 * 0 gdy podany login jest błędny
	 * -1 zbyt krótki
	 * -2 niedozwolone symbole w loginie*/
	public int checkLogin(String login) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "checkLogin()");
		if(stmt == null) throw new TysiacException(5, "checkLogin()");
		
		if(login.length() < 5) return -1;
		if(Pattern.matches("^[a-zA-Z0-9-_]+$", login) == false) return -2;
		
		int idUser = 0;
		try 
		{			
			ResultSet rs=stmt.executeQuery("select id_user from USERS WHERE login = '"+login+"'");  
			if(rs.next())
			{
				idUser = rs.getInt(1);
			}
		}
		catch (SQLException e) 
		{
			throw new TysiacException(10, "checkLogin()");
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
	public void createTable(int typeTable, int idPlayer1, int movement, int must, int trio) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "createTable()");
		if(stmt == null) throw new TysiacException(5, "createTable()");
		
		if(typeTable != 3 && typeTable !=4) throw new TysiacException(111, "CreateTable() as typeTable");
		if(idPlayer1 <= 0) throw new TysiacException(113, "CreateTable() as idPlayer");
		if(movement <= 0 || movement >4) throw new TysiacException(112, "CreateTable() as movement");
		if(must <= 0 || must >4) throw new TysiacException(112, "CreateTable() as must");
		if(trio <= 0 || trio >4) throw new TysiacException(112, "CreateTable() as trio");
			
		try 
		{
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String code = this.hashPassword(dateFormat.format(date));
			GaD.setAccessCode(code.substring(0, 10));
			
			try 
			{
				stmt.executeUpdate("INSERT INTO `Stoly`(`kod_dostepu`, `typ_stolu`, `id_gracz_1`, `id_gracz_2`, `id_gracz_3`, `id_gracz_4`, `ruch`, `musek`, `trojka`, "
							     + "`suma_pkt_1`, `suma_pkt_2`, `suma_pkt_3`, `suma_pkt_4`, `runda_pkt_1`, `runda_pkt_2`, `runda_pkt_3`, `runda_pkt_4`, `bomba_gracz_1`, `bomba_gracz_2`, `bomba_gracz_3`, `bomba_gracz_4`, `licytacja_ile`, `licytacja_gracz`, `kolor`, `status`, `zaczal`, `pas_gracz_1`, `pas_gracz_2`, `pas_gracz_3`, `pas_gracz_4`) "
								 + "VALUES "
								 + "('"+GaD.getAccessCode()+"', "+typeTable+", "+idPlayer1+",NULL,NULL,NULL,"+movement+", "+must+", "+trio+", "
								 + "0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,'Czekanie na graczy', 1,0,0,0,0) ", Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				
				if(rs.next())
				{
					GaD.setIdTable(rs.getInt(1));
				}
				GaD.setPlace(1);
				
				this.createTableCard();
				this.setAccesCode();
			} 
			catch (SQLException e) 
			{
				throw new TysiacException(11, "CreateTable()");
			}
			
		} 
		catch (TysiacException e) 
		{
			throw new TysiacException(102, "CreateTable()");
		}
		

		
		
	}
	
	public void createTableCard() throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "createTableCard()");
		if(stmt == null) throw new TysiacException(5, "createTableCard()");
		
		try 
		{
			stmt.executeUpdate("INSERT INTO `Karty_Stol`(`id_stolu`, `id_karty`, `gdzie`, `gracz`) "
							 + "VALUES "
							 + "("+GaD.getIdTable()+",  0, 's', 0),"
							 + "("+GaD.getIdTable()+",  1, 's', 0),"
							 + "("+GaD.getIdTable()+",  2, 's', 0),"
							 + "("+GaD.getIdTable()+",  3, 's', 0),"
							 + "("+GaD.getIdTable()+",  4, 's', 0),"
							 + "("+GaD.getIdTable()+",  5, 's', 0),"
							 + "("+GaD.getIdTable()+",  6, 's', 0),"
							 + "("+GaD.getIdTable()+",  7, 's', 0),"
							 + "("+GaD.getIdTable()+",  8, 's', 0),"
							 + "("+GaD.getIdTable()+",  9, 's', 0),"
							 + "("+GaD.getIdTable()+", 10, 's', 0),"
							 + "("+GaD.getIdTable()+", 11, 's', 0),"
							 + "("+GaD.getIdTable()+", 12, 's', 0),"
							 + "("+GaD.getIdTable()+", 13, 's', 0),"
							 + "("+GaD.getIdTable()+", 14, 's', 0),"
							 + "("+GaD.getIdTable()+", 15, 's', 0),"
							 + "("+GaD.getIdTable()+", 16, 's', 0),"
							 + "("+GaD.getIdTable()+", 17, 's', 0),"
							 + "("+GaD.getIdTable()+", 18, 's', 0),"
							 + "("+GaD.getIdTable()+", 19, 's', 0),"
							 + "("+GaD.getIdTable()+", 20, 's', 0),"
							 + "("+GaD.getIdTable()+", 21, 's', 0),"
							 + "("+GaD.getIdTable()+", 22, 's', 0),"
							 + "("+GaD.getIdTable()+", 23, 's', 0);", Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(12, "CreateTableCard()");
		}
	}
	
	public void setAccesCode() throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setAccesCode()");
		if(stmt == null) throw new TysiacException(5, "setAccesCode()");
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT kod_dostepu "
					 		   + "FROM Stoly "
					 		   + "WHERE id_stolu = '"+GaD.getIdTable()+"' ");
			

			if(rs.next())
			{
				String ac = rs.getString(1);

				GaD.setAccessCode(ac);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(13, "setAccesCode()");
		} 
	}

	
	//Jeżeli 1 to udało się dołączyć do stołu, 0 to nie :P 
	public int joinTable(int ip, String ac) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "joinTable()");
		if(stmt == null) throw new TysiacException(5, "joinTable()");
		
		if(ac.length() != 10) throw new TysiacException(114, "joinTable() as typeTable");
		if(ip <= 0) throw new TysiacException(113, "joinTable() as idPlayer");
		int joined = 0;
		
		try 
		{
			joined = stmt.executeUpdate("UPDATE Stoly "
							 + "SET id_gracz_2 = "+ip +" "
							 + "WHERE kod_dostepu = '"+ac+"' AND id_gracz_2 IS NULL", Statement.RETURN_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(14, "joinTable()");
		}
		
		if(joined==0)
		{
			try 
			{
				joined = stmt.executeUpdate("UPDATE Stoly "
						 + "SET id_gracz_3 = "+ip +" "
						 + "WHERE kod_dostepu = '"+ac+"' AND id_gracz_3 IS NULL", Statement.RETURN_GENERATED_KEYS);
			}  
			catch (SQLException e) 
			{
				throw new TysiacException(14, "joinTable()");
			}

			if(joined==0)
			{
				try 
				{
					joined = stmt.executeUpdate("UPDATE Stoly "
							 + "SET id_gracz_4 = "+ip +" "
							 + "WHERE kod_dostepu = '"+ac+"' AND id_gracz_4 IS NULL AND typ_stolu = 4", Statement.RETURN_GENERATED_KEYS);
				}  
				catch (SQLException e) 
				{
					throw new TysiacException(14, "joinTable()");
				}
				
				if(joined == 1)
				{
					GaD.setPlace(2);
				}
			}
			else
			{
				GaD.setPlace(3);
			}
		}
		else
		{
			GaD.setPlace(2);
		}
		
		if(joined == 1)
		{

			ResultSet rs;
			try 
			{
				rs=stmt.executeQuery("SELECT id_stolu, typ_stolu "
						 + "FROM Stoly "
						 + "WHERE kod_dostepu = '"+ac+"' "); 

				if(rs.next())
				{
					int it = rs.getInt(1);
					int tt = rs.getInt(2);
					
					GaD.setAccessCode(ac);
					GaD.setIdTable(it);
					GaD.setTypeTable(tt);
				}
			} 
			catch (SQLException e) 
			{
				throw new TysiacException(15, "joinTable()");
			}
			
		}
		
		return joined;
	}
	
	//Jeżeli 1 to udało się dołączyć do stołu, 0 to nie :P 
	public int BOTjoinTable(String ac) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "BOTjoinTable()");
		if(stmt == null) throw new TysiacException(5, "BOTjoinTable()");
		
		if(ac.length() != 10) throw new TysiacException(114, "joinTable() as typeTable");
		int joined = 0;
		
		
		try 
		{
			joined = stmt.executeUpdate("UPDATE Stoly "
						 + "SET id_gracz_1 = 1 "
						 + "WHERE kod_dostepu = '"+ac+"' AND id_gracz_1 IS NULL", Statement.RETURN_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(14, "BOTjoinTable()");
		}
		
		if(joined==0)
		{
			try 
			{
				joined = stmt.executeUpdate("UPDATE Stoly "
					 + "SET id_gracz_2 = 2 "
					 + "WHERE kod_dostepu = '"+ac+"' AND id_gracz_2 IS NULL", Statement.RETURN_GENERATED_KEYS);
			} 
			catch (SQLException e) 
			{
				throw new TysiacException(14, "BOTjoinTable()");
			}

			if(joined==0)
			{
				try 
				{
					joined = stmt.executeUpdate("UPDATE Stoly "
						 + "SET id_gracz_3 = 3 "
						 + "WHERE kod_dostepu = '"+ac+"' AND id_gracz_3 IS NULL", Statement.RETURN_GENERATED_KEYS);
				} 
				catch (SQLException e) 
				{
					throw new TysiacException(14, "BOTjoinTable()");
				}

				if(joined==0)
				{
					try 
					{
						joined = stmt.executeUpdate("UPDATE Stoly "
							 + "SET id_gracz_4 = 4 "
							 + "WHERE kod_dostepu = '"+ac+"' AND id_gracz_4 IS NULL AND typ_stolu = 4", Statement.RETURN_GENERATED_KEYS);
					} 
					catch (SQLException e) 
					{
						throw new TysiacException(14, "BOTjoinTable()");
					}

					if(joined == 1)
					{
						GaD.setPlace(4);
					}
				}
				else
				{
					GaD.setPlace(3);
				}
			}
			else
			{
				GaD.setPlace(2);
			}
		}
		else
		{
			GaD.setPlace(1);
		}
		
		if(joined == 1)
		{
			ResultSet rs;
			try 
			{
				rs=stmt.executeQuery("SELECT id_stolu, typ_stolu "
										 + "FROM Stoly "
										 + "WHERE kod_dostepu = '"+ac+"' "); 

				if(rs.next())
				{
					int it = rs.getInt(1);
					int tt = rs.getInt(2);
					
					GaD.setAccessCode(ac);
					GaD.setIdTable(it);
					GaD.setTypeTable(tt);
				}
			} 
			catch (SQLException e) 
			{
				throw new TysiacException(15, "BOTjoinTable()");
			}
			
		}
		
		return joined;
	}
	
	//Zwraca dane użytkownika... 
	public User selectUser(int player) throws TysiacException
	{

		if(con == null) throw new TysiacException(4, "selectUser()");
		if(stmt == null) throw new TysiacException(5, "selectUser()");
		
		if(player <= 0 || player >4) throw new TysiacException(113, "selectUser()");
		
		
		int iu = 0;
		int tt = 0;
		String un = " ";
		
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT id_gracz_"+player+", typ_stolu "
										 + "FROM Stoly "
										 + "where id_stolu =" + GaD.getIdTable());
			
			if(rs.next())
			{
				iu = rs.getInt(1);
				tt = rs.getInt(2);
			}
			
			if(tt==3 && player==4)
			{
				User user = new User(GaD.getIdTable(), 0, "MUSEK", player);
				return user;
			}
			if(iu > 0 )
			{
				ResultSet rs2;
				try 
				{
					rs2 = stmt.executeQuery("SELECT display_name "
							 					  + "FROM USERS "
							 					  + "where id_user =" + iu);
					
					if(rs2.next())
					{
						un = rs2.getString(1);
					}
				} 
				catch (SQLException e) 
				{
					throw new TysiacException(13, "selectUser()");
				} 
			}
			
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(16, "selectUser()");
		} 
		
		User user = new User(GaD.getIdTable(), iu, un, player);
		return user;
	}
	
	// Funkcja srawdza czy na danym miejscu jest już gracz... Zwraca 0, jeżeli NULL (puste)...  innaczej id gracza
	public int checkUser(int player) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "checkUser()");
		if(stmt == null) throw new TysiacException(5, "checkUser()");
		
		if(player <= 0 || player >4) throw new TysiacException(113, "checkUser()");

		
		int iu = 0;
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT id_gracz_"+player+" "
										 + "FROM Stoly "
										 + "where id_stolu =" + GaD.getIdTable());

			if(rs.next())
			{
				iu = rs.getInt(1);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(16, "checkUser()");
		} 
		
		
		return iu;
	}
	
	
	public Card[] getCards() throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getCards()");
		if(stmt == null) throw new TysiacException(5, "getCards()");
		
		Card [] cards = new Card[25];
		ResultSet rs;
		
		try 
		{
			rs = stmt.executeQuery("select * from Karty");
			
			
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
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(17, "getCards()");
		}  
		
		return cards;
	}

	public int getCountStackCards(int player, char what) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getCountStackCards()");
		if(stmt == null) throw new TysiacException(5, "getCountStackCards()");
		
		ResultSet rs;

		int sc = 0;
		
		try 
		{
			rs = stmt.executeQuery("SELECT COUNT(id_karty) FROM Karty_Stol " 
										 + "WHERE id_stolu = "+ GaD.getIdTable() +" "
										 		+ "AND gracz = "+ player +" "
										 		+ "AND gdzie = '"+ what +"' ");
			
			if(rs.next())
			{
				sc = rs.getInt(1);
			} 
		} catch (SQLException e) 
		{
			throw new TysiacException(17, "getCountStackCards()");
		} 
		
		return sc;
	}

	
	
	
	public int [] getStackCards(int player, char what) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getStackCards()");
		if(stmt == null) throw new TysiacException(5, "getStackCards()");
		
		ResultSet rs;
		int [] sc = new int[this.getCountStackCards(player, what)];
		int i=0;
		
		try 
		{
			rs = stmt.executeQuery("SELECT id_karty FROM Karty_Stol "
										  + "WHERE id_stolu = "+ GaD.getIdTable() +" "
										  		+ "&& gracz = "+ player +" "
										  		+ "&& gdzie = '"+ what +"' ");
			
			while(rs.next())
			{
				sc[i] = rs.getInt(1);
				
				i++;
			} 
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(17, "getStackCards()");
		}  
		
		return sc;
	}
	
	
	
	
	
	
	
	public void setStackCard(int idCard, int player, char what) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setStackCard()");
		if(stmt == null) throw new TysiacException(5, "setStackCard()");
		
		try 
		{
			stmt.executeUpdate("UPDATE `Karty_Stol` "
							 + "SET `gdzie`='"+ what +"', "
							     + "`gracz`="+ player +"  "
							 + "WHERE `id_stolu`="+GaD.getIdTable()+" AND `id_karty`="+idCard, Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(18, "setStackCard() change player and what");
		}
	}
	
	public void setStackCard(int idCard, char what) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setStackCard()");
		if(stmt == null) throw new TysiacException(5, "setStackCard()");
		
		try 
		{
			stmt.executeUpdate("UPDATE `Karty_Stol` "
							 + "SET `gdzie`= '"+ what +"'  "
							 + "WHERE `id_stolu`="+GaD.getIdTable()+" AND `id_karty`="+idCard, Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(18, "setStackCard() change what");
		}
	}
	
	public void setStackCard(int idCard, int player) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setStackCard()");
		if(stmt == null) throw new TysiacException(5, "setStackCard()");
		
		try 
		{
			stmt.executeUpdate("UPDATE `Karty_Stol` "
							 + "SET `gracz`="+ player +", "
							 + "WHERE `id_stolu`="+GaD.getIdTable()+" AND `id_karty`="+idCard, Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(18, "setStackCard() change player");
		}
	}
	
	public void moveCardToWinner(int player) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "moveCardToWinner()");
		if(stmt == null) throw new TysiacException(5, "moveCardToWinner()");
		
		try 
		{
			stmt.executeUpdate("UPDATE `Karty_Stol` "
					 		 + "SET `gdzie`='z', "
					 		 + "`gracz`="+ player +"  "
					 		 + "WHERE `id_stolu`="+GaD.getIdTable()+" AND `gdzie`='s'" , Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(18, "moveCardToWinner() change player");
		}
	}
	
	public void setNewStacksCards(int [] cardPlayer1, int [] cardPlayer2, int [] cardPlayer3, int [] cardPlayer4) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setNewStacksCards()");
		if(stmt == null) throw new TysiacException(5, "setNewStacksCards()");
		
		for(int i=0; i<cardPlayer1.length; i++)
		{
			try 
			{
				this.setStackCard(cardPlayer1[i], 1, 'r');
			} 
			catch (TysiacException e) 
			{
				throw new TysiacException(17, "setNewStacksCards() Player: 1, card: "+i);
			}
		}
		
		for(int i=0; i<cardPlayer2.length; i++)
		{
			try 
			{
				this.setStackCard(cardPlayer2[i], 2, 'r');
			} 
			catch (TysiacException e) 
			{
				throw new TysiacException(17, "setNewStacksCards() Player: 2, card: "+i);
			}
		}
		
		for(int i=0; i<cardPlayer3.length; i++)
		{
			try 
			{
				this.setStackCard(cardPlayer3[i], 3, 'r');
			} 
			catch (TysiacException e) 
			{
				throw new TysiacException(17, "setNewStacksCards() Player: 3, card: "+i);
			}
		}
		
		for(int i=0; i<cardPlayer4.length; i++)
		{
			try 
			{
				this.setStackCard(cardPlayer4[i], 4, 'r');
			} 
			catch (TysiacException e) 
			{
				throw new TysiacException(17, "setNewStacksCards() Player: 4, card: "+i);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	private String hashPassword(String password) throws TysiacException
	{
        StringBuffer sb = new StringBuffer();
		MessageDigest md;
		try 
		{
			md = MessageDigest.getInstance("MD5");
			
			md.update(password.getBytes());
	        
	        byte byteData[] = md.digest();
	 
	        for (int i = 0; i < byteData.length; i++) 
	        {
	        	sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
		} catch (NoSuchAlgorithmException e) 
		{
			throw new TysiacException(101, "HashPassword()");
		}
	     
        return sb.toString();
        
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public int getMovement() throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getMovement()");
		if(stmt == null) throw new TysiacException(5, "getMovement()");
		
		int tmp = 0;
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT `ruch` "
										 + "FROM `Stoly` "
										 + "where id_stolu =" + GaD.getIdTable());

			if(rs.next())
			{
				tmp = rs.getInt(1);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(19, "getMovement()");
		} 
		
		return tmp;
	}
	
	public void setMovement(int player) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setMovement()");
		if(stmt == null) throw new TysiacException(5, "setMovement()");
		
		if(player <= 0 || player >4) throw new TysiacException(112, "setMovement()");
		
		
		try 
		{
			stmt.executeUpdate("UPDATE `Stoly` "
							 + "SET `ruch`= "+ player +"  "
							 + "WHERE `id_stolu`="+GaD.getIdTable(), Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "setMovement()");
		}
	}
	
	
	
	
	
	
	
	
	
	public int getMust() throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getMust()");
		if(stmt == null) throw new TysiacException(5, "getMust()");
		
		int tmp = 0;
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT musek "
										 + "FROM Stoly "
										 + "where id_stolu =" + GaD.getIdTable());

			if(rs.next())
			{
				tmp = rs.getInt(1);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(19, "getMust()");
		} 
		
		return tmp;
	}
	
	public void setMust(int player) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setMust()");
		if(stmt == null) throw new TysiacException(5, "setMust()");
		
		if(player <= 0 || player >4) throw new TysiacException(112, "setMust()");
		
		
		try 
		{
			stmt.executeUpdate("UPDATE `Stoly` "
							 + "SET `musek`= "+ player +"  "
							 + "WHERE `id_stolu`="+GaD.getIdTable(), Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "setMust()");
		}
	}
	

	
	public int getTrio() throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getTrio()");
		if(stmt == null) throw new TysiacException(5, "getTrio()");
		
		int tmp = 0;
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT trojka "
										 + "FROM Stoly "
										 + "where id_stolu =" + GaD.getIdTable());

			if(rs.next())
			{
				tmp = rs.getInt(1);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(19, "getTrio()");
		} 
		
		return tmp;
	}
	
	public void setTrio(int player) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setTrio()");
		if(stmt == null) throw new TysiacException(5, "setTrio()");
		
		if(player <= 0 || player >4) throw new TysiacException(112, "setTrio()");
		
		
		try 
		{
			stmt.executeUpdate("UPDATE `Stoly` "
							 + "SET `trojka`= "+ player +"  "
							 + "WHERE `id_stolu`="+GaD.getIdTable(), Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "setTrio()");
		}
	}

	
	
	public int getStarted() throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getStarted()");
		if(stmt == null) throw new TysiacException(5, "getStarted()");
		
		int tmp = 0;
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT zaczal "
										 + "FROM Stoly "
										 + "where id_stolu =" + GaD.getIdTable());

			if(rs.next())
			{
				tmp = rs.getInt(1);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(19, "getStarted()");
		} 
		
		return tmp;
	}
	
	public void setStarted(int player) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setStarted()");
		if(stmt == null) throw new TysiacException(5, "setStarted()");
		
		if(player <= 0 || player >4) throw new TysiacException(112, "setStarted()");
		
		
		try 
		{
			stmt.executeUpdate("UPDATE `Stoly` "
							 + "SET `zaczal`= "+ player +"  "
							 + "WHERE `id_stolu`="+GaD.getIdTable(), Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "setStarted()");
		}
	}
	
	
	
	
	
	public int getSumPoint(int player) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getSumPoint()");
		if(stmt == null) throw new TysiacException(5, "getSumPoint()");
		
		if(player <= 0 || player >4) throw new TysiacException(113, "getSumPoint()");

		
		int iu = 0;
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT suma_pkt_"+player+" "
										 + "FROM Stoly "
										 + "where id_stolu =" + GaD.getIdTable());

			if(rs.next())
			{
				iu = rs.getInt(1);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(16, "getSumPoint()");
		} 
		
		
		return iu;
	}
	
	public void setSumPoint(int player, int points) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setSumPoint()");
		if(stmt == null) throw new TysiacException(5, "setSumPoint()");
		
		if(player <= 0 || player >4) throw new TysiacException(112, "setSumPoint()");
		if(points > 1000) throw new TysiacException(117, "setSumPoint()");
		if((Math.abs(points))%10 != 0 ) throw new TysiacException(116, "setSumPoint()");
		
		
		try 
		{
			stmt.executeUpdate("UPDATE `Stoly` "
							 + "SET `suma_pkt_"+player+"`= "+ points +"  "
							 + "WHERE `id_stolu`="+GaD.getIdTable(), Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "setSumPoint()");
		}
	}	
	
	//dodaje do punkty gracza
	public void addToSumPoint(int player, int points) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "addToSumPoint()");
		if(stmt == null) throw new TysiacException(5, "addToSumPoint()");
		
		if(player <= 0 || player >4) throw new TysiacException(112, "addToSumPoint()");
		if(points < -300 || points > 300) throw new TysiacException(115, "addToSumPoint()");
		if((Math.abs(points))%10 != 0 ) throw new TysiacException(116, "addToSumPoint()");
		
		
		try 
		{
			stmt.executeUpdate("UPDATE `Stoly` "
							 + "SET `suma_pkt_"+player+"`= `suma_pkt_"+player+"` + ("+ points +")  "
							 + "WHERE `id_stolu`="+GaD.getIdTable(), Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "addToSumPoint()");
		}
	}	
	
	public void subToSumPoint(int player, int points) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "subToSumPoint()");
		if(stmt == null) throw new TysiacException(5, "subToSumPoint()");
		
		if(player <= 0 || player >4) throw new TysiacException(112, "subToSumPoint()");
		if(points < -300 || points > 300) throw new TysiacException(115, "subToSumPoint()");
		if((Math.abs(points))%10 != 0 ) throw new TysiacException(116, "subToSumPoint()");
		
		points *= -1;
		
		try 
		{
			stmt.executeUpdate("UPDATE `Stoly` "
							 + "SET `suma_pkt_"+player+"`= `suma_pkt_"+player+"` - ("+ points +")  "
							 + "WHERE `id_stolu`="+GaD.getIdTable(), Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "subToSumPoint()");
		}
	}	
	
	

	
	public int getRoundPoint(int player) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getRoundPoint()");
		if(stmt == null) throw new TysiacException(5, "getRoundPoint()");
		
		if(player <= 0 || player >4) throw new TysiacException(113, "getRoundPoint()");

		
		int iu = 0;
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT runda_pkt_"+player+" "
										 + "FROM Stoly "
										 + "where id_stolu =" + GaD.getIdTable());

			if(rs.next())
			{
				iu = rs.getInt(1);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(16, "getRoundPoint()");
		} 
		
		
		return iu;
	}
	
	public void setRoundPoint(int player, int points) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setRoundPoint()");
		if(stmt == null) throw new TysiacException(5, "setRoundPoint()");
		
		if(player <= 0 || player >4) throw new TysiacException(112, "setRoundPoint()");
		if(points > 1000) throw new TysiacException(117, "setRoundPoint()");
		if((Math.abs(points))%10 != 0 ) throw new TysiacException(116, "setRoundPoint()");
		
		
		try 
		{
			stmt.executeUpdate("UPDATE `Stoly` "
							 + "SET `runda_pkt_"+player+"`= "+ points +"  "
							 + "WHERE `id_stolu`="+GaD.getIdTable(), Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "setRoundPoint()");
		}
	}	
	
	//dodaje/odejmuje do punkty gracza
	public void addToRoundPoint(int player, int points) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "addToRoundPoint()");
		if(stmt == null) throw new TysiacException(5, "addToRoundPoint()");
		
		if(player <= 0 || player >4) throw new TysiacException(112, "addToRoundPoint()");
		if(points < -300 || points > 300) throw new TysiacException(115, "addToRoundPoint()");
		if((Math.abs(points))%10 != 0 ) throw new TysiacException(116, "addToRoundPoint()");
		
		
		try 
		{
			stmt.executeUpdate("UPDATE `Stoly` "
							 + "SET `runda_pkt_"+player+"`= `runda_pkt_"+player+"` + ("+ points +")  "
							 + "WHERE `id_stolu`="+GaD.getIdTable(), Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "addToRoundPoint()");
		}
	}
	

	public void subToRoundPoint(int player, int points) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "subToRoundPoint()");
		if(stmt == null) throw new TysiacException(5, "subToRoundPoint()");
		
		if(player <= 0 || player >4) throw new TysiacException(112, "subToRoundPoint()");
		if(points < -300 || points > 300) throw new TysiacException(115, "subToRoundPoint()");
		if((Math.abs(points))%10 != 0 ) throw new TysiacException(116, "subToRoundPoint()");
		
		points *= -1;
		
		try 
		{
			stmt.executeUpdate("UPDATE `Stoly` "
							 + "SET `runda_pkt_"+player+"`= `runda_pkt_"+player+"` - ("+ points +")  "
							 + "WHERE `id_stolu`="+GaD.getIdTable(), Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "subToRoundPoint()");
		}
	}
	
	
	

	public int getBomb(int player) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getBomb()");
		if(stmt == null) throw new TysiacException(5, "getBomb()");
		
		if(player <= 0 || player >4) throw new TysiacException(113, "getBomb()");

		
		int iu = 0;
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT  bomba_gracz_"+player+" "
										 + "FROM Stoly "
										 + "where id_stolu =" + GaD.getIdTable());

			if(rs.next())
			{
				iu = rs.getInt(1);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(16, "getBomb()");
		} 
		
		
		return iu;
	}
	
	public void setBomb(int player, int value) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setBomb()");
		if(stmt == null) throw new TysiacException(5, "setBomb()");
		
		if(player <= 0 || player >4) throw new TysiacException(112, "setBomb()");
		if(value != 0 && value != 1) throw new TysiacException(118, "setBomb()");
		
		
		try 
		{
			stmt.executeUpdate("UPDATE `Stoly` "
							 + "SET `bomba_gracz_"+player+"`= "+ value +"  "
							 + "WHERE `id_stolu`="+GaD.getIdTable(), Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "setBomb()");
		}
	}	
	public int getSurrender(int player) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getSurrender()");
		if(stmt == null) throw new TysiacException(5, "getSurrender()");
		
		if(player <= 0 || player >4) throw new TysiacException(113, "getSurrender()");

		
		int iu = 0;
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT  pas_gracz_"+player+" "
										 + "FROM Stoly "
										 + "where id_stolu =" + GaD.getIdTable());

			if(rs.next())
			{
				iu = rs.getInt(1);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(16, "getSurrender()");
		} 
		
		
		return iu;
	}
	
	public void setSurrender(int player, int value) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setSurrender()");
		if(stmt == null) throw new TysiacException(5, "setSurrender()");
		
		if(player <= 0 || player >4) throw new TysiacException(112, "setSurrender()");
		if(value != 0 && value != 1) throw new TysiacException(118, "setSurrender()");
		
		
		try 
		{
			stmt.executeUpdate("UPDATE `Stoly` "
							 + "SET `pas_gracz_"+player+"`= "+ value +"  "
							 + "WHERE `id_stolu`="+GaD.getIdTable(), Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "setSurrender()");
		}
	}	
	

	

	public int getAuctionValue() throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getAuctionValue()");
		if(stmt == null) throw new TysiacException(5, "getAuctionValue()");
		
		int auction = 0;
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT  licytacja_ile  "
										 + "FROM Stoly "
										 + "where id_stolu =" + GaD.getIdTable());

			if(rs.next())
			{
				auction = rs.getInt(1);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(16, "getAuctionValue()");
		} 
		
		
		return auction;
	}
	
	public void setAuctionValue(int value) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setAuctionValue()");
		if(stmt == null) throw new TysiacException(5, "setAuctionValue()");
		
		if(value < 100 || value > 300) throw new TysiacException(119, "setAuctionValue()");
		
		
		try 
		{
			stmt.executeUpdate("UPDATE `Stoly` "
							 + "SET `licytacja_ile`= "+ value +"  "
							 + "WHERE `id_stolu`="+GaD.getIdTable(), Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "setAuctionValue()");
		}
	}	
	

	

	public int getAuctionPlayer() throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getAuctionPlayer()");
		if(stmt == null) throw new TysiacException(5, "getAuctionPlayer()");
		
		int auction = 0;
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT  licytacja_gracz  "
										 + "FROM Stoly "
										 + "where id_stolu =" + GaD.getIdTable());

			if(rs.next())
			{
				auction = rs.getInt(1);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(16, "getAuctionPlayer()");
		} 
		
		
		return auction;
	}
	
	public void setAuctionPlayer(int player) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setAuctionPlayer()");
		if(stmt == null) throw new TysiacException(5, "setAuctionPlayer()");

		if(player <= 0 || player >4) throw new TysiacException(112, "setAuctionPlayer()");
		
		
		try 
		{
			stmt.executeUpdate("UPDATE `Stoly` "
							 + "SET `licytacja_gracz`= "+ player +"  "
							 + "WHERE `id_stolu`="+GaD.getIdTable(), Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "setAuctionPlayer()");
		}
	}	
	
	
	

	

	public char getColor() throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getColor()");
		if(stmt == null) throw new TysiacException(5, "getColor()");
		
		char color = 0;
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT  kolor  "
										 + "FROM Stoly "
										 + "where id_stolu =" + GaD.getIdTable());

			if(rs.next())
			{
				color = rs.getString(1).charAt(0);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(16, "getColor()");
		} 
		
		
		return color;
	}
	
	public void setColor(char color) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setColor()");
		if(stmt == null) throw new TysiacException(5, "setColor()");

		if(color == 'C' || color == 'D' || color == 'Z' || color == 'W' || color == '\0' ) throw new TysiacException(120, "setColor()");
		
		
		try 
		{
			if(color == '\0')
			{
				stmt.executeUpdate("UPDATE `Stoly` "
						 + "SET `kolor`= NULL  "
						 + "WHERE `id_stolu`="+GaD.getIdTable(), Statement.NO_GENERATED_KEYS);
			}
			else
			{
				stmt.executeUpdate("UPDATE `Stoly` "
						 + "SET `kolor`= '"+ color +"'  "
						 + "WHERE `id_stolu`="+GaD.getIdTable(), Statement.NO_GENERATED_KEYS);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "setColor()");
		}
	}	
	
	
	


	public String getStatus() throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getStatus()");
		if(stmt == null) throw new TysiacException(5, "getStatus()");
		
		String status = "";
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT  status  "
										 + "FROM Stoly "
										 + "where id_stolu =" + GaD.getIdTable());

			if(rs.next())
			{
				status = rs.getString(1);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(16, "getStatus()");
		} 
		
		
		return status;
	}
	
	public void setStatus(String status) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setStatus()");
		if(stmt == null) throw new TysiacException(5, "setStatus()");
		
		
		try 
		{
			stmt.executeUpdate("UPDATE `Stoly` "
							 + "SET `status`= '"+ status +"'  "
							 + "WHERE `id_stolu`="+GaD.getIdTable(), Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "setStatus()");
		}
	}
	
	


	public int getSumCardValue(int player) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getSumCardValue()");
		if(stmt == null) throw new TysiacException(5, "getSumCardValue()");
		
		if(player <= 0 || player >4) throw new TysiacException(113, "getSumCardValue()");
		
		int sum = 0;
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT suma "
								 + "FROM ( "
								 	+ "SELECT ks.gracz, sum(k.wartosc) as suma "
								 	+ "FROM `Karty_Stol` as ks "
								 	+ "JOIN `Karty` as k on k.id_karty = ks.id_karty "
								 	+ "WHERE ks.id_stolu = "+ GaD.getIdTable() +" AND ks.gdzie = 'r' "
								 	+ "GROUP BY ks.gracz ) as t1 "
								 + "WHERE gracz = " + player);

			if(rs.next())
			{
				sum = rs.getInt(1);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(16, "getSumCardValue()");
		} 
		
		
		return sum;
	}
	

	
	public int countCardInColor(int player, char color) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "countCardInColor()");
		if(stmt == null) throw new TysiacException(5, "countCardInColor()");
		
		if(player <= 0 || player >4) throw new TysiacException(113, "countCardInColor()");
		if(color == 'C' || color == 'D' || color == 'Z' || color == 'W' || color == '\0' ) throw new TysiacException(120, "setColor()");
		
		int cards = 0;
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT ile "
								 + "FROM ( "
								 	+ "SELECT ks.gracz, count(k.wartosc) as ile "
								 	+ "FROM `Karty_Stol` as ks "
								 	+ "JOIN `Karty` as k on k.id_karty = ks.id_karty "
								 	+ "WHERE ks.id_stolu="+ GaD.getIdTable() +" AND ks.gdzie = 'r' AND k.kolor = '"+color+"' "
								 	+ "GROUP BY ks.gracz) as t1 "
								 + "WHERE gracz = " + player);

			if(rs.next())
			{
				cards = rs.getInt(1);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(16, "countCardInColor()");
		} 
		
		return cards;
	}
	
	public int [] getCardInColor(int player, char color) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getCardInColor()");
		if(stmt == null) throw new TysiacException(5, "getCardInColor()");
		
		if(player <= 0 || player >4) throw new TysiacException(113, "getCardInColor()");
		if(color == 'C' || color == 'D' || color == 'Z' || color == 'W' || color == '\0' ) throw new TysiacException(120, "setColor()");
		
		int [] cards = null;
		
		if(this.countCardInColor(player, color) > 0)
		{
			cards = new int[this.countCardInColor(player, color)];
			
			ResultSet rs;
			try 
			{
				rs = stmt.executeQuery("SELECT id_karty "
									 + "FROM ( "
									 	+ "SELECT ks.gracz, ks.id_karty "
									 	+ "FROM `Karty_Stol` as ks "
									 	+ "JOIN `Karty` as k on k.id_karty = ks.id_karty "
									 	+ "WHERE ks.id_stolu="+ GaD.getIdTable() +" AND ks.gdzie = 'r' AND k.kolor = '"+color+"' "
									 	+ "GROUP BY ks.gracz) as t1 "
									 + "WHERE gracz = " + player);
				int i=0;
				while(rs.next())
				{
					cards[i] = rs.getInt(1);
					i++;
				}
			} 
			catch (SQLException e) 
			{
				throw new TysiacException(16, "getCardInColor()");
			} 
		}
		else
		{
			char color2 = this.getColor(); //kolor meldunku
			
			if(this.countCardInColor(player, color2) > 0)
			{

				cards = new int[this.countCardInColor(player, color2)];
				ResultSet rs;
				try 
				{
					rs = stmt.executeQuery("SELECT id_karty "
										 + "FROM ( "
										 	+ "SELECT ks.gracz, ks.id_karty "
										 	+ "FROM `Karty_Stol` as ks "
										 	+ "JOIN `Karty` as k on k.id_karty = ks.id_karty "
										 	+ "WHERE ks.id_stolu="+ GaD.getIdTable() +" AND ks.gdzie = 'r' AND k.kolor = '"+color2+"' "
										 	+ "GROUP BY ks.gracz) as t1 "
										 + "WHERE gracz = " + player);
					int i=0;
					while(rs.next())
					{
						cards[i] = rs.getInt(1);
						i++;
					}
				} 
				catch (SQLException e) 
				{
					throw new TysiacException(16, "getCardInColor()");
				} 
			}
		}
		
		return cards;
	}
	
	
	

	
	public int countHihgerCardInColor(int player, char color, int value) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "countHihgerCardInColor()");
		if(stmt == null) throw new TysiacException(5, "countHihgerCardInColor()");
		
		if(player <= 0 || player >4) throw new TysiacException(113, "countCardInColor()");
		if(color == 'C' || color == 'D' || color == 'Z' || color == 'W' || color == '\0' ) throw new TysiacException(120, "setColor()");
		
		int cards = 0;
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT ile "
								 + "FROM ( "
								 	+ "SELECT ks.gracz, count(k.wartosc) as ile "
								 	+ "FROM `Karty_Stol` as ks "
								 	+ "JOIN `Karty` as k on k.id_karty = ks.id_karty "
								 	+ "WHERE ks.id_stolu="+ GaD.getIdTable() +" AND ks.gdzie = 'r' AND k.kolor = '"+color+"'  AND k.wartosc > "+ value + ""
								 	+ "GROUP BY ks.gracz) as t1 "
								 + "WHERE gracz = " + player);

			if(rs.next())
			{
				cards = rs.getInt(1);
			}
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(16, "countHihgerCardInColor()");
		} 
		
		return cards;
	}
	
	public int [] getHihgerCardInColor(int player, char color, int value) throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "getHihgerCardInColor()");
		if(stmt == null) throw new TysiacException(5, "getHihgerCardInColor()");
		
		if(player <= 0 || player >4) throw new TysiacException(113, "getHihgerCardInColor()");
		if(color == 'C' || color == 'D' || color == 'Z' || color == 'W' || color == '\0' ) throw new TysiacException(120, "setColor()");
		
		int [] cards = null;
		
		if(this.countCardInColor(player, color) > 0)
		{
			cards = new int[this.countHihgerCardInColor(player, color, value)];
			
			ResultSet rs;
			try 
			{
				rs = stmt.executeQuery("SELECT id_karty "
									 + "FROM ( "
									 	+ "SELECT ks.gracz, ks.id_karty "
									 	+ "FROM `Karty_Stol` as ks "
									 	+ "JOIN `Karty` as k on k.id_karty = ks.id_karty "
									 	+ "WHERE ks.id_stolu="+ GaD.getIdTable() +" AND ks.gdzie = 'r' AND k.kolor = '"+color+"' AND k.wartosc > "+ value + ""
									 	+ "GROUP BY ks.gracz) as t1 "
									 + "WHERE gracz = " + player);
				int i=0;
				while(rs.next())
				{
					cards[i] = rs.getInt(1);
					i++;
				}
			} 
			catch (SQLException e) 
			{
				throw new TysiacException(16, "getHihgerCardInColor()");
			} 
		}
		else
		{
			char color2 = this.getColor(); //kolor meldunku
			
			if(this.countHihgerCardInColor(player, color2, value) > 0)
			{

				cards = new int[this.countCardInColor(player, color2)];
				ResultSet rs;
				try 
				{
					rs = stmt.executeQuery("SELECT id_karty "
										 + "FROM ( "
										 	+ "SELECT ks.gracz, ks.id_karty "
										 	+ "FROM `Karty_Stol` as ks "
										 	+ "JOIN `Karty` as k on k.id_karty = ks.id_karty "
										 	+ "WHERE ks.id_stolu="+ GaD.getIdTable() +" AND ks.gdzie = 'r' AND k.kolor = '"+color2+"' "
										 	+ "GROUP BY ks.gracz) as t1 "
										 + "WHERE gracz = " + player);
					int i=0;
					while(rs.next())
					{
						cards[i] = rs.getInt(1);
						i++;
					}
				} 
				catch (SQLException e) 
				{
					throw new TysiacException(16, "getHihgerCardInColor()");
				} 
			}
		}
		
		return cards;
	}
	
	
	
	//aaa
	
	
}
