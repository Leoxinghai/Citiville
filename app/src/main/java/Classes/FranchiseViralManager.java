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

import Classes.virals.*;
import Display.*;
import Events.*;

    public class FranchiseViralManager
    {
        public static  int VIRAL_EMPTYLOT =0;
        public static  int VIRAL_APPROVEBUILDINGREQUEST =1;
        public static  int VIRAL_ACCEPTEDBUILDING =2;
        public static  int VIRAL_DECLINEDBUILDING =3;
        public static  int VIRAL_REMOVEDBUILDING =4;
        public static  int VIRAL_STARRATINGINCREASED =5;
        public static  int VIRAL_GROWHQ_FIRST =6;
        public static  int VIRAL_GROWHQ_NEXT =7;
        public static  int VIRAL_GROWHQ_LAST =8;
        public static  int VIRAL_REMINDERACCEPTBONUS =9;
        private static int m_ViralType =-1;
        private static String m_FranchiseType ="";
        private static String m_NeighborID ="";
        private static Array m_ReminderArray =new Array ();
        private static double m_NewStarRating =0;
        private static String m_FranchiseName ="";

        public  FranchiseViralManager ()
        {
            return;
        }//end

        public static boolean  checkStarRatingEarned (double param1 ,double param2 ,double param3 )
        {
            if (param1 < param3 && param2 >= param3)
            {
                return true;
            }
            return false;
        }//end

        public static void  triggerFranchiseViralFeeds (int param1 ,String param2 ="",String param3 ="",Array param4 =null ,double param5 =0,Business param6 =null )
        {
            Item _loc_18 =null ;
            m_ViralType = param1;
            String _loc_7 ="";
            m_FranchiseType = param2;
            m_NeighborID = param3;
            m_NewStarRating = 0;
            String _loc_8 ="";
            String _loc_9 ="";
            String _loc_10 ="assets/dialogs/feeds/feed_cityseal.png";
            _loc_11 = GenericPopup.TYPE_SHARECANCEL;
            _loc_12 =Global.player.cityName ;
            _loc_13 =Global.getVisiting ();
            _loc_14 =Global.player.getFriendFirstName(_loc_13 );
            _loc_15 =Global.player.getFriendCityName(_loc_13 );
            String _loc_16 ="";
            String _loc_17 ="";
            if (m_FranchiseType.length > 0)
            {
                _loc_18 = Global.gameSettings().getItemByName(m_FranchiseType);
                if (_loc_18.type == "business" && param6)
                {
                    _loc_16 = param6.getFranchiseName();
                    m_FranchiseName = _loc_16;
                }
                else if (m_NeighborID.length > 0)
                {
                    _loc_16 = Global.franchiseManager.model.getDefaultFranchiseName(m_FranchiseType, m_NeighborID);
                    m_FranchiseName = _loc_16;
                }
                else if (_loc_18.type == "business")
                {
                    m_FranchiseName = Global.franchiseManager.model.getFranchiseName(m_FranchiseType);
                }
                else
                {
                    _loc_17 = ZLoc.t("Items", _loc_18.name + "_friendlyName");
                    m_FranchiseName = _loc_16;
                }
            }
            if (param3.length > 0)
            {
                _loc_14 = Global.franchiseManager.getMapOwnerFirstName(param3);
            }
            if (m_ViralType == VIRAL_STARRATINGINCREASED && m_NeighborID.length > 0 && m_FranchiseType.length > 0)
            {
                m_NewStarRating = param5;
            }
            switch(m_ViralType)
            {
                case VIRAL_EMPTYLOT:
                {
                    _loc_8 = ZLoc.t("Dialogs", "viralFranchise_EmptyLot_prompt", {cityname:_loc_12});
                    _loc_9 = "viralFranchise_EmptyLot";
                    _loc_10 = "assets/dialogs/feeds/feed_owneracceptsfranchise.png";
                    _loc_7 = ViralType.FRANCHISE_EMPTY_LOT;
                    break;
                }
                case VIRAL_APPROVEBUILDINGREQUEST:
                {
                    _loc_8 = ZLoc.t("Dialogs", "viralFranchise_ApproveBuildingRequest_prompt", {friendName:_loc_14});
                    _loc_9 = "viralFranchise_ApproveBuildingRequest";
                    _loc_10 = "assets/dialogs/feeds/feed_needsapproval.png";
                    _loc_7 = ViralType.FRANCHISE_BUILDING_REQUEST;
                    break;
                }
                case VIRAL_ACCEPTEDBUILDING:
                {
                    _loc_8 = ZLoc.t("Dialogs", "viralFranchise_AcceptBuilding_prompt", {franchiseName:_loc_16, friendName:_loc_14});
                    _loc_9 = "viralFranchise_AcceptBuilding";
                    _loc_10 = "assets/dialogs/feeds/feed_owneracceptsfranchise.png";
                    _loc_7 = ViralType.FRANCHISE_ACCEPTED_BUILDING;
                    break;
                }
                case VIRAL_DECLINEDBUILDING:
                {
                    _loc_8 = ZLoc.t("Dialogs", "viralFranchise_DeclineBuilding_prompt", {friendName:_loc_14, cityname:_loc_12, franchiseName:_loc_16});
                    _loc_9 = "viralFranchise_DeclineBuilding";
                    _loc_10 = "assets/dialogs/feeds/feed_franchisedeclined.png";
                    _loc_7 = ViralType.FRANCHISE_DECLINED_BUILDING;
                    break;
                }
                case VIRAL_REMOVEDBUILDING:
                {
                    _loc_8 = ZLoc.t("Dialogs", "viralFranchise_RemoveBuilding_prompt", {friendName:_loc_14, cityname:_loc_12});
                    _loc_9 = "viralFranchise_RemoveBuilding";
                    _loc_10 = "assets/dialogs/feeds/feed_removefranchise.png";
                    _loc_7 = ViralType.FRANCHISE_REMOVED_BUILDING;
                    break;
                }
                case VIRAL_GROWHQ_FIRST:
                {
                    _loc_8 = ZLoc.t("Dialogs", "viralFranchise_GrowHQ1_prompt");
                    _loc_9 = "viralFranchise_GrowHQ1";
                    _loc_10 = "assets/dialogs/feeds/feed_growheadquarters.png";
                    _loc_7 = ViralType.FRANCHISE_GROW_HQ;
                    _loc_11 = GenericPopup.TYPE_OK;
                    break;
                }
                case VIRAL_GROWHQ_NEXT:
                {
                    _loc_8 = ZLoc.t("Dialogs", "viralFranchise_GrowHQ2_prompt");
                    _loc_9 = "viralFranchise_GrowHQ2";
                    _loc_10 = "assets/dialogs/feeds/feed_growheadquarters.png";
                    _loc_7 = ViralType.FRANCHISE_GROW_HQ;
                    break;
                }
                case VIRAL_GROWHQ_LAST:
                {
                    _loc_8 = ZLoc.t("Dialogs", "viralFranchise_GrowHQ3_prompt", {businessType:m_FranchiseName});
                    _loc_9 = "viralFranchise_GrowHQ3";
                    _loc_10 = "assets/dialogs/feeds/feed_growheadquarters.png";
                    _loc_7 = ViralType.FRANCHISE_GROW_HQ;
                    break;
                }
                case VIRAL_STARRATINGINCREASED:
                {
                    _loc_8 = ZLoc.t("Dialogs", "viralFranchise_StarUp_prompt", {businessType:m_FranchiseName, friendName:_loc_14});
                    _loc_9 = "viralFranchise_StarUp";
                    _loc_10 = "assets/dialogs/feeds/feed_starratingincrease.png";
                    _loc_7 = ViralType.FRANCHISE_STAR_RATING_INCREASED;
                    break;
                }
                case VIRAL_REMINDERACCEPTBONUS:
                {
                    _loc_8 = ZLoc.t("Dialogs", "viralFranchise_BonusRemindSent_prompt_single", {friendName:_loc_14});
                    _loc_9 = "viralFranchise_BonusRemindSent";
                    _loc_10 = "assets/dialogs/feeds/feed_remindertoaccept.png";
                    _loc_7 = ViralType.FRANCHISE_BONUS_REMIND_ACCEPT;
                    break;
                }
                default:
                {
                    break;
                }
            }
            UI.displayViralShareMessage(_loc_7, _loc_8, _loc_11, onViralDialogClosed, _loc_9, true, _loc_10, _loc_9);
            return;
        }//end

        private static void  onViralDialogClosed (GenericPopupEvent event )
        {
            Item _loc_8 =null ;
            event.target.removeEventListener(GenericPopupEvent.SELECTED, onViralDialogClosed);
            if (event.button != GenericPopup.YES)
            {
                return;
            }
            _loc_2 =Global.player.cityName ;
            _loc_3 =Global.getVisiting ();
            _loc_4 =Global.player.getFriendFirstName(_loc_3 );
            _loc_5 =Global.player.getFriendCityName(_loc_3 );
            _loc_6 = m_FranchiseName;
            String _loc_7 ="";
            if (m_FranchiseType.length > 0)
            {
                _loc_8 = Global.gameSettings().getItemByName(m_FranchiseType);
                if (_loc_8.type == "business")
                {
                    _loc_7 = ZLoc.t("Items", _loc_8.name + "_friendlyName");
                }
            }
            if (m_NeighborID.length > 0)
            {
                _loc_3 = m_NeighborID;
                _loc_4 = Global.player.getFriendFirstName(m_NeighborID);
            }
            switch(m_ViralType)
            {
                case VIRAL_EMPTYLOT:
                {
                    Global.world.viralMgr.sendFranchiseFeed_EmptyLot(Global.player, Global.player.cityName);
                    break;
                }
                case VIRAL_APPROVEBUILDINGREQUEST:
                {
                    Global.world.viralMgr.sendFranchiseFeed_BuildingRequest(_loc_6, _loc_3);
                    break;
                }
                case VIRAL_ACCEPTEDBUILDING:
                {
                    Global.world.viralMgr.sendFranchiseFeed_AcceptedBuilding(_loc_2, _loc_6, _loc_3);
                    break;
                }
                case VIRAL_DECLINEDBUILDING:
                {
                    Global.world.viralMgr.sendFranchiseFeed_DeclinedBuilding(_loc_2, _loc_6, _loc_3);
                    break;
                }
                case VIRAL_REMOVEDBUILDING:
                {
                    Global.world.viralMgr.sendFranchiseFeed_RemovedBuilding(_loc_2, _loc_6, _loc_3);
                    break;
                }
                case VIRAL_STARRATINGINCREASED:
                {
                    Global.world.viralMgr.sendFranchiseFeed_StarRatingIncreased(Global.player, _loc_5, _loc_6, m_NewStarRating);
                    break;
                }
                case VIRAL_GROWHQ_NEXT:
                {
                    Global.world.viralMgr.sendFranchiseFeed_GrowHQ(Global.player, _loc_6);
                    break;
                }
                case VIRAL_GROWHQ_LAST:
                {
                    Global.world.viralMgr.sendFranchiseFeed_GrowHQ(Global.player, _loc_6);
                    break;
                }
                case VIRAL_REMINDERACCEPTBONUS:
                {
                    Global.world.viralMgr.sendFranchiseFeed_ReminderAcceptBonus(_loc_5, _loc_6, _loc_3, _loc_5);
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

    }


