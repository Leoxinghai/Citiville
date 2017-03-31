package org.aswing;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.graphics.*;

import com.xiyu.util.Array;
import com.xiyu.util.Dictionary;

import org.aswing.plaf.basic.BasicPanelUI;
	

public class JPanel extends Container{
	
	public  JPanel (LayoutManager layout =null ){
		super();
		setName("JPanel");
		if(layout == null) layout = new FlowLayout();
		this.layout = layout;
		updateUI();
	}
	
	 public void  updateUI (){
		setUI(UIManager.getUI(this));
	}
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicPanelUI;
    }
	
	 public String  getUIClassID (){
		return "PanelUI";
	}
}


