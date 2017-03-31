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


/**
 * ASColor object to set color and alpha for a movieclip.
 * @author firdosh, iiley, n0rthwood
 */
public class ASColor{
			
	public static  ASColor WHITE =new ASColor(0xffffff );
	
	public static  ASColor LIGHT_GRAY =new ASColor(0xc0c0c0 );
	
	public static  ASColor GRAY =new ASColor(0x808080 );
	
	public static  ASColor DARK_GRAY =new ASColor(0x404040 );
	
	public static  ASColor BLACK =new ASColor(0x000000 );
	
	public static  ASColor RED =new ASColor(0xff0000 );
	
	public static  ASColor PINK =new ASColor(0xffafaf );
	
	public static  ASColor ORANGE =new ASColor(0xffc800 );
	
	public static  ASColor HALO_ORANGE =new ASColor(0xFFC200 );
	
	public static  ASColor YELLOW =new ASColor(0xffff00 );
	
	public static  ASColor GREEN =new ASColor(0x00ff00 );
	
	public static  ASColor HALO_GREEN =new ASColor(0x80FF4D );
	
	public static  ASColor MAGENTA =new ASColor(0xff00ff );
	
	public static  ASColor CYAN =new ASColor(0x00ffff );
	
	public static  ASColor BLUE =new ASColor(0x0000ff );
	
	public static  ASColor HALO_BLUE =new ASColor(0x2BF5F5 );
	
	
	protected int rgb ;
	protected double alpha ;
	
	protected double hue ;
	protected double luminance ;
	protected double saturation ;
	private boolean hlsCounted ;
	
	/**
	 * Create a ASColor
	 */
	public  ASColor (int rgb =0x000000 ,double alpha =1){
		this.rgb = rgb;
		this.alpha = Math.min(1, Math.max(0, alpha));
		hlsCounted = false;
	}
	
	/**
	 * Returns the alpha component in the range 0-1.
	 */
	public double  getAlpha (){
		return alpha;	
	}
	
	/**
	 * Returns the RGB value representing the color.
	 */
	public int  getRGB (){
		return rgb;	
	}
	
	/**
	 * Returns the ARGB value representing the color.
	 */	
	public int  getARGB (){
		int a =alpha *255;
		return rgb | (a << 24);
	}
	
	/**
     * Returns the red component in the range 0-255.
     * @return the red component.
     */
	public int  getRed (){
		return (rgb & 0x00FF0000) >> 16;
	}
	
	/**
     * Returns the green component in the range 0-255.
     * @return the green component.
     */	
	public int  getGreen (){
		return (rgb & 0x0000FF00) >> 8;
	}
	
	/**
     * Returns the blue component in the range 0-255.
     * @return the blue component.
     */	
	public int  getBlue (){
		return (rgb & 0x000000FF);
	}
	
	/**
     * Returns the hue component in the range .get(0, 1).
     * @return the hue component.
     */
	public double  getHue (){
		countHLS();
		return hue;
	}
	
	
	/**
     * Returns the luminance component in the range .get(0, 1).
     * @return the luminance component.
     */
	public double  getLuminance (){
		countHLS();
		return luminance;
	}
	
	
	/**
     * Returns the saturation component in the range .get(0, 1).
     * @return the saturation component.
     */
	public double  getSaturation (){
		countHLS();
		return saturation;
	}
	
