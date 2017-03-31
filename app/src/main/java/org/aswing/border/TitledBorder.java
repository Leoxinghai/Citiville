
package org.aswing.border;

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

	
import flash.display.*;
import flash.text.*;

import org.aswing.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.util.HashMap;

/**
 * TitledBorder, a border with a line rectangle and a title text.
 * @author iiley
 */	
public class TitledBorder extends DecorateBorder{
		
	public static ASFont  DEFAULT_FONT (){
		return UIManager.getFont("systemFont");
	}
	public static ASColor  DEFAULT_COLOR (){
		return ASColor.BLACK;
	}
	public static ASColor  DEFAULT_LINE_COLOR (){
		return ASColor.GRAY;
	}
	public static ASColor  DEFAULT_LINE_LIGHT_COLOR (){
		return ASColor.WHITE;
	}
	public static  int DEFAULT_LINE_THICKNESS =1;
		
	public static  int TOP =AsWingConstants.TOP ;
	public static  int BOTTOM =AsWingConstants.BOTTOM ;
	
	public static  int CENTER =AsWingConstants.CENTER ;
	public static  int LEFT =AsWingConstants.LEFT ;
	public static  int RIGHT =AsWingConstants.RIGHT ;
	

    // Space between the text and the line end
    public static int GAP =1;	
	
	private String title ;
	private int position ;
	private int align ;
	private double edge ;
	private double round ;
	private ASFont font ;
	private ASColor color ;
	private ASColor lineColor ;
	private ASColor lineLightColor ;
	private double lineThickness ;
	private boolean beveled ;
	private TextField textField ;
	private IntDimension textFieldSize ;
	
	/**
	 * Create a titled border.
	 * @param title the title text string.
	 * @param position the position of the title(TOP or BOTTOM), default is TOP
	 * @param align the align of the title(CENTER or LEFT or RIGHT), default is CENTER
	 * @param edge the edge space of title position, defaut is 0.
	 * @param round round rect radius, default is 0 means normal rectangle, not rect.
	 * @see org.aswing.border.SimpleTitledBorder
	 * @see #setColor()
	 * @see #setLineColor()
	 * @see #setFont()
	 * @see #setLineThickness()
	 * @see #setBeveled()
	 */
	public  TitledBorder (Border interior =null ,String title ="",int position =AsWingConstants .TOP ,int align =CENTER ,double edge =0,double round =0){
		super(interior);
		this.title = title;
		this.position = position;
		this.align = align;;
		this.edge = edge;
		this.round = round;
		
		font = DEFAULT_FONT;
		color = DEFAULT_COLOR;
		lineColor = DEFAULT_LINE_COLOR;
		lineLightColor = DEFAULT_LINE_LIGHT_COLOR;
		lineThickness = DEFAULT_LINE_THICKNESS;
		beveled = true;
		textField = null;
		textFieldSize = null;
	}
	
	private TextField  getTextField (){
    	if(textField == null){
	    	textField = new TextField();
	    	textField.selectable = false;
	    	textField.autoSize = TextFieldAutoSize.CENTER;
    	}
    	return textField;
	}
	
