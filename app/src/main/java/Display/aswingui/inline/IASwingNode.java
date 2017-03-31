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
import org.aswing.*;

    public interface IASwingNode
    {

//        public  IASwingNode ();

        String  id ();

        int  x ();

        void  x (int param1 );

        int  y ();

        void  y (int param1 );

        int  width ();

        void  width (int param1 );

        int  height ();

        void  height (int param1 );

        int  minWidth ();

        void  minWidth (int param1 );

        int  maxWidth ();

        void  maxWidth (int param1 );

        int  minHeight ();

        void  minHeight (int param1 );

        int  maxHeight ();

        void  maxHeight (int param1 );

        double  top ();

        void  top (double param1 );

        double  bottom ();

        void  bottom (double param1 );

        double  left ();

        void  left (double param1 );

        double  right ();

        void  right (double param1 );

        IASwingStyle  styles ();

        void  styles (IASwingStyle param1 );

        String  stringPackage ();

        void  stringPackage (String param1 );

        boolean  stringPackageSet ();

        Component  component ();

        void  destroy ();

    }


