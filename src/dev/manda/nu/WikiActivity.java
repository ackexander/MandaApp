package dev.manda.nu;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

public class WikiActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wiki);
		String information = getIntent().getExtras().getString("INFORMATION");
		TextView wikiText = (TextView) findViewById(R.id.wikiText);
		if (getIntent().getExtras().getString("IMAGE") != null) {
			setImage(getIntent().getExtras().getString("IMAGE"));
		}
		wikiText.setText(information);
	}

	private void setImage(String imageURL) {
		try {
			TextView wikiImage = (TextView) findViewById(R.id.wikiText);
			InputStream is = (InputStream) this.fetch(imageURL);
			Drawable d = Drawable.createFromStream(is, null);
			wikiImage.setCompoundDrawablesWithIntrinsicBounds(null, null, null, d);
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
}
