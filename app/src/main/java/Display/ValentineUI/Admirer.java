package Display.ValentineUI;

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

    public class Admirer
    {
        private String m_name ;
        private String m_portraitURL ;
        private double m_numCards ;
        private boolean m_newCard ;
        private String m_uid ;

        public  Admirer (String param1 ,String param2 ,double param3 ,boolean param4 ,String param5 ="")
        {
            this.m_name = param1;
            this.m_portraitURL = param2;
            this.m_numCards = param3;
            this.m_newCard = param4;
            this.m_uid = param5;
            return;
        }//end

        public String  name ()
        {
            return this.m_name;
        }//end

        public String  portrait ()
        {
            return this.m_portraitURL;
        }//end

        public double  numCards ()
        {
            return this.m_numCards;
        }//end

        public boolean  newCard ()
        {
            return this.m_newCard;
        }//end

        public void  newCard (boolean param1 )
        {
            this.m_newCard = param1;
            return;
        }//end

        public void  numCards (double param1 )
        {
            this.m_numCards = param1;
            return;
        }//end

        public void  uid (String param1 )
        {
            this.m_uid = param1;
            return;
        }//end

        public String  uid ()
        {
            return this.m_uid;
        }//end

    }



