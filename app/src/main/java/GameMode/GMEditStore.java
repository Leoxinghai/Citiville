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
import Display.DialogUI.*;
import Display.PopulationUI.*;
import Events.*;

//import flash.events.*;
import Modules.storage.StorageId;

    public class GMEditStore extends GMEdit
    {
        private MapResource m_warningResource ;
        private static boolean SEEN_WARNING =false ;

        public  GMEditStore ()
        {
            m_uiMode = UIEvent.STORE;
            return;
        }//end

         protected boolean  isObjectSelectable (GameObject param1 )
        {
            Vector _loc_3.<StorageId >=null ;
            if (GMEdit.isLocked)
            {
                return false;
            }
            _loc_2 = param1as MapResource ;
            if (_loc_2)
            {
                _loc_3 = Global.player.storageComponent.getValidStorageUnits(_loc_2);
                if (_loc_2.isStorable() && _loc_3.length == 1)
                {
                    return true;
                }
            }
            return false;
        }//end

         public void  enableMode ()
        {
            super.enableMode();
            m_cursorImage = EmbeddedArt.hud_act_store;
            return;
        }//end

         public boolean  onMouseUp (MouseEvent event )
        {
            IPeepCapacity _loc_3 =null ;
            String _loc_4 =null ;
            PopulationGateDialog _loc_5 =null ;
            String _loc_6 =null ;
            GenericDialog _loc_7 =null ;
            if (GMEdit.isLocked)
            {
                finishMouseUpEvent();
                return false;
            }
            if (UI.resolveIfAssetsHaveNotLoaded(DelayedAssetLoader.GENERIC_DIALOG_ASSETS))
            {
                finishMouseUpEvent();
                return false;
            }
            _loc_2 = (MapResource)m_selectedObject
            if (_loc_2 && _loc_2 instanceof IPeepCapacity)
            {
                _loc_3 =(IPeepCapacity) _loc_2;
                _loc_4 = _loc_2.getPopulationType();
                if (Global.world.citySim.getPopulation(_loc_4) > Global.world.citySim.getPopulationCap(_loc_4) - _loc_3.getPopulationCapYield() || Global.world.citySim.getTotalBusinesses() > Global.world.citySim.getPopulationCap() - Math.floor(_loc_3.getPopulationCapYield()) * Global.gameSettings().getNumber("businessLimitByPopulationMax"))
                {
                    _loc_5 = new PopulationGateDialog(ZLoc.t("Dialogs", "CannotStoreMunicipal"), "PopulationGate");
                    UI.displayPopup(_loc_5);
                    return super.onMouseUp(event);
                }
            }
            if (_loc_2 && !m_dragging)
            {
                if (_loc_2 instanceof Doober)
                {
                    _loc_2.onPlayAction();
                }
                else if (!SEEN_WARNING && _loc_2.warnForStorage())
                {
                    this.m_warningResource = _loc_2;
                    _loc_6 = _loc_2.warnForStorageMessage();
                    _loc_7 = new GenericDialog(_loc_6, "storageWarning", GenericDialogView.TYPE_YESNO, this.warningHandler);
                    UI.displayPopup(_loc_7);
                }
                else
                {
                    _loc_2.store();
                }
            }
            super.onMouseUp(event);
            deselectObject();
            dehighlightObject();
            return true;
        }//end

        private void  warningHandler (GenericPopupEvent event )
        {
            if (event.button == GenericPopup.YES)
            {
                this.m_warningResource.store();
                SEEN_WARNING = true;
            }
            return;
        }//end

    }



