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

import flash.events.Event;
import flash.events.FocusEvent;
import flash.events.MouseEvent;
import flash.geom.Matrix;

import org.aswing.*;
import org.aswing.border.BevelBorder;
import org.aswing.border.EmptyBorder;
import org.aswing.colorchooser.JColorMixer;
import org.aswing.colorchooser.PreviewColorIcon;
import org.aswing.colorchooser.VerticalLayout;
import org.aswing.event.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;
import org.aswing.plaf.BaseComponentUI;

/**
 * @private
 */
public class BasicColorMixerUI extends BaseComponentUI {
	
	private JColorMixer colorMixer ;
	private JPanel mixerPanel ;
	private AWSprite HSMC ;
	private AWSprite HSPosMC ;
	private AWSprite LMC ;
	private AWSprite LPosMC ;
	private JLabel previewColorLabel ;
	private PreviewColorIcon previewColorIcon ;
	private JAdjuster AAdjuster ;
	private JAdjuster RAdjuster ;
	private JAdjuster GAdjuster ;
	private JAdjuster BAdjuster ;
	private JAdjuster HAdjuster ;
	private JAdjuster SAdjuster ;
	private JAdjuster LAdjuster ;
	private JTextField hexText ;
			
	public  BasicColorMixerUI (){
		super();		
	}

    protected String  getPropertyPrefix (){
        return "ColorMixer.";
    }
	
     public void  installUI (Component c ){
		colorMixer = JColorMixer(c);
		installDefaults();
		installComponents();
		installListeners();
    }
    
	 public void  uninstallUI (Component c ){
		colorMixer = JColorMixer(c);
		uninstallDefaults();
		uninstallComponents();
		uninstallListeners();
    }
	
	private void  installDefaults (){
		String pp =getPropertyPrefix ();
        LookAndFeel.installColorsAndFont(colorMixer, pp);
		LookAndFeel.installBasicProperties(colorMixer, pp);
        LookAndFeel.installBorderAndBFDecorators(colorMixer, pp);
	}
    private void  uninstallDefaults (){
    	LookAndFeel.uninstallBorderAndBFDecorators(colorMixer);
    }	
    
	private void  installComponents (){
		mixerPanel = createMixerPanel();
		previewColorLabel = createPreviewColorLabel();
		previewColorIcon = createPreviewColorIcon();
		previewColorLabel.setIcon(previewColorIcon);
		hexText = createHexTextField();
		createAdjusters();
		layoutComponents();
		createHSAndL();
		updateSectionVisibles();
    }
	private void  uninstallComponents (){
		colorMixer.removeAll();
    }
        
	private void  installListeners (){
		colorMixer.addEventListener(InteractiveEvent.STATE_CHANGED, __colorSelectionChanged);
		
		AAdjuster.addActionListener(__AAdjusterValueAction);
		AAdjuster.addStateListener(__AAdjusterValueChanged);
		RAdjuster.addActionListener(__RGBAdjusterValueAction);
		RAdjuster.addStateListener(__RGBAdjusterValueChanged);
		GAdjuster.addActionListener(__RGBAdjusterValueAction);
		GAdjuster.addStateListener(__RGBAdjusterValueChanged);
		BAdjuster.addActionListener(__RGBAdjusterValueAction);
		BAdjuster.addStateListener(__RGBAdjusterValueChanged);
		
		HAdjuster.addActionListener(__HLSAdjusterValueAction);
		HAdjuster.addStateListener(__HLSAdjusterValueChanged);
		LAdjuster.addActionListener(__HLSAdjusterValueAction);
		LAdjuster.addStateListener(__HLSAdjusterValueChanged);
		SAdjuster.addActionListener(__HLSAdjusterValueAction);
		SAdjuster.addStateListener(__HLSAdjusterValueChanged);

		hexText.addActionListener(__hexTextAction);
		hexText.getTextField().addEventListener(Event.CHANGE, __hexTextChanged);
		hexText.getTextField().addEventListener(FocusEvent.FOCUS_OUT, __hexTextAction);
	}
    private void  uninstallListeners (){
    	colorMixer.removeEventListener(InteractiveEvent.STATE_CHANGED, __colorSelectionChanged);
    }
    
