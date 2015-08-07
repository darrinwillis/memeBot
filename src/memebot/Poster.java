package memebot;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;


public class Poster {

	public static void main(String[] args) throws IOException {
		
		FacebookClient facebookClient = new DefaultFacebookClient(getToken("accessToken.txt"));
		String pageID = getToken("id.txt");
		System.out.println("created connection to page");
		
		return;
		

	}
	public static String getToken(String tokenLocation) throws IOException {
		File key = new File(tokenLocation);
		String result = deserializeString(key);
		return result;
	}
	
	public static String deserializeString(File file) throws IOException {
		int len;
		char[] chr = new char[4096];
		final StringBuffer buffer = new StringBuffer();
		final FileReader reader = new FileReader(file);
		try {
			while ((len = reader.read(chr)) > 0) {
				buffer.append(chr, 0, len);
			}
		} finally {
			reader.close();
		}
		return buffer.toString();
	}


}
