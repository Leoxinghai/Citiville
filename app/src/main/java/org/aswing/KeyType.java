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
 * KeyType defined with description representing the key sequence with a string, 
 * codeSequence contains the key codes sequence.
 * Same codeSequence will be considered as same key definition.
 * <p>
 * For example "C" and .get(67) mean the C key on the key board.
 * "Ctrl+C" and .get(17, 67) mean the first Ctrl and then C keys.
 * <p>
 * Thanks Romain for his Fever{@link http://fever.riaforge.org} accelerator framworks implementation, 
 * this is a simpler implementation study from his.
 * 
 * @see org.aswing.Keyboard
 * @see org.aswing.KeySequence
 * @author iiley
 */	
public interface KeyType{
	
	/**
	 * Returns the key code sequence. Same code sequence be track as same key definition.
	 * @return an array(uint[]) that contains the key codes sequence
	 */
	Array  getCodeSequence ();	
	
	/**
	 * Returns the string that represent the key sequence.
	 * @return string that represent the key sequence. 
	 */
	String  getDescription ();

}


