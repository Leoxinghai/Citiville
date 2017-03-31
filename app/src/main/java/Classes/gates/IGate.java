package Classes.gates;

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

    public interface IGate
    {

        void  unlockCallback (Function param1 );

        void  targetObjectId (double param1 );

        String  loadType ();

        void  loadType (String param1 );

        void  factory (GateFactory param1 );

        GateFactory  factory ();

        int  keyProgress (String param1 );

        String  name ();

        void  loadFromXML (XML param1 );

        boolean  unlockGate ();

        void  loadFromObject (Object param1 );

        Object getData ();

        int  getKey (String param1 );

        void  incrementKey (String param1 );

        boolean  checkForKeys ();

        Array  getKeyArray ();

        int  keyCount ();

        String  getRequirementString ();

        void  displayGate (String param1 ,String param2 ,Object param3 =null );

    }


