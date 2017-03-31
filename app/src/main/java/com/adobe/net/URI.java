package com.adobe.net;

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

    public class URI
    {
        protected boolean _valid =false ;
        protected boolean _relative =false ;
        protected String _scheme ="";
        protected String _authority ="";
        protected String _username ="";
        protected String _password ="";
        protected String _port ="";
        protected String _path ="";
        protected String _query ="";
        protected String _fragment ="";
        protected String _nonHierarchical ="";
        public static  String URImustEscape =" %";
        public static  String URIbaselineEscape =URImustEscape +":?#/@";
        public static  String URIpathEscape =URImustEscape +"?#";
        public static  String URIqueryEscape =URImustEscape +"#";
        public static  String URIqueryPartEscape =URImustEscape +"#&=";
        public static  String URInonHierEscape =URImustEscape +"?#/";
        public static  String UNKNOWN_SCHEME ="unknown";
        public static  URIEncodingBitmap URIbaselineExcludedBitmap =new URIEncodingBitmap(URIbaselineEscape );
        public static  URIEncodingBitmap URIschemeExcludedBitmap =URIbaselineExcludedBitmap ;
        public static  URIEncodingBitmap URIuserpassExcludedBitmap =URIbaselineExcludedBitmap ;
        public static  URIEncodingBitmap URIauthorityExcludedBitmap =URIbaselineExcludedBitmap ;
        public static  URIEncodingBitmap URIportExludedBitmap =URIbaselineExcludedBitmap ;
        public static  URIEncodingBitmap URIpathExcludedBitmap =new URIEncodingBitmap(URIpathEscape );
        public static  URIEncodingBitmap URIqueryExcludedBitmap =new URIEncodingBitmap(URIqueryEscape );
        public static  URIEncodingBitmap URIqueryPartExcludedBitmap =new URIEncodingBitmap(URIqueryPartEscape );
        public static  URIEncodingBitmap URIfragmentExcludedBitmap =URIqueryExcludedBitmap ;
        public static  URIEncodingBitmap URInonHierexcludedBitmap =new URIEncodingBitmap(URInonHierEscape );
        public static  int NOT_RELATED =0;
        public static  int CHILD =1;
        public static  int EQUAL =2;
        public static  int PARENT =3;
        public static IURIResolver _resolver =null ;

        public void  URI (String param1)
        {
            if (param1 == null)
            {
                this.initialize();
            }
            else
            {
                this.constructURI(param1);
            }
            return;
        }//end

        protected boolean  constructURI (String param1 )
        {
            if (!this.parseURI(param1))
            {
                this._valid = false;
            }
            return this.isValid();
        }//end

        protected void  initialize ()
        {
            this._valid = false;
            this._relative = false;
            this._scheme = UNKNOWN_SCHEME;
            this._authority = "";
            this._username = "";
            this._password = "";
            this._port = "";
            this._path = "";
            this._query = "";
            this._fragment = "";
            this._nonHierarchical = "";
            return;
        }//end

        protected void  hierState (boolean param1 )
        {
            if (param1 !=null)
            {
                this._nonHierarchical = "";
                if (this._scheme == "" || this._scheme == UNKNOWN_SCHEME)
                {
                    this._relative = true;
                }
                else
                {
                    this._relative = false;
                }
                if (this._authority.length == 0 && this._path.length == 0)
                {
                    this._valid = false;
                }
                else
                {
                    this._valid = true;
                }
            }
            else
            {
                this._authority = "";
                this._username = "";
                this._password = "";
                this._port = "";
                this._path = "";
                this._relative = false;
                if (this._scheme == "" || this._scheme == UNKNOWN_SCHEME)
                {
                    this._valid = false;
                }
                else
                {
                    this._valid = true;
                }
            }
            return;
        }//end

        protected boolean  hierState ()
        {
            return this._nonHierarchical.length == 0;
        }//end

        protected boolean  validateURI ()
        {
            if (this.isAbsolute())
            {
                if (this._scheme.length <= 1 || this._scheme == UNKNOWN_SCHEME)
                {
                    return false;
                }
                if (this.verifyAlpha(this._scheme) == false)
                {
                    return false;
                }
            }
            if (this.hierState)
            {
                if (this._path.search("\\") != -1)
                {
                    return false;
                }
                if (this.isRelative() == false && this._scheme == UNKNOWN_SCHEME)
                {
                    return false;
                }
            }
            else if (this._nonHierarchical.search("\\") != -1)
            {
                return false;
            }
            return true;
        }//end

        protected boolean  parseURI (String param1 )
        {
            int _loc_3 =0;
            int _loc_4 =0;
            _loc_2 = param1;
            this.initialize();
            _loc_3 = _loc_2.indexOf("#");
            if (_loc_3 != -1)
            {
                if (_loc_2.length > (_loc_3 + 1))
                {
                    this._fragment = _loc_2.substr((_loc_3 + 1), _loc_2.length - (_loc_3 + 1));
                }
                _loc_2 = _loc_2.substr(0, _loc_3);
            }
            _loc_3 = _loc_2.indexOf("?");
            if (_loc_3 != -1)
            {
                if (_loc_2.length > (_loc_3 + 1))
                {
                    this._query = _loc_2.substr((_loc_3 + 1), _loc_2.length - (_loc_3 + 1));
                }
                _loc_2 = _loc_2.substr(0, _loc_3);
            }
            _loc_3 = _loc_2.search(":");
            _loc_4 = _loc_2.search("/");
            _loc_5 = _loc_3!= -1;
            _loc_6 = _loc_4!= -1;
            _loc_7 = _loc_4==-1|| _loc_3 < _loc_4;
            if (_loc_5 && _loc_7)
            {
                this._scheme = _loc_2.substr(0, _loc_3);
                this._scheme = this._scheme.toLowerCase();
                _loc_2 = _loc_2.substr((_loc_3 + 1));
                if (_loc_2.substr(0, 2) == "//")
                {
                    this._nonHierarchical = "";
                    _loc_2 = _loc_2.substr(2, _loc_2.length - 2);
                }
                else
                {
                    this._nonHierarchical = _loc_2;
                    _loc_8 = this.validateURI ();
                    this._valid = this.validateURI();
                    if (_loc_8 == false)
                    {
                        this.initialize();
                    }
                    return this.isValid();
                }
            }
            else
            {
                this._scheme = "";
                this._relative = true;
                this._nonHierarchical = "";
            }
            if (this.isRelative())
            {
                this._authority = "";
                this._port = "";
                this._path = _loc_2;
            }
            else
            {
                if (_loc_2.substr(0, 2) == "//")
                {
                    while (_loc_2.charAt(0) == "/")
                    {

                        _loc_2 = _loc_2.substr(1, (_loc_2.length - 1));
                    }
                }
                _loc_3 = _loc_2.search("/");
                if (_loc_3 == -1)
                {
                    this._authority = _loc_2;
                    this._path = "";
                }
                else
                {
                    this._authority = _loc_2.substr(0, _loc_3);
                    this._path = _loc_2.substr(_loc_3, _loc_2.length - _loc_3);
                }
                _loc_3 = this._authority.search("@");
                if (_loc_3 != -1)
                {
                    this._username = this._authority.substr(0, _loc_3);
                    this._authority = this._authority.substr((_loc_3 + 1));
                    _loc_3 = this._username.search(":");
                    if (_loc_3 != -1)
                    {
                        this._password = this._username.substring((_loc_3 + 1), this._username.length());
                        this._username = this._username.substr(0, _loc_3);
                    }
                    else
                    {
                        this._password = "";
                    }
                }
                else
                {
                    this._username = "";
                    this._password = "";
                }
                _loc_3 = this._authority.search(":");
                if (_loc_3 != -1)
                {
                    this._port = this._authority.substring((_loc_3 + 1), this._authority.length());
                    this._authority = this._authority.substr(0, _loc_3);
                }
                else
                {
                    this._port = "";
                }
                this._authority = this._authority.toLowerCase();
            }
            _loc_81 = this.validateURI();
            this._valid = this.validateURI();
            if (_loc_81 == false)
            {
                this.initialize();
            }
            return this.isValid();
        }//end

        public void  copyURI (URI param1 )
        {
            this._scheme = param1._scheme;
            this._authority = param1._authority;
            this._username = param1._username;
            this._password = param1._password;
            this._port = param1._port;
            this._path = param1._path;
            this._query = param1._query;
            this._fragment = param1._fragment;
            this._nonHierarchical = param1._nonHierarchical;
            this._valid = param1._valid;
            this._relative = param1._relative;
            return;
        }//end

        protected boolean  verifyAlpha (String param1 )
        {
            int _loc_3 =0;
            _loc_2 = [/^a-z]""[^a-z]/;
            param1 = param1.toLowerCase();
            _loc_3 = param1.search(_loc_2);
            if (_loc_3 == -1)
            {
                return true;
            }
            return false;
        }//end

        public boolean  isValid ()
        {
            return this._valid;
        }//end

        public boolean  isAbsolute ()
        {
            return !this._relative;
        }//end

        public boolean  isRelative ()
        {
            return this._relative;
        }//end

        public boolean  isDirectory ()
        {
            if (this._path.length == 0)
            {
                return false;
            }
            return this._path.charAt((this.path.length - 1)) == "/";
        }//end

        public boolean  isHierarchical ()
        {
            return this.hierState;
        }//end

        public String  scheme ()
        {
            return URI.unescapeChars(this._scheme);
        }//end

        public void  scheme (String param1 )
        {
            _loc_2 = param1.toLowerCase ();
            this._scheme = URI.fastEscapeChars(_loc_2, URI.URIschemeExcludedBitmap);
            return;
        }//end

        public String  authority ()
        {
            return URI.unescapeChars(this._authority);
        }//end

        public void  authority (String param1 )
        {
            param1 = param1.toLowerCase();
            this._authority = URI.fastEscapeChars(param1, URI.URIauthorityExcludedBitmap);
            this.hierState = true;
            return;
        }//end

        public String  username ()
        {
            return URI.unescapeChars(this._username);
        }//end

        public void  username (String param1 )
        {
            this._username = URI.fastEscapeChars(param1, URI.URIuserpassExcludedBitmap);
            this.hierState = true;
            return;
        }//end

        public String  password ()
        {
            return URI.unescapeChars(this._password);
        }//end

        public void  password (String param1 )
        {
            this._password = URI.fastEscapeChars(param1, URI.URIuserpassExcludedBitmap);
            this.hierState = true;
            return;
        }//end

        public String  port ()
        {
            return URI.unescapeChars(this._port);
        }//end

        public void  port (String param1 )
        {
            this._port = URI.escapeChars(param1);
            this.hierState = true;
            return;
        }//end

        public String  path ()
        {
            return URI.unescapeChars(this._path);
        }//end

        public void  path (String param1 )
        {
            this._path = URI.fastEscapeChars(param1, URI.URIpathExcludedBitmap);
            if (this._scheme == UNKNOWN_SCHEME)
            {
                this._scheme = "";
            }
            this.hierState = true;
            return;
        }//end

        public String  query ()
        {
            return URI.unescapeChars(this._query);
        }//end

        public void  query (String param1 )
        {
            this._query = URI.fastEscapeChars(param1, URI.URIqueryExcludedBitmap);
            return;
        }//end

        public String  queryRaw ()
        {
            return this._query;
        }//end

        public void  queryRaw (String param1 )
        {
            this._query = param1;
            return;
        }//end

        public String  fragment ()
        {
            return URI.unescapeChars(this._fragment);
        }//end

        public void  fragment (String param1 )
        {
            this._fragment = URI.fastEscapeChars(param1, URIfragmentExcludedBitmap);
            return;
        }//end

        public String  nonHierarchical ()
        {
            return URI.unescapeChars(this._nonHierarchical);
        }//end

        public void  nonHierarchical (String param1 )
        {
            this._nonHierarchical = URI.fastEscapeChars(param1, URInonHierexcludedBitmap);
            this.hierState = false;
            return;
        }//end

        public void  setParts (String param1 ,String param2 ,String param3 ,String param4 ,String param5 ,String param6 )
        {
            this.scheme = param1;
            this.authority = param2;
            this.port = param3;
            this.path = param4;
            this.query = param5;
            this.fragment = param6;
            this.hierState = true;
            return;
        }//end

        public boolean  isOfType (String param1 )
        {
            param1 = param1.toLowerCase();
            return this._scheme == param1;
        }//end

        public String  getQueryValue (String param1 )
        {
            Object _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            _loc_2 = this.getQueryByMap();
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3 == param1)
                {
                    _loc_4 = _loc_2.get(_loc_3);
                    return _loc_4;
                }
            }
            return new String("");
        }//end

        public void  setQueryValue (String param1 ,String param2 )
        {
            Object _loc_3 =null ;
            _loc_3 = this.getQueryByMap();
            _loc_3.put(param1,  param2);
            this.setQueryByMap(_loc_3);
            return;
        }//end

        public Object  getQueryByMap ()
        {
            String _loc_1 =null ;
            String _loc_2 =null ;
            Array _loc_3 =null ;
            Array _loc_4 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            int _loc_7 =0;
            _loc_8 = new Object ();
            _loc_1 = this._query;
            _loc_3 = _loc_1.split("&");
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_2 = _loc_3.get(i0);

                if (_loc_2.length == 0)
                {
                    continue;
                }
                _loc_4 = _loc_2.split("=");
                if (_loc_4.length > 0)
                {
                    _loc_5 = _loc_4.get(0);
                }
                else
                {
                    continue;
                }
                if (_loc_4.length > 1)
                {
                    _loc_6 = _loc_4.get(1);
                }
                else
                {
                    _loc_6 = "";
                }
                _loc_5 = queryPartUnescape(_loc_5);
                _loc_6 = queryPartUnescape(_loc_6);
                _loc_8.put(_loc_5,  _loc_6);
            }
            return _loc_8;
        }//end

        public void  setQueryByMap (Object param1 )
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_5 ="";
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            		_loc_2 = param1.get(i0);

                _loc_3 = _loc_2;
                _loc_4 = param1.get(_loc_2);
                if (_loc_4 == null)
                {
                    _loc_4 = "";
                }
                _loc_3 = queryPartEscape(_loc_3);
                _loc_4 = queryPartEscape(_loc_4);
                _loc_6 = _loc_3;
                if (_loc_4.length > 0)
                {
                    _loc_6 = _loc_6 + "=";
                    _loc_6 = _loc_6 + _loc_4;
                }
                if (_loc_5.length != 0)
                {
                    _loc_5 = _loc_5 + "&";
                }
                _loc_5 = _loc_5 + _loc_6;
            }
            this._query = _loc_5;
            return;
        }//end

        public String  toString ()
        {
            if (this == null)
            {
                return "";
            }
            return this.toStringInternal(false);
        }//end

        public String  toDisplayString ()
        {
            return this.toStringInternal(true);
        }//end

        protected String  toStringInternal (boolean param1 )
        {
            String _loc_2 ="";
            String _loc_3 ="";
            if (this.isHierarchical() == false)
            {
                _loc_2 = _loc_2 + (param1 ? (this.scheme) : (this._scheme));
                _loc_2 = _loc_2 + ":";
                _loc_2 = _loc_2 + (param1 ? (this.nonHierarchical) : (this._nonHierarchical));
            }
            else
            {
                if (this.isRelative() == false)
                {
                    if (this._scheme.length != 0)
                    {
                        _loc_3 = param1 ? (this.scheme) : (this._scheme);
                        _loc_2 = _loc_2 + (_loc_3 + ":");
                    }
                    if (this._authority.length != 0 || this.isOfType("file"))
                    {
                        _loc_2 = _loc_2 + "//";
                        if (this._username.length != 0)
                        {
                            _loc_3 = param1 ? (this.username) : (this._username);
                            _loc_2 = _loc_2 + _loc_3;
                            if (this._password.length != 0)
                            {
                                _loc_3 = param1 ? (this.password) : (this._password);
                                _loc_2 = _loc_2 + (":" + _loc_3);
                            }
                            _loc_2 = _loc_2 + "@";
                        }
                        _loc_3 = param1 ? (this.authority) : (this._authority);
                        _loc_2 = _loc_2 + _loc_3;
                        if (this.port.length != 0)
                        {
                            _loc_2 = _loc_2 + (":" + this.port);
                        }
                    }
                }
                _loc_3 = param1 ? (this.path) : (this._path);
                _loc_2 = _loc_2 + _loc_3;
            }
            if (this._query.length != 0)
            {
                _loc_3 = param1 ? (this.query) : (this._query);
                _loc_2 = _loc_2 + ("?" + _loc_3);
            }
            if (this.fragment.length != 0)
            {
                _loc_3 = param1 ? (this.fragment) : (this._fragment);
                _loc_2 = _loc_2 + ("#" + _loc_3);
            }
            return _loc_2;
        }//end

        public void  forceEscape ()
        {
            this.scheme = this.scheme;
            this.setQueryByMap(this.getQueryByMap());
            this.fragment = this.fragment;
            if (this.isHierarchical())
            {
                this.authority = this.authority;
                this.path = this.path;
                this.port = this.port;
                this.username = this.username;
                this.password = this.password;
            }
            else
            {
                this.nonHierarchical = this.nonHierarchical;
            }
            return;
        }//end

        public boolean  isOfFileType (String param1 )
        {
            String _loc_2 =null ;
            int _loc_3 =0;
            _loc_3 = param1.lastIndexOf(".");
            if (_loc_3 != -1)
            {
                param1 = param1.substr((_loc_3 + 1));
            }
            _loc_2 = this.getExtension(true);
            if (_loc_2 == "")
            {
                return false;
            }
            if (compareStr(_loc_2, param1, false) == 0)
            {
                return true;
            }
            return false;
        }//end

        public String  getExtension (boolean param1 =false )
        {
            String _loc_3 =null ;
            int _loc_4 =0;
            _loc_2 = this.getFilename ();
            if (_loc_2 == "")
            {
                return String("");
            }
            _loc_4 = _loc_2.lastIndexOf(".");
            if (_loc_4 == -1 || _loc_4 == 0)
            {
                return String("");
            }
            _loc_3 = _loc_2.substr(_loc_4);
            if (param1 && _loc_3.charAt(0) == ".")
            {
                _loc_3 = _loc_3.substr(1);
            }
            return _loc_3;
        }//end

        public String  getFilename (boolean param1 =false )
        {
            String _loc_3 =null ;
            int _loc_4 =0;
            if (this.isDirectory())
            {
                return String("");
            }
            _loc_2 = this.path ;
            _loc_4 = _loc_2.lastIndexOf("/");
            if (_loc_4 != -1)
            {
                _loc_3 = _loc_2.substr((_loc_4 + 1));
            }
            else
            {
                _loc_3 = _loc_2;
            }
            if (param1 !=null)
            {
                _loc_4 = _loc_3.lastIndexOf(".");
                if (_loc_4 != -1)
                {
                    _loc_3 = _loc_3.substr(0, _loc_4);
                }
            }
            return _loc_3;
        }//end

        public String  getDefaultPort ()
        {
            if (this._scheme == "http")
            {
                return String("80");
            }
            if (this._scheme == "ftp")
            {
                return String("21");
            }
            if (this._scheme == "file")
            {
                return String("");
            }
            if (this._scheme == "sftp")
            {
                return String("22");
            }
            return String("");
        }//end

        public int  getRelation (URI param1 ,boolean param2 =true )
        {
            Array _loc_9 =null ;
            Array _loc_10 =null ;
            String _loc_11 =null ;
            String _loc_12 =null ;
            int _loc_13 =0;
            _loc_3 = URI.resolve(this);
            _loc_4 = URI.resolve(param1);
            if (_loc_3.isRelative() || _loc_4.isRelative())
            {
                return URI.NOT_RELATED;
            }
            if (_loc_3.isHierarchical() == false || _loc_4.isHierarchical() == false)
            {
                if (_loc_3.isHierarchical() == false && _loc_4.isHierarchical() == true || _loc_3.isHierarchical() == true && _loc_4.isHierarchical() == false)
                {
                    return URI.NOT_RELATED;
                }
                if (_loc_3.scheme != _loc_4.scheme)
                {
                    return URI.NOT_RELATED;
                }
                if (_loc_3.nonHierarchical != _loc_4.nonHierarchical)
                {
                    return URI.NOT_RELATED;
                }
                return URI.EQUAL;
            }
            if (_loc_3.scheme != _loc_4.scheme)
            {
                return URI.NOT_RELATED;
            }
            if (_loc_3.authority != _loc_4.authority)
            {
                return URI.NOT_RELATED;
            }
            _loc_5 = _loc_3.port ;
            _loc_6 = _loc_4.port ;
            if (_loc_5 == "")
            {
                _loc_5 = _loc_3.getDefaultPort();
            }
            if (_loc_6 == "")
            {
                _loc_6 = _loc_4.getDefaultPort();
            }
            if (_loc_5 != _loc_6)
            {
                return URI.NOT_RELATED;
            }
            if (compareStr(_loc_3.path, _loc_4.path, param2))
            {
                return URI.EQUAL;
            }
            _loc_7 = _loc_3.path;
            _loc_8 = _loc_4.path;
            if ((_loc_7 == "/" || _loc_8 == "/") && (_loc_7 == "" || _loc_8 == ""))
            {
                return URI.EQUAL;
            }
            _loc_9 = _loc_7.split("/");
            _loc_10 = _loc_8.split("/");
            if (_loc_9.length > _loc_10.length())
            {
                _loc_12 = _loc_10.get((_loc_10.length - 1));
                if (_loc_12.length > 0)
                {
                    return URI.NOT_RELATED;
                }
                _loc_10.pop();
                _loc_13 = 0;
                while (_loc_13 < _loc_10.length())
                {

                    _loc_11 = _loc_9.get(_loc_13);
                    _loc_12 = _loc_10.get(_loc_13);
                    if (compareStr(_loc_11, _loc_12, param2) == false)
                    {
                        return URI.NOT_RELATED;
                    }
                    _loc_13++;
                }
                return URI.CHILD;
            }
            else
            {
                if (_loc_9.length < _loc_10.length())
                {
                    _loc_11 = _loc_9.get((_loc_9.length - 1));
                    if (_loc_11.length > 0)
                    {
                        return URI.NOT_RELATED;
                    }
                    _loc_9.pop();
                    _loc_13 = 0;
                    while (_loc_13 < _loc_9.length())
                    {

                        _loc_11 = _loc_9.get(_loc_13);
                        _loc_12 = _loc_10.get(_loc_13);
                        if (compareStr(_loc_11, _loc_12, param2) == false)
                        {
                            return URI.NOT_RELATED;
                        }
                        _loc_13++;
                    }
                    return URI.PARENT;
                }
                else
                {
                    return URI.NOT_RELATED;
                }
            }
        }//end

        public URI  getCommonParent (URI param1 ,boolean param2 =true )
        {
            String _loc_6 =null ;
            String _loc_7 =null ;
            _loc_3 = URI.resolve(this);
            _loc_4 = URI.resolve(param1);
            if (!_loc_3.isAbsolute() || !_loc_4.isAbsolute() || _loc_3.isHierarchical() == false || _loc_4.isHierarchical() == false)
            {
                return null;
            }
            _loc_5 = _loc_3.getRelation(_loc_4 );
            if (_loc_3.getRelation(_loc_4) == URI.NOT_RELATED)
            {
                return null;
            }
            _loc_3.chdir(".");
            _loc_4.chdir(".");
            do
            {

                _loc_5 = _loc_3.getRelation(_loc_4, param2);
                if (_loc_5 == URI.EQUAL || _loc_5 == URI.PARENT)
                {
                    break;
                }
                _loc_6 = _loc_3.toString();
                _loc_3.chdir("..");
                _loc_7 = _loc_3.toString();
            }while (_loc_6 != _loc_7)
            return _loc_3;
        }//end

        public boolean  chdir (String param1 ,boolean param2 =false )
        {
            URI _loc_3 =null ;
            String _loc_5 =null ;
            String _loc_6 =null ;
            Array _loc_7 =null ;
            Array _loc_8 =null ;
            String _loc_14 =null ;
            int _loc_15 =0;
            String _loc_17 =null ;
            _loc_4 = param1;
            if (param2)
            {
                _loc_4 = URI.escapeChars(param1);
            }
            if (_loc_4 == "")
            {
                return true;
            }
            if (_loc_4.substr(0, 2) == "//")
            {
                _loc_17 = this.scheme + ":" + _loc_4;
                return this.constructURI(_loc_17);
            }
            if (_loc_4.charAt(0) == "?")
            {
                _loc_4 = "./" + _loc_4;
            }
            _loc_3 = new URI(_loc_4);
            if (_loc_3.isAbsolute() || _loc_3.isHierarchical() == false)
            {
                this.copyURI(_loc_3);
                return true;
            }
            boolean _loc_9 =false ;
            boolean _loc_10 =false ;
            boolean _loc_11 =false ;
            boolean _loc_12 =false ;
            boolean _loc_13 =false ;
            _loc_5 = this.path;
            _loc_6 = _loc_3.path;
            if (_loc_5.length > 0)
            {
                _loc_7 = _loc_5.split("/");
            }
            else
            {
                _loc_7 = new Array();
            }
            if (_loc_6.length > 0)
            {
                _loc_8 = _loc_6.split("/");
            }
            else
            {
                _loc_8 = new Array();
            }
            if (_loc_7.length > 0 && _loc_7.get(0) == "")
            {
                _loc_11 = true;
                _loc_7.shift();
            }
            if (_loc_7.length > 0 && _loc_7.get((_loc_7.length - 1)) == "")
            {
                _loc_9 = true;
                _loc_7.pop();
            }
            if (_loc_8.length > 0 && _loc_8.get(0) == "")
            {
                _loc_12 = true;
                _loc_8.shift();
            }
            if (_loc_8.length > 0 && _loc_8.get((_loc_8.length - 1)) == "")
            {
                _loc_10 = true;
                _loc_8.pop();
            }
            if (_loc_12)
            {
                this.path = _loc_3.path;
                this.queryRaw = _loc_3.queryRaw;
                this.fragment = _loc_3.fragment;
                return true;
            }
            if (_loc_8.length == 0 && _loc_3.query == "")
            {
                this.fragment = _loc_3.fragment;
                return true;
            }
            if (_loc_9 == false && _loc_7.length > 0)
            {
                _loc_7.pop();
            }
            this.queryRaw = _loc_3.queryRaw;
            this.fragment = _loc_3.fragment;
            _loc_7 = _loc_7.concat(_loc_8);
            _loc_15 = 0;
            while (_loc_15 < _loc_7.length())
            {

                _loc_14 = _loc_7.get(_loc_15);
                _loc_13 = false;
                if (_loc_14 == ".")
                {
                    _loc_7.splice(_loc_15, 1);
                    _loc_15 = _loc_15 - 1;
                    _loc_13 = true;
                }
                else if (_loc_14 == "..")
                {
                    if (_loc_15 >= 1)
                    {
                        if (_loc_7.get((_loc_15 - 1)) == "..")
                        {
                        }
                        else
                        {
                            _loc_7.splice((_loc_15 - 1), 2);
                            _loc_15 = _loc_15 - 2;
                        }
                    }
                    else if (this.isRelative())
                    {
                    }
                    else
                    {
                        _loc_7.splice(_loc_15, 1);
                        _loc_15 = _loc_15 - 1;
                    }
                    _loc_13 = true;
                }
                _loc_15++;
            }
            String _loc_16 ="";
            _loc_10 = _loc_10 || _loc_13;
            _loc_16 = this.joinPath(_loc_7, _loc_11, _loc_10);
            this.path = _loc_16;
            return true;
        }//end

        protected String  joinPath (Array param1 ,boolean param2 ,boolean param3 )
        {
            int _loc_5 =0;
            String _loc_4 ="";
            _loc_5 = 0;
            while (_loc_5 < param1.length())
            {

                if (_loc_4.length > 0)
                {
                    _loc_4 = _loc_4 + "/";
                }
                _loc_4 = _loc_4 + param1.get(_loc_5);
                _loc_5++;
            }
            if (param3 && _loc_4.length > 0)
            {
                _loc_4 = _loc_4 + "/";
            }
            if (param2)
            {
                _loc_4 = "/" + _loc_4;
            }
            return _loc_4;
        }//end

        public boolean  makeAbsoluteURI (URI param1 )
        {
            if (this.isAbsolute() || param1.isRelative())
            {
                return false;
            }
            _loc_2 = new URI ();
            _loc_2.copyURI(param1);
            if (_loc_2.chdir(this.toString()) == false)
            {
                return false;
            }
            this.copyURI(_loc_2);
            return true;
        }//end

        public boolean  makeRelativeURI (URI param1 ,boolean param2 =true )
        {
            Array _loc_4 =null ;
            Array _loc_5 =null ;
            String _loc_7 =null ;
            String _loc_8 =null ;
            String _loc_9 =null ;
            int _loc_13 =0;
            _loc_3 = new URI ();
            _loc_3.copyURI(param1);
            _loc_6 = new Array ();
            _loc_10 = this.path ;
            _loc_11 = this.queryRaw ;
            _loc_12 = this.fragment ;
            boolean _loc_14 =false ;
            boolean _loc_15 =false ;
            if (this.isRelative())
            {
                return true;
            }
            if (_loc_3.isRelative())
            {
                return false;
            }
            if (this.isOfType(param1.scheme) == false || this.authority != param1.authority)
            {
                return false;
            }
            _loc_15 = this.isDirectory();
            _loc_3.chdir(".");
            _loc_4 = _loc_10.split("/");
            _loc_5 = _loc_3.path.split("/");
            if (_loc_4.length > 0 && _loc_4.get(0) == "")
            {
                _loc_4.shift();
            }
            if (_loc_4.length > 0 && _loc_4.get((_loc_4.length - 1)) == "")
            {
                _loc_15 = true;
                _loc_4.pop();
            }
            if (_loc_5.length > 0 && _loc_5.get(0) == "")
            {
                _loc_5.shift();
            }
            if (_loc_5.length > 0 && _loc_5.get((_loc_5.length - 1)) == "")
            {
                _loc_5.pop();
            }
            while (_loc_5.length > 0)
            {

                if (_loc_4.length == 0)
                {
                    break;
                }
                _loc_7 = _loc_4.get(0);
                _loc_8 = _loc_5.get(0);
                if (compareStr(_loc_7, _loc_8, param2))
                {
                    _loc_4.shift();
                    _loc_5.shift();
                    continue;
                }
                break;
            }
            String _loc_16 ="..";
            _loc_13 = 0;
            while (_loc_13 < _loc_5.length())
            {

                _loc_6.push(_loc_16);
                _loc_13++;
            }
            _loc_6 = _loc_6.concat(_loc_4);
            _loc_9 = this.joinPath(_loc_6, false, _loc_15);
            if (_loc_9.length == 0)
            {
                _loc_9 = "./";
            }
            this.setParts("", "", "", _loc_9, _loc_11, _loc_12);
            return true;
        }//end

        public boolean  unknownToURI (String param1 ,String param2 ="http")
        {
            String _loc_3 =null ;
            String _loc_5 =null ;
            if (param1.length == 0)
            {
                this.initialize();
                return false;
            }
            param1 = param1.replace(/\\\"""\\/g, "/");
            if (param1.length >= 2)
            {
                _loc_3 = param1.substr(0, 2);
                if (_loc_3 == "//")
                {
                    param1 = param2 + ":" + param1;
                }
            }
            if (param1.length >= 3)
            {
                _loc_3 = param1.substr(0, 3);
                if (_loc_3 == "://")
                {
                    param1 = param2 + param1;
                }
            }
            _loc_4 = new URI(param1 );
            if (new URI(param1).isHierarchical() == false)
            {
                if (_loc_4.scheme == UNKNOWN_SCHEME)
                {
                    this.initialize();
                    return false;
                }
                this.copyURI(_loc_4);
                this.forceEscape();
                return true;
            }
            else if (_loc_4.scheme != UNKNOWN_SCHEME && _loc_4.scheme.length > 0)
            {
                if (_loc_4.authority.length > 0 || _loc_4.scheme == "file")
                {
                    this.copyURI(_loc_4);
                    this.forceEscape();
                    return true;
                }
                if (_loc_4.authority.length == 0 && _loc_4.path.length == 0)
                {
                    this.setParts(_loc_4.scheme, "", "", "", "", "");
                    return false;
                }
            }
            else
            {
                _loc_5 = _loc_4.path;
                if (_loc_5 == ".." || _loc_5 == "." || _loc_5.length >= 3 && _loc_5.substr(0, 3) == "../" || _loc_5.length >= 2 && _loc_5.substr(0, 2) == "./")
                {
                    this.copyURI(_loc_4);
                    this.forceEscape();
                    return true;
                }
            }
            _loc_4 = new URI(param2 + "://" + param1);
            if (_loc_4.scheme.length > 0 && _loc_4.authority.length > 0)
            {
                this.copyURI(_loc_4);
                this.forceEscape();
                return true;
            }
            this.initialize();
            return false;
        }//end

        public static String  escapeChars (String param1 )
        {
            return fastEscapeChars(param1, URI.URIbaselineExcludedBitmap);
        }//end

        public static String  unescapeChars (String param1 )
        {
            String _loc_2 =null ;
            _loc_2 = decodeURIComponent(param1);
            return _loc_2;
        }//end

        public static String  fastEscapeChars (String param1 ,URIEncodingBitmap param2 )
        {
            String _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            String _loc_3 ="";
            _loc_6 = 0;
            while (_loc_6 < param1.length())
            {

                _loc_4 = param1.charAt(_loc_6);
                _loc_5 = param2.ShouldEscape(_loc_4);
                if (_loc_5)
                {
                    _loc_4 = _loc_5.toString(16);
                    if (_loc_4.length == 1)
                    {
                        _loc_4 = "0" + _loc_4;
                    }
                    _loc_4 = "%" + _loc_4;
                    _loc_4 = _loc_4.toUpperCase();
                }
                _loc_3 = _loc_3 + _loc_4;
                _loc_6++;
            }
            return _loc_3;
        }//end

        public static String  queryPartEscape (String param1 )
        {
            _loc_2 = param1;
            _loc_2 = URI.fastEscapeChars(param1, URI.URIqueryPartExcludedBitmap);
            return _loc_2;
        }//end

        public static String  queryPartUnescape (String param1 )
        {
            _loc_2 = param1;
            _loc_2 = unescapeChars(_loc_2);
            return _loc_2;
        }//end

        public static boolean  compareStr (String param1 ,String param2 ,boolean param3 =true )
        {
            if (param3 == false)
            {
                param1 = param1.toLowerCase();
                param2 = param2.toLowerCase();
            }
            return param1 == param2;
        }//end

        public static URI  resolve (URI param1 )
        {
            _loc_2 = new URI ;
            _loc_2.copyURI(param1);
            if (_resolver != null)
            {
                return _resolver.resolve(_loc_2);
            }
            return _loc_2;
        }//end

        public static void  resolver (IURIResolver param1 )
        {
            _resolver = param1;
            return;
        }//end

        public static IURIResolver  resolver ()
        {
            return _resolver;
        }//end

    }

