package Display.HunterAndPreyUI.HunterCellStates;

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
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.HunterAndPreyUI.*;
import Display.ValentineUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.bandits.*;
import Modules.workers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;

    public class GenericHunterState extends JPanel
    {
        protected HunterData m_officer ;
        protected int m_color ;
        protected int m_pos ;
        protected boolean m_donut =false ;
        protected HunterCell m_cell ;
        public Insets m_buttonInsets ;
        protected Player m_cop ;
        protected JTextField m_timeLeft ;
        protected Timer m_nextActiveTimer ;

        public  GenericHunterState (HunterData param1 ,int param2 ,HunterCell param3 )
        {
            this.m_buttonInsets = new Insets(3, 5, 3, 5);
            super(new BorderLayout());
            this.m_cell = param3;
            this.m_color = param2;
            if (param1 != null)
            {
                this.m_officer = param1;
                this.m_pos = this.m_officer.getPosition();
                this.m_donut = this.m_officer.getState() == HunterData.STATE_PATROLLING;
                if (this.m_officer.getZID().charAt(1) == "-")
                {
                    this.m_cop = Global.player.findFriendById("-1");
                }
                else
                {
                    this.m_cop = Global.player.findFriendById(this.m_officer.getZID().substr(1));
                }
            }
            this.init();
            return;
        }//end

        protected void  init ()
        {
            Sprite _loc_6 =null ;
            JPanel _loc_1 =new JPanel(new BorderLayout ());
            _loc_2 = ASwingHelper.makeFlowJPanel(FlowLayout.RIGHT,10);
            _loc_3 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            _loc_2.appendAll(this.makeActionPanel());
            _loc_4 = PreyManager.getHunterPreyMode(HunterDialog.groupId);
            if (int(_loc_4.get("showResourcePanel")) == 1)
            {
                _loc_2.append(this.makeDonutPanel());
            }
            _loc_3.appendAll(this.makeImagePanel(), this.makeNamePanel());
            ASwingHelper.prepare(_loc_2);
            _loc_5 = HunterScrollingList(.ROW_HEIGHT-_loc_2.getHeight())/2;
            ASwingHelper.setEasyBorder(_loc_2, _loc_5, 0, 0, 10);
            _loc_1.append(_loc_3, BorderLayout.WEST);
            _loc_1.append(_loc_2, BorderLayout.EAST);
            if (this.m_color % 2 == 0)
            {
                _loc_6 = new Sprite();
                _loc_6.graphics.beginFill(EmbeddedArt.lightBlueTextColor);
                _loc_6.graphics.drawRect(0, 0, 595, 50);
                _loc_6.graphics.endFill();
                ASwingHelper.setBackground(_loc_1, _loc_6);
            }
            _loc_1.setPreferredWidth(595);
            _loc_1.setMinimumWidth(595);
            _loc_1.setMaximumWidth(595);
            this.append(_loc_1);
            return;
        }//end

        protected JPanel  makeImagePanel ()
        {
            DisplayObject imageAsset ;
            AssetPane ap ;
            jp = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            imageAsset =(DisplayObject) new HunterDialog.assetDict.get("police_slot_unfilled");
            portraitURL = this.m_cop? (this.m_cop.snUser.picture) : (Global.getAssetURL("assets/dialogs/citysam_neighbor_card.jpg"));
            LoadingManager .load (portraitURL ,void  (Event event )
            {
                imageAsset = event.target.content;
                ap.setAsset(imageAsset);
                return;
            }//end
            );
            ap = new AssetPane(imageAsset);
            jp.append(ap);
            return jp;
        }//end

        protected JPanel  makeNamePanel ()
        {
            String _loc_2 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP,-5);
            if (this.m_cop)
            {
                _loc_2 = this.m_cop.snUser.name;
            }
            _loc_3 = ASwingHelper.makeLabel(_loc_2,EmbeddedArt.defaultFontNameBold,14,EmbeddedArt.brownTextColor);
            _loc_4 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            _loc_4.append(_loc_3);
            _loc_1.append(_loc_4);
            _loc_5 = HunterDialog.groupId+"_hunterPosition"+String(this.m_officer.getPosition());
            _loc_6 = ASwingHelper.makeLabel(ZLoc.t("Dialogs",_loc_5),EmbeddedArt.defaultFontNameBold,14,EmbeddedArt.blueTextColor);
            _loc_7 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            _loc_7.append(_loc_6);
            _loc_1.append(_loc_7);
            return _loc_1;
        }//end

        protected JPanel  makeActionPanel ()
        {
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            return _loc_1;
        }//end

        protected JPanel  makeDonutPanel ()
        {
            DisplayObject _loc_2 =null ;
            AssetPane _loc_3 =null ;
            Sprite _loc_4 =null ;
            _loc_1 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            if (this.m_donut)
            {
                _loc_2 = new Bitmap(HunterDialog.assetDict.get("resource").bitmapData);
                _loc_3 = new AssetPane(_loc_2);
            }
            else
            {
                _loc_4 = new Sprite();
                _loc_2 = new Bitmap(HunterDialog.assetDict.get("resource_empty").bitmapData);
                _loc_4.addChild(_loc_2);
                _loc_4.addEventListener(MouseEvent.CLICK, this.askDonut, false, 0, true);
                _loc_3 = new AssetPane(_loc_4);
            }
            _loc_1.append(_loc_3);
            return _loc_1;
        }//end

        protected void  askDonut (MouseEvent event )
        {
            GenericDialog _loc_2 =new GenericDialog(ZLoc.t("Dialogs",HunterDialog.groupId +"_HunterResourceMsg"),"",GenericDialogView.TYPE_CUSTOM_OK ,this.sendDonutsFeed ,"","",true ,0,"",null ,ZLoc.t("Dialogs",HunterDialog.groupId +"_askForResource"));
            UI.displayPopup(_loc_2);
            PreyUtil.logDialogStats(HunterDialog.groupId, "empty_resource", "hub_ui");
            PreyUtil.logDialogStats(HunterDialog.groupId, "view", "empty_resource_popup");
            return;
        }//end

        protected void  sendDonutsFeed (GenericPopupEvent event )
        {
            boolean _loc_2 =false ;
            if (event.button == GenericDialogView.YES)
            {
                _loc_2 = Global.world.viralMgr.sendGetHunterResource(HunterDialog.groupId);
                if (!_loc_2)
                {
                    UI.displayMessage(ZLoc.t("Dialogs", HunterDialog.groupId + "_HunterFeedMax"), GenericDialogView.TYPE_OK);
                    PreyUtil.logDialogStats(HunterDialog.groupId, "view", "request_limit_ui");
                }
                else
                {
                    PreyUtil.logDialogStats(HunterDialog.groupId, "get_resource", "empty_resource_popup");
                }
            }
            else
            {
                PreyUtil.logDialogStats(HunterDialog.groupId, "X", "empty_resource_popup");
            }
            return;
        }//end

        protected void  rebuildEvent ()
        {
            return;
        }//end

        protected double  getTimeLeft ()
        {
            return 0;
        }//end

        protected void  changeCellState ()
        {
            return;
        }//end

        protected void  doAction (AWEvent event )
        {
            return;
        }//end

        protected void  changeState ()
        {
            return;
        }//end

        protected void  updateTimerString (TimerEvent event )
        {
            TextFormat _loc_3 =null ;
            _loc_2 = this.getTimeLeft();
            if (_loc_2 <= 0)
            {
                this.changeState();
            }
            else
            {
                this.m_timeLeft.getTextField().text = GameUtil.formatMinutesSeconds(this.getTimeLeft());
                _loc_3 = this.m_timeLeft.getTextFormat();
                _loc_3.align = TextFormatAlign.RIGHT;
                this.m_timeLeft.setTextFormat(_loc_3);
                dispatchEvent(new Event(MakerPanel.PREPARE, true));
            }
            return;
        }//end

    }



