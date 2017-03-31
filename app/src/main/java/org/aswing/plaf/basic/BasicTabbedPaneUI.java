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


import flash.display.*;
import flash.events.*;
import flash.ui.Keyboard;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.*;
import org.aswing.plaf.basic.icon.ArrowIcon;
import org.aswing.util.*;
import org.aswing.plaf.basic.tabbedpane.*;
import org.aswing.event.InteractiveEvent;
import org.aswing.event.FocusKeyEvent;

/**
 * @private
 */
public class BasicTabbedPaneUI extends BaseComponentUI implements LayoutManager{

	protected int topBlankSpace =4;

	protected ASColor shadow ;
	protected ASColor darkShadow ;
	protected ASColor highlight ;
	protected ASColor lightHighlight ;
	protected ASColor arrowShadowColor ;
	protected ASColor arrowLightColor ;
	protected ASColor windowBG ;

	protected JTabbedPane tabbedPane ;
	protected IntDimension tabBarSize ;
	protected IntDimension maxTabSize ;
	protected IntDimension prefferedSize ;
	protected IntDimension minimumSize ;
	protected Array tabBoundArray ;
	protected Array drawnTabBoundArray ;
	protected Insets contentMargin =null ;
	protected int maxTabWidth =-1;
	protected int tabGap =1;
	//both the 3 values are just the values considering when placement is TOP
	protected Insets tabBorderInsets ;
	protected Insets selectedTabExpandInsets ;
	protected int contentRoundLineThickness ;

	protected Array tabs ;

	protected int firstIndex ;//first viewed tab index
	protected int lastIndex ;//last perfectly viewed tab index
	protected AbstractButton prevButton ;
	protected AbstractButton nextButton ;
	protected Container buttonMCPane ;

	protected Sprite uiRootMC ;
	protected Sprite tabBarMC ;
	protected Shape tabBarMaskMC ;
	protected Sprite buttonHolderMC ;

	public  BasicTabbedPaneUI (){
		super();
		tabBorderInsets = new Insets(2, 2, 0, 2);
		selectedTabExpandInsets = new Insets(2, 2, 0, 2);
		tabs = new Array();
		firstIndex = 0;
		lastIndex = 0;
	}

	 public void  installUI (Component c ){
		tabbedPane = JTabbedPane(c);
		tabbedPane.setLayout(this);
		installDefaults();
		installComponents();
		installListeners();
	}

	 public void  uninstallUI (Component c ){
		tabbedPane = JTabbedPane(c);
		uninstallDefaults();
		uninstallComponents();
		uninstallListeners();
	}

    protected String  getPropertyPrefix (){
        return "TabbedPane.";
    }

	protected void  installDefaults (){
		String pp =getPropertyPrefix ();
		LookAndFeel.installColorsAndFont(tabbedPane, pp);
		LookAndFeel.installBorderAndBFDecorators(tabbedPane, pp);
		LookAndFeel.installBasicProperties(tabbedPane, pp);

		shadow = getColor(pp+"shadow");
		darkShadow = getColor(pp+"darkShadow");
		highlight = getColor(pp+"light");
		lightHighlight = getColor(pp+"highlight");
		arrowShadowColor = getColor(pp+"arrowShadowColor");
		arrowLightColor = getColor(pp+"arrowLightColor");
		windowBG = getColor("window");
		if(windowBG == null) windowBG = tabbedPane.getBackground();

		contentMargin = getInsets(pp+"contentMargin");
		if(contentMargin == null) contentMargin = new Insets(8, 2, 2, 2);
		maxTabWidth = getInt(pp+"maxTabWidth");
		if(maxTabWidth == -1) maxTabWidth = 1000;

		contentRoundLineThickness = getInt(getPropertyPrefix() + "contentRoundLineThickness");

		Insets tabMargin =getInsets(pp +"tabMargin");
		if(tabMargin == null) tabMargin = new InsetsUIResource(1, 1, 1, 1);

		if(containsKey(pp+"topBlankSpace")){
			topBlankSpace = getInt(pp+"topBlankSpace");
		}
		if(containsKey(pp+"tabGap")){
			tabGap = getInt(pp+"tabGap");
		}
		if(containsKey(pp+"tabBorderInsets")){
			tabBorderInsets = getInsets(pp+"tabBorderInsets");
		}
		if(containsKey(pp+"selectedTabExpandInsets")){
			selectedTabExpandInsets = getInsets(pp+"selectedTabExpandInsets");
		}

		Insets i =tabbedPane.getMargin ();
		if (i is UIResource) {
			tabbedPane.setMargin(tabMargin);
		}
	}

