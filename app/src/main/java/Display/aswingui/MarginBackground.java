package Display.aswingui;

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
import org.aswing.*;
import org.aswing.geom.*;
import org.aswing.graphics.*;

    public class MarginBackground extends AssetBackground
    {
    	//public Insets m_margin ;

        public  MarginBackground (DisplayObject param1 ,Insets param2 )
        {
            super(param1);
            if (param2 != null)
            {
                m_margin = param2;
            }
            else
            {
                m_margin = new Insets(0, 0, 0, 0);
            }
            return;
        }//end

        public Insets  margin ()
        {
            return m_margin;
        }//end

        public void  margin (Insets param1 )
        {
            m_margin = param1;
            return;
        }//end

         public void  updateDecorator (Component param1 ,Graphics2D param2 ,IntRectangle param3 )
        {
            asset.x = param3.x + m_margin.left;
            asset.y = param3.y + m_margin.top;
            asset.width = param3.width - (m_margin.left + m_margin.right);
            asset.height = param3.height - (m_margin.top + m_margin.bottom);
            return;
        }//end

    }


