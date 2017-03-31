package Classes.announcements;

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
import Display.DialogUI.*;
import Display.aswingui.*;
//import flash.display.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class FancyAnnouncementDialogView extends GenericDialogView
    {

        public  FancyAnnouncementDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null )
        {
            super(param1, param2, param3, param4, param5);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            return;
        }//end

         protected JPanel  createHeaderPanel ()
        {
            JPanel _loc_1 =new JPanel(new BorderLayout ());
            _loc_2 = createCloseButtonPanel();
            ASwingHelper.prepare(_loc_2);
            _loc_1.append(ASwingHelper.horizontalStrut(_loc_2.getWidth()), BorderLayout.WEST);
            _loc_1.append(_loc_2, BorderLayout.EAST);
            return _loc_1;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            JPanel _loc_14 =null ;
            _loc_1 = this.createHeaderPanel();
            _loc_2 = m_assetDict.hasOwnProperty("image_size")? (m_assetDict.get("image_size")) : (new Point(m_assetDict.get("image").width, m_assetDict.get("image").height));
            _loc_3 = m_assetDict.hasOwnProperty("image_offset")? (m_assetDict.get("image_offset")) : (new Point());
            _loc_4 = m_assetDict.get("image");
            AssetPane _loc_5 =new AssetPane(_loc_4 );
            _loc_5.setPreferredSize(new IntDimension(_loc_2.x, _loc_2.y));
            _loc_6 = ASwingHelper.makeMultilineText(m_message,_loc_2.x,EmbeddedArt.defaultFontNameBold,TextFormatAlign.CENTER,16,16777215);
            ASwingHelper.setEasyBorder(_loc_6, 10, 0, 10);
            _loc_7 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            Sprite _loc_8 =new Sprite ();
            _loc_8.graphics.beginFill(0, 0.6);
            _loc_8.graphics.drawRect(0, 0, 20, 20);
            _loc_8.graphics.endFill();
            ASwingHelper.setBackground(_loc_7, _loc_8);
            _loc_7.append(_loc_6);
            ASwingHelper.prepare(_loc_7);
            _loc_9 = _loc_7.getHeight();
            _loc_10 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_10.setPreferredSize(new IntDimension(_loc_2.x + _loc_3.x, _loc_2.y + _loc_3.y));
            ASwingHelper.setEasyBorder(_loc_10, _loc_3.y, _loc_3.x);
            _loc_10.appendAll(_loc_5, ASwingHelper.verticalStrut(-_loc_9), _loc_7);
            _loc_11 = ASwingHelper.makeSoftBoxJPanel();
            _loc_11.append(_loc_10);
            _loc_12 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_12.appendAll(_loc_1, _loc_11);
            JPanel _loc_13 =new JPanel(new BorderLayout ());
            _loc_13.setPreferredHeight(m_bgAsset.height);
            _loc_13.append(_loc_12, BorderLayout.NORTH);
            if (m_type != TYPE_MODAL && m_type != TYPE_NOBUTTONS)
            {
                _loc_14 = createButtonPanel();
                _loc_13.append(_loc_14, BorderLayout.SOUTH);
            }
            return _loc_13;
        }//end

         protected void  makeBackground ()
        {
            super.makeBackground();
            int _loc_1 =1;
            m_bgAsset.scaleY = 1;
            m_bgAsset.scaleX = _loc_1;
            setPreferredSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
            return;
        }//end

    }




