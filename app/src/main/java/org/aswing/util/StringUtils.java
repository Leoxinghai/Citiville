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

public class StringUtils
	/**
 	 * Returns value is a string type value.
 	 * with undefined or null value, false returned.
 	 */
 	public static boolean  isString (Object value){
 		return value is String;
 	}

 	public static String  castString (*)str {
 		return (String)str;
 	}

 	/**
 	 * replace oldString with newString in targetString
 	 */
 	public static String  replace (String targetString ,String oldString ,String newString ){
 		return targetString.split(oldString).join(newString);
 	}

 	/**
 	 * remove the blankspaces of left and right in targetString
 	 */
 	public static String  trim (String targetString ){
 		return trimLeft(trimRight(targetString));
 	}

 	/**
 	 * remove only the blankspace on targetString's left
 	 */
 	public static String  trimLeft (String targetString ){
 		int tempIndex =0;
 		String tempChar ="";
 		for(int i =0;i <targetString.length ;i ++){
 			tempChar = targetString.charAt(i);
 			if(tempChar != " "){
 				tempIndex = i;
 				break;
 			}
 		}
 		return targetString.substr(tempIndex);
 	}

 	/**
 	 * remove only the blankspace on targetString's right
 	 */
 	public static String  trimRight (String targetString ){
 		int tempIndex =targetString.length -1;
 		String tempChar ="";
 		for(int i =targetString.length -1;i >=0;i --){
 			tempChar = targetString.charAt(i);
 			if(tempChar != " "){
 				tempIndex = i;
 				break;
 			}
 		}
 		return targetString.substring(0 , tempIndex+1);
 	}

 	public static Array  getCharsArray (String targetString ,boolean hasBlankSpace ){
 		String tempString =targetString ;
		if(hasBlankSpace == false){
			tempString = trim(targetString);
		}
 		return tempString.split("");
 	}

 	public static boolean  startsWith (String targetString ,String subString ){
 		return (targetString.indexOf(subString) == 0);
 	}

 	public static boolean  endsWith (String targetString ,String subString ){
 		return (targetString.lastIndexOf(subString) == (targetString.length - subString.length()));
 	}

 	public static boolean  isLetter (String chars ){
 		if(chars == null || chars == ""){
 			return false;
 		}
 		for(int i =0;i <chars.length ;i ++){
 			int code =chars.charCodeAt(i );
 			if(code < 65 || code > 122 || (code > 90 && code < 97)){
 				return false;
 			}
 		}
 		return true;
 	}
}


