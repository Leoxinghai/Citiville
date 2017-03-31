package Display.FriendRewardsUI;

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
//import flash.display.*;
//import flash.utils.*;

    public class FriendRewardsDialog extends GenericDialog
    {
        protected Array m_zooData ;
        protected String m_localeKey ;

        public  FriendRewardsDialog (MechanicMapResource param1 ,String param2 ,Object param3 =null ,Function param4 =null )
        {
            this.loadObject(param3);
            this.m_localeKey = param2;
            super("durp?", param2, 0, param4, param2, "", false, 0, "", null, "");
            return;
        }//end

        protected void  loadObject (Object param1 )
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            this.m_zooData = new Array();
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_2 = param1.get(i0);

                _loc_3 = _loc_2;
                if (_loc_3.indexOf("i") >= 0)
                {
                    _loc_3 = _loc_3.substr(1);
                }
                this.m_zooData.push({uid:_loc_3, itemName:param1.get(_loc_2)});
            }
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            FriendRewardsDialogView _loc_2 =new FriendRewardsDialogView(this.m_zooData ,param1 ,this.m_localeKey ,m_message ,m_dialogTitle );
            return _loc_2;
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.GENERIC_DIALOG_ASSETS, DelayedAssetLoader.MARKET_ASSETS);
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary(true );
            _loc_2 = m_assetDependencies.get(DelayedAssetLoader.GENERIC_DIALOG_ASSETS) ;
            _loc_1.put("dialog_bg", (DisplayObject) new _loc_2.dialog_bg());
            _loc_1.put("dialog_div", _loc_2.dialog_rename_divider);
            _loc_1.put("vertical_scrollBar_border", _loc_2.vertical_scrollBar_border);
            _loc_1.put("btn_up_normal", _loc_2.gridlist_nav_up_normal);
            _loc_1.put("btn_up_over", _loc_2.gridlist_nav_up_over);
            _loc_1.put("btn_up_down", _loc_2.gridlist_nav_up_down);
            _loc_1.put("btn_down_normal", _loc_2.gridlist_nav_down_normal);
            _loc_1.put("btn_down_over", _loc_2.gridlist_nav_down_over);
            _loc_1.put("btn_down_down", _loc_2.gridlist_nav_down_down);
            _loc_1.put("cell_bg", _loc_2.cell_bg);
            _loc_1.put("cell_bg_alt", _loc_2.cell_bg_alt);
            _loc_1.put("pic_cashBtn", EmbeddedArt.icon_cash_big);
            _loc_1.put("pic_checkGreen", _loc_2.checkmark_green);
            _loc_1.put("pic_dot", _loc_2.gridList_page_dot);
            _loc_3 = m_assetDependencies.get(DelayedAssetLoader.MARKET_ASSETS) ;
            _loc_1.put("card_available_selected", _loc_3.slices_card_active);
            return _loc_1;
        }//end

    }



