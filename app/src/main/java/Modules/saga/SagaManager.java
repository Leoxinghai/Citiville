package Modules.saga;

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
import Classes.QuestGroup.*;
import Classes.announcements.*;
import Display.*;
import Display.SagaUI.*;
import Engine.Managers.*;
import Modules.quest.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;

    public class SagaManager
    {
        private XMLList m_sagaDefinitions ;
        public Dictionary sagas ;
        private String m_nameOfQuestThatShowedIntro ;
        private Dictionary m_sagaIcons ;
        private Dictionary m_sagaHudIcons ;
        private static SagaManager m_instance ;

        public  SagaManager ()
        {
            this.sagas = new Dictionary();
            this.m_sagaDefinitions = Global.gameSettings().getSagas();
            this.m_sagaIcons = new Dictionary();
            this.m_sagaHudIcons = new Dictionary();
            return;
        }//end

        public boolean  isActAvailable (String param1 )
        {
            XML _loc_3 =null ;
            _loc_2 = this.getQuestGroupsByAct(param1 );
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (QuestGroupManager.instance.isQuestGroupAvailable(_loc_3.attribute("name")))
                {
                    return true;
                }
            }
            return false;
        }//end

        public void  showDialogForSaga (String param1 ,boolean param2 =true )
        {
            String _loc_4 =null ;
            SagaDialog _loc_5 =null ;
            _loc_3 = this.getSagaDefinitionByName(param1 );
            if (_loc_3)
            {
                _loc_4 = this.getNextPlayableSagaIntroName(param1);
                if (!param2 && _loc_4)
                {
                    this.showIntro(_loc_4);
                }
                else
                {
                    _loc_5 = new SagaDialog(param1);
                    UI.displayPopup(_loc_5);
                }
            }
            return;
        }//end

        public void  loadObject (Object param1 )
        {
            String _loc_2 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_2 = param1.get(i0);

                this.sagas.put(_loc_2,  param1.get(_loc_2));
            }
            return;
        }//end

        private void  initSagaAndActFeatureData (String param1 ,String param2 )
        {
            if (!this.sagas.get(param1))
            {
                this.sagas.put(param1,  {});
                this.sagas.get(param1).put("acts",  {});
            }
            if (param2 && !this.sagas.get(param1).get("acts").get(param2))
            {
                this.sagas.get(param1).get("acts").put(param2,  {});
            }
            return;
        }//end

        private boolean  isSagaAndActRewardGranted (String param1 ,String param2)
        {
            this.initSagaAndActFeatureData(param1, param2);
            if (param2)
            {
                return this.sagas[param1]["acts"][param2].get("rewardGranted") != null;
            }
            return this.sagas[param1].get("rewardGranted") != null;
        }//end

        private void  setSagaAndActRewardGranted (String param1 ,String param2)
        {
            this.initSagaAndActFeatureData(param1, param2);
            if (param2)
            {
                this.sagas.get(param1).get("acts").get(param2).put("rewardGranted",  GlobalEngine.getTimer());
            }
            else
            {
                this.sagas.get(param1).put("rewardGranted",  GlobalEngine.getTimer());
            }
            return;
        }//end

        public void  showSagaQuest (String param1 )
        {
            _loc_2 = this.getPlayableActIntroName(this.getActNameByQuestName(param1 ));
            if (_loc_2)
            {
                this.showIntro(_loc_2, param1);
            }
            else
            {
                Global.questManager.pumpActivePopup(param1);
            }
            return;
        }//end

        public void  showIntro (String param1 ,String param2 )
        {
            StartUpDialogManager.displayAnnouncement(param1);
            Global.announcementManager.getAnnouncementByIdUnconditionally(param1).setAsSeen();
            this.m_nameOfQuestThatShowedIntro = param2;
            return;
        }//end

        public void  notifyIntroDismissal (String param1 )
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            if (this.m_nameOfQuestThatShowedIntro)
            {
                _loc_2 = this.m_nameOfQuestThatShowedIntro;
                this.m_nameOfQuestThatShowedIntro = null;
                this.showSagaQuest(_loc_2);
            }
            else
            {
                _loc_3 = this.getSagaNameByActName(param1);
                if (_loc_3)
                {
                    this.showDialogForSaga(_loc_3);
                }
            }
            return;
        }//end

        public void  showOutroForAct (String param1 )
        {
            String _loc_3 =null ;
            _loc_2 = this.getActDefinitionByName(param1 );
            if (_loc_2)
            {
                _loc_3 = _loc_2.attribute("outro").toString();
                if (_loc_3 && _loc_3 != "")
                {
                    StartUpDialogManager.displayAnnouncement(_loc_3);
                }
                else
                {
                    this.notifyOutroDismissal(param1);
                }
            }
            return;
        }//end

        public void  showOutroForSaga (String param1 )
        {
            _loc_2 = this.getOutroForSaga(param1 );
            if (_loc_2)
            {
                StartUpDialogManager.displayAnnouncement(_loc_2);
            }
            else
            {
                this.notifyOutroDismissal(param1);
            }
            return;
        }//end

        public String  getOutroForSaga (String param1 )
        {
            String _loc_2 =null ;
            _loc_3 = this.getSagaDefinitionByName(param1 );
            if (_loc_3)
            {
                _loc_2 = _loc_3.attribute("outro").toString();
            }
            return _loc_2;
        }//end

        public void  notifyOutroDismissal (String param1 )
        {
            SagaRewardDialog _loc_3 =null ;
            XML _loc_4 =null ;
            String _loc_5 =null ;
            SagaRewardDialog _loc_6 =null ;
            _loc_2 = this.getSagaDefinitionByName(param1 );
            if (_loc_2)
            {
                _loc_3 = new SagaRewardDialog(this.getSagaCompleteDialogData(param1));
                UI.displayPopup(_loc_3);
            }
            else
            {
                _loc_4 = this.getActDefinitionByName(param1);
                if (_loc_4)
                {
                    _loc_5 = this.getSagaNameByActName(param1);
                    _loc_6 = new SagaRewardDialog(this.getActCompleteDialogData(_loc_5, param1));
                    UI.displayPopup(_loc_6);
                }
            }
            return;
        }//end

        public XML  getSagaDefinitionByName (String param1 )
        {
            XMLList desiredSaga ;
            sagaName = param1;
            int _loc_4 =0;
            _loc_5 = this.m_sagaDefinitions ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (attribute("name").contains(sagaName))
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            desiredSaga = _loc_3;
            if (desiredSaga.length() > 0)
            {
                return desiredSaga.get(0);
            }
            return null;
        }//end

        public XML  getActDefinitionByName (String param1 )
        {
            XMLList desiredAct ;
            actName = param1;
            int _loc_4 =0;
            _loc_5 = this.m_sagaDefinitions.child("act");
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (attribute("name").contains(actName))
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            desiredAct = _loc_3;
            if (desiredAct.length() > 0)
            {
                return desiredAct.get(0);
            }
            return null;
        }//end

        public Array  getActiveQuests (String param1 )
        {
            XML _loc_4 =null ;
            XMLList _loc_5 =null ;
            XML _loc_6 =null ;
            GameQuest _loc_7 =null ;
            _loc_2 = this.getActsBySaga(param1 );
            Array _loc_3 =new Array ();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_4 = _loc_2.get(i0);

                _loc_5 = this.getQuestGroupsByAct(_loc_4.attribute("name"));
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);

                    _loc_7 = QuestGroupManager.instance.getQuestGroupActiveQuest(String(_loc_6.attribute("name")));
                    if (_loc_7)
                    {
                        _loc_3.push(_loc_7.name);
                    }
                }
            }
            return _loc_3;
        }//end

        public XMLList  getQuestGroupsByAct (String param1 )
        {
            actName = param1;
            int _loc_4 =0;
            _loc_5 = this.m_sagaDefinitions.child("act");
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (attribute("name").contains(actName))
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            return _loc_3.child("questGroup");
        }//end

        public XMLList  getActsBySaga (String param1 )
        {
            return this.getSagaDefinitionByName(param1).child("act");
        }//end

        public XMLList  getSagaRewards (String param1 )
        {
            return this.getSagaDefinitionByName(param1).child("rewards").child("reward");
        }//end

        public String  getNextPlayableSagaIntroName (String param1 )
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            _loc_2 = this.m_sagaDefinitions.child("act").attribute("name");
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_4 = this.getPlayableActIntroName(_loc_3);
                if (_loc_4)
                {
                    return _loc_4;
                }
            }
            return null;
        }//end

        public XMLList  getActRewards (String param1 ,String param2 )
        {
            sagaName = param1;
            actName = param2;
            int _loc_5 =0;
            _loc_6 = this.getSagaDefinitionByName(sagaName ).child("act");
            XMLList _loc_4 =new XMLList("");
            Object _loc_7;
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            		_loc_7 = _loc_6.get(i0);


                with (_loc_7)
                {
                    if (attribute("name") == actName)
                    {
                        _loc_4.put(_loc_5++,  _loc_7);
                    }
                }
            }
            return _loc_4.child("rewards").child("reward");
        }//end

        public Object  getSagaCompleteDialogData (String param1 )
        {
            XMLList attributes ;
            Object data ;
            XML attribute ;
            sagaName = param1;
            int _loc_4 =0;
            _loc_5 = this.getSagaDefinitionByName(sagaName );
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (attribute("name") == sagaName)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            attributes = _loc_3.child("complete").attributes();

            int _loc_31 =0;
            _loc_41 = attributes;
            for(int i0 = 0; i0 < attributes.size(); i0++)
            {
            		attribute = attributes.get(i0);


                data.put(attribute.name().toString(),  attribute.toString());
            }
            return data;
        }//end

        public Object  getActCompleteDialogData (String param1 ,String param2 )
        {
            XMLList attributes ;
            Object data ;
            XML attribute ;
            sagaName = param1;
            actName = param2;
            int _loc_5 =0;
            _loc_6 = this.getSagaDefinitionByName(sagaName ).child("act");
            XMLList _loc_4 =new XMLList("");
            Object _loc_7;
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            		_loc_7 = _loc_6.get(i0);


                with (_loc_7)
                {
                    if (attribute("name") == actName)
                    {
                        _loc_4.put(_loc_5++,  _loc_7);
                    }
                }
            }
            attributes = _loc_4.child("complete").attributes();

            int _loc_41 =0;
            _loc_51 = attributes;
            for(int i0 = 0; i0 < attributes.size(); i0++)
            {
            		attribute = attributes.get(i0);


                data.put(attribute.name().toString(),  attribute.toString());
            }
            return data;
        }//end

        public String  getActCompleteNpcUrl (String param1 ,String param2 )
        {
            XMLList urls ;
            sagaName = param1;
            actName = param2;
            int _loc_5 =0;
            _loc_6 = this.getSagaDefinitionByName(sagaName ).child("act");
            XMLList _loc_4 =new XMLList("");
            Object _loc_7;
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            		_loc_7 = _loc_6.get(i0);


                with (_loc_7)
                {
                    if (attribute("name") == actName)
                    {
                        _loc_4.put(_loc_5++,  _loc_7);
                    }
                }
            }
            urls = _loc_4.child("complete").attribute("npcUrl");
            if (urls.length())
            {
                return urls.get(0).toString();
            }
            return null;
        }//end

        public String  getPlayableActIntroName (String param1 )
        {
            AnnouncementData _loc_3 =null ;
            _loc_2 = this.getActIntroName(param1 );
            if (_loc_2)
            {
                _loc_3 = Global.announcementManager.getAnnouncementByIdUnconditionally(_loc_2);
                if (_loc_3 && _loc_3.hasNotSeen)
                {
                    return _loc_2;
                }
            }
            return null;
        }//end

        public String  getActIntroName (String param1 )
        {
            XMLList intros ;
            String introName ;
            actName = param1;
            int _loc_4 =0;
            _loc_5 = this.m_sagaDefinitions.child("act");
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (attribute("name").contains(actName))
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            intros = _loc_3.attribute("intro");
            if (intros.length() > 0)
            {
                introName = intros.get(0).toString();
                return introName;
            }
            return null;
        }//end

        public String  getActCompleteReplay (String param1 )
        {
            XMLList completeReplays ;
            String replayName ;
            actName = param1;
            int _loc_4 =0;
            _loc_5 = this.m_sagaDefinitions.child("act");
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (attribute("name").contains(actName))
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            completeReplays = _loc_3.attribute("completeReplay");
            if (completeReplays.length() > 0)
            {
                replayName = completeReplays.get(0).toString();
                return replayName;
            }
            return null;
        }//end

        public Item  getFirstRewardItemForAct (String param1 ,String param2 )
        {
            Item item ;
            XMLList itemRewards ;
            sagaName = param1;
            actName = param2;
            item;
            int _loc_5 =0;
            _loc_6 = this.getActRewards(sagaName ,actName );
            XMLList _loc_4 =new XMLList("");
            Object _loc_7;
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            		_loc_7 = _loc_6.get(i0);


                with (_loc_7)
                {
                    if (attribute("type") == "item")
                    {
                        _loc_4.put(_loc_5++,  _loc_7);
                    }
                }
            }
            itemRewards = _loc_4;
            if (itemRewards.length() > 0)
            {
                item = Global.gameSettings().getItemByName(itemRewards.get(0).attribute("value"));
            }
            return item;
        }//end

        public Item  getFirstRewardItemForSaga (String param1 )
        {
            Item item ;
            XMLList itemRewards ;
            sagaName = param1;
            item;
            int _loc_4 =0;
            _loc_5 = this.getSagaRewards(sagaName );
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (attribute("type") == "item")
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            itemRewards = _loc_3;
            if (itemRewards.length() > 0)
            {
                item = Global.gameSettings().getItemByName(itemRewards.get(0).attribute("value"));
            }
            return item;
        }//end

        public boolean  isSagaComplete (String param1 )
        {
            XML _loc_3 =null ;
            String _loc_4 =null ;
            _loc_2 = this.getSagaDefinitionByName(param1 ).child("act");
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                _loc_4 = _loc_3.attribute("name").toString();
                if (!this.isActComplete(param1, _loc_4))
                {
                    return false;
                }
            }
            return true;
        }//end

        public boolean  isActComplete (String param1 ,String param2 ,boolean param3 =false )
        {
            int count ;
            XML group ;
            sagaName = param1;
            actName = param2;
            skipFeatureData = param3;
            if (!skipFeatureData)
            {
                if (this.isSagaAndActRewardGranted(sagaName, actName))
                {
                    return true;
                }
            }
            int _loc_6 =0;
            _loc_7 = this.getSagaDefinitionByName(sagaName ).child("act");
            XMLList _loc_5 =new XMLList("");
            Object _loc_8;
            for(int i0 = 0; i0 < _loc_7.size(); i0++)
            {
            		_loc_8 = _loc_7.get(i0);


                with (_loc_8)
                {
                    if (attribute("name").contains(actName))
                    {
                        _loc_5.put(_loc_6++,  _loc_8);
                    }
                }
            }
            list = _loc_5.child("questGroup");
            count = 0;
            int _loc_51 =0;
            _loc_61 = list;
            for(int i0 = 0; i0 < list.size(); i0++)
            {
            		group = list.get(i0);


                if (QuestGroupManager.instance.isQuestGroupComplete(group.attribute("name")))
                {
                    count = (count + 1);
                }
            }
            return list.length() == count;
        }//end

        public String  getActNameByQuestName (String param1 )
        {
            XMLList actNames ;
            questName = param1;
            String actName ;
            questGroupName = QuestGroupManager.instance.getQuestGroupNameByQuest(questName);
            if (questGroupName)
            {
                int _loc_4 =0;
                _loc_5 = this.m_sagaDefinitions.child("act");
                XMLList _loc_3 =new XMLList("");
                Object _loc_6;
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                		_loc_6 = _loc_5.get(i0);


                    with (_loc_6)
                    {
                        if (child("questGroup").attribute("name").contains(questGroupName))
                        {
                            _loc_3.put(_loc_4++,  _loc_6);
                        }
                    }
                }
                actNames = _loc_3.attribute("name");
                if (actNames.length() > 0)
                {
                    actName = actNames.get(0).toString();
                }
                return actName == "" ? (null) : (actName);
            }
            return null;
        }//end

        private void  grantRewards (XMLList param1 )
        {
            XML _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_2 = param1.get(i0);

                _loc_3 = String(_loc_2.attribute("type"));
                _loc_4 = String(_loc_2.attribute("value"));
                switch(_loc_3)
                {
                    case "item":
                    {
                        Global.player.inventory.addItems(_loc_4, 1);
                        break;
                    }
                    case "coupon":
                    {
                        Global.player.giveCoupon(_loc_4);
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return;
        }//end

        public void  doRewardsForActOnQuestComplete (String param1 )
        {
            _loc_2 = this.getActNameByQuestName(param1 );
            _loc_3 = this.getSagaNameByActName(_loc_2 );
            if (!_loc_3)
            {
                return;
            }
            if (this.isActComplete(_loc_3, _loc_2))
            {
                if (!this.isSagaAndActRewardGranted(_loc_3, _loc_2))
                {
                    this.setSagaAndActRewardGranted(_loc_3, _loc_2);
                    this.grantRewards(this.getActRewards(_loc_3, _loc_2));
                    this.showOutroForAct(_loc_2);
                }
            }
            return;
        }//end

        public void  doRewardsForSagaOnActComplete (String param1 )
        {
            XML _loc_5 =null ;
            _loc_2 = this.getSagaNameByActName(param1 );
            if (!_loc_2)
            {
                return;
            }
            _loc_3 = this.getActsBySaga(_loc_2 );
            boolean _loc_4 =true ;
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_5 = _loc_3.get(i0);

                param1 = _loc_5.attribute("name").toString();
                if (!this.isSagaAndActRewardGranted(_loc_2, param1))
                {
                    _loc_4 = false;
                    break;
                }
            }
            if (_loc_4 && !this.isSagaAndActRewardGranted(_loc_2))
            {
                this.setSagaAndActRewardGranted(_loc_2);
                this.grantRewards(this.getSagaRewards(_loc_2));
                this.showOutroForSaga(_loc_2);
            }
            return;
        }//end

        public String  getSagaNameByActName (String param1 )
        {
            String sagaName ;
            XMLList sagaNames ;
            actName = param1;
            sagaName = "";
            int _loc_4 =0;
            _loc_5 = this.m_sagaDefinitions ;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (child("act").attribute("name").contains(actName))
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            sagaNames = _loc_3.attribute("name");
            if (sagaNames.length() > 0)
            {
                sagaName = sagaNames.get(0).toString();
            }
            return sagaName == "" ? (null) : (sagaName);
        }//end

        public String  getSagaNameByQuestName (String param1 )
        {
            String _loc_3 =null ;
            _loc_2 = this.getActNameByQuestName(param1 );
            if (_loc_2)
            {
                _loc_3 = this.getSagaNameByActName(_loc_2);
                if (_loc_3)
                {
                    return _loc_3;
                }
            }
            return null;
        }//end

        public String  getSagaHudNameByQuestName (String param1 )
        {
            Array _loc_4 =null ;
            _loc_2 = param1;
            _loc_3 = this.getSagaNameByQuestName(param1 );
            if (_loc_3)
            {
                if (this.m_sagaHudIcons.get(_loc_3))
                {
                    _loc_2 = this.m_sagaHudIcons.get(_loc_3);
                }
                else
                {
                    _loc_4 = SagaManager.instance.getActiveQuests(_loc_3);
                    if (_loc_4.length > 0)
                    {
                        _loc_2 = _loc_4.get(0);
                        this.m_sagaHudIcons.put(_loc_3,  _loc_2);
                    }
                }
            }
            return _loc_2;
        }//end

        public String  getSagaIconName (String param1 )
        {
            _loc_2 = this.getSagaDefinitionByName(param1 );
            if (_loc_2 == null)
            {
                return null;
            }
            _loc_3 = _loc_2.child("icon").@url.get(0);
            if (_loc_3 == null)
            {
                return null;
            }
            return _loc_3;
        }//end

        public void  requestSagaIcon (String param1 ,Function param2 )
        {
            Object cache ;
            sagaName = param1;
            callback = param2;
            if (this.m_sagaIcons.hasOwnProperty(sagaName))
            {
                cache = this.m_sagaIcons.get(sagaName);
                if (cache instanceof Array)
                {
                    ((Array)cache).push(callback);
                    return;
                }
                if (cache instanceof BitmapData)
                {
                    callback(new Bitmap((BitmapData)(BitmapData)cache));
                    return;
                }
                throw new Error("unexpected cache object: " + cache);
            }
            iconName = this.getSagaIconName(sagaName);
            if (iconName == null)
            {
                this.m_sagaIcons.put(sagaName,  null);
                callback(null);
                return;
            }
            url = Global.getAssetURL(iconName);
            if (url == null)
            {
                this.m_sagaIcons.put(sagaName,  null);
                callback(null);
                return;
            }
            (Global.player.numQuestsLoading + 1);
            this.m_sagaIcons.put(sagaName,  new Array());
            this.m_sagaIcons.get(sagaName).push(callback);
            happyCallback = function(eventEvent)
            {
                (Global.player.numQuestsLoading - 1);
                _loc_2 =(Loader) event.target.loader;
                _loc_3 =(Bitmap) _loc_2.content;
                _loc_4 = _loc_3.bitmapData ;
                _loc_5 = (Array)m_sagaIcons.get(sagaName);
                m_sagaIcons.put(sagaName,  _loc_4);
                _loc_6 = _loc_5.length;
                int _loc_7 =0;
                while (_loc_7 < _loc_6)
                {

                    _loc_8 = _loc_5;
                    _loc_8._loc_5.get(_loc_7)(new Bitmap(_loc_4));
                    _loc_7++;
                }
                return;
            }//end
            ;
            unhappyCallback = function(eventEvent)
            {
                (Global.player.numQuestsLoading - 1);
                _loc_2 = m_sagaIcons.get(sagaName).length ;
                int _loc_3 =0;
                while (_loc_3 < _loc_2)
                {

                    _loc_4 = m_sagaIcons.get(sagaName);
                    _loc_4.m_sagaIcons.get(sagaName).get(_loc_3)(null);
                    _loc_3++;
                }
                m_sagaIcons.put(sagaName,  null);
                return;
            }//end
            ;
            LoadingManager.load(url, happyCallback, LoadingManager.PRIORITY_NORMAL, unhappyCallback);
            return;
        }//end

        public String  getActTechTreeIconUrl (String param1 ,String param2 )
        {
            XML urlXml ;
            sagaName = param1;
            actName = param2;
            int _loc_5 =0;
            _loc_6 = this.getSagaDefinitionByName(sagaName ).child("act");
            XMLList _loc_4 =new XMLList("");
            Object _loc_7;
            for(int i0 = 0; i0 < _loc_6.size(); i0++)
            {
            		_loc_7 = _loc_6.get(i0);


                with (_loc_7)
                {
                    if (attribute("name") == actName)
                    {
                        _loc_4.put(_loc_5++,  _loc_7);
                    }
                }
            }
            urlXml = _loc_4.attribute("techTreeIconUrl").get(0);
            return urlXml != null ? (urlXml.toString()) : (null);
        }//end

        public Object forEachSaga (Function param1 )
        {
            XML _loc_2 =null ;
            XML _loc_3 =null ;
            _loc_4 = null;
            for(int i0 = 0; i0 < this.m_sagaDefinitions.size(); i0++)
            {
            		_loc_2 = this.m_sagaDefinitions.get(i0);

                _loc_3 = _loc_2.@name.get(0);
                if (_loc_3 == null)
                {
                    continue;
                }
                _loc_4 = param1(_loc_3.toString());
                if (_loc_4)
                {
                    return _loc_4;
                }
            }
            return null;
        }//end

        public Object forEachActInSaga (String param1 ,Function param2 )
        {
            XML _loc_4 =null ;
            XML _loc_5 =null ;
            _loc_6 = null;
            _loc_3 = this.getSagaDefinitionByName(param1 ).act ;
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_4 = _loc_3.get(i0);

                _loc_5 = _loc_4.@name.get(0);
                if (_loc_5 == null)
                {
                    continue;
                }
                _loc_6 = param2(_loc_5.toString());
                if (_loc_6)
                {
                    return _loc_6;
                }
            }
            return null;
        }//end

        public Object forEachQuestGroupInAct (String param1 ,String param2 ,Function param3 )
        {
            XML actXml ;
            XMLList groupXmlList ;
            XML groupXml ;
            XML groupNameXml ;
            Object result;
            sagaName = param1;
            actName = param2;
            body = param3;
            int _loc_6 =0;
            _loc_7 = this.getSagaDefinitionByName(sagaName ).act ;
            XMLList _loc_5 =new XMLList("");
            Object _loc_8;
            for(int i0 = 0; i0 < _loc_7.size(); i0++)
            {
            		_loc_8 = _loc_7.get(i0);


                with (_loc_8)
                {
                    if (@name == actName)
                    {
                        _loc_5.put(_loc_6++,  _loc_8);
                    }
                }
            }
            actXml = _loc_5.get(0);
            if (actXml == null)
            {
                return null;
            }
            groupXmlList = actXml.questGroup;
            if (groupXmlList == null)
            {
                return null;
            }
            int _loc_51 =0;
            _loc_61 = groupXmlList;
            for(int i0 = 0; i0 < groupXmlList.size(); i0++)
            {
            		groupXml = groupXmlList.get(i0);


                groupNameXml = groupXml.@name.get(0);
                if (groupNameXml == null)
                {
                    continue;
                }
                result = body(groupNameXml.toString());
                if (result)
                {
                    return result;
                }
            }
            return null;
        }//end

        public Object forEachQuestInAct (String param1 ,String param2 ,Function param3 )
        {
            sagaName = param1;
            actName = param2;
            body = param3;
            return this .forEachQuestGroupInAct (sagaName ,actName , (String param1 )*
            {
                return QuestGroupManager.instance.forEachQuestInGroup(param1, body);
            }//end
            );
        }//end

        public static SagaManager  instance ()
        {
            if (!m_instance)
            {
                m_instance = new SagaManager;
            }
            return m_instance;
        }//end

    }