    /**
     * Override this method to change different layout
     */
    private void  layoutComponents (){
    	colorMixer.setLayout(new BorderLayout(0, 4));
    	
    	Container top =SoftBox.createHorizontalBox(4,SoftBoxLayout.CENTER );
    	top.append(mixerPanel);
    	top.append(previewColorLabel);
    	top.setUIElement(true);
    	colorMixer.append(top, BorderLayout.NORTH);
    	
    	Container bottom =SoftBox.createHorizontalBox(4,SoftBoxLayout.CENTER );
    	Container p =new JPanel(new VerticalLayout(VerticalLayout.RIGHT ,4));
    	p.append(createLabelToComponet(getALabel(), AAdjuster));
    	Component cube =new JPanel ();
    	cube.setPreferredSize(p.getComponent(0).getPreferredSize());
    	p.append(cube);
    	p.append(createLabelToComponet(getHexLabel(), hexText));
    	bottom.append(p);
    	
    	p = new JPanel(new VerticalLayout(VerticalLayout.RIGHT, 4));
    	p.append(createLabelToComponet(getRLabel(), RAdjuster));
    	p.append(createLabelToComponet(getGLabel(), GAdjuster));
    	p.append(createLabelToComponet(getBLabel(), BAdjuster));
    	bottom.append(p);
    	
    	p = new JPanel(new VerticalLayout(VerticalLayout.RIGHT, 4));
    	p.append(createLabelToComponet(getHLabel(), HAdjuster));
    	p.append(createLabelToComponet(getSLabel(), SAdjuster));
    	p.append(createLabelToComponet(getLLabel(), LAdjuster));
    	bottom.append(p);
    	
    	bottom.setUIElement(true);
    	colorMixer.append(bottom, BorderLayout.SOUTH);
    }
    
    private Container  createLabelToComponet (String label ,Component component ){
    	JPanel p =new JPanel(new FlowLayout(FlowLayout.LEFT ,0,0));
    	p.append(new JLabel(label));
    	p.append(component);

    	component .addEventListener (AWEvent .HIDDEN ,void  (){
    		p.setVisible(false);
    	});
    	component .addEventListener (AWEvent .SHOWN ,void  (){
    		p.setVisible(true);
    	});
    	
    	return p;
    }
    //----------------------------------------------------------------------
    
    
    private IntDimension  getMixerPaneSize (){
		double crm =getColorRectMargin ()*2;
		IntDimension hss =getHSSize ();
		IntDimension ls =getLSize ();
		hss.change(crm, crm);
		ls.change(crm, crm);
		IntDimension size =new IntDimension(hss.width +getHS2LGap ()+ls.width ,
							Math.max(hss.height, ls.height));
		size.change(getMCsMarginSize(), getMCsMarginSize());
		return size;
	}
	
	private int  getMCsMarginSize (){
		return 4;
	}
	
	private int  getColorRectMargin (){
		return 1;
	}
    
    private IntDimension  getHSSize (){
    	return new IntDimension(120, 100);
    }
    
    private int  getHS2LGap (){
    	return 8;
    }
    
    private IntDimension  getLSize (){
    	return new IntDimension(40, 100);
    }
	
	private int  getLStripWidth (){
		return 20; //half of getLSize().width
	}
	
    private ASColor  getSelectedColor (){
    	ASColor c =colorMixer.getSelectedColor ();
    	if(c == null) return ASColor.BLACK;
    	return c;
    }
    
    private void  setSelectedColor (ASColor c ){
    	color_at_views = c;
    	colorMixer.setSelectedColor(c);
    }
    
    private void  updateMixerAllItems (){
    	updateMixerAllItemsWithColor(getSelectedColor());
	}	
	
	private double  getHFromPos (IntPoint p ){
		return (p.x - getHSColorsStartX()) / getHSSize().width;
	}
	private double  getSFromPos (IntPoint p ){
		return 1 - ((p.y - getHSColorsStartY()) / getHSSize().height);
	}
	private double  getLFromPos (IntPoint p ){
		return 1 - ((p.y - getLColorsStartY()) / getLSize().height);
	}
	
