package memebot;

import java.io.IOException;
import java.util.List;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;

public class RedditConnect {
	
	static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	static final JsonFactory JSON_FACTORY = new JacksonFactory();
	
	private static class RedditUrl extends GenericUrl {
	    public RedditUrl(String encodedUrl) {
	        super(encodedUrl);
	        }
	    public static RedditUrl topPosts(String subreddit) {
	    	return new RedditUrl(
	    			"https://reddit.com/r/" + subreddit + "/hot/.json");
	    }
	}
	
	public static class ReturnObject {
		@Key("data")
		public ReturnData returnData;
	}
	
	public static class ReturnData {
		@Key("children")
		public List<PostWrap> postWraps;
	}
	
	public static class PostWrap {
		@Key("data")
		public Post post;
	}
	
	public static class Post {
		@Key
		public String author;
		
		@Key
		public String title;
		
		@Key
		public String url;
	}
	
	public static void main(String[] args) {
		System.out.println("Hello World");
	    HttpRequestFactory requestFactory =
	            HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
	                @Override
	              public void initialize(HttpRequest request) {
	                request.setParser(new JsonObjectParser(JSON_FACTORY));
	              }
	            });
		
		RedditUrl url = RedditUrl.topPosts("circlejerk");
		
		try {
			HttpRequest request = requestFactory.buildGetRequest(url);
		    ReturnObject retObj = request.execute().parseAs(ReturnObject.class);
		    for (PostWrap pw : retObj.returnData.postWraps) {
		    	Post p = pw.post;
		    	System.out.printf("[%s] %s \n    '%s'\n", p.author, p.title, p.url);
		    }
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

	}
}
