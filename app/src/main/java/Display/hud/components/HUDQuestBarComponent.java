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
import Engine.Managers.*;
import Modules.quest.Display.*;
import Modules.saga.*;
import com.adobe.utils.*;
//import flash.display.*;
//import flash.events.*;
//import flash.media.*;
//import flash.utils.*;
import Classes.sim.*;


    public class HUDQuestBarComponent extends HUDComponent
    {
        protected Dictionary m_sidebarIcons =null ;
        private Dictionary m_removedBanners ;
        private SoundTransform m_questIconClickVolume ;
        protected Container m_container ;
        private Array m_bannerQueue ;
        protected Array m_questNames ;
        public static  String NAME ="QuestSidebar";

        public void  HUDQuestBarComponent (double param1 =10,double param2 =10,double param3 =0,double param4 =260,int param5 =4,int param6 =1,int param7 =400,int param8 =350)
        {
            locX = param1;
            locY = param2;
            width = param3;
            height = param4;
            numSlots = param5;
            direction = param6;
            mask_width = param7;
            mask_height = param8;
            this.m_container = new Container(numSlots, direction, locX, locY, width, height, mask_width, mask_height);
            this.m_container.name = NAME;
            this.addChild(this.m_container);
            double sfxVolume ;
            try
            {
                int _loc_11 =0;
                _loc_12 =Global.gameSettings().getXML ().sounds.sound ;
                XMLList _loc_10 =new XMLList("");
                _loc_13 *;
                for(int i0 = 0; i0 < _loc_12.size(); i0++)
                {
                		_loc_13 = _loc_12.get(i0);


                    with (_loc_13)
                    {
                        if (@name == "click1")
                        {
                            _loc_10.put(_loc_11++,  _loc_13);
                        }
                    }
                }
                sfxVolume = _loc_10.@volume;
            }
            catch (err:Error)
            {
            }
            this.m_questIconClickVolume = new SoundTransform(sfxVolume);
            this.m_sidebarIcons = new Dictionary();
            this.m_questNames = new Array();
            this.m_removedBanners = new Dictionary();
            this.m_bannerQueue = new Array();
            return;
        }//end

         public void  cleanUp ()
        {
            String _loc_1 =null ;
            Array _loc_2 =null ;
            QuestIndicatorSprite _loc_3 =null ;
            String _loc_4 =null ;
            this.m_bannerQueue.splice(0, this.m_bannerQueue.length());
            for(int i0 = 0; i0 < this.m_sidebarIcons.size(); i0++)
            {
            		_loc_1 = this.m_sidebarIcons.get(i0);

                _loc_4 = this.m_sidebarIcons.get(_loc_1).name;
                this.removeIcon(_loc_4, this.m_sidebarIcons.get(_loc_1).clickCallback, false);
                delete this.m_sidebarIcons.get(_loc_4);
            }
            _loc_2 = DictionaryUtil.getKeys(this.m_removedBanners);
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                this.m_removedBanners.get(_loc_3).cleanUp();
                delete this.m_removedBanners.get(_loc_3);
            }
            return;
        }//end

        public void  cleanUpBanner (QuestIndicatorSprite param1 )
        {
            if (this.m_removedBanners.hasOwnProperty(param1))
            {
                delete this.m_removedBanners.get(param1);
            }
            return;
        }//end

         public void  refresh (boolean param1 )
        {
            if (Global.questManager)
            {
                Global.questManager.refreshActiveIconQuests();
            }
            return;
        }//end

        public Container  container ()
        {
            return this.m_container;
        }//end

        public boolean  sideBarFull ()
        {
            return this.m_questNames.length >= this.container.num_slots;
        }//end

        protected GameSprite  createSidebarSprite (String param1 ,Object param2 ,String param3 ="Placeholder for tooltip")
        {
            GameSideBarSprite _loc_4 =new GameSideBarSprite(param1 );
            param2.addEventListener(MouseEvent.MOUSE_MOVE, this.containerMouseMove);
            _loc_4.addChild(param2);
            _loc_4.toolTip = param3;
            return _loc_4;
        }//end

        public boolean  hasIcon (String param1 )
        {
            param1 = SagaManager.instance.getSagaHudNameByQuestName(param1);
            _loc_2 = this.m_sidebarIcons.hasOwnProperty(param1 );
            return _loc_2;
        }//end

        public boolean  addIcon (String param1 ,String param2 ,DisplayObject param3 ,Function param4 ,int param5 =0)
        {
            param1 = SagaManager.instance.getSagaHudNameByQuestName(param1);
            if (param1 && this.m_sidebarIcons.hasOwnProperty(param1))
            {
                return false;
            }
            _loc_6 = this.createSidebarSprite(param1 ,param3 ,param2 );
            this.createSidebarSprite(param1, param3, param2).buttonMode = true;
            _loc_6.useHandCursor = true;
            _loc_6.addEventListener(MouseEvent.CLICK, param4);
            _loc_6.hideCursor = true;
            this.m_sidebarIcons.put(param1,  new IconData(param1, param4));
            this.m_questNames.push(param1);
            StatsManager.count("quest_icons", "q_hud_add", param1);
            return this.m_container.push(_loc_6, param5);
        }//end

        public DisplayObject  removeIcon (String param1 ,Function param2 ,boolean param3 =true ,boolean param4 =false )
        {
            DisplayObjectContainer _loc_10 =null ;
            param1 = SagaManager.instance.getSagaHudNameByQuestName(param1);
            _loc_5 = this.m_container.numChildren ;
            DisplayObject _loc_6 =null ;
            DisplayObject _loc_7 =null ;
            int _loc_8 =0;
            while (_loc_8 < _loc_5)
            {

                if (this.container.getChildAt(_loc_8))
                {
                    _loc_10 =(DisplayObjectContainer) this.container.getChildAt(_loc_8);
                    _loc_6 = _loc_10.getChildByName(param1);
                    if (_loc_6 != null)
                    {
                        _loc_7 = _loc_6;
                        this.container.slotClick(null, (ContainerSlot)_loc_10, false);
                        _loc_6.removeEventListener(MouseEvent.CLICK, param2);
                    }
                }
                _loc_8++;
            }
            this.removeAllQueued(param1);
            _loc_9 =             !param3;
            this.removeBanner(param1, _loc_9, param4);
            delete this.m_sidebarIcons.get(param1);
            if (this.m_questNames.indexOf(param1) >= 0)
            {
                this.m_questNames.splice(this.m_questNames.indexOf(param1), 1);
            }
            return _loc_7;
        }//end

        protected void  removeAllQueued (String param1 )
        {
            _loc_2 = this.m_bannerQueue.length -1;
            while (_loc_2 >= 0)
            {

                if (this.m_bannerQueue.get(_loc_2).name == param1)
                {
                    this.m_bannerQueue.splice(_loc_2, 1);
                }
                _loc_2 = _loc_2 - 1;
            }
            return;
        }//end

        public void  showBanner (String param1 ,String param2 ,boolean param3 =false ,Object param4 =null )
        {
            param1 = SagaManager.instance.getSagaHudNameByQuestName(param1);
            this.m_bannerQueue.push({name:param1, type:param2, persist:param3, optionalLocale:param4});
            this.pumpBannerQueue();
            return;
        }//end

        public void  removeBanner (String param1 ,boolean param2 =false ,boolean param3 =false )
        {
            Object _loc_4 =null ;
            param1 = SagaManager.instance.getSagaHudNameByQuestName(param1);
            if (this.m_sidebarIcons.hasOwnProperty(param1))
            {
                if (this.m_sidebarIcons.get(param1).banner)
                {
                    _loc_4 = this.m_sidebarIcons.get(param1).banner.definition;
                    this.m_removedBanners.get(this.m_sidebarIcons.put(param1).banner,  this.m_sidebarIcons.get(param1).banner);
                    if (param2)
                    {
                        this.m_sidebarIcons.get(param1).banner.cleanUp();
                        this.cleanUpBanner(this.m_sidebarIcons.get(param1).banner);
                    }
                    else
                    {
                        this.m_sidebarIcons.get(param1).banner.remove();
                    }
                    this.m_sidebarIcons.get(param1).banner = null;
                }
                else if (param3)
                {
                    for(int i0 = 0; i0 < this.m_bannerQueue.size(); i0++)
                    {
                    		_loc_4 = this.m_bannerQueue.get(i0);

                        if (_loc_4.name == param1)
                        {
                            break;
                        }
                    }
                }
            }
            this.pumpBannerQueue();
            if (param3 && _loc_4)
            {
                this.m_bannerQueue.push(_loc_4);
            }
            return;
        }//end

        protected void  pumpBannerQueue ()
        {
            IconData _loc_1 =null ;
            Object _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            boolean _loc_5 =false ;
            Object _loc_6 =null ;
            boolean _loc_7 =false ;
            double _loc_8 =0;
            int _loc_9 =0;
            DisplayObjectContainer _loc_10 =null ;
            _loc_11 = undefined;
            QuestIndicatorSprite _loc_12 =null ;
            for(int i0 = 0; i0 < this.m_sidebarIcons.size(); i0++)
            {
            		_loc_1 = this.m_sidebarIcons.get(i0);

                if (_loc_1.banner)
                {
                    return;
                }
            }
            if (this.m_bannerQueue.length > 0)
            {
                _loc_2 = this.m_bannerQueue.get(0);
                _loc_3 = _loc_2.name;
                _loc_4 = _loc_2.type;
                _loc_5 = _loc_2.persist;
                _loc_6 = _loc_2.optionalLocale;
                _loc_7 = false;
                if (this.m_sidebarIcons.hasOwnProperty(_loc_3))
                {
                    _loc_7 = true;
                    _loc_8 = this.m_container.numChildren;
                    _loc_9 = 0;
                    while (_loc_9 < _loc_8)
                    {

                        if (this.container.getChildAt(_loc_9))
                        {
                            _loc_10 =(DisplayObjectContainer) this.container.getChildAt(_loc_9);
                            _loc_11 = _loc_10.getChildByName(_loc_3);
                            if (_loc_11 != null)
                            {
                                _loc_12 = new QuestIndicatorSprite(_loc_4, _loc_5, _loc_3, _loc_6);
                                _loc_12.mouseChildren = false;
                                _loc_12.mouseEnabled = false;
                                _loc_11.addChild(_loc_12);
                                this.m_sidebarIcons.get(_loc_3).banner = _loc_12;
                                _loc_7 = false;
                                break;
                            }
                        }
                        _loc_9++;
                    }
                }
                if (!_loc_7)
                {
                    this.m_bannerQueue.shift();
                }
            }
            return;
        }//end

        public void  containerMouseMove (MouseEvent event )
        {
            _loc_2 = event.target.parent ;
            _loc_2.buttonMode = true;
            _loc_2.useHandCursor = true;
            return;
        }//end

        public SoundTransform  clickVolume ()
        {
            return this.m_questIconClickVolume;
        }//end

        public Array  questNames ()
        {
            return this.m_questNames;
        }//end

        public void  refreshIcons ()
        {
            return;
        }//end

    }

import Modules.quest.Display.QuestIndicatorSprite;

class IconData
    public String name ;
    public QuestIndicatorSprite banner ;
    public Function clickCallback ;

     IconData (String param1 ,Function param2 )
    {
        this.name = param1;
        this.clickCallback = param2;
        this.banner = null;
        return;
    }//end




