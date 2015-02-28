package com.example.pathout;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class FailedActivity extends Activity {

	ImageButton backToMenu, restartButton;
	int SIZE_OF_BLOCKS;
	ImageView imageView;
	TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_failed);
		
		backToMenu =  (ImageButton)findViewById(R.id.backToMenu);
		restartButton = (ImageButton)findViewById(R.id.restart);
		Animation alwaysTwinkleFastAnimation =AnimationUtils.loadAnimation(
                FailedActivity.this, R.anim.always_twinkle_fast);
		restartButton.startAnimation(alwaysTwinkleFastAnimation);
		
		
		imageView = (ImageButton)findViewById(R.id.imageView1);
		textView = (TextView)findViewById(R.id.textView1);
		setFont(textView);
		textView.setText("You failed!!! Keep trying!!!");
		
		
		backToMenu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent backToMenu = new Intent(v.getContext(),StartActivity.class);
				startActivityForResult(backToMenu, 0);	
			}
		});
		
		restartButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				restartButton.clearAnimation();
				Intent intent = getIntent();
				SIZE_OF_BLOCKS = intent.getExtras().getInt("activity");
				
				Intent restartIntent = new Intent(v.getContext(), GameActivity.class);
				restartIntent.putExtra("activity", SIZE_OF_BLOCKS);
				startActivityForResult(restartIntent,2);
				finish();
			}
		});
	}
	
	public void setFont(TextView fontTextView){
		//TextView textView = (TextView) findViewById(R.id.theScore);
		Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/SHOWG.ttf");
		fontTextView.setTypeface(typeFace);
		
		fontTextView.setTextColor(Color.rgb(3, 158, 231));
		fontTextView.setTextSize(20);
	}

}
