/*
 Copyright aswing.org, see the LICENCE.txt.
*/

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

	
import org.aswing.plaf.*;
import org.aswing.plaf.basic.BasicTabbedPaneUI;
	
/**
 * A component that lets the user switch between a group of components by
 * clicking on a tab with a given title and/or icon.
 * <p>
 * Tabs/components are added to a <code>TabbedPane</code> object by using the
 * <code>addTab</code> and <code>insertTab</code> methods.
 * A tab is represented by an index corresponding
 * to the position it was added in, where the first tab has an index equal to 0
 * and the last tab has an index equal to the tab count minus 1.
 * @author iiley
 */	
	
public class JTabbedPane extends AbstractTabbedPane{

	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
	public static  int TOP =AsWingConstants.TOP ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
    public static  int LEFT =AsWingConstants.LEFT ;
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
    public static  int BOTTOM =AsWingConstants.BOTTOM ;
 	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
    public static  int RIGHT =AsWingConstants.RIGHT ;	
	
	private int tabPlacement ;
	
    /**
     * JTabbedPane()
     * <p>
     */
	public  JTabbedPane (){
		super();
		setName("JTabbedPane");
		tabPlacement = TOP;
		
		updateUI();
	}

	 public void  updateUI (){
		setUI(UIManager.getUI(this));
	}
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicTabbedPaneUI;
    }
		
	 public String  getUIClassID (){
		return "TabbedPaneUI";
	}
	
    /**
     * Sets the tab placement for this tabbedpane.
     * Possible values are:<ul>
     * <li><code>JTabbedPane.TOP</code>
     * <li><code>JTabbedPane.BOTTOM</code>
     * <li><code>JTabbedPane.LEFT</code>
     * <li><code>JTabbedPane.RIGHT</code>
     * </ul>
     * The default value, if not set, is <code>SwingConstants.TOP</code>.
     *
     * @param tabPlacement the placement for the tabs relative to the content
     */
    public void  setTabPlacement (int tabPlacement ){
    	if(this.tabPlacement != tabPlacement){
    		this.tabPlacement = tabPlacement;
    		revalidate();
    		repaint();
    	}
    }
    
    /**
     * Returns the placement of the tabs for this tabbedpane.
     * @see #setTabPlacement()
     */
    public int  getTabPlacement (){
    	return tabPlacement;
    }
    
	/**
	 * Generally you should not set layout to JTabbedPane.
	 * @param layout layoutManager for JTabbedPane
	 * @throws ArgumentError when you set a non-TabbedPaneUI layout to JTabbedPane.
	 */
	 public void  setLayout (LayoutManager layout ){
		if(layout is ComponentUI){
			super.setLayout(layout);
		}else{
			throw ArgumentError("Cannot set non-AccordionUI layout to JAccordion!");
		}
	}
    
    /**
     *Not support this  .
     * @throws Error("Not supported setVisibleAt!")
     */
     public void  setVisibleAt (int index ,boolean visible ){
    	throw new Error("Not supported setVisibleAt!");
    }	
	
}


