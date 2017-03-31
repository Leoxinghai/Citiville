package Display.hud;

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
import Display.aswingui.*;
//import flash.utils.*;
import org.aswing.*;

    public class CommodityPopupView extends NotifyPopupView
    {
        protected String m_commodityName ;
        protected JTextField m_commodityText ;
        private int m_amount ;
        private int m_cap ;
        private String m_friendlyName ;

        public  CommodityPopupView (Dictionary param1 ,String param2 )
        {
            this.m_commodityName = param2;
            super(param1, "", "", TYPE_OK, null);
            return;
        }//end  

         protected JPanel  makeShortPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_amount = Global.player.commodities.getCount(this.m_commodityName);
            this.m_cap = Global.player.commodities.getCapacity(this.m_commodityName);
            this.m_friendlyName = ZLoc.t("Main", "Commodity_" + this.m_commodityName + "_friendlyName");
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_4 = ASwingHelper.makeTextField(this.m_friendlyName+": ",EmbeddedArt.defaultFontNameBold,14,2531789);
            this.m_commodityText = ASwingHelper.makeTextField("" + this.m_amount + "/" + this.m_cap + "  ", EmbeddedArt.defaultFontNameBold, 12, 5785234);
            _loc_3.appendAll(_loc_4, this.m_commodityText);
            ASwingHelper.prepare(_loc_3);
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_6 = ASwingHelper.makeTextField("(Click for details) ",EmbeddedArt.defaultFontNameBold,10,2531789);
            _loc_5.append(_loc_6);
            ASwingHelper.prepare(_loc_5);
            _loc_2.appendAll(_loc_3, _loc_5);
            ASwingHelper.prepare(_loc_2);
            _loc_1.appendAll(_loc_2, ASwingHelper.horizontalStrut(25));
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end  

        public void  forceUpdateValue (int param1 )
        {
            this.m_commodityText.setText("" + param1 + "/" + this.m_cap + " ");
            return;
        }//end  

    }



