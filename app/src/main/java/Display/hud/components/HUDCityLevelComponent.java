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
import Display.*;
import Display.aswingui.*;
import Display.hud.*;
import Engine.*;
//import flash.display.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.border.*;
import Classes.sim.*;

import com.xinghai.Debug;

    public class HUDCityLevelComponent extends HUDComponent
    {
        public JTextField m_coinsText ;
        protected JPanel m_coinsHolder ;

        public  HUDCityLevelComponent (Function param1)
        {
            return;
        }//end

         protected void  buildComponent ()
        {

            m_jPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT, 0);
            TextFormat _loc_1 =new TextFormat(EmbeddedArt.DEFAULT_FONT_NAME_BOLD ,18,0,true ,false ,false ,null ,null ,TextFormatAlign.RIGHT );
            _loc_2 = HUDThemeManager.getAsset(HUDThemeManager.CITYLEVEL_ASSET);
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_4 = HUDThemeManager.getComponentConfig(HUDThemeManager.CITYLEVEL_ASSET);
            this.m_coinsHolder = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT);
            Debug.debug7("HUDCoinsComponent.buildComponent." +_loc_2);

            ASwingHelper.setSizedBackground(this.m_coinsHolder, _loc_2);
            this.m_coinsText = ASwingHelper.makeTextField("0", EmbeddedArt.TITLE_FONT, 18, EmbeddedArt.yellowTextColor, 0, _loc_1);

            _loc_3.append(this.m_coinsHolder);

            this.m_coinsText.setBorder(new EmptyBorder(null, new Insets(_loc_4.@textTopMargin)));

            this.m_coinsHolder.appendAll(this.m_coinsText, ASwingHelper.horizontalStrut(_loc_4.@textRightMargin));
            _loc_3.append(this.m_coinsHolder);
            m_jPanel.append(_loc_3);
            m_jWindow.setLocation(HUDThemeManager.getOffset(HUDThemeManager.CASH_ASSET));
            return;
        }//end

         protected void  attachToolTip ()
        {
            m_toolTip =new ToolTip (String  ()
            {
                return ZLoc.t("Main", "Gold_ToolTip", {coins:m_coinsText.getText()});
            }//end
            );
            m_toolTip.attachToolTip(this);
            m_toolTip.hideCursor = true;
            return;
        }//end

         protected void  setCounter ()
        {

            Debug.debug7("HUDCityLevelComponent.setCounter ");


            m_counter =new HUDCounter (void  (double param1 )
            {
	    _loc_11 =Global.player.level <10? ("0" + Global.player.level.toString()) : (Global.player.level.toString());
	    Debug.debug7("HUDCityLevelComponent.setCounter2 " + _loc_11);
	    m_coinsText.setText(Utilities.formatNumber(_loc_11));
                return;
            }//end
            );
            return;
        }//end

    }





