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
	private int idTable;
	private int place;
	private String accessCode; 
	
	
	// -----------------------------------------------------------------------------
	// ---------------- KONSTRUKTOR bez parametrów ---------------------------------
	// -----------------------------------------------------------------------------
	public SQL() throws TysiacException
	{
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
	public SQL(String host, int port, String base, String user, String pass) throws TysiacException
	{
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
	public void close() throws TysiacException
	{
		try 
		{
			con.close();
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(3, "close()");
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
		
		if(login.length() < 6) return -1;
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
			this.setAccessCode(code.substring(0, 10));
			
			try 
			{
				stmt.executeUpdate("INSERT INTO `Stoly`(`kod_dostepu`, `typ_stolu`, `id_gracz_1`, `id_gracz_2`, `id_gracz_3`, `id_gracz_4`, `ruch`, `musek`, `trojka`, "
							     + "`suma_pkt_1`, `suma_pkt_2`, `suma_pkt_3`, `suma_pkt_4`, `runda_pkt_1`, `runda_pkt_2`, `runda_pkt_3`, `runda_pkt_4`, `bomba_gracz_1`, `bomba_gracz_2`, `bomba_gracz_3`, `bomba_gracz_4`, `licytacja_ile`, `licytacja_gracz`, `kolor`, `status`) "
								 + "VALUES "
								 + "('"+this.getAccessCode()+"', "+typeTable+", "+idPlayer1+",NULL,NULL,NULL,"+movement+", "+must+", "+trio+", "
								 + "0,0,0,0,0,0,0,0,0,0,0,0,0,0,NULL,'Tworzenie stolu') ", Statement.RETURN_GENERATED_KEYS);

				ResultSet rs = stmt.getGeneratedKeys();
				
				if(rs.next())
				{
					setIdTable(rs.getInt(1));
				}
				this.setPlace(1);
				
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
	
	private void createTableCard() throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "createTableCard()");
		if(stmt == null) throw new TysiacException(5, "createTableCard()");
		
		try 
		{
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
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(12, "CreateTableCard()");
		}
	}
	
	private void setAccesCode() throws TysiacException
	{
		if(con == null) throw new TysiacException(4, "setAccesCode()");
		if(stmt == null) throw new TysiacException(5, "setAccesCode()");
		
		ResultSet rs;
		try 
		{
			rs = stmt.executeQuery("SELECT kod_dostepu "
					 		   + "FROM Stoly "
					 		   + "WHERE id_stolu = '"+this.getIdTable()+"' ");
			

			if(rs.next())
			{
				String ac = rs.getString(1);

				this.setAccessCode(ac);
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
					this.setPlace(2);
				}
			}
			else
			{
				this.setPlace(3);
			}
		}
		else
		{
			this.setPlace(2);
		}
		
		if(joined == 1)
		{

			ResultSet rs;
			try 
			{
				rs = stmt.executeQuery("SELECT id_stolu "
											 + "FROM Stoly "
											 + "WHERE kod_dostepu = '"+ac+"' ");
				

				if(rs.next())
				{
					int it = rs.getInt(1);
					
					this.setAccessCode(ac);
					this.setIdTable(it);
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
						this.setPlace(2);
					}
				}
				else
				{
					this.setPlace(3);
				}
			}
			else
			{
				this.setPlace(2);
			}
		}
		else
		{
			this.setPlace(1);
		}
		
		if(joined == 1)
		{
			ResultSet rs;
			try 
			{
				rs=stmt.executeQuery("SELECT id_stolu "
										 + "FROM Stoly "
										 + "WHERE kod_dostepu = '"+ac+"' "); 

				if(rs.next())
				{
					int it = rs.getInt(1);
					
					this.setAccessCode(ac);
					this.setIdTable(it);
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
										 + "where id_stolu =" + idTable);
			
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
		
		User user = new User(idTable, iu, un, player);
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
										 + "where id_stolu =" + idTable);

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
										 + "WHERE id_stolu = "+ this.idTable +" "
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
										  + "WHERE id_stolu = "+ this.idTable +" "
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
							 + "WHERE `id_stolu`="+this.idTable+" AND `id_karty`="+idCard, Statement.NO_GENERATED_KEYS);
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
							 + "WHERE `id_stolu`="+this.idTable+" AND `id_karty`="+idCard, Statement.NO_GENERATED_KEYS);
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
							 + "WHERE `id_stolu`="+this.idTable+" AND `id_karty`="+idCard, Statement.NO_GENERATED_KEYS);
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
					 		 + "WHERE `id_stolu`="+this.idTable+" AND `gdzie`='s'" , Statement.NO_GENERATED_KEYS);
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
			rs = stmt.executeQuery("SELECT ruch "
										 + "FROM Stoly "
										 + "where id_stolu =" + this.idTable);

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
							 + "WHERE `id_stolu`="+this.idTable, Statement.NO_GENERATED_KEYS);
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
										 + "where id_stolu =" + this.idTable);

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
							 + "WHERE `id_stolu`="+this.idTable, Statement.NO_GENERATED_KEYS);
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
										 + "where id_stolu =" + this.idTable);

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
							 + "WHERE `id_stolu`="+this.idTable, Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "setTrio()");
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
										 + "where id_stolu =" + this.idTable);

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
							 + "WHERE `id_stolu`="+this.idTable, Statement.NO_GENERATED_KEYS);
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
							 + "WHERE `id_stolu`="+this.idTable, Statement.NO_GENERATED_KEYS);
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
							 + "WHERE `id_stolu`="+this.idTable, Statement.NO_GENERATED_KEYS);
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
										 + "where id_stolu =" + this.idTable);

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
							 + "WHERE `id_stolu`="+this.idTable, Statement.NO_GENERATED_KEYS);
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
							 + "WHERE `id_stolu`="+this.idTable, Statement.NO_GENERATED_KEYS);
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
							 + "WHERE `id_stolu`="+this.idTable, Statement.NO_GENERATED_KEYS);
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
										 + "where id_stolu =" + this.idTable);

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
		if(value != 0 || value != 1) throw new TysiacException(118, "setBomb()");
		
		
		try 
		{
			stmt.executeUpdate("UPDATE `Stoly` "
							 + "SET `bomba_gracz_"+player+"`= "+ value +"  "
							 + "WHERE `id_stolu`="+this.idTable, Statement.NO_GENERATED_KEYS);
		} 
		catch (SQLException e) 
		{
			throw new TysiacException(20, "setBomb()");
		}
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	public int getIdTable() 
	{
		return idTable;
	}
	public String getAccessCode() 
	{
		return accessCode;
	}
	public int getPlace() {
		return place;
	}

	
	
	public void setIdTable(int idTable) 
	{
		this.idTable = idTable;
	}
	public void setAccessCode(String AccessCode) 
	{
		this.accessCode = AccessCode;
	}
	public void setPlace(int place) {
		this.place = place;
	}
	
	
	
	//aaa
	
	
}
