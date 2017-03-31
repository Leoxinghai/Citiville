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


import org.aswing.colorchooser.AbstractColorChooserPanel;
import org.aswing.geom.*;
import org.aswing.event.*;
import org.aswing.plaf.basic.BasicColorChooserUI;

/**
 * JColorChooser provides a pane of controls designed to allow a user to manipulate and 
 * select a color. 
 * <p>
 * You can add your color chooser pane to the chooser panels' tabbedPane container, or 
 * remove defaults.
 * @author iiley
 */
public class JColorChooser extends AbstractColorChooserPanel {
	
	private Array chooserPanels ;
	private JTabbedPane tabbedPane ;
	private JButton okButton ;
	private JButton cancelButton ;
	
	public  JColorChooser (){
		super();
		chooserPanels = new Array();
		tabbedPane    = new JTabbedPane();
		okButton      = new JButton("OK");
		cancelButton  = new JButton("Cancel");
		updateUI();
	}
	
	 public void  updateUI (){
		setUI(UIManager.getUI(this));
	}
	
     public Class  getDefaultBasicUIClass (){
    	return org.aswing.plaf.basic.BasicColorChooserUI;
    }
	
	 public String  getUIClassID (){
		return "ColorChooserUI";
	}
	
	/**
	 * Returns the tabbedPane which contains the color chooser panels.
	 * @return the tabbedPane 
	 */
	public JTabbedPane  getTabbedPane (){
		return tabbedPane;
	}
	
	/**
	 * Returns the ok button to finish the choosing.
	 * @return the ok button
	 */
	public JButton  getOkButton (){
		return okButton;
	}
	
	/**
	 * Returns the cancel button which use to cancel the choosing.
	 * @return the cancel button
	 */
	public JButton  getCancelButton (){
		return cancelButton;
	}
	
	/**
	 * Adds a chooser panel to the tabbedPane.
	 * <p>
	 * By default, the LAFs implements will add default chooser panels.
	 * @param tabTitle the tab title
	 * @param panel the chooser panel
	 * @see #removeChooserPanel()
	 */
	public void  addChooserPanel (String tabTitle ,AbstractColorChooserPanel panel ){
		getTabbedPane().appendTab(panel, tabTitle);
		panel.setModel(getModel());
	}
	
	/**
	 * Removes a chooser panel from the tabbedPane.
	 * @param panel the chooser panel to be removed
	 * @return the removed chooser panel, null means the panel is not in the tabbedPane.
	 * @see #addChooserPanel()
	 */
	public AbstractColorChooserPanel  removeChooserPanel (AbstractColorChooserPanel panel ){
		return AbstractColorChooserPanel(getTabbedPane().remove(panel));
	}
	
	/**
	 * Removes a chooser panel by index from the tabbedPane.
	 * @param index the index of the chooser panel to be removed
	 * @return the removed chooser panel, null means no such index exists.
	 * @see #addChooserPanel()
	 */
	public AbstractColorChooserPanel  removeChooserPanelAt (double index ){
		return AbstractColorChooserPanel(getTabbedPane().removeAt(index));
	}
	
	/**
	 * Removes all color chooser panels.
	 */
	public void  removeAllChooserPanels (){
		getTabbedPane().removeAll();
	}
	
	/**
	 * Gets a chooser panel by index from the tabbedPane.
	 * @param index the index of the chooser panel
	 * @return the chooser panel, null means no such index exists.
	 * @see #addChooserPanel()
	 */
	public AbstractColorChooserPanel  getChooserPanelAt (double index ){
		return AbstractColorChooserPanel(getTabbedPane().getComponent(index));
	}
	
	/**
	 * Returns the count of chooser panels.
	 * @return the count of chooser panels.
	 */
	public double  getChooserPanelCount (){
		return getTabbedPane().getComponentCount();
	}
	
	/**
	 * Shows a modal color-chooser dialog to let user to choose a color. 
	 * If the user presses the "OK" button, then the dialog will be disposed
	 * and invoke the <code>okHandler(selectedColor:ASColor)</code>. 
	 * If the user presses the "Cancel" button or closes 
	 * the dialog without pressing "OK", then the dialog will be disposed and 
	 * invoke the <code>cancelHandler()</code>.
	 * <p>
	 * <code>selectedColor</code> may be null, null means user selected no-color.
	 * <p>
	 * This method always create new Dialog and new JColorChooser, so its openning 
	 * may performance slowly.
	 * @param component determines the Frame in which the
	 *		dialog is displayed; can be null
	 * @param title the title of the box
	 * @param initialColor the initial color when start choosing
	 * @param (optional)modal is the window modal, default is true
	 * @param (optional)okHandler the function will be called when user finished the choosing with a specified color.
	 *the selected color will be passed to the  as a parammeter .
	 * @param (optional)cancelHandler the function will be called when user canceled the choosing
	 * @param (optional)location the location of the dialog to show, default is the center of the screen
	 * @return the color chooser
	 */
	public static  showDialog (Component component ,String title ,ASColor initialColor ,boolean modal =true ,
										okHandler:Function=null, cancelHandler:Function=null, location:IntPoint=null):JColorChooser{
		JColorChooser chooser =new JColorChooser ();
		chooser.setSelectedColor(initialColor);
		JFrame frame =createDialog(chooser ,
				component, title, modal, 
				okHandler, cancelHandler
		);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if(location == null){
			location = AsWingUtils.getScreenCenterPosition();
		}
		location.x = Math.round(location.x - frame.getWidth()/2);
		location.y = Math.round(location.y - frame.getHeight()/2);
		frame.setLocation(location);
		frame.show();
		return chooser;
	}
	
	/**
	 * Creates a dialog to be the color chooser dialog. 
	 * If the user presses the "OK" button, then the dialog will be disposed
	 * and invoke the <code>okHandler(selectedColor:ASColor)</code>. 
	 * If the user presses the "Cancel" button or closes 
	 * the dialog without pressing "OK", then the dialog will be disposed and 
	 * invoke the <code>cancelHandler()</code>.
	 * <p>
	 * <code>selectedColor</code> may be null, null means user selected no-color.
	 * <p>
	 * This dialog will be default to HIDE_ON_CLOSE, so it can be opened very fast 
	 * after first openning.
	 * @param chooser the color chooser
	 * @param component determines the Frame in which the
	 *		dialog is displayed; can be null
	 * @param title the title of the box
	 * @param (optional)modal is the window modal, default is true
	 * @param (optional)okHandler the function will be called when user finished the choosing with a specified color.
	 *the selected color will be passed to the  as a parammeter .
	 * @param (optional)cancelHandler the function will be called when user canceled the choosing
	 * @return the chooser dialog
	 */	
	public static  createDialog (JColorChooser chooser ,Component component ,String title ,boolean modal =true ,
										okHandler:Function=null, cancelHandler:Function=null):JFrame{
		JFrame frame =new JFrame(AsWingUtils.getOwnerAncestor(component ),title ,modal );
		
		frame.setContentPane(chooser);
		frame.setResizable(false);
		
		chooser .getOkButton ().addActionListener (void  (){
			if(okHandler != null) okHandler(chooser.getSelectedColor());
			frame.tryToClose();
		});
		chooser .getCancelButton ().addActionListener (void  (){
			frame.tryToClose();
			if(cancelHandler != null) cancelHandler();
		});
		frame .addEventListener (FrameEvent .FRAME_CLOSING ,void  (){
			if(cancelHandler != null) cancelHandler();
		});
		frame.pack();
		return frame;
	}
}


