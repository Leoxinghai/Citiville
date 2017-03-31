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

import Classes.effects.*;
import Classes.effects.Particle.*;


import com.xinghai.Debug;

    public class GMFireKDBomb extends GMEdit
    {
        public String m_contractType ;
        public String m_contractClass ;

        public  GMFireKDBomb (String param1 )
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
            m_cursorImage = EmbeddedArt.hud_kdbomb;
            m_showMousePointer = false;
            _loc_1 = this.m_contractType.split("_").get(1) ;
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
            Debug.debug7("GMFire.isObjectSelectable");

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

            Point _loc_2 =IsoMath.stageToViewport(new Point(event.stageX ,event.stageY ));
	    Point _loc_3 =IsoMath.screenPosToTilePos(event.stageX ,event.stageY );

            if(Global.player.kdbomb <= 0) {
	        _loc_00 = newWarningEffect(IsoMath.tilePosToPixelPos(_loc_3.x,_loc_3.y),EffectType.NOBOMB.type,true,false,false);
		return;
            }

            Global.weiboManager.FireKDBomb(_loc_3.x, _loc_3.y);

            return;

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


