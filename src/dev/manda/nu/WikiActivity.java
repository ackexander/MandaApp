package dev.manda.nu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class WikiActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wiki);
		String information = (String) getIntent().getExtras().get("INFORMATION");
		TextView wikiText = (TextView) findViewById(R.id.wikiText);
		wikiText.setText(information);
	}
}
