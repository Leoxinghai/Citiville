package Classes;

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

import Display.*;
import Display.DialogUI.*;
import Engine.Classes.*;
import Engine.Helpers.*;
//import flash.utils.*;

    public class DockHouse extends MapResource
    {
        protected  String STATE_STATIC ="static";
        private  String DOCK_HOUSE ="dock_house";
        private static  int NUM_FREEBIE_DOCKS =2;
        private static  double LINKED_OBJECT_TEMP_ID_START =70000;

        public  DockHouse (String param1 )
        {
            super(param1);
            m_objectType = WorldObjectTypes.DOCK_HOUSE;
            setState(this.STATE_STATIC);
            m_typeName = this.DOCK_HOUSE;
            return;
        }//end

         public boolean  isSellable ()
        {
            return true;
        }//end

         public void  onBuildingConstructionCompleted_PreServerUpdate ()
        {
            this.initialize();
            return;
        }//end

         public boolean  canBeRotated ()
        {
            return false;
        }//end

        protected void  initialize ()
        {
            Dock _loc_8 =null ;
            _loc_1 = getPosition();
            _loc_2 = LINKED_OBJECT_TEMP_ID_START;
            int _loc_3 =0;
            while (_loc_3 < NUM_FREEBIE_DOCKS)
            {

                _loc_8 = new Dock("dock_piece");
                _loc_1.x = _loc_1.x - 2;
                _loc_8.setPosition(_loc_1.x, _loc_1.y);
                _loc_8.setId(_loc_2);
                _loc_8.setOuter(Global.world);
                _loc_8.conditionallyReattach();
                _loc_2 = _loc_2 + 1;
                _loc_3++;
            }
            _loc_1 = getPosition();
            HarvestableShip _loc_4 =new HarvestableShip("ship_cruise");
            _loc_4.setPosition(_loc_1.x - 4, _loc_1.y + 2);
            _loc_4.setId(_loc_2);
            _loc_4.setOuter(Global.world);
            _loc_4.conditionallyReattach();
            _loc_4.setPlantedWhenShipArrives();
            _loc_5 = ZLoc.t("Dialogs","CruiseUnlocked_message");
            String _loc_6 ="CruiseUnlocked";
            CharacterDialog _loc_7 =new CharacterDialog(_loc_5 ,_loc_6 ,GenericDialogView.TYPE_OK ,null ,null ,true ,"assets/dialogs/cruise_guy.png");
            UI.displayPopup(_loc_7);
            return;
        }//end

        protected boolean  detachLinkedObjectsByPredicate (WorldObject param1 )
        {
            boolean _loc_2 =false ;
            if (param1 instanceof Dock)
            {
                ((Dock)param1).detach();
                ((Dock)param1).cleanUp();
            }
            if (param1 instanceof HarvestableShip)
            {
                ((HarvestableShip)param1).detach();
                ((HarvestableShip)param1).cleanUp();
            }
            return false;
        }//end

         public void  onSell (GameObject param1)
        {
            super.onSell(param1);
            _loc_2 =Global.world.getObjectsByPredicate(this.detachLinkedObjectsByPredicate );
            return;
        }//end

         public boolean  checkPlacementRequirements (int param1 ,int param2 )
        {
            Object _loc_6 =null ;
            boolean _loc_3 =false ;
            _loc_4 = m_item.getAllPlacementBoundaries();
            boolean _loc_5 =true ;
            if (_loc_4 != null)
            {
                _loc_5 = false;
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                		_loc_6 = _loc_4.get(i0);

                    if (_loc_6.right && param1 > _loc_6.right || _loc_6.left && param1 < _loc_6.left || _loc_6.bottom && param2 < _loc_6.bottom || _loc_6.top && param2 > _loc_6.top)
                    {
                        continue;
                        continue;
                    }
                    _loc_5 = true;
                    break;
                }
            }
            return _loc_5 && super.checkPlacementRequirements(param1, param2);
        }//end

    }



