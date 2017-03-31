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

import Classes.virals.*;
import Display.*;
import Display.FlashMFSList.*;
import Engine.Managers.*;
import Modules.stats.types.*;

    public class FlashMFSListManager extends BaseMFSManager
    {

        public  FlashMFSListManager ()
        {
            return;
        }//end

         public void  displayMFS ()
        {
            boolean _loc_7 =false ;
            String _loc_8 =null ;
            Object _loc_9 =null ;
            Array _loc_10 =null ;
            Object _loc_11 =null ;
            Array _loc_12 =null ;
            FlashMFSListDialog _loc_13 =null ;
            _loc_1 = getMFSInitData();
            _loc_2 = this.getExcludeIds(_loc_1.type);
            _loc_3 = Global.player.appFriends;
            _loc_4 = Global.player.nonAppFriends;
            _loc_5 = _loc_3.concat(_loc_4);
            Array _loc_6 =new Array();
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            		_loc_9 = _loc_3.get(i0);

                _loc_7 = false;
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_8 = _loc_2.get(i0);

                    if (_loc_8 == _loc_9.zid)
                    {
                        _loc_7 = true;
                        break;
                    }
                }
                if (!_loc_7)
                {
                    _loc_6.push(new FlashMFSRecipient(_loc_9.zid, _loc_9.name, "", false));
                }
            }
            _loc_10 = new Array();
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            		_loc_11 = _loc_5.get(i0);

                _loc_7 = false;
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                		_loc_8 = _loc_2.get(i0);

                    if (_loc_8 == _loc_11.zid)
                    {
                        _loc_7 = true;
                        break;
                    }
                }
                if (!_loc_7)
                {
                    _loc_10.push(new FlashMFSRecipient(_loc_11.zid, _loc_11.name, "", false));
                }
            }
            _loc_10.sortOn("name");
            _loc_6.sortOn("name");
            _loc_12 = .get({name:"app", data:_loc_6}, {name:"all", data:_loc_10});
            _loc_13 = new FlashMFSListDialog(_loc_12, _loc_1.title, _loc_1.subTitle, _loc_1.message, _loc_1.iconUrl, _loc_1.sendLabel);
            UI.displayPopup(_loc_13, true, "FlashMFSList", false);
            StatsManager.sample(100, StatsCounterType.DIALOG, StatsCounterType.FLASH_MFS, "factory_workers", "view", "app", "", _loc_6.length());
            return;
        }//end

        private Array  getExcludeIds (RequestType param1 )
        {
            return Global.player.getActiveRequests(param1);
        }//end

         protected void  onRequestSent (boolean param1 ,Array param2 )
        {
            return;
        }//end

    }



