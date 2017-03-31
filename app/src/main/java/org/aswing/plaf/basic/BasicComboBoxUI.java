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


import org.aswing.*;
import org.aswing.graphics.*;
import org.aswing.geom.*;
import org.aswing.plaf.*;
import org.aswing.plaf.basic.icon.ArrowIcon;
import org.aswing.util.Timer;
import org.aswing.border.LineBorder;
import flash.events.MouseEvent;
import flash.events.Event;
import flash.geom.Rectangle;
import org.aswing.event.ListItemEvent;
import org.aswing.event.FocusKeyEvent;
import flash.ui.Keyboard;
import org.aswing.event.AWEvent;

/**
 * Basic combo box ui imp.
 * @author iiley
 * @private
 */
public class BasicComboBoxUI extends BaseComponentUI implements ComboBoxUI{
		
	protected Component dropDownButton ;
	protected JComboBox box ;
	protected JPopup popup ;
	protected JScrollPane scollPane ;
    protected ASColor arrowShadowColor ;
    protected ASColor arrowLightColor ;
	
	protected Timer popupTimer ;
	protected double moveDir ;
		
	public  BasicComboBoxUI (){
		super();
	}
	
     public void  installUI (Component c ){
    	box = JComboBox(c);
		installDefaults();
		installComponents();
		installListeners();
    }
    
	 public void  uninstallUI (Component c ){
    	box = JComboBox(c);
		uninstallDefaults();
		uninstallComponents();
		uninstallListeners();
    }
    
	protected String  getPropertyPrefix (){
		return "ComboBox.";
	}
	
	protected void  installDefaults (){
		String pp =getPropertyPrefix ();
        LookAndFeel.installBorderAndBFDecorators(box, pp);
        LookAndFeel.installColorsAndFont(box, pp);
        LookAndFeel.installBasicProperties(box, pp);
		arrowShadowColor = getColor(pp+"arrowShadowColor");
		arrowLightColor = getColor(pp+"arrowLightColor");
	}
    
    protected void  uninstallDefaults (){
    	LookAndFeel.uninstallBorderAndBFDecorators(box);
    }
    
	protected void  installComponents (){
		dropDownButton = createDropDownButton();
		dropDownButton.setUIElement(true);
		box.addChild(dropDownButton);
    }
	protected void  uninstallComponents (){
		box.removeChild(dropDownButton);
		if(isPopupVisible(box)){
			setPopupVisible(box, false);
		}
    }
	
