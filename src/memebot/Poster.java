package memebot;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;


public class Poster {

	public static void main(String[] args) throws IOException {
		
		FBConnect connection = new FBConnect();
		connection.makeTestPost();
		connection.postPhoto("/photos/dank.jpg", "test");
		System.out.println("done");
		return;
		

	}


}
