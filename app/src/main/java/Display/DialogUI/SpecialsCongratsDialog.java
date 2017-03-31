package Display.DialogUI;

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
import Display.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.utils.*;

    public class SpecialsCongratsDialog extends GenericDialog
    {
        private SpecialsCongratsDialogView m_dialogView ;
        public static Item specialsItem ;

        public  SpecialsCongratsDialog (String param1 ,String param2 ="",int param3 =0,Function param4 =null ,String param5 ="",String param6 ="",boolean param7 =true ,int param8 =0,String param9 ="",Function param10 =null ,String param11 ="",boolean param12 =true ,Item param13 =null )
        {
            specialsItem = param13;
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11);
            return;
        }//end

        public void  setItem (String param1 )
        {
            specialsItem = Global.gameSettings().getItemByName(param1);
            return;
        }//end

         protected GenericDialogView  createDialogView (Dictionary param1 )
        {
            this.m_dialogView = new SpecialsCongratsDialogView(param1, m_message, m_dialogTitle, m_type, this.closeAndShow, m_icon, m_iconPos, "", null, m_customOk, true, specialsItem);
            StatsManager.count("bogo", "congrats_dialog", "view");
            return this.m_dialogView;
        }//end

        public void  closeAndShow ()
        {
            UI.displayInventory(specialsItem.name);
            return;
        }//end

         protected Dictionary  createAssetDict ()
        {
            Dictionary _loc_1 =new Dictionary ();
            _loc_1.put("dialog_bg",  new (DisplayObject)m_comObject.dialog_bg());
            _loc_1.put("extra_dialog_bg",  new (DisplayObject)EmbeddedArt.citySam_congratsAward());
            _loc_1.put("reward_burst",  new (DisplayObject)EmbeddedArt.reward_burst());
            return _loc_1;
        }//end

    }



