package Classes.Managers;

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
import Classes.doobers.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Engine.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Init.PostInit.PostInitActions.*;
import Transactions.*;
//import flash.events.*;

    public class InGameXPromoManager
    {
        protected XPromoFriend m_xpromoFriend =null ;
public static boolean m_hasInitted =false ;
public static InGameXPromoManager m_instance =null ;

        public  InGameXPromoManager ()
        {
            if (InGameXPromoManager.m_instance != null)
            {
                throw new Error("Attempting to instantiate more than one InGameXPromoManager");
            }
            return;
        }//end

        public StartupDialogBase  getStartupDialog ()
        {
            Object _loc_2 =null ;
            boolean _loc_3 =false ;
            Function _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            SamAdviceDialog _loc_7 =null ;
            StartupDialogBase _loc_8 =null ;
            _loc_1 = Global.gameSettings().getXpromos();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_2 = _loc_1.get(i0);

                _loc_3 = InGameXPromoManager.checkXPromoGates(_loc_2);
                if (_loc_3 && _loc_2.startUpDialog && !Global.player.getSeenFlag("xpromo_" + _loc_2.nameKey))
                {
                    GameTransactionManager.addTransaction(new TSetXPromoSeen(_loc_2));
                    this.m_xpromoFriend = new XPromoFriend(_loc_2);
                    _loc_4 = null;
                    if (m_instance.get("on" + _loc_2.nameKey))
                    {
                        _loc_4 =(Function) m_instance.get("on" + _loc_2.nameKey);
                    }
                    _loc_5 = ZLoc.t("Dialogs", "xpromo_advice_" + _loc_2.nameKey);
                    _loc_6 = ZLoc.t("Dialogs", "xpromo_advice_" + _loc_2.nameKey + "_title");
                    _loc_7 = new SamAdviceDialog(_loc_5, "", GenericPopup.TYPE_OK, _loc_4, "", "", true, 0, "Okay");
                    _loc_8 = new StartupDialogBase(_loc_7, "d2_invitefriendsdialog", false);
                    StatsManager.count("xpromo", _loc_2.nameKey, "dialog_startup");
                    return _loc_8;
                }
            }
            return null;
        }//end

        public void  onFarmEngland (Event event =null )
        {
            if (event)
            {
                event.stopImmediatePropagation();
                event.stopPropagation();
            }
            _loc_2 = Global.gameSettings().getInt("startCameraX");
            _loc_3 = Global.gameSettings().getInt("startCameraY");
            _loc_4 = IsoMath.screenPosToTilePos(GlobalEngine.viewport.x+GlobalEngine.viewport.width,GlobalEngine.viewport.y+GlobalEngine.viewport.height);
            Vector3 _loc_5 =new Vector3(_loc_4.x ,_loc_4.y );
            Vehicle _loc_6 =new Vehicle("npc_fvairship",false );
            _loc_7 = IsoMath.screenPosToTilePos(GlobalEngine.viewport.x,GlobalEngine.viewport.y-50);
            Vector3 _loc_8 =new Vector3(_loc_7.x ,_loc_7.y );
            Vector2 _loc_9 =new Vector2(_loc_2 ,_loc_3 );
            Vector3 _loc_10 =new Vector3(_loc_9.x ,_loc_9.y );
            Array _loc_11 =new Array();
            _loc_11.push(new ActionNavigateBeeline(_loc_6, _loc_10, _loc_8));
            _loc_11.push(new ActionPause(_loc_6, 1));
            _loc_11.push(new ActionFn(_loc_6, this.onDropOff));
            _loc_11.push(new ActionPause(_loc_6, 1));
            _loc_11.push(new ActionNavigateBeeline(_loc_6, _loc_5, _loc_10));
            _loc_11.push(new ActionDie(_loc_6));
            _loc_6 = Global.world.citySim.npcManager.addFlyerByName(_loc_6, _loc_8, _loc_11);
            _loc_6.velocityWalk = 2.5;
            _loc_6.velocityRun = 2.5;
            return;
        }//end

        public void  onDropOff (Event event =null )
        {
            _loc_2 = Global.gameSettings().getInt("startCameraX");
            _loc_3 = Global.gameSettings().getInt("startCameraY");
            Array _loc_4 =new Array();
            _loc_5 = this.m_xpromoFriend.configObj.numDoobers;
            _loc_6 = this.m_xpromoFriend.configObj.goods;
            while (_loc_6 > 0 && _loc_5 > 0)
            {

                _loc_4.push(.get(Global.gameSettings().getDooberFromType(Doober.DOOBER_GOODS, _loc_6), _loc_6));
                _loc_5 = _loc_5 - 1;
            }
            Global.world.dooberManager.createBatchDoobers(_loc_4, null, _loc_2, _loc_3, false, this.onDoobersCollected);
            return;
        }//end

        public void  onGagaville (Event event =null )
        {
            if (event)
            {
                event.stopImmediatePropagation();
                event.stopPropagation();
            }
            return;
        }//end

        public void  onGaGaFarm (Event event =null )
        {
            if (event)
            {
                event.stopImmediatePropagation();
                event.stopPropagation();
            }
            return;
        }//end

        public void  onMafia2 (Event event =null )
        {
            if (event)
            {
                event.stopImmediatePropagation();
                event.stopPropagation();
            }
            return;
        }//end

        public void  onDoobersCollected (MouseEvent event =null )
        {
            this.m_xpromoFriend.onXPromo(event);
            return;
        }//end

        public static void  initialize ()
        {
            if (!InGameXPromoManager.m_instance)
            {
                m_instance = new InGameXPromoManager;
            }
            else
            {
                GlobalEngine.log("XPromo", "Attempting to initialize xpromos after the manager has been instantiated");
            }
            return;
        }//end

        public static InGameXPromoManager  getInstance ()
        {
            if (!InGameXPromoManager.m_instance)
            {
                InGameXPromoManager.initialize();
            }
            return m_instance;
        }//end

        public static boolean  checkXPromoGates (Object param1 )
        {
            double _loc_3 =0;
            double _loc_4 =0;
            boolean _loc_2 =false ;
            if (param1.hasOwnProperty("startDate") && param1.hasOwnProperty("endDate"))
            {
                _loc_3 = GameUtil.synchronizedDateToNumber(param1.startDate);
                _loc_4 = GameUtil.synchronizedDateToNumber(param1.endDate);
                if (_loc_3 <= GlobalEngine.getTimer() && _loc_4 >= GlobalEngine.getTimer())
                {
                    if (param1.hasOwnProperty("level") && int(param1.level) <= Global.player.level)
                    {
                        _loc_2 = true;
                        if (param1.hasOwnProperty("experiment"))
                        {
                            _loc_2 = Global.experimentManager.getVariant(param1.experiment) > 0;
                        }
                    }
                }
            }
            return _loc_2;
        }//end

    }