	private double  getHAdjusterValueFromH (double h ){
		return h * (HAdjuster.getMaximum()- HAdjuster.getMinimum());
	}
	private double  getSAdjusterValueFromS (double s ){
		return s * (SAdjuster.getMaximum()- SAdjuster.getMinimum());
	}
	private double  getLAdjusterValueFromL (double l ){
		return l * (LAdjuster.getMaximum()- LAdjuster.getMinimum());
	}
	
	private double  getHFromHAdjuster (){
		return HAdjuster.getValue() / (HAdjuster.getMaximum()- HAdjuster.getMinimum());
	}
	private boolean HAdjusterUpdating ;
	private void  updateHAdjusterWithH (double h ){
		HAdjusterUpdating = true;
		HAdjuster.setValue(getHAdjusterValueFromH(h));
		HAdjusterUpdating = false;
	}
	private double  getSFromSAdjuster (){
		return SAdjuster.getValue() / (SAdjuster.getMaximum()- SAdjuster.getMinimum());
	}
	private boolean SAdjusterUpdating ;
	private void  updateSAdjusterWithS (double s ){
		SAdjusterUpdating = true;
		SAdjuster.setValue(getSAdjusterValueFromS(s));
		SAdjusterUpdating = false;
	}
	private double  getLFromLAdjuster (){
		return LAdjuster.getValue() / (LAdjuster.getMaximum()- LAdjuster.getMinimum());
	}
	private boolean LAdjusterUpdating ;
	private void  updateLAdjusterWithL (double l ){
		LAdjusterUpdating = true;
		LAdjuster.setValue(getLAdjusterValueFromL(l));
		LAdjusterUpdating = false;
	}
	
	private boolean RAdjusterUpdating ;
	private void  updateRAdjusterWithL (double v ){
		RAdjusterUpdating = true;
		RAdjuster.setValue(v);
		RAdjusterUpdating = false;
	}
	private boolean GAdjusterUpdating ;
	private void  updateGAdjusterWithG (double v ){
		GAdjusterUpdating = true;
		GAdjuster.setValue(v);
		GAdjusterUpdating = false;
	}
	private boolean BAdjusterUpdating ;
	private void  updateBAdjusterWithB (double v ){
		BAdjusterUpdating = true;
		BAdjuster.setValue(v);
		BAdjusterUpdating = false;
	}
	
	private double  getAFromAAdjuster (){
		return AAdjuster.getValue()/100;
	}
	private boolean AAdjusterUpdating ;
	private void  updateAAdjusterWithA (double v ){
		AAdjusterUpdating = true;
		AAdjuster.setValue(v*100);
		AAdjusterUpdating = false;
	}
	
