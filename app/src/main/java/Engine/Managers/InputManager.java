package Engine.Managers;

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

import Engine.Interfaces.*;
//import flash.events.*;
//import flash.external.*;

    public class InputManager
    {
public static Array m_inputHandlers =new Array ();
public static int m_disableMutex =0;

        public  InputManager ()
        {
            return;
        }//end

        public static void  initializeHandlers (IEventDispatcher param1 )
        {
            listenObject = param1;
            listenObject.addEventListener(MouseEvent.MOUSE_UP, onMouseUp);
            listenObject.addEventListener(MouseEvent.MOUSE_DOWN, onMouseDown);
            listenObject.addEventListener(MouseEvent.MOUSE_WHEEL, onMouseWheel);
            listenObject.addEventListener(MouseEvent.DOUBLE_CLICK, onMouseDoubleClick);
            listenObject.addEventListener(MouseEvent.MOUSE_MOVE, onMouseMove);
            listenObject.addEventListener(KeyboardEvent.KEY_DOWN, onKeyDown);
            listenObject.addEventListener(KeyboardEvent.KEY_UP, onKeyUp);
            try
            {
                if (ExternalInterface.available)
                {
                    ExternalInterface.addCallback("onMouseWheel", onMouseWheel);
                }
            }
            catch (e:Error)
            {
            }
            return;
        }//end

        public static void  enableInput ()
        {
            _loc_2 = m_disableMutex-1;
            m_disableMutex = _loc_2;
            m_disableMutex = Math.max(m_disableMutex, 0);
            return;
        }//end

        public static void  disableInput ()
        {
            _loc_2 = m_disableMutex+1;
            m_disableMutex = _loc_2;
            return;
        }//end

        public static boolean  isInputEnabled ()
        {
            return m_disableMutex == 0;
        }//end

        public static void  addHandler (IInputHandler param1 ,int param2)
        {
            m_inputHandlers.splice(param2, 0, param1);
            return;
        }//end

        public static void  removeHandler (IInputHandler param1 )
        {
            _loc_2 = m_inputHandlers.indexOf(param1);
            if (_loc_2 >= 0 && _loc_2 < m_inputHandlers.length())
            {
                m_inputHandlers.splice(_loc_2, 1);
            }
            return;
        }//end

        public static void  onMouseDown (MouseEvent event )
        {
            int _loc_2 =0;
            IInputHandler _loc_3 =null ;
            if (isInputEnabled())
            {
                _loc_2 = 0;
                while (_loc_2 < m_inputHandlers.length())
                {

                    _loc_3 =(IInputHandler) m_inputHandlers.get(_loc_2);
                    if (_loc_3 != null && _loc_3.onMouseDown(event))
                    {
                        break;
                    }
                    _loc_2++;
                }
            }
            return;
        }//end

        public static void  onMouseUp (MouseEvent event )
        {
            int _loc_2 =0;
            IInputHandler _loc_3 =null ;
            if (isInputEnabled())
            {
                _loc_2 = 0;
                while (_loc_2 < m_inputHandlers.length())
                {

                    _loc_3 =(IInputHandler) m_inputHandlers.get(_loc_2);
                    if (_loc_3 != null && _loc_3.onMouseUp(event))
                    {
                        break;
                    }
                    _loc_2++;
                }
            }
            return;
        }//end

        public static void  onMouseOut (MouseEvent event )
        {
            int _loc_2 =0;
            IInputHandler _loc_3 =null ;
            if (isInputEnabled())
            {
                _loc_2 = 0;
                while (_loc_2 < m_inputHandlers.length())
                {

                    _loc_3 =(IInputHandler) m_inputHandlers.get(_loc_2);
                    if (_loc_3 != null && _loc_3.onMouseOut(event))
                    {
                        break;
                    }
                    _loc_2++;
                }
            }
            return;
        }//end

        public static void  onMouseMove (MouseEvent event )
        {
            int _loc_2 =0;
            IInputHandler _loc_3 =null ;
            if (isInputEnabled())
            {
                _loc_2 = 0;
                while (_loc_2 < m_inputHandlers.length())
                {

                    _loc_3 =(IInputHandler) m_inputHandlers.get(_loc_2);
                    if (_loc_3 != null && _loc_3.onMouseMove(event))
                    {
                        break;
                    }
                    _loc_2++;
                }
            }
            return;
        }//end

        public static void  onMouseDoubleClick (MouseEvent event )
        {
            int _loc_2 =0;
            IInputHandler _loc_3 =null ;
            if (isInputEnabled())
            {
                _loc_2 = 0;
                while (_loc_2 < m_inputHandlers.length())
                {

                    _loc_3 =(IInputHandler) m_inputHandlers.get(_loc_2);
                    if (_loc_3 != null && _loc_3.onMouseDoubleClick(event))
                    {
                        break;
                    }
                    _loc_2++;
                }
            }
            return;
        }//end

        public static void  onMouseWheel (MouseEvent event )
        {
            int _loc_2 =0;
            IInputHandler _loc_3 =null ;
            if (isInputEnabled())
            {
                _loc_2 = 0;
                while (_loc_2 < m_inputHandlers.length())
                {

                    _loc_3 =(IInputHandler) m_inputHandlers.get(_loc_2);
                    if (_loc_3 != null && _loc_3.onMouseWheel(event))
                    {
                        break;
                    }
                    _loc_2++;
                }
            }
            return;
        }//end

        public static void  onKeyDown (KeyboardEvent event )
        {
            int _loc_2 =0;
            IInputHandler _loc_3 =null ;
            if (isInputEnabled())
            {
                _loc_2 = 0;
                while (_loc_2 < m_inputHandlers.length())
                {

                    _loc_3 =(IInputHandler) m_inputHandlers.get(_loc_2);
                    if (_loc_3 != null && _loc_3.onKeyDown(event))
                    {
                        event.stopPropagation();
                        break;
                    }
                    _loc_2++;
                }
            }
            return;
        }//end

        public static void  onKeyUp (KeyboardEvent event )
        {
            int _loc_2 =0;
            IInputHandler _loc_3 =null ;
            if (isInputEnabled())
            {
                _loc_2 = 0;
                while (_loc_2 < m_inputHandlers.length())
                {

                    _loc_3 =(IInputHandler) m_inputHandlers.get(_loc_2);
                    if (_loc_3 != null && _loc_3.onKeyUp(event))
                    {
                        event.stopPropagation();
                        break;
                    }
                    _loc_2++;
                }
            }
            return;
        }//end

    }



