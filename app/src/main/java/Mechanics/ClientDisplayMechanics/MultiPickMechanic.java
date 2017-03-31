package Mechanics.ClientDisplayMechanics;

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
import Engine.Events.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.utils.*;

    public class MultiPickMechanic implements IClientGameMechanic, IEdgeModifier
    {
        protected MechanicMapResource m_owner =null ;
        protected MechanicConfigData m_config =null ;
        protected MultiPickEffect m_multipick =null ;
        protected boolean m_isDirty =false ;
        protected String m_gameMode =null ;
        protected Dictionary m_pickToMechanic =null ;
        protected String m_dirtyPick =null ;
        protected MechanicConfigData m_dirtyMechanic =null ;
public static  String MECHANIC_PICKS ="picks";

        public  MultiPickMechanic ()
        {
            return;
        }//end

        public void  updateDisplayObject (double param1 )
        {
            String _loc_4 =null ;
            boolean _loc_2 =false ;
            _loc_3 =(IMultiPickOwner) this.m_owner;
            do
            {

                if (this.m_isDirty)
                {
                    _loc_2 = true;
                    break;
                }
                _loc_4 = getQualifiedClassName(Global.world.getTopGameMode());
                if (this.m_gameMode != _loc_4 && !Global.isTransitioningWorld)
                {
                    _loc_2 = true;
                    break;
                }
                if (MechanicManager.getForceDisplayRefresh(this.m_config.type, this.m_owner.getId()))
                {
                    MechanicManager.setForceDisplayRefresh(this.m_config.type, this.m_owner.getId(), false);
                    _loc_2 = true;
                    break;
                }
            }while (0)
            if (_loc_2)
            {
                this.refreshGameEventMechanicPicks();
                this.m_gameMode = _loc_4;
                if (!_loc_3 || _loc_3.clearDirtyOnUpdate)
                {
                    this.m_isDirty = false;
                }
            }
            this.m_multipick.reattach();
            return;
        }//end

        public void  detachDisplayObject ()
        {
            if (this.m_multipick)
            {
                this.m_multipick.cleanUp();
                this.m_multipick = null;
            }
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            XML _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            if (this.m_dirtyMechanic)
            {
                this.m_dirtyMechanic.resetMechanicToXMLConfig();
                this.m_dirtyMechanic = null;
            }
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = null;
            this.m_pickToMechanic = null;
            this.m_dirtyPick = null;
            this.m_dirtyMechanic = null;
            this.m_multipick = null;
            this.m_gameMode = getQualifiedClassName(Global.world.getTopGameMode());
            if (this.m_owner)
            {
                this.m_config = param2;
                this.m_pickToMechanic = new Dictionary();
                this.m_multipick = new MultiPickEffect(this.m_owner);
                this.m_multipick.setOption(MultiPickEffect.OPTION_ACTION_ON_SELECT, true);
                this.m_multipick.addEventListener(LoaderEvent.LOADED, this.onMultiPickLoaded, false, 0, true);
                if (param2.rawXMLConfig.hasOwnProperty(MECHANIC_PICKS))
                {
                    for(int i0 = 0; i0 < param2.rawXMLConfig.picks.pick.size(); i0++)
                    {
                    		_loc_3 = param2.rawXMLConfig.picks.pick.get(i0);

                        _loc_4 = String(_loc_3.@name);
                        _loc_5 = String(_loc_3.@type);
                        _loc_6 = String(_loc_3.@modifier);
                        if (!_loc_6.length())
                        {
                            _loc_6 = null;
                        }
                        this.m_multipick.create(_loc_4, _loc_5, _loc_6);
                        this.m_multipick.viewable(_loc_4, false);
                    }
                }
                this.refreshGameEventMechanicPicks();
                this.m_multipick.reattach();
                this.m_owner.addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.onMechanicDataChanged, false, 0, true);
                this.m_multipick.addEventListener(MouseEvent.MOUSE_UP, this.onMultiPickPickChanged, false, 0, true);
                this.m_multipick.addEventListener(MouseEvent.MOUSE_OVER, this.onMultiPickHighlightChanged, false, 0, true);
                this.m_owner.addEventListener(GameObjectEvent.STATE_CHANGE, this.onStateChanged, false, 0, true);
            }
            return;
        }//end

        protected void  onMultiPickLoaded (LoaderEvent event )
        {
            if (this.m_dirtyPick)
            {
                this.m_multipick.active = this.m_dirtyPick;
            }
            this.m_multipick.removeEventListener(LoaderEvent.LOADED, this.onMultiPickLoaded);
            return;
        }//end

        protected void  onMechanicDataChanged (GenericObjectEvent event )
        {
            if (this.m_config.type != event.obj)
            {
                this.m_isDirty = true;
            }
            return;
        }//end

        protected void  onStateChanged (GameObjectEvent event )
        {
            if (this.m_multipick)
            {
                this.m_multipick.reattach();
            }
            return;
        }//end

        protected void  onMultiPickPickChanged (MouseEvent event =null )
        {
            MechanicConfigData _loc_3 =null ;
            _loc_2 = this.m_multipick.active ;
            if (this.m_multipick && this.m_multipick.exists(_loc_2))
            {
                _loc_3 =(MechanicConfigData) this.m_pickToMechanic.get(_loc_2);
                if (this.m_dirtyMechanic)
                {
                    this.m_dirtyMechanic.resetMechanicToXMLConfig();
                    this.m_dirtyMechanic = null;
                }
                if (_loc_3)
                {
                    _loc_3.priority = int.MAX_VALUE;
                }
                this.m_dirtyMechanic = _loc_3;
                this.m_dirtyPick = _loc_2;
            }
            return;
        }//end

        protected void  onMultiPickHighlightChanged (MouseEvent event =null )
        {
            MechanicConfigData _loc_3 =null ;
            _loc_2 = this.m_multipick.active ;
            if (this.m_multipick && this.m_multipick.exists(_loc_2))
            {
                if (this.m_dirtyPick == _loc_2)
                {
                    return;
                }
                _loc_3 =(MechanicConfigData) this.m_pickToMechanic.get(_loc_2);
                if (this.m_dirtyMechanic)
                {
                    this.m_dirtyMechanic.resetMechanicToXMLConfig();
                    this.m_dirtyMechanic = null;
                }
                if (_loc_3)
                {
                    _loc_3.priority = int.MAX_VALUE;
                }
                this.m_dirtyMechanic = _loc_3;
                this.m_dirtyPick = _loc_2;
            }
            return;
        }//end

        protected void  refreshGameEventMechanicPicks (String param1)
        {
            int _loc_2 =0;
            int _loc_3 =0;
            String _loc_4 =null ;
            String _loc_5 =null ;
            Dictionary _loc_6 =null ;
            Dictionary _loc_7 =null ;
            String _loc_8 =null ;
            MechanicConfigData _loc_9 =null ;
            String _loc_10 =null ;
            IActionGameMechanic _loc_11 =null ;
            String _loc_12 =null ;
            Array _loc_13 =null ;
            String _loc_14 =null ;
            boolean _loc_15 =false ;
            boolean _loc_16 =false ;
            int _loc_17 =0;
            int _loc_18 =0;
            if (this.m_owner)
            {
                if (!param1 || param1 && !param1.length())
                {
                    _loc_8 = getQualifiedClassName(Global.world.getTopGameMode());
                    param1 = _loc_8.split("::").pop();
                }
                _loc_2 = 0;
                _loc_3 = 0;
                _loc_4 = null;
                _loc_5 = null;
                _loc_6 = this.m_owner.actionMechanics;
                _loc_7 =(Dictionary) _loc_6.get(param1);
                if (this.m_multipick)
                {
                    this.m_multipick.hideAll();
                }
                if (_loc_7)
                {
                    for(int i0 = 0; i0 < _loc_7.size(); i0++)
                    {
                    		_loc_9 = _loc_7.get(i0);

                        _loc_10 = _loc_9.type;
                        _loc_11 =(IActionGameMechanic) MechanicManager.getInstance().getMechanicInstance(this.m_owner, _loc_10, param1);
                        if (_loc_11 instanceof IMultiPickSupporter)
                        {
                            _loc_12 = ((IMultiPickSupporter)_loc_11).getPick();
                            _loc_13 = ((IMultiPickSupporter)_loc_11).getPicksToHide();
                            if (_loc_13 != null)
                            {
                                for(int i0 = 0; i0 < _loc_13.size(); i0++)
                                {
                                		_loc_14 = _loc_13.get(i0);

                                    if (this.m_multipick.exists(_loc_14))
                                    {
                                        this.m_multipick.viewable(_loc_14, false);
                                    }
                                }
                            }
                            if (this.m_multipick.exists(_loc_12))
                            {
                                _loc_15 = this.m_multipick.isViewable(_loc_12);
                                _loc_16 = _loc_11.hasOverrideForGameAction(param1);
                                _loc_17 = 0;
                                this.m_multipick.viewable(_loc_12, _loc_16);
                                this.m_pickToMechanic.put(_loc_12,  _loc_9);
                                if (!_loc_15 && _loc_16)
                                {
                                    _loc_17 = int(_loc_9.rawXMLConfig.@priority);
                                    if (_loc_17 > _loc_3)
                                    {
                                        _loc_3 = _loc_17;
                                        _loc_4 = _loc_12;
                                    }
                                }
                                if (_loc_16)
                                {
                                    _loc_17 = int(_loc_9.rawXMLConfig.@priority);
                                    if (_loc_17 > _loc_2)
                                    {
                                        _loc_2 = _loc_17;
                                        _loc_5 = _loc_12;
                                    }
                                }
                            }
                        }
                    }
                }
                if (this.m_dirtyMechanic && this.m_dirtyPick && this.m_multipick.isViewable(this.m_dirtyPick))
                {
                    _loc_5 = this.m_dirtyPick;
                    _loc_18 = int(this.m_dirtyMechanic.rawXMLConfig.@priority);
                    if (_loc_4 && _loc_3 > _loc_18)
                    {
                        _loc_5 = _loc_4;
                    }
                }
                if (_loc_5 != this.m_dirtyPick && this.m_dirtyMechanic)
                {
                    this.m_dirtyMechanic.resetMechanicToXMLConfig();
                    this.m_dirtyMechanic = null;
                }
                this.m_dirtyPick = _loc_5;
                this.m_multipick.active = _loc_5;
            }
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        public boolean  isPixelInside (Point param1 )
        {
            Rectangle _loc_3 =null ;
            boolean _loc_2 =false ;
            if (this.m_multipick && this.m_multipick.hasVisiblePicks())
            {
                _loc_3 = this.m_multipick.getRect(this.m_owner.displayObject.parent);
                _loc_2 = _loc_3.containsPoint(param1);
            }
            return _loc_2;
        }//end

        public int  getFloatOffset ()
        {
            int _loc_1 =0;
            if (this.m_multipick)
            {
                _loc_1 = this.m_multipick.getFloatOffset();
            }
            return _loc_1;
        }//end

    }




