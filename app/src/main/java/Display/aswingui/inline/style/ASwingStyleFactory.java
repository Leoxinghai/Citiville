package Display.aswingui.inline.style;

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

    public class ASwingStyleFactory
    {
        private static ASwingStyleFactory s_styles ;

        public  ASwingStyleFactory (PrivateConstructor param1 )
        {
            return;
        }//end

        public IASwingStyle  parse (Object param1 )
        {
            ASwingStyleProperties properties ;
            String property ;
            String field ;
            dictionary = param1;
            style = (IASwingStyle)dictionary
            if (!style)
            {
                properties = new ASwingStyleProperties();
                if (dictionary)
                {
                    int _loc_3 =0;
                    _loc_4 = dictionary;
		    for(int i0 = 0; i0 < dictionary .size(); i0++) 
		    {
		    		property = dictionary .get(i0);

                        field = this.toCamel(property);
                        try
                        {
                            properties.put(field,  dictionary.get(property));
                        }
                        catch (e:Error)
                        {
                        }
                    }
                }
                style = new ASwingStyle(properties);
            }
            return style;
        }//end

        private String  toCamel (String param1 )
        {
            _loc_2 = param1;
            _loc_3 = param1.indexOf("-");
            _loc_4 = param1.length ;
            while (_loc_3 > 0 && (_loc_3 + 1) < _loc_4)
            {

                _loc_2 = _loc_2.substr(0, _loc_3) + _loc_2.substr((_loc_3 + 1), 1).toUpperCase() + _loc_2.substr(_loc_3 + 2);
                _loc_3 = _loc_2.indexOf("-", (_loc_3 + 1));
            }
            return _loc_2;
        }//end

        public static ASwingStyleFactory  getInstance ()
        {
            if (!s_styles)
            {
                s_styles = new ASwingStyleFactory(new PrivateConstructor());
            }
            return s_styles;
        }//end

    }
class PrivateConstructor

     PrivateConstructor ()
    {
        return;
    }//end




