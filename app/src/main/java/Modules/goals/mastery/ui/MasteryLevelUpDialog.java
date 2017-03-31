package Modules.goals.mastery.ui;

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
import Display.*;
import Display.DialogUI.*;
import Events.*;
import Modules.goals.*;
import Modules.goals.mastery.*;
//import flash.utils.*;

    public class MasteryLevelUpDialog extends CharacterResponseDialog
    {
        protected String m_itemName ;
        protected String m_localizedName ;
        protected String m_feedImage ;
public static  String DEFAULT_ICON ="assets/dialogs/rita_bubble.png";

        public  MasteryLevelUpDialog (String param1 )
        {
            this.m_itemName = param1;
            String _loc_2 ="crop_mastery_share_goods";
            int _loc_3 =0;
            String _loc_4 ="";
            _loc_5 =Global.goalManager.getGoal(GoalManager.GOAL_MASTERY )as MasteryGoal ;
            _loc_6 =Global.gameSettings().getItemByName(this.m_itemName );
            if (_loc_5 && _loc_6)
            {
                _loc_3 = _loc_5.getBonusMultiplier(this.m_itemName);
                this.m_feedImage = _loc_5.getFeedImage(this.m_itemName);
                this.m_localizedName = _loc_6.localizedName;
            }
            _loc_7 = ZLoc.t("Dialogs","MasteryLevelUp_submessage",{percent _loc_3 ,itemName.m_localizedName });
            super(_loc_7, "", GenericDialogView.TYPE_SHARECANCEL, this.onShareGoods, "MasteryLevelUp", this.getIconUrl(), true, 0, _loc_2);
            return;
        }//end

        protected Item  getRewardItem ()
        {
            MasteryDefinition _loc_3 =null ;
            Item _loc_1 =null ;
            _loc_2 =Global.goalManager.getGoal(GoalManager.GOAL_MASTERY )as MasteryGoal ;
            if (_loc_2)
            {
                _loc_3 = _loc_2.getLevelDefinition(this.m_itemName);
                if (_loc_3 && _loc_3.rewards && _loc_3.rewards.get("item"))
                {
                    _loc_1 = Global.gameSettings().getItemByName(_loc_3.rewards.get("item"));
                }
            }
            return _loc_1;
        }//end

        protected String  getIconUrl ()
        {
            String _loc_3 =null ;
            _loc_1 = DEFAULT_ICON;
            _loc_2 = this.getRewardItem ();
            if (_loc_2)
            {
                _loc_3 = _loc_2.getRelativeImageByName("levelUpIcon");
                _loc_1 = _loc_3 ? (_loc_3) : (_loc_1);
            }
            return _loc_1;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            return new MasteryLevelUpDialogView(param1, this.m_itemName, m_message, m_title, m_icon, m_feedShareViralType);
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.GENERIC_DIALOG_ASSETS, DelayedAssetLoader.MASTERY_ASSETS);
        }//end

         protected Dictionary  createAssetDict ()
        {
            _loc_1 = super.createAssetDict();
            _loc_2 = m_assetDependencies.get(DelayedAssetLoader.MASTERY_ASSETS) ;
            _loc_1.put("cropMastery_dialogStar_empty",  _loc_2.cropMastery_dialogStar_empty);
            _loc_1.put("cropMastery_dialogStar_disabled",  _loc_2.cropMastery_dialogStar_disabled);
            _loc_1.put("cropMastery_dialogStar_full",  _loc_2.cropMastery_dialogStar_full);
            _loc_1.put("cropMastery_hoverStarLarge_empty",  _loc_2.cropMastery_hoverStarLarge_empty);
            _loc_1.put("cropMastery_hoverStarLarge_disabled",  _loc_2.cropMastery_hoverStarLarge_disabled);
            _loc_1.put("cropMastery_hoverStarLarge_full",  _loc_2.cropMastery_hoverStarLarge_full);
            _loc_1.put("cropMastery_smallStar_empty",  _loc_2.cropMastery_smallStar_empty);
            _loc_1.put("cropMastery_smallStar_disabled",  _loc_2.cropMastery_smallStar_disabled);
            _loc_1.put("cropMastery_smallStar_full",  _loc_2.cropMastery_smallStar_full);
            _loc_1.put("marketcardTop",  _loc_2.marketcardTop);
            _loc_1.put("upgradeBarBG",  _loc_2.upgradeBarBG);
            _loc_1.put("upgradeBar",  _loc_2.upgradeBar);
            _loc_1.put("checkbox",  _loc_2.checkbox);
            _loc_1.put("checkmark",  _loc_2.checkmark);
            return _loc_1;
        }//end

        public void  onShareGoods (GenericPopupEvent event )
        {
            _loc_2 =Global.goalManager.getGoal(GoalManager.GOAL_MASTERY )as MasteryGoal ;
            _loc_3 = _loc_2.getLevel(this.m_itemName );
            if (event.button == GenericDialogView.YES)
            {
                Global.world.viralMgr.sendCropMasteryShareGoodsFeed(Global.player, _loc_3, this.m_localizedName, this.m_feedImage, this.m_itemName);
            }
            _loc_4 = this.getRewardItem ();
            if (this.getRewardItem())
            {
                UI.displayInventory(_loc_4.name);
            }
            return;
        }//end

    }



