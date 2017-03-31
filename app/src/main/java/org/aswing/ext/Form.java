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


import org.aswing.BorderLayout;
import org.aswing.CenterLayout;
import org.aswing.Component;
import org.aswing.Container;
import org.aswing.FlowLayout;
import org.aswing.Insets;
import org.aswing.JLabel;
import org.aswing.JPanel;
import org.aswing.JSeparator;
import org.aswing.JSpacer;
import org.aswing.LayoutManager;
import org.aswing.geom.IntDimension;

/**
 * Form instanceof a vertical list of <code>FormRow</code>s.
 * Form will are tring to manage to layout <code>FormRow</code>s, if you append non-FormRow
 * component to be child of Form, it will layouted as a <code>SoftBoxLayout</code> layouted.
 * @author iiley
 * @see FormRow
 */
public class Form extends JPanel implements LayoutManager{

	private int hGap ;
	private int vGap ;

	public  Form (){
		super();
		layout = this;
		hGap = vGap = 2;
	}

	public void  setVGap (int gap ){
		if(this.vGap != gap){
			this.vGap = gap;
			revalidate();
		}
	}

	public int  getVGap (){
		return vGap;
	}

	public void  setHGap (int gap ){
		if(this.hGap != gap){
			this.hGap = gap;
			revalidate();
		}
	}

	public int  getHGap (){
		return hGap;
	}

	 public void  setLayout (LayoutManager layout ){
		if(!(layout instanceof Form)){
			throw new ArgumentError("layout must be Form instance!");
			return;
		}
		super.setLayout(layout);
	}

	/**
	 * Adds a FormRow with columns children. Each child sit a row, null child make a column blank,
	 * also a child can sit a continuous serveral columns.<br/>
	 * For the 3 case, they are:
	 * <p>
	 * <ul>
	 *
	 * <li>
	 * .get( --child1-- ).get( --child2-- ).get( --child3-- )<br/>
	 * 3 children sit 3 columns, one by one: <br/>
	 * <code>addRow(child1, child2, child3);</code>
	 * </li>
	 *
	 * <li>
	 * .get( ---------- ).get( --child1-- ).get( --child2-- )<br/>
	 * First blank, and then 2 children sit 2 columns: <br/>
	 * <code>addRow(null, child1, child2);</code>
	 * </li>
	 *
	 * <li>
	 * .get( ----------child1-------- ).get( --child2-- )<br/>
	 * child1 sit first two column2, child2 sit last column: <br/>
	 * <code>addRow(child1, child1, child2);</code>
	 * </li>
	 *
	 * </ul>
	 * </p>
	 * <p>
	 * @return the form row.
	 */
	public FormRow  addRow (...columns ){
		FormRow row =createRow(columns );
		append(row);
		return row;
	}

	/**
	 * Appends a <code>JSeparator</code> and return it.
	 * @return the separator.
	 */
	public JSeparator  addSeparator (){
		JSeparator sp =new JSeparator(JSeparator.HORIZONTAL );
		append(sp);
		return sp;
	}

	public JSpacer  addSpacer (int height =4){
		JSpacer sp =JSpacer.createVerticalSpacer(height );
		append(sp);
		return sp;
	}

	/**
	 * @see #addRow()
	 */
	public FormRow  createRow (Array columns ){
		FormRow row =new FormRow ();
		row.setColumnChildren(columns);
		row.setGap(getHGap());
		return row;
	}

	/**
	 * @see #addRow()
	 */
	public FormRow  insertRow (int index ,...columns ){
		FormRow row =createRow(columns );
		insert(index, row);
		return row;
	}

	public JLabel  createLeftLabel (String text ){
		return new JLabel(text, null, JLabel.LEFT);
	}

	public JLabel  createRightLabel (String text ){
		return new JLabel(text, null, JLabel.RIGHT);
	}

	public JLabel  createCenterLabel (String text ){
		return new JLabel(text, null, JLabel.CENTER);
	}

	public Container  centerHold (Component comp ){
		JPanel p =new JPanel(new CenterLayout ());
		p.append(comp);
		return p;
	}

	public Container  leftHold (Component comp ){
		JPanel p =new JPanel(new BorderLayout ());
		p.append(comp, BorderLayout.WEST);
		return p;
	}

	public Container  rightHold (Component comp ){
		JPanel p =new JPanel(new BorderLayout ());
		p.append(comp, BorderLayout.EAST);
		return p;
	}

