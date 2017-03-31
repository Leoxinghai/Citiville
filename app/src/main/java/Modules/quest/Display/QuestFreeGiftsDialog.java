package Modules.quest.Display;

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
import Classes.Managers.*;
import Classes.sim.*;
import Classes.util.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Modules.guide.ui.*;
import Modules.stats.experiments.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.event.*;

    public class QuestFreeGiftsDialog extends InviteFriendsGuideDialog
    {

        public  QuestFreeGiftsDialog (Object param1 )
        {
            super(ZLoc.t("Quest", param1.message), ZLoc.t("Quest", param1.title), GenericDialogView.TYPE_SENDGIFTS, null, true, param1.npcEpilogueUrl, "", null);
            return;
        }//end

         protected void  onSkip (Object param1)
        {
            IdleDialogManager.markDialogAsSkipped(IdleDialogManager.IDLE_SENDGIFT);
            MiniQuestManager.giftsSkipped = true;
            super.onSkip(param1);
            return;
        }//end

        public void  onSkipClicked (Event event )
        {
            return;
        }//end

         public void  close ()
        {
            Global.player.setAllowQuests(true);
            Global.questManager.refreshActiveIconQuests();
            super.close();
            return;
        }//end

         protected void  onPanelClick (AWEvent event )
        {
            RecentlyPlayedMFSManager _loc_3 =null ;
            _loc_2 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_RECENTLY_PLAYED_MFS );
            if (_loc_2 == 2 || _loc_2 == 3)
            {
                _loc_3 =(RecentlyPlayedMFSManager) Global.flashMFSManager.getManager(FlashMFSManager.TYPE_RECENTLY_PLAYED_MFS);
                if (_loc_3)
                {
                    _loc_3.giftingSource = "train";
                    _loc_3.displayMFS();
                }
            }
            else
            {
                FrameManager.navigateTo("gifts.php?ref=free_gift_icon");
            }
            this.close();
            dispatchEvent(new Event(Event.CLOSE, true));
            return;
        }//end

         protected JPanel  createButtonPanel ()
        {
            CustomButton _loc_2 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            if (m_SkipCallback != null)
            {
                _loc_2 = new CustomButton(ZLoc.t("Dialogs", "Skip"), null, "GreyButtonUI");
                _loc_2.setPreferredWidth(100);
                _loc_2.setMinimumWidth(100);
                _loc_2.setMaximumWidth(100);
                _loc_2.addActionListener(this.onSkip, 0, true);
                _loc_1.append(_loc_2);
                _loc_1.append(ASwingHelper.horizontalStrut(5));
            }
            m_button = new CustomButton(ZLoc.t("Dialogs", "SendFreeGifts"), null, "GreenButtonUI");
            m_button.addActionListener(this.onPanelClick, 0, true);
            _loc_1.append(m_button);
            return _loc_1;
        }//end

    }