	private void  countHLS (){
		if(hlsCounted){
			return;
		}
		hlsCounted = true;
		double rr =getRed ()/255.0;
		double gg =getGreen ()/255.0;
		double bb =getBlue ()/255.0;
		
		double rnorm ,gnorm double ,bnorm ;
		double minval ,maxval double ,msum ,mdiff ;
		double r ,g double ,b ;
		   
		r = g = b = 0;
		if (rr > 0) r = rr; if (r > 1) r = 1;
		if (gg > 0) g = gg; if (g > 1) g = 1;
		if (bb > 0) b = bb; if (b > 1) b = 1;
		
		minval = r;
		if (g < minval) minval = g;
		if (b < minval) minval = b;
		maxval = r;
		if (g > maxval) maxval = g;
		if (b > maxval) maxval = b;
		
		rnorm = gnorm = bnorm = 0;
		mdiff = maxval - minval;
		msum  = maxval + minval;
		luminance = 0.5 * msum;
		if (maxval != minval) {
			rnorm = (maxval - r)/mdiff;
			gnorm = (maxval - g)/mdiff;
			bnorm = (maxval - b)/mdiff;
		} else {
			saturation = hue = 0;
			return;
		}
		
		if (luminance < 0.5)
		  saturation = mdiff/msum;
		else
		  saturation = mdiff/(2.0 - msum);
		
		if (r == maxval)
		  hue = 60.0 * (6.0 + bnorm - gnorm);
		else if (g == maxval)
		  hue = 60.0 * (2.0 + rnorm - bnorm);
		else
		  hue = 60.0 * (4.0 + gnorm - rnorm);
		
		if (hue > 360)
			hue = hue - 360;
		hue /= 360;
	}	
	
	/**
	 * Create a new <code>ASColor</code> with another alpha but same rgb.
	 * @param newAlpha the new alpha
	 * @return the new <code>ASColor</code>
	 */
	public ASColor  changeAlpha (double newAlpha ){
		return new ASColor(getRGB(), newAlpha);
	}
	
	/**
	 * Create a new <code>ASColor</code> with just change hue channel value.
	 * @param newHue the new hue value
	 * @return the new <code>ASColor</code>
	 */	
	public ASColor  changeHue (double newHue ){
		return getASColorWithHLS(newHue, getLuminance(), getSaturation(), getAlpha());
	}
	
	/**
	 * Create a new <code>ASColor</code> with just change luminance channel value.
	 * @param newLuminance the new luminance value
	 * @return the new <code>ASColor</code>
	 */	
	public ASColor  changeLuminance (double newLuminance ){
		return getASColorWithHLS(getHue(), newLuminance, getSaturation(), getAlpha());
	}
	
	/**
	 * Create a new <code>ASColor</code> with just change saturation channel value.
	 * @param newSaturation the new saturation value
	 * @return the new <code>ASColor</code>
	 */	
	public ASColor  changeSaturation (double newSaturation ){
		return getASColorWithHLS(getHue(), getLuminance(), newSaturation, getAlpha());
	}
	
    /**
     * Creates a new <code>ASColor</code> that is a darker version of this
     * <code>ASColor</code>.
     * @param factor the darker factor(0, 1), default is 0.7
     * @return     a new <code>ASColor</code> object that is  
     *                 a darker version of this <code>ASColor</code>.
     * @see        #brighter()
     */		
	public ASColor  darker (double factor =0.7){
        int r =getRed ();
        int g =getGreen ();
        int b =getBlue ();
		return getASColor(r*factor, g*factor, b*factor, alpha);
	}
	
    /**
     * Creates a new <code>ASColor</code> that is a brighter version of this
     * <code>ASColor</code>.
     * @param factor the birghter factor 0 to 1, default is 0.7
     * @return     a new <code>ASColor</code> object that is  
     *                 a brighter version of this <code>ASColor</code>.
     * @see        #darker()
     */	
	public ASColor  brighter (double factor =0.7){
        int r =getRed ();
        int g =getGreen ();
        int b =getBlue ();

        /* From 2D group:
         * 1. black.brighter() should return grey
         * 2. applying brighter to blue will always return blue, brighter
         * 3. non pure color (non zero rgb) will eventually return white
         */
        double i =Math.floor(1.0/(1.0-factor ));
        if ( r == 0 && g == 0 && b == 0) {
           return getASColor(i, i, i, alpha);
        }
        if ( r > 0 && r < i ) r = i;
        if ( g > 0 && g < i ) g = i;
        if ( b > 0 && b < i ) b = i;
        
        return getASColor(r/factor, g/factor, b/factor, alpha);
	}
	
