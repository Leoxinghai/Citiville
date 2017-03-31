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

import Display.*;
import Display.DialogUI.*;
import Engine.*;
import Engine.Managers.*;
import Events.*;
//import flash.events.*;

    public class XPromoFriend extends Friend
    {
        private String m_action ="";
        private Object m_config ;

        public  XPromoFriend (Object param1 )
        {
            this.m_config = {};
            super(null, 0, 0, 0, false, null, "", null, null, null, 0, true, true);
            m_firstTimeVisit = false;
            this.m_config = param1;
            m_name = ZLoc.t("XPromo", param1.nameKey);
            this.m_action = param1.action;
            m_profilePic = String(param1.profilePic);
            return;
        }//end  

         public int  socialLevel ()
        {
            return 0;
        }//end  

         public String  uid ()
        {
            return "-2";
        }//end  

         public int  xp ()
        {
            return int.MAX_VALUE;
        }//end  

         public int  level ()
        {
            return int.MAX_VALUE;
        }//end  

         public Object  avatar ()
        {
            return null;
        }//end  

         public String  cityname ()
        {
            return m_name;
        }//end  

         public boolean  fake ()
        {
            return true;
        }//end  

         public boolean  empty ()
        {
            return false;
        }//end  

        public String  acceptButtonLocKey ()
        {
            return "xpromo_" + this.m_config.nameKey + "_accept";
        }//end  

        public String  neighborBarLocKey ()
        {
            return "xpromo_" + this.m_config.nameKey + "_neighborbar";
        }//end  

        public Object  configObj ()
        {
            return this.m_config;
        }//end  

        public void  onXPromo (MouseEvent event )
        {
            String title ;
            String message ;
            String url ;
            GenericPictureDialog dialogue ;
            e = event;
            if (e)
            {
                e.stopImmediatePropagation();
                e.stopPropagation();
            }
            callback = function(eventGenericPopupEvent)
            {
                if (event.button == GenericPopup.YES)
                {
                    GlobalEngine.socialNetwork.redirect("xpromo.php?action=" + m_action + "&source=neighbor", null, "_blank");
                }
                return;
            }//end  
            ;
            switch(this.m_action)
            {
                case "sendToMafia2":
                case "sendToAdventure":
                {
                    GlobalEngine.socialNetwork.redirect("xpromo.php?action=" + this.m_action + "&source=neighbor", null, "_blank");
                    break;
                }
                case "redirect":
                {
                    if (this.m_config.redirectUrl)
                    {
                        Utilities.launchURL(this.m_config.redirectUrl);
                    }
                    break;
                }
                default:
                {
                    title = ZLoc.t("Announcements", "Announcement" + this.m_config.nameKey + "_title");
                    message = ZLoc.t("Announcements", "Announcement" + this.m_config.nameKey + "_text");
                    url = "assets/announcements/announcement_" + this.m_config.nameKey + ".png";
                    dialogue = new GenericPictureDialog(message, "", GenericPopup.TYPE_OK, callback, title, "", true, 0, this.acceptButtonLocKey, url);
                    UI.displayPopup(dialogue, false);
                    break;
                    break;
                }
            }
            StatsManager.count("xpromo", this.m_config.nameKey, "dialog_redirect");
            return;
        }//end  

    }


