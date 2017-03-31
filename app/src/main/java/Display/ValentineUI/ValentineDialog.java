package Display.ValentineUI;

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
import Display.aswingui.*;
import Events.*;
import com.greensock.*;
//import flash.display.*;
//import flash.geom.*;
//import flash.utils.*;

    public class ValentineDialog extends GenericDialog
    {
        public static  int TAB_MAKE =0;
        public static  int TAB_ADMIRE =1;
        public static  int TAB_ACHIEVE =2;
        public static int DEFAULT_TAB =0;
        public static Dictionary assetDict =new Dictionary ();

        public  ValentineDialog ()
        {
            super("", "", 0, null, "ValUI");
            this.addEventListener(DataItemEvent.CLOSE_DIALOG, this.onCloseValentineDialog, false, 0, true);
            return;
        }//end

         protected void  loadAssets ()
        {
            Global.delayedAssets.get(DelayedAssetLoader.VALENTINES_ASSETS, makeAssets);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            assetDict.put("dialog_bg",  new m_comObject.valentinesDay_bg());
            assetDict.put("tab_unselected",  m_comObject.valentinesDay_tab_unselected);
            assetDict.put("tab_selected",  m_comObject.valentinesDay_tab_selected);
            assetDict.put("admirersBG",  m_comObject.valentinesDay_admirersBG);
            assetDict.put("valentineBG",  m_comObject.valentinesDay_valentineBG);
            assetDict.put("noportrait",  m_comObject.valentinesDay_admirerCard_blank);
            assetDict.put("heartBorder",  m_comObject.valentinesDay_newHighlight);
            assetDict.put("counterHeart",  m_comObject.valentinesDay_numbersHeart);
            assetDict.put("left_blue",  m_comObject.valentinesDay_rewardArea_left_blue);
            assetDict.put("middle_blue",  m_comObject.valentinesDay_rewardArea_middle_blue);
            assetDict.put("middle_white",  m_comObject.valentinesDay_rewardArea_middle_white);
            assetDict.put("right_white",  m_comObject.valentinesDay_rewardArea_right_white);
            assetDict.put("rewardCard_locked",  m_comObject.valentinesDay_rewardCard_locked);
            assetDict.put("rewardCard_unlocked",  m_comObject.valentinesDay_rewardCard_unlocked);
            assetDict.put("rewardClaimed",  m_comObject.valentinesDay_rewardClaimed);
            assetDict.put("prev_normal",  m_comObject.market_prev_up);
            assetDict.put("prev_down",  m_comObject.market_prev_down);
            assetDict.put("prev_over",  m_comObject.market_prev_over);
            assetDict.put("next_normal",  m_comObject.market_next_up);
            assetDict.put("next_down",  m_comObject.market_next_down);
            assetDict.put("next_over",  m_comObject.market_next_over);
            return assetDict;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new ValentineDialogView(param1, m_message, m_title, m_type, m_callback);
        }//end

        protected void  onCloseValentineDialog (DataItemEvent event )
        {
            this.close();
            return;
        }//end

         public void  centerPopup ()
        {
            Rectangle _loc_1 =null ;
            Point _loc_2 =null ;
            if (parent)
            {
                _loc_1 = getRect(parent);
                _loc_2 = new Point(4, Global.ui.screenHeight);
                this.x = _loc_2.x;
                this.y = _loc_1.y + _loc_2.y;
            }
            return;
        }//end

         protected void  showTween ()
        {
            Sprite me ;
            ASwingHelper.prepare(this.m_jwindow);
            boolean _loc_2 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = _loc_2;
            this.visible = true;
            this.centerPopup();
            rect = getRect(parent);
            newY = Global.ui.screenHeight/2-this.m_jwindow.getHeight()/2;
            me = new Sprite();
void             TweenLite .to (this ,0.5,{newY y , onComplete ()
            {
                boolean _loc_1 =true ;
                me.mouseEnabled = true;
                me.mouseChildren = _loc_1;
                return;
            }//end
            });
            return;
        }//end

    }



