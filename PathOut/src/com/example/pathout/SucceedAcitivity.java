package com.example.pathout;

import java.lang.annotation.RetentionPolicy;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SucceedAcitivity extends Activity {
  
	ImageButton backToMenu, restartButton;
	ImageView theScoreView;
	TextView textView;
	int SIZE_OF_BLOCKS;
	long DURATION;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_succeed);
		
		backToMenu =  (ImageButton)findViewById(R.id.backToMenu);
		restartButton = (ImageButton)findViewById(R.id.restart);
		Animation alwaysTwinkleFastAnimation =AnimationUtils.loadAnimation(
                SucceedAcitivity.this, R.anim.always_twinkle_fast);
		restartButton.startAnimation(alwaysTwinkleFastAnimation);
		
		Intent durationtimeIntent = getIntent();
		DURATION = durationtimeIntent.getExtras().getLong("durationtime");
		System.out.println("+++++++++++++++++++++++++++++++++Duration used "+DURATION);
		Intent intent = getIntent();
		SIZE_OF_BLOCKS = intent.getExtras().getInt("activity");
		
		calAndShowScore();
		
		backToMenu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent backToMenu = new Intent(v.getContext(),StartActivity.class);
				startActivity(backToMenu);
				finish();
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
	
	public void calAndShowScore() {
		int time= (int) DURATION;
		
		theScoreView = (ImageView)findViewById(R.id.score);
		textView = (TextView)findViewById(R.id.textView1);
		setFont(textView);
		
		
		String aString ="High IQ means you can be a geek or juse a fool...";
		String bString ="You're but a common person...";
		String cString ="You are almost a fool...";
		String dString ="Children and fools cannot lie. Yes, you are totally a fool";
		
		
		int totaltime = SIZE_OF_BLOCKS*10000/4;
		System.out.println("totaltime :"+totaltime);
		System.out.println("time :"+time);
		
		int a =totaltime/4;
		int b =a*2;
		int c =a*3;
		int d = a*4;
		
		if (time>=0 && time<a){
			theScoreView.setImageResource(R.drawable.score_a);
			textView.setText(aString);
	
		}else if(time>=a && time<b){
			theScoreView.setImageResource(R.drawable.score_b);
			textView.setText(bString);

		}else if(time>=b && time <c){
			theScoreView.setImageResource(R.drawable.score_c);
			textView.setText(cString);

		}else if (time>=c && time<=d) {
			theScoreView.setImageResource(R.drawable.score_d);
			textView.setText(dString);

		}
	}
	
	public void setFont(TextView fontTextView){
		//TextView textView = (TextView) findViewById(R.id.theScore);
		Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/SHOWG.ttf");
		fontTextView.setTypeface(typeFace);
		
		fontTextView.setTextColor(Color.rgb(3, 158, 231));
		fontTextView.setTextSize(20);
	}

}
