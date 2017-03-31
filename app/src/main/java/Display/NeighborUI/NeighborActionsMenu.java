package Display.NeighborUI;

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
import Events.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;

    public class NeighborActionsMenu extends Sprite
    {
        public  double ACTIONS_MENU_SPACING =30;
        public Array m_slots ;
        public int m_slotsNum ;
        public String m_neighborName ;
        public Dictionary m_commodities ;
        protected Friend m_friend ;
        protected JTextField m_goodsText ;
        protected JPanel m_innerPanel ;
        protected JButton m_buyBtn ;
        protected JButton m_sellBtn ;
        protected JButton m_remindBtn ;
        protected JPanel m_buttonPanel ;
        public static  int BUTTON_SEND_GIFT =0;
        public static  int BUTTON_VISIT =1;
        public static  int BUTTON_CHALLENGE =2;
        public static  int SLOT_HEIGHT =31;

        public  NeighborActionsMenu (Friend param1 )
        {
            this.m_slots = new Array();
            this.m_friend = param1;
            this.m_commodities = param1.commodities;
            this.createBackground();
            return;
        }//end

        public Friend  friend ()
        {
            return this.m_friend;
        }//end

        public void  friend (Friend param1 )
        {
            this.m_friend = param1;
            this.m_commodities = param1.commodities;
            return;
        }//end

        private void  createBackground ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,0);
            this.m_innerPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP, -3);
            this.m_innerPanel.setBorder(new EmptyBorder(null, new Insets(0, 0, 0, 0)));
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            ASwingHelper.setEasyBorder(_loc_3, 0, 10);
            addEventListener(MouseEvent.MOUSE_OVER, this.onMaskNeighborActions, false, 0, true);
            addEventListener(MouseEvent.MOUSE_MOVE, this.onMaskNeighborActions, false, 0, true);
            addEventListener(MouseEvent.MOUSE_OUT, this.onMaskNeighborActions, false, 0, true);
            this.m_buttonPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            ASwingHelper.setEasyBorder(this.m_buttonPanel, 0, 15, 0, 15);
            this.m_remindBtn = new CustomButton(ZLoc.t("Dialogs", "Remind"), null, "OrangeMediumButtonUI");
            this.m_buyBtn = new CustomButton(ZLoc.t("Dialogs", "Buy"), null, "OrangeMediumButtonUI");
            this.m_sellBtn = new CustomButton(ZLoc.t("Dialogs", "Sell"), null, "OrangeMediumButtonUI");
            this.m_remindBtn.setText(ZLoc.t("Dialogs", "Remind"));
            DisplayObject _loc_4 =new EmbeddedArt.nghbr_popup_bg ()as DisplayObject ;
            ASwingHelper.setSizedBackground(_loc_1, _loc_4, new Insets(3));
            this.m_innerPanel.appendAll(_loc_2, _loc_3, ASwingHelper.verticalStrut(5), this.m_buttonPanel);
            _loc_1.append(this.m_innerPanel);
            JWindow _loc_5 =new JWindow(this );
            _loc_5.setContentPane(_loc_1);
            _loc_5.show();
            ASwingHelper.prepare(_loc_5);
            return;
        }//end

        public void  setRemindMode ()
        {
            this.m_buttonPanel.remove(this.m_buyBtn);
            this.m_buttonPanel.remove(this.m_sellBtn);
            this.m_buyBtn.removeActionListener(this.onBuy);
            this.m_sellBtn.removeActionListener(this.onSell);
            this.m_buttonPanel.append(this.m_remindBtn);
            this.m_remindBtn.addActionListener(this.onRemind, 0, true);
            return;
        }//end

        public void  setNormalMode ()
        {
            this.m_remindBtn.removeActionListener(this.onRemind);
            this.m_buttonPanel.remove(this.m_remindBtn);
            this.m_buyBtn.addActionListener(this.onBuy, 0, true);
            this.m_sellBtn.addActionListener(this.onSell, 0, true);
            this.m_buttonPanel.appendAll(this.m_buyBtn, this.m_sellBtn);
            return;
        }//end

        public void  onMaskNeighborClickActions (MouseEvent event )
        {
            event.stopPropagation();
            return;
        }//end

        public void  onMaskNeighborActions (MouseEvent event )
        {
            event.stopImmediatePropagation();
            event.stopPropagation();
            return;
        }//end

        private void  onBuy (AWEvent event )
        {
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.NEIGHBOR_LADDER, "buy");
            dispatchEvent(new FriendTradeEvent(FriendTradeEvent.BUY, this.m_friend));
            return;
        }//end

        private void  onSell (AWEvent event )
        {
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.NEIGHBOR_LADDER, "sell");
            dispatchEvent(new FriendTradeEvent(FriendTradeEvent.SELL, this.m_friend));
            return;
        }//end

        private void  onRemind (AWEvent event )
        {
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.NEIGHBOR_LADDER, "remind");
            dispatchEvent(new FriendTradeEvent(FriendTradeEvent.REMIND, this.m_friend));
            return;
        }//end

    }


