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
import Engine.Managers.*;
import ZLocalization.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.event.*;

    public class VisitorsPanel extends JPanel
    {
        protected  int GRID_WIDTH =590;
        protected  int IDEAL_HEIGHT =330;
        protected JPanel m_gridHolder ;
        protected JPanel m_scrollPanel ;
        protected JButton m_downButton ;
        protected JButton m_upButton ;
        private AssetPane m_imagePane ;
        protected Array m_dataArr ;
        protected Array m_top5Friends ;
        protected Array m_bottom5Friends ;
        protected int m_indexOffset ;
        public static  String PREPARE ="prepare";

        public  VisitorsPanel (LayoutManager param1)
        {
            if (Global.isVisiting())
            {
                StatsManager.sample(1000, "dialog", "visitorcenter", "leaderboard", "visitorboard");
            }
            else
            {
                StatsManager.sample(1000, "dialog", "visitorcenter", "leaderboard", "ownboard");
            }
            super(new FlowLayout(FlowLayout.CENTER));
            this.init();
            return;
        }//end

        private int  compareItems (Array param1 ,Array param2 )
        {
            if (param1.get(3) > param2.get(3))
            {
                return -1;
            }
            if (param1.get(3) < param2.get(3))
            {
                return 1;
            }
            return 0;
        }//end

        protected void  init ()
        {
            String _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            Array _loc_4 =null ;
            _loc_1 =Global.world.mostFrequentHelpers ;
            this.m_dataArr = new Array();
            for(int i0 = 0; i0 < _loc_1.size(); i0++)
            {
            		_loc_2 = _loc_1.get(i0);

                _loc_4 = _loc_1.get(_loc_2);
                this.m_dataArr.push(.get(_loc_2, _loc_4.get(0), _loc_4.get(1), _loc_4.get(2)));
            }
            this.m_dataArr.sort(this.compareItems, Array.NUMERIC);
            this.m_top5Friends = this.m_dataArr.slice(0, 5);
            this.m_bottom5Friends = this.m_dataArr.slice(5, 10);
            _loc_3 =(DisplayObject) new VisitorCenterDialog.assetDict.get("inner_undertab");
            ASwingHelper.setBackground(this, _loc_3);
            if (this.m_dataArr.length > 0)
            {
                this.append(this.makeFriendGrid(this.m_top5Friends));
                this.append(this.makeScrollBar());
            }
            else
            {
                this.append(this.makeAddVisitors());
            }
            this.append(ASwingHelper.horizontalStrut(10));
            this.setPreferredHeight(this.IDEAL_HEIGHT);
            return;
        }//end

        protected JPanel  makeAddVisitors ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER );
            _loc_2 = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs","VisitorUI_moreVisitors"),400,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,16,EmbeddedArt.darkBrownTextColor );
            ASwingHelper.setEasyBorder(_loc_2, 75);
            _loc_1.append(_loc_2);
            ASwingHelper.setEasyBorder(_loc_1, 0, 117, 0, 118);
            return _loc_1;
        }//end

        protected JPanel  makeFriendGrid (Array param1 )
        {
            LocalizationObjectToken _loc_2 =null ;
            JPanel _loc_5 =null ;
            int _loc_6 =0;
            String _loc_7 =null ;
            JPanel _loc_8 =null ;
            String _loc_9 =null ;
            JLabel _loc_10 =null ;
            JLabel _loc_11 =null ;
            JPanel _loc_12 =null ;
            JPanel _loc_13 =null ;
            JLabel _loc_14 =null ;
            String _loc_15 =null ;
            Player _loc_16 =null ;
            Sprite _loc_17 =null ;
            if (this.m_gridHolder)
            {
                this.m_gridHolder.removeAll();
            }
            else
            {
                this.m_gridHolder = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            }
            this.m_gridHolder.append(ASwingHelper.verticalStrut(10));
            int _loc_3 =0;
            while (_loc_3 < param1.length())
            {

                _loc_5 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT, 3);
                _loc_6 = param1.get(_loc_3).get(3);
                _loc_2 = ZLoc.tk("Dialogs", "VisitorUI_visit", "", _loc_6);
                _loc_7 = ZLoc.t("Dialogs", "VisitorUI_visitortext", {amount:_loc_6, visit:_loc_2});
                _loc_8 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
                _loc_9 = param1.get(_loc_3).get(1);
                if (param1.get(_loc_3).get(2) != null)
                {
                    _loc_9 = ZLoc.t("Dialogs", "VisitorUI_from", {user:param1.get(_loc_3).get(1), cityName:param1.get(_loc_3).get(2)});
                }
                _loc_10 = ASwingHelper.makeLabel(_loc_9, EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.brownTextColor);
                _loc_11 = ASwingHelper.makeLabel(_loc_7, EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.blueTextColor);
                _loc_12 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
                _loc_12.append(_loc_10);
                _loc_13 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
                _loc_13.append(_loc_11);
                _loc_8.appendAll(_loc_12, _loc_13);
                _loc_14 = ASwingHelper.makeLabel(String((_loc_3 + 1) + this.m_indexOffset), EmbeddedArt.titleFont, 36, EmbeddedArt.blueTextColor, JLabel.CENTER);
                _loc_14.setPreferredWidth(50);
                if (_loc_3 % 2 == 0)
                {
                    _loc_17 = new Sprite();
                    _loc_17.graphics.beginFill(EmbeddedArt.lightBlueTextColor);
                    _loc_17.graphics.drawRect(0, 0, 637, 55);
                    _loc_17.graphics.endFill();
                    ASwingHelper.setBackground(_loc_5, _loc_17);
                }
                ASwingHelper.setEasyBorder(_loc_5, 5, 15, 5, 5);
                this.m_imagePane = new AssetPane(new EmbeddedArt.hud_no_profile_pic());
                _loc_15 = ((String)param1.get(_loc_3).get(0)).substr(1);
                _loc_16 = Global.player.findFriendById(_loc_15);
                if (_loc_16 != null)
                {
                    this.loadImage(this.m_imagePane, _loc_16);
                }
                else if (_loc_15 == Global.player.snUser.uid)
                {
                    this.loadImageSelf(this.m_imagePane, Global.player.snUser.picture);
                }
                _loc_5.appendAll(ASwingHelper.horizontalStrut(10), _loc_14, this.m_imagePane, _loc_8);
                _loc_5.setPreferredWidth(this.GRID_WIDTH);
                this.m_gridHolder.append(_loc_5);
                _loc_3++;
            }
            ASwingHelper.prepare(this.m_gridHolder);
            _loc_4 = this.IDEAL_HEIGHT -this.m_gridHolder.getHeight ();
            this.m_gridHolder.append(ASwingHelper.verticalStrut(_loc_4));
            return this.m_gridHolder;
        }//end

        protected void  loadImage (AssetPane param1 ,Player param2 )
        {
            imagePane = param1;
            tempFriend = param2;
            LoadingManager .load (tempFriend .snUser .picture ,void  (Event event )
            {
                imagePane.setAsset(event.target.content);
                return;
            }//end
            );
            return;
        }//end

        protected void  loadImageSelf (AssetPane param1 ,String param2 )
        {
            imagePane = param1;
            picture = param2;
            LoadingManager .load (picture ,void  (Event event )
            {
                imagePane.setAsset(event.target.content);
                return;
            }//end
            );
            return;
        }//end

        protected void  showTop5 (AWEvent event )
        {
            this.m_indexOffset = 0;
            this.makeFriendGrid(this.m_top5Friends);
            this.m_upButton.setEnabled(false);
            this.m_downButton.setEnabled(true);
            dispatchEvent(new Event(VisitorsPanel.PREPARE, true));
            return;
        }//end

        protected void  showBottom5 (AWEvent event )
        {
            this.m_indexOffset = 5;
            this.makeFriendGrid(this.m_bottom5Friends);
            this.m_upButton.setEnabled(true);
            this.m_downButton.setEnabled(false);
            dispatchEvent(new Event(VisitorsPanel.PREPARE, true));
            return;
        }//end

        protected JPanel  makeScrollBar ()
        {
            this.m_scrollPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER, 100);
            ASwingHelper.setBackground(this.m_scrollPanel, new VisitorCenterDialog.assetDict.get("conventionCenter_pagination_border"));
            AssetPane _loc_1 =new AssetPane(new VisitorCenterDialog.assetDict.get( "conventionCenter_pagination_dot") );
            _loc_2 = ASwingHelper.centerElement(_loc_1 );
            this.m_downButton = new JButton();
            this.m_upButton = new JButton();
            this.m_downButton.wrapSimpleButton(new SimpleButton(new VisitorCenterDialog.assetDict.get("conventionCenter_pagination_bottom_up"), new VisitorCenterDialog.assetDict.get("conventionCenter_pagination_bottom_over"), new VisitorCenterDialog.assetDict.get("conventionCenter_pagination_bottom_over"), new VisitorCenterDialog.assetDict.get("conventionCenter_pagination_bottom_over")));
            this.m_upButton.wrapSimpleButton(new SimpleButton(new VisitorCenterDialog.assetDict.get("conventionCenter_pagination_top_up"), new VisitorCenterDialog.assetDict.get("conventionCenter_pagination_top_over"), new VisitorCenterDialog.assetDict.get("conventionCenter_pagination_top_over"), new VisitorCenterDialog.assetDict.get("conventionCenter_pagination_top_over")));
            this.m_upButton.setEnabled(false);
            if (this.m_dataArr.length < 6)
            {
                this.m_downButton.setEnabled(false);
            }
            this.m_upButton.addActionListener(this.showTop5, 0, true);
            this.m_downButton.addActionListener(this.showBottom5, 0, true);
            this.m_scrollPanel.setPreferredHeight(300);
            this.m_scrollPanel.setPreferredWidth(50);
            this.m_scrollPanel.appendAll(ASwingHelper.verticalStrut(-80), this.m_upButton, _loc_2, this.m_downButton);
            return this.m_scrollPanel;
        }//end

    }



