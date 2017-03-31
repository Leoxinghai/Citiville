package Classes.sim;

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

import Classes.*;
    public class PopulationHelper
    {

        public  PopulationHelper ()
        {
            return;
        }//end

        public static String  getPopulationZlocType (String param1 ,boolean param2 =false )
        {
            String _loc_3 ="Dialogs";
            String _loc_4 ="TT_PopulationType_";
            if (param2)
            {
                _loc_4 = "TT_PopulationTypePlural_";
            }
            _loc_5 = _loc_4+param1 ;
            _loc_6 = ZLoc.t(_loc_3 ,_loc_5 );
            return ZLoc.t(_loc_3, _loc_5);
        }//end

        public static String  getItemPopulationSubTitle (Item param1 )
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_2 ="";
            if (Global.gameSettings().hasMultiplePopulations())
            {
                _loc_3 = param1.populationType;
                _loc_4 = "Dialogs";
                _loc_5 = "TT_PopulationTypeSubTitle_" + _loc_3;
                _loc_2 = ZLoc.t(_loc_4, _loc_5);
            }
            return _loc_2;
        }//end

        public static Class  getItemPopulationIcon (Item param1 )
        {
            String _loc_3 =null ;
            _loc_2 = EmbeddedArt.mkt_populationIcon;
            if (Global.gameSettings().hasMultiplePopulations())
            {
                _loc_3 = param1.populationType;
                switch(_loc_3)
                {
                    case Population.CITIZEN:
                    {
                        _loc_2 = EmbeddedArt.mkt_populationIcon_citizen;
                        break;
                    }
                    case Population.MONSTER:
                    {
                        _loc_2 = EmbeddedArt.mkt_populationIcon_monster;
                        break;
                    }
                    case Population.MIXED:
                    {
                        _loc_2 = EmbeddedArt.mkt_populationIcon;
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return _loc_2;
        }//end

    }