	private ASColor  getColorFromRGBAAdjusters (){
		double rr =RAdjuster.getValue ();
		double gg =GAdjuster.getValue ();
		double bb =BAdjuster.getValue ();
		return ASColor.getASColor(rr, gg, bb, getAFromAAdjuster());
	}
	private ASColor  getColorFromHLSAAdjusters (){
		double hh =getHFromHAdjuster ();
		double ll =getLFromLAdjuster ();
		double ss =getSFromSAdjuster ();
		return HLSA2ASColor(hh, ll, ss, getAFromAAdjuster());
	}
	//-----------------------------------------------------------------------
	 public void  paint (Component c ,Graphics2D g ,IntRectangle b ){
		super.paint(c, g, b);
		updateSectionVisibles();
		updateMixerAllItems();
	}
	private void  updateSectionVisibles (){
		hexText.setVisible(colorMixer.isHexSectionVisible());
		AAdjuster.setVisible(colorMixer.isAlphaSectionVisible());
	}
	//------------------------------------------------------------------------
	private void  __AAdjusterValueChanged (Event e ){
		if(!AAdjusterUpdating){
			updatePreviewColorWithHSL();
		}
	}
	private void  __AAdjusterValueAction (Event e ){
		colorMixer.setSelectedColor(getColorFromMCViewsAndAAdjuster());
	}
	private void  __RGBAdjusterValueChanged (Event e ){
		if(RAdjusterUpdating || GAdjusterUpdating || BAdjusterUpdating){
			return;
		}
		ASColor color =getColorFromRGBAAdjusters ();
		Object hls =getHLS(color );
		double hh =hls.h ;
		double ss =hls.s ;
		double ll =hls.l ;
		H_at_HSMC = hh;
		S_at_HSMC = ss;
		L_at_LMC  = ll;
		color_at_views = color;
		updateHSLPosFromHLS(H_at_HSMC, L_at_LMC, S_at_HSMC);
		updateHexTextWithColor(color);
		updateHLSAdjustersWithHLS(hh, ll, ss);
		updatePreviewWithColor(color);
	}
	private void  __RGBAdjusterValueAction (Event e ){
		colorMixer.setSelectedColor(getColorFromRGBAAdjusters());
	}
	private void  __HLSAdjusterValueChanged (Event e ){
		if(HAdjusterUpdating || LAdjusterUpdating || SAdjusterUpdating){
			return;
		}
		H_at_HSMC = getHFromHAdjuster();
		L_at_LMC  = getLFromLAdjuster();
		S_at_HSMC = getSFromSAdjuster();
		ASColor color =HLSA2ASColor(H_at_HSMC ,L_at_LMC ,S_at_HSMC ,getAFromAAdjuster ());
		color_at_views = color;
		updateHSLPosFromHLS(H_at_HSMC, L_at_LMC, S_at_HSMC);
		updateHexTextWithColor(color);
		updateRGBAdjustersWithColor(color);
		updatePreviewWithColor(color);
	}
	private void  __HLSAdjusterValueAction (Event e ){
		colorMixer.setSelectedColor(getColorFromHLSAAdjusters());
	}
	private void  __hexTextChanged (Event e ){
		if(hexTextUpdating){
			return;
		}
		ASColor color =getColorFromHexTextAndAdjuster ();
		Object hls =getHLS(color );
		double hh =hls.h ;
		double ss =hls.s ;
		double ll =hls.l ;
		H_at_HSMC = hh;
		S_at_HSMC = ss;
		L_at_LMC  = ll;
		color_at_views = color;
		updateHSLPosFromHLS(H_at_HSMC, L_at_LMC, S_at_HSMC);
		updateHLSAdjustersWithHLS(hh, ll, ss);
		updateRGBAdjustersWithColor(color);
		updatePreviewWithColor(color);
	}
	private void  __hexTextAction (Event e ){
		colorMixer.setSelectedColor(getColorFromHexTextAndAdjuster());
	}
	
	private double H_at_HSMC ;
	private double S_at_HSMC ;
	private double L_at_LMC ;
	private ASColor color_at_views ;
	
	private void  __colorSelectionChanged (Event e ){
		ASColor color =getSelectedColor ();
		previewColorIcon.setColor(color);
		previewColorLabel.repaint();
		if(!color.equals(color_at_views)){
			updateMixerAllItemsWithColor(color);
			color_at_views = color;
		}
	}	
		
	private void  createHSAndL (){
		HSMC = new AWSprite();
		HSPosMC = new AWSprite();
		LMC = new AWSprite();
		LPosMC = new AWSprite();
		
		mixerPanel.addChild(HSMC);
		mixerPanel.addChild(HSPosMC);
		mixerPanel.addChild(LMC);
		mixerPanel.addChild(LPosMC);

		paintHSMC();
		paintHSPosMC();
		paintLMC();
		paintLPosMC();
		
		HSMC.addEventListener(MouseEvent.MOUSE_DOWN, __HSMCPress);
		HSMC.addEventListener(ReleaseEvent.RELEASE, __HSMCRelease);
		HSMC.addEventListener(ReleaseEvent.RELEASE_OUT_SIDE, __HSMCRelease);
		LMC.addEventListener(MouseEvent.MOUSE_DOWN, __LMCPress);
		LMC.addEventListener(ReleaseEvent.RELEASE, __LMCRelease);
		LMC.addEventListener(ReleaseEvent.RELEASE_OUT_SIDE, __LMCRelease);
	}
	private void  __HSMCPress (Event e ){
		HSMC.addEventListener(MouseEvent.MOUSE_MOVE, __HSMCDragging);
		__HSMCDragging(null);
	}
	private void  __HSMCRelease (Event e ){
		HSMC.removeEventListener(MouseEvent.MOUSE_MOVE, __HSMCDragging);
		countHSWithMousePosOnHSMCAndUpdateViews();
		setSelectedColor(getColorFromMCViewsAndAAdjuster());
	}
	private void  __HSMCDragging (Event e ){
		countHSWithMousePosOnHSMCAndUpdateViews();
	}
	
