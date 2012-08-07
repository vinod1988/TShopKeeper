package com.yh.shopkeeper.activity;

import com.yh.shopkeeper.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class Flip extends Activity implements OnGestureListener {
	
	private GestureDetector detector;
	private ViewFlipper flipper;
	
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fliper_layout);
        
        flipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper01);
        
        flipper.addView(addImageView(1),new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        flipper.addView(addImageView(2),new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        flipper.addView(addImageView(3),new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        
        detector = new GestureDetector(this,this);
    }
    
    public View addButtonByText(String text){
        Button btn = new Button(this);  
        btn.setText(text);  
        return btn;  
    }  
    public View addTextByText(String text){
        TextView tv = new TextView(this);  
        tv.setText(text);  
        tv.setGravity(1);  
        return tv;  
    }
    
    public ImageView addImageView(Integer index){
    	ImageView tv = new ImageView(this);  
    	switch(index){
    	case 1:
    		tv.setImageResource(R.drawable.starting_guide_img_01);
    		break;
    	case 2:
    		tv.setImageResource(R.drawable.starting_guide_img_02);
    		break;
    	case 3:
    		tv.setImageResource(R.drawable.starting_guide_img_03);
    		break;
    	}
    	return tv;
    }
        
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Log.i("Fling", "Activity onTouchEvent!");
		return this.detector.onTouchEvent(event);
	}
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * 监听滑动
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		Log.i("Fling", "Fling Happened!");
		if (e1.getX() - e2.getX() > 120) {
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_right_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_right_out));
			this.flipper.showNext();
			return true;
		} else if (e1.getX() - e2.getX() < -120) {
			this.flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_left_in));
			this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_left_out));
			this.flipper.showPrevious();
			return true;
		}
		return true;
	}
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
}