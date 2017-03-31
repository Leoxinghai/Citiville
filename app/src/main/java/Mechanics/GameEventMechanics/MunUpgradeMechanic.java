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
    public class MunUpgradeMechanic extends UpgradeMechanic
    {

        public  MunUpgradeMechanic ()
        {
            return;
        }//end

         public boolean  hasOverrideForGameAction (String param1 )
        {
            Flag _loc_4 =null ;
            int _loc_5 =0;
            boolean _loc_6 =false ;
            Municipal _loc_7 =null ;
            boolean _loc_8 =false ;
            if (!m_owner.mechanicExperimentEnabled(m_config))
            {
                return false;
            }
            _loc_2 = super.hasOverrideForGameAction(param1);
            if (!_loc_2)
            {
                return false;
            }
            if (m_owner instanceof HarvestableResource)
            {
                if (((HarvestableResource)m_owner).isHarvestable())
                {
                    return false;
                }
            }
            _loc_3 = m_config.params.get( "countflag") ;
            if (_loc_3 != null && _loc_3 != "")
            {
                _loc_4 = Global.player.getFlag(_loc_3);
                if (_loc_4 == null)
                {
                    return false;
                }
                _loc_5 = m_config.params.get("flagval");
                if (_loc_5 > _loc_4.m_value)
                {
                    return false;
                }
            }
            if (m_config.params.hasOwnProperty("passGate"))
            {
                _loc_6 = m_config.params.get("passGate") == "true";
                _loc_7 =(Municipal) m_owner;
                if (_loc_7)
                {
                    _loc_8 = _loc_7.upgradeGateKeysMet();
                    if (_loc_6 == _loc_8)
                    {
                        return true;
                    }
                    return false;
                }
                return false;
            }
            return true;
        }//end

    }



