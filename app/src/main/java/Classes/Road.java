package Classes;

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

import Classes.util.*;
import Engine.*;
import Engine.Helpers.*;
import GameMode.*;
import Transactions.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;

    public class Road extends Decoration
    {
        protected  String ROAD ="road";
        protected Object m_currentRule =null ;
        public boolean m_heatMap =false ;
        protected boolean m_partialRuleMatch =false ;
        protected Sprite m_overlay1 =null ;
        protected Sprite m_overlay2 =null ;
        protected DisplayObject m_overlay3 =null ;
        protected Array m_adjacent =null ;
        protected Array m_adjacentPartial =null ;
        protected Array m_adjacentTight =null ;
        protected Array m_adjacentStretch =null ;
        protected Array m_adjacentRoads =null ;
        protected Array m_canAdjust =null ;
        protected Item m_saveDragItem ;
        protected boolean m_isLeftRight =false ;
        protected boolean m_isUpDown =false ;
        protected boolean m_isCorner =false ;
        protected boolean m_isTightSection =false ;
        protected int m_tightDirection =0;
        protected boolean m_isStretchSection =false ;
        protected int m_stretchDirection =0;
        protected int m_overlayContentMode =0;
        protected double m_overlayContentX =0;
        protected double m_overlayContentY =0;
        protected int m_overlay1Mode =0;
        protected double m_overlay1X =0;
        protected double m_overlay1Y =0;
        protected int m_overlay2Mode =0;
        protected double m_overlay2X =0;
        protected double m_overlay2Y =0;
        protected boolean m_fitShrink =false ;
        protected boolean m_fitStretch =false ;
        protected Vector2 m_fitOffset ;
        protected boolean m_isRoadBeingDragged =false ;
        protected boolean m_invalidDragPos =false ;
        protected boolean m_attached =false ;
        protected boolean m_checkForReplaceAtDrop =false ;
        protected boolean m_didConnectivity =false ;
public static Array m_roadRules ;
public static Array m_roadRules2x3 ;
public static Array m_roadRules3x2 ;
public static Array m_roadRules4x3 ;
public static Array m_roadRules3x4 ;
public static Shape m_dbgShape =null ;
        public static  int LEFT =0;
        public static  int DOWN =1;
        public static  int RIGHT =2;
        public static  int UP =3;
        public static  int DIRECTION_MAX =4;
        public static  int OVERLAY_NONE =0;
        public static  int OVERLAY_BOTTOM =1;
        public static  int OVERLAY_TOP =2;
        public static  int OVERLAY_OFFSET =3;
        public static  int CAN_ADJUST =1;
        public static  int SHOULD_ADJUST =2;
        public static  double SIZE_X =3;
        public static  double SIZE_Y =3;
public static  Array m_flipDirection =.get(2 ,3,0,1) ;
        public static boolean m_allowStretchCorners =true ;
        public static boolean m_allowTighten =true ;
public static double SPRITE_SCALE =0;
public static int colorShift =0;
public static Road s_shrinkRoad1 =null ;
public static Road s_shrinkRoad2 =null ;
public static int s_shrinkDir1 =-1;
public static int s_shrinkDir2 =-1;
        private static double s_nearX =0;
        private static double s_nearY =0;

        public  Road (String param1)
        {
            Object _loc_2 =null ;
            Array _loc_3 =null ;
            this.m_fitOffset = new Vector2();
            this.initSpriteScale();
            if (m_roadRules == null)
            {
                m_roadRules = [{match:[LEFT], partial:[LEFT], tile:"road_cap_ne"}, {match:[UP], partial:[UP], tile:"road_cap_se"}, {match:[RIGHT], partial:[RIGHT], tile:"road_cap_sw"}, {match:[DOWN], partial:[DOWN], tile:"road_cap_nw"}, {match:[UP, LEFT], partial:[LEFT], corner:[UP], tile:"corner_w", overlay:"straightaway_e", overlay2:"corner_b_w"}, {match:[UP, LEFT], partial:[UP], corner:[LEFT], tile:"corner_w", overlay:"straightaway_n", overlay2:"corner_b_w"}, {match:[UP, RIGHT], partial:[UP], corner:[RIGHT], tile:"corner_n", overlay:"straightaway_n", overlay2:"corner_b_n"}, {match:[UP, RIGHT], partial:[RIGHT], corner:[UP], tile:"corner_n", overlay:"straightaway_e", overlay2:"corner_b_n"}, {match:[LEFT, DOWN], partial:[LEFT], corner:[DOWN], tile:"corner_s", overlay:"straightaway_e", overlay2:"corner_b_s"}, {match:[LEFT, DOWN], partial:[DOWN], corner:[LEFT], tile:"corner_s", overlay:"straightaway_n", overlay2:"corner_b_s"}, {match:[RIGHT, DOWN], partial:[RIGHT], corner:[DOWN], tile:"corner_e", overlay:"straightaway_e", overlay2:"corner_b_e"}, {match:[RIGHT, DOWN], partial:[DOWN], corner:[RIGHT], tile:"corner_e", overlay:"straightaway_n", overlay2:"corner_b_e"}, {match:[UP, LEFT, RIGHT], partial:[UP], tee:[UP], tile:"straightaway_n", overlay:"tcap_se"}, {match:[UP, LEFT, DOWN], partial:[LEFT], tee:[LEFT], tile:"straightaway_e", overlay:"tcap_ne"}, {match:[LEFT, RIGHT, DOWN], partial:[DOWN], tee:[DOWN], tile:"straightaway_n", overlay:"tcap_nw"}, {match:[UP, RIGHT, DOWN], partial:[RIGHT], tee:[RIGHT], tile:"straightaway_e", overlay:"tcap_sw"}, {match:[UP, DOWN, LEFT, RIGHT], partial:[LEFT, RIGHT], cross:[LEFT, RIGHT], tile:"straightaway_e", overlay:"tcap_ne", overlay2:"tcap_sw"}, {match:[LEFT, RIGHT, UP, DOWN], partial:[UP, DOWN], cross:[UP, DOWN], tile:"straightaway_n", overlay:"tcap_se", overlay2:"tcap_nw"}, {match:[UP, DOWN, LEFT, RIGHT], partial:[LEFT], cross:[LEFT, RIGHT], tile:"straightaway_e", overlay:"tcap_ne", overlay2:"tcap_sw"}, {match:[LEFT, RIGHT, UP, DOWN], partial:[UP], cross:[UP, DOWN], tile:"straightaway_n", overlay:"tcap_se", overlay2:"tcap_nw"}, {match:[UP, DOWN, LEFT, RIGHT], partial:[RIGHT], cross:[LEFT, RIGHT], tile:"straightaway_e", overlay:"tcap_ne", overlay2:"tcap_sw"}, {match:[LEFT, RIGHT, UP, DOWN], partial:[DOWN], cross:[UP, DOWN], tile:"straightaway_n", overlay:"tcap_se", overlay2:"tcap_nw"}, {match:[UP, LEFT, RIGHT], partial:[LEFT, RIGHT], tee:[UP], tile:"straightaway_n", overlay:"tcap_se", overlay2:"straightaway_e"}, {match:[UP, LEFT, DOWN], partial:[UP, DOWN], tee:[LEFT], tile:"straightaway_e", overlay:"tcap_ne", overlay2:"straightaway_n"}, {match:[LEFT, RIGHT, DOWN], partial:[LEFT, RIGHT], tee:[DOWN], tile:"straightaway_n", overlay:"tcap_nw", overlay2:"straightaway_e"}, {match:[UP, RIGHT, DOWN], partial:[UP, DOWN], tee:[RIGHT], tile:"straightaway_e", overlay:"tcap_sw", overlay2:"straightaway_n"}, {match:[LEFT], tile:"road_cap_ne"}, {match:[UP], tile:"road_cap_se"}, {match:[RIGHT], tile:"road_cap_sw"}, {match:[DOWN], tile:"road_cap_nw"}, {match:[UP, LEFT], tile:"corner_w"}, {match:[UP, RIGHT], tile:"corner_n"}, {match:[LEFT, DOWN], tile:"corner_s"}, {match:[RIGHT, DOWN], tile:"corner_e"}, {match:[UP, DOWN], tile:"straightaway_e"}, {match:[LEFT, RIGHT], tile:"straightaway_n"}, {match:[UP, LEFT, RIGHT], tile:"tshape_nw", tee:[UP], overlay:"tcap_se"}, {match:[UP, LEFT, DOWN], tile:"tshape_sw", tee:[LEFT], overlay:"tcap_ne"}, {match:[LEFT, RIGHT, DOWN], tile:"tshape_se", tee:[DOWN], overlay:"tcap_nw"}, {match:[UP, RIGHT, DOWN], tile:"tshape_ne", tee:[RIGHT], overlay:"tcap_sw"}, {match:[UP, LEFT, RIGHT, DOWN], tile:"4way"}, {match:[], tile:"road_circle"}];
                m_roadRules2x3 = [{match:[LEFT], tile:"road_cap_ne"}, {match:[RIGHT], tile:"road_cap_sw"}, {match:[LEFT], partial:[LEFT], tile:"road_cap_ne"}, {match:[RIGHT], partial:[RIGHT], tile:"road_cap_sw"}, {match:[LEFT, RIGHT], tile:"straightaway_n"}, {match:[UP, LEFT], partial:[UP], corner:[LEFT], tile:"corner_w", overlay:"straightaway_n", overlay2:"corner_b_w"}, {match:[UP, RIGHT], partial:[UP], corner:[RIGHT], tile:"corner_n", overlay:"straightaway_n", overlay2:"corner_b_n"}, {match:[LEFT, DOWN], partial:[DOWN], corner:[LEFT], tile:"corner_s", overlay:"straightaway_n", overlay2:"corner_b_s"}, {match:[RIGHT, DOWN], partial:[DOWN], corner:[RIGHT], tile:"corner_e", overlay:"straightaway_n", overlay2:"corner_b_e"}, {match:[LEFT, RIGHT, DOWN], partial:[DOWN], tee:[DOWN], tile:"straightaway_n", overlay:"tcap_nw"}, {match:[UP, LEFT, RIGHT], partial:[UP], tee:[UP], tile:"straightaway_n", overlay:"tcap_se"}, {match:[UP, LEFT, DOWN], partial:[UP, DOWN], tee:[LEFT], tile:"straight_full_e", overlay:"tcap_ne"}, {match:[UP, RIGHT, DOWN], partial:[UP, DOWN], tee:[RIGHT], tile:"straight_full_e", overlay:"tcap_sw"}, {match:[LEFT, RIGHT, UP, DOWN], partial:[UP, DOWN], cross:[UP, DOWN], tile:"straightaway_n", overlay:"tcap_se", overlay2:"tcap_nw"}, {match:[], tile:"straightBlocked_n"}];
                m_roadRules3x2 = [{match:[UP], tile:"road_cap_se"}, {match:[DOWN], tile:"road_cap_nw"}, {match:[UP], partial:[UP], tile:"road_cap_se"}, {match:[DOWN], partial:[DOWN], tile:"road_cap_nw"}, {match:[UP, DOWN], tile:"straightaway_e"}, {match:[UP, LEFT], partial:[LEFT], corner:[UP], tile:"corner_w", overlay:"straightaway_e", overlay2:"corner_b_w"}, {match:[UP, RIGHT], partial:[RIGHT], corner:[UP], tile:"corner_n", overlay:"straightaway_e", overlay2:"corner_b_n"}, {match:[LEFT, DOWN], partial:[LEFT], corner:[DOWN], tile:"corner_s", overlay:"straightaway_e", overlay2:"corner_b_s"}, {match:[RIGHT, DOWN], partial:[RIGHT], corner:[DOWN], tile:"corner_e", overlay:"straightaway_e", overlay2:"corner_b_e"}, {match:[UP, LEFT, DOWN], partial:[LEFT], tee:[LEFT], tile:"straightaway_e", overlay:"tcap_ne"}, {match:[UP, RIGHT, DOWN], partial:[RIGHT], tee:[RIGHT], tile:"straightaway_e", overlay:"tcap_sw"}, {match:[UP, LEFT, RIGHT], partial:[LEFT, RIGHT], tee:[UP], tile:"straight_full_n", overlay:"tcap_se"}, {match:[LEFT, RIGHT, DOWN], partial:[LEFT, RIGHT], tee:[DOWN], tile:"straight_full_n", overlay:"tcap_nw"}, {match:[UP, DOWN, LEFT, RIGHT], partial:[LEFT, RIGHT], cross:[LEFT, RIGHT], tile:"straightaway_e", overlay:"tcap_ne", overlay2:"tcap_sw"}, {match:[], tile:"straightBlocked_e"}];
                m_roadRules4x3 = [{match:[UP, LEFT], partial:[UP], corner:[LEFT], tile:"corner_w", overlay:"straight_1_n", overlay2:"corner_b_w"}, {match:[UP, RIGHT], partial:[UP], corner:[RIGHT], tile:"corner_n", overlay:"straight_1_nt", overlay2:"corner_b_n"}, {match:[LEFT, DOWN], partial:[DOWN], corner:[LEFT], tile:"corner_s", overlay:"straight_1_n", overlay2:"corner_b_s"}, {match:[RIGHT, DOWN], partial:[DOWN], corner:[RIGHT], tile:"corner_e", overlay:"straight_1_nt", overlay2:"corner_b_e"}, {match:[UP, LEFT, DOWN], partial:[UP, DOWN], tee:[LEFT], tile:"straightaway_e", overlay:"tcap_ne", overlay2:"straightaway_n"}, {match:[UP, RIGHT, DOWN], partial:[UP, DOWN], tee:[RIGHT], tile:"straightaway_e", overlay:"tcap_sw", overlay2:"straightaway_n"}, {match:[LEFT, RIGHT, DOWN], partial:[DOWN], tee:[DOWN], tile:"straight_full_n", overlay:"tcap_full_nw"}, {match:[UP, LEFT, RIGHT], partial:[UP], tee:[UP], tile:"straight_full_n", overlay:"tcap_full_se"}, {match:[LEFT, RIGHT, DOWN], tee:[DOWN], tile:"straight_full_n", overlay:"tcap_full_nw"}, {match:[UP, LEFT, RIGHT], tee:[UP], tile:"straight_full_n", overlay:"tcap_full_se"}, {match:[LEFT, RIGHT, UP, DOWN], partial:[UP, DOWN], cross:[UP, DOWN], tile:"straight_full_n", overlay:"tcap_full_se", overlay2:"tcap_full_nw"}, {match:[LEFT, RIGHT], tile:"straight_full_n"}, {match:[UP, LEFT], tile:"straight_full_n"}, {match:[], tile:"straightBlocked_n"}];
                m_roadRules3x4 = [{match:[UP, LEFT], partial:[LEFT], corner:[UP], tile:"corner_w", overlay:"straight_1_et", overlay2:"corner_b_w"}, {match:[UP, RIGHT], partial:[RIGHT], corner:[UP], tile:"corner_n", overlay:"straight_1_et", overlay2:"corner_b_n"}, {match:[LEFT, DOWN], partial:[LEFT], corner:[DOWN], tile:"corner_s", overlay:"straight_1_e", overlay2:"corner_b_s"}, {match:[RIGHT, DOWN], partial:[RIGHT], corner:[DOWN], tile:"corner_e", overlay:"straight_1_e", overlay2:"corner_b_e"}, {match:[UP, LEFT, RIGHT], partial:[LEFT, RIGHT], tee:[UP], tile:"straightaway_n", overlay:"tcap_se", overlay2:"straightaway_e"}, {match:[LEFT, RIGHT, DOWN], partial:[LEFT, RIGHT], tee:[DOWN], tile:"straightaway_n", overlay:"tcap_nw", overlay2:"straightaway_e"}, {match:[UP, LEFT, DOWN], partial:[LEFT], tee:[LEFT], tile:"straight_full_e", overlay:"tcap_full_ne"}, {match:[UP, RIGHT, DOWN], partial:[RIGHT], tee:[RIGHT], tile:"straight_full_e", overlay:"tcap_full_sw"}, {match:[UP, LEFT, DOWN], tee:[LEFT], tile:"straight_full_e", overlay:"tcap_full_ne"}, {match:[UP, RIGHT, DOWN], tee:[RIGHT], tile:"straight_full_e", overlay:"tcap_full_sw"}, {match:[UP, DOWN, LEFT, RIGHT], partial:[LEFT, RIGHT], cross:[LEFT, RIGHT], tile:"straight_full_e", overlay:"tcap_full_ne", overlay2:"tcap_full_sw"}, {match:[UP, DOWN], tile:"straight_full_e"}, {match:[UP, DOWN], tile:"straight_full_e"}, {match:[], tile:"straightBlocked_e"}];
                for(int i0 = 0; i0 < m_roadRules.size(); i0++)
                {
                	_loc_2 = m_roadRules.get(i0);

                    _loc_3 = _loc_2.match;
                    _loc_3.sort();
                }
                for(int i0 = 0; i0 < m_roadRules2x3.size(); i0++)
                {
                	_loc_2 = m_roadRules2x3.get(i0);

                    _loc_3 = _loc_2.match;
                    _loc_3.sort();
                }
                for(int i0 = 0; i0 < m_roadRules3x2.size(); i0++)
                {
                	_loc_2 = m_roadRules3x2.get(i0);

                    _loc_3 = _loc_2.match;
                    _loc_3.sort();
                }
                for(int i0 = 0; i0 < m_roadRules4x3.size(); i0++)
                {
                	_loc_2 = m_roadRules4x3.get(i0);

                    _loc_3 = _loc_2.match;
                    _loc_3.sort();
                }
                for(int i0 = 0; i0 < m_roadRules3x4.size(); i0++)
                {
                	_loc_2 = m_roadRules3x4.get(i0);

                    _loc_3 = _loc_2.match;
                    _loc_3.sort();
                }
            }
            super(param1);
            m_objectType = WorldObjectTypes.ROAD;
            return;
        }//end

        protected void  initSpriteScale ()
        {
            if (SPRITE_SCALE == 0)
            {
                SPRITE_SCALE = 1 / Global.gameSettings().getNumber("maxZoom", 4);
            }
            return;
        }//end

        public Array  adjacentRoads ()
        {
            return this.m_adjacentRoads;
        }//end

        public boolean  tight ()
        {
            return this.m_isTightSection;
        }//end

         protected String  getLayerName ()
        {
            return "road";
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            return;
        }//end

         public void  setHighlighted (boolean param1 ,int param2 =1.67552e +007)
        {
            _loc_3 =             !param1 || Global.world.isEditMode;
            if (_loc_3)
            {
                super.setHighlighted(param1);
            }
            return;
        }//end

        protected void  drawDebugInfo ()
        {
            Array _loc_6 =null ;
            if (m_dbgShape == null)
            {
                return;
            }
            double _loc_1 =0.6;
            m_dbgShape.graphics.lineStyle(1, 4278190335);
            _loc_2 = getPosition();
            _loc_2 = getSize().scale(0.5).add(_loc_2);
            _loc_3 = IsoMath.tilePosToPixelPos(_loc_2.x-_loc_1,_loc_2.y-_loc_1);
            m_dbgShape.graphics.moveTo(_loc_3.x, _loc_3.y);
            _loc_3 = IsoMath.tilePosToPixelPos(_loc_2.x + _loc_1, _loc_2.y - _loc_1);
            m_dbgShape.graphics.lineTo(_loc_3.x, _loc_3.y);
            _loc_3 = IsoMath.tilePosToPixelPos(_loc_2.x + _loc_1, _loc_2.y + _loc_1);
            m_dbgShape.graphics.lineTo(_loc_3.x, _loc_3.y);
            _loc_3 = IsoMath.tilePosToPixelPos(_loc_2.x - _loc_1, _loc_2.y + _loc_1);
            m_dbgShape.graphics.lineTo(_loc_3.x, _loc_3.y);
            _loc_3 = IsoMath.tilePosToPixelPos(_loc_2.x - _loc_1, _loc_2.y - _loc_1);
            m_dbgShape.graphics.lineTo(_loc_3.x, _loc_3.y);
            _loc_4 = this.getBoundingBox();
            m_dbgShape.graphics.lineStyle(0.25, 2147549183);
            _loc_3 = IsoMath.tilePosToPixelPos(_loc_4.x, _loc_4.y);
            m_dbgShape.graphics.moveTo(_loc_3.x, _loc_3.y);
            _loc_3 = IsoMath.tilePosToPixelPos(_loc_4.x + _loc_4.width, _loc_4.y);
            m_dbgShape.graphics.lineTo(_loc_3.x, _loc_3.y);
            _loc_3 = IsoMath.tilePosToPixelPos(_loc_4.x + _loc_4.width, _loc_4.y + _loc_4.height);
            m_dbgShape.graphics.lineTo(_loc_3.x, _loc_3.y);
            _loc_3 = IsoMath.tilePosToPixelPos(_loc_4.x, _loc_4.y + _loc_4.height);
            m_dbgShape.graphics.lineTo(_loc_3.x, _loc_3.y);
            _loc_3 = IsoMath.tilePosToPixelPos(_loc_4.x, _loc_4.y);
            m_dbgShape.graphics.lineTo(_loc_3.x, _loc_3.y);
            Array _loc_5 =.get(.get( -_loc_1 ,0) ,.get(0 ,-_loc_1) ,.get(_loc_1 ,0) ,.get(0 ,_loc_1)) ;
            if (this.m_currentRule != null && "partial" in this.m_currentRule)
            {
                _loc_6 = this.m_currentRule.partial;
            }
            else
            {
                _loc_6 = new Array();
            }
            if (this.m_adjacentRoads == null)
            {
                return;
            }
            int _loc_7 =0;
            while (_loc_7 < this.m_adjacentRoads.length())
            {

                if (this.m_adjacentRoads.get(_loc_7) == null)
                {
                }
                else
                {
                    if (this.m_adjacent.indexOf(_loc_7) >= 0)
                    {
                        m_dbgShape.graphics.lineStyle(1, 4278255360);
                    }
                    else if (this.m_adjacentPartial.indexOf(_loc_7) >= 0)
                    {
                        m_dbgShape.graphics.lineStyle(1, 4294967040);
                    }
                    else
                    {
                        m_dbgShape.graphics.lineStyle(1, 4294901760);
                    }
                    _loc_3 = IsoMath.tilePosToPixelPos(_loc_2.x + _loc_5.get(_loc_7).get(0), _loc_2.y + _loc_5.get(_loc_7).get(1));
                    m_dbgShape.graphics.moveTo(_loc_3.x + 2 * _loc_5.get(3 - _loc_7).get(0), _loc_3.y + 2 * _loc_5.get(3 - _loc_7).get(1));
                    m_dbgShape.graphics.lineTo(_loc_3.x - 2 * _loc_5.get(3 - _loc_7).get(0), _loc_3.y - 2 * _loc_5.get(3 - _loc_7).get(1));
                    if (_loc_6.indexOf(_loc_7) >= 0)
                    {
                        m_dbgShape.graphics.lineStyle(1, 4294902015);
                        m_dbgShape.graphics.moveTo(_loc_3.x + 2 * _loc_5.get(_loc_7).get(0), _loc_3.y + 2 * _loc_5.get(_loc_7).get(1));
                        m_dbgShape.graphics.lineTo(_loc_3.x - 2 * _loc_5.get(_loc_7).get(0), _loc_3.y - 2 * _loc_5.get(_loc_7).get(1));
                    }
                }
                _loc_7++;
            }
            return;
        }//end

        public Sprite  getOverlay1 ()
        {
            return this.m_overlay1;
        }//end

        public Sprite  getOverlay2 ()
        {
            return this.m_overlay2;
        }//end

        public String  getOverlayImage ()
        {
            String _loc_1 ="";
            if (this.m_currentRule && "overlay" in this.m_currentRule)
            {
                _loc_1 = this.m_currentRule.overlay;
            }
            return _loc_1;
        }//end

        public String  getOverlayImage2 ()
        {
            String _loc_1 ="";
            if (this.m_currentRule && "overlay2" in this.m_currentRule)
            {
                _loc_1 = this.m_currentRule.overlay2;
            }
            return _loc_1;
        }//end

         protected ItemImageInstance  getCurrentImage ()
        {
            _loc_1 = this.getOverlayImage();
            if (_loc_1 != "")
            {
                m_item.getCachedImage(_loc_1);
            }
            _loc_1 = this.getOverlayImage2();
            if (_loc_1 != "")
            {
                m_item.getCachedImage(_loc_1);
            }
            String _loc_2 ="road_circle";
            if (!this.m_didConnectivity)
            {
                _loc_2 = "";
            }
            if (this.m_currentRule)
            {
                _loc_2 = this.m_currentRule.tile;
            }
            if (this.m_invalidDragPos)
            {
                _loc_2 = "road_blocked";
            }
            _loc_3 = m_item.getCachedImage(_loc_2);
            if (_loc_3 == null && this.m_didConnectivity && !m_item.isCachedImageLoading(_loc_2))
            {
                _loc_3 = m_item.getCachedImage("road_circle");
            }
            return _loc_3;
        }//end

        protected void  handleStretchTee (ItemImageInstance param1 ,int param2 ,ItemImageInstance param3 )
        {
            double _loc_6 =0;
            double _loc_7 =0;
            int _loc_8 =0;
            Road _loc_9 =null ;
            Vector2 _loc_10 =null ;
            if (param1 != null)
            {
                this.m_overlay1Mode = OVERLAY_TOP;
                this.m_overlay1X = 0;
                this.m_overlay1Y = 0;
            }
            boolean _loc_4 =false ;
            boolean _loc_5 =false ;
            if (this.m_adjacentPartial.length == 2)
            {
                _loc_6 = 0;
                _loc_7 = 0;
                param2 = this.m_adjacentPartial.get(0);
                _loc_8 = this.m_adjacent.get(0);
                _loc_9 = this.m_adjacentRoads.get(param2);
                if (_loc_9 && this.shouldAdjust(_loc_9, param2))
                {
                    if (param2 == LEFT || param2 == RIGHT)
                    {
                        _loc_7 = _loc_9.positionY - positionY;
                        if (_loc_8 == UP)
                        {
                            _loc_5 = _loc_7 < 0;
                            _loc_4 = _loc_7 > 0;
                        }
                        else
                        {
                            _loc_5 = _loc_7 > 0;
                            _loc_4 = _loc_7 < 0;
                        }
                    }
                    else
                    {
                        _loc_6 = _loc_9.positionX - positionX;
                        if (_loc_8 == RIGHT)
                        {
                            _loc_5 = _loc_6 < 0;
                            _loc_4 = _loc_6 > 0;
                        }
                        else
                        {
                            _loc_5 = _loc_6 > 0;
                            _loc_4 = _loc_6 < 0;
                        }
                    }
                    _loc_10 = IsoMath.getPixelDeltaFromTileDelta(_loc_6, _loc_7);
                    this.m_fitOffset.x = _loc_6;
                    this.m_fitOffset.y = _loc_7;
                    if (_loc_4)
                    {
                        this.m_fitShrink = true;
                        this.m_overlayContentMode = OVERLAY_OFFSET;
                        this.m_overlayContentX = _loc_10.x / SPRITE_SCALE;
                        this.m_overlayContentY = _loc_10.y / SPRITE_SCALE;
                        this.m_overlay2Mode = OVERLAY_NONE;
                    }
                    else if (_loc_5)
                    {
                        this.m_fitStretch = true;
                        this.m_overlayContentMode = OVERLAY_OFFSET;
                        this.m_overlayContentX = _loc_10.x / SPRITE_SCALE;
                        this.m_overlayContentY = _loc_10.y / SPRITE_SCALE;
                        if (param3 != null)
                        {
                            this.m_overlay2Mode = OVERLAY_BOTTOM;
                            this.m_overlay2X = 0;
                            this.m_overlay2Y = 0;
                        }
                    }
                    this.m_overlay1X = this.m_overlay1X + _loc_10.x;
                    this.m_overlay1Y = this.m_overlay1Y + _loc_10.y;
                }
            }
            if (!_loc_4 && !_loc_5)
            {
                if (m_size.x > SIZE_X)
                {
                    _loc_10 = IsoMath.getPixelDeltaFromTileDelta(1, 0);
                }
                else if (m_size.y > SIZE_Y)
                {
                    _loc_10 = IsoMath.getPixelDeltaFromTileDelta(0, 1);
                }
                else
                {
                    param3 = null;
                }
                if (param3 != null)
                {
                    this.m_overlay2Mode = OVERLAY_BOTTOM;
                    this.m_overlay2X = _loc_10.x;
                    this.m_overlay2Y = _loc_10.y;
                }
            }
            return;
        }//end

        protected void  handleStretchAdjustment ()
        {
            this.m_overlayContentMode = Road.OVERLAY_OFFSET;
            double _loc_1 =0;
            double _loc_2 =0;
            if (this.m_stretchDirection == DOWN)
            {
                _loc_2 = -1;
            }
            else if (this.m_stretchDirection == LEFT)
            {
                _loc_1 = -1;
            }
            _loc_3 = IsoMath.getPixelDeltaFromTileDelta(_loc_1 ,_loc_2 );
            this.m_overlayContentX = _loc_3.x / SPRITE_SCALE;
            this.m_overlayContentY = _loc_3.y / SPRITE_SCALE;
            return;
        }//end

        protected void  handleTeeAdjustment (ItemImageInstance param1 ,int param2 ,int param3 )
        {
            double _loc_7 =0;
            double _loc_8 =0;
            Road _loc_9 =null ;
            boolean _loc_10 =false ;
            Vector2 _loc_11 =null ;
            _loc_4 = OVERLAY_NONE;
            double _loc_5 =0;
            double _loc_6 =0;
            if (param1 != null)
            {
                _loc_4 = OVERLAY_TOP;
                _loc_7 = 0;
                _loc_8 = 0;
                _loc_9 = this.m_adjacentRoads.get(param2);
                if (_loc_9)
                {
                    _loc_10 = this.shouldAdjust(_loc_9, param2);
                    if (_loc_10)
                    {
                        if (param2 == LEFT || param2 == RIGHT)
                        {
                            _loc_8 = _loc_9.m_position.y - m_position.y;
                            if (_loc_9.m_size.y < SIZE_Y && _loc_8 > 0)
                            {
                                _loc_8 = _loc_8 - 1;
                            }
                        }
                        else
                        {
                            _loc_7 = _loc_9.m_position.x - m_position.x;
                            if (_loc_9.m_size.x < SIZE_X && _loc_7 > 0)
                            {
                                _loc_7 = _loc_7 - 1;
                            }
                        }
                    }
                    else if (param2 == LEFT || param2 == RIGHT)
                    {
                        if (m_size.y < SIZE_Y && _loc_9.m_size.y >= SIZE_Y)
                        {
                            if (m_position.y > _loc_9.m_position.y)
                            {
                                _loc_8 = _loc_8 - 1;
                            }
                        }
                        if (m_size.x < SIZE_X && _loc_9.m_size.x >= SIZE_X)
                        {
                            if (m_position.x > _loc_9.m_position.x)
                            {
                                _loc_7 = _loc_7 - 1;
                            }
                        }
                    }
                    _loc_11 = IsoMath.getPixelDeltaFromTileDelta(_loc_7, _loc_8);
                    _loc_5 = _loc_5 + _loc_11.x;
                    _loc_6 = _loc_6 + _loc_11.y;
                }
            }
            if (param3 == 1)
            {
                this.m_overlay1Mode = _loc_4;
                this.m_overlay1X = _loc_5;
                this.m_overlay1Y = _loc_6;
            }
            else if (param3 == 2)
            {
                this.m_overlay2Mode = _loc_4;
                this.m_overlay2X = _loc_5;
                this.m_overlay2Y = _loc_6;
            }
            return;
        }//end

        public boolean  cornerShouldAdjust (Road param1 ,int param2 )
        {
            if (param2 == UP || param2 == DOWN)
            {
                if (m_size.x != SIZE_X && param1.m_size.x == SIZE_X)
                {
                    return true;
                }
                if (m_size.x == SIZE_X && param1.m_size.x != SIZE_X)
                {
                    return false;
                }
            }
            else
            {
                if (m_size.y != SIZE_Y && param1.m_size.y == SIZE_Y)
                {
                    return true;
                }
                if (m_size.y == SIZE_Y && param1.m_size.y != SIZE_Y)
                {
                    return false;
                }
            }
            _loc_3 = this.m_canAdjust.get(param2);
            int _loc_4 =0;
            if (param1.m_canAdjust.get(Road.m_flipDirection.get(param2)) != null)
            {
                _loc_4 = param1.m_canAdjust.get(Road.m_flipDirection.get(param2));
            }
            if (_loc_4 == 0 && _loc_3 > 0)
            {
                return true;
            }
            if (_loc_3 == 0 && _loc_4 > 0)
            {
                return false;
            }
            if (this.m_adjacentPartial.length + this.m_adjacent.length < param1.m_adjacent.length + param1.m_adjacentPartial.length())
            {
                return true;
            }
            if (this.m_adjacentPartial.length + this.m_adjacent.length > param1.m_adjacent.length + param1.m_adjacentPartial.length())
            {
                return false;
            }
            return this.shouldAdjust(param1, param2);
        }//end

        public boolean  shouldAdjust (Road param1 ,int param2 )
        {
            Vector3 _loc_5 =null ;
            Vector3 _loc_6 =null ;
            param1 =(Road) param1;
            if (param1 == null)
            {
                return true;
            }
            _loc_3 = this.m_canAdjust.get(param2);
            int _loc_4 =0;
            if (param1.m_canAdjust.get(Road.m_flipDirection.get(param2)) != null)
            {
                _loc_4 = param1.m_canAdjust.get(Road.m_flipDirection.get(param2));
            }
            if (_loc_4 > _loc_3)
            {
                return false;
            }
            if (_loc_4 == _loc_3)
            {
                _loc_5 = getPosition();
                _loc_6 = param1.getPosition();
                if (_loc_5.x < _loc_6.x)
                {
                    return false;
                }
                if (_loc_5.x == _loc_6.x && _loc_5.y < _loc_6.y)
                {
                    return false;
                }
            }
            return true;
        }//end

        public boolean  shouldTighten (Road param1 ,int param2 )
        {
            _loc_3 = this.m_adjacent.length+this.m_adjacentPartial.length;
            _loc_4 = param1.m_adjacent.length+param1.m_adjacentPartial.length;
            if (param2 == DOWN && param2 == UP && param1.getSize().y < SIZE_Y && getSize().y >= SIZE_Y)
            {
                return false;
            }
            if (param2 == DOWN && param2 == UP && param1.getSize().y >= SIZE_Y && getSize().y < SIZE_Y)
            {
                return true;
            }
            if ((param2 == LEFT || param2 == RIGHT) && param1.getSize().x < SIZE_Y && getSize().x >= SIZE_X)
            {
                return false;
            }
            if ((param2 == LEFT || param2 == RIGHT) && param1.getSize().x >= SIZE_Y && getSize().x < SIZE_X)
            {
                return true;
            }
            if (_loc_3 == 2 && _loc_4 != 2)
            {
                return true;
            }
            if (_loc_3 != 2 && _loc_4 == 2)
            {
                return false;
            }
            if (this.m_isRoadBeingDragged && !param1.m_isRoadBeingDragged)
            {
                return true;
            }
            if (param1.m_isRoadBeingDragged)
            {
                return false;
            }
            _loc_5 = getPosition();
            _loc_6 = param1.getPosition ();
            if (_loc_5.x < _loc_6.x)
            {
                return false;
            }
            if (_loc_5.x == _loc_6.x && _loc_5.y < _loc_6.y)
            {
                return false;
            }
            return true;
        }//end

        public boolean  shouldStretch (Road param1 ,int param2 )
        {
            _loc_3 = this.m_adjacent.length+this.m_adjacentPartial.length;
            _loc_4 = param1.m_adjacent.length+param1.m_adjacentPartial.length;
            if (param2 == DOWN && param2 == UP && param1.getSize().y > SIZE_Y && getSize().y <= SIZE_Y)
            {
                return false;
            }
            if (param2 == DOWN && param2 == UP && param1.getSize().y <= SIZE_Y && getSize().y > SIZE_Y)
            {
                return true;
            }
            if ((param2 == LEFT || param2 == RIGHT) && param1.getSize().x > SIZE_Y && getSize().x <= SIZE_X)
            {
                return false;
            }
            if ((param2 == LEFT || param2 == RIGHT) && param1.getSize().x <= SIZE_Y && getSize().x > SIZE_X)
            {
                return true;
            }
            if (_loc_3 == 2 && _loc_4 != 2)
            {
                return true;
            }
            if (_loc_3 != 2 && _loc_4 == 2)
            {
                return false;
            }
            if (_loc_3 == 1 && _loc_4 > 1)
            {
                return false;
            }
            if (_loc_3 > 1 && _loc_4 == 1)
            {
                return true;
            }
            if (this.m_isRoadBeingDragged && !param1.m_isRoadBeingDragged)
            {
                return true;
            }
            if (param1.m_isRoadBeingDragged)
            {
                return false;
            }
            _loc_5 = getPosition();
            _loc_6 = param1.getPosition ();
            if (_loc_5.x < _loc_6.x)
            {
                return true;
            }
            if (_loc_5.x == _loc_6.x && _loc_5.y < _loc_6.y)
            {
                return true;
            }
            return false;
        }//end

        protected Object  checkOverlapCorner ()
        {
            double _loc_2 =0;
            double _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            Road _loc_6 =null ;
            Vector2 _loc_7 =null ;
            boolean _loc_8 =false ;
            boolean _loc_9 =false ;
            Vector3 _loc_10 =null ;
            Vector3 _loc_11 =null ;
            Array _loc_12 =null ;
            Road _loc_13 =null ;
            int _loc_1 =-1;
            if (this.m_adjacent.length == 1)
            {
                _loc_1 = this.m_adjacent.get(0);
            }
            if (this.m_adjacentPartial.length == 1)
            {
                _loc_2 = 0;
                _loc_3 = 0;
                _loc_4 = this.m_adjacentPartial.get(0);
                _loc_5 = this.m_adjacent.get(0);
                _loc_6 = this.m_adjacentRoads.get(_loc_4);
                if (this.m_isTightSection)
                {
                    if (_loc_6 && _loc_6.adjacentRoads.get(_loc_1) != null)
                    {
                        _loc_8 = (_loc_4 == LEFT || _loc_4 == RIGHT) && _loc_6.m_size.y >= SIZE_Y;
                        if ((_loc_4 == UP || _loc_4 == DOWN) && _loc_6.m_size.x >= SIZE_X)
                        {
                            _loc_8 = true;
                        }
                        if (!_loc_8)
                        {
                            this.m_adjacentRoads.put(_loc_4,  null);
                            this.m_adjacentPartial = new Array();
                            return null;
                        }
                    }
                }
                if (_loc_6 && this.cornerShouldAdjust(_loc_6, _loc_4))
                {
                    _loc_9 = false;
                    if (_loc_4 == LEFT || _loc_4 == RIGHT)
                    {
                        _loc_3 = _loc_6.getPosition().y - getPosition().y;
                        if (_loc_5 == UP)
                        {
                            _loc_9 = _loc_3 < 0;
                        }
                        else
                        {
                            _loc_9 = _loc_3 > 0;
                        }
                    }
                    else
                    {
                        _loc_2 = _loc_6.getPosition().x - getPosition().x;
                        if (_loc_5 == RIGHT)
                        {
                            _loc_9 = _loc_2 < 0;
                        }
                        else
                        {
                            _loc_9 = _loc_2 > 0;
                        }
                    }
                    if (_loc_9)
                    {
                        _loc_10 = getSize();
                        _loc_11 = getPosition();
                        if (_loc_2 == 1)
                        {
                            _loc_10.x = SIZE_X + 1;
                        }
                        else if (_loc_2 == -1)
                        {
                            (_loc_11.x - 1);
                            _loc_10.x = SIZE_X + 1;
                        }
                        else if (_loc_3 == 1)
                        {
                            _loc_10.y = SIZE_Y + 1;
                        }
                        else if (_loc_3 == -1)
                        {
                            (_loc_11.y - 1);
                            _loc_10.y = SIZE_Y + 1;
                        }
                        else
                        {
                            return this.m_currentRule;
                        }
                        if (Global.world.citySim.waterManager.waterDataLoaded())
                        {
                            if (!Global.world.citySim.waterManager.positionOnValidLand(new Rectangle(_loc_11.x, _loc_11.y, _loc_10.x, _loc_10.y)))
                            {
                                return null;
                            }
                        }
                        _loc_12 = new Array();
                        if (Global.world.checkCollision(_loc_11.x, _loc_11.y, _loc_11.x + _loc_10.x, _loc_11.y + _loc_10.y, [this], _loc_12, Constants.WORLDOBJECT_ALL, this))
                        {
                            if (_loc_12.length == 1 && _loc_12.get(0) instanceof Road)
                            {
                                _loc_13 =(Road) _loc_12.get(0);
                                if (_loc_13.m_isDragged && _loc_13.m_invalidDragPos)
                                {
                                    return this.m_currentRule;
                                }
                            }
                            this.m_adjacentRoads.put(_loc_4,  null);
                            this.m_adjacentPartial = new Array();
                            return null;
                        }
                    }
                }
                if (_loc_9 && m_size.x <= SIZE_X && m_size.y <= SIZE_Y)
                {
                    this.m_checkForReplaceAtDrop = true;
                }
                else if ((_loc_2 != 0 || _loc_3 != 0) && m_size.x >= SIZE_X && m_size.y >= SIZE_Y)
                {
                    this.m_checkForReplaceAtDrop = true;
                }
                else if (m_size.x != SIZE_X || m_size.y != SIZE_Y)
                {
                    this.m_checkForReplaceAtDrop = true;
                }
            }
            return this.m_currentRule;
        }//end

        protected void  handleCornerAdjustment (ItemImageInstance param1 ,ItemImageInstance param2 )
        {
            double _loc_5 =0;
            double _loc_6 =0;
            int _loc_7 =0;
            int _loc_8 =0;
            Road _loc_9 =null ;
            Vector2 _loc_10 =null ;
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            if (this.m_adjacentPartial.length == 1)
            {
                _loc_5 = 0;
                _loc_6 = 0;
                _loc_7 = this.m_adjacentPartial.get(0);
                _loc_8 = this.m_adjacent.get(0);
                _loc_9 = this.m_adjacentRoads.get(_loc_7);
                if (_loc_9 && this.cornerShouldAdjust(_loc_9, _loc_7))
                {
                    if (_loc_7 == LEFT || _loc_7 == RIGHT)
                    {
                        _loc_6 = _loc_9.getPosition().y - getPosition().y;
                        if (_loc_8 == UP)
                        {
                            _loc_4 = _loc_6 < 0;
                            _loc_3 = _loc_6 > 0;
                        }
                        else
                        {
                            _loc_4 = _loc_6 > 0;
                            _loc_3 = _loc_6 < 0;
                        }
                    }
                    else
                    {
                        _loc_5 = _loc_9.getPosition().x - getPosition().x;
                        if (_loc_8 == RIGHT)
                        {
                            _loc_4 = _loc_5 < 0;
                            _loc_3 = _loc_5 > 0;
                        }
                        else
                        {
                            _loc_4 = _loc_5 > 0;
                            _loc_3 = _loc_5 < 0;
                        }
                    }
                    _loc_10 = IsoMath.getPixelDeltaFromTileDelta(_loc_5, _loc_6);
                    this.m_fitOffset.x = _loc_5;
                    this.m_fitOffset.y = _loc_6;
                    if (_loc_3)
                    {
                        this.m_fitShrink = true;
                        this.m_overlayContentMode = OVERLAY_OFFSET;
                        this.m_overlayContentX = _loc_10.x / SPRITE_SCALE;
                        this.m_overlayContentY = _loc_10.y / SPRITE_SCALE;
                        if (param2 != null)
                        {
                            this.m_overlay2Mode = OVERLAY_TOP;
                            this.m_overlay2X = _loc_10.x;
                            this.m_overlay2Y = _loc_10.y;
                        }
                    }
                    else if (_loc_4)
                    {
                        this.m_fitStretch = true;
                        this.m_overlayContentMode = OVERLAY_OFFSET;
                        this.m_overlayContentX = _loc_10.x / SPRITE_SCALE;
                        this.m_overlayContentY = _loc_10.y / SPRITE_SCALE;
                        if (param1 != null)
                        {
                            this.m_overlay1Mode = OVERLAY_BOTTOM;
                            this.m_overlay1X = 0;
                            this.m_overlay1Y = 0;
                        }
                    }
                }
            }
            if (!_loc_3 && !_loc_4)
            {
                if (m_size.x > SIZE_X)
                {
                    _loc_10 = IsoMath.getPixelDeltaFromTileDelta(1, 0);
                    if (param1 != null)
                    {
                        this.m_overlay1Mode = OVERLAY_BOTTOM;
                        this.m_overlay1X = _loc_10.x;
                        this.m_overlay1Y = _loc_10.y;
                    }
                }
                else if (m_size.y > SIZE_Y)
                {
                    _loc_10 = IsoMath.getPixelDeltaFromTileDelta(0, 1);
                    if (param1 != null)
                    {
                        this.m_overlay1Mode = OVERLAY_BOTTOM;
                        this.m_overlay1X = _loc_10.x;
                        this.m_overlay1Y = _loc_10.y;
                    }
                }
            }
            return;
        }//end

        protected void  clearContent ()
        {
            if (this.m_overlay1 != null && this.m_overlay1.parent != null)
            {
                this.m_overlay1.parent.removeChild(this.m_overlay1);
            }
            if (this.m_overlay2 != null && this.m_overlay2.parent != null)
            {
                this.m_overlay2.parent.removeChild(this.m_overlay2);
            }
            if (this.m_overlay3 != null && this.m_overlay3.parent != null)
            {
                this.m_overlay3.parent.removeChild(this.m_overlay3);
            }
            this.m_overlay1 = null;
            this.m_overlay2 = null;
            this.m_overlay3 = null;
            return;
        }//end

         public void  updateDisplayObjectTransform ()
        {
            super.updateDisplayObjectTransform();
            m_displayObject.y = Math.floor(m_displayObject.y * 2) / 2;
            return;
        }//end

        protected void  colorize (DisplayObject param1 )
        {
            CompositeItemImage _loc_2 =null ;
            Bitmap _loc_3 =null ;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            ColorTransform _loc_7 =null ;
            BitmapData _loc_8 =null ;
            if (this.m_heatMap == true)
            {
                if (param1 instanceof CompositeItemImage)
                {
                    _loc_2 =(CompositeItemImage) param1;
                    _loc_3 =(Bitmap) _loc_2.getBaseImage();
                    _loc_10 = colorShift+1;
                    colorShift = _loc_10;
                    if (colorShift > 63)
                    {
                        colorShift = 1;
                    }
                    _loc_4 = ((colorShift & 1) + (colorShift & 8) / 4) * 50;
                    _loc_5 = ((colorShift & 2) + (colorShift & 16) / 4) / 2 * 50;
                    _loc_6 = ((colorShift & 4) + (colorShift & 32) / 4) / 4 * 50;
                    _loc_7 = new ColorTransform(1, 1, 1, 1, _loc_4, _loc_5, _loc_6, 0);
                    _loc_8 = new BitmapData(_loc_3.width, _loc_3.height, true, 0);
                    _loc_8.draw(_loc_3, null, _loc_7, null, null, true);
                    _loc_3.bitmapData = _loc_8;
                }
            }
            return;
        }//end

         public void  replaceContent (DisplayObject param1 )
        {
            DisplayObject _loc_7 =null ;
            DisplayObject _loc_8 =null ;
            ItemImageInstance _loc_9 =null ;
            this.colorize(param1);
            _loc_2 = (Sprite)m_displayObject
            if (_loc_2.contains(param1))
            {
                return;
            }
            super.replaceContent(param1);
            if (!this.m_attached)
            {
                return;
            }
            _loc_3 = (RoadLayer)Global.world.getObjectLayerByName(this.getLayerName())
            _loc_4 = _loc_3.bottomObject;
            _loc_5 = _loc_3.topObject;
            this.clearContent();
            if (this.m_overlayContentMode == OVERLAY_BOTTOM)
            {
                param1.x = param1.x + this.m_overlayContentX;
                param1.y = param1.y + this.m_overlayContentY;
            }
            else if (this.m_overlayContentMode == OVERLAY_TOP)
            {
                param1.x = param1.x + this.m_overlayContentX;
                param1.y = param1.y + this.m_overlayContentY;
            }
            else if (this.m_overlayContentMode == OVERLAY_OFFSET)
            {
                param1.x = param1.x + this.m_overlayContentX;
                param1.y = param1.y + this.m_overlayContentY;
            }
            _loc_6 = m_item.getCachedImage(this.getOverlayImage());
            if (m_item.getCachedImage(this.getOverlayImage()) != null)
            {
                _loc_7 =(DisplayObject) _loc_6.image;
                this.colorize(_loc_7);
                _loc_8 = null;
                _loc_9 = m_item.getCachedImage(this.getOverlayImage2());
                if (_loc_9 != null)
                {
                    _loc_8 =(DisplayObject) _loc_9.image;
                    this.colorize(_loc_8);
                }
                if (this.m_overlay1Mode != OVERLAY_NONE && _loc_7)
                {
                    this.m_overlay1 = new Sprite();
                    this.m_overlay1.x = this.m_overlay1X + _loc_2.x;
                    this.m_overlay1.y = this.m_overlay1Y + _loc_2.y;
                    this.m_overlay1.scaleX = SPRITE_SCALE;
                    this.m_overlay1.scaleY = SPRITE_SCALE;
                    _loc_7.x = _loc_6.offsetX;
                    _loc_7.y = _loc_6.offsetY;
                    this.m_overlay1.addChild(_loc_7);
                    if (this.m_overlay1Mode == OVERLAY_TOP)
                    {
                        _loc_5.addChild(this.m_overlay1);
                    }
                    else if (this.m_overlay1Mode == OVERLAY_BOTTOM)
                    {
                        _loc_4.addChild(this.m_overlay1);
                    }
                }
                if (this.m_overlay2Mode != OVERLAY_NONE && _loc_8)
                {
                    this.m_overlay2 = new Sprite();
                    this.m_overlay2.x = this.m_overlay2X + _loc_2.x;
                    this.m_overlay2.y = this.m_overlay2Y + _loc_2.y;
                    this.m_overlay2.scaleX = SPRITE_SCALE;
                    this.m_overlay2.scaleY = SPRITE_SCALE;
                    _loc_8.x = _loc_9.offsetX;
                    _loc_8.y = _loc_9.offsetY;
                    this.m_overlay2.addChild(_loc_8);
                    if (this.m_overlay2Mode == OVERLAY_TOP)
                    {
                        _loc_5.addChild(this.m_overlay2);
                    }
                    else if (this.m_overlay2Mode == OVERLAY_BOTTOM)
                    {
                        _loc_4.addChild(this.m_overlay2);
                    }
                }
            }
            if (Config.DEBUG_MODE)
            {
                this.drawDebugInfo();
            }
            return;
        }//end

         protected void  adjustSize (Vector3 param1 )
        {
            int _loc_2 =3;
            param1.y = 3;
            param1.x = _loc_2;
            return;
        }//end

         protected void  onItemImageLoaded (Event event )
        {
            this.calculateOverlayOffsets();
            super.onItemImageLoaded(event);
            return;
        }//end

         public double  getDepthPriority ()
        {
            if (this.m_currentRule && "tee" in this.m_currentRule)
            {
                return 1;
            }
            if (this.m_overlayContentMode == OVERLAY_TOP || this.m_overlayContentMode == OVERLAY_BOTTOM)
            {
                return 1;
            }
            return 0;
        }//end

         protected void  calculateDepthIndex ()
        {
            super.calculateDepthIndex();
            if (this.m_currentRule && "tee" in this.m_currentRule && this.m_adjacent.length == 1 && this.m_adjacentPartial.length == 2)
            {
                if (m_size.y < SIZE_Y && this.m_adjacent.get(0) == DOWN || m_size.x < SIZE_X && this.m_adjacent.get(0) == LEFT)
                {
                    this.m_overlayContentMode = OVERLAY_TOP;
                }
            }
            if (this.m_overlayContentMode == OVERLAY_TOP)
            {
                m_depthIndex = m_depthIndex - 6000;
            }
            else if (this.m_overlayContentMode == OVERLAY_BOTTOM)
            {
                m_depthIndex = m_depthIndex + 16000;
            }
            return;
        }//end

        protected void  calculateOverlayOffsets ()
        {
            ItemImageInstance _loc_2 =null ;
            int _loc_3 =0;
            this.m_overlayContentMode = OVERLAY_NONE;
            this.m_overlay1Mode = OVERLAY_NONE;
            this.m_overlay2Mode = OVERLAY_NONE;
            _loc_1 = m_item.getCachedImage(this.getOverlayImage());
            if (this.m_isStretchSection)
            {
                this.handleStretchAdjustment();
            }
            if (this.m_isRoadBeingDragged)
            {
                if (Road.s_shrinkRoad1 != null && Road.s_shrinkRoad2 != null)
                {
                    this.m_overlayContentMode = OVERLAY_BOTTOM;
                }
                else if (this.m_invalidDragPos)
                {
                    this.m_overlayContentMode = OVERLAY_TOP;
                }
                this.m_overlayContentX = 0;
                this.m_overlayContentY = 0;
            }
            if (_loc_1 == null && this.getOverlayImage() == "")
            {
                return;
            }
            if ("tee" in this.m_currentRule)
            {
                _loc_3 = this.m_currentRule.tee.get(0);
                if (this.m_adjacentPartial.length == 2 && this.m_adjacent.length == 1)
                {
                    _loc_2 = m_item.getCachedImage(this.getOverlayImage2());
                    this.handleStretchTee(_loc_1, _loc_3, _loc_2);
                    return;
                }
                this.handleTeeAdjustment(_loc_1, _loc_3, 1);
            }
            else if ("cross" in this.m_currentRule)
            {
                _loc_3 = this.m_currentRule.cross.get(0);
                this.handleTeeAdjustment(_loc_1, _loc_3, 1);
                _loc_3 = this.m_currentRule.cross.get(1);
                _loc_1 = m_item.getCachedImage(this.getOverlayImage2());
                if (_loc_1 != null)
                {
                    this.handleTeeAdjustment(_loc_1, _loc_3, 2);
                }
            }
            else if ("corner" in this.m_currentRule)
            {
                _loc_2 = m_item.getCachedImage(this.getOverlayImage2());
                this.handleCornerAdjustment(_loc_1, _loc_2);
            }
            return;
        }//end

        public void  clearAdjacent ()
        {
            if (this.m_adjacent != null && m_dbgShape)
            {
                m_dbgShape.graphics.clear();
            }
            this.m_adjacent = null;
            this.m_adjacentPartial = null;
            this.m_adjacentTight = null;
            this.m_adjacentStretch = null;
            this.m_adjacentRoads = null;
            this.m_currentRule = null;
            this.m_fitOffset.x = 0;
            this.m_fitOffset.y = 0;
            this.m_fitStretch = false;
            this.m_fitShrink = false;
            this.m_isTightSection = false;
            return;
        }//end

        protected void  initalUpdateAdjacentCalculations ()
        {
            int _loc_7 =0;
            Road _loc_9 =null ;
            boolean _loc_10 =false ;
            this.m_adjacent = new Array();
            this.m_adjacentPartial = new Array();
            this.m_adjacentTight = new Array();
            this.m_adjacentRoads = new Array();
            this.m_adjacentStretch = new Array();
            this.m_canAdjust = new Array();
            if (this.m_invalidDragPos)
            {
                return;
            }
            _loc_1 = getPosition().x;
            _loc_2 = getPosition().y;
            Array _loc_3 =.get(.get(_loc_1 -SIZE_X ,_loc_2) ,.get(_loc_1 ,_loc_2 -SIZE_Y) ,.get(_loc_1 +SIZE_X ,_loc_2) ,.get(_loc_1 ,_loc_2 +SIZE_Y)) ;
            Array _loc_4 =.get(.get(_loc_1 -SIZE_X -1,_loc_2) ,.get(_loc_1 ,_loc_2 -SIZE_Y -1) ,.get(_loc_1 +SIZE_X +1,_loc_2) ,.get(_loc_1 ,_loc_2 +SIZE_Y +1)) ;
            Array _loc_5 =.get(.get(_loc_1 -(SIZE_X -1),_loc_2) ,.get(_loc_1 ,_loc_2 -(SIZE_Y -1)) ,.get(_loc_1 +(SIZE_X -1),_loc_2) ,.get(_loc_1 ,_loc_2 +(SIZE_Y -1))) ;
            Array _loc_6 =new Array();
            int _loc_8 =0;
            while (_loc_8 < _loc_3.length())
            {

                _loc_9 = this.isRoadTile(_loc_3.get(_loc_8).get(0), _loc_3.get(_loc_8).get(1), _loc_8);
                if (_loc_9 != null)
                {
                    if (_loc_8 == UP && this.m_size.y < SIZE_Y || _loc_8 == RIGHT && this.m_size.x < SIZE_X)
                    {
                    }
                    this.m_adjacent.push(_loc_8);
                    this.m_adjacentRoads.put(_loc_8,  _loc_9);
                }
                else
                {
                    _loc_9 = this.isPartialMatchRoadTile(_loc_1, _loc_2, _loc_3.get(_loc_8).get(0), _loc_3.get(_loc_8).get(1), _loc_8);
                    if (_loc_9)
                    {
                        if (m_size.x > SIZE_X && (_loc_8 == DOWN || _loc_8 == UP))
                        {




                        }
                        if (m_size.y > SIZE_Y && (_loc_8 == LEFT || _loc_8 == RIGHT))
                        {




                        }
                        this.m_adjacentPartial.push(_loc_8);
                        this.m_adjacentRoads.put(_loc_8,  _loc_9);
                    }
                    else if (Road.m_allowTighten)
                    {
                        _loc_9 = this.isTightMatchRoadTile(_loc_1, _loc_2, _loc_5.get(_loc_8).get(0), _loc_5.get(_loc_8).get(1), _loc_8);
                        if (_loc_9)
                        {
                            if (_loc_8 == DOWN && _loc_9.m_size.y < SIZE_Y || _loc_8 == LEFT && _loc_9.m_size.x < SIZE_X)
                            {
                                if (_loc_8 == DOWN && _loc_9.m_position.x == m_position.x && _loc_9.m_size.x == m_size.x || _loc_8 == LEFT && _loc_9.m_position.y == m_position.y && _loc_9.m_size.y == m_size.y)
                                {
                                    this.m_adjacent.push(_loc_8);
                                }
                                else
                                {
                                    this.m_adjacentPartial.push(_loc_8);
                                }
                                this.m_adjacentRoads.put(_loc_8,  _loc_9);
                            }
                            else
                            {
                                this.m_adjacentTight.push(_loc_8);
                                this.m_adjacentRoads.put(_loc_8,  _loc_9);
                            }
                        }
                        else
                        {
                            _loc_10 = false;
                            if ((_loc_7 == LEFT || _loc_7 == RIGHT) && m_size.y != SIZE_Y)
                            {
                                _loc_10 = true;
                            }
                            if ((_loc_7 == UP || _loc_7 == DOWN) && m_size.x != SIZE_X)
                            {
                                _loc_10 = false;
                            }
                            _loc_9 = this.isStretchMatchRoadTile(_loc_1, _loc_2, _loc_4.get(_loc_8).get(0), _loc_4.get(_loc_8).get(1), _loc_8);
                            if (_loc_9)
                            {
                                if (_loc_8 == DOWN && _loc_9.m_size.y > SIZE_Y || _loc_8 == LEFT && _loc_9.m_size.x > SIZE_X)
                                {
                                    if (_loc_8 == DOWN && _loc_9.m_position.x == m_position.x || _loc_8 == LEFT && _loc_9.m_position.y == m_position.y)
                                    {
                                        if (_loc_10)
                                        {
                                            this.m_adjacentPartial.push(_loc_8);
                                        }
                                        else
                                        {
                                            this.m_adjacent.push(_loc_8);
                                        }
                                    }
                                    else
                                    {
                                        this.m_adjacentPartial.push(_loc_8);
                                    }
                                    this.m_adjacentRoads.put(_loc_8,  _loc_9);
                                }
                                else if (!_loc_10)
                                {
                                    this.m_adjacentStretch.push(_loc_8);
                                    this.m_adjacentRoads.put(_loc_8,  _loc_9);
                                }
                            }
                        }
                    }
                }
                _loc_8++;
            }
            return;
        }//end

        protected void  updateAdjacent ()
        {
            int _loc_4 =0;
            Road _loc_5 =null ;
            Road _loc_6 =null ;
            Vector3 _loc_7 =null ;
            Vector3 _loc_8 =null ;
            int _loc_9 =0;
            int _loc_10 =0;
            int _loc_11 =0;
            int _loc_12 =0;
            if (this.m_adjacentRoads != null)
            {
                return;
            }
            this.initalUpdateAdjacentCalculations();
            if (this.m_adjacentTight.lenght > 1)
            {
                for(int i0 = 0; i0 < this.m_adjacentTight.size(); i0++)
                {
                	_loc_4 = this.m_adjacentTight.get(i0);

                    this.m_adjacentRoads.put(_loc_4,  null);
                }
                this.m_adjacentTight = new Array();
            }
            this.m_isLeftRight = false;
            this.m_isUpDown = false;
            this.m_isCorner = false;
            _loc_1 = this.m_adjacent.concat(this.m_adjacentPartial);
            if (this.m_adjacent.length == 2)
            {
                if (this.m_adjacent.indexOf(LEFT) >= 0 && this.m_adjacent.indexOf(RIGHT) >= 0)
                {
                    this.m_isLeftRight = true;
                }
                if (this.m_adjacent.indexOf(UP) >= 0 && this.m_adjacent.indexOf(DOWN) >= 0)
                {
                    this.m_isUpDown = true;
                }
            }
            _loc_2 = this.m_adjacent.concat(this.m_adjacentTight.concat(this.m_adjacentPartial.concat(this.m_adjacentStretch)));
            this.m_isTightSection = false;
            if (_loc_2.length == 2)
            {
                if (_loc_2.indexOf(LEFT) >= 0 && _loc_2.indexOf(RIGHT) >= 0)
                {
                    this.m_isLeftRight = true;
                }
                if (_loc_2.indexOf(UP) >= 0 && _loc_2.indexOf(DOWN) >= 0)
                {
                    this.m_isUpDown = true;
                }
            }
            if (this.m_adjacentTight.length && (this.m_isLeftRight || this.m_isUpDown))
            {
                this.m_isTightSection = true;
                this.m_tightDirection = this.m_adjacentTight.get(0);
            }
            boolean _loc_3 =false ;
            if (this.m_adjacent.length + this.m_adjacentTight.length == 1 && this.m_adjacentPartial.length == 2)
            {
                _loc_5 = this.m_adjacentRoads.get(this.m_adjacentPartial.get(0));
                _loc_6 = this.m_adjacentRoads.get(this.m_adjacentPartial.get(1));
                _loc_7 = _loc_5.getPosition();
                _loc_8 = _loc_6.getPosition();
                if (_loc_7.x != _loc_8.x && _loc_7.y != _loc_8.y)
                {
                    _loc_3 = true;
                }
            }
            if (_loc_3)
            {
                _loc_9 = 0;
                _loc_10 = 0;
                _loc_11 = this.m_adjacentPartial.get(0);
                _loc_12 = this.m_adjacentPartial.get(1);
                _loc_5 = this.m_adjacentRoads.get(_loc_11);
                _loc_6 = this.m_adjacentRoads.get(_loc_12);
                _loc_7 = _loc_5.getPosition();
                _loc_8 = _loc_6.getPosition();
                if (_loc_11 == UP && _loc_12 == DOWN || _loc_12 == UP && _loc_11 == DOWN)
                {
                    if (_loc_7.x == m_position.x)
                    {
                        _loc_9 = _loc_9 + 2;
                    }
                    if (_loc_7.x + _loc_5.m_size.x == m_position.x + m_size.x)
                    {
                        _loc_9++;
                    }
                    if (_loc_8.x == m_position.x)
                    {
                        _loc_10 = _loc_10 + 2;
                    }
                    if (_loc_8.x + _loc_6.m_size.x == m_position.x + m_size.x)
                    {
                        _loc_10++;
                    }
                }
                else if (_loc_11 == LEFT && _loc_12 == RIGHT || _loc_12 == LEFT && _loc_11 == RIGHT)
                {
                    if (_loc_7.y == m_position.y)
                    {
                        _loc_9 = _loc_9 + 2;
                    }
                    if (_loc_7.y + _loc_5.m_size.y == m_position.y + m_size.y)
                    {
                        _loc_9++;
                    }
                    if (_loc_8.y == m_position.y)
                    {
                        _loc_10 = _loc_10 + 2;
                    }
                    if (_loc_8.y + _loc_6.m_size.y == m_position.y + m_size.y)
                    {
                        _loc_10++;
                    }
                }
                if (_loc_10 < _loc_9)
                {
                    this.m_adjacentRoads.put(_loc_12,  null);
                    this.m_adjacentPartial.splice(1, 1);
                }
                if (_loc_9 < _loc_10)
                {
                    this.m_adjacentRoads.put(_loc_11,  null);
                    this.m_adjacentPartial.splice(0, 1);
                }
            }
            return;
        }//end

        protected Array  getRules ()
        {
            if (m_size.x < SIZE_X)
            {
                return m_roadRules2x3;
            }
            if (m_size.y < SIZE_Y)
            {
                return m_roadRules3x2;
            }
            if (m_size.x > SIZE_X)
            {
                return m_roadRules4x3;
            }
            if (m_size.y > SIZE_Y)
            {
                return m_roadRules3x4;
            }
            if (this.m_isStretchSection)
            {
                if (this.m_stretchDirection == UP || this.m_stretchDirection == DOWN)
                {
                    return m_roadRules3x4;
                }
                return m_roadRules4x3;
            }
            return m_roadRules;
        }//end

        protected Object  matchPartialRule (Array param1 )
        {
            Object _loc_5 =null ;
            boolean _loc_6 =false ;
            int _loc_7 =0;
            int _loc_8 =0;
            int _loc_9 =0;
            Road _loc_10 =null ;
            int _loc_11 =0;
            int _loc_12 =0;
            boolean _loc_2 =false ;
            _loc_3 = this.getRules();
            int _loc_4 =0;
            while (_loc_4 < _loc_3.length())
            {

                _loc_5 = _loc_3.get(_loc_4);
                _loc_6 = "partial" in _loc_5;
                if (!_loc_6)
                {
                }
                else
                {
                    if (param1.length == 3)
                    {




                    }
                    if (_loc_5.match.length == param1.length())
                    {
                        _loc_2 = true;
                        if (!Road.m_allowStretchCorners && _loc_5.match.length == 2)
                        {
                        }
                        else
                        {
                            _loc_7 = 0;
                            _loc_8 = 0;
                            while (_loc_8 < _loc_5.match.length())
                            {

                                _loc_9 = param1.get(_loc_8);
                                if (_loc_5.match.get(_loc_8) != _loc_9)
                                {
                                    _loc_2 = false;
                                    break;
                                }
                                if (this.m_adjacent.indexOf(_loc_9) < 0)
                                {
                                    if (_loc_5.partial.indexOf(_loc_9) < 0)
                                    {
                                        _loc_2 = false;
                                        break;
                                    }
                                    _loc_7 = _loc_9;
                                }
                                _loc_8++;
                            }
                            if (_loc_2 && !this.m_isCorner)
                            {
                                _loc_10 =(Road) this.m_adjacentRoads.get(_loc_7);
                                if (_loc_10 != null && !_loc_10.m_isCorner)
                                {
                                    _loc_2 = false;
                                }
                            }
                            if (_loc_2)
                            {
                                _loc_11 = CAN_ADJUST;
                                if (!this.m_isCorner)
                                {
                                    _loc_11 = 0;
                                }
                                if (param1.length > 2)
                                {
                                    _loc_11 = SHOULD_ADJUST;
                                }
                                for(int i0 = 0; i0 < this.m_adjacentPartial.size(); i0++)
                                {
                                		_loc_12 = this.m_adjacentPartial.get(i0);

                                    this.m_canAdjust.put(_loc_12,  _loc_11);
                                }
                                return _loc_5;
                            }
                        }
                    }
                }
                _loc_4++;
            }
            return null;
        }//end

        protected Object  matchFullRule (Array param1 )
        {
            Object _loc_4 =null ;
            boolean _loc_5 =false ;
            boolean _loc_6 =false ;
            int _loc_7 =0;
            _loc_2 = this.getRules();
            int _loc_3 =0;
            while (_loc_3 < _loc_2.length())
            {

                _loc_4 = _loc_2.get(_loc_3);
                _loc_5 = "partial" in _loc_4;
                if (_loc_5)
                {
                }
                else if (_loc_4.match.length == param1.length())
                {
                    _loc_6 = true;
                    _loc_7 = 0;
                    while (_loc_7 < _loc_4.match.length())
                    {

                        if (_loc_4.match.get(_loc_7) != param1.get(_loc_7))
                        {
                            _loc_6 = false;
                            break;
                        }
                        _loc_7++;
                    }
                    if (_loc_6)
                    {
                        return _loc_4;
                    }
                }
                _loc_3++;
            }
            return null;
        }//end

        protected void  convertPartialsToAdjacent ()
        {
            int _loc_2 =0;
            Road _loc_3 =null ;
            boolean _loc_4 =false ;
            int _loc_1 =0;
            while (_loc_1 < this.m_adjacentPartial.length())
            {

                _loc_2 = this.m_adjacentPartial.get(_loc_1);
                _loc_3 = this.m_adjacentRoads.get(_loc_2);
                _loc_4 = false;
                switch(_loc_2)
                {
                    case UP:
                    case DOWN:
                    {
                        if (m_size.x != SIZE_X)
                        {
                            break;
                        }
                        _loc_4 = _loc_3.m_isLeftRight;
                        break;
                    }
                    case LEFT:
                    case RIGHT:
                    {
                        if (m_size.y != SIZE_Y)
                        {
                            break;
                        }
                        _loc_4 = _loc_3.m_isUpDown;
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                if (_loc_3.m_isCorner && _loc_3.m_adjacentPartial.length == 1)
                {
                    _loc_4 = true;
                }
                if (_loc_3.m_isCorner && _loc_3.m_currentRule && "tee" in _loc_3.m_currentRule)
                {
                    _loc_4 = true;
                }
                if (_loc_4)
                {
                    this.m_adjacent.push(_loc_2);
                    this.m_adjacent = this.m_adjacent.sort();
                    this.m_adjacentPartial.splice(_loc_1, 1);
                    _loc_1 = _loc_1 - 1;
                }
                _loc_1++;
            }
            return;
        }//end

        protected boolean  permitTight (int param1 ,Road param2 )
        {
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            boolean _loc_5 =false ;
            boolean _loc_6 =false ;
            if (param1 == UP && m_size.y < SIZE_Y)
            {
                return true;
            }
            if (param1 == RIGHT && m_size.x < SIZE_X)
            {
                return true;
            }
            if (this.m_adjacentTight.length != 1)
            {
            }
            return true;
        }//end

        protected void  convertTightToAdjacent ()
        {
            int _loc_2 =0;
            Road _loc_3 =null ;
            int _loc_4 =0;
            boolean _loc_5 =false ;
            boolean _loc_6 =false ;
            this.m_isTightSection = false;
            int _loc_1 =0;
            for(int i0 = 0; i0 < this.m_adjacentTight.size(); i0++)
            {
            	_loc_2 = this.m_adjacentTight.get(i0);

                _loc_3 = this.m_adjacentRoads.get(_loc_2);
                _loc_4 = Road.m_flipDirection.get(_loc_2);
                _loc_5 = this.m_adjacentTight.indexOf(_loc_4) >= 0;
                _loc_5 = false;
                if (!_loc_5 && (this.permitTight(_loc_2, _loc_3) || _loc_3.permitTight(_loc_4, this)))
                {
                    _loc_6 = false;
                    switch(_loc_2)
                    {
                        case UP:
                        case DOWN:
                        {
                            _loc_6 = _loc_3.getPosition().x != getPosition().x;
                            break;
                        }
                        case LEFT:
                        case RIGHT:
                        {
                            _loc_6 = _loc_3.getPosition().y != getPosition().y;
                            break;
                        }
                        default:
                        {
                            break;
                        }
                    }
                    if (_loc_6)
                    {
                        this.m_adjacentPartial.push(_loc_2);
                        this.m_adjacentPartial = this.m_adjacentPartial.sort();
                    }
                    else
                    {
                        this.m_adjacent.push(_loc_2);
                        this.m_adjacent = this.m_adjacent.sort();
                    }
                    _loc_1++;
                    this.m_tightDirection = _loc_2;
                    continue;
                }
                this.m_adjacentRoads.put(_loc_2,  null);
            }
            this.m_isTightSection = _loc_1 == 1;
            if (this.m_adjacentPartial.length + this.m_adjacent.length > 2)
            {
                this.m_isCorner = true;
            }
            return;
        }//end

        protected boolean  permitStretch (int param1 ,Road param2 )
        {
            boolean _loc_3 =false ;
            boolean _loc_4 =false ;
            boolean _loc_5 =false ;
            boolean _loc_6 =false ;
            if (param1 == UP && m_size.y > SIZE_Y)
            {
                return true;
            }
            if (param1 == RIGHT && m_size.x > SIZE_X)
            {
                return true;
            }
            return false;
        }//end

        protected void  convertStretchToAdjacent ()
        {
            int _loc_5 =0;
            Road _loc_6 =null ;
            int _loc_7 =0;
            boolean _loc_8 =false ;
            this.m_isStretchSection = false;
            int _loc_1 =0;
            int _loc_2 =-1;
            int _loc_3 =0;
            int _loc_4 =-1;
            for(int i0 = 0; i0 < this.m_adjacentStretch.size(); i0++)
            {
            	_loc_5 = this.m_adjacentStretch.get(i0);

                _loc_6 = this.m_adjacentRoads.get(_loc_5);
                _loc_7 = Road.m_flipDirection.get(_loc_5);
                if (this.permitStretch(_loc_5, _loc_6) || _loc_6.permitStretch(_loc_7, this))
                {
                    _loc_1++;
                    this.m_stretchDirection = _loc_5;
                    if (this.m_adjacent.indexOf(_loc_7) >= 0)
                    {
                        _loc_2 = _loc_5;
                        _loc_3++;
                    }
                    continue;
                }
                this.m_adjacentRoads.put(_loc_5,  null);
            }
            this.m_isStretchSection = _loc_1 == 1;
            if (_loc_1 == 2 && _loc_3 == 1)
            {
                this.m_isStretchSection = true;
                this.m_stretchDirection = _loc_2;
                for(int i0 = 0; i0 < this.m_adjacentStretch.size(); i0++)
                {
                		_loc_5 = this.m_adjacentStretch.get(i0);

                    if (_loc_5 != this.m_stretchDirection)
                    {
                        this.m_adjacentRoads.put(_loc_5,  null);
                    }
                }
            }
            if (this.m_isStretchSection)
            {
                _loc_8 = false;
                _loc_5 = this.m_stretchDirection;
                _loc_6 = this.m_adjacentRoads.get(_loc_5);
                switch(_loc_5)
                {
                    case UP:
                    case DOWN:
                    {
                        _loc_8 = _loc_6.getPosition().x != getPosition().x;
                        break;
                    }
                    case LEFT:
                    case RIGHT:
                    {
                        _loc_8 = _loc_6.getPosition().y != getPosition().y;
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
                if (_loc_8)
                {
                    this.m_adjacentPartial.push(_loc_5);
                    this.m_adjacentPartial = this.m_adjacentPartial.sort();
                }
                else
                {
                    this.m_adjacent.push(_loc_5);
                    this.m_adjacent = this.m_adjacent.sort();
                }
            }
            if (this.m_adjacentPartial.length + this.m_adjacent.length > 2)
            {
                this.m_isCorner = true;
            }
            return;
        }//end

        protected void  discardInvalidLinks ()
        {
            int _loc_2 =0;
            Road _loc_3 =null ;
            Road _loc_4 =null ;
            int _loc_1 =0;
            while (_loc_1 < this.m_adjacentPartial.length())
            {

                _loc_2 = this.m_adjacentPartial.get(_loc_1);
                _loc_3 = this.m_adjacentRoads.get(_loc_2);
                if (_loc_3 == null)
                {
                }
                else
                {
                    _loc_4 = _loc_3.m_adjacentRoads.get(Road.m_flipDirection.get(_loc_2));
                    if (_loc_4 != this)
                    {
                        this.m_adjacentRoads.put(_loc_2,  null);
                        this.m_adjacentPartial.splice(_loc_1, 1);
                        _loc_1 = _loc_1 - 1;
                    }
                }
                _loc_1++;
            }
            _loc_1 = 0;
            while (_loc_1 < this.m_adjacent.length())
            {

                _loc_2 = this.m_adjacent.get(_loc_1);
                _loc_3 = this.m_adjacentRoads.get(_loc_2);
                if (_loc_3 == null)
                {
                }
                else
                {
                    _loc_4 = _loc_3.m_adjacentRoads.get(Road.m_flipDirection.get(_loc_2));
                    if (_loc_4 != this)
                    {
                        this.m_adjacentRoads.put(_loc_2,  null);
                        this.m_adjacent.splice(_loc_1, 1);
                        _loc_1 = _loc_1 - 1;
                    }
                }
                _loc_1++;
            }
            return;
        }//end

        public void  calculateRoadConnectivity1 ()
        {
            Road _loc_1 =null ;
            Array _loc_2 =null ;
            Array _loc_3 =null ;
            int _loc_4 =0;
            Array _loc_5 =null ;
            this.updateAdjacent();
            for(int i0 = 0; i0 < this.m_adjacentRoads.size(); i0++)
            {
            	_loc_1 = this.m_adjacentRoads.get(i0);

                if (_loc_1 != null && _loc_1.m_adjacent == null)
                {
                    _loc_1.updateAdjacent();
                }
            }
            _loc_2 = this.m_adjacent;
            this.convertTightToAdjacent();
            this.convertStretchToAdjacent();
            _loc_3 = this.m_adjacent.concat(this.m_adjacentPartial);
            if (_loc_3.length == 2)
            {
                this.m_isCorner = true;
                if (_loc_3.indexOf(LEFT) >= 0 && _loc_3.indexOf(RIGHT) >= 0)
                {
                    this.m_isCorner = false;
                }
                if (_loc_3.indexOf(UP) >= 0 && _loc_3.indexOf(DOWN) >= 0)
                {
                    this.m_isCorner = false;
                }
            }
            if (!m_allowStretchCorners && this.m_isCorner && this.m_adjacent.length < 2)
            {
                this.m_isCorner = false;
                for(int i0 = 0; i0 < this.m_adjacentPartial.size(); i0++)
                {
                		_loc_4 = this.m_adjacentPartial.get(i0);

                    this.m_adjacentRoads.put(_loc_4,  null);
                }
                this.m_adjacentPartial = new Array();
            }
            if (_loc_3.length == 3 && this.m_adjacent.length > 1)
            {
                this.m_isCorner = true;
            }
            if (_loc_3.length == 4 && this.m_adjacent.length > 1)
            {
                this.m_isCorner = true;
            }
            this.m_currentRule = null;
            if (this.m_adjacentPartial.length > 0)
            {
                _loc_5 = this.m_adjacent.concat(this.m_adjacentPartial);
                _loc_5.sort();
                this.m_currentRule = this.matchPartialRule(_loc_5);
            }
            if (this.m_currentRule && this.m_adjacentRoads.get(UP) != null && this.m_adjacentRoads.get(DOWN) != null)
            {
                this.m_isUpDown = true;
            }
            if (this.m_currentRule && this.m_adjacentRoads.get(LEFT) != null && this.m_adjacentRoads.get(RIGHT) != null)
            {
                this.m_isLeftRight = true;
            }
            return;
        }//end

        public void  trimPartials ()
        {
            int _loc_2 =0;
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            Road _loc_6 =null ;
            this.convertPartialsToAdjacent();
            _loc_1 = this.m_adjacent.concat(this.m_adjacentPartial);
            _loc_1 = _loc_1.sort();
            this.m_currentRule = this.matchPartialRule(_loc_1);
            while (this.m_currentRule == null && this.m_adjacentPartial.length > 0)
            {

                _loc_2 = 10;
                _loc_3 = -1;
                _loc_4 = 0;
                while (_loc_4 < this.m_adjacentPartial.length())
                {

                    _loc_5 = 0;
                    _loc_6 = this.m_adjacentRoads.get(this.m_adjacentPartial.get(_loc_4));
                    if (_loc_6.m_position.x == m_position.x)
                    {
                        _loc_5++;
                    }
                    if (_loc_6.m_position.y == m_position.y)
                    {
                        _loc_5++;
                    }
                    if (_loc_6.m_position.x + _loc_6.m_size.x == m_position.x + m_size.x)
                    {
                        _loc_5++;
                    }
                    if (_loc_6.m_position.y + _loc_6.m_size.y == m_position.y + m_size.y)
                    {
                        _loc_5++;
                    }
                    if (_loc_5 < _loc_2)
                    {
                        _loc_2 = _loc_5;
                        _loc_3 = _loc_4;
                    }
                    _loc_4++;
                }
                this.m_adjacentRoads.get(this.m_adjacentPartial.put(_loc_3),  null);
                this.m_adjacentPartial.splice(_loc_3, 1);
                _loc_1 = this.m_adjacent.concat(this.m_adjacentPartial);
                _loc_1.sort();
                this.m_currentRule = this.matchPartialRule(_loc_1);
            }
            return;
        }//end

        public void  calculateRoadConnectivity2 ()
        {
            Array _loc_1 =null ;
            Road _loc_2 =null ;
            int _loc_3 =0;
            if (this.m_isStretchSection)
            {
                _loc_2 = this.m_adjacentRoads.get(this.m_stretchDirection);
                if (_loc_2 == null || !this.shouldStretch(_loc_2, this.m_stretchDirection))
                {
                    this.m_isStretchSection = false;
                }
                if (this.m_adjacentRoads.get(this.m_stretchDirection) == null)
                {
                    this.m_isStretchSection = false;
                }
            }
            this.m_currentRule = null;
            if (this.m_adjacentPartial.length > 0)
            {
                _loc_1 = this.m_adjacent.concat(this.m_adjacentPartial);
                _loc_1.sort();
                this.m_currentRule = this.matchPartialRule(_loc_1);
                if (this.m_currentRule == null)
                {
                    this.trimPartials();
                }
            }
            if (this.m_isStretchSection && this.m_adjacentRoads.get(this.m_stretchDirection) == null)
            {
                this.m_isStretchSection = false;
            }
            if (this.m_currentRule != null)
            {
                if ("corner" in this.m_currentRule)
                {
                    this.m_currentRule = this.checkOverlapCorner();
                }
            }
            if (this.m_currentRule == null)
            {
                for(int i0 = 0; i0 < this.m_adjacentPartial.size(); i0++)
                {
                	_loc_3 = this.m_adjacentPartial.get(i0);

                    this.m_adjacentRoads.put(_loc_3,  null);
                }
                this.m_adjacentPartial = new Array();
                this.m_currentRule = this.matchFullRule(this.m_adjacent);
                if (this.m_currentRule == null && this.m_isStretchSection && m_size.x == SIZE_X && m_size.y == SIZE_Y)
                {
                    this.m_adjacentRoads.put(this.m_stretchDirection,  null);
                    this.m_adjacent.splice(this.m_adjacent.indexOf(this.m_stretchDirection), 1);
                    this.m_isStretchSection = false;
                    this.m_currentRule = this.matchFullRule(this.m_adjacent);
                }
            }
            if (this.m_currentRule == null)
            {
                this.m_adjacent = new Array();
                this.m_adjacentRoads = new Array();
                this.m_adjacentPartial = new Array();
            }
            return;
        }//end

        public void  calculateRoadConnectivity3 ()
        {
            String _loc_1 =null ;
            int _loc_3 =0;
            Road _loc_4 =null ;
            int _loc_5 =0;
            boolean _loc_6 =false ;
            if (this.m_adjacent == null)
            {
                return;
            }
            this.discardInvalidLinks();
            if (this.m_isTightSection)
            {
                _loc_4 = this.m_adjacentRoads.get(this.m_tightDirection);
                if (_loc_4 == null || !this.shouldTighten(_loc_4, this.m_tightDirection))
                {
                    this.m_isTightSection = false;
                }
                else if (this.m_isRoadBeingDragged)
                {
                    if (this.m_adjacentPartial.length + this.m_adjacent.length == 1)
                    {
                        this.m_isTightSection = false;
                        this.m_adjacentPartial = new Array();
                        this.m_adjacent = new Array();
                        this.m_adjacentRoads = new Array();
                    }
                }
            }
            if (this.m_isStretchSection)
            {
                _loc_4 = this.m_adjacentRoads.get(this.m_stretchDirection);
                if (_loc_4 == null || !this.shouldStretch(_loc_4, this.m_stretchDirection))
                {
                    this.m_isStretchSection = false;
                }
            }
            int _loc_2 =0;
            for(int i0 = 0; i0 < this.m_adjacentRoads.size(); i0++)
            {
            	_loc_4 = this.m_adjacentRoads.get(i0);

                if (_loc_4 != null)
                {
                    _loc_2++;
                }
            }
            if (_loc_2 < 2)
            {
                this.m_isCorner = false;
            }
            for(int i0 = 0; i0 < this.m_adjacentPartial.size(); i0++)
            {
            	_loc_3 = this.m_adjacentPartial.get(i0);

                _loc_5 = Road.m_flipDirection.get(_loc_3);
                _loc_4 =(Road) this.m_adjacentRoads.get(_loc_3);
                _loc_6 = _loc_4 != null && _loc_4.m_adjacentPartial.indexOf(_loc_5) >= 0;
                if (_loc_4 && !_loc_6)
                {
                    if (this.m_isCorner)
                    {
                        _loc_6 = true;
                    }
                }
                if (!_loc_6)
                {
                    this.m_adjacentPartial.splice(this.m_adjacentPartial.indexOf(_loc_3), 1);
                    this.m_adjacentRoads.put(_loc_3,  null);
                }
            }
            this.m_didConnectivity = true;
            return;
        }//end

        public void  calculateRoadImage ()
        {
            Array _loc_1 =null ;
            this.discardInvalidLinks();
            this.m_currentRule = null;
            _loc_2 = this.m_adjacent;
            if (Road.s_shrinkRoad1 == this)
            {
                if (_loc_2.indexOf(s_shrinkDir1) < 0)
                {
                    _loc_2 = _loc_2.concat(Road.s_shrinkDir1);
                    _loc_2.sort();
                }
            }
            else if (Road.s_shrinkRoad2 == this)
            {
                if (_loc_2.indexOf(s_shrinkDir2) < 0)
                {
                    _loc_2 = _loc_2.concat(s_shrinkDir2);
                    _loc_2.sort();
                }
            }
            else if (this.m_isRoadBeingDragged && s_shrinkRoad1 != null && s_shrinkRoad2 != null)
            {
                if (_loc_2.indexOf(s_shrinkDir1) < 0)
                {
                    _loc_2 = _loc_2.concat(s_shrinkDir1);
                }
                if (_loc_2.indexOf(s_shrinkDir2) < 0)
                {
                    _loc_2 = _loc_2.concat(s_shrinkDir2);
                }
                _loc_2.sort();
            }
            if (this.m_adjacentPartial.length > 0)
            {
                _loc_1 = _loc_2.concat(this.m_adjacentPartial);
                _loc_1.sort();
                this.m_currentRule = this.matchPartialRule(_loc_1);
            }
            if (this.m_currentRule != null)
            {
                if ("corner" in this.m_currentRule)
                {
                    this.m_currentRule = this.checkOverlapCorner();
                }
            }
            if (this.m_currentRule == null)
            {
                this.m_currentRule = this.matchFullRule(_loc_2);
            }
            if (this.m_currentRule == null)
            {
                this.m_adjacent = new Array();
                this.m_adjacentRoads = new Array();
                this.m_adjacentPartial = new Array();
                this.m_currentRule = this.matchFullRule(this.m_adjacent);
            }
            this.calculateOverlayOffsets();
            _loc_3 = m_depthIndex;
            this.calculateDepthIndex();
            if (_loc_3 != this.m_depthIndex)
            {
                this.conditionallyReattach(true);
            }
            return;
        }//end

        public Road  isRoadTile (int param1 ,int param2 ,int param3 )
        {
            Road _loc_8 =null ;
            Vector3 _loc_9 =null ;
            Road _loc_4 =null ;
            if (param3 == UP && m_size.y > SIZE_Y)
            {
                param2 = param2 + 1;
            }
            if (param3 == RIGHT && m_size.x > SIZE_X)
            {
                param1 = param1 + 1;
            }
            _loc_5 = Global.world.getCollisionMap().getObjectsByPosition(param1,param2);
            Vector3 _loc_6 =new Vector3(param1 ,param2 );
            int _loc_7 =0;
            while (_loc_7 < _loc_5.length())
            {

                _loc_8 =(Road) _loc_5.get(_loc_7);
                if (_loc_8)
                {
                    _loc_9 = _loc_8.getPosition();
                    if (param3 == LEFT && _loc_8.m_size.x > SIZE_X)
                    {
                        (_loc_9.x + 1);
                    }
                    if (param3 == DOWN && _loc_8.m_size.y > SIZE_Y)
                    {
                        (_loc_9.y + 1);
                    }
                    if (_loc_9.equals(_loc_6))
                    {
                        _loc_4 = _loc_8;
                        break;
                    }
                }
                _loc_7++;
            }
            if (_loc_4 != null)
            {
                if (_loc_4.m_invalidDragPos)
                {
                    return null;
                }
                if ((param3 == UP || param3 == DOWN) && (m_size.x != SIZE_X || _loc_4.m_size.x != SIZE_X))
                {
                    return null;
                }
                if ((param3 == LEFT || param3 == RIGHT) && (m_size.y != SIZE_Y || _loc_4.m_size.y != SIZE_Y))
                {
                    return null;
                }
            }
            if (_loc_4 != null && _loc_4.m_invalidDragPos)
            {
                return null;
            }
            return _loc_4;
        }//end

        public Road  isPartialMatchRoadTile (int param1 ,int param2 ,int param3 ,int param4 ,int param5 )
        {
            Road _loc_8 =null ;
            Vector3 _loc_9 =null ;
            _loc_6 = Global.world.getCollisionMap().getObjectsByPosition(param3,param4);
            _loc_6 = Global.world.getCollisionMap().getObjectsByPosition(param3, param4).concat(Global.world.getCollisionMap().getObjectsByPosition((param3 + 1), param4));
            _loc_6 = _loc_6.concat(Global.world.getCollisionMap().getObjectsByPosition(param3, (param4 + 1)));
            int _loc_7 =0;
            while (_loc_7 < _loc_6.length())
            {

                _loc_8 =(Road) _loc_6.get(_loc_7);
                if (_loc_8)
                {
                    if (_loc_8.m_invalidDragPos)
                    {
                    }
                    else if (param5 == DOWN && _loc_8.m_size.y < SIZE_Y)
                    {
                    }
                    else if (param5 == LEFT && _loc_8.m_size.x < SIZE_X)
                    {
                    }
                    else if (param5 == UP && m_size.y < SIZE_Y)
                    {
                    }
                    else if (param5 == RIGHT && m_size.x < SIZE_X)
                    {
                    }
                    else
                    {
                        _loc_9 = _loc_8.getPosition();
                        if ((param5 == LEFT || param5 == RIGHT) && _loc_8.m_size.y < SIZE_Y && _loc_9.y < param2)
                        {
                        }
                        else if ((param5 == UP || param5 == DOWN) && _loc_8.m_size.x < SIZE_X && _loc_9.x < param1)
                        {
                        }
                        else
                        {
                            if (param1 == param3 && param4 == _loc_9.y)
                            {
                                if (Math.abs(param1 - _loc_9.x) < 1.01)
                                {
                                    return _loc_8;
                                }
                            }
                            if (param2 == param4 && param3 == _loc_9.x)
                            {
                                if (Math.abs(param2 - _loc_9.y) < 1.01)
                                {
                                    return _loc_8;
                                }
                            }
                        }
                    }
                }
                _loc_7++;
            }
            return null;
        }//end

        public Road  isTightMatchRoadTile (int param1 ,int param2 ,int param3 ,int param4 ,int param5 )
        {
            Road _loc_8 =null ;
            Vector3 _loc_9 =null ;
            _loc_6 = Global.world.getCollisionMap().getObjectsByPosition(param3,param4);
            _loc_6 = Global.world.getCollisionMap().getObjectsByPosition(param3, param4).concat(Global.world.getCollisionMap().getObjectsByPosition((param3 + 1), param4));
            _loc_6 = _loc_6.concat(Global.world.getCollisionMap().getObjectsByPosition(param3, (param4 + 1)));
            int _loc_7 =0;
            while (_loc_7 < _loc_6.length())
            {

                _loc_8 =(Road) _loc_6.get(_loc_7);
                if (_loc_8)
                {
                    if (_loc_8.m_invalidDragPos)
                    {
                    }
                    else
                    {
                        _loc_9 = _loc_8.getPosition();
                        if (param1 == param3 && param4 == _loc_9.y)
                        {
                            if (Math.abs(param1 - _loc_9.x) < 1.01)
                            {
                                return _loc_8;
                            }
                        }
                        if (param2 == param4 && param3 == _loc_9.x)
                        {
                            if (Math.abs(param2 - _loc_9.y) < 1.01)
                            {
                                return _loc_8;
                            }
                        }
                    }
                }
                _loc_7++;
            }
            return null;
        }//end

        public Road  isStretchMatchRoadTile (int param1 ,int param2 ,int param3 ,int param4 ,int param5 )
        {
            Road _loc_8 =null ;
            Vector3 _loc_9 =null ;
            _loc_6 = Global.world.getCollisionMap().getObjectsByPosition(param3,param4);
            _loc_6 = Global.world.getCollisionMap().getObjectsByPosition(param3, param4).concat(Global.world.getCollisionMap().getObjectsByPosition((param3 + 1), param4));
            _loc_6 = _loc_6.concat(Global.world.getCollisionMap().getObjectsByPosition(param3, (param4 + 1)));
            int _loc_7 =0;
            while (_loc_7 < _loc_6.length())
            {

                _loc_8 =(Road) _loc_6.get(_loc_7);
                if (_loc_8)
                {
                    if (_loc_8.m_invalidDragPos)
                    {
                    }
                    else
                    {
                        _loc_9 = _loc_8.getPosition();
                        if (param1 == param3 && param4 == _loc_9.y)
                        {
                            if (Math.abs(param1 - _loc_9.x) < 1.01)
                            {
                                return _loc_8;
                            }
                        }
                        if (param2 == param4 && param3 == _loc_9.x)
                        {
                            if (Math.abs(param2 - _loc_9.y) < 1.01)
                            {
                                return _loc_8;
                            }
                        }
                    }
                }
                _loc_7++;
            }
            return null;
        }//end

         public void  attach ()
        {
            this.m_attached = true;
            super.attach();
            return;
        }//end

         public void  detach ()
        {
            super.detach();
            this.m_attached = false;
            this.clearContent();
            this.clearAdjacent();
            return;
        }//end

         public int  getSnapX ()
        {
            return 1;
        }//end

         public int  getSnapY ()
        {
            return 1;
        }//end

         public void  onSell (GameObject param1)
        {
            super.onSell(param1);
            Global.world.citySim.roadManager.updateAllRoadTiles();
            return;
        }//end

         public void  preDisableMove (String param1 ,int param2 )
        {
            Road _loc_4 =null ;
            if (this.m_saveDragItem != null)
            {
                setItem(this.m_saveDragItem);
                this.m_saveDragItem = null;
            }
            _loc_3 =Global.world.getObjectsByClass(Road );
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_4 = _loc_3.get(i0);

                if (_loc_4 != null)
                {
                    if (_loc_4.m_saveDragItem != null)
                    {
                        _loc_4.setItem(_loc_4.m_saveDragItem);
                        _loc_4.m_saveDragItem = null;
                    }
                }
                _loc_4.m_checkForReplaceAtDrop = false;
            }
            this.m_isRoadBeingDragged = false;
            return;
        }//end

         public void  postDisableMove (Vector3 param1 )
        {
            this.m_isRoadBeingDragged = false;
            Global.world.citySim.roadManager.updateAllRoadTiles();
            return;
        }//end

        public String  baseRoadName ()
        {
            _loc_1 = m_item.name;
            if (_loc_1.search("-") > 0)
            {
                _loc_1 = _loc_1.slice(0, _loc_1.search("-"));
                return _loc_1;
            }
            return _loc_1;
        }//end

        protected void  shrinkTile (Road param1 ,int param2 )
        {
            _loc_3 = Global.gameSettings().getItemByName(this.baseRoadName()+"-2");
            if (_loc_3 != null)
            {
                param1.setItem(_loc_3);
                param1.setDirection(param2);
                param1.updateSize();
                param1.conditionallyReattach(true);
                if (param1.m_id != 0)
                {
                    GameTransactionManager.addTransaction(new TReplace(param1, param1));
                }
                setItem(_loc_3);
                setDirection(param2);
                updateSize();
                if (param2)
                {
                    m_position.y = param1.m_position.y + 2;
                }
                else
                {
                    m_position.x = param1.m_position.x + 2;
                }
                conditionallyReattach(true);
                this.m_saveDragItem = null;
                if (this.m_id != 0)
                {
                    GameTransactionManager.addTransaction(new TReplace(this, this));
                }
            }
            return;
        }//end

        protected boolean  shrinkOneTile (boolean param1 )
        {
            Object _loc_7 =null ;
            Road _loc_8 =null ;
            Road _loc_9 =null ;
            int _loc_10 =0;
            Road _loc_11 =null ;
            int _loc_12 =0;
            Vector3 _loc_13 =null ;
            Vector3 _loc_14 =null ;
            Road _loc_15 =null ;
            boolean _loc_16 =false ;
            boolean _loc_17 =false ;
            boolean _loc_18 =false ;
            boolean _loc_19 =false ;
            Vector3 _loc_20 =null ;
            _loc_2 = getPosition();
            _loc_3 = getSize();
            Array _loc_4 =new Array();
            s_shrinkRoad1 = null;
            s_shrinkRoad2 = null;
            s_shrinkDir1 = -1;
            s_shrinkDir2 = -1;
            Array _loc_5 =new Array();
            Array _loc_6 =new Array();
            Global.world.checkCollision((_loc_2.x - 1), _loc_2.y, _loc_2.x + _loc_3.x + 1, _loc_2.y + _loc_3.y, [this], _loc_5, Constants.WORLDOBJECT_ALL, this);
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_7 = _loc_5.get(i0);

                if (!(_loc_7 instanceof Road) || _loc_7 instanceof ParkingLot)
                {
                    _loc_5 = new Array();
                    break;
                }
            }
            Global.world.checkCollision(_loc_2.x, (_loc_2.y - 1), _loc_2.x + _loc_3.x, _loc_2.y + _loc_3.y + 1, [this], _loc_6, Constants.WORLDOBJECT_ALL, this);
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            	_loc_7 = _loc_6.get(i0);

                if (!(_loc_7 instanceof Road) || _loc_7 instanceof ParkingLot)
                {
                    _loc_6 = new Array();
                    break;
                }
            }
            if (_loc_5.length == 2)
            {
                _loc_4 = _loc_5;
            }
            else if (_loc_6.length == 2)
            {
                _loc_4 = _loc_6;
            }
            else if (_loc_5.length == 3)
            {
                _loc_4 = _loc_5;
            }
            else
            {
                _loc_4 = _loc_6;
            }
            if (_loc_4.length > 1)
            {
                if (_loc_4.length == 2)
                {
                    _loc_8 = _loc_4.get(0);
                    _loc_9 = _loc_4.get(1);
                    if (_loc_8.m_size.x < SIZE_X || _loc_8.m_size.y < SIZE_Y)
                    {
                        return false;
                    }
                    _loc_10 = 0;
                    _loc_11 = null;
                    for(int i0 = 0; i0 < _loc_8.adjacentRoads.size(); i0++)
                    {
                    	_loc_11 = _loc_8.adjacentRoads.get(i0);

                        if (_loc_11 != null && _loc_11 != this)
                        {
                            _loc_10++;
                        }
                    }
                    _loc_12 = 0;
                    for(int i0 = 0; i0 < _loc_9.adjacentRoads.size(); i0++)
                    {
                    	_loc_11 = _loc_9.adjacentRoads.get(i0);

                        if (_loc_11 != null && _loc_11 != this)
                        {
                            _loc_12++;
                        }
                    }
                    if (_loc_10 > 1 && _loc_12 > 1)
                    {
                        return false;
                    }
                    _loc_13 = _loc_8.getPosition();
                    _loc_14 = _loc_9.getPosition();
                    if (_loc_13.x == _loc_14.x && _loc_13.x == _loc_2.x)
                    {
                        if (_loc_13.y + _loc_8.getSize().y + 1 == _loc_14.y)
                        {
                            if (param1 !=null)
                            {
                                this.shrinkTile(_loc_8, 1);
                                return true;
                            }
                            s_shrinkRoad1 = _loc_8;
                            s_shrinkDir1 = UP;
                            s_shrinkRoad2 = _loc_9;
                            s_shrinkDir2 = DOWN;
                            return true;
                        }
                        else if (_loc_14.y + _loc_9.getSize().y + 1 == _loc_13.y)
                        {
                            if (param1 !=null)
                            {
                                this.shrinkTile(_loc_9, 1);
                                return true;
                            }
                            s_shrinkRoad1 = _loc_9;
                            s_shrinkDir1 = UP;
                            s_shrinkRoad2 = _loc_8;
                            s_shrinkDir2 = DOWN;
                            return true;
                        }
                    }
                    else if (_loc_13.y == _loc_14.y && _loc_13.y == _loc_2.y)
                    {
                        if (_loc_13.x + _loc_8.getSize().x + 1 == _loc_14.x)
                        {
                            if (param1 !=null)
                            {
                                this.shrinkTile(_loc_8, 0);
                                return true;
                            }
                            s_shrinkRoad1 = _loc_8;
                            s_shrinkDir1 = RIGHT;
                            s_shrinkRoad2 = _loc_9;
                            s_shrinkDir2 = LEFT;
                            return true;
                        }
                        else if (_loc_14.x + _loc_9.getSize().x + 1 == _loc_13.x)
                        {
                            if (param1 !=null)
                            {
                                this.shrinkTile(_loc_9, 0);
                                return true;
                            }
                            s_shrinkRoad1 = _loc_9;
                            s_shrinkDir1 = RIGHT;
                            s_shrinkRoad2 = _loc_8;
                            s_shrinkDir2 = LEFT;
                            return true;
                        }
                    }
                }
                else if (_loc_4.length == 3)
                {
                    for(int i0 = 0; i0 < _loc_4.size(); i0++)
                    {
                    	_loc_11 = _loc_4.get(i0);

                        _loc_20 = _loc_11.getPosition();
                        if (_loc_11.m_position.x == m_position.x)
                        {
                            if (_loc_8 != null)
                            {
                                return false;
                            }
                            _loc_8 = _loc_11;
                            continue;
                        }
                        if (_loc_11.m_position.y == m_position.y)
                        {
                            if (_loc_8 != null)
                            {
                                return false;
                            }
                            _loc_8 = _loc_11;
                            continue;
                        }
                        if (_loc_9 == null)
                        {
                            _loc_9 = _loc_11;
                            continue;
                        }
                        if (_loc_15 == null)
                        {
                            _loc_15 = _loc_11;
                            continue;
                        }
                        return false;
                    }
                    if (_loc_8 == null || _loc_9 == null || _loc_15 == null)
                    {
                        return false;
                    }
                    if (_loc_8.m_size.x < SIZE_X || _loc_8.m_size.y < SIZE_Y)
                    {
                        return false;
                    }
                    _loc_10 = 0;
                    for(int i0 = 0; i0 < _loc_8.adjacentRoads.size(); i0++)
                    {
                    	_loc_11 = _loc_8.adjacentRoads.get(i0);

                        if (_loc_11 != null && _loc_11 != this)
                        {
                            _loc_10++;
                        }
                    }
                    if (_loc_10 > 1)
                    {
                        return false;
                    }
                    _loc_16 = m_position.x == _loc_8.m_position.x;
                    _loc_17 = m_position.y == _loc_8.m_position.y;
                    _loc_18 = _loc_9.m_position.x == _loc_15.m_position.x;
                    _loc_19 = _loc_9.m_position.y == _loc_15.m_position.y;
                    if (_loc_16 && _loc_19)
                    {
                        if (_loc_8.m_position.y + _loc_8.m_size.y + 1 == _loc_9.m_position.y)
                        {
                            if (param1 !=null)
                            {
                                this.shrinkTile(_loc_8, 1);
                                return true;
                            }
                            s_shrinkRoad1 = _loc_8;
                            s_shrinkDir1 = UP;
                            s_shrinkRoad2 = _loc_9;
                            s_shrinkDir2 = DOWN;
                            return true;
                        }
                        else if (_loc_8.m_position.y == _loc_9.m_position.y + _loc_9.m_size.y + 1)
                        {
                            if (param1 !=null)
                            {
                                (_loc_8.m_position.y - 1);
                                this.shrinkTile(_loc_8, 1);
                                return true;
                            }
                            s_shrinkRoad1 = _loc_8;
                            s_shrinkDir1 = DOWN;
                            s_shrinkRoad2 = _loc_9;
                            s_shrinkDir2 = UP;
                            return true;
                        }
                    }
                    else if (_loc_17 && _loc_18)
                    {
                        if (_loc_8.m_position.x + _loc_8.m_size.x + 1 == _loc_9.m_position.x)
                        {
                            if (param1 !=null)
                            {
                                this.shrinkTile(_loc_8, 0);
                                return true;
                            }
                            s_shrinkRoad1 = _loc_8;
                            s_shrinkDir1 = RIGHT;
                            s_shrinkRoad2 = _loc_9;
                            s_shrinkDir2 = LEFT;
                            return true;
                        }
                        else if (_loc_8.m_position.x == _loc_9.m_position.x + _loc_9.m_size.x + 1)
                        {
                            if (param1 !=null)
                            {
                                (_loc_8.m_position.x - 1);
                                this.shrinkTile(_loc_8, 0);
                                return true;
                            }
                            s_shrinkRoad1 = _loc_8;
                            s_shrinkDir1 = LEFT;
                            s_shrinkRoad2 = _loc_9;
                            s_shrinkDir2 = RIGHT;
                            return true;
                        }
                    }
                }
            }
            return false;
        }//end

        protected void  stretchTile (int param1 )
        {
            _loc_2 = Global.gameSettings().getItemByName(this.baseRoadName()+"-4");
            if (_loc_2 != null)
            {
                this.setItem(_loc_2);
                this.setDirection(param1);
                this.updateSize();
                this.conditionallyReattach(true);
                if (m_id != 0)
                {
                    GameTransactionManager.addTransaction(new TReplace(this, this));
                }
            }
            return;
        }//end

        protected boolean  stretchOneTile (boolean param1 )
        {
            Object _loc_5 =null ;
            Road _loc_6 =null ;
            Road _loc_7 =null ;
            Vector3 _loc_8 =null ;
            Vector3 _loc_9 =null ;
            Vector3 _loc_10 =null ;
            Vector3 _loc_11 =null ;
            Road _loc_12 =null ;
            Road _loc_13 =null ;
            boolean _loc_14 =false ;
            boolean _loc_15 =false ;
            boolean _loc_16 =false ;
            boolean _loc_17 =false ;
            Vector3 _loc_18 =null ;
            _loc_2 = getPosition();
            _loc_3 = getSize();
            Array _loc_4 =new Array();
            if (Global.world.checkCollision((_loc_2.x - 1), (_loc_2.y - 1), _loc_2.x + _loc_3.x + 2, _loc_2.y + _loc_3.y + 2, [this], _loc_4, Constants.WORLDOBJECT_ALL, this))
            {
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_5 = _loc_4.get(i0);

                    if (!(_loc_5 instanceof Road))
                    {
                        return false;
                    }
                }
                if (_loc_4.length == 2)
                {
                    _loc_6 = _loc_4.get(0);
                    _loc_7 = _loc_4.get(1);
                    _loc_8 = _loc_6.getPosition();
                    _loc_9 = _loc_7.getPosition();
                    _loc_10 = _loc_6.getSize();
                    _loc_11 = _loc_7.getSize();
                    if (_loc_8.x == _loc_9.x && _loc_8.x == _loc_2.x)
                    {
                        if (_loc_8.y + _loc_6.getSize().y + 1 == _loc_9.y)
                        {
                            if (param1 !=null)
                            {
                                _loc_6.stretchTile(1);
                            }
                            return true;
                        }
                        else if (_loc_9.y + _loc_7.getSize().y + 1 == _loc_8.y)
                        {
                            if (param1 !=null)
                            {
                                _loc_7.stretchTile(1);
                            }
                            return true;
                        }
                    }
                    else if (_loc_8.y == _loc_9.y && _loc_8.y == _loc_2.y)
                    {
                        if (_loc_8.x + _loc_6.getSize().x + 1 == _loc_9.x)
                        {
                            if (param1 !=null)
                            {
                                _loc_6.stretchTile(0);
                            }
                            return true;
                        }
                        else if (_loc_9.x + _loc_7.getSize().x + 1 == _loc_8.x)
                        {
                            if (param1 !=null)
                            {
                                _loc_7.stretchTile(0);
                            }
                            return true;
                        }
                    }
                }
                else if (_loc_4.length == 3)
                {
                    for(int i0 = 0; i0 < _loc_4.size(); i0++)
                    {
                    	_loc_13 = _loc_4.get(i0);

                        _loc_18 = _loc_13.getPosition();
                        if (_loc_13.m_position.x == m_position.x)
                        {
                            if (_loc_6 != null)
                            {
                                return false;
                            }
                            _loc_6 = _loc_13;
                            continue;
                        }
                        if (_loc_13.m_position.y == m_position.y)
                        {
                            if (_loc_6 != null)
                            {
                                return false;
                            }
                            _loc_6 = _loc_13;
                            continue;
                        }
                        if (_loc_7 == null)
                        {
                            _loc_7 = _loc_13;
                            continue;
                        }
                        if (_loc_12 == null)
                        {
                            _loc_12 = _loc_13;
                            continue;
                        }
                        return false;
                    }
                    if (_loc_6 == null || _loc_7 == null || _loc_12 == null)
                    {
                        return false;
                    }
                    _loc_14 = m_position.x == _loc_6.m_position.x;
                    _loc_15 = m_position.y == _loc_6.m_position.y;
                    _loc_16 = _loc_7.m_position.x == _loc_12.m_position.x;
                    _loc_17 = _loc_7.m_position.y == _loc_12.m_position.y;
                    if (_loc_14 && _loc_17)
                    {
                        if (_loc_6.m_position.y + _loc_6.m_size.y + 1 == _loc_7.m_position.y)
                        {
                            if (param1 !=null)
                            {
                                _loc_6.stretchTile(1);
                            }
                            return true;
                        }
                        else if (_loc_6.m_position.y == _loc_7.m_position.y + _loc_7.m_size.y + 1)
                        {
                            if (param1 !=null)
                            {
                                (_loc_6.m_position.y - 1);
                                _loc_6.stretchTile(1);
                            }
                            return true;
                        }
                    }
                    else if (_loc_15 && _loc_16)
                    {
                        if (_loc_6.m_position.x + _loc_6.m_size.x + 1 == _loc_7.m_position.x)
                        {
                            if (param1 !=null)
                            {
                                _loc_6.stretchTile(0);
                            }
                            return true;
                        }
                        else if (_loc_6.m_position.x == _loc_7.m_position.x + _loc_7.m_size.x + 1)
                        {
                            if (param1 !=null)
                            {
                                (_loc_6.m_position.x - 1);
                                _loc_6.stretchTile(0);
                            }
                            return true;
                        }
                    }
                }
            }
            return false;
        }//end

        public boolean  isPositionValid ()
        {
            if (this.shrinkOneTile(false))
            {
                return true;
            }
            _loc_1 = getPosition();
            _loc_2 = getSize();
            if (this.m_isTightSection)
            {
                switch(this.m_tightDirection)
                {
                    case LEFT:
                    {
                        (_loc_1.x + 1);
                        (_loc_2.x - 1);
                        break;
                    }
                    case RIGHT:
                    {
                        (_loc_2.x - 1);
                        break;
                    }
                    case DOWN:
                    {
                        (_loc_1.y + 1);
                        (_loc_2.y - 1);
                        break;
                    }
                    case UP:
                    {
                        (_loc_2.y - 1);
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            if (Global.world.checkCollision(_loc_1.x, _loc_1.y, _loc_1.x + _loc_2.x, _loc_1.y + _loc_2.y, [this], null, Constants.WORLDOBJECT_ALL, this))
            {
                return false;
            }
            return true;
        }//end

         public void  onObjectDrag (Vector3 param1 )
        {
            Array _loc_2 =null ;
            Road _loc_3 =null ;
            this.m_isRoadBeingDragged = true;
            if (this.m_saveDragItem == null && this.m_adjacentRoads != null)
            {
                _loc_2 = this.m_adjacentRoads.concat(this);
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                	_loc_3 = _loc_2.get(i0);

                    canSwitchToBaseItem(_loc_3);
                }
            }
            if (this.m_saveDragItem == null)
            {
                this.m_saveDragItem = this.m_item;
            }
            super.onObjectDrag(param1);
            s_nearX = m_position.x;
            s_nearY = m_position.y;
            _loc_2 = Global.world.getObjectsByPredicate(nearbyRoads);
            Global.world.citySim.roadManager.updateRoadTiles(_loc_2);
            if (!this.isPositionValid())
            {
                this.m_invalidDragPos = true;
                Global.world.citySim.roadManager.updateRoadTiles(_loc_2);
                this.m_invalidDragPos = false;
            }
            conditionallyReattach(true);
            return;
        }//end

        protected Road  checkForNewCorner (Vector2 param1 )
        {
            boolean _loc_2 =false ;
            Road _loc_3 =null ;
            this.clearContent();
            if (this.m_adjacentPartial.length == 1)
            {
                _loc_2 = false;
                if (this.m_fitShrink)
                {
                    if (this.m_fitOffset.x == 1)
                    {
                        (param1.x + 1);
                    }
                    else if (this.m_fitOffset.x == -1)
                    {
                    }
                    else if (this.m_fitOffset.y == 1)
                    {
                        (param1.y + 1);
                        _loc_2 = true;
                    }
                    else if (this.m_fitOffset.y == -1)
                    {
                        _loc_2 = true;
                    }
                    else
                    {
                        return null;
                    }
                    _loc_3 = new Road(this.baseRoadName() + "-2");
                    _loc_3.setPosition(param1.x, param1.y);
                    if (_loc_2)
                    {
                        _loc_3.rotate();
                    }
                    return _loc_3;
                }
                else if (this.m_fitStretch)
                {
                    if (this.m_fitOffset.x == 1)
                    {
                    }
                    else if (this.m_fitOffset.x == -1)
                    {
                        (param1.x - 1);
                    }
                    else if (this.m_fitOffset.y == 1)
                    {
                        _loc_2 = true;
                    }
                    else if (this.m_fitOffset.y == -1)
                    {
                        (param1.y - 1);
                        _loc_2 = true;
                    }
                    else
                    {
                        return null;
                    }
                    _loc_3 = new Road(this.baseRoadName() + "-4");
                    _loc_3.setPosition(param1.x, param1.y);
                    if (_loc_2)
                    {
                        _loc_3.rotate();
                    }
                    return _loc_3;
                }
            }
            return null;
        }//end

        protected Road  checkForNewTee (Vector2 param1 )
        {
            boolean _loc_2 =false ;
            Road _loc_3 =null ;
            this.clearContent();
            if (this.m_adjacentPartial.length == 2)
            {
                _loc_2 = false;
                if (this.m_fitShrink)
                {
                    if (this.m_fitOffset.x == 1)
                    {
                        (param1.x + 1);
                    }
                    else if (this.m_fitOffset.x == -1)
                    {
                    }
                    else if (this.m_fitOffset.y == 1)
                    {
                        (param1.y + 1);
                        _loc_2 = true;
                    }
                    else if (this.m_fitOffset.y == -1)
                    {
                        _loc_2 = true;
                    }
                    else
                    {
                        return null;
                    }
                    _loc_3 = new Road(this.baseRoadName() + "-2");
                    _loc_3.setPosition(param1.x, param1.y);
                    if (_loc_2)
                    {
                        _loc_3.rotate();
                    }
                    return _loc_3;
                }
                else if (this.m_fitStretch)
                {
                    if (this.m_fitOffset.x == 1)
                    {
                    }
                    else if (this.m_fitOffset.x == -1)
                    {
                        (param1.x - 1);
                    }
                    else if (this.m_fitOffset.y == 1)
                    {
                        _loc_2 = true;
                    }
                    else if (this.m_fitOffset.y == -1)
                    {
                        (param1.y - 1);
                        _loc_2 = true;
                    }
                    else
                    {
                        return null;
                    }
                    _loc_3 = new Road(this.baseRoadName() + "-4");
                    _loc_3.setPosition(param1.x, param1.y);
                    if (_loc_2)
                    {
                        _loc_3.rotate();
                    }
                    return _loc_3;
                }
            }
            return null;
        }//end

        protected Road  shouldReplaceRoad (Vector2 param1 )
        {
            boolean _loc_5 =false ;
            Road _loc_2 =null ;
            switch(this.m_tightDirection)
            {
                case LEFT:
                {
                    break;
                }
                case DOWN:
                {
                    break;
                }
                case UP:
                {
                    break;
                }
                default:
                {
                    break;
                }
            }
            if (_loc_5)
            {
            }
            if (this.m_currentRule != null && "corner" in this.m_currentRule)
            {
            }
            else if (this.m_currentRule != null && "tee" in this.m_currentRule)
            {
            }
            if (_loc_2 && _loc_2.m_position.equals(m_position) && _loc_2.m_item == m_item && _loc_2.m_size.equals(m_size))
            {
                _loc_2 = null;
            }
            if (this.m_saveDragItem != null && _loc_2 == null && m_item != this.m_saveDragItem)
            {
                _loc_2 = new Road(this.baseRoadName());
                _loc_2.setPosition(param1.x, param1.y);
            }
            this.m_saveDragItem = null;
            this.m_checkForReplaceAtDrop = false;
            if (_loc_2 == null)
            {
                return null;
            }
            _loc_3 = _loc_2.getSize();
            Array _loc_4 =new Array();
            if (!Global.world.checkCollision(param1.x, param1.y, param1.x + _loc_3.x, param1.y + _loc_3.y, [this], _loc_4, Constants.WORLDOBJECT_ALL, _loc_2))
            {
                _loc_2.setOuter(Global.world);
                this.m_overlayContentMode = OVERLAY_NONE;
                this.m_overlay1Mode = OVERLAY_NONE;
                this.m_overlay2Mode = OVERLAY_NONE;
                if (this.m_id != 0)
                {
                    GameTransactionManager.addTransaction(new TReplace(this, _loc_2));
                }
                _loc_2.m_adjacent = this.m_adjacent;
                _loc_2.m_adjacentRoads = this.m_adjacentRoads;
                _loc_2.m_adjacentPartial = this.m_adjacentPartial;
                _loc_2.m_adjacentTight = this.m_adjacentTight;
                this.detach();
                cleanUp();
                return _loc_2;
            }
            return null;
        }//end

        public Road  prepareToDrop (Vector3 param1 ,Vector2 param2 )
        {
            m_position.x = param2.x;
            m_position.y = param2.y;
            this.clearContent();
            if (this.shrinkOneTile(true))
            {
                param2.x = m_position.x;
                param2.y = m_position.y;
            }
            else if (this.stretchOneTile(true))
            {
                this.detach();
                if (this.getId())
                {
                    GameTransactionManager.addTransaction(new TSell(this));
                }
                Global.world.citySim.roadManager.updateAllRoadTiles();
                return null;
            }
            _loc_3 = this.shouldReplaceRoad(param2 );
            if (_loc_3 != null)
            {
                return _loc_3;
            }
            return this;
        }//end

         public void  onObjectDrop (Vector3 param1 )
        {
            Road _loc_3 =null ;
            Road _loc_5 =null ;
            Vector2 _loc_6 =null ;
            Road _loc_7 =null ;
            _loc_2 = Global.world.getObjectsByClass(Road);
            _loc_4 = this.m_adjacentRoads;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                if (_loc_3 != null && _loc_3.m_checkForReplaceAtDrop)
                {
                    _loc_4 = _loc_4.concat(_loc_3);
                }
            }
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                if (_loc_5 != null)
                {
                    _loc_6 = new Vector2(_loc_5.m_position.x, _loc_5.m_position.y);
                    _loc_7 = _loc_5.shouldReplaceRoad(_loc_6);
                    _loc_5.m_checkForReplaceAtDrop = false;
                    if (_loc_7 != null)
                    {
                        _loc_7.attach();
                    }
                }
            }
            Global.world.citySim.roadManager.updateAllRoadTiles();
            super.onObjectDrop(param1);
            this.m_isRoadBeingDragged = false;
            return;
        }//end

         public String  getToolTipHeader ()
        {
            return null;
        }//end

         public String  getGameModeToolTipAction ()
        {
            _loc_1 = super.getGameModeToolTipAction();
            _loc_2 = Global.world.getTopGameMode();
            if (_loc_2 instanceof GMEditRotate)
            {
                _loc_1 = null;
            }
            return _loc_1;
        }//end

         public boolean  isPlacedObjectNonBuilding ()
        {
            return true;
        }//end

         public Vector3  getHotspot ()
        {
            return new Vector3(m_position.x + m_size.x / 2, m_position.y + m_size.y / 2);
        }//end

        public static boolean  overlay ()
        {
            return m_dbgShape != null;
        }//end

        public static void  showOverlay (boolean param1 )
        {
            if (param1 !=null)
            {
                if (m_dbgShape == null)
                {
                    m_dbgShape = new Shape();
                    GlobalEngine.viewport.objectBase.addChild(m_dbgShape);
                }
                m_dbgShape.graphics.clear();
                Global.world.citySim.roadManager.updateAllRoadTiles();
            }
            else if (m_dbgShape != null)
            {
                if (m_dbgShape.parent)
                {
                    m_dbgShape.parent.removeChild(m_dbgShape);
                }
                m_dbgShape.graphics.clear();
                m_dbgShape = null;
            }
            return;
        }//end


        public static boolean  canSwitchToBaseItem (Road param1 )
        {
            Item _loc_2 =null ;
            Vector3 _loc_3 =null ;
            if (param1 != null && param1.m_saveDragItem == null)
            {
                param1.m_saveDragItem = param1.m_item;
                if (param1.m_size.x < SIZE_X || param1.m_size.y < SIZE_Y)
                {
                    _loc_2 = Global.gameSettings().getItemByName(param1.baseRoadName());
                    if (_loc_2 != null)
                    {
                        if (param1.m_isRoadBeingDragged || !Global.world.checkCollision(param1.m_position.x, param1.m_position.y, param1.m_position.x + SIZE_X, param1.m_position.y + SIZE_Y, [param1], null, Constants.WORLDOBJECT_ALL, param1))
                        {
                            param1.setItem(_loc_2);
                            param1.updateSize();
                            param1.conditionallyReattach(true);
                        }
                        else if (param1.m_size.x < SIZE_X)
                        {
                            if (!Global.world.checkCollision((param1.m_position.x - 1), param1.m_position.y, (param1.m_position.x - 1) + SIZE_X, param1.m_position.y + SIZE_Y, [param1], null, Constants.WORLDOBJECT_ALL, param1))
                            {
                                param1.setPosition((param1.m_position.x - 1), param1.m_position.y);
                                param1.setItem(_loc_2);
                                param1.updateSize();
                                param1.conditionallyReattach(true);
                            }
                        }
                        else if (!Global.world.checkCollision(param1.m_position.x, (param1.m_position.y - 1), param1.m_position.x + SIZE_X, (param1.m_position.y - 1) + SIZE_Y, [param1], null, Constants.WORLDOBJECT_ALL, param1))
                        {
                            param1.setPosition(param1.m_position.x, (param1.m_position.y - 1));
                            param1.setItem(_loc_2);
                            param1.updateSize();
                            param1.conditionallyReattach(true);
                        }
                    }
                }
                else if (param1.m_size.x > SIZE_X || param1.m_size.y > SIZE_Y)
                {
                    _loc_2 = Global.gameSettings().getItemByName(param1.baseRoadName());
                    _loc_3 = param1.getPosition();
                    if (param1.m_size.x > SIZE_X)
                    {
                        if (param1.m_adjacentRoads.get(RIGHT) != null && param1.m_adjacentRoads.get(LEFT) == null)
                        {
                            (_loc_3.x + 1);
                            param1.setPosition(_loc_3.x, _loc_3.y);
                        }
                    }
                    if (param1.m_size.y > SIZE_Y)
                    {
                        if (param1.m_adjacentRoads.get(UP) != null && param1.m_adjacentRoads.get(DOWN) == null)
                        {
                            (_loc_3.y + 1);
                            param1.setPosition(_loc_3.x, _loc_3.y);
                        }
                    }
                    if (_loc_2 != null)
                    {
                        param1.setItem(_loc_2);
                    }
                }
                param1.updateSize();
                param1.m_checkForReplaceAtDrop = true;
                return param1.m_saveDragItem != null;
            }
            return false;
        }//end

        private static boolean  nearbyRoads (GameObject param1 )
        {
            _loc_2 = (Road)param1
            if (_loc_2 == null)
            {
                return false;
            }
            double _loc_3 =9;
            _loc_4 = s_nearX-_loc_2.m_position.x;
            _loc_5 = s_nearY-_loc_2.m_position.y;
            if (_loc_4 * _loc_4 + _loc_5 * _loc_5 < _loc_3 * _loc_3)
            {
                return true;
            }
            return false;
        }//end

    }





