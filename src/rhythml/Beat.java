package rhythml;

public class Beat
{

	/**
	 * @param args
	 */
	private String token;
	
	public Beat(String pToken)
	{
		token = pToken;
	}
	
	public Beat(Node node)
	{
		token = node.getId();
	}
	
	public String getToken()
	{
		return token;
	}

}
