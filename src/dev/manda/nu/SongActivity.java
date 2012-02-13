package dev.manda.nu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class SongActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.songs);
		// Gallery gallery = (Gallery) findViewById(R.id.songGallery);
		// gallery.setAdapter(new SongAdapter(this));
	}

	public void nextSong(View view) {
		ViewFlipper songFlipper = (ViewFlipper) findViewById(R.id.songFlipper);
		songFlipper.showNext();
	}

	public void prevSong(View view) {
		ViewFlipper songFlipper = (ViewFlipper) findViewById(R.id.songFlipper);
		songFlipper.showPrevious();
	}

	public class SongAdapter extends BaseAdapter {

		int mGalleryItemBackground;
		private Context mContext;
		private Integer[] mStringIds = { R.string.balladenOmDenKaxigaMyran, R.string.enMandaGarPaVagen };

		public SongAdapter(Context c) {
			mContext = c;
			// TypedArray attr = mContext.obtainStyledAttributes(R.styleable.SongGallery);
			// mGalleryItemBackground = attr.getResourceId(R.styleable.SongGallery_android_galleryItemBackground, 0);
			// attr.recycle();
		}

		public int getCount() {
			return mStringIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ScrollView scrollView = new ScrollView(mContext);
			scrollView.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			TextView songView = new TextView(mContext);
			songView.setText(mStringIds[position]);
			songView.setLayoutParams(new Gallery.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
			songView.setBackgroundResource(mGalleryItemBackground);
			scrollView.addView(songView);
			return scrollView;
		}
	}
}
