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
import Display.aswingui.*;
import Events.*;
import Modules.bandits.*;
//import flash.display.*;
//import flash.text.*;
import org.aswing.*;

    public class PreyPanel extends JPanel
    {
        protected Array m_prey ;
        protected JPanel m_headerPanel ;
        protected JPanel m_bodyPanel ;
        protected JPanel m_footerPanel ;
        protected JPanel m_shelf ;
        protected TextField m_banditTextField ;
        protected TextFormat m_banditTextFormat ;
        public static  int BANDITS_PER_PAGE =4;

        public  PreyPanel ()
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            this.init();
            Global.stage.addEventListener(GenericObjectEvent.REFRESH_BANDIT_INFO, this.onUpdateBanditText, false, 0, true);
            return;
        }//end

        protected void  init ()
        {
            this.m_banditTextField = new TextField();
            this.m_banditTextField.text = "";
            this.m_banditTextField.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            this.m_banditTextField.autoSize = TextFieldAutoSize.LEFT;
            this.m_banditTextField.multiline = true;
            this.m_banditTextField.wordWrap = true;
            this.m_banditTextField.antiAliasType = AntiAliasType.ADVANCED;
            this.m_banditTextField.gridFitType = GridFitType.PIXEL;
            this.m_banditTextFormat = new TextFormat(EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.darkBlueTextColor, false, false, false, null, null, TextFormatAlign.LEFT);
            this.m_prey = PreyManager.getPreyDefinitions(HunterDialog.groupId);
            while (this.m_prey.length % BANDITS_PER_PAGE != 0)
            {

                this.m_prey.push(new PreyData("", null));
            }
            this.makeBackground();
            ASwingHelper.setEasyBorder(this, 0, 0);
            this.m_headerPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_bodyPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_footerPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_footerPanel.addChild(this.m_banditTextField);
            this.m_banditTextField.x = 10;
            this.m_banditTextField.y = 5;
            this.setPreferredWidth(671);
            this.setPreferredHeight(390);
            this.append(ASwingHelper.verticalStrut(8));
            this.append(this.makeBodyPanel());
            this.append(ASwingHelper.verticalStrut(5));
            this.append(this.makeFooterPanel());
            this.append(ASwingHelper.verticalStrut(15));
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  makeBackground ()
        {
            DisplayObject _loc_1 =(DisplayObject)new HunterDialog.assetDict.get( "inner_undertab");
            ASwingHelper.setBackground(this, _loc_1);
            return;
        }//end

        protected JPanel  makeBodyPanel ()
        {
            this.m_shelf = new PreyScrollingList(this.m_prey, PreyCellFactory, 0, BANDITS_PER_PAGE, 1);
            ASwingHelper.prepare(this.m_shelf);
            this.m_bodyPanel.append(this.m_shelf);
            return this.m_bodyPanel;
        }//end

        protected JPanel  makeFooterPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            ASwingHelper.setBackground(this.m_footerPanel, new HunterDialog.assetDict.get("police_innerBorder"));
            _loc_2 = this.m_shelf != null ? ((this.m_shelf as PreyScrollingList).getScrollWidth()) : (PreyCell.BANDIT_CELL_WIDTH * BANDITS_PER_PAGE);
            ASwingHelper.setForcedWidth(this.m_footerPanel, _loc_2);
            ASwingHelper.setForcedHeight(this.m_footerPanel, 65);
            _loc_1.append(this.m_footerPanel);
            return _loc_1;
        }//end

        public void  onUpdateBanditText (GenericObjectEvent event )
        {
            if (event.obj)
            {
                this.m_banditTextField.text = String(event.obj);
                this.m_banditTextField.width = this.m_footerPanel.getPreferredWidth() - 20;
                this.m_banditTextField.setTextFormat(this.m_banditTextFormat);
                ASwingHelper.prepare(this.m_footerPanel);
            }
            return;
        }//end

    }



