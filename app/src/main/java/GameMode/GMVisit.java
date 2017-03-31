package GameMode;

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
import Display.*;
import Engine.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.crew.*;
//import flash.events.*;

    public class GMVisit extends GMPlay
    {
        protected String m_hostId ;

        public  GMVisit (String param1 )
        {
            Global.ui.hideExpandedMainMenu();
            this.m_hostId = param1;
            setVisit(this.m_hostId);
            m_uiMode = UIEvent.CURSOR;
            return;
        }//end

         protected int  getSelectableTypes ()
        {
            return Constants.WORLDOBJECT_ALL;
        }//end

         protected boolean  canBeClicked (MouseEvent event )
        {
            return m_highlightedObject != null && m_highlightedObject.isVisitorInteractable();
        }//end

         protected void  updateCursor ()
        {
            if (m_currentCursor != null)
            {
                if (m_cursorId > 0)
                {
                    UI.removeCursor(m_cursorId);
                    m_cursorId = 0;
                }
                m_currentCursor = null;
            }
            return;
        }//end

         protected void  handleClick (MouseEvent event )
        {
            GameObject _loc_2 =null ;
            boolean _loc_3 =false ;
            if (this.canBeClicked(event) && m_highlightedObject && m_highlightedObject.isAttached())
            {
                _loc_2 = m_highlightedObject;
                if (_loc_2.unlimitedVisits)
                {
                    RollCallManager.debugSample("GMVisitHandleClick", "unlimitedVisits");
                    _loc_3 = true;
                    if (m_highlightedObject && m_highlightedObject instanceof IMechanicUser)
                    {
                        _loc_3 = !MechanicManager.getInstance().handleAction(m_highlightedObject as IMechanicUser, Global.getClassName(this), [m_highlightedObject.VISIT_PLAY_ACTION]);
                    }
                    if (_loc_2 instanceof IMechanicPrePlayAction && !_loc_3)
                    {
                        ((IMechanicPrePlayAction)_loc_2).onPrePlayTrack();
                    }
                    if (m_highlightedObject && _loc_3)
                    {
                        m_highlightedObject.onVisitPlayAction();
                    }
                }
                else
                {
                    if (this.canInteractWithNeighborObject(event))
                    {
                        if (!_loc_2.areVisitorInteractionsExhausted)
                        {
                            if (!_loc_2.doesVisitActionCostEnergy)
                            {
                                this.invokeVisitPlayAction(_loc_2);
                            }
                            else if (Global.player.useVisitorEnergy(1, this.m_hostId))
                            {
                                this.invokeVisitPlayAction(_loc_2);
                            }
                            else
                            {
                                m_highlightedObject.displayStatus(ZLoc.t("Main", "NoMoreWork"));
                                if (Global.world.isOwnerCitySam)
                                {
                                    Global.world.addGameMode(new GMVisitBuy(this.m_hostId));
                                }
                            }
                        }
                        else if (m_highlightedObject instanceof Business || m_highlightedObject instanceof Mall)
                        {
                            m_highlightedObject.displayStatus(ZLoc.t("Main", "AlreadySentTourBus"));
                        }
                    }
                    if (_loc_2 && _loc_2.isAttached() == false)
                    {
                        dehighlightObject();
                    }
                }
            }
            else
            {
                super.handleClick(event);
            }
            return;
        }//end

        private void  invokeVisitPlayAction (GameObject param1 )
        {
            IMechanicUser _loc_2 =null ;
            if (param1 instanceof IMechanicUser)
            {
                _loc_2 =(IMechanicUser) param1;
            }
            if (param1 !=null)
            {
                param1.onVisitPlayAction();
            }
            return;
        }//end

        protected boolean  canInteractWithNeighborObject (MouseEvent event )
        {
            return m_highlightedObject && this.canBeClicked(event) && m_highlightedObject.isAttached() == true;
        }//end

    }



