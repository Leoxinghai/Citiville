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
import Display.*;
import Display.DialogUI.*;
import Modules.stats.experiments.*;

    public class Specials
    {
        private static Specials m_instance =null ;
public static Array m_systemSpecials ;
public static boolean specialsSet =false ;
public static Array cachedItemOutput =null ;

        public  Specials ()
        {
            this._setSpecials();
            return;
        }//end

        protected void  _setSpecials ()
        {
            Array _loc_2 =null ;
            Array _loc_3 =null ;
            int _loc_4 =0;
            m_systemSpecials = new Array();
            _loc_1 = RuntimeVariableManager.getString("BUY_N_GET_M","");
            if (_loc_1.length > 0)
            {
                _loc_2 = _loc_1.split(",");
                _loc_4 = 0;
                while (_loc_4 < _loc_2.length())
                {

                    _loc_3 = _loc_2.get(_loc_4).split("|");
                    m_systemSpecials.put(_loc_4,  new Array());
                    m_systemSpecials.get(_loc_4).put("name",  _loc_3.get(0));
                    m_systemSpecials.get(_loc_4).put("n",  _loc_3.get(1));
                    m_systemSpecials.get(_loc_4).put("m",  _loc_3.get(2));
                    _loc_4++;
                }
            }
            else
            {
                m_systemSpecials = new Array();
            }
            return;
        }//end

        public Array  getAllSpecials (boolean param1 =false )
        {
            int _loc_3 =0;
            Array _loc_2 =new Array();
            if (param1 == true)
            {
                if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUY_N_GET_M) != ExperimentDefinitions.BUY_N_GET_M_ENABLED)
                {
                    return _loc_2;
                }
            }
            if (cachedItemOutput == null)
            {
                while (_loc_3 < m_systemSpecials.length())
                {

                    _loc_2.put(_loc_3,  m_systemSpecials.get(_loc_3).get("name"));
                    _loc_3++;
                }
                cachedItemOutput = _loc_2;
            }
            return cachedItemOutput;
        }//end

        public boolean  isValidSpecial (String param1 )
        {
            int _loc_2 =0;
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUY_N_GET_M) != ExperimentDefinitions.BUY_N_GET_M_ENABLED)
            {
                return false;
            }
            while (_loc_2 < m_systemSpecials.length())
            {

                if (m_systemSpecials.get(_loc_2).get("name") == param1)
                {
                    return true;
                }
                _loc_2++;
            }
            return false;
        }//end

        public int  getUserProgressByItemName (String param1 )
        {
            Array _loc_2 =null ;
            int _loc_3 =0;
            if (Global.player.purchasedSpecials)
            {
                _loc_2 = Global.player.purchasedSpecials;
            }
            else
            {
                return 0;
            }
            while (_loc_3 < _loc_2.length())
            {

                if (_loc_2.get(_loc_3).get("name") == param1)
                {
                    return _loc_2.get(_loc_3).get("cnt");
                }
                _loc_3++;
            }
            return 0;
        }//end

        public Array  getSpecialDataByName (String param1 )
        {
            int _loc_2 =0;
            while (_loc_2 < m_systemSpecials.length())
            {

                if (m_systemSpecials.get(_loc_2).get("name") == param1)
                {
                    return m_systemSpecials.get(_loc_2);
                }
                _loc_2++;
            }
            return new Array();
        }//end

        public void  setUserSpecial (String param1 ,int param2 )
        {
            Array _loc_8 =null ;
            Item _loc_9 =null ;
            SpecialsCongratsDialog _loc_10 =null ;
            Array _loc_11 =null ;
            _loc_3 = Global.player.purchasedSpecials;
            int _loc_4 =0;
            int _loc_5 =0;
            int _loc_6 =0;
            int _loc_7 =0;
            while (_loc_7 < _loc_3.length())
            {

                if (_loc_3.get(_loc_7).get("name") == param1)
                {
                    _loc_3.get(_loc_7).put("cnt",  _loc_3.get(_loc_7).get("cnt") + param2);
                    _loc_4 = _loc_3.get(_loc_7).get("cnt");
                    _loc_6 = _loc_7;
                }
                _loc_7++;
            }
            if (_loc_4 > 0)
            {
                _loc_8 = this.getSpecialDataByName(param1);
                _loc_5 = _loc_8.get("n");
                if (_loc_4 >= _loc_5)
                {
                    Global.player.inventory.addItems(param1, _loc_8.get("m"), true, false, true);
                    _loc_3.get(_loc_6).put("cnt",  0);
                    _loc_9 = Global.gameSettings().getItemByName(param1);
                    _loc_10 = new SpecialsCongratsDialog("", "SpecialsCongratsDialog", GenericDialogView.TYPE_CUSTOM_OK, null, "congratulations", _loc_9.iconRelative, true, 0, "", null, ZLoc.t("Dialogs", "OpenInventory"), true, _loc_9);
                    UI.displayPopup(_loc_10, true, "test", false);
                }
                else
                {
                    _loc_3.get(_loc_6).put("cnt",  _loc_4);
                }
            }
            else
            {
                _loc_11 = new Array();
                _loc_11.put("name",  param1);
                _loc_11.put("cnt",  param2);
                _loc_3.push(_loc_11);
            }
            Global.player.purchasedSpecials = _loc_3;
            return;
        }//end

        public static Specials  getInstance ()
        {
            if (m_instance == null)
            {
                m_instance = new Specials;
            }
            return m_instance;
        }//end

    }



