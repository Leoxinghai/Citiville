package Display.MarketUI;

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
//import flash.display.*;
//import flash.text.*;
import org.aswing.*;

    public class ComingSoonPanel extends JPanel
    {
        protected String m_type ;
        protected DisplayObject m_bg ;

        public  ComingSoonPanel (String param1 )
        {
            super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 5, SoftBoxLayout.CENTER));
            this.m_type = param1;
            this.makePanel();
            return;
        }//end

        protected void  makePanel ()
        {
            JPanel _loc_1 =null ;
            JPanel _loc_2 =null ;
            this.m_bg =(DisplayObject) new Catalog.assetDict.get("comingSoonStars");
            ASwingHelper.setSizedBackground(this, this.m_bg, new Insets(0, 0, 0, 0));
            if (this.m_type == "wonders")
            {
                _loc_1 = this.makeItem(Catalog.assetDict.get("tower"));
                _loc_2 = this.makeItem(Catalog.assetDict.get("coliseum"));
            }
            else if (this.m_type == "vehicles")
            {
                _loc_1 = this.makeItem(Catalog.assetDict.get("tractor"));
                _loc_2 = this.makeItem(Catalog.assetDict.get("truck"));
            }
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_4 = ASwingHelper.makeTextField(ZLoc.t("Dialogs","ComingSoon"),EmbeddedArt.titleFont ,30,EmbeddedArt.yellowTextColor );
            _loc_4.filters = EmbeddedArt.titleFilters;
            TextFieldUtil.formatSmallCaps(_loc_4.getTextField(), new TextFormat(EmbeddedArt.titleFont, 42));
            _loc_3.appendAll(_loc_4);
            this.appendAll(_loc_1, _loc_2, ASwingHelper.horizontalStrut(90), _loc_3);
            return;
        }//end

        private JPanel  makeItem (Class param1 )
        {
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            DisplayObject _loc_4 =(DisplayObject)new Catalog.assetDict.get( "comingSoonBg");
            ASwingHelper.setSizedBackground(_loc_2, _loc_4);
            DisplayObject _loc_5 =(DisplayObject)new param1;
            AssetPane _loc_6 =new AssetPane(_loc_5 );
            _loc_7 = Math.max(0,(_loc_4.width -_loc_5.width )/2+1);
            _loc_8 = Math.max(0,(_loc_4.height -_loc_5.height )/2);
            ASwingHelper.setEasyBorder(_loc_6, _loc_8, _loc_7);
            _loc_3.append(_loc_6);
            _loc_2.append(_loc_3);
            return _loc_2;
        }//end

    }



