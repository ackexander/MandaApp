package dev.manda.nu;

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
		if (!wikiSearch.getText().toString().equals("")) {
			Intent intent = new Intent(this, WikiActivity.class);
			intent.putExtra("SEARCH_WORD", wikiSearch.getText().toString());
			startActivity(intent);
		} else {
			// TODO toast
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
}
