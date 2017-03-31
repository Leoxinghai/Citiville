package Display.DialogUI;

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
import Classes.util.*;
import Display.aswingui.*;
//import flash.display.*;
//import flash.events.*;
//import flash.net.*;
//import flash.system.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class CharacterYoutubeDialogView extends CharacterTalkDialogView
    {
        private Loader m_videoLoader ;
        private Object m_videoPlayer ;
        private AssetPane m_videoPlayerContainer ;
        private String m_videoId ;

        public  CharacterYoutubeDialogView (Dictionary param1 ,Object param2 )
        {
            this.m_videoId = param2.hasOwnProperty("videoId") ? (param2.get("videoId")) : (null);
            Sounds.pauseMusic();
            super(param1, param2);
            return;
        }//end  

         protected JPanel  createContentBody ()
        {
            JPanel _loc_2 =null ;
            String _loc_3 =null ;
            AssetPane _loc_4 =null ;
            JPanel _loc_5 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical();
            _loc_1.setPreferredWidth(470);
            if (this.m_videoId)
            {
                _loc_2 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER);
                _loc_2.setPreferredWidth(470);
                Security.allowDomain("www.youtube.com");
                this.m_videoLoader = new Loader();
                this.m_videoLoader.contentLoaderInfo.addEventListener(Event.INIT, this.onVideoLoaderInit);
                this.m_videoLoader.load(new URLRequest("http://www.youtube.com/v/" + this.m_videoId + "?version=3"));
                this.m_videoPlayerContainer = new AssetPane();
                this.m_videoPlayerContainer.setPreferredSize(new IntDimension(400, 300));
                _loc_2.append(this.m_videoPlayerContainer);
                _loc_1.append(_loc_2);
            }
            if (m_data.hasOwnProperty("message"))
            {
                _loc_3 = m_data.get("message");
                _loc_4 = ASwingHelper.makeMultilineText(_loc_3, 450, EmbeddedArt.defaultFontNameBold, TextFormatAlign.CENTER, 18, EmbeddedArt.brownTextColor, null, true);
                _loc_5 = ASwingHelper.makeSoftBoxJPanel();
                ASwingHelper.setEasyBorder(_loc_5, 3, 20, 10, 20);
                _loc_5.append(_loc_4);
                _loc_1.append(_loc_5);
            }
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end  

         protected void  closeMe ()
        {
            if (this.m_videoPlayer)
            {
                this.m_videoPlayer.destroy();
            }
            Sounds.setSoundManagerMusicMute();
            super.closeMe();
            return;
        }//end  

        private void  onVideoLoaderInit (Event event )
        {
            this.m_videoPlayerContainer.setAsset(this.m_videoLoader);
            this.m_videoLoader.content.addEventListener("onReady", this.onVideoPlayerReady);
            return;
        }//end  

        private void  onVideoPlayerReady (Event event )
        {
            this.m_videoPlayer = this.m_videoLoader.content;
            this.m_videoPlayer.setSize(400, 300);
            this.m_videoPlayer.playVideo();
            return;
        }//end  

    }



