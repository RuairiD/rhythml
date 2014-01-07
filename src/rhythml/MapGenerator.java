package rhythml;

import java.util.ArrayList;
import java.util.HashMap;

public class MapGenerator
{

	public static HashMap<Object, Object> getMap(int bpm, ArrayList<Object> data)
	{
		Node node = new Node(data);

		Rhythm rhythm = new Rhythm(node);
		ArrayList<ArrayList<String>> beatList = rhythm.getBeatList(null);
		
		HashMap<Object, Object> result = new HashMap<Object, Object>();
		ArrayList<HashMap<Object, Object>> beats = new ArrayList<HashMap<Object, Object>>();

		HashMap<Object, Object> beat;
		for(int i = 0; i < beatList.size(); i++)
		{
			beat = new HashMap<Object, Object>();
			beat.put("count", i);
			beat.put("instruments", beatList.get(i));
			beats.add(beat);
		}
		
		result.put("interval", (int) Math.round(60000/bpm));
		result.put("length", beatList.size());
		result.put("beats", beats);
		
		return result;
	}
	
	public static void main(String[] args)
	{
		
	}

}