	protected void  installListeners (){
		getPopupList().setFocusable(false);
		box.addEventListener(MouseEvent.MOUSE_DOWN, __onBoxPressed);
		box.addEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onFocusKeyDown);
		box.addEventListener(AWEvent.FOCUS_LOST, __onFocusLost);
		box.addEventListener(Event.REMOVED_FROM_STAGE, __onBoxRemovedFromStage);
		getPopupList().addEventListener(ListItemEvent.ITEM_CLICK, __onListItemReleased);
		popupTimer = new Timer(40);
		popupTimer.addActionListener(__movePopup);
	}
    
    protected void  uninstallListeners (){
    	popupTimer.stop();
    	popupTimer = null;
		box.removeEventListener(MouseEvent.MOUSE_DOWN, __onBoxPressed);
		box.removeEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onFocusKeyDown);
		box.removeEventListener(AWEvent.FOCUS_LOST, __onFocusLost);
		box.removeEventListener(Event.REMOVED_FROM_STAGE, __onBoxRemovedFromStage);
		getPopupList().removeEventListener(ListItemEvent.ITEM_CLICK, __onListItemReleased);
    }
    
	 public void  paint (Component c ,Graphics2D g ,IntRectangle b ){
		super.paint(c, g, b);
		layoutCombobox();
		dropDownButton.setEnabled(box.isEnabled());
	}
        
     protected void  paintBackGround (Component c ,Graphics2D g ,IntRectangle b ){
    	if(c.isOpaque()){
	 		ASColor bgColor ;
	 		bgColor = (c.getBackground() == null ? ASColor.WHITE : c.getBackground());
	 		if(!box.isEnabled()){
	 			bgColor = BasicGraphicsUtils.getDisabledColor(c);
	 		}
			g.fillRectangle(new SolidBrush(bgColor), b.x, b.y, b.width, b.height);
    	}
    }
    
    /**
     *Just  this method if you want other LAF drop down buttons .
     */
    protected Component  createDropDownButton (){
    	JButton btn =new JButton("",new ArrowIcon(
    				Math.PI/2, 8,
				    arrowLightColor,
				    arrowShadowColor
    	));
    	btn.setFocusable(false);
    	btn.setPreferredSize(new IntDimension(16, 16));
    	return btn;
    }
    
    protected JScrollPane  getScollPane (){
    	if(scollPane == null){
    		scollPane = new JScrollPane(getPopupList());
    		scollPane.setBorder(getBorder(getPropertyPrefix()+"popupBorder"));
    		scollPane.setOpaque(true);
    	}
    	return scollPane;
    }
    
    protected JPopup  getPopup (){
    	if(popup == null){
    		popup = new JPopup(box.root, false);
    		popup.setLayout(new BorderLayout());
    		popup.append(getScollPane(), BorderLayout.CENTER);
    		popup.setClipMasked(false);
    	}
    	return popup;
    }
    
    protected JList  getPopupList (){
    	return box.getPopupList();
    }
    
    protected void  viewPopup (){
    	if(!box.isOnStage()){
    		return;
    	}
		int width =box.getWidth ();
		int cellHeight ;
		if(box.getListCellFactory().isAllCellHasSameHeight()){
			cellHeight = box.getListCellFactory().getCellHeight();
		}else{
			cellHeight = box.getPreferredSize().height;
		}
		int height =Math.min(box.getItemCount (),box.getMaximumRowCount ())*cellHeight ;
		Insets i =getScollPane ().getInsets ();
		height += i.top + i.bottom;
		i = getPopupList().getInsets();
		height += i.top + i.bottom;
		getPopup().changeOwner(AsWingUtils.getOwnerAncestor(box));
		getPopup().setSizeWH(width, height);
		getPopup().show();
		startMoveToView();
		AsWingManager.callLater(__addMouseDownListenerToStage);
    }
    
    private void  __addMouseDownListenerToStage (){
    	if(getPopup().isVisible() && box.stage){
			box.stage.addEventListener(MouseEvent.MOUSE_DOWN, __onMouseDownWhenPopuped, false, 0, true);
    	}
    }
    
    private void  hidePopup (){
    	if(box.stage){
    		box.stage.removeEventListener(MouseEvent.MOUSE_DOWN, __onMouseDownWhenPopuped);
    	}
		popupTimer.stop();
    	if(getPopup().isVisible()){
			getPopup().dispose();
    	}
    }
    
    private Rectangle scrollRect ;
    //return the destination pos
    private void  startMoveToView (){
    	JPopup popupPane =getPopup ();
    	int height =popupPane.getHeight ();
    	int popupPaneHeight =height ;
    	IntPoint downDest =box.componentToGlobal(new IntPoint(0,box.getHeight ()));
    	IntPoint upDest =new IntPoint(downDest.x ,downDest.y -box.getHeight ()-popupPaneHeight );
    	IntRectangle visibleBounds =AsWingUtils.getVisibleMaximizedBounds(popupPane.parent );
    	int distToBottom =visibleBounds.y +visibleBounds.height -downDest.y -popupPaneHeight ;
    	int distToTop =upDest.y -visibleBounds.y ;
    	IntPoint gp =box.getGlobalLocation ();
    	if(distToBottom > 0 || (distToBottom < 0 && distToTop < 0 && distToBottom > distToTop)){
    		moveDir = 1;
    		gp.y += box.getHeight();
			scrollRect = new Rectangle(0, height, popupPane.getWidth(), 0);
    	}else{
    		moveDir = -1;
			scrollRect = new Rectangle(0, 0, popupPane.getWidth(), 0);
    	}
    	popupPane.setGlobalLocation(gp);
    	popupPane.scrollRect = scrollRect;
    	
		popupTimer.restart();
    }
    
    private void  setComboBoxValueFromListSelection (){
		Object selectedValue =getPopupList ().getSelectedValue ();
		box.setSelectedItem(selectedValue);
    }
    
    //-----------------------------
    
    protected void  __movePopup (Event e ){
    	JPopup popupPane =getPopup ();
    	int popupPaneHeight =popupPane.getHeight ();
    	int maxTime =10;
    	int minTime =3;
    	int speed =50;
    	if(popupPaneHeight < speed*minTime){
    		speed = Math.ceil(popupPaneHeight/minTime);
    	}else if(popupPaneHeight > speed*maxTime){
    		speed = Math.ceil(popupPaneHeight/maxTime);
    	}
    	if(popupPane.height - scrollRect.height <= speed){
    		//motion ending
    		speed = popupPane.height - scrollRect.height;
			popupTimer.stop();
    	}
		if(moveDir > 0){
			scrollRect.y -= speed;
			scrollRect.height += speed;
		}else{
			popupPane.y -= speed;
			scrollRect.height += speed;
		}
    	popupPane.scrollRect = scrollRect;
    }
    
    protected void  __onFocusKeyDown (FocusKeyEvent e ){
    	int code =e.keyCode ;
    	if(code == Keyboard.DOWN){
    		if(!isPopupVisible(box)){
    			setPopupVisible(box, true);
    			return;
    		}
    	}
    	if(code == Keyboard.ESCAPE){
    		if(isPopupVisible(box)){
    			setPopupVisible(box, false);
    			return;
    		}
    	}
    	if(code == Keyboard.ENTER && isPopupVisible(box)){
	    	hidePopup();
	    	setComboBoxValueFromListSelection();
	    	return;
    	}
    	JList list =getPopupList ();
    	int index =list.getSelectedIndex ();
    	if(code == Keyboard.DOWN){
    		index += 1;
    	}else if(code == Keyboard.UP){
    		index -= 1;
    	}else if(code == Keyboard.PAGE_DOWN){
    		index += box.getMaximumRowCount();
    	}else if(code == Keyboard.PAGE_UP){
    		index -= box.getMaximumRowCount();
    	}else if(code == Keyboard.HOME){
    		index = 0;
    	}else if(code == Keyboard.END){
    		index = list.getModel().getSize()-1;
    	}else{
    		return;
    	}
    	index = Math.max(0, Math.min(list.getModel().getSize()-1, index));
    	list.setSelectedIndex(index, false);
    	list.ensureIndexIsVisible(index);
    }
    
    protected void  __onFocusLost (Event e ){
    	hidePopup();
    }
    
    protected void  __onBoxRemovedFromStage (Event e ){
    	hidePopup();
    }
    
    protected void  __onListItemReleased (Event e ){
    	hidePopup();
    	setComboBoxValueFromListSelection();
    }
    
    protected void  __onBoxPressed (Event e ){
    	if(!isPopupVisible(box)){
    		if(box.isEditable()){
    			if(!box.getEditor().getEditorComponent().hitTestMouse()){
    				setPopupVisible(box, true);
    			}
    		}else{
    			setPopupVisible(box, true);
    		}
    	}else{
    		hidePopup();
    	}
    }
    
    protected void  __onMouseDownWhenPopuped (Event e ){
    	if(!getPopup().hitTestMouse() && !box.hitTestMouse()){
    		hidePopup();
    	}
    }
    
	/**
     * Set the visiblity of the popup
     */
	public void  setPopupVisible (JComboBox c ,boolean v ){
		if(v){
			viewPopup();
		}else{
			hidePopup();
		}
	}
	
	/** 
     * Determine the visibility of the popup
     */
	public boolean  isPopupVisible (JComboBox c ){
		return getPopup().isVisible();
	}
	
	//---------------------Layout Implementation---------------------------
    protected void  layoutCombobox (){
    	IntDimension td =box.getSize ();
		Insets insets =box.getInsets ();
		int top =insets.top ;
		int left =insets.left ;
		int right =td.width -insets.right ;
		
		int height =td.height -insets.top -insets.bottom ;
    	IntDimension buttonSize =dropDownButton.getPreferredSize ();
    	dropDownButton.setSizeWH(buttonSize.width, height);
    	dropDownButton.setLocationXY(right - buttonSize.width, top);
    	box.getEditor().getEditorComponent().setLocationXY(left, top);
    	box.getEditor().getEditorComponent().setSizeWH(td.width-insets.left-insets.right- buttonSize.width, height);
    }
    
     public IntDimension  getPreferredSize (Component c ){
    	Insets insets =box.getInsets ();
    	IntDimension listPreferSize =getPopupList ().getPreferredSize ();
    	int ew =listPreferSize.width ;
    	int wh =box.getEditor ().getEditorComponent ().getPreferredSize ().height ;
    	IntDimension buttonSize =dropDownButton.getPreferredSize ();
    	buttonSize.width += ew;
    	if(wh > buttonSize.height){
    		buttonSize.height = wh;
    	}
    	return insets.getOutsideSize(buttonSize);
    }

     public IntDimension  getMinimumSize (Component c ){
    	return box.getInsets().getOutsideSize(dropDownButton.getPreferredSize());
    }

     public IntDimension  getMaximumSize (Component c ){
		return IntDimension.createBigDimension();
    }    
}


