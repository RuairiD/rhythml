package rhythml;

import java.util.ArrayList;
import java.util.Set;

public class Beats
{
	private Beat beat;
	private Beats rest;

	public Beats(Beat pBeat, Beats pRest)
	{
		beat = pBeat;
		rest = pRest;
	}
	
	public Beats(Node node)
	{
		Set<Node> children = node.getChildren();
		for (Node child : children)
		{
			if(child.getId().equals("beats"))
			{
				rest = new Beats(child);
			}
			else
			{
				beat = new Beat(child);
			}
		}
	}
	
	public ArrayList<ArrayList<String>> getBeatList(String id, int beatNumber, ArrayList<ArrayList<String>> beatList)
	{
		if(beatNumber >= beatList.size())
		{
			beatList.add(new ArrayList<String>());
		}
		
		if(!beat.getToken().equals("-"))
		{
			beatList.get(beatNumber).add(id);
		}
		
		if(rest != null)
		{
			rest.getBeatList(id, beatNumber + 1, beatList);
		}
		
		return beatList;
	}
	
	public Beat getBeat()
	{
		return beat;
	}
	
	public Beats getRest()
	{
		return rest;
	}

}
