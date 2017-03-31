package Modules.sunset;

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
import Init.*;
import Modules.quest.Managers.*;

//import flash.utils.*;
import validation.*;

    public class SunsetManager
    {
        private Dictionary m_sunsets ;
        private Object m_questNameToSunsetMap ;

        public  SunsetManager ()
        {
            this.m_questNameToSunsetMap = new Object();
            this.m_sunsets = new Dictionary();
            return;
        }//end

        public void  addSunset (Sunset param1 )
        {
            this.m_sunsets.put(param1.theme,  param1);
            return;
        }//end

        public Sunset  getSunsetByThemeName (String param1 )
        {
            return this.m_sunsets.get(param1) as Sunset;
        }//end

        public String  getQuestIconTextForExpiringQuest (GameQuest param1 )
        {
            int _loc_4 =0;
            _loc_2 = param1.getRemainingTimeForExpirableQuest ();
            String _loc_3 =null ;
            if (_loc_2 > 0)
            {
                _loc_3 = DateUtil.getFormattedDayCounter(_loc_2);
                _loc_4 = int(_loc_3);
                if (_loc_4 > 0)
                {
                    _loc_3 = ZLoc.t("Dialogs", "TimedQuests_days", {count:_loc_4});
                }
            }
            return _loc_3;
        }//end

        public Sunset  getSunsetByQuestName (String param1 )
        {
            XML _loc_3 =null ;
            GenericValidationScript _loc_4 =null ;
            Vector _loc_5.<GenericValidationCondition >=null ;
            String _loc_6 =null ;
            _loc_2 = this.m_questNameToSunsetMap.get(param1) ;
            if (!_loc_2)
            {
                _loc_3 = QuestSettingsInit.getQuestXMLByName(param1);
                _loc_4 = Global.validationManager.getValidator(_loc_3.@validate);
                if (_loc_4)
                {
                    _loc_5 = _loc_4.getValidationConditionsByType("SunsetValidationUtil");
                    if (_loc_5 && _loc_5.length == 1)
                    {
                        _loc_6 = _loc_5.get(0).getArgument("forTheme");
                        if (_loc_6 && _loc_6.length > 0)
                        {
                            _loc_2 = this.getSunsetByThemeName(_loc_6);
                            if (_loc_2)
                            {
                                this.m_questNameToSunsetMap.put(param1,  _loc_2);
                            }
                        }
                    }
                }
            }
            return _loc_2;
        }//end

    }



