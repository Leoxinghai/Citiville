package Modules.quest.Display;

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
import Display.DialogUI.*;
import Events.*;

    public class QuestMiniGameCompleteDialog extends GenericDialog
    {
        protected Object m_popupData ;

        public  QuestMiniGameCompleteDialog (Object param1 ,boolean param2 =true )
        {
            this.m_popupData = param1;
            _loc_3 = ZLoc.t("Quest",param1.name +"_dialog_share_btn_text");
            String _loc_4 ="QuestMiniGameComplete_"+param1.name ;
            _loc_5 = this.getRewardList(param1 );
            Object _loc_6 ={score param1.minigameResults.score ,rewardList };
            _loc_7 = ZLoc.t("Dialogs",_loc_4 +"_message",_loc_6 );
            super(_loc_7, "MiniGameComplete", GenericDialogView.TYPE_CUSTOM_OK, this.onComplete, _loc_4, param1.finishIcon, true, 0, "", null, _loc_3);
            return;
        }//end

        protected String  getRewardList (Object param1 )
        {
            Object _loc_4 =null ;
            String _loc_5 =null ;
            Item _loc_6 =null ;
            String _loc_2 ="";
            int _loc_3 =0;
            while (_loc_3 < param1.rewards.length())
            {

                _loc_4 = param1.rewards.get(_loc_3);
                if (_loc_4.get("resource") != "xpromo")
                {
                    _loc_5 = ZLoc.t("Quest", "reward_" + String(_loc_4.resource), {amount:int(_loc_4.amount)});
                    if (_loc_4.itemName != null && _loc_4.itemName != "")
                    {
                        _loc_6 = Global.gameSettings().getItemByName(_loc_4.itemName);
                        _loc_5 = _loc_6.localizedName;
                    }
                    if (_loc_4.loc != "" && _loc_4.loc != null)
                    {
                        _loc_5 = ZLoc.t("Dialogs", _loc_4.loc, {reward:_loc_5});
                    }
                }
                _loc_2 = _loc_2 + _loc_5;
                if (_loc_3 < (param1.rewards.length - 1))
                {
                    _loc_2 = _loc_2 + ", ";
                }
                _loc_3++;
            }
            return _loc_2;
        }//end

        protected void  onComplete (GenericPopupEvent event )
        {
            _loc_2 = ZLoc.t("Quest",this.m_popupData.name +"_dialog_title");
            if (event.button == GenericDialogView.YES)
            {
                Global.world.viralMgr.sendQuestCompleteFeed(Global.player, this.m_popupData.name, _loc_2);
            }
            return;
        }//end

         public boolean  isLockable ()
        {
            return true;
        }//end

    }



