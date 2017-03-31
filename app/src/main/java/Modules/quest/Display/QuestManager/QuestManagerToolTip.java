package Modules.quest.Display.QuestManager;

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
import Display.aswingui.*;
import ZLocalization.*;
//import flash.display.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class QuestManagerToolTip extends JPanel
    {
        protected DisplayObject m_bgAsset ;
        protected JPanel m_centerPanel ;
        public static  int TEXT_WIDTH =170;

        public  QuestManagerToolTip (LayoutManager param1)
        {
            super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 5, SoftBoxLayout.CENTER));
            this.setPreferredSize(new IntDimension(QuestManagerView.QUEST_MANAGER_TOOLTIP_WIDTH, QuestManagerView.QUEST_MANAGER_TOOLTIP_HEIGHT));
            this.makeBackground();
            return;
        }//end

        public void  emptyToolTip ()
        {
            this.removeAll();
            return;
        }//end

        public void  initializeToolTip (String param1 ="",Array param2 ,Array param3 =null ,boolean param4 =true )
        {
            int _loc_7 =0;
            String _loc_8 =null ;
            Object _loc_9 =null ;
            String _loc_10 =null ;
            Item _loc_11 =null ;
            this.emptyToolTip();
            this.m_centerPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP, 5);
            this.m_centerPanel.append(ASwingHelper.verticalStrut(QuestManagerUI.TOP_INSET_HEIGHT + 30));
            _loc_5 = ASwingHelper.makeMultilineText(param1 ,TEXT_WIDTH ,EmbeddedArt.titleFont ,TextFormatAlign.LEFT ,18,EmbeddedArt.whiteTextColor );
            this.m_centerPanel.append(_loc_5);
            _loc_5 = ASwingHelper.makeMultilineText(ZLoc.t("Quest", "quest_manager_overview_tasks"), TEXT_WIDTH, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 18, EmbeddedArt.blueTextColor);
            this.m_centerPanel.append(_loc_5);
            if (param4)
            {
                _loc_7 = 0;
                for(int i0 = 0; i0 < param2.size(); i0++)
                {
                		_loc_8 = param2.get(i0);

                    _loc_5 = ASwingHelper.makeMultilineText(_loc_8, TEXT_WIDTH, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 12, EmbeddedArt.whiteTextColor);
                    this.m_centerPanel.append(_loc_5);
                }
            }
            else
            {
                _loc_5 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs", "TimedQuests_start_desc"), TEXT_WIDTH, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 12, EmbeddedArt.whiteTextColor);
                this.m_centerPanel.append(_loc_5);
            }
            _loc_5 = ASwingHelper.makeMultilineText(ZLoc.t("Quest", "quest_manager_overview_rewards"), TEXT_WIDTH, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 18, EmbeddedArt.yellowTextColor);
            this.m_centerPanel.append(_loc_5);
            int _loc_6 =0;
            while (_loc_6 < param3.length())
            {

                _loc_9 = param3.get(_loc_6);
                if (_loc_9.get("resource") != "xpromo")
                {
                    _loc_10 = ZLoc.t("Quest", "reward_" + String(_loc_9.resource), {amount:Utilities.formatNumber(int(_loc_9.amount))});
                    if (_loc_9.itemName != null && _loc_9.itemName != "")
                    {
                        _loc_11 = Global.gameSettings().getItemByName(_loc_9.itemName);
                        _loc_10 = _loc_11.localizedName;
                    }
                    if (_loc_9.locInProgress != "" && _loc_9.locInProgress != null)
                    {
                        _loc_10 = ZLoc.t("Dialogs", _loc_9.locInProgress, {reward:_loc_10});
                    }
                    else if (_loc_9.loc != "" && _loc_9.loc != null)
                    {
                        _loc_10 = ZLoc.t("Dialogs", _loc_9.loc, {reward:_loc_10});
                    }
                    _loc_5 = ASwingHelper.makeMultilineText(_loc_10, TEXT_WIDTH, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 12, EmbeddedArt.whiteTextColor);
                    this.m_centerPanel.append(_loc_5);
                }
                _loc_6++;
            }
            this.m_centerPanel.append(ASwingHelper.verticalStrut(QuestManagerUI.TOP_INSET_HEIGHT));
            this.append(ASwingHelper.horizontalStrut(10));
            this.append(this.m_centerPanel);
            this.setMaximumHeight(this.m_centerPanel.getPreferredHeight());
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  makeBackground ()
        {
            this.m_bgAsset =(DisplayObject) new QuestManagerView.assetDict.get("questManagerBG");
            MarginBackground _loc_1 =new MarginBackground(this.m_bgAsset ,new Insets(QuestManagerUI.TOP_INSET_HEIGHT +30));
            this.setBackgroundDecorator(_loc_1);
            return;
        }//end

    }



