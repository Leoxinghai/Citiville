package org.aswing.flyfish.css;

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

//import flash.display.*;
//import flash.system.*;

    public class LazyLoadData extends Object
    {
        public Array params ;
        public ApplicationDomain domain ;
        public String linkage ;
        public DisplayObject content ;

        public  LazyLoadData ()
        {
            return;
        }//end  

        public boolean  isImage ()
        {
            return this.content != null;
        }//end  

        public DisplayObject  getImage ()
        {
            return this.content;
        }//end  

        public DisplayObject  getSymbol ()
        {
            return getAssetInDomain(this.domain, this.linkage);
        }//end  

        public static DisplayObject  getAssetInDomain (ApplicationDomain param1 ,String param2 )
        {
            Class symbolClass ;
            domain = param1;
            linkageID = param2;
            if (domain == null)
            {
                return null;
            }
            DisplayObject ass ;
            try
            {
                symbolClass =(Class) domain.getDefinition(linkageID);
                if (symbolClass != null)
                {
                    ass =(DisplayObject) new symbolClass;
                }
            }
            catch (e:Error)
            {
            }
            return ass;
        }//end  

    }


