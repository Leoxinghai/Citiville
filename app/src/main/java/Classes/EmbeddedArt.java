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

//import flash.filters.*;
//import flash.text.*;
import org.aswing.*;

    public class EmbeddedArt
    {
        public static  int HIGHLIGHT_COLOR =16755200;
        public static  int READY_HIGHLIGHT_COLOR =65496;
        public static  int UPGRADE_HIGHLIGHT_COLOR =589568;
        public static  int PEEP_HIGHLIGHT_COLOR =16771584;
        public static  int BORDER_MAIN_COLOR =872882;
        public static  int TEXT_MAIN_COLOR =872882;
        public static  int TOOL_TIP_STROKE =3355443;
        public static  int TEXT_BACKGROUND_COLOR =16777215;
        public static  int VIEWPORT_CLEAR_COLOR =4.28629e +009;
        public static  int VISITOR_INTERACTED_HIGHLIGHT_COLOR =711625;
        public static  int TEXT_SALE_COLOR =11278335;
        public static  int HOVER_GLOW_COLOR =16711935;
        public static  int KEYWORD_GLOW_COLOR =16057340;
        public static  int HIGHLIGHT_ORANGE =15034112;
        public static  int SUB_HIGHLIGHT_BLUE =3181242;
        public static  String DEFAULT_FONT_NAME ="bryantReg";
        private static String m_defaultFontName ="bryantReg";
        private static boolean m_defaultFontEmbed =true ;
        public static  String DEFAULT_FONT_NAME_BOLD ="bryantBold";
        private static String m_defaultFontNameBold ="bryantBold";
        private static boolean m_defaultBoldFontEmbed =true ;
        public static  String TITLE_FONT ="rhino";
        private static String m_titleFont ="rhino";
        private static boolean m_titleFontEmbed =true ;
        public static  String DEFAULT_SERIF_FONT ="timesNewBold";
        private static String m_defaultSerifFont ="timesNewBold";
        private static boolean m_defaultSerifFontEmbed =true ;
        private static Array m_embedFonts =.get( {name isEmbed "bryantReg",},{name "bryantBold",},{name "rhino",},{name "timesNewBold",}) ;
        public static int titleColor =16508714;
        public static Array titleFilters =.get(new GlowFilter(33724,1,4,4,10,BitmapFilterQuality.LOW ),new DropShadowFilter(1,45,2446451,1,3,3)) ;
        public static Array newtitleFilters =.get(new GlowFilter(16090657,1,3,3,8,BitmapFilterQuality.LOW ),new DropShadowFilter(1,90,2446451,1,5,5,10)) ;
        public static Array questTitleFilters =.get(new GlowFilter(16023072,1,3,3,8,BitmapFilterQuality.LOW ),new DropShadowFilter(1,90,3840965,1,5,5,10)) ;
        public static Array newtitleSmallFilters =.get(new GlowFilter(16023072,1,3,3,8,BitmapFilterQuality.LOW ),new DropShadowFilter(1,90,3840965,1,2,2,2,BitmapFilterQuality.LOW )) ;
        public static Array socialLevelUpTitleFilters =.get(new GlowFilter(11010060,1,4,4,10,BitmapFilterQuality.LOW )) ;
        public static Array valtitleFilters =.get(new GlowFilter(16767986,1,3,3,8,BitmapFilterQuality.LOW ),new DropShadowFilter(1,90,2446451,1,5,5,10)) ;
        public static Array commonBlackFilter =.get(new GlowFilter(0,1,1.2,1.2,20,BitmapFilterQuality.HIGH )) ;
        public static Array subtleBlackFilter =.get(new GlowFilter(0,0.5,1.2,1.2,20,BitmapFilterQuality.HIGH )) ;
        public static Array darkerOverlayFilter =.get(new GlowFilter(0,0.4,4,4,100,BitmapFilterQuality.LOW )) ;
        public static Array commonGreenFilter =.get(new GlowFilter(5811777,1,1.2,1.2,20,BitmapFilterQuality.HIGH )) ;
        public static Array commonOrangeFilter =.get(new GlowFilter(16421200,1,1.2,1.2,20,BitmapFilterQuality.HIGH )) ;
        public static Array commonGrayFilter =.get(new GlowFilter(10197915,1,1.2,1.2,20,BitmapFilterQuality.HIGH )) ;
        public static Array desaturateFilter =.get(new ColorMatrixFilter(.get(0 .33,0.33,0.33,0,0,0.33,0.33,0.33,0,0,0.33,0.33,0.33,0,0,0.33,0.33,0.33,1,0) )) ;
        public static Array toolTipNormalTitleFilters =.get(new GlowFilter(5738697,1,2,2,20,BitmapFilterQuality.LOW ),new DropShadowFilter(1,90,5738697,1,3,3,10,BitmapFilterQuality.LOW )) ;
        public static Array toolTipFranchiseTitleFilters =.get(new GlowFilter(14058534,1,2,2,20,BitmapFilterQuality.LOW ),new DropShadowFilter(1,90,14058534,1,3,3,10,BitmapFilterQuality.LOW )) ;
        public static ASFontAdvProperties advancedFontProps =new ASFontAdvProperties(true ,AntiAliasType.ADVANCED ,GridFitType.SUBPIXEL );
        public static int orangeTextColor =12675344;
        public static int blueTextColor =2335175;
        public static int redTextColor =12517378;
        public static int dialogHintColor =16023072;
        public static int fireBrickRedColor =15608876;
        public static int brownTextColor =7356965;
        public static int fadedBrownTextColor =10059115;
        public static int darkBrownTextColor =7356965;
        public static int greenTextColor =46094;
        public static int limeGreenTextColor =6474564;
        public static int darkBlueTextColor =3638975;
        public static int darkerBlueTextColor =489375;
        public static int yellowTextColor =16051981;
        public static int whiteTextColor =16777215;
        public static int blackTextColor =0;
        public static int darkGreyTextColor =1118481;
        public static int pinkTextColor =16344739;
        public static int darkPinkTextColor =11218531;
        public static int lightGrayTextColor =7829624;
        public static int lightBlueTextColor =15398395;
        public static int lightishBlueTextColor =12969719;
        public static int lightOrangeTextColor =16418589;
        public static int darkBlueToolTipColor =5738697;
        public static int rollCallBlue =12116719;
        public static int rollCallStatusBlue =5749455;
        public static int salePopupBlue =2533856;
        public static int salePopupDarkBlue =29116;
        public static int salePopupLightBlue =5552087;
        public static int salePopupTealBlue =34732;
        public static Class font_bryant =EmbeddedArt_font_bryant ;
        public static Class font_bryant_bold =EmbeddedArt_font_bryant_bold ;
        public static Class font_fat_rhino =EmbeddedArt_font_fat_rhino ;
        public static Class font_times_bold =EmbeddedArt_font_times_bold ;
        public static Class Options_holder =EmbeddedArt_Options_holder ;
        public static Class FV_Main_Options_SFX_Down =EmbeddedArt_FV_Main_Options_SFX_Down ;
        public static Class FV_Main_Options_SFX_Over =EmbeddedArt_FV_Main_Options_SFX_Over ;
        public static Class FV_Main_Options_SFX_Normal =EmbeddedArt_FV_Main_Options_SFX_Normal ;
        public static Class FV_Main_Options_Visual_Down =EmbeddedArt_FV_Main_Options_Visual_Down ;
        public static Class FV_Main_Options_Visual_Over =EmbeddedArt_FV_Main_Options_Visual_Over ;
        public static Class FV_Main_Options_Visual_Normal =EmbeddedArt_FV_Main_Options_Visual_Normal ;
        public static Class FV_Main_Options_Music_Down =EmbeddedArt_FV_Main_Options_Music_Down ;
        public static Class FV_Main_Options_Music_Over =EmbeddedArt_FV_Main_Options_Music_Over ;
        public static Class FV_Main_Options_Music_Normal =EmbeddedArt_FV_Main_Options_Music_Normal ;
        public static Class FV_Main_Zoom_FullScreen_Over =EmbeddedArt_FV_Main_Zoom_FullScreen_Over ;
        public static Class FV_Main_Zoom_FullScreen_Norm =EmbeddedArt_FV_Main_Zoom_FullScreen_Norm ;
        public static Class FV_Main_ZoomIn_Down =EmbeddedArt_FV_Main_ZoomIn_Down ;
        public static Class FV_Main_ZoomIn_Over =EmbeddedArt_FV_Main_ZoomIn_Over ;
        public static Class FV_Main_ZoomIn_Norm =EmbeddedArt_FV_Main_ZoomIn_Norm ;
        public static Class FV_Main_ZoomOut_Down =EmbeddedArt_FV_Main_ZoomOut_Down ;
        public static Class FV_Main_ZoomOut_Over =EmbeddedArt_FV_Main_ZoomOut_Over ;
        public static Class FV_Main_ZoomOut_Norm =EmbeddedArt_FV_Main_ZoomOut_Norm ;
        public static Class Options_settings_closed_down =EmbeddedArt_Options_settings_closed_down ;
        public static Class Options_settings_closed_over =EmbeddedArt_Options_settings_closed_over ;
        public static Class Options_settings_closed_up =EmbeddedArt_Options_settings_closed_up ;
        public static Class Options_settings_open_down =EmbeddedArt_Options_settings_open_down ;
        public static Class Options_settings_open_over =EmbeddedArt_Options_settings_open_over ;
        public static Class Options_settings_open_up =EmbeddedArt_Options_settings_open_up ;
        public static Class scrollbarThumb =EmbeddedArt_scrollbarThumb ;
        public static Class scrollbarArrowUp =EmbeddedArt_scrollbarArrowUp ;
        public static Class scrollbarBg =EmbeddedArt_scrollbarBg ;
        public static Class cash_time_energy_off_up =EmbeddedArt_cash_time_energy_off_up ;
        public static Class cash_time_energy_off_over =EmbeddedArt_cash_time_energy_off_over ;
        public static Class addcoins_up =EmbeddedArt_addcoins_up ;
        public static Class addcoins_over =EmbeddedArt_addcoins_over ;
        public static Class addcoinssale_up =EmbeddedArt_addcoinssale_up ;
        public static Class addcoinssale_over =EmbeddedArt_addcoinssale_over ;
        public static Class freeGiftOffer_over =EmbeddedArt_freeGiftOffer_over ;
        public static Class freeGiftOffer_up =EmbeddedArt_freeGiftOffer_up ;
        public static Class getMoreEnergyPanel =EmbeddedArt_getMoreEnergyPanel ;
        public static Class hud_addcoins =EmbeddedArt_hud_addcoins ;
        public static Class home_Button =EmbeddedArt_home_Button ;
        public static Class hud_energyUnlimited =EmbeddedArt_hud_energyUnlimited ;
        public static Class Tool_build =EmbeddedArt_Tool_build ;
        public static Class Tool_cursor =EmbeddedArt_Tool_cursor ;
        public static Class Tool_gift =EmbeddedArt_Tool_gift ;
        public static Class Tool_move =EmbeddedArt_Tool_move ;
        public static Class Tool_move_on =EmbeddedArt_Tool_move_on ;
        public static Class Tool_remove =EmbeddedArt_Tool_remove ;
        public static Class Tool_remove_on =EmbeddedArt_Tool_remove_on ;
        public static Class Tool_rotate =EmbeddedArt_Tool_rotate ;
        public static Class Tool_rotate_on =EmbeddedArt_Tool_rotate_on ;
        public static Class actn_bar =EmbeddedArt_actn_bar ;
        public static Class actn_container =EmbeddedArt_actn_container ;
        public static Class actn_mask =EmbeddedArt_actn_mask ;
        public static Class context_rect =EmbeddedArt_context_rect ;
        public static Class tooltip_bg_blue =EmbeddedArt_tooltip_bg_blue ;
        public static Class tooltip_bg_orange =EmbeddedArt_tooltip_bg_orange ;
        public static Class tooltip_tail_blue =EmbeddedArt_tooltip_tail_blue ;
        public static Class tooltip_tail_orange =EmbeddedArt_tooltip_tail_orange ;
        public static Class flashSale_button_over =EmbeddedArt_flashSale_button_over ;
        public static Class flashSale_button_up =EmbeddedArt_flashSale_button_up ;
        public static Class eoq_sale_timer_small =EmbeddedArt_eoq_sale_timer_small ;
        public static Class streakBar =EmbeddedArt_streakBar ;
        public static Class streakFrame =EmbeddedArt_streakFrame ;
        public static Class hud_actMenu_auto =EmbeddedArt_hud_actMenu_auto ;
        public static Class hud_actMenu_rotate =EmbeddedArt_hud_actMenu_rotate ;
        public static Class hud_actMenu_move =EmbeddedArt_hud_actMenu_move ;
        public static Class hud_actMenu_remove =EmbeddedArt_hud_actMenu_remove ;
        public static Class hud_actMenu_store =EmbeddedArt_hud_actMenu_store ;
        public static Class hud_actMenu_hearts =EmbeddedArt_hud_actMenu_hearts ;
        public static Class hud_actMenu_remodel =EmbeddedArt_hud_actMenu_remodel ;
        public static Class hud_act_cursor =EmbeddedArt_hud_act_cursor ;
        public static Class hud_act_move =EmbeddedArt_hud_act_move ;
        public static Class hud_act_remove =EmbeddedArt_hud_act_remove ;
        public static Class hud_act_store =EmbeddedArt_hud_act_store ;
        public static Class hud_act_rotate =EmbeddedArt_hud_act_rotate ;
        public static Class hud_act_mall =EmbeddedArt_hud_act_mall ;
        public static Class hud_act_hood =EmbeddedArt_hud_act_hood ;
        public static Class hud_act_collection =EmbeddedArt_hud_act_collection ;
        public static Class hud_act_inventory =EmbeddedArt_hud_act_inventory ;
        public static Class hud_act_clearWilderness =EmbeddedArt_hud_act_clearWilderness ;
        public static Class hud_actMenu_franchise =EmbeddedArt_hud_actMenu_franchise ;
        public static Class hud_act_construction =EmbeddedArt_hud_act_construction ;
        public static Class hud_act_rent =EmbeddedArt_hud_act_rent ;
        public static Class hud_act_openBusiness =EmbeddedArt_hud_act_openBusiness ;
        public static Class hud_act_delivery =EmbeddedArt_hud_act_delivery ;
        public static Class hud_act_clean =EmbeddedArt_hud_act_clean ;
        public static Class hud_act_plant =EmbeddedArt_hud_act_plant ;
        public static Class hud_act_harvest =EmbeddedArt_hud_act_harvest ;
        public static Class hud_act_hearts =EmbeddedArt_hud_act_hearts ;
        public static Class hud_act_zap =EmbeddedArt_hud_act_zap ;
        public static Class hud_act_remodel =EmbeddedArt_hud_act_remodel ;
        public static Class hud_sale_cursor =EmbeddedArt_hud_sale_cursor ;
        public static Class hud_cursor =EmbeddedArt_hud_cursor ;
        public static Class hud_act_bg =EmbeddedArt_hud_act_bg ;
        public static Class hud_act_doublewide_bg =EmbeddedArt_hud_act_doublewide_bg ;
        public static Class hud_act_corn =EmbeddedArt_hud_act_corn ;
        public static Class hud_act_straw =EmbeddedArt_hud_act_straw ;
        public static Class hud_act_carrot =EmbeddedArt_hud_act_carrot ;
        public static Class hud_act_squash =EmbeddedArt_hud_act_squash ;
        public static Class hud_act_eggplant =EmbeddedArt_hud_act_eggplant ;
        public static Class hud_act_pea =EmbeddedArt_hud_act_pea ;
        public static Class hud_act_pumpkin =EmbeddedArt_hud_act_pumpkin ;
        public static Class hud_act_watermelon =EmbeddedArt_hud_act_watermelon ;
        public static Class hud_act_wheat =EmbeddedArt_hud_act_wheat ;
        public static Class hud_act_cran =EmbeddedArt_hud_act_cran ;
        public static Class hud_act_pinkrose =EmbeddedArt_hud_act_pinkrose ;
        public static Class hud_act_redrose =EmbeddedArt_hud_act_redrose ;
        public static Class hud_act_yellowrose =EmbeddedArt_hud_act_yellowrose ;
        public static Class hud_act_clover =EmbeddedArt_hud_act_clover ;
        public static Class hud_act_cabbage =EmbeddedArt_hud_act_cabbage ;
        public static Class hud_act_sweetpotato =EmbeddedArt_hud_act_sweetpotato ;
        public static Class hud_act_lettuce =EmbeddedArt_hud_act_lettuce ;
        public static Class hud_act_tomato =EmbeddedArt_hud_act_tomato ;
        public static Class hud_act_alfalfa =EmbeddedArt_hud_act_alfalfa ;
        public static Class hud_act_blueberries =EmbeddedArt_hud_act_blueberries ;
        public static Class hud_act_artichoke =EmbeddedArt_hud_act_artichoke ;
        public static Class hud_act_pineapple =EmbeddedArt_hud_act_pineapple ;
        public static Class hud_act_sugarcane =EmbeddedArt_hud_act_sugarcane ;
        public static Class hud_act_applepie =EmbeddedArt_hud_act_applepie ;
        public static Class hud_act_spinach =EmbeddedArt_hud_act_spinach ;
        public static Class hud_act_grapes =EmbeddedArt_hud_act_grapes ;
        public static Class hud_act_cassava =EmbeddedArt_hud_act_cassava ;
        public static Class hud_act_rhubarb =EmbeddedArt_hud_act_rhubarb ;
        public static Class hud_act_asparagus =EmbeddedArt_hud_act_asparagus ;
        public static Class hud_act_taro =EmbeddedArt_hud_act_taro ;
        public static Class hud_act_barley =EmbeddedArt_hud_act_barley ;
        public static Class hud_act_grapesgreen =EmbeddedArt_hud_act_grapesgreen ;
        public static Class hud_act_cottoncandy =EmbeddedArt_hud_act_cottoncandy ;
        public static Class hud_act_peanuts =EmbeddedArt_hud_act_peanuts ;
        public static Class hud_act_sugarbeets =EmbeddedArt_hud_act_sugarbeets ;
        public static Class hud_act_sportsdrink =EmbeddedArt_hud_act_sportsdrink ;
        public static Class hud_act_potatoes =EmbeddedArt_hud_act_potatoes ;
        public static Class hud_act_sunflowers =EmbeddedArt_hud_act_sunflowers ;
        public static Class hud_act_mushroom =EmbeddedArt_hud_act_mushroom ;
        public static Class hud_act_jacolantern =EmbeddedArt_hud_act_jacolantern ;
        public static Class hud_act_candyapples =EmbeddedArt_hud_act_candyapples ;
        public static Class hud_act_candycorn =EmbeddedArt_hud_act_candycorn ;
        public static Class hud_act_grapescabernet =EmbeddedArt_hud_act_grapescabernet ;
        public static Class hud_act_cvcarsbug =EmbeddedArt_hud_act_cvcarsbug ;
        public static Class hud_biz_supply =EmbeddedArt_hud_biz_supply ;
        public static Class hud_biz_premiumsupply =EmbeddedArt_hud_biz_premiumsupply ;
        public static Class hud_act_blackberries =EmbeddedArt_hud_act_blackberries ;
        public static Class hud_act_agave =EmbeddedArt_hud_act_agave ;
        public static Class hud_act_sprouts =EmbeddedArt_hud_act_sprouts ;
        public static Class hud_act_winterwheat =EmbeddedArt_hud_act_winterwheat ;
        public static Class hud_act_coffee =EmbeddedArt_hud_act_coffee ;
        public static Class hud_act_turnip =EmbeddedArt_hud_act_turnip ;
        public static Class hud_act_edelweiss =EmbeddedArt_hud_act_edelweiss ;
        public static Class freeGiftSale_bg =EmbeddedArt_freeGiftSale_bg ;
        public static Class citySam_congratsAward =EmbeddedArt_citySam_congratsAward ;
        public static Class reward_burst =EmbeddedArt_reward_burst ;
        public static Class dia_close_on =EmbeddedArt_dia_close_on ;
        public static Class dia_close_over =EmbeddedArt_dia_close_over ;
        public static Class deliveryInfoField =EmbeddedArt_deliveryInfoField ;
        public static Class deliveryPick =EmbeddedArt_deliveryPick ;
        public static Class trainUIBG =EmbeddedArt_trainUIBG ;
        public static Class trainUIBG2 =EmbeddedArt_trainUIBG2 ;
        public static Class trainUIPick =EmbeddedArt_trainUIPick ;
        public static Class deliveryOptions =EmbeddedArt_deliveryOptions ;
        public static Class deliveryDialog =EmbeddedArt_deliveryDialog ;
        public static Class trainUIAmntField =EmbeddedArt_trainUIAmntField ;
        public static Class mkt_close_down =EmbeddedArt_mkt_close_down ;
        public static Class mkt_close_over =EmbeddedArt_mkt_close_over ;
        public static Class mkt_close_up =EmbeddedArt_mkt_close_up ;
        public static Class littleClose_down =EmbeddedArt_littleClose_down ;
        public static Class littleClose_over =EmbeddedArt_littleClose_over ;
        public static Class littleClose_up =EmbeddedArt_littleClose_up ;
        public static Class icon_cash_big =EmbeddedArt_icon_cash_big ;
        public static Class icon_cash =EmbeddedArt_icon_cash ;
        public static Class icon_coin =EmbeddedArt_icon_coin ;
        public static Class mkt_category_beak =EmbeddedArt_mkt_category_beak ;
        public static Class mkt_category_bubble =EmbeddedArt_mkt_category_bubble ;
        public static Class mkt_rollover_horizontalRule =EmbeddedArt_mkt_rollover_horizontalRule ;
        public static Class mkt_pop_info =EmbeddedArt_mkt_pop_info ;
        public static Class mkt_coinIcon =EmbeddedArt_mkt_coinIcon ;
        public static Class mkt_energyIcon =EmbeddedArt_mkt_energyIcon ;
        public static Class mkt_gasIcon =EmbeddedArt_mkt_gasIcon ;
        public static Class mkt_checkmark =EmbeddedArt_mkt_checkmark ;
        public static Class mkt_goodsIcon =EmbeddedArt_mkt_goodsIcon ;
        public static Class mkt_premiumGoodsIcon =EmbeddedArt_mkt_premiumGoodsIcon ;
        public static Class mkt_populationIcon =EmbeddedArt_mkt_populationIcon ;
        public static Class mkt_populationIcon_citizen =EmbeddedArt_mkt_populationIcon_citizen ;
        public static Class mkt_populationIcon_monster =EmbeddedArt_mkt_populationIcon_monster ;
        public static Class mkt_saleIcon =EmbeddedArt_mkt_saleIcon ;
        public static Class mkt_xpIcon =EmbeddedArt_mkt_xpIcon ;
        public static Class mkt_shieldIcon =EmbeddedArt_mkt_shieldIcon ;
        public static Class mkt_strikethroughIcon =EmbeddedArt_mkt_strikethroughIcon ;
        public static Class mkt_btn_green =EmbeddedArt_mkt_btn_green ;
        public static Class mkt_masteryStar =EmbeddedArt_mkt_masteryStar ;
        public static Class timer_bg =EmbeddedArt_timer_bg ;
        public static Class timer_colon =EmbeddedArt_timer_colon ;
        public static Class timer_slot =EmbeddedArt_timer_slot ;
        public static Class bogo_marketcard_bg =EmbeddedArt_bogo_marketcard_bg ;
        public static Class storage_bg =EmbeddedArt_storage_bg ;
        public static Class collections_bg =EmbeddedArt_collections_bg ;
        public static Class inventory_bg =EmbeddedArt_inventory_bg ;
        public static Class questNotifications_bg =EmbeddedArt_questNotifications_bg ;
        public static Class emptyAvatar =EmbeddedArt_emptyAvatar ;
        public static Class neighborCard_over =EmbeddedArt_neighborCard_over ;
        public static Class neighborCard =EmbeddedArt_neighborCard ;
        public static Class neighborCard_new =EmbeddedArt_neighborCard_new ;
        public static Class neighborCard_new_over =EmbeddedArt_neighborCard_new_over ;
        public static Class neighborCard_online =EmbeddedArt_neighborCard_online ;
        public static Class neighborCard_online_over =EmbeddedArt_neighborCard_online_over ;
        public static Class neighborCard_buildingBuddy =EmbeddedArt_neighborCard_buildingBuddy ;
        public static Class neighborCard_buildingBuddy_over =EmbeddedArt_neighborCard_buildingBuddy_over ;
        public static Class hud_nghbr_notification_ticker =EmbeddedArt_hud_nghbr_notification_ticker ;
        public static Class hud_nghbr_toasters_bg =EmbeddedArt_hud_nghbr_toasters_bg ;
        public static Class hud_nghbr_toasters_close_over =EmbeddedArt_hud_nghbr_toasters_close_over ;
        public static Class hud_nghbr_toasters_close_up =EmbeddedArt_hud_nghbr_toasters_close_up ;
        public static Class hud_nghbr_toasters_frame =EmbeddedArt_hud_nghbr_toasters_frame ;
        public static Class hud_no_profile_pic =EmbeddedArt_hud_no_profile_pic ;
        public static Class hud_cycleBttn_left_1 =EmbeddedArt_hud_cycleBttn_left_1 ;
        public static Class hud_cycleBttn_left_2 =EmbeddedArt_hud_cycleBttn_left_2 ;
        public static Class hud_cycleBttn_left_end =EmbeddedArt_hud_cycleBttn_left_end ;
        public static Class hud_cycleBttn_left_1_down =EmbeddedArt_hud_cycleBttn_left_1_down ;
        public static Class hud_cycleBttn_left_2_down =EmbeddedArt_hud_cycleBttn_left_2_down ;
        public static Class hud_cycleBttn_left_end_down =EmbeddedArt_hud_cycleBttn_left_end_down ;
        public static Class hud_cycleBttn_right_1 =EmbeddedArt_hud_cycleBttn_right_1 ;
        public static Class hud_cycleBttn_right_2 =EmbeddedArt_hud_cycleBttn_right_2 ;
        public static Class hud_cycleBttn_right_end =EmbeddedArt_hud_cycleBttn_right_end ;
        public static Class hud_cycleBttn_right_1_down =EmbeddedArt_hud_cycleBttn_right_1_down ;
        public static Class hud_cycleBttn_right_2_down =EmbeddedArt_hud_cycleBttn_right_2_down ;
        public static Class hud_cycleBttn_right_end_down =EmbeddedArt_hud_cycleBttn_right_end_down ;
        public static Class nghbr_icon_sandwich =EmbeddedArt_nghbr_icon_sandwich ;
        public static Class nghbr_popup_bg =EmbeddedArt_nghbr_popup_bg ;
        public static Class nghbr_icon_crate =EmbeddedArt_nghbr_icon_crate ;
        public static Class nghbr_pop_popup =EmbeddedArt_nghbr_pop_popup ;
        public static Class friendReplayPick =EmbeddedArt_friendReplayPick ;
        public static Class friendReplaySlide =EmbeddedArt_friendReplaySlide ;
        public static Class neighborActionsBG =EmbeddedArt_neighborActionsBG ;
        public static Class tool_onstate_btn =EmbeddedArt_tool_onstate_btn ;
        public static Class tool_offstate_btn =EmbeddedArt_tool_offstate_btn ;
        public static Class tool_cancel_up =EmbeddedArt_tool_cancel_up ;
        public static Class tool_cancel_over =EmbeddedArt_tool_cancel_over ;
        public static Class franchise_onstate_btn =EmbeddedArt_franchise_onstate_btn ;
        public static Class franchise_offstate_btn =EmbeddedArt_franchise_offstate_btn ;
        public static Class build_onstate_btn =EmbeddedArt_build_onstate_btn ;
        public static Class build_offstate_btn =EmbeddedArt_build_offstate_btn ;
        public static Class payroll_neighborBadge_checkin =EmbeddedArt_payroll_neighborBadge_checkin ;
        public static Class payroll_neighborBadge_collect =EmbeddedArt_payroll_neighborBadge_collect ;
        public static Class neighborVisit_neighborBadge_socialHelp =EmbeddedArt_neighborVisit_neighborBadge_socialHelp ;
        public static Class npcThoughtBubble =EmbeddedArt_npcThoughtBubble ;
        public static Class npcSpeechBubble =EmbeddedArt_npcSpeechBubble ;
        public static Class houseFeedbackBubble =EmbeddedArt_houseFeedbackBubble ;
        public static Class factoryTruckBubble =EmbeddedArt_factoryTruckBubble ;
        public static Class businessShockwave =EmbeddedArt_businessShockwave ;
        public static Class businessShockwavePreview =EmbeddedArt_businessShockwavePreview ;
        public static Class openIcon =EmbeddedArt_openIcon ;
        public static Class smallCoinIcon =EmbeddedArt_smallCoinIcon ;
        public static Class smallStarIcon =EmbeddedArt_smallStarIcon ;
        public static Class smallHumanIcon =EmbeddedArt_smallHumanIcon ;
        public static Class smallEnergyIcon =EmbeddedArt_smallEnergyIcon ;
        public static Class smallClockIcon =EmbeddedArt_smallClockIcon ;
        public static Class smallHappyIcon =EmbeddedArt_smallHappyIcon ;
        public static Class smallFoodIcon =EmbeddedArt_smallFoodIcon ;
        public static Class smallGoodsIcon =EmbeddedArt_smallGoodsIcon ;
        public static Class tourBusIcon =EmbeddedArt_tourBusIcon ;
        public static Class smallXPIcon =EmbeddedArt_smallXPIcon ;
        public static Class bigCoinIcon =EmbeddedArt_bigCoinIcon ;
        public static Class spark =EmbeddedArt_spark ;
        public static Class visitEnergyIcon =EmbeddedArt_visitEnergyIcon ;
        public static Class heartInventoryIcon =EmbeddedArt_heartInventoryIcon ;
        public static Class franchiseRequestIcon =EmbeddedArt_franchiseRequestIcon ;
        public static Class franchiseRequestPendingIcon =EmbeddedArt_franchiseRequestPendingIcon ;
        public static Class sale_sign =EmbeddedArt_sale_sign ;
        public static Class crewFriendBackground =EmbeddedArt_crewFriendBackground ;
        public static Class cashSaleBackground =EmbeddedArt_cashSaleBackground ;
        public static Class grassTile =EmbeddedArt_grassTile ;
        public static Class neighborVisitLargeCoin =EmbeddedArt_neighborVisitLargeCoin ;
        public static Class neighborVisitLargeEnergy =EmbeddedArt_neighborVisitLargeEnergy ;
        public static Class neighborVisitLargeXP =EmbeddedArt_neighborVisitLargeXP ;
        public static Class neighborVisitBlueHighlight =EmbeddedArt_neighborVisitBlueHighlight ;
        public static Class fr_tooltip_bg =EmbeddedArt_fr_tooltip_bg ;
        public static Class fr_tooltip_star_empty =EmbeddedArt_fr_tooltip_star_empty ;
        public static Class fr_tooltip_star_full =EmbeddedArt_fr_tooltip_star_full ;
        public static Class hud_act_garlic =EmbeddedArt_hud_act_garlic ;

        public static Class hud_kdbomb =EmbeddedArt_hud_kdbomb ;
        public static Class hud_lrbomb =EmbeddedArt_hud_lrbomb ;

        public static Class hud_collect =EmbeddedArt_hud_collect ;
        public static Class hud_fly =EmbeddedArt_hud_fly ;
        public static Class hud_transmit =EmbeddedArt_hud_transmit ;
        public static Class hud_topup =EmbeddedArt_hud_topup ;


        public  EmbeddedArt ()
        {
            return;
        }//end

        public static String  defaultFontName ()
        {
            return m_defaultFontName;
        }//end

        public static boolean  defaultFontEmbed ()
        {
            return m_defaultFontEmbed;
        }//end

        public static String  defaultFontNameBold ()
        {
            return m_defaultFontNameBold;
        }//end

        public static boolean  defaultBoldFontEmbed ()
        {
            return m_defaultBoldFontEmbed;
        }//end

        public static String  titleFont ()
        {
            return m_titleFont;
        }//end

        public static boolean  titleFontEmbed ()
        {
            return m_titleFontEmbed;
        }//end

        public static String  defaultSerifFont ()
        {
            return m_defaultSerifFont;
        }//end

        public static boolean  defaultSerifFontEmbed ()
        {
            return m_defaultSerifFontEmbed;
        }//end

        public static void  defaultFontName (String param1 )
        {
            m_defaultFontName = param1;
            return;
        }//end

        public static void  defaultFontEmbed (boolean param1 )
        {
            m_defaultFontEmbed = param1;
            return;
        }//end

        public static void  defaultFontNameBold (String param1 )
        {
            m_defaultFontNameBold = param1;
            return;
        }//end

        public static void  defaultBoldFontEmbed (boolean param1 )
        {
            m_defaultBoldFontEmbed = param1;
            return;
        }//end

        public static void  titleFont (String param1 )
        {
            m_titleFont = param1;
            return;
        }//end

        public static void  titleFontEmbed (boolean param1 )
        {
            m_titleFontEmbed = param1;
            return;
        }//end

        public static void  defaultSerifFont (String param1 )
        {
            m_defaultSerifFont = param1;
            return;
        }//end

        public static void  defaultSerifFontEmbed (boolean param1 )
        {
            m_defaultSerifFontEmbed = param1;
            return;
        }//end

        public static boolean  isEmbedFont (String param1 )
        {
            Object _loc_2 =null ;
            for(int i0 = 0; i0 < m_embedFonts.size(); i0++)
            {
            	_loc_2 = m_embedFonts.get(i0);

                if (_loc_2.name == param1)
                {
                    return _loc_2.isEmbed;
                }
            }
            return false;
        }//end

        public static void  setEmbedFont (String param1 ,boolean param2 )
        {
            m_embedFonts.push({name:param1, isEmbed:param2});
            return;
        }//end

        public static ASFontAdvProperties  getAdvancedFontProps (String param1 )
        {
            return new ASFontAdvProperties(isEmbedFont(param1), AntiAliasType.ADVANCED, GridFitType.PIXEL);
        }//end

        public static void  localizeAndUpdateFont (TextField param1 ,String param2 ,String param3 ,Array param4 =null )
        {
            updateFont(param1);
            return;
        }//end

        public static void  updateFont (TextField param1 ,Object param2 ,Object param3 =null )
        {
            _loc_4 = param1.defaultTextFormat;
            if (param2 == null)
            {
                param2 = Boolean(_loc_4.bold) || _loc_4.font == defaultFontNameBold;
            }
            if (param3 == null)
            {
                param3 = Boolean(_loc_4.italic);
            }
            _loc_5 = param2? (defaultFontNameBold) : (defaultFontName);
            TextFormat _loc_6 =new TextFormat(_loc_5 ,_loc_4.size ,_loc_4.color ,param2 ,param3 ,_loc_4.underline ,_loc_4.url ,_loc_4.target ,_loc_4.align ,_loc_4.leftMargin ,_loc_4.rightMargin ,_loc_4.indent ,_loc_4.leading );
            param1.embedFonts = param2 ? (defaultBoldFontEmbed) : (defaultFontEmbed);
            param1.defaultTextFormat = _loc_6;
            param1.setTextFormat(_loc_6);
            return;
        }//end

    }



