package Display;

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
import Classes.util.*;
import Display.DialogUI.*;
import Engine.*;
import Engine.Managers.*;
import Events.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.net.*;
//import flash.utils.*;

    public class Market extends SlotDialog
    {
        private  String MARKET_COUNTER ="market";
        private  int MASTERY_BAR_MAX_WIDTH =54;
        private  String OUTOFRANGE ="outofrange";
        private String m_viewSource ="unknown";
        private String m_lastClickedType ="initial";
        private int m_iconOffset ;
        private int m_costOffset ;
        private String m_currentUpState ;
        protected String m_currentType ="tool";
        protected String m_currentSubType ="all";
        protected XMLList m_allCurrentSubTypes ;
        protected int m_subTypeIndex =1;
        private double m_xpInitY ;
        protected  boolean SHOW_LIMITED_CLOCK =false ;
        protected Object topButtons ;
        protected Object subcatButtons ;
        private Array m_progBarTooltips ;
        private boolean m_popFeedFlag =false ;
        private Class m_comingSoon ;
        private MovieClip m_soonPopup ;
        private int m_buyTileNumber =-1;
        private Array m_tileLoaders ;
        private WhatIsThisPopup m_whatIsThisDialog =null ;
        private String m_rewardName ="";
        private String m_rewardLink ="";
        protected Array m_icons ;
        protected String m_defaultIcon ;
        protected boolean m_showLimitedIcons =true ;
        private Function m_onCloseClickCallback =null ;
        protected Array specialItemsSortOrder ;
        protected Object experimentObject ;
        public static  String COST_COINS ="coins";
        public static  String COST_CASH ="cash";
        public static  String COST_BOTH ="both";
        private static  int NUM_SLOTS =4;
public static  int NUM_SUBSLOTS =6;
        private static boolean m_soundLoaded =false ;

        public  Market (String param1 ="unknown",String param2 ="tool",String param3 ="all")
        {
            this.m_currentUpState = GenericButton.UPSTATE;
            this.m_progBarTooltips = new Array();
            this.m_tileLoaders = new Array(NUM_SLOTS);
            this.m_icons = new Array();
            this.specialItemsSortOrder = new Array();
            if (!m_soundLoaded)
            {
                this.loadPlantingSounds();
            }
            this.m_viewSource = param1;
            this.m_currentType = param2;
            this.m_currentSubType = param3;
            m_dialogAsset = "assets/dialogs/FV_Market_Mega.swf";
            return;
        }//end

         protected void  showTween ()
        {
            boolean _loc_2 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = _loc_2;
            this.visible = true;
            centerPopup();
            contentClip = (MovieClip)m_content
            MovieClipUtil .playAndStop (contentClip ,1,-1,false ,void  ()
            {
                boolean _loc_1 =true ;
                this.mouseChildren = true;
                this.mouseEnabled = _loc_1;
                return;
            }//end
            );
            return;
        }//end

         protected void  hideTween (Function param1 )
        {
            MovieClip _loc_2 =null ;
            boolean _loc_3 =false ;
            this.mouseChildren = false;
            this.mouseEnabled = _loc_3;
            if (m_content == null)
            {
                if (param1 != null)
                {
                    param1();
                }
            }
            else
            {
                _loc_2 =(MovieClip) m_content;
                MovieClipUtil.playBackwardsAndStop(_loc_2, -1, 1, false, param1);
            }
            Sounds.play("dialogClose");
            return;
        }//end

        public void  setCloseClickCallback (Function param1 )
        {
            this.m_onCloseClickCallback = param1;
            return;
        }//end

         protected int  getNumSlots ()
        {
            return NUM_SLOTS;
        }//end

         protected void  onLoadComplete ()
        {
            XML icon ;
            XML objectXML ;
            MovieClip btn_mc ;
            GenericButton gb ;
            String newText ;
            String btnName ;
            String subText ;
            ToolTip tooltip ;
            MovieClip mc ;
            m_window =(MovieClip) m_loader.content;
            this.topButtons = new Array();
            this.subcatButtons = new Array();
            icons = Global.gameSettings().getLimitedIcons();
            int _loc_2 =0;
            _loc_3 = icons.icon;
            for(int i0 = 0; i0 < icons.icon.size(); i0++)
            {
            		icon = icons.icon.get(i0);


                this.m_icons.push(icon.@name.toString());
            }
            if (icons.@defaultIcon && icons.@defaultIcon.toString() != "")
            {
                this.m_defaultIcon = icons.@defaultIcon.toString();
            }
            objects = Global.gameSettings().getMenuItems();
            this.specialItemsSortOrder = this.xmlListToArray(objects, "type");
            int cntr ;
            int objectIdx ;
            while (objectIdx < objects.length())
            {

                objectXML = objects.get(objectIdx);
                btn_mc = m_window.get("cat" + cntr + "_bt");
                cntr = (cntr + 1);
                if (btn_mc != null)
                {
                    gb = new GenericButton(btn_mc, this.onMenuBarClick);
                    gb.m_overlay.name = objectXML.@type + "_bt";
                    gb.enableHitState(true);
                    this.topButtons.put(objectXML.@type,  gb);
                    newText = ZLoc.t("Dialogs", objectXML.@type + "_menu");
                    gb.text = newText;
                }
                if (this.m_showLimitedIcons)
                {
                    this.checkTopRowLimitedIcon(objectXML, btn_mc);
                }
                objectIdx = (objectIdx + 1);
            }
            if (m_window.special_bt && m_window.special_bt.visible == true)
            {
                if (this.m_showLimitedIcons)
                {
                    this.initializeSpecialButton();
                }
                else
                {
                    m_window.special_bt.visible = false;
                }
            }
            m_window.close_bt.addEventListener(MouseEvent.CLICK, this.onCloseClick);
            this.m_allCurrentSubTypes = Global.gameSettings().getSubMenuItemsByMenuType(this.m_currentType);
            int i ;
            while (i <= NUM_SUBSLOTS)
            {

                btnName = "subcat" + i + "_bt";
                btn_mc =(MovieClip) m_window.get(btnName);
                gb = new GenericButton(btn_mc, this.onSubMenuBarClick);
                gb.m_overlay.name = "subcat" + i.toString() + "_bt";
                gb.enableHitState(true);
                this.subcatButtons.put(btnName,  gb);
                i = (i + 1);
            }
            indx = Math.min((this.m_allCurrentSubTypes.length()+1),NUM_SUBSLOTS);
            int currentIndex ;
            i = 0;
            while (i <= indx)
            {

                btnName = "subcat" + i + "_bt";
                gb =(GenericButton) this.subcatButtons.get(btnName);
                subText = ZLoc.t("Dialogs", this.m_allCurrentSubTypes.get(i - 2).@subtype + "_submenu");
                if (this.m_allCurrentSubTypes.get(i - 2).@subtype == this.m_currentSubType)
                {
                    currentIndex = i;
                }
                gb.text = subText;
                gb.enableHitState(true);
                i = (i + 1);
            }
            this.showSubCatButtons(this.m_allCurrentSubTypes);
            m_window.arrowLt_bt.addEventListener(MouseEvent.CLICK, onLtArrowClick);
            m_window.arrowRt_bt.addEventListener(MouseEvent.CLICK, onRtArrowClick);
            m_tiles.push(m_window.tile1, m_window.tile2, m_window.tile3, m_window.tile4);
            if (m_tiles.get(0) && m_tiles.get(0).xp_tf)
            {
                this.m_xpInitY = m_tiles.get(0).xp_tf.y;
            }
            int tileId ;
            while (tileId < m_tiles.length())
            {

                tooltip = new ToolTip();
                tooltip.attachToolTip(m_tiles.get(tileId).progressBar_mc);
                tooltip.attachToolTip(m_tiles.get(tileId).progressTrack_mc);
                this.m_progBarTooltips.push(tooltip);
                tileId = (tileId + 1);
            }
            removeTiles();
            this.m_lastClickedType = "";
            this.pushNewData(this.m_currentType, this.m_currentSubType);
            this.onMenuBarClick(null, this.m_currentType, this.m_currentSubType);
            gb =(GenericButton) this.subcatButtons.get("subcat1_bt");
            gb.text = "All";
            gb =(GenericButton) this.subcatButtons.get("subcat" + currentIndex + "_bt");
            gb.state = GenericButton.HITSTATE;
            int compIdx ;
            while (compIdx < NUM_SLOTS && compIdx < m_data.length())
            {

                this.addComponent(compIdx);
                compIdx = (compIdx + 1);
            }
            this.count("viewInitial");
            double index ;
            while (index < NUM_SLOTS)
            {

                mc =(MovieClip) m_tiles.get(index);
                if (mc.costGroup_mc && mc.icon)
                {
                    this.m_iconOffset = mc.icon.x;
                    this.m_costOffset = mc.costGroup_mc.x;
                }
                if (this.m_costOffset != 0 && this.m_iconOffset != 0)
                {
                    break;
                }
                index = (index + 1);
            }
            addChild(m_window);
            button = this.topButtons.get(this.m_currentType) ;
            this.topButtons.forEach (void  (MovieClip param1 )
            {
                param1.state = GenericButton.UPSTATE;
                return;
            }//end
            );
            button.state = GenericButton.HITSTATE;
            return;
        }//end

        protected void  initializeSpecialButton ()
        {
            GenericButton _loc_2 =null ;
            String _loc_3 =null ;
            _loc_1 = m_window.get("special_bt") ;
            if (_loc_1 != null)
            {
                _loc_2 = new GenericButton(_loc_1, this.onMenuBarClick);
                _loc_2.m_overlay.name = "special_bt";
                _loc_2.enableHitState(true);
                this.topButtons.put("special",  _loc_2);
                _loc_3 = ZLoc.t("Dialogs", "special_menu");
                _loc_2.text = _loc_3;
            }
            return;
        }//end

        protected void  checkTopRowLimitedIcon (XML param1 ,MovieClip param2 )
        {
            int indx ;
            XML item ;
            double startDate ;
            double endDate ;
            String iconClassName ;
            Class classType ;
            Object andrewObj ;
            MovieClip displayIcon ;
            objectXML = param1;
            btn_mc = param2;
            if (objectXML.limitedIcon.length() > 0)
            {
                indx;
                while (indx < objectXML.limitedIcon.length())
                {

                    item = objectXML.limitedIcon.get(indx);
                    if (item.@startDate && item.@startDate.toString() != "")
                    {
                        startDate = Utilities.dateToNumber(item.@startDate.toString());
                        endDate = Utilities.dateToNumber(item.@endDate.toString());
                        if (startDate <= GlobalEngine.getTimer() && endDate >= GlobalEngine.getTimer())
                        {
                            iconClassName = item.@className;
                            try
                            {
                                andrewObj = getDefinitionByName(iconClassName);
                            }
                            catch (e:ReferenceError)
                            {
                                ;
                            }
                            classType =(Class) andrewObj;
                            if (classType)
                            {
                                displayIcon = new classType;
                                if (displayIcon)
                                {
                                    m_window.addChild(displayIcon);
                                    if (item.@iconScale.toString() != "")
                                    {
                                        _loc_4 = parseFloat(item.@iconScale.toString());
                                        displayIcon.scaleY = parseFloat(item.@iconScale.toString());
                                        displayIcon.scaleX = _loc_4;
                                    }
                                    displayIcon.x = btn_mc.x + btn_mc.width - displayIcon.width / 2 - 5;
                                    displayIcon.y = btn_mc.y - btn_mc.height / 2 * 0 - displayIcon.height / 2;
                                }
                            }
                        }
                    }
                    indx = (indx + 1);
                }
            }
            return;
        }//end

        protected void  pushNewData (String param1 ,String param2 ="all")
        {
            GenericButton _loc_3 =null ;
            XMLList _loc_7 =null ;
            Array _loc_8 =null ;
            int _loc_10 =0;
            int _loc_11 =0;
            Object _loc_12 =null ;
            XML _loc_13 =null ;
            boolean _loc_14 =false ;
            if (this.m_currentType && this.m_currentUpState != null && this.m_currentType != param1)
            {
                _loc_3 = this.topButtons.get(this.m_currentType);
                _loc_3.state = GenericButton.UPSTATE;
            }
            Array _loc_4 =new Array();
            Array _loc_5 =new Array();
            this.experimentObject = null;
            m_data = new Array();
            this.m_currentType = param1;
            m_tileSet = 0;
            _loc_3 =(GenericButton) this.topButtons.get(param1);
            this.m_currentUpState = _loc_3.state;
            _loc_6 =Global.player.homeIslandSize ;
            if (param1 == "special")
            {
                _loc_7 = Global.gameSettings().getSpecialItems();
                _loc_10 = 1;
                _loc_11 = 0;
                while (_loc_11 < _loc_7.length())
                {

                    _loc_7.get(_loc_11).@sortPriority = _loc_10 + 1;
                    _loc_11++;
                }
                _loc_8 = this.xmlListToArray(_loc_7);
                _loc_8.sort(this.compareSpecialItems);
            }
            else
            {
                _loc_7 = Global.gameSettings().getItemsByTypeXML(param1, param2);
                _loc_8 = this.xmlListToArray(_loc_7);
            }
            int _loc_9 =0;
            while (_loc_9 < _loc_8.length())
            {

                _loc_12 = new Object();
                _loc_13 = _loc_8.get(_loc_9);
                this.populateObjectXML(_loc_12, _loc_13);
                _loc_14 = this.determineItemVisibility(param1, _loc_12, _loc_13, _loc_6);
                if (_loc_14)
                {
                    if (_loc_12.isMysteryItem)
                    {
                        if (_loc_12.limitedTimeLeft)
                        {
                            if (_loc_12.limitedTimeLeft != this.OUTOFRANGE)
                            {
                                _loc_4 = this.addObjectToArray(_loc_12, _loc_4);
                            }
                        }
                        else
                        {
                            _loc_4 = this.addObjectToArray(_loc_12, _loc_4);
                        }
                    }
                    else if (_loc_12.limitedTimeLeft)
                    {
                        if (_loc_12.limitedTimeLeft != this.OUTOFRANGE)
                        {
                            _loc_4 = this.addObjectToArray(_loc_12, _loc_4);
                        }
                    }
                    else
                    {
                        _loc_5 = this.addObjectToArray(_loc_12, _loc_5);
                    }
                }
                _loc_9++;
            }
            m_data = _loc_4.concat(_loc_5);
            if (this.experimentObject != null)
            {
                m_data = .get(this.experimentObject).concat(m_data);
            }
            fixArrows();
            return;
        }//end

        protected boolean  determineItemVisibility (String param1 ,Object param2 ,XML param3 ,int param4 )
        {
            double _loc_5 =0;
            double _loc_10 =0;
            double _loc_11 =0;
            double _loc_12 =0;
            double _loc_6 =0;
            _loc_7 = param2.cost;
            _loc_8 = Math.floor(_loc_7 *Global.gameSettings().getNumber("sellBackRatio"));
            _loc_5 = Math.floor(_loc_7 * Global.gameSettings().getNumber("buyXpGainRatio"));
            _loc_6 = Math.max(_loc_5, Global.gameSettings().getNumber("buyXpGainMin"));
            if (param3.buyXp.toString() != "")
            {
                _loc_6 = param3.buyXp;
            }
            boolean _loc_9 =true ;
            if (param1 == "special")
            {
                param1 = param3.@type;
            }
            switch(param1)
            {
                case "tree":
                case "animal":
                {
                    if (param3.@buyable == true)
                    {
                        if (param3.plantXp.toString() != "")
                        {
                            _loc_6 = param3.plantXp;
                        }
                        else
                        {
                            _loc_6 = 0;
                        }
                        param2.xpGained = _loc_6;
                        if (param3.@license && param3.@license == -1)
                        {
                            _loc_10 = 0;
                            _loc_11 = GlobalEngine.getTimer() / 1000;
                            if (Global.player.licenses.hasOwnProperty(param2.name.toString()))
                            {
                                _loc_10 = Global.player.licenses.get(param2.name.toString());
                            }
                            _loc_12 = _loc_10 - _loc_11;
                            if (_loc_10 != 0 && _loc_12 > 0)
                            {
                            }
                            else
                            {
                                _loc_9 = false;
                            }
                        }
                    }
                    else
                    {
                        _loc_9 = false;
                    }
                    break;
                }
                case "change_farm":
                {
                    _loc_9 = param3.@subtype == "expand_farm" && param3.@size > param4;
                    break;
                }
                case "tool":
                {
                    if (Global.player.licenses.get(String(param3.@name)))
                    {
                        _loc_9 = false;
                    }
                    break;
                }
                default:
                {
                    _loc_6 = 0;
                    param2.coinYield = _loc_8;
                    _loc_9 = param3.@buyable == true;
                    break;
                    break;
                }
            }
            return _loc_9;
        }//end

        public Array  addObjectToArray (Object param1 ,Array param2 )
        {
            _loc_3 = param2;
            if (param1.experiment)
            {
                this.experimentObject = param1;
            }
            else
            {
                param2.push(param1);
            }
            return param2;
        }//end

        protected Array  xmlListToArray (XMLList param1 ,String param2)
        {
            XML _loc_4 =null ;
            Array _loc_3 =new Array ();
            if (param2 == "")
            {
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                		_loc_4 = param1.get(i0);

                    _loc_3.push(_loc_4);
                }
            }
            else
            {
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                		_loc_4 = param1.get(i0);

                    _loc_3.push(String(_loc_4.attribute(param2)));
                }
            }
            return _loc_3;
        }//end

        protected int  compareSpecialItems (XML param1 ,XML param2 )
        {
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_3 =0;
            _loc_4 = this.compareMysteryStatus(param1 ,param2 );
            if (this.compareMysteryStatus(param1, param2) == 0)
            {
                _loc_5 = this.compareLimitedStartDates(param1, param2);
                if (_loc_5 == 0)
                {
                    _loc_6 = this.compareSendButtonStatus(param1, param2);
                    if (_loc_6 == 0)
                    {
                        _loc_7 = this.compareTypeStatus(param1, param2);
                        if (_loc_7 == 0)
                        {
                            _loc_3 = this.compareSortPriority(param1, param2);
                        }
                        else
                        {
                            _loc_3 = _loc_7;
                        }
                    }
                    else
                    {
                        _loc_3 = _loc_6;
                    }
                }
                else
                {
                    _loc_3 = _loc_5;
                }
            }
            else
            {
                _loc_3 = _loc_4;
            }
            return _loc_3;
        }//end

        protected int  compareSortPriority (XML param1 ,XML param2 )
        {
            int _loc_3 =0;
            _loc_4 = (String)param1.@sortPriority.toString();
            _loc_5 = (String)param2.@sortPriority.toString();
            if (_loc_4 < _loc_5)
            {
                _loc_3 = -1;
            }
            else if (_loc_4 > _loc_5)
            {
                _loc_3 = 1;
            }
            return _loc_3;
        }//end

        protected int  compareTypeStatus (XML param1 ,XML param2 )
        {
            int _loc_3 =0;
            _loc_4 = (String)param1.@type.toString();
            _loc_5 = (String)param2.@type.toString();
            if (this.specialItemsSortOrder.indexOf(_loc_4) < this.specialItemsSortOrder.indexOf(_loc_5))
            {
                _loc_3 = -1;
            }
            else if (this.specialItemsSortOrder.indexOf(_loc_4) > this.specialItemsSortOrder.indexOf(_loc_5))
            {
                _loc_3 = 1;
            }
            return _loc_3;
        }//end

        protected int  compareSendButtonStatus (XML param1 ,XML param2 )
        {
            int _loc_3 =0;
            boolean _loc_4 =false ;
            boolean _loc_5 =false ;
            if (param1.@present && param1.@present == true)
            {
                _loc_4 = true;
            }
            if (param2.@present && param2.@present == true)
            {
                _loc_5 = true;
            }
            if (_loc_4 && !_loc_5)
            {
                _loc_3 = -1;
            }
            else if (_loc_5 && !_loc_4)
            {
                _loc_3 = 1;
            }
            return _loc_3;
        }//end

        protected int  compareLimitedStartDates (XML param1 ,XML param2 )
        {
            boolean _loc_4 =false ;
            boolean _loc_5 =false ;
            double _loc_6 =0;
            double _loc_7 =0;
            int _loc_3 =0;
            if (param1.limitedStart && param1.limitedStart.toString() != "")
            {
                _loc_4 = true;
            }
            if (param2.limitedStart && param2.limitedStart.toString() != "")
            {
                _loc_5 = true;
            }
            if (_loc_4 && _loc_5)
            {
                _loc_6 = Utilities.dateToNumber(param1.limitedStart.toString());
                _loc_7 = Utilities.dateToNumber(param2.limitedStart.toString());
                if (_loc_6 < _loc_7)
                {
                    _loc_3 = -1;
                }
                else if (_loc_6 > _loc_7)
                {
                    _loc_3 = 1;
                }
            }
            else if (_loc_4 && !_loc_5)
            {
                _loc_3 = 1;
            }
            else if (!_loc_4 && _loc_5)
            {
                _loc_3 = -1;
            }
            return _loc_3;
        }//end

        protected int  compareMysteryStatus (XML param1 ,XML param2 )
        {
            int _loc_3 =0;
            boolean _loc_4 =false ;
            boolean _loc_5 =false ;
            if (param1.lootTable && param1.lootTable.toString() != "")
            {
                _loc_4 = true;
            }
            if (param2.lootTable && param2.lootTable.toString() != "")
            {
                _loc_5 = true;
            }
            if (_loc_4 && !_loc_5)
            {
                _loc_3 = -1;
            }
            else if (_loc_5 && !_loc_4)
            {
                _loc_3 = 1;
            }
            return _loc_3;
        }//end

        protected void  populateObjectXML (Object param1 ,XML param2 )
        {
            double _loc_3 =0;
            double _loc_4 =0;
            double _loc_5 =0;
            String _loc_6 =null ;
            param1.name = param2.@name;
            param1.localizedName = Global.gameSettings().getItemFriendlyName(param1.name);
            param1.cost = param2.cost;
            param1.requiredLevel = param2.requiredLevel;
            param1.type = param2.@type;
            param1.category = param2.@category;
            if (param2.@subtype)
            {
                param1.subtype = param2.@subtype;
            }
            if (param2.child("unlockCost").length() > 0)
            {
                param1.unlockCost = param2.unlockCost;
            }
            if (param2.coinYield)
            {
                param1.coinYield = param2.coinYield;
            }
            if (param2.growTime)
            {
                param1.harvestTime = param2.growTime;
            }
            if (param2.capacity && param2.@subtype == "storage")
            {
                param1.capacity = param2.capacity;
            }
            if (param2.capacity && param2.@subtype == "shipping")
            {
                param1.capacity = param2.capacity;
            }
            if (param2.sizeX)
            {
                param1.sizeX = param2.sizeX;
            }
            if (param2.sizeY)
            {
                param1.sizeX = param2.sizeY;
            }
            if (param2.minNeighbors)
            {
                param1.minNeighbors = param2.minNeighbors;
            }
            if (param2.@unlock.toString() != "")
            {
                param1.unlock = param2.@unlock;
            }
            else
            {
                param1.unlock = Item.UNLOCK_LEVEL;
            }
            if (param2.cash)
            {
                param1.cash = param2.cash;
            }
            if (param2.size)
            {
                param1.size = param2.@size;
            }
            if (param2.@market.toString() != "")
            {
                param1.market = param2.@market;
            }
            else
            {
                param1.market = Market.COST_COINS;
            }
            if (param2.@experiment.toString() != "")
            {
                param1.experiment = param2.@experiment;
            }
            param1.whatIsThis = false;
            if (param2.@whatIsThis.toString() == "true")
            {
                param1.whatIsThis = true;
            }
            if (param2.lootTable && param2.lootTable.toString() != "")
            {
                param1.isMysteryItem = true;
            }
            else
            {
                param1.isMysteryItem = false;
            }
            if (param2.limitedIcon && param2.limitedIcon.toString() != "")
            {
                param1.limitedIcon = param2.limitedIcon.toString();
            }
            else if (param1.isMysteryItem)
            {
                param1.limitedIcon = "";
            }
            else
            {
                param1.limitedIcon = this.m_defaultIcon;
            }
            param1.isPresent = false;
            if (param2.@present && param2.@present == true)
            {
                param1.isPresent = true;
            }
            if (param2.limitedStart && param2.limitedStart.toString() != "")
            {
                _loc_3 = Utilities.dateToNumber(param2.limitedStart.toString());
                if (param2.limitedEnd && param2.limitedEnd.toString() != "")
                {
                    _loc_4 = Utilities.dateToNumber(param2.limitedEnd.toString());
                    if (_loc_3 <= GlobalEngine.getTimer() && _loc_4 >= GlobalEngine.getTimer())
                    {
                        _loc_5 = (_loc_4 - GlobalEngine.getTimer()) / 1000 / 3600;
                        if (_loc_5 < 1)
                        {
                            _loc_6 = ZLoc.t("Dialogs", "TimeLeftMinutes", {time:int(_loc_5 * 60)});
                        }
                        else if (_loc_5 <= 24)
                        {
                            _loc_6 = ZLoc.t("Dialogs", "TimeLeftHours", {time:int(_loc_5)});
                        }
                        else
                        {
                            _loc_6 = ZLoc.t("Dialogs", "TimeLeftDays", {time:Math.round(_loc_5 / 24)});
                        }
                        param1.limitedTimeLeft = _loc_6;
                    }
                    else
                    {
                        param1.limitedTimeLeft = this.OUTOFRANGE;
                    }
                }
            }
            return;
        }//end

         public Point  getDialogOffset ()
        {
            return new Point(-5, 5);
        }//end

        protected boolean  isLimitedEdition (int param1 ,int param2)
        {
            boolean _loc_3 =false ;
            if (Object(m_data.get(param2 + param1)).hasOwnProperty("limitedTimeLeft"))
            {
                _loc_3 = true;
            }
            return _loc_3;
        }//end

        protected boolean  isMysteryItem (int param1 ,int param2)
        {
            return Object(m_data.get(param2 + param1)).isMysteryItem;
        }//end

        protected boolean  hasWhatIsThisButton (int param1 ,int param2)
        {
            boolean _loc_3 =false ;
            if (Object(m_data.get(param2 + param1)).whatIsThis || this.isMysteryItem(param2, param1))
            {
                _loc_3 = true;
            }
            return _loc_3;
        }//end

        protected void  hideAllUIElements (int param1 )
        {
            if (m_tiles.get(param1).brownBorder_mc != null)
            {
                m_tiles.get(param1).brownBorder_mc.visible = false;
            }
            m_tiles.get(param1).stars_mc.visible = false;
            m_tiles.get(param1).progressBar_mc.visible = false;
            m_tiles.get(param1).progressTrack_mc.visible = false;
            m_tiles.get(param1).timeLeft_tf.visible = false;
            m_tiles.get(param1).locked_mc.visible = false;
            m_tiles.get(param1).locked_tf.visible = false;
            m_tiles.get(param1).yield_tf.visible = false;
            m_tiles.get(param1).growth_tf.visible = false;
            m_tiles.get(param1).xp_tf.visible = false;
            m_tiles.get(param1).whatisthis_mc.visible = false;
            m_tiles.get(param1).costGroup_mc.visible = false;
            m_tiles.get(param1).level_tf.visible = false;
            m_tiles.get(param1).neighbor_tf.visible = false;
            m_tiles.get(param1).add_neighbor_bt.visible = false;
            m_tiles.get(param1).buy_bt.visible = false;
            m_tiles.get(param1).greenexpires_tf.visible = false;
            m_tiles.get(param1).greenyield_tf.visible = false;
            m_tiles.get(param1).greengrowth_tf.visible = false;
            m_tiles.get(param1).greenxp_tf.visible = false;
            m_tiles.get(param1).buyAlt_bt.visible = false;
            m_tiles.get(param1).present_bt.visible = false;
            if (m_tiles.get(param1).whatisthis_bt != null)
            {
                m_tiles.get(param1).whatisthis_bt.turnOff();
                m_tiles.get(param1).whatisthis_bt = null;
            }
            return;
        }//end

        protected void  setupMasteryDisplay (int param1 ,int param2)
        {
            int _loc_3 =0;
            int _loc_4 =0;
            if (m_data.get(param2 + param1).mastery == true && Global.player.level >= Global.gameSettings().getAttribute("masteryStartLevel", 15))
            {
                m_tiles.get(param1).stars_mc.visible = true;
                m_tiles.get(param1).brownBorder_mc.visible = true;
                m_tiles.get(param1).progressBar_mc.visible = true;
                m_tiles.get(param1).progressTrack_mc.visible = true;
                this.m_progBarTooltips.get(param1).attachToolTip(m_tiles.get(param1).progressBar_mc);
                this.m_progBarTooltips.get(param1).attachToolTip(m_tiles.get(param1).progressTrack_mc);
                if (m_data.get(param2 + param1).masteryObject.hasAllMasteries())
                {
                    m_tiles.get(param1).stars_mc.gotoAndStop(m_data.get(param2 + param1).masteryObject.currentLevel + 2);
                    m_tiles.get(param1).progressBar_mc.width = this.MASTERY_BAR_MAX_WIDTH;
                    this.m_progBarTooltips.get(param1).toolTip = ZLoc.t("Dialogs", "Mastered");
                }
                else
                {
                    _loc_3 = m_data.get(param2 + param1).masteryObject.currentProgress;
                    _loc_4 = m_data.get(param2 + param1).masteryObject.totalCropsNeeded;
                    m_tiles.get(param1).stars_mc.gotoAndStop(m_data.get(param2 + param1).masteryObject.currentLevel + 2);
                    m_tiles.get(param1).progressBar_mc.width = _loc_3 / _loc_4 * this.MASTERY_BAR_MAX_WIDTH;
                    this.m_progBarTooltips.get(param1).toolTip = ZLoc.t("Dialogs", "MasteryProgress", {curProgress:_loc_3, total:_loc_4});
                }
            }
            return;
        }//end

        protected void  turnOnLimitedIcon (String param1 ,MovieClip param2 )
        {
            MovieClip _loc_4 =null ;
            int _loc_3 =0;
            while (_loc_3 < this.m_icons.length())
            {

                _loc_4 =(MovieClip) param2.getChildByName(this.m_icons.get(_loc_3));
                if (_loc_4)
                {
                    if (param1 == this.m_icons.get(_loc_3))
                    {
                        _loc_4.visible = true;
                    }
                    else
                    {
                        _loc_4.visible = false;
                    }
                }
                _loc_3++;
            }
            return;
        }//end

         protected void  addComponent (int param1 ,int param2)
        {
            MovieClip mc ;
            int tileSet ;
            String url ;
            Loader icon ;
            String name ;
            double licenseHeld ;
            double currentTime ;
            double delta ;
            boolean neighborSuccess ;
            boolean levelSuccess ;
            compId = param1;
            index = param2;
            if (WhatIsThisPopup.describeDialogOpen)
            {
                this.m_whatIsThisDialog.close();
            }
            if (m_tiles && m_data.get(index + compId) != null)
            {
                mc =(MovieClip) m_tiles.get(compId);
                mc .addFrameScript (0,void  ()
            {
                GenericButton _loc_2 =null ;
                String _loc_3 =null ;
                double _loc_4 =0;
                String _loc_5 =null ;
                double _loc_6 =0;
                String _loc_7 =null ;
                GenericButton _loc_8 =null ;
                GenericButton _loc_9 =null ;
                GenericButton _loc_10 =null ;
                GenericButton _loc_11 =null ;
                mc.addFrameScript(0, null, false, false);
                hideAllUIElements(compId);
                _loc_1 = m_xpInitY;
                if (isLimitedEdition(compId, index))
                {
                    if (!isMysteryItem(compId, index))
                    {
                        turnOnLimitedIcon(m_data.get(index + compId).limitedIcon, m_tiles.get(compId));
                    }
                    else
                    {
                        turnOnLimitedIcon("", m_tiles.get(compId));
                    }
                    m_tiles.get(compId).timeLeft_tf.visible = true;
                    m_tiles.get(compId).timeLeft_tf.text = m_data.get(index + compId).limitedTimeLeft;
                    m_tiles.get(compId).limited_tf.visible = true;
                    m_tiles.get(compId).limited_tf.text = ZLoc.t("Dialogs", "LimitedText");
                    m_tiles.get(compId).brownBorder_mc.visible = true;
                }
                else
                {
                    m_tiles.get(compId).timeLeft_tf.visible = false;
                    m_tiles.get(compId).limited_tf.visible = false;
                    turnOnLimitedIcon("", m_tiles.get(compId));
                }
                if (hasWhatIsThisButton(compId, index))
                {
                    m_tiles.get(compId).whatisthis_mc.visible = true;
                    _loc_2 = new GenericButton(m_tiles.get(compId).whatisthis_mc, onShowWhatIsThisDialog, "", "gb_WhatIsThis" + compId);
                    _loc_2.buttonValue = compId.toString();
                    m_tiles.get(compId).whatisthis_mc.value = compId.toString();
                    m_tiles.get(compId).whatisthis_bt = _loc_2;
                }
                if (m_data.get(index + compId))
                {
                    if (m_data.get(index + compId).harvestTime.toString() != "")
                    {
                        m_tiles.get(compId).growth_tf.visible = true;
                        _loc_3 = "GrowthDays";
                        _loc_4 = m_data.get(index + compId).harvestTime;
                        _loc_5 = "";
                        if (_loc_4 < 1)
                        {
                            _loc_3 = "GrowthHours";
                            _loc_6 = Global.gameSettings().getNumber("inGameDaySeconds");
                            _loc_4 = _loc_6 / 60 / 60 * _loc_4;
                            _loc_5 = _loc_4.toFixed(0);
                        }
                        else
                        {
                            _loc_5 = _loc_4.toString();
                        }
                        m_tiles.get(compId).growth_tf.text = ZLoc.t("Dialogs", _loc_3, {time:_loc_5});
                    }
                    else
                    {
                        m_tiles.get(compId).growth_tf.visible = false;
                    }
                    if (m_data.get(index + compId).capacity && m_data.get(index + compId).capacity.toString() != "")
                    {
                        m_tiles.get(compId).growth_tf.visible = true;
                        m_tiles.get(compId).growth_tf.text = ZLoc.t("Dialogs", "CapacityMarket", {capacity:m_data.get(index + compId).capacity.toString()});
                    }
                    if (m_tiles.get(compId).growth_tf.visible == false)
                    {
                        if (m_tiles.get(0) && m_tiles.get(0).growth_tf)
                        {
                            _loc_1 = m_tiles.get(0).growth_tf.y;
                        }
                    }
                    if (m_data.get(index + compId).coinYield.toString() != "" && isSellable(m_data.get(index + compId).type, m_data.get(index + compId).subtype) && !isMysteryItem(compId, index))
                    {
                        m_tiles.get(compId).yield_tf.visible = true;
                        m_tiles.get(compId).yield_tf.text = ZLoc.t("Dialogs", "Yield", {coins:m_data.get(index + compId).coinYield});
                    }
                    if (m_data.get(index + compId).cost > 0 || m_data.get(index + compId).cash > 0)
                    {
                        switch(m_data.get(index + compId).market.toString())
                        {
                            case Market.COST_CASH:
                            {
                                _loc_7 = Utilities.formatNumber(m_data.get(index + compId).cash);
                                m_tiles.get(compId).costGroup_mc.cashIcon_mc.visible = true;
                                m_tiles.get(compId).costGroup_mc.coinIcon_mc.visible = false;
                                break;
                            }
                            case Market.COST_COINS:
                            {
                            }
                            default:
                            {
                                _loc_7 = Utilities.formatNumber(m_data.get(index + compId).cost);
                                m_tiles.get(compId).costGroup_mc.cashIcon_mc.visible = false;
                                m_tiles.get(compId).costGroup_mc.coinIcon_mc.visible = true;
                                break;
                                break;
                            }
                        }
                        m_tiles.get(compId).costGroup_mc.cost_tf.text = _loc_7;
                        if (_loc_7.length > 2)
                        {
                            m_tiles.get(compId).costGroup_mc.x = m_costOffset - 6 * (_loc_7.length - 2);
                        }
                        else
                        {
                            m_tiles.get(compId).costGroup_mc.x = m_costOffset;
                        }
                        m_tiles.get(compId).costGroup_mc.visible = true;
                    }
                    if (m_tiles.get(compId).presentGenericButton instanceof GenericButton)
                    {
                        _loc_8 = m_tiles.get(compId).presentGenericButton;
                        _loc_8.disabled = true;
                        m_tiles.get(compId).presentGenericButton = _loc_8;
                    }
                    if (m_data.get(index + compId).isPresent)
                    {
                        if (m_tiles.get(compId).buyAlt_bt)
                        {
                            m_tiles.get(compId).buyAlt_bt.visible = true;
                            _loc_9 = new GenericButton(m_tiles.get(compId).buyAlt_bt, onBuyClick, "", "gb_BuyBtn" + compId);
                            _loc_9.disabled = m_tiles.get(compId).disabled;
                            m_tiles.get(compId).genericButton = _loc_9;
                        }
                        if (m_tiles.get(compId).present_bt)
                        {
                            m_tiles.get(compId).present_bt.visible = true;
                            _loc_10 = new GenericButton(m_tiles.get(compId).present_bt, onPresentClick, "", "gb_PresentBtn" + compId);
                            _loc_10.disabled = m_tiles.get(compId).disabled;
                            m_tiles.get(compId).presentGenericButton = _loc_10;
                        }
                    }
                    else if (m_tiles.get(compId).buy_bt)
                    {
                        m_tiles.get(compId).buy_bt.visible = true;
                        _loc_11 = new GenericButton(m_tiles.get(compId).buy_bt, onBuyClick, "", "gb_BuyBtn" + compId);
                        _loc_11.disabled = m_tiles.get(compId).disabled;
                        m_tiles.get(compId).genericButton = _loc_11;
                    }
                    if (m_data.get(index + compId).xpGained && m_data.get(index + compId).xpGained.toString() != "" && isSellable(m_data.get(index + compId).type, m_data.get(index + compId).subtype) && !isMysteryItem(compId, index))
                    {
                        m_tiles.get(compId).xp_tf.visible = true;
                        m_tiles.get(compId).xp_tf.text = ZLoc.t("Dialogs", "XPGained", {experience:m_data.get(index + compId).xpGained});
                    }
                    m_tiles.get(compId).xp_tf.y = _loc_1;
                    setupMasteryDisplay(compId, index);
                    m_tiles.get(compId).itemTitle_tf.text = m_data.get(index + compId).localizedName;
                }
                if (hasWhatIsThisButton(compId, index))
                {
                    m_tiles.get(compId).growth_tf.visible = false;
                    m_tiles.get(compId).yield_tf.visible = false;
                    m_tiles.get(compId).xp_tf.visible = false;
                }
                return;
            }//end
            );
                mc .addFrameScript (1,void  ()
            {
                String _loc_2 =null ;
                XML _loc_3 =null ;
                mc.addFrameScript(1, null, false, false);
                hideAllUIElements(compId);
                if (isLimitedEdition(compId, index))
                {
                    if (!isMysteryItem(compId, index))
                    {
                        turnOnLimitedIcon(m_data.get(index + compId).limitedIcon, m_tiles.get(compId));
                    }
                    else
                    {
                        turnOnLimitedIcon("", m_tiles.get(compId));
                    }
                    m_tiles.get(compId).limited_tf.visible = true;
                    m_tiles.get(compId).limited_tf.text = ZLoc.t("Dialogs", "LimitedText");
                    m_tiles.get(compId).brownBorder_mc.visible = true;
                }
                else
                {
                    m_tiles.get(compId).limited_tf.visible = false;
                    turnOnLimitedIcon("", m_tiles.get(compId));
                }
                if (m_data.get(index + compId).cost > 0 || m_data.get(index + compId).cash > 0)
                {
                    switch(m_data.get(index + compId).market.toString())
                    {
                        case Market.COST_CASH:
                        {
                            _loc_2 = Utilities.formatNumber(m_data.get(index + compId).cash);
                            m_tiles.get(compId).costGroup_mc.cashIcon_mc.visible = true;
                            m_tiles.get(compId).costGroup_mc.coinIcon_mc.visible = false;
                            break;
                        }
                        case Market.COST_COINS:
                        {
                        }
                        default:
                        {
                            _loc_2 = Utilities.formatNumber(m_data.get(index + compId).cost);
                            m_tiles.get(compId).costGroup_mc.cashIcon_mc.visible = false;
                            m_tiles.get(compId).costGroup_mc.coinIcon_mc.visible = true;
                            break;
                            break;
                        }
                    }
                    m_tiles.get(compId).costGroup_mc.cost_tf.text = _loc_2;
                    if (_loc_2.length > 2)
                    {
                        m_tiles.get(compId).costGroup_mc.x = m_costOffset - 6 * (_loc_2.length - 2);
                    }
                    else
                    {
                        m_tiles.get(compId).costGroup_mc.x = m_costOffset;
                    }
                    m_tiles.get(compId).costGroup_mc.visible = true;
                }
                String _loc_1 ="";
                if (Global.player.checkItemLimit(m_data.get(index + compId).name))
                {
                    _loc_3 = Global.gameSettings().getItemXMLByName(m_data.get(index + compId).name);
                    _loc_1 = ZLoc.t("Dialogs", "LimitHit", {limit:_loc_3.limit});
                }
                else
                {
                    switch(m_data.get(index + compId).unlock.toString())
                    {
                        case Item.UNLOCK_LOCKED:
                        {
                            _loc_1 = ZLoc.t("Dialogs", "ComingSoon");
                            break;
                        }
                        case Item.UNLOCK_LEVEL:
                        {
                            _loc_1 = ZLoc.t("Dialogs", "LevelNeeded", {amount:m_data.get(index + compId).requiredLevel});
                            break;
                        }
                        case Item.UNLOCK_NEIGHBOR:
                        {
                            _loc_1 = ZLoc.t("Dialogs", "NeighborsNeeded", {amount:m_data.get(index + compId).minNeighbors});
                            break;
                        }
                        case Item.UNLOCK_PERMITS:
                        {
                            _loc_1 = ZLoc.t("Dialogs", "PermitsNeeded", {amount2:ExpansionManager.instance.getNextExpansionPermitRequirement()});
                            break;
                        }
                        default:
                        {
                            _loc_1 = ZLoc.t("Dialogs", "LevelNeeded", {amount:m_data.get(index + compId).requiredLevel});
                            break;
                            break;
                        }
                    }
                }
                m_tiles.get(compId).level_tf.text = _loc_1;
                m_tiles.get(compId).level_tf.visible = true;
                m_tiles.get(compId).locked_mc.visible = true;
                m_tiles.get(compId).locked_tf.visible = true;
                m_tiles.get(compId).locked_tf.text = ZLoc.t("Dialogs", "Locked");
                m_tiles.get(compId).buy_tf.text = ZLoc.t("Dialogs", "Buy");
                m_tiles.get(compId).itemTitle_tf.text = m_data.get(index + compId).localizedName;
                return;
            }//end
            );
                mc .addFrameScript (2,void  ()
            {
                GenericButton _loc_1 =null ;
                mc.addFrameScript(2, null, false, false);
                hideAllUIElements(compId);
                if (isLimitedEdition(compId, index))
                {
                    if (!isMysteryItem(compId, index))
                    {
                        turnOnLimitedIcon(m_data.get(index + compId).limitedIcon, m_tiles.get(compId));
                    }
                    else
                    {
                        turnOnLimitedIcon("", m_tiles.get(compId));
                    }
                    m_tiles.get(compId).limitedClock_mc.visible = SHOW_LIMITED_CLOCK;
                    m_tiles.get(compId).timeLeft_tf.text = m_data.get(index + compId).limitedTimeLeft;
                    m_tiles.get(compId).limited_tf.text = ZLoc.t("Dialogs", "LimitedText");
                    m_tiles.get(compId).brownBorder_mc.visible = true;
                }
                else
                {
                    m_tiles.get(compId).limitedClock_mc.visible = false;
                    m_tiles.get(compId).limited_tf.visible = false;
                    turnOnLimitedIcon("", m_tiles.get(compId));
                }
                if (hasWhatIsThisButton(compId, index))
                {
                    m_tiles.get(compId).whatisthis_mc.visible = true;
                    _loc_1 = new GenericButton(m_tiles.get(compId).whatisthis_mc, onShowWhatIsThisDialog);
                    _loc_1.buttonValue = compId.toString();
                    m_tiles.get(compId).whatisthis_mc.value = compId.toString();
                    m_tiles.get(compId).whatisthis_bt = _loc_1;
                }
                m_tiles.get(compId).locked_mc.visible = true;
                m_tiles.get(compId).locked_tf.visible = true;
                m_tiles.get(compId).locked_tf.text = ZLoc.t("Dialogs", "Locked");
                m_tiles.get(compId).add_neighbor_bt.visible = true;
                m_tiles.get(compId).neighbor_tf.visible = true;
                m_tiles.get(compId).neighbor_tf.text = ZLoc.t("Dialogs", "NeighborsNeeded", {amount:m_data.get(index + compId).minNeighbors});
                m_tiles.get(compId).add_neighbor_bt.addEventListener(MouseEvent.CLICK, onAddNeighborClick);
                m_tiles.get(compId).itemTitle_tf.text = m_data.get(index + compId).localizedName;
                return;
            }//end
            );
                mc .addFrameScript (3,void  ()
            {
                mc.addFrameScript(3, null, false, false);
                hideAllUIElements(compId);
                turnOnLimitedIcon("", m_tiles.get(compId));
                m_tiles.get(compId).itemTitle_tf.text = m_data.get(index + compId).localizedName;
                m_tiles.get(compId).buy_bt.visible = true;
                _loc_1 = Utilities.formatNumber(m_data.get(index +compId).unlockCost );
                m_tiles.get(compId).costGroup_mc.cost_tf.text = _loc_1;
                m_tiles.get(compId).costGroup_mc.cashIcon_mc.visible = true;
                m_tiles.get(compId).costGroup_mc.coinIcon_mc.visible = false;
                m_tiles.get(compId).costGroup_mc.visible = true;
                return;
            }//end
            );
                mc .addFrameScript (4,void  ()
            {
                GenericButton gb ;
                String harvestLocStr ;
                double harvestTime ;
                String harvestTimeStr ;
                double dayInSeconds ;
                mc.addFrameScript(4, null, false, false);
                hideAllUIElements(compId);
                GameSprite sweetSeedsButton =new GameSprite ();
                sweetSeedsButton.addChild(m_tiles.get(compId).sweetSeedsInfo_bt);
                m_tiles.get(compId).addChild(sweetSeedsButton);
                ToolTip tooltip =new ToolTip ();
                tooltip.attachToolTip(sweetSeedsButton);
                sweetSeedsButton.toolTip = ZLoc.t("Dialogs", "TooltipSeeds");
                m_tiles.get(compId) .sweetSeedsInfo_bt .addEventListener (MouseEvent .CLICK ,void  (Event event )
                {
                    GlobalEngine.socialNetwork.redirect("seedsforhaiti.php", null, "_blank");
                    return;
                }//end
                );
                if (m_tiles.get(compId).buy_bt)
                {
                    gb = new GenericButton(m_tiles.get(compId).buy_bt, onBuyClick, "", "gb_BuyBtn" + compId);
                    gb.disabled = m_tiles.get(compId).disabled;
                    m_tiles.get(compId).genericButton = gb;
                }
                name = m_data.get(index+compId).name ;
                licenseHeld = Global.player.licenses.get(name) ;
                currentTime = GlobalEngine.getTimer()*0.001;
                delta = licenseHeld-currentTime;
                expiresValue = Math.floor(delta/60);
                if (expiresValue < 60)
                {
                    m_tiles.get(compId).greenexpires_tf.text = ZLoc.t("Dialogs", "ExpiresImmediately");
                }
                else if (expiresValue / 60 < 24)
                {
                    m_tiles.get(compId).greenexpires_tf.text = ZLoc.t("Dialogs", "ExpiresHours", {time:Math.ceil(expiresValue / 60)});
                }
                else
                {
                    m_tiles.get(compId).greenexpires_tf.text = ZLoc.t("Dialogs", "ExpiresDays", {time:Math.ceil(expiresValue / 60 / 24)});
                }
                m_tiles.get(compId).greenexpires_tf.visible = true;
                if (m_data.get(index + compId).coinYield.toString() != "" && isSellable(m_data.get(index + compId).type, m_data.get(index + compId).subtype))
                {
                    m_tiles.get(compId).greenyield_tf.visible = true;
                    m_tiles.get(compId).greenyield_tf.text = ZLoc.t("Dialogs", "Yield", {coins:m_data.get(index + compId).coinYield});
                }
                if (m_data.get(index + compId).harvestTime.toString() != "")
                {
                    m_tiles.get(compId).greengrowth_tf.visible = true;
                    harvestLocStr;
                    harvestTime = m_data.get(index + compId).harvestTime;
                    harvestTimeStr;
                    if (harvestTime < 1)
                    {
                        harvestLocStr;
                        dayInSeconds = Global.gameSettings().getNumber("inGameDaySeconds");
                        harvestTime = dayInSeconds / 60 / 60 * harvestTime;
                        harvestTimeStr = harvestTime.toFixed(0);
                    }
                    else
                    {
                        harvestTimeStr = harvestTime.toString();
                    }
                    m_tiles.get(compId).greengrowth_tf.text = ZLoc.t("Dialogs", harvestLocStr, {time:harvestTimeStr});
                }
                if (m_data.get(index + compId).xpGained && m_data.get(index + compId).xpGained.toString() != "" && isSellable(m_data.get(index + compId).type, m_data.get(index + compId).subtype))
                {
                    m_tiles.get(compId).greenxp_tf.visible = true;
                    m_tiles.get(compId).greenxp_tf.text = ZLoc.t("Dialogs", "XPGained", {experience:m_data.get(index + compId).xpGained});
                }
                m_tiles.get(compId).buy_bt.visible = true;
                costStr = Utilities.formatNumber(m_data.get(index +compId).cost );
                m_tiles.get(compId).costGroup_mc.cost_tf.text = costStr;
                m_tiles.get(compId).costGroup_mc.cashIcon_mc.visible = false;
                m_tiles.get(compId).costGroup_mc.coinIcon_mc.visible = true;
                m_tiles.get(compId).costGroup_mc.visible = true;
                m_tiles.get(compId).itemTitle_tf.text = m_data.get(index + compId).localizedName;
                return;
            }//end
            );
                m_tiles.get(compId).visible = true;
                if (m_tiles.get(compId).itemTitle_tf)
                {
                    m_tiles.get(compId).itemTitle_tf.text = m_data.get(index + compId).localizedName;
                }
                if (!Global.player.checkItemLimit(m_data.get(index + compId).name) && Global.player.checkGate(m_data.get(index + compId).unlock, m_data.get(index + compId).name) && !m_data.get(index + compId).unlockCost)
                {
                    if (mc.currentFrame == 4 || mc.currentFrame == 5)
                    {
                        mc.gotoAndStop(mc.totalFrames);
                    }
                    mc.gotoAndStop(1);
                    m_tiles.get(compId).disabled = false;
                }
                else if (m_data.get(index + compId).unlockCost && m_data.get(index + compId).unlockCost > 0)
                {
                    name = m_data.get(index + compId).name;
                    licenseHeld;
                    currentTime = GlobalEngine.getTimer() / 1000;
                    if (Global.player.licenses.get(name))
                    {
                        licenseHeld = Global.player.licenses.get(name);
                    }
                    delta = licenseHeld - currentTime;
                    if (licenseHeld != 0 && delta > -10)
                    {
                        mc.gotoAndStop(5);
                        m_tiles.get(compId).disabled = false;
                    }
                    else
                    {
                        if (licenseHeld > 0)
                        {
                            GameTransactionManager.addTransaction(new TExpireLicense(name));
                        }
                        mc.gotoAndStop(4);
                        m_tiles.get(compId).disabled = false;
                    }
                }
                else
                {
                    neighborSuccess = Global.player.checkNeighbors(m_data.get(index + compId).minNeighbors);
                    if (Global.player.checkItemLimit(m_data.get(index + compId).name))
                    {
                        mc.gotoAndStop(2);
                        m_tiles.get(compId).disabled = true;
                    }
                    else
                    {
                        switch(m_data.get(index + compId).unlock.toString())
                        {
                            case Item.UNLOCK_NEIGHBOR:
                            {
                                levelSuccess = Global.player.checkLevel(m_data.get(index + compId).requiredLevel);
                                if (neighborSuccess)
                                {
                                    mc.gotoAndStop(2);
                                }
                                else
                                {
                                    mc.gotoAndStop(3);
                                }
                                m_tiles.get(compId).disabled = true;
                                break;
                            }
                            case Item.UNLOCK_LEVEL:
                            {
                            }
                            default:
                            {
                                mc.gotoAndStop(2);
                                m_tiles.get(compId).disabled = true;
                                break;
                                break;
                            }
                        }
                    }
                }
                if (m_tiles.get(compId).genericButton)
                {
                    m_tiles.get(compId).genericButton.disabled = m_tiles.get(compId).disabled;
                }
                tileSet = m_tileSet;
                url = Global.gameSettings().getImageByName(m_data.get(index + compId).name, "icon");
                icon =LoadingManager .load (url ,void  (Event event )
            {
                _loc_2 = null;
                _loc_3 = null;
                _loc_4 = null;
                if (icon && icon.content && m_tileSet == tileSet && m_tileLoaders.get(compId).url == event.target.url)
                {
                    Utilities.removeAllChildren(mc.icon);
                    _loc_2 = icon.content;
                    _loc_3 = 1;
                    _loc_4 = 1;
                    if (m_tiles.get(0) != null)
                    {
                        _loc_3 = 1 / m_tiles.get(0).scaleX;
                        _loc_4 = 1 / m_tiles.get(0).scaleY;
                    }
                    _loc_2.width = 50 * _loc_3;
                    _loc_2.height = 50 * _loc_4;
                    mc.icon.addChild(_loc_2);
                    mc.icon.x = m_iconOffset + 3;
                }
                return;
            }//end
            );
                this.m_tileLoaders.put(compId,  icon.contentLoaderInfo);
            }
            return;
        }//end

        private void  onGetCoinsClick (Event event )
        {
            GlobalEngine.socialNetwork.redirect("money.php?ref=marketDialogGetCoins");
            return;
        }//end

        private void  loadPlantingSounds ()
        {
            SoundManager.addSound("plant1", Global.getAssetURL("assets/sounds/actions/sfx_plant_01.mp3"), false);
            SoundManager.addSound("plant2", Global.getAssetURL("assets/sounds/actions/sfx_plant_02.mp3"), false);
            SoundManager.addSound("plant3", Global.getAssetURL("assets/sounds/actions/sfx_plant_03.mp3"), false);
            m_soundLoaded = true;
            return;
        }//end

        private void  onCloseClick (MouseEvent event ,boolean param2 =false )
        {
            GenericButton gb ;
            event = event;
            isBuy = param2;
            removeTiles(false);
            m_window.close_bt.removeEventListener(MouseEvent.CLICK, this.onCloseClick);
            this .topButtons .forEach (void  (MovieClip param11 )
            {
                param11.removeEventListener(MouseEvent.CLICK, onMenuBarClick);
                return;
            }//end
            );
            this .subcatButtons .forEach (void  (MovieClip param11 )
            {
                param11.removeEventListener(MouseEvent.CLICK, onSubMenuBarClick);
                return;
            }//end
            );


            for(int i0 = 0; i0 < this.topButtons.size(); i0++)
            {
            		gb = this.topButtons.get(i0);


                gb.turnOff();
            }


            for(int i0 = 0; i0 < this.subcatButtons.size(); i0++)
            {
            		gb = this.subcatButtons.get(i0);


                gb.turnOff();
            }
            m_window.arrowLt_bt.removeEventListener(MouseEvent.CLICK, onLtArrowClick);
            m_window.arrowRt_bt.removeEventListener(MouseEvent.CLICK, onRtArrowClick);
            if (event)
            {
                event.stopPropagation();
            }
            this.count("close");
            this.close();
            dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
            if (this.m_onCloseClickCallback != null)
            {
                this.m_onCloseClickCallback(isBuy);
            }
            return;
        }//end

         public void  close ()
        {
            Array _loc_1 =null ;
            Item _loc_2 =null ;
            super.close();
            if (this.m_popFeedFlag)
            {
                this.m_popFeedFlag = false;
                _loc_1 = new Array();
                _loc_1.push({src:Global.getAssetURL("assets/newsfeed/sweetseedsforhaiti_newsfeed02_cb.png"), href:"{SN_APP_URL}{creative:picture}" + this.m_rewardLink});
                if (this.m_rewardLink)
                {
                    _loc_2 = Global.gameSettings().getItemByName(this.m_rewardName);
                    GlobalEngine.socialNetwork.publishFeedStory("SweetSeedsHaiti", {player_name:Global.player.snUser.firstName, item_name:_loc_2.localizedName, images:_loc_1, reward_link:this.m_rewardLink, subCategory:"sweet_seeds_haiti"}, [], false);
                }
                else
                {
                    GlobalEngine.socialNetwork.publishFeedStory("SweetSeedsHaiti", {images:_loc_1}, [], false);
                }
            }
            if (WhatIsThisPopup.describeDialogOpen)
            {
                this.m_whatIsThisDialog.close();
            }
            return;
        }//end

        private void  onMenuBarClick (MouseEvent event ,String param2 ,String param3 ="all")
        {
            String _loc_4 =null ;
            GenericButton _loc_5 =null ;
            String _loc_6 =null ;
            int _loc_7 =0;
            if (param2 != null)
            {
                _loc_4 = param2;
            }
            else
            {
                _loc_4 = event.target.name.replace(/_bt""_bt/, "");
            }
            if (_loc_4 != this.m_currentType || param2 != null)
            {
                removeTiles();
                this.m_subTypeIndex = 1;
                this.m_currentSubType = param3;
                for(int i0 = 0; i0 < this.subcatButtons.size(); i0++)
                {
                		_loc_6 = this.subcatButtons.get(i0);

                    _loc_5 =(GenericButton) this.subcatButtons.get(_loc_6);
                    if (_loc_6 != this.m_currentSubType)
                    {
                        _loc_5.state = GenericButton.UPSTATE;
                    }
                }
                if (param3 == "all")
                {
                    _loc_5 =(GenericButton) this.subcatButtons.get("subcat1_bt");
                    _loc_5.state = GenericButton.HITSTATE;
                }
                this.pushNewData(_loc_4, this.m_currentSubType);
                this.m_lastClickedType = this.m_currentType;
                this.count("menuBarClick");
                this.m_allCurrentSubTypes = Global.gameSettings().getSubMenuItemsByMenuType(this.m_currentType);
                this.showSubCatButtons(this.m_allCurrentSubTypes);
                _loc_7 = 0;
                while (_loc_7 < NUM_SLOTS && _loc_7 < m_data.length())
                {

                    this.addComponent(_loc_7);
                    _loc_7++;
                }
            }
            return;
        }//end

        private void  onSubMenuBarClick (MouseEvent event )
        {
            GenericButton _loc_3 =null ;
            String _loc_4 =null ;
            int _loc_5 =0;
            _loc_2 = parseInt(event.target.name.replace(/subcat""subcat/,"").replace(/_bt""_bt/,""));
            if (this.m_subTypeIndex != _loc_2)
            {
                if (_loc_2 == 1)
                {
                    this.m_currentSubType = "all";
                }
                else
                {
                    this.m_currentSubType = this.m_allCurrentSubTypes.get(_loc_2 - 2).@subtype;
                }
                if (this.m_allCurrentSubTypes.get(_loc_2 - 2).@comingSoon == "true")
                {
                    StatsManager.count(this.MARKET_COUNTER, "subcat", "flowers");
                    for(int i0 = 0; i0 < this.subcatButtons.size(); i0++)
                    {
                    		_loc_4 = this.subcatButtons.get(i0);

                        if (_loc_4 == event.target.name)
                        {
                            _loc_3 =(GenericButton) this.subcatButtons.get(_loc_4);
                            _loc_3.state = GenericButton.UPSTATE;
                        }
                    }
                    UI.displayPopup(new GenericDialog(ZLoc.t("Dialogs", "ComingSoon")), false);
                }
                else
                {
                    this.m_subTypeIndex = _loc_2;
                    for(int i0 = 0; i0 < this.subcatButtons.size(); i0++)
                    {
                    		_loc_4 = this.subcatButtons.get(i0);

                        if (_loc_4 != event.target.name)
                        {
                            _loc_3 =(GenericButton) this.subcatButtons.get(_loc_4);
                            _loc_3.state = GenericButton.UPSTATE;
                        }
                    }
                    removeTiles();
                    this.pushNewData(this.m_currentType, this.m_currentSubType);
                    this.m_lastClickedType = this.m_currentType;
                    this.count("subMenuBarClick");
                    _loc_5 = 0;
                    while (_loc_5 < NUM_SLOTS && _loc_5 < m_data.length())
                    {

                        this.addComponent(_loc_5);
                        _loc_5++;
                    }
                }
            }
            return;
        }//end

        protected void  showSubCatButtons (XMLList param1 )
        {
            int _loc_2 =0;
            MovieClip _loc_3 =null ;
            int _loc_4 =0;
            GenericButton _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            _loc_3 =(MovieClip) m_window.get("subcat1_bt");
            if (param1.length() < 1)
            {
                _loc_3.visible = false;
                _loc_5 =(GenericButton) this.subcatButtons.get("subcat1_bt");
                _loc_5.turnOff();
            }
            else
            {
                _loc_3.visible = true;
                _loc_5 =(GenericButton) this.subcatButtons.get("subcat1_bt");
                _loc_5.turnOn();
            }
            if (param1 == null)
            {
                _loc_4 = 0;
            }
            else
            {
                _loc_4 = Math.min((param1.length() + 1), NUM_SUBSLOTS);
            }
            _loc_2 = 2;
            while (_loc_2 <= _loc_4)
            {

                _loc_6 = "subcat" + _loc_2 + "_bt";
                _loc_5 =(GenericButton) this.subcatButtons.get(_loc_6);
                _loc_7 = ZLoc.t("Dialogs", param1.get(_loc_2 - 2).@subtype + "_submenu");
                _loc_5.text = _loc_7;
                _loc_5.turnOn();
                _loc_2++;
            }
            _loc_2 = _loc_4 + 1;
            while (_loc_2 <= NUM_SUBSLOTS)
            {

                _loc_6 = "subcat" + _loc_2 + "_bt";
                _loc_5 =(GenericButton) this.subcatButtons.get(_loc_6);
                _loc_5.turnOff();
                _loc_2++;
            }
            return;
        }//end

         protected void  postRemoveTiles (int param1 )
        {
            if (m_tiles.get(param1).buy_bt)
            {
                m_tiles.get(param1).buy_bt.removeEventListener(MouseEvent.CLICK, this.onBuyClick);
            }
            if (m_tiles.get(param1).sweetSeedsInfo_bt)
            {
                m_tiles.get(param1).sweetSeedsInfo_bt.visible = false;
            }
            if (m_tiles.get(param1).add_neighbor_bt)
            {
                m_tiles.get(param1).add_neighbor_bt.removeEventListener(MouseEvent.CLICK, this.onAddNeighborClick);
            }
            return;
        }//end

        private XML  getItemXMLFromEvent (MouseEvent event )
        {
            String _loc_2 =null ;
            int _loc_3 =0;
            if (event == null)
            {
                if (this.m_buyTileNumber > 0)
                {
                    _loc_3 = this.m_buyTileNumber;
                }
            }
            else
            {
                _loc_2 = String(event.target.parent.name).replace(/tile""tile/, "");
                _loc_3 = int(_loc_2) - 1;
            }
            _loc_4 = _loc_3+m_tileSet *NUM_SLOTS ;
            _loc_5 = m_data.get(_loc_4).name ;
            return Global.gameSettings().getItemXMLByName(_loc_5);
        }//end

        private void  onBuyFromPopupClick (GenericPopupEvent event )
        {
            if (event.button == GenericPopup.YES)
            {
                this.onBuyClick(null);
            }
            return;
        }//end

        private void  onBuyClick (MouseEvent event )
        {
            XML _loc_2 =null ;
            boolean _loc_3 =false ;
            int _loc_4 =0;
            int _loc_5 =0;
            boolean _loc_6 =false ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            int _loc_9 =0;
            boolean _loc_10 =false ;
            boolean _loc_11 =false ;
            String _loc_12 =null ;
            XMLList _loc_13 =null ;
            int _loc_14 =0;
            XML _loc_15 =null ;
            int _loc_16 =0;
            String _loc_17 =null ;
            XMLList _loc_18 =null ;
            XML _loc_19 =null ;
            _loc_2 = this.getItemXMLFromEvent(event);
            SoundManager.chooseAndPlaySound(.get("click1", "click2", "click3"));
            if (_loc_2)
            {
                _loc_3 = false;
                switch(_loc_2.@market.toString())
                {
                    case Market.COST_CASH:
                    {
                        _loc_4 = _loc_2.cash;
                        _loc_3 = Global.player.canBuyCash(_loc_4, false);
                        break;
                    }
                    case Market.COST_COINS:
                    {
                    }
                    default:
                    {
                        _loc_5 = _loc_2.cost;
                        _loc_3 = Global.player.canBuy(_loc_5, true);
                        if (!_loc_3)
                        {
                            this.onCloseClick(event);
                        }
                        break;
                        break;
                    }
                }
                if (_loc_3 == true)
                {
                    _loc_6 = true;
                    _loc_7 = _loc_2.@type;
                    _loc_8 = _loc_2.@subtype;
                    switch(_loc_7)
                    {
                        case "tool":
                        {
                            dispatchEvent(new MarketEvent(MarketEvent.MARKET_BUY, MarketEvent.EQUIPMENT, _loc_2.@name));
                            break;
                        }
                        case "contract":
                        {
                            dispatchEvent(new MarketEvent(MarketEvent.MARKET_BUY, MarketEvent.CONTRACT, _loc_2.@name));
                            break;
                        }
                        case "residence":
                        {
                            dispatchEvent(new MarketEvent(MarketEvent.MARKET_BUY, MarketEvent.RESIDENCE, _loc_2.@name));
                            break;
                        }
                        case "business":
                        {
                            dispatchEvent(new MarketEvent(MarketEvent.MARKET_BUY, MarketEvent.BUSINESS, _loc_2.@name));
                            break;
                        }
                        case "factory":
                        case "decoration":
                        case "road":
                        case "pier":
                        case "ship":
                        {
                            dispatchEvent(new MarketEvent(MarketEvent.MARKET_BUY, MarketEvent.GENERIC, _loc_2.@name));
                            break;
                        }
                        case "change_farm":
                        {
                            if (_loc_8 == "expand_farm")
                            {
                                _loc_9 = Global.player.homeIslandSize;
                                _loc_10 = false;
                                _loc_11 = false;
                                _loc_12 = null;
                                _loc_13 = Global.gameSettings().getItemsByTypeXML("change_farm", "expand_farm");
                                _loc_14 = 0;
                                while (_loc_14 < _loc_13.length())
                                {

                                    _loc_15 = _loc_13.get(_loc_14);
                                    if (_loc_15.@size == _loc_9)
                                    {
                                        _loc_11 = true;
                                    }
                                    else if (_loc_11 == true)
                                    {
                                        if (_loc_15.@size == _loc_2.@size)
                                        {
                                            _loc_10 = true;
                                        }
                                        else
                                        {
                                            _loc_16 = 0;
                                            while (_loc_16 < m_data.length())
                                            {

                                                if (m_data.get(_loc_16).subtype == "expand_farm")
                                                {
                                                    _loc_12 = m_data.get(_loc_16).localizedName;
                                                    break;
                                                }
                                                _loc_16++;
                                            }
                                        }
                                        break;
                                    }
                                    _loc_14++;
                                }
                                if (_loc_10)
                                {
                                    dispatchEvent(new MarketEvent(MarketEvent.MARKET_BUY, MarketEvent.EXPAND_FARM, _loc_2.@name));
                                }
                                else
                                {
                                    _loc_18 = Global.gameSettings().getItemsByTypeXML("change_farm", "expand_farm");
                                    for(int i0 = 0; i0 < _loc_18.size(); i0++)
                                    {
                                    		_loc_19 = _loc_18.get(i0);

                                        if (int(_loc_19.@size) == Global.player.homeIslandSize + 2)
                                        {
                                            _loc_17 = _loc_19.@name;
                                            break;
                                        }
                                    }
                                    _loc_6 = false;
                                    UI.displayMessage(ZLoc.t("Dialogs", "CantExpandFarm", {expansion:_loc_17}), GenericPopup.TYPE_OK, null, "", true);
                                }
                            }
                            break;
                        }
                        default:
                        {
                            break;
                        }
                    }
                    this.count("buy_" + _loc_2.@name);
                    if (_loc_6 == true)
                    {
                        this.onCloseClick(event, true);
                    }
                }
                else if (_loc_2.@market.toString() == Market.COST_CASH)
                {
                    UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_CASH);
                    StatsManager.count("fv_cash_buy_fail", "view_popup");
                }
                else if (_loc_2.@market.toString() == Market.COST_COINS)
                {
                    UI.displayImpulseBuyPopup(ImpulseBuy.TYPE_MARKET_COINS);
                }
            }
            return;
        }//end

        protected void  onPresentClick (MouseEvent event )
        {
            this.onCloseClick(event, true);
            _loc_2 = this.getItemXMLFromEvent(event );
            dispatchEvent(new MarketEvent(MarketEvent.MARKET_PRESENT, 0, _loc_2.@name));
            StatsManager.count("fv_buyableGift", "click_send");
            return;
        }//end

        private void  onAddNeighborClick (MouseEvent event )
        {
            URLVariables _loc_2 =new URLVariables ();
            _loc_3 = this.getItemXMLFromEvent(event );
            String _loc_4 =null ;
            if (_loc_3)
            {
                switch(this.m_currentType)
                {
                    case "change_farm":
                    {
                        _loc_2.requestType = "expandFarm";
                        _loc_2.farm = _loc_3.@name;
                        _loc_2.creative = "market_" + _loc_3.@size;
                        _loc_4 = "invite.php?ref=neighborsExpansionPage&";
                        break;
                    }
                    case "building":
                    {
                        _loc_2.requestType = "building";
                        _loc_2.creative = "building_" + _loc_3.@name;
                        _loc_4 = "invite.php?ref=neighborsExpansionPage&";
                        break;
                    }
                    case "tree":
                    {
                        _loc_2.requestType = "trees";
                        _loc_2.creative = "tree_" + _loc_3.@name;
                        _loc_4 = "invite.php?ref=neighborsTreePage&";
                        break;
                    }
                    case "decoration":
                    {
                        _loc_2.requestType = "decoration";
                        _loc_2.creative = "market_" + _loc_3.@size;
                        _loc_4 = "invite.php?ref=neighborsExpansionPage&";
                        break;
                    }
                    case "vehicle":
                    {
                        if (_loc_3.@type == "fuelcapacity")
                        {
                            _loc_2.requestType = "fuelcapacity";
                            _loc_2.creative = "fuelcapacity_" + _loc_3.@size;
                            _loc_4 = "invite.php?ref=neighborsExpansionPage&";
                        }
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            if (_loc_4)
            {
                GlobalEngine.socialNetwork.redirect(_loc_4, _loc_2);
            }
            return;
        }//end

        private void  displayStatus (MouseEvent event ,String param2 )
        {
            Point _loc_3 =new Point(event.stageX ,event.stageY );
            if (m_tiles.length > 0)
            {
                _loc_3.y = _loc_3.y - m_tiles.get(0).height;
            }
            _loc_3 = IsoMath.viewportToStage(_loc_3);
            UI.displayStatus(param2, _loc_3.x, _loc_3.y);
            return;
        }//end

        private void  count (String param1 ,String param2 )
        {
            if (param2 == null)
            {
                param2 = this.m_lastClickedType;
            }
            StatsManager.count(this.MARKET_COUNTER, this.m_viewSource, param2, param1, this.m_currentSubType);
            return;
        }//end

        private boolean  isSellable (String param1 ,String param2 )
        {
            boolean _loc_3 =true ;
            switch(param1)
            {
                case "fuel":
                case "fuelcapacity":
                {
                    _loc_3 = false;
                    break;
                }
                case "tool":
                {
                    _loc_3 = param2 != "equipment";
                    break;
                }
                default:
                {
                    break;
                }
            }
            return _loc_3;
        }//end

        private void  onShowWhatIsThisDialog (MouseEvent event )
        {
            XML _loc_2 =null ;
            XML _loc_3 =null ;
            this.m_buyTileNumber = int(event.currentTarget.parent.whatisthis_mc.value);
            if (!WhatIsThisPopup.describeDialogOpen)
            {
                _loc_2 = this.getItemXMLFromEvent(null);
                this.m_whatIsThisDialog = new WhatIsThisPopup(_loc_2, this.onBuyFromPopupClick);
                UI.displayPopup(this.m_whatIsThisDialog, false, "whatIsThisDescription", true);
                _loc_3 = this.getItemXMLFromEvent(event);
                StatsManager.count("Market", "actionMenu", this.m_currentType, "click_questionmark", _loc_3.@name);
            }
            return;
        }//end

    }



