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


 
import flash.text.TextField;

import org.aswing.border.EmptyBorder;
import org.aswing.event.*;
import org.aswing.geom.IntPoint;

/**
 * JOptionPane makes it easy to pop up a standard dialog box that prompts users 
 * for a value or informs them of something.
 * <p>
 * There's some shortcut to do there with static methods like <code>showMessageDialog</code>, 
 * <code>showInputDialog</code>. But if you want to make a hole control of JOptionPane, 
 * you can create a JOptionPane by yourself and append it into a your JFrame.
 * </p>
 * @see #showMessageDialog()
 * @see #showInputDialog()
 * @author iiley
 */
public class JOptionPane extends JPanel {
	public static String OK_STR ="OK";
	public static String CANCEL_STR ="Cancel";
	public static String YES_STR ="Yes";
	public static String NO_STR ="No";
	public static String CLOSE_STR ="Close";
	
	
	public static  int OK =1;//00001
	public static  int CANCEL =2;//00010
	public static  int YES =4;//00100
	public static  int NO =8;//01000
	public static  int CLOSE =16;//10000
	
	private JButton okButton ;
	private JButton cancelButton ;
	private JButton yesButton ;
	private JButton noButton ;
	private JButton closeButton ;
	
	private JPanel centerPane ;
	private JLabel msgLabel ;
	private JTextField inputText ;
	private JPanel buttonPane ;
	private JFrame frame ;
	
	public  JOptionPane (){
		super(new BorderLayout());
		centerPane = SoftBox.createVerticalBox(6);
		msgLabel = new JLabel();
		centerPane.append(AsWingUtils.createPaneToHold(msgLabel, new FlowLayout(FlowLayout.CENTER, 5, 5)));
		inputText = new JTextField();
		JPanel inputContainer =new JPanel(new BorderLayout ());
		inputContainer.setBorder(new EmptyBorder(null, new Insets(0, 8, 0, 8)));
		inputContainer.append(inputText, BorderLayout.CENTER);
		centerPane.append(inputContainer);
		buttonPane = new JPanel(new FlowLayout(FlowLayout.CENTER));
		append(centerPane, BorderLayout.CENTER);
		append(buttonPane, BorderLayout.SOUTH);
	}
	
	public JFrame  getFrame (){
		return frame;
	}
	
	public JTextField  getInputText (){
		return inputText;
	}
	
	public JLabel  getMsgLabel (){
		return msgLabel;
	}
	
	public JButton  getOkButton (){
		if(okButton == null){
			okButton = new JButton(OK_STR);
		}
		return okButton;
	}
	
	public JButton  getCancelButton (){
		if(cancelButton == null){
			cancelButton = new JButton(CANCEL_STR);
		}
		return cancelButton;
	}	
	public JButton  getYesButton (){
		if(yesButton == null){
			yesButton = new JButton(YES_STR);
		}
		return yesButton;
	}
	
	public JButton  getNoButton (){
		if(noButton == null){
			noButton = new JButton(NO_STR);
		}
		return noButton;
	}	
	public JButton  getCloseButton (){
		if(closeButton == null){
			closeButton = new JButton(CLOSE_STR);
		}
		return closeButton;
	}	
	
	public void  addButton (JButton button ){
		buttonPane.append(button);
	}
	private void  setMessage (String msg ){
		msgLabel.setText(msg);
	}
	private void  setIcon (Icon icon ){
		msgLabel.setIcon(icon);
	}	
	private void  addCloseListener (JButton button ){
		JFrame f =getFrame ();
		button .addActionListener (tryToClose f void  (){.();});
	}
	
