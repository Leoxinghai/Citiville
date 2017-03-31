package Transactions.tracking;

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

import Engine.Transactions.*;

import com.xinghai.Debug;

    public class ResourceState
    {
        private double m_energy =0;
        private double m_gold =0;
        private double m_xp =0;
        private double m_cash =0;

        public  ResourceState ()
        {
            return;
        }//end

        public boolean  equals (ResourceState param1 )
        {
            if (this.energy != param1.energy)
            {
                return false;
            }
            if (this.gold != param1.gold)
            {
                return false;
            }
            if (this.xp != param1.xp)
            {
                return false;
            }
            if (this.cash != param1.cash)
            {
                return false;
            }
            return true;
        }//end

        public double  energy ()
        {
            return this.m_energy;
        }//end

        public void  energy (double param1 )
        {
            this.m_energy = param1;
            return;
        }//end

        public double  gold ()
        {
            return this.m_gold;
        }//end

        public void  gold (double param1 )
        {
            this.m_gold = param1;
            return;
        }//end

        public double  xp ()
        {
            return this.m_xp;
        }//end

        public void  xp (double param1 )
        {
            this.m_xp = param1;
            return;
        }//end

        public double  cash ()
        {
            return this.m_cash;
        }//end

        public void  cash (double param1 )
        {
            this.m_cash = param1;
            return;
        }//end

        public static ResourceState  getClientState ()
        {
            ResourceState _loc_1 =new ResourceState ;
            _loc_1.energy = Global.player.energy;
            _loc_1.gold = Global.player.gold;
            _loc_1.xp = Global.player.xp;
            _loc_1.cash = Global.player.cash;
            Debug.debug7("ResourceState.getClientState");
            return _loc_1;
        }//end

        public static ResourceState  getInitialServerState (Transaction param1 )
        {
            return getServerState(param1, false);
        }//end

        public static ResourceState  getFinalServerState (Transaction param1 )
        {
            return getServerState(param1, true);
        }//end

        private static ResourceState  getServerState (Transaction param1 ,boolean param2 )
        {
            _loc_3 = param2? ("finalState") : ("initialState");
            if (!param1.rawResult.hasOwnProperty(_loc_3))
            {
                return null;
            }
            _loc_4 = param1.rawResult.get(_loc_3) ;
            ResourceState _loc_5 =new ResourceState ;
            _loc_5.energy = _loc_4.energy;
            _loc_5.gold = _loc_4.gold;
            _loc_5.xp = _loc_4.xp;
            _loc_5.cash = _loc_4.cash;
            Debug.debug7("ResourceState.getServerState " + param1);

            return _loc_5;
        }//end

    }



