package Display.GateUI;

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
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class AskFriendsDialogView extends GenericDialogView
    {
        protected AssetPane m_iconPane ;

        public  AskFriendsDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,Function param8 =null )
        {
            super(param1, param2, param3, param4, param5, param6, param7, "", param8);
            return;
        }//end  

         protected JPanel  createTitlePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2 = m_titleString;
            title = ASwingHelper.makeTextField(_loc_2, EmbeddedArt.titleFont, m_titleFontSize, EmbeddedArt.titleColor);
            title.filters = EmbeddedArt.titleFilters;
            TextFormat _loc_3 =new TextFormat ();
            _loc_3.size = m_titleSmallCapsFontSize;
            TextFieldUtil.formatSmallCaps(title.getTextField(), _loc_3);
            _loc_1.append(title);
            title.getTextField().height = m_titleFontSize * 1.5;
            ASwingHelper.setEasyBorder(_loc_1, 4);
            return _loc_1;
        }//end  

         protected JPanel  createIconPane ()
        {
            this.m_iconPane = new AssetPane();
            _loc_1 = m_icon;
            _loc_2 = LoadingManager.load(_loc_1,this.loadIcon);
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_3.append(this.m_iconPane);
            return _loc_3;
        }//end  

        private void  loadIcon (Event event )
        {
            this.m_iconPane.setAsset((event.target as LoaderInfo).content);
            ASwingHelper.prepare(this.parent);
            dispatchEvent(new UIEvent(UIEvent.REFRESH_DIALOG, "iconLoaded", true));
            return;
        }//end  

    }



