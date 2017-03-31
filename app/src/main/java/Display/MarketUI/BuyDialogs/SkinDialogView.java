package Display.MarketUI.BuyDialogs;

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
import Display.MarketUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Modules.remodel.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.colorchooser.*;
import org.aswing.event.*;

    public class SkinDialogView extends GenericDialogView
    {
        protected Catalog m_catalog ;
        protected Item m_item ;

        public  SkinDialogView (Dictionary param1 ,String param2 ="",String param3 ="",int param4 =0,Function param5 =null ,String param6 ="",int param7 =0,String param8 ="",Function param9 =null ,String param10 ="",boolean param11 =true )
        {
            this.m_catalog = param1.get("catalog");
            this.m_item = param1.get("item");
            super(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11);
            return;
        }//end

         protected JPanel  createTextArea ()
        {
            double _loc_3 =0;
            JPanel _loc_15 =null ;
            JPanel _loc_16 =null ;
            JLabel _loc_17 =null ;
            CustomButton _loc_18 =null ;
            JLabel _loc_19 =null ;
            CustomButton _loc_20 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP ,10);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER ,10);
            if (m_icon != null && m_icon != "")
            {
                _loc_3 = setMessageTextWidth(m_iconPos == ICON_POS_LEFT || m_iconPos == ICON_POS_RIGHT);
                _loc_15 = createIconPane();
            }
            _loc_4 = createTextComponent(_loc_3);
            _loc_5 = createTextAreaInnerPane(_loc_4);
            _loc_6 = _loc_15;
            _loc_7 = _loc_5;
            if (m_iconPos == ICON_POS_RIGHT || m_iconPos == ICON_POS_BOTTOM)
            {
                _loc_6 = _loc_5;
                _loc_7 = _loc_15;
            }
            _loc_2.append(ASwingHelper.horizontalStrut(m_messagePaddingLeft));
            if (m_iconPos == ICON_POS_LEFT || m_iconPos == ICON_POS_RIGHT)
            {
                if (_loc_6)
                {
                    _loc_2.append(_loc_6);
                }
                if (_loc_7)
                {
                    _loc_2.append(_loc_7);
                }
            }
            _loc_2.append(ASwingHelper.horizontalStrut(m_messagePaddingRight));
            _loc_8 = _loc_4.getHeight();
            _loc_9 = ASwingHelper.makeFlowJPanel(FlowLayout.CENTER ,20);
            AssetIcon _loc_10 =new AssetIcon(new EmbeddedArt.icon_coin ()as DisplayObject );
            AssetIcon _loc_11 =new AssetIcon(new EmbeddedArt.icon_coin ()as DisplayObject );
            JPanel _loc_12 =new JPanel(new VerticalLayout(VerticalLayout.CENTER ,3));
            if (RemodelManager.hasRemodelEligibleResidence(this.m_item))
            {
                _loc_16 = new JPanel(new VerticalLayout(VerticalLayout.CENTER, 3));
                _loc_17 = ASwingHelper.makeLabel(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "skin_reskin")), EmbeddedArt.titleFont, 16, EmbeddedArt.darkBlueTextColor, JLabel.CENTER);
                _loc_18 = new CustomButton(String(this.m_item.cost), _loc_10, "GreenButtonUI");
                _loc_18.addActionListener(this.reskinBtn, 0, true);
                _loc_16.appendAll(_loc_17, _loc_18);
            }
            else
            {
                _loc_9.append(ASwingHelper.horizontalStrut(50));
            }
            _loc_13 =Global.gameSettings().getItemByName(this.m_item.derivedItemName );
            _loc_14 =Global.gameSettings().getItemByName(this.m_item.derivedItemName ).cost +this.m_item.cost ;
            if (this.m_item.derivedItemName != "res_simpsonmegabrick")
            {
                if (this.m_item.derivedItemName == "res_beachfrontapt_a" && !RemodelManager.hasRemodelEligibleResidence(this.m_item))
                {
                }
                else
                {
                    _loc_19 = ASwingHelper.makeLabel(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "skin_newskin")), EmbeddedArt.titleFont, 16, EmbeddedArt.darkBlueTextColor, JLabel.CENTER);
                    _loc_20 = new CustomButton(String(_loc_14), _loc_11, "GreenButtonUI");
                    _loc_20.addActionListener(this.newskinBtn, 0, true);
                    _loc_12.appendAll(_loc_19, _loc_20);
                }
            }
            _loc_9.appendAll(_loc_16, _loc_12);
            _loc_1.appendAll(_loc_2, _loc_9, ASwingHelper.verticalStrut(15));
            return _loc_1;
        }//end

         protected JPanel  createButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(new FlowLayout(AsWingConstants.CENTER ));
            return _loc_1;
        }//end

        protected void  reskinBtn (AWEvent event )
        {
            if (this.m_catalog.canBuy(this.m_item))
            {
                StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "remodel", "click_reskin_button");
                this.m_catalog.onBuy(this.m_item, {remodel:"skin"});
                closeMe();
            }
            return;
        }//end

        protected void  newskinBtn (AWEvent event )
        {
            if (this.m_catalog.canBuy(this.m_item))
            {
                StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, "remodel", "click_combobuy_button");
                this.m_catalog.onBuy(this.m_item, {remodel:"combined"});
                closeMe();
            }
            return;
        }//end

    }



