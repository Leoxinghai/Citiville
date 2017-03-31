package Display.DialogUI;

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
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;

    public class OneTimeExpansionDialogView extends BaseDialogView
    {

        public  OneTimeExpansionDialogView (Dictionary param1 ,Object param2 )
        {
            super(param1, param2);
            return;
        }//end

         protected JPanel  createContentPanel ()
        {
            m_contentPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            m_contentPanel.append(ASwingHelper.horizontalStrut(15));
            m_contentPanel.append(new AssetPane(m_assetDict.get("icon")));
            m_contentPanel.append(ASwingHelper.horizontalStrut(20));
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_2 = m_data.get("message") ;
            _loc_3 = ASwingHelper.makeMultilineText(_loc_2,350,EmbeddedArt.defaultFontNameBold,TextFormatAlign.CENTER,18,EmbeddedArt.brownTextColor);
            ASwingHelper.setEasyBorder(m_contentPanel, 20, 0, 20);
            _loc_1.append(_loc_3);
            _loc_1.append(ASwingHelper.verticalStrut(5));
            AssetPane _loc_4 =new AssetPane ();
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_5.append(_loc_4);
            _loc_1.append(_loc_5);
            _loc_1.append(ASwingHelper.verticalStrut(5));
            _loc_6 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_6.append(ASwingHelper.makeLabel(ZLoc.t("Dialogs", "CatalogCost").toUpperCase(), EmbeddedArt.defaultFontNameBold, 18, EmbeddedArt.blueTextColor));
            _loc_6.append(ASwingHelper.horizontalStrut(10));
            _loc_6.append(new AssetPane(m_assetDict.get("cashIcon")));
            _loc_6.append(ASwingHelper.horizontalStrut(10));
            _loc_6.append(ASwingHelper.makeLabel(String(m_data.expansionCost), EmbeddedArt.defaultFontNameBold, 18, EmbeddedArt.greenTextColor));
            _loc_6.append(ASwingHelper.horizontalStrut(30));
            _loc_1.append(_loc_6);
            m_contentPanel.append(_loc_1);
            m_contentPanel.append(ASwingHelper.horizontalStrut(17));
            ASwingHelper.prepare(m_contentPanel);
            Sprite _loc_7 =new Sprite ();
            _loc_7.graphics.lineStyle(2, 14733997);
            _loc_7.graphics.moveTo(0, 0);
            _loc_7.graphics.lineTo(350, 0);
            _loc_4.setAsset(_loc_7);
            ASwingHelper.prepare(m_contentPanel);
            return m_contentPanel;
        }//end

    }




