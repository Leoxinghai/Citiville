package Modules.factory.ui;

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
//import flash.display.*;
import org.aswing.*;

    public class WorkerCell extends CrewCell
    {
        protected Function m_layoutInfoPanel ;

        public  WorkerCell (CrewCellFactory param1 ,LayoutManager param2 )
        {
            super(param1, param2);
            return;
        }//end

        public String  position ()
        {
            return m_cellData.position;
        }//end

        public String  friendName ()
        {
            return m_cellData.friendName;
        }//end

         protected JPanel  makeInfoPanel ()
        {
            JTextField _loc_5 =null ;
            JPanel _loc_6 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            JPanel _loc_3 =null ;
            _loc_4 = ASwingHelper.makeTextField(TextFieldUtil.formatSmallCapsString(this.position )+" ",EmbeddedArt.DEFAULT_FONT_NAME_BOLD ,14,EmbeddedArt.darkBlueTextColor );
            if (this.friendName)
            {
                _loc_5 = ASwingHelper.makeTextField(this.friendName + " ", EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.darkBrownTextColor);
            }
            else
            {
                _loc_5 = ASwingHelper.makeTextField(ZLoc.t("Dialogs", "EmptyCrewCell") + " ", EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor);
                _loc_3 = this.makeRewardPanel();
            }
            _loc_2.appendAll(_loc_4, _loc_5);
            _loc_1.append(_loc_2);
            if (_loc_3)
            {
                ASwingHelper.prepare(_loc_2, _loc_3);
                _loc_6 = ASwingHelper.horizontalStrut(m_factory.preferredCellWidth - m_imagePanel.getWidth() - _loc_2.getWidth() - _loc_3.getWidth() - 15);
                _loc_1.appendAll(_loc_6, _loc_3);
            }
            return _loc_1;
        }//end

        protected JPanel  makeRewardPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            AssetPane _loc_3 =new AssetPane(new m_factory.assets.get( "cell_goods") );
            _loc_2.append(_loc_3);
            _loc_4 = ASwingHelper.makeLabel(m_factory.assets.stringAssets.workerBonusText ,EmbeddedArt.titleFont ,14,EmbeddedArt.greenTextColor );
            _loc_1.appendAll(_loc_2, _loc_4);
            return _loc_1;
        }//end

    }



