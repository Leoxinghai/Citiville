/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.util;

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


import flash.utils.getQualifiedClassName;
import flash.system.ApplicationDomain;
import flash.display.DisplayObject;
import org.aswing.flyfish.css.property.*;
import org.aswing.flyfish.awml.property.*;

public class Reflection{

	public static ValueDecoder inst1 ;
	public static ButtonIconDecoder inst2 ;
	public static GroundDecoratorDecoder inst3 ;
	public static FiltersDecoder inst4 ;
	public static ValueInjector inst5 ;
	public static ButtonMarginInjector inst6 ;
	public static FiltersInjector inst7 ;
	public static ColorDecoder inst8 ;
	public static IconInjector inst9 ;
	public static ButtonSkinDecoder inst10 ;
	public static TextFiltersInjector inst11 ;
	public static StringDecoder inst12 ;
	public static IntDecoder inst13 ;
	public static FontDecoder inst14 ;
	public static DecoratorInjector inst15 ;
	public static IconDecoder inst16 ;
	public static SimpleInjector inst17 ;
	public static BorderDecoder inst18 ;
	public static LayoutDecoder inst19 ;
	public static NumberDecoder inst20 ;
	public static ButtonSkinInjector inst21 ;
	public static ButtonIconInjector inst22 ;
	public static BooleanDecoder inst23 ;
	public static LayoutInjector inst24 ;

	public static LayoutSerializer inst30 ;
	public static ColorSerializer inst31 ;
	public static IntDimensionSerializer inst32 ;
	public static IntSerializer inst33 ;
	public static ScaleModeSerializer inst34 ;
	public static AssetValue inst35 ;
	public static ArraySerializer inst36 ;
	public static AssetSerializer inst37 ;
	public static BorderSerializer inst38 ;
	public static OrientationSerializer inst39 ;
	public static AxisSerializer inst40 ;
	public static StringSerializer inst41 ;
	public static ScopeSerializer inst42 ;
	public static IntPointSerializer inst43 ;
	public static AlignSerializer inst44 ;
	public static EmptySerializer inst45 ;
	public static FontSerializer inst46 ;
	public static BooleanSerializer inst47 ;
	public static NumberSerializer inst48 ;


	public static DisplayObject  createDisplayObjectInstance (String fullClassName ,ApplicationDomain applicationDomain =null ){
		return createInstance(fullClassName, applicationDomain);
	}

	public static Object createInstance (String fullClassName ,ApplicationDomain applicationDomain =null ) {
		Class assetClass =getClass(fullClassName ,applicationDomain );
		if(assetClass != null){
			return new assetClass();
		}
		return null;
	}

	public static Class  getClass (String fullClassName ,ApplicationDomain applicationDomain =null ){
		if(applicationDomain == null){
			applicationDomain = ApplicationDomain.currentDomain;
		}
		Class assetClass =applicationDomain.getDefinition(fullClassName )as Class ;
		return assetClass;
	}

	public static String  getFullClassName (*)o {
		return getQualifiedClassName(o);
	}

	public static String  getClassName (*)o {
		String name =getFullClassName(o );
		int lastI =name.lastIndexOf(".");
		if(lastI >= 0){
			name = name.substr(lastI+1);
		}
		return name;
	}

	public static String  getPackageName (*)o {
		String name =getFullClassName(o );
		int lastI =name.lastIndexOf(".");
		if(lastI >= 0){
			return name.substring(0, lastI);
		}else{
			return "";
		}
	}

}


