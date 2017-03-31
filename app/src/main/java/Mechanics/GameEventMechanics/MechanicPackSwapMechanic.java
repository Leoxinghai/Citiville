package Mechanics.GameEventMechanics;

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
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;

    public class MechanicPackSwapMechanic implements IActionGameMechanic
    {
        protected MechanicMapResource m_owner ;
        protected MechanicConfigData m_config ;
        protected String m_additionalRefreshEventType =null ;

        public  MechanicPackSwapMechanic ()
        {
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            switch(param1)
            {
                case MechanicManager.ON_ADD_TO_WORLD:
                case MechanicManager.ON_LOAD:
                case MechanicManager.ON_UPGRADE:
                {
                    return true;
                }
                default:
                {
                    break;
                }
            }
            return false;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return false;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            MechanicPackData _loc_3 =null ;
            String _loc_4 =null ;
            if (param1 == MechanicManager.ON_ADD_TO_WORLD)
            {
                _loc_3 = this.m_owner.getItem().getAvailableMechanicPackData(this.m_owner);
            }
            else if (param1 == MechanicManager.ON_LOAD || param1 == MechanicManager.ON_UPGRADE)
            {
                _loc_4 = this.m_owner.getDataForMechanic(this.m_config.type);
                if (_loc_4)
                {
                    _loc_3 = this.m_owner.getItem().getAvailableMechanicPackData(this.m_owner, _loc_4);
                }
                else
                {
                    _loc_3 = this.m_owner.getItem().getAvailableMechanicPackData(this.m_owner);
                }
            }
            else
            {
                return new MechanicActionResult(false, true, false);
            }
            if (_loc_3)
            {
                this.updateMechanicPackHelper(_loc_3.name);
                this.m_owner.setDataForMechanic(this.m_config.type, _loc_3.name, param1);
            }
            else
            {
                return new MechanicActionResult(false, true, false);
            }
            return new MechanicActionResult(true, !this.blocksMechanicsOnSuccess(), false);
        }//end

        private void  updateMechanicPackHelper (String param1 )
        {
            XML _loc_2 =null ;
            if (param1 && param1 != "")
            {
                _loc_2 = Global.gameSettings().loadAndReplaceMechanicPackXmlIntoItem(this.m_owner.getItem().xml, param1);
                this.m_owner.getItem().updateXML(_loc_2);
                this.m_owner.getItem().updateMechanicPackName(param1);
                Global.gameSettings().addOrReplaceItemXMLByName(_loc_2);
                this.m_owner.reinitializeMechanics();
                this.m_owner.onMechanicPackSwap();
            }
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            if (this.m_owner.hasEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED))
            {
                this.m_owner.removeEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.onMechanicDataChanged);
            }
            this.m_owner.addEventListener(GenericObjectEvent.MECHANIC_DATA_CHANGED, this.onMechanicDataChanged, false, 0, true);
            if (this.m_config.params.listenForQuestExpire)
            {
                if (Global.world.hasEventListener(GenericObjectEvent.QUEST_EXPIRED_EVENT))
                {
                    this.m_owner.removeEventListener(GenericObjectEvent.QUEST_EXPIRED_EVENT, this.onQuestExpired);
                }
                Global.world.addEventListener(GenericObjectEvent.QUEST_EXPIRED_EVENT, this.onQuestExpired, false, 0, true);
            }
            if (this.m_config.params.refreshOn)
            {
                this.m_additionalRefreshEventType = String(this.m_config.params.refreshOn);
            }
            return;
        }//end

        private void  onMechanicDataChanged (GenericObjectEvent event )
        {
            String _loc_2 =null ;
            if (event.obj == this.m_config.type)
            {
                _loc_2 = this.m_owner.getDataForMechanic(this.m_config.type);
                if (this.m_owner.getItem().getCurrentMechanicPackName() != _loc_2)
                {
                    this.updateMechanicPackHelper(_loc_2);
                }
            }
            else if (event.obj == this.m_additionalRefreshEventType)
            {
                this.forceRefreshMechanicPacks(this.m_additionalRefreshEventType);
            }
            return;
        }//end

        private void  onQuestExpired (GenericObjectEvent event )
        {
            if (event.obj == String(this.m_config.params.listenForQuestExpire))
            {
                this.forceRefreshMechanicPacks(String(this.m_config.params.listenForQuestExpire));
            }
            return;
        }//end

        private void  forceRefreshMechanicPacks (String param1 )
        {
            _loc_2 = this.m_owner.getDataForMechanic(this.m_config.type );
            _loc_3 = this.m_owner.getItem ().getAvailableMechanicPackData(this.m_owner ,_loc_2 );
            if (_loc_3 && this.m_owner.getItem().getCurrentMechanicPackName() != _loc_3.name)
            {
                this.updateMechanicPackHelper(_loc_3.name);
                this.m_owner.setDataForMechanic(this.m_config.type, _loc_3.name, param1);
            }
            return;
        }//end

    }



