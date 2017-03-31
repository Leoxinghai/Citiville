package Classes.announcers.announcerinteractions;

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
import Classes.announcers.*;
import Mechanics.*;
//import flash.utils.*;

    public class AnnouncerInteractionCustomHandler extends BaseAnnouncerInteraction implements IAnnouncerInteraction
    {
        protected Dictionary m_arguments ;

        public  AnnouncerInteractionCustomHandler (AnnouncerData param1 ,XML param2 )
        {
            XML _loc_3 =null ;
            this.m_arguments = new Dictionary();
            for(int i0 = 0; i0 < param2.attributes().size(); i0++) 
            {
            		_loc_3 = param2.attributes().get(i0);

                this.m_arguments.put(_loc_3.name().toString(),  _loc_3.toString());
            }
            super(param1);
            return;
        }//end

        public void  activate ()
        {
            if (this.get(this.m_arguments.get("functionName")) != null)
            {
                this.m_arguments.get("functionName")();
            }
            return;
        }//end

        protected void  showAnnouncement ()
        {
            String _loc_1 =null ;
            if (this.m_arguments.hasOwnProperty("announcementName"))
            {
                _loc_1 = this.m_arguments.get("announcementName");
                StartUpDialogManager.displayAnnouncement(_loc_1);
            }
            return;
        }//end

        protected void  showBirthdayUpgradeDialog ()
        {
            MunicipalCenter _loc_2 =null ;
            _loc_1 =Global.world.getObjectsByNames(.get( "city_center_1","city_center_2","city_center_3","city_center_4","city_center_5","city_center_6","city_center_7") );
            if (_loc_1.length == 0)
            {
                Global.questManager.pumpActivePopup("qf_birthday_2011");
                return;
            }
            for(int i0 = 0; i0 < _loc_1.size(); i0++) 
            {
            		_loc_2 = _loc_1.get(i0);

                MechanicManager.getInstance().handleAction(_loc_2, MechanicManager.PLAY);
            }
            return;
        }//end

    }



