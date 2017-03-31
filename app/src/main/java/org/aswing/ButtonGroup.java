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


import org.aswing.util.ArrayUtils;

/**
 * This class is used to create a multiple-exclusion scope for a set of buttons. 
 * Creating a set of buttons with the same ButtonGroup object means that turning "on" 
 * one of those buttons turns off all other buttons in the group. 
 * <p>
 * A ButtonGroup can be used with any set of objects that inherit from AbstractButton. 
 * Typically a button group contains instances of JRadioButton, JRadioButtonMenuItem, 
 * or JToggleButton. It wouldn't make sense to put an instance of JButton or JMenuItem in a button group because JButton and JMenuItem don't implement the selected state. 
 * </p>
 * <p>
 * Initially, all buttons in the group are unselected. Once any button is selected, one button is always selected in the group. There is no way to turn a button programmatically to "off", in order to clear the button group. To give the appearance of "none selected", add an invisible radio button to the group and then programmatically select that button to turn off all the displayed radio buttons. For example, a normal button with the label "none" could be wired to select the invisible radio button. 
 * </p>
 * @author iiley
 */
public class ButtonGroup
    // the list of buttons participating in this group
    protected Array buttons ;

    /**
	 * The current selection.
	 */
    private ButtonModel selection =null ;

    /**
	 * Creates a new <code>ButtonGroup</code>.
	 */
    public  ButtonGroup (){
    	buttons = new Array();
    }
    
    /**
     * Create a button group and append the buttons in, then return the group.
     * @return the button group.
     */
    public static ButtonGroup  groupButtons (...buttons ){
    	ButtonGroup g =new ButtonGroup ();
    	for(int i0 = 0; i0 < buttons .size(); i0++) 
    	{
    			i = buttons .get(i0);
    	}
    	return g;
    }

    /**
	 * Adds the button to the group.
	 * 
	 * @param b the button to be added
	 */ 
    public void  append (AbstractButton b ){
        if(b == null) {
            return;
        }
        buttons.push(b);

        if (b.isSelected()) {
            if (selection == null) {
                selection = b.getModel();
            } else {
                b.setSelected(false);
            }
        }

        b.getModel().setGroup(this);
    }
 	
 	/**
 	 * Appends all buttons into this group.
 	 */
 	public void  appendAll (...buttons ){
    	for(int i0 = 0; i0 < buttons .size(); i0++) 
    	{
    			i = buttons .get(i0);
    	}
 	}
 	
    /**
	 * Removes the button from the group.
	 * 
	 * @param b the button to be removed
	 */ 
    public void  remove (AbstractButton b ){
        if(b == null) {
            return;
        }
        ArrayUtils.removeFromArray(buttons, b);
        if(b.getModel() == selection) {
            selection = null;
        }
        b.getModel().setGroup(null);
    }
    
    /**
     * Returns whether the group contains the button.
     * @return true if the group contains the button, false otherwise
     */
    public boolean  contains (AbstractButton b ){
    	for(double i =0;i <buttons.length ;i ++){
    		if(buttons.get(i) == b){
    			return true;
    		}
    	}
    	return false;
    }

    /**
	 * Returns all the buttons that are participating in this group.
	 * 
	 * @return an <code>Array</code> of the buttons in this group
	 */
    public Array  getElements (){
        return ArrayUtils.cloneArray(buttons);
    }

    /**
	 * Returns the model of the selected button.
	 * 
	 * @return the selected button model
	 */
    public ButtonModel  getSelection (){
        return selection;
    }
    
    /**
     * Return the first selected button, if none, return null.
     */
    public AbstractButton  getSelectedButton (){
    	for(int i0 = 0; i0 < buttons .size(); i0++) 
    	{
    			b = buttons .get(i0);
    			return b;
    		}
    	}
    	return null;
    }

    /**
	 * Sets the selected value for the <code>ButtonModel</code>. Only one
	 * button in the group may be selected at a time.
	 * 
	 * @param m the <code>ButtonModel</code>
	 * @param b <code>true</code> if this button is to be selected,
	 *            otherwise <code>false</code>
	 */
    public void  setSelected (ButtonModel m ,boolean b ){
        if (b && m != null && m != selection) {
            ButtonModel oldSelection =selection ;
            selection = m;
            if (oldSelection != null) {
                oldSelection.setSelected(false);
            }
            m.setSelected(true);
        }
    }

    /**
	 * Returns whether a <code>ButtonModel</code> is selected.
	 * 
	 * @return <code>true</code> if the button is selected, otherwise returns
	 *         <code>false</code>
	 */
    public boolean  isSelected (ButtonModel m ){
        return (m == selection);
    }

    /**
	 * Returns the number of buttons in the group.
	 * 
	 * @return the button count
	 */
    public double  getButtonCount (){
    	return buttons.length;
    }

}


