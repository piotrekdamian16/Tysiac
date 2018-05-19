
public class Card  implements Comparable<Card>
{
	private String symbol; /* 9, 10, J, Q, K, A ... TY L -> Tył karty*/
	private char color; /*  C -> Czerwo,
	 						Z -> żołądź
	 						D -> Dzwonek
	 						W -> Wino
	 						TY L - Tył Karty*/
	private int value; /*do liczenia pkt*/ 
	private String unicodeValue = new String();

	public Card(String s, char c, int v, String uv)
	{
		this.symbol = s;
		this.color = c;
		this.value = v;
		this.unicodeValue = uv;
	}
	
	public String getSymbol()
	{
		return this.symbol;
	}
	
	public char getColor()
	{
		return this.color;
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	public String getUnicodeValue()
	{
		return this.unicodeValue;
	}
	
	@Override
    public int compareTo(Card x) 
    {
         return Integer.compare(value, x.value);
    }
	
	public void display()
	{
		System.out.println(symbol + "\t" + color + "\t" + value + "\t" + unicodeValue);
	}
}
