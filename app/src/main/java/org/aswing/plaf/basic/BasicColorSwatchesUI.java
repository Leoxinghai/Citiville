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


import flash.display.MovieClip;
import flash.events.Event;
import flash.events.MouseEvent;
import flash.events.TextEvent;
import flash.text.TextField;
import flash.ui.Mouse;

import org.aswing.*;
import org.aswing.border.BevelBorder;
import org.aswing.colorchooser.*;
import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.*;

/**
 * @private
 */
public class BasicColorSwatchesUI extends BaseComponentUI implements ColorSwatchesUI{
	
	private JColorSwatches colorSwatches ;
	private JLabel selectedColorLabel ;
	private ColorRectIcon selectedColorIcon ;
	private JTextField colorHexText ;
	private JAdjuster alphaAdjuster ;
	private AbstractButton noColorButton ;
	private JPanel colorTilesPane ;
	private Container topBar ;
	private Container barLeft ;
	private Container barRight ;
	private AWSprite selectionRectMC ;
	
	public  BasicColorSwatchesUI (){
		super();		
	}
    
    protected String  getPropertyPrefix (){
    	return "ColorSwatches.";
    }

     public void  installUI (Component c ){
		colorSwatches = JColorSwatches(c);
		installDefaults();
		installComponents();
		installListeners();
    }
    
	 public void  uninstallUI (Component c ){
		colorSwatches = JColorSwatches(c);
		uninstallDefaults();
		uninstallComponents();
		uninstallListeners();
    }
	
	private void  installDefaults (){
		String pp =getPropertyPrefix ();
        LookAndFeel.installColorsAndFont(colorSwatches, pp);
		LookAndFeel.installBasicProperties(colorSwatches, pp);
        LookAndFeel.installBorderAndBFDecorators(colorSwatches, pp);
	}
    private void  uninstallDefaults (){
    	LookAndFeel.uninstallBorderAndBFDecorators(colorSwatches);
    }	
    
	private void  installComponents (){
		selectedColorLabel = createSelectedColorLabel();
		selectedColorIcon = createSelectedColorIcon();
		selectedColorLabel.setIcon(selectedColorIcon);
		
		colorHexText = createHexText();
		alphaAdjuster = createAlphaAdjuster();
		noColorButton = createNoColorButton();
		colorTilesPane = createColorTilesPane();
		
		topBar = new JPanel(new BorderLayout());
		barLeft = SoftBox.createHorizontalBox(2, SoftBoxLayout.LEFT);
		barRight = SoftBox.createHorizontalBox(2, SoftBoxLayout.RIGHT);
		topBar.append(barLeft, BorderLayout.WEST);
		topBar.append(barRight, BorderLayout.EAST);
		
		barLeft.append(selectedColorLabel);
		barLeft.append(colorHexText);
		barRight.append(alphaAdjuster);
		barRight.append(noColorButton);
		
		topBar.setUIElement(true);
		colorTilesPane.setUIElement(true);
		
		colorSwatches.setLayout(new BorderLayout(4, 4));
		colorSwatches.append(topBar, BorderLayout.NORTH);
		colorSwatches.append(colorTilesPane, BorderLayout.CENTER);
		createTitles();
		updateSectionVisibles();
    }
	private void  uninstallComponents (){
		colorSwatches.remove(topBar);
		colorSwatches.remove(colorTilesPane);
    }
        
