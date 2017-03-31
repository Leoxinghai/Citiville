package Engine.Classes;

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

//import flash.text.*;
import Engine.Interfaces.*;

    public class FontMapperProxy implements IFontMapper
    {
        private Function m_setLanguageCode ;
        private Function m_getFontsToRegister ;
        private Function m_mapFontName ;
        private Function m_mapFontSize ;
        private Function m_mapFontEmbed ;
        private Function m_mapFontBold ;
        private Function m_mapTextFormat ;

        public  FontMapperProxy (Object param1 )
        {
            this.m_setLanguageCode = param1.setLanguageCode;
            this.m_getFontsToRegister = param1.getFontsToRegister;
            this.m_mapFontName = param1.mapFontName;
            this.m_mapFontSize = param1.mapFontSize;
            this.m_mapFontEmbed = param1.mapFontEmbed;
            this.m_mapFontBold = param1.mapFontBold;
            this.m_mapTextFormat = param1.mapTextFormat;
            return;
        }//end

        public void  setLanguageCode (String param1 )
        {
            this.m_setLanguageCode(param1);
            return;
        }//end

        public Array  getFontsToRegister ()
        {
            return this.m_getFontsToRegister();
        }//end

        public String  mapFontName (String param1 ,String param2 )
        {
            return this.m_mapFontName(param1, param2);
        }//end

        public int  mapFontSize (String param1 ,String param2 ,int param3 )
        {
            return this.m_mapFontSize(param1, param2, param3);
        }//end

        public boolean  mapFontEmbed (String param1 ,String param2 ,boolean param3 )
        {
            return this.m_mapFontEmbed(param1, param2, param3);
        }//end

        public boolean  mapFontBold (String param1 ,String param2 ,int param3 ,boolean param4 )
        {
            return this.m_mapFontBold(param1, param2, param3, param4);
        }//end

        public TextFormat  mapTextFormat (String param1 ,TextFormat param2 )
        {
            return this.m_mapTextFormat(param1, param2);
        }//end

    }



