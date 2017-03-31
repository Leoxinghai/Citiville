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
import Classes.sim.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Modules.stats.types.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class AdmirersPanel extends JPanel
    {
        private JPanel m_headerPanel ;
        private JPanel m_portraitsPanel ;
        private JPanel m_portraitsShelf ;
        private JPanel m_footerPanel ;
        private Dictionary m_assetDict ;
        private Array m_admirers ;
        public static  double ADMIRERS_PER_PAGE =15;

        public  AdmirersPanel ()
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            this.m_admirers = ValentineManager.getAdmirers();
            while (this.m_admirers.length % ADMIRERS_PER_PAGE != 0)
            {

                this.m_admirers.push(new Admirer(null, null, 0, false));
            }
            this.init();
            return;
        }//end

        protected void  init ()
        {
            ASwingHelper.setEasyBorder(this, 0, 20);
            this.m_headerPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_portraitsPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_footerPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.append(ASwingHelper.verticalStrut(8));
            this.append(this.makeHeaderPanel());
            this.append(this.makePortraitsPanel());
            this.append(this.makeFooterPanel());
            ASwingHelper.prepare(this);
            dispatchEvent(new Event(MakerPanel.PREPARE, true));
            return;
        }//end

        protected JPanel  makeHeaderPanel ()
        {
            _loc_1 = ZLoc.t("Dialogs","ValUI_admirer_header");
            _loc_2 = ASwingHelper.makeMultilineText(_loc_1 ,700,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,16,EmbeddedArt.brownTextColor );
            this.m_headerPanel.append(_loc_2);
            return this.m_headerPanel;
        }//end

        protected JPanel  makePortraitsPanel ()
        {
            this.m_portraitsShelf = new AdmirersScrollingList(this.m_admirers, AdmirersCellFactory, 0, 5, 3, ADMIRERS_PER_PAGE);
            ASwingHelper.prepare(this.m_portraitsShelf);
            this.m_portraitsPanel.append(this.m_portraitsShelf);
            return this.m_portraitsPanel;
        }//end

        protected JPanel  makeFooterPanel ()
        {
            _loc_1 = ZLoc.t("Dialogs","ValUI_admirer_footer",{numAdmirers ValentineManager.getTotalAdmirers (),numCards.getTotalCards ()});
            _loc_2 = ASwingHelper.makeMultilineText(_loc_1 ,700,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,16,EmbeddedArt.blueTextColor );
            this.m_footerPanel.append(_loc_2);
            _loc_1 = ZLoc.t("Dialogs", "Share");
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            CustomButton _loc_5 =new CustomButton(_loc_1 ,null ,"PinkButtonUI");
            _loc_5.setFont(ASwingHelper.makeFont(EmbeddedArt.titleFont, 18));
            _loc_5.setPreferredSize(new IntDimension(190, 30));
            _loc_5.setMinimumSize(new IntDimension(190, 30));
            _loc_5.setMaximumSize(new IntDimension(190, 30));
            _loc_4.appendAll(ASwingHelper.horizontalStrut(5), _loc_5, ASwingHelper.horizontalStrut(5));
            _loc_3.appendAll(ASwingHelper.verticalStrut(5), _loc_4, ASwingHelper.verticalStrut(5));
            _loc_5.addEventListener(MouseEvent.CLICK, this.onButtonClick, false, 0, true);
            this.m_footerPanel.append(_loc_3);
            this.m_footerPanel.append(ASwingHelper.verticalStrut(20));
            return this.m_footerPanel;
        }//end

        protected void  onButtonClick (MouseEvent event )
        {
            if (!Global.world.viralMgr.vdayBragAboutAdmirers())
            {
                UI.displayMessage(ZLoc.t("Dialogs", "ValUI_throttle"), GenericDialogView.TYPE_OK);
            }
            StatsManager.count(StatsCounterType.CARDMAKER, StatsKingdomType.VDAY_2011, StatsPhylumType.ADMIRERS, "share");
            return;
        }//end

    }



