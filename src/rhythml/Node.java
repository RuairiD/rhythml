package rhythml;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node
{
	private String id;
	private Set<Node> children;

	public Node(String data)
	{
		if(data.charAt(0) == '(')
		{
			data = data.substring(1, data.length() - 1);
		}
		
		List<String> strings = splitChildren(data);
		
		id = strings.get(0);
		children = new HashSet<Node>();
		
		for(int i = 1; i < strings.size(); i++)
		{
			children.add(new Node(strings.get(i)));
		}
	}
	
	public String getId()
	{
		return id;
	}
	
	public Set<Node> getChildren()
	{
		return children;
	}
	
	public static List<String> splitChildren(String data)
	{
		List<String> result = new ArrayList<String>();
		int startIndex = 0;
		int endIndex = 0;
		for(endIndex = 0; endIndex < data.length(); endIndex++)
		{
			if(data.charAt(endIndex) == ' ')
			{
				result.add(data.substring(startIndex, endIndex));
				
				startIndex = endIndex + 1;
			}
			
			if(data.charAt(startIndex) == '(')
			{
				endIndex = bracketLimit(data, startIndex);

				result.add(data.substring(startIndex, endIndex));
				
				startIndex = endIndex + 1;
			}
		}
		if(startIndex != endIndex)
		{
			result.add(data.substring(startIndex, endIndex));
		}
		return result;
	}
	
	public static int bracketLimit(String data, int startIndex)
	{
		int bracketDepth = 1;
		int endIndex = startIndex + 1;
		while(bracketDepth > 0)
		{
			if(data.charAt(endIndex) == '(')
			{
				bracketDepth++;
			}
			else if(data.charAt(endIndex) == ')')
			{
				bracketDepth--;
			}
			endIndex++;
		}
		return endIndex;
	}
	
	

}
