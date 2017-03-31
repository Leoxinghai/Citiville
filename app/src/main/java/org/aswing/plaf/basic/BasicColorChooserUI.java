/*
 Copyright aswing.org, see the LICENCE.txt.
*/

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


import flash.events.*;

import org.aswing.*;
import org.aswing.border.BevelBorder;
import org.aswing.border.EmptyBorder;
import org.aswing.colorchooser.AbstractColorChooserPanel;
import org.aswing.colorchooser.JColorMixer;
import org.aswing.colorchooser.JColorSwatches;
import org.aswing.colorchooser.PreviewColorIcon;
import org.aswing.event.InteractiveEvent;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.BaseComponentUI;

/**
 * @author iiley
 * @private
 */
public class BasicColorChooserUI extends BaseComponentUI {
	
	private JColorChooser chooser ;
	private JLabel previewColorLabel ;
	private PreviewColorIcon previewColorIcon ;
	
	public  BasicColorChooserUI (){
		super();		
	}

    protected String  getPropertyPrefix (){
        return "ColorChooser.";
    }

     public void  installUI (Component c ){
		chooser = JColorChooser(c);
		installDefaults();
		installComponents();
		installListeners();
    }
    
	 public void  uninstallUI (Component c ){
		chooser = JColorChooser(c);
		uninstallDefaults();
		uninstallComponents();
		uninstallListeners();
    }
	
	private void  installDefaults (){
		String pp =getPropertyPrefix ();
        LookAndFeel.installColorsAndFont(chooser, pp);
		LookAndFeel.installBasicProperties(chooser, pp);
        LookAndFeel.installBorderAndBFDecorators(chooser, pp);
	}
    private void  uninstallDefaults (){
    	LookAndFeel.uninstallBorderAndBFDecorators(chooser);
    }	
    
	private void  installComponents (){
		addChooserPanels();
		previewColorLabel = createPreviewColorLabel();
		previewColorLabel.setUIElement(true);
		previewColorIcon = createPreviewColorIcon();
		previewColorLabel.setIcon(previewColorIcon);
		previewColorIcon.setColor(chooser.getSelectedColor());
		
		layoutComponents();
		updateSectionVisibles();
    }
	private void  uninstallComponents (){
		chooser.removeAllChooserPanels();
    }
        
	private void  installListeners (){
		chooser.addEventListener(InteractiveEvent.STATE_CHANGED, __selectedColorChanged);
	}
    private void  uninstallListeners (){
    	chooser.removeEventListener(InteractiveEvent.STATE_CHANGED, __selectedColorChanged);
    }
    
    //------------------------------------------------------------------------------
    
	private void  __selectedColorChanged (Event e ){
		previewColorIcon.setPreviousColor(previewColorIcon.getCurrentColor());
		previewColorIcon.setCurrentColor(chooser.getSelectedColor());
		previewColorLabel.repaint();
	}

	 public void  paint (Component c ,Graphics2D g ,IntRectangle b ){
		super.paint(c, g, b);
		previewColorIcon.setColor(chooser.getSelectedColor());
		previewColorLabel.repaint();
		updateSectionVisibles();
	}
	private void  updateSectionVisibles (){
		for(double i =0;i <chooser.getChooserPanelCount ();i ++){
			AbstractColorChooserPanel pane =chooser.getChooserPanelAt(i );
			pane.setAlphaSectionVisible(chooser.isAlphaSectionVisible());
			pane.setHexSectionVisible(chooser.isHexSectionVisible());
			pane.setNoColorSectionVisible(chooser.isNoColorSectionVisible());
		}
	}
	//*******************************************************************************
	//       Override these methods to easiy implement different look
	//*******************************************************************************
	
	private void  layoutComponents (){
		chooser.setLayout(new BorderLayout(6, 6));	
		chooser.append(chooser.getTabbedPane(), BorderLayout.CENTER);
		BevelBorder bb =new BevelBorder(new EmptyBorder(null ,new Insets(0,0,2,0)),BevelBorder.LOWERED );
		chooser.getTabbedPane().setBorder(bb);
		
		Container rightPane =SoftBox.createVerticalBox(6,SoftBoxLayout.TOP );
		chooser.getCancelButton().setMargin(new Insets(0, 5, 0, 5));
		rightPane.append(chooser.getOkButton());
		rightPane.append(chooser.getCancelButton());
		rightPane.append(new JLabel("Old"));
		rightPane.append(AsWingUtils.createPaneToHold(previewColorLabel, new CenterLayout()));
		rightPane.append(new JLabel("Current"));
		chooser.append(rightPane, BorderLayout.WEST);
	}
	
    private void  addChooserPanels (){
    	JColorSwatches colorS =new JColorSwatches ();
    	JColorMixer colorM =new JColorMixer ();
    	colorS.setUIElement(true);
    	colorM.setUIElement(true);
    	chooser.addChooserPanel("Color Swatches", colorS);
    	chooser.addChooserPanel("Color Mixer", colorM);
    }
    
	private PreviewColorIcon  createPreviewColorIcon (){
		return new PreviewColorIcon(60, 60, PreviewColorIcon.VERTICAL);
	}
	
	private JLabel  createPreviewColorLabel (){
		JLabel label =new JLabel ();
		BevelBorder bb =new BevelBorder(null ,BevelBorder.LOWERED );
		bb.setThickness(1);
		label.setBorder(bb); 
		return label;
	}
}


