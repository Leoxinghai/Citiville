package Display.FactoryUI;

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
import Classes.effects.*;
import Classes.util.*;
import Classes.virals.*;
import Display.*;
import Display.NeighborUI.*;
import Display.aswingui.*;
import Engine.*;
import Engine.Managers.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class FriendNPCSlidePick extends NPCSlidePick implements ICustomToolTipTarget
    {
        protected Object m_friendData ;
        protected Player m_friend ;
        protected String m_firstName ;
        protected boolean m_paused =false ;
        protected boolean m_showingMessagePane =false ;
        protected Object m_toolTipComponents ;
        protected JLabel m_info ;
        protected boolean m_slideVisible =false ;
        protected Array m_fakeNames ;
        protected Function m_onThankYouCallback =null ;
        protected CustomButton m_thankYouButton ;
        protected boolean m_allowReporting =true ;
        public static  String FRIEND_ID ="id";
        public static  String CHECK_IN_TIMESTAMP ="ts";
        public static  String CHECK_IN_TIME ="time";
        public static  String UGC_MESSAGE ="ugc_msg";
        public static  String UGC_MESSAGE_REPORTED ="ugc_msg_reported";
        public static  String HAS_UGC_MESSAGE ="has_ugc_message";
        public static  double UGC_MSG_MAX_WIDTH =300;
        public static  int MAXIMUM_REPORT_ALLOWED =2;
public static SlidePickGroupManager m_group =new SlidePickGroupManager ();

        public  FriendNPCSlidePick (NPC param1 ,Object param2 )
        {
            this.m_toolTipComponents = new Object();
            this.m_fakeNames = .get("Kevin", "David", "Ben", "Blair", "Jess", "Roman", "Chris", "Jennifer", "Mauro", "Sora", "Jason", "Manilo", "Manlio", "Adam", "Pinky");
            this.m_friendData = param2;
            if (param2.get(FRIEND_ID) != null)
            {
                this.m_friend = Global.player.findFriendById(String(param2.get(FRIEND_ID)));
                if (this.m_friend == null && String(param2.get(FRIEND_ID)) == Global.player.uid)
                {
                    this.m_friend = Global.player;
                }
            }
            if (param2.get(HAS_UGC_MESSAGE) != null)
            {
                this.m_allowReporting = Boolean(param2.get(HAS_UGC_MESSAGE));
                if (this.isMessageReportedForMe())
                {
                    this.m_allowReporting = false;
                }
                if (this.isMessageReportCountExceeded())
                {
                    this.m_allowReporting = false;
                }
            }
            if (this.m_friend)
            {
                this.m_firstName = this.m_friend.firstName;
            }
            else
            {
                this.m_firstName = this.m_fakeNames.get(Math.random() * this.m_fakeNames.length());
            }
            _loc_3 = this.m_friend ? (this.m_friend.snUser.picture) : (Global.getAssetURL("assets/hud/Friendbar/portrait_noProfileImg.png"));
            if (!this.m_friend)
            {
                this.getNonFriendInfo(param2.get(FRIEND_ID));
            }
            super(param1, _loc_3);
            this.slidePickGroup.addPick(this);
            enableInteraction();
            enableStageInputHandling();
            m_stopPropogatingMouseEvents = true;
            return;
        }//end

        protected void  getNonFriendInfo (String param1 )
        {
            TGetAvatarData _loc_2 =new TGetAvatarData(param1 ,this.onNonFriendInfoRetrieved );
            GameTransactionManager.addTransaction(_loc_2);
            return;
        }//end

        protected void  onNonFriendInfoRetrieved (Player param1 )
        {
            if (param1 && param1.snUser && param1.snUser.picture && param1.snUser.picture != "")
            {
                LoadingManager.load(param1.snUser.picture, this.onReplaceIcon);
                this.replaceName(param1.snUser.firstName);
            }
            return;
        }//end

        protected void  onReplaceIcon (Event event )
        {
            if (m_icon != null)
            {
                m_mainSprite.removeChild(m_icon);
            }
            if (m_iconBackground != null)
            {
                m_mainSprite.removeChild(m_iconBackground);
            }
            onIconLoaded(event);
            return;
        }//end

        protected void  replaceName (String param1 )
        {
            this.m_firstName = param1;
            return;
        }//end

         protected void  init (String param1 )
        {
            Array _loc_5 =null ;
            Array _loc_6 =null ;
            m_innerSprite = new Sprite();
            m_innerWindow = new JWindow(m_innerSprite);
            addChild(m_innerSprite);
            m_jw = new JWindow(this);
            _loc_2 = getPickBackground();
            _loc_3 = getInnerBackground();
            m_mainSprite = new Sprite();
            DisplayObject _loc_4 =(DisplayObject)new _loc_2;
            if (this.m_friendData.get(HAS_UGC_MESSAGE) != null && Boolean(this.m_friendData.get(HAS_UGC_MESSAGE)))
            {
                _loc_5 = _loc_4.filters;
                _loc_6 = new Array();
                _loc_6 = _loc_6.concat(.get(0.89, 0, 0, 0, 0));
                _loc_6 = _loc_6.concat(.get(0, 0.79, 0, 0, 0));
                _loc_6 = _loc_6.concat(.get(0, 0, 0.121, 0, 0));
                _loc_6 = _loc_6.concat(.get(0, 0, 0, 1, 0));
                _loc_5.push(new ColorMatrixFilter(_loc_6));
                _loc_4.filters = _loc_5;
            }
            m_mainSprite.addChild(_loc_4);
            m_bgAsset =(DisplayObject) new _loc_3;
            m_innerPanel.setBackgroundDecorator(new MarginBackground(m_bgAsset));
            m_innerPanel.setPreferredHeight(m_bgAsset.height);
            m_innerPanel.setMinimumHeight(m_bgAsset.height);
            if (param1 == null)
            {
                param1 = Global.getAssetURL("assets/hud/Friendbar/portrait_noProfileImg.png");
            }
            LoadingManager.load(param1, onIconLoaded, 0, onIconFail);
            return;
        }//end

         protected void  initInner ()
        {
            String _loc_1 =null ;
            JPanel _loc_2 =null ;
            JPanel _loc_3 =null ;
            JPanel _loc_4 =null ;
            double _loc_5 =0;
            JPanel _loc_6 =null ;
            AssetPane _loc_7 =null ;
            TextField _loc_8 =null ;
            boolean _loc_9 =false ;
            JPanel _loc_10 =null ;
            CustomButton _loc_11 =null ;
            super.initInner();
            if (m_state == STATE_ACCEPT_DECLINE)
            {
                _loc_1 = this.getUGCMessage();
                if (!_loc_1)
                {
                    return;
                }
                _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.LEFT);
                _loc_3 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.LEFT);
                _loc_4 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.CENTER, 15);
                _loc_5 = 5;
                _loc_6 = ASwingHelper.verticalStrut(_loc_5);
                ASwingHelper.setEasyBorder(_loc_2, 3, 20, 0, 10);
                _loc_7 = ASwingHelper.makeMultilineText(_loc_1, -1, EmbeddedArt.defaultFontNameBold, TextFieldAutoSize.LEFT, 12, 0);
                _loc_8 =(TextField) _loc_7.getAsset();
                _loc_7.setPreferredWidth(_loc_8.width);
                if (_loc_8.textWidth > UGC_MSG_MAX_WIDTH)
                {
                    _loc_7 = ASwingHelper.makeMultilineText(_loc_1, UGC_MSG_MAX_WIDTH, EmbeddedArt.defaultFontNameBold, TextFieldAutoSize.LEFT, 12, 0);
                    _loc_8 =(TextField) _loc_7.getAsset();
                }
                _loc_3.setPreferredHeight(Math.min(Math.max((_loc_8.textHeight + 1), FriendSlidePick.BUTTON_LABEL_HEIGHT), FriendSlidePick.BUTTON_LABEL_HEIGHT + FriendSlidePick.BUTTON_LABEL_HEIGHT / 2));
                _loc_3.append(_loc_7);
                _loc_9 = this.m_friend && Global.world.viralMgr.canPost(ViralType.HOTEL_THANKYOU, this.m_friend.uid);
                this.m_thankYouButton = new CustomButton(ZLoc.t("Dialogs", "ThanksCaps"), null, "GreenSmallButtonUI");
                this.m_thankYouButton.setPreferredHeight(FriendSlidePick.BUTTON_HEIGHT);
                this.m_thankYouButton.addEventListener(MouseEvent.MOUSE_DOWN, this.onThankYou, false, 0, true);
                this.m_thankYouButton.setEnabled(_loc_9);
                if (Global.isVisiting())
                {
                    this.m_thankYouButton.setEnabled(false);
                }
                _loc_10 = ASwingHelper.makeSoftBoxJPanelVertical();
                _loc_11 = new CustomButton(ZLoc.t("Dialogs", "ReportCaps"), null, "CashSmallButtonUI");
                _loc_11.addEventListener(MouseEvent.MOUSE_DOWN, this.onReport, false, 0, true);
                _loc_10.append(_loc_11);
                if (this.isMessageReportedForMe() || !this.m_allowReporting)
                {
                    _loc_11.setEnabled(false);
                }
                if (this.isMessageReportCountExceeded())
                {
                    _loc_11.setEnabled(false);
                }
                _loc_4.appendAll(this.m_thankYouButton, _loc_10);

                _loc_6.mouseChildren = false;
                _loc_3.mouseChildren = false;

                _loc_6.mouseEnabled = false;
                _loc_3.mouseEnabled = false;
                _loc_2.appendAll(_loc_3, _loc_6, _loc_4);
                ASwingHelper.prepare(_loc_2);
                innerAppendAll(_loc_2);
                m_slideWidth = _loc_2.getWidth();
                m_slideHeight = _loc_2.getHeight() + _loc_5;
                m_offXPos = -m_slideWidth;
                m_innerPanel.setPreferredSize(new IntDimension(m_slideWidth, m_slideHeight));
                (m_innerPanel.getBackgroundDecorator() as MarginBackground).margin.bottom = 20;
                redrawMask();
            }
            return;
        }//end

         public void  owner (NPC param1 )
        {
            npc = param1;
            super.owner = npc;
            if (owner)
            {
                if (owner.highlightCallback == null && owner.playActionCallback == null)
                {
                    owner .playActionCallback =void  ()
            {
                onNPCClick();
                return;
            }//end
            ;
                    owner .highlightCallback =void  (boolean param1 ,boolean param2 )
            {
                if (param1 && !param2)
                {
                    onNPCRollOver();
                }
                else if (!param1 && param2)
                {
                    onNPCRollOut();
                }
                return;
            }//end
            ;
                }
            }
            return;
        }//end

         protected void  onRollOver (MouseEvent event )
        {
            super.onRollOver(event);
            this.pauseAndWaveNPC();
            this.slidePickGroup.makeTopPick(this);
            return;
        }//end

         protected void  onRollOut (MouseEvent event )
        {
            super.onRollOut(event);
            this.unpauseNPC();
            this.m_showingMessagePane = false;
            this.closeSlidePane();
            return;
        }//end

        protected void  onNPCRollOver ()
        {
            this.toggleNPCGlow(false);
            showNPCPick();
            this.pauseAndWaveNPC();
            return;
        }//end

        protected void  onNPCRollOut ()
        {
            if (!this.m_slideVisible)
            {
                this.unpauseNPC();
                if (!m_showPick)
                {
                    this.toggleNPCGlow(true);
                }
            }
            return;
        }//end

        protected void  onNPCClick ()
        {
            this.onClick(new MouseEvent(MouseEvent.MOUSE_DOWN));
            if (!this.m_slideVisible)
            {
                this.unpauseNPC();
            }
            return;
        }//end

         protected void  onClick (MouseEvent event )
        {
            super.onClick(event);
            this.toggleMessagePanel();
            Global.ui.showToolTip(this);
            this.toggleSlidePane();
            return;
        }//end

         public boolean  onMouseUp (MouseEvent event )
        {
            _loc_2 = this.getRect(this.parent);
            _loc_3 = owner.getDisplayObject().getRect(this.parent);
            if (this.m_slideVisible)
            {
                if (!_loc_2.contains(event.stageX, event.stageY) && !_loc_3.contains(event.stageX, event.stageY))
                {
                    this.closeSlidePane();
                    this.unpauseNPC();
                }
                else
                {
                    return true;
                }
            }
            else if (_loc_2.contains(event.stageX, event.stageY))
            {
                return true;
            }
            return false;
        }//end

         public String  getToolTipHeader ()
        {
            String _loc_1 =null ;
            if (this.m_friend)
            {
                _loc_1 = this.m_friend.name;
            }
            else
            {
                _loc_1 = this.m_firstName;
            }
            return _loc_1;
        }//end

         public String  getToolTipAction ()
        {
            String _loc_1 =null ;
            if (!this.m_showingMessagePane && this.getUGCMessage())
            {
                _loc_1 = ZLoc.t("Dialogs", "FriendNPC_tooltip_action");
            }
            return _loc_1;
        }//end

         public ToolTipSchema  getToolTipSchema ()
        {
            return ToolTipSchema.TOOLTIP_SCHEMA_DEFAULT;
        }//end

        protected void  pauseAndWaveNPC ()
        {
            if (m_npc && !this.m_paused)
            {
                m_npc.pauseAndWave();
                m_npc.setHighlightedSpecial(true, Constants.COLOR_HIGHLIGHT, 8);
                this.m_paused = true;
            }
            return;
        }//end

        protected void  unpauseNPC ()
        {
            if (m_npc && this.m_paused)
            {
                m_npc.unpause();
                m_npc.setHighlightedSpecial(false);
                this.m_paused = false;
            }
            return;
        }//end

        protected void  toggleNPCGlow (boolean param1 )
        {
            ShortGlowEffect _loc_2 =null ;
            if (owner)
            {
                if (param1 && !owner.hasAnimatedEffects())
                {
                    _loc_2 =(ShortGlowEffect) owner.addAnimatedEffect(EffectType.SHORT_GLOW);
                    _loc_2.setLoop(true);
                }
                else if (!param1 && owner.hasAnimatedEffects())
                {
                    owner.removeAnimatedEffects();
                }
            }
            return;
        }//end

        public void  toggleSlidePane ()
        {
            if (this.m_slideVisible)
            {
                this.closeSlidePane();
            }
            else
            {
                this.showSlidePane();
            }
            return;
        }//end

        public void  showSlidePane ()
        {
            if (this.m_slideVisible)
            {
                return;
            }
            showNPCPick();
            this.toggleNPCGlow(false);
            m_state = STATE_ACCEPT_DECLINE;
            m_sliderActive = true;
            this.m_slideVisible = true;
            buildAndShowInnerPane();
            return;
        }//end

        public void  closeSlidePane ()
        {
            if (this.m_slideVisible)
            {
                this.m_slideVisible = false;
                this.hideInnerPane();
                hideNPCPick();
                this.toggleNPCGlow(true);
            }
            return;
        }//end

         public void  hideInnerPane ()
        {
            if (m_sliderActive)
            {
                Z_TweenLite.to(m_innerSprite, 0.5, {x:m_offXPos, onComplete:this.finishHideInnerPanel});
            }
            return;
        }//end

        private void  finishHideInnerPanel ()
        {
            m_state = STATE_CLOSED;
            m_innerSprite.visible = false;
            m_innerMask.visible = false;
            return;
        }//end

        public void  setThankyouCallback (Function param1 )
        {
            this.m_onThankYouCallback = param1;
            return;
        }//end

        protected void  onThankYou (MouseEvent event )
        {
            Object _loc_2 =null ;
            this.closeSlidePane();
            postProcessMouseEvent(event);
            if (this.m_onThankYouCallback != null)
            {
                _loc_2 = new Object();
                _loc_2.guestId = this.m_friend.uid;
                _loc_2.friendData = this.m_friendData;
                this.m_thankYouButton.setEnabled(false);
                this.m_onThankYouCallback(_loc_2);
            }
            return;
        }//end

        protected void  onReport (MouseEvent event )
        {
            int _loc_3 =0;
            MapResource _loc_4 =null ;
            if (event.target instanceof CustomButton)
            {
                (event.target as CustomButton).setEnabled(false);
            }
            if (!this.m_friendData.get(UGC_MESSAGE_REPORTED))
            {
                this.m_friendData.put(UGC_MESSAGE_REPORTED,  new Array());
            }
            this.m_friendData.get(UGC_MESSAGE_REPORTED).push(String(Global.player.uid));
            String _loc_2 ="hotel";
            if (this.m_friendData.get("hotelId"))
            {
                _loc_3 = int(this.m_friendData.get("hotelId"));
                _loc_4 =(MapResource) Global.world.getObjectById(_loc_3);
                if (_loc_4)
                {
                    _loc_2 = _loc_4.getItemName();
                }
            }
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", _loc_2, "NPC_dialog", "hotel_guestmessage");
            GameTransactionManager.addTransaction(new THotelReportGuestMessage(Global.getVisiting(), this.m_friendData.get(FRIEND_ID)));
            return;
        }//end

        protected void  toggleMessagePanel ()
        {
            int _loc_2 =0;
            MapResource _loc_3 =null ;
            String _loc_1 ="hotel";
            if (this.m_friendData.get("hotelId"))
            {
                _loc_2 = int(this.m_friendData.get("hotelId"));
                _loc_3 =(MapResource) Global.world.getObjectById(_loc_2);
                if (_loc_3)
                {
                    _loc_1 = _loc_3.getItemName();
                }
            }
            if (!this.m_showingMessagePane)
            {
                if (Global.isVisiting())
                {
                    StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", _loc_1, "NPC_dialog", "visitor_viewmessage");
                }
                else
                {
                    StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.ACTIVE_BUILDING, "click", _loc_1, "NPC_dialog", "owner_viewmessage");
                }
            }
            this.m_showingMessagePane = !this.m_showingMessagePane;
            return;
        }//end

        public boolean  isMessageReportedForMe ()
        {
            int _loc_1 =0;
            if (this.m_friendData.get(UGC_MESSAGE))
            {
                if (this.m_friendData.get(UGC_MESSAGE_REPORTED) && this.m_friendData.get(UGC_MESSAGE_REPORTED) instanceof Array)
                {
                    _loc_1 = 0;
                    while (_loc_1 < this.m_friendData.get(UGC_MESSAGE_REPORTED).length())
                    {

                        if (this.m_friendData.get(UGC_MESSAGE_REPORTED).get(_loc_1).toString() == Global.player.uid)
                        {
                            return true;
                        }
                        _loc_1++;
                    }
                }
            }
            return false;
        }//end

        public boolean  isMessageReportCountExceeded ()
        {
            if (this.m_friendData.get(UGC_MESSAGE_REPORTED).length >= MAXIMUM_REPORT_ALLOWED)
            {
                return true;
            }
            return false;
        }//end

        public String  getUGCMessage ()
        {
            String _loc_1 =null ;
            if (this.m_friendData.get(UGC_MESSAGE) && String(this.m_friendData.get(UGC_MESSAGE)).length != 0)
            {
                if (!this.isMessageReportedForMe())
                {
                    _loc_1 = this.m_friendData.get(UGC_MESSAGE);
                }
                else
                {
                    _loc_1 = ZLoc.t("Dialogs", "FriendNPC_tooltip_ugcMsgReported");
                }
                if (this.m_friendData.get(UGC_MESSAGE_REPORTED).length > MAXIMUM_REPORT_ALLOWED)
                {
                    _loc_1 = ZLoc.t("Dialogs", "FriendNPC_tooltip_ugcMsgReported");
                }
            }
            return _loc_1;
        }//end

         public void  kill (Function param1)
        {
            super.kill(param1);
            this.slidePickGroup.removePick(this);
            return;
        }//end

        public SlidePickGroupManager  slidePickGroup ()
        {
            return m_group;
        }//end

        public String  getCustomToolTipTitle ()
        {
            return this.getToolTipHeader();
        }//end

        public Component  getCustomToolTipImage ()
        {
            return null;
        }//end

        public Array  getToolTipComponentList ()
        {
            Array _loc_1 =new Array();
            if (this.m_friendData.get(CHECK_IN_TIME))
            {
                _loc_1.push(ToolTipDialog.buildToolTipComponent(CHECK_IN_TIME, this.m_toolTipComponents, ZLoc.t("Dialogs", "FriendNPC_tooltip_checkIn", {time:this.m_friendData.get(CHECK_IN_TIME)}), ToolTipSchema.getSchemaForObject(this)));
            }
            _loc_1.push(ToolTipDialog.buildToolTipComponent(ToolTipDialog.ACTION, this.m_toolTipComponents, this.getToolTipAction() ? (TextFieldUtil.formatSmallCapsString(this.getToolTipAction())) : (null), ToolTipSchema.getSchemaForObject(this)));
            return _loc_1;
        }//end

    }




