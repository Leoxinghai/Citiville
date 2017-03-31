package com.adobe.net;

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
    public class URIEncodingBitmap extends ByteArray
    {

        public void  URIEncodingBitmap (String param1 )
        {
            int _loc_2 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            _loc_3 = new ByteArray ();
            _loc_2 = 0;
            while (_loc_2 < 16)
            {

                this.writeByte(0);
                _loc_2++;
            }
            _loc_3.writeUTFBytes(param1);
            _loc_3.position = 0;
            while (_loc_3.bytesAvailable)
            {

                _loc_4 = _loc_3.readByte();
                if (_loc_4 > 127)
                {
                    continue;
                }
                this.position = _loc_4 >> 3;
                _loc_5 = this.readByte();
                _loc_5 = _loc_5 | 1 << (_loc_4 & 7);
                this.position = _loc_4 >> 3;
                this.writeByte(_loc_5);
            }
            return;
        }//end

        public int  ShouldEscape (String param1 )
        {
            int _loc_3 =0;
            int _loc_4 =0;
            _loc_2 = new ByteArray ();
            _loc_2.writeUTFBytes(param1);
            _loc_2.position = 0;
            _loc_3 = _loc_2.readByte();
            if (_loc_3 & 128)
            {
                return 0;
            }
            if (_loc_3 < 31 || _loc_3 == 127)
            {
                return _loc_3;
            }
            this.position = _loc_3 >> 3;
            _loc_4 = this.readByte();
            if (_loc_4 & 1 << (_loc_3 & 7))
            {
                return _loc_3;
            }
            return 0;
        }//end

    }

