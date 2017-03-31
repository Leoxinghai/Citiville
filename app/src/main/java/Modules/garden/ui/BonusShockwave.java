package Modules.garden.ui;

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
import Engine.*;
import Engine.Helpers.*;
import GameMode.*;
import com.greensock.*;
import com.greensock.easing.*;
//import flash.display.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;

    public class BonusShockwave
    {
        private Shape m_shape ;
        private int m_color ;
        private MapResource m_object ;
        private double m_radius ;
        private ItemBonus m_curBonus ;
        private Dictionary m_highlightedObjects ;
        private double m_alpha ;
        private TextFormat m_textFormat ;

        public  BonusShockwave (MapResource param1 )
        {
            this.m_shape = new Shape();
            this.m_object = param1;
            return;
        }//end

        public void  onComplete ()
        {
            GMObjMoveHelper _loc_1 =null ;
            Sprite _loc_2 =null ;
            for(int i0 = 0; i0 < this.m_highlightedObjects.size(); i0++)
            {
            		_loc_1 = this.m_highlightedObjects.get(i0);

                _loc_1.deactivate();
            }
            _loc_2 =(Sprite) Global.world.getObjectLayerByName("road").getDisplayObject();
            _loc_2.removeChild(this.m_shape);
            return;
        }//end

        public void  start (int param1 ,int param2 =0,int param3 =0)
        {
            _loc_4 = IsoMath.tilePosToPixelPos(this.m_object.getPosition ().x ,this.m_object.getPosition ().y );
            this.m_shape.x = _loc_4.x;
            this.m_shape.y = _loc_4.y;
            this.m_color = param1;
            this.m_textFormat = new TextFormat(EmbeddedArt.titleFont, 7, param1, true);
            _loc_5 =Global.world.getObjectLayerByName("road").getDisplayObject ()as Sprite ;
            (Global.world.getObjectLayerByName("road").getDisplayObject() as Sprite).addChild(this.m_shape);
            this.m_highlightedObjects = new Dictionary();
            this.radius = param2;
            this.m_alpha = 1;
            TimelineLite _loc_6 =new TimelineLite ();
            _loc_6.append(new TweenLite(this, 1, {radius:param3, delay:0.5, ease:Elastic.easeOut}));
            _loc_6.append(new TweenLite(this, 1, {alpha:0, delay:0.5, ease:Linear.easeNone, onComplete:this.onComplete}));
            _loc_6.play();
            return;
        }//end

        public double  alpha ()
        {
            return this.m_alpha;
        }//end

        public void  alpha (double param1 )
        {
            GMObjMoveHelper _loc_2 =null ;
            this.m_alpha = param1;
            this.m_shape.alpha = param1;
            for(int i0 = 0; i0 < this.m_highlightedObjects.size(); i0++)
            {
            		_loc_2 = this.m_highlightedObjects.get(i0);

                _loc_2.alpha = param1;
            }
            return;
        }//end

        public double  radius ()
        {
            return this.m_radius;
        }//end

        private boolean  matchBonus (GameObject param1 )
        {
            if (!param1 instanceof MapResource)
            {
                return false;
            }
            _loc_2 = MapResource(param1);
            if (!this.m_curBonus.bonusAppliesToObject(_loc_2))
            {
                return false;
            }
            if (_loc_2 == this.m_object)
            {
                return false;
            }
            if (_loc_2.getParent() == this.m_object)
            {
                return false;
            }
            return ItemBonus.withinNearbyRadius(this.m_object, _loc_2, this.radius);
        }//end

        public void  radius (double param1 )
        {
            Point _loc_10 =null ;
            ItemBonus _loc_12 =null ;
            Array _loc_13 =null ;
            MapResource _loc_14 =null ;
            Point _loc_15 =null ;
            GMObjMoveHelper _loc_16 =null ;
            ItemBonus _loc_17 =null ;
            this.m_radius = param1;
            this.m_shape.alpha = 0;
            this.m_shape.graphics.clear();
            _loc_2 = this.m_object.getSize ();
            _loc_3 = this.m_object.getPosition ();
            _loc_4 = IsoMath.getPixelDeltaFromTileDelta(1,0);
            _loc_5 = IsoMath.getPixelDeltaFromTileDelta(0,1);
            _loc_6 = param1-;
            _loc_7 = _loc_2.x +param1 ;
            _loc_8 = param1-;
            _loc_9 = _loc_2.y+param1;
            this.m_shape.graphics.lineStyle(1, this.m_color, 0.75);
            _loc_10 = IsoMath.getPixelDeltaFromTileDelta(_loc_6, _loc_8);
            this.m_shape.graphics.moveTo(_loc_10.x, _loc_10.y);
            _loc_10 = IsoMath.getPixelDeltaFromTileDelta(_loc_7, _loc_8);
            this.m_shape.graphics.lineTo(_loc_10.x, _loc_10.y);
            _loc_10 = IsoMath.getPixelDeltaFromTileDelta(_loc_7, _loc_9);
            this.m_shape.graphics.lineTo(_loc_10.x, _loc_10.y);
            _loc_10 = IsoMath.getPixelDeltaFromTileDelta(_loc_6, _loc_9);
            this.m_shape.graphics.lineTo(_loc_10.x, _loc_10.y);
            _loc_10 = IsoMath.getPixelDeltaFromTileDelta(_loc_6, _loc_8);
            this.m_shape.graphics.lineTo(_loc_10.x, _loc_10.y);
            this.m_shape.alpha = 1;
            _loc_11 = this.m_object.getItem ().bonuses ;
            for(int i0 = 0; i0 < _loc_11.size(); i0++)
            {
            		_loc_12 = _loc_11.get(i0);

                this.m_curBonus = _loc_12;
                _loc_13 = Global.world.getObjectsByPredicate(this.matchBonus);
                for(int i0 = 0; i0 < _loc_13.size(); i0++)
                {
                		_loc_14 = _loc_13.get(i0);

                    _loc_15 = getPixelCenter(_loc_14);
                    if (this.m_highlightedObjects.get(_loc_14.getId()) == null)
                    {
                        _loc_16 = new GMObjMoveHelper(_loc_14, _loc_15, this.m_textFormat, this.m_color, this.m_object);
                        this.m_highlightedObjects.put(_loc_14.getId(),  _loc_16);
                        for(int i0 = 0; i0 < _loc_11.size(); i0++)
                        {
                        		_loc_17 = _loc_11.get(i0);

                            this.m_curBonus = _loc_17;
                            if (this.matchBonus(_loc_14))
                            {
                                _loc_16.applyBonus(_loc_17);
                            }
                        }
                        _loc_16.activate();
                    }
                }
            }
            return;
        }//end

        public static Point  getPixelCenter (ItemInstance param1 )
        {
            _loc_2 = param1.getPosition ();
            _loc_3 = param1.getSize ();
            return IsoMath.tilePosToPixelPos(_loc_2.x + _loc_3.x / 2, _loc_2.y + _loc_3.y / 2);
        }//end

    }



