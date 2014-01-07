package rhythml;

public class InstrumentId
{

	/**
	 * @param args
	 */
	private String token;
	
	public InstrumentId(String pToken)
	{
		token = pToken;
	}
	
	public InstrumentId(Node node)
	{
		for (Node child : node.getChildren())
		{
			token = child.getId();
		}
		
		if(token.equals("B"))
		{
			token = "bd";
		}
		else if(token.equals("SN"))
		{
			token = "sn";
		}
		else if(token.equals("HH"))
		{
			token = "ch";
		}
	}
	
	public String getToken()
	{
		return token;
	}

}
