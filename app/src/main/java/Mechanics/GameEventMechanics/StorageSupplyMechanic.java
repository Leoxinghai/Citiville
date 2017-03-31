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

import Classes.*;
import Classes.inventory.*;

    public class StorageSupplyMechanic extends CustomerSupplyMechanic
    {

        public  StorageSupplyMechanic ()
        {
            return;
        }//end

         public boolean  hasOverrideForGameAction (String param1 )
        {
            return super.hasOverrideForGameAction(param1) && this.hasStores();
        }//end

         public String  getToolTipStatus ()
        {
            int _loc_2 =0;
            String _loc_1 =null ;
            if (!hasHarvestState() && this.hasStores())
            {
                _loc_2 = this.getRequiredCommodity();
                _loc_1 = ZLoc.t("Main", "NeedCommodityToOpenAmount", {amount:_loc_2});
            }
            return _loc_1;
        }//end

         public String  getToolTipAction ()
        {
            int _loc_2 =0;
            int _loc_3 =0;
            String _loc_1 =null ;
            if (!hasHarvestState() && this.hasStores())
            {
                _loc_2 = this.getRequiredCommodity();
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

        protected boolean  hasStores ()
        {
            boolean _loc_1 =false ;
            _loc_2 = m_owner.getDataForMechanic(m_config.params.get( "storageSource") );
            if (_loc_2 && _loc_2.length > 0)
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

         protected int  getRequiredCommodity ()
        {
            MapResource _loc_5 =null ;
            int _loc_1 =0;
            Item _loc_2 =null ;
            String _loc_3 =null ;
            _loc_4 = m_owner.getDataForMechanic(m_config.params.get( "storageSource") );
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            		_loc_5 = _loc_4.get(i0);

                _loc_3 = _loc_5.getItemName();
                _loc_2 = Global.gameSettings().getItemByName(_loc_3);
                if (_loc_2)
                {
                    _loc_1 = _loc_1 + _loc_2.commodityReq;
                }
            }
            return _loc_1;
        }//end

         protected Object  generateHarvestState (int param1 ,int param2 )
        {
            Item _loc_5 =null ;
            String _loc_6 =null ;
            MapResource _loc_7 =null ;
            Object _loc_8 =null ;
            _loc_3 = m_owner.getDataForMechanic(m_config.params.get( "storageSource") );
            Array _loc_4 =new Array ();
            if (_loc_3)
            {
                _loc_5 = null;
                _loc_6 = null;
                for(int i0 = 0; i0 < _loc_3.size(); i0++)
                {
                		_loc_7 = _loc_3.get(i0);

                    _loc_6 = _loc_7.getItemName();
                    _loc_5 = Global.gameSettings().getItemByName(_loc_6);
                    if (_loc_5)
                    {
                        _loc_4.push(_loc_6);
                    }
                }
                _loc_8 = {customers:0, customersReq:param2, resources:_loc_4};
                return _loc_8;
            }
            return null;
        }//end

         protected void  awardUpgradeActions ()
        {
            Item _loc_2 =null ;
            String _loc_3 =null ;
            MapResource _loc_4 =null ;
            _loc_1 = m_owner.getDataForMechanic(m_config.params.get( "storageSource") );
            if (_loc_1)
            {
                _loc_2 = null;
                _loc_3 = null;
                for(int i0 = 0; i0 < _loc_1.size(); i0++)
                {
                		_loc_4 = _loc_1.get(i0);

                    _loc_3 = _loc_4.getItemName();
                    _loc_2 = Global.gameSettings().getItemByName(_loc_3);
                    if (_loc_2)
                    {
                        if (_loc_4.canCountUpgradeActions())
                        {
                            _loc_4.incrementUpgradeActionCount(false);
                        }
                    }
                }
            }
            return;
        }//end

    }



