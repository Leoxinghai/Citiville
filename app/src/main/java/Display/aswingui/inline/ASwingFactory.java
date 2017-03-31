package Display.aswingui.inline;

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

import Display.aswingui.inline.impl.*;
import Display.aswingui.inline.layout.*;
import Display.aswingui.inline.style.*;
import Display.aswingui.inline.util.*;

    public class ASwingFactory
    {
        private ASwingLayoutFactory m_layouts ;
        private ASwingStyleFactory m_styles ;
        private static ASwingFactory s_swing ;

        public  ASwingFactory (PrivateConstructor param1 )
        {
            this.m_styles = ASwingStyleFactory.getInstance();
            this.m_layouts = ASwingLayoutFactory.getInstance();
            ASwingFont.embed(ASwingFont.DEFAULT_FONT);
            ASwingFont.embed(ASwingFont.DEFAULT_FONT_BOLD);
            ASwingFont.embed(ASwingFont.TITLE_FONT);
            ASwingFont.embed(ASwingFont.DEFAULT_SERIF_FONT);
            return;
        }//end

        public ASwingLayoutFactory  layout ()
        {
            return this.m_layouts;
        }//end

        public IASwingPanel  panel (Object param1 ,Object param2 )
        {
            if (!(param1 instanceof String) && !param2)
            {
                param2 = param1;
                param1 = null;
            }
            ASwingPanel _loc_3 =new ASwingPanel((String)param1 );
            return _loc_3.style(this.m_styles.parse(param2));
        }//end

        public IASwingPanel  verticalSpacer (int param1 )
        {
            return this.spacer(1, param1);
        }//end

        public IASwingPanel  horizontalSpacer (int param1 )
        {
            return this.spacer(param1, 1);
        }//end

        private IASwingPanel  spacer (int param1 =1,int param2 =1)
        {
            return this.panel().size(param1, param2);
        }//end

        public IASwingText  text (Object param1 ,Object param2 )
        {
            if (!(param1 instanceof String) && !param2)
            {
                param2 = param1;
                param1 = null;
            }
            ASwingText _loc_3 =new ASwingText((String)param1 );
            return _loc_3.style(this.m_styles.parse(param2));
        }//end

        public IASwingRichText  richText (Object param1 ,Object param2 )
        {
            if (!(param1 instanceof String) && !param2)
            {
                param2 = param1;
                param1 = null;
            }
            ASwingRichText _loc_3 =new ASwingRichText((String)param1 );
            return _loc_3.style(this.m_styles.parse(param2));
        }//end

        public IASwingButton  button (Object param1 ,Object param2 )
        {
            if (!(param1 instanceof String) && !param2)
            {
                param2 = param1;
                param1 = null;
            }
            ASwingButton _loc_3 =new ASwingButton((String)param1 );
            return _loc_3.style(this.m_styles.parse(param2));
        }//end

        public IASwingImage  image (Object param1 ,Object param2 )
        {
            if (!(param1 instanceof String) && !param2)
            {
                param2 = param1;
                param1 = null;
            }
            ASwingImage _loc_3 =new ASwingImage((String)param1 );
            return _loc_3.style(this.m_styles.parse(param2));
        }//end

        public IASwingList  horizontalList (Object param1 ,Object param2 )
        {
            if (!(param1 instanceof String) && !param2)
            {
                param2 = param1;
                param1 = null;
            }
            ASwingList _loc_3 =new ASwingList((String)param1 );
            return _loc_3.style(this.m_styles.parse(param2)).horizontal();
        }//end

        public IASwingList  verticalList (Object param1 ,Object param2 )
        {
            if (!(param1 instanceof String) && !param2)
            {
                param2 = param1;
                param1 = null;
            }
            ASwingList _loc_3 =new ASwingList((String)param1 );
            return _loc_3.style(this.m_styles.parse(param2)).vertical();
        }//end

        public IASwingCountdownText  countdown (Object param1 ,Object param2 )
        {
            if (!(param1 instanceof String) && !param2)
            {
                param2 = param1;
                param1 = null;
            }
            ASwingCountdownText _loc_3 =new ASwingCountdownText((String)param1 );
            return _loc_3.style(this.m_styles.parse(param2));
        }//end

        public static ASwingFactory  getInstance ()
        {
            if (!s_swing)
            {
                s_swing = new ASwingFactory(new PrivateConstructor());
            }
            return s_swing;
        }//end

    }
class PrivateConstructor

     PrivateConstructor ()
    {
        return;
    }//end




