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
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Engine.Managers.*;
import Events.*;

import com.xinghai.Debug;

    public class TrainStation extends MapResource
    {
        private  String TRAIN_STATION ="trainStation";
        protected  String STATE_STATIC ="static";

        public  TrainStation (String param1 )
        {
            super(param1);
            m_objectType = WorldObjectTypes.TRAIN_STATION;
            this.setState(this.STATE_STATIC);
            m_typeName = this.TRAIN_STATION;
            m_ownable = false;
            return;
        }//end

         public void  setState (String param1 )
        {
            super.setState(param1);
            this.updateArrow();
            return;
        }//end

        public void  forceUpdateArrow ()
        {
            this.updateArrow();
            return;
        }//end

         protected void  updateArrow ()
        {
            this.createStagePickEffect();
            return;
        }//end

         protected void  createStagePickEffect ()
        {
            String _loc_1 =null ;
            if (!Global.isVisiting())
            {
                if (Global.world.citySim.trainManager.isNeighborGated)
                {
                    _loc_1 = StagePickEffect.PICK_LOCK;
                }
                else
                {
                    switch(Global.world.citySim.trainManager.trainState)
                    {
                        case TrainManager.TRAIN_STATE_IDLE_AWAY:
                        {
                            _loc_1 = StagePickEffect.PICK_ATTENTION;
                            break;
                        }
                        case TrainManager.TRAIN_STATE_DEPARTING:
                        {
                            _loc_1 = Global.world.citySim.trainManager.isWaitingForTrain ? (StagePickEffect.PICK_IN_TRANSIT) : (StagePickEffect.PICK_ATTENTION);
                            break;
                        }
                        case TrainManager.TRAIN_STATE_EN_ROUTE:
                        {
                            _loc_1 = StagePickEffect.PICK_IN_TRANSIT;
                            break;
                        }
                        case TrainManager.TRAIN_STATE_AWAITING_CLEARANCE:
                        {
                            _loc_1 = StagePickEffect.PICK_TRAIN_AWAITING_CLEARANCE;
                            break;
                        }
                        default:
                        {
                            break;
                        }
                    }
                }
            }
            if (_loc_1)
            {
                if (!m_stagePickEffect)
                {
                    m_stagePickEffect =(StagePickEffect) MapResourceEffectFactory.createEffect(this, EffectType.STAGE_PICK);
                    m_stagePickEffect.setPickType(_loc_1);
                    m_stagePickEffect.float();
                }
                else
                {
                    m_stagePickEffect.setPickType(_loc_1);
                    m_stagePickEffect.reattach();
                    m_stagePickEffect.float();
                }
            }
            else
            {
                removeStagePickEffect();
            }
            return;
        }//end

         public void  cleanUp ()
        {
            removeStagePickEffect();
            super.cleanUp();
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            return;
        }//end

         public boolean  isSellable ()
        {
            return false;
        }//end

         public boolean  canBeDragged ()
        {
            return false;
        }//end

         protected void  calculateDepthIndex ()
        {
            super.calculateDepthIndex();
            m_depthIndex = m_depthIndex + 4 * 1000;
            return;
        }//end

         public void  onPlayAction ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            int _loc_3 =0;
            String _loc_4 =null ;
            CharacterAdviceDialog _loc_5 =null ;

            Debug.debug7("TrainStation.onPlayAction");

            upgradeBuildingIfPossible();

            /*
            if (!Global.isVisiting())
            {
                if (Global.world.citySim.trainManager.isNeighborGated)
                {
                    _loc_1 = Global.gameSettings().getInt("trainRequiredNeighbors", 0);
                    _loc_2 = Global.player.neighbors.length;
                    _loc_3 = Math.max(0, _loc_1 - _loc_2);
                    _loc_4 = _loc_3 > 1 ? ("TrainUI_Neighbors_plural_text") : ("TrainUI_Neighbors_singular_text");
                    _loc_5 = new CharacterAdviceDialog(ZLoc.t("Dialogs", _loc_4, {num:_loc_3}), "", GenericDialogView.TYPE_YESNO, this.onAddNeighborsDlgClosed, "TrainUI_Neighbors", null, true, 0, "TrainUI_Neighbors_button");
                    UI.displayPopup(_loc_5);
                    return;
                }
                StatsManager.sample(100, "trains", "clicked_station");
                if (Global.world.citySim.trainManager.isWaitingForTrain)
                {
                    Global.world.citySim.trainManager.showTrainStationDialog();
                }
                else
                {
                    Global.world.citySim.trainManager.showTrainScheduleCatalog();
                }
            }
            */

            return;
        }//end

        protected void  onAddNeighborsDlgClosed (GenericPopupEvent event )
        {
            if (event.button != GenericPopup.YES)
            {
                return;
            }
            FrameManager.showTray("invite.php?ref=train_station");
            return;
        }//end

         public String  getToolTipStatus ()
        {
            String _loc_1 ="";
            if (!Global.isVisiting())
            {
                if (Global.world.citySim.trainManager.isWaitingForTrain)
                {
                    _loc_1 = ZLoc.t("Dialogs", "TrainUI_TrainReturnTime", {time:GameUtil.formatMinutesSeconds(Global.world.citySim.trainManager.trainArrivalTime)});
                }
                else
                {
                    _loc_1 = ZLoc.t("Dialogs", "TrainUI_TrainReady");
                }
            }

            Debug.debug7("TrainStation.getToolTipStatus." + _loc_1);
            _loc_1 ="??????????100??";
            return _loc_1;
        }//end

         public String  getToolTipAction ()
        {
            if (!Global.world.citySim.trainManager.isOrderAllowed)
            {
                return null;
            }
            String _loc_1 =null ;
            if (!Global.world.citySim.trainManager.isWaitingForTrain)
            {
                _loc_1 = ZLoc.t("Dialogs", "TrainUI_TrainReadyAction");
            }
            return _loc_1;
        }//end

         public boolean  isHighlightable ()
        {
            return super.isHighlightable && !Global.isVisiting() && !Global.world.citySim.trainManager.isNeighborGated;
        }//end

        public void  onNeighborAdded ()
        {
            this.updateArrow();
            return;
        }//end

    }



