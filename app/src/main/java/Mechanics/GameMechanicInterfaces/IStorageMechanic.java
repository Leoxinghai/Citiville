package Mechanics.GameMechanicInterfaces;

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

    public interface IStorageMechanic extends IActionGameMechanic
    {

        boolean  isValidName (String param1 );

        int  getCapacity (String param1 );

        int  getSpareCapacity (String param1 );

        int  getCount (String param1 );

        int  getTotalCount ();

        int  getAddedCount (Array param1 );

        boolean  add (String param1 ,int param2 );

        boolean  remove (String param1 ,int param2 );

        boolean  clear ();

        boolean  purchase (String param1 ,int param2 ,String param3 );

        void  sendTransactions (boolean param1 );

        Array  restrictedKeywords ();

    }



