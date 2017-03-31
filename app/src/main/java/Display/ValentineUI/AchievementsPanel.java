package Display.ValentineUI;

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
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Modules.achievements.data.*;
import Modules.stats.types.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class AchievementsPanel extends JPanel
    {
        protected Array m_achieves ;
        protected JPanel m_headerPanel ;
        protected JPanel m_bodyPanel ;
        protected JPanel m_footerPanel ;
        protected JPanel m_achievementsShelf ;
        public static  int ACHIEVEMENTS_PER_PAGE =4;

        public  AchievementsPanel ()
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            this.init();
            return;
        }//end

        protected void  init ()
        {
            this.m_achieves = Global.achievementsManager.getAchievementGroup("vday_2011").achievements;
            while (this.m_achieves.length % ACHIEVEMENTS_PER_PAGE != 0)
            {

                this.m_achieves.push(new Achievement(null, null));
            }
            ASwingHelper.setEasyBorder(this, 0, 20);
            this.m_headerPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_bodyPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_footerPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.append(ASwingHelper.verticalStrut(8));
            this.append(this.makeHeaderPanel());
            this.append(ASwingHelper.verticalStrut(5));
            this.append(this.makeBodyPanel());
            this.append(ASwingHelper.verticalStrut(5));
            this.append(this.makeFooterPanel());
            this.append(ASwingHelper.verticalStrut(15));
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  makeHeaderPanel ()
        {
            _loc_1 = ZLoc.t("Dialogs","ValUI_achievements_header")+"\n"+ZLoc.t("Dialogs","ValUI_achievements_header2");
            _loc_2 = ASwingHelper.makeMultilineText(_loc_1 ,700,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,16,EmbeddedArt.brownTextColor );
            this.m_headerPanel.append(_loc_2);
            return this.m_headerPanel;
        }//end

        protected JPanel  makeBodyPanel ()
        {
            this.m_achievementsShelf = new AchievementsScrollingList(this.m_achieves, AchievementsCellFactory, 0, 4, 1);
            ASwingHelper.prepare(this.m_achievementsShelf);
            this.m_bodyPanel.append(this.m_achievementsShelf);
            return this.m_bodyPanel;
        }//end

        protected JPanel  makeFooterPanel ()
        {
            CustomButton _loc_1 =new CustomButton(ZLoc.t("Dialogs","ValUI_askforhelp"),null ,"PinkButtonUI");
            _loc_1.setPreferredSize(new IntDimension(200, 35));
            _loc_1.setMinimumSize(new IntDimension(200, 35));
            _loc_1.setMaximumSize(new IntDimension(200, 35));
            _loc_1.addEventListener(MouseEvent.CLICK, this.onMouseClick, false, 0, true);
            this.m_footerPanel.append(_loc_1);
            return this.m_footerPanel;
        }//end

        protected void  onMouseClick (Event event )
        {
            if (!Global.world.viralMgr.vdayBragAboutAchievements())
            {
                UI.displayMessage(ZLoc.t("Dialogs", "ValUI_throttle"), GenericDialogView.TYPE_OK);
            }
            StatsManager.count(StatsCounterType.CARDMAKER, StatsKingdomType.VDAY_2011, StatsPhylumType.PRIZES, "share");
            return;
        }//end

    }



