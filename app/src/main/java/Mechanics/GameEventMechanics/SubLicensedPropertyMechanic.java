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

import Classes.*;
import Mechanics.GameMechanicInterfaces.*;

    public class SubLicensedPropertyMechanic extends LicensedPropertyMechanic implements IGameMechanic
    {

        public  SubLicensedPropertyMechanic ()
        {
            return;
        }//end

         protected String  validatePropertyName (String param1 )
        {
            Player _loc_4 =null ;
            Item _loc_5 =null ;
            Array _loc_6 =null ;
            if (param1 == null || param1.length == 0)
            {
                return "";
            }
            _loc_2 =Global.gameSettings().getItemByName(param1 );
            if (_loc_2 == null)
            {
                return "";
            }
            if (itemNeedsLicense(_loc_2))
            {
                _loc_4 = Global.player;
                _loc_5 = ((MapResource)m_owner).getItem();
                if (_loc_4 == null || !_loc_4.checkSubLicense(_loc_5.name, param1))
                {
                    return "";
                }
            }
            _loc_3 = m_config.params.get( "restrictByKeywords") ;
            if (_loc_3 != null && _loc_3.length > 0)
            {
                _loc_6 = _loc_3.split(",");
                if (_loc_6.length > 0 && !itemHasAnyOfTheseKeywords(_loc_2, _loc_6))
                {
                    return "";
                }
            }
            return param1;
        }//end

    }



