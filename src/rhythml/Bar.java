package rhythml;

import java.util.ArrayList;
import java.util.Set;

public class Bar
{
	private Beats beats;

	public Bar(Beats pBeats)
	{
		beats = pBeats;
	}
	
	public Bar(Node node)
	{
		Set<Node> children = node.getChildren();
		for (Node child : children)
		{
			if(child.getId().equals("beats"))
			{
				beats = new Beats(child);
			}
		}
	}
	
	public ArrayList<ArrayList<String>> getBeatList(String id, ArrayList<ArrayList<String>> beatList)
	{
		if(beats != null)
		{
			beats.getBeatList(id, 0, beatList);
		}
		
		return beatList;
	}
	
	public Beats getBeats()
	{
		return beats;
	}

}
