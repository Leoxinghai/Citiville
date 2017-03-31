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
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameEventMechanics.*;
import Mechanics.GameMechanicInterfaces.*;

//import flash.events.*;

    public class GMMechanicStore extends GMEdit
    {
        private IMechanicUser m_storage ;
        private Vector<GameObject> m_storables;
		public static boolean m_seenWarning =false ;

        public  GMMechanicStore (IMechanicUser param1 )
        {
            m_uiMode = UIEvent.STORE;
            this.m_storage = param1;
            this.m_storables = this.getStorables(this.m_storage);
            return;
        }//end

        protected GameObject Vector  getStorables (IMechanicUser param1 ).<>
        {
            MapResource _loc_6 =null ;
            Vector<GameObject> _loc_2 =new Vector<GameObject>();
            if (param1 == null || !param1.getMechanicConfig("slots", Global.getClassName(this)))
            {
                return _loc_2;
            }
            _loc_3 = MechanicManager.getInstance();
            _loc_4 = _loc_3.getMechanicInstance(param1 ,"slots",Global.getClassName(this ))as IStorage ;
            _loc_5 =Global.world.getObjects ();
            if (_loc_4 != null)
            {
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_6 = _loc_5.get(i0);

                    if (_loc_4.canStore(_loc_6))
                    {
                        _loc_2.push(_loc_6);
                    }
                }
            }
            return _loc_2;
        }//end

        protected void  addPulsateEffects ()
        {
            MapResource _loc_1 =null ;
            if (this.m_storables == null)
            {
                return;
            }
            for(int i0 = 0; i0 < this.m_storables.size(); i0++)
            {
            	_loc_1 = this.m_storables.get(i0);

                _loc_1.addAnimatedEffect(EffectType.PULSATE_GLOW);
            }
            return;
        }//end

        protected void  removePulsateEffects ()
        {
            MapResource _loc_1 =null ;
            if (!this.m_storables)
            {
                return;
            }
            for(int i0 = 0; i0 < this.m_storables.size(); i0++)
            {
            	_loc_1 = this.m_storables.get(i0);

                _loc_1.removeAnimatedEffects();
            }
            return;
        }//end

         public boolean  supportsEditing ()
        {
            return false;
        }//end

         public void  enableMode ()
        {
            super.enableMode();
            this.addPulsateEffects();
            return;
        }//end

         public void  disableMode ()
        {
            super.disableMode();
            this.removePulsateEffects();
            return;
        }//end

         protected boolean  isObjectSelectable (GameObject param1 )
        {
            if (GMEdit.isLocked)
            {
                return false;
            }
            _loc_2 = (IStorage)MechanicManager.getInstance().getMechanicInstance(this.m_storage,"slots",Global.getClassName(this))
            return param1 instanceof MapResource && _loc_2 && _loc_2.canStore(param1 as MapResource);
        }//end

         protected boolean  canBeClicked (MouseEvent event )
        {
            return m_highlightedObject != null && !Global.isVisiting();
        }//end

         protected Object  getCursor ()
        {
            if (this.m_storage instanceof Mall)
            {
                return EmbeddedArt.hud_act_mall;
            }
            if (this.m_storage instanceof Neighborhood)
            {
                return EmbeddedArt.hud_act_hood;
            }
            return super.getCursor();
        }//end

         public boolean  onMouseUp (MouseEvent event )
        {
            MapResource _loc_2 =null ;
            String _loc_3 =null ;
            GenericDialog _loc_4 =null ;
            if (UI.resolveIfAssetsHaveNotLoaded(DelayedAssetLoader.GENERIC_DIALOG_ASSETS))
            {
                finishMouseUpEvent();
                return false;
            }
            if (m_selectedObject == null)
            {
                finishMouseUpEvent();
                return false;
            }
            super.onMouseUp(event);
            if (!m_seenWarning && (m_selectedObject as MapResource).warnForStorage())
            {
                m_seenWarning = true;
                if (m_selectedObject instanceof Business)
                {
                    _loc_2 =(MapResource) m_selectedObject;
                    _loc_3 = ZLoc.t("Dialogs", "MechanicStoreWarning", {storeTarget:_loc_2.getItemFriendlyName(), storage:(this.m_storage as MechanicMapResource).getItemFriendlyName()});
                    _loc_4 = new GenericDialog(_loc_3, "storageWarning", GenericDialogView.TYPE_YESNO, this.warningHandler);
                    UI.displayPopup(_loc_4);
                    finishMouseUpEvent();
                    return false;
                }
            }
            this.handleStore();
            return true;
        }//end

        private void  handleStore ()
        {
            Array _loc_1 =new Array ();
            _loc_1.put("mode",  BaseStorageMechanic.MODE_STORE);
            _loc_1.put("object",  m_selectedObject);
            _loc_2 = MechanicManager.getInstance().handleAction(this.m_storage,Global.getClassName(this),_loc_1);
            Global.ui.dispatchEvent(new GenericObjectEvent(GenericObjectEvent.OBJECT_STORED, null));
            deselectObject();
            dehighlightObject();
            if (_loc_2)
            {
                Global.world.addGameMode(new GMPlay());
            }
            return;
        }//end

        private void  warningHandler (GenericPopupEvent event )
        {
            if (event.button == GenericPopup.YES)
            {
                this.handleStore();
            }
            else
            {
                m_seenWarning = false;
            }
            return;
        }//end

    }



