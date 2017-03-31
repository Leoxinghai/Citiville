package Init;

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
import Engine.Classes.*;
import Engine.Init.*;
import Engine.Interfaces.*;
import Engine.Managers.*;
//import flash.events.*;
//import flash.system.*;
//import flash.text.*;

import com.xinghai.Debug;

    public class FontMapperInit extends InitializationAction
    {
        private String m_url ;
        private  String FONT_MAPPER_DEFINITION ="FontMapper";
        public static  String INIT_ID ="FontMapperInit";

        public  FontMapperInit ()
        {
            super(INIT_ID);
            addDependency(GlobalsInit.INIT_ID);
            addDependency(LocalizationInit.INIT_ID);
            return;
        }//end

         public void  execute ()
        {
            String _loc_1 =null ;
            String _loc_3 =null ;
            int _loc_4 =0;
            String _loc_5 =null ;
            this.m_url = null;
            if (GlobalEngine.localizer)
            {
                _loc_1 = GlobalEngine.localizer.localeCode;
            }
            String _loc_2 =null ;

            //add by xinghai
            _loc_1 = "zh_CN";

            if (_loc_1 == "zh_CN")
            {
                _loc_2 = "FontMapper_zh.swf";
            }
            else if (_loc_1 == "ko_KR")
            {
                _loc_2 = "FontMapper_ko.swf";
            }
            else if (_loc_1 == "ja_JP")
            {
                _loc_2 = "FontMapper_ja.swf";
            }
            if (_loc_2)
            {
                if (GlobalEngine.stage.loaderInfo.url.indexOf("file://") == 0)
                {
                    _loc_3 = GlobalEngine.stage.loaderInfo.url;
                    _loc_4 = _loc_3.lastIndexOf("/");
                    _loc_5 = "";
                    if (_loc_4)
                    {
                        _loc_5 = _loc_3.substr(0, (_loc_4 + 1));
                    }
                    this.m_url = _loc_5 + _loc_2;
                }
                else
                {
                    this.m_url = Global.getAssetURL("assets/" + _loc_2);
                }
                LoadingManager.load(this.m_url, this.onLoadComplete, LoadingManager.PRIORITY_NORMAL, this.onLoadFailed);
            }
            else
            {
                dispatchEvent(new Event(Event.COMPLETE));
            }
            return;
        }//end

        private void  onLoadComplete (Event event )
        {
            Class _loc_3 =null ;
            Object _loc_4 =null ;
            IFontMapper _loc_5 =null ;
            Array _loc_6 =null ;
            String _loc_7 =null ;
            Object _loc_8 =null ;
            _loc_2 = event.target.applicationDomain ;
            if (_loc_2.hasDefinition(this.FONT_MAPPER_DEFINITION))
            {
                _loc_3 =(Class) _loc_2.getDefinition(this.FONT_MAPPER_DEFINITION);
                if (_loc_3)
                {
                    _loc_4 = new _loc_3;
                    _loc_5 = new FontMapperProxy(_loc_4);
                    if (GlobalEngine.localizer)
                    {
                        _loc_5.setLanguageCode(GlobalEngine.localizer.localeCode);
                    }
                    _loc_6 = _loc_5.getFontsToRegister();
                    for(int i0 = 0; i0 < _loc_6.size(); i0++) 
                    {
                    	_loc_7 = _loc_6.get(i0);

                        _loc_8 = _loc_2.getDefinition(_loc_7);
                        Font.registerFont((Class)_loc_8);
                    }
                    EmbeddedArt.defaultFontName = _loc_5.mapFontName("", FontNames.STYLED_UI_FONT);

                    EmbeddedArt.defaultFontEmbed = _loc_5.mapFontEmbed("", FontNames.STYLED_UI_FONT, true);
                    EmbeddedArt.setEmbedFont(EmbeddedArt.defaultFontName, EmbeddedArt.defaultFontEmbed);
                    EmbeddedArt.defaultFontNameBold = _loc_5.mapFontName("", FontNames.STYLED_UI_FONT);
                    EmbeddedArt.defaultBoldFontEmbed = _loc_5.mapFontEmbed("", FontNames.STYLED_UI_FONT, true);
                    EmbeddedArt.setEmbedFont(EmbeddedArt.defaultFontNameBold, EmbeddedArt.defaultBoldFontEmbed);
                    EmbeddedArt.titleFont = _loc_5.mapFontName("", FontNames.STYLED_TITLE_FONT);
                    EmbeddedArt.titleFontEmbed = _loc_5.mapFontEmbed("", FontNames.STYLED_TITLE_FONT, true);
                    EmbeddedArt.setEmbedFont(EmbeddedArt.titleFont, EmbeddedArt.titleFontEmbed);
                    EmbeddedArt.defaultSerifFont = _loc_5.mapFontName("", FontNames.STYLED_UI_FONT);
                    EmbeddedArt.defaultSerifFontEmbed = _loc_5.mapFontEmbed("", FontNames.STYLED_UI_FONT, true);
                    EmbeddedArt.setEmbedFont(EmbeddedArt.defaultSerifFont, EmbeddedArt.defaultSerifFontEmbed);
                    GlobalEngine.fontMapper = _loc_5;
                }
            }
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end

        private void  onLoadFailed (Event event )
        {
            throw Error("Failed to load font mapper swf \"" + this.m_url + "\".");
        }//end

    }



