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
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;

    public class RollCallIntroDialogView extends GenericDialogView
    {
        protected RollCallDataMechanic m_mechData ;
        protected TieredDooberMechanic m_rewardData ;
        private int m_nextRewardIndex =0;
        private int m_checkinsLeft =0;
        private static  int MAX_DISPLAY_CREW =4;

        public  RollCallIntroDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",Function param9 =null ,String param10 ="")
        {
            this.m_mechData =(RollCallDataMechanic) MechanicManager.getInstance().getMechanicInstance(param1.get("spawner"), "rollCall", MechanicManager.ALL);
            this.m_rewardData =(TieredDooberMechanic) MechanicManager.getInstance().getMechanicInstance(param1.get("spawner"), "rollCallTieredDooberValue", MechanicManager.ALL);
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10);
            return;
        }//end

        public int  nextRewardIndex ()
        {
            return this.m_nextRewardIndex;
        }//end

        public void  nextRewardIndex (int param1 )
        {
            this.m_nextRewardIndex = param1;
            return;
        }//end

        public int  checkinsLeft ()
        {
            return this.m_checkinsLeft;
        }//end

        public void  checkinsLeft (int param1 )
        {
            this.m_checkinsLeft = param1;
            return;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_1 =null ;
            JPanel _loc_3 =null ;
            _loc_1 = new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            textArea = this.createTextArea();
            ASwingHelper.prepare(textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            _loc_1.append(ASwingHelper.verticalStrut(2));
            if (m_type != TYPE_NOBUTTONS)
            {
                _loc_1.append(_loc_2);
            }
            _loc_1.append(ASwingHelper.verticalStrut(2));
            _loc_1.append(textArea);
            _loc_1.append(ASwingHelper.verticalStrut(-5));
            if (m_type != TYPE_MODAL && m_type != TYPE_NOBUTTONS)
            {
                _loc_3 = createButtonPanel();
                _loc_1.append(_loc_3);
            }
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

         protected JPanel  createTextArea ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,5);
            _loc_1.setPreferredWidth(m_bgAsset.width);
            ASwingHelper.setEasyBorder(_loc_1, 0, 8);
            _loc_2 = this.makeSamPanel ();
            _loc_3 = this.makeRewardsPane ();
            _loc_1.appendAll(_loc_2, _loc_3);
            return _loc_1;
        }//end

        protected AssetPane  makeBorder ()
        {
            Sprite _loc_1 =new Sprite ();
            _loc_1.graphics.beginFill(EmbeddedArt.lightBlueTextColor);
            _loc_1.graphics.drawRoundRect(0, 0, 3, 78, 2, 2);
            _loc_1.graphics.endFill();
            AssetPane _loc_2 =new AssetPane(_loc_1 );
            return _loc_2;
        }//end

        protected JPanel  makeRewardsPane ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER ,10);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_3 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","RollCall_duration",{hours m_assetDict.get( "duration") }),EmbeddedArt.titleFont ,20,EmbeddedArt.lightOrangeTextColor ,JLabel.RIGHT );
            _loc_4 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","RollCall_toCheckIn"),EmbeddedArt.defaultFontNameBold ,12,EmbeddedArt.blueTextColor ,JLabel.RIGHT );
            _loc_2.appendAll(_loc_3, _loc_4);
            AssetPane _loc_5 =new AssetPane(new m_assetDict.get( "payroll_icon_clock") );
            _loc_1.appendAll(_loc_2, ASwingHelper.topAlignElement(_loc_5), this.makeBorder(), this.generateRewardPane());
            return _loc_1;
        }//end

        protected JPanel  generateRewardPane ()
        {
            boolean rewardComplete ;
            Object tierObj ;
            int checkinsNeeded ;
            Dictionary tierRewardObject ;
            Object key ;
            DisplayObject rewardAsset ;
            CrewRewardIntroSprite reward ;
            AssetPane rewardPane ;
            int verticalStrutHeight ;
            String tierName ;
            String itemName ;
            double tierAmount ;
            Item item ;
            Loader itemLoader ;
            jp = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT,10);
            numTiers = this.m_rewardData.getNumTiers();
            int i ;
            while (i < numTiers)
            {

                tierObj = this.m_rewardData.getTierInfo(i);
                checkinsNeeded = tierObj.num;
                tierRewardObject = this.m_rewardData.getTierRewardInfo(i);
                int _loc_2 =0;
                _loc_3 = tierRewardObject;
                for(int i0 = 0; i0 < tierRewardObject.size(); i0++)
                {
                		key = tierRewardObject.get(i0);


                    tierName = key.toString();
                    itemName;
                    tierAmount;
                    if (tierRewardObject.get(key) instanceof Number)
                    {
                        tierAmount = tierRewardObject.get(key);
                        continue;
                    }
                    itemName = tierRewardObject.get(key).name;
                    tierAmount = tierRewardObject.get(key).count;
                }
                rewardAsset = new Sprite();
                switch(tierName)
                {
                    case "coin":
                    {
                        rewardAsset =(DisplayObject) new m_assetDict.get("payroll_reward_coin");
                        break;
                    }
                    case "xp":
                    {
                        rewardAsset =(DisplayObject) new m_assetDict.get("payroll_reward_xp");
                        break;
                    }
                    case "energy":
                    {
                        rewardAsset =(DisplayObject) new m_assetDict.get("payroll_reward_energy");
                        break;
                    }
                    case "item":
                    {
                        item = Global.gameSettings().getItemByName(itemName);
                        if (item)
                        {
                            itemLoader =LoadingManager .load (item .icon ,Curry .curry (void  (JPanel param1 ,Event param2 )
            {
                if (rewardAsset instanceof DisplayObjectContainer)
                {
                    ((DisplayObjectContainer)rewardAsset).addChild(itemLoader.content);
                    itemLoader.content.x = itemLoader.content.x - itemLoader.content.width / 2;
                    itemLoader.content.y = itemLoader.content.y - itemLoader.content.height / 2;
                }
                ASwingHelper.prepare(param1);
                param1.dispatchEvent(new UIEvent(UIEvent.REFRESH_DIALOG, "itemIconLoaded", true));
                return;
            }//end
            , this));
                        }
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                reward = new CrewRewardIntroSprite(m_assetDict, rewardAsset, tierAmount);
                rewardPane = new AssetPane(reward);
                verticalStrutHeight;
                if (Global.localizer.langCode == "ja")
                {
                    verticalStrutHeight;
                }
                if (!rewardComplete)
                {
                    ASwingHelper.setEasyBorder(rewardPane, verticalStrutHeight);
                }
                jp.append(rewardPane);
                if (i != 3)
                {
                    jp.append(new AssetPane(new m_assetDict.get("payroll_rewardArrow")));
                }
                i = (i + 1);
            }
            return jp;
        }//end

        protected JPanel  makeSamPanel ()
        {
            JPanel _loc_10 =null ;
            JPanel _loc_11 =null ;
            JLabel _loc_12 =null ;
            JLabel _loc_13 =null ;
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT ,10);
            AssetPane _loc_2 =new AssetPane(new m_assetDict.get( "payroll_sam") );
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,20);
            _loc_4 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT ,20);
            _loc_5 = this.m_mechData.getCrewList ();
            String _loc_6 ="";
            int _loc_7 =0;
            while (_loc_7 < Math.min(_loc_5.length, MAX_DISPLAY_CREW))
            {

                _loc_10 = ASwingHelper.makeFriendImageFromZid(_loc_5.get(_loc_7));
                _loc_4.append(_loc_10);
                if (_loc_5.get(_loc_7).substr(0, 1) == "-")
                {
                    _loc_6 = _loc_6 + (ZLoc.t("Main", "FakeFriendName") + ", ");
                }
                else if (Global.player.isFriendIDInList(_loc_5.get(_loc_7)))
                {
                    _loc_6 = _loc_6 + (Global.player.findFriendById(_loc_5.get(_loc_7)).firstName + ", ");
                }
                _loc_7++;
            }
            if (_loc_5.length > MAX_DISPLAY_CREW)
            {
                _loc_11 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                _loc_12 = ASwingHelper.makeLabel("+" + String(_loc_5.length - MAX_DISPLAY_CREW), EmbeddedArt.titleFont, 18, EmbeddedArt.whiteTextColor, JLabel.CENTER);
                _loc_13 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "RollCall_more"), EmbeddedArt.defaultFontNameBold, 10, EmbeddedArt.whiteTextColor, JLabel.CENTER);
                _loc_11.appendAll(_loc_12, _loc_13);
                _loc_4.append(_loc_11);
            }
            _loc_8 = ZLoc.t("Dialogs","RollCall_getfriends",{friendNames _loc_6 });
            _loc_9 = ASwingHelper.makeMultilineText(_loc_8 ,435,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,16,EmbeddedArt.blueTextColor );
            _loc_3.appendAll(_loc_4, _loc_9);
            _loc_1.appendAll(_loc_2, ASwingHelper.topAlignElement(_loc_3));
            return _loc_1;
        }//end

         protected void  makeBackground ()
        {
            if (m_bgAsset)
            {
                ASwingHelper.setSizedBackground(this, m_bgAsset, new Insets(0, 0, 10, 0));
            }
            return;
        }//end

    }



