package Mechanics.GameEventMechanics;

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

import Classes.inventory.*;
import Mechanics.GameMechanicInterfaces.*;
    
    public class CustomerSupplyMechanic extends BaseSupplyMechanic implements IMultiPickSupporter, IToolTipModifier
    {

        public  CustomerSupplyMechanic ()
        {
            return;
        }//end  

         protected Object  generateHarvestState (int param1 ,int param2 )
        {
            Object _loc_3 ={customers customersReq 0,};
            return _loc_3;
        }//end  

        public String  getPick ()
        {
            return m_config.params.get("pick");
        }//end  

        public Array  getPicksToHide ()
        {
            return null;
        }//end  

        public String  getToolTipStatus ()
        {
            int _loc_2 =0;
            String _loc_1 =null ;
            if (!hasHarvestState())
            {
                _loc_2 = getRequiredCommodity();
                _loc_1 = ZLoc.t("Main", "NeedCommodityToOpenAmount", {amount:_loc_2});
            }
            return _loc_1;
        }//end  

        public String  getToolTipAction ()
        {
            int _loc_2 =0;
            int _loc_3 =0;
            String _loc_1 =null ;
            if (!hasHarvestState())
            {
                _loc_2 = getRequiredCommodity();
                _loc_3 = Global.player.commodities.getCount("goods");
                if (_loc_3 >= _loc_2)
                {
                    _loc_1 = ZLoc.t("Main", "ClickToSupply");
                }
                else
                {
                    _loc_1 = Commodities.getNoCommoditiesTooltip();
                }
            }
            return _loc_1;
        }//end  

    }



