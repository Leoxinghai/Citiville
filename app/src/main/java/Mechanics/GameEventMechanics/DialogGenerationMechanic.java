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
import Display.*;
import Display.DialogUI.*;
import Display.FriendRewardsUI.*;
import Engine.Managers.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.attractions.*;
import Modules.crew.ui.*;
import Modules.downtown.*;
import Modules.garden.ui.*;
import Modules.hotels.*;
import Modules.powerstations.*;
import Modules.streetFair.*;
import Modules.universities.ui.*;
import Modules.zoo.ui.*;
//import flash.display.*;
//import flash.utils.*;

    public class DialogGenerationMechanic implements IDialogGenerationMechanic, IMultiPickSupporter, IToolTipModifier
    {
        protected MechanicMapResource m_owner ;
        protected MechanicConfigData m_config ;
        private static Array dialogs =.get(GardenDialog ,ZooDialog ,FriendRewardsDialog ,ZooUnlockDialog ,RollCallIntroDialog ,RollCallDialog ,StreetFairDialog ,HotelInfographicDialog ,PowerStationInfographicDialog ,AttractionInfographicDialog ,MaintenanceDialog ,HotelsGuestDialog ,SocialBusinessDialog ,SocialBusinessGuestDialog ,UniversityLogoDialog ,CharacterDialog ,DownTownCenterDialog) ;

        public  DialogGenerationMechanic ()
        {
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return this.canPopDialog();
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return true;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            DisplayObject _loc_3 =null ;
            boolean _loc_4 =false ;
            boolean _loc_5 =false ;
            if (this.canPopDialog())
            {
                _loc_3 = this.instantiateDialog();
                _loc_4 = true;
                if (this.m_config.params.get("popImmediately") == "true")
                {
                    _loc_4 = false;
                }
                UI.displayPopup(_loc_3, _loc_4, this.m_config.type, true);
                this.trackDialog();
                _loc_5 = this.m_config.params.get("proceed") == "false" ? (false) : (true);
                return new MechanicActionResult(true, _loc_5, false);
            }
            else
            {
                return new MechanicActionResult(false, true, false);
            }
        }//end

        public DisplayObject  instantiateDialog ()
        {
            DisplayObject _loc_2 =null ;
            _loc_1 = getDefinitionByName(this.m_config.params.get( "dialogToPop") )as Class ;
            _loc_3 = this.getDataForDialog ();
            if (_loc_3)
            {
                _loc_2 = new _loc_1(this.m_owner, _loc_3);
            }
            else
            {
                _loc_2 = new _loc_1(this.m_owner);
            }
            return _loc_2;
        }//end

        public void  trackDialog ()
        {
            if (this.m_config.params.get("phylum"))
            {
                StatsManager.sample(100, this.m_config.params.get("counter"), this.m_config.params.get("kingdom"), this.m_config.params.get("phylum"));
            }
            else if (this.m_config.params.get("kingdom"))
            {
                StatsManager.sample(100, this.m_config.params.get("counter"), this.m_config.params.get("kingdom"));
            }
            else if (this.m_config.params.get("counter"))
            {
                StatsManager.sample(100, this.m_config.params.get("counter"));
            }
            return;
        }//end

        public boolean  canPopDialog ()
        {
            if (this.m_owner instanceof HarvestableResource)
            {
                if ((this.m_owner as HarvestableResource).isHarvestable())
                {
                    return false;
                }
            }
            if (this.m_config.params.get("skipIfNeedsRoad") == "true" && this.m_owner.isNeedingRoad)
            {
                return false;
            }
            return true;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        public Object  getDataForDialog ()
        {
            Object _loc_2 =null ;
            _loc_1 = this.m_config.params.get( "dataSourceType") ;
            if (_loc_1 && _loc_1.length > 0)
            {
                _loc_2 = this.m_owner.getDataForMechanic(_loc_1);
                return _loc_2;
            }
            return null;
        }//end

        public MechanicMapResource  getOwner ()
        {
            return this.m_owner;
        }//end

        public String  getPick ()
        {
            return this.m_config.params.get("pick");
        }//end

        public Array  getPicksToHide ()
        {
            return null;
        }//end

        public String  getToolTipAction ()
        {
            String _loc_1 ="";
            if (this.m_config && this.m_config.params && this.m_config.params.hasOwnProperty("tooltipAction"))
            {
                _loc_1 = ZLoc.t("Main", this.m_config.params.get("tooltipAction"));
            }
            return _loc_1;
        }//end

        public String  getToolTipStatus ()
        {
            String _loc_1 ="";
            if (this.m_config && this.m_config.params && this.m_config.params.hasOwnProperty("tooltipStatus"))
            {
                _loc_1 = ZLoc.t("Main", this.m_config.params.get("tooltipStatus"));
            }
            return _loc_1;
        }//end

    }



