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
import Engine.Managers.*;
//import flash.display.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;

    public class PlayerChooserDialogView extends GenericDialogView
    {

        public  PlayerChooserDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",Function param9 =null ,String param10 ="",boolean param11 =true )
        {
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11);
            return;
        }//end

         protected JPanel  createButtonPanel ()
        {
            JPanel _loc_1 =new JPanel ();
            return _loc_1;
        }//end

         protected JPanel  createTextArea ()
        {
            String _loc_3 =null ;
            String _loc_4 =null ;
            JPanel _loc_16 =null ;
            String _loc_17 =null ;
            JLabel _loc_18 =null ;
            JPanel _loc_19 =null ;
            JPanel _loc_20 =null ;
            CustomDataButton _loc_21 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER,10);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,10);
            _loc_3 = ZLoc.t("Dialogs", "CampaignRally_header");
            _loc_4 = ZLoc.t("Dialogs", "CampaignRally_header1");
            AssetPane _loc_5 =new AssetPane(new (DisplayObject)m_assetDict.get( "campaignRally_sam"));
            AssetPane _loc_6 =new AssetPane(new (DisplayObject)m_assetDict.get( "campaignRally_dialogDivider"));
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.Y_AXIS,10);
            _loc_8 = ASwingHelper.makeMultilineText(_loc_3,300,EmbeddedArt.defaultFontNameBold,TextFormatAlign.CENTER,22,EmbeddedArt.blueTextColor);
            _loc_9 = ASwingHelper.makeMultilineText(_loc_4,300,EmbeddedArt.defaultFontNameBold,TextFormatAlign.CENTER,20,EmbeddedArt.brownTextColor);
            AssetPane _loc_10 =new AssetPane(new (DisplayObject)m_assetDict.get( "campaignRally_w2w_90x90"));
            _loc_11 = Global.player.inactiveNeighbors.length;
            _loc_12 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.Y_AXIS,10);
            _loc_13 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,10);
            _loc_14 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER,10);
            int _loc_15 =0;
            while (_loc_15 < Global.player.inactiveNeighbors.length())
            {

                _loc_16 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.Y_AXIS, 1);
                _loc_17 = Global.player.getFriendFirstName(Global.player.inactiveNeighbors.get(_loc_15));
                if (_loc_17 == null)
                {
                    _loc_17 = "No Name";
                }
                _loc_18 = ASwingHelper.makeLabel(_loc_17, EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.brownTextColor, JLabel.CENTER);
                _loc_19 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER, 10);
                ASwingHelper.setBackground(_loc_19, new (DisplayObject)m_assetDict.get("campaignRally_profileBox"));
                _loc_20 = ASwingHelper.makeFriendImageFromZid(Global.player.inactiveNeighbors.get(_loc_15));
                ASwingHelper.setEasyBorder(_loc_20, 8, 10, 20, 10);
                _loc_19.append(_loc_20);
                _loc_21 = new CustomDataButton(ZLoc.t("Dialogs", "CampaignRally_send"), null, "GreenButtonUI", {id:Global.player.inactiveNeighbors.get(_loc_15)});
                _loc_21.addActionListener(this.sendThis, 0, true);
                _loc_16.appendAll(_loc_18, _loc_19, _loc_21);
                if (_loc_15 <= 4)
                {
                    _loc_13.append(_loc_16);
                }
                else
                {
                    _loc_14.append(_loc_16);
                }
                _loc_15++;
            }
            ASwingHelper.setEasyBorder(_loc_14, 0, 0, 15, 0);
            _loc_12.appendAll(_loc_13, _loc_14);
            _loc_7.appendAll(_loc_8, _loc_9);
            _loc_2.appendAll(_loc_5, _loc_6, _loc_7, _loc_10);
            _loc_1.appendAll(_loc_2, _loc_12);
            ASwingHelper.setEasyBorder(_loc_1, 0, 10, 10, 10);
            ASwingHelper.setBackground(_loc_2, new (DisplayObject)m_assetDict.get("campaignRally_whiteRectangleBG"));
            ASwingHelper.setBackground(_loc_12, new (DisplayObject)m_assetDict.get("campaignRally_whiteRectangleBG"));
            return _loc_1;
        }//end

        private void  sendThis (AWEvent event )
        {
            _loc_2 = event.target.data;
            _loc_3 = _loc_2.id;
            _loc_4 = Global.player.getFriendFirstName(_loc_3);
            Global.world.viralMgr.sendCampaignPayerFeed(Global.player, _loc_4, _loc_3);
            (event.currentTarget as JButton).setEnabled(false);
            return;
        }//end

         protected void  onCancel (Object param1)
        {
            StatsManager.count("dialog", "popup", "close", "campaign_rally_payer_react");
            closeMe();
            return;
        }//end

    }




