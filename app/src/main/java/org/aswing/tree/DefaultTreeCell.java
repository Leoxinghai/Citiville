/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.tree;

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
import org.aswing.Icon;
import org.aswing.JLabel;
import org.aswing.JTree;
import org.aswing.geom.IntRectangle;

/**
 * The default cell for tree.
 * @author iiley
 */
public class DefaultTreeCell extends JLabel implements TreeCell {

	protected Icon expanded_folder_icon ;
	protected Icon collapsed_folder_icon ;
	protected Icon leaf_icon ;

	protected Object value;

	public  DefaultTreeCell (){
		super();
		setHorizontalAlignment(LEFT);
		setOpaque(true);
	}

	/**
	 * Simpler this method to speed up performance
	 */
	 public void  setComBounds (IntRectangle b ){
		if(!b.equals(bounds)){
			bounds.setRect(b);
			locate();
			valid = false;
		}
	}

	/**
	 * Simpler this method to speed up performance
	 */
	 public void  invalidate (){
		clearPreferSizeCaches();
		valid = false;
	}

	/**
	 * Simpler this method to speed up performance
	 */
	 public void  revalidate (){
		valid = false;
	}

	/**
	 * do nothing, because paintImmediately will be called in by Tree
	 * @see #paintImmediately()
	 */
	 public void  repaint (){
		//do nothing, because paintImmediately will be called in by Tree
	}

	public Icon  getExpandedFolderIcon (){
		return expanded_folder_icon;
	}
	public Icon  getCollapsedFolderIcon (){
		return collapsed_folder_icon;
	}
	public Icon  getLeafIcon (){
		return leaf_icon;
	}

	protected Icon  createExpandedFolderIcon (JTree tree ){
		return tree.getUI().getIcon("Tree.folderExpandedIcon");
	}
	protected Icon  createCollapsedFolderIcon (JTree tree ){
		return tree.getUI().getIcon("Tree.folderCollapsedIcon");;
	}
	protected Icon  createLeafIcon (JTree tree ){
		return tree.getUI().getIcon("Tree.leafIcon");;
	}

	//**********************************************************
	//				  Implementing TableCell
	//**********************************************************
	public void  setCellValue (Object value){
		readyToPaint = true;
		this.value = value;
		setText(value + "");
	}

	public Object getCellValue () {
		return value;
	}

	public void  setTreeCellStatus (JTree tree ,boolean selected ,boolean expanded ,boolean leaf ,int row ){
		if(expanded_folder_icon == null){
			expanded_folder_icon = createExpandedFolderIcon(tree);
			//make it can get image from tree ui properties
			getUI().putDefault("Tree.folderExpandedImage", tree.getUI().getDefault("Tree.folderExpandedImage"));
		}
		if(collapsed_folder_icon == null){
			collapsed_folder_icon = createCollapsedFolderIcon(tree);
			//make it can get image from tree ui properties
			getUI().putDefault("Tree.folderCollapsedImage", tree.getUI().getDefault("Tree.folderCollapsedImage"));
		}
		if(leaf_icon == null){
			leaf_icon = createLeafIcon(tree);
			//make it can get image from tree ui properties
			getUI().putDefault("Tree.leafImage", tree.getUI().getDefault("Tree.leafImage"));
		}

		if(selected){
			setBackground(tree.getSelectionBackground());
			setForeground(tree.getSelectionForeground());
		}else{
			setBackground(tree.getBackground());
			setForeground(tree.getForeground());
		}
		setFont(tree.getFont());
		if(leaf){
			setIcon(getLeafIcon());
		}else if(expanded){
			setIcon(getExpandedFolderIcon());
		}else{
			setIcon(getCollapsedFolderIcon());
		}
	}

	public Component  getCellComponent (){
		return this;
	}

	 public String  toString (){
		return "TreeCell.get(label:" + super.toString() + ")\n";
	}
}


