package Engine.Managers;

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

import com.adobe.net.*;
//import flash.utils.*;

    public class AssetUrlManager
    {
        protected Dictionary m_domainPrefixMap ;
        protected Array m_domains ;
        protected Object m_index ;
        private Object m_packIndex ;
        private static AssetUrlManager m_instance ;

        public void  AssetUrlManager (Dictionary param1 ,String param2 ,String param3 )
        {
            String _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            Array _loc_7 =null ;
            String _loc_8 =null ;
            int _loc_9 =0;
            Array _loc_10 =null ;
            GlobalEngine.assert(AssetUrlManager.m_instance == null, "Multiple AssetUrlManagers instantiated");
            AssetUrlManager.m_instance = this;
            this.m_domainPrefixMap = new Dictionary();
            this.m_domains = new Array();
            if (param1 != null)
            {
                for(int i0 = 0; i0 < param1.size(); i0++)
                {
                	_loc_4 = param1.get(i0);

                    _loc_5 = this.getDomain(_loc_4);
                    _loc_6 = param1.get(_loc_4);
                    this.m_domainPrefixMap.put(_loc_5,  {assetPrefix:_loc_4, hashedPrefix:_loc_6});
                    this.m_domains.push(_loc_5);
                }
                this.m_domains.sort();
            }
            if (param2 != null)
            {
                this.m_index = new Object();
                _loc_7 = param2.replace(/(^\s*|\s*$)""(^\s*|\s*$)/, "").split("\n");
                _loc_9 = 0;
                while (_loc_9 < _loc_7.length())
                {

                    _loc_8 = _loc_7.get(_loc_9);
                    _loc_10 = _loc_8.split(":", 2);
                    if (_loc_10.length == 2 && ((String)_loc_10.get(1)).length > 0)
                    {
                        this.m_index.get(_loc_10.put(1),  _loc_10.get(0));
                    }
                    _loc_9++;
                }
            }
            if (param3 != null)
            {
                this.m_packIndex = new Object();
                _loc_7 = param3.replace(/(^\s*|\s*$)""(^\s*|\s*$)/, "").split("\n");
                _loc_9 = 0;
                while (_loc_9 < _loc_7.length())
                {

                    _loc_8 = _loc_7.get(_loc_9);
                    _loc_10 = _loc_8.split(":", 2);
                    if (_loc_10.length == 2 && ((String)_loc_10.get(1)).length > 0)
                    {
                        this.m_packIndex.get(_loc_10.put(1),  _loc_10.get(0));
                    }
                    _loc_9++;
                }
            }
            return;
        }//end

        public String  lookUpUrl (String param1 )
        {
            String _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            Array _loc_7 =null ;
            String _loc_8 =null ;
            _loc_2 = this.getDomain(param1 );
            _loc_3 = this.m_domainPrefixMap.get(_loc_2) ;
            if (this.m_index != null && _loc_3 != null)
            {
                _loc_4 = _loc_3.assetPrefix;
                if (param1.indexOf(_loc_4) == 0)
                {
                    _loc_5 = param1.substr(_loc_4.length());
                    if (this.m_index.get(_loc_5))
                    {
                        _loc_6 = this.getHashedPrefix(this.m_index.get(_loc_5));
                        _loc_7 = param1.split(".");

                        _loc_8 = "." + _loc_7.get((_loc_7.length - 1));

			if(this.m_index.get(_loc_5).indexOf("f") == 0) {
				return _loc_4+"hashed2/"+this.m_index.get(_loc_5) + _loc_8;
			}

                        return _loc_6 + this.m_index.get(_loc_5) + _loc_8;
                    }
                }
            }
            return param1;
        }//end

        public boolean  isPackedResource (String param1 )
        {
            return this.lookupPackedResourceUrl(param1) != null;
        }//end

        public String  lookupPackedResourceUrl (String param1 )
        {
            String _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            _loc_2 = this.getDomain(param1 );
            _loc_3 = this.m_domainPrefixMap.get(_loc_2) ;
            if (this.m_packIndex != null && _loc_3 != null)
            {
                _loc_4 = _loc_3.assetPrefix;
                if (param1.indexOf(_loc_4) == 0)
                {
                    _loc_5 = param1.substr(_loc_4.length());
                    if (this.m_packIndex.get(_loc_5))
                    {
                        _loc_6 = this.getHashedPrefix(this.m_packIndex.get(_loc_5));
                        return _loc_6 + this.m_packIndex.get(_loc_5) + ".zip";
                    }
                }
            }
            return null;
        }//end

        public boolean  isStaticAssetDomain (String param1 )
        {
            _loc_2 = this.getDomain(param1 );
            if (this.m_domainPrefixMap.get(_loc_2) != null)
            {
                return true;
            }
            return false;
        }//end

        private String  getDomain (String param1 )
        {
            URI _loc_2 =new URI(param1 );
            return _loc_2.authority;
        }//end

        private String  getHashedPrefix (String param1 )
        {
            String _loc_3 =null ;
            int _loc_4 =0;
            _loc_2 = this.m_domains.get(0) ;
            if (this.m_domains.length > 1)
            {
                _loc_3 = param1.substr(Math.max(0, param1.length - 8), param1.length());
                _loc_4 =(uint) parseInt(_loc_3, 16);
                _loc_2 = this.m_domains.get(_loc_4 % this.m_domains.length());
            }
            return this.m_domainPrefixMap.get(_loc_2).hashedPrefix;
        }//end

        public static AssetUrlManager  instance ()
        {
            GlobalEngine.assert(AssetUrlManager.m_instance != null, "AssetUrlManager not instantiated");
            return AssetUrlManager.m_instance;
        }//end

        public static boolean  initialized ()
        {
            return AssetUrlManager.m_instance != null;
        }//end

    }



