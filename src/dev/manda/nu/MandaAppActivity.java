package dev.manda.nu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MandaAppActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void searchWiki(View view) {
		System.out.println("wiki");
	}

	public void yakatanaRecipe(View view) {
		System.out.println("yakatana");
		Intent intent = new Intent(this, YakatanaActivity.class);
		startActivity(intent);
	}

	public void songList(View view) {
		System.out.println("songs");
	}

	public void imageGallery(View view) {
		System.out.println("images");
	}
}
