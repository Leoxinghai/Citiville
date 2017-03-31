package by.blooddy.crypto.image;

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

//import flash.utils.*;
    final public class JPEGTable
    {
        private static  Array _quantTables =new Array ();
        private static ByteArray _jpegTable ;

        public  JPEGTable ()
        {
            Error.throwError(ArgumentError, 2012, getQualifiedClassName(this));
            return;
        }//end

        public static ByteArray  getTable (int param1 =60)
        {
            if (param1 > 100)
            {
                Error.throwError(RangeError, 2006, "quality");
            }
            if (param1 < 1)
            {
                param1 = 1;
            }
            _loc_2 = _quantTables.get(param1);
            if (!_loc_2)
            {
                _loc_2 = JPEGTableHelper.createQuantTable(param1);
                if (!_jpegTable)
                {
                    _jpegTable = new ByteArray();
                    _jpegTable.writeBytes(JPEGTableHelper.createZigZagTable());
                    _jpegTable.writeBytes(JPEGTableHelper.createHuffmanTable());
                    _jpegTable.writeBytes(JPEGTableHelper.createCategoryTable());
                }
            }
            ByteArray _loc_3 =new ByteArray ();
            _loc_3.writeBytes(_loc_2);
            _loc_3.writeBytes(_jpegTable);
            _loc_3.position = 0;
            return _loc_3;
        }//end

    }


