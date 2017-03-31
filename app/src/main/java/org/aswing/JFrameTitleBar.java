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

import flash.events.Event;
import org.aswing.event.AWEvent;
import org.aswing.event.FrameEvent;
import org.aswing.event.InteractiveEvent;
import org.aswing.event.WindowEvent;
import org.aswing.plaf.UIResource;

/**
 * The default Imp of FrameTitleBar
 */
public class JFrameTitleBar extends Container implements FrameTitleBar, UIResource{
	
	protected AbstractButton iconifiedButton ;
	protected AbstractButton maximizeButton ;
	protected AbstractButton restoreButton ;
	protected AbstractButton closeButton ;
	protected ASColor activeTextColor ;
	protected ASColor inactiveTextColor ;
	protected JLabel titleLabel ;
	protected Icon icon ;
	protected String text ;
	protected boolean titleEnabled ;
	
	protected Container buttonPane ;
	protected SoftBoxLayout buttonPaneLayout ;
	
	protected JWindow owner ;
	protected JFrame frame ;
	
	public  JFrameTitleBar (){
		super();
		titleEnabled = true;
		setLayout(new FrameTitleBarLayout());
		
		buttonPane = new Container();
		buttonPane.setCachePreferSizes(false);
		buttonPaneLayout = new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0);
		buttonPane.setLayout(buttonPaneLayout);
		titleLabel = new JLabel();
		titleLabel.mouseEnabled = false;
		titleLabel.mouseChildren = false;
		titleLabel.setHorizontalAlignment(JLabel.LEFT);
		append(titleLabel, BorderLayout.CENTER);
		append(buttonPane, BorderLayout.EAST);
		
		setIconifiedButton(createIconifiedButton());
		setMaximizeButton(createMaximizeButton());
		setRestoreButton(createRestoreButton());
		setCloseButton(createCloseButton());
		setMaximizeButtonVisible(false);
		buttonPane.appendAll(iconifiedButton, restoreButton, maximizeButton, closeButton);
		
