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
import Classes.sim.*;
import Display.*;
import Display.MarketUI.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.mechanics.ui.*;

    public class SlottedContainerCatalogMechanic implements ICatalogMechanic, IMultiPickSupporter, IToolTipModifier
    {
        protected MechanicMapResource m_owner ;
        protected MechanicConfigData m_config ;

        public  SlottedContainerCatalogMechanic ()
        {
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
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

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            SlottedContainerConfig _loc_5 =null ;
            ItemCatalogUI _loc_6 =null ;
            TabbedCatalogUI _loc_7 =null ;
            String _loc_8 =null ;
            String _loc_9 =null ;
            Array _loc_10 =null ;
            String _loc_11 =null ;
            boolean _loc_12 =false ;
            boolean _loc_13 =false ;
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            if (this.canShowCatalog())
            {
                if (this.m_config.params.get("blockOthers") == "true")
                {
                    _loc_4 = true;
                }
                _loc_5 = new SlottedContainerConfig(this.m_config);
                _loc_6 = new _loc_5.catalogUI(this.m_owner, _loc_5);
                _loc_7 =(TabbedCatalogUI) _loc_6;
                _loc_7.numTabs = _loc_5.tabCount;
                if (_loc_5.tabWidth == null)
                {
                    _loc_7.tabWidth = -1;
                }
                else if (_loc_5.tabWidth == "auto")
                {
                    _loc_7.allowAutoTabWidth = true;
                }
                else if (_loc_5.tabWidth == "default")
                {
                    _loc_7.tabWidth = -1;
                }
                else
                {
                    _loc_7.tabWidth = int(_loc_5.tabWidth);
                }
                _loc_8 = null;
                _loc_9 = null;
                _loc_10 = _loc_7.categoryNames;
                _loc_11 = _loc_7.overrideTitle;
                _loc_12 = false;
                _loc_13 = false;
                UI.clearTabbedCatalog();
                UI.displayTabbedCatalog(_loc_7, _loc_10, new CatalogParams(_loc_8).setItemName(_loc_9).setOverrideTitle(_loc_11));
                _loc_3 = true;
            }
            return new MechanicActionResult(_loc_3, !_loc_4, false);
        }//end

        public boolean  canShowCatalog ()
        {
            boolean _loc_1 =false ;
            if (this.m_owner instanceof ISlottedContainer)
            {
                _loc_1 = true;
            }
            return _loc_1;
        }//end

        public String  getPick ()
        {
            return this.m_config.params.get("pick");
        }//end

        public Array  getPicksToHide ()
        {
            return null;
        }//end

        public String  getToolTipStatus ()
        {
            Neighborhood _loc_2 =null ;
            double _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            Object _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            String _loc_9 =null ;
            String _loc_1 =null ;
            if (this.m_owner instanceof Neighborhood)
            {
                _loc_2 =(Neighborhood) this.m_owner;
                _loc_3 = Global.gameSettings().getNumber("populationScale", 1);
                _loc_4 = _loc_2.getPopulationYield() * _loc_3;
                _loc_5 = _loc_2.getPopulationMaxYield() * _loc_3;
                _loc_6 = {curPop:_loc_4, maxPop:_loc_5};
                _loc_7 = "TT_PopulationCurOfMax";
                if (Global.gameSettings().hasMultiplePopulations())
                {
                    _loc_8 = _loc_2.getPopulationType();
                    _loc_9 = PopulationHelper.getPopulationZlocType(_loc_8, true);
                    _loc_6.popType = _loc_9;
                    _loc_7 = "TT_PopulationTypeCurOfMax";
                }
                _loc_1 = ZLoc.t("Dialogs", _loc_7, _loc_6);
            }
            return _loc_1;
        }//end

        public String  getToolTipAction ()
        {
            _loc_1 = ZLoc.t("Items","warehouse_defaultToolTip");
            return _loc_1;
        }//end

        protected String  getInferredItemName ()
        {
            String _loc_1 ="";
            if (this.m_owner)
            {
                _loc_1 = this.m_owner.getItemName();
            }
            return _loc_1;
        }//end

        protected String  getInferredCatalogType ()
        {
            Item _loc_2 =null ;
            String _loc_1 ="new";
            if (this.m_owner)
            {
                _loc_2 = this.m_owner.getItem();
                if (_loc_2)
                {
                    _loc_1 = _loc_2.type;
                }
            }
            return _loc_1;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return this.m_config.params.get("blockOthers") == "true";
        }//end

    }



