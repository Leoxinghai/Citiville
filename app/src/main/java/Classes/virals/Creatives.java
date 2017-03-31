package Classes.virals;

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
import Engine.Managers.*;
import Modules.stats.experiments.*;
import com.adobe.utils.*;
//import flash.utils.*;
import com.facebook.utils.*;
import com.xinghai.Debug;

    public class Creatives
    {
        private String m_configUrl ;
        private Object m_feeds ;
        private CreativeFeed m_creativeFeed ;
        private Object m_requests ;
        private boolean m_loaded =false ;
        private Array m_callbacks ;
        public static  String FEEDS_TEXT_DELIMETER =":";
        public static  String MEDIA_URL_TAG ="{ASSET_URL}";

        public  Creatives (String param1 )
        {
            this.m_configUrl = param1;
            this.m_feeds = {};
            this.m_callbacks = new Array();
            if (param1 !=null)
            {
                Global.delayedAssets.get(param1, this.loadCreatives, DelayedAssetLoader.LOADTYPE_BINARY, true);
            }
            if (Global.creatives == null)
            {
                Global.creatives = this;
            }
            return;
        }//end

        public void  loadCreatives (Object param1 ,String param2)
        {
            XML _loc_3 =null ;
            Function _loc_4 =null ;
            Object _loc_5 =null ;
            XMLList _loc_6 =null ;
            Object _loc_7 =null ;
            String _loc_8 =null ;
            if (param1 instanceof XML)
            {
                _loc_3 = param1;
            }
            else if (param1 instanceof String)
            {
                _loc_3 = XML(param1);
            }
            else if (param1 instanceof ByteArray)
            {
                _loc_3 = XML(((ByteArray)param1).toString());
            }
            else
            {
                ErrorManager.addError("Improper data in Creatives, load failed!");
            }
            if (_loc_3)
            {
                _loc_5 = FacebookXMLParserUtils.xmlToObject(_loc_3);
                _loc_6 = _loc_5.feeds.feed;
                for(int i0 = 0; i0 < _loc_6.size(); i0++)
                {
                	_loc_7 = _loc_6.get(i0);

                    _loc_8 = _loc_7.name;
                    if (!this.m_feeds.hasOwnProperty(_loc_8))
                    {
                        this.m_feeds.put(_loc_8,  _loc_7);
                        continue;
                    }
                    ErrorManager.addError("Duplicate Feed Name.  Feed name already exists: " + _loc_8);
                }
            }
            for(int i0 = 0; i0 < this.m_callbacks.size(); i0++)
            {
            	_loc_4 = this.m_callbacks.get(i0);

                if (_loc_4 != null)
                {
                    _loc_4();
                }
            }
            this.m_callbacks = new Array();
            this.m_loaded = true;
            return;
        }//end

        public void  registerOnLoadCallback (Function param1 )
        {
            if (param1 != null)
            {
                if (!this.m_loaded)
                {
                    if (!this.m_callbacks)
                    {
                        this.m_callbacks = new Array();
                    }
                    if (!ArrayUtil.arrayContainsValue(this.m_callbacks, param1))
                    {
                        this.m_callbacks.push(param1);
                    }
                }
                else
                {
                    param1();
                }
            }
            return;
        }//end

        public CreativeFeed  getFeed (String param1 ,Object param2 )
        {
            if (!this.m_creativeFeed)
            {
                this.m_creativeFeed = new CreativeFeed();
            }
            this.m_creativeFeed.data = this.m_feeds.get(param1);
            this.m_creativeFeed.viral = param2;
            return this.m_creativeFeed;
        }//end

        public String  getFeedName (String param1 ,Object param2 )
        {
            Object _loc_6 =null ;
            _loc_3 = param1;
            if (param2 && (param1 == ViralType.BUSINESS_OPENING || param1 == ViralType.HARVEST_BUSINESS_MASTERY))
            {
                _loc_3 = param1 + "_" + param2.businessId;
                if (!this.m_feeds.hasOwnProperty(_loc_3))
                {
                    _loc_3 = param1;
                }
                else
                {
                    param1 = _loc_3;
                }
            }
            _loc_4 =Global.experimentManager ;
            _loc_5 =Global.experimentManager.getExperiment(ExperimentDefinitions.FEED_AB_TEST );
            if (Global.experimentManager.getExperiment(ExperimentDefinitions.FEED_AB_TEST))
            {
                switch(_loc_5.variant)
                {
                    case ExperimentDefinitions.FEED_TEST_GROUP_A:
                    {
                        _loc_3 = param1 + "_a";
                        break;
                    }
                    default:
                    {
                        _loc_3 = null;
                        break;
                        break;
                    }
                }
                if (_loc_3)
                {
                    _loc_6 = this.m_feeds.hasOwnProperty(_loc_3) ? (this.m_feeds.get(_loc_3)) : (null);
                    if (!_loc_6 || !_loc_6.hasOwnProperty("feedUnderTest") || _loc_6.feedUnderTest != param1)
                    {
                        if (_loc_6)
                        {
                            ErrorManager.addError("AB Feed test found for \'" + _loc_3 + "\', but the " + "feed was either missing @feedUnderTest or the " + "@feedUnderTest value was not equal to \'" + param1 + "\'");
                        }
                        _loc_3 = param1;
                    }
                }
                else
                {
                    _loc_3 = param1;
                }
            }
            return _loc_3;
        }//end

    }



