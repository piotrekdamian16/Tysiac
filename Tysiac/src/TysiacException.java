import java.util.HashMap;

@SuppressWarnings("serial")
public class TysiacException extends Exception
{
	private String errorMessage;
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
		
		errorMessage = "Error #" + en + "\tDescript: " + errors.get(en);
	}
	public TysiacException(int en, String functionName)
	{
		super();

		CreateExceptionsList();
		
		errorMessage = "Error #" + en + "\tDescript: " + errors.get(en) + " in: " + functionName;
	}
	
	
	
	public String getErrorMessage() {
		return errorMessage;
	}

	
	
	private void CreateExceptionsList()
	{
		//SQL exceprions 1 - 100
		errors.put(1, "SQL: Database Driver not found");
		errors.put(2, "SQL: No Connection to Game's Database");
		
		//Hashing exception 101 - 110
		errors.put(101, "Hashing Password Error");
		errors.put(102, "Create Access Code Error");
		
		//Parameter exceptions 111 - 150
		errors.put(111, "Incorrect value as parameter, expected 3 or 4");
		errors.put(112, "Incorrect value as parameter, expected 1, 2, 3 or 4");
	}
	
	
	
	
	


	
	
}
