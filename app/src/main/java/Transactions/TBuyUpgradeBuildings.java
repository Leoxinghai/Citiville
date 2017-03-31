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
    public class TBuyUpgradeBuildings extends TWorldState
    {
        protected Array m_resources ;
        protected Array m_resourceIds ;
        protected Array m_oldItems ;
        protected boolean m_cashSuccess =false ;

        public  TBuyUpgradeBuildings (Array param1 ,Array param2 ,int param3 )
        {
            MapResource _loc_5 =null ;
            Item _loc_6 =null ;
            super(param1.get(0));
            if (param3 > 0 && Global.player.cash >= param3)
            {
                Global.player.cash = Global.player.cash - param3;
                this.m_cashSuccess = true;
            }
            if (!this.m_cashSuccess)
            {
                return;
            }
            this.m_resources = param1;
            this.m_resourceIds = new Array();
            this.m_oldItems = new Array();
            int _loc_4 =0;
            while (_loc_4 < param1.length())
            {

                _loc_5 =(MapResource) param1.get(_loc_4);
                this.m_resourceIds.push(_loc_5.getId());
                this.m_oldItems.push(_loc_5.getItem());
                _loc_6 = Global.gameSettings().getItemByName(param2.get(_loc_4));
                _loc_5.detach();
                _loc_5.setItem(_loc_6);
                _loc_5.attach();
                _loc_5.setState(_loc_5.getState());
                _loc_5.doUpgradeAnimations(_loc_5.defaultUpgradeFinishCallback);
                _loc_4++;
            }
            return;
        }//end

         public void  perform ()
        {
            if (this.m_cashSuccess)
            {
                signedWorldAction("buyUpgradeBuildings", this.m_resourceIds);
            }
            return;
        }//end

         protected void  onWorldActionComplete (Object param1 )
        {
            MapResource _loc_4 =null ;
            Object _loc_5 =null ;
            _loc_2 = param1as Array ;
            int _loc_3 =0;
            while (_loc_3 < this.m_resources.length())
            {

                _loc_4 =(MapResource) this.m_resources.get(_loc_3);
                _loc_5 = _loc_2.get(_loc_3);
                if (_loc_4 != null && (_loc_4.getId() == 0 || _loc_4.getId() >= TEMP_ID_START) && _loc_5.hasOwnProperty("id") && _loc_5.id != 0)
                {
                    _loc_4.setId(_loc_5.id);
                }
                _loc_4.notifyUpgrade(param1);
                _loc_3++;
            }
            return;
        }//end

         protected void  onWorldActionFault (int param1 ,String param2 )
        {
            MapResource _loc_4 =null ;
            int _loc_3 =0;
            while (_loc_3 < this.m_resources.length())
            {

                _loc_4 =(MapResource) this.m_resources.get(_loc_3);
                _loc_4.setItem(this.m_oldItems.get(_loc_3));
                _loc_4.setState(_loc_4.getState());
                _loc_3++;
            }
            return;
        }//end

    }



