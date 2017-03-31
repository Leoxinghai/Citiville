package Display.HunterAndPreyUI;

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
import Display.FactoryUI.*;
import Display.aswingui.*;
import Modules.workers.*;
//import flash.filters.*;
//import flash.text.*;
import org.aswing.*;

    public class HunterSlidePick extends NPCSlidePick
    {
        protected HunterData m_data ;
        protected AssetPane m_toolTip ;

        public  HunterSlidePick (String param1 ,NPC param2 ,HunterData param3 )
        {
            Player _loc_7 =null ;
            this.m_data = param3;
            _loc_4 = param3.getZID().substr(1);
            _loc_5 =Global.player.friends ;
            String _loc_6 =null ;
            for(int i0 = 0; i0 < _loc_5.size(); i0++) 
            {
            	_loc_7 = _loc_5.get(i0);

                if (_loc_7.uid == _loc_4)
                {
                    _loc_6 = _loc_7.snUser.picture;
                    break;
                }
            }
            if (_loc_6 == null || _loc_6.length == 0)
            {
                _loc_6 = Global.getAssetURL("assets/dialogs/citysam_neighbor_card.jpg");
            }
            _loc_8 =Global.player.findFriendById(GameUtil.formatServerUid(param3.getZID ()));
            if (!Global.player.findFriendById(GameUtil.formatServerUid(param3.getZID())))
            {
                _loc_8 = Global.player.findFriendById("-1");
            }
            _loc_9 = ZLoc.t("Prey",param1+"_Officer_Title",{officerName_loc_8? (_loc_8.firstName) : ("Sam")});
            this.m_toolTip = ASwingHelper.makeMultilineText(_loc_9, 500, EmbeddedArt.defaultFontNameBold, TextFormatAlign.CENTER, 14, EmbeddedArt.whiteTextColor, .get(new GlowFilter(0, 1, 4, 4, 10, BitmapFilterQuality.LOW)));
            this.m_toolTip.mouseChildren = false;
            this.m_toolTip.mouseEnabled = false;
            super(param2, _loc_6);
            m_tween.kill();
            this.m_toolTip.x = (-this.m_toolTip.width) / 2 + this.m_mainSprite.width / 2 + 5;
            this.m_toolTip.y = -(this.m_toolTip.height + 10);
            this.m_toolTip.visible = false;
            addChild(this.m_toolTip);
            return;
        }//end

        public void  hideText ()
        {
            this.m_toolTip.visible = false;
            return;
        }//end

        public void  showText ()
        {
            this.m_toolTip.visible = true;
            return;
        }//end

    }



