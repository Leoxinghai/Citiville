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

import Classes.*;
import Display.aswingui.*;
import Modules.socialinventory.*;
//import flash.display.*;
//import flash.filters.*;
import org.aswing.*;
import Classes.sim.*;

    public class HUDHeartInventoryComponent extends HUDComponent
    {
        protected int m_heartCount ;
        protected Component m_heartIcon ;
        protected JTextField m_heartCountTxt ;

        public  HUDHeartInventoryComponent ()
        {
            this.m_heartCount = SocialInventoryManager.instance.getCount("heart");
            this.m_heartIcon = null;
            this.m_heartCountTxt = null;
            return;
        }//end

         protected void  buildComponent ()
        {
            DisplayObject _loc_1 =null ;
            _loc_1 =(DisplayObject) new EmbeddedArt.heartInventoryIcon();
            m_jPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT ,-20);
            AssetPane _loc_3 =new AssetPane(_loc_1 );
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM );
            this.m_heartCountTxt = ASwingHelper.makeTextField(""+this.m_heartCount, EmbeddedArt.TITLE_FONT, 24, EmbeddedArt.yellowTextColor);
            this.m_heartCountTxt.filters = .get(new GlowFilter(EmbeddedArt.orangeTextColor, 1, 3, 3, 5, BitmapFilterQuality.LOW), new DropShadowFilter(1, 45, EmbeddedArt.blackTextColor, 0.6, 3, 3, 6));
            _loc_4.append(new AssetPane(this.m_heartCountTxt));
            _loc_2.append(_loc_3);
            _loc_2.append(_loc_4);
            m_jPanel.append(_loc_2);
            this.m_heartIcon = _loc_2;
            return;
        }//end

         public void  refresh (boolean param1 )
        {
            super.refresh(param1);
            this.updateCounter(this.m_heartCount);
            return;
        }//end

         public void  updateCounter (...args )
        {
            int argsvalue =SocialInventoryManager.instance.getCount("heart");
            this.m_heartCount = argsvalue;
            this.m_heartCountTxt.setText(""+ this.m_heartCount + "  ");
            return;
        }//end

    }


