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


import flash.display.Shape;
import flash.events.*;
import flash.ui.Keyboard;

import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.*;
import org.aswing.util.*;

/**
 * Basic slider ui imp.
 * @author iiley
 * @private
 */
public class BasicSliderUI extends BaseComponentUI implements SliderUI{
	
	protected JSlider slider ;
	protected Icon thumbIcon ;

	protected ASColor highlightColor ;
	protected ASColor shadowColor ;
	protected ASColor darkShadowColor ;
	protected ASColor lightColor ;
	protected ASColor tickColor ;
	protected ASColor progressColor ;
	
	protected IntRectangle trackRect ;
	protected IntRectangle trackDrawRect ;
	protected IntRectangle tickRect ;
	protected IntRectangle thumbRect ;
	
	private int offset ;
	private boolean isDragging ;
	private int scrollIncrement ;
	private int scrollContinueDestination ;
	private Timer scrollTimer ;
	private static double scrollSpeedThrottle =60;//delay in milli seconds
	private static double initialScrollSpeedThrottle =500;//first delay in milli seconds
	
	protected Shape progressCanvas ;
	
	public  BasicSliderUI (){
		super();
		trackRect   = new IntRectangle();
		tickRect	= new IntRectangle();
		thumbRect   = new IntRectangle();
		trackDrawRect = new IntRectangle();
		offset	  = 0;
		isDragging  = false;
	}
		
	protected String  getPropertyPrefix (){
		return "Slider.";
	}
		
	 public void  installUI (Component c ){
		slider = JSlider(c);
		installDefaults();
		installComponents();
		installListeners();
	}
	
	 public void  uninstallUI (Component c ){
		slider = JSlider(c);
		uninstallDefaults();
		uninstallComponents();
		uninstallListeners();
	}
	
	protected void  installDefaults (){
		String pp =getPropertyPrefix ();
		LookAndFeel.installColorsAndFont(slider, pp);
		LookAndFeel.installBasicProperties(slider, pp);
		LookAndFeel.installBorderAndBFDecorators(slider, pp);
		configureSliderColors();
	}
	
	protected void  configureSliderColors (){
		String pp =getPropertyPrefix ();
		highlightColor = getColor(pp+"highlight");
		shadowColor = getColor(pp+"shadow");
		darkShadowColor = getColor(pp+"darkShadow");
		lightColor = getColor(pp+"light");
		
		tickColor = getColor(pp+"tickColor");
		progressColor = getColor(pp+"progressColor");
	}
	
	protected void  uninstallDefaults (){
		LookAndFeel.uninstallBorderAndBFDecorators(slider);
	}
	
	protected void  installComponents (){
		String pp =getPropertyPrefix ();
		thumbIcon = getIcon(pp+"thumbIcon");
		if(thumbIcon.getDisplay(slider)==null){
			throw new Error("Slider thumb icon must has its own display object(getDisplay()!=null)!");
		}
		progressCanvas = new Shape();
		slider.addChild(progressCanvas);
		slider.addChild(thumbIcon.getDisplay(slider));
	}
	
	protected void  uninstallComponents (){
		slider.removeChild(progressCanvas);
		slider.removeChild(thumbIcon.getDisplay(slider));
		thumbIcon = null;
		progressCanvas = null;
	}
	
