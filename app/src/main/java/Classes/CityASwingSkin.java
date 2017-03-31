package Classes;

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

import Display.FlashMFSList.*;
import Display.aswingui.buttonui.*;
import Display.aswingui.labelui.*;
//import flash.filters.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.plaf.*;
import org.aswing.plaf.basic.*;

    public class CityASwingSkin extends BasicLookAndFeel
    {

        public  CityASwingSkin ()
        {
            return;
        }//end  

         protected void  initSystemFontDefaults (UIDefaults param1 )
        {
            super.initSystemFontDefaults(param1);
            _loc_2 = EmbeddedArt.defaultFontName;
            Array _loc_3 =.get( "systemFont",new ASFontUIResource(EmbeddedArt.defaultFontName ,16,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.defaultFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL )),"boldFont",new ASFontUIResource(EmbeddedArt.defaultFontNameBold ,18,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.defaultBoldFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL )),"bold16Font",new ASFontUIResource(EmbeddedArt.defaultFontNameBold ,16,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.defaultBoldFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL )),"subMarketFont",new ASFontUIResource(EmbeddedArt.defaultFontNameBold ,14,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.defaultBoldFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL )),"boldsmFont",new ASFontUIResource(EmbeddedArt.defaultFontNameBold ,14,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.defaultBoldFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL )),"boldminiFont",new ASFontUIResource(EmbeddedArt.defaultFontNameBold ,12,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.defaultBoldFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL )),"regminiFont",new ASFontUIResource(EmbeddedArt.defaultFontName ,12,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.defaultFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL )),"marketButtonFont",new ASFontUIResource(EmbeddedArt.titleFont ,14,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.titleFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL )),"rhinoButtonFont",new ASFontUIResource(EmbeddedArt.titleFont ,20,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.titleFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL )),"rhinoSmallButtonFont",new ASFontUIResource(EmbeddedArt.titleFont ,14,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.titleFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL )),"rhinoBigButtonFont",new ASFontUIResource(EmbeddedArt.titleFont ,20,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.titleFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL )),"rhino8SmallButtonFont",new ASFontUIResource(EmbeddedArt.titleFont ,8,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.titleFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL )),"rhino10SmallButtonFont",new ASFontUIResource(EmbeddedArt.titleFont ,10,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.titleFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL )),"rhino11SmallButtonFont",new ASFontUIResource(EmbeddedArt.titleFont ,11,false ,false ,false ,new ASFontAdvProperties(EmbeddedArt.titleFontEmbed ,AntiAliasType.ADVANCED ,GridFitType.PIXEL ))) ;
            param1.putDefaults(_loc_3);
            return;
        }//end  

         protected void  initClassDefaults (UIDefaults param1 )
        {
            Array _loc_2 =.get( "OrangeMediumButtonUI",OrangeMediumButtonUI ,"BlankButtonUI",BlankButtonUI ,"OrangeButtonUI",OrangeButtonUI ,"RedSmallButtonUI",RedSmallButtonUI ,"GreenSmallButtonUI",GreenSmallButtonUI ,"OrangeSmallButtonUI",OrangeSmallButtonUI ,"CustomButtonUI",CustomButtonUI ,"RedButtonUI",RedButtonUI ,"GreenButtonUI",GreenButtonUI ,"BigGreenButtonUI",BigGreenButtonUI ,"GreyButtonUI",GreyButtonUI ,"BuyGateKeyButtonUI",BuyGateKeyButtonUI ,"AskForKeyButtonUI",AskForKeyButtonUI ,"SendGiftButtonUI",SendGiftButtonUI ,"CashButtonUI",CashButtonUI ,"CoinsButtonUI",CoinsButtonUI ,"PlusButtonUI",PlusButtonUI ,"MinusButtonUI",MinusButtonUI ,"BigCashButtonUI",BigCashButtonUI ,"AddCoinsButtonUI",AddCoinsCashButtonUI ,"AddCoinsSaleButtonUI",AddCoinsCashSaleButtonUI ,"FreeGiftSaleButtonUI",FreeGiftSaleButtonUI ,"CashSmallButtonUI",CashSmallButtonUI ,"CoinsSmallButtonUI",CoinsSmallButtonUI ,"PinkButtonUI",PinkButtonUI ,"PurpleButtonUI",PurpleButtonUI ,"GreenWhiteButtonUI",GreenWhiteButtonUI ,"MarketSortButtonUI",MarketSortButtonUI ,"CashTimeEnergyButtonUI",CashTimeEnergyButtonUI ,"ScrollBarUI",CityScrollBarUI ,"LabelUI",TextFieldLabelUI ,"BlueSmallButtonUI",BlueSmallButtonUI) ;
            param1.putDefaults(_loc_2);
            return;
        }//end  

         protected void  initComponentDefaults (UIDefaults param1 )
        {
            super.initComponentDefaults(param1);
            Array _loc_2 =.get( "CustomButton.foreground",new ASColorUIResource(16777215),"CustomButton.colorAdjust",new UIStyleTune(0.18,-0.02,0.34,0.22,5),"CustomButton.focusable",true ,"CustomButton.textFilters",new ArrayUIResource(.get(new DropShadowFilter(1,90,0,0.2,1,1,1,1),new GlowFilter(0,0.2,4,4,10)) ),"CustomButton.background",new ASColorUIResource(1052077),"CustomButton.opaque",false ,"CustomButton.margin",new InsetsUIResource(3,10,3,10),"CustomButton.font",param1.getFont("rhinoButtonFont"),"CustomButton.textShiftOffset",0) ;
            param1.putDefaults(_loc_2);
            _loc_2 = .get("RedButton.foreground", new ASColorUIResource(16777214), "RedButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "RedButton.focusable", true, "RedButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "RedButton.background", new ASColorUIResource(1052077), "RedButton.opaque", false, "RedButton.margin", new InsetsUIResource(3, 10, 3, 10), "RedButton.font", param1.getFont("rhinoButtonFont"), "RedButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("GreyButton.foreground", new ASColorUIResource(16777214), "GreyButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "GreyButton.focusable", true, "GreyButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "GreyButton.background", new ASColorUIResource(16777215), "GreyButton.opaque", false, "GreyButton.margin", new InsetsUIResource(3, 10, 3, 10), "GreyButton.font", param1.getFont("rhinoButtonFont"), "GreyButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("GreenButton.foreground", new ASColorUIResource(16777214), "GreenButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "GreenButton.focusable", true, "GreenButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "GreenButton.background", new ASColorUIResource(16777215), "GreenButton.opaque", false, "GreenButton.margin", new InsetsUIResource(3, 10, 3, 10), "GreenButton.font", param1.getFont("rhinoButtonFont"), "GreenButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("AddCoinsCashSaleButton.foreground", new ASColorUIResource(16777214), "AddCoinsCashSaleButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "AddCoinsCashSaleButton.focusable", true, "AddCoinsCashSaleButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "AddCoinsCashSaleButton.background", new ASColorUIResource(0), "AddCoinsCashSaleButton.opaque", false, "AddCoinsCashSaleButton.margin", new InsetsUIResource(14, 45, 10, 5), "AddCoinsCashSaleButton.font", param1.getFont("rhinoSmallButtonFont"), "AddCoinsCashSaleButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("FreeGiftSaleButton.foreground", new ASColorUIResource(16777214), "FreeGiftSaleButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "FreeGiftSaleButton.focusable", true, "FreeGiftSaleButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "FreeGiftSaleButton.background", new ASColorUIResource(0), "FreeGiftSaleButton.opaque", false, "FreeGiftSaleButton.margin", new InsetsUIResource(14, 45, 10, 5), "FreeGiftSaleButton.font", param1.getFont("rhinoSmallButtonFont"), "FreeGiftSaleButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("GreenWhiteButton.foreground", new ASColorUIResource(16777214), "GreenWhiteButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "GreenWhiteButton.focusable", true, "GreenWhiteButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "GreenWhiteButton.background", new ASColorUIResource(16777215), "GreenWhiteButton.opaque", false, "GreenWhiteButton.margin", new InsetsUIResource(5, 10, 5, 10), "GreenWhiteButton.font", param1.getFont("rhinoButtonFont"), "GreenWhiteButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("OrangeButton.foreground", new ASColorUIResource(16777214), "OrangeButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "OrangeButton.focusable", true, "OrangeButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "OrangeButton.background", new ASColorUIResource(16777215), "OrangeButton.opaque", false, "OrangeButton.margin", new InsetsUIResource(3, 10, 3, 10), "OrangeButton.font", param1.getFont("rhinoButtonFont"), "OrangeButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("OrangeMediumButton.foreground", new ASColorUIResource(16777214), "OrangeMediumButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "OrangeMediumButton.focusable", true, "OrangeMediumButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "OrangeMediumButton.background", new ASColorUIResource(16777215), "OrangeMediumButton.opaque", false, "OrangeMediumButton.margin", new InsetsUIResource(1, 5, 1, 5), "OrangeMediumButton.font", param1.getFont("rhinoSmallButtonFont"), "OrangeMediumButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("BuyGateKeyButton.foreground", new ASColorUIResource(16777214), "BuyGateKeyButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "BuyGateKeyButton.focusable", true, "BuyGateKeyButton.textFilters", new ArrayUIResource(.get(new GlowFilter(0, 0.4, 3, 3, 10))), "BuyGateKeyButton.background", new ASColorUIResource(16777215), "BuyGateKeyButton.opaque", false, "BuyGateKeyButton.margin", new InsetsUIResource(-3, 2, -3, 2), "BuyGateKeyButton.font", param1.getFont("rhinoSmallButtonFont"), "BuyGateKeyButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("AskForKeyButton.foreground", new ASColorUIResource(16777214), "AskForKeyButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "AskForKeyButton.focusable", true, "AskForKeyButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "AskForKeyButton.background", new ASColorUIResource(16777215), "AskForKeyButton.opaque", false, "AskForKeyButton.margin", new InsetsUIResource(2, 2, 2, 2), "AskForKeyButton.font", param1.getFont("rhinoSmallButtonFont"), "AskForKeyButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("BigCashButton.foreground", new ASColorUIResource(16777214), "BigCashButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "BigCashButton.focusable", true, "BigCashButton.textFilters", new ArrayUIResource(.get(new GlowFilter(0, 0.4, 3, 3, 10))), "BigCashButton.background", new ASColorUIResource(16777215), "BigCashButton.opaque", false, "BigCashButton.margin", new InsetsUIResource(1, 5, 1, 5), "BigCashButton.font", param1.getFont("rhinoButtonFont"), "BigCashButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("CashButton.foreground", new ASColorUIResource(16777214), "CashButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "CashButton.focusable", true, "CashButton.textFilters", new ArrayUIResource(.get(new GlowFilter(0, 0.4, 3, 3, 10))), "CashButton.background", new ASColorUIResource(16777215), "CashButton.opaque", false, "CashButton.margin", new InsetsUIResource(-3, 5, -3, 5), "CashButton.font", param1.getFont("marketButtonFont"), "CashButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("CashButtonSmall.foreground", new ASColorUIResource(16777214), "CashButtonSmall.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "CashButtonSmall.focusable", true, "CashButtonSmall.textFilters", new ArrayUIResource(.get(new GlowFilter(0, 0.4, 3, 3, 10))), "CashButtonSmall.background", new ASColorUIResource(16777215), "CashButtonSmall.opaque", false, "CashButtonSmall.margin", new InsetsUIResource(-3, 2, -3, 2), "CashButtonSmall.font", param1.getFont("rhino10SmallButtonFont"), "CashButtonSmall.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("CoinsButton.foreground", new ASColorUIResource(16777214), "CoinsButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "CoinsButton.focusable", true, "CoinsButton.textFilters", new ArrayUIResource(.get(new GlowFilter(0, 0.4, 3, 3, 10))), "CoinsButton.background", new ASColorUIResource(16777215), "CoinsButton.opaque", false, "CoinsButton.margin", new InsetsUIResource(0, 5, 1, 5), "CoinsButton.font", param1.getFont("marketButtonFont"), "CoinsButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("CoinsButtonSmall.foreground", new ASColorUIResource(16777214), "CoinsButtonSmall.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "CoinsButtonSmall.focusable", true, "CoinsButtonSmall.textFilters", new ArrayUIResource(.get(new GlowFilter(0, 0.4, 3, 3, 10))), "CoinsButtonSmall.background", new ASColorUIResource(16777215), "CoinsButtonSmall.opaque", false, "CoinsButtonSmall.margin", new InsetsUIResource(3, 2, 2, 3), "CoinsButtonSmall.font", param1.getFont("rhino11SmallButtonFont"), "CoinsButtonSmall.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("RedSmallButton.foreground", new ASColorUIResource(16777214), "RedSmallButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "RedSmallButton.focusable", true, "RedSmallButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "RedSmallButton.background", new ASColorUIResource(16777215), "RedSmallButton.opaque", false, "RedSmallButton.margin", new InsetsUIResource(0, 5, 0, 5), "RedSmallButton.font", param1.getFont("rhinoSmallButtonFont"), "RedSmallButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("GreenSmallButton.foreground", new ASColorUIResource(16777214), "GreenSmallButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "GreenSmallButton.focusable", true, "GreenSmallButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "GreenSmallButton.background", new ASColorUIResource(16777215), "GreenSmallButton.opaque", false, "GreenSmallButton.margin", new InsetsUIResource(0, 0, 0, 0), "GreenSmallButton.font", param1.getFont("rhinoSmallButtonFont"), "GreenSmallButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("OrangeSmallButton.foreground", new ASColorUIResource(16777214), "OrangeSmallButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "OrangeSmallButton.focusable", true, "OrangeSmallButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "OrangeSmallButton.background", new ASColorUIResource(16777215), "OrangeSmallButton.opaque", false, "OrangeSmallButton.margin", new InsetsUIResource(0, 5, 0, 5), "OrangeSmallButton.font", param1.getFont("rhinoSmallButtonFont"), "OrangeSmallButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("BlankButton.foreground", new ASColorUIResource(0), "BlankButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "BlankButton.focusable", true, "BlankButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "BlankButton.background", new ASColorUIResource(0), "BlankButton.opaque", false, "BlankButton.defaultImage", null, "BlankButton.pressedImage", null, "BlankButton.disabledImage", null, "BlankButton.rolloverImage", null, "BlankButton.DefaultButton.defaultImage", null, "BlankButton.margin", new InsetsUIResource(0, 5, 0, 5), "BlankButton.font", param1.getFont("boldminiFont"), "BlankButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("BigGreenButton.foreground", new ASColorUIResource(16777214), "BigGreenButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "BigGreenButton.focusable", true, "BigGreenButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "BigGreenButton.background", new ASColorUIResource(16777215), "BigGreenButton.opaque", false, "BigGreenButton.margin", new InsetsUIResource(8, 20, 8, 20), "BigGreenButton.font", param1.getFont("rhinoBigButtonFont"), "BigGreenButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("AddCoinsCashButton.foreground", new ASColorUIResource(16777214), "AddCoinsCashButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "AddCoinsCashButton.focusable", true, "AddCoinsCashButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "AddCoinsCashButton.background", new ASColorUIResource(0), "AddCoinsCashButton.opaque", false, "AddCoinsCashButton.margin", new InsetsUIResource(14, 45, 10, 5), "AddCoinsCashButton.font", param1.getFont("rhinoSmallButtonFont"), "AddCoinsCashButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("PinkButton.foreground", new ASColorUIResource(16777214), "PinkButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "PinkButton.focusable", true, "PinkButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "PinkButton.background", new ASColorUIResource(0), "PinkButton.opaque", false, "PinkButton.margin", new InsetsUIResource(5, 10, 5, 10), "PinkButton.font", param1.getFont("rhinoBigButtonFont"), "PinkButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("PurpleButton.foreground", new ASColorUIResource(16777214), "PurpleButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "PurpleButton.focusable", true, "PurpleButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "PurpleButton.background", new ASColorUIResource(0), "PurpleButton.opaque", false, "PurpleButton.margin", new InsetsUIResource(0, 5, 0, 5), "PurpleButton.font", param1.getFont("rhinoSmallButtonFont"), "PurpleButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("MarketSortButton.foreground", new ASColorUIResource(16777215), "MarketSortButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "MarketSortButton.focusable", true, "MarketSortButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 1, 1, 1, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "MarketSortButton.background", new ASColorUIResource(1052077), "MarketSortButton.opaque", false, "MarketSortButton.margin", new InsetsUIResource(3, 10, 3, 10), "MarketSortButton.font", param1.getFont("boldminiFont"), "MarketSortButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("CashTimeEnergyButton.foreground", new ASColorUIResource(16777214), "CashTimeEnergyButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "CashTimeEnergyButton.focusable", true, "CashTimeEnergyButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "CashTimeEnergyButton.background", new ASColorUIResource(0), "CashTimeEnergyButton.opaque", false, "CashTimeEnergyButton.margin", new InsetsUIResource(14, 48, 12, 45), "CashTimeEnergyButton.font", param1.getFont("rhinoSmallButtonFont"), "CashTimeEnergyButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("ScrollBar.foreground", param1.get("controlText"), "ScrollBar.mideground", new ASColorUIResource(15198183, 0.8), "ScrollBar.opaque", true, "ScrollBar.focusable", true, "ScrollBar.barWidth", 25, "ScrollBar.minimumThumbLength", 24, "ScrollBar.font", param1.getFont("controlFont"), "ScrollBar.bg", CityScrollBarBackground, "ScrollBar.thumbDecorator", CityScrollBarThumb);
            param1.putDefaults(_loc_2);
            _loc_2 = .get("BlueSmallButton.foreground", new ASColorUIResource(16777214), "BlueSmallButton.colorAdjust", new UIStyleTune(0.18, -0.02, 0.34, 0.22, 5), "BlueSmallButton.focusable", true, "BlueSmallButton.textFilters", new ArrayUIResource(.get(new DropShadowFilter(1, 90, 0, 0.2, 3, 3, 3, 1), new GlowFilter(0, 0.2, 4, 4, 10))), "BlueSmallButton.background", new ASColorUIResource(16777215), "BlueSmallButton.opaque", false, "BlueSmallButton.margin", new InsetsUIResource(0, 0, 0, 0), "BlueSmallButton.font", param1.getFont("rhinoSmallButtonFont"), "BlueSmallButton.textShiftOffset", 0);
            param1.putDefaults(_loc_2);
            return;
        }//end  

    }




