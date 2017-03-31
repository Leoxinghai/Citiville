package Engine.Interfaces;

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
    public interface IFontMapper
    {


        void  setLanguageCode (String param1 );

        Array  getFontsToRegister ();

        String  mapFontName (String param1 ,String param2 );

        int  mapFontSize (String param1 ,String param2 ,int param3 );

        boolean  mapFontEmbed (String param1 ,String param2 ,boolean param3 );

        boolean  mapFontBold (String param1 ,String param2 ,int param3 ,boolean param4 );

        TextFormat  mapTextFormat (String param1 ,TextFormat param2 );

    }



