package Display.LotUI;

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
import Classes.orders.*;
import Classes.sim.*;
import Display.FactoryUI.*;
import Display.aswingui.*;
import ZLocalization.*;
import com.greensock.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
import org.aswing.*;

    public class LotSlidePick extends SlidePick
    {
        protected JLabel m_info ;
        protected JLabel m_info2 ;
        protected JLabel m_amountLabel ;
        protected JLabel m_jLabel ;
        protected LotManager m_lotManager ;
        protected LotOrder m_order ;
        protected int m_uiHandle ;
        protected String m_senderName ;
        protected CustomButton m_acceptBtn ;
        protected CustomButton m_laterBtn ;
        protected CustomButton m_declineBtn ;
        public static  int MARGIN_WIDTH =30;
        public static  int BUTTON_HEIGHT =25;
        public static  int INFO_PANE_HEIGHT =30;

        public  LotSlidePick (LotManager param1 ,LotOrder param2 ,boolean param3 =true ,boolean param4 =false ,int param5 =0,boolean param6 =false ,int param7 =-1)
        {
            this.m_order = param2;
            this.m_lotManager = param1;
            this.m_uiHandle = 1;
            this.m_info = null;
            this.m_amountLabel = null;
            m_state = STATE_CLOSED;
            _loc_8 = param2.getSenderID();
            _loc_9 =Global.player.findFriendById(_loc_8 );
            _loc_10 =Global.player.findFriendById(_loc_8 )? (_loc_9.snUser.picture) : (null);
            this.m_senderName = _loc_9 ? (_loc_9.firstName) : ("");
            _loc_11 = this.m_order.getOrderResourceName ()|| ZLoc.t("Items", this.m_order.getResourceType() + "_friendlyName");
            _loc_12 = ZLoc.t("Dialogs","lotOrder_accept_message",{nameZLoc.tn(this.m_senderName),businessName});
            this.m_jLabel = new JLabel(_loc_12);
            this.m_jLabel.setFont(ASwingHelper.getBoldFont(14));
            ASwingHelper.prepare(this.m_jLabel);
            super(_loc_10, param3, param4, param5, false, this.m_jLabel.width + 2 * MARGIN_WIDTH);
            addEventListener(MouseEvent.MOUSE_MOVE, this.stopMousePropagation, false, 0, true);
            addEventListener(MouseEvent.MOUSE_OUT, this.stopMousePropagation, false, 0, true);
            addEventListener(MouseEvent.MOUSE_OVER, this.stopMousePropagation, false, 0, true);
            addEventListener(MouseEvent.CLICK, this.onMouseClick, false, 0, true);
            return;
        }//end

        protected void  stopMousePropagation (MouseEvent event )
        {
            event.stopPropagation();
            event.stopImmediatePropagation();
            return;
        }//end

        protected void  onMouseClick (MouseEvent event )
        {
            LotSite _loc_3 =null ;
            SlidePickGroup _loc_4 =null ;
            _loc_2 = this.order ? (this.order.getLotId()) : (0);
            if (_loc_2)
            {
                _loc_3 = Global.world.citySim.lotManager.getLotSite(_loc_2);
                if (_loc_3)
                {
                    _loc_4 =(SlidePickGroup) _loc_3.getPick();
                    if (_loc_4)
                    {
                        _loc_4.onClick();
                    }
                }
            }
            return;
        }//end

        public Sprite  getPickSprite ()
        {
            return this.m_mainSprite;
        }//end

         public Rectangle  getMainSpriteRect (DisplayObject param1 )
        {
            _loc_2 = super.getMainSpriteRect(param1);
            if (m_innerSprite.visible && m_innerSprite.x == 0)
            {
                _loc_2.width = _loc_2.width + slideWidth;
            }
            return _loc_2;
        }//end

         protected Class  getPickBackground ()
        {
            return EmbeddedArt.trainUIPick;
        }//end

         protected Class  getInnerBackground ()
        {
            return EmbeddedArt.trainUIBG;
        }//end

         protected void  initInner ()
        {
            super.initInner();
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(AsWingConstants.CENTER );
            _loc_1.setPreferredWidth(slideWidth);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.CENTER );
            _loc_2.setPreferredWidth(slideWidth);
            _loc_2.setPreferredHeight(INFO_PANE_HEIGHT);
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.CENTER ,10);
            this.m_acceptBtn = new CustomButton(ZLoc.t("Dialogs", "Accept"), null, "GreenSmallButtonUI");
            this.m_acceptBtn.setPreferredHeight(BUTTON_HEIGHT);
            this.m_acceptBtn.addActionListener(this.onAccept, 0, true);
            _loc_3.append(this.m_acceptBtn);
            this.m_laterBtn = new CustomButton(ZLoc.t("Dialogs", "TrainUI_Later"), null, "OrangeSmallButtonUI");
            this.m_laterBtn.setPreferredHeight(BUTTON_HEIGHT);
            this.m_laterBtn.addActionListener(this.onLater, 0, true);
            _loc_3.append(this.m_laterBtn);
            this.m_declineBtn = new CustomButton(ZLoc.t("Dialogs", "TrainUI_Decline"), null, "RedSmallButtonUI");
            this.m_declineBtn.setPreferredHeight(BUTTON_HEIGHT);
            this.m_declineBtn.addActionListener(this.onDecline, 0, true);
            _loc_3.append(this.m_declineBtn);
            _loc_2.appendAll(ASwingHelper.horizontalStrut(MARGIN_WIDTH), this.m_jLabel);
            ASwingHelper.prepare(_loc_2);
            ASwingHelper.prepare(_loc_3);
            _loc_1.appendAll(_loc_2, _loc_3);
            ASwingHelper.prepare(_loc_1);
            innerAppendAll(_loc_1);
            return;
        }//end

        protected void  makeAssets (DisplayObject param1 ,String param2 )
        {
            this.openInnerPane();
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

        public LotOrder  order ()
        {
            return this.m_order;
        }//end

        public void  openInnerPane ()
        {
            if (m_sliderActive)
            {
                TweenLite.to(m_innerSprite, 0.5, {x:m_offXPos, onComplete:buildAndShowInnerPane});
            }
            return;
        }//end

        public void  closeInnerPane ()
        {
            if (m_sliderActive)
            {
                TweenLite.to(m_innerSprite, 0.5, {x:m_offXPos});
            }
            return;
        }//end

        public void  backOnePanel ()
        {
            if (m_state == STATE_ACCEPT_DECLINE)
            {
                m_state = STATE_INFO;
                this.openInnerPane();
            }
            return;
        }//end

        private void  removeButtonListeners ()
        {
            if (this.m_acceptBtn)
            {
                this.m_acceptBtn.removeActionListener(this.onAccept);
            }
            if (this.m_laterBtn)
            {
                this.m_laterBtn.removeActionListener(this.onLater);
            }
            if (this.m_declineBtn)
            {
                this.m_declineBtn.removeActionListener(this.onDecline);
            }
            return;
        }//end

        protected void  onAccept (Event event )
        {
            this.removeButtonListeners();
            if (!this.m_lotManager || !this.m_order)
            {
                this.kill();
            }
            if (this.m_order.getState() == OrderStates.PENDING)
            {
                this.kill(this.lotManagerAccepted);
            }
            return;
        }//end

        protected void  lotManagerAccepted ()
        {
            _loc_1 = this.m_lotManager.accepted(this.m_order );
            _loc_2 = _loc_1as Business ;
            FranchiseViralManager.triggerFranchiseViralFeeds(FranchiseViralManager.VIRAL_ACCEPTEDBUILDING, this.m_order.getResourceType(), this.m_order.getSenderID(), null, 0, _loc_2);
            return;
        }//end

        protected void  onLater (Event event )
        {
            this.removeButtonListeners();
            this.kill(this.lotManagerLater);
            return;
        }//end

        protected void  lotManagerLater ()
        {
            this.m_lotManager.later(this.m_order);
            return;
        }//end

        protected void  onDecline (Event event )
        {
            this.removeButtonListeners();
            this.kill(this.lotManagerDeclined);
            return;
        }//end

        protected void  lotManagerDeclined ()
        {
            this.m_lotManager.declined(this.m_order);
            _loc_1 = this.m_lotManager.accepted(this.m_order );
            _loc_2 = _loc_1as Business ;
            FranchiseViralManager.triggerFranchiseViralFeeds(FranchiseViralManager.VIRAL_DECLINEDBUILDING, this.m_order.getResourceType(), this.m_order.getSenderID(), null, 0, _loc_2);
            return;
        }//end

    }



