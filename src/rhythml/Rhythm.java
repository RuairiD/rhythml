package rhythml;

import java.util.ArrayList;
import java.util.Set;

public class Rhythm {

	/**
	 * @param args
	 */
	private Instrument instrument;
	private Rhythm rhythm;
	
	public Rhythm(Instrument pInstrument, Rhythm pRhythm)
	{
		instrument = pInstrument;
		rhythm = pRhythm;
	}
	
	public Rhythm(Node node)
	{
		Set<Node> children = node.getChildren();
		for (Node child : children)
		{
			if(child.getId().equals("inst"))
			{
				instrument = new Instrument(child);
			}
			else if(child.getId().equals("rhy"))
			{
				rhythm = new Rhythm(child);
			}
		}
	}
	
	public ArrayList<ArrayList<String>> getBeatList(ArrayList<ArrayList<String>> beatList)
	{
		if(beatList == null)
		{
			beatList = new ArrayList<ArrayList<String>>();
		}
		
		if(instrument != null)
		{
			instrument.getBeatList(beatList);
		}
		if(rhythm != null)
		{
			rhythm.getBeatList(beatList);
		}
		
		return beatList;
	}
	
	public Instrument getInstrument()
	{
		return instrument;
	}
	
	public Rhythm getRhythm()
	{
		return rhythm;
	}

}
