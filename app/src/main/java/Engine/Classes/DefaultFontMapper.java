package Engine.Classes;

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

//import flash.text.*;
import Engine.Interfaces.*;

    public class DefaultFontMapper implements IFontMapper
    {

        public  DefaultFontMapper ()
        {
            return;
        }//end

        public void  setLanguageCode (String param1 )
        {
            return;
        }//end

        public Array  getFontsToRegister ()
        {
            return new Array();
        }//end

        public String  mapFontName (String param1 ,String param2 )
        {
            return param2;
        }//end

        public int  mapFontSize (String param1 ,String param2 ,int param3 )
        {
            return param3;
        }//end

        public boolean  mapFontEmbed (String param1 ,String param2 ,boolean param3 )
        {
            return param3;
        }//end

        public boolean  mapFontBold (String param1 ,String param2 ,int param3 ,boolean param4 )
        {
            return param4;
        }//end

        public TextFormat  mapTextFormat (String param1 ,TextFormat param2 )
        {
            return param2;
        }//end

    }



