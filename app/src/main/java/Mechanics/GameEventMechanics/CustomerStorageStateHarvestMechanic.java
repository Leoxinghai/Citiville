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
import Classes.doobers.*;

    public class CustomerStorageStateHarvestMechanic extends StorageStateHarvestMechanic
    {

        public  CustomerStorageStateHarvestMechanic ()
        {
            return;
        }//end

         public boolean  isHarvestReady ()
        {
            _loc_1 = m_owner.getDataForMechanic(m_config.type);
            return super.isHarvestReady() && _loc_1 && _loc_1.hasOwnProperty("customers") && _loc_1.hasOwnProperty("customersReq") && _loc_1.get("customersReq") <= _loc_1.get("customers");
        }//end

         protected Array  applyPayoutMultipliers (Item param1 ,Array param2 )
        {
            String _loc_4 =null ;
            Array _loc_5 =null ;
            _loc_3 = param2;
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_5 = _loc_3.get(i0);

                _loc_4 = Global.gameSettings().getDooberTypeFromItemName(_loc_5.get(0));
                switch(_loc_4)
                {
                    case Doober.DOOBER_COIN:
                    {
                        _loc_5.put(1,  _loc_5.get(1) * (param1.customerCapacity > 0 ? (param1.customerCapacity) : (param1.commodityReq)));
                        _loc_5.put(0,  Global.gameSettings().getDooberFromType(Doober.DOOBER_COIN, _loc_5.get(1)));
                        break;
                    }
                    default:
                    {
                        break;
                        break;
                    }
                }
            }
            return _loc_3;
        }//end

    }



