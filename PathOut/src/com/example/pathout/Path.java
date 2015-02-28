package com.example.pathout;

import java.util.ArrayList;
import java.util.Random;

public class Path {
	

	private int size;
	private int[] position;
	private Random r = new Random();
	private int start;    // generate start point randomly
	private ArrayList<Integer> path = new ArrayList<Integer>();

   
	Path(int size){
	   position = new int[size*size];
	   this.size = size;
	   start = r.nextInt(size);
	   for (int i = 0; i <size*size; i++) {
		   position[i] = 0;
	   }
   }
   
   public ArrayList<Integer> getPath(){
	   ArrayList<Integer> nextPoint = new ArrayList<Integer>();
	   initialPath();
	   int now =start;
	   nextPoint = next(now);   
	  
	   
	   while(!nextPoint.isEmpty()){
		  // nextPoint.clear();
		   now = path.get(path.size()-1);
	       nextPoint = next(now);
	       if(nextPoint.isEmpty())
	    	   break;
	       int a = r.nextInt(100);
	       if(nextPoint.size()==3){
	    	   if(a < 20)
	    		   path.add(nextPoint.get(0));
	    	  
	    	   else if(a>=20&&a<60)
				
	    		   path.add(nextPoint.get(1));
	    	   else
	    		   path.add(nextPoint.get(2));			
	       }
	        if(nextPoint.size()==2){
	        	
	    	   if(a<30)
	    		   path.add(nextPoint.get(0));
	    	   else				
	    		   path.add(nextPoint.get(1));			
	       }
	       if(nextPoint.size()==1){
	    	   path.add(nextPoint.get(0));   
	       }	      
	   }
	   
	   int choice = r.nextInt(10);
	   if(choice>3 && nextPoint.isEmpty())
		   finalRow();
	   
	   return path;
   }
   
   public void initialPath(){
	   if(path.size()==0){
		   path.add(start);
	   }
   }
   
   public ArrayList<Integer> next(int now){
	   ArrayList<Integer> nextP= new ArrayList<Integer>();
	   int col_now = now%size;
	   int row_now = now/size;
	   
	   if(row_now == (size-1)){ 
		   return nextP;
	   }
	   
	   int col_next1 = col_now;
	   int row_next1 = row_now+1;
	   nextP.add(row_next1*size+col_next1);
	   
	   // the left column
	   if(col_now == 0){
		   int nextright = col_now+1+row_now*size;
		   if(!(path.contains(nextright))&& row_now==0){
			   nextP.add(nextright);
		   }
		   int top = (row_now-1)*size+col_now+1;
		   if(!(path.contains(nextright))&& (top>=0)&&!(path.contains(top))){
			   nextP.add(nextright);
		   }
	   }
	   
	   // the right column
	   if(col_now == (size-1)){
		   int nextleft = col_now-1+row_now*size;
		   if(!(path.contains(nextleft))&& row_now==0){
			   nextP.add(nextleft);
		   }
		   int top = (row_now-1)*size+col_now-1;
		   if(!(path.contains(nextleft))&& (top>=0)&&!(path.contains(top))){
			   nextP.add(nextleft);
		   }
	   }
	   
	   if((col_now>0)&&(col_now<(size-1))){
		   int nextleft = col_now-1+row_now*size;
		   if(!(path.contains(nextleft))&& row_now==0){
			   nextP.add(nextleft);
		   }
		   int lefttop = (row_now-1)*size+col_now-1;
		   if(!(path.contains(nextleft))&& (lefttop>=0)&&!(path.contains(lefttop))){
			   nextP.add(nextleft);
		   }
		   int nextright = col_now+1+row_now*size;
		   if(!(path.contains(nextright))&& row_now==0){
			   nextP.add(nextright);
		   }
		   int righttop = (row_now-1)*size+col_now+1;
		   if(!(path.contains(nextright))&& (righttop>=0)&&!(path.contains(righttop))){
			   nextP.add(nextright);
		   }  
	   }
	   return nextP;	   
   }
   
   public void finalRow () {
	int now_finalrow = path.get(path.size()-1);
	int col_now = now_finalrow%size;
    int row_now = now_finalrow/size;
    int lefttop = (row_now-1)*size+col_now-1;
    int righttop = (row_now-1)*size+col_now+1;
    
    // bottom right corner
    if(col_now== size-1 && !path.contains(lefttop)){
    	int num = r.nextInt(size-1);
    	for (int i = 0; i < num; i++) {
			path.add(now_finalrow-i);
		}
    }
    
    // bottom left corner
    if(col_now== 0 && !path.contains(righttop)){
    	int num = r.nextInt(size-1);
    	for (int i = 0; i < num; i++) {
			path.add(now_finalrow+i);
		}
    }
    
    if(col_now > 0 && col_now< size-1){
    	if(path.contains(lefttop)){
    		int num = r.nextInt(size-1-col_now);
    		for (int i = 0; i < num; i++) {
				path.add(now_finalrow+i);
			}
    	}
    	else if(path.contains(righttop)){
    		int num = r.nextInt(col_now);
    		for(int i = 0; i< num; i++){
    			path.add(now_finalrow-i);
    		}
    	}
    	else {
    		int direction = r.nextInt(2);
    		if(direction == 0){
    			int num = r.nextInt(size-1-col_now);
        		for (int i = 0; i < num; i++) {
    				path.add(now_finalrow+i);
    			}
    		}
    		if(direction == 1){
    			int num = r.nextInt(col_now);
        		for(int i = 0; i< num; i++){
        			path.add(now_finalrow-i);
        		}
    		}
    	}
    	
    }
    
}
	
	
}

