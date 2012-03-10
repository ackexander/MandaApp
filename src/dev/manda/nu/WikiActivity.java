package dev.manda.nu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class WikiActivity extends Activity {

	private String mImageUrl = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageUrl = null;
		setContentView(R.layout.wiki);
		String searchWord = getIntent().getExtras().getString("SEARCH_WORD");
		try {
			String searchResult = executeSearch(searchWord);
			String information = getPageContext(searchResult);
			TextView wikiText = (TextView) findViewById(R.id.wikiText);
			if (mImageUrl != null) {
				setImage(mImageUrl);
			}
			wikiText.setText(information);
		} catch (Exception e) {
			// TODO do toast
		}
	}

	private void setImage(String imageURL) {
		try {
			InputStream is = (InputStream) this.fetch(imageURL);
			Drawable d = Drawable.createFromStream(is, null);
			ImageView wikiImage = (ImageView) findViewById(R.id.wikiImage);
			wikiImage.setImageDrawable(d);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Object fetch(String address)
			throws MalformedURLException, IOException {
		URL url = new URL(address);
		Object content = url.getContent();
		return content;
	}

	private String executeSearch(String searchWord)
			throws Exception {
		BufferedReader in = null;
		String page = "";
		// searchWord = searchWord.replaceAll("å", "%C3%A5");
		// searchWord = searchWord.replaceAll("ä", "%C3%A4");
		// searchWord = searchWord.replaceAll("ö", "%C3%B6");
		// searchWord = searchWord.replaceAll("Å", "%C3%A5");
		// searchWord = searchWord.replaceAll("Ä", "%C3%A5");
		// searchWord = searchWord.replaceAll("Ö", "%C3%A5");
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
		if (searchResult.contains("Kategori:Arbetare")) {
			content = parseArbetare(searchResult);
		} else {
			// TODO parsa annat
		}
		return content;
	}

	private String parseArbetare(String searchResult) {
		String arbetareContent = getFaktaruta(searchResult);
		arbetareContent += "\nBeskrivning:\n" + getDescription(searchResult);
		String imageURL = getImageURL(searchResult);
		if (imageURL != "") {
			mImageUrl = imageURL;
		}
		return arbetareContent;
	}

	private String getImageURL(String searchResult) {
		String imageURL = "";
		int startImage = searchResult.indexOf("<div class=\"thumbinner\"");
		if (startImage != -1) {
			int endImage = searchResult.indexOf("</a>", startImage);
			imageURL = searchResult.substring(startImage, endImage);
			Pattern p = Pattern.compile("src=\".*?\"");
			Matcher matcher = p.matcher(imageURL);
			if (matcher.find()) {
				imageURL = matcher.group();
				imageURL = "http://manda.nu" + imageURL.substring(5, imageURL.length() - 1);
			} else {
				imageURL = "";
			}
		}
		return imageURL;
	}

	private String getDescription(String searchResult) {
		int descriptionStart = searchResult.indexOf("</table>");
		int descriptionEnd = searchResult.indexOf("<div class=\"", descriptionStart);
		String description = searchResult.substring(descriptionStart, descriptionEnd);
		descriptionEnd = description.lastIndexOf("</p>");
		description = description.substring(0, descriptionEnd);
		description = formatDescription(description);
		description = description.trim();
		return description;
	}

	private String formatDescription(String description) {
		description = description.replaceAll("\n", "");
		description = description.replaceAll("</p>", "\n");
		description = description.replaceAll("<.*?>", "");
		description = description.replaceAll("\\.", ". ");
		description = description.replaceAll("\\.  ", ". ");
		description = description.replaceAll("\\?", "? ");
		description = description.replaceAll("\\?  ", "? ");
		description = description.replaceAll("!", "! ");
		description = description.replaceAll("!  ", "! ");
		return description;
	}

	private String getFaktaruta(String searchResult) {
		String arbetareContent = "";
		int factStart = searchResult.indexOf("<table>");
		int factEnd = searchResult.indexOf("</table>");
		String fact = searchResult.substring(factStart, factEnd);
		factStart = fact.indexOf("Namn");
		fact = fact.substring(factStart);
		fact = fact.replaceAll("<\\S*>", "");
		fact = fact.replaceAll("[\n]", "");
		String[] split = fact.split("[\\s]");
		for (int i = 0; i < split.length; i++) {
			if (split[i].getBytes().length != 0) {
				if (split[i].equals("Namn")) {
					arbetareContent = split[i] + ":";
				} else if (split[i].equals("Generation") || split[i].equals("Kallas") || split[i].equals("Årgång") || split[i].equals("Läs(er/te)")) {
					arbetareContent += "\n" + split[i] + ":";
				} else {
					arbetareContent += " " + split[i];
				}
			}
		}
		arbetareContent += "\n";
		return arbetareContent;
	}
}
