package Transactions;

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
import Engine.Transactions.*;

    public class TInstantReady extends Transaction
    {
        private HarvestableResource m_harvestable ;
        private String m_harvestType ;
        public static int ALL_CROPS =-1;
        public static int ALL_SHIPS =-2;
        public static int ALL_RESIDENCES =-3;

        public  TInstantReady (HarvestableResource param1 ,String param2 )
        {
            this.m_harvestable = param1;
            this.m_harvestType = param2;
            if (this.m_harvestType)
            {
                this.instantReadyAllOfType(this.m_harvestType);
            }
            else
            {
                this.m_harvestable.setFullGrown();
            }
            return;
        }//end

         public void  perform ()
        {
            double _loc_1 =0;
            if (this.m_harvestable)
            {
                _loc_1 = this.m_harvestable.getId();
            }
            signedCall("UserService.makeInstantlyHarvestable", _loc_1, this.m_harvestType);
            return;
        }//end

        private void  instantReadyAllOfType (String param1 )
        {
            HarvestableResource _loc_4 =null ;
            _loc_2 =Global.world.getObjectsByTypes(.get(this.getWorldObjectType(this.m_harvestType )) );
            double _loc_3 =0;
            while (_loc_3 < _loc_2.length())
            {

                _loc_4 = HarvestableResource(_loc_2.get(_loc_3));
                if (_loc_4.getState() == HarvestableResource.STATE_PLANTED && !(_loc_4 is Factory))
                {
                    _loc_4.setFullGrown();
                }
                _loc_3 = _loc_3 + 1;
            }
            return;
        }//end

        private int  getWorldObjectType (String param1 )
        {
            switch(param1)
            {
                case "plot":
                {
                    return WorldObjectTypes.PLOT;
                }
                case "ship":
                {
                    return WorldObjectTypes.SHIP;
                }
                case "residence":
                {
                    return WorldObjectTypes.RESIDENCE;
                }
                default:
                {
                    break;
                }
            }
            return Number.NaN;
        }//end

    }



