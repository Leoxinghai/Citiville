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
import Classes.Managers.*;
import Display.*;
import Display.DialogUI.*;
import Events.*;
import Mechanics.*;
import Modules.crew.*;
import Modules.crew.ui.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import Mechanics.GameMechanicInterfaces.*;

    public class RollCallDialogMechanic extends DialogGenerationMechanic implements IDialogGenerationMechanic
    {
        protected boolean m_pendingReopen ;
        private String m_rollCallName ;
        private static boolean s_seenPreCollectDialog =false ;
        private static Array allowedDialogClasses =.get(GovernorsRunFailDialog ,GovernorsRunSuccessDialog) ;

        public  RollCallDialogMechanic ()
        {
            this.m_pendingReopen = false;
            return;
        }//end

         public boolean  canPopDialog ()
        {
            this.m_rollCallName = m_config.params.get("name");
            _loc_1 = (RollCallDataMechanic)MechanicManager.getInstance().getMechanicInstance(m_owner,"rollCall",MechanicManager.ALL)
            _loc_2 =Global.player.level <_loc_1.requiredLevel ;
            _loc_3 = this.isExperimentGated ();
            if ((ReactivationManager.isPlayerReactive() || _loc_3 || _loc_2) && !Global.isVisiting())
            {
                return false;
            }
            _loc_4 = _loc_1.getState ();
            _loc_5 =             !super.canPopDialog();
            _loc_6 = _loc_4==RollCallDataMechanic.STATE_IN_PROGRESS ;
            _loc_7 = _loc_1.canCollect(Global.player.uid);
            _loc_8 = _loc_4==RollCallDataMechanic.STATE_FINISHED;
            if (_loc_1.isActiveObject() && !_loc_5)
            {
                if (!Global.isVisiting())
                {
                    if (_loc_1.canPerformRollCall())
                    {
                        RollCallManager.debugSample("IntroMechanicTrue", "canPerformNotHarvestable");
                        return true;
                    }
                    if (_loc_7 && !s_seenPreCollectDialog)
                    {
                        if (!this.checkCollection())
                        {
                            this.makeRollCallFailDialog();
                        }
                        else
                        {
                            this.makeRollCallSuccessDialog();
                        }
                        s_seenPreCollectDialog = true;
                    }
                    else if (_loc_7 || _loc_6)
                    {
                        this.makeRollCallDialog();
                        return false;
                    }
                    if (_loc_8)
                    {
                        return false;
                    }
                }
                else if (_loc_1.isCrewMember(Global.player.uid) && (_loc_6 || _loc_7))
                {
                    this.makeRollCallDialog();
                }
            }
            return false;
        }//end

        private boolean  isExperimentGated ()
        {
            Array _loc_2 =null ;
            String _loc_3 =null ;
            _loc_1 =Global.experimentManager.getVariant(m_config.params.get( "experiment") );
            if (m_config.params.get("allowedVariants"))
            {
                _loc_2 = m_config.params.get("allowedVariants").split(",");
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_3 = _loc_2.get(i0);

                    if (_loc_1 == parseInt(_loc_3))
                    {
                        return false;
                    }
                }
            }
            return true;
        }//end

        protected boolean  isUpgradable ()
        {
            boolean _loc_1 =true ;
            _loc_2 = (Municipal)m_owner
            if (_loc_2)
            {
                _loc_1 = _loc_2.getLogicComponent().passesExperimentGate();
            }
            return _loc_1 && m_owner.isUpgradePossible();
        }//end

         public DisplayObject  instantiateDialog ()
        {
            DisplayObject _loc_2 =null ;
            _loc_1 = getDefinitionByName(m_config.params.get( "introDialog") )as Class ;
            _loc_3 = (RollCallDataMechanic)MechanicManager.getInstance().getMechanicInstance(m_owner,"rollCall",MechanicManager.ALL)
            _loc_4 = _loc_3.getRollCallDuration ();
            _loc_2 = new _loc_1(m_owner, this.m_rollCallName, _loc_4, "", "", GenericDialogView.TYPE_CUSTOM_OK, this.dialogCloseCallback, "RollCallIntroDialog", "", false, 0, "", null, ZLoc.t("Dialogs", "RollCall_" + this.m_rollCallName + "_startrollcall"));
            Global.ui.displayLightbox(true, UI.MASK_ALL_UI, true);
            return _loc_2;
        }//end

        private void  makeRollCallDialog ()
        {
            _loc_1 = (RollCallDataMechanic)MechanicManager.getInstance().getMechanicInstance(m_owner,"rollCall",MechanicManager.ALL)
            RollCallDialog _loc_2 =new RollCallDialog(m_owner ,this.m_rollCallName ,_loc_1 ,this.dialogCloseCallback );
            Global.ui.displayLightbox(true, UI.MASK_ALL_UI, true);
            UI.displayPopup(_loc_2);
            return;
        }//end

        private void  makeRollCallSuccessDialog ()
        {
            _loc_1 = m_config.params.get( "successDialog") ;
            _loc_2 = (Class)getDefinitionByName(_loc_1)
            _loc_3 = new _loc_2(this.m_rollCallName ,this.successDialogCallback );
            UI.displayPopup(_loc_3);
            return;
        }//end

        private void  successDialogCallback (GenericPopupEvent event )
        {
            if (event.button == GenericDialogView.YES)
            {
                this.makeRollCallDialog();
            }
            return;
        }//end

        private void  makeRollCallFailDialog ()
        {
            _loc_1 = m_config.params.get( "failDialog") ;
            _loc_2 = (Class)getDefinitionByName(_loc_1)
            _loc_3 = new _loc_2(this.m_rollCallName ,this.rollCallFailCallback );
            UI.displayPopup(_loc_3);
            return;
        }//end

        private void  rollCallFailCallback (GenericPopupEvent event )
        {
            _loc_2 = (RollCallDataMechanic)MechanicManager.getInstance().getMechanicInstance(m_owner,"rollCall",MechanicManager.ALL)
            _loc_2.setFinished();
            return;
        }//end

        private boolean  checkCollection ()
        {
            Object _loc_5 =null ;
            Object _loc_6 =null ;
            int _loc_7 =0;
            _loc_1 = (TieredDooberMechanic)MechanicManager.getInstance().getMechanicInstance(m_owner,"rollCallTieredDooberValue",MechanicManager.ALL)
            _loc_2 = (RollCallDataMechanic)MechanicManager.getInstance().getMechanicInstance(m_owner,"rollCall",MechanicManager.ALL)
            _loc_3 = _loc_2.getCrewState ();
            int _loc_4 =0;
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_5 = _loc_3.get(i0);

                if (_loc_5.checkedIn == true)
                {
                    _loc_4++;
                }
            }
            _loc_6 = _loc_1.getTierInfo(1);
            _loc_7 = _loc_6.num;
            if (_loc_4 >= _loc_7)
            {
                return true;
            }
            return false;
        }//end

        protected void  dialogCloseCallback (GenericPopupEvent event )
        {
            RollCallDataMechanic _loc_2 =null ;
            Global.ui.displayLightbox(false);
            m_owner.dispatchEvent(new Event(MapResource.REPLACE_CONTENT));
            if (event.button == GenericDialogView.YES)
            {
                _loc_2 =(RollCallDataMechanic) MechanicManager.getInstance().getMechanicInstance(m_owner, "rollCall", MechanicManager.ALL);
                if (Global.isVisiting() == false && _loc_2.canCollect(Global.player.uid))
                {
                    if (!this.checkCollection())
                    {
                        this.makeRollCallFailDialog();
                    }
                    else
                    {
                        this.makeRollCallSuccessDialog();
                    }
                }
                if (_loc_2.canPerformRollCall())
                {
                    _loc_2.startRollCall();
                    this.m_pendingReopen = true;
                    this.makeRollCallDialog();
                }
            }
            return;
        }//end

    }



