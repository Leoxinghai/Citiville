package Display.hud.components;

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

import Display.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
import Classes.sim.*;

    public class HUDQuestManagerIconComponent extends HUDComponent
    {

        public  HUDQuestManagerIconComponent ()
        {
            this.visible = false;
            this.loadAssets();
            return;
        }//end

        protected void  loadAssets ()
        {
            LoadingManager.load(Global.getAssetURL(Global.gameSettings().getString("questManagerIconUrl")), this.onIconLoaded);
            return;
        }//end

        protected void  onIconLoaded (Event event )
        {
            _loc_2 =(Loader) event.target.loader;
            _loc_3 =(Bitmap) _loc_2.content;
            GameSprite _loc_4 =new GameSprite ();
            _loc_4.addChild(_loc_3);
            _loc_4.toolTip = ZLoc.t("Quest", "quest_manager_icon_tooltip");
            _loc_4.buttonMode = true;
            _loc_4.useHandCursor = true;
            _loc_4.addEventListener(MouseEvent.CLICK, this.onQuestManagerClick);
            _loc_4.hideCursor = true;
            this.addChild(_loc_4);
            if (UI.questManagerView.content.model.size() > 0)
            {
                this.visible = true;
            }
            return;
        }//end

        protected void  onQuestManagerClick (MouseEvent event )
        {
            UI.displayQuestManager();
            Global.ui.resetActionMenu();
            StatsManager.count("quest_manager", "open");
            return;
        }//end

    }


