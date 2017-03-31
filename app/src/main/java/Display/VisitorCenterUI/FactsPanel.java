package Display.VisitorCenterUI;

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
import VisitingCenter.*;
import root.ZLoc;
//import flash.display.*;
import org.aswing.*;

    public class FactsPanel extends JPanel
    {
        protected  int GRID_WIDTH =670;
        protected  int IDEAL_HEIGHT =330;

        public  FactsPanel (LayoutManager param1)
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            this.init();
            return;
        }//end

        protected void  init ()
        {
            JPanel _loc_5 =null ;
            JLabel _loc_6 =null ;
            JLabel _loc_7 =null ;
            Sprite _loc_8 =null ;
            DisplayObject _loc_1 =(DisplayObject)new VisitorCenterDialog.assetDict.get( "inner_undertab");
            ASwingHelper.setBackground(this, _loc_1);
            StatsManagerVisitorCenter.generateData();
            Array _loc_2 =[[ZLoc.t("Dialogs","VisitorUI_facts_roads"),StatsManagerVisitorCenter.roads] ,[ZLoc.t("Dialogs","VisitorUI_facts_area"),StatsManagerVisitorCenter.area] ,[ZLoc.t("Dialogs","VisitorUI_facts_emptyfranchises"),StatsManagerVisitorCenter.empty_franchise] ,[ZLoc.t("Dialogs","VisitorUI_facts_marketvalue"),StatsManagerVisitorCenter.marketValue] ,.get(ZLoc.t("Dialogs","VisitorUI_facts_wilderness"),StatsManagerVisitorCenter.wilderness] ,.get(ZLoc.t("Dialogs","VisitorUI_facts_businesses"),StatsManagerVisitorCenter.businesses) ,.get(ZLoc.t("Dialogs","VisitorUI_facts_residences"),StatsManagerVisitorCenter.residences) ,.get(ZLoc.t("Dialogs","VisitorUI_facts_municipals"),StatsManagerVisitorCenter.municipals) ,.get(ZLoc.t("Dialogs","VisitorUI_facts_landmarks"),StatsManagerVisitorCenter.landmarks) ,.get(ZLoc.t("Dialogs","VisitorUI_facts_farmplots"),StatsManagerVisitorCenter.farmPlots)) ;
            int _loc_3 =0;
            while (_loc_3 < _loc_2.length())
            {

                _loc_5 = new JPanel(new BorderLayout());
                _loc_6 = ASwingHelper.makeLabel(_loc_2.get(_loc_3).get(0), EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.blueTextColor);
                _loc_7 = ASwingHelper.makeLabel(_loc_2.get(_loc_3).get(1), EmbeddedArt.titleFont, 18, EmbeddedArt.blueTextColor);
                ASwingHelper.setEasyBorder(_loc_6, 0, 5);
                ASwingHelper.setEasyBorder(_loc_7, 0, 0, 0, 5);
                _loc_5.append(_loc_6, BorderLayout.WEST);
                _loc_5.append(_loc_7, BorderLayout.EAST);
                if (_loc_3 % 2 == 0)
                {
                    _loc_8 = new Sprite();
                    _loc_8.graphics.beginFill(EmbeddedArt.lightBlueTextColor);
                    _loc_8.graphics.drawRect(0, 0, 535, 30);
                    _loc_8.graphics.endFill();
                    ASwingHelper.setBackground(_loc_5, _loc_8);
                }
                ASwingHelper.setEasyBorder(_loc_5, 0, 70, 0, 70);
                _loc_5.setPreferredWidth(this.GRID_WIDTH);
                this.append(_loc_5);
                _loc_3++;
            }
            ASwingHelper.prepare(this);
            _loc_4 = this.IDEAL_HEIGHT -this.getHeight ();
            this.insert(0, ASwingHelper.verticalStrut(_loc_4 / 2));
            this.append(ASwingHelper.verticalStrut(_loc_4 / 2));
            return;
        }//end

    }



