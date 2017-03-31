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

import Classes.effects.*;
import Classes.inventory.*;
import Classes.sim.*;

    public class Peep extends NPC
    {
        public boolean isStraggler =false ;
        public boolean isTired =false ;
        public boolean isFranchiseFreebie =false ;
        public String spawnSource ="residence";
        public double lastMerchantId =0;
        public static  String SOURCE_HOUSE ="residence";
        public static  String SOURCE_TOURBUS ="tourbus";
        public static  String SOURCE_CRUISESHIP ="harvest_ship";
        public static  String SOURCE_HOTEL ="hotel";

        public  Peep (String param1 ,boolean param2 )
        {
            super(param1, param2);
            return;
        }//end  

        public boolean  isReadyToGoHome ()
        {
            return !this.isStraggler && Global.player.commodities.getCount(Commodities.GOODS_COMMODITY) == 0 || this.isTired;
        }//end  

        public boolean  canWander ()
        {
            return true;
        }//end  

        public void  beforeDecision ()
        {
            this.lastMerchantId = 0;
            return;
        }//end  

         protected IActionSelection  makeActionSelection ()
        {
            return new PeepActionSelection(this);
        }//end  

        public String  getBusinessEntranceEffects (IMerchant param1 )
        {
            switch(this.spawnSource)
            {
                case Peep.SOURCE_CRUISESHIP:
                {
                    return CoinPickEffect.COIN_SPIN_1_DOUBLE;
                }
                default:
                {
                    break;
                }
            }
            return CoinPickEffect.COIN_SPIN_1;
        }//end  

        public String  source ()
        {
            return this.spawnSource;
        }//end  

    }


