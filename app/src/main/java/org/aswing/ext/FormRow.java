package org.aswing.ext;

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


import org.aswing.JPanel;
import org.aswing.LayoutManager;
import org.aswing.util.ArrayList;
import org.aswing.Component;
import org.aswing.Container;
import org.aswing.AsWingConstants;
import org.aswing.geom.IntDimension;
import org.aswing.event.ContainerEvent;

/**
 * FormRow is a row in the Form.<br/>
 * A row include column children, each child sit a row, null child make a column blank, 
 * also a child can sit a continuous serveral columns.<br/>
 * For the 3 case, they are:
 * <p>
 * <ul>
 * 
 * <li>
 * .get( --child1-- ).get( --child2-- ).get( --child3-- )<br/>
 * 3 children sit 3 columns, one by one: <br/>
 * <code>setColumnChildren(child1, child2, child3);</code>
 * </li>
 * 
 * <li>
 * .get( ---------- ).get( --child1-- ).get( --child2-- )<br/>
 * First blank, and then 2 children sit 2 columns: <br/>
 * <code>setColumnChildren(null, child1, child2);</code>
 * </li>
 * 
 * <li>
 * .get( ----------child1-------- ).get( --child2-- )<br/>
 * child1 sit first two column2, child2 sit last column: <br/>
 * <code>setColumnChildren(child1, child1, child2);</code>
 * </li>
 * 
 * </ul>
 * </p>
 * <p>
 * Use <code>setColumnChildren</code> and <code>setColumnChild</code> to set the columns 
 * instead of <code>append/remove</code> method of <code>Container</code>.
 * </p>
 * 
 * @author iiley
 */
public class FormRow extends JPanel implements LayoutManager{
	
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
	public static  int CENTER =AsWingConstants.CENTER ;
	
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
	public static  int TOP =AsWingConstants.TOP ;
	
	/**
	 * A fast access to AsWingConstants Constant
	 * @see org.aswing.AsWingConstants
	 */
    public static  int BOTTOM =AsWingConstants.BOTTOM ;
        
    private int verticalAlignment ;
    private ArrayList columns ;
    private Array widthes ;
	private Array columnVerticalAlignments ;
	private int gap ;
	private String columnChildrenIndecis ;
    
    /**
     * Create a form row with specified column children.
     * @see #setColumnChildren()
     */
	public  FormRow (...columnChildren ){
		super();
		layout = this;
		verticalAlignment = AsWingConstants.CENTER;
		columns = new ArrayList();
		columns.appendAll(columnChildren);
		appendChildren(columnChildren);
		columnVerticalAlignments = new Array();
		gap = 0;
		addEventListener(ContainerEvent.COM_ADDED, __childrenChangedResetColumns);
		addEventListener(ContainerEvent.COM_REMOVED, __childrenChangedResetColumns);
	}
	
	protected void  appendChildren (Array arr ){
		for(int i =0;i <arr.length ;i ++){
			Component com =(Component)arr.get(i);
			if(com != null){
				append(com);
			}
		}
	}
	
	internal void  setGap (int gap ){
		this.gap = gap;
	}
	
	internal int  getGap (){
		return gap;
	}
	
	/**
	 * Sets the column children.
	 * @param columnChildren the children list.
	 */
	public void  setColumnChildren (Array columnChildren ){
		removeAll();
		columns = new ArrayList();
		columns.appendAll(columnChildren);
		appendChildren(columnChildren);
	}
	
	/**
	 * This is used for GuiBuilder.
	 * If a columnChildrenIndecis is set, when children changed, it will reset the 
	 * columns value with columnChildrenIndecis. Default is null
	 * <br>
	 * Set it null to not automatical reset column when children changed.
	 * <p>
	 * For example: 
	 * "-1,0,0,1" means .get( ---------- ).get( --child0 sit two column-- ).get( --child1-- )
	 * </p>
	 * @param indices the indices of children to be the columns, null to disable this automatic column set.
	 */
	public void  setColumnChildrenIndecis (String indices ){
		if(indices == null){
			columnChildrenIndecis = null;
			return;
		}
		columnChildrenIndecis = indices;
		Array childIndecis =indices.split(",");
		columns = new ArrayList();
		for(int i =0;i <childIndecis.length ;i ++){
			int index =parseInt(childIndecis.get(i) );
			if(isNaN(index)) index = -1;
			if(index >= 0 && index < getComponentCount()){
				columns.append(getComponent(index));
			}else{
				columns.append(null);
			}
		}
		revalidate();
	}
	
	private void  __childrenChangedResetColumns (ContainerEvent e ){
		if(columnChildrenIndecis != null){
			setColumnChildrenIndecis(columnChildrenIndecis);
		}
	}
	
	/**
	 * Sets the specified column position child.
	 * @param column the column position.
	 * @param child the child.
	 */
	public Component  setColumnChild (int column ,Component child ){
		if(column < 0 || column > getColumnCount()){
			throw new RangeError("Out of column bounds!");
			return;
		}
		Component old =null ;
		if(column < getColumnCount()){
			columns.get(column);
			columns.setElementAt(column, child);
		}else{
			columns.append(child);
		}
		if(old != null){
			if(!columns.contains(old)){
				remove(old);
			}
		}
		if(child != null){
			append(child);
		}
		return old;
	}
	
	/**
	 * Returns the child of column.
	 * @return a component, or null.
	 */
	public Component  getColumnChild (int column ){
		return columns.get(column);
	}
	
	/**
	 * Returns the column count.
	 * @return column count.
	 */
	public int  getColumnCount (){
		return columns.size();
	}
	
