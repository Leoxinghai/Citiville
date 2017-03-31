package com.xinghai.gamemode;

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
//import flash.events.*;
import GameMode.*;


import Classes.actions.*;
import Classes.doobers.*;
import Classes.inventory.*;
import Classes.orders.*;
import Classes.util.*;
import Display.DialogUI.*;
import Display.MarketUI.*;
import Display.TrainUI.*;
import Engine.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import Events.*;
import Modules.trains.*;
import Modules.workers.*;
import Modules.workers.transactions.*;
import Transactions.train.*;

import com.greensock.*;
//import flash.geom.*;


import com.xinghai.Debug;

    public class GMFire extends GMEdit
    {
        public String m_contractType ;
        public String m_contractClass ;

        public  GMFire (String param1 )
        {
            this.m_contractType = param1;
            this.m_contractClass = "";
            _loc_2 =Global.gameSettings().getItemByName(param1 );
            if (_loc_2)
            {
                this.m_contractClass = _loc_2.type;
            }
            return;
        }//end

         public void  enableMode ()
        {
            super.enableMode();
            Debug.debug7("GMFire.enableMode");

            UI.popupLock();
            _loc_1 = this.m_contractType.split("_").get(1) ;
            switch(_loc_1)
            {
                case "test":
                {
                    m_cursorImage = EmbeddedArt.hud_act_corn;
                    break;
                }
                case "strawberries":
                {
                    m_cursorImage = EmbeddedArt.hud_act_straw;
                    break;
                }
                case "eggplants":
                {
                    m_cursorImage = EmbeddedArt.hud_act_eggplant;
                    break;
                }
                case "carrots":
                {
                    m_cursorImage = EmbeddedArt.hud_act_carrot;
                    break;
                }
                case "squash":
                {
                    m_cursorImage = EmbeddedArt.hud_act_squash;
                    break;
                }
                case "corn":
                {
                    m_cursorImage = EmbeddedArt.hud_act_corn;
                    break;
                }
                case "watermelon":
                {
                    m_cursorImage = EmbeddedArt.hud_act_watermelon;
                    break;
                }
                case "pumpkin":
                {
                    m_cursorImage = EmbeddedArt.hud_act_pumpkin;
                    break;
                }
                case "wheat":
                {
                    m_cursorImage = EmbeddedArt.hud_act_wheat;
                    break;
                }
                case "peas":
                {
                    m_cursorImage = EmbeddedArt.hud_act_pea;
                    break;
                }
                case "cranberries":
                {
                    m_cursorImage = EmbeddedArt.hud_act_cran;
                    break;
                }
                case "rosesyellow":
                {
                    m_cursorImage = EmbeddedArt.hud_act_yellowrose;
                    break;
                }
                case "rosespink":
                {
                    m_cursorImage = EmbeddedArt.hud_act_pinkrose;
                    break;
                }
                case "roses":
                {
                    m_cursorImage = EmbeddedArt.hud_act_redrose;
                    break;
                }
                case "clover":
                {
                    m_cursorImage = EmbeddedArt.hud_act_clover;
                    break;
                }
                case "cabbage":
                {
                    m_cursorImage = EmbeddedArt.hud_act_cabbage;
                    break;
                }
                case "sweetpotato":
                {
                    m_cursorImage = EmbeddedArt.hud_act_sweetpotato;
                    break;
                }
                case "lettuce":
                {
                    m_cursorImage = EmbeddedArt.hud_act_lettuce;
                    break;
                }
                case "tomatoes":
                {
                    m_cursorImage = EmbeddedArt.hud_act_tomato;
                    break;
                }
                case "organictomato":
                {
                    m_cursorImage = EmbeddedArt.hud_act_tomato;
                    break;
                }
                case "alfalfa":
                {
                    m_cursorImage = EmbeddedArt.hud_act_alfalfa;
                    break;
                }
                case "blueberries":
                {
                    m_cursorImage = EmbeddedArt.hud_act_blueberries;
                    break;
                }
                case "artichoke":
                {
                    m_cursorImage = EmbeddedArt.hud_act_artichoke;
                    break;
                }
                case "pineapple":
                {
                    m_cursorImage = EmbeddedArt.hud_act_pineapple;
                    break;
                }
                case "coinpineapple":
                {
                    m_cursorImage = EmbeddedArt.hud_act_pineapple;
                    break;
                }
                case "sugarcane":
                {
                    m_cursorImage = EmbeddedArt.hud_act_sugarcane;
                    break;
                }
                case "applepie":
                {
                    m_cursorImage = EmbeddedArt.hud_act_applepie;
                    break;
                }
                case "spinach":
                {
                    m_cursorImage = EmbeddedArt.hud_act_spinach;
                    break;
                }
                case "grapes":
                {
                    m_cursorImage = EmbeddedArt.hud_act_grapes;
                    break;
                }
                case "cassava":
                {
                    m_cursorImage = EmbeddedArt.hud_act_cassava;
                    break;
                }
                case "sunflowers":
                {
                    m_cursorImage = EmbeddedArt.hud_act_sunflowers;
                    break;
                }
                case "coinsunflowers":
                {
                    m_cursorImage = EmbeddedArt.hud_act_sunflowers;
                    break;
                }
                case "rhubarb":
                {
                    m_cursorImage = EmbeddedArt.hud_act_rhubarb;
                    break;
                }
                case "asparagus":
                {
                    m_cursorImage = EmbeddedArt.hud_act_asparagus;
                    break;
                }
                case "taro":
                {
                    m_cursorImage = EmbeddedArt.hud_act_taro;
                    break;
                }
                case "grapesgreen":
                {
                    m_cursorImage = EmbeddedArt.hud_act_grapesgreen;
                    break;
                }
                case "barley":
                {
                    m_cursorImage = EmbeddedArt.hud_act_barley;
                    break;
                }
                case "cottoncandy":
                {
                    m_cursorImage = EmbeddedArt.hud_act_cottoncandy;
                    break;
                }
                case "candyapples":
                {
                    m_cursorImage = EmbeddedArt.hud_act_candyapples;
                    break;
                }
                case "peanuts":
                {
                    m_cursorImage = EmbeddedArt.hud_act_peanuts;
                    break;
                }
                case "sugarbeets":
                {
                    m_cursorImage = EmbeddedArt.hud_act_sugarbeets;
                    break;
                }
                case "blackberries":
                {
                    m_cursorImage = EmbeddedArt.hud_act_blackberries;
                    break;
                }
                case "sportsdrink":
                {
                    m_cursorImage = EmbeddedArt.hud_act_sportsdrink;
                    break;
                }
                case "potatoes":
                {
                    m_cursorImage = EmbeddedArt.hud_act_potatoes;
                    break;
                }
                case "agave":
                {
                    m_cursorImage = EmbeddedArt.hud_act_agave;
                    break;
                }
                case "sprouts":
                {
                    m_cursorImage = EmbeddedArt.hud_act_sprouts;
                    break;
                }
                case "grapescabernet":
                {
                    m_cursorImage = EmbeddedArt.hud_act_grapescabernet;
                    break;
                }
                case "winterwheat":
                {
                    m_cursorImage = EmbeddedArt.hud_act_winterwheat;
                    break;
                }
                case "garlic":
                {
                    m_cursorImage = EmbeddedArt.hud_act_garlic;
                    break;
                }
                case "jackolantern":
                {
                    m_cursorImage = EmbeddedArt.hud_act_jacolantern;
                    break;
                }
                case "candycorn":
                {
                    m_cursorImage = EmbeddedArt.hud_act_candycorn;
                    break;
                }
                case "coffee":
                {
                    m_cursorImage = EmbeddedArt.hud_act_coffee;
                    break;
                }
                case "turnip":
                {
                    m_cursorImage = EmbeddedArt.hud_act_turnip;
                    break;
                }
                case "edelweiss":
                {
                    m_cursorImage = EmbeddedArt.hud_act_edelweiss;
                    break;
                }
                default:
                {
                    if (this.m_contractClass == "ship_contract")
                    {
                        m_cursorImage = EmbeddedArt.hud_biz_supply;
                    }
                    break;
                    break;
                }
            }
            return;
        }//end

         public void  disableMode ()
        {
            super.disableMode();
            Debug.debug7("GMFire.disableMode");
            UI.popupUnlock();
            return;
        }//end

         protected boolean  isObjectSelectable (GameObject param1 )
        {

            _loc_2 = param1as HarvestableResource ;
            return _loc_2 && _loc_2.isPlantable(this.m_contractType);
        }//end

         protected void  selectObject (GameObject param1 ,MouseEvent param2 )
        {
            super.selectObject(param1, param2);
            Debug.debug7("GMFire.selectObject");

            _loc_3 = param1as HarvestableResource ;
            if (_loc_3)
            {
                _loc_3.plant(this.m_contractType);
            }
            return;
        }//end

         protected void  handleClick (MouseEvent event )
        {
            Debug.debug7("GMFire.handleClick");

            Point _loc_2 =IsoMath.stageToViewport(new Point(event.stageX ,event.stageY ));
	    Point _loc_3 =IsoMath.screenPosToTilePos(event.stageX ,event.stageY );
            Global.weiboManager.addActionGo(_loc_3.x, _loc_3.y);


	    _loc_1 = this.generateDoobers(0,0,20,Doober.DOOBER_KDBOMB );

	    Global.world.dooberManager.createBatchDoobers(_loc_1, null, _loc_3.x, _loc_3.y);

	    _loc_11 = this.generateDoobers(0,0,10,Doober.DOOBER_LRBOMB );

	    Global.world.dooberManager.createBatchDoobers(_loc_11, null, _loc_3.x, _loc_3.y);


            if (m_highlightedObject)
            {
                super.handleClick(event);
            }
            else
            {
                Global.world.addGameMode(new GMPlay());
            }
            return;
        }//end

        protected Array  generateDoobers (int param1 ,int param2 ,int param3 ,String param4 )
        {
            int _loc_6 =0;
            int _loc_7 =0;
            int _loc_11 =0;
            Array _loc_5 =new Array ();
            _loc_8 =Global.gameSettings().getInt("trainAmountPerDoober",3);
            _loc_9 =Global.gameSettings().getInt("trainMaxDoobers",6);
            int _loc_10 =0;
            if (param1 > 0)
            {
                _loc_6 = Math.floor(Math.min(param1 / _loc_8, _loc_9));
                _loc_7 = Math.floor(param1 / _loc_6);
                _loc_11 = param1 % _loc_6;
                _loc_10 = 0;
                while (_loc_10 < _loc_6)
                {

                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN, _loc_7), _loc_7));
                    _loc_10++;
                }
                if (_loc_11 > 0)
                {
                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN, _loc_11), _loc_11));
                }
            }
            if (param2 > 0)
            {
                _loc_6 = Math.floor(Math.min(param2 / _loc_8, _loc_9));
                _loc_7 = Math.floor(param2 / _loc_6);
                _loc_11 = param2 % _loc_6;
                _loc_10 = 0;
                while (_loc_10 < _loc_6)
                {

                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_XP, _loc_7), _loc_7));
                    _loc_10++;
                }
                if (_loc_11 > 0)
                {
                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(Doober.DOOBER_XP, _loc_11), _loc_11));
                }
            }
            if (param3 > 0)
            {
                _loc_6 = Math.floor(Math.min(param3 / _loc_8, _loc_9));
                _loc_7 = Math.floor(param3 / _loc_6);
                _loc_11 = param3 % _loc_6;
                _loc_10 = 0;
                while (_loc_10 < _loc_6)
                {

                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(param4, _loc_7), _loc_7));
                    _loc_10++;
                }
                if (_loc_11 > 0)
                {
                    _loc_5.push(new Array(Global.gameSettings().getDooberFromType(param4, _loc_11), _loc_11));
                }
            }
            return _loc_5;
        }//end


    }


