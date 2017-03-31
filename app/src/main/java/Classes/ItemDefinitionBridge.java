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

import Engine.Helpers.*;
    public class ItemDefinitionBridge
    {
        protected String m_leftPart ;
        protected String m_centerPart ;
        protected String m_rightPart ;
        protected String m_expansionFeedback ;
        protected String m_validBuildExpansions ;
        protected String m_grantedExpansionsOnPlace ;
        protected String m_grantedExpansionType ;
        protected String m_grantedExpansionsOnFinish ;
        protected int m_minX ;
        protected int m_maxX ;
        protected Vector3 m_bridgeBoundary ;
        protected Vector3 m_targetSquare ;
        protected Array m_bridgeparts ;
        protected int m_isPixelInsideAlphaThresholdOveride =128;
        protected double m_ghostAlpha =1;
        protected double m_ghostScale =1;
        protected int m_ghostXOffset =0;
        protected int m_ghostYOffset =0;

        public  ItemDefinitionBridge ()
        {
            return;
        }//end

        public void  init (XML param1 )
        {
            Array _loc_2 =null ;
            Array _loc_3 =null ;
            XML _loc_4 =null ;
            Object _loc_5 =null ;
            this.m_leftPart = param1.leftPart.length() > 0 ? (String(param1.leftPart)) : (null);
            this.m_centerPart = param1.centerPart.length() > 0 ? (String(param1.centerPart)) : (null);
            this.m_rightPart = param1.rightPart.length() > 0 ? (String(param1.rightPart)) : (null);
            this.m_expansionFeedback = param1.expansionFeedback.length() > 0 ? (String(param1.expansionFeedback)) : (null);
            this.m_validBuildExpansions = param1.validbuildexpansions.length() > 0 ? (String(param1.validbuildexpansions)) : (null);
            this.m_grantedExpansionsOnPlace = param1.grantedExpansionsOnPlace.length() > 0 ? (String(param1.grantedExpansionsOnPlace)) : (null);
            this.m_grantedExpansionType = param1.grantedExpansionType.length() > 0 ? (String(param1.grantedExpansionType)) : (null);
            this.m_grantedExpansionsOnFinish = param1.grantedExpansionsOnFinish.length() > 0 ? (String(param1.grantedExpansionsOnFinish)) : (null);
            this.m_minX = param1.minX;
            this.m_maxX = param1.maxX;
            this.m_bridgeBoundary = new Vector3(0, 0, 0);
            if (param1.bridgeBoundary.length() > 0)
            {
                _loc_2 = param1.bridgeBoundary.split("|");
                if (_loc_2.length == 2)
                {
                    this.m_bridgeBoundary.x = int(_loc_2.get(0));
                    this.m_bridgeBoundary.y = int(_loc_2.get(1));
                }
            }
            this.m_targetSquare = new Vector3(0, 0, 0);
            if (param1.targetSquare.length() > 0)
            {
                _loc_3 = param1.targetSquare.split("|");
                if (_loc_3.length == 2)
                {
                    this.m_targetSquare.x = int(_loc_3.get(0));
                    this.m_targetSquare.y = int(_loc_3.get(1));
                }
            }
            this.m_bridgeparts = new Array();
            if (param1.hasOwnProperty("bridgeparts"))
            {
                for(int i0 = 0; i0 < param1.bridgeparts.part.size(); i0++) 
                {
                	_loc_4 = param1.bridgeparts.part.get(i0);

                    _loc_5 = {type:String(_loc_4.@type), x:int(_loc_4.@x), y:int(_loc_4.@y)};
                    this.m_bridgeparts.push(_loc_5);
                }
            }
            this.m_ghostAlpha = 1;
            if (param1.ghostAlpha.length() > 0)
            {
                this.m_ghostAlpha = Number(Number(param1.ghostAlpha) / 100);
            }
            if (this.m_ghostAlpha < 0 || this.m_ghostAlpha > 1)
            {
                this.m_ghostAlpha = 1;
            }
            this.m_ghostScale = 1;
            if (param1.ghostScale.length() > 0)
            {
                this.m_ghostScale = Number(Number(param1.ghostScale) / 100);
            }
            this.m_ghostXOffset = int(param1.ghostXOffset);
            this.m_ghostYOffset = int(param1.ghostYOffset);
            return;
        }//end

        public String  leftPart ()
        {
            return this.m_leftPart;
        }//end

        public String  centerPart ()
        {
            return this.m_centerPart;
        }//end

        public String  rightPart ()
        {
            return this.m_rightPart;
        }//end

        public String  expansionFeedback ()
        {
            return this.m_expansionFeedback;
        }//end

        public String  validBuildExpansions ()
        {
            return this.m_validBuildExpansions;
        }//end

        public String  grantedExpansionsOnPlace ()
        {
            return this.m_grantedExpansionsOnPlace;
        }//end

        public String  grantedExpansionType ()
        {
            return this.m_grantedExpansionType;
        }//end

        public String  grantedExpansionsOnFinish ()
        {
            return this.m_grantedExpansionsOnFinish;
        }//end

        public int  minX ()
        {
            return this.m_minX;
        }//end

        public int  maxX ()
        {
            return this.m_maxX;
        }//end

        public Vector3  bridgeBoundary ()
        {
            return this.m_bridgeBoundary;
        }//end

        public Vector3  targetSquare ()
        {
            return this.m_targetSquare;
        }//end

        public Array  bridgeParts ()
        {
            return this.m_bridgeparts;
        }//end

        public double  ghostAlpha ()
        {
            return this.m_ghostAlpha;
        }//end

        public double  ghostScale ()
        {
            return this.m_ghostScale;
        }//end

        public int  ghostXOffset ()
        {
            return this.m_ghostXOffset;
        }//end

        public int  ghostYOffset ()
        {
            return this.m_ghostYOffset;
        }//end

    }



