package Display.TrainUI;

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
import Classes.inventory.*;
import Classes.orders.*;
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Display.FactoryUI.*;
import Display.aswingui.*;
import ZLocalization.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class TrainSlidePick extends SlidePick
    {
        protected JLabel m_info ;
        protected JLabel m_info2 ;
        protected JLabel m_amountLabel ;
        protected TrainManager m_trainSim ;
        protected TrainOrder m_order ;
        protected int m_uiHandle ;
        protected int m_amountToAccept ;
        public static  int SLIDE_WIDTH =350;
        public static  int BUTTON_HEIGHT =25;
        public static  int INFO_PANE_HEIGHT =30;
public static Object m_assets =null ;

        public  TrainSlidePick (String param1 ,int param2 ,TrainManager param3 ,TrainOrder param4 ,boolean param5 =false ,boolean param6 =true ,int param7 =0,boolean param8 =false ,int param9 =-1)
        {
            this.m_order = param4;
            this.m_trainSim = param3;
            this.m_uiHandle = param2;
            this.m_info = null;
            this.m_amountLabel = null;
            this.m_amountToAccept = 0;
            m_state = STATE_CLOSED;
            super(param1, param5, param6, param7, false, SLIDE_WIDTH);
            return;
        }//end

        public Sprite  getPickSprite ()
        {
            return this.m_mainSprite;
        }//end

         protected Class  getPickBackground ()
        {
            return EmbeddedArt.trainUIPick;
        }//end

         protected Class  getInnerBackground ()
        {
            int _loc_1 =0;
            if (this.m_order.getState() == OrderStates.ACCEPTED)
            {
                _loc_1 = this.m_order.getAmountProposed() - this.m_order.getAmountFinal();
                if (_loc_1 > 0)
                {
                    return EmbeddedArt.trainUIBG2;
                }
            }
            return EmbeddedArt.trainUIBG;
        }//end

         protected void  initInner ()
        {
            super.initInner();
            switch(m_state)
            {
                case STATE_INFO:
                {
                    this.initAccept();
                    break;
                }
                case STATE_ACCEPT_DECLINE:
                {
                    this.initToaster();
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        protected void  initAccept ()
        {
            AssetPane _loc_8 =null ;
            CustomButton _loc_9 =null ;
            CustomButton _loc_10 =null ;
            int _loc_11 =0;
            JPanel _loc_12 =null ;
            AssetPane _loc_13 =null ;
            JLabel _loc_14 =null ;
            JLabel _loc_15 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(AsWingConstants.CENTER );
            _loc_1.setPreferredWidth(slideWidth);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.CENTER );
            _loc_2.setPreferredHeight(INFO_PANE_HEIGHT);
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.CENTER ,15);
            this.m_info = new JLabel(this.getInfoText());
            this.m_info.setFont(ASwingHelper.getBoldFont(14));
            _loc_2.append(this.m_info);
            if (this.m_order.getCommodityType() == Commodities.GOODS_COMMODITY)
            {
                _loc_8 = new AssetPane(new m_assets.crate_small());
                _loc_2.append(_loc_8);
            }
            int _loc_4 =0;
            CustomButton _loc_5 =new CustomButton(this.getAcceptButtonText (),null ,"GreenSmallButtonUI");
            _loc_5.setPreferredHeight(BUTTON_HEIGHT);
            _loc_5.addActionListener(this.onAccept, 0, true);
            if (this.m_order.getState() == OrderStates.PENDING)
            {
                _loc_4 = this.m_order.getCoinCostProposed();
                _loc_3.append(_loc_5);
                _loc_9 = new CustomButton(ZLoc.t("Dialogs", "TrainUI_Later"), null, "OrangeSmallButtonUI");
                _loc_9.setPreferredHeight(BUTTON_HEIGHT);
                _loc_9.addActionListener(this.onLater, 0, true);
                _loc_3.append(_loc_9);
                _loc_10 = new CustomButton(ZLoc.t("Dialogs", "TrainUI_Decline"), null, "RedSmallButtonUI");
                _loc_10.setPreferredHeight(BUTTON_HEIGHT);
                _loc_10.addActionListener(this.onDecline, 0, true);
                _loc_3.append(_loc_10);
            }
            else
            {
                _loc_4 = this.m_order.getCoinCostFinal();
                _loc_11 = this.m_order.getAmountProposed() - this.m_order.getAmountFinal();
                if (_loc_11 > 0)
                {
                    _loc_12 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.CENTER);
                    _loc_12.setPreferredWidth(slideWidth / 2);
                    _loc_12.setPreferredHeight(INFO_PANE_HEIGHT);
                    _loc_13 = null;
                    if (this.m_order.getAction() == TrainOrder.BUY)
                    {
                        _loc_11 = this.m_order.getCoinCostProposed() - this.m_order.getCoinCostFinal();
                        _loc_13 = new AssetPane(new m_assets.coin_small());
                    }
                    else if (this.m_order.getCommodityType() == Commodities.GOODS_COMMODITY)
                    {
                        _loc_13 = new AssetPane(new m_assets.crate_small());
                    }
                    else
                    {
                        _loc_13 = new AssetPane(new m_assets.crate_small());
                    }
                    _loc_14 = new JLabel(ZLoc.t("Dialogs", "TrainUI_YouGet", {number:_loc_11}));
                    _loc_14.setFont(ASwingHelper.getBoldFont(14));
                    _loc_15 = new JLabel(ZLoc.t("Dialogs", "TrainUI_Back"));
                    _loc_15.setFont(ASwingHelper.getBoldFont(14));
                    _loc_12.appendAll(_loc_14, _loc_13, _loc_15);
                    ASwingHelper.prepare(_loc_12);
                    _loc_3.appendAll(_loc_12, _loc_5);
                }
                else
                {
                    _loc_3.append(_loc_5);
                }
            }
            JLabel _loc_6 =new JLabel(ZLoc.t("Dialogs","TrainUI_ForNum",{number _loc_4 }));
            _loc_6.setFont(ASwingHelper.getBoldFont(14));
            _loc_2.append(_loc_6);
            AssetPane _loc_7 =new AssetPane(new m_assets.coin_small ()as DisplayObject );
            _loc_2.append(_loc_7);
            ASwingHelper.prepare(_loc_2);
            ASwingHelper.prepare(_loc_3);
            _loc_1.appendAll(_loc_2, _loc_3);
            ASwingHelper.prepare(_loc_1);
            innerAppendAll(_loc_1);
            return;
        }//end

        protected void  initToaster ()
        {
            CustomButton _loc_6 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(AsWingConstants.CENTER );
            _loc_1.setPreferredWidth(slideWidth);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.CENTER );
            _loc_2.setPreferredHeight(INFO_PANE_HEIGHT);
            JPanel _loc_3 =new JPanel(new BorderLayout ());
            this.m_info = new JLabel(this.getInfoText());
            this.m_info.setFont(ASwingHelper.getBoldFont(14));
            this.m_info2 = new JLabel(this.getInfoText2());
            this.m_info2.setFont(ASwingHelper.getBoldFont(14));
            AssetPane _loc_4 =null ;
            if (this.m_order.getCommodityType() == Commodities.GOODS_COMMODITY)
            {
                _loc_4 = new AssetPane(new m_assets.crate_small());
            }
            else
            {
                _loc_4 = new AssetPane(new m_assets.crate_small());
            }
            AssetPane _loc_5 =new AssetPane(new m_assets.coin_small ()as DisplayObject );
            _loc_2.appendAll(this.m_info, _loc_4, this.m_info2, _loc_5);
            _loc_7 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_7.append(ASwingHelper.horizontalStrut(15));
            CustomButton _loc_8 =new CustomButton("",null ,"MinusButtonUI");
            _loc_8.setPreferredSize(new IntDimension(BUTTON_HEIGHT, BUTTON_HEIGHT));
            _loc_8.addActionListener(this.onLess, 0, true);
            _loc_7.append(_loc_8);
            _loc_9 = new EmbeddedArt.trainUIAmntField ();
            this.m_amountLabel = new JLabel("" + this.m_amountToAccept);
            this.m_amountLabel.setFont(ASwingHelper.getBoldFont(16));
            this.m_amountLabel.setForeground(new ASColor(16777215));
            this.m_amountLabel.setTextFilters(.get(new DropShadowFilter(1.5, 90, 13204516, 1, 0, 0)));
            this.m_amountLabel.setBackgroundDecorator(new AssetBackground(_loc_9));
            this.m_amountLabel.setPreferredSize(new IntDimension(_loc_9.width, _loc_9.height));
            _loc_7.append(this.m_amountLabel);
            CustomButton _loc_10 =new CustomButton("",null ,"PlusButtonUI");
            _loc_10.setPreferredSize(new IntDimension(BUTTON_HEIGHT, BUTTON_HEIGHT));
            _loc_10.addActionListener(this.onMore, 0, true);
            _loc_7.append(_loc_10);
            _loc_3.append(_loc_7, BorderLayout.WEST);
            _loc_11 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_6 = new CustomButton(this.getAcceptButtonText(), null, "GreenSmallButtonUI");
            _loc_6.setPreferredHeight(BUTTON_HEIGHT);
            _loc_6.addActionListener(this.onAccept, 0, true);
            _loc_11.append(_loc_6);
            _loc_11.append(ASwingHelper.horizontalStrut(15));
            _loc_3.append(_loc_11, BorderLayout.EAST);
            ASwingHelper.prepare(_loc_2);
            ASwingHelper.prepare(_loc_3);
            _loc_1.appendAll(_loc_2, _loc_3);
            ASwingHelper.prepare(_loc_1);
            innerAppendAll(_loc_1);
            return;
        }//end

        protected void  makeAssets (DisplayObject param1 ,String param2 )
        {
            m_assets = param1;
            this.toggleInnerPane();
            return;
        }//end

        public void  showTrainUI (TrainOrder param1 )
        {
            AWEvent _loc_2 =null ;
            double _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            this.m_order = param1;
            if (this.m_order.autoComplete)
            {
                _loc_2 = new AWEvent("");
                this.onAccept(_loc_2);
                return;
            }
            if (this.m_order.getState() == OrderStates.PENDING)
            {
                this.m_amountToAccept = param1.getAmountProposed();
                if (this.m_order.getAction() == TrainOrder.SELL)
                {
                    _loc_3 = TrainOrder.getCostPerCommodity(param1.getCommodityType(), param1.getTrainItemName(), param1.getAction());
                    _loc_4 = Math.floor(Global.player.gold / _loc_3);
                    if (_loc_4 < this.m_amountToAccept)
                    {
                        this.m_amountToAccept = _loc_4;
                    }
                }
                else if (this.m_order.getAction() == TrainOrder.BUY)
                {
                    _loc_5 = Global.player.commodities.getCount(this.m_order.getCommodityType());
                    if (this.m_amountToAccept > _loc_5)
                    {
                        this.m_amountToAccept = _loc_5;
                    }
                }
            }
            m_state = STATE_INFO;
            if (m_assets == null)
            {
                Global.delayedAssets.get(DelayedAssetLoader.TRAIN_ASSETS, this.makeAssets);
            }
            else
            {
                this.toggleInnerPane();
            }
            return;
        }//end

        protected LocalizationName  fn ()
        {
            String _loc_1 =null ;
            _loc_2 = this.m_order.getTransmissionStatus ()==OrderStatus.SENT ? (this.m_order.getRecipientID()) : (this.m_order.getSenderID());
            _loc_1 = Global.player.getFriendFirstName(_loc_2);
            if (_loc_1 == null)
            {
                _loc_1 = "???";
            }
            return ZLoc.tn(_loc_1);
        }//end

        protected String  getInfoText2 ()
        {
            int _loc_1 =0;
            if (m_state == STATE_ACCEPT_DECLINE)
            {
                _loc_1 = Math.floor(TrainOrder.getCostPerCommodity(this.m_order.getCommodityType(), this.m_order.getTrainItemName(), this.m_order.getAction()) * this.m_amountToAccept);
                return ZLoc.t("Dialogs", "TrainUI_ForNum", {number:_loc_1});
            }
            return "";
        }//end

        protected String  getInfoText ()
        {
            switch(m_state)
            {
                case STATE_INFO:
                {
                    if (this.m_order && this.m_order.getState() == OrderStates.ACCEPTED)
                    {
                        if (this.m_order && this.m_order.getAction() == TrainOrder.SELL)
                        {
                            return ZLoc.t("Dialogs", "TrainUI_AcceptedSell_Info", {name:this.fn(), number:this.m_order.getAmountFinal()});
                        }
                        if (this.m_order && this.m_order.getAction() == TrainOrder.BUY)
                        {
                            return ZLoc.t("Dialogs", "TrainUI_AcceptedBuy_Info", {name:this.fn(), number:this.m_order.getAmountFinal()});
                        }
                    }
                    else if (this.m_order && this.m_order.getState() == OrderStates.DENIED)
                    {
                        if (this.m_order && this.m_order.getAction() == TrainOrder.SELL)
                        {
                            return ZLoc.t("Dialogs", "TrainUI_DeniedSell_Info", {name:this.fn(), number:this.m_order.getAmountProposed()});
                        }
                        if (this.m_order && this.m_order.getAction() == TrainOrder.BUY)
                        {
                            return ZLoc.t("Dialogs", "TrainUI_DeniedBuy_Info", {name:this.fn(), number:this.m_order.getAmountProposed()});
                        }
                    }
                    else if (this.m_order && this.m_order.getState() == OrderStates.PENDING)
                    {
                        if (this.m_order && this.m_order.getAction() == TrainOrder.SELL)
                        {
                            return ZLoc.t("Dialogs", "TrainUI_AcceptBuy_Info", {name:this.fn(), number:this.m_order.getAmountProposed()});
                        }
                        if (this.m_order && this.m_order.getAction() == TrainOrder.BUY)
                        {
                            return ZLoc.t("Dialogs", "TrainUI_AcceptSell_Info", {name:this.fn(), number:this.m_order.getAmountProposed()});
                        }
                    }
                    break;
                }
                case STATE_ACCEPT_DECLINE:
                {
                    if (this.m_order && this.m_order.getAction() == TrainOrder.SELL)
                    {
                        return ZLoc.t("Dialogs", "TrainUI_ToasterBuy_Info", {number:this.m_amountToAccept});
                    }
                    if (this.m_order && this.m_order.getAction() == TrainOrder.BUY)
                    {
                        return ZLoc.t("Dialogs", "TrainUI_ToasterSell_Info", {number:this.m_amountToAccept});
                    }
                    break;
                }
                default:
                {
                    break;
                }
            }
            return "";
        }//end

        protected String  getAcceptButtonText ()
        {
            switch(m_state)
            {
                case STATE_INFO:
                {
                    if (this.m_order && this.m_order.getState() == OrderStates.ACCEPTED)
                    {
                        return ZLoc.t("Dialogs", "TrainUI_AcceptComplete_AcceptButton");
                    }
                }
                case STATE_ACCEPT_DECLINE:
                {
                    if (this.m_order && this.m_order.getAction() == TrainOrder.SELL)
                    {
                        return ZLoc.t("Dialogs", "TrainUI_ToasterBuy_AcceptButton");
                    }
                    if (this.m_order && this.m_order.getAction() == TrainOrder.BUY)
                    {
                        return ZLoc.t("Dialogs", "TrainUI_ToasterSell_AcceptButton");
                    }
                    break;
                }
                default:
                {
                    break;
                }
            }
            return "";
        }//end

        protected void  onAccept (Event event )
        {
            CustomButton _loc_2 =null ;
            if (event.currentTarget instanceof CustomButton)
            {
                _loc_2 =(CustomButton) event.currentTarget;
                _loc_2.removeActionListener(this.onAccept);
            }
            if (!this.m_trainSim || !this.m_order)
            {
                this.kill();
            }
            if (this.m_order.getState() != OrderStates.PENDING)
            {
                this.m_trainSim.accepted(this.m_order, this.m_order.getAmountFinal(), this.m_uiHandle);
                this.kill();
                return;
            }
            switch(m_state)
            {
                case STATE_ACCEPT_DECLINE:
                {
                    if (!this.checkAmount(this.m_amountToAccept))
                    {
                        break;
                    }
                    this.m_trainSim.accepted(this.m_order, this.m_amountToAccept, this.m_uiHandle);
                    this.kill();
                    break;
                }
                case STATE_INFO:
                {
                    if (!this.checkAmount(1))
                    {
                        break;
                    }
                    m_state = STATE_ACCEPT_DECLINE;
                    this.toggleInnerPane();
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        public void  toggleInnerPane ()
        {
            if (m_sliderActive)
            {
                TweenLite.to(m_innerSprite, 0.5, {x:m_offXPos, onComplete:buildAndShowInnerPane});
            }
            return;
        }//end

        public void  backOnePanel ()
        {
            if (m_state == STATE_ACCEPT_DECLINE)
            {
                m_state = STATE_INFO;
                this.toggleInnerPane();
            }
            return;
        }//end

        protected void  onLater (Event event )
        {
            CustomButton _loc_2 =null ;
            if (event.currentTarget instanceof CustomButton)
            {
                _loc_2 =(CustomButton) event.currentTarget;
                _loc_2.removeActionListener(this.onLater);
            }
            this.m_trainSim.later(this.m_order, this.m_uiHandle);
            this.kill();
            return;
        }//end

        protected void  onDecline (Event event )
        {
            CustomButton _loc_2 =null ;
            if (event.currentTarget instanceof CustomButton)
            {
                _loc_2 =(CustomButton) event.currentTarget;
                _loc_2.removeActionListener(this.onDecline);
            }
            this.m_trainSim.declined(this.m_order, this.m_uiHandle);
            this.kill();
            return;
        }//end

        public void  onMore (Event event )
        {
            if (this.m_order)
            {
                if (this.m_amountToAccept + Global.gameSettings().getInt("trainSliderAmount", 1) > this.m_order.getAmountProposed())
                {
                    return;
                }
                if (!this.checkAmount(this.m_amountToAccept + Global.gameSettings().getInt("trainSliderAmount", 1)))
                {
                    return;
                }
                this.m_amountToAccept = this.m_amountToAccept + Global.gameSettings().getInt("trainSliderAmount", 1);
                this.m_trainSim.updateAmountAccepted(this.m_order, this.m_amountToAccept, this.m_uiHandle);
                this.m_amountLabel.setText("" + this.m_amountToAccept);
                prepareWindow();
                this.m_info.setText(this.getInfoText());
                this.m_info2.setText(this.getInfoText2());
            }
            return;
        }//end

        public void  onLess (Event event )
        {
            if (this.m_amountToAccept >= Global.gameSettings().getInt("trainSliderAmount", 1))
            {
                this.m_amountToAccept = this.m_amountToAccept - Global.gameSettings().getInt("trainSliderAmount", 1);
                this.m_trainSim.updateAmountAccepted(this.m_order, this.m_amountToAccept, this.m_uiHandle);
                this.m_amountLabel.setText("" + this.m_amountToAccept);
                this.m_info.setText(this.getInfoText());
                this.m_info2.setText(this.getInfoText2());
                prepareWindow();
            }
            return;
        }//end

        protected boolean  checkAmount (int param1 )
        {
            int _loc_2 =0;
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            int _loc_5 =0;
            if (this.m_order.getAction() == TrainOrder.SELL)
            {
                _loc_2 = this.m_order.getCoinCostProposed() / this.m_order.getAmountProposed();
                _loc_3 = _loc_2 * param1 <= Global.player.gold;
                _loc_4 = Global.player.commodities.getSpareCapacity(this.m_order.getCommodityType()) >= param1;
                if (!_loc_3)
                {
                    UI.displayMessage(ZLoc.t("Dialogs", "TrainUI_NotEnoughMoney"));
                    return false;
                }
                if (!_loc_4)
                {
                    UI.displayMessage(ZLoc.t("Dialogs", "TrainUI_NotWithinCapacity", {item:this.m_order.getCommodityType()}));
                }
            }
            else if (this.m_order.getAction() == TrainOrder.BUY)
            {
                _loc_5 = Global.player.commodities.getCount(this.m_order.getCommodityType());
                if (param1 > _loc_5)
                {
                    UI.displayMessage(ZLoc.t("Dialogs", "TrainUI_NotEnoughCommodity", {item:this.m_order.getCommodityType()}));
                    return false;
                }
            }
            return true;
        }//end

    }



