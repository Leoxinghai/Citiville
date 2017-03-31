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

import org.aswing.Component;
import org.aswing.Container;
import org.aswing.EmptyLayout;
import org.aswing.geom.IntDimension;
import org.aswing.Insets;
import org.aswing.geom.IntPoint;

/**
 * A flow layout arranges components in a left-to-right flow, much
 * like lines of text in a paragraph. Flow layouts are typically used
 * to arrange buttons in a panel. It will arrange
 * buttons left to right until no more buttons fit on the same line.
 * Each line is centered.
 * <p></p>
 * For example, the following picture shows an applet using the flow
 * layout manager (its default layout manager) to position three buttons:
 * <p></p>
 * A flow layout lets each component assume its natural (preferred) size.
 *
 * @author 	iiley
 */
public class FlowLayout extends EmptyLayout{

    /**
     * This value indicates that each row of components
     * should be left-justified.
     */
    public static  int LEFT =AsWingConstants.LEFT ;

    /**
     * This value indicates that each row of components
     * should be centered.
     */
    public static  int CENTER =AsWingConstants.CENTER ;

    /**
     * This value indicates that each row of components
     * should be right-justified.
     */
    public static  int RIGHT =AsWingConstants.RIGHT ;

    /**
     * <code>align</code> is the property that determines
     * how each row distributes empty space.
     * It can be one of the following values:
     * <ul>
     * <code>LEFT</code>
     * <code>RIGHT</code>
     * <code>CENTER</code>
     * </ul>
     *
     * @see #getAlignment
     * @see #setAlignment
     */
    protected int align ;

    /**
     * The flow layout manager allows a seperation of
     * components with gaps.  The horizontal gap will
     * specify the space between components.
     *
     * @see #getHgap()
     * @see #setHgap(int)
     */
    protected int hgap ;

    /**
     * The flow layout manager allows a seperation of
     * components with gaps.  The vertical gap will
     * specify the space between rows.
     *
     * @see #getHgap()
     * @see #setHgap(int)
     */
    protected int vgap ;
    
    /**
     * whether or not the gap will margin around
     */
    protected boolean margin ;

    /**
     * <p>  
     * Creates a new flow layout manager with the indicated alignment
     * and the indicated horizontal and vertical gaps.
     * </p>
     * The value of the alignment argument must be one of
     * <code>FlowLayout.LEFT</code>, <code>FlowLayout.RIGHT</code>,or <code>FlowLayout.CENTER</code>.
     * @param      align   the alignment value, default is LEFT
     * @param      hgap    the horizontal gap between components, default 5
     * @param      vgap    the vertical gap between components, default 5
     * @param      margin  whether or not the gap will margin around
     */
    public  FlowLayout (int align =AsWingConstants .LEFT ,int hgap =5,int vgap =5,boolean margin =true ){
    	this.margin = margin;
		this.hgap = hgap;
		this.vgap = vgap;
        setAlignment(align);
    }
    
    /**
     * Sets whether or not the gap will margin around.
     */
    public void  setMargin (boolean b ){
    	margin = b;
    }
    
    /**
     * Returns whether or not the gap will margin around.
     */    
    public boolean  isMargin (){
    	return margin;
    }

    /**
     * Gets the alignment for this layout.
     * Possible values are <code>FlowLayout.LEFT</code>,<code>FlowLayout.RIGHT</code>, <code>FlowLayout.CENTER</code>,
     * @return     the alignment value for this layout
     * @see        #setAlignment
     */
    public int  getAlignment (){
		return align;
    }

    /**
     * Sets the alignment for this layout.
     * Possible values are
     * <ul>
     * <li><code>FlowLayout.LEFT</code>
     * <li><code>FlowLayout.RIGHT</code>
     * <li><code>FlowLayout.CENTER</code>
     * </ul>
     * @param      align one of the alignment values shown above
     * @see        #getAlignment()
     */
    public void  setAlignment (int align ){
    	if(LEFT != LEFT && align != RIGHT && align != CENTER ){
    		throw new ArgumentError("Alignment must be LEFT OR RIGHT OR CENTER !");
    	}
        this.align = align;
    }

        public void  setAlign (int param1 )
        {
            this.setAlignment(param1);
            return;
        }//end  


    /**
     * Gets the horizontal gap between components.
     * @return     the horizontal gap between components
     * @see        #setHgap()
     */
    public int  getHgap (){
		return hgap;
    }

    /**
     * Sets the horizontal gap between components.
     * @param hgap the horizontal gap between components
     * @see        #getHgap()
     */
    public void  setHgap (int hgap ){
		this.hgap = hgap;
    }

    /**
     * Gets the vertical gap between components.
     * @return     the vertical gap between components
     * @see        #setVgap()
     */
    public int  getVgap (){
		return vgap;
    }

