package Classes;

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


    public class ThemeListDefinition implements IThemeDefinition
    {
        private Vector<ThemeDefinition> m_themeDefinitions;

        public  ThemeListDefinition (Vector param1 .<XML >)
        {
            XML _loc_2 =null ;
            this.m_themeDefinitions = new Vector<ThemeDefinition>();
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_2 = param1.get(i0);

                this.m_themeDefinitions.push(new ThemeDefinition(_loc_2));
            }
            return;
        }//end

        public Item  getThemeItemByName (String param1 ,Item param2 )
        {
            ThemeDefinition _loc_4 =null ;
            _loc_3 = param2;
            for(int i0 = 0; i0 < this.m_themeDefinitions.size(); i0++)
            {
            	_loc_4 = this.m_themeDefinitions.get(i0);

                _loc_3 = _loc_4.getThemeItemByName(param1, _loc_3);
            }
            return _loc_3;
        }//end

    }



