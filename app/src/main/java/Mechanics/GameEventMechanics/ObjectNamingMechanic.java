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

import Display.*;
import Display.DialogUI.*;
import Engine.Managers.*;
import Events.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Mechanics.Transactions.*;
import Modules.universities.ui.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;

    public class ObjectNamingMechanic extends DialogGenerationMechanic implements IDialogGenerationMechanic
    {
        protected String m_ugcName ;
        protected String m_gameEvent ;
        private static Array dialogs =.get(InputTextDialog ,UniversityNameDialog) ;

        public  ObjectNamingMechanic ()
        {
            return;
        }//end

         public boolean  hasOverrideForGameAction (String param1 )
        {
            _loc_2 = (IMechanicUser)m_owner
            if (_loc_2.getDataForMechanic("name") == null)
            {
                return true;
            }
            return false;
        }//end

         public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            Object _loc_5 =null ;
            this.m_gameEvent = param1;
            this.createNameDialog();
            return new MechanicActionResult(_loc_3, !_loc_4, _loc_3, _loc_5);
        }//end

        public void  createNameDialog ()
        {
            GenericDialog _loc_6 =null ;
            String _loc_7 =null ;
            Class _loc_8 =null ;
            Object _loc_9 =null ;
            _loc_1 = ZLoc.t("Dialogs","rename_business_message",{item ZLoc.t("Items",m_owner.getItem ().name +"_friendlyName")});
            _loc_2 = ZLoc.t("Dialogs","rename_business_inputLabel",{item ZLoc.t("Items",m_owner.getItem ().name +"_friendlyName")});
            _loc_3 = m_owner.getItemFriendlyName();
            _loc_4 = m_config.params.get( "defaultNameLoc") ;
            if (m_config.params.get("defaultNameLoc") != null && _loc_4 != "")
            {
                _loc_7 = Global.player.snUser.gender == "M" ? (ZLoc.instance.MASC) : (ZLoc.instance.FEM);
                _loc_3 = ZLoc.t("Items", _loc_4, {user:ZLoc.tn(Global.player.snUser.firstName, _loc_7)});
            }
            _loc_5 = m_owner.getDataForMechanic("name");
            if (m_owner.getDataForMechanic("name") == null)
            {
                _loc_5 = new String();
            }
            this.m_ugcName = _loc_3;
            m_owner.setDataForMechanic("name", this.m_ugcName, "");
            if (m_config.params.get("dialogToPop") != null)
            {
                _loc_8 =(Class) getDefinitionByName(m_config.params.get("dialogToPop"));
                _loc_9 = new Object();
                _loc_9.put("submitNameCallback",  this.submitNewName);
                _loc_6 = new _loc_8(m_owner, _loc_9);
            }
            else
            {
                _loc_6 = new InputTextDialog(_loc_1, "rename_business", _loc_2, _loc_3, Global.gameSettings().getInt("maxBusinessNameLength", 15), GenericDialogView.TYPE_SAVESHARECOINS, this.onNameDialogComplete, true, this.onNameDialogSkip);
                ((InputTextDialog)_loc_6).textField.addEventListener(Event.CHANGE, this.onNameDialogChange);
            }
            UI.displayPopup(_loc_6);
            return;
        }//end

        private void  submitNewName (String param1 )
        {
            this.m_ugcName = param1;
            m_owner.setDataForMechanic("name", this.m_ugcName, "");
            TransactionManager.addTransaction(new TMechanicAction(m_owner, m_config.type, this.m_gameEvent, {operation:"name", name:this.m_ugcName}));
            return;
        }//end

        protected void  onNameDialogChange (Event event )
        {
            _loc_2 =(TextField) event.target;
            if (_loc_2)
            {
                this.m_ugcName = _loc_2.text;
            }
            return;
        }//end

        protected void  onNameDialogComplete (GenericPopupEvent event )
        {
            m_owner.setDataForMechanic("name", this.m_ugcName, "");
            TransactionManager.addTransaction(new TMechanicAction(m_owner, m_config.type, this.m_gameEvent, {operation:"name", name:this.m_ugcName}));
            if (event.button == GenericDialogView.YES)
            {
                Global.world.viralMgr.sendUGCViralFeed(Global.player, Global.player.cityName, ZLoc.t("Items", m_owner.getItemName() + "_friendlyName"), this.m_ugcName, m_owner.getItemName());
            }
            return;
        }//end

        protected void  onNameDialogSkip (GenericPopupEvent event )
        {
            m_owner.setDataForMechanic("name", this.m_ugcName, "");
            TransactionManager.addTransaction(new TMechanicAction(m_owner, m_config.type, this.m_gameEvent, {operation:"name", name:this.m_ugcName}));
            return;
        }//end

    }



