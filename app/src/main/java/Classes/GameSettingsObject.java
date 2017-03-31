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

//import flash.events.*;
    public class GameSettingsObject extends EventDispatcher
    {
        protected XML m_xml =null ;
        protected String m_name =null ;

        public  GameSettingsObject (XML param1 )
        {
            this.m_xml = param1;
            this.m_name = this.m_xml.@name;
            return;
        }//end

        public String  name ()
        {
            return this.m_name;
        }//end

        public XML  xml ()
        {
            return this.m_xml;
        }//end

        protected String  getFriendlyNameLocFile ()
        {
            throw new Error("This function needs to be overriden");
        }//end

        public String  localizedName ()
        {
            return ZLoc.t(this.getFriendlyNameLocFile(), this.name + "_friendlyName");
        }//end

        public String  tooltipNotes ()
        {
            return ZLoc.t(this.getFriendlyNameLocFile(), this.name + "_notes");
        }//end

        public String  getImageByName (String param1 )
        {
            String result ;
            XMLList images ;
            imageName = param1;
            result;
            int _loc_4 =0;
            _loc_5 = this.m_xml.image;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@name == imageName)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            images = _loc_3;
            if (images.length())
            {
                result = Global.getAssetURL(images.get(0).@url);
            }
            return result;
        }//end

        public String  getRelativeImageByName (String param1 )
        {
            String result ;
            XMLList images ;
            imageName = param1;
            result;
            int _loc_4 =0;
            _loc_5 = this.m_xml.image;
            XMLList _loc_3 =new XMLList("");
            Object _loc_6;
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);


                with (_loc_6)
                {
                    if (@name == imageName)
                    {
                        _loc_3.put(_loc_4++,  _loc_6);
                    }
                }
            }
            images = _loc_3;
            if (images.length())
            {
                result = images.get(0).@url;
            }
            return result;
        }//end

    }



