package Modules.landmarks;

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
    public class LandmarkDatum
    {
        public String name ;
        public String state ="uninit";
        public ConstructionSite construction ;
        public static  String STATE_COMING_SOON ="im_coming_dont_stop";
        public static  String STATE_UNPLACED ="unplaced";
        public static  String STATE_PLACED ="placed";
        public static  String STATE_COMPLETE ="complete";
        public static  String STATE_UNINIT ="uninit";

        public  LandmarkDatum (String param1 )
        {
            this.name = param1;
            this.state = this.updateState();
            return;
        }//end

        public String  updateState ()
        {
            Item _loc_1 =null ;
            Array _loc_2 =null ;
            int _loc_3 =0;
            while (this.state == STATE_UNINIT)
            {

                if (this.name == "dummy")
                {
                    this.state = STATE_COMING_SOON;
                    return this.state;
                }
                _loc_2 = Global.world.getObjectsByNames(.get(this.name));
                if (_loc_2.length())
                {
                    this.state = STATE_COMPLETE;
                    return this.state;
                }
                if (this.findConstructionSite())
                {
                    this.construction = this.findConstructionSite();
                    this.state = STATE_PLACED;
                    return this.state;
                }
                _loc_3 = Global.player.inventory.getItemCountByName(this.name);
                if (_loc_3)
                {
                    this.state = STATE_UNPLACED;
                    return this.state;
                }
                _loc_1 = Global.gameSettings().getItemByName(this.name);
                if (_loc_1.upgrade)
                {
                    this.name = _loc_1.upgrade.newItemName;
                    continue;
                }
                this.name = "dummy";
            }
            return this.state;
        }//end

        public ConstructionSite  findConstructionSite ()
        {
            ConstructionSite _loc_2 =null ;
            _loc_1 =Global.world.getObjectsByTypes(.get(WorldObjectTypes.CONSTRUCTION_SITE) );
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_2 = _loc_1.get(i0);

                if (_loc_2.targetName == this.name)
                {
                    return _loc_2;
                }
            }
            return null;
        }//end

    }



