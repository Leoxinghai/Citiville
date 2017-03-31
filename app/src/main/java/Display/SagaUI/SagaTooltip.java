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

import Classes.*;
import Display.*;
import Display.aswingui.*;
import Modules.quest.Managers.*;
import ZLocalization.*;
//import flash.display.*;
//import flash.text.*;
import org.aswing.*;

    public class SagaTooltip extends Sprite
    {
        private JWindow m_window ;
        private JPanel m_container ;
        private String m_title ;
        private String m_description ;
        private Array m_rewardTexts ;
        private int m_maxInternalWidth ;
        private boolean m_showTitleOnly ;
        public static  String COMING_SOON ="sagaTooltipComingSoon";
        public static  String COMPLETED ="sagaTooltipCompleted";
        public static  String ACT_REWARD ="sagaTooltipActReward";
        public static  String SAGA_REWARD ="sagaTooltipSagaReward";

        public  SagaTooltip ()
        {
            boolean _loc_2 =false ;
            mouseChildren = false;
            mouseEnabled = _loc_2;
            _loc_1 = new EmbeddedArt.mkt_pop_info ();
            this.m_maxInternalWidth = _loc_1.width - 10;
            this.m_container = ASwingHelper.makeSoftBoxJPanelVertical();
            ASwingHelper.setBackground(this.m_container, _loc_1);
            this.m_container.setPreferredWidth(_loc_1.width);
            this.m_window = new JWindow(this);
            this.m_window.setContentPane(this.m_container);
            this.m_window.show();
            return;
        }//end

        public int  getWidth ()
        {
            return this.m_window.getWidth();
        }//end

        public int  getHeight ()
        {
            return this.m_window.getHeight();
        }//end

        private void  init ()
        {
            String _loc_8 =null ;
            JPanel _loc_9 =null ;
            JPanel _loc_10 =null ;
            _loc_1 = ASwingHelper.makeMultilineText(this.m_title ,this.m_maxInternalWidth ,EmbeddedArt.titleFont ,TextFormatAlign.CENTER ,12,EmbeddedArt.orangeTextColor );
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2.append(_loc_1);
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_4 = ASwingHelper.makeMultilineText(this.m_description ,this.m_maxInternalWidth ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,12,EmbeddedArt.darkBlueTextColor );
            _loc_3.append(_loc_4);
            _loc_5 = TextFieldUtil.getLocaleFontSize(16,14,null);
            _loc_6 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","Saga_reward"),EmbeddedArt.titleFont ,_loc_5 ,EmbeddedArt.titleColor );
            _loc_6.setTextFilters(EmbeddedArt.newtitleSmallFilters);
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_7.append(_loc_6);
            for(int i0 = 0; i0 < this.m_rewardTexts.size(); i0++)
            {
            	_loc_8 = this.m_rewardTexts.get(i0);

                _loc_7.append(ASwingHelper.makeLabel(_loc_8, EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.greenTextColor));
            }
            _loc_9 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_9.append(new AssetPane(new EmbeddedArt.mkt_rollover_horizontalRule()));
            _loc_10 = ASwingHelper.makeSoftBoxJPanelVertical();
            ASwingHelper.setEasyBorder(_loc_10, 5, 5, 25, 5);
            _loc_10.append(_loc_2);
            if (!this.m_showTitleOnly)
            {
                _loc_10.appendAll(_loc_9, _loc_3);
                if (this.m_rewardTexts.length > 0)
                {
                    _loc_10.append(_loc_7);
                }
            }
            this.m_container.removeAll();
            this.m_container.append(_loc_10);
            ASwingHelper.prepare(this.m_window);
            return;
        }//end

        public void  setInfoForQuest (String param1 )
        {
            GameQuest _loc_2 =null ;
            Array _loc_3 =null ;
            int _loc_4 =0;
            Object _loc_5 =null ;
            String _loc_6 =null ;
            Item _loc_7 =null ;
            this.m_showTitleOnly = false;
            this.m_title = "";
            this.m_description = "";
            this.m_rewardTexts = new Array();
            switch(param1)
            {
                case COMING_SOON:
                {
                    this.m_title = ZLoc.t("Dialogs", "Saga_comingSoon");
                    this.m_showTitleOnly = true;
                    break;
                }
                case COMPLETED:
                {
                    this.m_title = ZLoc.t("Dialogs", "Saga_completed");
                    this.m_showTitleOnly = true;
                    break;
                }
                case ACT_REWARD:
                {
                    this.m_title = ZLoc.t("Dialogs", "Saga_act_reward_tooltip");
                    this.m_showTitleOnly = true;
                    break;
                }
                case SAGA_REWARD:
                {
                    this.m_title = ZLoc.t("Dialogs", "Saga_reward_tooltip");
                    this.m_showTitleOnly = true;
                    break;
                }
                default:
                {
                    this.m_title = ZLoc.t("Quest", param1 + "_dialog_title");
                    this.m_description = ZLoc.t("Saga", param1 + "_tooltip");
                    if (this.m_description.indexOf(param1 + "_tooltip") != -1)
                    {
                        this.m_description = ZLoc.t("Quest", param1 + "_dialog_header");
                    }
                    _loc_2 = Global.questManager.getQuestByName(param1);
                    if (_loc_2)
                    {
                        _loc_3 = _loc_2.getCurrentRewardsData();
                        _loc_4 = 0;
                        while (_loc_4 < _loc_3.length())
                        {

                            _loc_5 = _loc_3.get(_loc_4);
                            if (_loc_5.get("resource") != "xpromo")
                            {
                                _loc_6 = ZLoc.t("Quest", "reward_" + String(_loc_5.resource), {amount:Utilities.formatNumber(int(_loc_5.get("amount")))});
                                if (_loc_5.itemName != null && _loc_5.itemName != "")
                                {
                                    _loc_7 = Global.gameSettings().getItemByName(_loc_5.itemName);
                                    _loc_6 = _loc_7.localizedName;
                                }
                                if (_loc_5.locInProgress != "" && _loc_5.locInProgress != null)
                                {
                                    _loc_6 = ZLoc.t("Dialogs", _loc_5.locInProgress, {reward:_loc_6});
                                }
                                else if (_loc_5.loc != "" && _loc_5.loc != null)
                                {
                                    _loc_6 = ZLoc.t("Dialogs", _loc_5.loc, {reward:_loc_6});
                                }
                                this.m_rewardTexts.push(_loc_6);
                            }
                            _loc_4++;
                        }
                    }
                    break;
                }
            }
            this.init();
            return;
        }//end

    }



