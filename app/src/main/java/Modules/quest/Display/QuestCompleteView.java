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

import Display.aswingui.*;
import Modules.saga.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.event.*;

    public class QuestCompleteView extends QuestPopupView
    {
        private boolean m_isSagaActComplete =false ;
        private static  int BUTTON_OFFSET =20;
        private static  int FRAME_OFFSET =9;

        public  QuestCompleteView (Dictionary param1 ,Object param2 )
        {
            _loc_3 = SagaManager.instance.getSagaNameByQuestName(param2.name);
            _loc_4 = SagaManager.instance.getActNameByQuestName(param2.name);
            if (_loc_3 && _loc_4)
            {
                this.m_isSagaActComplete = SagaManager.instance.isActComplete(_loc_3, _loc_4);
            }
            super(param1, param2);
            return;
        }//end

         protected void  init ()
        {
            initCommonAssets();
            m_bgAsset = m_assetDict.get("imageBG_0");
            m_headerTextKey = "_dialog_finish";
            m_subTitleKey = "complete_text";
            m_bottomSpeechBubbleOffset = 20;
            this.makeCenterPanel();
            this.makeBackground();
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeBackground ()
        {
            MarginBackground _loc_1 =new MarginBackground(m_bgAsset ,new Insets(0,0,BUTTON_OFFSET ));
            this.setBackgroundDecorator(_loc_1);
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            m_interiorHolder = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            m_outerInteriorHolder = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            m_outerInteriorHolder.setBorder(new EmptyBorder(null, new Insets(0, 6, 0, 3)));
            makeHeader();
            makeInfoPanel();
            this.makeButton();
            ASwingHelper.prepare(m_interiorHolder);
            this.append(m_interiorHolder);
            ASwingHelper.prepare(this);
            return;
        }//end

         protected void  makeButton ()
        {
            JPanel _loc_1 =new JPanel(new FlowLayout(FlowLayout.CENTER ));
            _loc_2 = ZLoc.t("Quest",m_data.name +"_dialog_share_btn_text");
            if (_loc_2.indexOf("Cannot find string") == 0 || _loc_2.length <= 0)
            {
                _loc_2 = ZLoc.t("Dialogs", "ShareExperience");
            }
            if (this.m_isSagaActComplete)
            {
                _loc_2 = ZLoc.t("Dialogs", "Okay");
            }
            CustomButton _loc_3 =new CustomButton(_loc_2 ,null ,"BigGreenButtonUI");
            _loc_3.addActionListener(this.shareAndClose, 0, true);
            _loc_1.appendAll(_loc_3);
            ASwingHelper.prepare(_loc_1);
            _loc_4 = _loc_1.getHeight ();
            m_interiorHolder.append(ASwingHelper.verticalStrut(-_loc_4 + BUTTON_OFFSET + FRAME_OFFSET));
            m_interiorHolder.append(_loc_1);
            return;
        }//end

        protected void  shareAndClose (AWEvent event )
        {
            if (!this.m_isSagaActComplete)
            {
                countDialogViewAction("SHARE");
                Global.world.viralMgr.sendQuestCompleteFeed(Global.player, m_data.name, m_questTitle);
            }
            this.close();
            return;
        }//end

         public void  close ()
        {
            if (this.m_isSagaActComplete)
            {
                SagaManager.instance.doRewardsForActOnQuestComplete(m_data.name);
            }
            super.close();
            return;
        }//end

    }



