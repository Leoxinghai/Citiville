package Modules.quest.Display.TaskFooters;

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

import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Modules.streetFair.*;
//import flash.events.*;

    public class OpenCarnivalDialog extends BasicButtonFooter
    {

        public  OpenCarnivalDialog (GenericDialogView param1 ,String param2 )
        {
            super(param1, param2, ZLoc.t("Dialogs", "OpenCarnivalDialog"));
            return;
        }//end

         protected void  onButtonClick (Event event )
        {
            Sounds.playFromSet(Sounds.SET_CLICK);
            m_dialogView.countDialogViewAction("OPEN_CARNIVAL_DIALOG");
            m_dialogView.close();
            _loc_2 = m_type;
            _loc_3 =Global.gameSettings().getTicketMQConfigForTheme(_loc_2 );
            _loc_4 = _loc_3.get("dialogSwf") ;
            UI.displayPopup(new StreetFairDialog(null, _loc_4, _loc_2));
            return;
        }//end

    }



