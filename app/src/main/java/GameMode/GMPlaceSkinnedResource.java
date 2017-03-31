package GameMode;

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
import Engine.Transactions.*;
import Transactions.*;

    public class GMPlaceSkinnedResource extends GMPlaceMapResource
    {

        public  GMPlaceSkinnedResource (String param1 ,Class param2 ,boolean param3 =false )
        {
            super(param1, param2, param3);
            return;
        }//end  

         protected Transaction  transaction ()
        {
            return new TPlaceSkinnedResource(m_resource);
        }//end  

         protected int  getItemCost (Item param1 )
        {
            return param1.getRemodelCost();
        }//end  

    }



