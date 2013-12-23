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
		token = node.getId();
	}
	
	public String getToken()
	{
		return token;
	}

}
