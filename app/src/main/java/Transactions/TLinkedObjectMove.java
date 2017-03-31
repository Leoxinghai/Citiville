package Transactions;

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

import Classes.*;
import Engine.Helpers.*;

    public class TLinkedObjectMove extends TWorldState
    {
        protected Object m_params ;

        public  TLinkedObjectMove (MapResource param1 ,Array param2 )
        {
            MapResource _loc_5 =null ;
            this.m_params = new Object();
            super(param1);
            this.m_params.linkedObjects = new Array();
            Array _loc_3 =new Array ();
            _loc_4 = param1.getPosition ();
            _loc_3.put("x",  _loc_4.x);
            _loc_3.put("y",  _loc_4.y);
            _loc_3.put("direction",  param1.getDirection());
            _loc_3.put("id",  param1.getId());
            this.m_params.linkedObjects.put(this.m_params.linkedObjects.length,  _loc_3);
            for(int i0 = 0; i0 < param2.size(); i0++) 
            {
            		_loc_5 = param2.get(i0);

                _loc_3 = new Array();
                _loc_4 = _loc_5.getPosition();
                _loc_3.put("x",  _loc_4.x);
                _loc_3.put("y",  _loc_4.y);
                _loc_3.put("direction",  _loc_5.getDirection());
                _loc_3.put("id",  _loc_5.getId());
                this.m_params.linkedObjects.put(this.m_params.linkedObjects.length,  _loc_3);
            }
            return;
        }//end

         public void  perform ()
        {
            signedWorldAction("linkedObjectMove", this.m_params);
            return;
        }//end

    }



