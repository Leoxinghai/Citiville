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
import Classes.effects.*;
import Display.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.remodel.*;
//import flash.events.*;

    public class GMSwapResidenceSkin extends GMEdit
    {
        protected Item m_item ;
        protected Array m_residenceIds ;

        public  GMSwapResidenceSkin (String param1 )
        {
            Residence _loc_2 =null ;
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            this.m_item = Global.gameSettings().getItemByName(param1);
            this.m_residenceIds = new Array();
            for(int i0 = 0; i0 < Global.world.getObjectsByTypes(.get(WorldObjectTypes.RESIDENCE)).size(); i0++)
            {
            		_loc_2 = Global.world.getObjectsByTypes(.get(WorldObjectTypes.RESIDENCE)).get(i0);

                _loc_3 = _loc_2.getItem().name != this.m_item.derivedItemName && _loc_2.getItem().derivedItemName != this.m_item.derivedItemName;
                _loc_4 = _loc_2.getItem().name == this.m_item.name;
                if (_loc_2.getItem().name != this.m_item.derivedItemName && _loc_2.getItem().derivedItemName != this.m_item.derivedItemName || _loc_2.getItem().name == this.m_item.name)
                {
                    this.m_residenceIds.push(_loc_2.getId());
                }
            }
            m_cursorImage = EmbeddedArt.hud_act_remodel;
            return;
        }//end

         public void  enableMode ()
        {
            UI.popupLock();
            super.enableMode();
            Global.stagePickManager.hideAllPicks();
            Global.stagePickManager.showPicksByType(StagePickEffect.PICK_REMODEL);
            Global.stagePickManager.attachStagePicksBatch(StagePickEffect.PICK_REMODEL, RemodelManager.isRemodelPossible, this.m_residenceIds);
            return;
        }//end

         public void  disableMode ()
        {
            deselectObject();
            dehighlightObject();
            Global.stagePickManager.detachStagePicksBatch(RemodelManager.isRemodelPossible);
            Global.stagePickManager.showAllPicks();
            UI.popupUnlock();
            super.disableMode();
            return;
        }//end

         public boolean  allowHighlight (MapResource param1 )
        {
            return RemodelManager.isRemodelPossible(param1) && (param1.getItem().name == this.m_item.derivedItemName || param1.getItem().derivedItemName == this.m_item.derivedItemName);
        }//end

         protected boolean  isObjectSelectable (GameObject param1 )
        {
            Residence _loc_2 =null ;
            if (param1 instanceof Residence)
            {
                _loc_2 =(Residence) param1;
                return RemodelManager.isRemodelPossible(_loc_2) && (_loc_2.getItem().name == this.m_item.derivedItemName || _loc_2.getItem().derivedItemName == this.m_item.derivedItemName);
            }
            return false;
        }//end

         protected void  handleClick (MouseEvent event )
        {
            if (m_highlightedObject && m_highlightedObject instanceof IMechanicUser)
            {
                MechanicManager.getInstance().handleAction((IMechanicUser)m_highlightedObject, "catalogPurchase", [this.m_item.name]);
                Global.world.addGameMode(new GMPlay());
            }
            return;
        }//end

    }