	private void  installListeners (){
		noColorButton.addActionListener(__noColorButtonAction);

		colorSwatches.addEventListener(InteractiveEvent.STATE_CHANGED, __colorSelectionChanged);
		colorSwatches.addEventListener(AWEvent.HIDDEN, __colorSwatchesUnShown);
		
		colorTilesPane.addEventListener(MouseEvent.ROLL_OVER, __colorTilesPaneRollOver);
		colorTilesPane.addEventListener(MouseEvent.ROLL_OUT, __colorTilesPaneRollOut);
		colorTilesPane.addEventListener(DragAndDropEvent.DRAG_EXIT, __colorTilesPaneRollOut);
		colorTilesPane.addEventListener(MouseEvent.MOUSE_UP, __colorTilesPaneReleased);
		
		colorHexText.addActionListener(__hexTextAction);
		colorHexText.getTextField().addEventListener(Event.CHANGE, __hexTextChanged);
		
		alphaAdjuster.addStateListener(__adjusterValueChanged);
		alphaAdjuster.addActionListener(__adjusterAction);
	}
    private void  uninstallListeners (){
    	noColorButton.removeActionListener(__noColorButtonAction);
    	
    	colorSwatches.removeEventListener(InteractiveEvent.STATE_CHANGED, __colorSelectionChanged);
    	colorSwatches.removeEventListener(AWEvent.HIDDEN, __colorSwatchesUnShown);
    	colorSwatches.removeEventListener(MouseEvent.MOUSE_MOVE, __colorTilesPaneMouseMove); 
    	
		colorTilesPane.removeEventListener(MouseEvent.ROLL_OVER, __colorTilesPaneRollOver);
		colorTilesPane.removeEventListener(MouseEvent.ROLL_OUT, __colorTilesPaneRollOut);
		colorTilesPane.removeEventListener(DragAndDropEvent.DRAG_EXIT, __colorTilesPaneRollOut);
		colorTilesPane.removeEventListener(MouseEvent.MOUSE_UP, __colorTilesPaneReleased);   
		
		colorHexText.removeActionListener(__hexTextAction);
		colorHexText.getTextField().removeEventListener(Event.CHANGE, __hexTextChanged);	
		
		alphaAdjuster.removeStateListener(__adjusterValueChanged);
		alphaAdjuster.removeActionListener(__adjusterAction);		
    }
    
    //------------------------------------------------------------------------------
    private void  __adjusterValueChanged (Event e ){
		updateSelectedColorLabelColor(getColorFromHexTextAndAdjuster());
    }
    private void  __adjusterAction (Event e ){
    	colorSwatches.setSelectedColor(getColorFromHexTextAndAdjuster());
    }
    
    private void  __hexTextChanged (Event e ){
		updateSelectedColorLabelColor(getColorFromHexTextAndAdjuster());
    }
    private void  __hexTextAction (Event e ){
    	colorSwatches.setSelectedColor(getColorFromHexTextAndAdjuster());
    }
    
    private void  __colorTilesPaneRollOver (Event e ){
    	colorSwatches.removeEventListener(MouseEvent.MOUSE_MOVE, __colorTilesPaneMouseMove);
    	colorSwatches.addEventListener(MouseEvent.MOUSE_MOVE, __colorTilesPaneMouseMove);
    	
    }
    private void  __colorTilesPaneRollOut (Event e ){
    	stopMouseMovingSelection();
    }
    private boolean lastOutMoving ;
    private void  __colorTilesPaneMouseMove (Event e ){
    	IntPoint p =colorTilesPane.getMousePosition ();
    	ASColor color =getColorWithPosAtColorTilesPane(p );
    	if(color != null){
    		IntPoint sp =getSelectionRectPos(p );
    		selectionRectMC.visible = true;
    		selectionRectMC.x = sp.x;
    		selectionRectMC.y = sp.y;
			updateSelectedColorLabelColor(color);
			fillHexTextWithColor(color);
    		lastOutMoving = false;
    		//updateAfterEvent();
    	}else{
    		color = colorSwatches.getSelectedColor();
    		selectionRectMC.visible = false;
    		if(lastOutMoving != true){
				updateSelectedColorLabelColor(color);
				fillHexTextWithColor(color);
    		}
    		lastOutMoving = true;
    	}
    }
    private void  __colorTilesPaneReleased (MouseEvent e ){
    	IntPoint p =new IntPoint(e.localX ,e.localY );//colorTilesPane.getMousePosition ();
    	ASColor color =getColorWithPosAtColorTilesPane(p );
    	if(color != null){
    		colorSwatches.setSelectedColor(color);
    	}
    }
    
    private void  __noColorButtonAction (AWEvent e ){
    	colorSwatches.setSelectedColor(null);
    }
    
    private AWSprite colorTilesMC ;
	private void  createTitles (){
		colorTilesMC = new AWSprite();
		selectionRectMC = new AWSprite();
		colorTilesPane.addChild(colorTilesMC);
		colorTilesPane.addChild(selectionRectMC);
		paintColorTiles();
		paintSelectionRect();
		selectionRectMC.visible = false;
	}
	
