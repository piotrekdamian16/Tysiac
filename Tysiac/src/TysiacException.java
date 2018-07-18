import java.util.HashMap;

@SuppressWarnings("serial")
public class TysiacException extends Exception
{
	private String errorMessage;
	private int errorNumber;
	private HashMap<Integer, String> errors = new HashMap<Integer, String>(); 
	
	public TysiacException()
	{
		super();
	}

	public TysiacException(String msg)
	{
		super(msg);
	}
	

	public TysiacException(int en)
	{
		super();

		CreateExceptionsList();
		errorNumber = en;
		errorMessage = "Error #" + en + "\tDescript: " + errors.get(en);
	}
	public TysiacException(int en, String functionName)
	{
		super();

		CreateExceptionsList();
		errorNumber = en;
		errorMessage = "Error #" + en + "\tDescript: " + errors.get(en) + " in: " + functionName;
	}
	
	
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public int getErrorNumber() {
		return errorNumber;
	}

	
	
	private void CreateExceptionsList()
	{
		//SQL exceprions 1 - 100
		errors.put(1, "SQL: Database Driver not found");
		errors.put(2, "SQL: No Connection to Game's Database");
		errors.put(3, "SQL: Error of closing connection");

		errors.put(4, "SQL: Connection is not established");
		errors.put(5, "SQL: Statement is not established");
		
		errors.put(10, "SQL: Error Login or Password of User");
		errors.put(11, "SQL: Insert Table 'stol'");
		errors.put(12, "SQL: Insert Table 'karty_stol'");
		errors.put(13, "SQL: Get AccesCode from base is falled");
		errors.put(14, "SQL: Update error ");
		errors.put(15, "SQL: Get idTable from base is falled");
		errors.put(16, "SQL: Get idUser from base is falled");
		errors.put(17, "SQL: Get Cards Info from base is falled");
		errors.put(18, "SQL: Set Cards Info to base is falled");

		errors.put(19, "SQL: Cannot GET value");
		errors.put(20, "SQL: Cannot SET value");
		
		//Hashing exception 101 - 110
		errors.put(101, "Hashing Password Error");
		errors.put(102, "Create Access Code Error");
		
		//Parameter exceptions 111 - 150
		errors.put(111, "Incorrect value of parameter, expected 3 or 4");
		errors.put(112, "Incorrect value of parameter, expected 1, 2, 3 or 4");
		errors.put(113, "Incorrect value of parameter, idUser must be bigger than 0");
		errors.put(114, "Incorrect length of AccessCode, it must be equal 10");
		errors.put(115, "Incorrect value of parameter, expected from -300 to 300");
		errors.put(116, "Incorrect value of parameter, points must be divisible by 10");
		errors.put(117, "Incorrect value of parameter, expected max 1000");
		errors.put(118, "Incorrect value of parameter, expected 0 or 1");
		errors.put(119, "Incorrect value of parameter, expected 100 - 300");
		errors.put(120, "Incorrect value of parameter, expected `C`, `D`, `Z`, `W` or `\\0` as null color");
		
		//Wyswietlanie
		errors.put(150, "InterruptedException");
	}


	
	
	
	
	


	
	
}
