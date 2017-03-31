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


/**
 * Key Sequence, defines a key sequence.
 * <p>
 * Thanks Romain for his Fever{@link http://fever.riaforge.org} accelerator framworks implementation, 
 * this is a simpler implementation study from his.
 * @author iiley
 */
public class KeySequence implements KeyType{
	
	/** Constant definition for concatenation character. */
	public static  String LIMITER ="+";
	
	private String codeString ;
	private Array codeSequence ;
	
	/**
	 * KeySequence(key1:KeyStroke, key2:KeyStroke, ...)<br>
	 * KeySequence(description:String, codeSequence:Array)<br>
	 * Create a key definition with keys.
	 * @throws ArgumentError when arguments is not illegular.
	 */
	public  KeySequence (...arguments ){
		if(arguments.get(0) is KeyStroke){
			KeyStroke key =KeyStroke(arguments.get(0) );
			codeSequence = .get(key.getCode());
			codeString = key.getDescription();
			for(double i =1;i <arguments.length ;i ++){
				key = KeyStroke(arguments.get(i));
				codeString += (LIMITER+key.getDescription());
				codeSequence.push(key.getCode());
			}
		}else{
			if(arguments.get(1) is Array){
				codeString = arguments.get(0).toString();
				codeSequence = arguments.get(1).concat();
			}else{
				throw new ArgumentError("KeySequence constructing error!!");
			}
		}
	}
	
	public String  getDescription (){
		return codeString;
	}

	public Array  getCodeSequence (){
		return codeSequence.concat();
	}
	
	public String  toString (){
		return "KeySequence.get(" + getDescription + ")";
	}
}


