/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf.basic.accordion;

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


import org.aswing.*;
import org.aswing.plaf.basic.tabbedpane.Tab;
	
/**
 * BasicAccordionHeader implemented with a JButton 
 * @author iiley
 * @private
 */
public class BasicAccordionHeader implements Tab{
	
	protected AbstractButton button ;
	protected Component owner ;
	
	public  BasicAccordionHeader (){
	}
	
	public void  initTab (Component owner ){
		this.owner = owner;
		button = createHeaderButton();
	}
	
	protected AbstractButton  createHeaderButton (){
		return new JButton();
	}
	
	public void  setTextAndIcon (String text ,Icon icon ){
		button.setText(text);
		button.setIcon(icon);
	}
	
	public void  setFont (ASFont font ){
		button.setFont(font);
	}
	
	public void  setForeground (ASColor color ){
		button.setForeground(color);
	}
	
	public void  setSelected (boolean b ){
		//Do nothing here, if your header is selectable, you can set it here like
		//button.setSelected(b);
	}
	
    public void  setVerticalAlignment (int alignment ){
    	button.setVerticalAlignment(alignment);
    }
    public void  setHorizontalAlignment (int alignment ){
    	button.setHorizontalAlignment(alignment);
    }
    public void  setVerticalTextPosition (int textPosition ){
    	button.setVerticalTextPosition(textPosition);
    }
    public void  setHorizontalTextPosition (int textPosition ){
    	button.setHorizontalTextPosition(textPosition);
    }
    public void  setIconTextGap (int iconTextGap ){
    	button.setIconTextGap(iconTextGap);
    }
    public void  setMargin (Insets m ){
    	button.setMargin(m);
    }

	public Component  getTabComponent (){
		return button;
	}

}


