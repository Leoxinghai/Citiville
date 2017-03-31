package Display.GateUI;

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
import Events.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.text.*;
import org.aswing.*;

    public class CrewKeyCell extends KeyCell
    {
        private String m_footerText ;
        private Class m_backgroundClass ;
        private static IStaticCellRenderer s_askBuddy ;

        public  CrewKeyCell (String param1 ,String param2 ,double param3 ,double param4 ,String param5 ,Class param6 ,Class param7 =null )
        {
            super(param1, param2, param3, param4, 0, param7);
            this.m_footerText = param5;
            this.m_backgroundClass = param6;
            HPADDING = 0;
            return;
        }//end

         protected Component  createHeaderComponent ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2 = Global.localizer.langCode=="ja"? (0) : (35);
            _loc_3 = m_name? (m_name) : ("");
            _loc_4 = ASwingHelper.makeMultilineCapsText(_loc_3,100,EmbeddedArt.titleFont,TextFormatAlign.CENTER,13,14123308,null,false,_loc_2);
            _loc_5 = (TextField)ASwingHelper.makeMultilineCapsText(_loc_3,100,EmbeddedArt.titleFont,TextFormatAlign.CENTER,13,14123308,null,false,_loc_2).getAsset()
            while (_loc_5.numLines < 2)
            {

                m_name = "\n" + m_name;
                _loc_4 = ASwingHelper.makeMultilineCapsText(m_name, 100, EmbeddedArt.titleFont, TextFormatAlign.CENTER, 13, 14123308, null, false, _loc_2);
                _loc_5 =(TextField) _loc_4.getAsset();
            }
            _loc_1.append(_loc_4);
            return _loc_1;
        }//end

         protected Component  createTopButtonComponent ()
        {
            return null;
        }//end

         protected Component  createBottomButtonComponent ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            AssetIcon _loc_2 =new AssetIcon(new EmbeddedArt.icon_cash ()as DisplayObject );
            CustomButton _loc_3 =new CustomButton(m_cost.toString (),_loc_2 ,"BuyGateKeyButtonUI");
            _loc_3.addEventListener(MouseEvent.CLICK, this.onBuyClick);
            _loc_4 = _loc_3.getMargin();
            _loc_3.setMargin(new Insets(_loc_4.top, _loc_4.left + 2, _loc_4.bottom + 3, _loc_4.right + 2));
            _loc_1.append(_loc_3);
            return _loc_1;
        }//end

         protected Component  createFooterComponent ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            m_itemCount = new JLabel(this.m_footerText);
            m_itemCount.setForeground(new ASColor(3776202, 1));
            m_itemCount.setFont(ASwingHelper.getBoldFont(14));
            m_itemCount.setTextFilters(.get(new GlowFilter(16777215, 1, 2, 2, 10)));
            _loc_1.append(m_itemCount);
            return _loc_1;
        }//end

         protected Component  createImageComponent ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
            Sprite _loc_3 =new Sprite ();
            _loc_4 = newthis.m_backgroundClass();
            _loc_3.addChild(_loc_4);
            _loc_3.addChild(m_itemIcon);
            m_itemIcon.x = (_loc_4.width - m_itemIcon.width) / 2;
            m_itemIcon.y = (_loc_4.height - m_itemIcon.height) / 2;
            AssetPane _loc_5 =new AssetPane(_loc_3 );
            _loc_2.appendAll(_loc_5);
            _loc_3.buttonMode = true;
            _loc_3.addEventListener(MouseEvent.CLICK, this.inviteFriends, false, 0, true);
            _loc_1.append(ASwingHelper.verticalStrut(75));
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

        private void  inviteFriends (MouseEvent event )
        {
            dispatchEvent(new Event("friendClicked", true));
            return;
        }//end

         protected void  onBuyClick (MouseEvent event )
        {
            Player _loc_3 =null ;
            _loc_2 = m_buyCallback(m_cost);
            if (_loc_2)
            {
                _loc_5 = m_count+1;
                m_count = _loc_5;
                _loc_3 = Global.player.findFriendById("-1");
                m_url = _loc_3.snUser.picture;
                this.m_footerText = _loc_3.firstName;
                setCellValue(m_value);
                initializeCell();
                dispatchEvent(new Event("update_discount", true));
            }
            return;
        }//end

        public void  setCrewKeyCellValue (Object param1)
        {
            m_count = 1;
            m_url = param1.url;
            this.m_footerText = param1.footerText;
            super.setCellValue(param1);
            return;
        }//end

        public Object  getCrewKeyCellValue ()
        {
            Object _loc_1 =new Object ();
            _loc_1.count = m_count;
            _loc_1.footerText = this.m_footerText;
            _loc_1.amountNeeded = m_amountNeeded;
            _loc_1.backgroundClass = m_displayObjectClass;
            _loc_1.friendName = this.m_footerText;
            _loc_1.name = m_name;
            _loc_1.picUrl = m_url;
            _loc_1.url = m_url;
            return _loc_1;
        }//end

        public boolean  isOpenPosition ()
        {
            if (m_count == 0)
            {
                return true;
            }
            return false;
        }//end

         protected Component  createActionCompleteComponent ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            if (m_footerComponent)
            {
                _loc_1.append(m_footerComponent);
            }
            if (m_askHolder)
            {
                _loc_1.append(m_askHolder);
            }
            return _loc_1;
        }//end

         protected void  onIconFail (Event event )
        {
            m_displayObjectClass = EmbeddedArt.hud_no_profile_pic;
            m_itemIcon = new m_displayObjectClass();
            onIconLoad(event);
            return;
        }//end

         protected void  onWishlistChanged (DataItemEvent event )
        {
            return;
        }//end

         protected IStaticCellRenderer  createAskBuddyRenderer ()
        {
            StaticCrewCellRenderer _loc_1 =null ;
            Class _loc_2 =null ;
            if (!s_askBuddy)
            {
                _loc_1 = new StaticCrewCellRenderer();
                _loc_1.label = "askBuddyCrewHeader";
                _loc_1.buttonLabel = "askBuddyCrewButton";
                _loc_1.footerLabel = "askBuddyCrewFooter";
                _loc_2 =(Class) GateDialog.assets.buildingBuddy_helmet;
                _loc_1.icon = new _loc_2;
                s_askBuddy = _loc_1;
            }
            s_askBuddy.refreshHandler = onTimerComplete;
            s_askBuddy.buttonClickHandler = onAskBuddy;
            return s_askBuddy;
        }//end

    }