	/**
	 * Returns a ASColor with the specified red, green, blue values in the range .get(0 - 255) 
	 * and alpha value in range.get(0, 1). 
	 * @param r red channel
	 * @param g green channel
	 * @param b blue channel
	 * @param a alpha channel
	 */
	public static ASColor  getASColor (int r ,int g ,int b ,double a =1){
		return new ASColor(getRGBWith(r, g, b), a);
	}
	
	/**
	 * Returns a ASColor with specified ARGB uint value.
	 * @param argb ARGB value representing the color
	 * @return the ASColor
	 */
	public static ASColor  getWithARGB (int argb ){
		int rgb =argb & 0x00FFFFFF;
		double alpha =(argb >>>24)/255;
		return new ASColor(rgb, alpha);
	}
	
	/**
	 * Returns a ASColor with with the specified hue, luminance, 
	 * saturation and alpha values in the range .get(0 - 1). 
	 * @param h hue channel
	 * @param l luminance channel
	 * @param s saturation channel
	 * @param a alpha channel
	 */	
	public static ASColor  getASColorWithHLS (double h ,double l ,double s ,double a =1){
		ASColor c =new ASColor(0,a );
		c.hlsCounted = true;
		c.hue = Math.max(0, Math.min(1, h));
		c.luminance = Math.max(0, Math.min(1, l));
		c.saturation = Math.max(0, Math.min(1, s));
		
		double H =c.hue ;
		double L =c.luminance ;
		double S =c.saturation ;
		
		double p1 ,p2 double ,r ,g ,b ;
		p1 = p2 = 0;
		H = H*360;
		if(L<0.5){
			p2=L*(1+S);
		}else{
			p2=L + S - L*S;
		}
		p1=2*L-p2;
		if(S==0){
			r=L;
			g=L;
			b=L;
		}else{
			r = hlsValue(p1, p2, H+120);
			g = hlsValue(p1, p2, H);
			b = hlsValue(p1, p2, H-120);
		}
		r = 		255;
		g = 		255;
		b = 		255;
		double color_n =(r <<16)+(g <<8)+b ;
		int color_rgb =Math.max(0,Math.min(0xFFFFFF ,Math.round(color_n )));
		c.rgb = color_rgb;
		return c;
	}
	
	private static double  hlsValue (double p1 ,double p2 ,double h ){
	   if (h > 360) h = h - 360;
	   if (h < 0)   h = h + 360;
	   if (h < 60 ) return p1 + (p2-p1)*h/60;
	   if (h < 180) return p2;
	   if (h < 240) return p1 + (p2-p1)*(240-h)/60;
	   return p1;
	}	
		
	/**
	 * Returns the RGB value representing the red, green, and blue values. 
	 * @param rr red channel
	 * @param gg green channel
	 * @param bb blue channel
	 */
	public static int  getRGBWith (int rr ,int gg ,int bb ){
		int r =rr ;
		int g =gg ;
		int b =bb ;
		if(r > 255){
			r = 255;
		}
		if(g > 255){
			g = 255;
		}
		if(b > 255){
			b = 255;
		}
		int color_n =(r <<16)+(g <<8)+b ;
		return color_n;
	}
	
	public String  toString (){
		return "ASColor(rgb:"+rgb.toString(16)+", alpha:"+alpha+")";
	}

	/**
	 * Compare if compareTo object has the same value as this ASColor object does
	 * @param compareTo the object to compare with
	 * 
	 * @return  a Boolean value that indicates if the compareTo object's value is the same as this one
	 */	
	public boolean  equals (Object o ){
		ASColor c =(ASColor)o;
		if(c != null){
			return c.alpha === alpha && c.rgb === rgb;
		}else{
			return false;
		}
	}
	
        public ASColor  clone ()
        {
            return new ASColor(this.getRGB(), this.getAlpha());
        }//end  
        

}


