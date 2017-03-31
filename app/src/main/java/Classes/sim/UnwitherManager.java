package Classes.sim;

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
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Engine.Classes.*;
import Engine.Helpers.*;
import Modules.stats.experiments.*;
import Transactions.*;

    public class UnwitherManager implements IGameWorldStateObserver
    {
        private boolean m_isManagerEnabled =false ;
        private boolean m_isGlobalUnwitherEnabled =false ;
        private static  String GLOBAL_UNWITHER_ITEM_KEYWORD ="Unwither_Global";
        private static  Array AFFECTED_CLASSES =.get(Ship ,Plot) ;

        public  UnwitherManager (GameWorld param1 )
        {
            this.m_isManagerEnabled = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_UNWITHER_GLOBAL) > 0;
            param1.addObserver(this);
            return;
        }//end

        public boolean  isWitheringDisabledGlobally ()
        {
            return this.m_isGlobalUnwitherEnabled;
        }//end

        public void  initialize ()
        {
            this.setUnwitherAndRevive(false, false);
            return;
        }//end

        public void  cleanUp ()
        {
            this.setUnwitherAndRevive(false, false);
            return;
        }//end

        public void  onGameLoaded (Object param1 )
        {
            this.updateUnwither(false);
            return;
        }//end

        public void  onResourceChange (MapResource param1 ,Vector3 param2 ,Vector3 param3 )
        {
            this.updateUnwither(true);
            return;
        }//end

        private void  updateUnwither (boolean param1 )
        {
            boolean _loc_2 =false ;
            _loc_3 =Global.world.getObjectsByKeywords(GLOBAL_UNWITHER_ITEM_KEYWORD );
            if (_loc_3.length > 0)
            {
                _loc_2 = true;
            }
            this.setUnwitherAndRevive(_loc_2, param1);
            return;
        }//end

        private void  setUnwitherAndRevive (boolean param1 ,boolean param2 )
        {
            Class _loc_4 =null ;
            Array _loc_5 =null ;
            WorldObject _loc_6 =null ;
            HarvestableResource _loc_7 =null ;
            GenericDialog _loc_8 =null ;
            if (!this.m_isManagerEnabled)
            {
                return;
            }
            if (param1 == this.m_isGlobalUnwitherEnabled)
            {
                return;
            }
            this.m_isGlobalUnwitherEnabled = param1;
            int _loc_3 =0;
            for(int i0 = 0; i0 < AFFECTED_CLASSES.size(); i0++)
            {
            	_loc_4 = AFFECTED_CLASSES.get(i0);

                _loc_5 = Global.world.getObjectsByClass(_loc_4);
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_6 = _loc_5.get(i0);

                    _loc_7 =(HarvestableResource) _loc_6;
                    if (_loc_7 != null)
                    {
                        if (param1 == true && _loc_7.isWithered())
                        {
                            _loc_7.setFullGrown();
                            _loc_3++;
                        }
                        if (param1 == false && _loc_7.isGrown())
                        {
                            _loc_7.forceGrowthStateUpdate();
                            _loc_3++;
                        }
                    }
                }
            }
            if (param2)
            {
                if (param1 !=null)
                {
                    UI.displayMessage(ZLoc.t("Dialogs", "Unwither_added"), GenericDialogView.TYPE_OK);
                    TimerUtil.callLater(Global.hud.refreshGoodsHUD, 1000);
                }
                else
                {
                    _loc_8 = UI.displayMessage(ZLoc.t("Dialogs", "Unwither_removed"), GenericDialogView.TYPE_NOBUTTONS, null, "", true);
                    GameTransactionManager.addTransaction(new TRoundTripSentinel(null, GameUtil.reloadApp), true);
                    Global.isTransitioningWorld = true;
                    Global.world.citySim.pickupManager.clearQueue("NPC_farmer");
                }
            }
            return;
        }//end

    }