	private void  __colorSelectionChanged (Event e ){
		ASColor color =colorSwatches.getSelectedColor ();
		fillHexTextWithColor(color);
		fillAlphaAdjusterWithColor(color);
		updateSelectedColorLabelColor(color);
	}
	private void  __colorSwatchesUnShown (Event e ){
		stopMouseMovingSelection();
	}
	private void  stopMouseMovingSelection (){
		colorSwatches.removeEventListener(MouseEvent.MOUSE_MOVE, __colorTilesPaneMouseMove);
		selectionRectMC.visible = false;
		ASColor color =colorSwatches.getSelectedColor ();
		updateSelectedColorLabelColor(color);
		fillHexTextWithColor(color);
	}
	
	//-----------------------------------------------------------------------
	 public void  paint (Component c ,Graphics2D g ,IntRectangle b ){
		super.paint(c, g, b);
		updateSectionVisibles();
		updateSelectedColorLabelColor(colorSwatches.getSelectedColor());
		fillHexTextWithColor(colorSwatches.getSelectedColor());
	}
	private void  updateSectionVisibles (){
		colorHexText.setVisible(colorSwatches.isHexSectionVisible());
		alphaAdjuster.setVisible(colorSwatches.isAlphaSectionVisible());
		noColorButton.setVisible(colorSwatches.isNoColorSectionVisible());
	}
    
	//*******************************************************************************
	//      Data caculating methods
	//******************************************************************************
    private ASColor  getColorFromHexTextAndAdjuster (){
    	String text =colorHexText.getText ();
    	if(text.charAt(0) == "#"){
    		text = text.substr(1);
    	}
    	double rgb =parseInt("0x"+text );
    	return new ASColor(rgb, alphaAdjuster.getValue()/100);
    }
    private ASColor hexTextColor ;
    private void  fillHexTextWithColor (ASColor color ){
    	if (color == null){
    		 hexTextColor = color;
	    	colorHexText.setText("#000000");
    	}else if(!color.equals(hexTextColor)){
	    	hexTextColor = color;
	    	String hex ;
	    	hex = color.getRGB().toString(16);
	    	for(double i =6-hex.length ;i >0;i --){
	    		hex = "0" + hex;
	    	}
	    	hex = "#" + hex.toUpperCase();
	    	colorHexText.setText(hex);
    	}
    }
    private void  fillAlphaAdjusterWithColor (ASColor color ){
    	double alpha =(color ==null ? 100 : color.getAlpha());
		alphaAdjuster.setValue(alpha*100);
    }
    
