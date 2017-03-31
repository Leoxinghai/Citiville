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

import Classes.announcers.*;
import Display.*;
import Display.DialogUI.*;

    public class AnnouncerInteractionDialog extends BaseAnnouncerInteraction implements IAnnouncerInteraction
    {
        private GenericDialog m_dialog ;

        public  AnnouncerInteractionDialog (AnnouncerData param1 ,XML param2 )
        {
            super(param1);
            _loc_3 = ZLoc.t("Announcements",param2.attribute("text").toString ());
            _loc_4 = ZLoc.t("Announcements",param2.child("text").toString ());
            _loc_5 = param2.attribute("acceptButtonLoc").length ()>0? (param2.attribute("acceptButtonLoc").toString()) : ("Okay");
            _loc_6 = param2.attribute("picUrl").toString ();
            _loc_7 = param2.attribute("widthOffset").length()>0? (int(param2.attribute("widthOffset").toString())) : (0);
            _loc_8 = param2.attribute("widthOffset").length()>0? (int(param2.attribute("widthOffset").toString())) : (0);
            this.m_dialog = new GenericPictureDialog(_loc_4, "", GenericPopup.TYPE_OK, null, _loc_3, "", true, 0, _loc_5, _loc_6, _loc_7, _loc_8);
            return;
        }//end

        public void  activate ()
        {
            UI.displayPopup(this.m_dialog);
            return;
        }//end

    }