	private void  __LMCPress (Event e ){
		LMC.addEventListener(MouseEvent.MOUSE_MOVE, __LMCDragging);
		__LMCDragging(null);
	}
	private void  __LMCRelease (Event e ){
		LMC.removeEventListener(MouseEvent.MOUSE_MOVE, __LMCDragging);		
		countLWithMousePosOnLMCAndUpdateViews();
		setSelectedColor(getColorFromMCViewsAndAAdjuster());
	}
	private void  __LMCDragging (Event e ){
		countLWithMousePosOnLMCAndUpdateViews();
	}
	
	private void  countHSWithMousePosOnHSMCAndUpdateViews (){
		IntPoint p =mixerPanel.getMousePosition ();
		Object hs =getHSWithPosOnHSMC(p );
		HSPosMC.x = p.x;
		HSPosMC.y = p.y;
		double h =hs.h ;
		double s =hs.s ;
		H_at_HSMC = h;
		S_at_HSMC = s;
		updateOthersWhenHSMCAdjusting();
	}
	private Object  getHSWithPosOnHSMC (IntPoint p ){
		IntDimension hsSize =getHSSize ();
		double minX =getHSColorsStartX ();
		double maxX =minX +hsSize.width ;
		double minY =getHSColorsStartY ();
		double maxY =minY +hsSize.height ;
		p.x = Math.max(minX, Math.min(maxX, p.x));
		p.y = Math.max(minY, Math.min(maxY, p.y));
		double h =getHFromPos(p );
		double s =getSFromPos(p );
		return {h:h, s:s};
	}
	
	private void  countLWithMousePosOnLMCAndUpdateViews (){
		IntPoint p =mixerPanel.getMousePosition ();
		double ll =getLWithPosOnLMC(p );
		LPosMC.y = p.y;
		L_at_LMC = ll;
		updateOthersWhenLMCAdjusting();
	}
	private double  getLWithPosOnLMC (IntPoint p ){
		IntDimension lSize =getLSize ();
		double minY =getLColorsStartY ();
		double maxY =minY +lSize.height ;
		p.y = Math.max(minY, Math.min(maxY, p.y));
		return getLFromPos(p);
	}
	
	private ASColor  getColorFromMCViewsAndAAdjuster (){
		return HLSA2ASColor(H_at_HSMC, L_at_LMC, S_at_HSMC, getAFromAAdjuster());
	}
	
	private void  updatePreviewColorWithHSL (){
		updatePreviewWithColor(getColorFromMCViewsAndAAdjuster());
	}
	
	private void  updatePreviewWithColor (ASColor color ){
		previewColorIcon.setCurrentColor(color);
		previewColorLabel.repaint();
    	colorMixer.getModel().fireColorAdjusting(color);
	}
	
	private void  updateOthersWhenHSMCAdjusting (){
		paintLMCWithHS(H_at_HSMC, S_at_HSMC);
		ASColor color =getColorFromMCViewsAndAAdjuster ();
		updateHexTextWithColor(color);
		updateHLSAdjustersWithHLS(H_at_HSMC, L_at_LMC, S_at_HSMC);
		updateRGBAdjustersWithColor(color);
		updatePreviewWithColor(color);
	}
	
	private void  updateOthersWhenLMCAdjusting (){
		ASColor color =getColorFromMCViewsAndAAdjuster ();
		updateHexTextWithColor(color);
		LAdjuster.setValue(getLAdjusterValueFromL(L_at_LMC));
		updateRGBAdjustersWithColor(color);
		updatePreviewWithColor(color);
	}
	    
	//*******************************************************************************
	//       Override these methods to easiy implement different look
	//******************************************************************************
	
	private String  getALabel (){
		return "Alpha:";
	}
	private String  getRLabel (){
		return "R:";
	}
	private String  getGLabel (){
		return "G:";
	}
	private String  getBLabel (){
		return "B:";
	}
	private String  getHLabel (){
		return "H:";
	}
	private String  getSLabel (){
		return "S:";
	}
	private String  getLLabel (){
		return "L:";
	}
	private String  getHexLabel (){
		return "#";
	}
	
