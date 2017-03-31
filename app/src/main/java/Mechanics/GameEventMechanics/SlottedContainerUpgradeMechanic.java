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
import Classes.util.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Mechanics.Transactions.*;

    public class SlottedContainerUpgradeMechanic extends UpgradeMechanic
    {

        public  SlottedContainerUpgradeMechanic ()
        {
            return;
        }//end

         protected void  preUpgradeForGameAction (String param1 ,Array param2 )
        {
            IActionGameMechanic _loc_4 =null ;
            MechanicActionResult _loc_5 =null ;
            MechanicMapResource _loc_6 =null ;
            _loc_3 = (String)(m_config.params.get( "friend") );
            if (_loc_3.length())
            {
                _loc_4 =(IActionGameMechanic) MechanicManager.getInstance().getMechanicInstance(m_owner, _loc_3, param1);
                if (_loc_4.hasOverrideForGameAction(param1))
                {
                    _loc_5 = _loc_4.executeOverrideForGameEvent(param1, param2);
                    if (_loc_5 && _loc_5.sendTransaction)
                    {
                        _loc_6 =(MechanicMapResource) m_owner;
                        GameTransactionManager.addTransaction(new TMechanicAction(_loc_6, _loc_3, param1, _loc_5.transactionParams));
                    }
                }
            }
            return;
        }//end

    }



