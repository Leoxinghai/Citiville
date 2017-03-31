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
import Display.*;
import Display.hud.*;
import Engine.Init.*;
//import flash.events.*;
import org.aswing.*;

//import flash.display.*;
//import flash.net.*;
//import flash.system.*;
//import flash.utils.*;

import Engine.Managers.*;

import com.xinghai.Debug;

    public class UIInit extends InitializationAction
    {
        private boolean m_themeAssetsLoaded =false ;
        public static  String INIT_ID ="UIInit";

        public Array m_assetsToPreload ;
        public Dictionary m_cache ;
        private int totalLoaded =0;


        public  UIInit ()
        {
            super(INIT_ID);
            addDependency(GlobalsInit.INIT_ID);
            addDependency(LocalizationInit.INIT_ID);
            addDependency(FontMapperInit.INIT_ID);

            this.m_assetsToPreload = .get("assets/hud/tool_build2.png", "assets/hud/tool_franchise.png","assets/hud/tool_offstate_btn.png","assets/hud/Action_Menu/subtool_cursor.png","assets/dialogs/btn_close_on.png","assets/dialogs/btn_close_over.png","assets/doobers/kdbomb_doober.png","assets/doobers/lrbomb_doober.png","assets/menu/collect.png","assets/menu/fly.png","assets/menu/transmit.png","assets/menu/topup.png","assets/menu/kdbomb.png","assets/menu/lrbomb.png","assets/menu/enter.png","assets/menu/zoomout.png","assets/menu/zoomin.png","assets/menu/fullscreen.png");
            this.m_cache = new Dictionary();

            return;
        }//end

         public void  execute ()
        {
            UIManager.setLookAndFeel(new CityASwingSkin());
            HUDThemeManager .loadAssets (void  ()
            {
                m_themeAssetsLoaded = true;
                loadToolAssets();

                return;
            }//end
            );


            return;
        }//end


        private void  loadToolAssets ()
        {

            String _loc_5 =null ;
            Function callback =this.onAssetLoaded ;
            Loader loader ;
            int priority =0;
            Object absoluteUrl;

            for(int i0 = 0; i0 < m_assetsToPreload.size(); i0++)
            {
            	_loc_5 = m_assetsToPreload.get(i0);

                if (this.m_cache.get(_loc_5) == null)
                {

		    absoluteUrl = Global.getAssetURL(_loc_5);
		    startLoading(absoluteUrl,loader,_loc_5);

                }
            }

        }

        protected void  startLoading (String param1 ,Loader param2 ,String param3 )
        {
            Function callback =this.onAssetLoaded ;
            Loader loader =param2 ;
            int priority =0;
            String keyvalue =param3 ;

	    loader =LoadingManager .load (param1 ,void  (Event event )
	    {
		callback(keyvalue, loader);
		return;
	    }//end
	    , priority);

        }


        protected void  onAssetLoaded (String param1 ,Loader param2 )
        {
            _loc_3 =(DisplayObject) param2.content;

            Debug.debug7("UIInit.onAssetLoaded "+param1);

            if (_loc_3 == null)
            {
                ErrorManager.addError("Failed to load delayed asset: " + param1);
            }

            this.m_cache.put(param1,  _loc_3);
            totalLoaded++;


            if(totalLoaded >=3) {
            	onFinishedLoading();
            }
            return;
        }//end


        private void  onFinishedLoading ()
        {
            UI _loc_1 =null ;



            if (this.m_themeAssetsLoaded)
            {
                _loc_1 = new UI();


                GlobalEngine.zaspManager.trackTimingStart("UI_INIT");
                Global.player.setFakeFriend();
                if (Global.friendbar)
                {
                    Global.ui.m_friendBar.populateNeighbors(Global.friendbar);
                    Global.ui.setFriendBarPos(Math.max(Global.friendbar.length - 15, 0));
                }
                GlobalEngine.zaspManager.trackTimingStop("UI_INIT");
                dispatchEvent(new Event(Event.COMPLETE));
            }

            Global.weiboManager = new WeiboManager();

            return;
        }//end

    }




