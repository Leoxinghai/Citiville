package Display.PopulationUI;

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
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class ResourceExplanationDialogView extends GenericDialogView
    {
        protected DisplayObject m_infoAsset ;
        protected String m_line1 ;
        protected String m_line2 ;
        protected String m_legend ;
        public static  int BOTTOM_MARGIN =20;
        public static  int BUTTON_PADDING =20;

        public  ResourceExplanationDialogView (Dictionary param1 ,String param2 ,String param3 ,String param4 ,String param5 ,Function param6 =null )
        {
            this.m_line1 = param3;
            this.m_line2 = param4;
            this.m_legend = param5;
            super(param1, "", param2, TYPE_OK, param6);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            return;
        }//end

         protected void  init ()
        {
            m_bgAsset =(DisplayObject) new m_assetDict.get("bg");
            this.m_infoAsset =(DisplayObject) new m_assetDict.get("info");
            this.makeBackground();
            this.makeCenterPanel();
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeBackground ()
        {
            if (m_bgAsset)
            {
                ASwingHelper.setSizedBackground(this, m_bgAsset, new Insets(0, 0, BUTTON_PADDING));
            }
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            this.setPreferredSize(new IntDimension(m_bgAsset.width, m_bgAsset.height + BUTTON_PADDING));
            this.setMaximumSize(new IntDimension(m_bgAsset.width, m_bgAsset.height + BUTTON_PADDING));
            this.setMinimumSize(new IntDimension(m_bgAsset.width, m_bgAsset.height + BUTTON_PADDING));
            this.appendAll(this.makeTopPanel(), this.makeBottomPanel(), this.makeAcceptButtonPanel());
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  makeTopPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_1.setPreferredSize(new IntDimension(m_bgAsset.width, 225));
            _loc_1.setMaximumSize(new IntDimension(m_bgAsset.width, 225));
            _loc_1.setMinimumSize(new IntDimension(m_bgAsset.width, 225));
            _loc_1.appendAll(this.makeInfoAndClosePanel());
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeInfoAndClosePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_1.appendAll(this.makeTitleAndClosePanel(), ASwingHelper.verticalStrut(15), this.makeSpacedAdvicePanel());
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeTitleAndClosePanel ()
        {
            JPanel _loc_1 =new JPanel(new BorderLayout ());
            _loc_2 = this.makeCloseButtonPanel ();
            _loc_3 = createTitlePanel();
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(ASwingHelper.horizontalStrut(_loc_2.getWidth()), BorderLayout.WEST);
            _loc_1.append(_loc_3, BorderLayout.CENTER);
            _loc_1.append(_loc_2, BorderLayout.EAST);
            _loc_4 = m_bgAsset.width;
            _loc_1.setPreferredWidth(_loc_4);
            _loc_1.setMaximumWidth(_loc_4);
            _loc_1.setMinimumWidth(_loc_4);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeCloseButtonPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT );
            _loc_2 = ASwingHelper.makeMarketCloseButton ();
            _loc_2.addEventListener(MouseEvent.CLICK, onCancelX, false, 0, true);
            _loc_1.append(_loc_2);
            ASwingHelper.setEasyBorder(_loc_2, 0, 0, 9, 6);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeSpacedAdvicePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_1.appendAll(ASwingHelper.horizontalStrut(122), this.makeAdvicePanel());
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeAdvicePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_1.setPreferredSize(new IntDimension(290, 245));
            _loc_1.setMaximumSize(new IntDimension(290, 245));
            _loc_1.setMinimumSize(new IntDimension(290, 245));
            _loc_2 = ASwingHelper.makeMultilineText(this.m_line1 +"<br/><br/>"+this.m_line2 ,285,EmbeddedArt.defaultFontName ,TextFormatAlign.LEFT ,18,4210752,null ,true );
            ASwingHelper.prepare(_loc_2);
            _loc_1.appendAll(_loc_2);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeBottomPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_1.setPreferredSize(new IntDimension(m_bgAsset.width, 95));
            _loc_1.setMaximumSize(new IntDimension(m_bgAsset.width, 95));
            _loc_1.setMinimumSize(new IntDimension(m_bgAsset.width, 95));
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_2.appendAll(ASwingHelper.horizontalStrut(BOTTOM_MARGIN), this.makeInfoText());
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(_loc_2);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  makeInfoText ()
        {
            _loc_1 = m_bgAsset.width;
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_2.setPreferredSize(new IntDimension(_loc_1, 115));
            _loc_2.setMaximumSize(new IntDimension(_loc_1, 115));
            _loc_2.setMinimumSize(new IntDimension(_loc_1, 115));
            _loc_3 = ASwingHelper.makeMultilineText(this.m_legend ,_loc_1 ,EmbeddedArt.defaultFontName ,TextFormatAlign.CENTER ,14,4210752,null ,true );
            ASwingHelper.prepare(_loc_3);
            AssetPane _loc_4 =new AssetPane(this.m_infoAsset );
            _loc_4.setHorizontalAlignment(AsWingConstants.CENTER);
            ASwingHelper.prepare(_loc_4);
            _loc_2.appendAll(_loc_4, _loc_3);
            return _loc_2;
        }//end

        protected JPanel  makeAcceptButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(new FlowLayout(AsWingConstants.CENTER ));
            CustomButton _loc_2 =new CustomButton(ZLoc.t("Dialogs","Accept"),null ,"GreenButtonUI");
            _loc_2.addActionListener(onAccept, 0, true);
            _loc_1.append(_loc_2);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

    }



