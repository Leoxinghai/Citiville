package Modules.stats.data;

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

    public class StatsOntology
    {
        private String m_kingdom ;
        private String m_phylum ;
        private String m_zclass ;
        private String m_family ;
        private String m_genus ;

        public  StatsOntology (String param1 ="",String param2 ="",String param3 ="",String param4 ="",String param5 ="")
        {
            this.m_kingdom = param1;
            this.m_phylum = param2;
            this.m_zclass = param3;
            this.m_family = param4;
            this.m_genus = param5;
            return;
        }//end

        public String  kingdom ()
        {
            return this.m_kingdom;
        }//end

        public String  phylum ()
        {
            return this.m_phylum;
        }//end

        public String  zclass ()
        {
            return this.m_zclass;
        }//end

        public String  family ()
        {
            return this.m_family;
        }//end

        public String  genus ()
        {
            return this.m_genus;
        }//end

        public void  kingdom (String param1 )
        {
            this.m_kingdom = param1;
            return;
        }//end

        public void  phylum (String param1 )
        {
            this.m_phylum = param1;
            return;
        }//end

        public void  zclass (String param1 )
        {
            this.m_zclass = param1;
            return;
        }//end

        public void  family (String param1 )
        {
            this.m_family = param1;
            return;
        }//end

        public void  genus (String param1 )
        {
            this.m_genus = param1;
            return;
        }//end

    }



