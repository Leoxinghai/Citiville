package org.aswing.table;

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
 * Property translator, it return the value of a specified property name.
 * Some property need translate, not directly return the real value, For example a int
 * value for sex, 0 means female, 1 means male, then your can implement your property translator
 * like this:
 * <pre>
 *public  translate (Object info ,String key )*{
 *int sex =info.get(key) ;
 * 		if(sex == 0){
 * 			return "female";
 * 		}else if(sex == 1){
 * 			return "male";
 * 		}else{
 * 			return "no-sex";
 * 		}
 * }
 * </pre>
 * @author iiley
 */
public interface PropertyTranslator{

	Object translate (*info ,String key );

}


