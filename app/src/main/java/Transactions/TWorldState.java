package Transactions;

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

import Engine.Classes.*;
import Modules.realtime.*;
import Transactions.tracking.*;

    public class TWorldState extends TFarmTransaction
    {
        protected Object m_save ;
        protected WorldObject m_object =null ;
        public static  int TEMP_ID_START =63000;
        public static  int TEMP_ID_END =63750;
        public static boolean wrappedTempIds =false ;
        public static int m_curTempID =63000;
        private static DesyncTracker m_tracker =new DesyncTracker ();

        public  TWorldState (WorldObject param1 )
        {
            this.m_object = param1;
            if (this.m_object != null && this.m_object.getId() == 0 && !Global.isVisiting())
            {
                this.m_object.setId(getNextTempID());
            }
            if (param1 != null)
            {
                this.m_save = param1.getSaveObject();
            }
            else
            {
                this.m_save = null;
            }
            return;
        }//end

         public void  perform ()
        {
            throw new Error("Subclasses of TWorldState must override perform()");
        }//end

        final  public void  onAdd ()
        {
            super.onAdd();
            this.onWorldActionAdd();
            m_tracker.onAdd(this);
            return;
        }//end

        final  protected void  onComplete (Object param1 )
        {
            super.onComplete(param1);
            this.onWorldActionComplete(param1);
            if (this.m_object != null && (this.m_object.getId() == 0 || this.m_object.getId() >= TEMP_ID_START) && param1.hasOwnProperty("id") && param1.id != 0)
            {
                this.m_object.setId(param1.id);
            }
            m_tracker.onComplete(this);
            return;
        }//end

        final  protected void  onFault (int param1 ,String param2 )
        {
            super.onFault(param1, param2);
            this.onWorldActionFault(param1, param2);
            m_tracker.onFault(this);
            return;
        }//end

        protected void  onWorldActionAdd ()
        {
            return;
        }//end

        protected void  onWorldActionComplete (Object param1 )
        {
            return;
        }//end

        protected void  onWorldActionFault (int param1 ,String param2 )
        {
            return;
        }//end

        protected void  signedWorldAction (String param1 ,...args )
        {
            signedCall("WorldService.performAction", param1, this.m_save, m_clientEnqueueTime, args);
            m_functionName = "WorldService.performAction:" + param1;
            return;
        }//end

        public WorldObject  getWorldObject ()
        {
            return this.m_object;
        }//end

        public Object  getSaveObject ()
        {
            return this.m_save;
        }//end

        public RealtimeMethod  getRealtimeMethod ()
        {
            return null;
        }//end

        public static int  getNextTempID ()
        {
            Array _loc_3 =null ;
            int _loc_4 =0;
            WorldObject _loc_5 =null ;
            boolean _loc_1 =false ;
            int _loc_2 =0;
            if (wrappedTempIds)
            {
                _loc_3 = Global.world.getObjects();
                _loc_4 = 0;
                while (_loc_4 < _loc_3.length())
                {

                    _loc_5 =(WorldObject) _loc_3.get(_loc_4);
                    if (_loc_5 != null)
                    {
                        if (_loc_5.getId() == m_curTempID)
                        {
                            _loc_1 = true;
                            break;
                        }
                    }
                    _loc_4++;
                }
            }
            if (_loc_1 == false)
            {
                _loc_2 = m_curTempID;
            }
            _loc_7 = m_curTempID+1;
            m_curTempID = _loc_7;
            if (m_curTempID > TEMP_ID_END)
            {
                m_curTempID = TEMP_ID_START;
                wrappedTempIds = true;
            }
            return _loc_2;
        }//end

    }



