package Classes.virals;

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

    public class ViralType
    {
        public static  String LEVEL_UP ="level_up";
        public static  String LEVEL_UP_01 ="level_up_01";
        public static  String LEVEL_UP_02 ="level_up_02";
        public static  String LEVEL_UP_03 ="level_up_03";
        public static  String LEVEL_UP_04 ="level_up_04";
        public static  String LEVEL_UP_05 ="level_up_05";
        public static  String LEVEL_UP_06 ="level_up_06";
        public static  String LEVEL_UP_07 ="level_up_07";
        public static  String LEVEL_UP_08 ="level_up_08";
        public static  String LEVEL_UP_09 ="level_up_09";
        public static  String LEVEL_UP_10 ="level_up_10";
        public static  String QUEST_COMPLETE ="quest_complete";
        public static  String COLLECTION_TRADE_IN ="collection_trade_in";
        public static  String CROP_MASTERY_SHARE_GOODS ="crop_mastery_share_goods";
        public static  String ENERGY_FEED ="energy_feed";
        public static  String ENERGY_REQUEST ="energy_request";
        public static  String NAME_YOUR_CITY ="name_your_city";
        public static  String WISHLIST_REQUEST ="wishlist_request";
        public static  String NEIGHBOR_VISIT_DEFAULT ="neighbor_visit_default";
        public static  String NEIGHBOR_VISIT_BUS ="neighbor_visit_bus";
        public static  String NEIGHBOR_VISIT_CROPS ="neighbor_visit_crops";
        public static  String NEIGHBOR_VISIT_SHIPS ="neighbor_visit_ships";
        public static  String NEIGHBOR_VISIT_BUILD ="neighbor_visit_build";
        public static  String REPUTATION_LEVEL_UP ="reputation_level_up";
        public static  String FRANCHISE_EMPTY_LOT ="franchise_empty_lot";
        public static  String FRANCHISE_BUILDING_REQUEST ="franchise_building_request";
        public static  String FRANCHISE_ACCEPTED_BUILDING ="franchise_accepted_building";
        public static  String FRANCHISE_DECLINED_BUILDING ="franchise_declined_building";
        public static  String FRANCHISE_REMOVED_BUILDING ="franchise_removed_building";
        public static  String FRANCHISE_STAR_RATING_INCREASED ="franchise_star_rating_increased";
        public static  String FRANCHISE_GROW_HQ ="franchise_grow_hq";
        public static  String FRANCHISE_BONUS_REMIND_ACCEPT ="franchise_bonus_remind_accept";
        public static  String QUEST_ITEM_PTA ="quest_item_pta";
        public static  String TRAIN_TRADE_BUY ="train_trade_buy";
        public static  String TRAIN_TRADE_SOLD ="train_trade_sold";
        public static  String TRAIN_TRADE_SELL ="train_trade_sell";
        public static  String BECOME_NEIGHBOR ="become_neighbor";
        public static  String NEWS_VIRAL ="news_viral";
        public static  String UGC_VIRAL ="ugc_viral";
        public static  String DAILY_BONUS ="daily_bonus";
        public static  String PERMITS_REQUEST ="permits_request";
        public static  String HOLIDAY2010_TREE_GIFT_REQUEST ="holiday2010_tree_gift_request";
        public static  String NEIGHBOR_GATE_QUEST_REQUEST ="neighbor_gate_quest_request";
        public static  String TRAIN_SEND_1 ="qm_train_1";
        public static  String TRAIN_SEND_2 ="qm_train_2";
        public static  String TRAIN_SEND_3 ="qm_train_3";
        public static  String TRAIN_SEND_4 ="qm_train_4";
        public static  String ROLL_CALL_COLLECTION_REMINDER ="rollcall_collection_reminder";
        public static  String ROLL_CALL_NOTIFY_CHECKIN ="rollcall_notify_checkin";
        public static  String ROLL_CALL_CHECKIN ="roll_call_checkin";
        public static  String VDAY2011_CARD_THANK_YOU ="vday2011_card_thank_you";
        public static  String VDAY2011_ADMIRER_BRAG ="vday2011_admirer_brag";
        public static  String VDAY2011_ACHIEVEMENTS_BRAG ="vday2011_achievements_brag";
        public static  String VISITORUI ="visitorUI_card_thank_you";
        public static  String HOTEL_CHECKIN ="hotel_checkin";
        public static  String HOTEL_GUESTCHECKIN ="hotel_guestCheckIn";
        public static  String HOTEL_GRANTVIP ="hotel_grantvip";
        public static  String HOTEL_THANKYOU ="hotel_thankyou";
        public static  String BUSINESS_OPENING ="business_opening";
        public static  String HARVEST_BUSINESS_MASTERY ="harvest_business_mastery";
        public static  String GAS ="AskForGas";
        public static  String REQUEST_SIGNATURE ="request_signature";
        public static  String WONDER_OPENING ="wonder_opening";
        public static  String SEND_GARDEN_SEED ="garden_send_seed";
        public static  String CAMPAIGN_PAYER ="campaign_payer";
        public static  String UNIVERSITY_CUSTOM ="univ_viral";
        public static  String ASK_BUILDING_BUDDY_MATERIAL ="ask_building_buddy_material";
        public static  String ASK_BUILDING_BUDDY_CREW ="ask_building_buddy_crew";

        public  ViralType ()
        {
            return;
        }//end

    }


