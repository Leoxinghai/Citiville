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

import Engine.Helpers.*;


    public interface IPeepTarget
    {

        double  getId ();

        boolean  isAttached ();

        boolean  isRouteable ();

        MapResource  getMapResource ();

        Vector3  getSize ();

        Vector3  getPosition ();

        boolean  hasHotspot ();

        Vector3  getHotspot ();

        Vector3 Vector  getHotspots (String param1 ="").<>;

        int  getPopularity ();

        Array  npcNames ();

        void  makePeepEnterTarget (Peep param1 );

    }


