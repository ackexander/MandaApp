package dev.manda.nu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MandaAppActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void searchWiki(View view) {
		System.out.println("wiki");
		EditText wikiSearch = (EditText) findViewById(R.id.wikiSearchText);
		try {
			String searchResult = executeSearch(wikiSearch.getText().toString());
			String information = getPageContext(searchResult);
		} catch (Exception e) {
			// TODO make toast
		}
	}

	public void yakatanaRecipe(View view) {
		System.out.println("yakatana");
		Intent intent = new Intent(this, YakatanaActivity.class);
		startActivity(intent);
	}

	public void songList(View view) {
		System.out.println("songs");
		Intent intent = new Intent(this, SongActivity.class);
		startActivity(intent);
	}

	public void imageGallery(View view) {
		System.out.println("images");
	}

	private String executeSearch(String searchWord)
			throws Exception {
		BufferedReader in = null;
		String page = "";
		searchWord.replaceAll("å", "%C3%A5");
		searchWord.replaceAll("ä", "%C3%A4");
		searchWord.replaceAll("ö", "%C3%B6");
		searchWord.replaceAll("Å", "%C3%A5");
		searchWord.replaceAll("Ä", "%C3%A5");
		searchWord.replaceAll("ö", "%C3%A5");
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI("http://manda.nu/wiki/index.php/Special:S%C3%B6k?search=" + searchWord + "&go=G%C3%A5+till"));
			HttpResponse response = client.execute(request);
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while ((line = in.readLine()) != null) {
				sb.append(line + NL);
			}
			in.close();
			page = sb.toString();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return page;
	}

	private String getPageContext(String searchResult) {
		String content = "";
		int contentStart = searchResult.indexOf("<!-- start content -->");
		int contentEnd = searchResult.indexOf("<!-- end content -->");
		searchResult = searchResult.substring(contentStart, contentEnd);
		System.out.println(searchResult);
		if (searchResult.contains("Kategori:Arbetare")) {
			// TODO parsa arbetare
		} else {
			// TODO parsa annat
		}
		return content;
	}
}
