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

import Classes.orders.*;
import Engine.Managers.*;

    public class TGetOrders extends TFarmTransaction
    {
        private String m_message ;

        public  TGetOrders (String param1)
        {
            this.m_message = param1;
            return;
        }//end

         public void  perform ()
        {
            signedCall("UserService.getOrders");
            return;
        }//end

         protected void  onComplete (Object param1 )
        {
            int _loc_2 =0;
            String _loc_3 =null ;
            int _loc_4 =0;
            if (param1 !=null)
            {
                _loc_2 = 0;
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                		_loc_3 = param1.get(i0);

                    switch(_loc_3)
                    {
                        case OrderType.VISITOR_HELP:
                        {
                            _loc_4 = Global.world.orderMgr.addOrdersByType(_loc_3, param1);
                            if (_loc_4 > 0)
                            {
                                _loc_2 = _loc_2 + _loc_4;
                                StatsManager.count("zoom", "show", "neighbor_order");
                            }
                            Global.world.citySim.friendVisitManager.cleanUp();
                            Global.world.citySim.friendVisitManager.setNeedToCheckQueue();
                            break;
                        }
                        default:
                        {
                            break;
                            break;
                        }
                    }
                }
                if (this.m_message && _loc_2 > 0)
                {
                    Global.ui.showTickerMessage(this.m_message);
                }
            }
            return;
        }//end

    }



