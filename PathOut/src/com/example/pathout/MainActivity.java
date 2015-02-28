package com.example.pathout;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.animation.*;
import android.widget.ImageButton;

public class MainActivity extends Activity {
    
	ImageButton guide, start;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		guide = (ImageButton)findViewById(R.id.guide);
		start = (ImageButton)findViewById(R.id.start);
		
		Animation alwaysTwinkleFastAnimation =AnimationUtils.loadAnimation(
                MainActivity.this, R.anim.always_twinkle_fast);
		start.startAnimation(alwaysTwinkleFastAnimation);
		
		guide.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent(v.getContext(), GuideActivity.class);
				startActivityForResult(intent1,0);
			}
		});

		start.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent2 = new Intent(v.getContext(), StartActivity.class);
				startActivityForResult(intent2,0);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
