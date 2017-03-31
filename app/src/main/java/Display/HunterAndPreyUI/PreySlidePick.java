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
import Classes.actions.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.FactoryUI.*;
import Display.aswingui.*;
import Engine.*;
import Engine.Helpers.*;
import Events.*;
import Modules.bandits.*;
import Modules.bandits.transactions.*;
import Modules.workers.*;
import com.greensock.*;
import com.greensock.easing.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.text.*;
import org.aswing.*;

    public class PreySlidePick extends SlidePick
    {
        protected  Point PICK_OFFSET =new Point (-30,-70);
        protected Vector3 m_position ;
        protected boolean m_listeningToRender =false ;
        protected boolean m_slideVisible =false ;
        protected int m_banditId ;
        protected int m_outFrames =0;
        protected boolean m_out =false ;
        protected boolean m_followMovement =false ;
        protected boolean m_accepted =false ;
        protected NPC m_npc =null ;
        protected PreyData m_preyData ;
        protected JPanel m_floatBox ;
        protected AssetPane m_toolTip ;
        protected JLabel m_info ;
        protected JLabel m_info2 ;
        protected int m_tweenCount =0;
        protected double m_tweenY =0;
        public static  int SLIDE_WIDTH =550;
        public static  int BUTTON_HEIGHT =22;
        public static  int BUTTON_LABEL_HEIGHT =40;
        public static  int INFO_PANE_HEIGHT =24;
        public static  int SLIDE_Y_OFFSET =10;

        public  PreySlidePick (NPC param1 ,PreyData param2 ,boolean param3 =false ,boolean param4 =false ,int param5 =0,boolean param6 =false )
        {
            this.m_npc = param1;
            m_state = STATE_CLOSED;
            this.m_preyData = param2;
            this.m_toolTip = ASwingHelper.makeMultilineText(this.getToolTipText(), 500, EmbeddedArt.defaultFontNameBold, TextFormatAlign.CENTER, 16, EmbeddedArt.whiteTextColor, [new GlowFilter(0, 1, 4, 4, 10, BitmapFilterQuality.LOW)]);
            this.m_toolTip.mouseChildren = false;
            this.m_toolTip.mouseEnabled = false;
            _loc_7 = this.m_preyData.thumbnail ;
            _loc_7 = this.m_preyData.thumbnail == "" ? (null) : (Global.getAssetURL(_loc_7));
            super(_loc_7, param3, param4, param5, false, SLIDE_WIDTH);
            enableInteraction();
            enableStageInputHandling();
            this.m_followMovement = true;
            this.m_toolTip.x = (-this.m_toolTip.width) / 2 + this.m_mainSprite.width / 2 + 5;
            this.m_toolTip.y = -(this.m_toolTip.height + 10);
            return;
        }//end

         protected void  onClick (MouseEvent event )
        {
            if (Global.ui.mouseEnabled == false)
            {
                return;
            }
            this.onAccept(event);
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
            if (!contains(this.m_toolTip))
            {
                addChild(this.m_toolTip);
            }
            this.m_out = false;
            this.m_outFrames = 0;
            return;
        }//end

         protected void  onOut (MouseEvent event )
        {
            this.m_out = true;
            this.m_outFrames = 0;
            if (contains(this.m_toolTip))
            {
                removeChild(this.m_toolTip);
            }
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
            m_state = STATE_ACCEPT_DECLINE;
            m_sliderActive = true;
            buildAndShowInnerPane();
            this.m_slideVisible = true;
            this.m_followMovement = false;
            if (this.m_npc)
            {
                this.m_npc.actionQueue.removeAllStates();
            }
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
            return EmbeddedArt.friendReplaySlide;
        }//end

        protected String  getToolTipText ()
        {
            Object _loc_2 =null ;
            String _loc_3 =null ;
            _loc_1 =Global.gameSettings().getXmlData("preyGroups");
            if (_loc_1)
            {
                _loc_2 = _loc_1.get(this.m_preyData.groupId);
                if (_loc_2 && _loc_2.get("captureText"))
                {
                    _loc_3 = _loc_2.get("captureText").get("toolTipKey");
                    return ZLoc.t("Prey", _loc_3, {hunterName:this.m_preyData.name});
                }
            }
            return ZLoc.t("Prey", "ToolTip_Capture", {hunterName:this.m_preyData.name});
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
                default:
                {
                    break;
                }
            }
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
            if (!this.m_followMovement)
            {
                return;
            }
            this.m_position = new Vector3(param1 + 3, param2 + 3);
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

        protected void  initInfo ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(AsWingConstants.CENTER );
            _loc_1.setPreferredWidth(slideWidth);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.CENTER );
            _loc_2.setPreferredHeight(INFO_PANE_HEIGHT);
            this.m_info = new JLabel(this.m_preyData.name);
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
            ASwingHelper.setEasyBorder(_loc_1, 0, 20, 0, 10);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.CENTER );
            _loc_2.setPreferredHeight(BUTTON_LABEL_HEIGHT);
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(AsWingConstants.CENTER ,15);
            _loc_4 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_4.setHeight(10);
            ASwingHelper.prepare(_loc_4);
            _loc_2.append(_loc_4);
            _loc_3.alpha = 0;
            m_innerPanel.setBackgroundDecorator(null);
            ASwingHelper.prepare(_loc_2);
            ASwingHelper.prepare(_loc_3);
            _loc_1.appendAll(_loc_2, _loc_3);
            ASwingHelper.prepare(_loc_1);
            innerAppendAll(_loc_1);
            return;
        }//end

        public void  preyClicked ()
        {
            this.onAccept(null);
            return;
        }//end

        protected void  onAccept (Event event )
        {
            PreyEscapedDialog _loc_2 =null ;
            PreyUtil.logGameActionStats("active_building", this.m_preyData.groupId, "prey_npc_click", this.m_preyData);
            if (!PreyManager.isUsingResource(this.m_preyData.groupId) || PreyManager.getNumUsableCops(this.m_preyData.groupId) >= this.m_preyData.getRequiredHunters())
            {
                this.capturePrey();
            }
            else
            {
                this.m_npc.hideSlidePick();
                this.m_npc.playActionCallback = null;
                this.m_npc.slidePick = null;
                _loc_2 = new PreyEscapedDialog(this.m_preyData.groupId, this.m_preyData.id, false, this.buyCaptureBandit);
                UI.displayPopup(_loc_2);
            }
            return;
        }//end

        protected void  capturePrey (boolean param1 =false )
        {
            Array _loc_3 =null ;
            int _loc_4 =0;
            HunterPreyWorkers _loc_5 =null ;
            Array _loc_6 =null ;
            HunterData _loc_7 =null ;
            _loc_2 = PreyManager.createCaptureScene(this.m_preyData,this.m_npc.getPosition(),this.m_npc,param1);
            if (_loc_2)
            {
                this.closeInnerPane();
                this.m_npc.hideSlidePick();
                this.m_accepted = true;
                _loc_3 = new Array();
                if (PreyManager.isUsingResource(this.m_preyData.groupId))
                {
                    _loc_5 =(HunterPreyWorkers) Global.world.citySim.preyManager.getWorkerManagerByGroup(this.m_preyData.groupId).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET);
                    _loc_6 = _loc_2.getHuntersUsed();
                    for(int i0 = 0; i0 < _loc_6.size(); i0++)
                    {
                    		_loc_7 = _loc_6.get(i0);

                        _loc_3.push(_loc_7.getPosition());
                        _loc_7.setState(HunterData.STATE_SLEEPING);
                        _loc_7.setTimestamp(Math.floor(GlobalEngine.getTimer() / 1000));
                        _loc_5.setHunterData(_loc_7);
                    }
                }
                PreyManager.setNumPreyCaptured((PreyManager.getNumPreyCaptured(this.m_preyData.groupId) + 1), this.m_preyData.groupId);
                PreyManager.getTypesPreyCaptured(this.m_preyData.groupId).put(this.m_preyData.id,  true);
                _loc_4 = PreyManager.getPrey(this.m_preyData.groupId).indexOf(this.m_preyData.id);
                if (_loc_4 > -1)
                {
                    PreyManager.getPrey(this.m_preyData.groupId).splice(_loc_4, 1);
                }
                _loc_4 = PreyManager.getPreySpawned(this.m_preyData.groupId).indexOf(this.m_preyData.id);
                if (_loc_4 > -1)
                {
                    PreyManager.getPreySpawned(this.m_preyData.groupId).splice(_loc_4, 1);
                }
                if (param1 !=null)
                {
                    GameTransactionManager.addTransaction(new TBuyCapturePrey(this.m_preyData.groupId, this.m_preyData.id, _loc_3));
                }
                else
                {
                    GameTransactionManager.addTransaction(new TCapturePrey(this.m_preyData.groupId, this.m_preyData.id, _loc_3));
                }
            }
            return;
        }//end

        protected void  buyCaptureBandit (GenericPopupEvent event )
        {
            Object queueInfo ;
            int cashCost ;
            e = event;
            boolean killPrey ;
            if (e.button == GenericDialogView.YES)
            {
                queueInfo = Global.gameSettings().getHubQueueInfo(this.m_preyData.groupId);
                cashCost = queueInfo ? (queueInfo.get("catchPreyNowCashCost")) : (5);
                if (cashCost > 0 && Global.player.cash >= cashCost)
                {
                    this.capturePrey(true);
                    Global.player.cash = Global.player.cash - cashCost;
                }
                else
                {
                    UI.displayPopup(new GetCoinsDialog(ZLoc.t("Dialogs", "ImpulseMarketCash"), "GetCash", GenericDialogView.TYPE_GETCASH, null, true));
                    killPrey;
                }
            }
            else
            {
                killPrey;
            }
            if (killPrey)
            {
                if (this.m_npc)
                {
                    this.closeInnerPane();
                    this.m_npc.clearStates();
                    this.m_npc.animation = "static";
                    this .m_npc .getStateMachine ().addActions (new ActionNavigateRandom (this .m_npc ).setTimeout (2),new ActionTween (this .m_npc ,ActionTween .TO ,1,{0alpha }),new ActionFn (this .m_npc ,Curry .curry (void  (NPC param1 )
            {
                if (Global.world.citySim.npcManager.isNpcTracked(param1))
                {
                    Global.world.citySim.npcManager.removeWalker(param1);
                }
                else
                {
                    param1.detach();
                    param1.cleanUp();
                }
                return;
            }//end
            , this.m_npc)));
                }
                this.m_npc = null;
            }
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
                this.m_followMovement = true;
                Z_TweenLite.to(m_innerSprite, 0.5, {x:m_offXPos, onComplete:this.panelClosed});
            }
            return;
        }//end

        public boolean  slideVisible ()
        {
            return this.m_slideVisible;
        }//end

        protected void  panelClosed ()
        {
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

    }