		setUIElement(true);
		addEventListener(AWEvent.PAINT, __framePainted);
	}
	
	protected JButton  createPureButton (){
		JButton b =new JButton ();
		b.setBackgroundDecorator(null);
		b.setMargin(new Insets());
		return b;
	}
	
	protected AbstractButton  createIconifiedButton (){
		return createPureButton();
	}
	
	protected AbstractButton  createMaximizeButton (){
		return createPureButton();
	}
	
	protected AbstractButton  createRestoreButton (){
		return createPureButton();
	}
	
	protected AbstractButton  createCloseButton (){
		return createPureButton();
	}
	
	public void  updateUIPropertiesFromOwner (){
		if(getIconifiedButton()){
			getIconifiedButton().setIcon(getFrameIcon("Frame.iconifiedIcon"));
		}
		if(getMaximizeButton()){
			getMaximizeButton().setIcon(getFrameIcon("Frame.maximizeIcon"));
		}
		if(getRestoreButton()){
			getRestoreButton().setIcon(getFrameIcon("Frame.normalIcon"));
		}
		if(getCloseButton()){
			getCloseButton().setIcon(getFrameIcon("Frame.closeIcon"));
		}
		
		activeTextColor     = getFrameUIColor("Frame.activeCaptionText");
		inactiveTextColor   = getFrameUIColor("Frame.inactiveCaptionText"); 	
		setBackgroundDecorator(getTitleBGD("Frame.titleBarBG"));
		buttonPaneLayout.setGap(getFrameUIInt("Frame.titleBarButtonGap"));
		revalidateIfNecessary();
		__activeChange(null);
		__framePainted(null);
	}
	
	protected Icon  getFrameIcon (String key ){
		if(owner.getUI()){
			return owner.getUI().getIcon(key);
		}else{
			return UIManager.getIcon(key);
		}
	}
	
	protected GroundDecorator  getTitleBGD (String key ){
		if(owner.getUI()){
			return owner.getUI().getGroundDecorator(key);
		}else{
			return UIManager.getGroundDecorator(key);
		}
	}
	
	protected int  getFrameUIInt (String key ){
		if(owner.getUI()){
			return owner.getUI().getInt(key);
		}else{
			return UIManager.getInt(key);
		}
	}
	
	protected ASColor  getFrameUIColor (String key ){
		if(owner.getUI()){
			return owner.getUI().getColor(key);
		}else{
			return UIManager.getColor(key);
		}
	}
	
	public Component  getSelf (){
		return this;
	}
	
	public void  setFrame (JWindow f ){
		if(owner){
			owner.removeEventListener(FrameEvent.FRAME_ABILITY_CHANGED, __frameAbilityChanged);
			owner.removeEventListener(AWEvent.PAINT, __framePainted);
			owner.removeEventListener(InteractiveEvent.STATE_CHANGED, __stateChanged);
			owner.removeEventListener(WindowEvent.WINDOW_ACTIVATED, __activeChange);
			owner.removeEventListener(WindowEvent.WINDOW_DEACTIVATED, __activeChange);
		}
		owner = f;
		frame =(JFrame) f;
		if(owner){
			owner.addEventListener(FrameEvent.FRAME_ABILITY_CHANGED, __frameAbilityChanged, false, 0, true);
			owner.addEventListener(AWEvent.PAINT, __framePainted, false, 0, true);
			owner.addEventListener(InteractiveEvent.STATE_CHANGED, __stateChanged, false, 0, true);
			owner.addEventListener(WindowEvent.WINDOW_ACTIVATED, __activeChange, false, 0, true);
			owner.addEventListener(WindowEvent.WINDOW_DEACTIVATED, __activeChange, false, 0, true);
			
			updateUIPropertiesFromOwner();
		}
		__stateChanged(null);
	}
	
	public JWindow  getFrame (){
		return owner;
	}
	
	public void  setTitleEnabled (boolean b ){
		titleEnabled = b;
	}
	
	public boolean  isTitleEnabled (){
		return titleEnabled;
	}
		
	public void  addExtraControl (Component c ,int position ){
		if(position == AsWingConstants.LEFT){
			buttonPane.insert(0, c);
		}else{
			buttonPane.append(c);
		}
	}
	
	public Component  removeExtraControl (Component c ){
		return buttonPane.remove(c);
	}
	
	public JLabel  getLabel (){
		return titleLabel;
	}
	
	public void  setIcon (Icon i ){
		icon = i;
		if(titleLabel){
			titleLabel.setIcon(i);
		}
	}
	
	public Icon  getIcon (){
		return icon;
	}
	
	public void  setText (String t ){
		text = t;
		if(titleLabel){
			titleLabel.setText(t);
		}
	}
	
	public String  getText (){
		return text;
	}
	
	public void  setIconifiedButton (AbstractButton b ){
		if(iconifiedButton != b){
			int index =-1;
			if(iconifiedButton){
				index = buttonPane.getIndex(iconifiedButton);
				buttonPane.removeAt(index);
				iconifiedButton.removeActionListener(__iconifiedPressed);
			}
			iconifiedButton = b;
			if(iconifiedButton){
				buttonPane.insert(index, iconifiedButton);
				iconifiedButton.addActionListener(__iconifiedPressed);
			}
		}
	}
	
	public void  setMaximizeButton (AbstractButton b ){
		if(maximizeButton != b){
			int index =-1;
			if(maximizeButton){
				index = buttonPane.getIndex(maximizeButton);
				buttonPane.removeAt(index);
				maximizeButton.removeActionListener(__maximizePressed);
			}
			maximizeButton = b;
			if(maximizeButton){
				buttonPane.insert(index, maximizeButton);
				maximizeButton.addActionListener(__maximizePressed);
			}
		}
	}
	
	public void  setRestoreButton (AbstractButton b ){
		if(restoreButton != b){
			int index =-1;
			if(restoreButton){
				index = buttonPane.getIndex(restoreButton);
				buttonPane.removeAt(index);
				restoreButton.removeActionListener(__restorePressed);
			}
			restoreButton = b;
			if(restoreButton){
				buttonPane.insert(index, restoreButton);
				restoreButton.addActionListener(__restorePressed);
			}
		}
	}
	
	public void  setCloseButton (AbstractButton b ){
		if(closeButton != b){
			int index =-1;
			if(closeButton){
				index = buttonPane.getIndex(closeButton);
				buttonPane.removeAt(index);
				closeButton.removeActionListener(__closePressed);
			}
			closeButton = b;
			if(closeButton){
				buttonPane.insert(index, closeButton);
				closeButton.addActionListener(__closePressed);
			}
		}
	}
	
	public AbstractButton  getIconifiedButton (){
		return iconifiedButton;
	}
	
	public AbstractButton  getMaximizeButton (){
		return maximizeButton;
	}
	
	public AbstractButton  getRestoreButton (){
		return restoreButton;
	}
	
	public AbstractButton  getCloseButton (){
		return closeButton;
	}
	
	public void  setIconifiedButtonVisible (boolean b ){
		if(getIconifiedButton()){
			getIconifiedButton().setVisible(b);
		}
	}
	
	public void  setMaximizeButtonVisible (boolean b ){
		if(getMaximizeButton()){
			getMaximizeButton().setVisible(b);
		}
	}
	
	public void  setRestoreButtonVisible (boolean b ){
		if(getRestoreButton()){
			getRestoreButton().setVisible(b);
		}
	}
	
	public void  setCloseButtonVisible (boolean b ){
		if(getCloseButton()){
			getCloseButton().setVisible(b);
		}
	}
	
	private void  __iconifiedPressed (Event e ){
		if(frame && isTitleEnabled()){
			frame.setState(JFrame.ICONIFIED, false);
		}
	}
	
	private void  __maximizePressed (Event e ){
		if(frame && isTitleEnabled()){
			frame.setState(JFrame.MAXIMIZED, false);
		}
	}
	
	private void  __restorePressed (Event e ){
		if(frame && isTitleEnabled()){
			frame.setState(JFrame.NORMAL, false);
		}
	}
	
	private void  __closePressed (Event e ){
		if(frame && isTitleEnabled()){
			frame.closeReleased();
		}
	}
	
	private void  __activeChange (Event e ){
		if(getLabel()){
			getLabel().setForeground(owner.isActive() ? activeTextColor : inactiveTextColor);
			getLabel().repaint();
		}
		repaint();
	}
	
	private void  __framePainted (AWEvent e ){
		if(getLabel()){
			getLabel().setFont(owner.getFont());
		}
	}
	
	private void  __frameAbilityChanged (FrameEvent e ){
		__stateChanged(null);
	}
	
	private void  __stateChanged (InteractiveEvent e ){
		if(frame == null){
			return;
		}
		int state =frame.getState ();
		if(state != JFrame.ICONIFIED 
			&& state != JFrame.NORMAL
			&& state != JFrame.MAXIMIZED_HORIZ
			&& state != JFrame.MAXIMIZED_VERT
			&& state != JFrame.MAXIMIZED){
			state = JFrame.NORMAL;
		}
		if(state == JFrame.ICONIFIED){
			setIconifiedButtonVisible(false);
			setMaximizeButtonVisible(false);
			setRestoreButtonVisible(true);
			setCloseButtonVisible(frame.isClosable());
		}else if(state == JFrame.NORMAL){
			setIconifiedButtonVisible(frame.isResizable());
			setRestoreButtonVisible(false);
			setMaximizeButtonVisible(frame.isResizable());
			setCloseButtonVisible(frame.isClosable());
		}else{
			setIconifiedButtonVisible(frame.isResizable());
			setRestoreButtonVisible(frame.isResizable());
			setMaximizeButtonVisible(false);
			setCloseButtonVisible(frame.isClosable());
		}
		revalidateIfNecessary();
	}
}


