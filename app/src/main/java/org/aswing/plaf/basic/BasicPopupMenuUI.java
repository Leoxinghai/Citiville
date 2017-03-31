package org.aswing.plaf.basic;

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
import flash.display.*;
import org.aswing.geom.*;
import org.aswing.plaf.*;

/**
 * @private
 * @author iiley
 */
public class BasicPopupMenuUI extends BaseComponentUI implements MenuElementUI{

	protected JPopupMenu popupMenu ;
	
	public  BasicPopupMenuUI (){
		super();
	}
	
	 public void  installUI (Component c ){
		popupMenu = JPopupMenu(c);
		installDefaults();
		installListeners();
	}

	 public void  uninstallUI (Component c ){
		popupMenu = JPopupMenu(c);
		uninstallDefaults();
		uninstallListeners();
	}
	
	protected String  getPropertyPrefix (){
		return "PopupMenu.";
	}

	protected void  installDefaults (){
		String pp =getPropertyPrefix ();
        LookAndFeel.installColorsAndFont(popupMenu, pp);
        LookAndFeel.installBorderAndBFDecorators(popupMenu, pp);
        LookAndFeel.installBasicProperties(popupMenu, pp);
		LayoutManager layout =popupMenu.getLayout ();
		if(layout == null || layout is UIResource){
			popupMenu.setLayout(new DefaultMenuLayout(DefaultMenuLayout.Y_AXIS));
		}
	}
	
	protected void  installListeners (){
	}

	protected void  uninstallDefaults (){
		LookAndFeel.uninstallBorderAndBFDecorators(popupMenu);
	}
	
	protected void  uninstallListeners (){
	}
	
	//-----------------
	
	/**
	 *Subclass  this to process key event .
	 */
	public void  processKeyEvent (int code ){
		MenuSelectionManager manager =MenuSelectionManager.defaultManager ();
		Array path =manager.getSelectedPath ();
		if(path.get(path.length-1) != popupMenu){
			return;
		}
		MenuElement root ;
		MenuElement prev ;
		Array subs ;
		if(manager.isPrevPageKey(code)){
			if(path.length > 2){
				path.pop();
			}
			if(path.length == 2 && !(path[0] is JPopupMenu)){ //generally means jmenubar here
				root = MenuElement(path.get(0));
				prev = manager.prevSubElement(root, MenuElement(path.get(1)));
				path.pop();
				path.push(prev);
				if(prev.getSubElements().length > 0){
					MenuElement prevPop =MenuElement(prev.getSubElements ().get(0) );
					path.push(prevPop);
					if(prevPop.getSubElements().length > 0){
						path.push(prevPop.getSubElements().get(0));
					}
				}
			}else{
				subs = popupMenu.getSubElements();
				if(subs.length > 0){
					path.push(subs.get(subs.length-1));
				}
			}
			manager.setSelectedPath(popupMenu.stage, path, false);
		}else if(manager.isNextPageKey(code)){
			root = MenuElement(path.get(0));
			if(root.getSubElements().length > 1 && !(root is JPopupMenu)){
				MenuElement next =manager.nextSubElement(root ,MenuElement(path.get(1) ));
				path = .get(root);
				path.push(next);
				if(next.getSubElements().length > 0){
					MenuElement nextPop =MenuElement(next.getSubElements ().get(0) );
					path.push(nextPop);
					if(nextPop.getSubElements().length > 0){
						path.push(nextPop.getSubElements().get(0));
					}
				}
			}else{
				subs = popupMenu.getSubElements();
				if(subs.length > 0){
					path.push(subs.get(0));
				}
			}
			manager.setSelectedPath(popupMenu.stage, path, false);
		}else if(manager.isNextItemKey(code)){
			subs = popupMenu.getSubElements();
			if(subs.length > 0){
				if(manager.isPrevItemKey(code)){
					path.push(subs.get(subs.length-1));
				}else{
					path.push(subs.get(0));
				}
			}
			manager.setSelectedPath(popupMenu.stage, path, false);
		}
	}	   
	
	//-----------------
		
	public static MenuElement  getFirstPopup (){
		MenuSelectionManager msm =MenuSelectionManager.defaultManager ();
		Array p =msm.getSelectedPath ();
		MenuElement me =null ;		
	
		for(double i =0;me ==null && i < p.length ; i++) {
			if (p.get(i) is JPopupMenu){
				me = p.get(i);
			}
		}
	
		return me;
	}
	
	public static MenuElement  getLastPopup (){
		MenuSelectionManager msm =MenuSelectionManager.defaultManager ();
		Array p =msm.getSelectedPath ();
		MenuElement me =null ;		
	
		for(double i =p.length -1;me ==null && i >= 0 ; i--) {
			if (p.get(i) is JPopupMenu){
				me = p.get(i);
			}
		}
	
		return me;
	}
	
}


