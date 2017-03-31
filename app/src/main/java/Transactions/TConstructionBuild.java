﻿package Transactions;

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

import Engine.Classes.*;
    public class TConstructionBuild extends TWorldState
    {
        private Object m_params ;

        public  TConstructionBuild (WorldObject param1 )
        {
            super(param1);
            this.m_params = {};
            this.m_params.put("mapOwner",  Global.world.ownerId);
            this.m_params.put("itemId",  param1.getId());
            return;
        }//end  

         public void  perform ()
        {
            signedWorldAction("build", this.m_params);
            return;
        }//end  

    }



