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

import Display.*;
import Display.MarketUI.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;

    public class ShowCatalogOnClickMechanic implements IActionGameMechanic
    {
        public String type =null ;
        public String subtype =null ;

        public  ShowCatalogOnClickMechanic ()
        {
            return;
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.type = param2.params.catalogType;
            this.subtype = param2.params.catalogSubtype;
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return param1 == "GMPlay";
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return false;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            CatalogParams _loc_3 =new CatalogParams(this.type ,this.subtype );
            UI.displayCatalog(_loc_3);
            Global.ui.showTickerMessage(ZLoc.t("Main", "TickerBuyExpansion"));
            return new MechanicActionResult(true, true, false, null);
        }//end

    }



