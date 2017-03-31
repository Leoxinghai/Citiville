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
import Display.*;
import Engine.*;
import Engine.Helpers.*;
import Engine.Managers.*;
import com.greensock.*;
import com.zynga.skelly.util.*;
//import flash.display.*;
//import flash.geom.*;
//import flash.text.*;

    public class GMObjMoveHelper extends Sprite
    {
        private boolean m_active ;
        private MapResource m_gameObject ;
        private MapResource m_movedObject ;
        private Sprite m_displayObject ;
        private TextField m_textField1 ;
        private TextField m_textField2 ;
        private int m_color ;
        private ItemBonus m_bonus ;
        private int m_fullBonus =0;
        private double m_curBonus =0;
        private Point m_origin ;
        private TextFormat m_format ;
        private static Array m_waitingForDeactivate =new Array ();

        public  GMObjMoveHelper (MapResource param1 ,Point param2 ,TextFormat param3 ,int param4 ,MapResource param5 )
        {
            this.m_displayObject = new Sprite();
            this.m_movedObject = param5;
            deactivatePending(param1);
            this.m_gameObject = param1;
            this.m_color = param4;
            this.m_format = param3;
            this.m_origin = param2;
            this.m_bonus = null;
            this.m_curBonus = 0;
            this.m_fullBonus = 0;
            return;
        }//end

        public boolean  active ()
        {
            return this.m_active;
        }//end

        public void  active (boolean param1 )
        {
            this.m_active = param1;
            return;
        }//end

        public MapResource  gameObject ()
        {
            return this.m_gameObject;
        }//end

        public void  applyBonus (ItemBonus param1 )
        {
            int _loc_2 =0;
            double _loc_3 =0;
            if (this.m_bonus == null)
            {
                this.m_bonus = param1;
                this.m_curBonus = 0;
                _loc_3 = ItemBonus.getBonusModifier(this.m_gameObject, this.m_bonus.field);
                this.m_fullBonus = Math.round((_loc_3 - 1) * 100);
            }
            else if (this.m_bonus.field != param1.field)
            {
                ErrorManager.addError("GMObjMoveHelper: Bonus fields are different.  Must match.");
                return;
            }
            if (this.m_gameObject instanceof Business && param1.name == "FranchiseAOE")
            {
                _loc_2 = ItemBonus.calcIndividualHQBonus(this.m_gameObject as Business, this.m_movedObject);
            }
            else
            {
                _loc_2 = param1.getPercentModifier(this.m_movedObject);
            }
            this.m_curBonus = this.m_curBonus + _loc_2;
            return;
        }//end

        private void  createText ()
        {
            String _loc_4 =null ;
            _loc_1 = GMObjectMove.getSmallBonusTextSize();
            this.m_textField1 = new StrokeTextField(0, 0.4);
            this.m_textField2 = new StrokeTextField(0, 0.4);
            _loc_2 = this.m_textField1 ;
            _loc_2.text = ZLoc.t("Main", "Bonus");
            _loc_2.border = false;
            _loc_2.embedFonts = EmbeddedArt.isEmbedFont(this.m_format.font);
            _loc_2.autoSize = TextFieldAutoSize.LEFT;
            this.m_format.size = _loc_1;
            _loc_2.setTextFormat(this.m_format);
            _loc_3 = _loc_2.getLineMetrics(0);
            _loc_2.x = this.m_origin.x - _loc_3.width / 2;
            _loc_2.y = this.m_origin.y - _loc_3.height * 3 / 2;
            addChild(_loc_2);
            this.m_curBonus = Math.round(this.m_curBonus);
            _loc_2 = this.m_textField2;
            _loc_4 = (this.m_curBonus > 0 ? ("+") : ("")) + this.m_curBonus + "%";
            if (this.m_fullBonus != this.m_curBonus)
            {
                _loc_4 = _loc_4 + (" (" + this.m_fullBonus + "%)");
            }
            _loc_2.text = _loc_4;
            _loc_2.border = false;
            _loc_2.embedFonts = EmbeddedArt.isEmbedFont(this.m_format.font);
            _loc_2.autoSize = TextFieldAutoSize.LEFT;
            this.m_format.size = _loc_1;
            _loc_2.setTextFormat(this.m_format);
            _loc_3 = _loc_2.getLineMetrics(0);
            _loc_2.x = this.m_origin.x - _loc_3.width / 2;
            _loc_2.y = this.m_origin.y - _loc_3.height / 2;
            _loc_3 = _loc_2.getLineMetrics(0);
            addChild(_loc_2);
            GlobalEngine.viewport.overlayBase.addChild(this.m_displayObject);
            Point _loc_5 =new Point(this.m_gameObject.positionX ,this.m_gameObject.positionY );
            this.m_displayObject.graphics.clear();
            this.m_displayObject.graphics.lineStyle(3, this.m_color, Constants.ALPHA_VALID_PLACEMENT);
            this.m_displayObject.graphics.beginFill(0, 0);
            this.drawTileArea(this.m_displayObject.graphics, _loc_5, this.m_gameObject.sizeX, this.m_gameObject.sizeY);
            this.m_displayObject.graphics.endFill();
            alpha = 0;
            return;
        }//end

        public void  activate ()
        {
            if (this.m_textField1 == null)
            {
                this.createText();
                GlobalEngine.viewport.uiBase.addChildAt(this, 0);
                TweenLite.to(this, GMObjectMove.BONUS_FADE_IN, {alpha:1});
                if (!this.m_gameObject.glowActive)
                {
                    this.m_gameObject.setHighlightedSpecial(true, Constants.COLOR_HIGHLIGHT_BLUE, 4);
                }
            }
            return;
        }//end

        private void  removeFromParent ()
        {
            _loc_1 = m_waitingForDeactivate.indexOf(this);
            if (_loc_1 >= 0)
            {
                m_waitingForDeactivate.splice(_loc_1, 1);
            }
            if (parent != null)
            {
                parent.removeChild(this);
            }
            return;
        }//end

        private void  showYieldAndDeactivate2 ()
        {
            TweenLite.to(this, GMObjectMove.BONUS_FADE_OUT, {alpha:0, delay:0.5, onComplete:this.removeFromParent});
            return;
        }//end

        public void  showYieldAndDeactivate (TextFormat param1 )
        {
            textFmt = param1;
            cont = Curry.curry(function(param11Sprite)
            {
                if (param11.parent != null)
                {
                    param11.parent.removeChild(param11);
                }
                return;
            }//end
            , this);
            this.m_gameObject.setHighlighted(false);
            if (this.m_displayObject)
            {
                GlobalEngine.viewport.overlayBase.removeChild(this.m_displayObject);
                this.m_displayObject = null;
            }
            TweenLite.to(this, GMObjectMove.BONUS_FADE_OUT, {x:this.x, immediateRender:false, onComplete:this.showYieldAndDeactivate2});
            m_waitingForDeactivate.push(this);
            return;
        }//end

        public void  deactivate ()
        {
            DisplayObjectContainer _loc_2 =null ;
            this.m_gameObject.setHighlighted(false);
            if (this.m_displayObject)
            {
                GlobalEngine.viewport.overlayBase.removeChild(this.m_displayObject);
                this.m_displayObject = null;
            }
            _loc_1 = m_waitingForDeactivate.indexOf(this);
            if (_loc_1 >= 0)
            {
                m_waitingForDeactivate.splice(_loc_1, 1);
            }
            if (parent != null)
            {
                _loc_2 = parent;
                _loc_2.removeChild(this);
                _loc_2.addChildAt(this, 0);
            }
            TweenLite.to(this, GMObjectMove.BONUS_FADE_OUT, {alpha:0, onComplete:this.removeFromParent});
            return;
        }//end

        public void  drawTileArea (Graphics param1 ,Point param2 ,double param3 ,double param4 )
        {
            _loc_5 = IsoMath.tilePosToPixelPos(param2.x ,param2.y );
            _loc_6 = IsoMath.getPixelDeltaFromTileDelta(param3 ,0);
            _loc_7 = IsoMath.getPixelDeltaFromTileDelta(0,param4 );
            param1.moveTo(_loc_5.x, _loc_5.y);
            param1.lineTo(_loc_5.x + _loc_6.x, _loc_5.y + _loc_6.y);
            param1.lineTo(_loc_5.x + _loc_6.x + _loc_7.x, _loc_5.y + _loc_6.y + _loc_7.y);
            param1.lineTo(_loc_5.x + _loc_7.x, _loc_5.y + _loc_7.y);
            param1.lineTo(_loc_5.x, _loc_5.y);
            return;
        }//end

        public static void  deactivatePending (MapResource param1 )
        {
            GMObjMoveHelper _loc_2 =null ;
            for(int i0 = 0; i0 < m_waitingForDeactivate.size(); i0++)
            {
            	_loc_2 = m_waitingForDeactivate.get(i0);

                if (_loc_2.m_gameObject == param1)
                {
                    _loc_2.deactivate();
                }
            }
            return;
        }//end

        public static void  deactivateAllPending ()
        {
            GMObjMoveHelper _loc_1 =null ;
            for(int i0 = 0; i0 < m_waitingForDeactivate.size(); i0++)
            {
            	_loc_1 = m_waitingForDeactivate.get(i0);

                _loc_1.deactivate();
            }
            m_waitingForDeactivate.length = 0;
            return;
        }//end

    }



