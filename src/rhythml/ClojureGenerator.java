package rhythml;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClojureGenerator
{

	public static void main(String[] args)
	{
		//String data = "(p (rhy (inst B : (bar [ (beats o (beats - (beats - (beats -)))) ])) (rhy (inst SN : (bar [ (beats - (beats - (beats o (beats -)))) ])) (rhy (inst HH : (bar [ (beats x (beats X (beats x (beats x)))) ]))))))";
		//String data = "(p (rhy (inst HH : (bar [ (beats x (beats - (beats x (beats - (beats x (beats - (beats x (beats - (beats x (beats - (beats x (beats - (beats x (beats - (beats x (beats - (beats x (beats - (beats x (beats - (beats x (beats - (beats x (beats - (beats x (beats - (beats x (beats - (beats x (beats - (beats x (beats -)))))))))))))))))))))))))))))))) ])) (rhy (inst HT : (bar [ (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats o (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats o (beats -)))))))))))))))))))))))))))))))) ])) (rhy (inst SN : (bar [ (beats - (beats - (beats - (beats - (beats o (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats o (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats o (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats o (beats - (beats - (beats -)))))))))))))))))))))))))))))))) ])) (rhy (inst B : (bar [ (beats o (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats o (beats o (beats - (beats - (beats - (beats - (beats - (beats - (beats o (beats - (beats - (beats - (beats - (beats - (beats - (beats - (beats o (beats o (beats - (beats - (beats - (beats - (beats - (beats -)))))))))))))))))))))))))))))))) ])))))))";
		
		List<String> argList = new ArrayList<String>(Arrays.asList(args));
		List<String> flagList = extractFlags(argList);
		
		String name = argList.get(0);
		int bpm = Integer.parseInt(argList.get(1));
		String data = argList.get(2);
		
		Node node = new Node(data);
		Rhythm rhythm = new Rhythm(node);
		ArrayList<ArrayList<String>> beatList = rhythm.getBeatList(null);
		System.out.println("Built rhythm of " + beatList.size() + " beats");
		
		System.out.println("Generating Clojure...");
		String result = generateClojure(beatList, name, bpm);
		
		BufferedWriter bufferedWriter = null;
		String fileName = argList.get(3);
		System.out.println("Writing to " + fileName);
		
		try 
		{
		    bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));
		    bufferedWriter.write(result);
		    bufferedWriter.newLine();
		    bufferedWriter.flush();
		} 
		catch (IOException ioe) 
		{
		    ioe.printStackTrace();
		}
		
	    if (bufferedWriter != null) {
	        try {
	            bufferedWriter.close();
	        } catch (IOException ioe2) {
	            
	        }
	    }
	    
		System.out.println("Done!");
		
	}
	
	public static List<String> extractFlags(List<String> args)
	{
		List<String> flags = new ArrayList<String>();
		
		String arg;
		for(int i = 0; i < args.size(); i++)
		{
			arg = args.get(i);
			if(arg.charAt(0) == '-')
			{
				flags.add(arg);
			}
		}
		
		for(int i = 0; i < flags.size(); i++)
		{
			args.remove(flags.get(i));
		}
		
		return flags;
	}
	
	public static String generateClojure(ArrayList<ArrayList<String>> beatList, String name, int bpm)
	{
		int interval = 1000/(bpm/60);
		String result = "";
		
		//Create pool
		result = result.concat("(def " + name + "-pool (overtone.at-at/mk-pool))\n\n");
		
		//Create rhythm
		result = result.concat("(defn " + name + " [] (let [time (now)]\n");
		for(int i = 0; i < beatList.size(); i++)
		{
			result = result.concat("    (at (+ " + i * interval + " time) \n");
			for(int j = 0; j < beatList.get(i).size(); j++)
			{
				if(beatList.get(i).get(j).equals("B"))
				{
					result = result.concat("        (overtone.inst.drum/kick) \n");
				}
				else if(beatList.get(i).get(j).equals("SN"))
				{
					result = result.concat("        (overtone.inst.drum/snare 405 1 0.1 0.1 0.25 40 1000) \n");
				}
				else if(beatList.get(i).get(j).equals("HT"))
				{
					result = result.concat("        (overtone.inst.drum/tom) \n");
				}
				else if(beatList.get(i).get(j).equals("HH"))
				{
					result = result.concat("        (overtone.inst.drum/closed-hat) \n");
				}
				else if(beatList.get(i).get(j).equals("CC"))
				{
					result = result.concat("        (overtone.inst.drum/open-hat) \n");
				}
			}
			result = result.concat("    )\n");
		}
		result = result.concat("))\n\n");
		
		//Create looped function
		int rhythmLength = beatList.size() * interval;
		result = result.concat("(defn " + name + "-loop [] \n");
		result = result.concat("    (overtone.at-at/every " + rhythmLength + " #(" + name + ") " + name + "-pool))\n\n");
		
		return result;
	}

}
