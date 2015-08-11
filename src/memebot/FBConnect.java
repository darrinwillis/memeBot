package memebot;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.exception.FacebookException;
import com.restfb.types.FacebookType;
import com.restfb.types.Page;
import com.restfb.types.User;

public class FBConnect {

	/* Variables */
	private String pageAccessToken = null;
	private String pageID = null;
	private FacebookClient fbClient;
	private User myuser = null; // Store references to your user and page
	private Page mypage = null; // for later use.
	private int counter = 0;

	public FBConnect() {
		try {
			pageAccessToken = getToken("accessToken.txt");
			
			pageID = getToken("id.txt");
			System.out.println("grabbed access token and page ID");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.print("failed to get tokens");
			e.printStackTrace();
		} 
		
		try {

			fbClient = new DefaultFacebookClient(pageAccessToken);
			myuser = fbClient.fetchObject("me", User.class);
			mypage = fbClient.fetchObject(pageID, Page.class);
			counter = 0;
			System.out.println("connected client without fail");
		} catch (FacebookException ex) { // So that you can see what went wrong
			ex.printStackTrace(System.err); // in case you did anything
											// incorrectly HAPPY DARRIN
			System.out.print("failed to connect");
		}
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

	public void makeTestPost() {
		fbClient.publish(
				pageID + "/feed",
				FacebookType.class,
				Parameter.with("message", Integer.toString(counter)
						+ ": Hello, facebook World did this work?!"));
		Random rand = new Random();
		counter = rand.nextInt(50);
	}
	public void postPhoto(String photo, String caption){
		//This method will generate a photo post given a file path to a photo, and a caption
		FacebookType publishPhotoResponse = fbClient.publish("me/photos", FacebookType.class,
				  BinaryAttachment.with(photo, getClass().getResourceAsStream(photo)),
				  Parameter.with("message", caption));
				System.out.println("Published photo ID: " + publishPhotoResponse.getId());
	}

}