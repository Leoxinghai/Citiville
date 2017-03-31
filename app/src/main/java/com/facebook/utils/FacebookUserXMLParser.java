package com.facebook.utils;

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

import com.facebook.data.*;
import com.facebook.data.users.*;

    public class FacebookUserXMLParser
    {

        public  FacebookUserXMLParser ()
        {
            return;
        }//end

        public static Array  parseWorkHistory (XML param1 ,Namespace param2 )
        {
            Object _loc_5 =null ;
            FacebookWorkInfo _loc_6 =null ;
            Array _loc_3 =new Array();
            _loc_4 = param1.children ();
            for(int i0 = 0; i0 < _loc_4.size(); i0++) 
            {
            		_loc_5 = _loc_4.get(i0);

                _loc_6 = new FacebookWorkInfo();
                _loc_6.location = FacebookXMLParserUtils.createLocation(param2::location.get(0), param2);
                _loc_6.company_name = String(param2::company_name);
                _loc_6.description = String(param2::description);
                _loc_6.position = String(param2::position);
                _loc_6.start_date = FacebookDataUtils.formatDate(param2::start_date);
                _loc_6.end_date = FacebookDataUtils.formatDate(param2::end_date);
                _loc_3.push(_loc_6);
            }
            return _loc_3;
        }//end

        public static StatusData  createStatus (XML param1 ,Namespace param2 )
        {
            _loc_3 = new StatusData ();
            _loc_3.message = String(param2::message);
            _loc_3.time = FacebookDataUtils.formatDate(String(param2::time));
            return _loc_3;
        }//end

        public static Array  parseEducationHistory (XML param1 ,Namespace param2 )
        {
            Object _loc_5 =null ;
            FacebookEducationInfo _loc_6 =null ;
            XML _loc_7 =null ;
            Array _loc_3 =new Array();
            _loc_4 = param1.children ();
            for(int i0 = 0; i0 < _loc_4.size(); i0++) 
            {
            		_loc_5 = _loc_4.get(i0);

                _loc_6 = new FacebookEducationInfo();
                _loc_6.name = String(param2::name);
                _loc_6.year = String(param2::year);
                _loc_6.degree = String(param2::degree);
                _loc_6.concentrations = new Array();
                for(int i0 = 0; i0 < _loc_5.concentration.size(); i0++) 
                {
                		_loc_7 = _loc_5.concentration.get(i0);

                    _loc_6.concentrations.push(_loc_7);
                }
                _loc_3.push(_loc_6);
            }
            return _loc_3;
        }//end

        public static FacebookUser  createFacebookUser (XML param1 ,Namespace param2 )
        {
            XML _loc_6 =null ;
            String _loc_7 =null ;
            _loc_3 = new FacebookUser ();
            _loc_4 = param1.children ();
            _loc_5 = param1.children ().length ();
            int _loc_8 =0;
            while (_loc_8 < _loc_5)
            {

                _loc_6 = _loc_4.get(_loc_8);
                _loc_7 = _loc_6.localName().toString();
                switch(_loc_7)
                {
                    case "status":
                    {
                        _loc_3.put(_loc_7,  createStatus(_loc_6, param2));
                        break;
                    }
                    case "affiliations":
                    {
                        _loc_3.put(_loc_7,  createAffiliations(_loc_6.children(), param2));
                        break;
                    }
                    case "hometown_location":
                    case "current_location":
                    {
                        _loc_3.put(_loc_7,  FacebookXMLParserUtils.createLocation(_loc_6, param2));
                        break;
                    }
                    case "profile_update_time":
                    {
                        _loc_3.put(_loc_7,  FacebookDataUtils.formatDate(_loc_6.toString()));
                        break;
                    }
                    case "hs_info":
                    {
                        _loc_3.hs1_id = parseInt(param2::hs1_id);
                        _loc_3.hs1_name = String(param2::hs1_name);
                        _loc_3.hs2_id = parseInt(param2::hs2_id);
                        _loc_3.hs2_name = String(param2::hs2_name);
                        _loc_3.grad_year = String(param2::grad_year);
                        break;
                    }
                    case "education_history":
                    {
                        _loc_3.put(_loc_7,  parseEducationHistory(_loc_6, param2));
                        break;
                    }
                    case "work_history":
                    {
                        _loc_3.put(_loc_7,  parseWorkHistory(_loc_6, param2));
                        break;
                    }
                    case "timezone":
                    case "notes_count":
                    case "wall_count":
                    {
                        _loc_3.put(_loc_7,  Number(_loc_6.toString()));
                        break;
                    }
                    case "has_added_app":
                    case "is_app_user":
                    {
                        _loc_3.put(_loc_7,  FacebookXMLParserUtils.toBoolean(_loc_6));
                        break;
                    }
                    case "meeting_sex":
                    case "meeting_for":
                    case "email_hashes":
                    {
                        _loc_3.put(_loc_7,  toArray(_loc_6, param2));
                        break;
                    }
                    default:
                    {
                        if (_loc_7 in _loc_3)
                        {
                            _loc_3.put(_loc_7,  String(_loc_6));
                        }
                        break;
                    }
                }
                _loc_8 = _loc_8 + 1;
            }
            return _loc_3;
        }//end

        public static Array  toArray (XML param1 ,Namespace param2 )
        {
            Array _loc_3 =new Array();
            _loc_4 = param1.children ();
            _loc_5 = param1.children ().length ();
            int _loc_6 =0;
            while (_loc_6 < _loc_5)
            {

                _loc_3.push(_loc_4.get(_loc_6).toString());
                _loc_6 = _loc_6 + 1;
            }
            return _loc_3;
        }//end

        public static Array  createAffiliations (XMLList param1 ,Namespace param2 )
        {
            _loc_4 = null;
            FacebookNetwork _loc_5 =null ;
            Array _loc_3 =new Array();
            for(int i0 = 0; i0 < param1.size(); i0++) 
            {
            		_loc_4 = param1.get(i0);

                _loc_5 = new FacebookNetwork();
                _loc_5.nid = parseInt(param2::nid);
                _loc_5.name = String(param2::name);
                _loc_5.type = String(param2::type);
                _loc_5.status = String(param2::status);
                _loc_5.year = String(param2::year);
                _loc_3.push(_loc_5);
            }
            return _loc_3;
        }//end

    }

