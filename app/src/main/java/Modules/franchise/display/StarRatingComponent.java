package Modules.franchise.display;

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
import Display.aswingui.*;
//import flash.display.*;
//import flash.utils.*;
import org.aswing.*;

    public class StarRatingComponent extends JPanel
    {
        private double m_rating ;
        private double m_maxRating ;
        private String m_starType ;
        private boolean m_useCustomStars =false ;
        private Dictionary m_assetDict ;
        public static  double NONE =-2;
        public static  double DISABLED =-1;
        private static  int DEFAULT_MAX_RATING =5;

        public  StarRatingComponent (double param1 ,boolean param2 =false ,String param3 =null ,int param4 =5,int param5 =-5,String param6 ="fr_star")
        {
            param5 = param2 ? (param5) : (0);
            super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, param5, SoftBoxLayout.LEFT));
            this.m_rating = param1;
            this.m_maxRating = param4;
            this.m_useCustomStars = param2;
            this.m_starType = param6;
            this.m_assetDict = null;
            if (param3 && param2)
            {
                Global.delayedAssets.get(param3, this.onAssetsLoaded);
            }
            else
            {
                this.draw(param1);
            }
            return;
        }//end

        protected void  onAssetsLoaded (DisplayObject param1 ,String param2 )
        {
            this.m_assetDict = new Dictionary();
            _loc_3 = param1as Object ;
            this.m_assetDict.put("fr_star_empty",  _loc_3.get(this.m_starType + "_empty"));
            this.m_assetDict.put("fr_star_full",  _loc_3.get(this.m_starType + "_full"));
            this.m_assetDict.put("fr_star_disabled",  _loc_3.get(this.m_starType + "_disabled"));
            this.draw(this.m_rating);
            return;
        }//end

        public void  setStarRating (double param1 )
        {
            if (param1 != this.m_rating)
            {
                this.m_rating = param1;
                this.draw(param1);
            }
            return;
        }//end

        public void  setMaxRating (double param1 )
        {
            if (param1 != this.m_maxRating)
            {
                this.m_maxRating = param1;
                this.draw(this.m_rating);
            }
            return;
        }//end

        private void  draw (double param1 )
        {
            Component _loc_3 =null ;
            removeAll();
            int _loc_2 =0;
            while (_loc_2 < this.m_maxRating)
            {

                _loc_3 = null;
                if (param1 == NONE)
                {
                    _loc_3 = this.createNoStarComponent();
                }
                else if (param1 == DISABLED)
                {
                    _loc_3 = this.createDisabledStarComponent();
                }
                else
                {
                    _loc_3 = this.createStarComponent(Math.min(param1 - _loc_2, 1));
                }
                if (_loc_3)
                {
                    append(_loc_3);
                }
                _loc_2++;
            }
            ASwingHelper.prepare(this);
            return;
        }//end

        private Component  createStarComponent (double param1 )
        {
            DisplayObject _loc_2 =null ;
            if (param1 <= 0)
            {
                _loc_2 = this.m_useCustomStars ? (new this.assetDict.get("fr_star_empty")) : (new EmbeddedArt.fr_tooltip_star_empty());
                _loc_2.alpha = 0.5;
            }
            else if (param1 >= 1)
            {
                _loc_2 = this.m_useCustomStars ? (new this.assetDict.get("fr_star_full")) : (new EmbeddedArt.fr_tooltip_star_full());
            }
            else
            {
                _loc_2 = this.m_useCustomStars ? (new this.assetDict.get("fr_star_full")) : (new EmbeddedArt.fr_tooltip_star_full());
            }
            AssetPane _loc_3 =new AssetPane(_loc_2 );
            ASwingHelper.prepare(_loc_3);
            return _loc_3;
        }//end

        private Component  createDisabledStarComponent ()
        {
            _loc_1 = this.m_useCustomStars ? (new this.assetDict.get("fr_star_disabled")) : (new EmbeddedArt.fr_tooltip_star_empty());
            _loc_1.alpha = 0.5;
            AssetPane _loc_2 =new AssetPane(_loc_1 );
            ASwingHelper.prepare(_loc_2);
            return _loc_2;
        }//end

        private Dictionary  assetDict ()
        {
            return this.m_assetDict ? (this.m_assetDict) : (FranchiseMenuUI.m_assetDict);
        }//end

        private Component  createNoStarComponent ()
        {
            return null;
        }//end

    }



