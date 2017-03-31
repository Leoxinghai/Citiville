package Modules.downtown;

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
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import ZLocalization.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.ext.*;

    public class DownTownCenterTooltip extends FlyFishDialog
    {
        protected Item m_item ;
        protected boolean m_finalLevel =false ;
        protected String m_stringKeyPrefix ="Default";
        protected AssetPane m_bonusTextArea ;
        protected AssetPane m_bonustHintTextArea ;

        public  DownTownCenterTooltip (String param1 ,String param2 )
        {
            this.m_item = Global.gameSettings().getItemByName(param1);
            this.m_stringKeyPrefix = param2;
            super("assets/flyfish/DownTownCenter_toolTip.xml");
            return;
        }//end

         protected Array  getAssetDependencies ()
        {
            return .get(DelayedAssetLoader.DOWN_TOWN_CENTER_ASSETS, DelayedAssetLoader.ATTRACTIONS_ASSETS, DelayedAssetLoader.NEW_QUEST_ASSETS, DelayedAssetLoader.MARKET_ASSETS);
        }//end

         protected void  performDialogActions ()
        {
            this.refreshTooltip();
            return;
        }//end

        public void  setItem (String param1 )
        {
            if (this.m_item && (this.m_item.name == param1 || !param1))
            {
                return;
            }
            this.m_item = Global.gameSettings().getItemByName(param1);
            this.refreshTooltip();
            return;
        }//end

        protected void  refreshTooltip ()
        {
            JLabel label ;
            MultilineLabel multiLabel ;
            AssetPane buildingPreview ;
            AssetPane previewBG ;
            String hintText ;
            if (!this.m_item || !pane)
            {
                return;
            }
            upgradeChain = UpgradeDefinition.getFullUpgradeChain(Item.findUpgradeRoot(this.m_item));
            lastLevel = upgradeChain.get((upgradeChain.length -1)).level ;
            this.m_finalLevel = this.m_item.level == lastLevel;
            label =(JLabel) pane.getComponent("levelText");
            label.setText(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "level_text", {amount:this.m_item.level})));
            ASwingHelper.setProperFont(label, EmbeddedArt.titleFont);
            buildingPreview =(AssetPane) pane.getComponent("buidling_deco");
            previewBG =(AssetPane) pane.getComponent("tooltipBlueGradient_bg");
            DataItemImageCell dataCell =new DataItemImageCell(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,0,SoftBoxLayout.CENTER ));
            dataCell.containerScaleHeight = previewBG.getAsset().height - 2;
            dataCell.containerScaleWidth = previewBG.getAsset().width - 2;
            dataCell.targetAssetPane = buildingPreview;
            dataCell .loadedCB =void  ()
            {
                ASwingHelper.setEasyBorder(buildingPreview, 0, (previewBG.getAsset().width - 2 - buildingPreview.getAsset().width) / 2);
                return;
            }//end
            ;
            dataCell.setCellValue(this.m_item);
            label =(JLabel) pane.getComponent("downTownAllowsText");
            label.setText(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "TT_Allows")));
            ASwingHelper.setProperFont(label, EmbeddedArt.defaultFontNameBold);
            label =(JLabel) pane.getComponent("populationNumberText");
            label.setText(LocaleFormatter.formatNumber(this.m_item.populationCapYield * Global.gameSettings().getNumber("populationScale", 10)));
            ASwingHelper.setProperFont(label, EmbeddedArt.defaultFontNameBold);
            label =(JLabel) pane.getComponent("downTownEarningsText");
            label.setText(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "TT_Earnings")));
            ASwingHelper.setProperFont(label, EmbeddedArt.defaultFontNameBold);
            label =(JLabel) pane.getComponent("earningsNumberText");
            label.setText(LocaleFormatter.formatNumber(this.m_item.coinYield));
            ASwingHelper.setProperFont(label, EmbeddedArt.defaultFontNameBold);
            label =(JLabel) pane.getComponent("downTownHoldsText");
            label.setText(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "DownTownCenter_NeighborhoodCapacityTitle")));
            ASwingHelper.setProperFont(label, EmbeddedArt.defaultFontNameBold);
            label =(JLabel) pane.getComponent("communityBuildingsText");
            label.setText(ZLoc.t("Dialogs", "DownTownCenter_wonderUpgrade_building_count", {count:LocaleFormatter.formatNumber(this.m_item.storageInitCapacity)}));
            ASwingHelper.setProperFont(label, EmbeddedArt.defaultFontNameBold);
            label =(JLabel) pane.getComponent("downTownBonusText");
            label.setText(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "TT_Bonus")));
            ASwingHelper.setProperFont(label, EmbeddedArt.defaultFontNameBold);
            multiLabel =(MultilineLabel) pane.getComponent("downtownBonusInfoText");
            panel = pane.getComponent("downTown_mysterBonus");
            if (panel.contains(multiLabel))
            {
                panel.remove(multiLabel);
            }
            if (this.m_bonusTextArea && panel.contains(this.m_bonusTextArea))
            {
                panel.remove(this.m_bonusTextArea);
            }
            text = ZLoc.t("Dialogs",this.m_stringKeyPrefix+DownTownCenterDialog.STRING_KEY_BASE+"mystery_bonus",{multiplierLocaleFormatter.formatNumber(this.m_item.bonusPercent/100)});
            this.m_bonusTextArea = ASwingHelper.makeMultilineText(text, 100, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 12, 3394560);
            panel.append(this.m_bonusTextArea);
            hint = pane.getComponent("toolTipBottomInfoPanel");
            if (!this.m_finalLevel)
            {
                hint.visible = false;
            }
            else
            {
                hint.visible = true;
                multiLabel =(MultilineLabel) pane.getComponent("tootlTip_bottomInfo_Text");
                if (hint.contains(multiLabel))
                {
                    hint.remove(multiLabel);
                }
                if (this.m_bonustHintTextArea && hint.contains(this.m_bonustHintTextArea))
                {
                    hint.remove(this.m_bonustHintTextArea);
                }
                hintText = ZLoc.t("Dialogs", this.m_stringKeyPrefix + DownTownCenterDialog.STRING_KEY_BASE + "finalLevel_Tooltip", {level:this.m_item.level});
                this.m_bonustHintTextArea = ASwingHelper.makeMultilineText(hintText, 150, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 12, 8213307);
                hint.append(this.m_bonustHintTextArea);
            }
            ASwingHelper.prepare(m_jpanel);
            return;
        }//end

    }



