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
import Display.aswingui.*;
import Modules.franchise.display.*;
import Modules.goals.*;
import Modules.goals.mastery.*;
//import flash.display.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class MasteryLevelUpDialogView extends CharacterResponseDialogView
    {
        protected  int STAR_GAP =18;
        protected String m_itemName ;
        protected CheckBoxComponent m_checkbox ;

        public  MasteryLevelUpDialogView (Dictionary param1 ,String param2 ,String param3 ,String param4 ,String param5 ,String param6 )
        {
            this.m_itemName = param2;
            super(param1, param3, param4, TYPE_SHARECANCEL, null, param5, ICON_POS_LEFT, param6);
            return;
        }//end

         protected Component  createTextComponent (double param1 )
        {
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_2.append(this.createMainMessagePanel(param1));
            _loc_2.append(ASwingHelper.verticalStrut(10));
            _loc_2.append(this.createProgressPanel(param1));
            _loc_2.append(ASwingHelper.verticalStrut(10));
            _loc_2.append(this.createSubMessagePanel(param1));
            _loc_2.append(ASwingHelper.verticalStrut(10));
            _loc_2.append(this.createCheckboxPanel(param1));
            _loc_2.append(ASwingHelper.verticalStrut(15));
            return _loc_2;
        }//end

        protected JPanel  createMainMessagePanel (double param1 )
        {
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            int _loc_3 =1;
            String _loc_4 ="";
            _loc_5 =Global.goalManager.getGoal(GoalManager.GOAL_MASTERY )as MasteryGoal ;
            _loc_6 =Global.gameSettings().getItemByName(this.m_itemName );
            if (_loc_5 && _loc_6)
            {
                _loc_3 = _loc_5.getLevel(this.m_itemName);
                _loc_4 = _loc_6.localizedName;
            }
            _loc_7 = ZLoc.t("Dialogs","MasteryLevelUp_message",{level _loc_3 ,itemName });
            _loc_8 = ASwingHelper.makeMultilineCapsText(_loc_7 ,param1 ,EmbeddedArt.titleFont ,TextFormatAlign.CENTER ,16,EmbeddedArt.blueTextColor );
            _loc_2.append(_loc_8);
            return _loc_2;
        }//end

        protected JPanel  createSubMessagePanel (double param1 )
        {
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_3 = ASwingHelper.makeMultilineText(m_message ,param1 ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,16,EmbeddedArt.brownTextColor );
            _loc_2.append(_loc_3);
            return _loc_2;
        }//end

        protected JPanel  createProgressPanel (double param1 )
        {
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2.append(this.createStarPanel());
            _loc_2.append(ASwingHelper.horizontalStrut(15));
            _loc_2.append(this.createRatioPanel());
            return _loc_2;
        }//end

        protected JPanel  createRatioPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical ();
            int _loc_2 =0;
            int _loc_3 =0;
            _loc_4 =Global.goalManager.getGoal(GoalManager.GOAL_MASTERY )as MasteryGoal ;
            if (Global.goalManager.getGoal(GoalManager.GOAL_MASTERY) as MasteryGoal)
            {
                _loc_2 = _loc_4.getCount(this.m_itemName);
                _loc_3 = _loc_4.getTotalRequiredForLevel(this.m_itemName, _loc_4.getMaxLevel(this.m_itemName));
            }
            _loc_5 = ZLoc.t("Dialogs","MasteryProgressRatio",{count _loc_2 ,total });
            _loc_6 = ASwingHelper.makeTextField(_loc_5 ,EmbeddedArt.titleFont ,22,EmbeddedArt.orangeTextColor );
            _loc_1.append(_loc_6);
            return _loc_1;
        }//end

        protected JPanel  createStarPanel ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            _loc_3 =Global.goalManager.getGoal(GoalManager.GOAL_MASTERY )as MasteryGoal ;
            if (_loc_3)
            {
                _loc_1 = _loc_3.getLevel(this.m_itemName);
                _loc_2 = _loc_3.getMaxLevel(this.m_itemName);
            }
            return new StarRatingComponent(_loc_1, true, DelayedAssetLoader.MASTERY_ASSETS, _loc_2, this.STAR_GAP, "cropMastery_dialogStar");
        }//end

        protected JPanel  createCheckboxPanel (double param1 )
        {
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT );
            DisplayObject _loc_3 =(DisplayObject)new m_assetDict.get( "checkbox");
            DisplayObject _loc_4 =(DisplayObject)new m_assetDict.get( "checkmark");
            _loc_5 =Global.world.viralMgr.getGlobalPublishStreamCheck(MasteryGoal.PUBLISH_STREAM_FEATURE_NAME );
            this.m_checkbox = new CheckBoxComponent(_loc_5, ZLoc.t("Dialogs", "AutoPost"), this.onToggleAutoPost, _loc_4, _loc_3);
            _loc_2.append(this.m_checkbox);
            return _loc_2;
        }//end

        protected void  onToggleAutoPost (boolean param1 )
        {
            Global.world.viralMgr.setGlobalPublishStreamCheck(this.m_checkbox.isChecked, MasteryGoal.PUBLISH_STREAM_FEATURE_NAME);
            return;
        }//end

         protected JPanel  createIconPane ()
        {
            MasteryDefinition _loc_3 =null ;
            _loc_1 = super.createIconPane();
            _loc_2 =Global.goalManager.getGoal(GoalManager.GOAL_MASTERY )as MasteryGoal ;
            if (_loc_2)
            {
                _loc_3 = _loc_2.getLevelDefinition(this.m_itemName);
                if (_loc_3 && _loc_3.rewards && _loc_3.rewards.get("item"))
                {
                    ASwingHelper.setEasyBorder(_loc_1, 0, 15, 0, 0);
                }
            }
            return _loc_1;
        }//end

    }