	 public void  updateBorderImp (Component c ,Graphics2D g ,IntRectangle bounds ){
    	double textHeight =Math.ceil(getTextFieldSize ().height );
    	double x1 =bounds.x +lineThickness *0.5;
    	double y1 =bounds.y +lineThickness *0.5;
    	if(position == TOP){
    		y1 += textHeight/2;
    	}
    	double w =bounds.width -lineThickness ;
    	double h =bounds.height -lineThickness -textHeight /2;
    	if(beveled){
    		w -= lineThickness;
    		h -= lineThickness;
    	}
    	double x2 =x1 +w ;
    	double y2 =y1 +h ;
    	
    	IntRectangle textR =new IntRectangle ();
    	IntRectangle viewR =new IntRectangle(bounds.x ,bounds.y ,bounds.width ,bounds.height );
    	String text =title ;
        double verticalAlignment =position ;
        double horizontalAlignment =align ;
    	
    	Pen pen =new Pen(lineColor ,lineThickness );
    	if(round <= 0){
    		if(bounds.width <= edge*2){
    			g.drawRectangle(pen, x1, y1, w, h);
    			if(beveled){
    				pen.setColor(lineLightColor);
    				g.beginDraw(pen);
    				g.moveTo(x1+lineThickness, y2-lineThickness);
    				g.lineTo(x1+lineThickness, y1+lineThickness);
    				g.lineTo(x2-lineThickness, y1+lineThickness);
    				g.moveTo(x2+lineThickness, y1);
    				g.lineTo(x2+lineThickness, y2+lineThickness);
    				g.lineTo(x1, y2+lineThickness);
    			}
    			textField.text="";
    		}else{
    			viewR.x += edge;
    			viewR.width -= edge*2;
    			text = AsWingUtils.layoutText(font, text, verticalAlignment, horizontalAlignment, viewR, textR);
    			//draw dark rect
    			g.beginDraw(pen);
    			if(position == TOP){
	    			g.moveTo(textR.x - GAP, y1);
	    			g.lineTo(x1, y1);
	    			g.lineTo(x1, y2);
	    			g.lineTo(x2, y2);
	    			g.lineTo(x2, y1);
	    			g.lineTo(textR.x + textR.width+GAP, y1);
	    				    			
    			}else{
	    			g.moveTo(textR.x - GAP, y2);
	    			g.lineTo(x1, y2);
	    			g.lineTo(x1, y1);
	    			g.lineTo(x2, y1);
	    			g.lineTo(x2, y2);
	    			g.lineTo(textR.x + textR.width+GAP, y2);
    			}
    			g.endDraw();
    			if(beveled){
	    			//draw hightlight
	    			pen.setColor(lineLightColor);
	    			g.beginDraw(pen);
	    			if(position == TOP){
		    			g.moveTo(textR.x - GAP, y1+lineThickness);
		    			g.lineTo(x1+lineThickness, y1+lineThickness);
		    			g.lineTo(x1+lineThickness, y2-lineThickness);
		    			g.moveTo(x1, y2+lineThickness);
		    			g.lineTo(x2+lineThickness, y2+lineThickness);
		    			g.lineTo(x2+lineThickness, y1);
		    			g.moveTo(x2-lineThickness, y1+lineThickness);
		    			g.lineTo(textR.x + textR.width+GAP, y1+lineThickness);
		    				    			
	    			}else{
		    			g.moveTo(textR.x - GAP, y2+lineThickness);
		    			g.lineTo(x1, y2+lineThickness);
		    			g.moveTo(x1+lineThickness, y2-lineThickness);
		    			g.lineTo(x1+lineThickness, y1+lineThickness);
		    			g.lineTo(x2-lineThickness, y1+lineThickness);
		    			g.moveTo(x2+lineThickness, y1);
		    			g.lineTo(x2+lineThickness, y2+lineThickness);
		    			g.lineTo(textR.x + textR.width+GAP, y2+lineThickness);
	    			}
	    			g.endDraw();
    			}
    		}
    	}else{
    		if(bounds.width <= (edge*2 + round*2)){
    			if(beveled){
    				g.drawRoundRect(new Pen(lineLightColor, lineThickness), 
    							x1+lineThickness, y1+lineThickness, w, h, 
    							Math.min(round, Math.min(w/2, h/2)));
    			}
    			g.drawRoundRect(pen, x1, y1, w, h, 
    							Math.min(round, Math.min(w/2, h/2)));
    			textField.text="";
    		}else{
    			viewR.x += (edge+round);
    			viewR.width -= (edge+round)*2;
    			text = AsWingUtils.layoutText(font, text, verticalAlignment, horizontalAlignment, viewR, textR);
				double r =round ;

    			if(beveled){
    				pen.setColor(lineLightColor);
	    			g.beginDraw(pen);
	    			double t =lineThickness ;
    				x1+=t;
    				x2+=t;
    				y1+=t;
    				y2+=t;
	    			if(position == TOP){
			    		g.moveTo(textR.x - GAP, y1);
						//Top left
						g.lineTo (x1+r, y1);
						g.curveTo(x1, y1, x1, y1+r);
						//Bottom left
						g.lineTo (x1, y2-r );
						g.curveTo(x1, y2, x1+r, y2);
						//bottom right
						g.lineTo(x2-r, y2);
						g.curveTo(x2, y2, x2, y2-r);
						//Top right
						g.lineTo (x2, y1+r);
						g.curveTo(x2, y1, x2-r, y1);
						g.lineTo(textR.x + textR.width+GAP, y1);
	    			}else{
			    		g.moveTo(textR.x + textR.width+GAP, y2);
						//bottom right
						g.lineTo(x2-r, y2);
						g.curveTo(x2, y2, x2, y2-r);
						//Top right
						g.lineTo (x2, y1+r);
						g.curveTo(x2, y1, x2-r, y1);
						//Top left
						g.lineTo (x1+r, y1);
						g.curveTo(x1, y1, x1, y1+r);
						//Bottom left
						g.lineTo (x1, y2-r );
						g.curveTo(x1, y2, x1+r, y2);
						g.lineTo(textR.x - GAP, y2);
	    			}
	    			g.endDraw();  
    				x1-=t;
    				x2-=t;
    				y1-=t;
    				y2-=t;  				
    			}		
    			pen.setColor(lineColor);		
    			g.beginDraw(pen);
    			if(position == TOP){
		    		g.moveTo(textR.x - GAP, y1);
					//Top left
					g.lineTo (x1+r, y1);
					g.curveTo(x1, y1, x1, y1+r);
					//Bottom left
					g.lineTo (x1, y2-r );
					g.curveTo(x1, y2, x1+r, y2);
					//bottom right
					g.lineTo(x2-r, y2);
					g.curveTo(x2, y2, x2, y2-r);
					//Top right
					g.lineTo (x2, y1+r);
					g.curveTo(x2, y1, x2-r, y1);
					g.lineTo(textR.x + textR.width+GAP, y1);
    			}else{
		    		g.moveTo(textR.x + textR.width+GAP, y2);
					//bottom right
					g.lineTo(x2-r, y2);
					g.curveTo(x2, y2, x2, y2-r);
					//Top right
					g.lineTo (x2, y1+r);
					g.curveTo(x2, y1, x2-r, y1);
					//Top left
					g.lineTo (x1+r, y1);
					g.curveTo(x1, y1, x1, y1+r);
					//Bottom left
					g.lineTo (x1, y2-r );
					g.curveTo(x1, y2, x1+r, y2);
					g.lineTo(textR.x - GAP, y2);
    			}
    			g.endDraw();
    		}
    	}
    	textField.text = text;
		AsWingUtils.applyTextFontAndColor(textField, font, color);
    	textField.x = textR.x;
    	textField.y = textR.y;   	
    }
    	   
