package Display.FactoryUI;

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
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class SuppliesDialogView extends JPanel
    {
        protected DisplayObject m_bgAsset ;
        protected Dictionary m_assetDict ;
        protected Array m_items ;
        protected String m_title ;

        public  SuppliesDialogView (Dictionary param1 ,Array param2 ,String param3 ="",Function param4 =null )
        {
            super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0, SoftBoxLayout.CENTER));
            this.append(ASwingHelper.horizontalStrut(20));
            this.m_title = param3;
            this.m_items = param2;
            this.m_assetDict = param1;
            this.init();
            return;
        }//end  

        private void  closeMe (AWEvent event )
        {
            dispatchEvent(new Event(Event.CLOSE, true));
            return;
        }//end  

        private void  init ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            JPanel _loc_2 =new JPanel(new BorderLayout ());
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            JButton _loc_4 =new JButton ();
            _loc_5 = (DisplayObject)newthis.m_assetDict.get("close_button")
            _loc_4.wrapSimpleButton(new SimpleButton(_loc_5, _loc_5, _loc_5, _loc_5));
            _loc_4.addActionListener(this.closeMe, 0, false);
            _loc_3.append(_loc_4);
            _loc_2.append(_loc_3, BorderLayout.EAST);
            _loc_6 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_7 = (DisplayObject)newAssetPane(newthis.m_assetDict.get("forklift")
            _loc_8 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,-10);
            _loc_8.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 5)));
            _loc_9 = ASwingHelper.makeTextField(this.m_title+" ",EmbeddedArt.defaultFontNameBold,50,16777215,-4);
            _loc_9.filters = .get(new GlowFilter(489375, 1, 4, 4, 10));
            _loc_8.appendAll(_loc_7, _loc_9);
            _loc_6.append(ASwingHelper.verticalStrut(30));
            _loc_6.append(_loc_8);
            _loc_2.append(_loc_6, BorderLayout.CENTER);
            _loc_2.append(ASwingHelper.horizontalStrut(20), BorderLayout.WEST);
            _loc_1.append(_loc_2);
            this.m_bgAsset =(DisplayObject) new this.m_assetDict.get("supplies_background");
            MarginBackground _loc_10 =new MarginBackground(this.m_bgAsset ,new Insets(20,0,0,0));
            this.setBackgroundDecorator(_loc_10);
            IntDimension _loc_11 =new IntDimension(this.m_bgAsset.width ,this.m_bgAsset.height +20);
            this.setPreferredSize(_loc_11);
            this.setMaximumSize(_loc_11);
            this.setMinimumSize(_loc_11);
            SuppliesScrollingList _loc_12 =new SuppliesScrollingList(this.m_items ,SuppliesCellFactory ,0,2,2);
            _loc_1.append(ASwingHelper.verticalStrut(18));
            _loc_1.append(_loc_12);
            this.append(_loc_1);
            return;
        }//end  

    }




