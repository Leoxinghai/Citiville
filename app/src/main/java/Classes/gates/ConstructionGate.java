package Classes.gates;

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
import Classes.effects.*;
import Mechanics.GameMechanicInterfaces.*;

    public class ConstructionGate extends AbstractGate implements IToolTipModifier
    {
        protected int m_buildProgress =0;
        public static  String BUILD_KEY ="builds";

        public  ConstructionGate (String param1 )
        {
            super(param1);
            return;
        }//end

        public int  buildProgress ()
        {
            return Math.min(this.m_buildProgress, m_keys.get(BUILD_KEY));
        }//end

        public String  getToolTipAction ()
        {
            if (this.m_buildProgress >= (m_keys.get(BUILD_KEY) - 1))
            {
                return ZLoc.t("Main", "ClickToFinish");
            }
            return ZLoc.t("Main", "ClickToBuild");
        }//end

        public String  getToolTipStatus ()
        {
            return ZLoc.t("Main", "BuildInfo", {current:this.buildProgress, required:m_keys.get(BUILD_KEY)});
        }//end

         public Object getData ()
        {
            Object _loc_1 ={};
            _loc_1.put(BUILD_KEY,  this.m_buildProgress);
            return _loc_1;
        }//end

         public void  loadFromObject (Object param1 )
        {
            _loc_2 = null;
            for(int i0 = 0; i0 < param1.keys.size(); i0++)
            {
            		_loc_2 = param1.keys.get(i0);

                m_keys.put(_loc_2,  param1.get(_loc_2));
            }
            this.m_buildProgress = param1.hasOwnProperty(BUILD_KEY) ? (param1.get(BUILD_KEY)) : (this.m_buildProgress);
            return;
        }//end

         public void  incrementKey (String param1 )
        {
            _loc_2 =Global.world.getObjectById(m_targetObjectId )as MapResource ;
            this.m_buildProgress++;
            if (_loc_2 && !this.checkForKeys())
            {
                _loc_2.actionQueue.removeAllStates();
                _loc_2.actionQueue.addActions(new ActionProgressBar(null, _loc_2, ZLoc.t("Main", "Building"), 2, this.pgStartHandler, this.pgEndHandler, this.pgCancelHandler));
            }
            return;
        }//end

         public void  displayGate (String param1 ,String param2 ,Object param3 =null )
        {
            ScaffoldEffect _loc_5 =null ;
            _loc_4 = (MapResource)Global.world.getObjectById(m_targetObjectId ) ;
            if ((MapResource)Global.world.getObjectById(m_targetObjectId))
            {
                _loc_5 =(ScaffoldEffect) _loc_4.getAnimatedEffectByClass(ScaffoldEffect);
                if (_loc_5 == null)
                {
                    _loc_5 = new ScaffoldEffect(_loc_4, (this.m_buildProgress + 1), (this.m_buildProgress + 1), 1000, "");
                    _loc_4.addAnimatedEffectFromInstance(_loc_5);
                }
                else
                {
                    _loc_5.startStage = this.m_buildProgress + 1;
                    _loc_5.endStage = this.m_buildProgress + 1;
                    _loc_5.reattach();
                }
            }
            return;
        }//end

         public boolean  unlockGate ()
        {
            MapResource _loc_2 =null ;
            ScaffoldEffect _loc_3 =null ;
            _loc_1 = super.unlockGate();
            if (_loc_1)
            {
                _loc_2 =(MapResource) Global.world.getObjectById(m_targetObjectId);
                _loc_3 =(ScaffoldEffect) _loc_2.getAnimatedEffectByClass(ScaffoldEffect);
                if (_loc_3)
                {
                    _loc_3.cleanUp();
                    _loc_2.removeAnimatedEffectByClass(ScaffoldEffect);
                    _loc_2.refreshArrow();
                }
            }
            return _loc_1;
        }//end

         public boolean  checkForKeys ()
        {
            _loc_1 = this.m_buildProgress >=m_keys.get(BUILD_KEY) ;
            return _loc_1;
        }//end

        private boolean  pgStartHandler ()
        {
            return true;
        }//end

        private void  pgEndHandler ()
        {
            return;
        }//end

        private void  pgCancelHandler ()
        {
            return;
        }//end

    }


