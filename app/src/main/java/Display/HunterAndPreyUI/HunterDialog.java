package Display.HunterAndPreyUI;

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
import Display.DialogUI.*;
import Engine.Managers.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class HunterDialog extends UIGenericDialog
    {
        protected int m_numAssetsLoaded =0;
        protected int m_numAssetsToLoad =1;
        protected Dictionary m_assetLoaders ;
        public static  int TAB_HUNTERS =0;
        public static  int TAB_PREY =1;
        public static  int TAB_UPGRADES =2;
        public static int DEFAULT_TAB =0;
        public static  Array STATS_TAB_NAMES =.get( "hunters_tab","prey_tab","upgrades_tab") ;
        public static Dictionary assetDict =new Dictionary ();
        public static String groupId ;

        public  HunterDialog (String param1 )
        {
            groupId = param1;
            super("", "", 0, null, groupId);
            return;
        }//end

         protected void  loadAssets ()
        {
            String _loc_2 =null ;
            this.m_assetLoaders = new Dictionary();
            _loc_1 =Global.gameSettings().getPreyGroupDialogAssetURLs(HunterDialog.groupId );
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                this.m_numAssetsToLoad++;
                this.m_assetLoaders.put(_loc_2,  LoadingManager.load(Global.getAssetURL(_loc_1.get(_loc_2)), this.onAssetsLoaded));
            }
            Global.delayedAssets.get(DelayedAssetLoader.TABBED_ASSETS, makeAssets);
            return;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            String _loc_2 =null ;
            this.m_numAssetsLoaded++;
            if (this.m_numAssetsLoaded < this.m_numAssetsToLoad)
            {
                return;
            }
            for(int i0 = 0; i0 < this.m_assetLoaders.size(); i0++)
            {
            	_loc_2 = this.m_assetLoaders.get(i0);

                assetDict.put(_loc_2,  (DisplayObject)this.m_assetLoaders.get(_loc_2).content);
            }
            super.onAssetsLoaded();
            return;
        }//end

         protected void  showTween ()
        {
            boolean _loc_1 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = false;
            centerPopup();
            m_content.alpha = 0;

            m_content.scaleY = 1;
            m_content.scaleX = 1;
            TweenLite.to(m_content, TWEEN_TIME, {alpha:1, onComplete:onShowComplete});
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            assetDict.put("dialog_bg", (DisplayObject)new m_comObject.conventionCenter_bg());
            assetDict.put("tab_unselected",  m_comObject.police_tab_unselected);
            assetDict.put("tab_selected",  m_comObject.police_tab_selected);
            assetDict.put("inner_top",  m_comObject.conventionCenter_innerArea_round);
            assetDict.put("inner_undertab",  m_comObject.conventionCenter_innerArea_flatTop);
            assetDict.put("conventionCenter_pagination_border",  m_comObject.conventionCenter_pagination_border);
            assetDict.put("conventionCenter_pagination_bottom_disabled",  m_comObject.conventionCenter_pagination_bottom_disabled);
            assetDict.put("conventionCenter_pagination_bottom_over",  m_comObject.conventionCenter_pagination_bottom_over);
            assetDict.put("conventionCenter_pagination_bottom_up",  m_comObject.conventionCenter_pagination_bottom_up);
            assetDict.put("conventionCenter_pagination_dot",  m_comObject.conventionCenter_pagination_dot);
            assetDict.put("conventionCenter_pagination_top_disabled",  m_comObject.conventionCenter_pagination_top_disabled);
            assetDict.put("conventionCenter_pagination_top_over",  m_comObject.conventionCenter_pagination_top_over);
            assetDict.put("conventionCenter_pagination_top_up",  m_comObject.conventionCenter_pagination_top_up);
            assetDict.put("police_checkmark",  m_comObject.police_checkmark);
            assetDict.put("police_innerBorder",  m_comObject.police_innerBorder);
            assetDict.put("police_nextButton_down",  m_comObject.police_nextButton_down);
            assetDict.put("police_nextButton_over",  m_comObject.police_nextButton_over);
            assetDict.put("police_nextButton_up",  m_comObject.police_nextButton_up);
            assetDict.put("police_prevButton_down",  m_comObject.police_prevButton_down);
            assetDict.put("police_prevButton_over",  m_comObject.police_prevButton_over);
            assetDict.put("police_prevButton_up",  m_comObject.police_prevButton_up);
            assetDict.put("police_rewardBG_center_blue",  m_comObject.police_rewardBG_center_blue);
            assetDict.put("police_rewardBG_center_disabled",  m_comObject.police_rewardBG_center_disabled);
            assetDict.put("police_rewardBG_center_white",  m_comObject.police_rewardBG_center_white);
            assetDict.put("police_rewardBG_left_blue",  m_comObject.police_rewardBG_left_blue);
            assetDict.put("police_rewardBG_left_disabled",  m_comObject.police_rewardBG_left_disabled);
            assetDict.put("police_rewardBG_left_white",  m_comObject.police_rewardBG_left_white);
            assetDict.put("police_rewardBG_right_blue",  m_comObject.police_rewardBG_right_blue);
            assetDict.put("police_rewardBG_right_disabled",  m_comObject.police_rewardBG_right_disabled);
            assetDict.put("police_rewardBG_right_white",  m_comObject.police_rewardBG_right_white);
            assetDict.put("police_scrollContainer",  m_comObject.police_scrollContainer);
            assetDict.put("rewardCard_locked",  m_comObject.police_rewardCard_locked);
            assetDict.put("rewardCard_unlocked",  m_comObject.police_rewardCard_unlocked);
            assetDict.put("police_arrow",  m_comObject.police_arrow);
            assetDict.put("police_checkmarkBig",  m_comObject.police_checkmarkBig);
            assetDict.put("police_slot_locked",  m_comObject.police_slot_locked);
            assetDict.put("police_slot_unfilled",  m_comObject.police_slot_unfilled);
            assetDict.put("police_divider",  m_comObject.police_divider);
            assetDict.put("donut_auto_feed_checkbox",  m_comObject.donut_auto_feed_checkbox);
            assetDict.put("donut_auto_feed_checkmark",  m_comObject.donut_auto_feed_checkmark);
            return assetDict;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new HunterDialogView(param1, m_message, m_title, m_type, m_callback);
        }//end

         protected void  onShow ()
        {
            super.onShow();
            Global.ui.mouseEnabled = true;
            Global.ui.mouseChildren = true;
            return;
        }//end

    }



