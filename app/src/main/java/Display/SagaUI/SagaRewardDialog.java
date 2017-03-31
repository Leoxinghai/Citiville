package Display.SagaUI;

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
//import flash.display.*;
//import flash.utils.*;

    public class SagaRewardDialog extends GenericDialog
    {
        private Object m_data ;
        private int m_numCustomDependencies =0;
        private String m_npcUrl ;
        private DisplayObject m_npcAsset ;

        public  SagaRewardDialog (Object param1 )
        {
            this.m_data = param1;
            String _loc_2 ="SagaReward";
            if (this.m_data.hasOwnProperty("sagaName"))
            {
                _loc_2 = _loc_2 + ("_" + this.m_data.get("sagaName"));
            }
            if (this.m_data.hasOwnProperty("actName"))
            {
                _loc_2 = _loc_2 + ("_" + this.m_data.get("actName"));
            }
            super("", _loc_2);
            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            Array _loc_1 =.get(DelayedAssetLoader.INVENTORY_ASSETS ,DelayedAssetLoader.MARKET_ASSETS ,DelayedAssetLoader.NEW_QUEST_ASSETS) ;
            if (this.m_data.hasOwnProperty("npcUrl"))
            {
                this.m_npcUrl = this.m_data.get("npcUrl");
                _loc_1.push(this.m_npcUrl);
            }
            return _loc_1;
        }//end

         protected Dictionary  createAssetDict ()
        {
            _loc_1 = DelayedAssetLoader.NEW_QUEST_ASSETS);
            Dictionary _loc_2 =new Dictionary ();
            _loc_2.put("checkMark",  _loc_1.quest_check);
            _loc_2.put("placeNowArrow",  _loc_1.quest_arrow_place);
            _loc_2.put("horizontalRule",  _loc_1.quest_horizontal_rule);
            _loc_2.put("verticalRule",  new _loc_1.quest_vertical_rule());
            _loc_2.put("rewardItemBG",  new _loc_1.quest_item_card());
            _loc_2.put("imageBG_0",  new _loc_1.quest_bg_slim());
            _loc_2.put("imageBG_1",  new _loc_1.quest_bg_single());
            _loc_2.put("imageBG_2",  new _loc_1.quest_bg_half());
            _loc_2.put("imageBG_3",  new _loc_1.quest_bg_full());
            _loc_2.put("tasksBG",  _loc_1.quest_tasks_bg);
            _loc_2.put("speechBG",  new _loc_1.quest_speech_bubble());
            _loc_2.put("speechTail",  new _loc_1.quest_speech_tail());
            _loc_2.put("checkList",  _loc_1.quest_check_list);
            _loc_2.put("tierAvailable",  _loc_1.quest_reward_available);
            _loc_2.put("tierUnavailable",  _loc_1.quest_reward_unavailable);
            _loc_2.put("tierCurrent",  _loc_1.quest_reward_current);
            _loc_2.put("currentQuestBG",  _loc_1.currentQuestBG);
            _loc_2.put("questGroupHolderBG",  _loc_1.questGroupHolderBG);
            _loc_2.put("questGroupHolderBGNoBack",  _loc_1.questGroupHolderBGNoBack);
            _loc_2.put("btnBack",  _loc_1.btnBack);
            _loc_2.put("dialog_bg",  new _loc_1.quest_bg_slim());
            if (this.m_npcUrl)
            {
                _loc_2.put("npcAvatar",  m_assetDependencies.get(this.m_npcUrl));
            }
            return _loc_2;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new SagaRewardDialogView(param1, this.m_data);
        }//end

    }



