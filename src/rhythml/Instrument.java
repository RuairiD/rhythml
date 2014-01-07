package rhythml;

import java.util.ArrayList;
import java.util.Set;

public class Instrument
{
	private InstrumentId id;
	private Bar bar;

	public Instrument(InstrumentId pId, Bar pBar)
	{
		id = pId;
		bar = pBar;
	}
	
	public Instrument(Node node)
	{
		Set<Node> children = node.getChildren();
		for (Node child : children)
		{
			if(child.getId().equals(":bar"))
			{
				bar = new Bar(child);
			}
			else if (!child.getId().equals(":"))
			{
				id = new InstrumentId(child);
			}
		}
	}
	
	public ArrayList<ArrayList<String>> getBeatList(ArrayList<ArrayList<String>> beatList)
	{
		bar.getBeatList(id.getToken(), beatList);
		
		return beatList;
	}
	
	public InstrumentId getId()
	{
		return id;
	}
	
	public Bar getBar()
	{
		return bar;
	}

}
