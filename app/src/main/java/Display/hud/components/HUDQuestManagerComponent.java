package Display.hud.components;

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
import Display.hud.*;
import Engine.Managers.*;
import Init.*;
import Modules.quest.Display.QuestManager.*;
import Modules.quest.Managers.*;
import Modules.saga.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import Classes.sim.*;

    public class HUDQuestManagerComponent extends HUDQuestBarComponent
    {
        protected Dictionary m_loadedQuestIcons =null ;
        protected Dictionary m_loadedQuestBanners =null ;
        protected boolean m_locked =false ;
        public static  int MAX_QUEST_SIDEBAR_SLOTS =4;
        public static  String QUEST_MANAGER_NAME ="QuestManager";

        public  HUDQuestManagerComponent ()
        {
            this.m_loadedQuestIcons = new Dictionary();
            this.m_loadedQuestBanners = new Dictionary();
            UI.questManagerView.addEventListener(QuestManagerView.QUEST_MANAGER_ENABLED, this.startQuestManagerMode, false, 0, true);
            UI.questManagerView.addEventListener(QuestManagerView.QUEST_MANAGER_DISABLED, this.endQuestManagerMode, false, 0, true);
            return;
        }//end

         public void  cleanUp ()
        {
            super.cleanUp();
            UI.questManagerView.removeEventListener(QuestManagerView.QUEST_MANAGER_ENABLED, this.startQuestManagerMode);
            UI.questManagerView.removeEventListener(QuestManagerView.QUEST_MANAGER_DISABLED, this.endQuestManagerMode);
            return;
        }//end

         public void  showBanner (String param1 ,String param2 ,boolean param3 =false ,Object param4 =null )
        {
            param1 = SagaManager.instance.getSagaHudNameByQuestName(param1);
            if (!m_sidebarIcons.hasOwnProperty(param1) && this.m_loadedQuestIcons.hasOwnProperty(param1))
            {
                this.m_loadedQuestBanners.put(param1,  {name:param1, type:param2, persist:param3, optionalLocale:param4});
            }
            else
            {
                super.showBanner(param1, param2, param3, param4);
            }
            return;
        }//end

         public boolean  hasIcon (String param1 )
        {
            boolean _loc_2 =false ;
            param1 = SagaManager.instance.getSagaHudNameByQuestName(param1);
            if (m_sidebarIcons.hasOwnProperty(param1) || UI.questManagerView.content.iconExists(param1) || this.m_loadedQuestIcons.hasOwnProperty(param1))
            {
                _loc_2 = true;
            }
            return _loc_2;
        }//end

         public boolean  addIcon (String param1 ,String param2 ,DisplayObject param3 ,Function param4 ,int param5 =0)
        {
            param1 = SagaManager.instance.getSagaHudNameByQuestName(param1);
            if (param1 !=null)
            {
                if (m_sidebarIcons.hasOwnProperty(param1) || UI.questManagerView.content.iconExists(param1) || this.m_loadedQuestIcons.hasOwnProperty(param1))
                {
                    return false;
                }
            }
            this.storeQuestIcon(param1, param2, param3, param4);
            if (Global.player.numQuestsLoading > 0 || this.m_locked)
            {
                return false;
            }
            _loc_6 = this.processStoredQuests ();
            if (this.processStoredQuests())
            {
                Global.player.saveQuestOrderSequence();
            }
            return _loc_6;
        }//end

         public DisplayObject  removeIcon (String param1 ,Function param2 ,boolean param3 =true ,boolean param4 =false )
        {
            XML _loc_7 =null ;
            int _loc_8 =0;
            boolean _loc_9 =false ;
            XML _loc_10 =null ;
            XML _loc_11 =null ;
            DisplayObject _loc_5 =null ;
            param1 = SagaManager.instance.getSagaHudNameByQuestName(param1);
            _loc_6 = super.removeIcon(param1,param2,param3,param4);
            if (!super.removeIcon(param1, param2, param3, param4))
            {
                _loc_5 = UI.questManagerView.content.removeIconByName(param1);
            }
            else if (param3)
            {
                if (Global.questManager.getQuestByName(param1).getHidden())
                {
                    this.addFirstQuestFromQuestManager();
                }
                else
                {
                    _loc_7 = QuestSettingsInit.getQuestXMLByName(param1);
                    _loc_8 = Global.gameSettings().getInt("maxQuestNumWithManager", 14);
                    if (Global.questManager.getQuestByName(param1).hideable || (_loc_7.sequels as XMLList).length() <= 0)
                    {
                        this.addFirstQuestFromQuestManager();
                    }
                    else if ((_loc_7.sequels as XMLList).length() > 0 && Global.questManager.getActiveQuests().length >= _loc_8)
                    {
                        _loc_9 = false;
                        for(int i0 = 0; i0 < _loc_7.sequels.sequel.size(); i0++)
                        {
                        	_loc_10 = _loc_7.sequels.sequel.get(i0);

                            if (_loc_10.attribute("name").length() <= 0)
                            {
                                continue;
                            }
                            _loc_11 = QuestSettingsInit.getQuestXMLByName(_loc_10.attribute("name"));
                            if (!_loc_11)
                            {
                                continue;
                            }
                            if (_loc_11.attribute("forcePriority") == "true")
                            {
                                _loc_9 = true;
                                break;
                            }
                        }
                        if (!_loc_9)
                        {
                            this.addFirstQuestFromQuestManager();
                        }
                    }
                }
            }
            return _loc_6;
        }//end

        protected void  storeQuestIcon (String param1 ,String param2 ,DisplayObject param3 ,Function param4 )
        {
            QuestManagerSprite _loc_5 =new QuestManagerSprite(param1 ,param2 ,param3 ,param4 );
            this.m_loadedQuestIcons.put(param1,  _loc_5);
            return;
        }//end

        protected void  addToQuestManager (QuestManagerSprite param1 )
        {
            UI.questManagerView.content.addIcon(param1);
            return;
        }//end

        public boolean  addQuestManagerSprite (QuestManagerSprite param1 )
        {
            DisplayObject _loc_2 =null ;
            String _loc_3 =null ;
            if (param1 !=null)
            {
                _loc_2 = null;
                _loc_3 = SagaManager.instance.getSagaNameByQuestName(name);
                if (_loc_3)
                {
                    StatsManager.count("quest_icons", "q_hud_add", _loc_3);
                }
                _loc_2 = param1.questIcon;
                return super.addIcon(param1.name, param1.questToolTip, _loc_2, param1.questCallBack);
            }
            return false;
        }//end

        public boolean  removeLastIconAndAddToQuestManager ()
        {
            _loc_1 = m_questNames.get(0);
            if (!m_sidebarIcons.get(_loc_1))
            {
                return false;
            }
            _loc_2 =Global.questManager.getQuestByName(_loc_1 );
            if (!_loc_2)
            {
                return false;
            }
            _loc_3 = m_sidebarIcons.get(_loc_1).clickCallback ;
            super.removeIcon(_loc_2.name, _loc_3, false);
            QuestManagerSprite _loc_4 =new QuestManagerSprite(_loc_2.name ,_loc_2.getToolTipText (),_loc_2.iconDisplayObject ,_loc_3 );
            UI.questManagerView.content.addIcon(_loc_4);
            return true;
        }//end

        protected boolean  addFirstQuestFromQuestManager ()
        {
            _loc_1 = UI.questManagerView.content.popFirstQuestIcon();
            if (_loc_1)
            {
                return this.addQuestManagerSprite(_loc_1);
            }
            return false;
        }//end

        protected boolean  processStoredQuests ()
        {
            QuestManagerSprite _loc_7 =null ;
            int _loc_8 =0;
            boolean _loc_9 =false ;
            Object _loc_10 =null ;
            int _loc_11 =0;
            HUDComponent _loc_12 =null ;
            Array _loc_1 =new Array ();
            Array _loc_2 =new Array ();
            Array _loc_3 =new Array ();
            Array _loc_4 =new Array ();
            boolean _loc_5 =false ;
            Dictionary _loc_6 =new Dictionary ();
            for(int i0 = 0; i0 < this.m_loadedQuestIcons.size(); i0++)
            {
            	_loc_7 = this.m_loadedQuestIcons.get(i0);

                if (_loc_6.get(_loc_7.name))
                {
                    continue;
                }
                _loc_6.put(_loc_7.name,  1);
                _loc_11 = Global.player.getSavedQuestSlot(_loc_7.name);
                if (Global.questManager.getQuestByName(_loc_7.name).getHidden())
                {
                    _loc_4.push(_loc_7);
                    continue;
                }
                if (_loc_11 >= 0)
                {
                    _loc_1.put(_loc_11,  _loc_7);
                    continue;
                }
                if (Global.player.getQuestManagerSlot(_loc_7.name) >= 0)
                {
                    _loc_2.push(_loc_7);
                    continue;
                }
                _loc_3.push(_loc_7);
            }
            this.m_loadedQuestIcons = new Dictionary();
            _loc_8 = m_questNames.length;
            _loc_9 = false;
            _loc_10 = null;
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            	_loc_7 = _loc_1.get(i0);

                if (_loc_8 >= MAX_QUEST_SIDEBAR_SLOTS)
                {
                    _loc_5 = this.removeLastIconAndAddToQuestManager();
                    if (!_loc_5)
                    {
                        return false;
                    }
                    _loc_9 = true;
                }
                _loc_5 = this.addQuestManagerSprite(_loc_7);
                if (!_loc_5)
                {
                    return false;
                }
                if (this.m_loadedQuestBanners.hasOwnProperty(_loc_7.name))
                {
                    _loc_10 = this.m_loadedQuestBanners.get(_loc_7.name);
                    super.showBanner(_loc_10.name, _loc_10.type, _loc_10.persist, _loc_10.optionalLocale);
                }
                _loc_8++;
            }
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_7 = _loc_3.get(i0);

                if (_loc_8 >= MAX_QUEST_SIDEBAR_SLOTS)
                {
                    _loc_5 = this.removeLastIconAndAddToQuestManager();
                    if (!_loc_5)
                    {
                        return false;
                    }
                    _loc_9 = true;
                }
                _loc_5 = this.addQuestManagerSprite(_loc_7);
                if (!_loc_5)
                {
                    return false;
                }
                if (this.m_loadedQuestBanners.hasOwnProperty(_loc_7.name))
                {
                    _loc_10 = this.m_loadedQuestBanners.get(_loc_7.name);
                    super.showBanner(_loc_10.name, _loc_10.type, _loc_10.persist, _loc_10.optionalLocale);
                }
                _loc_8++;
            }
            this.m_loadedQuestBanners = new Dictionary();
            _loc_2.sort(this.sortOnQuestOrder);
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_7 = _loc_2.get(i0);

                if (_loc_8 < MAX_QUEST_SIDEBAR_SLOTS)
                {
                    _loc_5 = this.addQuestManagerSprite(_loc_7);
                    if (!_loc_5)
                    {
                        return false;
                    }
                    _loc_8++;
                    continue;
                }
                this.addToQuestManager(_loc_7);
                _loc_9 = true;
            }
            _loc_4.sort(this.sortOnHiddenQuestOrder);
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_7 = _loc_4.get(i0);

                UI.questManagerView.content.addHiddenIcon(_loc_7);
                _loc_9 = true;
            }
            if (_loc_9)
            {
                _loc_12 = Global.hud.getComponent(HUD.COMP_QUEST_MANAGER_ICON);
                if (_loc_12)
                {
                    _loc_12.visible = true;
                }
            }
            return _loc_5;
        }//end

         public void  refreshIcons ()
        {
            String _loc_2 =null ;
            GameQuest _loc_1 =null ;
            if (Global.questManager)
            {
                for(int i0 = 0; i0 < m_sidebarIcons.size(); i0++)
                {
                	_loc_2 = m_sidebarIcons.get(i0);

                    _loc_1 = Global.questManager.getQuestByName(_loc_2);
                    if (_loc_1)
                    {
                        _loc_1.updateQuestIconTimer();
                    }
                }
            }
            return;
        }//end

        public void  startQuestManagerMode (Event event )
        {
            Global.hud.attachComponentToUI(HUD.COMP_QUESTS);
            return;
        }//end

        public void  endQuestManagerMode (Event event )
        {
            Global.hud.attachComponentBackToHUD(HUD.COMP_QUESTS);
            return;
        }//end

        public void  lock ()
        {
            if (!this.m_locked)
            {
                this.m_locked = true;
            }
            return;
        }//end

        public void  unlock ()
        {
            if (this.m_locked)
            {
                this.m_locked = false;
                this.processStoredQuests();
            }
            return;
        }//end

        protected int  sortOnQuestOrder (QuestManagerSprite param1 ,QuestManagerSprite param2 )
        {
            _loc_3 =Global.player.getQuestManagerSlot(param1.name );
            _loc_4 =Global.player.getQuestManagerSlot(param2.name );
            if (_loc_3 > _loc_4)
            {
                return 1;
            }
            if (_loc_3 < _loc_4)
            {
                return -1;
            }
            return 0;
        }//end

        protected int  sortOnHiddenQuestOrder (QuestManagerSprite param1 ,QuestManagerSprite param2 )
        {
            _loc_3 =Global.player.getHiddenQuestSlot(param1.name );
            _loc_4 =Global.player.getHiddenQuestSlot(param2.name );
            if (_loc_3 > _loc_4)
            {
                return 1;
            }
            if (_loc_3 < _loc_4)
            {
                return -1;
            }
            return 0;
        }//end

    }