	protected void  installListeners (){
		slider.addEventListener(MouseEvent.MOUSE_DOWN, __onSliderPress);
		slider.addEventListener(ReleaseEvent.RELEASE, __onSliderReleased);
		slider.addEventListener(MouseEvent.MOUSE_WHEEL, __onSliderMouseWheel);
		slider.addStateListener(__onSliderStateChanged);
		slider.addEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onSliderKeyDown);
		scrollTimer = new Timer(scrollSpeedThrottle);
		scrollTimer.setInitialDelay(initialScrollSpeedThrottle);
		scrollTimer.addActionListener(__scrollTimerPerformed);
	}
	
	protected void  uninstallListeners (){
		slider.removeEventListener(MouseEvent.MOUSE_DOWN, __onSliderPress);
		slider.removeEventListener(ReleaseEvent.RELEASE, __onSliderReleased);
		slider.removeEventListener(MouseEvent.MOUSE_WHEEL, __onSliderMouseWheel);
		slider.removeStateListener(__onSliderStateChanged);
		slider.removeEventListener(FocusKeyEvent.FOCUS_KEY_DOWN, __onSliderKeyDown);
		scrollTimer.stop();
		scrollTimer = null;
	}
	
	protected boolean  isVertical (){
		return slider.getOrientation() == JSlider.VERTICAL;
	}
	
	 public void  paint (Component c ,Graphics2D g ,IntRectangle b ){
		super.paint(c, g, b);
		countTrackRect(b);
		countThumbRect();
		countTickRect(b);
		paintTrack(g, trackDrawRect);
		paintThumb(g, thumbRect);
		paintTick(g, tickRect);
	}
	
	protected void  countTrackRect (IntRectangle b ){
		IntDimension thumbSize =getThumbSize ();
		int h_margin ,v_margin int ;
		if(isVertical()){
			v_margin = Math.ceil(thumbSize.height/2.0);
			h_margin = thumbSize.width/3-1;
			trackDrawRect.setRectXYWH(b.x+h_margin, b.y+v_margin, 
				h_margin+2, b.height-v_margin*2);
			trackRect.setRectXYWH(b.x, b.y+v_margin, 
				thumbSize.width, b.height-v_margin*2);
		}else{
			h_margin = Math.ceil(thumbSize.width/2.0);
			v_margin = thumbSize.height/3-1;
			trackDrawRect.setRectXYWH(b.x+h_margin, b.y+v_margin, 
				b.width-h_margin*2, v_margin+2);
			trackRect.setRectXYWH(b.x+h_margin, b.y, 
				b.width-h_margin*2, thumbSize.height);
		}
	}
	
	protected void  countTickRect (IntRectangle b ){
		if(isVertical()){
			tickRect.y = trackRect.y;
			tickRect.x = trackRect.x+trackRect.width+getTickTrackGap();
			tickRect.height = trackRect.height;
			tickRect.width = b.width-trackRect.width-getTickTrackGap();
		}else{
			tickRect.x = trackRect.x;
			tickRect.y = trackRect.y+trackRect.height+getTickTrackGap();
			tickRect.width = trackRect.width;
			tickRect.height = b.height-trackRect.height-getTickTrackGap();
		}
	}
	
	protected void  countThumbRect (){
		thumbRect.setSize(getThumbSize());
		if (slider.getSnapToTicks()){
			int sliderValue =slider.getValue ();
			int snappedValue =sliderValue ;
			int majorTickSpacing =slider.getMajorTickSpacing ();
			int minorTickSpacing =slider.getMinorTickSpacing ();
			int tickSpacing =0;
			if (minorTickSpacing > 0){
				tickSpacing = minorTickSpacing;
			}else if (majorTickSpacing > 0){
				tickSpacing = majorTickSpacing;
			}
			if (tickSpacing != 0){
				// If it's not on a tick, change the value
				if ((sliderValue - slider.getMinimum()) % tickSpacing != 0){
					double temp =(sliderValue -slider.getMinimum ())/tickSpacing ;
					int whichTick =Math.round(temp );
					snappedValue = slider.getMinimum() + (whichTick * tickSpacing);
				}
				if(snappedValue != sliderValue){ 
					slider.setValue(snappedValue);
				}
			}
		}
		int valuePosition ;
		if (isVertical()) {
			valuePosition = yPositionForValue(slider.getValue());
			thumbRect.x = trackRect.x;
			thumbRect.y = valuePosition - (thumbRect.height / 2);
		}else {
			valuePosition = xPositionForValue(slider.getValue());
			thumbRect.x = valuePosition - (thumbRect.width / 2);
			thumbRect.y = trackRect.y;
		}
	}
	
	protected IntDimension  getThumbSize (){
		if(isVertical()){
			return new IntDimension(thumbIcon.getIconHeight(slider), thumbIcon.getIconWidth(slider));
		}else{
			return new IntDimension(thumbIcon.getIconWidth(slider), thumbIcon.getIconHeight(slider));
		}
	}
	
	protected IntDimension  countTickSize (IntRectangle sliderRect ){
		if(isVertical()){
			return new IntDimension(getTickLength(), sliderRect.height);
		}else{
			return new IntDimension(sliderRect.width, getTickLength());
		}
	}
	
	/**
	 * Gets the height of the tick area for horizontal sliders and the width of the
	 * tick area for vertical sliders.  BasicSliderUI uses the returned value to
	 * determine the tick area rectangle.  If you want to give your ticks some room,
	 * make this larger than you need and paint your ticks away from the sides in paintTicks().
	 */
	protected double  getTickLength (){
		return 10;
	}	
	
	protected IntDimension  countTrackAndThumbSize (IntRectangle sliderRect ){
		if(isVertical()){
			return new IntDimension(getThumbSize().width, sliderRect.height);
		}else{
			return new IntDimension(sliderRect.width, getThumbSize().height);
		}
	}
	
	protected int  getTickTrackGap (){
		return 2;
	}
	
	public double  xPositionForValue (int value ){
		int min =slider.getMinimum ();
		int max =slider.getMaximum ();
		int trackLength =trackRect.width ;
		int valueRange =max -min ;
		double pixelsPerValue =trackLength /valueRange ;
		int trackLeft =trackRect.x ;
		int trackRight =trackRect.x +(trackRect.width -0);//0
		int xPosition ;

		if ( !slider.getInverted() ) {
			xPosition = trackLeft;
			xPosition += Math.round(pixelsPerValue * (value - min));
		}else {
			xPosition = trackRight;
			xPosition -= Math.round(pixelsPerValue * (value - min));
		}

		xPosition = Math.max( trackLeft, xPosition );
		xPosition = Math.min( trackRight, xPosition );

		return xPosition;
	}

	public int  yPositionForValue (int value ){
		int min =slider.getMinimum ();
		int max =slider.getMaximum ();
		int trackLength =trackRect.height ;
		int valueRange =max -min ;
		double pixelsPerValue =trackLength /valueRange ;
		int trackTop =trackRect.y ;
		int trackBottom =trackRect.y +(trackRect.height -1);
		int yPosition ;

		if ( !slider.getInverted() ) {
			yPosition = trackTop;
			yPosition += Math.round(pixelsPerValue * (max - value));
		}
		else {
			yPosition = trackTop;
			yPosition += Math.round(pixelsPerValue * (value - min));
		}

		yPosition = Math.max( trackTop, yPosition );
		yPosition = Math.min( trackBottom, yPosition );

		return yPosition;
	}	
	
	/**
	 * Returns a value give a y position.  If yPos is past the track at the top or the
	 * bottom it will set the value to the min or max of the slider, depending if the
	 * slider is inverted or not.
	 */
	public int  valueForYPosition (int yPos ){
		int value ;
		int minValue =slider.getMinimum ();
		int maxValue =slider.getMaximum ();
		int trackLength =trackRect.height ;
		int trackTop =trackRect.y ;
		int trackBottom =trackRect.y +(trackRect.height -1);
		boolean inverted =slider.getInverted ();
		if ( yPos <= trackTop ) {
			value = inverted ? minValue : maxValue;
		}else if ( yPos >= trackBottom ) {
			value = inverted ? maxValue : minValue;
		}else {
			int distanceFromTrackTop =yPos -trackTop ;
			int valueRange =maxValue -minValue ;
			double valuePerPixel =valueRange /trackLength ;
			int valueFromTrackTop =Math.round(distanceFromTrackTop *valuePerPixel );
	
			value = inverted ? minValue + valueFromTrackTop : maxValue - valueFromTrackTop;
		}
		return value;
	}
  
	/**
	 * Returns a value give an x position.  If xPos is past the track at the left or the
	 * right it will set the value to the min or max of the slider, depending if the
	 * slider is inverted or not.
	 */
	public int  valueForXPosition (int xPos ){
		int value ;
		int minValue =slider.getMinimum ();
		int maxValue =slider.getMaximum ();
		int trackLength =trackRect.width ;
		int trackLeft =trackRect.x ;
		int trackRight =trackRect.x +(trackRect.width -0);//1
		boolean inverted =slider.getInverted ();
		if ( xPos <= trackLeft ) {
			value = inverted ? maxValue : minValue;
		}else if ( xPos >= trackRight ) {
			value = inverted ? minValue : maxValue;
		}else {
			int distanceFromTrackLeft =xPos -trackLeft ;
			int valueRange =maxValue -minValue ;
			double valuePerPixel =valueRange /trackLength ;
			int valueFromTrackLeft =Math.round(distanceFromTrackLeft *valuePerPixel );
			
			value = inverted ? maxValue - valueFromTrackLeft : minValue + valueFromTrackLeft;
		}
		return value;
	}
	
	//-------------------------
	
	protected void  paintTrack (Graphics2D g ,IntRectangle drawRect ){
		if(!slider.getPaintTrack()){
			return;
		}
		if(slider.isEnabled()){
			BasicGraphicsUtils.paintLoweredBevel(g, drawRect, shadowColor, darkShadowColor, lightColor, highlightColor);
			paintTrackProgress(new Graphics2D(progressCanvas.graphics), drawRect);
		}else{
			g.beginDraw(new Pen(lightColor, 1));
			drawRect.grow(-1, -1);
			g.rectangle(drawRect.x, drawRect.y, drawRect.width, drawRect.height);
			g.endDraw();
		}
	}
	protected void  paintTrackProgress (Graphics2D g ,IntRectangle trackDrawRect ){
		if(!slider.getPaintTrack()){
			return;
		}		
		IntRectangle rect =trackDrawRect.clone ();
		int width ;
		int height ;
		int x ;
		int y ;
		boolean inverted =slider.getInverted ();
		if(isVertical()){
			width = rect.width-5;
			height = thumbRect.y + thumbRect.height/2 - rect.y - 5;
			x = rect.x + 2;
			if(inverted){
				y = rect.y + 2;
			}else{
				height = rect.y + rect.height - thumbRect.y - thumbRect.height/2 - 2;
				y = thumbRect.y + thumbRect.height/2;
			}
		}else{
			height = rect.height-5;
			if(inverted){
				width = rect.x + rect.width - thumbRect.x - thumbRect.width/2 - 2;
				x = thumbRect.x + thumbRect.width/2;
			}else{
				width = thumbRect.x + thumbRect.width/2 - rect.x - 5;
				x = rect.x + 2;
			}
			y = rect.y + 2;
		}
		g.fillRectangle(new SolidBrush(progressColor), x, y, width, height);		
	}
	
	protected void  paintThumb (Graphics2D g ,IntRectangle drawRect ){
		if(isVertical()){
			thumbIcon.getDisplay(slider).rotation = 90;
			thumbIcon.updateIcon(slider, g, drawRect.x+thumbIcon.getIconHeight(slider), drawRect.y);
		}else{
			thumbIcon.getDisplay(slider).rotation = 0;
			thumbIcon.updateIcon(slider, g, drawRect.x, drawRect.y);
		}
	}
	
	protected void  paintTick (Graphics2D g ,IntRectangle drawRect ){
		if(!slider.getPaintTicks()){
			return;
		}		
		IntRectangle tickBounds =drawRect ;
		int majT =slider.getMajorTickSpacing ();
		int minT =slider.getMinorTickSpacing ();
		int max =slider.getMaximum ();
		
		g.beginDraw(new Pen(tickColor, 0));
			
		int yPos =0;
		int value =0;
		int xPos =0;
			
		if (isVertical()) {
			xPos = tickBounds.x;
			value = slider.getMinimum();
			yPos = 0;

			if ( minT > 0 ) {
				while ( value <= max ) {
					yPos = yPositionForValue( value );
					paintMinorTickForVertSlider( g, tickBounds, xPos, yPos );
					value += minT;
				}
			}

			if ( majT > 0 ) {
				value = slider.getMinimum();
				while ( value <= max ) {
					yPos = yPositionForValue( value );
					paintMajorTickForVertSlider( g, tickBounds, xPos, yPos );
					value += majT;
				}
			}
		}else {
			yPos = tickBounds.y;
			value = slider.getMinimum();
			xPos = 0;

			if ( minT > 0 ) {
				while ( value <= max ) {
					xPos = xPositionForValue( value );
					paintMinorTickForHorizSlider( g, tickBounds, xPos, yPos );
					value += minT;
				}
			}

			if ( majT > 0 ) {
				value = slider.getMinimum();

				while ( value <= max ) {
					xPos = xPositionForValue( value );
					paintMajorTickForHorizSlider( g, tickBounds, xPos, yPos );
					value += majT;
				}
			}
		}
		g.endDraw();		
	}

	private void  paintMinorTickForHorizSlider (Graphics2D g ,IntRectangle tickBounds ,int x ,int y ){
		g.line( x, y, x, y+tickBounds.height / 2 - 1);
	}

	private void  paintMajorTickForHorizSlider (Graphics2D g ,IntRectangle tickBounds ,int x ,int y ){
		g.line( x, y, x, y+tickBounds.height - 2);
	}

	private void  paintMinorTickForVertSlider (Graphics2D g ,IntRectangle tickBounds ,int x ,int y ){
		g.line( x, y, x+tickBounds.width / 2 - 1, y );
	}

	private void  paintMajorTickForVertSlider (Graphics2D g ,IntRectangle tickBounds ,int x ,int y ){
		g.line( x, y, x+tickBounds.width - 2, y );
	}
	
	//----------------------------
	
	private void  __onSliderStateChanged (Event e ){
		if(!isDragging){
			countThumbRect();
			paintThumb(null, thumbRect);
			progressCanvas.graphics.clear();
			paintTrackProgress(new Graphics2D(progressCanvas.graphics), trackDrawRect);
		}
	}
	
	private void  __onSliderKeyDown (FocusKeyEvent e ){
		if(!slider.isEnabled()){
			return;
		}
		int code =e.keyCode ;
		int unit =getUnitIncrement ();
		int block =slider.getMajorTickSpacing ()>0? slider.getMajorTickSpacing() : unit*5;
		if(isVertical()){
			unit = -unit;
			block = -block;
		}
		if(slider.getInverted()){
			unit = -unit;
			block = -block;
		}
		if(code == Keyboard.UP || code == Keyboard.LEFT){
			scrollByIncrement(-unit);
		}else if(code == Keyboard.DOWN || code == Keyboard.RIGHT){
			scrollByIncrement(unit);
		}else if(code == Keyboard.PAGE_UP){
			scrollByIncrement(-block);
		}else if(code == Keyboard.PAGE_DOWN){
			scrollByIncrement(block);
		}else if(code == Keyboard.HOME){
			slider.setValue(slider.getMinimum());
		}else if(code == Keyboard.END){
			slider.setValue(slider.getMaximum() - slider.getExtent());
		}
	}
	
	private void  __onSliderPress (Event e ){
		IntPoint mousePoint =slider.getMousePosition ();
		if(thumbRect.containsPoint(mousePoint)){
			__startDragThumb();
		}else{
			boolean inverted =slider.getInverted ();
			double thumbCenterPos ;
			if(isVertical()){
				thumbCenterPos = thumbRect.y + thumbRect.height/2;
				if(mousePoint.y > thumbCenterPos){
					scrollIncrement = inverted ? getUnitIncrement() : -getUnitIncrement();
				}else{
					scrollIncrement = inverted ? -getUnitIncrement() : getUnitIncrement();
				}
				scrollContinueDestination = valueForYPosition(mousePoint.y);
			}else{
				thumbCenterPos = thumbRect.x + thumbRect.width/2;
				if(mousePoint.x > thumbCenterPos){
					scrollIncrement = inverted ? -getUnitIncrement() : getUnitIncrement();
				}else{
					scrollIncrement = inverted ? getUnitIncrement() : -getUnitIncrement();
				}
				scrollContinueDestination = valueForXPosition(mousePoint.x);
			}
			scrollTimer.restart();
			__scrollTimerPerformed(null);//run one time immediately first
		}
	}
	private void  __onSliderReleased (Event e ){
		if(isDragging){
			__stopDragThumb();
		}
		if(scrollTimer.isRunning()){
			scrollTimer.stop();
		}
	}
	private void  __onSliderMouseWheel (MouseEvent e ){
		if(!slider.isEnabled()){
			return;
		}
		int delta =e.delta ;
		if(slider.getInverted()){
			delta = -delta;
		}
		scrollByIncrement(delta*getUnitIncrement());
	}
	
	private void  __scrollTimerPerformed (Event e ){
		int value =slider.getValue ()+scrollIncrement ;
		boolean finished =false ;
		if(scrollIncrement > 0){
			if(value >= scrollContinueDestination){
				finished = true;
			}
		}else{
			if(value <= scrollContinueDestination){
				finished = true;
			}
		}
		if(finished){
			slider.setValue(scrollContinueDestination);
			scrollTimer.stop();
		}else{
			scrollByIncrement(scrollIncrement);
		}
	}	
	
	private void  scrollByIncrement (int increment ){
		slider.setValue(slider.getValue() + increment);
	}
	
	private int  getUnitIncrement (){
		int unit =0;
		if(slider.getMinorTickSpacing() >0 ){
			unit = slider.getMinorTickSpacing();
		}else if(slider.getMajorTickSpacing() > 0){
			unit = slider.getMajorTickSpacing();
		}else{
			double range =slider.getMaximum ()-slider.getMinimum ();
			if(range > 2){
				unit = Math.max(1, Math.round(range/500));
			}else{
				unit = range/100;
			}
		}
		return unit;
	}
	
	private void  __startDragThumb (){
		isDragging = true;
		slider.setValueIsAdjusting(true);
		IntPoint mp =slider.getMousePosition ();
		int mx =mp.x ;
		int my =mp.y ;
		IntRectangle tr =thumbRect ;
		if(isVertical()){
			offset = my - tr.y;
		}else{
			offset = mx - tr.x;
		}
		__startHandleDrag();
	}
	
	private void  __stopDragThumb (){
		__stopHandleDrag();
		if(isDragging){
			isDragging = false;
			countThumbRect();
		}
		slider.setValueIsAdjusting(false);
		offset = 0;
	}
	
	private void  __startHandleDrag (){
		if(slider.stage){
			slider.stage.addEventListener(MouseEvent.MOUSE_MOVE, __onMoveThumb, false, 0, true);
			slider.addEventListener(Event.REMOVED_FROM_STAGE, __onMoveThumbRFS, false, 0, true);
			showValueTip();
		}
	}
	private void  __onMoveThumbRFS (Event e ){
		slider.stage.removeEventListener(MouseEvent.MOUSE_MOVE, __onMoveThumb);
		slider.removeEventListener(Event.REMOVED_FROM_STAGE, __onMoveThumbRFS);
	}
	private void  __stopHandleDrag (){
		if(slider.stage){
			__onMoveThumbRFS(null);
		}
		disposValueTip();
	}
	private void  __onMoveThumb (MouseEvent e ){
		scrollThumbToCurrentMousePosition();
		showValueTip();
		e.updateAfterEvent();
	}
	
	protected void  showValueTip (){
		if(slider.getShowValueTip()){
			JToolTip tip =slider.getValueTip ();
			tip.setWaitThenPopupEnabled(false);
			tip.setTipText(slider.getValue()+"");
			if(!tip.isShowing()){
				tip.showToolTip();
			}
			tip.moveLocationRelatedTo(slider.componentToGlobal(slider.getMousePosition()));
		}
	}
	
	protected void  disposValueTip (){
		if(slider.getValueTip() != null){
			slider.getValueTip().disposeToolTip();
		}
	}
	
	protected void  scrollThumbToCurrentMousePosition (){
		IntPoint mp =slider.getMousePosition ();
		int mx =mp.x ;
		int my =mp.y ;
		
		int thumbPos ,minPos int ,maxPos ;
		int halfThumbLength ;
		int sliderValue ;
		IntRectangle paintThumbRect =thumbRect.clone ();
		if(isVertical()){
			halfThumbLength = thumbRect.height / 2;
			thumbPos = my - offset;
			if(!slider.getInverted()){
				maxPos = yPositionForValue(slider.getMinimum()) - halfThumbLength;
				minPos = yPositionForValue(slider.getMaximum() - slider.getExtent()) - halfThumbLength;
			}else{
				minPos = yPositionForValue(slider.getMinimum()) - halfThumbLength;
				maxPos = yPositionForValue(slider.getMaximum() - slider.getExtent()) - halfThumbLength;
			}
			thumbPos = Math.max(minPos, Math.min(maxPos, thumbPos));
			sliderValue = valueForYPosition(thumbPos + halfThumbLength);
			slider.setValue(sliderValue);
			thumbRect.y = yPositionForValue(slider.getValue()) - halfThumbLength;
			paintThumbRect.y = thumbPos;
		}else{
			halfThumbLength = thumbRect.width / 2;
			thumbPos = mx - offset;
			if(slider.getInverted()){
				maxPos = xPositionForValue(slider.getMinimum()) - halfThumbLength;
				minPos = xPositionForValue(slider.getMaximum() - slider.getExtent()) - halfThumbLength;
			}else{
				minPos = xPositionForValue(slider.getMinimum()) - halfThumbLength;
				maxPos = xPositionForValue(slider.getMaximum() - slider.getExtent()) - halfThumbLength;
			}
			thumbPos = Math.max(minPos, Math.min(maxPos, thumbPos));
			sliderValue = valueForXPosition(thumbPos + halfThumbLength);
			slider.setValue(sliderValue);
			thumbRect.x = xPositionForValue(slider.getValue()) - halfThumbLength;
			paintThumbRect.x = thumbPos;
		}
		paintThumb(null, paintThumbRect);
		progressCanvas.graphics.clear();
		paintTrackProgress(new Graphics2D(progressCanvas.graphics), trackDrawRect);
	}

    public Insets  getTrackMargin (){
    	IntRectangle b =slider.getPaintBounds ();
    	countTrackRect(b);
    	
    	Insets insets =new Insets ();
    	insets.top = trackRect.y - b.y;
    	insets.bottom = b.y + b.height - trackRect.y - trackRect.height;
    	insets.left = trackRect.x - b.x;
    	insets.right = b.x + b.width - trackRect.x - trackRect.width;
    	return insets;
    }	
	
	//---------------------
	
	protected int  getPrefferedLength (){
		return 200;
	}
		
     public IntDimension  getPreferredSize (Component c ){
    	IntDimension size ;
    	IntDimension thumbSize =getThumbSize ();
    	int tickLength =this.getTickLength ();
    	int gap =this.getTickTrackGap ();
    	int wide =slider.getPaintTicks ()? gap+tickLength : 0;
    	if(isVertical()){
    		wide += thumbSize.width;
    		size = new IntDimension(wide, Math.max(wide, getPrefferedLength()));
    	}else{
    		wide += thumbSize.height;
    		size = new IntDimension(Math.max(wide, getPrefferedLength()), wide);
    	}
    	return c.getInsets().getOutsideSize(size);
    }

     public IntDimension  getMaximumSize (Component c ){
		return IntDimension.createBigDimension();
    }

	 public IntDimension  getMinimumSize (Component c ){
    	IntDimension size ;
    	IntDimension thumbSize =getThumbSize ();
    	int tickLength =this.getTickLength ();
    	int gap =this.getTickTrackGap ();
    	int wide =slider.getPaintTicks ()? gap+tickLength : 0;
    	if(isVertical()){
    		wide += thumbSize.width;
    		size = new IntDimension(wide, thumbSize.height);
    	}else{
    		wide += thumbSize.height;
    		size = new IntDimension(thumbSize.width, wide);
    	}
    	return c.getInsets().getOutsideSize(size);
    }    	
}


