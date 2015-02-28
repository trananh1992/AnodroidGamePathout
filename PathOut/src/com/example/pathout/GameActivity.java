package com.example.pathout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class GameActivity extends Activity {
	
   
	int SIZE_OF_BLOCKS;
//	Button succeed, failed, backToMenu;
	ProgressBar pBar;
	int timeLimit;
	Timer timerLimit;
	TimerTask taskTimeLimit;
	ImageButton startBtn;
	TableLayout blocksBackGround,blocks;
	ArrayList<ImageView> blocksArrayList;
	ArrayList<Integer> pathArrayList;
	Timer timer;
	TimerTask task;
	int delay;
	int numOfClicked;
	int count;
	
	long begin_second;
	long end_second;
	long DURATION_TIME;
	
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case 1:
				
				startGame();
				break;
				
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		Intent intent = getIntent();
		SIZE_OF_BLOCKS = intent.getExtras().getInt("activity");
		timeLimit = SIZE_OF_BLOCKS*10000/4;
		pBar = (ProgressBar)findViewById(R.id.progressBar1);
		pBar.setProgress(0);
		delay =SIZE_OF_BLOCKS*1000/3*2;
		
		blocksArrayList = new ArrayList<ImageView>();
		blocksBackGround = (TableLayout) this.findViewById(R.id.blocksBackGround); 
		blocks = (TableLayout) this.findViewById(R.id.blocks);
				
		generateBlocksBackground();
		generateBlocks();
		
		startBtn = (ImageButton)findViewById(R.id.startgame);
		Animation alwaysTwinkleFastAnimation =AnimationUtils.loadAnimation(
                GameActivity.this, R.anim.always_twinkle_fast);
		startBtn.startAnimation(alwaysTwinkleFastAnimation);
		startBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				startBtn.clearAnimation();
				showPath();
				RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.gameLayout);
				relativeLayout.removeView(startBtn);
				timer= new Timer();
			    task = new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Message message = new Message();
						message.what=1;
						handler.sendMessage(message);
					}
				};
				timer.schedule(task, delay);
				
			}
		});
			
	}

	
	public void showPath(){
		Path path = new Path(SIZE_OF_BLOCKS);
	    pathArrayList = path.getPath();
		
		for(int i=0; i< SIZE_OF_BLOCKS; i++){		
			for(int j=0; j<SIZE_OF_BLOCKS;j++){
				
				int index = i*SIZE_OF_BLOCKS+j;			
				
				for(int k=0; k<pathArrayList.size();k++){

					if(pathArrayList.get(k)==index){
						this.blocksArrayList.get(index).setAlpha(1.0f);
					}
				}
				
			}
		}
		
	}
	
	public void startGame(){
		
		hidePath();
		generateBar();
		addBlockListeners();
		begin_second=getMSecond();
	}
	
	public void hidePath(){
		for(int i=0; i< SIZE_OF_BLOCKS; i++){		
			for(int j=0; j<SIZE_OF_BLOCKS;j++){
				
				int index = i*SIZE_OF_BLOCKS+j;			
				
				for(int k=0; k<pathArrayList.size();k++){

					if(pathArrayList.get(k)==index){
						this.blocksArrayList.get(index).setAlpha(0.0f);
					}
				}
				
			}
		}
	}
	
	public boolean isInPath(int index){
		if (pathArrayList.contains(index)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void generateBlocks(){
		for (int i = 0; i < SIZE_OF_BLOCKS; i++) {
			DisplayMetrics metric = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(metric);
	        int width = metric.widthPixels;     
	        int height = metric.heightPixels;   
	        
			TableRow tableRow = new TableRow(this);  	          
	        blocks.addView(tableRow);         
	        for (int j = 0; j < SIZE_OF_BLOCKS; j++) {
	        	ImageView aBlock = new ImageView(GameActivity.this);
					Bitmap bm = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.foot_print));
					Bitmap newBm = scaleImg(bm , (int)(width/(SIZE_OF_BLOCKS+0.1)), (int)(width/(SIZE_OF_BLOCKS+0.1)));
					aBlock.setImageBitmap(newBm);					
				aBlock.setAlpha(0.0f);
				//******************set the id of a block
				//******************id should equals to the index in blockArrayList
				int id = i*SIZE_OF_BLOCKS+j;
				aBlock.setId(id);
				
				tableRow.addView(aBlock);
				blocksArrayList.add(aBlock);
			}//end of for j
		}
		
		
	}
	

	public void generateBlocksBackground(){
		DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     
        int height = metric.heightPixels;   
        
		for (int i = 0; i < SIZE_OF_BLOCKS; i++) {
			TableRow tableRow = new TableRow(this);  	          
	        blocksBackGround.addView(tableRow);         
	        for (int j = 0; j < SIZE_OF_BLOCKS; j++) {
	        	ImageView aBlock = new ImageView(GameActivity.this);
					Bitmap bm = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.block_bg));
					Bitmap newBm = scaleImg(bm , (int)(width/(SIZE_OF_BLOCKS+0.1)), (int)(width/(SIZE_OF_BLOCKS+0.1)));
					aBlock.setImageBitmap(newBm);
	    			aBlock.setBackgroundColor(Color.WHITE);
	    			
				tableRow.addView(aBlock);
			}
		}
	}
	
	public void generateBar() {
		pBar.setProgress(0);
		timerLimit= new Timer();
		taskTimeLimit = new TimerTask() {
			
			@Override
			public void run() {
				if(pBar.getProgress()<100){
					pBar.incrementProgressBy(1);
				}
				if(pBar.getProgress()==100){
					//timerLimit.cancel();
					pBar.setProgress(0);
					taskTimeLimit.cancel();
					timerLimit.cancel();
					System.out.println("bar:::::::::");
					failed(pBar);
				}
			}
		};
		timerLimit.schedule(taskTimeLimit, 0, timeLimit/100);
	}
	
	public void addBlockListeners(){
		count = pathArrayList.size();
		numOfClicked=0;
		for (int i = 0; i <blocksArrayList.size(); i++) {
			blocksArrayList.get(i).setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					// System.out.println("++++++++");
					int id = v.getId();
					//System.out.println("id  : " + id);
					if (count - numOfClicked - 1 >= 0) {
						//System.out.println("id+++++++++++++: " + id);
						//System.out.println(" numofclick -- " + numOfClicked);
						//System.out.println("pathArrayList: "
								//+ pathArrayList.get(count - numOfClicked - 1));
						int temp = pathArrayList.get(count - numOfClicked - 1);
						if (id == temp) {
							numOfClicked++;
							// light(v);
							v.setAlpha(1.0f);
//							System.out.println("" + count + "   "
//									+ numOfClicked);

							if (numOfClicked == count) {
								//System.out
								//		.println("++++++++++++++++++++++++++++++++++++successla!!!!!!!!!!!!");
								success(v);
							}
						} else {
							//System.out.println("**************failed*********");
							failed(v);
						}
					}
				}
			});
			
		}//end of for
	} 
	
	public void light(View v){
		
		v.setAlpha(1.0f);
		ImageView iv= (ImageView)v;
		Animation anim =AnimationUtils.loadAnimation(GameActivity.this, R.anim.alpha);
		iv.startAnimation(anim);
		
	}
	
	public void success(View v) {	
		taskTimeLimit.cancel();
		timerLimit.cancel();
		
		end_second=getMSecond();	
		DURATION_TIME = end_second-begin_second;

		Intent successIntent = new Intent(v.getContext(), SucceedAcitivity.class);
		successIntent.putExtra("activity", SIZE_OF_BLOCKS);
		successIntent.putExtra("durationtime", DURATION_TIME);
		startActivityForResult(successIntent,2);
		
		finish();
	}
	
	public void failed(View v){
		taskTimeLimit.cancel();
		timerLimit.cancel();
		
		end_second=getMSecond();	
		DURATION_TIME = end_second-begin_second;

		Intent failedIntent = new Intent(v.getContext(), FailedActivity.class);
		failedIntent.putExtra("activity", SIZE_OF_BLOCKS);
		failedIntent.putExtra("durationtime", DURATION_TIME);
		startActivityForResult(failedIntent,2);
		finish();
	}
	
	protected Bitmap scaleImg(Bitmap bm, int newWidth, int newHeight) {

	    int width = bm.getWidth();
	    int height = bm.getHeight();

	    int newWidth1 = newWidth;
	    int newHeight1 = newHeight;

	    float scaleWidth = ((float) newWidth1) / width;
	    float scaleHeight = ((float) newHeight1) / height;

	    Matrix matrix = new Matrix();
	    matrix.postScale(scaleWidth, scaleHeight);

	    Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
	      true);
	    return newbm;
	   }
	
	protected long getMSecond() {		
		long mSecond = System.currentTimeMillis();
		return mSecond;
	}
}
