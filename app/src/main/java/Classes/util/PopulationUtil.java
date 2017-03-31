package Classes.util;

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
import Classes.sim.*;
import Display.PopulationUI.*;

    public class PopulationUtil
    {

        public  PopulationUtil ()
        {
            throw new Error("Cannot instantiate class " + Global.getClassName(this));
        }//end

        public static int  capPopulation (int param1 ,int param2 ,int param3 )
        {
            if (param1 > param3)
            {
                param1 = param3;
            }
            if (param1 < param2)
            {
                param1 = param2;
            }
            return param1;
        }//end

        public static int  getPopulationAfterReplace (Item param1 ,MapResource param2 )
        {
            Item _loc_4 =null ;
            _loc_3 = param1.populationBase ;
            if (param2 && param2.getItem())
            {
                _loc_4 = param2.getItem();
                _loc_3 = capPopulation(param2.getPopulationYield(), param1.populationBase, param1.populationMax);
            }
            return _loc_3;
        }//end

        public static boolean  canAddItemToWorld (Item param1 ,boolean param2 ,MapResource param3 =null )
        {
            String _loc_7 =null ;
            int _loc_8 =0;
            int _loc_9 =0;
            Item _loc_10 =null ;
            Object _loc_11 =null ;
            int _loc_12 =0;
            _loc_4 = getPopulationAfterReplace(param1,param3);
            int _loc_5 =0;
            boolean _loc_6 =true ;
            if (_loc_4)
            {
                if (param3)
                {
                    _loc_10 = param3.getItem();
                    if (_loc_10)
                    {
                        _loc_5 = param3.getPopulationYield();
                    }
                }
                _loc_7 = param1.populationType;
                _loc_8 = Global.world.citySim.getPopulation(_loc_7) - _loc_5;
                _loc_9 = _loc_8 + _loc_4;
                if (_loc_9 > Global.world.citySim.getPopulationCap(_loc_7))
                {
                    if (param2)
                    {
                        _loc_11 = {capType:_loc_7, capNeeded:Global.world.citySim.getRequiredNonTotalPopulationCap(param1)};
                        ResourceExplanationDialog.show(ResourceExplanationDialog.TYPE_NOT_ENOUGH_HAPPINESS_RESIDENCE, false, null, _loc_11);
                    }
                    _loc_6 = false;
                }
            }
            else if (param1.type == "business")
            {
                _loc_12 = Math.floor(Global.world.citySim.getPopulationCap() * Global.gameSettings().getNumber("businessLimitByPopulationMax"));
                if (Global.world.citySim.getTotalBusinesses() > _loc_12)
                {
                    if (param2)
                    {
                        _loc_11 = {capType:Population.MIXED, capNeeded:Global.world.citySim.getRequiredPopulationCap(param1)};
                        ResourceExplanationDialog.show(ResourceExplanationDialog.TYPE_NOT_ENOUGH_HAPPINESS_BUSINESS, false, null, _loc_11);
                    }
                    _loc_6 = false;
                }
            }
            return _loc_6;
        }//end

    }


