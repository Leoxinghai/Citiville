package Classes;

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

import Classes.effects.*;
import Classes.util.*;
import Display.aswingui.*;
import Engine.*;
import GameMode.*;
import Modules.quest.Helpers.*;
import Modules.socialinventory.*;
import Modules.socialinventory.GameMode.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.geom.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class Wilderness extends Decoration
    {
        private  String WILDERNESS ="wilderness";
        private  int ACTIONBAR_OFFSET_X =0;
        private  int ACTIONBAR_OFFSET_Y =-15;
        protected  String DEFAULT_PICKUP_ANIM ="static";
        protected Array m_pickupAnims ;
        protected boolean m_isBeingMoved =false ;
        private double m_heldEnergy =0;
        private static  int STATUS_Y_OFFSET =22;

        public  Wilderness (String param1)
        {
            XML _loc_2 =null ;
            super(param1);
            m_objectType = WorldObjectTypes.WILDERNESS;
            setState(STATE_STATIC);
            m_typeName = this.WILDERNESS;
            m_ownable = false;
            if (m_item.xml.pickupAnim)
            {
                this.m_pickupAnims = new Array();
                for(int i0 = 0; i0 < m_item.xml.pickupAnim.size(); i0++)
                {
                		_loc_2 = m_item.xml.pickupAnim.get(i0);

                    this.m_pickupAnims.push(new String(_loc_2.@name));
                }
            }
            else
            {
                this.m_pickupAnims = .get(this.DEFAULT_PICKUP_ANIM);
            }
            return;
        }//end

        public String  getPickupAnim ()
        {
            return MathUtil.randomElement(this.m_pickupAnims);
        }//end

         public void  onPlayAction ()
        {
            if (m_visitReplayLock > 0)
            {
                return;
            }
            m_actionMode = PLAY_ACTION;
            if (!Global.isVisiting() && Global.world.territory.pointInTerritory(m_position.x, m_position.y))
            {
                if (Global.player.checkEnergy(-m_item.clearEnergyCost))
                {
                    Global.world.citySim.pickupManager.enqueue("NPC_lumberjack", this);
                }
                else
                {
                    displayStatus(ZLoc.t("Main", "NotEnoughEnergyRed"));
                }
            }
            return;
        }//end

         public void  onVisitPlayAction ()
        {
            Global.world.citySim.pickupManager.enqueue("NPC_lumberjack", this);
            _loc_2 = m_numVisitorInteractions+1;
            m_numVisitorInteractions = _loc_2;
            trackVisitAction(TrackedActionType.REMOVE_WILDERNESS);
            return;
        }//end

         public void  clearFromMap ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            if (Global.isVisiting())
            {
                _loc_1 = Global.gameSettings().getInt("friendVisitWildernessRepGain", 1);
                _loc_2 = Global.gameSettings().getNumber("friendHelpDefaultCoinReward", 10);
                finalizeAndAwardVisitorHelp(VisitorHelpType.WILDERNESS_CLEAR, _loc_1, _loc_2);
            }
            super.clearFromMap();
            return;
        }//end

         public double  onVisitReplayAction (TRedeemVisitorHelpAction param1 )
        {
            _loc_2 = super.onVisitReplayAction(param1);
            if (this.isLocked())
            {
                _loc_2 = 0;
            }
            else
            {
                m_actionMode = VISIT_REPLAY_ACTION;
                GameTransactionManager.addTransaction(param1);
                this..getProgressBarEndFunction()();
                m_actionMode = NO_ACTION;
            }
            return _loc_2;
        }//end

         public boolean  isVisitorInteractable ()
        {
            return true;
        }//end

         protected Point  displayStatusStartPos ()
        {
            _loc_1 = IsoMath.tilePosToPixelPos(m_position.x ,m_position.y );
            if (m_displayObject)
            {
                _loc_1.y = _loc_1.y - STATUS_Y_OFFSET;
            }
            return IsoMath.viewportToStage(_loc_1);
        }//end

         public Class  getCursor ()
        {
            if (Global.world.getTopGameMode() instanceof GMEditMove || Global.world.getTopGameMode() instanceof GMObjectMove)
            {
                return EmbeddedArt.hud_act_move;
            }
            if (Global.player.checkEnergy(-m_item.clearEnergyCost, false) || Global.isVisiting())
            {
                return EmbeddedArt.hud_act_clearWilderness;
            }
            return null;
        }//end

         public Object  getCustomCursor ()
        {
            ASFont _loc_3 =null ;
            GlowFilter _loc_4 =null ;
            MarginBackground _loc_5 =null ;
            if (!m_customCursor)
            {
                m_customCursor = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                m_customCursorLabel = new JLabel("");
                m_customCursorHolder = new Sprite();
                Global.ui.addChild(m_customCursorHolder);
                m_customCursorWindow = new JWindow(m_customCursorHolder);
                _loc_3 = ASwingHelper.getBoldFont(16);
                _loc_4 = new GlowFilter(0, 1, 1.2, 1.2, 20, BitmapFilterQuality.HIGH);
                m_customCursorLabel.setForeground(new ASColor(16777215));
                m_customCursorLabel.setFont(_loc_3);
                m_customCursorLabel.setTextFilters(.get(_loc_4));
            }
            DisplayObject _loc_1 =null ;
            _loc_2 =Global.world.getTopGameMode ();
            if ((_loc_2 instanceof GMEditMove || _loc_2 instanceof GMObjectMove) && this.isMoveLocked)
            {
                _loc_1 =(DisplayObject) new EmbeddedArt.hud_act_move();
                m_customCursorLabel.setText(m_item.unlockMovementEnergyCost.toString());
            }
            else if (_loc_2 instanceof GMEditMove || _loc_2 instanceof GMObjectMove && !this.isMoveLocked)
            {
                _loc_1 =(DisplayObject) new EmbeddedArt.hud_act_move();
                m_customCursorLabel.setText("");
            }
            else if (_loc_2 instanceof GMPlay || _loc_2 instanceof GMEditSell)
            {
                _loc_1 =(DisplayObject) new EmbeddedArt.hud_act_clearWilderness();
                m_customCursorLabel.setText(m_item.clearEnergyCost.toString());
            }
            else
            {
                return null;
            }
            if (_loc_1)
            {
                _loc_5 = new MarginBackground(_loc_1, new Insets(0, 0, 0, 0));
                m_customCursor.setBackgroundDecorator(_loc_5);
                m_customCursor.setPreferredSize(new IntDimension(_loc_1.width, _loc_1.height));
                m_customCursor.setMinimumSize(new IntDimension(_loc_1.width, _loc_1.height));
                m_customCursor.setMaximumSize(new IntDimension(_loc_1.width, _loc_1.height));
            }
            m_customCursor.append(m_customCursorLabel);
            m_customCursorWindow.setContentPane(m_customCursor);
            ASwingHelper.prepare(m_customCursorWindow);
            m_customCursorWindow.show();
            return m_customCursorWindow;
        }//end

         public boolean  isSellable ()
        {
            return true;
        }//end

         public void  sell ()
        {
            this.onPlayAction();
            return;
        }//end

         public boolean  canBeDragged ()
        {
            if (Global.world.getTopGameMode() instanceof GMEditRotate)
            {
                return false;
            }
            return m_actionMode != PLAY_ACTION && m_actionMode != VISIT_PLAY_ACTION;
        }//end

         public void  onMenuClick (MouseEvent event )
        {
            return;
        }//end

         public boolean  usingTileHighlight ()
        {
            if (Global.world.getTopGameMode() instanceof GMEditRotate || Global.world.getTopGameMode() instanceof GMRemodel)
            {
                return false;
            }
            return true;
        }//end

         public boolean  overrideCursor ()
        {
            boolean _loc_1 =true ;
            return _loc_1;
        }//end

         public void  onMouseOver (MouseEvent event )
        {
            super.onMouseOver(event);
            return;
        }//end

         public void  onMouseOut ()
        {
            super.onMouseOut();
            return;
        }//end

         public String  getToolTipHeader ()
        {
            return null;
        }//end

         public String  getToolTipAction ()
        {
            boolean _loc_3 =false ;
            Object _loc_4 =null ;
            String _loc_1 ="";
            _loc_2 =Global.world.getTopGameMode ();
            if (_loc_2 instanceof GMEditMove)
            {
                if (m_isMoveLocked)
                {
                    _loc_3 = !Global.world.isUnlockingMove && Global.player.checkEnergy(-m_item.unlockMovementEnergyCost, false);
                    if (_loc_3)
                    {
                        _loc_1 = ZLoc.t("Main", "UprootToMove");
                    }
                    else if (Global.world.isUnlockingMove)
                    {
                        _loc_1 = ZLoc.t("Main", "UprootWait");
                    }
                    else
                    {
                        _loc_1 = ZLoc.t("Main", "NeedEnergy", {amount:m_item.unlockMovementEnergyCost});
                    }
                }
            }
            else if (_loc_2 instanceof GMEditSocialInventory)
            {
                _loc_4 = getDataForMechanic(SocialInventoryManager.MECHANIC_DATA);
                if (_loc_4 && "heart" in _loc_4)
                {
                    _loc_1 = ZLoc.t("Main", "RemoveHeart");
                }
                else
                {
                    _loc_1 = ZLoc.t("Main", "PlaceHeart");
                }
            }
            else if (_loc_2 instanceof GMRemodel)
            {
            }
            else if (m_item)
            {
                if (Global.player.checkEnergy(-m_item.clearEnergyCost, false))
                {
                    _loc_1 = ZLoc.t("Main", "WildernessToolTip");
                }
                else
                {
                    _loc_1 = ZLoc.t("Main", "NeedEnergy", {amount:m_item.clearEnergyCost});
                }
            }
            return _loc_1;
        }//end

         public void  isMoveLocked (boolean param1 )
        {
            m_isMoveLocked = param1;
            removeAnimatedEffects();
            if (m_isMoveLocked)
            {
                addAnimatedEffect(EffectType.DARKEN);
            }
            return;
        }//end

         public String  getActionText ()
        {
            return ZLoc.t("Main", "ClearingWilderness");
        }//end

         public void  unlockMovement ()
        {
            this.m_isBeingMoved = true;
            super.unlockMovement();
            return;
        }//end

         public Function  getProgressBarStartFunction ()
        {
            return boolean  ()
            {
                if (m_isBeingMoved)
                {
                    if (!Global.player.checkEnergy(-m_item.unlockMovementEnergyCost))
                    {
                        displayStatus(ZLoc.t("Main", "NotEnoughEnergyRed"));
                        return false;
                    }
                    doEnergyChanges(-m_item.unlockMovementEnergyCost, new Array("energy", "expenditures", "unlock_movement", m_item.name));
                    Global.player.heldEnergy = Global.player.heldEnergy + m_item.unlockMovementEnergyCost;
                    m_heldEnergy = m_item.unlockMovementEnergyCost;
                }
                else if (!Global.isVisiting())
                {
                    if (m_actionMode != VISIT_REPLAY_ACTION)
                    {
                        if (m_actionMode == PLAY_ACTION && !Global.player.checkEnergy(-m_item.clearEnergyCost))
                        {
                            displayStatus(ZLoc.t("Main", "NotEnoughEnergyRed"));
                            return false;
                        }
                        doEnergyChanges(-m_item.clearEnergyCost, new Array("energy", "expenditures", "remove_widerness", m_item.name));
                        Global.player.heldEnergy = Global.player.heldEnergy + m_item.clearEnergyCost;
                        m_heldEnergy = m_item.clearEnergyCost;
                    }
                }
                else
                {
                    return true;
                }
                return true;
            }//end
            ;
        }//end

         public Function  getProgressBarEndFunction ()
        {
            return void  ()
            {
                Global.player.heldEnergy = Global.player.heldEnergy - m_heldEnergy;
                if (m_isBeingMoved)
                {
                    m_isBeingMoved = false;
                }
                else
                {
                    clearFromMap();
                }
                return;
            }//end
            ;
        }//end

         public Function  getProgressBarCancelFunction ()
        {
            Global.player.heldEnergy = Global.player.heldEnergy - this.m_heldEnergy;
            return super.getProgressBarCancelFunction();
        }//end

         public Point  getProgressBarOffset ()
        {
            return new Point(this.ACTIONBAR_OFFSET_X, this.ACTIONBAR_OFFSET_Y);
        }//end

         public String  getVisitReplayEquivalentActionString ()
        {
            return "clear";
        }//end

    }



