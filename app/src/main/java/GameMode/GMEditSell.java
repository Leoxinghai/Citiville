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
import Classes.doobers.*;
import Classes.util.*;
import Display.*;
import Display.PopulationUI.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Transactions.*;
//import flash.events.*;

    public class GMEditSell extends GMEdit
    {

        public  GMEditSell ()
        {
            m_uiMode = UIEvent.REMOVE;
            return;
        }//end

         protected boolean  isObjectSelectable (GameObject param1 )
        {
            return param1.isSellable() || param1 instanceof Doober;
        }//end

         public void  enableMode ()
        {
            super.enableMode();
            m_cursorImage = EmbeddedArt.hud_act_remove;
            return;
        }//end

         public boolean  onMouseUp (MouseEvent event )
        {
            IPeepCapacity _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            PopulationGateDialog _loc_6 =null ;
            MechanicMapResource _loc_7 =null ;
            RollCallDataMechanic _loc_8 =null ;
            if (UI.resolveIfAssetsHaveNotLoaded(DelayedAssetLoader.GENERIC_DIALOG_ASSETS))
            {
                return false;
            }
            _loc_2 = (MapResource)m_selectedObject
            if (_loc_2 && _loc_2 instanceof IPeepCapacity)
            {
                _loc_3 =(IPeepCapacity) _loc_2;
                _loc_4 = _loc_2.getPopulationType();
                if (Global.world.citySim.getPopulation(_loc_4) > Global.world.citySim.getPopulationCap(_loc_4) - _loc_3.getPopulationCapYield() || Global.world.citySim.getTotalBusinesses() > Global.world.citySim.getPopulationCap() - Math.floor(_loc_3.getPopulationCapYield()) * Global.gameSettings().getNumber("businessLimitByPopulationMax"))
                {
                    if (_loc_2 instanceof Landmark)
                    {
                        _loc_5 = ZLoc.t("Dialogs", "CannotSellLandmark");
                    }
                    else
                    {
                        _loc_5 = ZLoc.t("Dialogs", "CannotSellMunicipal");
                    }
                    _loc_6 = new PopulationGateDialog(_loc_5, "PopulationGate");
                    UI.displayPopup(_loc_6);
                    return super.onMouseUp(event);
                }
                else
                {
                    _loc_7 =(MechanicMapResource) _loc_2;
                    _loc_8 =(RollCallDataMechanic) MechanicManager.getInstance().getMechanicInstance(_loc_7, "rollCall", MechanicManager.ALL);
                    if (_loc_8)
                    {
                        GameTransactionManager.addTransaction(new TClearRollCallArrays());
                    }
                }
            }
            if (_loc_2 && !m_dragging)
            {
                if (_loc_2 instanceof Doober)
                {
                    _loc_2.onPlayAction();
                }
                else
                {
                    _loc_2.sell();
                }
            }
            deselectObject();
            return super.onMouseUp(event);
        }//end

    }



