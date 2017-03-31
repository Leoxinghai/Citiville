/*
 Copyright aswing.org, see the LICENCE.txt.
*/

package org.aswing.plaf;

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

import org.aswing.ASFont;

/**
 * Font UI Resource.
 * @author iiley
 */
public class ASFontUIResource extends ASFont implements UIResource
	, = falseembedFontsOrAdvancedProsnull)
	{
		super(name, size, bold, italic, underline, embedFontsOrAdvancedPros);
	}
		
	/**
	 * Create a font ui resource with a font.
	 */
	public static ASFontUIResource  createResourceFont (ASFont font ){
		return new ASFontUIResource(font.getName(), font.getSize(), font.isBold(), font.isItalic(), font.isUnderline(), font.getAdvancedProperties());
	}
}


