package Classes.effects;

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
import Engine.Classes.*;
//import flash.display.*;
//import flash.geom.*;

    public class MasteryStagePickEffect extends SlowStagePickEffect
    {
        protected Sprite m_masteryBar ;
        public static  String PICK_MASTERY ="guitarpicMastery";
        public static  String MASTERY_INDICATOR_ON ="masteryStarIndicator";
        public static  int MAX_MASTERY_LEVEL =4;

        public  MasteryStagePickEffect (MapResource param1 )
        {
            super(param1);
            return;
        }//end

         public void  cleanUp ()
        {
            super.cleanUp();
            if (this.m_masteryBar)
            {
                removeFromParent(this.m_masteryBar);
                this.m_masteryBar = null;
            }
            return;
        }//end

         protected void  fadeCallback ()
        {
            m_mapResource.setShowUpgradeArrow(false);
            super.fadeCallback();
            return;
        }//end

         protected void  setPickImage (String param1 ,String param2 )
        {
            IsoRect _loc_7 =null ;
            Point _loc_8 =null ;
            double _loc_9 =0;
            int _loc_10 =0;
            double _loc_11 =0;
            Sprite _loc_12 =null ;
            Bitmap _loc_13 =null ;
            Bitmap _loc_14 =null ;
            removeFromParent(m_currentPickImage);
            removeFromParent(m_currentModifierImage);
            removeFromParent(m_guitarPickAnimation);
            removeFromParent(m_ministarImage);
            removeFromParent(this.m_masteryBar);
            _loc_3 = m_mapResource(.canCountUpgradeActions()|| m_mapResource.canShowAlternativeUpgradeToolTip()) && m_mapResource.canShowUpgradeToolTips();
            _loc_4 = _loc_3? (PICK_MASTERY) : (PICK_SHINE);
            int _loc_5 =-1;
            _loc_6 = m_mapResource.getItem();
            if (_loc_3)
            {
                _loc_5 = _loc_6.level;
            }
            if (param1 !=null)
            {
                m_guitarPickAnimation =(AnimatedBitmap) getImage(_loc_4);
                m_guitarPickAnimation.x = (-m_guitarPickAnimation.width >> 1) + 1;
                if (param1 == PICK_4)
                {
                    m_guitarPickAnimation.x = (-m_guitarPickAnimation.width >> 1) + 1.5;
                }
                m_guitarPickAnimation.y = (-m_guitarPickAnimation.height >> 1) + 3;
                m_currentPickImage =(DisplayObject) getImage(param1);
                m_currentPickImage.x = (-m_currentPickImage.width) * 0.5;
                m_currentPickImage.y = (-m_currentPickImage.height) * 0.5 + (_loc_3 ? (1.5) : (0));
                m_currentModifierImage = param2 != null ? ((DisplayObject)getImage(param2)) : (new Sprite());
                m_currentModifierImage.x = (-m_currentModifierImage.width) * 0.5;
                m_currentModifierImage.y = (-m_currentModifierImage.height) * 1.5;
                m_ministarImage =(DisplayObject) getImage(MINI_STAR);
                m_ministarImage.x = m_currentPickImage.x + m_currentPickImage.width * 0.6;
                m_ministarImage.y = m_currentPickImage.y + m_currentPickImage.height * 0.1;
                if (!m_showMinistar)
                {
                    m_ministarImage.visible = false;
                }
                _loc_7 = IsoRect.getIsoRectFromSize(m_mapResource.getReference().getSize());
                m_effectX = _loc_7.bottom.x;
                m_effectY = _loc_7.top.y + m_guitarPickAnimation.height / 2;
                m_guitarPickAnimation.stop();
                this.m_masteryBar = new Sprite();
                if (_loc_3)
                {
                    _loc_10 = 0;
                    _loc_11 = 1;
                    while (_loc_10 < _loc_5)
                    {

                        _loc_12 = new Sprite();
                        _loc_13 =(Bitmap) getImage(MASTERY_INDICATOR_ON);
                        _loc_14 = new Bitmap(_loc_13.bitmapData);
                        _loc_12.addChild(_loc_14);
                        _loc_12.x = _loc_11 + _loc_10 * (_loc_12.width - 1);
                        this.m_masteryBar.addChild(_loc_12);
                        _loc_10++;
                    }
                }
                this.m_masteryBar.x = (-this.m_masteryBar.width) * 0.5;
                this.m_masteryBar.y = m_guitarPickAnimation.y;
                this.addChild(m_guitarPickAnimation);
                this.addChild(m_currentPickImage);
                this.addChild(m_currentModifierImage);
                this.addChild(m_ministarImage);
                this.addChild(this.m_masteryBar);
                if (m_mapResource && m_mapResource.getItem() && m_mapResource.getItem().pickOffset)
                {
                    _loc_8 = m_mapResource.getItem().pickOffset;
                }
                else
                {
                    _loc_8 = new Point(0, 0);
                }
                _loc_9 = PICK_Y_OFFSET;
                this.y = m_effectY + _loc_9 - PICK_FLOAT_DISTANCE + _loc_8.y;
                this.x = m_effectX + _loc_8.x;
            }
            if (m_floatActive)
            {
                float();
            }
            else if (m_queuedActive)
            {
                queuedFloat();
            }
            return;
        }//end

    }



