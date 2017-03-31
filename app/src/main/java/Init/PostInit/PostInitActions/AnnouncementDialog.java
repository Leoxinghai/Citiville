package Init.PostInit.PostInitActions;

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
import Classes.announcements.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
//import flash.external.*;

    public class AnnouncementDialog extends StartupDialogBase
    {
        private AnnouncementData m_data ;

        public  AnnouncementDialog (AnnouncementData param1 ,Function param2 ,boolean param3 =true ,boolean param4 =false )
        {
            GenericDialog _loc_10 =null ;
            int _loc_13 =0;
            Object _loc_14 =null ;
            String _loc_15 =null ;
            Array _loc_16 =null ;
            String _loc_17 =null ;
            this.m_data = param1;
            String _loc_5 =null ;
            if (this.m_data.view.hasOwnProperty("multiLang"))
            {
                _loc_5 = this.getMultiLangMessage(this.m_data);
            }
            else if (this.m_data.view.hasOwnProperty("otherLang"))
            {
                _loc_5 = this.getOtherLangMessage(this.m_data);
            }
            else if (this.m_data.view.hasOwnProperty("textByExperiment"))
            {
                _loc_5 = ZLoc.t("Announcements", expandByExperiment(this.m_data.view.textByExperiment));
            }
            else
            {
                _loc_5 = ZLoc.t("Announcements", this.m_data.view.text);
            }
            _loc_6 = this.m_data.view.widthOffset ? (int(this.m_data.view.widthOffset)) : (0);
            _loc_7 = this.m_data.view.heightOffset ? (int(this.m_data.view.heightOffset)) : (0);
            String _loc_8 =null ;
            if (this.m_data.view.hasOwnProperty("titleByExperiment"))
            {
                _loc_8 = ZLoc.t("Announcements", expandByExperiment(this.m_data.view.titleByExperiment));
            }
            else
            {
                _loc_8 = ZLoc.t("Announcements", this.m_data.view.title);
            }
            _loc_9 = this.m_data.view.acceptButtonLoc ? (this.m_data.view.acceptButtonLoc) : ("Okay");
            if (this.m_data.view.hasOwnProperty("type") && this.m_data.view.get("type") == "fancy")
            {
                _loc_10 = new FancyAnnouncementDialog(_loc_5, "", GenericPopup.TYPE_OK, param2, _loc_8, "", true, 0, _loc_9, param1);
            }
            else
            {
                _loc_13 = GenericPopup.TYPE_OK;
                if (this.m_data.view.hasOwnProperty("params") && this.m_data.view.get("params").hasOwnProperty("disableCancel"))
                {
                    _loc_14 = this.m_data.view.get("params").get("disableCancel");
                    if (_loc_14.get("manualOverride") == "true")
                    {
                        _loc_13 = GenericDialogView.TYPE_OK_WITHOUTCANCEL;
                    }
                    else
                    {
                        _loc_15 = _loc_14.get("experimentName");
                        _loc_16 = ((String)_loc_14.get("experimentVariant")).split(",");
                        _loc_17 = Global.experimentManager.getVariant(_loc_15).toString();
                        if (_loc_16.indexOf(_loc_17) != -1)
                        {
                            _loc_13 = GenericDialogView.TYPE_OK_WITHOUTCANCEL;
                        }
                    }
                }
                _loc_10 = new NormalAnnouncementDialog(_loc_5, "", _loc_13, param2, _loc_8, "", true, 0, _loc_9, this.m_data.view.picUrl, _loc_6, _loc_7, param1.view.get("params"));
            }
            _loc_11 =(StatTracker) this.m_data.view.get( "viewTracking");
            _loc_12 =(StatTracker) this.m_data.view.get( "closeTracking");
            super(_loc_10, this.m_data.view.title, param4, this.onAnnouncementSeen, param3, _loc_11, _loc_12);
            return;
        }//end

        protected void  onAnnouncementSeen ()
        {
            if (this.m_data)
            {
                this.m_data.setAsSeen();
            }
            return;
        }//end

        protected String  getMultiLangMessage (AnnouncementData param1 )
        {
            _loc_2 =Global.localizer.langCode ;
            _loc_3 =Global.player.snUser.locale.split("_").get(0) ;
            _loc_4 = ExternalInterface.call("getSupportedLanguages");
            if (ExternalInterface.call("getSupportedLanguages") && _loc_2 != "" && _loc_3 != "")
            {
                if (_loc_4.indexOf(_loc_3) != -1 && _loc_2 != _loc_3)
                {
                    return param1.view.get("text_" + _loc_2) + "\n" + param1.view.get("text_" + _loc_3);
                }
            }
            return param1.view.get("text_" + _loc_2);
        }//end

        protected String  getOtherLangMessage (AnnouncementData param1 )
        {
            _loc_2 =Global.localizer.langCode ;
            _loc_3 =Global.player.snUser.locale.split("_").get(0) ;
            _loc_4 = ZLoc.t("Announcements",param1.view.text );
            if (_loc_2 == _loc_3)
            {
                return _loc_4;
            }
            if (_loc_2 != "en")
            {
                return _loc_4;
            }
            _loc_5 = _loc_3"text_"+;
            if (param1.view.hasOwnProperty(_loc_5))
            {
                _loc_4 = _loc_4 + "\n" + param1.view.get(_loc_5);
            }
            return _loc_4;
        }//end

        public static String  expandByExperiment (String param1 )
        {
            String _loc_6 =null ;
            String _loc_7 =null ;
            _loc_2 = BracketFinder.getBracketedSubstrings(param1);
            Array _loc_3 =new Array ();
            int _loc_4 =0;
            while (_loc_4 < _loc_2.length())
            {

                _loc_6 = _loc_2.get(_loc_4);
                _loc_7 = "" + Global.experimentManager.getVariant(_loc_6);
                _loc_3.push(_loc_7);
                _loc_4++;
            }
            _loc_5 = BracketFinder.substituteBracketedSubstrings(param1,_loc_3);
            return BracketFinder.substituteBracketedSubstrings(param1, _loc_3);
        }//end

    }



