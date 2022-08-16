package com.jeroenreijn.examples.view;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class StringCache {
	private Map<String, byte[]> stringRendered=new HashMap<>();

	public void write(OutputStream os, String s) throws IOException {
		byte[] data=stringRendered.get(s);
		if(data==null)
		{
			data=s.getBytes(StandardCharsets.UTF_8);
			stringRendered.put(s, data);
		}
		os.write(data);
	}
}
