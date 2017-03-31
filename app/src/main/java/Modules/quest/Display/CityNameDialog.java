package Modules.quest.Display;

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
import Events.*;
import Transactions.*;
//import flash.utils.*;

    public class CityNameDialog extends InputTextDialog
    {
        public String m_inputType ;

        public  CityNameDialog (Object param1 ,boolean param2 =false ,String param3 ="rename_city",int param4 =13,Function param5 =null )
        {
            this.m_inputType = param3;
            _loc_6 = ZLoc.t("Quest","NameYourCity_inputLabel");
            _loc_7 =Global.player.snUser.gender =="M"? (ZLoc.instance.MASC) : (ZLoc.instance.FEM);
            _loc_8 = ZLoc.t("Quest","NameYourCity_inputField",{name ZLoc.tn(Global.player.firstName ,_loc_7 )});
            super("", "ATownIsBorn", _loc_6, _loc_8, Global.gameSettings().getInt("maxCityNameLength", 15), param4, this.setCityName, true);
            return;
        }//end

        protected void  setCityName (GenericPopupEvent event )
        {
            if (GenericPopup.SAVE == event.button)
            {
                Global.player.cityName = this.m_textField.text;
                GameTransactionManager.addTransaction(new TRenameCity(this.m_textField.text), true, true);
            }
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            CityNameDialogView _loc_2 =null ;
            _loc_2 = new CityNameDialogView(param1, m_message, m_title, m_inputLabel, m_inputField, m_type, m_callback);
            m_textField = _loc_2.textField;
            return _loc_2;
        }//end

    }



