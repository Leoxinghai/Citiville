package Display.aswingui.inline;

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

import Display.aswingui.inline.style.*;
//import flash.text.*;

    public interface IASwingRichText extends IASwingNode
    {

//        public  IASwingRichText ();

        IASwingRichText  style (IASwingStyle param1 );

        IASwingRichText  textKey (String param1 );

        IASwingRichText  strings (String param1 );

        IASwingRichText  replacements (Object param1 );

        IASwingRichText  position (int param1 ,int param2 );

        IASwingRichText  size (int param1 ,int param2 );

        IASwingRichText  selectable (boolean param1 =true );

        IASwingRichText  editable (boolean param1 =true );

        IASwingRichText  maxChars (int param1 );

        IASwingRichText  columns (int param1 );

        IASwingRichText  rows (int param1 );

        IASwingRichText  multiline (boolean param1 =true );

        IASwingRichText  wordWrap (boolean param1 =true );

        IASwingRichText  password (boolean param1 =true );

        TextField  textField ();

    }


