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

import Classes.*;
import Classes.util.*;
import Display.*;
import Display.DialogUI.*;
import Display.MatchmakingUI.*;
import Engine.*;
import Engine.Managers.*;
import Modules.goals.mastery.*;
import Modules.matchmaking.*;
import Modules.request.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Transactions.*;
import com.zynga.skelly.util.*;
//import flash.external.*;
//import flash.utils.*;
import validation.*;

    public class ViralManager
    {
        private Dictionary m_activeVirals ;
        private Dictionary m_activeRequests ;
        private int m_nextRequestId ;
        private Object m_addedFriends ;
        private static  boolean PERFORM_IMMEDIATELY =true ;
        public static  int STREAM_PUBLISH_AUTO =0;
        public static  int STREAM_PUBLISH_DEFAULT =1;
        public static  int STREAM_PUBLISH_FAIL =2;
        public static  String STREAM_PUBLISH_FLAG ="can_publish_stream";
        private static double m_FreezeTimerStart =-1;
        private static double m_ThawTimerStart =-1;
        private static double m_viralFreezeStart =-1;
        private static double m_viralFreezeTimeout =-1;
        private static String m_customFreezeText =null ;
        private static boolean m_showLoading =true ;

        public  ViralManager ()
        {
            this.m_addedFriends = {};
            this.m_activeVirals = new Dictionary();
            this.m_activeRequests = new Dictionary();
            this.m_nextRequestId = 0;
            m_viralFreezeStart = Global.gameSettings().getNumber("viralFreezeStart", 500);
            m_viralFreezeTimeout = Global.gameSettings().getNumber("viralFreezeTimeout", 5000);
            m_showLoading = !Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_DEPRECATED, ExperimentDefinitions.EXPERIMENT_WMODE_DEFAULT);
            return;
        }//end

        public void  loadActiveVirals (Object param1 )
        {
            String _loc_2 =null ;
            Viral _loc_3 =null ;
            for(int i0 = 0; i0 < param1.size(); i0++)
            {
            	_loc_2 = param1.get(i0);

                _loc_3 = new Viral(_loc_2, uint(param1.get(_loc_2).timesPosted), Number(param1.get(_loc_2).originTime), param1.get(_loc_2).recipients);
                this.m_activeVirals.put(_loc_2,  _loc_3);
            }
            return;
        }//end

        public Viral  getActiveViralByType (String param1 )
        {
            return this.m_activeVirals.get(param1);
        }//end

        private String  getAutoPublishingFlag (String param1 )
        {
            return "auto_publishing_" + param1;
        }//end

        public boolean  getGlobalPublishStreamCheck (String param1 ,boolean param2 =true )
        {
            _loc_3 = this.getAutoPublishingFlag(param1 );
            if (!Global.player.doesFlagExist(_loc_3))
            {
                return param2;
            }
            _loc_4 =Global.player.getFlag(_loc_3 );
            return Global.player.getFlag(_loc_3).value == 1;
        }//end

        public void  setGlobalPublishStreamCheck (boolean param1 ,String param2 )
        {
            _loc_3 = this.getAutoPublishingFlag(param2 );
            _loc_4 =Global.player.getFlag(_loc_3 );
            if (param1 !=null)
            {
                _loc_4.setAndSave(1);
            }
            else
            {
                _loc_4.setAndSave(0);
            }
            return;
        }//end

        public boolean  sendFeedorAutoPublish (String param1 ,String param2 ,Object param3 =null ,String param4 =null ,Object param5 =null ,String param6 =null ,boolean param7 =true ,Function param8 =null ,boolean param9 =true )
        {
            param4 = param4 ? (param4) : (Global.player.uid);
            if (param3 == null)
            {
                param3 = {};
            }
            if (this.getGlobalPublishStreamCheck(param2, param9))
            {
                return this.sendAutoStreamPublish(param1, param3, param4, param5, param6, param7, param8);
            }
            return this.sendStreamPublish(param1, param3, param4, param5, param6, param8);
        }//end

        public boolean  canPost (String param1 ,String param2 )
        {
            ActionHelpDialog addFriend ;
            viralType = param1;
            recipientId = param2;
            boolean result ;
            viral = this.m_activeVirals.get(viralType);
            if (recipientId && !Global.isFriend(recipientId))
            {
                addFriend =new ActionHelpDialog ("BuildingBuddyAddFriend",recipientId ,void  ()
            {
                MatchmakingManager.instance.addFriend(recipientId);
                return;
            }//end
            );
                UI.displayPopup(addFriend);
                result;
            }
            else if (viral != null && viral.hasHitPostLimit(recipientId) && !viral.hasReset())
            {
                result;
            }
            else
            {
                result = this.checkValidatorByName(viralType);
            }
            return result;
        }//end

        public int  getTimeTillCanPost (String param1 ,String param2 )
        {
            _loc_3 = this.m_activeVirals.get(param1) ;
            int _loc_4 =-1;
            if (_loc_3 != null)
            {
                _loc_4 = _loc_3.timeToUnlock();
            }
            return _loc_4;
        }//end

        public void  postViral (String param1 ,String param2 )
        {
            this.purgeResetVirals();
            Viral _loc_3 =null ;
            if (this.m_activeVirals.hasOwnProperty(param1))
            {
                _loc_3 = this.m_activeVirals.get(param1);
            }
            if (_loc_3 == null)
            {
                if (param2)
                {
                    this.m_activeVirals.put(param1,  new Viral(param1, 0, GlobalEngine.serverTime / 1000));
                    this.m_activeVirals.get(param1).addPost(param2);
                }
                else
                {
                    this.m_activeVirals.put(param1,  new Viral(param1, 1, GlobalEngine.serverTime / 1000));
                }
            }
            else if (_loc_3.hasHitPostLimit(param2) == false)
            {
                _loc_3.addPost(param2);
            }
            return;
        }//end

        public boolean  checkValidatorByName (String param1 )
        {
            GenericValidationScript _loc_4 =null ;
            _loc_2 =Global.gameSettings().getViralXMLByName(param1 );
            _loc_3 = _loc_2.@validate;
            if (_loc_3 && _loc_3.length > 0)
            {
                _loc_4 = Global.validationManager.getValidator(_loc_3);
                if (_loc_4)
                {
                    return _loc_4.validate();
                }
            }
            return true;
        }//end

        protected void  purgeResetVirals ()
        {
            Viral _loc_1 =null ;
            for(int i0 = 0; i0 < this.m_activeVirals.size(); i0++)
            {
            	_loc_1 = this.m_activeVirals.get(i0);

                if (_loc_1.hasReset())
                {
                    this.m_activeVirals.put(_loc_1.getType(),  null);
                    delete this.m_activeVirals.get(_loc_1.getType());
                }
            }
            return;
        }//end

        protected boolean  sendStreamPublish (String param1 ,Object param2 ,String param3 =null ,Object param4 =null ,String param5 =null ,Function param6 =null )
        {
            int experimentVariant ;
            FeedDialog feedDialog ;
            viralType = param1;
            viralData = param2;
            targetID = param3;
            clickthroughParams = param4;
            userMessage = param5;
            callback = param6;
            if (this.canPost(viralType, targetID))
            {
                experimentVariant = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_FEED_COMMENTING);
                if ((experimentVariant == ExperimentDefinitions.EXPERIMENT_FEED_COMMENTING_VARIANT1 || experimentVariant == ExperimentDefinitions.EXPERIMENT_FEED_COMMENTING_VARIANT2) && GlobalEngine.getTimer() / 1000 > Global.player.lastSuperFeedPostError + Global.player.SUPER_FEED_DELAY)
                {
                    feedDialog =new FeedDialog (viralType ,viralData ,targetID ,void  (String param1 )
            {
                GameTransactionManager.addTransaction(new TSuperStreamPublish(viralType, viralData, targetID, clickthroughParams, null, param1, callback), PERFORM_IMMEDIATELY, true);
                postViral(viralType, targetID);
                return;
            }//end
            , callback);
                    UI.displayPopup(feedDialog, false, "feedDialog");
                }
                else
                {
                    resetFreezeTimer("FrameLoading_Viral");
                    GameTransactionManager.addTransaction(new TCityStreamPublish(viralType, viralData, targetID, clickthroughParams, null, userMessage, callback), PERFORM_IMMEDIATELY, true);
                    this.postViral(viralType, targetID);
                }
                return true;
            }
            else
            {
                return false;
            }
        }//end

        protected boolean  sendAutoStreamPublish (String param1 ,Object param2 ,String param3 =null ,Object param4 =null ,String param5 =null ,boolean param6 =true ,Function param7 =null )
        {
            boolean _loc_8 =false ;
            if (this.canPost(param1))
            {
                if (!GlobalEngine.socialNetwork.userHasStreamPermissions())
                {
                    resetFreezeTimer("FrameLoading_Viral");
                    GlobalEngine.socialNetwork.showStreamPermissions(Curry.curry(this.onStreamPermissionsClosed, param1, param2, param3, param4, param5, param6, param7));
                    _loc_8 = true;
                }
                else
                {
                    _loc_8 = this.sendAutoStreamPublishHelper(param1, param2, param3, param4, param5, param7);
                }
            }
            return _loc_8;
        }//end

        protected void  onStreamPermissionsClosed (String param1 ,Object param2 ,String param3 =null ,Object param4 =null ,String param5 =null ,boolean param6 =true ,Function param7 =null )
        {
            boolean _loc_8 =false ;
            if (GlobalEngine.socialNetwork.userHasStreamPermissions())
            {
                _loc_8 = this.sendAutoStreamPublishHelper(param1, param2, param3, param4, param5, param7);
                if (!_loc_8)
                {
                    param7(STREAM_PUBLISH_FAIL);
                }
            }
            else if (param6)
            {
                this.sendStreamPublish(param1, param2, param3, param4, param5, param7);
            }
            return;
        }//end

        protected boolean  sendAutoStreamPublishHelper (String param1 ,Object param2 ,String param3 =null ,Object param4 =null ,String param5 =null ,Function param6 =null )
        {
            int _loc_7 =0;
            if (this.canPost(param1))
            {
                _loc_7 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_FEED_COMMENTING);
                if ((_loc_7 == ExperimentDefinitions.EXPERIMENT_FEED_COMMENTING_VARIANT1 || _loc_7 == ExperimentDefinitions.EXPERIMENT_FEED_COMMENTING_VARIANT2) && GlobalEngine.getTimer() > Global.player.lastSuperFeedPostError + Global.player.SUPER_FEED_DELAY)
                {
                    if (param3 == Global.player.uid)
                    {
                        StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.FEED, StatsPhylumType.FEED_DIALOG_SHARE, param1, "auto");
                    }
                    else
                    {
                        StatsManager.sample(100, StatsCounterType.DIALOG, StatsKingdomType.WALL_TO_WALL, StatsPhylumType.FEED_DIALOG_SHARE, param1, "auto");
                    }
                    GameTransactionManager.addTransaction(new TSuperStreamPublish(param1, param2, param3, param4, null, param5, param6, true), PERFORM_IMMEDIATELY);
                }
                else
                {
                    resetFreezeTimer("FrameLoading_Viral");
                    GameTransactionManager.addTransaction(new TCityStreamPublish(param1, param2, param3, param4, null, param5, param6, true), PERFORM_IMMEDIATELY);
                }
                this.postViral(param1, param3);
                return true;
            }
            else
            {
                return false;
            }
        }//end

        public boolean  sendLevelUpFeed (Player param1 ,double param2 )
        {
            _loc_3 = param1.levelUpNickName(param2 );
            Array _loc_4 =new Array ();
            if (param2 < 30)
            {
                _loc_4.push(ViralType.LEVEL_UP_01);
                _loc_4.push(ViralType.LEVEL_UP_02);
                _loc_4.push(ViralType.LEVEL_UP_03);
            }
            else if (param2 < 60)
            {
                _loc_4.push(ViralType.LEVEL_UP_04);
                _loc_4.push(ViralType.LEVEL_UP_05);
                _loc_4.push(ViralType.LEVEL_UP_06);
            }
            else if (param2 < 90)
            {
                _loc_4.push(ViralType.LEVEL_UP_07);
                _loc_4.push(ViralType.LEVEL_UP_08);
                _loc_4.push(ViralType.LEVEL_UP_09);
            }
            else
            {
                _loc_4.push(ViralType.LEVEL_UP_10);
            }
            _loc_5 = Math.round(Utilities.randBetween(0,(_loc_4.length -1)));
            _loc_6 = _loc_4.get(_loc_5);
            return this.sendStreamPublish(_loc_6, {level:param2, nickName:_loc_3}, param1.uid);
        }//end

        public boolean  sendCampaignPayerFeed (Player param1 ,String param2 ,String param3 )
        {
            return this.sendStreamPublish(ViralType.CAMPAIGN_PAYER, {friendFirst:param2, numberCash:"10"}, param3);
        }//end

        public boolean  sendQuestCompleteFeed (Player param1 ,String param2 ,String param3 )
        {
            return this.sendStreamPublish(ViralType.QUEST_COMPLETE, {quest:param2, questName:param3, cityname:Global.player.cityName}, param1.uid);
        }//end

        public boolean  sendCollectionTradeInFeed (Player param1 ,String param2 )
        {
            return this.sendStreamPublish(ViralType.COLLECTION_TRADE_IN, {collection:param2}, param1.uid);
        }//end

        public boolean  sendCropMasteryShareGoodsFeed (Player param1 ,int param2 ,String param3 ,String param4 ,String param5 )
        {
            Object _loc_6 ={category subcategory "crop_mastery",,family };
            return this.sendFeedorAutoPublish(ViralType.CROP_MASTERY_SHARE_GOODS, MasteryGoal.PUBLISH_STREAM_FEATURE_NAME, {level:param2, mastery:param3, _feedImage:param4, contractName:param5, _ontology:_loc_6}, param1.uid);
        }//end

        public boolean  sendEnergyFeed (Player param1 )
        {
            return this.sendStreamPublish(ViralType.ENERGY_FEED, {}, param1.uid);
        }//end

        public boolean  sendNameYourCityFeed (Player param1 ,String param2 )
        {
            return this.sendStreamPublish(ViralType.NAME_YOUR_CITY, {cityname:param2}, param1.uid);
        }//end

        public boolean  sendMunicipalMaterialFeed (Player param1 ,String param2 ,Item param3 )
        {
            return this.sendCustomMaterialFeed("municipal_", param1, param2, param3);
        }//end

        public boolean  sendCustomMaterialFeed (String param1 ,Player param2 ,String param3 ,Item param4 )
        {
            _loc_5 = this.sendStreamPublish(param1 +param4.name ,{building param3 ,material.name ,materialName.localizedName },param2.uid );
            return this.sendStreamPublish(param1 + param4.name, {building:param3, material:param4.name, materialName:param4.localizedName}, param2.uid);
        }//end

        public boolean  sendWishlistRequest (Player param1 )
        {
            return this.sendStreamPublish(ViralType.WISHLIST_REQUEST, {}, param1.uid);
        }//end

        public boolean  sendNeighborVisitFeed_Default (Player param1 ,String param2 ,String param3 )
        {
            return this.sendStreamPublish(ViralType.NEIGHBOR_VISIT_DEFAULT, {toCity:param3}, param2);
        }//end

        public boolean  sendNeighborVisitFeed_Bus (Player param1 ,String param2 ,String param3 ,String param4 )
        {
            return this.sendStreamPublish(ViralType.NEIGHBOR_VISIT_BUS, {fromCity:param2, toCity:param4}, param3);
        }//end

        public boolean  sendNeighborVisitFeed_Crop (Player param1 ,String param2 )
        {
            return this.sendStreamPublish(ViralType.NEIGHBOR_VISIT_CROPS, {}, param2);
        }//end

        public boolean  sendNeighborVisitFeed_Ship (Player param1 ,String param2 )
        {
            return this.sendStreamPublish(ViralType.NEIGHBOR_VISIT_SHIPS, {}, param2);
        }//end

        public boolean  sendNeighborVisitFeed_Build (Player param1 ,String param2 )
        {
            return this.sendStreamPublish(ViralType.NEIGHBOR_VISIT_BUILD, {}, param2);
        }//end

        public boolean  sendReputationUpFeed (Player param1 ,double param2 )
        {
            return this.sendStreamPublish(ViralType.REPUTATION_LEVEL_UP, {level:param2, cityname:Global.player.cityName}, param1.uid);
        }//end

        public boolean  sendQuestFeed (Player param1 ,String param2 )
        {
            return this.sendStreamPublish(param2, {cityname:Global.player.cityName}, param1.uid);
        }//end

        public boolean  sendFranchiseFeed_EmptyLot (Player param1 ,String param2 )
        {
            return this.sendStreamPublish(ViralType.FRANCHISE_EMPTY_LOT, {cityname:param2}, param1.uid);
        }//end

        public boolean  sendFranchiseFeed_BuildingRequest (String param1 ,String param2 )
        {
            return this.sendStreamPublish(ViralType.FRANCHISE_BUILDING_REQUEST, {franchiseName:param1}, param2);
        }//end

        public boolean  sendFranchiseFeed_AcceptedBuilding (String param1 ,String param2 ,String param3 )
        {
            return this.sendStreamPublish(ViralType.FRANCHISE_ACCEPTED_BUILDING, {cityname:param1, franchiseName:param2}, param3);
        }//end

        public boolean  sendFranchiseFeed_DeclinedBuilding (String param1 ,String param2 ,String param3 )
        {
            return this.sendStreamPublish(ViralType.FRANCHISE_DECLINED_BUILDING, {cityname:param1, franchiseName:param2}, param3);
        }//end

        public boolean  sendFranchiseFeed_RemovedBuilding (String param1 ,String param2 ,String param3 )
        {
            return this.sendStreamPublish(ViralType.FRANCHISE_REMOVED_BUILDING, {cityname:param1, franchiseName:param2}, param3);
        }//end

        public boolean  sendFranchiseFeed_StarRatingIncreased (Player param1 ,String param2 ,String param3 ,double param4 )
        {
            return this.sendStreamPublish(ViralType.FRANCHISE_STAR_RATING_INCREASED, {franchiseName:param3, friendCity:param2, starsLevel:param4}, param1.uid);
        }//end

        public boolean  sendFranchiseFeed_GrowHQ (Player param1 ,String param2 )
        {
            return this.sendStreamPublish(ViralType.FRANCHISE_GROW_HQ, {franchiseName:param2}, param1.uid);
        }//end

        public boolean  sendFranchiseFeed_ReminderAcceptBonus (String param1 ,String param2 ,String param3 ,String param4 )
        {
            return this.sendStreamPublish(ViralType.FRANCHISE_BONUS_REMIND_ACCEPT, {cityname:param1, franchiseName:param2, friendCity:param4}, param3);
        }//end

        public boolean  sendAskForGas ()
        {
            return this.sendStreamPublish(ViralType.GAS, {}, Global.player.uid);
        }//end

        public boolean  sendTrainTradeViral_Buy (Player param1 ,String param2 ,String param3 )
        {
            return this.sendStreamPublish(ViralType.TRAIN_TRADE_BUY, {commodityName:param3}, param2);
        }//end

        public boolean  sendTrainTradeViral_Sold (Player param1 ,String param2 ,String param3 )
        {
            return this.sendStreamPublish(ViralType.TRAIN_TRADE_SOLD, {commodityName:param3}, param2);
        }//end

        public boolean  sendTrainTradeViral_Sell (Player param1 ,String param2 ,String param3 )
        {
            return this.sendStreamPublish(ViralType.TRAIN_TRADE_SELL, {commodityName:param3}, param2);
        }//end

        public boolean  sendNewsViralFeed (Player param1 ,String param2 ,String param3 ,String param4 )
        {
            return this.sendStreamPublish(ViralType.NEWS_VIRAL, {newsTitle:param2, newsSubhead:param3, newsMessage:param4}, param1.uid);
        }//end

        public boolean  sendUGCViralFeed (Player param1 ,String param2 ,String param3 ,String param4 ,String param5 ="")
        {
            return this.sendStreamPublish(ViralType.UGC_VIRAL, {franchiseType:param3, franchiseName:param4, cityname:param2, itemName:param5}, param1.uid);
        }//end

        public boolean  sendBecomeNeighborFeed ()
        {
            return this.sendStreamPublish(ViralType.BECOME_NEIGHBOR, {}, Global.player.uid);
        }//end

        public boolean  sendDailyBonusFeed ()
        {
            return this.sendStreamPublish(ViralType.DAILY_BONUS, {}, Global.player.uid);
        }//end

        public boolean  sendPermitsRequest (Player param1 ,String param2 )
        {
            return this.sendStreamPublish(ViralType.PERMITS_REQUEST, {cityname:param2}, param1.uid);
        }//end

        public boolean  sendHoliday2010PresentRequest (Player param1 )
        {
            return this.sendStreamPublish(ViralType.HOLIDAY2010_TREE_GIFT_REQUEST, {}, param1.uid);
        }//end

        public boolean  sendTrainFeed (String param1 ,String param2 ,String param3 )
        {
            _loc_4 =Global.gameSettings().getItemByName(param1 );
            if (Global.gameSettings().getItemByName(param1))
            {
                return this.sendStreamPublish(_loc_4.feed, {cityname:param2}, Global.player.uid, {bucket:param3});
            }
            return false;
        }//end

        public boolean  sendZooFeed (String param1 ,String param2 )
        {
            _loc_3 =Global.gameSettings().getItemByName(param1 );
            if (_loc_3)
            {
                return this.sendStreamPublish(_loc_3.feed, {cityname:param2}, Global.player.uid, {enclosure:param1});
            }
            return false;
        }//end

        public boolean  sendFlowerRequestFeed (String param1 ,String param2 )
        {
            _loc_3 =Global.gameSettings().getItemByName(param1 );
            if (_loc_3)
            {
                return this.sendGardenFeed(_loc_3.feed, _loc_3.gardenType, _loc_3.feedImageIndex, param2);
            }
            return false;
        }//end

        public boolean  sendGardenSeedFeed (String param1 ,int param2 ,String param3 )
        {
            return this.sendGardenFeed(ViralType.SEND_GARDEN_SEED, param1, param2, param3);
        }//end

        public boolean  sendGardenFeed (String param1 ,String param2 ,double param3 ,String param4 )
        {
            String _loc_5 =null ;
            if (param2)
            {
                _loc_5 = ZLoc.t("Feeds", "garden_" + param2);
                return this.sendStreamPublish(param1, {gardenName:_loc_5, cityname:param4, _feedImage:param3}, Global.player.uid, {gardenType:param2});
            }
            return false;
        }//end

        public boolean  sendWonderBonusCompletionFeed (String param1 ,Object param2 )
        {
            return this.sendStreamPublish(param1, param2, Global.player.uid);
        }//end

        public boolean  sendTicketFeed (String param1 )
        {
            return this.sendStreamPublish(param1, {}, Global.player.uid);
        }//end

        public boolean  sendBusinessOpeningFeed (Player param1 ,String param2 ,String param3 ,String param4 )
        {
            StatsManager.count("Zyngage", "feed", ViralType.BUSINESS_OPENING, param3);
            return this.sendStreamPublish(ViralType.BUSINESS_OPENING, {cityName:param2, businessId:param3, businessName:param4}, param1.uid);
        }//end

        public boolean  sendHarvestBasedBusinessMasteryFeed (Player param1 ,String param2 ,String param3 ,String param4 ,String param5 ,String param6 )
        {
            StatsManager.count("Zyngage", "feed", ViralType.HARVEST_BUSINESS_MASTERY, param6);
            return this.sendStreamPublish(ViralType.HARVEST_BUSINESS_MASTERY, {cityName:param2, businessId:param3, businessType:param4, rewardName:param5}, param1.uid);
        }//end

        public boolean  sendConsumableFeed (String param1 ,int param2 ,String param3 )
        {
            _loc_4 =Global.gameSettings().getItemByName(param1 );
            if (Global.gameSettings().getItemByName(param1))
            {
                return this.sendStreamPublish(_loc_4.feed, {building:_loc_4.localizedName}, Global.player.uid, {objId:param2, objectType:param3});
            }
            return false;
        }//end

        public boolean  sendWonderOpeningFeed (String param1 ,String param2 )
        {
            Object _loc_3 ={subcategory param2 +"_complete"};
            _loc_4 = ViralType.WONDER_OPENING+"_"+param2;
            return this.sendStreamPublish(_loc_4, {wonderName:param1, _ontology:_loc_3}, Global.player.uid);
        }//end

        public boolean  sendUniversityLogoFeed (Player param1 ,String param2 ,String param3 ,String param4 )
        {
            _loc_5 = ViralType.UNIVERSITY_CUSTOM;
            return this.sendStreamPublish(_loc_5, {itemName:param2, logoName:param4, _feedImage:param3}, Global.player.uid);
        }//end

        public boolean  sendAutoConsumableFeed (String param1 ,int param2 ,String param3 )
        {
            _loc_4 =Global.gameSettings().getItemByName(param1 );
            if (Global.gameSettings().getItemByName(param1))
            {
                return this.sendFeedorAutoPublish(_loc_4.feed, param1, {building:_loc_4.localizedName}, Global.player.uid, {objId:param2, objectType:param3});
            }
            return false;
        }//end

        public boolean  sendRequestSignatureFeed (String param1 ,String param2 ,String param3 )
        {
            return this.sendStreamPublish(ViralType.REQUEST_SIGNATURE, {businessFriendlyName:param1, newBusinessName:param2}, Global.player.uid, {invKey:param3});
        }//end

        public boolean  sendRollCallCollectReminder (String param1 ,MapResource param2 )
        {
            return this.sendAutoStreamPublish(ViralType.ROLL_CALL_COLLECTION_REMINDER, {title:param2.getCrewPositionName(param1), buildingName:param2.getItemFriendlyName()}, param1, {action:"collectionReminder", bid:param2.getId()});
        }//end

        public boolean  sendRollCallCheckInFeed (String param1 ,Object param2 ,String param3 ,Function param4 =null )
        {
            _loc_5 =Global.player.getFriendFirstName(param1 );
            if (!Global.player.getFriendFirstName(param1))
            {
                _loc_5 = " ";
            }
            return this.sendAutoStreamPublish(ViralType.ROLL_CALL_CHECKIN, {cityname:Global.player.cityName, recipient:_loc_5}, param1, param2, param3, true, param4);
        }//end

        public boolean  sendRollCallNotifyCheckInFeed (String param1 ,MapResource param2 )
        {
            return this.sendStreamPublish(ViralType.ROLL_CALL_NOTIFY_CHECKIN, {owner:Global.player.getFriendFirstName(param1), cityname:Global.player.getFriendCityName(param1), buildingName:param2.getItemFriendlyName()}, param1, {action:"notifyCheckIn", bid:param2.getId(), ownerId:param1});
        }//end

        public boolean  vdaySendCardThankYou (String param1 )
        {
            return this.sendStreamPublish(ViralType.VDAY2011_CARD_THANK_YOU, {}, param1);
        }//end

        public boolean  vdayBragAboutAdmirers ()
        {
            return this.sendStreamPublish(ViralType.VDAY2011_ADMIRER_BRAG, {}, Global.player.uid);
        }//end

        public boolean  vdayBragAboutAchievements ()
        {
            return this.sendStreamPublish(ViralType.VDAY2011_ACHIEVEMENTS_BRAG, {}, Global.player.uid);
        }//end

        public boolean  visitorUISendNameFeed ()
        {
            return this.sendStreamPublish(ViralType.VISITORUI, {cityname:Global.player.cityName}, Global.player.uid);
        }//end

        public boolean  sendGetHunterResource (String param1 )
        {
            _loc_2 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_AUTO_DONUT );
            _loc_3 = _loc_2==ExperimentDefinitions.AUTO_DONUT_DEFAULT_ON ;
            _loc_4 = param1+"_getResource";
            return this.sendFeedorAutoPublish(_loc_4, _loc_4, {cityname:Global.player.cityName}, Global.player.uid, null, null, true, null, _loc_3);
        }//end

        public boolean  sendPreyCapture (String param1 ,int param2 ,String param3 )
        {
            boolean _loc_4 =false ;
            String _loc_5 =null ;
            _loc_6 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_AUTO_DONUT );
            _loc_7 = param1+"_capture_"+param2 ;
            _loc_8 = param1+"_getResource";
            _loc_4 = _loc_6 == ExperimentDefinitions.AUTO_DONUT_DEFAULT_ON;
            return this.sendFeedorAutoPublish(_loc_7, _loc_8, {cityname:Global.player.cityName, capturer:param3}, Global.player.uid, null, null, true, null, _loc_4);
        }//end

        public boolean  sendHotelCheckInFeed (double param1 ,String param2 ,String param3 ,String param4 ,String param5 ,Function param6 =null )
        {
            return this.sendStreamPublish(ViralType.HOTEL_CHECKIN + param2, {hotelName:param3}, Global.player.uid, {hotelId:param1, ownerID:param4, guestID:param5}, null, param6);
        }//end

        public boolean  sendHotelVIPFeed (double param1 ,String param2 ,String param3 )
        {
            return this.sendStreamPublish(ViralType.HOTEL_GRANTVIP, {}, param3, {hotelId:param1, ownerID:param2, guestID:param3});
        }//end

        public boolean  sendHotelThankYouFeed (String param1 )
        {
            return this.sendStreamPublish(ViralType.HOTEL_THANKYOU, {cityname:Global.player.cityName}, param1, {});
        }//end

        public boolean  sendHotelVIPRequest (double param1 )
        {
            FrameManager.showTray("hotel.php?action=requestVIP&hid=" + param1 + "&uid=" + Global.world.ownerId);
            return true;
        }//end

        public boolean  sendHotelGuestCheckInFeed (String param1 )
        {
            return this.sendAutoStreamPublish(ViralType.HOTEL_GUESTCHECKIN, {guestMessage:param1}, Global.world.ownerId);
        }//end

        protected void  sendItemRequest (String param1 ,String param2 )
        {
            _loc_3 = param1"request.php?itemName="+;
            if (param2)
            {
                _loc_3 = _loc_3 + ("&requestType=" + param2);
            }
            FrameManager.showTray(_loc_3);
            return;
        }//end

        public void  sendGift (String param1 )
        {
            return;
        }//end

        public boolean  sendEnergyRequest ()
        {
            if (this.canPost(ViralType.ENERGY_REQUEST))
            {
                this.sendItemRequest("energy_3", "energy_request");
                this.postViral(ViralType.ENERGY_REQUEST);
                return true;
            }
            return false;
        }//end

        public boolean  sendCustomRequest (String param1 ,String param2 )
        {
            this.sendItemRequest(param1, param2);
            return true;
        }//end

        public boolean  sendMunicipalMaterialRequest (Player param1 ,String param2 ,Item param3 )
        {
            return this.sendCustomMaterialRequest("municipal_", param1, param2, param3);
        }//end

        public boolean  sendCustomMaterialRequest (String param1 ,Player param2 ,String param3 ,Item param4 )
        {
            _loc_5 = param1+param4.name ;
            if (this.canPost(_loc_5))
            {
                this.sendItemRequest(param4.name, _loc_5);
                this.postViral(_loc_5);
                return true;
            }
            return false;
        }//end

        public boolean  canSendRollCallCheckInFBMessage (String param1 )
        {
            _loc_2 =Global.gameSettings().getInt("fbRollCallMessageCooldown")*3600;
            _loc_3 =Global.player.getLastActivationTime("rollCall"+param1 );
            _loc_4 = int(GlobalEngine.getTimer()/1000);
            return uint(GlobalEngine.getTimer() / 1000) > _loc_3 + _loc_2;
        }//end

        public void  sendRollCallCheckFBMessage (String param1 ,int param2 ,String param3 =null )
        {
            _loc_4 = int(GlobalEngine.getTimer()/1000);
            Global.player.setLastActivationTime("rollCall" + param1, _loc_4);
            GameTransactionManager.addTransaction(new TSendRollCallFacebookMessage(param1, param2, param3), true);
            _loc_5 =Global.world.getObjectById(param2 )as MechanicMapResource ;
            if (Global.world.getObjectById(param2) as MechanicMapResource)
            {
                StatsManager.social("fb_chat", param1, "roll_call", _loc_5.getItemName(), _loc_5.getCrewPositionName(param1));
            }
            return;
        }//end

        public void  onRequestSent (String param1 ,String param2 ,Object param3 ,Array param4 )
        {
            int _loc_6 =0;
            RequestType _loc_7 =null ;
            ViralRequest _loc_5 =null ;
            if (param3 && param3.hasOwnProperty("requestId"))
            {
                _loc_6 = param3.get("requestId");
                if (this.m_activeRequests.get(_loc_6))
                {
                    _loc_5 = this.m_activeRequests.get(_loc_6);
                    delete this.m_activeRequests.get(_loc_6);
                }
            }
            else
            {
                _loc_7 = RequestType.getType(param2);
                if (_loc_7)
                {
                    _loc_5 = RequestType.createRequest(_loc_7, param4, param3);
                }
                else
                {
                    ErrorManager.addError("onRequestSent called for unsupported request type: " + param2);
                }
            }
            if (_loc_5)
            {
                _loc_5.onRequestSent(param1, param4);
            }
            return;
        }//end

        public void  sendRequest (RequestType param1 ,Array param2 ,Object param3 ,Function param4 =null ,Object param5 =null )
        {
            _loc_6 = RequestType.createRequest(param1,param2,param3,param4,param5);
            if (RequestType.createRequest(param1, param2, param3, param4, param5).canSend())
            {
                _loc_6.send(this.m_nextRequestId);
                this.m_activeRequests.put(this.m_nextRequestId,  _loc_6);
                this.m_nextRequestId++;
            }
            return;
        }//end

        public boolean  canSendRequest (RequestType param1 ,Array param2 ,Object param3 )
        {
            _loc_4 = RequestType.createRequest(param1,param2,param3);
            return RequestType.createRequest(param1, param2, param3).canSend();
        }//end

        public void  showStreamExtendedPermissions ()
        {
            try
            {
                if (ExternalInterface.available)
                {
                    ExternalInterface.call("showStreamExtendedPermissions");
                }
            }
            catch (err:Error)
            {
                ErrorManager.addError("ExternalInterface exception: " + err.message);
            }
            return;
        }//end

        public void  checkExtendedPermissions (Object param1 ,boolean param2 =false )
        {
            if (param1 && param1.hasOwnProperty("publish_actions") && param1.hasOwnProperty("user_games_activity") && param1.hasOwnProperty("friends_games_activity"))
            {
                if (!parseInt(param1.get("publish_actions")) || !parseInt(param1.get("user_games_activity")) || !parseInt(param1.get("friends_games_activity")))
                {
                    Global.player.hasExtendedPermissions = false;
                }
                else
                {
                    Global.player.hasExtendedPermissions = true;
                    Global.hud.removePermsComponent();
                    if (param2)
                    {
                        StatsManager.count("new_perms_granted");
                    }
                }
            }
            return;
        }//end

        public void  extendedPermissionsDialogClosed ()
        {
            GameTransactionManager.addTransaction(new TCheckPermissions(), true);
            return;
        }//end

        public static void  resetFreezeTimer (String param1)
        {
            m_FreezeTimerStart = GlobalEngine.getTimer();
            m_customFreezeText = param1;
            return;
        }//end

        private static void  resetThawTimer ()
        {
            m_ThawTimerStart = GlobalEngine.getTimer();
            return;
        }//end

        public static void  doViralManagerThawCheck ()
        {
            double _loc_3 =0;
            double _loc_4 =0;
            if (m_ThawTimerStart == -1 && m_FreezeTimerStart == -1)
            {
                return;
            }
            boolean _loc_1 =false ;
            if (m_FreezeTimerStart != -1)
            {
                _loc_3 = GlobalEngine.getTimer() - m_FreezeTimerStart;
                if (_loc_3 > m_viralFreezeStart)
                {
                    _loc_1 = true;
                }
            }
            if (_loc_1)
            {
                UI.freezeScreen(m_showLoading, false, m_customFreezeText);
                m_FreezeTimerStart = -1;
                resetThawTimer();
                return;
            }
            boolean _loc_2 =false ;
            if (m_ThawTimerStart != -1)
            {
                _loc_4 = GlobalEngine.getTimer() - m_ThawTimerStart;
                if (_loc_4 > m_viralFreezeTimeout)
                {
                    _loc_2 = true;
                }
            }
            if (_loc_2 == true)
            {
                doUIThaw();
            }
            return;
        }//end

        public static void  doUIThaw ()
        {
            if (m_ThawTimerStart == -1)
            {
                return;
            }
            UI.thawScreen();
            m_customFreezeText = null;
            m_FreezeTimerStart = -1;
            m_ThawTimerStart = -1;
            return;
        }//end

    }