    public Insets  getBorderInsetsImp (Component c ,IntRectangle bounds ){
    	double cornerW =Math.ceil(lineThickness *2+round -round *0.707106781186547);
    	Insets insets =new Insets(cornerW ,cornerW ,cornerW ,cornerW );
    	if(position == BOTTOM){
    		insets.bottom += Math.ceil(getTextFieldSize().height);
    	}else{
    		insets.top += Math.ceil(getTextFieldSize().height);
    	}
    	return insets;
    }
	
	 public DisplayObject  getDisplayImp ()
	{
		return getTextField();
	}		
	
	//-----------------------------------------------------------------

	public ASFont  getFont (){
		return font;
	}

	public void  setFont (ASFont font ){
		if(this.font != font){
			if(font == null) font = DEFAULT_FONT;
			this.font = font;
			textFieldSize == null;
		}
	}

	public ASColor  getLineColor (){
		return lineColor;
	}

	public void  setLineColor (ASColor lineColor ){
		if (lineColor != null){
			this.lineColor = lineColor;
		}
	}
	
	public ASColor  getLineLightColor (){
		return lineLightColor;
	}
	
	public void  setLineLightColor (ASColor lineLightColor ){
		if (lineLightColor != null){
			this.lineLightColor = lineLightColor;
		}
	}
	
	public boolean  isBeveled (){
		return beveled;
	}
	
	public void  setBeveled (boolean b ){
		beveled = b;
	}

	public double  getEdge (){
		return edge;
	}

	public void  setEdge (double edge ){
		this.edge = edge;
	}

	public String  getTitle (){
		return title;
	}

	public void  setTitle (String title ){
		if(this.title != title){
			this.title = title;
			textFieldSize == null;
		}
	}

	public double  getRound (){
		return round;
	}

	public void  setRound (double round ){
		this.round = round;
	}

	public ASColor  getColor (){
		return color;
	}

	public void  setColor (ASColor color ){
		this.color = color;
	}

	public int  getAlign (){
		return align;
	}
	
	/**
	 * Sets the align of title text.
	 * @see #CENTER
	 * @see #LEFT
	 * @see #RIGHT
	 */
	public void  setAlign (int align ){
		this.align = align;
	}

	public int  getPosition (){
		return position;
	}
	
	/**
	 * Sets the position of title text.
	 * @see #TOP
	 * @see #BOTTOM
	 */
	public void  setPosition (int position ){
		this.position = position;
	}	
	
	public int  getLineThickness (){
		return lineThickness;
	}

	public void  setLineThickness (double lineThickness ){
		this.lineThickness = lineThickness;
	}
		
	private IntDimension  getTextFieldSize (){
    	if (textFieldSize == null){
			textFieldSize = getFont().computeTextSize(title);  	
    	}
    	return textFieldSize;
	}
}