	/**
	 * showMessageDialog(title:String, msg:String, finishHandler:Function, parentComponent:Component, modal:Boolean, icon:Icon, buttons:Number)<br>
	 * showMessageDialog(title:String, msg:String, finishHandler:Function, parentComponent:Component, modal:Boolean, icon:Icon)<br>
	 * showMessageDialog(title:String, msg:String, finishHandler:Function, parentComponent:Component, modal:Boolean)<br>
	 * showMessageDialog(title:String, msg:String, finishHandler:Function, parentComponent:Component)<br>
	 * showMessageDialog(title:String, msg:String, finishHandler:Function)
	 * <p>
	 * Show a message box with specifield title, message, and icon.
	 * <p>
	 * for example:
	 * <pre>
	 *Function handler =__whenUserConformed ;
	 *JOptionPane pane =showMessageDialog("title","is that OK?",handler ,null ,JOptionPane.YES |JOptionPane.NO);
	 * </pre>
	 * will show a message box with yes and no buttons.
	 * 
	 * @param title the title of the box, can be null
	 * @param msg the message , can be null
	 * @param finishHandler the function(result:Number) to call when user finished input, 
	 * will pass a number as parammeter, its value indicate what user's selection. 
	 * For example CANCEL indicate that user pressed CANCEL button, CLOSE indicate that 
	 * user pressed CLOSE button or just closed the frame by click frame's close button, 
	 * YES indicate that user pressed the YES button etc.
	 * @param parentComponent determines the Frame in which the
	 *		dialog is displayed; can be null
	 * @param modal is the window modal, default is true
	 * @param icon the icon, can be null
	 * @param buttons which buttons need to show.
	 * @param closeBox is close the box frame when user click buttons
	 * @see #OK
	 * @see #CANCEL
	 * @see #YES
	 * @see #NO
	 * @see #CLOSE
	 */
	public static JOptionPane  showMessageDialog (String title ,String msg ,Function finishHandler =null ,Component parentComponent =null ,boolean modal =true ,Icon icon =null ,int buttons =OK ){
		JFrame frame =new JFrame(AsWingUtils.getOwnerAncestor(parentComponent ),title ,modal );
		JOptionPane pane =new JOptionPane ();
		pane.getInputText().setVisible(false);
		pane.setMessage(msg);
		pane.setIcon(icon);
		pane.frame = frame;
		Function handler =finishHandler ;
		if((buttons & OK) == OK){
			pane.addButton(pane.getOkButton());
			pane.addCloseListener(pane.getOkButton());
			pane .getOkButton ().addActionListener (void  (){
				if (handler != null) handler(JOptionPane.OK);
			});
		}
		if((buttons & YES) == YES){
			pane.addButton(pane.getYesButton());
			pane.addCloseListener(pane.getYesButton());
			pane .getYesButton ().addActionListener (void  (){
				if (handler != null) handler(JOptionPane.YES);
			});
		}
		if((buttons & NO) == NO){
			pane.addButton(pane.getNoButton());
			pane.addCloseListener(pane.getNoButton());
			pane .getNoButton ().addActionListener (void  (){
				if (handler != null) handler(JOptionPane.NO);
			});
		}
		if((buttons & CANCEL) == CANCEL){
			pane.addButton(pane.getCancelButton());
			pane.addCloseListener(pane.getCancelButton());
			pane .getCancelButton ().addActionListener (void  (){
				if (handler != null) handler(JOptionPane.CANCEL);
			});
		}
		if((buttons & CLOSE) == CLOSE){
			pane.addButton(pane.getCloseButton());
			pane.addCloseListener(pane.getCloseButton());
			pane .getCloseButton ().addActionListener (void  (){
				if (handler != null) handler(JOptionPane.CLOSE);
			});
		}
		frame.addEventListener(FrameEvent.FRAME_CLOSING, 
			void  (){
				if (handler != null) handler(JOptionPane.CLOSE);
			});
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().append(pane, BorderLayout.CENTER);
		frame.pack();
		IntPoint location =AsWingUtils.getScreenCenterPosition ();
		location.x = Math.round(location.x - frame.getWidth()/2);
		location.y = Math.round(location.y - frame.getHeight()/2);
		frame.setLocation(location);
		frame.show();
		return pane;
	}
	
	/**
	 * showMessageDialog(title:String, msg:String, finishHandler:Function, defaultValue:String, parentComponent:Component, modal:Boolean, icon:Icon)<br>
	 * showMessageDialog(title:String, msg:String, finishHandler:Function, defaultValue:String, parentComponent:Component, modal:Boolean)<br>
	 * showMessageDialog(title:String, msg:String, finishHandler:Function, defaultValue:String, parentComponent:Component)<br>
	 * showMessageDialog(title:String, msg:String, finishHandler:Function, defaultValue:String)<br>
	 * showMessageDialog(title:String, msg:String, finishHandler:Function)
	 * <p>
	 * Show a message box with specifield title, message, and icon and a TextField to requare 
	 * user to input a string.
	 * <p>
	 * for example:
	 * <pre>
	 *Function handler =__whenUserEntered ;
	 *JOptionPane pane =showMessageDialog("title","Please enter your name:",handler ,"yournamehere");
	 * </pre>
	 * will show a message box with OK and CANCEL and a input Texts.
	 * 
	 * @param title the title of the box, can be null
	 * @param msg the message , can be null
	 * @param finishHandler the function(result:String) to call when user finished input, 
	 * will pass a string as parammeter, if it is null indicate that user had pressed 
	 * cancel or just closed the frame, otherwise it is the string what user entered.
	 * @param defaultValue the default value for the input
	 * @param parentComponent determines the Frame in which the
			dialog is displayed; can be null
	 * @param modal is the window modal, default is true
	 * @param icon the icon, can be null
	 */
	public static JOptionPane  showInputDialog (String title ,String msg ,Function finishHandler =null ,String defaultValue ="",Component parentComponent =null ,boolean modal =true ,Icon icon =null ){
		JFrame frame =new JFrame(AsWingUtils.getOwnerAncestor(parentComponent ),title ,modal );
		JOptionPane pane =new JOptionPane ();
		if(defaultValue != ""){
			pane.inputText.setText(defaultValue);
		}
		pane.setMessage(msg);
		pane.setIcon(icon);
		pane.frame = frame;
		
		pane.addButton(pane.getOkButton());
		pane.addCloseListener(pane.getOkButton());
		pane.addButton(pane.getCancelButton());
		pane.addCloseListener(pane.getCancelButton());
		
		Function handler =finishHandler ;
		
		pane.getOkButton().addActionListener(
			void  (){
				if (handler != null) handler(pane.getInputText().getText());
			}
		);
		
		Function cancelHandler =function ()void {
			if (handler != null) handler(null);
		};
		
		pane.getCancelButton().addActionListener(cancelHandler);
		frame.addEventListener(FrameEvent.FRAME_CLOSING, cancelHandler);
			
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().append(pane, BorderLayout.CENTER);
		frame.pack();
		IntPoint location =AsWingUtils.getScreenCenterPosition ();
		location.x = Math.round(location.x - frame.getWidth()/2);
		location.y = Math.round(location.y - frame.getHeight()/2);
		frame.setLocation(location);
		frame.show();
		return pane;
	}
	
}