	private void  updateMixerAllItemsWithColor (ASColor color ){
		Object hls =getHLS(color );
		double hh =hls.h ;
		double ss =hls.s ;
		double ll =hls.l ;
		H_at_HSMC = hh;
		S_at_HSMC = ss;
		L_at_LMC  = ll;
		
		updateHSLPosFromHLS(hh, ll, ss);
		
		previewColorIcon.setColor(color);
		previewColorLabel.repaint();
		
		updateRGBAdjustersWithColor(color);
		updateHLSAdjustersWithHLS(hh, ll, ss);
		updateAlphaAdjusterWithColor(color);
		updateHexTextWithColor(color);
	}
	
	private void  updateHSLPosFromHLS (double hh ,double ll ,double ss ){
		IntDimension hsSize =getHSSize ();
		HSPosMC.x = hh*hsSize.width + getHSColorsStartX();
		HSPosMC.y = (1-ss)*hsSize.height + getHSColorsStartY();
		paintLMCWithHS(hh, ss);
		LPosMC.y = getLColorsStartY() + (1-ll)*getLSize().height;
	}
		
	private void  updateRGBAdjustersWithColor (ASColor color ){

		updateRAdjusterWithL(color.getRed());
		updateGAdjusterWithG(color.getGreen());
		updateBAdjusterWithB(color.getBlue());
	}
	
	private void  updateHLSAdjustersWithHLS (double h ,double l ,double s ){
		updateHAdjusterWithH(h);
		updateLAdjusterWithL(l);
		updateSAdjusterWithS(s);
	}
	
	private void  updateAlphaAdjusterWithColor (ASColor color ){
		updateAAdjusterWithA(color.getAlpha());
	}

    private ASColor hexTextColor ;
    private boolean hexTextUpdating ;
    private void  updateHexTextWithColor (ASColor color ){
    	if(!color.equals(hexTextColor)){
	    	hexTextColor = color;
	    	String hex ;
	    	if(color == null){
	    		hex = "000000";
	    	}else{
	    		hex = color.getRGB().toString(16);
	    	}
	    	for(double i =6-hex.length ;i >0;i --){
	    		hex = "0" + hex;
	    	}
	    	hex = hex.toUpperCase();
	    	hexTextUpdating = true;
	    	hexText.setText(hex);
	    	hexTextUpdating = false;
    	}
    }
	
    private ASColor  getColorFromHexTextAndAdjuster (){
    	String text =hexText.getText ();
    	double rgb =parseInt("0x"+text );
    	return new ASColor(rgb, getAFromAAdjuster());
    }
	
	private double  getHSColorsStartX (){
		return getMCsMarginSize() + getColorRectMargin();
	}
	private double  getHSColorsStartY (){
		return getMCsMarginSize() + getColorRectMargin();
	}
	private double  getLColorsStartY (){
		return getMCsMarginSize() + getColorRectMargin();
	}
	//private double  getLColorsStartX (){
	//	return getHSSize().width + getMCsMarginSize() + getColorRectMargin()*2 + getHS2LGap();
	//}	
	
	private void  paintHSMC (){
		HSMC.graphics.clear();
		Graphics2D g =new Graphics2D(HSMC.graphics );
		double offset =getMCsMarginSize ();
		double thickness =getColorRectMargin ();
		IntDimension hsSize =getHSSize ();
		double H =hsSize.width ;
		double S =hsSize.height ;
		
		double w =H +thickness *2;
		double h =S +thickness *2;
		g.drawLine(new Pen(ASColor.GRAY, thickness), 
					offset+thickness/2, offset+thickness/2, 
					offset+w-thickness, 
					offset+thickness/2);
		g.drawLine(new Pen(ASColor.GRAY, 1), 
					offset+thickness/2, offset+thickness/2, 
					offset+thickness/2, 
					offset+h-thickness);
		
		offset += thickness;

		Array colors =.get(0 ,0x808080) ;
		Array alphas =.get(1 ,1) ;
		Array ratios =.get(0 ,255) ;
		Matrix matrix =new Matrix ();
		matrix.createGradientBox(1, S, (90/180)*Math.PI, offset, 0); 
		GradientBrush brush =new GradientBrush(GradientBrush.LINEAR ,colors ,alphas ,ratios ,matrix );
		for(double x =0;x <H ;x ++){
			ASColor pc =HLSA2ASColor(x /H ,0.5,1,100);
			colors.put(0,  pc.getRGB());
			matrix.tx = x+offset;
			g.fillRectangle(brush, x+offset, offset, 1, S);
		}
	}
	
