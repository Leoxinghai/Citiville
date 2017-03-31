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

import Modules.stats.experiments.*;
//import flash.utils.*;

    public class CrossPromoHelper
    {
        private static CrossPromoHelper m_instance ;

        public  CrossPromoHelper ()
        {
            return;
        }//end

        public void  beginFarmXGifting ()
        {
            return;
        }//end

        public void  beginXGifting (Dictionary param1 )
        {
            if (param1.get("gameId"))
            {
                switch(parseInt(param1.get("gameId")))
                {
                    case ExternalGameIds.FARMVILLE:
                    {
                        if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_FARMVILLE_GIFTING) == ExperimentDefinitions.FARMVILLE_GIFTING && !Global.player.getSeenFlag("announcements_farmville_gift_xpromo"))
                        {
                            StartUpDialogManager.displayAnnouncement("farmville_gift_xpromo");
                        }
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return;
        }//end

        public static CrossPromoHelper  instance ()
        {
            if (!m_instance)
            {
                m_instance = new CrossPromoHelper;
            }
            return m_instance;
        }//end

    }



