package Transactions;

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
import Engine.Managers.*;

    public class TMove extends TWorldState
    {
        protected Object m_params ;
        protected double m_coinBonus ;
        protected double m_xpBonus ;
        protected int m_numBonus ;

        public  TMove (GameObject param1 ,Object param2 )
        {
            Array _loc_3 =null ;
            Array _loc_4 =null ;
            int _loc_5 =0;
            ItemBonus _loc_6 =null ;
            ItemInstance _loc_7 =null ;
            this.m_params = param2;
            super(param1);
            this.m_numBonus = 0;
            this.m_coinBonus = 0;
            this.m_xpBonus = 0;
            if (param1 instanceof MapResource)
            {
                _loc_3 = new Array();
                _loc_4 = ItemBonus.getBonuses(MapResource(param1), _loc_3);
                _loc_5 = 0;
                while (_loc_5 < _loc_4.length())
                {

                    _loc_6 = _loc_4.get(_loc_5);
                    _loc_7 = _loc_3.get(_loc_5);
                    if (_loc_6.field == ItemBonus.COIN_YIELD)
                    {
                        this.m_numBonus++;
                        this.m_coinBonus = this.m_coinBonus + _loc_6.getPercentModifier(_loc_7);
                    }
                    else if (_loc_6.field == ItemBonus.XP_YIELD)
                    {
                        this.m_numBonus++;
                        this.m_xpBonus = this.m_xpBonus + _loc_6.getPercentModifier(_loc_7);
                    }
                    _loc_5++;
                }
            }
            return;
        }//end

         public void  perform ()
        {
            signedWorldAction("move", this.m_params);
            return;
        }//end

         protected void  onWorldActionComplete (Object param1 )
        {
            if (m_object is MapResource && param1.numBonus != null)
            {
                if (this.m_numBonus != param1.numBonus)
                {
                    ErrorManager.addError("Number of bonuses did not match for " + MapResource(m_object).getItem().name + ", expected " + this.m_numBonus + ", received " + param1.numBonus, ErrorManager.ERROR_REMOTEOBJECT_FAULT);
                }
                else if (this.m_coinBonus != param1.coinBonus)
                {
                    ErrorManager.addError("Bonus percent did not match for " + MapResource(m_object).getItem().name + ", expected " + this.m_coinBonus + ", received " + param1.coinBonus, ErrorManager.ERROR_REMOTEOBJECT_FAULT);
                }
                else if (this.m_xpBonus != param1.xpBonus)
                {
                    ErrorManager.addError("XP bonus did not match for " + MapResource(m_object).getItem().name + ", expected " + this.m_xpBonus + ", received " + param1.absoluteBonus, ErrorManager.ERROR_REMOTEOBJECT_FAULT);
                }
            }
            return;
        }//end

    }



