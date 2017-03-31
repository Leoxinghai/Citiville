package Classes.Managers;

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
import Display.FreeGiftMFS.*;
import Display.Toaster.*;
import Engine.Managers.*;
import Modules.dataservices.*;
import Modules.stats.types.*;
import ZLocalization.*;

    public class RecentlyPlayedMFSManager extends BaseMFSManager
    {
        protected String m_chosenGift ;
        protected double m_numSent =0;
        protected Array m_recipients ;
        protected double m_numDummyRecipients =0;
        protected Array m_currentList ;
        protected double m_numCurrentSelected ;
        protected FreeGiftMFSDialog m_dialog ;
        protected double m_initialTotal ;
        protected String m_giftingSource ="after_announce";
        protected double m_batchNum ;
        public static  double RECIPIENTS_PER_VOLLEY =24;
        public static  double NUM_DAYS =14;

        public  RecentlyPlayedMFSManager ()
        {
            this.m_chosenGift = Global.gameSettings().getString("defaultFreeGift", "mysterygift_v1");
            this.m_numSent = 0;
            this.m_numDummyRecipients = 0;
            this.m_batchNum = 0;
            this.m_recipients = new Array();
            return;
        }//end

        public void  onQueryComplete (DataServicesResult param1 )
        {
            String _loc_2 =null ;
            Player _loc_3 =null ;
            String _loc_4 =null ;
            String _loc_5 =null ;
            FlashMFSRecipient _loc_6 =null ;
            if (param1 && param1.isValid() && param1.get() instanceof Array)
            {
                this.m_recipients = new Array();
                for(int i0 = 0; i0 < param1.get().size(); i0++)
                {
                		_loc_2 = param1.get().get(i0);

                    _loc_3 = Global.player.findFriendById(_loc_2);
                    if (_loc_3)
                    {
                        _loc_4 = Global.player.getFriendName(_loc_2);
                        _loc_5 = _loc_3.snUser.picture;
                        _loc_6 = new FlashMFSRecipient(parseInt(_loc_2), _loc_4, _loc_5, true);
                        if (canSendRequest(.get(_loc_6)))
                        {
                            this.m_recipients.push(_loc_6);
                        }
                    }
                }
                if (this.m_recipients.length > 0)
                {
                    this.m_initialTotal = this.m_recipients.length;
                    while (this.m_recipients.length % RECIPIENTS_PER_VOLLEY != 0)
                    {

                        this.m_recipients.push(new FlashMFSRecipient(0, null, null));
                        this.m_numDummyRecipients++;
                    }
                }
            }
            return;
        }//end

         public void  displayMFS ()
        {
            FlashMFSRecipient _loc_1 =null ;
            this.m_numCurrentSelected = 0;
            if (this.numAvailable > 0)
            {
                this.m_recipients.sortOn("name");
                this.m_currentList = this.m_recipients.slice(0, RECIPIENTS_PER_VOLLEY);
                for(int i0 = 0; i0 < this.m_currentList.size(); i0++)
                {
                		_loc_1 = this.m_currentList.get(i0);

                    if (_loc_1.zid != 0 && _loc_1.selected == true)
                    {
                        this.m_numCurrentSelected++;
                    }
                }
                this.m_batchNum++;
                this.m_dialog = new FreeGiftMFSDialog(this.m_chosenGift, this.m_currentList);
                UI.displayPopup(this.m_dialog, true, "RecentlyPlayedMFS", true);
                StatsManager.sample(100, StatsCounterType.RECENTLY_PLAYED_MFS, "main_dialog_impression", this.m_giftingSource, "batch_num_" + this.m_batchNum);
            }
            else
            {
                FrameManager.navigateTo("gifts.php?ref=free_gift_icon");
            }
            return;
        }//end

        public void  onSend ()
        {
            FlashMFSRecipient _loc_1 =null ;
            this.m_dialog.close();
            for(int i0 = 0; i0 < this.m_currentList.size(); i0++)
            {
            		_loc_1 = this.m_currentList.get(i0);

                if (_loc_1.selected == true)
                {
                    this.m_numSent++;
                }
                this.m_recipients.splice(this.m_recipients.indexOf(_loc_1), 1);
            }
            sendRequest(this.m_currentList);
            StatsManager.sample(100, StatsCounterType.RECENTLY_PLAYED_MFS, "send_button", this.m_giftingSource, "batch_num_" + this.m_batchNum);
            this.m_numCurrentSelected = 0;
            if (this.numAvailable > 0)
            {
                this.displayMFS();
            }
            return;
        }//end

        public void  onClose ()
        {
            StatsManager.sample(100, StatsCounterType.RECENTLY_PLAYED_MFS, "close_button", this.m_giftingSource, "batch_num_" + this.m_batchNum);
            return;
        }//end

         protected Object  getRequestData ()
        {
            return {item:this.m_chosenGift};
        }//end

         protected Object  getRequestOntology ()
        {
            return {};
        }//end

         protected void  onRequestSent (boolean param1 ,Array param2 )
        {
            LocalizationObjectToken _loc_4 =null ;
            Toaster _loc_5 =null ;
            _loc_3 = param1==true && param2.length > 0;
            if (_loc_3)
            {
                StatsManager.sample(100, StatsCounterType.RECENTLY_PLAYED_MFS, "request_send_success", this.m_giftingSource);
            }
            else
            {
                StatsManager.sample(100, StatsCounterType.RECENTLY_PLAYED_MFS, "request_send_fail", this.m_giftingSource);
            }
            if (_loc_3 && this.numAvailable < 1)
            {
                _loc_4 = ZLoc.tk("Gifts", "Friend", "", this.numSent);
                _loc_5 = new TickerToaster(ZLoc.t("Dialogs", "recentlyPlayedMFS_toaster", {friendCount:this.numSent, friends:_loc_4}));
                Global.ui.toaster.show(_loc_5);
            }
            return;
        }//end

        public double  numCurrentSelected ()
        {
            return this.m_numCurrentSelected;
        }//end

        public String  chosenGift ()
        {
            return this.m_chosenGift;
        }//end

        public double  numSent ()
        {
            return this.m_numSent;
        }//end

        public double  numAvailable ()
        {
            return this.m_recipients.length - this.m_numDummyRecipients;
        }//end

        public Array  recipients ()
        {
            return this.m_recipients;
        }//end

        public double  numDummyRecipients ()
        {
            return this.m_numDummyRecipients;
        }//end

        public Array  currentList ()
        {
            return this.m_currentList;
        }//end

        public void  notifySelected ()
        {
            this.m_numCurrentSelected++;
            return;
        }//end

        public void  notifyUnselected ()
        {
            this.m_numCurrentSelected--;
            StatsManager.sample(100, StatsCounterType.RECENTLY_PLAYED_MFS, "deselect_friend", this.m_giftingSource);
            if (this.m_numCurrentSelected == 0)
            {
                this.onSend();
            }
            return;
        }//end

        public double  initialTotal ()
        {
            return this.m_initialTotal;
        }//end

        public void  giftingSource (String param1 )
        {
            this.m_giftingSource = param1;
            return;
        }//end

    }


