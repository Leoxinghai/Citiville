package Display.DialogUI;

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
import Classes.util.*;
import Display.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class DailyDripDialogView extends GenericDialogView
    {
        protected int m_itemCheck =0;
        protected JPanel m_textAreaInnerPane ;
        protected Component m_textAP ;
        protected JPanel m_overlapPane ;
        protected JPanel m_bottomPane ;
        protected String m_subtitleString ="";
        protected int m_checkWidth =0;
        protected Array m_bgSel ;
        protected Array m_bg ;
        protected DisplayObject m_cellBG =null ;
        protected Array m_ctrCnt ;
        protected Array m_check ;
        protected int m_hover =-1;
        public static  int DIALOG_TEXT_WIDTH =350;
        public static  int CHECK_PANE_SIZE =40;
        public static  int OVERLAP_AMOUNT =10;

        public  DailyDripDialogView (Dictionary param1 ,String param2 ="",String param3 ="",String param4 ="",Function param5 =null ,Function param6 =null )
        {
            this.m_bgSel = new Array();
            this.m_bg = new Array();
            this.m_ctrCnt = new Array();
            this.m_check = new Array();
            this.m_subtitleString = param4;
            this.m_itemCheck = Math.random() * 3;
            if (this.m_itemCheck > 2)
            {
                this.m_itemCheck = 2;
            }
            super(param1, param2, param3, TYPE_OK, param5, "", 0, "", param6);
            return;
        }//end

         protected JPanel  createTitlePanel ()
        {
            JPanel _loc_1 =null ;
            _loc_1 = super.createTitlePanel();
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            m_titleFontSize = TextFieldUtil.getLocaleFontSize(30, 20, [{locale:"ja", size:30}]);
            _loc_3 = ASwingHelper.makeMultilineText(this.m_subtitleString,DIALOG_TEXT_WIDTH,EmbeddedArt.defaultFontNameBold,TextFormatAlign.CENTER,m_titleFontSize,EmbeddedArt.blueTextColor);
            _loc_2.append(_loc_3);
            ASwingHelper.setEasyBorder(_loc_2, 0, 10, 0, 10);
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_4.appendAll(_loc_1, _loc_2);
            return _loc_4;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_1 =new JPanel(new SoftBoxLayout(SoftBoxLayout.Y_AXIS ,-10,AsWingConstants.CENTER ));
            _loc_2 = createHeaderPanel();
            ASwingHelper.prepare(_loc_2);
            textArea = this.createTextArea();
            ASwingHelper.prepare(textArea);
            _loc_1.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            _loc_1.append(_loc_2);
            _loc_1.append(ASwingHelper.verticalStrut(10));
            _loc_1.append(textArea);
            _loc_1.append(ASwingHelper.verticalStrut(2 * BUTTON_MARGIN));
            _loc_3 = createButtonPanel();
            _loc_1.append(_loc_3);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

         protected JPanel  createTextArea ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,10);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER,10);
            ASwingHelper.setEasyBorder(_loc_2, 10);
            _loc_3 = this.createCenterArea();
            _loc_2.append(_loc_3);
            this.m_textAP = this.createText(m_message, DIALOG_TEXT_WIDTH, EmbeddedArt.blueTextColor);
            this.m_textAreaInnerPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_textAreaInnerPane.append(this.m_textAP);
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_4.append(this.m_textAreaInnerPane);
            ASwingHelper.prepare(_loc_4);
            _loc_2.append(_loc_4);
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        protected JPanel  createText (String param1 ,double param2 ,int param3 )
        {
            _loc_4 = ASwingHelper.makeMultilineText(param1 ,param2 ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,TextFieldUtil.getLocaleFontSize(18,18,.get( {locale size "ja",16) }),param3 );
            _loc_5 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_5.append(_loc_4);
            return _loc_5;
        }//end

        protected JPanel  createCenterArea ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT ,10);
            int _loc_2 =20;
            _loc_1.append(ASwingHelper.horizontalStrut(_loc_2 + OVERLAP_AMOUNT / 2));
            int _loc_3 =0;
            while (_loc_3 < 3)
            {

                this.m_overlapPane = this.createCell(_loc_3);
                _loc_1.append(this.m_overlapPane);
                _loc_3++;
            }
            _loc_1.append(ASwingHelper.horizontalStrut(_loc_2));
            return _loc_1;
        }//end

        protected void  handleMouseOver (MouseEvent event ,int param2 )
        {
            if (param2 >= 0 && param2 < this.m_check.length())
            {
                if (param2 != this.m_itemCheck && param2 != this.m_hover)
                {
                    this.m_hover = param2;
                    this.m_ctrCnt.get(this.m_hover).setBackgroundDecorator(this.m_bgSel.get(this.m_hover));
                }
            }
            return;
        }//end

        protected void  handleMouseOut (MouseEvent event ,int param2 )
        {
            if (this.m_hover >= 0 && this.m_hover < this.m_check.length && this.m_hover != this.m_itemCheck)
            {
                this.m_ctrCnt.get(this.m_hover).setBackgroundDecorator(this.m_bg.get(this.m_hover));
            }
            this.m_hover = -1;
            return;
        }//end

        protected void  handleMouseDown (MouseEvent event ,int param2 )
        {
            if (param2 == this.m_itemCheck)
            {
                return;
            }
            if (this.m_itemCheck >= 0 && this.m_itemCheck < this.m_check.length())
            {
                this.m_check.get(this.m_itemCheck).visible = false;
                this.m_ctrCnt.get(this.m_itemCheck).setBackgroundDecorator(this.m_bg.get(this.m_itemCheck));
            }
            if (param2 >= 0 && param2 < this.m_check.length())
            {
                this.m_itemCheck = param2;
                this.m_check.get(this.m_itemCheck).visible = true;
                this.m_ctrCnt.get(this.m_itemCheck).setBackgroundDecorator(this.m_bgSel.get(this.m_itemCheck));
            }
            return;
        }//end

        protected JPanel  createCell (int param1 )
        {
            ndx = param1;
            checkPane = this.makeCheckPane(ndx);
            centeringContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            DisplayObject cellBG1 =(DisplayObject)new m_assetDict.get( "cell_bg");
            this.m_bg.put(ndx,  new MarginBackground(cellBG1));
            this.m_cellBG =(DisplayObject) new m_assetDict.get("cell_bg_ready");
            this.m_bgSel.put(ndx,  new MarginBackground(this.m_cellBG));
            bgDec = this.m_bg.get(ndx);
            centeringContainer.setBackgroundDecorator(bgDec);
            centeringContainer.setPreferredSize(new IntDimension(this.m_cellBG.width, this.m_cellBG.height));
            centeringContainer.setMinimumSize(new IntDimension(this.m_cellBG.width, this.m_cellBG.height));
            centeringContainer.setMaximumSize(new IntDimension(this.m_cellBG.width, this.m_cellBG.height));
            this.m_ctrCnt.put(ndx,  centeringContainer);
            int overlap ;
            if (this.m_checkWidth)
            {
                overlap = OVERLAP_AMOUNT - this.m_checkWidth;
            }
            overlapPane = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.BOTTOM,overlap);
            itemIconPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            DisplayObject itemIcon =(DisplayObject)new m_assetDict.get( "itemIcon").get(ndx);
            iconAlignPane = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            AssetPane iconPane =new AssetPane(itemIcon );
            widthToCenter = this(.m_cellBG.width-itemIcon.width)/2;
            iconAlignPane.append(ASwingHelper.horizontalStrut(widthToCenter));
            iconAlignPane.append(iconPane);
            itemIconPane.append(iconAlignPane);
            centeringContainer.append(itemIconPane);
            overlapPane.append(centeringContainer);
            this.m_bottomPane = new JPanel(new BorderLayout());
            this.m_bottomPane.append(checkPane, BorderLayout.SOUTH);
            overlapPane.append(this.m_bottomPane);
            text = this.createText(m_assetDict.get("itemLabel").get(ndx),this.m_cellBG.width,EmbeddedArt.brownTextColor);
            overlapPane .addEventListener (MouseEvent .MOUSE_OVER ,void  (MouseEvent event )
            {
                handleMouseOver(event, ndx);
                return;
            }//end
            , false, 0, false);
            overlapPane .addEventListener (MouseEvent .MOUSE_DOWN ,void  (MouseEvent event )
            {
                handleMouseDown(event, ndx);
                return;
            }//end
            , false, 0, false);
            overlapPane .addEventListener (MouseEvent .MOUSE_OUT ,void  (MouseEvent event )
            {
                handleMouseOut(event, ndx);
                return;
            }//end
            , false, 0, false);
            overlapPane.buttonMode = true;
            container = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            container.append(overlapPane);
            text.setMinimumHeight(40);
            text.setPreferredHeight(40);
            container.append(text);
            ASwingHelper.prepare(container);
            if (ndx == this.m_itemCheck)
            {
                this.m_check.get(ndx).visible = true;
                this.m_ctrCnt.get(ndx).setBackgroundDecorator(this.m_bgSel.get(ndx));
            }
            return container;
        }//end

        protected JPanel  makeCheckPane (int param1 )
        {
            DisplayObject _loc_4 =null ;
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
            int _loc_5 =16777215;
            _loc_6 = (Class)assetDict.get("check_mark");
            _loc_4 = new ((Class)(DisplayObject)assetDict.get("check_mark")();
            this.m_check.put(param1,  _loc_4);
            this.m_checkWidth = _loc_4.width;
            _loc_2.setBackgroundDecorator(new AssetBackground(_loc_4));
            _loc_2.setPreferredHeight(_loc_4.height);
            _loc_2.setMinimumHeight(_loc_4.height);
            _loc_2.setMaximumHeight(_loc_4.height);
            _loc_2.setPreferredWidth(_loc_4.width);
            _loc_2.setMinimumWidth(_loc_4.width);
            _loc_2.setMaximumWidth(_loc_4.width);
            _loc_2.append(_loc_3);
            _loc_4.visible = false;
            ASwingHelper.prepare(_loc_2);
            return _loc_2;
        }//end

         protected void  onAccept (AWEvent event )
        {
            _loc_2 = assetDict.get("rewardType").get(this.m_itemCheck) ;
            StatsManager.sample(100, StatsCounterType.DAILY_BONUS, "email", "email_reward_signup", _loc_2);
            GameTransactionManager.addTransaction(new TQueueDailyDrip(_loc_2), true);
            if (m_closeCallback != null)
            {
                removeEventListener(GenericPopupEvent.SELECTED, m_closeCallback);
            }
            closeMe();
            _loc_3 = ZLoc.t("Dialogs","DailyDripConfirmation");
            GenericDialog _loc_4 =new GenericDialog(_loc_3 ,"",TYPE_OK );
            UI.displayPopup(_loc_4, true, "DripConfirm", true);
            return;
        }//end

         protected void  onCancel (Object param1)
        {
            super.onCancel(param1);
            StatsManager.sample(100, StatsCounterType.DAILY_BONUS, "email", "email_reward_decline");
            GameTransactionManager.addTransaction(new TQueueDailyDrip("decline"), false);
            return;
        }//end

    }




