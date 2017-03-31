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
import org.aswing.geom.IntRectangle;

/**
 * A border layout lays out a container, arranging and resizing
 * its components to fit in five regions:
 * north, south, east, west, and center.
 * Each region may contain no more than one component, and 
 * is identified by a corresponding constant:
 * <code>NORTH</code>, <code>SOUTH</code>, <code>EAST</code>,
 * <code>WEST</code>, and <code>CENTER</code>.  When adding a
 * component to a container with a border layout, use one of these
 * five constants, for example:
 * <pre>
 *    Panel p = new Panel();
 *    p.setLayout(new BorderLayout());
 *    p.add(new Button("Okay"), BorderLayout.SOUTH);
 * </pre>
 * For convenience, <code>BorderLayout</code> interprets the
 * absence of a string specification the same as the constant
 * <code>CENTER</code>:
 * <pre>
 *    Panel p2 = new Panel();
 *    p2.setLayout(new BorderLayout());
 *    p2.add(new TextArea());  // Same as p.add(new TextArea(), BorderLayout.CENTER);
 * </pre>
 * 
 * <p>
 * The following image illustrate the way the borderLayout layout child component.
 * <br></br>
 * <img src="../../aswingImg/BorderLayout.JPG" ></img>
 * </p>
 * @author iiley
 */
 
public class BorderLayout extends EmptyLayout{
	private double hgap ;

	private double vgap ;

	private Component north ;

	private Component west ;

	private Component east ;

    private Component south ;

	private Component center ;
    
    private Component firstLine ;

	private Component lastLine ;

	private Component firstItem ;

	private Component lastItem ;
	
	private String defaultConstraints ;

    /**
     * The north layout constraint (top of container).
     */
    public static  String NORTH ="North";

    /**
     * The south layout constraint (bottom of container).
     */
    public static  String SOUTH ="South";

    /**
     * The east layout constraint (right side of container).
     */
    public static  String EAST ="East";

    /**
     * The west layout constraint (left side of container).
     */
    public static  String WEST ="West";

    /**
     * The center layout constraint (middle of container).
     */
    public static  String CENTER ="Center";

	
	public static  String BEFORE_FIRST_LINE ="First";


    public static  String AFTER_LAST_LINE ="Last";


    public static  String BEFORE_LINE_BEGINS ="Before";


    public static  String AFTER_LINE_ENDS ="After";


    public static  String PAGE_START =BEFORE_FIRST_LINE ;


    public static  String PAGE_END =AFTER_LAST_LINE ;


    public static  String LINE_START =BEFORE_LINE_BEGINS ;


    public static  String LINE_END =AFTER_LINE_ENDS ;

    /**
     * Constructs a border layout with the specified gaps
     * between components.
     * The horizontal gap is specified by <code>hgap</code>
     * and the vertical gap is specified by <code>vgap</code>.
     * @param   hgap   the horizontal gap.
     * @param   vgap   the vertical gap.
     */
    public  BorderLayout (int hgap =0,int vgap =0){
		this.hgap = hgap;
		this.vgap = vgap;
		this.defaultConstraints = CENTER; 
    }
	
	/**
	 * 
	 */
	public void  setDefaultConstraints (Object constraints ){
		defaultConstraints = constraints.toString();
	}

    public int  getHgap (){
		return hgap;
    }
	
	/**
	 * Set horizontal gap
	 */
    public void  setHgap (int hgap ){
		this.hgap = hgap;
    }

    public int  getVgap (){
		return vgap;
    }
	
	/**
	 *  Set vertical gap
	 */
    public void  setVgap (int vgap ){
		this.vgap = vgap;
    }
	
	/**
	 * 
	 */
     public void  addLayoutComponent (Component comp ,Object constraints ){
    	String name =constraints != null ? constraints.toString() : null;
	    addLayoutComponentByAlign(name, comp);
    }

    private void  addLayoutComponentByAlign (String name ,Component comp ){
		if (name == null) {
	   		name = defaultConstraints;
		}

		if (CENTER == name) {
		    center = comp;
		} else if (NORTH == name) {
		    north = comp;
		} else if (SOUTH == name) {
		    south = comp;
		} else if (EAST == name) {
		    east = comp;
		} else if (WEST == name) {
		    west = comp;
		} else if (BEFORE_FIRST_LINE == name) {
		    firstLine = comp;
		} else if (AFTER_LAST_LINE == name) {
		    lastLine = comp;
		} else if (BEFORE_LINE_BEGINS == name) {
		    firstItem = comp;
		} else if (AFTER_LINE_ENDS == name) {
		    lastItem = comp;
		} else {
			//defaut center
		    center = comp;
		}
    }
    
	/**
	 * 
	 */
     public void  removeLayoutComponent (Component comp ){
		if (comp == center) {
		    center = null;
		} else if (comp == north) {
		    north = null;
		} else if (comp == south) {
		    south = null;
		} else if (comp == east) {
		    east = null;
		} else if (comp == west) {
		    west = null;
		}
		if (comp == firstLine) {
		    firstLine = null;
		} else if (comp == lastLine) {
		    lastLine = null;
		} else if (comp == firstItem) {
		    firstItem = null;
		} else if (comp == lastItem) {
		    lastItem = null;
		}
    }
	/**
	 * 
	 */
     public IntDimension  minimumLayoutSize (Container target ){
		return target.getInsets().getOutsideSize();
    }
	
