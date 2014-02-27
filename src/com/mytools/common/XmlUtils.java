package com.mytools.common;

public class XmlUtils {
	public final static String unescapeXMLChars(String input)
	{
		if (input == null)
			return null;
		
		String outStr;

		outStr = input.replace("&amp;", "&");
		outStr = outStr.replace("&lt;", "<");
		outStr = outStr.replace("&gt;", ">");
		outStr = outStr.replace("&apos;", "\'");
		outStr = outStr.replace("&quot;", "\"");
		
		return outStr;
	}
	
	public final static String DeleteXmldeclaration(String input){
		if (input == null)
			return null;
		
		String outStr;
		outStr=input.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+outStr;
	}
}
