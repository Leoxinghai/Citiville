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

    public class CreativeFeed
    {
        private Object m_feed ;
        private Object m_attachment ;
        private Object m_viral ;
        private static  Object EMPTY_OBJECT ={};

        public  CreativeFeed ()
        {
            return;
        }//end

        public Object  data ()
        {
            return this.m_feed;
        }//end

        public void  data (Object param1 )
        {
            this.m_feed = param1;
            this.m_attachment = this.m_feed.hasOwnProperty("attachment") ? (this.m_feed.attachment) : (EMPTY_OBJECT);
            return;
        }//end

        public void  viral (Object param1 )
        {
            this.m_viral = param1 ? (param1) : (EMPTY_OBJECT);
            return;
        }//end

        public String  attachmentName ()
        {
            return this.localized(this.m_attachment, "name");
        }//end

        public String  attachmentDescription ()
        {
            return this.localized(this.m_attachment, "description");
        }//end

        public String  attachmentCaption ()
        {
            return this.localized(this.m_attachment, "caption");
        }//end

        public String  mediaURL ()
        {
            String _loc_1 =null ;
            Object _loc_2 =null ;
            Array _loc_3 =null ;
            String _loc_4 =null ;
            Object _loc_5 =null ;
            if (this.m_attachment.hasOwnProperty("media"))
            {
                _loc_2 = this.m_attachment.media;
                _loc_3 =(Array) _loc_2;
                if (!_loc_3)
                {
                    _loc_1 = _loc_2.get("src");
                }
                else
                {
                    _loc_4 = this.m_viral.hasOwnProperty("_feedImage") ? (this.m_viral._feedImage) : (null);
                    for(int i0 = 0; i0 < _loc_3.size(); i0++) 
                    {
                    	_loc_5 = _loc_3.get(i0);

                        if (!_loc_4 || _loc_5.imageID == _loc_4)
                        {
                            _loc_1 = _loc_5.src;
                            break;
                        }
                    }
                }
            }
            if (_loc_1)
            {
                _loc_1 = Global.getAssetURL(_loc_1);
            }
            return _loc_1;
        }//end

        private String  localized (Object param1 ,String param2 )
        {
            Array _loc_5 =null ;
            String _loc_6 =null ;
            String _loc_7 =null ;
            String _loc_3 =null ;
            _loc_4 = param1.hasOwnProperty(param2)? (param1.get(param2)) : (null);
            if (param1.hasOwnProperty(param2) ? (param1.get(param2)) : (null))
            {
                _loc_5 = _loc_4.split(Creatives.FEEDS_TEXT_DELIMETER);
                if (_loc_5.length >= 2)
                {
                    _loc_6 = _loc_5.get(0);
                    _loc_7 = _loc_5.get(1);
                    _loc_3 = ZLoc.t(_loc_6, _loc_7, this.m_viral);
                }
                else
                {
                    _loc_3 = "";
                }
            }
            return _loc_3;
        }//end

    }