	private void  paintHSPosMC (){
		HSPosMC.graphics.clear();
		Graphics2D g =new Graphics2D(HSPosMC.graphics );
		g.drawLine(new Pen(ASColor.BLACK, 2), -6, 0, -3, 0);
		g.drawLine(new Pen(ASColor.BLACK, 2), 6, 0, 3, 0);
		g.drawLine(new Pen(ASColor.BLACK, 2), 0, -6, 0, -3);
		g.drawLine(new Pen(ASColor.BLACK, 2), 0, 6, 0, 3);
	}
	private void  paintLMCWithHS (double hh ,double ss ){
		LMC.graphics.clear();
		Graphics2D g =new Graphics2D(LMC.graphics );
		double x =getHSSize ().width +getMCsMarginSize ()+getColorRectMargin ()*2+getHS2LGap ();
		double y =getMCsMarginSize ();
		
		double thickness =getColorRectMargin ();
		IntDimension lSize =getLSize ();
		double w =getLStripWidth ()+thickness *2;
		double h =lSize.height +thickness *2;
		
		g.drawLine(new Pen(ASColor.GRAY, thickness), 
					x+thickness/2, y+thickness/2, 
					x+w-thickness, 
					y+thickness/2);
		g.drawLine(new Pen(ASColor.GRAY, 1), 
					x+thickness/2, y+thickness/2, 
					x+thickness/2, 
					y+h-thickness);
		x += thickness;
		y += thickness;
		w = getLStripWidth();
		h = lSize.height;

		Array colors =.get(0xFFFFFF ,HLSA2ASColor(hh ,0.5,ss ,100).getRGB (),0) ;
		Array alphas =.get(1 ,1,1) ;
		Array ratios =.get(0 ,127.5,255) ;
		Matrix matrix =new Matrix ();
		matrix.createGradientBox(w, h, (90/180)*Math.PI, x, y); 
		GradientBrush brush =new GradientBrush(GradientBrush.LINEAR ,colors ,alphas ,ratios ,matrix );
		g.fillRectangle(brush, x, y, w, h);		
	}
	private void  paintLMCWithColor (ASColor color ){
		Object hls =getHLS(color );
		double hh =hls.h ;
		double ss =hls.s ;
		paintLMCWithHS(hh, ss);
	}
	private void  paintLMC (){
		paintLMCWithColor(getSelectedColor());
	}
	private void  paintLPosMC (){
		LPosMC.graphics.clear();
		Graphics2D g =new Graphics2D(LPosMC.graphics );
		g.fillPolygon(new SolidBrush(ASColor.BLACK), [
		new IntPoint(0, 0), new IntPoint(4, -4), new IntPoint(4, 4)]);
		LPosMC.x = getHSSize().width + getMCsMarginSize() + getColorRectMargin()*2 
					+ getHS2LGap() + getLStripWidth() + 1;
	}
	
	private JPanel  createMixerPanel (){
		JPanel p =new JPanel ();
		p.setBorder(null); //esure there is no border
		p.setPreferredSize(getMixerPaneSize());
		return p;
	}
	
	private PreviewColorIcon  createPreviewColorIcon (){
		return new PreviewColorIcon(46, 100, PreviewColorIcon.VERTICAL);
	}
	
	private JLabel  createPreviewColorLabel (){
		JLabel label =new JLabel ();
		double margin =getMCsMarginSize ();
		BevelBorder bb =new BevelBorder(null ,BevelBorder.LOWERED );
		bb.setThickness(1);
		label.setBorder(new EmptyBorder(bb, new Insets(margin, 0, margin, 0))); 
		return label;
	}
	
	private JTextField  createHexTextField (){
		return new JTextField("000000", 6);
	}
	
	private void  createAdjusters (){
		AAdjuster = createAAdjuster();
		RAdjuster = createRAdjuster();
		GAdjuster = createGAdjuster();
		BAdjuster = createBAdjuster();
		HAdjuster = createHAdjuster();
		SAdjuster = createSAdjuster();
		LAdjuster = createLAdjuster();
	}
	
