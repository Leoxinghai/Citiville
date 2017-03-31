package Mechanics.GameEventMechanics;

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

import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
//import flash.utils.*;

    public class TieredDooberMechanic extends DooberMechanic
    {
        protected Array m_tierTable ;

        public  TieredDooberMechanic ()
        {
            this.m_tierTable = null;
            return;
        }//end

         public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            XML _loc_4 =null ;
            super.initialize(param1, param2);
            this.m_tierTable = new Array();
            _loc_3 =Global.gameSettings().getTierXml(m_config.params.get( "tierTable") );
            for(int i0 = 0; i0 < _loc_3.children().size(); i0++)
            {
            		_loc_4 = _loc_3.children().get(i0);

                this.m_tierTable.push({num:int(_loc_4.@num), amount:String(_loc_4.@amount)});
            }
            return;
        }//end

         public String  getRandomModifiersName ()
        {
            return Global.gameSettings().getTieredString(m_config.params.get("tierTable"), this.value);
        }//end

        protected double  value ()
        {
            return Number(m_owner.getDataForMechanic(m_config.type));
        }//end

        public Object  getTierInfo (int param1 )
        {
            Object _loc_2 =null ;
            if (this.m_tierTable)
            {
                _loc_2 = this.m_tierTable.get(param1);
            }
            return _loc_2;
        }//end

        public int  getNumTiers ()
        {
            int _loc_1 =0;
            if (this.m_tierTable)
            {
                _loc_1 = this.m_tierTable.length;
            }
            return _loc_1;
        }//end

        public Dictionary  getTierRewardInfo (int param1 )
        {
            tier = param1;
            Dictionary dict ;
            tierInfo = this.getTierInfo(tier);
            if (tierInfo)
            {
                int _loc_4 =0;
                _loc_5 = m_config.rawXMLConfig.displayedStats;
                XMLList _loc_3 =new XMLList("");
                Object _loc_6;
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);

                    _loc_7 = _loc_5.get(_loc_4);
                    with (_loc_5.get(_loc_4))
                    {
                        if (@name == tierInfo.amount)
                        {
                            _loc_3.put(_loc_4,  _loc_6);
                        }
                    }
                }
                dict = Global.gameSettings().parseDisplayedStats(m_owner.getItemName(), _loc_3, tierInfo.amount);
            }
            return dict;
        }//end

    }



