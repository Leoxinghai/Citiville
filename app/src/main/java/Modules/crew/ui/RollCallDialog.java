package Modules.crew.ui;

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
import Classes.util.*;
import Display.DialogUI.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
//import flash.display.*;
//import flash.utils.*;

    public class RollCallDialog extends GenericDialog
    {
        protected Array m_rollCallData ;
        protected MechanicMapResource m_spawner ;
        private String m_rollCallName ;
        protected Function m_reopenCallback ;

        public  RollCallDialog (MechanicMapResource param1 ,String param2 ,Object param3 =null ,Function param4 =null ,Function param5 =null )
        {
            this.m_rollCallName = param2;
            this.m_spawner = param1;
            this.m_reopenCallback = param5;
            this.loadObject(param3);
            super("dialog", "RollCallDialog_" + this.m_rollCallName, 0, param4, "RollCallDialog_" + this.m_rollCallName, "", false, 0, "", null, "");
            return;
        }//end

        protected void  loadObject (Object param1 )
        {
            _loc_2 = (RollCallDataMechanic)MechanicManager.getInstance().getMechanicInstance(this.m_spawner,"rollCall",MechanicManager.ALL)
            this.m_rollCallData = _loc_2.getCrewState();
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            RollCallDialogView _loc_2 =new RollCallDialogView(this.m_rollCallData ,this.m_rollCallName ,param1 ,m_message ,m_dialogTitle ,GenericDialogView.TYPE_OK ,m_callback );
            return _loc_2;
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.PAYROLL_ASSETS, DelayedAssetLoader.GENERIC_DIALOG_ASSETS, DelayedAssetLoader.MARKET_ASSETS, DelayedAssetLoader.UPGRADES_ASSETS);
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary(true );
            _loc_2 = m_assetDependencies.get(DelayedAssetLoader.GENERIC_DIALOG_ASSETS) ;
            _loc_1.put("dialog_div",  _loc_2.dialog_rename_divider);
            _loc_1.put("vertical_scrollBar_border",  _loc_2.vertical_scrollBar_border);
            _loc_1.put("btn_up_normal",  _loc_2.gridlist_nav_up_normal);
            _loc_1.put("btn_up_over",  _loc_2.gridlist_nav_up_over);
            _loc_1.put("btn_up_down",  _loc_2.gridlist_nav_up_down);
            _loc_1.put("btn_down_normal",  _loc_2.gridlist_nav_down_normal);
            _loc_1.put("btn_down_over",  _loc_2.gridlist_nav_down_over);
            _loc_1.put("btn_down_down",  _loc_2.gridlist_nav_down_down);
            _loc_1.put("cell_bg",  _loc_2.cell_bg);
            _loc_1.put("cell_bg_alt",  _loc_2.cell_bg_alt);
            _loc_1.put("pic_cashBtn",  EmbeddedArt.icon_cash_big);
            _loc_1.put("pic_checkGreen",  _loc_2.checkmark_green);
            _loc_1.put("pic_dot",  _loc_2.gridList_page_dot);
            _loc_3 = m_assetDependencies.get(DelayedAssetLoader.MARKET_ASSETS) ;
            _loc_1.put("card_available_selected",  _loc_3.slices_card_active);
            _loc_4 = m_assetDependencies.get(DelayedAssetLoader.UPGRADES_ASSETS) ;
            _loc_1.put("dialog_bg", (DisplayObject) new _loc_4.upgrades_bg());
            _loc_5 = m_assetDependencies.get(DelayedAssetLoader.PAYROLL_ASSETS) ;
            _loc_1.put("payroll_checkin_confirmCheckmark",  _loc_5.payroll_checkin_confirmCheckmark);
            _loc_1.put("payroll_checkin_missingX",  _loc_5.payroll_checkin_missingX);
            _loc_1.put("payroll_checkmark_reward",  _loc_5.payroll_checkmark_reward);
            _loc_1.put("payroll_dividerGradient",  _loc_5.payroll_dividerGradient);
            _loc_1.put("payroll_icon_clock",  _loc_5.payroll_icon_clock);
            _loc_1.put("payroll_icon_responses",  _loc_5.payroll_icon_responses);
            _loc_1.put("payroll_reward_coin",  _loc_5.payroll_reward_coin);
            _loc_1.put("payroll_reward_energy",  _loc_5.payroll_reward_energy);
            _loc_1.put("payroll_reward_xp",  _loc_5.payroll_reward_xp);
            _loc_1.put("payroll_rewardArrow",  _loc_5.payroll_rewardArrow);
            _loc_1.put("payroll_rewardBG_blue",  _loc_5.payroll_rewardBG_blue);
            _loc_1.put("payroll_rewardBG_green",  _loc_5.payroll_rewardBG_green);
            _loc_1.put("payroll_spinner",  _loc_5.payroll_spinner);
            _loc_1.put("payroll_burst",  _loc_5.payroll_burst);
            _loc_1.put("spawner",  this.m_spawner);
            return _loc_1;
        }//end

         protected void  onHideComplete ()
        {
            super.onHideComplete();
            if (this.m_reopenCallback != null)
            {
                this.m_reopenCallback();
            }
            return;
        }//end

         public void  setupDialogStatsTracking ()
        {
            Object _loc_2 =null ;
            RollCallDataMechanic _loc_3 =null ;
            boolean _loc_1 =true ;
            for(int i0 = 0; i0 < this.m_rollCallData.size(); i0++)
            {
            		_loc_2 = this.m_rollCallData.get(i0);

                if (_loc_2.checkedIn)
                {
                    _loc_1 = false;
                    break;
                }
            }
            _loc_3 =(RollCallDataMechanic) MechanicManager.getInstance().getMechanicInstance(this.m_spawner, "rollCall", MechanicManager.ALL);
            if (_loc_1 && _loc_3 && _loc_3.isRollCallComplete())
            {
                countDialogAction("no_one_checked_in");
            }
            super.setupDialogStatsTracking();
            return;
        }//end

    }