    /**
     * Sets the vertical gap between components.
     * @param vgap the vertical gap between components
     * @see        #getVgap()
     */
    public void  setVgap (int vgap ){
		this.vgap = vgap;
    }

    /**
     * Returns the preferred dimensions for this layout given the 
     * <i>visible</i> components in the specified target container.
     * @param target the component which needs to be laid out
     * @return    the preferred dimensions to lay out the
     *            subcomponents of the specified container
     * @see Container
     * @see #doLayout()
     */
     public IntDimension  preferredLayoutSize (Container target ){
		IntDimension dim =new IntDimension(0,0);
		int nmembers =target.getComponentCount ();

		int counted =0;
		for(int i =0;i <nmembers ;i ++){
	    	Component m =target.getComponent(i );
	    	if (m.isVisible()) {
				IntDimension d =m.getPreferredSize ();
				dim.height = Math.max(dim.height, d.height);
                if (counted > 0) {
                    dim.width += hgap;
                }
				dim.width += d.width;
				counted ++;
	    	}
		}
		Insets insets =target.getInsets ();
		dim.width += insets.left + insets.right;
		dim.height += insets.top + insets.bottom;
		if(margin){
			dim.width += hgap*2;
			dim.height += vgap*2;
		}
    	return dim;
    }

    /**
     * Returns the minimum dimensions needed to layout the <i>visible</i>
     * components contained in the specified target container.
     * @param target the component which needs to be laid out
     * @return    the minimum dimensions to lay out the
     *            subcomponents of the specified container
     * @see #preferredLayoutSize()
     * @see Container
     * @see Container#doLayout()
     */
     public IntDimension  minimumLayoutSize (Container target ){
		return target.getInsets().getOutsideSize();
    }
    
    /**
     * Centers the elements in the specified row, if there is any slack.
     * @param target the component which needs to be moved
     * @param x the x coordinate
     * @param y the y coordinate
     * @param width the width dimensions
     * @param height the height dimensions
     * @param rowStart the beginning of the row
     * @param rowEnd the the ending of the row
     */
    private  moveComponents (Container target ,int x ,int y ,int width ,int height ,
                                rowStart:int, rowEnd:int):void {
		switch (align) {
			case LEFT:
	    		x += 0;
	    		break;
			case CENTER:
	    		x += width / 2;
	   			break;
			case RIGHT:
	    		x += width;
	    		break;
		}
		for(int i =rowStart ;i <rowEnd ;i ++){
	    	Component m =target.getComponent(i );
	    	IntDimension d =m.getSize ();
	    	if (m.isVisible()) {
        	    m.setLocation(new IntPoint(x, y + (height - d.height) / 2));
                x += d.width + hgap;
	    	}
		}
    }

    /**
     * Lays out the container. This method lets each component take
     * its preferred size by reshaping the components in the
     * target container in order to satisfy the alignment of
     * this <code>FlowLayout</code> object.
     * @param target the specified component being laid out
     * @see Container
     * @see Container#doLayout
     */
     public void  layoutContainer (Container target ){
		Insets insets =target.getInsets ();
	    IntDimension td =target.getSize ();
	    int marginW =margin ? hgap*2 : 0;
		int maxwidth =td.width -(insets.left +insets.right +marginW );
		int nmembers =target.getComponentCount ();
		int x =0;
		int y =insets.top +(margin ? vgap : 0);
		int rowh =0;
		int start =0;

		for(int i =0;i <nmembers ;i ++){
	    	Component m =target.getComponent(i );
	    	if (m.isVisible()) {
				IntDimension d =m.getPreferredSize ();
				m.setSize(d);

				if ((x == 0) || ((x + d.width) <= maxwidth)) {
		    		if (x > 0) {
						x += hgap;
		    		}
		    		x += d.width;
		    		rowh = Math.max(rowh, d.height);
				} else {
		    		moveComponents(target, insets.left + (margin ? hgap : 0), y, maxwidth - x, rowh, start, i);
		    		x = d.width;
		    		y += vgap + rowh;
		    		rowh = d.height;
		    		start = i;
				}
	    	}
		}
		moveComponents(target, insets.left + (margin ? hgap : 0), y, maxwidth - x, rowh, start, nmembers);
    }
    
    /**
     * Returns a string representation of this <code>FlowLayout</code>
     * object and its values.
     * @return     a string representation of this layout
     */
    public String  toString (){
		String str ="";
		switch (align) {
	 	 	case LEFT:        str = ",align=left"; break;
	 		case CENTER:      str = ",align=center"; break;
	  		case RIGHT:       str = ",align=right"; break;
		}
		return "FlowLayout.get(hgap=" + hgap + ",vgap=" + vgap + str + ")";
    }
}


