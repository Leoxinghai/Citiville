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

import Classes.actions.*;
import Classes.util.*;
import Classes.virals.*;
import Display.*;
import Display.DialogUI.*;
import Engine.Events.*;
import Engine.Managers.*;
import Events.*;
import GameMode.*;
import Modules.stats.types.*;
import Transactions.*;
//import flash.events.*;
//import flash.geom.*;

    public class HolidayTree extends Decoration
    {
        private  String HOLIDAYTREE ="holidaytree";
        private boolean m_isHarvesting =false ;
        public int m_mostPresents =0;
        private int m_lastPresents =0;
        public static  String PRE_HOLIDAY ="pre";
        public static  String HOLIDAY ="holiday";
        public static  String HOLIDAY_WARNING ="holiday_warning";
        public static  String POST_HOLIDAY ="post";
        public static  String STATE_LARGETREE_LARGEGIFTS ="largetree_largegifts";
        public static  String STATE_LARGETREE_MEDIUMGIFTS ="largetree_mediumgifts";
        public static  String STATE_LARGETREE_SMALLGIFTS ="largetree_smallgifts";
        public static  String STATE_LARGETREE_LASTGIFT ="largetree_lastgift";
        public static  String STATE_LARGETREE_NOGIFTS ="largetree_nogifts";
        public static  int NOGIFTS =-1;
        public static  int LASTGIFT =1;
        public static  int SMALLGIFTS =2;
        public static  int MEDIUMGIFTS =3;
        public static  int LARGEGIFTS =4;
        public static  int LARGETREE =3;
        public static boolean m_PlacedFromInventory =false ;
        public static int m_transactionMostPresents =0;
        public static int m_transactionLastPresents =0;
        public static HolidayTree m_Instance =null ;

        public  HolidayTree (String param1)
        {
            super(param1);
            m_typeName = this.HOLIDAYTREE;
            m_isHighlightable = true;
            m_Instance = this;
            return;
        }//end

         public void  loadObject (Object param1 )
        {
            super.loadObject(param1);
            this.m_mostPresents = int(param1.maxPresents);
            return;
        }//end

         protected void  statsInit ()
        {
            super.statsInit();
            m_statsName = "holidaytree";
            return;
        }//end

         protected ItemImageInstance  getCurrentImage ()
        {
            _loc_1 = this.getState();
            _loc_2 = m_item.getCachedImage(_loc_1,null,m_direction);
            if (_loc_2 == null)
            {
                m_item.addEventListener(LoaderEvent.LOADED, this.onContractImageLoaded, false, 0, true);
            }
            return _loc_2;
        }//end

         public boolean  isHighlightable ()
        {
            return true;
        }//end

        protected void  onContractImageLoaded (LoaderEvent event )
        {
            event.target.removeEventListener(LoaderEvent.LOADED, this.onContractImageLoaded);
            reloadImage();
            return;
        }//end

        public String  getTimeState ()
        {
            if (TimestampEvents.checkCurrentTimeBeforeTime(TimestampEvents.TIME_NONE))
            {
                return PRE_HOLIDAY;
            }
            if (TimestampEvents.checkCurrentTimeBetweenRange(TimestampEvents.TIME_NONE, TimestampEvents.TIME_CHRISTMAS2010))
            {
                return PRE_HOLIDAY;
            }
            if (TimestampEvents.checkCurrentTimeBetweenRange(TimestampEvents.TIME_CHRISTMAS2010, TimestampEvents.TIME_CHRISTMAS2010_WARNING))
            {
                return HOLIDAY;
            }
            if (TimestampEvents.checkCurrentTimeBetweenRange(TimestampEvents.TIME_CHRISTMAS2010_WARNING, TimestampEvents.TIME_CHRISTMAS2010_END))
            {
                return HOLIDAY_WARNING;
            }
            if (TimestampEvents.checkCurrentTimeAfterOrDuringTime(TimestampEvents.TIME_CHRISTMAS2010_END))
            {
                return POST_HOLIDAY;
            }
            return POST_HOLIDAY;
        }//end

        private int  GetCurrentPresentLevel ()
        {
            _loc_1 =Global.player.inventory.getItemCountByName(Item.HOLIDAY2010_PRESENT_ITEM );
            if (_loc_1 == 0)
            {
                return NOGIFTS;
            }
            if (_loc_1 == 1)
            {
                if (this.getTimeState() == PRE_HOLIDAY)
                {
                    return SMALLGIFTS;
                }
                return LASTGIFT;
            }
            if (_loc_1 <= Global.gameSettings().getInt("holiday2010_MediumPresents"))
            {
                return SMALLGIFTS;
            }
            if (_loc_1 <= Global.gameSettings().getInt("holiday2010_HighPresents"))
            {
                return MEDIUMGIFTS;
            }
            return LARGEGIFTS;
        }//end

        private String  getLargeTreeState ()
        {
            _loc_1 = this.GetCurrentPresentLevel();
            switch(_loc_1)
            {
                case NOGIFTS:
                {
                    return STATE_LARGETREE_NOGIFTS;
                }
                case LASTGIFT:
                {
                    return STATE_LARGETREE_LASTGIFT;
                }
                case SMALLGIFTS:
                {
                    return STATE_LARGETREE_SMALLGIFTS;
                }
                case MEDIUMGIFTS:
                {
                    return STATE_LARGETREE_MEDIUMGIFTS;
                }
                default:
                {
                    break;
                }
            }
            return STATE_LARGETREE_LARGEGIFTS;
        }//end

         public String  getState ()
        {
            return this.getLargeTreeState();
        }//end

         public String  getToolTipStatus ()
        {
            int _loc_2 =0;
            String _loc_1 =null ;
            if (!Global.isVisiting() && !Global.world.isEditMode)
            {
                _loc_2 = Global.player.inventory.getItemCountByName(Item.HOLIDAY2010_PRESENT_ITEM);
                switch(this.getTimeState())
                {
                    case PRE_HOLIDAY:
                    {
                        if (_loc_2 < Global.gameSettings().getInt("holiday2010_MaxPresents"))
                        {
                            _loc_1 = ZLoc.t("Main", "PreTreeAction", {count:_loc_2});
                        }
                        else
                        {
                            _loc_1 = ZLoc.t("Main", "PreTreeActionFull", {count:_loc_2});
                        }
                        break;
                    }
                    case HOLIDAY:
                    case HOLIDAY_WARNING:
                    case POST_HOLIDAY:
                    {
                        if (_loc_2 > 0)
                        {
                            _loc_1 = ZLoc.t("Main", "DuringTreeAction", {count:_loc_2});
                        }
                        else
                        {
                            _loc_1 = ZLoc.t("Main", "PostTreeAction");
                        }
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return _loc_1;
        }//end

         public void  onPlayAction ()
        {
            super.onPlayAction();
            _loc_1 = Global.player.inventory.getItemCountByName(Item.HOLIDAY2010_PRESENT_ITEM);
            if (Global.isVisiting())
            {
                return;
            }
            if (!hasValidId())
            {
                showObjectBusy();
                return;
            }
            m_actionMode = PLAY_ACTION;
            switch(this.getTimeState())
            {
                case PRE_HOLIDAY:
                {
                    if (_loc_1 < Global.gameSettings().getInt("holiday2010_MaxPresents"))
                    {
                        HolidayTree.doPresentViral();
                    }
                    else
                    {
                        return;
                    }
                    break;
                }
                case HOLIDAY:
                case HOLIDAY_WARNING:
                case POST_HOLIDAY:
                {
                    if (_loc_1 > 0)
                    {
                        m_Instance.doPresentUnwrap();
                    }
                    else
                    {
                        HolidayTree.doSpecialMessage();
                    }
                    break;
                }
                default:
                {
                    break;
                }
            }
            return;
        }//end

         public String  getGameModeToolTipAction ()
        {
            _loc_1 = super.getGameModeToolTipAction();
            _loc_2 = Global.world.getTopGameMode();
            if (_loc_2 instanceof GMEditSell)
            {
                if (this.getTimeState() != POST_HOLIDAY)
                {
                    return null;
                }
            }
            return _loc_1;
        }//end

         public void  onMenuClick (MouseEvent event )
        {
            ContextMenuItem _loc_2 =null ;
            if (event.target instanceof ContextMenuItem)
            {
                _loc_2 =(ContextMenuItem) event.target;
                switch(_loc_2.action)
                {
                    case SELL_OBJECT:
                    {
                        if (this.getTimeState() == POST_HOLIDAY)
                        {
                            this.sell();
                        }
                        else
                        {
                            return;
                        }
                        break;
                    }
                    default:
                    {
                        super.onMenuClick(event);
                        break;
                        break;
                    }
                }
            }
            return;
        }//end

        public String  getHarvestingText ()
        {
            return ZLoc.t("Main", "Unwrapping");
        }//end

         public String  getActionText ()
        {
            return this.getHarvestingText();
        }//end

         protected Array  makeDoobers (double param1 =1)
        {
            return Global.player.processRandomModifiers(harvestingDefinition, this, true, m_secureRands);
        }//end

         public boolean  isVisitorInteractable ()
        {
            return false;
        }//end

        public void  doPresentUnwrap ()
        {
            if (this.m_isHarvesting)
            {
                return;
            }
            this.m_isHarvesting = true;
            _loc_1 = this.GetCurrentPresentLevel();
            Sounds.play("unwrap");
            if (_loc_1 == LASTGIFT)
            {
                HolidayTree.instance.trackAction("unwrap_last_present");
            }
            else
            {
                HolidayTree.instance.trackAction("unwrap_present");
            }
            m_actionQueue.removeAllStates();
            m_actionQueue.addActions(new ActionProgressBar(null, this, ZLoc.t("Main", "Unwrapping"), 3));
            return;
        }//end

         public Function  getProgressBarStartFunction ()
        {
            return boolean  ()
            {
                return true;
            }//end
            ;
        }//end

         public Function  getProgressBarCancelFunction ()
        {
            return void  ()
            {
                m_isHarvesting = false;
                return;
            }//end
            ;
        }//end

         public Function  getProgressBarEndFunction ()
        {
            return void  ()
            {
                _loc_1 = undefined;
                m_isHarvesting = false;
                m_doobersArray = Global.player.processRandomModifiers(harvestingDefinition, HolidayTree.instance, true, m_secureRands, "miniboss");
                for(int i0 = 0; i0 < m_doobersArray.size(); i0++)
                {
                		_loc_1 = m_doobersArray.get(i0);

                    StatsManager.sample(100, StatsKingdomType.GAME_ACTIONS, "event", "reward", _loc_1.get(0));
                }
                Global.world.dooberManager.createBatchDoobers(m_doobersArray, harvestingDefinition, m_position.x, m_position.y);
                GameTransactionManager.addTransaction(new THolidayUnwrapPresents(HolidayTree.instance));
                Global.player.inventory.removeItems(Item.HOLIDAY2010_PRESENT_ITEM, 1);
                m_Instance.reloadImage();
                return;
            }//end
            ;
        }//end

         public boolean  deferProgressBarToNPC ()
        {
            return false;
        }//end

         public Point  getProgressBarOffset ()
        {
            if (this.content)
            {
                return new Point(0, this.content.height >> 1);
            }
            return new Point(0, 0);
        }//end

         public void  sell ()
        {
            _loc_1 = this.GetCurrentPresentLevel();
            if (_loc_1 == NOGIFTS)
            {
                UI.displayMessage(ZLoc.t("Main", "HolidaySellTreeWarning", {coins:getSellPrice()}), GenericPopup.TYPE_YESNO, sellConfirmationHandler);
                return;
            }
            UI.displayMessage(ZLoc.t("Main", "HolidaySellTreePresents", {coins:getSellPrice()}), GenericPopup.TYPE_YESNO, sellConfirmationHandler);
            return;
        }//end

        public static HolidayTree  instance ()
        {
            if (m_Instance == null)
            {
                m_Instance = new HolidayTree(Item.HOLIDAY2010_TREE_ITEM);
            }
            return m_Instance;
        }//end

        public static void  doPresentViral ()
        {
            _loc_1 = ZLoc.t("Dialogs","Holiday2010_giftrequest_message");
            String _loc_2 ="Holiday2010_giftrequest";
            String _loc_3 ="assets/missions/gift.png";
            _loc_4 = GenericDialogView.TYPE_ASKFRIEND;
            HolidayTree.instance.trackAction("present_request");
            UI.displayViralShareMessage(ViralType.HOLIDAY2010_TREE_GIFT_REQUEST, _loc_1, _loc_4, onViralDialogClosed, _loc_2, true, _loc_3, _loc_2);
            return;
        }//end

        private static void  onViralDialogClosed (GenericPopupEvent event )
        {
            boolean _loc_2 =false ;
            String _loc_3 =null ;
            event.target.removeEventListener(GenericPopupEvent.SELECTED, onViralDialogClosed);
            if (event.button != GenericPopup.YES)
            {
                return;
            }
            _loc_2 = Global.world.viralMgr.sendHoliday2010PresentRequest(Global.player);
            if (_loc_2 == false)
            {
                _loc_3 = ZLoc.t("Dialogs", "Holiday2010_alreadyAsked_message");
                UI.displayMessage(_loc_3, GenericDialogView.TYPE_OK, null, "holidayTreeAlreadyAsked", false, null, "Holiday2010_alreadyAsked");
            }
            return;
        }//end

        public static void  showUniquePresentDialog (String param1 )
        {
            _loc_2 = ZLoc.t("Items",param1+"_friendlyName");
            _loc_3 = ZLoc.t("Dialogs","Holiday2010_unwrap_text");
            String _loc_4 ="OkButton";
            String _loc_5 ="Holiday2010_unwrap";
            String _loc_6 ="";
            _loc_7 = Global.gameSettings().getItemByName(param1);
            if (Global.gameSettings().getItemByName(param1) == null)
            {
                _loc_6 = "assets/dialogs/blue_present.png";
            }
            else
            {
                _loc_6 = _loc_7.unwrapIcon;
            }
            UI.displayMessageWithBold(_loc_3, _loc_2, GenericPopup.TYPE_OK, null, _loc_5, true, _loc_6, _loc_5);
            return;
        }//end

        public static void  doHolidayStartDialog ()
        {
            if (m_Instance == null)
            {
                return;
            }
            HolidayTree.instance.trackAction("holiday_start_event");
            _loc_1 = ZLoc.t("Dialogs","Holiday2010_holidaystart_message");
            String _loc_2 ="OkButton";
            String _loc_3 ="Holiday2010_holidaystart";
            String _loc_4 ="assets/dialogs/holidaytree/treepresents.png";
            UI.displayMessage(_loc_1, GenericPopup.TYPE_OK, null, _loc_3, true, _loc_4, _loc_3);
            return;
        }//end

        public static void  doSpecialMessage ()
        {
            HolidayTree.instance.trackAction("special_message");
            _loc_1 = ZLoc.t("Dialogs","HappyHolidays2010_message");
            String _loc_2 ="OkButton";
            String _loc_3 ="HappyHolidays2010";
            String _loc_4 ="assets/dialogs/feed_viral_holiday_santasworkshop_large.png";
            UI.displayMessage(_loc_1, GenericPopup.TYPE_OK, null, _loc_3, true, _loc_4, _loc_3);
            return;
        }//end

        public static void  doPresentExpireWarning ()
        {
            HolidayTree.instance.trackAction("presents_expiration_warning");
            _loc_1 = ZLoc.t("Dialogs","Holiday2010_giftswarning_message");
            String _loc_2 ="OkButton";
            String _loc_3 ="Holiday2010_giftswarning";
            String _loc_4 ="assets/missions/gift.png";
            UI.displayMessage(_loc_1, GenericPopup.TYPE_OK, null, _loc_3, true, _loc_4, _loc_3);
            return;
        }//end

        public static void  doPresentExpiredNotice ()
        {
            HolidayTree.instance.trackAction("presents_expired");
            _loc_1 = ZLoc.t("Dialogs","Holiday2010_giftsremoved_message");
            String _loc_2 ="OkButton";
            String _loc_3 ="Holiday2010_giftsremoved";
            String _loc_4 ="assets/missions/gift.png";
            UI.displayMessage(_loc_1, GenericPopup.TYPE_OK, null, _loc_3, true, _loc_4, _loc_3);
            return;
        }//end

        private static void  showPreHolidayTreeReminder ()
        {
            _loc_1 = ZLoc.t("Dialogs","Holiday2010_treeplacereminder_message");
            String _loc_2 ="OkButton";
            _loc_3 = ZLoc.t("Dialogs","Holiday2010_treeplacereminder_title");
            String _loc_4 ="assets/dialogs/feed_placetree.png";
            GenericPictureDialog _loc_5 =new GenericPictureDialog(_loc_1 ,_loc_3 ,GenericDialogView.TYPE_OK ,null ,_loc_3 ,"",true ,0,_loc_2 ,_loc_4 );
            UI.displayPopup(_loc_5);
            return;
        }//end

        private static void  showPreHolidayGiftReminder ()
        {
            _loc_1 = ZLoc.t("Dialogs","Holiday2010_sendgiftreminder_message");
            String _loc_2 ="Holiday2010_sendgiftreminder_button";
            _loc_3 = ZLoc.t("Dialogs","Holiday2010_sendgiftreminder_title");
            String _loc_4 ="assets/dialogs/feed_presents.png";
            GenericPictureDialog _loc_5 =new GenericPictureDialog(_loc_1 ,_loc_3 ,GenericDialogView.TYPE_OK ,onGiftReminderDialogClosed ,_loc_3 ,"",true ,0,_loc_2 ,_loc_4 );
            UI.displayPopup(_loc_5);
            return;
        }//end

        private static void  onGiftReminderDialogClosed (GenericPopupEvent event )
        {
            event.target.removeEventListener(GenericPopupEvent.SELECTED, onGiftReminderDialogClosed);
            if (event.button != GenericPopup.YES)
            {
                return;
            }
            FrameManager.navigateTo("gifts.php?ref=idle_popup");
            return;
        }//end

        private static void  showPreHolidayReminder ()
        {
            _loc_1 = Global.world.getObjectsByClass(HolidayTree);
            if (_loc_1.length == 0)
            {
                showPreHolidayTreeReminder();
            }
            else
            {
                showPreHolidayGiftReminder();
            }
            return;
        }//end

        public static void  doPreHolidayStartupChecks (boolean param1 =true )
        {
            Array _loc_3 =null ;
            if (Global.player.inventory == null)
            {
                return;
            }
            _loc_2 = Global.player.inventory.getItemCountByName(Item.HOLIDAY2010_PRESENT_ITEM);
            if (TimestampEvents.checkCurrentTimeBeforeTime(TimestampEvents.TIME_CHRISTMAS2010_END))
            {
                _loc_3 = Global.world.getObjectsByClass(HolidayTree);
                if (_loc_3.length > 0)
                {
                    GameTransactionManager.addTransaction(new THolidayCheckPresents(HolidayTree.instance));
                }
                if (TimestampEvents.checkCurrentTimeBeforeTime(TimestampEvents.TIME_CHRISTMAS2010))
                {
                    if (param1 !=null)
                    {
                        if (TimestampEvents.hasTimestampEventChanged && Global.player.level >= 2)
                        {
                            showPreHolidayReminder();
                        }
                    }
                }
            }
            return;
        }//end

        private static void  doHolidayStartupChecks ()
        {
            if (Global.player.inventory == null)
            {
                return;
            }
            _loc_1 = Global.player.inventory.getItemCountByName(Item.HOLIDAY2010_PRESENT_ITEM);
            if (TimestampEvents.checkCurrentTimeBetweenRange(TimestampEvents.TIME_CHRISTMAS2010, TimestampEvents.TIME_CHRISTMAS2010_WARNING))
            {
                if (_loc_1 > 0)
                {
                    doHolidayStartDialog();
                }
            }
            return;
        }//end

        private static void  doWarningHolidayStartupChecks ()
        {
            _loc_1 = Global.player.inventory.getItemCountByName(Item.HOLIDAY2010_PRESENT_ITEM);
            if (TimestampEvents.checkCurrentTimeBetweenRange_FirstTimeOnly(TimestampEvents.TIME_CHRISTMAS2010_WARNING, TimestampEvents.TIME_CHRISTMAS2010_END))
            {
                if (_loc_1 > 0)
                {
                    if (doesPlayerHaveTree())
                    {
                        doPresentExpireWarning();
                    }
                }
            }
            return;
        }//end

        public static boolean  doesPlayerHaveTree ()
        {
            if (Global.player.inventory.getItemCountByName(Item.HOLIDAY2010_TREE_ITEM) > 0)
            {
                return true;
            }
            _loc_1 =Global.world.getObjectsByClass(HolidayTree );
            if (_loc_1.length > 0)
            {
                return true;
            }
            return false;
        }//end

        private static boolean  areHolidayQuestsActive ()
        {
            _loc_1 = Global.questManager.isQuestActive("holiday_tree1");
            _loc_2 = Global.questManager.isQuestActive("holiday_tree2");
            _loc_3 = Global.questManager.isQuestActive("holiday_tree3");
            _loc_4 = Global.questManager.isQuestActive("holiday_tree4");
            return _loc_1 || _loc_2 || _loc_3 || _loc_4;
        }//end

        private static void  checkToGiveFreeHolidayTree ()
        {
            int _loc_1 =0;
            if (Global.player.inventory.verifySingletonItem(Item.HOLIDAY2010_TREE_ITEM))
            {
                _loc_1 = Global.player.inventory.getItemCountByName(Item.HOLIDAY2010_PRESENT_ITEM);
                if (_loc_1 > 0)
                {
                    HolidayTree.instance.trackAction("tree_issued");
                    Global.player.inventory.addSingletonItem(Item.HOLIDAY2010_TREE_ITEM, true);
                    GameTransactionManager.addTransaction(new THolidayIssueFreeTree(HolidayTree.instance));
                }
            }
            return;
        }//end

        private static void  forceHolidayQuestWrapUp ()
        {
            Global.questManager.expireSpecificQuest("holiday_tree1");
            Global.questManager.expireSpecificQuest("holiday_tree2");
            Global.questManager.expireSpecificQuest("holiday_tree3");
            Global.questManager.expireSpecificQuest("holiday_tree4");
            GameTransactionManager.addTransaction(new THolidayTreeRemoveAllQuests(HolidayTree.instance));
            checkToGiveFreeHolidayTree();
            return;
        }//end

        public static void  wrapUpHolidayQuests ()
        {
            if (areHolidayQuestsActive())
            {
                forceHolidayQuestWrapUp();
            }
            checkToGiveFreeHolidayTree();
            return;
        }//end

        private static void  doWrapUpHolidayStartUpChecks ()
        {
            if (TimestampEvents.checkCurrentTimeAfterOrDuringTime_FirstTimeOnly(TimestampEvents.TIME_CHRISTMAS2010_END))
            {
                forceHolidayQuestWrapUp();
            }
            return;
        }//end

        public static void  doStartupCheck ()
        {
            if (Global.guide.isActive())
            {
                return;
            }
            doPreHolidayStartupChecks();
            doHolidayStartupChecks();
            doWarningHolidayStartupChecks();
            doWrapUpHolidayStartUpChecks();
            return;
        }//end

    }



