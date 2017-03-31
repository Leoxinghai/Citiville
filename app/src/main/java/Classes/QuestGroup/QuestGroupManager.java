package Classes.QuestGroup;

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

import Init.*;
import Modules.quest.Managers.*;
//import flash.utils.*;

    public class QuestGroupManager
    {
        public Dictionary tickets ;
        private static QuestGroupManager m_ticketManager =null ;
        private static XMLList m_questGroupDefinitions ;
        public static  int MAX_TICKETS =300;
        public static  double GRAY_FILTER_LEVEL =0.33;
        public static  String COMPLETED ="COMPLETED";
        public static  String LOCKED ="LOCKED";
        public static  String IN_PROGRESS ="IN_PROGRESS";

        public  QuestGroupManager ()
        {
            this.tickets = new Dictionary();
            return;
        }//end

        public boolean  isQuestGroupAvailable (String param1 )
        {
            int numQuests ;
            questGroupName = param1;
            int _loc_4 =0;
            _loc_5 = m_questGroupDefinitions;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (attribute("name").contains(questGroupName))
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            numQuests = _loc_3.child("quest").length();
            return numQuests > 0;
        }//end

        public GameQuest  getQuestGroupActiveQuest (String param1 )
        {
            XMLList questGroupQuests ;
            String currQuestName ;
            GameQuest currQuest ;
            questGroupName = param1;
            int _loc_4 =0;
            _loc_5 = m_questGroupDefinitions;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (attribute("name") == questGroupName)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            questGroupQuests = _loc_3.child("quest");
            if (questGroupQuests.length() > 0)
            {


                for(int i0 = 0; i0 < questGroupQuests.size(); i0++)
                {
                	currQuestName = questGroupQuests.get(i0);


                    currQuest = Global.questManager.getQuestByName(currQuestName);
                    if (currQuest && !Global.player.isQuestCompleted(currQuestName))
                    {
                        return currQuest;
                    }
                }
            }
            return null;
        }//end

        public boolean  isQuestGroupComplete (String param1 )
        {
            int counter ;
            XMLList questGroupQuests ;
            String currQuestName ;
            questGroupName = param1;
            counter;
            int _loc_4 =0;
            _loc_5 = m_questGroupDefinitions;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (attribute("name") == questGroupName)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            questGroupQuests = _loc_3.child("quest");
            if (questGroupQuests.length() > 0)
            {
                int _loc_31 =0;
                _loc_41 = questGroupQuests;
                for(int i0 = 0; i0 < questGroupQuests.size(); i0++)
                {
                	currQuestName = questGroupQuests.get(i0);


                    if (Global.player.isQuestCompleted(currQuestName))
                    {
                        counter = (counter + 1);
                    }
                }
            }
            else
            {
                return false;
            }
            return questGroupQuests.length() == counter;
        }//end

        public Array  getQuestGroupIcons (String param1 )
        {
            XMLList questList ;
            Array questDefList ;
            XML quest ;
            String name ;
            XML questXml ;
            String url ;
            Object objInternal ;
            questName = param1;
            int _loc_4 =0;
            _loc_5 = m_questGroupDefinitions;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (child("quest").contains(questName))
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            questList = _loc_3.child("quest");
            if (questList.length())
            {
                questDefList;
                int _loc_31 =0;
                _loc_41 = questList;
                for(int i0 = 0; i0 < questList.size(); i0++)
                {
                	quest = questList.get(i0);


                    name = quest;
                    questXml = QuestSettingsInit.getQuestXMLByName(name);
                    url = questXml.attribute("url");
                    objInternal;
                    objInternal.put("icon",  Global.getAssetURL(url));
                    if (Global.player.isQuestCompleted(name))
                    {
                        objInternal.put("state",  QuestGroupManager.COMPLETED);
                    }
                    else if (Global.questManager.getQuestProgressByName(name))
                    {
                        objInternal.put("state",  QuestGroupManager.IN_PROGRESS);
                    }
                    else
                    {
                        objInternal.put("state",  QuestGroupManager.LOCKED);
                    }
                    questDefList.push(objInternal);
                }
                return questDefList;
            }
            return null;
        }//end

        public String  getQuestGroupNameByQuest (String param1 )
        {
            XMLList questGroupName ;
            questName = param1;
            int _loc_4 =0;
            _loc_5 = m_questGroupDefinitions;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (child("quest").contains(questName))
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            questGroupName = _loc_3.attribute("name");
            return questGroupName.length() > 0 && questGroupName.toString().length > 0 ? (questGroupName.toString()) : (null);
        }//end

        public  forEachQuestInGroup (String param1 ,Function param2 )*
        {
            XMLList groupQuests ;
            XML quest ;
            Object result;
            groupName = param1;
            body = param2;
            int _loc_5 =0;
            _loc_6 = m_questGroupDefinitions;
            XMLList _loc_4 =new XMLList("");
            Object _loc_7;
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            	_loc_7 = _loc_6.get(i0);


                with (_loc_7)
                {
                    if (attribute("name") == groupName)
                    {
                        _loc_4.put(_loc_5++,  _loc_7);
                    }
                }
            }
            groupQuests = _loc_4.child("quest");


            for(int i0 = 0; i0 < groupQuests.size(); i0++)
            {
            	quest = groupQuests.get(i0);


                result = param2(quest.toString());
                if (result)
                {
                    return result;
                }
            }
            return null;
        }//end

        public static QuestGroupManager  instance ()
        {
            XML _loc_1 =null ;
            if (m_ticketManager)
            {
                return m_ticketManager;
            }
            m_ticketManager = new QuestGroupManager;
            _loc_1 = Global.gameSettings().getXML();
            m_questGroupDefinitions = _loc_1.child("questGroups").child("questGroup");
            return m_ticketManager;
        }//end

    }




