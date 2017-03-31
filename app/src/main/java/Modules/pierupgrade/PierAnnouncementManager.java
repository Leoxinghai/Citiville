package Modules.pierupgrade;

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

import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.MarketUI.*;

//import flash.geom.*;

    public class PierAnnouncementManager
    {
        private static Object SINGLETON_ENFORCER ={};
        private static PierAnnouncementManager m_instance ;

        public  PierAnnouncementManager (Object param1 )
        {
            if (param1 != SINGLETON_ENFORCER)
            {
                throw new Error("PierAnnouncementManager instanceof a singleton");
            }
            return;
        }//end

        public void  onAnnouncementOk ()
        {
            Vector<Rectangle> rects;
            boolean hasExpandedToWater ;
            Rectangle rect ;
            Function callback ;
            Object data ;
            Global.player.setSeenFlag("qux_pier_ships");
            count = Global.world.getAllCountByName("goods_pier")+Global.world.getAllCountByName("goods_pier_2");
            if (count > 0)
            {
                Global.guide.notify("ShowPier");
            }
            else
            {
                rects = Vector<Rectangle>(.get(new Rectangle(-36, 12, 1, 1), new Rectangle(-36, 0, 1, 1), new Rectangle(-36, -12, 1, 1), new Rectangle(-36, -24, 1, 1), new Rectangle(-36, -36, 1, 1)));
                hasExpandedToWater;
                int _loc_2 =0;
                _loc_3 = rects;
                for(int i0 = 0; i0 < rects.size(); i0++)
                {
                		rect = rects.get(i0);


                    if (Global.world.rectInTerritory(rect))
                    {
                        hasExpandedToWater;
                        break;
                    }
                }
                if (hasExpandedToWater)
                {
                    Global.guide.notify("ShowPierUpgrade");
                }
                else
                {
                    callback =void  ()
            {
                _loc_1 = UI.displayCatalog(new CatalogParams ().setCustomItems(.get(Global.gameSettings().getItemByName("expand_12_12_special")) ).setExclusiveCategory(true ).setOverrideTitle("SpecialPierExpansion").setCloseMarket(true ).setIgnoreExcludeExperiments(true ),false ,true );
                return;
            }//end
            ;
                    data;
                    UI.displayPopup(new BaseDialog(data));
                }
            }
            return;
        }//end

        public static PierAnnouncementManager  instance ()
        {
            if (!m_instance)
            {
                m_instance = new PierAnnouncementManager(SINGLETON_ENFORCER);
            }
            return m_instance;
        }//end

    }



