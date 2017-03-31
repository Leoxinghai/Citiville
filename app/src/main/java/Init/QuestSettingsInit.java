package Init;

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
import Engine.*;
import Engine.Init.*;
//import flash.events.*;
//import flash.net.*;
//import flash.utils.*;
import com.xinghai.Debug;

    public class QuestSettingsInit extends InitializationAction
    {
        private String m_settingsUrl ;
        public static  String INIT_ID ="ZQuestSettingsInit";
        private static XMLList m_xml ;
        private static Dictionary m_quests =new Dictionary ();
        private static XML m_rawXml ;
        private static Class m_questClass ;
        private static Class m_questUtility ;
        private static QuestSettingsInit m_instance ;
        private static ByteArray m_qsArray =null ;
        private static Object m_questXmlMap =new Object ();

        public  QuestSettingsInit (String param1 ,Class param2 ,Class param3 ,ByteArray param4 )
        {
            super(INIT_ID);
            this.m_settingsUrl = param1;
            m_questClass = param2;
            m_questUtility = param3;
            m_instance = this;
            m_qsArray = param4;
            return;
        }//end

         public void  execute ()
        {
            URLLoader _loc_1 =null ;
            GlobalEngine.zaspManager.trackTimingStart("QUEST_SETTINGS_INIT");
            if (m_qsArray)
            {
                this.onConfigXmlLoaded(null);
            }
            else
            {
                _loc_1 = new URLLoader(new URLRequest(this.m_settingsUrl));
                _loc_1.addEventListener(Event.COMPLETE, this.onConfigXmlLoaded);
            }
            return;
        }//end

        private void  onConfigXmlLoaded (Event event )
        {
            String _loc_2 =null ;
            if (m_qsArray)
            {
                _loc_2 = m_qsArray.toString();
            }
            else if (event)
            {
                if (this.m_settingsUrl.indexOf(".xml.z") > 0)
                {
                    Debug.debug4("QuestSettingsInit."+m_settingsUrl);
                    //_loc_2 = Utilities.uncompress(event.target.data);
                    _loc_2 = event.target.data;
                }
                else
                {
                    _loc_2 = event.target.data;
                }
            }
            _loc_3 = XML(_loc_2);
            m_rawXml = _loc_3;
            m_xml = _loc_3..quests;
            if (m_xml.length > 0)
            {
                m_xml = m_xml.get(0);
            }
            GlobalEngine.zaspManager.trackTimingStop("QUEST_SETTINGS_INIT");
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end

        public static QuestSettingsInit  getInstance ()
        {
            return m_instance;
        }//end

        public static Quest  getItemByName (String param1 )
        {
            XML _loc_3 =null ;
            _loc_2 = m_quests.get(param1);
            if (_loc_2 == null)
            {
                _loc_3 = getQuestXMLByName(param1);
                if (_loc_3 != null)
                {
                    _loc_2 = new m_questClass(_loc_3);
                    m_quests.put(param1,  _loc_2);
                }
            }
            return _loc_2;
        }//end

        public static XML  getQuestXMLByName (String param1 )
        {
            XMLList list ;
            nameStr = param1;
            result = m_questXmlMap.get(nameStr);
            if (!result)
            {
                int _loc_4 =0;
                _loc_5 = m_rawXml.quest;
                XMLList _loc_3 =new XMLList("");
                Object _loc_6;
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_6 = _loc_5.get(i0);


                    with (_loc_6)
                    {
                        if (@name == nameStr)
                        {
                            _loc_3.put(_loc_4++,  _loc_6);
                        }
                    }
                }
                list = _loc_3;
                if (list.length())
                {
                    result = list.get(0);
                    m_questXmlMap.put(nameStr,  result);
                }
            }
            return result;
        }//end

        public static boolean  isClientTestingEnabled ()
        {
            return m_questUtility != null;
        }//end

        public static Class  getClientTestingClass ()
        {
            return m_questUtility;
        }//end

    }



