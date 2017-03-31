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
    public interface IASwingList extends IASwingNode
    {

//        public  IASwingList ();

        IASwingList  style (IASwingStyle param1 );

        IASwingList  position (int param1 ,int param2 );

        IASwingList  size (int param1 ,int param2 );

        IASwingList  firstPageButton (IASwingButton param1 );

        IASwingList  prevPageButton (IASwingButton param1 );

        IASwingList  prevButton (IASwingButton param1 );

        IASwingList  nextButton (IASwingButton param1 );

        IASwingList  nextPageButton (IASwingButton param1 );

        IASwingList  lastPageButton (IASwingButton param1 );

        IASwingList  cellFactory (Object param1 );

        IASwingList  dataModel (Object param1 );

        IASwingList  horizontal ();

        IASwingList  vertical ();

        IASwingList  rows (int param1 );

        IASwingList  columns (int param1 );

        IASwingList  cellSize (int param1 ,int param2 );

        IASwingList  verticalGap (int param1 );

        IASwingList  horizontalGap (int param1 );

        IASwingList  selectable (boolean param1 );

        IASwingList  allowMultipleSelection (boolean param1 );

    }