	/**
	 * 
	 */
     public IntDimension  preferredLayoutSize (Container target ){
    	IntDimension dim =new IntDimension(0,0);
	    boolean ltr =true ;
	    Component c =null ;
		
		IntDimension d ;
		if ((c=getChild(EAST,ltr)) != null) {
		    d = c.getPreferredSize();
		    dim.width += d.width + hgap;
		    dim.height = Math.max(d.height, dim.height);
		}
		if ((c=getChild(WEST,ltr)) != null) {
		    d = c.getPreferredSize();
		    dim.width += d.width + hgap;
		    dim.height = Math.max(d.height, dim.height);
		}
		if ((c=getChild(CENTER,ltr)) != null) {
		    d = c.getPreferredSize();
		    dim.width += d.width;
		    dim.height = Math.max(d.height, dim.height);
		}
		if ((c=getChild(NORTH,ltr)) != null) {
		    d = c.getPreferredSize();
		    dim.width = Math.max(d.width, dim.width);
		    dim.height += d.height + vgap;
		}
		if ((c=getChild(SOUTH,ltr)) != null) {
		    d = c.getPreferredSize();
		    dim.width = Math.max(d.width, dim.width);
		    dim.height += d.height + vgap;
		}
	
		Insets insets =target.getInsets ();
		dim.width += insets.left + insets.right;
		dim.height += insets.top + insets.bottom;
		return dim;
    }
	/**
	 *
	 */
     public double  getLayoutAlignmentX (Container target ){
    	return 0.5;
    }
	
	/**
	 * 
	 */
     public double  getLayoutAlignmentY (Container target ){
    	return 0.5;
    }

    /**
     * <p>
     * Lays out the container argument using this border layout.
     * </p>
     * <p>
     * This method actually reshapes the components in the specified
     * container in order to satisfy the constraints of this
     * <code>BorderLayout</code> object. The <code>NORTH</code>
     * and <code>SOUTH</code> components, if any, are placed at
     * the top and bottom of the container, respectively. The
     * <code>WEST</code> and <code>EAST</code> components are
     * then placed on the left and right, respectively. Finally,
     * the <code>CENTER</code> object is placed in any remaining
     * space in the middle.
     * </p>
     * <p>
     * Most applications do not call this method directly. This method
     * is called when a container calls its <code>doLayout</code> method.
     * </p>
     * @param   target   the container in which to do the layout.
     * @see     Container
     * @see     Container#doLayout()
     */
     public void  layoutContainer (Container target ){
    	IntDimension td =target.getSize ();
		Insets insets =target.getInsets ();
		int top =insets.top ;
		int bottom =td.height -insets.bottom ;
		int left =insets.left ;
		int right =td.width -insets.right ;
	    boolean ltr =true ;
	    Component c =null ;
	
		IntDimension d ;
		if ((c=getChild(NORTH,ltr)) != null) {
		    d = c.getPreferredSize();
		    c.setBounds(new IntRectangle(left, top, right - left, d.height));
		    top += d.height + vgap;
		}
		if ((c=getChild(SOUTH,ltr)) != null) {
		    d = c.getPreferredSize();
		    c.setBounds(new IntRectangle(left, bottom - d.height, right - left, d.height));
		    bottom -= d.height + vgap;
		}
		if ((c=getChild(EAST,ltr)) != null) {
		    d = c.getPreferredSize();
		    c.setBounds(new IntRectangle(right - d.width, top, d.width, bottom - top));
		    right -= d.width + hgap;
		    //Flashout.log("East prefer size : " + d);
		}
		if ((c=getChild(WEST,ltr)) != null) {
		    d = c.getPreferredSize();
		    c.setBounds(new IntRectangle(left, top, d.width, bottom - top));
		    left += d.width + hgap;
		}
		if ((c=getChild(CENTER,ltr)) != null) {
		    c.setBounds(new IntRectangle(left, top, right - left, bottom - top));
		}
      
    }

    /**
     * Get the component that corresponds to the given constraint location
     *
     * @param   key     The desired absolute position,
     *                  either NORTH, SOUTH, EAST, or WEST.
     * @param   ltr     Is the component line direction left-to-right?
     */
    private Component  getChild (String key ,boolean ltr ){
        Component result =null ;

        if (key == NORTH) {
            result = (firstLine != null) ? firstLine : north;
        }
        else if (key == SOUTH) {
            result = (lastLine != null) ? lastLine : south;
        }
        else if (key == WEST) {
            result = ltr ? firstItem : lastItem;
            if (result == null) {
                result = west;
            }
        }
        else if (key == EAST) {
            result = ltr ? lastItem : firstItem;
            if (result == null) {
                result = east;
            }
        }
        else if (key == CENTER) {
            result = center;
        }
        if (result != null && !result.isVisible()) {
            result = null;
        }
        return result;
    }

    public String  toString (){
		return "BorderLayout.get(hgap=" + hgap + ",vgap=" + vgap + ")";
    }
}


