package Display.MarketUI;

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
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.sale.*;
import Modules.sale.payments.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class SpecialsPanel extends JPanel
    {
        protected Dictionary m_callbacks ;
        protected JButton m_leftBtn ;
        protected JButton m_rightBtn ;
        private static  Array PANEL_IDS =.get( "promoPanel","featurePanel","cashPanel") ;

        public  SpecialsPanel ()
        {
            super(new FlowLayout(FlowLayout.LEFT, 10, 0, false));
            this.makeButtons();
            this.init();
            return;
        }//end

        private void  setupCallBacks ()
        {
            this.m_callbacks = new Dictionary(true);
            this.m_callbacks.put("showCategory",  this.showCategory);
            this.m_callbacks.put("getCash",  this.getCash);
            this.m_callbacks.put("showMinigame",  this.showMinigame);
            return;
        }//end

        protected void  makeButtons ()
        {
            DisplayObject _loc_1 =(DisplayObject)new Catalog.assetDict.get( "btn_left_offstate");
            DisplayObject _loc_2 =(DisplayObject)new Catalog.assetDict.get( "btn_left_onstate");
            DisplayObject _loc_3 =(DisplayObject)new Catalog.assetDict.get( "btn_left_downstate");
            DisplayObject _loc_4 =(DisplayObject)new Catalog.assetDict.get( "btn_right_offstate");
            DisplayObject _loc_5 =(DisplayObject)new Catalog.assetDict.get( "btn_right_onstate");
            DisplayObject _loc_6 =(DisplayObject)new Catalog.assetDict.get( "btn_right_downstate");
            this.m_leftBtn = new JButton();
            this.m_leftBtn.wrapSimpleButton(new SimpleButton(_loc_1, _loc_2, _loc_3, _loc_1));
            this.m_leftBtn.setEnabled(false);
            ASwingHelper.setEasyBorder(this.m_leftBtn, 0, 2, 20, 2);
            this.m_rightBtn = new JButton();
            this.m_rightBtn.wrapSimpleButton(new SimpleButton(_loc_4, _loc_5, _loc_6, _loc_4));
            this.m_rightBtn.setEnabled(false);
            ASwingHelper.setEasyBorder(this.m_rightBtn, 0, 0, 20, 4);
            return;
        }//end

        private void  init ()
        {
            String _loc_2 =null ;
            XML _loc_3 =null ;
            JPanel _loc_4 =null ;
            ASwingHelper.setEasyBorder(this, 3, 2);
            this.setupCallBacks();
            _loc_1 =Global.gameSettings().getMarketPromoXML ();
            this.appendAll(this.m_leftBtn);
            for(int i0 = 0; i0 < PANEL_IDS.size(); i0++)
            {
            		_loc_2 = PANEL_IDS.get(i0);

                _loc_3 = _loc_1.get(_loc_2).get(0);
                if (_loc_3 == null)
                {
                    break;
                }
                _loc_4 = this.createPanel(_loc_3);
                if (_loc_4 != null)
                {
                    this.append(_loc_4);
                }
            }
            this.appendAll(this.m_rightBtn);
            return;
        }//end

        private JPanel  createPanel (XML param1 )
        {
            String trackTag ;
            XML data ;
            panelXML = param1;
            contentXML = this.getPanelContentXML(panelXML);
            if (contentXML == null)
            {
                return null;
            }
            locFile = String(contentXML.@textLocFile) != "" ? (contentXML.@textLocFile) : ("Market");
            imagePath = contentXML.@image;
            titleString = ZLoc.t(locFile,contentXML.@title);
            textString = ZLoc.t(locFile,contentXML.@text);
            buttonString = TextFieldUtil.formatSmallCapsString(ZLoc.t("Market",contentXML.@buttonText));
            callback = contentXML.@callback;
            Object callbackData =new Object ();
            decalPath = in"@decal"contentXML? (contentXML.@decal) : ("");
            trackTag = "@trackTag" in contentXML ? (contentXML.@trackTag) : ("");
            callbackDataXML = contentXML.callbackData.get(0);
            if (callbackDataXML != null)
            {
                int _loc_3 =0;
                _loc_4 = callbackDataXML.*;
                for(int i0 = 0; i0 < callbackDataXML.*.size(); i0++)
                {
                	data = callbackDataXML.*.get(i0);


                    callbackData.put(data.name().toString(),  data.toString());
                }
            }
            jp = this.makePanel(imagePath,titleString,textString,buttonString,callback,callbackData,decalPath);
            if (jp != null && trackTag.length > 0)
            {
                this.trackPanelViews(trackTag);
                jp .addEventListener (MouseEvent .CLICK ,void  (MouseEvent event )
            {
                trackPanelClicks(trackTag);
                return;
            }//end
            );
            }
            return jp;
        }//end

        private XML  getPanelContentXML (XML param1 )
        {
            XML _loc_3 =null ;
            int _loc_4 =0;
            String _loc_5 =null ;
            Array _loc_6 =null ;
            Array _loc_2 =new Array();
            for(int i0 = 0; i0 < param1.variant.size(); i0++)
            {
            	_loc_3 = param1.variant.get(i0);

                _loc_4 = _loc_3.@requiresLevel;
                if (Global.player.level < _loc_4)
                {
                    continue;
                }
                if ("@experimentName" in _loc_3)
                {
                    _loc_5 = _loc_3.@experimentName.get(0).toString();
                    if (_loc_5 == null || _loc_5.length == 0)
                    {
                        continue;
                    }
                    _loc_6 = this.getExperimentVariants(_loc_3);
                    if (_loc_6 == null)
                    {
                        continue;
                    }
                    if (_loc_6.indexOf(Global.experimentManager.getVariant(_loc_5)) >= 0)
                    {
                        _loc_2.push(_loc_3);
                    }
                    continue;
                }
                _loc_2.push(_loc_3);
            }
            _loc_2 = this.pruneFilterSetByValidityDate(_loc_2);
            return this.pickVariantForPanel(_loc_2);
        }//end

        private Array  pruneFilterSetByValidityDate (Array param1 )
        {
            double _loc_3 =0;
            double _loc_4 =0;
            boolean _loc_6 =false ;
            Array _loc_2 =new Array ();
            int _loc_5 =0;
            while (_loc_5 < param1.length())
            {

                _loc_6 = true;
                if ("@startDate" in param1.get(_loc_5))
                {
                    _loc_3 = DateFormatter.parseTimeString(param1.get(_loc_5).@startDate.toString());
                    _loc_6 = _loc_3 <= GlobalEngine.getTimer();
                }
                if (_loc_6 && "@endDate" in param1.get(_loc_5))
                {
                    _loc_4 = DateFormatter.parseTimeString(param1.get(_loc_5).@endDate.toString());
                    _loc_6 = _loc_4 >= GlobalEngine.getTimer();
                }
                if (_loc_6)
                {
                    _loc_2.push(param1.get(_loc_5));
                }
                _loc_5++;
            }
            return _loc_2;
        }//end

        private XML  pickVariantForPanel (Array param1 )
        {
            int _loc_7 =0;
            int _loc_8 =0;
            if (param1.length == 0)
            {
                return null;
            }
            if (param1.length == 1)
            {
                return param1.get(0);
            }
            Array _loc_2 =new Array();
            int _loc_3 =0;
            int _loc_4 =0;
            int _loc_5 =0;
            while (_loc_5 < param1.length())
            {

                _loc_7 = 1;
                if ("@priority" in param1.get(_loc_5))
                {
                    if (param1.get(_loc_5).@priority.get(0) == "forced")
                    {
                        return param1.get(_loc_5);
                    }
                    _loc_7 = param1.get(_loc_5).@priority.get(0);
                }
                _loc_3 = _loc_3 + _loc_7;
                _loc_8 = 0;
                while (_loc_8 < _loc_7)
                {

                    _loc_2.push(_loc_4);
                    _loc_8++;
                }
                _loc_4++;
                _loc_5++;
            }
            _loc_6 = this.randomNumber(0,_loc_3 );
            return param1.get(_loc_2.get(_loc_6));
        }//end

        private Array  getExperimentVariants (XML param1 )
        {
            _loc_2 = param1.@experimentVariants.get(0).toString();
            if (_loc_2 == null || _loc_2.length == 0)
            {
                return null;
            }
            _loc_3 = _loc_2.split(",");
            _loc_4 = _loc_3.length ;
            int _loc_5 =0;
            while (_loc_5 < _loc_4)
            {

                _loc_3.put(_loc_5,  int(_loc_3.get(_loc_5)));
                _loc_5++;
            }
            return _loc_3;
        }//end

        private JPanel  makePanel (String param1 ,String param2 ,String param3 ,String param4 ,String param5 ,Object param6 ,String param7 )
        {
            JPanel jp ;
            JPanel offsetPanel ;
            JPanel middlePanel ;
            AssetPane middlePane ;
            img = param1;
            title = param2;
            textString = param3;
            buttonString = param4;
            callback = param5;
            callbackData = param6;
            decalPath = param7;
            jp = new JPanel(new BorderLayout());
            jp.setPreferredSize(new IntDimension(207, 340));
            topPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            Array titleFilters ;
            titlePane = ASwingHelper.makeMultilineCapsText(title,190,EmbeddedArt.titleFont,TextFormatAlign.CENTER,18,16776960,titleFilters);
            textPane = ASwingHelper.makeMultilineText(textString,190,EmbeddedArt.defaultFontNameBold,TextFormatAlign.CENTER,12,EmbeddedArt.darkBlueTextColor);
            ASwingHelper.setEasyBorder(titlePane, 0, 7);
            ASwingHelper.setEasyBorder(textPane, 0, 7);
            LoadingManager .load (Global .getAssetURL (img ),void  (Event event )
            {
                ASwingHelper.setBackground(jp, event.target.content, new Insets(0, 0, 10));
                ASwingHelper.prepare(jp);
                return;
            }//end
            );
            topPanel.appendAll(titlePane, textPane);
            CustomButton actionButton =new CustomButton(buttonString ,null ,"GreenButtonUI");
            actionButton.setFont(new ASFont(EmbeddedArt.titleFont, 16, false, false, false, EmbeddedArt.advancedFontProps));
            jp.append(topPanel, BorderLayout.NORTH);
            if (decalPath.length > 0)
            {
                offsetPanel = ASwingHelper.makeSoftBoxJPanel();
                middlePanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
                middlePane = new AssetPane();
                LoadingManager .load (Global .getAssetURL (decalPath ),void  (Event event )
            {
                middlePane.setAsset(event.target.content);
                ASwingHelper.prepare(middlePane);
                return;
            }//end
            );
                middlePanel.append(ASwingHelper.centerElement(ASwingHelper.verticalStrut(10)), BorderLayout.CENTER);
                middlePanel.append(ASwingHelper.centerElement(middlePane), BorderLayout.CENTER);
                offsetPanel.appendAll(ASwingHelper.leftAlignElement(ASwingHelper.horizontalStrut(100)), middlePanel);
                jp.append(ASwingHelper.centerElement(offsetPanel), BorderLayout.CENTER);
            }
            jp.append(ASwingHelper.centerElement(actionButton), BorderLayout.SOUTH);
            func = this.m_callbacks.get(callback);
            jp .addEventListener (MouseEvent .CLICK ,void  (MouseEvent event )
            {
                func(callbackData);
                return;
            }//end
            );
            return jp;
        }//end

        private void  showCategory (Object param1 )
        {
            StatsManager.count("market_views", "click_from_specials", param1.get("category"), param1.get("subcategory"));
            dispatchEvent(new GenericObjectEvent(GenericObjectEvent.SPECIALS_CLICK, param1, true));
            return;
        }//end

        private void  showMinigame (Object param1 )
        {
            UI.closeCatalog();
            Global.ui.startPickThings(param1.get("minigameId"));
            return;
        }//end

        private void  getCash (Object param1 )
        {
            StatsManager.count("get_coins", "click_from_specials");
            this.trackSales(PaymentsSaleManager.TYPE_FLASH_SALE);
            FrameManager.showTray("money.php?ref=coinsDialog");
            return;
        }//end

        private void  trackPanelViews (String param1 )
        {
            StatsManager.count("market_views", "specials", "cards", param1);
            return;
        }//end

        private void  trackPanelClicks (String param1 )
        {
            StatsManager.count("market_clicks", "specials", "cards", param1);
            return;
        }//end

        private void  trackSales (String param1 )
        {
            PaymentsSale _loc_2 =null ;
            if (Global.paymentsSaleManager.isSaleActive(param1, false))
            {
                _loc_2 = Global.paymentsSaleManager.getSaleByType(param1);
                StatsManager.count(StatsCounterType.GAME_ACTIONS, _loc_2.statsName, "hud_icon", "clicked");
            }
            return;
        }//end

        private int  randomNumber (int param1 =0,int param2 =1)
        {
            return Math.floor(Math.random() * (param2 - param1)) + param1;
        }//end

    }



