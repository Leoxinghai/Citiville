package Modules.landmarks;

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
import Modules.stats.types.*;

//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class LandmarksTechTreeDialogView extends GenericDialogView
    {
        protected Vector<WonderDatum> m_wondersData;
        protected String m_chosenWonderName ;
        protected JPanel m_hintPanel ;
        protected JPanel m_scrollingPanel ;
        protected LandmarksTechTreeScrollingList m_scrollingList ;

        public  LandmarksTechTreeDialogView (Dictionary param1 ,String param2 ,Vector param3 .<WonderDatum >)
        {
            this.m_chosenWonderName = param2;
            StatsManager.sample(100, StatsKingdomType.DIALOG_STATS, "tech_tree", "view", this.m_chosenWonderName);
            this.m_wondersData = param3;
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            super(param1);
            return;
        }//end

         protected void  init ()
        {
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            this.makeBackground();
            this.makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            this.appendAll(createHeaderPanel(), ASwingHelper.verticalStrut(8));
            this.appendAll(this.createScrollPanel(), ASwingHelper.verticalStrut(5));
            return;
        }//end

         protected JPanel  createTitlePanel ()
        {
            JPanel _loc_1 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            m_titleFontSize = TextFieldUtil.getLocaleFontSize(27, 20, [{locale:"ja", size:27}]);
            _loc_2 = ZLoc.t("Items",this.m_chosenWonderName +"_friendlyName");
            _loc_2 = TextFieldUtil.formatSmallCapsString(_loc_2);
            title = ASwingHelper.makeTextField(_loc_2 + " ", EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.titleColor, 3);
            title.filters = EmbeddedArt.newtitleFilters;
            TextFormat _loc_3 =new TextFormat ();
            _loc_3.size = this.getTitleTextSizeHeader(_loc_2.length());
            TextFieldUtil.formatSmallCaps(title.getTextField(), _loc_3);
            _loc_1.append(title);
            title.getTextField().height = m_titleFontSize * 1.5;
            ASwingHelper.setEasyBorder(_loc_1, 4);
            return _loc_1;
        }//end

        protected JPanel  createScrollPanel ()
        {
            this.m_scrollingPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_scrollingList = new LandmarksTechTreeScrollingList(assetDict, this.m_wondersData, this);
            this.m_scrollingPanel.append(this.m_scrollingList);
            return this.m_scrollingPanel;
        }//end

        public int  getTitleTextSizeHeader (int param1 )
        {
            if (param1 > 30)
            {
                return m_titleFontSize * 0.89;
            }
            return m_titleFontSize;
        }//end

         protected void  makeBackground ()
        {
            m_bgAsset = assetDict.get("wonders_quests_background_full");
            MarginBackground _loc_1 =new MarginBackground(m_bgAsset );
            this.setBackgroundDecorator(_loc_1);
            ASwingHelper.setForcedSize(this, new IntDimension(m_bgAsset.width, m_bgAsset.height));
            Sprite _loc_2 =new Sprite ();
            _loc_3 = assetDict.get("wonders_bg_white") ;
            _loc_2.addChild(_loc_3);
            this.addChild(_loc_2);
            _loc_3.width = 719;
            _loc_3.height = 470;
            _loc_2.x = (m_bgAsset.width - _loc_3.width) / 2;
            _loc_2.y = _loc_2.y + 48;
            return;
        }//end

         protected JPanel  createCloseButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(ASwingHelper.softBoxLayoutVertical );
            _loc_2 = ASwingHelper.makeMarketCloseButton ();
            _loc_2.addEventListener(MouseEvent.CLICK, this.onCancelX, false, 0, true);
            _loc_1.append(_loc_2);
            ASwingHelper.setEasyBorder(_loc_2, 5, 0, 0, 5);
            return _loc_1;
        }//end

         protected void  onCancelX (Object param1)
        {
            StatsManager.sample(100, StatsKingdomType.DIALOG_STATS, "tech_tree", "x", this.m_chosenWonderName);
            super.onCancelX(param1);
            return;
        }//end

    }



