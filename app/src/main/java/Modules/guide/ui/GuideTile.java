package Modules.guide.ui;

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

import Engine.*;
import Engine.Helpers.*;
import com.greensock.*;
//import flash.display.*;
//import flash.geom.*;

    public class GuideTile
    {
        protected double m_width ;
        protected double m_height ;
        protected Vector3 m_position ;
        protected double m_id ;
        protected boolean m_visible ;
        protected Sprite m_tileSprite ;
        private TimelineLite m_animTimeline ;

        public  GuideTile (Vector3 param1 ,double param2 ,double param3 )
        {
            this.m_tileSprite = new Sprite();
            this.m_position = param1;
            this.m_width = param2;
            this.m_height = param3;
            GlobalEngine.viewport.overlayBase.addChild(this.m_tileSprite);
            return;
        }//end

        public void  cleanup ()
        {
            GlobalEngine.viewport.overlayBase.removeChild(this.m_tileSprite);
            this.m_tileSprite = null;
            if (this.m_animTimeline)
            {
                this.m_animTimeline.kill();
            }
            return;
        }//end

        public void  drawGuideTile (int param1 )
        {
            this.m_tileSprite.graphics.clear();
            this.m_tileSprite.graphics.lineStyle(2, param1);
            this.m_tileSprite.graphics.beginFill(0, 0);
            this.drawTileArea(this.m_tileSprite.graphics, this.m_position, this.m_width, this.m_height);
            this.m_tileSprite.graphics.endFill();
            return;
        }//end

        public Vector3  origin ()
        {
            return this.m_position;
        }//end

        public void  drawTileArea (Graphics param1 ,Vector3 param2 ,double param3 ,double param4 )
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

        public void  blinkTile (double param1 ,int param2 ,Function param3 =null )
        {
            this.m_animTimeline = new TimelineLite({onComplete:this.blinkComplete, onCompleteParams:.get(param3)});
            this.m_animTimeline.append(new TweenLite(this.m_tileSprite, param1, {alpha:0}));
            int _loc_4 =0;
            while (_loc_4 < param2)
            {

                this.m_animTimeline.appendMultiple(.get(new TweenLite(this.m_tileSprite, param1, {alpha:1}), new TweenLite(this.m_tileSprite, param1, {alpha:0})), 0, TweenAlign.SEQUENCE);
                _loc_4++;
            }
            return;
        }//end

        private void  blinkComplete (Function param1)
        {
            this.cleanup();
            if (param1 != null)
            {
                param1();
            }
            return;
        }//end

    }



