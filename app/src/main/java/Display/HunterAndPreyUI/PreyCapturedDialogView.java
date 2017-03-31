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

import Classes.*;
import Classes.doobers.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Modules.bandits.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class PreyCapturedDialogView extends GenericDialogView
    {
        protected PreyData m_bandit ;
        protected Player m_cop ;
        public static  int TEXT_WIDTH =300;

        public  PreyCapturedDialogView (Dictionary param1 ,PreyData param2 ,Player param3 ,boolean param4 =false ,String param5 ="",String param6 ="",Function param7 =null ,Function param8 =null ,String param9 ="")
        {
            this.m_bandit = param2;
            this.m_cop = param3;
            _loc_10 = TYPE_CUSTOM_OK;
            if (param4)
            {
                _loc_10 = TYPE_OK;
            }
            super(param1, param5, param6, _loc_10, param7, "", 0, "", param8, param9);
            return;
        }//end

         protected void  init ()
        {
            super.init();
            PreyUtil.logDialogStats(this.m_bandit.groupId, "view", "prey_captured_ui", this.m_bandit);
            return;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_1 =null ;
            JPanel _loc_2 =null ;
            _loc_1 = new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, -10, AsWingConstants.CENTER));
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            textArea = this.createTextArea();
            ASwingHelper.prepare(textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 20, 0, 0)));
            _loc_1.append(_loc_2);
            _loc_1.append(ASwingHelper.verticalStrut(20));
            _loc_1.append(textArea);
            _loc_1.append(ASwingHelper.verticalStrut(BUTTON_MARGIN));
            _loc_3 = createButtonPanel();
            _loc_1.append(_loc_3);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

         protected JPanel  createTextArea ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            JPanel _loc_2 =new JPanel(new FlowLayout(FlowLayout.LEFT ));
            _loc_3 = this.makeImagePanel ();
            _loc_4 = ASwingHelper.makeMultilineText(m_message ,TEXT_WIDTH ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,14,EmbeddedArt.brownTextColor );
            _loc_2.appendAll(_loc_3, _loc_4);
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT ,5);
            _loc_6 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_7 = m_assetDict.get("banditIcon") ;
            AssetPane _loc_8 =new AssetPane(_loc_7 );
            _loc_6.append(_loc_8);
            _loc_9 = this.makeRewardPanel ();
            _loc_5.appendAll(_loc_6, _loc_9);
            _loc_1.appendAll(_loc_2, _loc_5);
            return _loc_1;
        }//end

        protected JPanel  makeImagePanel ()
        {
            DisplayObject imageAsset ;
            AssetPane ap ;
            jp = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            imageAsset =(DisplayObject) new m_assetDict.get("police_slot_unfilled");
            portraitURL = Global.getAssetURL("assets/dialogs/citysam_neighbor_card.jpg");
            if (this.m_cop)
            {
                portraitURL = this.m_cop.snUser.picture;
            }
            else if (PreyManager.getHunterPreyMode(this.m_bandit.groupId).get("id") == PreyManager.MODE_SUPERHERO)
            {
                portraitURL = Global.getAssetURL("assets/dialogs/HunterPrey/bandits/copsAndBandits_PigeonManIcon.png");
            }
            LoadingManager .load (portraitURL ,void  (Event event )
            {
                imageAsset = event.target.content;
                ap.setAsset(imageAsset);
                return;
            }//end
            );
            ap = new AssetPane(imageAsset);
            jp.append(ap);
            return jp;
        }//end

        protected JPanel  makeRewardPanel ()
        {
            JPanel _loc_3 =null ;
            DisplayObject _loc_4 =null ;
            JPanel _loc_5 =null ;
            DisplayObject _loc_6 =null ;
            AssetPane _loc_7 =null ;
            int _loc_8 =0;
            int _loc_9 =0;
            ASColor _loc_10 =null ;
            DisplayObject _loc_11 =null ;
            JLabel _loc_12 =null ;
            JPanel _loc_13 =null ;
            DisplayObject _loc_14 =null ;
            JLabel _loc_15 =null ;
            JPanel _loc_16 =null ;
            DisplayObject _loc_17 =null ;
            JLabel _loc_18 =null ;
            JPanel _loc_19 =null ;
            DisplayObject _loc_20 =null ;
            JLabel _loc_21 =null ;
            JPanel _loc_22 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_2 = ASwingHelper.makeMultilineCapsText(this.m_bandit.name ,250,EmbeddedArt.titleFont ,TextFormatAlign.LEFT ,15,EmbeddedArt.darkBlueTextColor );
            _loc_1.appendAll(_loc_2, ASwingHelper.verticalStrut(5));
            if (this.m_bandit.getRewardItems().length > 0)
            {
                _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                _loc_4 =(DisplayObject) new m_assetDict.get("rewardCard_unlocked");
                ASwingHelper.setSizedBackground(_loc_3, _loc_4, new Insets(0, 26, 6, 26));
                _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                _loc_6 = m_assetDict.get("itemReward");
                _loc_7 = new AssetPane(_loc_6);
                ASwingHelper.setForcedSize(_loc_7, new IntDimension(_loc_6.width, _loc_6.height));
                _loc_8 = _loc_3.getPreferredWidth() / 2 - _loc_6.width / 2;
                _loc_5.appendAll(ASwingHelper.horizontalStrut(_loc_8), _loc_7);
                _loc_9 = _loc_3.getPreferredHeight() / 2 - _loc_6.height / 2;
                _loc_3.appendAll(ASwingHelper.verticalStrut(_loc_9), _loc_5);
                _loc_1.append(_loc_3);
            }
            else
            {
                _loc_10 = new ASColor(EmbeddedArt.blueTextColor);
                if (this.m_bandit.getRewardDooberTotals().get(Doober.DOOBER_XP) != null)
                {
                    _loc_11 =(DisplayObject) new EmbeddedArt.smallXPIcon();
                    _loc_12 = new JLabel(ZLoc.t("Dialogs", "NumXP", {num:this.m_bandit.getRewardDooberTotals().get(Doober.DOOBER_XP)}));
                    _loc_12.setFont(ASwingHelper.getBoldFont(14));
                    _loc_12.setForeground(_loc_10);
                    _loc_13 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    _loc_13.appendAll(ASwingHelper.horizontalStrut(15), new AssetPane(_loc_11));
                    _loc_13.appendAll(ASwingHelper.horizontalStrut(5), _loc_12);
                    _loc_1.append(_loc_13);
                }
                if (this.m_bandit.getRewardDooberTotals().get(Doober.DOOBER_GOODS) != null)
                {
                    _loc_14 =(DisplayObject) new EmbeddedArt.smallGoodsIcon();
                    _loc_15 = new JLabel(ZLoc.t("Dialogs", "NumGoods", {num:this.m_bandit.getRewardDooberTotals().get(Doober.DOOBER_GOODS)}));
                    _loc_15.setFont(ASwingHelper.getBoldFont(14));
                    _loc_15.setForeground(_loc_10);
                    _loc_16 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    _loc_16.appendAll(ASwingHelper.horizontalStrut(15), new AssetPane(_loc_14));
                    _loc_16.appendAll(ASwingHelper.horizontalStrut(5), _loc_15);
                    _loc_1.append(_loc_16);
                }
                if (this.m_bandit.getRewardDooberTotals().get(Doober.DOOBER_COIN) != null)
                {
                    _loc_17 =(DisplayObject) new EmbeddedArt.smallCoinIcon();
                    _loc_18 = new JLabel(ZLoc.t("Dialogs", "NumCoins", {num:this.m_bandit.getRewardDooberTotals().get(Doober.DOOBER_COIN)}));
                    _loc_18.setFont(ASwingHelper.getBoldFont(14));
                    _loc_18.setForeground(_loc_10);
                    _loc_19 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    _loc_19.appendAll(ASwingHelper.horizontalStrut(15), new AssetPane(_loc_17));
                    _loc_19.appendAll(ASwingHelper.horizontalStrut(5), _loc_18);
                    _loc_1.append(_loc_19);
                }
                if (this.m_bandit.getRewardDooberTotals().get(Doober.DOOBER_ENERGY) != null)
                {
                    _loc_20 =(DisplayObject) new EmbeddedArt.smallEnergyIcon();
                    _loc_21 = new JLabel(ZLoc.t("Dialogs", "NumEnergy", {num:this.m_bandit.getRewardDooberTotals().get(Doober.DOOBER_ENERGY)}));
                    _loc_21.setFont(ASwingHelper.getBoldFont(14));
                    _loc_21.setForeground(_loc_10);
                    _loc_22 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    _loc_22.appendAll(ASwingHelper.horizontalStrut(15), new AssetPane(_loc_20));
                    _loc_22.appendAll(ASwingHelper.horizontalStrut(5), _loc_21);
                    _loc_1.append(_loc_22);
                }
            }
            return _loc_1;
        }//end

         protected void  onAccept (AWEvent event )
        {
            super.onAccept(event);
            PreyUtil.logDialogStats(this.m_bandit.groupId, "celebrate_with_resources", "prey_captured_ui", this.m_bandit);
            return;
        }//end

         protected void  onCancelX (Object param1)
        {
            super.onCancelX(param1);
            PreyUtil.logDialogStats(this.m_bandit.groupId, "X", "prey_captured_ui", this.m_bandit);
            return;
        }//end

    }