	public Container  flowLeftHold (int gap ,...comps ){
		JPanel p =new JPanel(new FlowLayout(FlowLayout.LEFT ,gap ,0,false ));
		for(int i0 = 0; i0 < comps .size(); i0++)
		{
				i = comps .get(i0);
		}
		return p;
	}

	public Container  flowCenterHold (int gap ,...comps ){
		JPanel p =new JPanel(new FlowLayout(FlowLayout.CENTER ,gap ,0,false ));
		for(int i0 = 0; i0 < comps .size(); i0++)
		{
				i = comps .get(i0);
		}
		return p;
	}

	public Container  flowRightHold (int gap ,...comps ){
		JPanel p =new JPanel(new FlowLayout(FlowLayout.RIGHT ,gap ,0,false ));
		for(int i0 = 0; i0 < comps .size(); i0++)
		{
				i = comps .get(i0);
		}
		return p;
	}

	/**
	 * Returns the FormRows list in the children list.
	 */
	public Array  getRows (){
		Array rows =new Array ();
		int n =getComponentCount ();
		for(int i =0;i <n ;i ++){
			Component c =this.getComponent(i );
			if(c instanceof FormRow){
				rows.push(c);
			}
		}
		return rows;
	}

	/**
	 * Returns the children that are not Form Row.
	 */
	public Array  getOtherChildren (){
		Array others =new Array ();
		int n =getComponentCount ();
		for(int i =0;i <n ;i ++){
			Component c =this.getComponent(i );
			if(!(c instanceof FormRow)){
				others.push(c);
			}
		}
		return others;
	}

	public int  getColumnCount (){
		Array rows =getRows ();
		int count =0;
		for(int i =0;i <rows.length ;i ++){
			FormRow row =rows.get(i) ;
			if(row.isVisible()){
				count = Math.max(count, row.getColumnCount());
			}
		}
		return count;
	}
	//___________________________layout manager_________________________________

	/**
	 * Returns the preferred size of specified column.
	 */
	protected int  getColumnPreferredWidth (int column ,Array rows ){
		int wid =0;
		for(int i =0;i <rows.length ;i ++){
			FormRow row =rows.get(i) ;
			if(row.isVisible()){
				wid = Math.max(wid, row.getColumnPreferredWidth(column));
			}
		}
		return wid;
	}

	public void  addLayoutComponent (Component comp ,Object constraints ){
	}

	public void  invalidateLayout (Container target ){
	}

	public IntDimension  minimumLayoutSize (Container target ){
		return getInsets().getOutsideSize(new IntDimension(0, 0));;
	}

	public IntDimension  preferredLayoutSize (Container target ){
		Array rows =getRows ();
    	int count ;
    	Insets insets =target.getInsets ();
    	int height =0;
    	int width =0;
    	Component c ;
    	int i =0;
    	count = getComponentCount();
    	for(i=0; i<count; i++){
    		c = getComponent(i);
    		if(c.isVisible()){
	    		int g =i >0? getVGap() : 0;
	    		height += (c.getPreferredHeight() + g);
    		}
    	}
    	count = getColumnCount();
    	for(i=0; i<count; i++){
    		width += getColumnPreferredWidth(i, rows);
    		if(i > 0){
    			width += getHGap();
    		}
    	}
    	Array others =getOtherChildren ();
    	count = others.length;
    	for(i=0; i<count; i++){
    		c = others.get(i);
    		if(c.isVisible()){
    			width = Math.max(width, c.getPreferredWidth());
    		}
    	}

    	IntDimension dim =new IntDimension(width ,height );
    	return insets.getOutsideSize(dim);
	}

	public IntDimension  maximumLayoutSize (Container target ){
		return IntDimension.createBigDimension();
	}

	public void  layoutContainer (Container target ){
		Array rows =getRows ();
    	Array columnWids =new Array ();
    	int n ;
    	int i ;
    	n = getColumnCount();
    	for(i=0; i<n; i++){
    		columnWids.push(getColumnPreferredWidth(i, rows));
    	}

    	Insets insets =getInsets ();
		int sx =insets.left ;
		int sy =insets.top ;
		int w =getWidth ()-insets.getMarginWidth ();
    	n = getComponentCount();
    	for(i=0; i<n; i++){
    		Component c =getComponent(i );
    		if(c.isVisible()){
	    		FormRow row =(FormRow)c;
	    		int ph =c.getPreferredHeight ();
	    		if(row){
	    			row.setColumnWidthes(columnWids);
	    			row.setGap(getHGap());
	    		}
	    		c.setComBoundsXYWH(sx, sy, w, ph);
	    		sy += (ph + getVGap());
    		}
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


