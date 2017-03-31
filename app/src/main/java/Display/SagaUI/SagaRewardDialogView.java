package Display.SagaUI;

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
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Modules.saga.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class SagaRewardDialogView extends GenericDialogView
    {
        private Object m_data ;
        private boolean m_isDisplayingForSaga =false ;
        private boolean m_isActComplete =false ;
        private boolean m_isSagaComplete =false ;
public static  int SPEECH_BUBBLE_MARGIN_TOP_OFFSET =15;
public static  int SPEECH_BUBBLE_MARGIN_BOTTOM_OFFSET =10;
public static  int HINT_TEXT_MIN_WIDTH =320;
public static  int HINT_TEXT_MAX_WIDTH =360;
public static  int HEADER_TEXT_WIDTH =440;
public static  int MAX_HEADER_TEXT_WIDTH =680;
        private static  int TASK_PANEL_SIDE_PADDING =14;
public static  int GAP_ABOVE_NPC =14;
public static  double TITLE_GAP =8;
public static  int TEXT_PANEL_SIDE_PADDING =15;
        private static  int BUTTON_OFFSET =20;
        private static  int FRAME_OFFSET =9;

        public  SagaRewardDialogView (Dictionary param1 ,Object param2 )
        {
            this.m_data = param2;
            if (this.m_data.hasOwnProperty("sagaName"))
            {
                this.m_isSagaComplete = SagaManager.instance.isSagaComplete(this.m_data.get("sagaName"));
            }
            if (this.m_data.hasOwnProperty("actName"))
            {
                this.m_isActComplete = SagaManager.instance.isActComplete(this.m_data.get("sagaName"), this.m_data.get("actName"));
            }
            else
            {
                this.m_isDisplayingForSaga = true;
            }
            super(param1, "", "");
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            return;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            _loc_1 = this.createHeaderPanel ();
            _loc_2 = this.createButtonPanel ();
            _loc_3 = this.createInfoPanel ();
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_4.appendAll(_loc_1, _loc_3, ASwingHelper.verticalStrut(-_loc_2.getHeight() + BUTTON_OFFSET + FRAME_OFFSET), _loc_2);
            return _loc_4;
        }//end

        protected JPanel  createInfoPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM );
            AssetPane _loc_2 =new AssetPane(m_assetDict.get( "npcAvatar") );
            _loc_1.append(ASwingHelper.verticalStrut(GAP_ABOVE_NPC));
            _loc_1.append(_loc_2);
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            AssetPane _loc_4 =new AssetPane(m_assetDict.get( "speechTail") );
            _loc_3.append(ASwingHelper.verticalStrut(0));
            _loc_3.append(_loc_4);
            TextFormat _loc_5 =new TextFormat(EmbeddedArt.titleFont ,24);
            _loc_5.align = TextFormatAlign.CENTER;
            _loc_6 = ZLoc.t("Saga",this.m_data.get( "title") );
            _loc_7 = ASwingHelper.makeTextField(_loc_6 ,EmbeddedArt.titleFont ,18,2925509,0,_loc_5 );
            TextFieldUtil.formatSmallCaps(_loc_7.getTextField(), _loc_5);
            _loc_8 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_8.append(_loc_7);
            _loc_9 = ASwingHelper.makeMultilineText(ZLoc.t("Saga",this.m_data.get( "body") ),HINT_TEXT_MIN_WIDTH ,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,18,EmbeddedArt.darkerBlueTextColor );
            _loc_10 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_10.appendAll(_loc_8, _loc_9);
            _loc_11 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_11.append(ASwingHelper.horizontalStrut(TEXT_PANEL_SIDE_PADDING + 10));
            _loc_11.append(_loc_10);
            _loc_11.append(ASwingHelper.horizontalStrut(TEXT_PANEL_SIDE_PADDING));
            _loc_12 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_12.appendAll(ASwingHelper.verticalStrut(30), _loc_11);
            AssetPane _loc_13 =new AssetPane(m_assetDict.get( "verticalRule") );
            ASwingHelper.setEasyBorder(_loc_13, SPEECH_BUBBLE_MARGIN_TOP_OFFSET + 10, 0, SPEECH_BUBBLE_MARGIN_BOTTOM_OFFSET + 10, 0);
            _loc_14 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.TOP );
            _loc_14.setBackgroundDecorator(new MarginBackground(m_assetDict.get("speechBG"), new Insets(SPEECH_BUBBLE_MARGIN_TOP_OFFSET, 0)));
            _loc_14.appendAll(_loc_12, _loc_13, this.createRewardPanel());
            _loc_15 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_15.appendAll(_loc_14, ASwingHelper.verticalStrut(30));
            _loc_16 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_16.append(ASwingHelper.horizontalStrut(TASK_PANEL_SIDE_PADDING));
            _loc_16.append(_loc_1);
            _loc_16.append(ASwingHelper.horizontalStrut(-5));
            _loc_16.append(_loc_3);
            _loc_16.append(ASwingHelper.horizontalStrut(-5));
            _loc_16.append(_loc_15);
            _loc_16.append(ASwingHelper.horizontalStrut(15));
            _loc_16.swapChildren(_loc_15, _loc_3);
            return _loc_16;
        }//end

        protected JPanel  createRewardPanel ()
        {
            String itemRewardName ;
            Item item ;
            Sprite itemPanelBG ;
            JPanel itemImagePanel ;
            JPanel itemImageContainer ;
            JPanel itemTextContainer ;
            AssetPane itemTextPanel ;
            TextField itemTextField ;
            titleStr = ZLoc.t("Quest","rewards_text");
            fontsize = TextFieldUtil.getLocaleFontSize(16,14,null);
            JLabel title =new JLabel(titleStr );
            title.setFont(new ASFont(EmbeddedArt.titleFont, fontsize, false, false, false, EmbeddedArt.advancedFontProps));
            title.setForeground(new ASColor(EmbeddedArt.titleColor));
            title.setTextFilters(EmbeddedArt.newtitleSmallFilters);
            rewardPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            rewardPane.appendAll(ASwingHelper.verticalStrut(4), title);
            rewards = this.m_data.get("rewards");
            if (rewards && rewards.length())
            {
                int _loc_3 =0;
                _loc_4 = rewards;
                XMLList _loc_2 =new XMLList("");
                Object _loc_5;
                for(int i0 = 0; i0 < _loc_4.size(); i0++)
                {
                	_loc_5 = _loc_4.get(i0);


                    with (_loc_5)
                    {
                        if (attribute("type").contains("item"))
                        {
                            _loc_2.put(_loc_3++,  _loc_5);
                        }
                    }
                }
                itemRewardName = _loc_2.attribute("value").toString();
                if (itemRewardName)
                {
                    item = Global.gameSettings().getItemByName(itemRewardName);
                    itemPanelBG = new Sprite();
                    itemPanelBG.addChild(m_assetDict.get("rewardItemBG"));
                    itemImagePanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
                    itemImagePanel.setBackgroundDecorator(new AssetBackground(itemPanelBG));
                    itemImagePanel.setPreferredSize(new IntDimension(itemPanelBG.width, itemPanelBG.height));
                    LoadingManager.load(item.getImageByName("icon"), this.getIconLoadCallback(itemPanelBG, 1, new Point(itemPanelBG.width, itemPanelBG.height)));
                    itemImageContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                    itemImageContainer.append(itemImagePanel);
                    itemTextContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                    itemTextPanel = ASwingHelper.makeMultilineText(item.localizedName, 100, EmbeddedArt.defaultFontNameBold, TextFormatAlign.CENTER, 12, EmbeddedArt.darkerBlueTextColor);
                    itemTextPanel.setHorizontalAlignment(JLabel.CENTER);
                    itemTextField =(TextField) itemTextPanel.getAsset();
                    TextFieldUtil.limitLineCount(itemTextField, 2, 2);
                    itemTextPanel.setPreferredWidth(itemTextField.width + 2);
                    itemTextPanel.setPreferredHeight(itemTextField.textHeight + 5);
                    itemTextContainer.append(itemTextPanel);
                    rewardPane.appendAll(itemImageContainer, itemTextContainer, ASwingHelper.verticalStrut(10));
                }
            }
            return rewardPane;
        }//end

         protected void  makeBackground ()
        {
            setBackgroundDecorator(new MarginBackground(m_bgAsset, new Insets(0, 0, 20, 0)));
            return;
        }//end

         protected JPanel  createHeaderPanel ()
        {
            JPanel _loc_1 =new JPanel(new BorderLayout ());
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_3 = ZLoc.t("Saga",this.m_data.get( "title") );
            _loc_4 = TextFieldUtil.getLocaleFontSize(30,20,.get({localesize"ja",30)});
            TextFormat _loc_5 =new TextFormat ();
            _loc_5.size = _loc_4 + 6;
            _loc_5.align = TextFormatAlign.CENTER;
            _loc_5.leading = 30 - _loc_4;
            _loc_4 = ASwingHelper.shrinkFontSizeToFit(MAX_HEADER_TEXT_WIDTH, _loc_3, EmbeddedArt.titleFont, _loc_4, EmbeddedArt.questTitleFilters, null, _loc_5);
            _loc_5.size = _loc_4 + 6;
            _loc_5.leading = 30 - _loc_4;
            _loc_6 = ASwingHelper.makeTextField(_loc_3 ,EmbeddedArt.titleFont ,_loc_4 ,EmbeddedArt.titleColor );
            _loc_6.filters = EmbeddedArt.questTitleFilters;
            TextFieldUtil.formatSmallCaps(_loc_6.getTextField(), _loc_5);
            _loc_2.append(ASwingHelper.verticalStrut(TITLE_GAP));
            _loc_2.append(_loc_6);
            _loc_1.append(_loc_2, BorderLayout.CENTER);
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_8 = ASwingHelper.makeMarketCloseButton ();
            _loc_8.addActionListener(onCancelX, 0, true);
            _loc_7.append(_loc_8);
            ASwingHelper.setEasyBorder(_loc_7, 2, 0, 0, 6);
            _loc_1.append(_loc_7, BorderLayout.EAST);
            _loc_1.append(ASwingHelper.horizontalStrut(_loc_7.getPreferredWidth()), BorderLayout.WEST);
            return _loc_1;
        }//end

         protected JPanel  createButtonPanel ()
        {
            JPanel _loc_1 =new JPanel(new FlowLayout(FlowLayout.CENTER ));
            _loc_2 = ZLoc.t("Dialogs","Okay");
            if (!this.m_isDisplayingForSaga && !this.m_isSagaComplete || this.m_isDisplayingForSaga && this.m_isSagaComplete)
            {
                _loc_2 = ZLoc.t("Dialogs", "Share");
            }
            CustomButton _loc_3 =new CustomButton(_loc_2 ,null ,"BigGreenButtonUI");
            _loc_3.addActionListener(this.shareAndClose, 0, true);
            _loc_1.appendAll(_loc_3);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        private void  shareAndClose (AWEvent event )
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            if (!this.m_isDisplayingForSaga && !this.m_isSagaComplete || this.m_isDisplayingForSaga && this.m_isSagaComplete)
            {
                countDialogViewAction("SHARE");
                _loc_2 = this.m_isDisplayingForSaga ? (this.m_data.get("sagaName")) : (this.m_data.get("actName"));
                _loc_3 = ZLoc.t("Saga", _loc_2);
                Global.world.viralMgr.sendQuestCompleteFeed(Global.player, _loc_2, _loc_3);
            }
            this.close();
            return;
        }//end

         public void  close ()
        {
            if (!this.m_isDisplayingForSaga)
            {
                SagaManager.instance.doRewardsForSagaOnActComplete(this.m_data.get("actName"));
            }
            super.close();
            return;
        }//end

        private Function  getIconLoadCallback (DisplayObjectContainer param1 ,double param2 =1,Point param3 =null )
        {
            target = param1;
            desiredScale = param2;
            overrideSize = param3;
            return void  (Event event )
            {
                _loc_2 = event(.currentTarget as LoaderInfo ).content ;
                BitmapData _loc_3 =new BitmapData(_loc_2.width ,_loc_2.height ,true ,16777215);
                _loc_3.draw(_loc_2);
                BitmapData _loc_4 =new BitmapData(_loc_2.width *desiredScale ,_loc_2.height *desiredScale ,true ,16777215);
                Matrix _loc_5 =new Matrix ();
                _loc_5.scale(desiredScale, desiredScale);
                _loc_4.draw(_loc_3, _loc_5, null, null, null, true);
                Bitmap _loc_6 =new Bitmap(_loc_4 ,"auto",true );
                _loc_7 = overrideSize? (overrideSize.x) : (target.width);
                _loc_8 = overrideSize? (overrideSize.y) : (target.height);
                _loc_6.x = (_loc_7 - _loc_6.width) * 0.5;
                _loc_6.y = (_loc_8 - _loc_6.height) * 0.5;
                target.addChild(_loc_6);
                return;
            }//end
            ;
        }//end

    }



