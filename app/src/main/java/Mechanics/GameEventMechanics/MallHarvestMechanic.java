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
import Display.*;
import Display.Toaster.*;
import Mechanics.*;
import Modules.stats.types.*;
import com.xinghai.Debug;

    public class MallHarvestMechanic extends CustomerStorageStateHarvestMechanic
    {
        protected String m_foundMysteryBus ="";
public static  String BASE_HARVEST_BONUS_TOASTER_SEEN_FLAG ="toaster_mall_harvest_bonus_";
public static  String BASE_HARVEST_BONUS_TICKER_SEEN_FLAG ="ticker_mall_harvest_bonus_";

        public  MallHarvestMechanic ()
        {
            return;
        }//end

        public int  mysteryBusinessRequirement ()
        {
            return m_config.params.get("mysteryBusinessReq");
        }//end

         protected void  preHarvest ()
        {
            Debug.debug4("MallHarvestMechanic."+"preHarvest");
            m_doobers = new Array();
            _loc_1 = m_owner.getDataForMechanic(m_config.type);
            _loc_2 =(Array) _loc_1.get("resources");
            if (_loc_2.length >= m_config.params.get("mysteryBusinessReq"))
            {
                m_doobers = m_doobers.concat(Global.player.processRandomModifiers(m_owner.getItem(), m_owner, true, [], m_owner.getItemName()));
            }
            else if (m_config.params.get("previousState"))
            {
                m_doobers = m_doobers.concat(Global.player.processRandomModifiers(m_owner.getItem(), m_owner, true, [], m_config.params["previousState"]));
            }
            if (m_doobers.length > 0)
            {
                this.m_foundMysteryBus =(Array).get(0) as String) (m_doobers.get(0);
            }
            m_owner.trackAction(TrackedActionType.PLANT);
            return;
        }//end

         protected void  postHarvest ()
        {
            Debug.debug4("MallHarvestMechanic."+"postHarvest");

            String _loc_2 =null ;
            String _loc_3 =null ;
            double _loc_4 =0;
            int _loc_5 =0;
            Item _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            String _loc_9 =null ;
            Object _loc_10 =null ;
            ItemToaster _loc_11 =null ;
            String _loc_12 =null ;
            Object _loc_13 =null ;
            _loc_1 = m_owner.getDataForMechanic(m_config.type);
            if (!_loc_1)
            {
                if (m_config.params.get("ticker") || m_config.params.get("toaster"))
                {
                    _loc_2 = m_owner.getItemName();
                    _loc_3 = m_owner.getItemFriendlyName();
                    _loc_4 = ItemBonus.getBonusModifier(m_owner, ItemBonus.ALL);
                    _loc_5 = 0;
                    if (_loc_4 > 1)
                    {
                        _loc_5 = Math.round((_loc_4 - 1) * 100);
                    }
                    _loc_6 = m_owner.getItem();
                    _loc_7 = BASE_HARVEST_BONUS_TICKER_SEEN_FLAG + _loc_2;
                    _loc_8 = BASE_HARVEST_BONUS_TOASTER_SEEN_FLAG + _loc_2;
                    if (m_config.params.get("toaster") && !Global.player.getSeenFlag(_loc_8))
                    {
                        _loc_9 = String(m_config.params.get("toaster"));
                        _loc_10 = _loc_6.getToasterInfo(_loc_9);
                        if (_loc_10)
                        {
                            Global.player.setSeenFlag(_loc_8);
                            _loc_11 = new ItemToaster(ZLoc.t("Main", _loc_10.title, {item:_loc_3}), ZLoc.t("Main", _loc_10.text, {item:_loc_3, bonus:_loc_5}), Global.getAssetURL(_loc_10.asset));
                            Global.ui.toaster.show(_loc_11);
                        }
                    }
                    else if (m_config.params.get("ticker") && !Global.player.getSeenFlag(_loc_7))
                    {
                        _loc_12 = String(m_config.params.get("ticker"));
                        _loc_13 = _loc_6.getTickerInfo(_loc_12);
                        if (_loc_13)
                        {
                            Global.player.setSeenFlag(_loc_7);
                            Global.ui.showTickerMessage(ZLoc.t("Main", _loc_13.text, {item:_loc_3, bonus:_loc_5}));
                        }
                    }
                }
                ((IMerchant)m_owner).updatePeepSpawning();
                MechanicManager.getInstance().handleAction(m_owner, "harvest", null);
            }
            return;
        }//end

         protected void  onDoobersCollected ()
        {
            Debug.debug4("MallHarvestMechanic."+"onDoobersCollected");
            Item _loc_1 =null ;
            String _loc_2 =null ;
            String _loc_3 =null ;
            if (this.m_foundMysteryBus)
            {
                _loc_1 = Global.gameSettings().getItemByName(this.m_foundMysteryBus);
                _loc_2 = ZLoc.t("Items", this.m_foundMysteryBus + "_friendlyName");
                _loc_3 = ZLoc.t("Dialogs", "MysteryBusFound", {bus:_loc_2});
                UI.displayMessage(_loc_3, 0, null, "", true);
            }
            return;
        }//end

    }