	private JAdjuster  createAAdjuster (){
		JAdjuster adjuster =new JAdjuster(4,JAdjuster.VERTICAL );		
		adjuster.setValueTranslator(
			String  (double value ){
				return Math.round(value) + "%";
			});
		adjuster.setValues(100, 0, 0, 100);
		return adjuster;
	}
	private JAdjuster  createRAdjuster (){
		JAdjuster adjuster =new JAdjuster(4,JAdjuster.VERTICAL );
		adjuster.setValues(255, 0, 0, 255);
		return adjuster;
	}
	private JAdjuster  createGAdjuster (){
		JAdjuster adjuster =new JAdjuster(4,JAdjuster.VERTICAL );
		adjuster.setValues(255, 0, 0, 255);
		return adjuster;
	}
	private JAdjuster  createBAdjuster (){
		JAdjuster adjuster =new JAdjuster(4,JAdjuster.VERTICAL );
		adjuster.setValues(255, 0, 0, 255);
		return adjuster;
	}
	private JAdjuster  createHAdjuster (){
		JAdjuster adjuster =new JAdjuster(4,JAdjuster.VERTICAL );
		adjuster.setValues(0, 0, 0, 360);
		adjuster.setValueTranslator(
			String  (double value ){
				return Math.round(value) + "°";
			});
		return adjuster;
	}
	private JAdjuster  createSAdjuster (){
		JAdjuster adjuster =new JAdjuster(4,JAdjuster.VERTICAL );
		adjuster.setValues(0, 0, 0, 100);
		adjuster.setValueTranslator(
			String  (double value ){
				return Math.round(value) + "%";
			});
		return adjuster;
	}
	private JAdjuster  createLAdjuster (){
		JAdjuster adjuster =new JAdjuster(4,JAdjuster.VERTICAL );
		adjuster.setValues(0, 0, 0, 100);
		adjuster.setValueTranslator(
			String  (double value ){
				return Math.round(value) + "%";
			});
		return adjuster;
	}
	
	//----------------Tool functions--------------------
	
	private static Object  getHLS (ASColor color ){
		double rr =color.getRed ()/255;
		double gg =color.getGreen ()/255;
		double bb =color.getBlue ()/255;
		Object hls =rgb2Hls(rr ,gg ,bb );
		return hls;
	}
	
	/**
	 * H, L, S -> .get(0, 1), alpha -> .get(0, 100)
	 */
	private static ASColor  HLSA2ASColor (double H ,double L ,double S ,double alpha ){
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
		return ASColor.getASColor(r, g, b, alpha);
	}
	
	private static double  hlsValue (double p1 ,double p2 ,double h ){
	   if (h > 360) h = h - 360;
	   if (h < 0)   h = h + 360;
	   if (h < 60 ) return p1 + (p2-p1)*h/60;
	   if (h < 180) return p2;
	   if (h < 240) return p1 + (p2-p1)*(240-h)/60;
	   return p1;
	}
	
	private static Object  rgb2Hls (double rr ,double gg ,double bb ){
	   // Static method to compute HLS from RGB. The r,g,b triplet is between
	   // .get(0,1), h, l, s are .get(0,1).
	
		double rnorm ,gnorm double ,bnorm ;
		double minval ,maxval double ,msum ,mdiff ;
		double r ,g double ,b ;
	   	double hue ,light double ,satur ;
	   	
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
		light = 0.5 * msum;
		if (maxval != minval) {
			rnorm = (maxval - r)/mdiff;
			gnorm = (maxval - g)/mdiff;
			bnorm = (maxval - b)/mdiff;
		} else {
			satur = hue = 0;
			return {h:hue, l:light, s:satur};
		}
		
		if (light < 0.5)
		  satur = mdiff/msum;
		else
		  satur = mdiff/(2.0 - msum);
		
		if (r == maxval)
		  hue = 60.0 * (6.0 + bnorm - gnorm);
		else if (g == maxval)
		  hue = 60.0 * (2.0 + rnorm - bnorm);
		else
		  hue = 60.0 * (4.0 + gnorm - rnorm);
		
		if (hue > 360)
			hue = hue - 360;
		hue/=360;
		return {h:hue, l:light, s:satur};
	}	
}