    private boolean  isEqualsToSelectedIconColor (ASColor color ){
		if(color == null){
			return selectedColorIcon.getColor() == null;
		}else{
			return color.equals(selectedColorIcon.getColor());
		}
	}
    private void  updateSelectedColorLabelColor (ASColor color ){
    	if(!isEqualsToSelectedIconColor(color)){
	    	selectedColorIcon.setColor(color);
	    	selectedColorLabel.repaint();
	    	colorSwatches.getModel().fireColorAdjusting(color);
    	}
    }
    private IntPoint  getSelectionRectPos (IntPoint p ){
    	double L =getTileL ();
    	return new IntPoint(Math.floor(p.x/L)*L, Math.floor(p.y/L)*L);
    }
    //if null returned means not in color tiles bounds
    private ASColor  getColorWithPosAtColorTilesPane (IntPoint p ){
    	double L =getTileL ();
    	IntDimension size =getColorTilesPaneSize ();
    	if(p.x < 0 || p.y < 0 || p.x >= size.width || p.y >= size.height){
    		return null;
    	}
    	double alpha =alphaAdjuster.getValue ()/100;
    	if(p.x < L){
    		double index =Math.floor(p.y /L );
    		index = Math.max(0, Math.min(11, index));
    		return new ASColor(getLeftColumColors().get(index), alpha);
    	}
    	if(p.x < L*2){
    		return new ASColor(0x000000, alpha);
    	}
    	double x =p.x -L *2;
    	double y =p.y ;
    	double bigTile =(L *6);
    	double tx =Math.floor(x /bigTile );
    	double ty =Math.floor(y /bigTile );
    	double ti =ty *3+tx ;
    	double xi =Math.floor ((x -tx *bigTile )/L );
    	double yi =Math.floor ((y -ty *bigTile )/L );
    	return getTileColorByTXY(ti, xi, yi, alpha);
    }
    private Array  getLeftColumColors (){
    	return [0x000000, 0x333333, 0x666666, 0x999999, 0xCCCCCC, 0xFFFFFF, 
							  0xFF0000, 0x00FF00, 0x0000FF, 0xFFFF00, 0x00FFFF, 0xFF00FF];
    }
    private ASColor  getTileColorByTXY (double t ,double x ,double y ,double alpha =1){
		double rr =0x33 *t ;
		double gg =0x33 *x ;
		double bb =0x33 *y ;
		ASColor c =ASColor.getASColor(rr ,gg ,bb ,alpha );
		return c;
    }
	private void  paintColorTiles (){
		Graphics2D g =new Graphics2D(colorTilesMC.graphics );	
		double startX =0;
		double startY =0;
		double L =getTileL ();
		Array leftLine =getLeftColumColors ();
		for(double y =0;y <6*2;y ++){
			fillRect(g, startX, startY+y*L, new ASColor(leftLine.get(y)));
		}
		startX += L;
		for(double y2 =0;y2 <6*2;y2 ++){
			fillRect(g, startX, startY+y2*L, ASColor.BLACK);
		}
		startX += L;		
		
		for(double t =0;t <6;t ++){
			for(double x =0;x <6;x ++){
				for(double y3 =0;y3 <6;y3 ++){
					ASColor c =getTileColorByTXY(t ,x ,y3 );
					fillRect(g, 
							 startX + (t%3)*(6*L) + x*L, 
							 startY + Math.floor(t/3)*(6*L) + y3*L, 
							 c);
				}
			}
		}
	}
	private void  paintSelectionRect (){
		Graphics2D g =new Graphics2D(selectionRectMC.graphics );
		g.drawRectangle(new Pen(ASColor.WHITE, 0), 0, 0, getTileL(), getTileL());
	}
	
	private void  fillRect (Graphics2D g ,double x ,double y ,ASColor c ){
		g.beginDraw(new Pen(ASColor.BLACK, 0));
		g.beginFill(new SolidBrush(c));
		g.rectangle(x, y, getTileL(), getTileL());
		g.endFill();
		g.endDraw();
	}
	private IntDimension  getColorTilesPaneSize (){
		return new IntDimension((3*6+2)*getTileL(), (2*6)*getTileL());
	}
	
	private double  getTileL (){
		return 12;
	}
    
	//*******************************************************************************
	//              Override these methods to easiy implement different look
	//******************************************************************************
	public void  addComponentColorSectionBar (Component com ){
		barRight.append(com);
	}	
	
	private JLabel  createSelectedColorLabel (){
		JLabel label =new JLabel ();
		BevelBorder bb =new BevelBorder(null ,BevelBorder.LOWERED );
		bb.setThickness(1);
		label.setBorder(bb); 
		return label;
	}
	
	private ColorRectIcon  createSelectedColorIcon (){
		return new ColorRectIcon(38, 18, colorSwatches.getSelectedColor());
	}
	
	private JTextField  createHexText (){
		return new JTextField("#FFFFFF", 6);
	}
	
	private JAdjuster  createAlphaAdjuster (){
		JAdjuster adjuster =new JAdjuster(4,JAdjuster.VERTICAL );
		adjuster.setValueTranslator(
			String  (double value ){
				return Math.round(value) + "%";
			});
		adjuster.setValues(100, 0, 0, 100);
		return adjuster;
	}
	private AbstractButton  createNoColorButton (){
		return new JButton("", new NoColorIcon(16, 16));
	}
	private JPanel  createColorTilesPane (){
		JPanel p =new JPanel ();
		p.setBorder(null); //ensure there is no border there
    	IntDimension size =getColorTilesPaneSize ();
    	size.change(1, 1);
		p.setPreferredSize(size);
		p.mouseChildren = false;
		return p;
	}
}


