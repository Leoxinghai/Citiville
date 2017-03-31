package Classes.MiniQuest;

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

import Classes.Managers.*;
import Classes.util.*;
import Engine.*;
import Engine.Helpers.*;
import Modules.stats.experiments.*;
//import flash.events.*;

    public class GiftMQ extends MiniQuest
    {
        public static  String QUEST_NAME ="giftMQ";

        public  GiftMQ ()
        {
            m_recurrenceTime = Global.gameSettings().inGameDaySeconds;
            super(QUEST_NAME);
            return;
        }//end

         protected void  onIconClicked (MouseEvent event )
        {
            RecentlyPlayedMFSManager _loc_3 =null ;
            super.onIconClicked(event);
            if (Utilities.isFullScreen())
            {
                Utilities.setFullScreen(false);
            }
            Global.player.setLastActivationTime(m_questName, int(GlobalEngine.getTimer() / 1000));
            _loc_2 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_RECENTLY_PLAYED_MFS);
            if (_loc_2 == 2 || _loc_2 == 3)
            {
                _loc_3 =(RecentlyPlayedMFSManager) Global.flashMFSManager.getManager(FlashMFSManager.TYPE_RECENTLY_PLAYED_MFS);
                if (_loc_3)
                {
                    _loc_3.giftingSource = "hud_icon";
                    _loc_3.displayMFS();
                }
            }
            else
            {
                FrameManager.navigateTo("gifts.php?ref=free_gift_icon");
            }
            return;
        }//end

         protected void  initQuest ()
        {
            _loc_1 = ZLoc.t("Dialogs","Mini_"+m_questName);
            Global.hud.showMiniQuestSprite(m_questName, _loc_1, m_questHudIcon, this.onIconClicked);
            Global.hud.showGoalsProgressOverlayOnQuestIcon(m_questName, "send");
            m_questActive = true;
            return;
        }//end

         protected String  miniQuestLabel ()
        {
            return ZLoc.t("Main", "FreeGifts");
        }//end

         protected Vector2  miniQuestLabelOffset ()
        {
            return new Vector2(-2, 25);
        }//end

    }



