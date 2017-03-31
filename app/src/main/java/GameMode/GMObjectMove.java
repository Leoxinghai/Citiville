package GameMode;

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
import Classes.bonus.*;
import Classes.sim.*;
import Classes.util.*;
import Display.*;
import Display.MarketUI.*;
import Engine.*;
import Engine.Helpers.*;
import Events.*;
import Modules.guide.ui.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Transactions.*;
import com.greensock.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.text.*;

    public class GMObjectMove extends GameMode
    {
        protected boolean m_isGift =false ;
        protected int m_cid ;
        protected Point m_highlightedPoint ;
        protected GameObject m_selectedObject =null ;
        protected boolean m_positionValid =false ;
        protected Array m_collisions ;
        protected Vector3 m_objectStartPos =null ;
        protected int m_objectStartDirection ;
        protected Vector2 m_releasedPoint =null ;
        protected boolean m_objectPlacedSuccess =false ;
        protected boolean m_objectStoredSuccess =false ;
        protected boolean m_dropImmediately =false ;
        protected Array m_validStorageClasses ;
        private Point m_dragStartPos ;
        protected Point m_objectDragOffset ;
        protected Point m_viewDragStartPos =null ;
        protected boolean m_dragging =false ;
        protected String m_oldState ;
        protected int m_oldDirection ;
        protected int m_storedOrigin =0;
        protected boolean m_mouseDownFlag =false ;
        protected Array m_onPlaceCollisions ;
        protected int m_onPlaceFilter ;
        protected Sprite m_displayBonusObject ;
        protected TextField m_displayTextObj ;
        protected TextField m_displayTextObj2 ;
        protected TextFormat m_textFormat ;
        protected int m_textYOffset ;
        protected Array m_moveHelperList ;
        protected Point m_prevPos ;
        protected ItemBonus m_curBonus ;
        protected Bitmap m_coinIcon ;
        protected boolean m_autoRotate ;
        protected DisplayObject m_preview ;
        private Array m_areasOfEffect ;
        private Array m_aoEffectObjects ;
        private boolean m_alternatingGrantReceive =false ;
        private boolean m_selectedLocked =false ;
        public  double LINE_ALPHA =0.5;
        public  double LINE_WIDTH =0.5;
        private boolean m_exitAfterDrop =false ;
        private Vector3 m_backupStartPosition ;
        private boolean m_linkedObjectMode =false ;
        private Array m_keywordGlowObjects ;
        protected GameObject m_curTransObj =null ;
        protected Shape m_positionFeedback =null ;
public static  int TILE_OUTLINE_WIDTH =2;
        public static  int BONUS_TEXT_SIZE =20;
        public static  double BONUS_TEXT_INCREASE =1.1;
        public static  double BONUS_FADE_IN =0.5;
        public static  double BONUS_FADE_OUT =0.5;
        public static  double MOVE_TWEEN_TIME =0.2;
        public static  int COLOR_NO_ROAD_CONNECT =16496919;
        public static boolean USE_TILES =true ;
        public static boolean SHOW_AREAS_AFFECTING_OBJECT =false ;

        public  GMObjectMove (GameObject param1 ,String param2 ,Point param3 =null ,int param4 =-1,boolean param5 =false ,Vector3 param6 =null )
        {
            Vector2 _loc_7 =null ;
            Point _loc_8 =null ;
            this.m_collisions = new Array();
            this.m_validStorageClasses = new Array();
            this.m_displayBonusObject = new Sprite();
            this.m_moveHelperList = new Array();
            this.m_prevPos = new Point();
            this.m_areasOfEffect = new Array();
            this.m_aoEffectObjects = new Array();
            this.m_keywordGlowObjects = new Array();
            if (!this.m_linkedObjectMode && Global.world.getTopGameMode() instanceof GMObjectMove)
            {
                Global.world.popGameMode();
            }
            this.m_selectedObject = param1;
            this.m_oldState = param2;
            this.m_oldDirection = param4;
            this.m_exitAfterDrop = param5;
            this.resetOnPlaceCollisions();
            this.m_viewDragStartPos = param3;
            if (this.m_selectedObject)
            {
                if (param6)
                {
                    this.m_backupStartPosition = param6;
                }
                this.m_objectStartPos = param1.getPosition();
                _loc_7 = IsoMath.tilePosToPixelPos(this.m_objectStartPos.x, this.m_objectStartPos.y);
                _loc_8 = new Point(_loc_7.x, _loc_7.y);
                _loc_8 = IsoMath.viewportToStage(_loc_8);
                this.m_objectStartDirection = this.m_selectedObject.getDirection();
                this.m_objectDragOffset = getMouseStagePos().subtract(_loc_8);
                if (param4 == Constants.INDEX_NONE)
                {
                    this.m_oldDirection = this.m_selectedObject.getDirection();
                }
                this.registerAllOnPlaceCollisions();
            }
            m_showMousePointer = true;
            m_cursorImage = EmbeddedArt.hud_act_move;
            this.m_coinIcon = new EmbeddedArt.smallCoinIcon();
            this.m_coinIcon.visible = false;
            this.m_autoRotate = true;
            this.m_displayTextObj = new StrokeTextField(0, 0.4);
            this.m_displayTextObj.embedFonts = EmbeddedArt.titleFontEmbed;
            this.m_displayTextObj.autoSize = TextFieldAutoSize.LEFT;
            this.m_textFormat = new TextFormat(EmbeddedArt.titleFont, 7, EmbeddedArt.TEXT_MAIN_COLOR, true);
            this.m_displayTextObj2 = new StrokeTextField(EmbeddedArt.TEXT_BACKGROUND_COLOR, 0.4);
            this.m_displayTextObj2.embedFonts = EmbeddedArt.titleFontEmbed;
            this.m_displayTextObj2.autoSize = TextFieldAutoSize.RIGHT;
            this.m_displayBonusObject.addChild(this.m_displayTextObj);
            this.m_displayBonusObject.addChild(this.m_displayTextObj2);
            this.m_displayBonusObject.addChild(this.m_coinIcon);
            this.m_displayBonusObject.alpha = 0;
            TweenLite.to(this.m_displayBonusObject, BONUS_FADE_IN, {alpha:1});
            m_uiMode = UIEvent.MOVE;
            return;
        }//end

        public GameObject  getSelectedObject ()
        {
            return this.m_selectedObject;
        }//end

        protected void  registerAllOnPlaceCollisions ()
        {
            return;
        }//end

        public void  doImmediateDrop ()
        {
            this.m_dropImmediately = true;
            return;
        }//end

        private double  getWaveScale (double param1 )
        {
            _loc_2 = Constants.TILE_WIDTH*ConsumerShockwave.ORTHO_FACTOR/200;
            return _loc_2 * param1;
        }//end

        public DisplayObject  startWavePreview (DisplayObject param1 ,double param2 ,Point param3 ,int param4 )
        {
            _loc_5 = new EmbeddedArt.businessShockwavePreview ();
            int _loc_9 =0;
            _loc_5.scaleY = 0;
            _loc_5.scaleX = 0;
            _loc_5.x = param3.x;
            _loc_5.y = param3.y;
            _loc_6 =Global.world.getObjectLayerByName("road").getDisplayObject ()as Sprite ;
            _loc_6.addChild(_loc_5);
            _loc_7 = _loc_5.transform.colorTransform ;
            _loc_7.color = param4;

            _loc_8 = this.getWaveScale(param2 );
            TweenLite.to(_loc_5, BONUS_FADE_IN / 2, {scaleX:_loc_8, scaleY:_loc_8});
            return _loc_5;
        }//end

        public DisplayObject  startAOEPreview (ItemInstance param1 ,double param2 ,Point param3 ,int param4 )
        {
            Point _loc_15 =null ;
            Array _loc_17 =null ;
            int _loc_18 =0;
            int _loc_19 =0;
            double _loc_20 =0;
            double _loc_21 =0;
            double _loc_22 =0;
            Shape _loc_5 =new Shape ();
            _loc_5.alpha = 0;
            _loc_5.x = param3.x;
            _loc_5.y = param3.y;
            _loc_6 =Global.world.getObjectLayerByName("road").getDisplayObject ()as Sprite ;
            _loc_6.addChild(_loc_5);
            _loc_7 = param1.getSize ();
            _loc_8 = param1.getPosition ();
            _loc_9 = IsoMath.getPixelDeltaFromTileDelta(1,0);
            _loc_10 = IsoMath.getPixelDeltaFromTileDelta(0,1);
            _loc_11 = param2-;
            _loc_12 = _loc_7.x+param2;
            _loc_13 = param2-;
            _loc_14 = _loc_7.y+param2;
            boolean _loc_16 =true ;
            if (true)
            {
                _loc_5.graphics.lineStyle(1, param4, 0.75);
                _loc_15 = IsoMath.getPixelDeltaFromTileDelta(_loc_11, _loc_13);
                _loc_5.graphics.moveTo(_loc_15.x, _loc_15.y);
                _loc_15 = IsoMath.getPixelDeltaFromTileDelta(_loc_12, _loc_13);
                _loc_5.graphics.lineTo(_loc_15.x, _loc_15.y);
                _loc_15 = IsoMath.getPixelDeltaFromTileDelta(_loc_12, _loc_14);
                _loc_5.graphics.lineTo(_loc_15.x, _loc_15.y);
                _loc_15 = IsoMath.getPixelDeltaFromTileDelta(_loc_11, _loc_14);
                _loc_5.graphics.lineTo(_loc_15.x, _loc_15.y);
                _loc_15 = IsoMath.getPixelDeltaFromTileDelta(_loc_11, _loc_13);
                _loc_5.graphics.lineTo(_loc_15.x, _loc_15.y);
            }
            else
            {
                _loc_17 = new Array();
                _loc_18 = _loc_11;
                while (_loc_18 < _loc_12)
                {

                    _loc_17.put(_loc_18,  new Array());
                    _loc_19 = _loc_13;
                    while (_loc_19 < _loc_14)
                    {

                        if (_loc_18 >= 0 && _loc_18 < _loc_7.x && _loc_19 >= 0 && _loc_19 < _loc_7.y)
                        {
                            _loc_17.get(_loc_18).put(_loc_19,  true);
                        }
                        else
                        {
                            _loc_20 = _loc_18 + 0.5 - _loc_7.x * 0.5;
                            _loc_21 = _loc_19 + 0.5 - _loc_7.y * 0.5;
                            _loc_22 = Math.sqrt(_loc_20 * _loc_20 + _loc_21 * _loc_21);
                            if (_loc_22 > param2 + 0.5)
                            {
                            }
                            else
                            {
                                _loc_17.get(_loc_18).put(_loc_19,  true);
                                _loc_15 = IsoMath.getPixelDeltaFromTileDelta(_loc_18, _loc_19);
                                _loc_5.graphics.moveTo(_loc_15.x, _loc_15.y);
                                _loc_5.graphics.lineTo(_loc_15.x + _loc_9.x, _loc_15.y + _loc_9.y);
                                _loc_5.graphics.lineTo(_loc_15.x + _loc_9.x + _loc_10.x, _loc_15.y + _loc_9.y + _loc_10.y);
                                _loc_5.graphics.lineTo(_loc_15.x + _loc_10.x, _loc_15.y + _loc_10.y);
                                _loc_5.graphics.lineTo(_loc_15.x, _loc_15.y);
                                _loc_5.graphics.endFill();
                            }
                        }
                        _loc_19++;
                    }
                    _loc_18++;
                }
                _loc_5.graphics.lineStyle(1, param4, 0.75);
                _loc_18 = _loc_11;
                while (_loc_18 < _loc_12)
                {

                    _loc_19 = _loc_13;
                    while (_loc_19 < _loc_14)
                    {

                        if (_loc_17.get(_loc_18).get(_loc_19) != true)
                        {
                        }
                        else
                        {
                            _loc_15 = IsoMath.getPixelDeltaFromTileDelta(_loc_18, _loc_19);
                            if (_loc_18 == -param2 || _loc_17.get((_loc_18 - 1)).get(_loc_19) != true)
                            {
                                _loc_5.graphics.moveTo(_loc_15.x, _loc_15.y);
                                _loc_5.graphics.lineTo(_loc_15.x + _loc_10.x, _loc_15.y + _loc_10.y);
                            }
                            if (_loc_18 == (_loc_12 - 1) || _loc_17.get((_loc_18 + 1)).get(_loc_19) != true)
                            {
                                _loc_5.graphics.moveTo(_loc_15.x + _loc_9.x, _loc_15.y + _loc_9.y);
                                _loc_5.graphics.lineTo(_loc_15.x + _loc_9.x + _loc_10.x, _loc_15.y + _loc_9.y + _loc_10.y);
                            }
                            if (_loc_17.get(_loc_18).get((_loc_19 - 1)) != true)
                            {
                                _loc_5.graphics.moveTo(_loc_15.x, _loc_15.y);
                                _loc_5.graphics.lineTo(_loc_15.x + _loc_9.x, _loc_15.y + _loc_9.y);
                            }
                            if (_loc_17.get(_loc_18).get((_loc_19 + 1)) != true)
                            {
                                _loc_5.graphics.moveTo(_loc_15.x + _loc_10.x, _loc_15.y + _loc_10.y);
                                _loc_5.graphics.lineTo(_loc_15.x + _loc_9.x + _loc_10.x, _loc_15.y + _loc_9.y + _loc_10.y);
                            }
                        }
                        _loc_19++;
                    }
                    _loc_18++;
                }
            }
            TweenLite.to(_loc_5, BONUS_FADE_IN / 2, {alpha:1});
            return _loc_5;
        }//end

        public Shape  positionFeedback (ItemInstance param1 ,Point param2 )
        {
            Point _loc_20 =null ;
            Point _loc_21 =null ;
            Point _loc_22 =null ;
            int _loc_27 =0;
            MapResource _loc_28 =null ;
            int _loc_29 =0;
            int _loc_30 =0;
            double _loc_31 =0;
            double _loc_32 =0;
            double _loc_33 =0;
            double _loc_34 =0;
            double _loc_35 =0;
            _loc_3 = this.m_positionFeedback ;
            if (_loc_3 == null)
            {
                _loc_3 = new Shape();
                _loc_28 =(MapResource) this.m_selectedObject;
                if (_loc_28 && _loc_28.getItem().type != "road")
                {
                    if (!this.m_selectedLocked)
                    {
                        this.m_selectedObject.lock();
                        this.m_selectedLocked = true;
                    }
                }
                _loc_29 = GlobalEngine.viewport.overlayBase.numChildren;
                if (_loc_29 > 1)
                {
                    _loc_29 = _loc_29 - 1;
                }
                GlobalEngine.viewport.overlayBase.addChildAt(_loc_3, _loc_29);
                _loc_3.alpha = 0;
            }
            _loc_3.x = param2.x;
            _loc_3.y = param2.y;
            _loc_4 = param1.getSize ();
            _loc_5 = param1.getSize ().y ;
            if (_loc_4.x > _loc_4.y)
            {
                _loc_5 = _loc_4.x;
            }
            _loc_6 = param1.getPosition ();
            _loc_7 = IsoMath.getPixelDeltaFromTileDelta(1,0);
            _loc_8 = IsoMath.getPixelDeltaFromTileDelta(0,1);
            int _loc_9 =5;
            _loc_10 =             -5;
            _loc_11 = _loc_5+_loc_9;
            _loc_12 = _loc_5(-_loc_4.x)/2;
            _loc_10 = _loc_10 - _loc_12;
            _loc_11 = _loc_11 - _loc_12;
            _loc_13 = _loc_9-;
            _loc_14 = _loc_5+_loc_9;
            _loc_12 = (_loc_5 - _loc_4.y) / 2;
            _loc_13 = _loc_13 - _loc_12;
            _loc_14 = _loc_14 - _loc_12;
            _loc_3.graphics.clear();
            _loc_15 = _loc_14(-_loc_13-2*_loc_9-0.5)/2;
            _loc_16 = this.m_positionValid ? (Constants.COLOR_VALID_PLACEMENT) : (Constants.COLOR_NOT_VALID_PLACEMENT);
            if (this.m_positionValid)
            {
                _loc_16 = 1148945;
            }
            Rectangle _loc_17 =new Rectangle(0,0,1,1);
            Rectangle _loc_18 =new Rectangle ();
            Matrix _loc_19 =new Matrix ();
            Array _loc_23 =new Array();
            Array _loc_24 =new Array();
            Array _loc_25 =new Array();
            _loc_26 = _loc_10;
            while (_loc_26 <= _loc_11)
            {

                _loc_20 = IsoMath.getPixelDeltaFromTileDelta(_loc_26, _loc_13);
                _loc_21 = IsoMath.getPixelDeltaFromTileDelta(_loc_26, _loc_14);
                _loc_19.createGradientBox(_loc_21.x - _loc_20.x, _loc_21.y - _loc_20.y, 0, _loc_20.x, _loc_20.y);
                _loc_3.graphics.lineStyle(this.LINE_WIDTH, 0);
                _loc_30 = 0;
                _loc_23 = new Array();
                _loc_24 = new Array();
                _loc_25 = new Array();
                _loc_27 = _loc_13;
                while (_loc_27 <= _loc_14)
                {

                    _loc_31 = _loc_26 - _loc_4.x * 0.5;
                    _loc_32 = _loc_27 - _loc_4.y * 0.5;
                    _loc_33 = Math.sqrt(_loc_31 * _loc_31 + _loc_32 * _loc_32);
                    _loc_33 = _loc_33 - _loc_15;
                    _loc_34 = 1;
                    if (_loc_33 > 0)
                    {
                        _loc_34 = 1 - _loc_33 / _loc_9;
                        if (_loc_34 < 0)
                        {
                            _loc_34 = 0;
                        }
                    }
                    _loc_23.push(_loc_16);
                    _loc_24.push(_loc_34);
                    _loc_25.push(_loc_30 * 255 / (_loc_14 - _loc_13));
                    _loc_30++;
                    _loc_27++;
                }
                _loc_3.graphics.lineGradientStyle(GradientType.LINEAR, _loc_23, _loc_24, _loc_25, _loc_19);
                if (_loc_26 > 0 && _loc_26 < _loc_4.x)
                {
                    _loc_3.graphics.moveTo(_loc_20.x, _loc_20.y);
                    _loc_22 = IsoMath.getPixelDeltaFromTileDelta(_loc_26, 0);
                    _loc_3.graphics.lineTo(_loc_22.x, _loc_22.y);
                    _loc_22 = IsoMath.getPixelDeltaFromTileDelta(_loc_26, _loc_4.y);
                    _loc_3.graphics.moveTo(_loc_22.x, _loc_22.y);
                    _loc_3.graphics.lineTo(_loc_21.x, _loc_21.y);
                }
                else
                {
                    _loc_3.graphics.moveTo(_loc_20.x, _loc_20.y);
                    _loc_3.graphics.lineTo(_loc_21.x, _loc_21.y);
                }
                _loc_26++;
            }
            _loc_27 = _loc_13;
            while (_loc_27 <= _loc_14)
            {

                _loc_20 = IsoMath.getPixelDeltaFromTileDelta(_loc_10, _loc_27);
                _loc_21 = IsoMath.getPixelDeltaFromTileDelta(_loc_11, _loc_27);
                _loc_35 = 0;
                _loc_19.createGradientBox(_loc_21.x - _loc_20.x, _loc_21.y - _loc_20.y, _loc_35, _loc_20.x, _loc_20.y);
                _loc_3.graphics.lineStyle(this.LINE_WIDTH, 0);
                _loc_30 = 0;
                _loc_23 = new Array();
                _loc_24 = new Array();
                _loc_25 = new Array();
                _loc_26 = _loc_10;
                while (_loc_26 <= _loc_11)
                {

                    _loc_31 = _loc_26 - _loc_4.x * 0.5;
                    _loc_32 = _loc_27 - _loc_4.y * 0.5;
                    _loc_33 = Math.sqrt(_loc_31 * _loc_31 + _loc_32 * _loc_32);
                    _loc_33 = _loc_33 - _loc_15;
                    _loc_34 = 1;
                    if (_loc_33 > 0)
                    {
                        _loc_34 = 1 - _loc_33 / _loc_9;
                        if (_loc_34 < 0)
                        {
                            _loc_34 = 0;
                        }
                    }
                    _loc_23.push(_loc_16);
                    _loc_24.push(_loc_34);
                    _loc_25.push(_loc_30 * 255 / (_loc_14 - _loc_13));
                    _loc_30++;
                    _loc_26++;
                }
                _loc_3.graphics.lineGradientStyle(GradientType.LINEAR, _loc_23, _loc_24, _loc_25, _loc_19);
                if (_loc_27 > 0 && _loc_27 < _loc_4.y)
                {
                    _loc_3.graphics.moveTo(_loc_20.x, _loc_20.y);
                    _loc_22 = IsoMath.getPixelDeltaFromTileDelta(0, _loc_27);
                    _loc_3.graphics.lineTo(_loc_22.x, _loc_22.y);
                    _loc_22 = IsoMath.getPixelDeltaFromTileDelta(_loc_4.x, _loc_27);
                    _loc_3.graphics.moveTo(_loc_22.x, _loc_22.y);
                    _loc_3.graphics.lineTo(_loc_21.x, _loc_21.y);
                }
                else
                {
                    _loc_3.graphics.moveTo(_loc_20.x, _loc_20.y);
                    _loc_3.graphics.lineTo(_loc_21.x, _loc_21.y);
                }
                _loc_27++;
            }
            TweenLite.to(_loc_3, BONUS_FADE_IN / 2, {alpha:1});
            return _loc_3;
        }//end

        public Shape  startCloseShape (ItemInstance param1 )
        {
            return null;
        }//end

        public void  fadeThenRemove (DisplayObject param1 )
        {
            preview = param1;
            if (preview == null)
            {
                return;
            }
            cont = Curry.curry(function(param11DisplayObject)
            {
                if (param11.parent != null)
                {
                    param11.parent.removeChild(param11);
                }
                return;
            }//end
            , preview);
            TweenLite.to(preview, BONUS_FADE_OUT, {alpha:0, onComplete:cont});
            return;
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
            if (_loc_2 == this.m_selectedObject)
            {
                return false;
            }
            if (_loc_2.getParent() == this.m_selectedObject)
            {
                return false;
            }
            return this.m_curBonus.withinRadius(MapResource(this.m_selectedObject), _loc_2);
        }//end

        private boolean  matchKeywordGlow (GameObject param1 )
        {
            MapResource _loc_2 =null ;
            MapResource _loc_3 =null ;
            Item _loc_4 =null ;
            Array _loc_5 =null ;
            Item _loc_6 =null ;
            String _loc_7 =null ;
            ConstructionSite _loc_8 =null ;
            if (!ItemBonus.keywordExperimentEnabled())
            {
                return false;
            }
            if (this.m_selectedObject instanceof MapResource && param1 instanceof MapResource)
            {
                _loc_2 = MapResource(this.m_selectedObject);
                _loc_3 = MapResource(param1);
                if (_loc_3 == this.m_selectedObject)
                {
                    return false;
                }
                if (this.m_selectedObject instanceof ConstructionSite)
                {
                    _loc_8 = ConstructionSite(this.m_selectedObject);
                    _loc_4 = _loc_8.targetItem;
                }
                else
                {
                    _loc_4 = _loc_2.getItem();
                }
                _loc_5 = _loc_4.getVisibleItemKeywords();
                _loc_6 = _loc_3.getItem();
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_7 = _loc_5.get(i0);

                    if (_loc_6.itemHasKeyword(_loc_7))
                    {
                        return true;
                    }
                }
            }
            return false;
        }//end

        private void  endAreasOfEffect ()
        {
            DisplayObject _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_areasOfEffect.size(); i0++)
            {
            	_loc_1 = this.m_areasOfEffect.get(i0);

                this.fadeThenRemove(_loc_1);
            }
            this.m_areasOfEffect.length = 0;
            this.m_aoEffectObjects.length = 0;
            return;
        }//end

        private void  showAreasOfEffect (Array param1 ,Array param2 )
        {
            ItemBonus _loc_3 =null ;
            ItemInstance _loc_4 =null ;
            int _loc_7 =0;
            Point _loc_9 =null ;
            Point _loc_10 =null ;
            DisplayObject _loc_11 =null ;
            double _loc_5 =0;
            int _loc_6 =0;
            _loc_6 = 0;
            while (_loc_6 < param1.length())
            {

                _loc_4 = param2.get(_loc_6);
                _loc_3 = param1.get(_loc_6);
                if (this.m_aoEffectObjects.indexOf(_loc_4) >= 0)
                {
                }
                else
                {
                    _loc_9 = getPixelCenter(_loc_4);
                    _loc_10 = IsoMath.tilePosToPixelPos(this.m_highlightedPoint.x, this.m_highlightedPoint.y);
                    _loc_7 = !_loc_3.isNegative() ? (Constants.COLOR_VALID_PLACEMENT) : (Constants.COLOR_NOT_VALID_PLACEMENT);
                    if (USE_TILES)
                    {
                        _loc_11 = this.startAOEPreview(_loc_4, _loc_3.getRadius(_loc_4), _loc_10, _loc_7);
                    }
                    else
                    {
                        _loc_11 = this.startWavePreview(this.m_displayBonusObject, _loc_3.effectiveRadius(_loc_4), _loc_9, _loc_7);
                    }
                    this.m_areasOfEffect.push(_loc_11);
                    this.m_aoEffectObjects.push(_loc_4);
                }
                _loc_6++;
            }
            int _loc_8 =0;
            while (_loc_8 < this.m_aoEffectObjects.length())
            {

                _loc_4 =(MapResource) this.m_aoEffectObjects.get(_loc_8);
                if (param2.indexOf(_loc_4) >= 0)
                {
                }
                else
                {
                    this.m_aoEffectObjects.splice(_loc_8, 1);
                    if (_loc_8 < this.m_areasOfEffect.length())
                    {
                        _loc_11 =(DisplayObject) this.m_areasOfEffect.get(_loc_8);
                        this.fadeThenRemove(_loc_11);
                        this.m_areasOfEffect.splice(_loc_8, 1);
                    }
                    _loc_8 = _loc_8 - 1;
                }
                _loc_8++;
            }
            return;
        }//end

        private void  showBonusesAffectingMapObj (MapResource param1 ,Point param2 )
        {
            ItemInstance _loc_10 =null ;
            ItemBonus _loc_11 =null ;
            TextLineMetrics _loc_12 =null ;
            Array _loc_3 =new Array ();
            _loc_4 = ItemBonus.getBonuses(param1,_loc_3);
            if (SHOW_AREAS_AFFECTING_OBJECT)
            {
                this.showAreasOfEffect(_loc_4, _loc_3);
            }
            _loc_5 = getSmallBonusTextSize();
            double _loc_6 =0;
            boolean _loc_7 =false ;
            int _loc_8 =0;
            _loc_8 = 0;
            while (_loc_8 < _loc_4.length())
            {

                _loc_10 = _loc_3.get(_loc_8);
                _loc_11 = _loc_4.get(_loc_8);
                if (_loc_11.field == ItemBonus.COIN_YIELD && !(_loc_11 instanceof CustomerMaintenanceBonus))
                {
                    if (param1 instanceof Business && _loc_11.name == "FranchiseAOE")
                    {
                        if (!_loc_7)
                        {
                            _loc_6 = _loc_6 + ItemBonus.calcFranchiseBonuses((Business)param1);
                            _loc_7 = true;
                        }
                    }
                    else
                    {
                        _loc_6 = _loc_6 + _loc_11.getPercentModifier(_loc_10);
                    }
                }
                _loc_8++;
            }
            if (this.m_textYOffset == 0)
            {
                this.m_textYOffset = _loc_5;
            }
            _loc_9 = param1.getCoinYield ();
            if (this.m_displayBonusObject.x == 0)
            {
                this.m_displayBonusObject.x = param2.x;
                this.m_displayBonusObject.y = param2.y;
            }
            if (_loc_6)
            {
                this.m_displayBonusObject.x = param2.x;
                this.m_displayBonusObject.y = param2.y;
                TweenLite.to(this.m_displayTextObj, 0.5, {alpha:1});
                this.m_textFormat.size = _loc_5;
                this.m_displayTextObj.text = ZLoc.t("Main", "Bonus") + "\n" + (_loc_6 > 0 ? ("+") : ("")) + int(_loc_6) + "%";
                this.m_textFormat.color = bonusColor(param1);
                this.m_textFormat.align = TextFormatAlign.CENTER;
                this.m_displayTextObj.setTextFormat(this.m_textFormat);
                _loc_12 = this.m_displayTextObj.getLineMetrics(0);
                this.m_displayTextObj.x = (-_loc_12.width) / 2;
                this.m_displayTextObj.y = (-_loc_12.height) * 1.5;
            }
            else
            {
                this.m_coinIcon.visible = false;
                TweenLite.to(this.m_displayTextObj, 0.5, {alpha:0});
            }
            return;
        }//end

        private void  showPositioningFeedback (MapResource param1 ,Point param2 )
        {
            this.m_positionFeedback = this.positionFeedback(param1, param2);
            return;
        }//end

        private void  showBonusesMapObjApplies (MapResource param1 ,Point param2 )
        {
            ItemBonus _loc_3 =null ;
            String _loc_4 =null ;
            int _loc_5 =0;
            GMObjMoveHelper _loc_8 =null ;
            Point _loc_9 =null ;
            Array _loc_11 =null ;
            String _loc_12 =null ;
            Item _loc_13 =null ;
            boolean _loc_14 =false ;
            Array _loc_15 =null ;
            MapResource _loc_16 =null ;
            double _loc_17 =0;
            Point _loc_18 =null ;
            String _loc_19 =null ;
            ItemBonus _loc_20 =null ;
            int _loc_6 =0;
            _loc_7 = getSmallBonusTextSize();
            for(int i0 = 0; i0 < this.m_moveHelperList.size(); i0++)
            {
            	_loc_8 = this.m_moveHelperList.get(i0);

                _loc_8.active = false;
            }
            _loc_9 = IsoMath.tilePosToPixelPos(this.m_highlightedPoint.x, this.m_highlightedPoint.y);
            if (this.m_positionValid)
            {
                _loc_11 = new Array();
                if (param1 instanceof ConstructionSite)
                {
                    _loc_12 = ((ConstructionSite)param1).targetName;
                    _loc_13 = Global.gameSettings().getItemByName(_loc_12);
                    _loc_11 = _loc_13.bonuses;
                }
                else
                {
                    _loc_11 = param1.getItem().bonuses;
                }
                for(int i0 = 0; i0 < _loc_11.size(); i0++)
                {
                	_loc_3 = _loc_11.get(i0);

                    this.m_curBonus = _loc_3;
                    _loc_14 = _loc_3.isNegative();
                    _loc_5 = !_loc_14 ? (Constants.COLOR_HIGHLIGHT_BLUE) : (Constants.COLOR_NOT_VALID_PLACEMENT);
                    if (this.m_preview == null)
                    {
                        if (USE_TILES)
                        {
                            this.m_preview = this.startAOEPreview(param1, this.m_curBonus.getRadius(param1), _loc_9, _loc_5);
                        }
                        else
                        {
                            this.m_preview = this.startWavePreview(this.m_displayBonusObject, this.m_curBonus.effectiveRadius(param1), param2, _loc_5);
                        }
                    }
                    else if (USE_TILES)
                    {
                        TweenLite.to(this.m_preview, 0.3, {x:_loc_9.x, y:_loc_9.y, alpha:1});
                    }
                    else
                    {
                        _loc_17 = this.getWaveScale(this.m_curBonus.effectiveRadius(param1));
                        TweenLite.to(this.m_preview, 0.3, {x:param2.x, y:param2.y, scaleX:_loc_17, scaleY:_loc_17});
                    }
                    _loc_15 = new Array();
                    _loc_15 = Global.world.getObjectsByPredicate(this.matchBonus);
                    for(int i0 = 0; i0 < _loc_15.size(); i0++)
                    {
                    	_loc_16 = _loc_15.get(i0);

                        _loc_18 = getPixelCenter(_loc_16);
                        _loc_6 = 0;
                        while (_loc_6 < this.m_moveHelperList.length())
                        {

                            if (_loc_16 == this.m_moveHelperList.get(_loc_6).gameObject)
                            {
                                break;
                            }
                            _loc_6++;
                        }
                        _loc_19 = _loc_16.getItem().name;
                        if (_loc_6 >= this.m_moveHelperList.length())
                        {
                            _loc_6 = this.m_moveHelperList.length;
                            this.m_textFormat.color = _loc_5;
                            this.m_textFormat.size = _loc_7;
                            _loc_8 = new GMObjMoveHelper(_loc_16, _loc_18, this.m_textFormat, _loc_5, param1);
                            this.m_moveHelperList.push(_loc_8);
                            for(int i0 = 0; i0 < _loc_11.size(); i0++)
                            {
                            	_loc_20 = _loc_11.get(i0);

                                this.m_curBonus = _loc_20;
                                if (this.matchBonus(_loc_16))
                                {
                                    _loc_8.applyBonus(_loc_20);
                                }
                            }
                        }
                        else
                        {
                            _loc_8 = this.m_moveHelperList.get(_loc_6);
                        }
                        _loc_8.active = true;
                    }
                }
            }
            else if (this.m_preview != null)
            {
                this.fadeThenRemove(this.m_preview);
                this.m_preview = null;
            }
            int _loc_10 =0;
            while (_loc_10 < this.m_moveHelperList.length())
            {

                _loc_8 =(GMObjMoveHelper) this.m_moveHelperList.get(_loc_10);
                if (_loc_8.active)
                {
                    _loc_8.activate();
                }
                else
                {
                    if (this.m_positionValid)
                    {
                        _loc_8.showYieldAndDeactivate(this.m_textFormat);
                    }
                    else
                    {
                        _loc_8.deactivate();
                    }
                    this.m_moveHelperList.splice(_loc_10, 1);
                    _loc_10 = _loc_10 - 1;
                }
                _loc_10++;
            }
            return;
        }//end

        private void  updateBonuses ()
        {
            MapResource _loc_1 =null ;
            Point _loc_2 =null ;
            if (this.m_selectedObject instanceof MapResource)
            {
                _loc_1 = MapResource(this.m_selectedObject);
                if (this.m_prevPos.x == this.m_highlightedPoint.x && this.m_prevPos.y == this.m_highlightedPoint.y)
                {
                    return;
                }
                this.showPositioningFeedback(_loc_1, IsoMath.tilePosToPixelPos(this.m_highlightedPoint.x, this.m_highlightedPoint.y));
                this.m_prevPos.x = this.m_highlightedPoint.x;
                this.m_prevPos.y = this.m_highlightedPoint.y;
                this.m_displayBonusObject.graphics.clear();
                _loc_2 = getPixelCenter(_loc_1);
                this.showBonusesAffectingMapObj(_loc_1, _loc_2);
                this.showBonusesMapObjApplies(_loc_1, _loc_2);
            }
            return;
        }//end

         public void  enableMode ()
        {
            MapResource _loc_1 =null ;
            super.enableMode();
            this.onMouseMove(null);
            this.m_keywordGlowObjects = Global.world.getObjectsByPredicate(this.matchKeywordGlow);
            for(int i0 = 0; i0 < this.m_keywordGlowObjects.size(); i0++)
            {
            	_loc_1 = this.m_keywordGlowObjects.get(i0);

                _loc_1.enableGlow(EmbeddedArt.KEYWORD_GLOW_COLOR);
            }
            GlobalEngine.viewport.objectBase.addChild(this.m_displayBonusObject);
            if (this.m_selectedObject)
            {
                this.m_selectedObject.onMouseOut();
            }
            return;
        }//end

         public boolean  onMouseMove (MouseEvent event )
        {
            Vector2 _loc_3 =null ;
            Vector3 _loc_4 =null ;
            DisplayObject _loc_5 =null ;
            MapResource _loc_6 =null ;
            double _loc_7 =0;
            double _loc_8 =0;
            _loc_2 = getMouseStagePos();
            if (!Global.guide.isActive() && this.m_viewDragStartPos && _loc_2 && m_mouseDownPos && _loc_2.subtract(m_mouseDownPos).length >= Constants.DRAG_CLICK_EPSILON)
            {
                this.m_dragging = true;
            }
            if (this.m_dragging && this.m_viewDragStartPos)
            {
                _loc_3 = new Vector2();
                _loc_3.x = this.m_viewDragStartPos.x + (_loc_2.x - m_mouseDownPos.x) / GlobalEngine.viewport.getZoom();
                _loc_3.y = this.m_viewDragStartPos.y + (_loc_2.y - m_mouseDownPos.y) / GlobalEngine.viewport.getZoom();
                _loc_3 = limitBackgroundScrolling(_loc_3);
                GlobalEngine.viewport.setScrollPosition(_loc_3);
                Global.world.updateWorldObjectCulling(GameWorld.CULL_CHECK_ALL);
            }
            else if (this.m_selectedObject)
            {
                if (this.m_selectedObject.getOuter() == null)
                {
                    Global.world.popGameMode();
                    return false;
                }
                _loc_4 = this.m_selectedObject.getPosition();
                this.m_positionValid = this.updateHighlightPoint(event);
                if (_loc_4.x != this.m_highlightedPoint.x || _loc_4.y != this.m_highlightedPoint.y)
                {
                    _loc_5 = this.m_selectedObject.getDisplayObject();
                    if (_loc_5)
                    {
                        _loc_7 = _loc_5.x;
                        _loc_8 = _loc_5.y;
                    }
                    this.m_selectedObject.setPosition(this.m_highlightedPoint.x, this.m_highlightedPoint.y);
                    this.m_selectedObject.conditionallyReattach();
                    this.m_selectedObject.onObjectDrag(_loc_4);
                    _loc_6 =(MapResource) this.m_selectedObject;
                    if (_loc_6)
                    {
                        Global.world.citySim.roadManager.updateResource(_loc_6);
                        this.m_autoRotate = this.m_autoRotate && _loc_6.autoRotate;
                        if (this.m_autoRotate)
                        {
                            this.rotateToRoad(_loc_6);
                        }
                    }
                }
            }
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_TRANS))
            {
                this.setOccluderObjectTransparency(event);
            }
            this.updateOverlay();
            return true;
        }//end

         public boolean  onMouseDown (MouseEvent event )
        {
            super.onMouseDown(event);
            this.m_viewDragStartPos = GlobalEngine.viewport.getScrollPosition().clone();
            this.m_dragging = false;
            this.m_mouseDownFlag = true;
            this.m_textYOffset = 0;
            this.m_prevPos.x = 9999.2;
            this.m_prevPos.y = 9999.2;
            return true;
        }//end

         public boolean  onMouseUp (MouseEvent event )
        {
            if (!this.m_mouseDownFlag && this.m_selectedObject && this.m_objectStartPos)
            {
                if (!this.m_objectStartPos.equals(this.m_selectedObject.getPosition()))
                {
                    this.m_mouseDownFlag = true;
                }
                if (this.m_dropImmediately)
                {
                    this.m_mouseDownFlag = true;
                }
            }
            if (this.m_dragging == false && this.m_mouseDownFlag)
            {
                this.onMouseMove(event);
                this.handleObjectDrop(event);
            }
            this.m_dragging = false;
            this.m_viewDragStartPos = null;
            this.m_mouseDownFlag = false;
            if (!this.m_linkedObjectMode)
            {
                super.onMouseUp(event);
            }
            return true;
        }//end

        protected void  setOccluderObjectTransparency (MouseEvent event )
        {
            Point _loc_2 =new Point(Global.stage.mouseX ,Global.stage.mouseY );
            Array _loc_3 =TransparencyUtil.getObjectListFromPoint(_loc_2 );
            if (this.m_curTransObj != null)
            {
                this.m_curTransObj.showOccluder();
            }
            this.m_curTransObj = null;
            if (_loc_3.length())
            {
                this.m_curTransObj = _loc_3.get((length - 1));
                if (!TransparencyUtil.pointingToObjBase(this.m_curTransObj, IsoMath.screenPosToTilePos(_loc_2.x, _loc_2.y, true)))
                {
                    if (TransparencyUtil.isTransparencyType(this.m_curTransObj))
                    {
                        this.m_curTransObj.hideOccluder();
                    }
                }
            }
            return;
        }//end

        protected boolean  updateHighlightPoint (MouseEvent event )
        {
            this.m_highlightedPoint = getMouseTilePos(event, this.m_objectDragOffset);
            this.snapPoint(this.m_highlightedPoint);
            _loc_2 = this.isPositionValid ();
            return _loc_2;
        }//end

        protected void  resetOnPlaceCollisions ()
        {
            this.m_onPlaceFilter = Constants.WORLDOBJECT_NONE;
            this.m_onPlaceCollisions = new Array();
            return;
        }//end

        protected void  registerOnPlaceCollision (int param1 ,Function param2 ,Function param3 )
        {
            this.m_onPlaceFilter = this.m_onPlaceFilter | param1;
            this.m_onPlaceCollisions.push({arrFilter:param2, handler:param3});
            return;
        }//end

        public void  rotateToRoad (MapResource param1 )
        {
            int _loc_3 =0;
            Array _loc_2 =.get(param1.BOTTOM_SIDE ,param1.LEFT_SIDE ,param1.RIGHT_SIDE ,param1.TOP_SIDE) ;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                if (this.rotateToRoadSide(param1, _loc_3))
                {
                    break;
                }
            }
            return;
        }//end

        private boolean  rotateToRoadSide (MapResource param1 ,int param2 )
        {
            int _loc_5 =0;
            boolean _loc_3 =false ;
            _loc_4 = param1.getDirection ();
            if (param1.isRoadAdjacent(param2) && param1.sideDirectionMap.hasOwnProperty(param2))
            {
                _loc_5 =(int) param1.sideDirectionMap.get(param2);
                param1.rotateToDirection(_loc_5);
                if (this.isPositionValid())
                {
                    _loc_3 = true;
                }
                else
                {
                    param1.rotateToDirection(_loc_4);
                }
            }
            return _loc_3;
        }//end

        private boolean  isMovingPier ()
        {
            ConstructionSite _loc_1 =null ;
            if (this.m_selectedObject instanceof Pier)
            {
                return true;
            }
            if (this.m_selectedObject instanceof ConstructionSite)
            {
                _loc_1 = ConstructionSite(this.m_selectedObject);
                if (_loc_1.targetClass == Pier)
                {
                    return true;
                }
            }
            return false;
        }//end

        private boolean  isMovingBridge ()
        {
            ConstructionSite _loc_1 =null ;
            if (this.m_selectedObject instanceof Bridge)
            {
                return true;
            }
            if (this.m_selectedObject instanceof ConstructionSite)
            {
                _loc_1 = ConstructionSite(this.m_selectedObject);
                if (_loc_1.targetItem.type == "bridge")
                {
                    return true;
                }
            }
            return false;
        }//end

        private boolean  isPierPositionValid ()
        {
            Ship _loc_7 =null ;
            Pier _loc_8 =null ;
            ConstructionSite _loc_9 =null ;
            Pier _loc_10 =null ;
            _loc_1 = this.m_selectedObject.getSize ();
            String _loc_2 ="";
            double _loc_3 =8;
            Pier _loc_4 =null ;
            if (this.m_selectedObject instanceof Pier)
            {
                _loc_8 = Pier(this.m_selectedObject);
                _loc_4 = _loc_8;
                _loc_2 = _loc_8.landSquares;
                _loc_3 = _loc_8.waterLength;
            }
            if (this.m_selectedObject instanceof ConstructionSite)
            {
                _loc_9 = ConstructionSite(this.m_selectedObject);
                if (_loc_9.targetClass == Pier)
                {
                    _loc_10 = new Pier(_loc_9.targetName);
                    _loc_4 = _loc_10;
                    _loc_2 = _loc_10.landSquares;
                    _loc_3 = _loc_10.waterLength;
                }
            }
            if (this.m_highlightedPoint.x > -15 && this.m_highlightedPoint.y > -60)
            {
                return false;
            }
            _loc_5 =Global.world.citySim.waterManager.positionValidPierOnWater(new Rectangle(this.m_highlightedPoint.x ,this.m_highlightedPoint.y ,_loc_1.x ,_loc_1.y ),_loc_2 ,_loc_3 );
            if (!Global.world.citySim.waterManager.positionValidPierOnWater(new Rectangle(this.m_highlightedPoint.x, this.m_highlightedPoint.y, _loc_1.x, _loc_1.y), _loc_2, _loc_3))
            {
                return _loc_5;
            }
            _loc_6 =Global.world.getObjectsByClass(Ship );
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            	_loc_7 = _loc_6.get(i0);

                if (_loc_4.shipCollidedWithDock(new Rectangle(_loc_7.positionX, _loc_7.positionY, _loc_7.sizeX, _loc_7.sizeY)))
                {
                    return false;
                }
            }
            return _loc_5;
        }//end

        private boolean  isMovingShip ()
        {
            return this.m_selectedObject instanceof Ship;
        }//end

        private boolean  isShipPositionValid ()
        {
            Ship _loc_3 =null ;
            _loc_1 = this.m_selectedObject.getSize ();
            if (!Global.world.citySim.waterManager.positionValidShipOnWater(new Rectangle(this.m_highlightedPoint.x, this.m_highlightedPoint.y, _loc_1.x, _loc_1.y)))
            {
                return false;
            }
            this.m_collisions = new Array();
            boolean _loc_2 =true ;
            if (Global.world.checkCollision(this.m_highlightedPoint.x, this.m_highlightedPoint.y, this.m_highlightedPoint.x + _loc_1.x, this.m_highlightedPoint.y + _loc_1.y, [this.m_selectedObject], this.m_collisions, ~this.m_onPlaceFilter, this.m_selectedObject))
            {
                if (this.m_collisions.length > 0)
                {
                    _loc_2 = false;
                }
            }
            if (!_loc_2)
            {
                return false;
            }
            if (this.m_selectedObject instanceof Ship)
            {
                this.m_collisions = new Array();
                _loc_3 = Ship(this.m_selectedObject);
                return _loc_3.isValidShipPosition();
            }
            return false;
        }//end

        protected boolean  isBridgePositionValid ()
        {
            return false;
        }//end

        public boolean  isPositionValid (Point param1)
        {
            MapResource _loc_6 =null ;
            if (!param1)
            {
                param1 = this.m_highlightedPoint;
            }
            _loc_2 = this.m_selectedObject.getSize ();
            boolean _loc_3 =false ;
            if (this.isMovingShip())
            {
                return this.isShipPositionValid();
            }
            this.m_collisions = new Array();
            _loc_3 = !Global.world.checkCollision(param1.x, param1.y, param1.x + _loc_2.x, param1.y + _loc_2.y, [this.m_selectedObject], this.m_collisions, ~this.m_onPlaceFilter, this.m_selectedObject);
            if (this.isMovingBridge())
            {
                if (this.m_collisions.length == 0)
                {
                    return this.isBridgePositionValid();
                }
                return false;
            }
            _loc_4 =(Road) this.m_selectedObject;
            if (!_loc_3 && _loc_4)
            {
                _loc_4.setPosition(param1.x, param1.y);
                _loc_3 = _loc_4.isPositionValid();
                if (_loc_3)
                {
                    this.m_collisions = new Array();
                }
            }
            if (_loc_3)
            {
                _loc_3 = Global.world.rectInTerritory(new Rectangle(param1.x, param1.y, _loc_2.x, _loc_2.y));
            }
            _loc_5 =(ConstructionSite) this.m_selectedObject;
            if (this.m_selectedObject as ConstructionSite)
            {
                if (_loc_5.targetItem.placeableOnly && _loc_5.targetItem.mustOwnToPlace)
                {
                    if (this.m_collisions.length == 0)
                    {
                        _loc_3 = Global.world.rectInTerritory(new Rectangle(_loc_5.targetItem.mustOwnToPlace.get(0), _loc_5.targetItem.mustOwnToPlace.get(1), 1, 1));
                    }
                }
            }
            if (this.isMovingPier() && _loc_3)
            {
                return this.isPierPositionValid();
            }
            _loc_6 =(MapResource) this.m_selectedObject;
            if (_loc_3 && _loc_6)
            {
                _loc_3 = _loc_6.checkPlacementRequirements(param1.x, param1.y);
            }
            return _loc_3;
        }//end

        protected void  snapPoint (Point param1 )
        {
            param1.x = Math.round(param1.x);
            param1.y = Math.round(param1.y);
            if (this.m_selectedObject)
            {
                param1.x = param1.x - param1.x % this.m_selectedObject.getSnapX();
                param1.y = param1.y - param1.y % this.m_selectedObject.getSnapY();
            }
            return;
        }//end

        protected void  replaceResource (MapResource param1 )
        {
            return;
        }//end

        protected void  handleObjectDrop (MouseEvent event =null )
        {
            Vector3 _loc_2 =null ;
            Road _loc_3 =null ;
            Vector3 _loc_4 =null ;
            Vector3 _loc_5 =null ;
            Array _loc_6 =null ;
            Object _loc_7 =null ;
            Road _loc_8 =null ;
            Array _loc_9 =null ;
            boolean _loc_10 =false ;
            this.m_positionValid = event ? (this.updateHighlightPoint(event)) : (true);
            if (this.m_positionValid)
            {
                if (this.m_objectStartPos != null)
                {
                    if (!this.isPositionValid(new Point(this.m_objectStartPos.x, this.m_objectStartPos.y)))
                    {
                        if (this.m_backupStartPosition)
                        {
                            this.m_objectStartPos = this.m_backupStartPosition;
                        }
                    }
                }
                _loc_2 = this.m_objectStartPos;
                this.m_releasedPoint = new Vector2();
                if (event)
                {
                    this.m_releasedPoint.x = this.m_highlightedPoint.x;
                    this.m_releasedPoint.y = this.m_highlightedPoint.y;
                }
                else
                {
                    this.m_releasedPoint.x = this.m_objectStartPos.x;
                    this.m_releasedPoint.y = this.m_objectStartPos.y;
                    _loc_2 = this.m_selectedObject.getPosition();
                    if (this.m_selectedObject as MapResource)
                    {
                        (this.m_selectedObject as MapResource).rotateToDirection(this.m_oldDirection);
                    }
                    else
                    {
                        this.m_selectedObject.setDirection(this.m_oldDirection);
                    }
                }
                _loc_3 =(Road) this.m_selectedObject;
                if (_loc_3 != null)
                {
                    _loc_8 = _loc_3.prepareToDrop(_loc_2, this.m_releasedPoint);
                    if (_loc_8 != _loc_3)
                    {
                        this.m_selectedObject = _loc_8;
                        this.replaceResource(_loc_8);
                        if (_loc_8 == null)
                        {
                            this.m_objectPlacedSuccess = false;
                            this.onObjectDropCompleted();
                            return;
                        }
                    }
                }
                _loc_4 = this.m_selectedObject.getSize();
                _loc_5 = this.m_selectedObject.getPosition();
                this.snapPoint(this.m_releasedPoint);
                _loc_6 = new Array();
                Global.world.checkCollision(_loc_5.x, _loc_5.y, _loc_5.x + _loc_4.x, _loc_5.y + _loc_4.y, [this.m_selectedObject], _loc_6, this.m_onPlaceFilter, this.m_selectedObject);
                if (_loc_6.length == 0)
                {
                    this.completeObjectDrop();
                    return;
                }
                for(int i0 = 0; i0 < this.m_onPlaceCollisions.size(); i0++)
                {
                	_loc_7 = this.m_onPlaceCollisions.get(i0);

                    _loc_9 = _loc_6.filter(_loc_7.arrFilter);
                    _loc_7.handler(_loc_9, _loc_6.length - _loc_9.length());
                }
            }
            else if (event)
            {
                _loc_10 = Global.world.rectInTerritory(new Rectangle(this.m_highlightedPoint.x, this.m_highlightedPoint.y, this.m_selectedObject.sizeX, this.m_selectedObject.sizeY));
                if (!_loc_10)
                {
                    UI.displayStatus(ZLoc.t("Main", "BuyExpansionFirstRed"), event.stageX, event.stageY);
                    Global.ui.showTickerMessage(ZLoc.t("Main", "TickerBuyExpansion"));
                }
            }
            return;
        }//end

        protected void  objectSellCallback (GameObject param1 )
        {
            if (param1 instanceof Road && this.m_selectedObject instanceof Road)
            {
                this.completeObjectDrop();
            }
            return;
        }//end

        protected void  completeObjectDrop ()
        {
            Object _loc_3 =null ;
            LotSite _loc_4 =null ;
            _loc_1 =(MapResource) this.m_selectedObject;
            _loc_2 = _loc_1.isPlacedObjectNonBuilding ();
            this.m_selectedObject.setPosition(Math.round(this.m_releasedPoint.x), Math.round(this.m_releasedPoint.y));
            this.m_selectedObject.onObjectDropPreTansaction(this.m_objectStartPos);
            if (this.m_objectStartPos && this.m_releasedPoint)
            {
                _loc_3 = new Object();
                _loc_3.x = Math.round(this.m_releasedPoint.x);
                _loc_3.y = Math.round(this.m_releasedPoint.y);
                _loc_3.state = _loc_1.getState();
                _loc_3.direction = _loc_1.getDirection();
                _loc_3 = _loc_1.addTMoveParams(_loc_3);
                if (!this.m_linkedObjectMode)
                {
                    GameTransactionManager.addTransaction(new TMove(_loc_1, _loc_3));
                }
                if (this.m_objectStartPos.x != this.m_releasedPoint.x || this.m_objectStartPos.y != this.m_releasedPoint.y)
                {
                    _loc_1.trackDetailedAction(TrackedActionType.MOVE, "successful", "");
                }
                else
                {
                    _loc_1.trackDetailedAction(TrackedActionType.MOVE, "unsuccessful", "");
                }
            }
            this.m_selectedObject.conditionallyReattach();
            this.m_selectedObject.onObjectDrop(this.m_objectStartPos);
            this.m_objectPlacedSuccess = true;
            if (!this.m_linkedObjectMode)
            {
                if (_loc_2)
                {
                    Sounds.play("click4");
                }
                else
                {
                    Sounds.play("place_building");
                }
            }
            if (_loc_1 instanceof LotSite)
            {
                _loc_4 =(LotSite) _loc_1;
                MarketCell.reLockCells(_loc_4.getItem().name);
            }
            this.onObjectDropCompleted();
            return;
        }//end

        protected void  onObjectDropCompleted ()
        {
            if (this.m_curTransObj)
            {
                this.m_curTransObj.showOccluder();
                this.m_curTransObj = null;
            }
            if (this.m_linkedObjectMode)
            {
                return;
            }
            if (this.m_exitAfterDrop)
            {
                Global.world.addGameMode(new GMPlay(), true);
            }
            else
            {
                Global.world.popGameMode();
            }
            return;
        }//end

        public void  drawArea (Graphics param1 ,double param2 ,double param3 )
        {
            _loc_4 = IsoMath.getPixelDeltaFromTileDelta(param2 ,0);
            _loc_5 = IsoMath.getPixelDeltaFromTileDelta(0,param3 );
            param1.moveTo(0, 0);
            param1.lineTo(0 + _loc_4.x, 0 + _loc_4.y);
            param1.lineTo(0 + _loc_4.x + _loc_5.x, 0 + _loc_4.y + _loc_5.y);
            param1.lineTo(0 + _loc_5.x, 0 + _loc_5.y);
            param1.lineTo(0, 0);
            return;
        }//end

        protected void  updateOverlay ()
        {
            Vector3 _loc_1 =null ;
            int _loc_2 =0;
            GuideTile _loc_3 =null ;
            Point _loc_4 =null ;
            Vector3 _loc_5 =null ;
            MapResource _loc_6 =null ;
            m_displayObject.graphics.clear();
            if (this.m_highlightedPoint)
            {
                _loc_1 = this.m_selectedObject.getSize();
                _loc_2 = this.m_positionValid ? (Constants.COLOR_VALID_PLACEMENT) : (Constants.COLOR_NOT_VALID_PLACEMENT);
                if (this.m_positionValid && this.m_selectedObject instanceof MapResource && (this.m_selectedObject as MapResource).isNeedingRoad)
                {
                    _loc_2 = COLOR_NO_ROAD_CONNECT;
                }
                if (Global.guide.isActive())
                {
                    for(int i0 = 0; i0 < Global.guide.guideTiles.size(); i0++)
                    {
                    	_loc_3 = Global.guide.guideTiles.get(i0);

                        if (_loc_3.origin.equals(this.m_selectedObject.getPosition()))
                        {
                            _loc_2 = Constants.COLOR_VALID_PLACEMENT;
                            continue;
                        }
                        _loc_2 = Constants.COLOR_NOT_VALID_PLACEMENT;
                    }
                }
                m_displayObject.graphics.lineStyle(TILE_OUTLINE_WIDTH, _loc_2, Constants.ALPHA_VALID_PLACEMENT);
                drawTileArea(m_displayObject.graphics, this.m_highlightedPoint, _loc_1.x, _loc_1.y);
                if (!this.m_positionValid)
                {
                    _loc_4 = new Point();
                    m_displayObject.graphics.lineStyle(TILE_OUTLINE_WIDTH, Constants.COLOR_NOT_VALID_PLACEMENT, Constants.ALPHA_VALID_PLACEMENT);
                    for(int i0 = 0; i0 < this.m_collisions.size(); i0++)
                    {
                    	_loc_6 = this.m_collisions.get(i0);

                        _loc_5 = _loc_6.getPosition();
                        _loc_4.x = _loc_5.x;
                        _loc_4.y = _loc_5.y;
                        drawTileArea(m_displayObject.graphics, _loc_4, _loc_6.sizeX, _loc_6.sizeY);
                    }
                }
            }
            this.updateBonuses();
            return;
        }//end

         public void  disableMode ()
        {
            GMObjMoveHelper _loc_1 =null ;
            MapResource _loc_2 =null ;
            if (this.m_selectedLocked)
            {
                this.m_selectedObject.unlock();
                this.m_selectedLocked = false;
            }
            if (this.m_objectPlacedSuccess == false && this.m_objectStoredSuccess == false && this.m_selectedObject && this.m_selectedObject.getOuter() != null)
            {
                if (this.m_objectStartPos == null)
                {
                    this.m_selectedObject.detach();
                    this.m_selectedObject.cleanup();
                }
                else
                {
                    this.handleObjectDrop();
                }
            }
            if (this.m_displayBonusObject)
            {
                this.fadeThenRemove(this.m_displayBonusObject);
            }
            if (this.m_preview)
            {
                this.fadeThenRemove(this.m_preview);
                this.m_preview = null;
            }
            if (this.m_positionFeedback)
            {
                this.fadeThenRemove(this.m_positionFeedback);
                this.m_positionFeedback = null;
            }
            this.endAreasOfEffect();
            for(int i0 = 0; i0 < this.m_moveHelperList.size(); i0++)
            {
            	_loc_1 = this.m_moveHelperList.get(i0);

                _loc_1.deactivate();
            }
            this.m_moveHelperList.length = 0;
            GMObjMoveHelper.deactivateAllPending();
            for(int i0 = 0; i0 < this.m_keywordGlowObjects.size(); i0++)
            {
            	_loc_2 = this.m_keywordGlowObjects.get(i0);

                _loc_2.disableGlow();
            }
            super.disableMode();
            return;
        }//end

        public void  setLinkedObjectMode (boolean param1 )
        {
            this.m_linkedObjectMode = param1;
            return;
        }//end

        public static String  getCoinLabel (MapResource param1 )
        {
            _loc_2 = param1.getTypeName ();
            if (_loc_2 == "residence")
            {
                return ZLoc.t("Main", "Rent");
            }
            if (_loc_2 == "business")
            {
                return ZLoc.t("Main", "Meals");
            }
            return "";
        }//end

        public static double  getSmallBonusTextSize ()
        {
            _loc_1 = GMObjectMove.BONUS_TEXT_SIZE/GlobalEngine.viewport.getZoom();
            return _loc_1;
        }//end

        public static double  getLargeBonusTextSize ()
        {
            _loc_1 = getSmallBonusTextSize()*BONUS_TEXT_INCREASE;
            return _loc_1;
        }//end

        public static Point  getPixelCenter (ItemInstance param1 )
        {
            return IsoMath.tilePosToPixelPos(param1.positionX + param1.sizeX / 2, param1.positionY + param1.sizeY / 2);
        }//end

        public static int  bonusColor (MapResource param1 )
        {
            _loc_2 = ItemBonus.getBonusModifier(param1,ItemBonus.ALL);
            if (_loc_2 > 0)
            {
                return Constants.COLOR_HIGHLIGHT_BLUE;
            }
            if (_loc_2 < 0)
            {
                return Constants.COLOR_NOT_VALID_PLACEMENT;
            }
            return 4294967295;
        }//end

    }



