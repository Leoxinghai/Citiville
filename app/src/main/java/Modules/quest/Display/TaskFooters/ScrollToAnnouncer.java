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

import Classes.announcers.*;
import Classes.announcers.announcerinteractions.*;
import Classes.util.*;
import Display.DialogUI.*;
import Display.aswingui.*;
//import flash.events.*;

    public class ScrollToAnnouncer extends BasicButtonFooter
    {

        public  ScrollToAnnouncer (GenericDialogView param1 ,String param2 )
        {
            super(param1, param2, ZLoc.t("Dialogs", "ScrollTo"));
            return;
        }//end

         protected CustomButton  createButton (String param1 )
        {
            _loc_2 = super.createButton(param1);
            _loc_3 =Global.world.citySim.announcerManager.findAnnouncerDataByName(m_type );
            if (!_loc_3)
            {
                _loc_2.setEnabled(false);
            }
            return _loc_2;
        }//end

         protected void  onButtonClick (Event event )
        {
            Function _loc_3 =null ;
            Sounds.playFromSet(Sounds.SET_CLICK);
            m_dialogView.countDialogViewAction("SCROLL");
            _loc_2 =Global.world.citySim.announcerManager.findAnnouncerDataByName(m_type );
            if (_loc_2)
            {
                m_dialogView.close();
                _loc_3 = _loc_2.npc.playActionCallback != null ? (_loc_2.npc.playActionCallback) : (null);
                Global.world.centerOnObjectWithCallback(_loc_2.npc, 1, _loc_3);
                _loc_2.activateInteraction(AnnouncerInteractionTypes.SCROLL_TO);
            }
            return;
        }//end

    }



