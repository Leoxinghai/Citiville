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

import Display.DialogUI.*;
import Display.HunterAndPreyUI.HunterCellStates.*;
import Display.ValentineUI.*;
import Display.aswingui.*;
import Events.*;
import Modules.bandits.*;
import Modules.workers.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.ext.*;

    public class HunterCell extends CrewCell
    {
        protected GridList m_gridList ;
        protected int m_index ;
        protected HunterData m_officer ;
        protected int m_color ;
        protected String m_officerState ;
        public static  String STATE_READY ="state_ready";
        public static  String STATE_PATROLLING ="state_patrolling";
        public static  String STATE_SLEEPING ="state_sleeping";
        public static  String STATE_EMPTY ="state_empty";
        public static  String STATE_LOCKED ="state_locked";
        public static  String STATE_RETIRED ="state_retired";

        public  HunterCell (HunterCellFactory param1 ,int param2 ,LayoutManager param3 =null )
        {
            this.m_color = param2;
            super(param1, param3);
            return;
        }//end

         public void  setGridListCellStatus (GridList param1 ,boolean param2 ,int param3 )
        {
            if (param1 !=null)
            {
                this.m_gridList = param1;
                this.m_index = param3;
                param1.setTileWidth(595);
                param1.setTileHeight(55);
            }
            return;
        }//end

         public void  buildCell ()
        {
            this.removeAll();
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            _loc_2 = PreyManager.getHunterPreyMode(HunterDialog.groupId);
            switch(this.m_officerState)
            {
                case STATE_PATROLLING:
                {
                    break;
                }
                case STATE_READY:
                {
                    break;
                }
                case STATE_SLEEPING:
                {
                    break;
                }
                case STATE_EMPTY:
                {
                    break;
                }
                case STATE_LOCKED:
                {
                    break;
                }
                default:
                {
                    break;
                }
            }
            if (this.m_officerState == STATE_EMPTY)
            {
            }
            else
            {
            }
            this.append(_loc_1);
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            if (param1 == "empty")
            {
                this.m_officerState = STATE_EMPTY;
            }
            else if (param1 == "locked")
            {
                this.m_officerState = STATE_LOCKED;
            }
            else
            {
                this.m_officer = param1;
                this.m_officerState = this.m_officer.getState();
            }
            if (!this.hasEventListener(UIEvent.CHANGE_CREW_STATE))
            {
                this.addEventListener(UIEvent.CHANGE_CREW_STATE, this.rebuildCell, false, 0, true);
            }
            this.buildCell();
            ASwingHelper.prepare(this);
            dispatchEvent(new Event(MakerPanel.PREPARE, true));
            return;
        }//end

        public void  forceRebuildCell (String param1 ,boolean param2 =false ,boolean param3 =false )
        {
            this.m_officerState = param1;
            if (param2)
            {
                this.m_officer.setState(HunterData.STATE_PATROLLING);
            }
            if (param3)
            {
                this.m_officer.setTimestamp(Math.floor(GlobalEngine.getTimer() / 1000));
            }
            this.buildCell();
            dispatchEvent(new Event(MakerPanel.PREPARE, true));
            return;
        }//end

        protected void  rebuildCell (UIEvent event )
        {
            HunterData _loc_5 =null ;
            this.m_officerState = event.label;
            _loc_2 = (HunterPreyWorkers)Global.world.citySim.preyManager.getWorkerManagerByGroup(HunterDialog.groupId).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET)
            _loc_3 = _loc_2.getAllCopData();
            int _loc_4 =0;
            while (_loc_4 < _loc_3.length())
            {

                _loc_5 = _loc_3.get(_loc_4);
                if (_loc_5.getPosition() == this.m_officer.getPosition())
                {
                    this.m_officer = _loc_5;
                }
                _loc_4++;
            }
            this.buildCell();
            ASwingHelper.prepare(this);
            dispatchEvent(new Event(MakerPanel.PREPARE, true));
            return;
        }//end

         public Component  getCellComponent ()
        {
            return this;
        }//end

    }




