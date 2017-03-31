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
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;

    public class LootTableRollMechanic implements IActionGameMechanic
    {
        private MechanicMapResource m_owner ;
        private MechanicConfigData m_config ;

        public  LootTableRollMechanic ()
        {
            return;
        }//end

        public boolean  hasOverrideForGameAction (String param1 )
        {
            return true;
        }//end

        public MechanicActionResult  executeOverrideForGameEvent (String param1 ,Array param2 )
        {
            _loc_3 = (Int)(this.m_owner.getDataForMechanic(this.m_config.type ));
            Global.player.cash = Global.player.cash - int(this.m_config.rawXMLConfig.@purchasePrice);
            return new MechanicActionResult(true, false, true, {callback:this.onLootRoll});
        }//end

        public void  initialize (IMechanicUser param1 ,MechanicConfigData param2 )
        {
            this.m_owner =(MechanicMapResource) param1;
            this.m_config = param2;
            return;
        }//end

        protected void  onLootRoll (Object param1 )
        {
            return;
        }//end

        public boolean  isVisitEnabled ()
        {
            return false;
        }//end

        public boolean  blocksMechanicsOnSuccess ()
        {
            return this.m_config.params.get("blockOthers") == "true";
        }//end

    }