	protected void  uninstallDefaults (){
		LookAndFeel.uninstallBorderAndBFDecorators(tabbedPane);
	}

	protected void  installComponents (){
		prevButton = createPrevButton();
		nextButton = createNextButton();
		prevButton.setFocusable(false);
		nextButton.setFocusable(false);
		prevButton.setUIElement(true);
		nextButton.setUIElement(true);

		prevButton.addActionListener(__prevButtonReleased);
		nextButton.addActionListener(__nextButtonReleased);
		createUIAssets();
		synTabs();
	}

	protected void  uninstallComponents (){
		prevButton.removeActionListener(__prevButtonReleased);
		nextButton.removeActionListener(__nextButtonReleased);
		removeUIAssets();
	}

	protected void  installListeners (){
		tabbedPane.addStateListener(__onSelectionChanged);
		tabbedPane.addEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onNavKeyDown);
		tabbedPane.addEventListener(MouseEvent.MOUSE_DOWN, __onTabPanePressed);
	}

	protected void  uninstallListeners (){
		tabbedPane.removeStateListener(__onSelectionChanged);
		tabbedPane.removeEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onNavKeyDown);
		tabbedPane.removeEventListener(MouseEvent.MOUSE_DOWN, __onTabPanePressed);
	}

	//----------------------------------------------------------------

	protected int  getMousedOnTabIndex (){
		IntPoint p =tabbedPane.getMousePosition ();
		int n =tabbedPane.getComponentCount ();
		for(int i =firstIndex ;i <n && i<=lastIndex+1; i++){
			IntRectangle b =getDrawnTabBounds(i );
			if(b && b.containsPoint(p)){
				return i;
			}
		}
		return -1;
	}

	protected void  __onSelectionChanged (InteractiveEvent e ){
		tabbedPane.revalidate();
		tabbedPane.repaint();
	}

	protected void  __onTabPanePressed (Event e ){
		if((prevButton.hitTestMouse() || nextButton.hitTestMouse())
			&& (prevButton.isShowing() && nextButton.isShowing())){
			return;
		}
		int index =getMousedOnTabIndex ();
		if(index >= 0 && tabbedPane.isEnabledAt(index)){
			tabbedPane.setSelectedIndex(index, false);
		}
	}

	protected void  __onNavKeyDown (FocusKeyEvent e ){
		if(!tabbedPane.isEnabled()){
			return;
		}
		int n =tabbedPane.getComponentCount ();
		if(n > 0){
			int index =tabbedPane.getSelectedIndex ();
			int code =e.keyCode ;
			int count =1;
			if(code == Keyboard.DOWN || code == Keyboard.RIGHT){
				setTraversingTrue();
				index++;
				while((!tabbedPane.isEnabledAt(index) || !tabbedPane.isVisibleAt(index)) && index<n){
					index++;
					count++;
					if(index >= n){
						return;
					}
				}
				if(index >= n){
					return;
				}
				if(lastIndex < n-1){
					firstIndex = Math.min(firstIndex + count, n-1);
				}
			}else if(code == Keyboard.UP || code == Keyboard.LEFT){
				setTraversingTrue();
				index--;
				while((!tabbedPane.isEnabledAt(index) || !tabbedPane.isVisibleAt(index)) && index>=0){
					index--;
					count++;
					if(index < 0){
						return;
					}
				}
				if(index < 0){
					return;
				}
				if(firstIndex > 0){
					firstIndex = Math.max(0, firstIndex - count);
				}
			}
			tabbedPane.setSelectedIndex(index, false);
		}
	}

    protected void  setTraversingTrue (){
    	FocusManager fm =FocusManager.getManager(tabbedPane.stage );
    	if(fm){
    		fm.setTraversing(true);
    	}
    }

	protected void  __prevButtonReleased (Event e ){
		if(firstIndex > 0){
			firstIndex--;
			tabbedPane.repaint();
		}
	}

	protected void  __nextButtonReleased (Event e ){
		if(lastIndex < tabbedPane.getComponentCount()-1){
			firstIndex++;
			tabbedPane.repaint();
		}
	}

	//----------


	protected boolean  isTabHorizontalPlacing (){
		return tabbedPane.getTabPlacement() == JTabbedPane.TOP || tabbedPane.getTabPlacement() == JTabbedPane.BOTTOM;
	}
	/**
	 * This is just the value when placement is TOP
	 */
	protected Insets  getTabBorderInsets (){
		return tabBorderInsets;
	}

	protected AbstractButton  createPrevButton (){
		JButton b =new JButton(null ,createArrowIcon(Math.PI ,true ));
		b.setMargin(new Insets(2, 2, 2, 2));
		b.setDisabledIcon(createArrowIcon(Math.PI, false));
		return b;
	}

	protected AbstractButton  createNextButton (){
		JButton b =new JButton(null ,createArrowIcon(0,true ));
		b.setMargin(new Insets(2, 2, 2, 2));
		b.setDisabledIcon(createArrowIcon(0, false));
		return b;
	}

	protected Icon  createArrowIcon (double direction ,boolean enable ){
		Icon icon ;
		if(enable){
			icon = new ArrowIcon(direction, 8,
					arrowLightColor,
					arrowShadowColor);
		}else{
			icon = new ArrowIcon(direction, 8,
					arrowLightColor.brighter(0.4),
					arrowShadowColor.brighter(0.4));
		}
		return icon;
	}

	protected IntDimension  getTabBarSize (){
		if(tabBarSize != null){
			return tabBarSize;
		}
		boolean isHorizontalPlacing =isTabHorizontalPlacing ();
		tabBarSize = new IntDimension(0, 0);
		int n =tabbedPane.getComponentCount ();
		tabBoundArray = new Array(n);
		int x =0;
		int y =0;
		for(int i =0;i <n ;i ++){
			IntDimension ts =countPreferredTabSizeAt(i );
			IntRectangle tbounds =new IntRectangle(x ,y ,ts.width ,ts.height );
			tabBoundArray.put(i,  tbounds);
			int offset =i <(n +1)? tabGap : 0;
			if(isHorizontalPlacing){
				tabBarSize.height = Math.max(tabBarSize.height, ts.height);
				tabBarSize.width += ts.width + offset;
				x += ts.width + offset;
			}else{
				tabBarSize.width = Math.max(tabBarSize.width, ts.width);
				tabBarSize.height += ts.height + offset;
				y += ts.height + offset;
			}
		}
		maxTabSize = tabBarSize.clone();
		if(isHorizontalPlacing){
			tabBarSize.height += (topBlankSpace + contentMargin.top);
			//blank space at start and end for selected tab expanding
			tabBarSize.width += (tabBorderInsets.left + tabBorderInsets.right);
		}else{
			tabBarSize.width += (topBlankSpace + contentMargin.top);
			//blank space at start and end for selected tab expanding
			tabBarSize.height += (tabBorderInsets.left + tabBorderInsets.right);
		}
		return tabBarSize;
	}

	protected Array  getTabBoundArray (){
		//when tabBoundArray.lenght != tabbedPane.getComponentCount() then recalled the getTabBarSize()
		if(tabBoundArray != null && tabBoundArray.length == tabbedPane.getComponentCount()){
			return tabBoundArray;
		}else{
			invalidateLayout(tabbedPane);
			getTabBarSize();
			if(tabBoundArray == null){
				trace("Debug : Error tabBoundArray == null but tabBarSize = " + tabBarSize);
			}
			return tabBoundArray;
		}
	}

	protected IntDimension  countPreferredTabSizeAt (int index ){
		Tab tab =getTab(index );
		IntDimension size =tab.getTabComponent ().getPreferredSize ();
		size.width = Math.min(size.width, maxTabWidth);
		return size;
	}

	protected void  setDrawnTabBounds (int index ,IntRectangle b ,IntRectangle paneBounds ){
		b = b.clone();
		if(b.x < paneBounds.x){
			b.x = paneBounds.x;
		}
		if(b.y < paneBounds.y){
			b.y = paneBounds.y;
		}
		if(b.x + b.width > paneBounds.x + paneBounds.width){
			b.width = paneBounds.x + paneBounds.width - b.x;
		}
		if(b.y + b.height > paneBounds.y + paneBounds.height){
			b.height = paneBounds.y + paneBounds.height - b.y;
		}
		drawnTabBoundArray.put(index,  b);
	}

	protected IntRectangle  getDrawnTabBounds (int index ){
		return drawnTabBoundArray.get(index);
	}

	protected void  createUIAssets (){
		uiRootMC = AsWingUtils.createSprite(tabbedPane, "uiRootMC");
		tabBarMC = AsWingUtils.createSprite(uiRootMC, "tabBarMC");
		tabBarMaskMC = AsWingUtils.createShape(uiRootMC, "tabBarMaskMC");
		buttonHolderMC = AsWingUtils.createSprite(uiRootMC, "buttonHolderMC");

		tabBarMC.mask = tabBarMaskMC;
		Graphics2D g =new Graphics2D(tabBarMaskMC.graphics );
		g.fillRectangle(new SolidBrush(ASColor.BLACK), 0, 0, 1, 1);

		JPanel p =new JPanel(new SoftBoxLayout(SoftBoxLayout.X_AXIS ,0));
		p.setOpaque(false);
		p.setFocusable(false);
		p.setSizeWH(100, 100);
		p.setUIElement(true);
		buttonHolderMC.addChild(p);
		buttonMCPane = p;
		Insets insets =new Insets(topBlankSpace ,topBlankSpace ,topBlankSpace ,topBlankSpace );
		p.setBorder(new EmptyBorder(null, insets));
		p.append(prevButton);
		p.append(nextButton);
		//buttonMCPane.setVisible(false);
	}

	protected void  removeUIAssets (){
		tabbedPane.removeChild(uiRootMC);
		tabs = new Array();
	}

	protected Graphics2D  createTabBarGraphics (){
		tabBarMC.graphics.clear();
		Graphics2D g =new Graphics2D(tabBarMC.graphics );
		return g;
	}

	protected Tab  getTab (int i ){
    	return Tab(tabs.get(i));
	}

    protected Tab  getSelectedTab (){
    	if(tabbedPane.getSelectedIndex() >= 0){
    		return getTab(tabbedPane.getSelectedIndex());
    	}else{
    		return null;
    	}
    }

    protected int  indexOfTabComponent (Component tab ){
    	for(int i =0;i <tabs.length ;i ++){
    		if(getTab(i).getTabComponent() == tab){
    			return i;
    		}
    	}
    	return -1;
    }

   	 public void  paintFocus (Component c ,Graphics2D g ,IntRectangle b ){
    	Tab header =getSelectedTab ();
    	if(header != null){
    		header.getTabComponent().paintFocusRect(true);
    	}else{
    		super.paintFocus(c, g, b);
    	}
    }


    /**
     *Just  this method if you want other LAF headers .
     */
    protected Tab  createNewTab (){
    	Tab tab =getInstance(getPropertyPrefix ()+"tab")as Tab ;
    	if(tab == null){
    		tab = new BasicTabbedPaneTab();
    	}
    	tab.initTab(tabbedPane);
    	tab.getTabComponent().setFocusable(false);
    	return tab;
    }

    protected void  synTabs (){
    	int comCount =tabbedPane.getComponentCount ();
    	if(comCount != tabs.length()){
    		int i ;
    		Tab header ;
    		if(comCount > tabs.length()){
    			for(i = tabs.length; i<comCount; i++){
    				header = createNewTab();
    				setTabProperties(header, i);
    				tabBarMC.addChild(header.getTabComponent());
    				tabs.push(header);
    			}
    		}else{
    			for(i = tabs.length-comCount; i>0; i--){
    				header = Tab(tabs.pop());
    				tabBarMC.removeChild(header.getTabComponent());
    			}
    		}
    	}
    }

    protected void  synTabProperties (){
    	for(int i =0;i <tabs.length ;i ++){
    		Tab header =getTab(i );
    		setTabProperties(header, i);
    	}
    }

    protected void  setTabProperties (Tab header ,int i ){
		header.setTextAndIcon(tabbedPane.getTitleAt(i), tabbedPane.getIconAt(i));
		header.getTabComponent().setUIElement(true);
		header.getTabComponent().setEnabled(tabbedPane.isEnabledAt(i));
		header.getTabComponent().setVisible(tabbedPane.isVisibleAt(i));
		header.getTabComponent().setToolTipText(tabbedPane.getTipAt(i));
    	header.setHorizontalAlignment(tabbedPane.getHorizontalAlignment());
    	header.setHorizontalTextPosition(tabbedPane.getHorizontalTextPosition());
    	header.setIconTextGap(tabbedPane.getIconTextGap());
    	setTabMarginProperty(header, getTransformedMargin());
    	header.setVerticalAlignment(tabbedPane.getVerticalAlignment());
    	header.setVerticalTextPosition(tabbedPane.getVerticalTextPosition());
    	header.setFont(tabbedPane.getFont());
    	header.setForeground(tabbedPane.getForeground());
    }

    protected void  setTabMarginProperty (Tab tab ,Insets margin ){
    	tab.setMargin(margin); //no need here, because drawTabAt and countPreferredTabSizeAt did this work
    }

    protected Insets  getTransformedMargin (){
    	int placement =tabbedPane.getTabPlacement ();
    	Insets tabMargin =tabbedPane.getMargin ();
    	Insets transformedTabMargin =tabMargin.clone ();
		if(placement == JTabbedPane.LEFT){
			transformedTabMargin.left = tabMargin.top;
			transformedTabMargin.right = tabMargin.bottom;
			transformedTabMargin.top = tabMargin.right;
			transformedTabMargin.bottom = tabMargin.left;
		}else if(placement == JTabbedPane.RIGHT){
			transformedTabMargin.left = tabMargin.bottom;
			transformedTabMargin.right = tabMargin.top;
			transformedTabMargin.top = tabMargin.left;
			transformedTabMargin.bottom = tabMargin.right;
		}else if(placement == JTabbedPane.BOTTOM){
			transformedTabMargin.top = tabMargin.bottom;
			transformedTabMargin.bottom = tabMargin.top;
		}
		return transformedTabMargin;
    }

	 public void  paint (Component c ,Graphics2D g ,IntRectangle b ){
		super.paint(c, g, b);
		synTabProperties();

		tabBarMaskMC.x = b.x;
		tabBarMaskMC.y = b.y;
		tabBarMaskMC.width = b.width;
		tabBarMaskMC.height = b.height;
		g = createTabBarGraphics();

		boolean horizontalPlacing =isTabHorizontalPlacing ();
	  	IntRectangle contentBounds =b.clone ();
		IntRectangle tabBarBounds =getTabBarSize ().getBounds(0,0);
		tabBarBounds.x = contentBounds.x;
		tabBarBounds.y = contentBounds.y;
		tabBarBounds.width = Math.min(tabBarBounds.width, contentBounds.width);
		tabBarBounds.height = Math.min(tabBarBounds.height, contentBounds.height);
		Insets transformedTabMargin =getTransformedMargin ();
		int placement =tabbedPane.getTabPlacement ();
		if(placement == JTabbedPane.LEFT){
			tabBarBounds.y += tabBorderInsets.left;//extra for expand
		}else if(placement == JTabbedPane.RIGHT){
			tabBarBounds.x = contentBounds.x + contentBounds.width - tabBarBounds.width;
			tabBarBounds.y += tabBorderInsets.left;//extra for expand
		}else if(placement == JTabbedPane.BOTTOM){
			tabBarBounds.y = contentBounds.y + contentBounds.height - tabBarBounds.height;
			tabBarBounds.x += tabBorderInsets.left;//extra for expand
		}else{ //others value are all (TOP)considered
			tabBarBounds.x += tabBorderInsets.left;//extra for expand
		}

		int i =0;
		int n =tabbedPane.getComponentCount ();
		Array tba =getTabBoundArray ();
		drawnTabBoundArray = new Array(n);
		int selectedIndex =tabbedPane.getSelectedIndex ();

		//count not viewed front tabs's width and invisible them
		IntPoint offsetPoint =new IntPoint ();
		for(i=0; i<firstIndex; i++){
			if(horizontalPlacing){
				offsetPoint.x -= tba.get(i).width;
			}else{
				offsetPoint.y -= tba.get(i).height;
			}
			getTab(i).getTabComponent().setVisible(false);
		}
		//draw from firstIndex to last viewable tabs
		for(i=firstIndex; i<n; i++){
			if(i != selectedIndex){
				int viewedFlag =drawTabWithFullInfosAt(i ,b ,tba.get(i) ,g ,tabBarBounds ,offsetPoint ,transformedTabMargin );
				if(viewedFlag < 0){
					lastIndex = i;
				}
				if(viewedFlag >= 0){
					break;
				}
			}
		}
		drawBaseLine(tabBarBounds, g, b);
		if(selectedIndex >= 0){
			if(drawTabWithFullInfosAt(selectedIndex, b, tba.get(selectedIndex), g, tabBarBounds, offsetPoint, transformedTabMargin) < 0){
				lastIndex = Math.max(lastIndex, selectedIndex);
			}
		}
		//invisible tab after last
		for(i=lastIndex+2; i<n; i++){
			getTab(i).getTabComponent().setVisible(false);
		}

		//view prev and next buttons
		if(firstIndex > 0 || lastIndex < n-1){
			buttonMCPane.setVisible(true);
			prevButton.setEnabled(firstIndex > 0);
			nextButton.setEnabled(lastIndex < n-1);
			IntDimension bps =buttonMCPane.getPreferredSize ();
			buttonMCPane.setSize(bps);
			IntPoint bpl =new IntPoint ();
			if(placement == JTabbedPane.LEFT){
				bpl.x = contentBounds.x;
				bpl.y = contentBounds.y + contentBounds.height - bps.height;
			}else if(placement == JTabbedPane.RIGHT){
				bpl.x = contentBounds.x + contentBounds.width - bps.width;
				bpl.y = contentBounds.y + contentBounds.height - bps.height;
			}else if(placement == JTabbedPane.BOTTOM){
				bpl.x = contentBounds.x + contentBounds.width - bps.width;
				bpl.y = contentBounds.y + contentBounds.height - bps.height;
			}else{
				bpl.x = contentBounds.x + contentBounds.width - bps.width;
				bpl.y = contentBounds.y;
			}
			buttonMCPane.setLocation(bpl);
			buttonMCPane.revalidate();
		}else{
			buttonMCPane.setVisible(false);
		}
		tabbedPane.bringToTop(uiRootMC);//make it at top
	}

	/**
	 * Returns whether the tab painted out of tabbedPane bounds or not viewable or viewable.<br>
	 * @return -1 , viewable whole area;
	 *		 0, viewable but end out of bounds
	 *		 1, not viewable in the bounds.
	 */
	protected  drawTabWithFullInfosAt (int index ,IntRectangle paneBounds ,IntRectangle bounds ,
	 g:Graphics2D, tabBarBounds:IntRectangle, offsetPoint:IntPoint, transformedTabMargin:Insets):int{
		IntRectangle tb =bounds.clone ();
		tb.x += (tabBarBounds.x + offsetPoint.x);
		tb.y += (tabBarBounds.y + offsetPoint.y);
		int placement =tabbedPane.getTabPlacement ();
		if(placement == JTabbedPane.LEFT){
			tb.width = maxTabSize.width;
			tb.x += topBlankSpace;
		}else if(placement == JTabbedPane.RIGHT){
			tb.width = maxTabSize.width;
			tb.x += contentMargin.top;
		}else if(placement == JTabbedPane.BOTTOM){
			tb.y += contentMargin.top;
			tb.height = maxTabSize.height;
		}else{
			tb.height = maxTabSize.height;
			tb.y += topBlankSpace;
		}
		if(isTabHorizontalPlacing()){
			if(tb.x > paneBounds.x + paneBounds.width){
				//do not need paint
				return 1;
			}
		}else{
			if(tb.y > paneBounds.y + paneBounds.height){
				//do not need paint
				return 1;
			}
		}
		drawTabAt(index, tb, paneBounds, g, transformedTabMargin);
		if(isTabHorizontalPlacing()){
			if(tb.x + tb.width > paneBounds.x + paneBounds.width){
				return 0;
			}
		}else{
			if(tb.y + tb.height > paneBounds.y + paneBounds.height){
				return 0;
			}
		}
		return -1;
	}


    /**
     * this method to draw different tab base line for your LAF
     */
    protected void  drawBaseLine (IntRectangle tabBarBounds ,Graphics2D g ,IntRectangle fullB ){
    	IntRectangle b =tabBarBounds.clone ();
    	int placement =tabbedPane.getTabPlacement ();
    	Pen pen ;
    	double lineT =contentRoundLineThickness ;
    	double hlt =lineT /2;
    	if(isTabHorizontalPlacing()){
    		boolean isTop =(placement ==JTabbedPane.TOP );
    		if(isTop){
    			b.y = b.y + b.height - contentMargin.top;
    		}
    		b.height = contentMargin.top;
    		b.width = fullB.width;
    		b.x = fullB.x;
    		BasicGraphicsUtils.fillGradientRect(g, b,
    			tabbedPane.getBackground(), windowBG,
    			isTop ? Math.PI/2 : -Math.PI/2);
    		pen = new Pen(darkShadow, lineT);
    		pen.setCaps(CapsStyle.SQUARE);
			if(isTop){
				g.drawRectangle(pen, b.x+hlt, b.y+hlt, fullB.width-lineT, fullB.rightBottom().y - b.y-lineT);
			}else{
				g.drawRectangle(pen, fullB.x+hlt, fullB.y+hlt, fullB.width-lineT, b.y+b.height-fullB.y-lineT);
			}
    	}else{
    		boolean isLeft =(placement ==JTabbedPane.LEFT );
    		if(isLeft){
    			b.x = b.x + b.width - contentMargin.top;
    		}
    		b.width = contentMargin.top;
    		b.height = fullB.height;
    		b.y = fullB.y;

    		BasicGraphicsUtils.fillGradientRect(g, b,
    			tabbedPane.getBackground(), windowBG,
    			isLeft ? 0 : -Math.PI);
    		pen = new Pen(darkShadow, lineT);
    		pen.setCaps(CapsStyle.SQUARE);
			if(isLeft){
    			g.drawRectangle(pen, b.x+hlt, b.y+hlt, fullB.rightTop().x-b.x-lineT, b.height-lineT);
			}else{
				g.drawRectangle(pen, fullB.x+hlt, fullB.y+hlt, b.x+b.width-fullB.x-lineT, b.height-lineT);
			}

    	}
    }

    /**
     * this method to draw different tab border for your LAF .<br >
     * Note, you must call setDrawnTabBounds() to set the right bounds for each tab in this method
     */
    protected void  drawTabBorderAt (int index ,IntRectangle b ,IntRectangle paneBounds ,Graphics2D g ){
    	int placement =tabbedPane.getTabPlacement ();
    	Pen pen ;
    	b = b.clone();//make a clone to be safty modification
    	if(index == tabbedPane.getSelectedIndex()){
    		if(isTabHorizontalPlacing()){
    			b.x -= selectedTabExpandInsets.left;
    			b.width += (selectedTabExpandInsets.left + selectedTabExpandInsets.right);
	    		b.height += Math.round(topBlankSpace/2+contentRoundLineThickness);
    			if(placement == JTabbedPane.BOTTOM){
	    			b.y -= contentRoundLineThickness;
    			}else{
	    			b.y -= Math.round(topBlankSpace/2);
    			}
    		}else{
    			b.y -= selectedTabExpandInsets.left;
    			b.height += (selectedTabExpandInsets.left + selectedTabExpandInsets.right);
	    		b.width += Math.round(topBlankSpace/2+contentRoundLineThickness);
    			if(placement == JTabbedPane.RIGHT){
	    			b.x -= contentRoundLineThickness;
    			}else{
	    			b.x -= Math.round(topBlankSpace/2);
    			}
    		}
    	}
    	//This is important, should call this in sub-implemented drawTabBorderAt method
    	setDrawnTabBounds(index, b, paneBounds);
    	double x1 =b.x +0.5;
    	double y1 =b.y +0.5;
    	double x2 =b.x +b.width -0.5;
    	double y2 =b.y +b.height -0.5;
    	if(placement == JTabbedPane.LEFT){
    		BasicGraphicsUtils.drawControlBackground(g, b, getTabColor(index), Math.PI/2);

    		pen = new Pen(darkShadow, 1);
    		pen.setCaps(CapsStyle.SQUARE);
    		g.beginDraw(pen);
    		g.moveTo(x2, y1);
    		g.lineTo(x1, y1);
    		g.lineTo(x1, y2);
    		g.lineTo(x2, y2);
    		g.endDraw();
    	}else if(placement == JTabbedPane.RIGHT){
    		BasicGraphicsUtils.drawControlBackground(g, b, getTabColor(index), Math.PI/2);

    		pen = new Pen(darkShadow, 1);
    		pen.setCaps(CapsStyle.SQUARE);
    		g.beginDraw(pen);
    		g.moveTo(x1, y1);
    		g.lineTo(x2, y1);
    		g.lineTo(x2, y2);
    		g.lineTo(x1, y2);
    		g.endDraw();
    	}else if(placement == JTabbedPane.BOTTOM){
    		BasicGraphicsUtils.drawControlBackground(g, b, getTabColor(index), -Math.PI/2);

    		pen = new Pen(darkShadow, 1);
    		pen.setCaps(CapsStyle.SQUARE);
    		g.beginDraw(pen);
    		g.moveTo(x1, y1);
    		g.lineTo(x1, y2);
    		g.lineTo(x2, y2);
    		g.lineTo(x2, y1);
    		g.endDraw();
    	}else{
    		BasicGraphicsUtils.drawControlBackground(g, b, getTabColor(index), Math.PI/2);

    		pen = new Pen(darkShadow, 1);
    		pen.setCaps(CapsStyle.SQUARE);
    		g.beginDraw(pen);
    		g.moveTo(x1, y2);
    		g.lineTo(x1, y1);
    		g.lineTo(x2, y1);
    		g.lineTo(x2, y2);
    		g.endDraw();
    		//removed below make it cleaner than button style
//    		x1 += 1;
//    		y1 += 1;
//    		x2 -=1;
//    		y2 -=1;
//    		pen = new Pen(highlight, 1);
//    		g.beginDraw(pen);
//    		g.moveTo(x1, y2);
//    		g.lineTo(x1, y1);
//    		g.lineTo(x2, y1);
//    		g.endDraw();
//    		pen = new Pen(shadow, 1);
//    		g.beginDraw(pen);
//    		g.moveTo(x1, y1);
//    		g.lineTo(x2, y1);
//    		g.lineTo(x2, y2);
//    		g.endDraw();
    	}
    }

	protected void  drawTabAt (int index ,IntRectangle bounds ,IntRectangle paneBounds ,Graphics2D g ,Insets transformedTabMargin ){
		//trace("drawTabAt : " + index + ", bounds : " + bounds + ", g : " + g);
		drawTabBorderAt(index, bounds, paneBounds, g);

		IntRectangle viewRect =bounds ;//transformedTabMargin.getInsideBounds(bounds );
		Tab tab =getTab(index );
		tab.getTabComponent().setComBounds(viewRect);
		tab.getTabComponent().validate();
	}

	protected ASColor  getTabColor (int index ){
		return tabbedPane.getBackground();
	}

	//----------------------------LayoutManager Implementation-----------------------------

	public void  addLayoutComponent (Component comp ,Object constraints ){
		tabbedPane.repaint();
		synTabs();
		synTabProperties();
	}

	public void  removeLayoutComponent (Component comp ){
		tabbedPane.repaint();
		synTabs();
		synTabProperties();
	}

	public IntDimension  preferredLayoutSize (Container target ){
		if(target != tabbedPane){
			trace("Error : BasicTabbedPaneUI Can't layout " + target);
			return null;
		}
		if(prefferedSize != null){
			return prefferedSize;
		}
		Insets insets =tabbedPane.getInsets ();

		int w =0;
		int h =0;

		for(int i =tabbedPane.getComponentCount ()-1;i >=0;i --){
			IntDimension size =tabbedPane.getComponent(i ).getPreferredSize ();
			w = Math.max(w, size.width);
			h = Math.max(h, size.height);
		}
		IntDimension tbs =getTabBarSize ();
		if(isTabHorizontalPlacing()){
			w = Math.max(w, tbs.width);
			h += tbs.height;
		}else{
			h = Math.max(h, tbs.height);
			w += tbs.width;
		}

		prefferedSize = contentMargin.getOutsideSize(insets.getOutsideSize(new IntDimension(w, h)));
		return prefferedSize;
	}

	public IntDimension  minimumLayoutSize (Container target ){
		if(target != tabbedPane){
			trace("Error : BasicTabbedPaneUI Can't layout " + target);
			return null;
		}
		if(minimumSize != null){
			return minimumSize;
		}
		Insets insets =tabbedPane.getInsets ();

		int w =0;
		int h =0;

		for(int i =tabbedPane.getComponentCount ()-1;i >=0;i --){
			IntDimension size =tabbedPane.getComponent(i ).getMinimumSize ();
			w = Math.max(w, size.width);
			h = Math.max(h, size.height);
		}
		IntDimension tbs =getTabBarSize ();
		if(isTabHorizontalPlacing()){
			h += tbs.height;
		}else{
			w += tbs.width;
		}

		minimumSize = contentMargin.getOutsideSize(insets.getOutsideSize(new IntDimension(w, h)));
		return minimumSize;
	}

	public IntDimension  maximumLayoutSize (Container target ){
		if(target != tabbedPane){
			trace("Error : BasicTabbedPaneUI Can't layout " + target);
			return null;
		}
		return IntDimension.createBigDimension();
	}

	public void  layoutContainer (Container target ){
		if(target != tabbedPane){
			trace("Error : BasicTabbedPaneUI Can't layout " + target);
			return;
		}
		int n =tabbedPane.getComponentCount ();
		int selectedIndex =tabbedPane.getSelectedIndex ();

		Insets insets =tabbedPane.getInsets ();
		IntRectangle paneBounds =insets.getInsideBounds(new IntRectangle(0,0,tabbedPane.getWidth (),tabbedPane.getHeight ()));
		IntDimension tbs =getTabBarSize ();
		if(isTabHorizontalPlacing()){
			paneBounds.height -= (tbs.height + contentMargin.bottom);
			paneBounds.x += contentMargin.left;
			paneBounds.width -= (contentMargin.left + contentMargin.right);
		}else{
			paneBounds.width -= (tbs.width + contentMargin.bottom);
			paneBounds.y += contentMargin.right;
			paneBounds.height -= (contentMargin.left + contentMargin.right);
		}
		int placement =tabbedPane.getTabPlacement ();
		if(placement == JTabbedPane.LEFT){
			paneBounds.x += tbs.width;
		}else if(placement == JTabbedPane.RIGHT){
			paneBounds.x += contentMargin.bottom;
		}else if(placement == JTabbedPane.BOTTOM){
			paneBounds.y += contentMargin.bottom;
		}else{ //others value are all (TOP)considered
			paneBounds.y += tbs.height;
		}

		for(int i =0;i <n ;i ++){
			tabbedPane.getComponent(i).setBounds(paneBounds);
			tabbedPane.getComponent(i).setVisible(i == selectedIndex);
		}
	}

	public void  invalidateLayout (Container target ){
		if(target != tabbedPane){
			trace("Error : BasicTabbedPaneUI Can't layout " + target);
			return;
		}
		prefferedSize = null;
		minimumSize = null;
		tabBarSize = null;
		tabBoundArray = null;
		synTabProperties();
	}

	public double  getLayoutAlignmentX (Container target ){
		return 0;
	}

	public double  getLayoutAlignmentY (Container target ){
		return 0;
	}
}