	/**
	 * Returns the preferred size of specified column.
	 */
	public int  getColumnPreferredWidth (int column ){
		Component child =getColumnChild(column );
		if(child == null){
			return 0;
		}
		return Math.ceil(child.getPreferredWidth()/getContinuousCount(column));
	}
	
    /**
     * Returns the default vertical alignment of the children in the row.
     *
     * @return the <code>verticalAlignment</code> property, one of the
     *		following values: 
     * <ul>
     * <li>AsWingConstants.CENTER (the default)
     * <li>AsWingConstants.TOP
     * <li>AsWingConstants.BOTTOM
     * </ul>
     */
    public int  getVerticalAlignment (){
        return verticalAlignment;
    }
    
    /**
     * Sets the default vertical alignment of the children in the row.
     * @param alignment  one of the following values:
     * <ul>
     * <li>AsWingConstants.CENTER (the default)
     * <li>AsWingConstants.TOP
     * <li>AsWingConstants.BOTTOM
     * </ul>
     */
    public void  setVerticalAlignment (int alignment ){
        if (alignment == verticalAlignment){
        	return;
        }else{
        	verticalAlignment = alignment;
        	revalidate();
        }
    }

    /**
     * Returns the vertical alignment of a row.
     *
     * @return the <code>verticalAlignment</code> property, one of the
     *		following values: 
     * <ul>
     * <li>AsWingConstants.CENTER (the default)
     * <li>AsWingConstants.TOP
     * <li>AsWingConstants.BOTTOM
     * </ul>
     */
    public int  getColumnVerticalAlignment (int column ){
    	if(columnVerticalAlignments.get(column) == null){
        	return verticalAlignment;
     	}else{
     		return columnVerticalAlignments.get(column);
     	}
    }
    
    /**
     * Sets the vertical alignment of a row.
     * @param alignment  one of the following values:
     * <ul>
     * <li>AsWingConstants.CENTER (the default)
     * <li>AsWingConstants.TOP
     * <li>AsWingConstants.BOTTOM
     * </ul>
     */
    public void  setColumnVerticalAlignment (int column ,int alignment ){
        if (alignment == columnVerticalAlignments.get(column)){
        	return;
        }else{
        	columnVerticalAlignments.put(column,  alignment);
        	revalidate();
        }
    }    
	
	 public void  setLayout (LayoutManager layout ){
		if(!(layout is FormRow)){
			throw new ArgumentError("layout must be FormRow instance!");
			return;
		}
		super.setLayout(layout);
	}
	
	protected int  getContinuousCount (int column ){
		Component child =columns.get(column );
		int total =getColumnCount ();
		int count =1;
		int i ;
		for(i=column+1; i<total; i++){
			if(getColumnChild(i) != child){
				break;
			}
			count++;
		}
		for(i=column-1; i>=0; i--){
			if(getColumnChild(i) != child){
				break;
			}
			count++;
		}
		return count;
	}
	
	/**
	 * Sets the width of all columns.
	 * @param widthes a array that contains all width of columns.
	 */
	public void  setColumnWidthes (Array widthes ){
		this.widthes = widthes.concat();
	}
	
	/**
	 * Returns the width of column.
	 * @return the width of column.
	 */
	public int  getColumnWidth (int column ){
		if(widthes == null) return 0;
		if(widthes.get(column) == null) return 0;
		return widthes.get(column);
	}
	
	//___________________________layout manager_________________________________
	
	public void  addLayoutComponent (Component comp ,Object constraints ){
	}
	
	public void  invalidateLayout (Container target ){
	}
	
	public IntDimension  minimumLayoutSize (Container target ){
		return getInsets().getOutsideSize(new IntDimension(0, 0));
	}
	
	public IntDimension  preferredLayoutSize (Container target ){
		int h =0;
		int w =0;
		int i ;
		int n ;
		for(i=getComponentCount()-1; i>=0; i--){
			int ih =getComponent(i ).getPreferredHeight ();
			if(ih > h) h = ih;
		}
		n = getColumnCount();
		for(i=0; i<n; i++){
			w += getColumnPreferredWidth(i);
		}
		if(n > 1){
			w += (n-1)*gap;
		}
		return getInsets().getOutsideSize(new IntDimension(w, h));
	}
	
	public IntDimension  maximumLayoutSize (Container target ){
		return IntDimension.createBigDimension();
	}
	
	public void  layoutContainer (Container target ){
		Component c ;
		int i ;
		int n ;
		n = getColumnCount();
		int sx =getInsets ().left ;
		int sy =getInsets ().top ;
		int h =getHeight ()-getInsets ().getMarginHeight ();
		for(i=0; i<n; i++){
			c = getColumnChild(i);
			int ccount =getContinuousCount(i );
			int va =getColumnVerticalAlignment(i );
			int ph =0;
			if(c){
				ph = c.getPreferredHeight();
			}
			int pw =getColumnWidth(i )*ccount ;
			int py =sy ;
			if(va == TOP){
			}else if(va == BOTTOM){
				py = sy + h - ph;
			}else{
				py = sy + (h - ph)/2;
			}
			if(ccount > 1){
				i += (ccount-1);
				pw += (ccount-1)*gap;
			}
			if(c){
				c.setComBoundsXYWH(sx, sy, pw, ph);
			}
			sx += (pw + gap);
		}
	}
	
	public void  removeLayoutComponent (Component comp ){
	}
	
	public double  getLayoutAlignmentY (Container target ){
		return getAlignmentY();
	}
	
	public double  getLayoutAlignmentX (Container target ){
		return getAlignmentX();
	}
	
}


