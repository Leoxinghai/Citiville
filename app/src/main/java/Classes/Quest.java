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

import Engine.Managers.*;
import Transactions.*;
import root.GlobalEngine;

import java.util.Vector;


    public class Quest
    {
        protected XML m_xml =null ;
        protected String m_name ;
        private String m_icon ;
        private String m_feed ;
        private int m_delay ;
        private int m_popupDelay ;
        private boolean m_visible ;
        private Vector<Object> m_tasks;
        private Vector<String> m_rewards;
        private Vector<Boolean> m_purchased;
        private Object m_extraData ;
        private double m_activatedTime =0;
        private boolean m_activatable ;
        private boolean m_autoActivate =false ;

        public  Quest (XML param1 )
        {
            Object _loc_3 =null ;
            this.m_tasks = new Vector<Object>();
            this.m_rewards = new Vector<String>();
            this.m_purchased = new Vector<Boolean>();
            this.m_xml = param1;
            this.m_name = String(param1.@name);
            this.m_icon = String(param1.@url);
            this.m_feed = String(param1.@feed);
            this.m_delay = int(param1.@delay);
            this.m_popupDelay = int(param1.@popup_delay);
            this.m_visible = String(param1.@visible) == "true";
            this.m_extraData = null;
            this.m_activatable = String(param1.@activatable) == "true";
            this.m_autoActivate = String(this.m_xml.@autoActivate) == "true";
            int _loc_2 =0;
            param1 = null;
            _loc_2 = 0;
            while (_loc_2 < this.m_xml.tasks.task.length())
            {

                param1 = this.m_xml.tasks.task.get(_loc_2);
                _loc_3 = new Object();
                _loc_3.action = String(param1.@action);
                _loc_3.type = String(param1.@type);
                _loc_3.total = int(param1.@total);
                _loc_3.max = int(param1.@max);
                _loc_3.filter = String(param1.@filter);
                _loc_3.sticky = String(param1.@sticky) == "true";
                _loc_3.stickyType = String(param1.@stickyType) ? (String(param1.@stickyType)) : (StickyType.NONE);
                _loc_3.percent = String(param1.@percent) == "true";
                _loc_3.icon = String(param1.@icon_url);
                _loc_3.footerId = String(param1.@footerId);
                if (param1.overrideTable.length())
                {
                    _loc_3.overrideTable = new Object();
                    _loc_3.overrideTable.table = String(param1.overrideTable.@table);
                    _loc_3.overrideTable.keyword = String(param1.overrideTable.@keyword);
                }
                this.m_tasks.push(_loc_3);
                this.m_purchased.push(false);
                _loc_2++;
            }
            _loc_2 = 0;
            while (_loc_2 < this.m_xml.rewards.reward.length())
            {

                this.m_rewards.push(String(this.m_xml.rewards.reward.get(_loc_2).@url));
                _loc_2++;
            }
            return;
        }//end

        public void  addExtraData (Object param1 )
        {
            this.m_extraData = param1;
            return;
        }//end

        public Object  extraData ()
        {
            return this.m_extraData;
        }//end

        public void  setActivatedTime (double param1 )
        {
            this.m_activatedTime = param1;
            return;
        }//end

        public double  activatedTime ()
        {
            return this.m_activatedTime;
        }//end

        public String  name ()
        {
            return this.m_name;
        }//end

        public String  icon ()
        {
            return this.m_icon;
        }//end

        public String  feed ()
        {
            return this.m_feed;
        }//end

        public int  delay ()
        {
            return this.m_delay;
        }//end

        public int  popupDelay ()
        {
            return this.m_popupDelay;
        }//end

        public boolean  visible ()
        {
            return this.m_visible;
        }//end

        public Vector tasks ()
        {
            return this.m_tasks;
        }//end

        public Vector<String>  rewards ()
        {
            return this.m_rewards;
        }//end

        public Vector<Boolean>  purchased ()
        {
            return this.m_purchased;
        }//end

        public boolean  isActivatable ()
        {
            return this.m_activatable;
        }//end

        public boolean  autoActivate ()
        {
            return this.m_autoActivate;
        }//end

        public boolean  isActivated ()
        {
            return !this.isActivatable() || this.activatedTime > 0;
        }//end

        public void  activate ()
        {
            if (this.isActivated())
            {
                return;
            }
            this.setActivatedTime(GlobalEngine.serverTime / 1000);
            TActivateQuest _loc_1 =new TActivateQuest(this.m_name );
            TransactionManager.addTransaction(_loc_1);
            return;
        }//end

        public boolean  wasAnyTaskPurchased ()
        {
            boolean _loc_1 =false ;
            for(int i0 = 0; i0 < this.m_purchased.size(); i0++)
            {
            	_loc_1 = this.m_purchased.get(i0);

                if (_loc_1)
                {
                    return true;
                }
            }
            return false;
        }//end

    }



