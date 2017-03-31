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

import flash.utils.getDefinitionByName;
import flash.utils.getQualifiedClassName;
import flash.net.registerClassAlias;
import flash.utils.ByteArray;


public class ObjectUtils
	/**
	 * Deep clone object using thiswind@gmail.com 's solution
	 */
	public static  baseClone (*)source *{
		String typeName =getQualifiedClassName(source );
        String packageName =typeName.split("::").get(1) ;
        Class type =Class(getDefinitionByName(typeName ));

        registerClassAlias(packageName, type);

        ByteArray copier =new ByteArray ();
        copier.writeObject(source);
        copier.position = 0;
        return copier.readObject();
	}

	/**
	 * Checks wherever passed-in value is <code>String</code>.
	 */
	public static boolean  isString (Object value){
		return ( typeof(value) == "string" || value is String );
	}

	/**
	 * Checks wherever passed-in value is <code>Number</code>.
	 */
	public static boolean  isNumber (Object value){
		return ( typeof(value) == "number" || value is Number );
	}

	/**
	 * Checks wherever passed-in value is <code>Boolean</code>.
	 */
	public static boolean  isBoolean (Object value){
		return ( typeof(value) == "boolean" || value is Boolean );
	}

	/**
	 * Checks wherever passed-in value is <code>Function</code>.
	 */
	public static boolean  isFunction (Object value){
		return ( typeof(value) == "function" || value is Function );
	}


}


