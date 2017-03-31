package Display.NeighborUI;

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
import Classes.virals.*;
import Display.*;
import Display.FactoryUI.*;
import Display.aswingui.*;
import Engine.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Modules.matchmaking.*;
import Modules.socialinventory.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import ZLocalization.*;
import com.greensock.*;
import com.greensock.easing.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
import org.aswing.*;

    public class FriendSlidePick extends SlidePick
    {
        protected  Point PICK_OFFSET =new Point (-30,-70);
        protected Vector3 m_position ;
        protected boolean m_listeningToRender =false ;
        protected boolean m_slideVisible =false ;
        protected Player m_friend ;
        protected int m_outFrames =0;
        protected boolean m_out =false ;
        protected boolean m_accepted =false ;
        protected FriendVisitMgrHelper m_manager ;
        protected boolean m_loggedStat ;
        protected CustomButton m_acceptThankBtn ;
        protected CustomButton m_acceptBtn ;
        protected JLabel m_info ;
        protected JLabel m_info2 ;
        protected int m_tweenCount =0;
        protected double m_tweenY =0;
        public static  int SLIDE_WIDTH =350;
        public static  int BUTTON_HEIGHT =25;
        public static  int BUTTON_LABEL_HEIGHT =28;
        public static  int INFO_PANE_HEIGHT =24;
        public static  int SLIDE_Y_OFFSET =10;
        public static  int STATE_VISIT_BACK =3;
        private static  int NUM_STATES =4;

        public  FriendSlidePick (Player param1 ,FriendVisitMgrHelper param2 ,boolean param3 =false ,boolean param4 =false ,int param5 =0,boolean param6 =false )
        {

            this.m_friend = param1;
            this.m_manager = param2;
            m_state = STATE_CLOSED;
            this.m_loggedStat = false;
            super(this.m_friend.snUser.picture, param3, param4, param5, false, SLIDE_WIDTH);
            enableInteraction();
            enableStageInputHandling();
            return;
        }//end

         protected void  init (String param1 )
        {
            super.init(param1);
            m_innerPanel.setBackgroundDecorator(new MarginBackground(m_bgAsset, this.innerBackgroundMargins));
            m_innerPanel.setPreferredHeight(-1);
            m_innerPanel.setMinimumHeight(-1);
            return;
        }//end

         protected void  redrawMask ()
        {
            super.redrawMask();
            _loc_1 = slideWidth+this.innerBackgroundMargins.left+this.innerBackgroundMargins.right;
            _loc_2 = m_bgAsset.height+this.innerBackgroundMargins.top+this.innerBackgroundMargins.bottom;
            m_innerMask.graphics.beginFill(0, 1);
            m_innerMask.graphics.drawRect(pickWidth, INNER_Y_OFFSET, _loc_1, _loc_2);
            m_innerMask.graphics.endFill();
            return;
        }//end

         protected void  onClick (MouseEvent event )
        {
            if (Global.ui.mouseEnabled == false)
            {
                return;
            }
            SocialInventoryManager.hideAllHearts();
            if (m_state == STATE_INFO)
            {
                event.stopPropagation();
                event.stopImmediatePropagation();
                if (this.m_manager.orderStatus == VisitorHelpOrder.CLAIMED)
                {
                    m_state = STATE_VISIT_BACK;
                }
                else
                {
                    m_state = STATE_ACCEPT_DECLINE;
                }
                this.toggleInnerPane();
            }
            return;
        }//end

         protected void  onOver (MouseEvent event )
        {
            if (Global.ui.mouseEnabled == false)
            {
                return;
            }
            if (this.m_accepted)
            {
                return;
            }
            if (!this.m_slideVisible)
            {
                this.m_manager.moveToFront(this);
                this.showSlidePane();
            }
            this.m_out = false;
            this.m_outFrames = 0;
            return;
        }//end

         protected void  onOut (MouseEvent event )
        {
            this.m_out = true;
            this.m_outFrames = 0;
            return;
        }//end

         public boolean  onMouseUp (MouseEvent event )
        {
            Rectangle _loc_2 =null ;
            if (this.m_slideVisible)
            {
                _loc_2 = this.getRect(this.parent);
                if (!_loc_2.contains(event.stageX, event.stageY))
                {
                    this.closeInnerPane();
                }
                else
                {
                    return true;
                }
            }
            return false;
        }//end

        public void  showSlidePane ()
        {
            if (this.m_slideVisible)
            {
                return;
            }
            _loc_1 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_NEIGHBOR_VISIT_2 );
            if (_loc_1 >= ExperimentDefinitions.NEIGHBOR_VISIT_2_FEATURE && this.m_manager.orderStatus == OrderStates.ACCEPTED)
            {
                m_state = STATE_VISIT_BACK;
            }
            else
            {
                m_state = STATE_ACCEPT_DECLINE;
            }
            SocialInventoryManager.showAllHearts();
            m_sliderActive = true;
            buildAndShowInnerPane();
            this.m_slideVisible = true;
            this.m_manager.showingSlide(this);
            return;
        }//end

        public Sprite  getPickSprite ()
        {
            return this.m_mainSprite;
        }//end

         protected Class  getPickBackground ()
        {
            return EmbeddedArt.friendReplayPick;
        }//end

         protected Class  getInnerBackground ()
        {
            return EmbeddedArt.friendReplaySlide;
        }//end

         protected Insets  innerBackgroundMargins ()
        {
            if (!m_innerBackgroundMargins)
            {
                m_innerBackgroundMargins = new Insets(0, 0, 10, 0);
            }
            return m_innerBackgroundMargins;
        }//end

         protected void  initInner ()
        {
            super.initInner();
            switch(m_state)
            {
                case STATE_INFO:
                {
                    this.initInfo();
                    break;
                }
                case STATE_ACCEPT_DECLINE:
                {
                    this.initToaster();
                    break;
                }
                case STATE_VISIT_BACK:
                {
                    this.initVisitBack();
                    break;
                }
                default:
                {
                    break;
                }
            }
            if (!this.m_loggedStat)
            {
                this.logStat("view");
                this.m_loggedStat = true;
            }
            return;
        }//end

         protected void  onIconLoaded (Event event )
        {
            int _loc_2 =50;
            int _loc_3 =10;
            m_icon =(Bitmap) event.target.content;
            m_icon.smoothing = true;
            m_icon.width = m_icon.width > _loc_2 ? (_loc_2) : (m_icon.width);
            m_icon.height = m_icon.height > _loc_2 ? (_loc_2) : (m_icon.height);
            _loc_4 = this.m_assetPane.width ;
            if (this.m_assetPane.width == 0)
            {
                _loc_4 = m_mainSprite.width;
            }
            m_icon.x = _loc_4 / 2 - m_icon.width / 2;
            m_icon.y = _loc_4 / 2 - m_icon.height / 2;
            m_icon.y = m_icon.y + _loc_3;
            Sprite _loc_5 =new Sprite ();
            _loc_5.graphics.beginFill(16777215);
            _loc_5.graphics.drawRect(m_icon.x, m_icon.y, m_icon.width, m_icon.height);
            _loc_5.graphics.endFill();
            m_mainSprite.addChildAt(m_icon, 0);
            m_mainSprite.addChildAt(_loc_5, 0);
            prepareWindow();
            return;
        }//end

        public void  setState (int param1 )
        {
            if (param1 >= NUM_STATES)
            {
                return;
            }
            m_state = param1;
            buildAndShowInnerPane();
            return;
        }//end

        public boolean  isTweening ()
        {
            return m_tween != null;
        }//end

        protected void  onUpdateTween (Object param1 )
        {
            _loc_2 = Vector3.lerp(param1.startPos,param1.endPos,param1.alpha);
            this.m_position = _loc_2;
            return;
        }//end

        protected void  onCompleteTween ()
        {
            m_tween.kill();
            m_tween = null;
            this.m_manager.tweenToFinished(this);
            return;
        }//end

        public void  tweenToPos (Vector3 param1 ,double param2 =1.5)
        {
            if (m_tween)
            {
                m_tween.complete(true);
                m_tween.kill();
                m_tween = null;
            }
            Object _loc_3 ={startPos this.m_position ,endPos ,alpha 0};
            m_tween = new TimelineLite({onUpdate:this.onUpdateTween, onUpdateParams:.get(_loc_3), onComplete:this.onCompleteTween});
            m_tween.appendMultiple(.get(new Z_TweenLite(_loc_3, param2, {alpha:1})));
            this.startListen();
            m_tween.gotoAndPlay(0);
            return;
        }//end

         public void  setPosition (double param1 ,double param2 )
        {
            this.m_position = new Vector3(param1, param2);
            this.startListen();
            return;
        }//end

        protected void  renderListener (Event event )
        {
            Point _loc_2 =null ;
            if (this.m_out)
            {
                this.m_outFrames++;
                if (this.m_outFrames == 45 && this.m_slideVisible && m_state == STATE_ACCEPT_DECLINE)
                {
                    this.closeInnerPane();
                }
            }
            if (this.m_position)
            {
                _loc_2 = IsoMath.tilePosToPixelPos(this.m_position.x, this.m_position.y);
                _loc_2 = IsoMath.viewportToStage(_loc_2);
                this.x = _loc_2.x + this.PICK_OFFSET.x;
                this.y = _loc_2.y + this.PICK_OFFSET.y + this.m_tweenY;
            }
            return;
        }//end

        protected void  startListen ()
        {
            if (!this.m_listeningToRender && stage != null)
            {
                stage.addEventListener(Event.RENDER, this.renderListener, false, 0, true);
                this.m_listeningToRender = true;
            }
            return;
        }//end

         public void  kill (Function param1)
        {
            super.kill(param1);
            if (this.m_listeningToRender)
            {
                Global.stage.removeEventListener(Event.RENDER, this.renderListener);
                this.m_listeningToRender = false;
            }
            disableInteraction();
            disableStageInputHandling();
            return;
        }//end

         public void  cleanUp ()
        {
            super.cleanUp();
            if (this.parent)
            {
                this.parent.removeChild(this);
            }
            return;
        }//end

        protected LocalizationName  fn ()
        {
            String _loc_1 =null ;
            _loc_1 = Global.player.getFriendFirstName(this.m_friend.uid);
            if (_loc_1 == null)
            {
                _loc_1 = "???";
            }
            return ZLoc.tn(_loc_1);
        }//end

        protected void  initInfo ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(AsWingConstants.CENTER );
            _loc_1.setPreferredWidth(slideWidth);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.CENTER );
            _loc_2.setPreferredHeight(INFO_PANE_HEIGHT);
            this.m_info = new JLabel(ZLoc.t("Dialogs", "StoppedBy", {name:this.fn()}));
            this.m_info.setFont(ASwingHelper.getBoldFont(14));
            _loc_2.append(this.m_info);
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(_loc_2);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.LEFT);
            _loc_2.setPreferredHeight(INFO_PANE_HEIGHT);
            _loc_3 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_3.setPreferredWidth(30);
            ASwingHelper.prepare(_loc_3);
            _loc_2.append(_loc_3);
            this.m_info2 = new JLabel(ZLoc.t("Dialogs", "ClickToRespond"));
            this.m_info2.setFont(ASwingHelper.getBoldFont(14));
            _loc_2.append(this.m_info2);
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(_loc_2);
            ASwingHelper.prepare(_loc_1);
            innerAppendAll(_loc_1);
            return;
        }//end

        protected void  initToaster ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(AsWingConstants.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT );
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            ASwingHelper.setEasyBorder(_loc_3, 0, 20, 10, 10);
            _loc_5 = ASwingHelper.makeLittleCloseButton ();
            _loc_5.addActionListener(this.onCancel, 0, true);
            _loc_2.appendAll(_loc_5);
            this.m_info = new JLabel(ZLoc.t("Dialogs", "AcceptActions", {name:this.fn()}));
            this.m_info.setFont(ASwingHelper.getBoldFont(14));
            _loc_3.append(this.m_info);
            _loc_6 =Global.gameSettings().getString("visitAcceptAndThankReward","mysterygift_v1");
            _loc_7 =Global.world.viralMgr.canSendRequest(RequestType.GIFT_REQUEST ,.get(this.m_friend.uid) ,{item _loc_6 });
            _loc_8 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_NEIGHBOR_VISIT_2_GIFT_BACK )==ExperimentDefinitions.NEIGHBOR_VISIT_2_GIFT_BACK_FEATURE ;
            if (_loc_7 && _loc_8 && SocialInventoryManager.isFeatureAvailable())
            {
                this.m_acceptThankBtn = new CustomButton(ZLoc.t("Dialogs", "AcceptAndThank"), null, "GreenSmallButtonUI");
                this.m_acceptThankBtn.setPreferredHeight(BUTTON_HEIGHT);
                this.m_acceptThankBtn.addActionListener(this.onAcceptAndThank, 0, true);
                this.m_acceptThankBtn.setMargin(new Insets(0, 3, 0, 3));
                _loc_4.appendAll(this.m_acceptThankBtn, ASwingHelper.horizontalStrut(5));
            }
            this.m_acceptBtn = new CustomButton(ZLoc.t("Dialogs", "Accept"), null, "GreenSmallButtonUI");
            this.m_acceptBtn.setPreferredHeight(BUTTON_HEIGHT);
            this.m_acceptBtn.addActionListener(this.onAccept, 0, true);
            this.m_acceptBtn.setMargin(new Insets(0, 3, 0, 3));
            _loc_4.append(this.m_acceptBtn);
            ASwingHelper.setEasyBorder(_loc_4, 0, 15, 5, 5);
            ASwingHelper.prepare(_loc_2);
            ASwingHelper.prepare(_loc_3);
            ASwingHelper.prepare(_loc_4);
            _loc_1.appendAll(_loc_2, _loc_3, _loc_4);
            ASwingHelper.prepare(_loc_1);
            innerAppendAll(_loc_1);
            return;
        }//end

        protected void  initVisitBack ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(AsWingConstants.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT );
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            ASwingHelper.setEasyBorder(_loc_3, 5, 20, 25, 20);
            _loc_4 = ASwingHelper.makeLittleCloseButton ();
            _loc_4.addActionListener(this.onCancel, 0, true);
            _loc_2.appendAll(_loc_4);
            _loc_5 = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs","VisitBack"));
            this.m_acceptBtn = new CustomButton(_loc_5, null, "GreenSmallButtonUI");
            this.m_acceptBtn.setPreferredHeight(BUTTON_HEIGHT);
            this.m_acceptBtn.addActionListener(this.onAccept, 0, true);
            _loc_3.append(this.m_acceptBtn);
            ASwingHelper.prepare(_loc_2);
            ASwingHelper.prepare(_loc_3);
            _loc_1.appendAll(_loc_2, _loc_3);
            ASwingHelper.prepare(_loc_1);
            innerAppendAll(_loc_1);
            return;
        }//end

        protected void  onAccept (Event event )
        {
            this.handleAccept();
            this.logStat("accept");
            return;
        }//end

        protected void  onAcceptAndThank (Event event )
        {
            _loc_2 =Global.gameSettings().getString("visitAcceptAndThankReward","mysterygift_v1");
            Object _loc_3 ={category subcategory "neighbor_visit","accept_and_thank"family ,};
            Global.world.viralMgr.sendRequest(RequestType.GIFT_REQUEST, [this.m_friend.uid], {item:_loc_2, message:"VisitAcceptAndThank"}, this.onGiftBackSent, _loc_3);
            this.m_acceptBtn.setEnabled(false);
            this.m_acceptThankBtn.setEnabled(false);
            this.logStat("accept_and_thank", MatchmakingManager.instance.getNeighborStatsId(this.m_friend.uid), this.m_friend.uid);
            return;
        }//end

        protected void  onGiftBackSent (boolean param1 ,Array param2 )
        {
            this.handleAccept();
            return;
        }//end

        protected void  handleAccept ()
        {
            switch(m_state)
            {
                case STATE_ACCEPT_DECLINE:
                {
                    this.closeInnerPane();
                    this.m_manager.acceptVisit(this);
                    this.m_accepted = true;
                    break;
                }
                case STATE_VISIT_BACK:
                {
                    this.closeInnerPane();
                    UI.visitNeighbor(this.m_friend.uid);
                    if (MatchmakingManager.instance.isBuildingBuddy(this.m_friend.uid))
                    {
                        this.logStat("matchmaking", this.m_friend.uid);
                    }
                    this.m_accepted = true;
                    this.kill(this.cleanUp);
                    this.m_manager.notifyHelperFinished();
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

        protected void  onCancel (Event event )
        {
            this.m_manager.cancelVisit(this);
            this.logStat("cancel");
            return;
        }//end

        public void  toggleInnerPane ()
        {
            if (this.m_slideVisible)
            {
                Z_TweenLite.to(m_innerSprite, 0.5, {x:m_offXPos, onComplete:buildAndShowInnerPane});
            }
            return;
        }//end

        public void  closeInnerPane ()
        {
            if (this.m_slideVisible)
            {
                this.m_slideVisible = false;
                Z_TweenLite.to(m_innerSprite, 0.5, {x:m_offXPos, onComplete:this.panelClosed});
            }
            return;
        }//end

        protected void  panelClosed ()
        {
            this.m_manager.panelClosed(this);
            SocialInventoryManager.hideAllHearts();
            return;
        }//end

         protected double  innerYOffset ()
        {
            return SLIDE_Y_OFFSET;
        }//end

        public void  tweenBounce ()
        {
            if (m_tween != null)
            {
                m_tween.complete(false);
                m_tween.kill();
                m_tween = null;
            }
            Object _loc_1 ={pos endPos 0,-HEIGHT_OFFSET ,alpha 0};
            m_tween = new TimelineLite({onUpdate:this.onUpdateAnim, onUpdateParams:.get(_loc_1)});
            m_tween.appendMultiple(.get(new Z_TweenLite(_loc_1, 0.5, {alpha:1, ease:Back.easeOut}), new Z_TweenLite(_loc_1, 0.5, {alpha:0, ease:Back.easeIn})), 0, TweenAlign.SEQUENCE);
            m_tween.gotoAndPlay(0);
            return;
        }//end

        protected void  onUpdateAnim (Object param1 )
        {
            _loc_2 = param1.endPos *param1.alpha ;
            this.m_tweenY = _loc_2;
            return;
        }//end

        protected void  logStat (String param1 ,String param2 ="",String param3 ="")
        {
            switch(m_state)
            {
                case STATE_ACCEPT_DECLINE:
                {
                    StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.DIALOG_NEIGHBOR_PICK, StatsPhylumType.NEIGHBORS2_VISIT_HELP_PICK, param1, param2, param3);
                    break;
                }
                case STATE_VISIT_BACK:
                {
                    StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.DIALOG_NEIGHBOR_PICK, StatsPhylumType.NEIGHBORS2_VISIT_BACK_PICK, param1, param2, param3);
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

    }



