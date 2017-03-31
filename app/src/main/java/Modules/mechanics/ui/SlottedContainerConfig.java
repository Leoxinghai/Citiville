package Modules.mechanics.ui;

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

import Display.MarketUI.*;
import Mechanics.*;
import Modules.mechanics.ui.items.*;
//import flash.utils.*;

    public class SlottedContainerConfig
    {
        protected MechanicConfigData m_config =null ;
        protected Class m_catalogUI =null ;
        protected String m_key =null ;
        protected String m_overrideTitle =null ;
        protected int m_tabCount =0;
        protected String m_tabWidth =null ;
        protected GateItem m_tabGate =null ;
        protected int m_slotsPerTab =0;
        protected GateItem m_slotGate =null ;
        protected MechanicItem m_storageMechanic =null ;
        protected MechanicItem m_upgradeMechanic =null ;
        protected boolean m_hasMysteryItem =false ;
        protected Dictionary m_titles =null ;
        protected Dictionary m_tooltips =null ;
        protected Dictionary m_buttons =null ;

        public  SlottedContainerConfig (MechanicConfigData param1 )
        {
            this.m_config = param1;
            _loc_2 = this.m_config.params.get( "catalog") ;
            if (_loc_2 == "SlottedContainer")
            {
                this.configureSlottedContainer();
            }
            else if (_loc_2 == "Mall")
            {
                this.configureMall();
            }
            else if (_loc_2 == "Neighborhood")
            {
                this.configureNeighborhood();
            }
            else if (_loc_2 == "MunicipalCenter")
            {
                this.configureMunicipalCenter();
            }
            else
            {
                this.configureSlottedContainer();
            }
            return;
        }//end

        public Class  catalogUI ()
        {
            return this.m_catalogUI;
        }//end

        public String  key ()
        {
            return this.m_key;
        }//end

        public String  overrideTitle ()
        {
            return this.m_overrideTitle;
        }//end

        public int  tabCount ()
        {
            return this.m_tabCount;
        }//end

        public String  tabWidth ()
        {
            return this.m_tabWidth;
        }//end

        public GateItem  tabGate ()
        {
            return this.m_tabGate;
        }//end

        public int  slotsPerTab ()
        {
            return this.m_slotsPerTab;
        }//end

        public GateItem  slotGate ()
        {
            return this.m_slotGate;
        }//end

        public MechanicItem  storageMechanic ()
        {
            return this.m_storageMechanic;
        }//end

        public MechanicItem  upgradeMechanic ()
        {
            return this.m_upgradeMechanic;
        }//end

        public boolean  hasMysteryItem ()
        {
            return this.m_hasMysteryItem;
        }//end

        public ZlocItem  getTitleZlocItem (String param1 )
        {
            ZlocItem _loc_2 =null ;
            if (this.m_titles.get(param1))
            {
                _loc_2 = this.m_titles.get(param1);
            }
            return _loc_2;
        }//end

        public TooltipItem  getTooltipItem (String param1 )
        {
            TooltipItem _loc_2 =null ;
            if (this.m_tooltips.get(param1))
            {
                _loc_2 = this.m_tooltips.get(param1);
            }
            return _loc_2;
        }//end

        public ZlocItem  getButtonZlocItem (String param1 )
        {
            ZlocItem _loc_2 =null ;
            if (this.m_buttons.get(param1))
            {
                _loc_2 = this.m_buttons.get(param1);
            }
            return _loc_2;
        }//end

        public void  configureSlottedContainer ()
        {
            this.m_catalogUI = SlottedContainerCatalogUI;
            this.m_key = "SlottedContainer";
            this.m_overrideTitle = "SlottedContainer_title";
            this.m_tabCount = 3;
            this.m_tabWidth = "default";
            this.m_tabGate = new GateItem("inventory", "pre_upgrade");
            this.m_slotsPerTab = 5;
            this.m_slotGate = new GateItem("crew", "storage");
            this.m_storageMechanic = new MechanicItem("slots", "GMMechanicStore");
            this.m_upgradeMechanic = new MechanicItem("upgrade", "GMPlay");
            this.m_hasMysteryItem = false;
            this.m_titles = new Dictionary();
            this.m_titles.put("buy",  new ZlocItem("Dialogs", "SlottedContainerQuestionBuy"));
            this.m_titles.put("tab",  new ZlocItem("Dialogs", "SlottedContainerTabTitle"));
            this.m_titles.put("mystery",  new ZlocItem("Dialogs", "SlottedContainerCellFooterMystery"));
            TooltipItem _loc_1 =new TooltipItem ();
            _loc_1.type = "description";
            _loc_1.title = new ZlocItem("Dialogs", "SlottedContainerCellTitleCrewable");
            _loc_1.text = new ZlocItem("Dialogs", "SlottedContainerCellTextCrewable");
            _loc_1.gate = new ZlocItem("Dialogs", "SlottedContainerCellGateCrewable");
            TooltipItem _loc_2 =new TooltipItem ();
            _loc_2.type = "description";
            _loc_2.title = new ZlocItem("Dialogs", "SlottedContainerCellTitleEmpty");
            _loc_2.text = new ZlocItem("Dialogs", "SlottedContainerCellTextEmpty");
            _loc_2.gate = new ZlocItem("Dialogs", "SlottedContainerCellGateEmpty");
            TooltipItem _loc_3 =new TooltipItem ();
            _loc_3.type = TooltipType.BUSINESS;
            _loc_3.title = null;
            _loc_3.text = null;
            _loc_3.gate = null;
            TooltipItem _loc_4 =new TooltipItem ();
            _loc_4.type = "description";
            _loc_4.title = new ZlocItem("Dialogs", "SlottedContainerCellTitleMysteryLocked");
            _loc_4.text = new ZlocItem("Dialogs", "SlottedContainerCellTextMysteryLocked");
            _loc_4.gate = new ZlocItem("Dialogs", "SlottedContainerCellGateMysteryLocked");
            TooltipItem _loc_5 =new TooltipItem ();
            _loc_5.type = "description";
            _loc_5.title = new ZlocItem("Dialogs", "SlottedContainerCellTitleMysteryUnlocked");
            _loc_5.text = new ZlocItem("Dialogs", "SlottedContainerCellTextMysteryUnlocked");
            _loc_5.gate = null;
            TooltipItem _loc_6 =new TooltipItem ();
            _loc_6.type = "description";
            _loc_6.title = new ZlocItem("Dialogs", "SlottedContainerCellTitleMysteryAwarded");
            _loc_6.text = new ZlocItem("Dialogs", "SlottedContainerCellTextMysteryAwarded");
            _loc_6.gate = null;
            TooltipItem _loc_7 =new TooltipItem ();
            _loc_7.type = "description";
            _loc_7.title = new ZlocItem("Dialogs", "SlottedContainerTabTitle");
            _loc_7.text = new ZlocItem("Dialogs", "SlottedContainerTabText");
            _loc_7.gate = new ZlocItem("Dialogs", "SlottedContainerTabGate");
            this.m_tooltips = new Dictionary();
            this.m_tooltips.put("crewable",  _loc_1);
            this.m_tooltips.put("empty",  _loc_2);
            this.m_tooltips.put("filled",  _loc_3);
            this.m_tooltips.put("mysterylocked",  _loc_4);
            this.m_tooltips.put("mysteryunlocked",  _loc_5);
            this.m_tooltips.put("mysteryawarded",  _loc_6);
            this.m_tooltips.put("tab",  _loc_7);
            this.m_buttons = new Dictionary();
            this.m_buttons.put("ask",  new ZlocItem("Dialogs", "Ask"));
            this.m_buttons.put("cancel",  new ZlocItem("Dialogs", "Cancel"));
            this.m_buttons.put("fill",  new ZlocItem("Dialogs", "Fill"));
            this.m_buttons.put("unlock",  new ZlocItem("Dialogs", "Unlock"));
            this.m_buttons.put("upgrade",  new ZlocItem("Dialogs", "Upgrade"));
            return;
        }//end

        public void  configureMall ()
        {
            this.m_catalogUI = SlottedContainerCatalogUI;
            this.m_key = "Mall";
            this.m_overrideTitle = "Mall_title";
            this.m_tabCount = 5;
            this.m_tabWidth = "auto";
            this.m_tabGate = new GateItem("inventory", "pre_upgrade");
            this.m_slotsPerTab = 4;
            this.m_slotGate = new GateItem("crew", "storage");
            this.m_storageMechanic = new MechanicItem("slots", "GMMechanicStore");
            this.m_upgradeMechanic = new MechanicItem("upgrade", "GMPlay");
            this.m_hasMysteryItem = true;
            this.m_titles = new Dictionary();
            this.m_titles.put("buy",  new ZlocItem("Dialogs", "MallQuestionBuy"));
            this.m_titles.put("tab",  new ZlocItem("Dialogs", "MallTabTitle"));
            this.m_titles.put("mystery",  new ZlocItem("Dialogs", "MallCellFooterMystery"));
            TooltipItem _loc_1 =new TooltipItem ();
            _loc_1.type = "description";
            _loc_1.title = new ZlocItem("Dialogs", "MallCellTitleCrewable");
            _loc_1.text = new ZlocItem("Dialogs", "MallCellTextCrewable");
            _loc_1.gate = new ZlocItem("Dialogs", "MallCellGateCrewable");
            TooltipItem _loc_2 =new TooltipItem ();
            _loc_2.type = "description";
            _loc_2.title = new ZlocItem("Dialogs", "MallCellTitleEmpty");
            _loc_2.text = new ZlocItem("Dialogs", "MallCellTextEmpty");
            _loc_2.gate = new ZlocItem("Dialogs", "MallCellGateEmpty");
            TooltipItem _loc_3 =new TooltipItem ();
            _loc_3.type = TooltipType.BUSINESS;
            _loc_3.title = null;
            _loc_3.text = null;
            _loc_3.gate = null;
            TooltipItem _loc_4 =new TooltipItem ();
            _loc_4.type = "description";
            _loc_4.title = new ZlocItem("Dialogs", "MallCellTitleMysteryLocked");
            _loc_4.text = new ZlocItem("Dialogs", "MallCellTextMysteryLocked");
            _loc_4.gate = new ZlocItem("Dialogs", "MallCellGateMysteryLocked");
            TooltipItem _loc_5 =new TooltipItem ();
            _loc_5.type = "description";
            _loc_5.title = new ZlocItem("Dialogs", "MallCellTitleMysteryUnlocked");
            _loc_5.text = new ZlocItem("Dialogs", "MallCellTextMysteryUnlocked");
            _loc_5.gate = null;
            TooltipItem _loc_6 =new TooltipItem ();
            _loc_6.type = "description";
            _loc_6.title = new ZlocItem("Dialogs", "MallCellTitleMysteryAwarded");
            _loc_6.text = new ZlocItem("Dialogs", "MallCellTextMysteryAwarded");
            _loc_6.gate = null;
            TooltipItem _loc_7 =new TooltipItem ();
            _loc_7.type = "description";
            _loc_7.title = new ZlocItem("Dialogs", "MallTabTitle");
            _loc_7.text = new ZlocItem("Dialogs", "MallTabText");
            _loc_7.gate = new ZlocItem("Dialogs", "MallTabGate");
            this.m_tooltips = new Dictionary();
            this.m_tooltips.put("crewable",  _loc_1);
            this.m_tooltips.put("empty",  _loc_2);
            this.m_tooltips.put("filled",  _loc_3);
            this.m_tooltips.put("mysterylocked",  _loc_4);
            this.m_tooltips.put("mysteryunlocked",  _loc_5);
            this.m_tooltips.put("mysteryawarded",  _loc_6);
            this.m_tooltips.put("tab",  _loc_7);
            this.m_buttons = new Dictionary();
            this.m_buttons.put("ask",  new ZlocItem("Dialogs", "Ask"));
            this.m_buttons.put("cancel",  new ZlocItem("Dialogs", "Cancel"));
            this.m_buttons.put("fill",  new ZlocItem("Dialogs", "Fill"));
            this.m_buttons.put("unlock",  new ZlocItem("Dialogs", "Unlock"));
            this.m_buttons.put("upgrade",  new ZlocItem("Dialogs", "Upgrade"));
            return;
        }//end

        public void  configureNeighborhood ()
        {
            this.m_catalogUI = SlottedContainerCatalogUI;
            this.m_key = "Neighborhood";
            this.m_overrideTitle = "Neighborhood_title";
            this.m_tabCount = 3;
            this.m_tabWidth = "default";
            this.m_tabGate = new GateItem("inventory", "pre_upgrade");
            this.m_slotsPerTab = 5;
            this.m_slotGate = new GateItem("crew", "storage");
            this.m_storageMechanic = new MechanicItem("slots", "GMMechanicStore");
            this.m_upgradeMechanic = new MechanicItem("upgrade", "GMPlay");
            this.m_hasMysteryItem = false;
            this.m_titles = new Dictionary();
            this.m_titles.put("buy",  new ZlocItem("Dialogs", "NeighborhoodQuestionBuy"));
            this.m_titles.put("tab",  new ZlocItem("Dialogs", "NeighborhoodTabTitle"));
            this.m_titles.put("mystery",  new ZlocItem("Dialogs", "NeighborhoodCellFooterMystery"));
            TooltipItem _loc_1 =new TooltipItem ();
            _loc_1.type = "description";
            _loc_1.title = new ZlocItem("Dialogs", "NeighborhoodCellTitleCrewable");
            _loc_1.text = new ZlocItem("Dialogs", "NeighborhoodCellTextCrewable");
            _loc_1.gate = new ZlocItem("Dialogs", "NeighborhoodCellGateCrewable");
            TooltipItem _loc_2 =new TooltipItem ();
            _loc_2.type = "description";
            _loc_2.title = new ZlocItem("Dialogs", "NeighborhoodCellTitleEmpty");
            _loc_2.text = new ZlocItem("Dialogs", "NeighborhoodCellTextEmpty");
            _loc_2.gate = new ZlocItem("Dialogs", "NeighborhoodCellGateEmpty");
            TooltipItem _loc_3 =new TooltipItem ();
            _loc_3.type = TooltipType.RESIDENCE;
            _loc_3.title = null;
            _loc_3.text = null;
            _loc_3.gate = null;
            TooltipItem _loc_4 =new TooltipItem ();
            _loc_4.type = "description";
            _loc_4.title = new ZlocItem("Dialogs", "NeighborhoodCellTitleMysteryLocked");
            _loc_4.text = new ZlocItem("Dialogs", "NeighborhoodCellTextMysteryLocked");
            _loc_4.gate = new ZlocItem("Dialogs", "NeighborhoodCellGateMysteryLocked");
            TooltipItem _loc_5 =new TooltipItem ();
            _loc_5.type = "description";
            _loc_5.title = new ZlocItem("Dialogs", "NeighborhoodCellTitleMysteryUnlocked");
            _loc_5.text = new ZlocItem("Dialogs", "NeighborhoodCellTextMysteryUnlocked");
            _loc_5.gate = null;
            TooltipItem _loc_6 =new TooltipItem ();
            _loc_6.type = "description";
            _loc_6.title = new ZlocItem("Dialogs", "NeighborhoodCellTitleMysteryAwarded");
            _loc_6.text = new ZlocItem("Dialogs", "NeighborhoodCellTextMysteryAwarded");
            _loc_6.gate = null;
            TooltipItem _loc_7 =new TooltipItem ();
            _loc_7.type = "description";
            _loc_7.title = new ZlocItem("Dialogs", "NeighborhoodTabTitle");
            _loc_7.text = new ZlocItem("Dialogs", "NeighborhoodTabText");
            _loc_7.gate = new ZlocItem("Dialogs", "NeighborhoodTabGate");
            this.m_tooltips = new Dictionary();
            this.m_tooltips.put("crewable",  _loc_1);
            this.m_tooltips.put("empty",  _loc_2);
            this.m_tooltips.put("filled",  _loc_3);
            this.m_tooltips.put("mysterylocked",  _loc_4);
            this.m_tooltips.put("mysteryunlocked",  _loc_5);
            this.m_tooltips.put("mysteryawarded",  _loc_6);
            this.m_tooltips.put("tab",  _loc_7);
            this.m_buttons = new Dictionary();
            this.m_buttons.put("ask",  new ZlocItem("Dialogs", "Ask"));
            this.m_buttons.put("cancel",  new ZlocItem("Dialogs", "Cancel"));
            this.m_buttons.put("fill",  new ZlocItem("Dialogs", "Fill"));
            this.m_buttons.put("unlock",  new ZlocItem("Dialogs", "Unlock"));
            this.m_buttons.put("upgrade",  new ZlocItem("Dialogs", "Upgrade"));
            return;
        }//end

        public void  configureMunicipalCenter ()
        {
            this.m_catalogUI = MunicipalCenterCatalogUI;
            this.m_key = "MunicipalCenter";
            this.m_overrideTitle = "MunicipalCenter_title";
            this.m_tabCount = 3;
            this.m_tabWidth = "default";
            this.m_tabGate = new GateItem("inventory", "pre_upgrade");
            this.m_slotsPerTab = 4;
            this.m_slotGate = new GateItem("crew", "storage");
            this.m_storageMechanic = new MechanicItem("slots", "GMMechanicStore");
            this.m_upgradeMechanic = new MechanicItem("upgrade", "GMPlay");
            this.m_hasMysteryItem = false;
            this.m_titles = new Dictionary();
            this.m_titles.put("buy",  new ZlocItem("Dialogs", "MunicipalCenterQuestionBuy"));
            this.m_titles.put("tab",  new ZlocItem("Dialogs", "MunicipalCenterTabTitle"));
            this.m_titles.put("mystery",  new ZlocItem("Dialogs", "MunicipalCenterCellFooterMystery"));
            TooltipItem _loc_1 =new TooltipItem ();
            _loc_1.type = "description";
            _loc_1.title = new ZlocItem("Dialogs", "MunicipalCenterCellTitleCrewable");
            _loc_1.text = new ZlocItem("Dialogs", "MunicipalCenterCellTextCrewable");
            _loc_1.gate = new ZlocItem("Dialogs", "MunicipalCenterCellGateCrewable");
            TooltipItem _loc_2 =new TooltipItem ();
            _loc_2.type = "description";
            _loc_2.title = new ZlocItem("Dialogs", "MunicipalCenterCellTitleEmpty");
            _loc_2.text = new ZlocItem("Dialogs", "MunicipalCenterCellTextEmpty");
            _loc_2.gate = new ZlocItem("Dialogs", "MunicipalCenterCellGateEmpty");
            TooltipItem _loc_3 =new TooltipItem ();
            _loc_3.type = TooltipType.MUNICIPAL;
            _loc_3.title = null;
            _loc_3.text = null;
            _loc_3.gate = null;
            TooltipItem _loc_4 =new TooltipItem ();
            _loc_4.type = "description";
            _loc_4.title = new ZlocItem("Dialogs", "MunicipalCenterCellTitleMysteryLocked");
            _loc_4.text = new ZlocItem("Dialogs", "MunicipalCenterCellTextMysteryLocked");
            _loc_4.gate = new ZlocItem("Dialogs", "MunicipalCenterCellGateMysteryLocked");
            TooltipItem _loc_5 =new TooltipItem ();
            _loc_5.type = "description";
            _loc_5.title = new ZlocItem("Dialogs", "MunicipalCenterCellTitleMysteryUnlocked");
            _loc_5.text = new ZlocItem("Dialogs", "MunicipalCenterCellTextMysteryUnlocked");
            _loc_5.gate = null;
            TooltipItem _loc_6 =new TooltipItem ();
            _loc_6.type = "description";
            _loc_6.title = new ZlocItem("Dialogs", "MunicipalCenterCellTitleMysteryAwarded");
            _loc_6.text = new ZlocItem("Dialogs", "MunicipalCenterCellTextMysteryAwarded");
            _loc_6.gate = null;
            TooltipItem _loc_7 =new TooltipItem ();
            _loc_7.type = "description";
            _loc_7.title = new ZlocItem("Dialogs", "MunicipalCenterTabTitle");
            _loc_7.text = new ZlocItem("Dialogs", "MunicipalCenterTabText");
            _loc_7.gate = new ZlocItem("Dialogs", "MunicipalCenterTabGate");
            this.m_tooltips = new Dictionary();
            this.m_tooltips.put("crewable",  _loc_1);
            this.m_tooltips.put("empty",  _loc_2);
            this.m_tooltips.put("filled",  _loc_3);
            this.m_tooltips.put("mysterylocked",  _loc_4);
            this.m_tooltips.put("mysteryunlocked",  _loc_5);
            this.m_tooltips.put("mysteryawarded",  _loc_6);
            this.m_tooltips.put("tab",  _loc_7);
            this.m_buttons = new Dictionary();
            this.m_buttons.put("ask",  new ZlocItem("Dialogs", "Ask"));
            this.m_buttons.put("cancel",  new ZlocItem("Dialogs", "Cancel"));
            this.m_buttons.put("fill",  new ZlocItem("Dialogs", "Fill"));
            this.m_buttons.put("unlock",  new ZlocItem("Dialogs", "Unlock"));
            this.m_buttons.put("upgrade",  new ZlocItem("Dialogs", "Upgrade"));
            return;
        }//end

    }



