package Classes;

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

import mx.core.*;
//import flash.display.*;

    public class EmbeddedArt_hud_actMenu_move extends BitmapAsset
    {

        public  EmbeddedArt_hud_actMenu_move ()
        {
            super.bitmapData = ((Bitmap)Global.uiinit.m_cache.get("assets/hud/Action_Menu/subtool_cursor.png")).bitmapData;
            return;
        }//end

    }


