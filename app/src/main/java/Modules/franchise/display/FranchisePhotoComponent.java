package Modules.franchise.display;

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
import Display.aswingui.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;

    public class FranchisePhotoComponent extends JPanel
    {
        private String m_id ;
        private DisplayObjectContainer m_picContainer ;

        public  FranchisePhotoComponent (String param1 )
        {
            super(new SoftBoxLayout(SoftBoxLayout.TOP));
            this.m_id = param1;
            _loc_2 = this.createPhotoComponent ();
            append(ASwingHelper.verticalStrut(4));
            append(_loc_2);
            append(ASwingHelper.verticalStrut(7));
            ASwingHelper.prepare(this);
            return;
        }//end

        private Component  createPhotoComponent ()
        {
            Player _loc_1 =null ;
            if (this.m_id == Global.player.uid)
            {
                _loc_1 = Global.player;
            }
            else
            {
                _loc_1 = Global.player.findFriendById(this.m_id);
            }
            _loc_2 = new EmbeddedArt.hud_no_profile_pic ();
            this.m_picContainer = new Sprite();
            _loc_3 =(Sprite) this.m_picContainer;
            _loc_3.graphics.beginFill(16711680, 0);
            _loc_3.graphics.drawRect(0, 0, 50, 50);
            _loc_3.graphics.endFill();
            this.m_picContainer.addChild(_loc_2);
            if (_loc_1 && _loc_1.snUser && _loc_1.snUser.picture)
            {
                LoadingManager.load(_loc_1.snUser.picture, this.photoCompleteHandler);
            }
            AssetPane _loc_4 =new AssetPane(this.m_picContainer );
            ASwingHelper.prepare(_loc_4);
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_5.appendAll(ASwingHelper.horizontalStrut(3), _loc_4);
            return _loc_5;
        }//end

        private void  photoCompleteHandler (Event event )
        {
            DisplayObject _loc_3 =null ;
            _loc_2 =(LoaderInfo) event.target;
            if (_loc_2 && _loc_2.content)
            {
                _loc_3 = _loc_2.content;
                int _loc_4 =50;
                _loc_3.height = 50;
                _loc_3.width = _loc_4;
                this.m_picContainer.addChild(_loc_3);
            }
            return;
        }//end

    }



