package Classes.Managers;

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

import Engine.Managers.*;
//import flash.display.*;

    public class GameZaspManager extends ZaspManager
    {

        public  GameZaspManager (DisplayObject param1 )
        {
            super(param1);
            return;
        }//end

         protected String  gameDetail ()
        {
            if (Global.world)
            {
                return ((String)Global.world.getProfilingGameState().get("description")) + "-" + this.m_lastActivity + "-" + this.m_lastInactivity;
            }
            return "";
        }//end

         protected int  gameDefCon ()
        {
            if (Global.world)
            {
                return Global.world.defCon;
            }
            return -1;
        }//end

         protected void  modifyReport (Object param1 )
        {
            _loc_2 = Global.world&& Global.world.gameInitStatus ? (Global.world.logGetProfilingData(0, true)) : (null);
            if (param1.get("type") == "fps")
            {
                param1.put("constants",  param1.get("constants") + " V:2");
                param1.put("game_state", this.gameDetail());
                param1.put("string5",  Global.player ? (Global.player.level) : (-1));
                param1.put("string6", LoadingManager.getObjectsLoaded());
                param1.put("string7",  _loc_2 ? (_loc_2.get("npc")) : (0));
                param1.put("string8",  _loc_2 ? (_loc_2.get("npcv")) : (0));
                param1.put("number7",  _loc_2 ? (_loc_2.get("build")) : (0));
                param1.put("number8",  _loc_2 ? (_loc_2.get("buildv")) : (0));
            }
            else if (param1.get("type") == "GPI")
            {
                param1.put("level",  Global.player ? (Global.player.level) : (-1));
            }
            return;
        }//end

    }



