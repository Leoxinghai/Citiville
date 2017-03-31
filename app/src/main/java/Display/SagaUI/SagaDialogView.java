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
import Classes.QuestGroup.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Modules.quest.Managers.*;
import Modules.saga.*;
//import flash.display.*;
//import flash.events.*;
//import flash.geom.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.geom.*;

    public class SagaDialogView extends GenericDialogView
    {
        private Function m_getSagaAssetClass ;
        private JPanel m_speechContainer ;
        private String m_sagaName ;
        private XML m_sagaDefinition ;
        private Dictionary m_tooltipData ;
        private SagaTooltip m_questGroupTooltip ;
        public static  int SPEECH_WIDTH =525;
        public static  int SPEECH_HEIGHT =150;

        public  SagaDialogView (Dictionary param1 ,String param2 ="",String param3 ="",Function param4 =null )
        {
            this.m_sagaName = param3;
            this.m_getSagaAssetClass = param4;
            this.m_tooltipData = new Dictionary();
            this.m_questGroupTooltip = new SagaTooltip();
            this.m_sagaDefinition = SagaManager.instance.getSagaDefinitionByName(param3);
            super(param1, param2, param3);
            this.setLayout(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            return;
        }//end

         protected void  makeCenterPanel ()
        {
            setBackgroundDecorator(new AssetBackground(m_bgAsset));
            setPreferredSize(new IntDimension(m_bgAsset.width, m_bgAsset.height));
            super.makeCenterPanel();
            return;
        }//end

        protected JPanel  createSpeechBubble ()
        {
            _loc_1 = ZLoc.t("Dialogs",this.m_sagaName +"_description");
            _loc_2 = ASwingHelper.makeMultilineText(_loc_1 ,380,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.LEFT ,16,EmbeddedArt.brownTextColor );
            this.m_speechContainer = ASwingHelper.makeSoftBoxJPanelVertical();
            this.m_speechContainer.setPreferredWidth(380);
            this.m_speechContainer.append(_loc_2);
            _loc_3 = TextFieldUtil.getLocaleFontSize(16,14,null);
            _loc_4 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","Saga_reward"),EmbeddedArt.titleFont ,_loc_3 ,EmbeddedArt.titleColor );
            _loc_4.setTextFilters(EmbeddedArt.newtitleSmallFilters);
            _loc_5 = this.getSagaAsset("item_bg");
            _loc_6 = SagaManager.instance.getFirstRewardItemForSaga(this.m_sagaName);
            Sprite _loc_7 =new Sprite ();
            _loc_7.addChild(_loc_5);
            LoadingManager.load(_loc_6.getImageByName("icon"), this.getIconLoadCallback(_loc_7));
            _loc_8 = _loc_6.localizedName;
            _loc_9 = ASwingHelper.makeLabel(_loc_8 ,EmbeddedArt.defaultFontNameBold ,12,EmbeddedArt.darkBlueTextColor );
            AssetPane _loc_10 =new AssetPane(_loc_7 );
            _loc_10.setPreferredSize(new IntDimension(_loc_5.width, _loc_5.height));
            _loc_11 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_11.append(_loc_10);
            _loc_12 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_12.setPreferredWidth(120);
            _loc_12.appendAll(_loc_4, _loc_11, _loc_9);
            this.addTooltipTarget(_loc_12, SagaTooltip.SAGA_REWARD);
            _loc_13 = ASwingHelper.makeSoftBoxJPanel ();
            ASwingHelper.setEasyBorder(_loc_13, 10, 10, 10, 10);
            _loc_13.appendAll(this.m_speechContainer, ASwingHelper.horizontalStrut(10), new AssetPane(this.getSagaAsset("vRule")), ASwingHelper.horizontalStrut(5), _loc_12);
            _loc_14 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_14.setBackgroundDecorator(new AssetBackground(this.getSagaAsset("speechBubble")));
            _loc_14.setPreferredHeight(SPEECH_HEIGHT);
            _loc_14.append(_loc_13);
            _loc_15 = ASwingHelper.makeSoftBoxJPanel ();
            _loc_15.appendAll(ASwingHelper.horizontalStrut(155), _loc_14, ASwingHelper.horizontalStrut(30));
            return _loc_15;
        }//end

        protected JPanel  createActPanel (XML param1 ,double param2 =1)
        {
            String _loc_19 =null ;
            JLabel _loc_20 =null ;
            DisplayObject _loc_21 =null ;
            Item _loc_22 =null ;
            Sprite _loc_23 =null ;
            AssetPane _loc_24 =null ;
            JPanel _loc_25 =null ;
            JPanel _loc_26 =null ;
            JPanel _loc_27 =null ;
            XMLList _loc_28 =null ;
            XML _loc_29 =null ;
            Sprite _loc_30 =null ;
            JLabel _loc_31 =null ;
            JLabel _loc_32 =null ;
            String _loc_33 =null ;
            boolean _loc_34 =false ;
            boolean _loc_35 =false ;
            GameQuest _loc_36 =null ;
            Class _loc_37 =null ;
            Class _loc_38 =null ;
            Class _loc_39 =null ;
            String _loc_40 =null ;
            Sprite _loc_41 =null ;
            Sprite _loc_42 =null ;
            Sprite _loc_43 =null ;
            SimpleButton _loc_44 =null ;
            JButton _loc_45 =null ;
            Sprite _loc_46 =null ;
            Sprite _loc_47 =null ;
            AssetPane _loc_48 =null ;
            Sprite _loc_49 =null ;
            String _loc_50 =null ;
            AssetPane _loc_51 =null ;
            JPanel _loc_52 =null ;
            JLabel _loc_53 =null ;
            _loc_3 = param1.attribute("name").toString ();
            _loc_4 = SagaManager.instance.isActAvailable(_loc_3);
            _loc_5 = SagaManager.instance.isActComplete(this.m_sagaName,_loc_3);
            int _loc_6 =16508752;
            _loc_7 = EmbeddedArt.commonOrangeFilter;
            if (!_loc_4)
            {
                _loc_6 = 15527148;
                _loc_7 = EmbeddedArt.commonGrayFilter;
            }
            _loc_8 = ASwingHelper.makeLabel(ZLoc.t("Dialogs","Saga_act",{actNumber param2 }),EmbeddedArt.titleFont ,20,_loc_6 );
            _loc_8.setTextFilters(_loc_7);
            _loc_9 = ASwingHelper.makeSoftBoxJPanelVertical ();
            if (_loc_4)
            {
                _loc_19 = ZLoc.t("Saga", _loc_3);
                _loc_20 = ASwingHelper.makeLabel(_loc_19, EmbeddedArt.defaultFontNameBold, 16, EmbeddedArt.darkBlueTextColor);
                _loc_21 = this.getSagaAsset("item_bg");
                _loc_22 = SagaManager.instance.getFirstRewardItemForAct(this.m_sagaName, param1.attribute("name").toString());
                _loc_23 = new Sprite();
                _loc_23.addChild(_loc_21);
                LoadingManager.load(_loc_22.getImageByName("icon"), this.getIconLoadCallback(_loc_23));
                _loc_24 = new AssetPane(_loc_23);
                _loc_24.setPreferredSize(new IntDimension(_loc_21.width, _loc_21.height));
                _loc_25 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_25.appendAll(_loc_24);
                this.addTooltipTarget(_loc_25, SagaTooltip.ACT_REWARD);
                if (_loc_5)
                {
                    _loc_30 = new Sprite();
                    _loc_30.addChild(this.getSagaAsset("check"));
                    _loc_30.x = 120;
                    _loc_30.y = 30;
                    _loc_25.addChild(_loc_30);
                }
                _loc_26 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_26.setPreferredHeight(30);
                if (_loc_5)
                {
                    _loc_31 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "Saga_completed"), EmbeddedArt.titleFont, 20, 11139980);
                    _loc_31.setTextFilters(EmbeddedArt.commonGreenFilter);
                    _loc_26.appendAll(new AssetPane(this.getSagaAsset("star")), _loc_31, new AssetPane(this.getSagaAsset("star")));
                }
                else
                {
                    _loc_32 = ASwingHelper.makeLabel(_loc_22.localizedName, EmbeddedArt.defaultFontNameBold, 14, EmbeddedArt.whiteTextColor);
                    _loc_26.append(_loc_32);
                }
                _loc_27 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_28 = param1.child("questGroup");
                for(int i0 = 0; i0 < _loc_28.size(); i0++)
                {
                	_loc_29 = _loc_28.get(i0);

                    _loc_33 = _loc_29.attribute("name").toString();
                    _loc_34 = QuestGroupManager.instance.isQuestGroupAvailable(_loc_33);
                    _loc_35 = QuestGroupManager.instance.isQuestGroupComplete(_loc_33);
                    _loc_36 = QuestGroupManager.instance.getQuestGroupActiveQuest(_loc_33);
                    _loc_37 = this.m_getSagaAssetClass("btnQuestActive_up");
                    _loc_38 = this.m_getSagaAssetClass("btnQuestActive_over");
                    _loc_39 = this.m_getSagaAssetClass("btnQuestActive_down");
                    if (_loc_35)
                    {
                        _loc_37 = this.m_getSagaAssetClass("btnQuestComplete_up");
                        _loc_38 = this.m_getSagaAssetClass("btnQuestComplete_over");
                        _loc_39 = this.m_getSagaAssetClass("btnQuestComplete_down");
                    }
                    _loc_40 = Global.getAssetURL(_loc_29.attribute("defaultIcon").toString());
                    if (_loc_34 && _loc_36 && !_loc_35)
                    {
                        _loc_40 = Global.getAssetURL(_loc_36.icon);
                        _loc_41 = new Sprite();
                        _loc_41.addChild(new _loc_37);
                        LoadingManager.load(_loc_40, this.getIconLoadCallback(_loc_41, 0.7));
                        _loc_42 = new Sprite();
                        _loc_42.addChild(new _loc_38);
                        LoadingManager.load(_loc_40, this.getIconLoadCallback(_loc_42, 0.75));
                        _loc_43 = new Sprite();
                        _loc_43.addChild(new _loc_39);
                        LoadingManager.load(_loc_40, this.getIconLoadCallback(_loc_43, 0.7));
                        _loc_44 = new SimpleButton(_loc_41, _loc_42, _loc_43, _loc_43);
                        _loc_45 = new JButton();
                        _loc_45.wrapSimpleButton(_loc_44);
                        _loc_45.addActionListener(this.getQuestGroupButtonClickCallback(_loc_36.name));
                        this.addTooltipTarget(_loc_45, _loc_36.name);
                        _loc_27.append(_loc_45);
                        continue;
                    }
                    _loc_46 = new Sprite();
                    _loc_46.addChild(new _loc_37);
                    _loc_47 = new Sprite();
                    _loc_46.addChild(_loc_47);
                    LoadingManager.load(_loc_40, this.getIconLoadCallback(_loc_47, 0.7, new Point(_loc_46.width, _loc_46.height)));
                    _loc_48 = new AssetPane(_loc_46);
                    if (_loc_35)
                    {
                        _loc_49 = new Sprite();
                        int _loc_56 =40;
                        _loc_49.y = 40;
                        _loc_49.x = 40;
                        _loc_46.addChild(_loc_49);
                        _loc_50 = Global.getAssetURL("assets/market/market_checkmark_medium.png");
                        LoadingManager.load(_loc_50, this.getIconLoadCallback(_loc_49, 1));
                        this.addTooltipTarget(_loc_48, SagaTooltip.COMPLETED);
                    }
                    else
                    {
                        this.addTooltipTarget(_loc_48, SagaTooltip.COMING_SOON);
                        _loc_46.filters = EmbeddedArt.desaturateFilter;
                    }
                    _loc_27.append(_loc_48);
                }
                _loc_9.appendAll(_loc_20, _loc_25, _loc_26, _loc_27);
            }
            else
            {
                _loc_51 = new AssetPane(this.getSagaAsset("newslogo"));
                _loc_52 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_52.append(_loc_51);
                _loc_53 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "Saga_comingSoon"), EmbeddedArt.titleFont, 16, 15527148);
                _loc_53.setTextFilters(EmbeddedArt.commonGrayFilter);
                _loc_9.appendAll(ASwingHelper.verticalStrut(30), _loc_52, _loc_53, ASwingHelper.verticalStrut(40));
            }
            _loc_10 = _loc_4? (this.m_getSagaAssetClass("tvBg_active")) : (this.m_getSagaAssetClass("tvBg_disabled"));
            _loc_11 = new(_loc_4? (this.m_getSagaAssetClass("tvBg_active")) : (this.m_getSagaAssetClass("tvBg_disabled")));
            Sprite _loc_12 =new Sprite ();
            _loc_12.addChild(_loc_11);
            _loc_13 = this.getSagaAsset("tvChrome");
            Sprite _loc_14 =new Sprite ();
            _loc_14.addChild(_loc_13);
            Sprite _loc_15 =new Sprite ();
            _loc_15.addChild(_loc_11);
            _loc_15.addChild(_loc_13);
            _loc_16 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_16.setBackgroundDecorator(new AssetBackground(_loc_15));
            _loc_16.setPreferredSize(new IntDimension(_loc_15.width, _loc_15.height));
            _loc_17 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT );
            _loc_18 = this.createReplayButton(_loc_3 );
            _loc_17.appendAll(_loc_18, ASwingHelper.horizontalStrut(11));
            _loc_16.appendAll(ASwingHelper.verticalStrut(13), _loc_17, ASwingHelper.verticalStrut(-20), _loc_8, ASwingHelper.verticalStrut(-10), _loc_9);
            _loc_16.bringToTop(_loc_17);
            return _loc_16;
        }//end

        protected JButton  createReplayButton (String param1 )
        {
            Class _loc_3 =null ;
            Class _loc_4 =null ;
            Class _loc_5 =null ;
            _loc_2 = SagaManager.instance.isActAvailable(param1);
            if (_loc_2)
            {
                _loc_3 = this.m_getSagaAssetClass("btnReplay_up");
                _loc_4 = this.m_getSagaAssetClass("btnReplay_over");
                _loc_5 = this.m_getSagaAssetClass("btnReplay_down");
            }
            else
            {
                _loc_3 = this.m_getSagaAssetClass("btnReplay_gray");
                _loc_4 = this.m_getSagaAssetClass("btnReplay_gray");
                _loc_5 = this.m_getSagaAssetClass("btnReplay_gray");
            }
            Sprite _loc_6 =new Sprite ();
            _loc_6.addChild(new _loc_3);
            Sprite _loc_7 =new Sprite ();
            _loc_7.addChild(new _loc_4);
            Sprite _loc_8 =new Sprite ();
            _loc_8.addChild(new _loc_5);
            SimpleButton _loc_9 =new SimpleButton(_loc_6 ,_loc_7 ,_loc_8 ,_loc_8 );
            JButton _loc_10 =new JButton ();
            _loc_10.wrapSimpleButton(_loc_9);
            if (_loc_2)
            {
                _loc_10.addActionListener(this.getReplayButtonClickCallback(param1));
            }
            return _loc_10;
        }//end

        protected JPanel  createSagaPanel ()
        {
            XML _loc_4 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = SagaManager.instance.getActsBySaga(this.m_sagaName);
            int _loc_3 =1;
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_4 = _loc_2.get(i0);

                _loc_1.append(this.createActPanel(_loc_4, _loc_3));
                _loc_3++;
                if (_loc_3 > 3)
                {
                    break;
                }
            }
            return _loc_1;
        }//end

         protected JPanel  createInteriorHolder ()
        {
            _loc_1 = createHeaderPanel();
            _loc_2 = this.createSpeechBubble ();
            _loc_3 = this.createSagaPanel ();
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_4.appendAll(_loc_1, _loc_2, ASwingHelper.verticalStrut(40), _loc_3);
            return _loc_4;
        }//end

        protected void  addTooltipTarget (DisplayObject param1 ,String param2 )
        {
            this.m_tooltipData.put(param1,  param2);
            param1.addEventListener(MouseEvent.MOUSE_OVER, this.onTooltipTargetMouseOver, false, 0, true);
            param1.addEventListener(MouseEvent.MOUSE_OUT, this.onTooltipTargetMouseOut, false, 0, true);
            return;
        }//end

        protected void  removeTooltipTarget (DisplayObject param1 )
        {
            param1.removeEventListener(MouseEvent.MOUSE_OVER, this.onTooltipTargetMouseOver);
            param1.removeEventListener(MouseEvent.MOUSE_OUT, this.onTooltipTargetMouseOut);
            delete this.m_tooltipData.get(param1);
            return;
        }//end

        private Function  getReplayButtonClickCallback (String param1 )
        {
            String announceName ;
            SagaDialogView self ;
            actName = param1;
            isActComplete = SagaManager.instance.isActComplete(this.m_sagaName,actName);
            if (isActComplete)
            {
                announceName = SagaManager.instance.getActCompleteReplay(actName);
            }
            else
            {
                announceName = SagaManager.instance.getActIntroName(actName);
            }
            self;
            return void  (AWEvent event )
            {
                SagaManager.instance.showIntro(announceName);
                self.closeMe();
                return;
            }//end
            ;
        }//end

        private void  onTooltipTargetMouseOver (MouseEvent event )
        {
            DisplayObject _loc_3 =null ;
            Point _loc_4 =null ;
            _loc_2 = this.m_tooltipData.get(event.currentTarget) ;
            if (_loc_2)
            {
                _loc_3 =(DisplayObject) event.currentTarget;
                this.m_questGroupTooltip.setInfoForQuest(_loc_2);
                _loc_4 = _loc_3.parent.localToGlobal(new Point(_loc_3.x, _loc_3.y));
                this.m_questGroupTooltip.x = _loc_4.x - (this.m_questGroupTooltip.getWidth() - _loc_3.width) / 2;
                this.m_questGroupTooltip.y = _loc_4.y - this.m_questGroupTooltip.getHeight();
                Global.stage.addChild(this.m_questGroupTooltip);
            }
            return;
        }//end

        private void  onTooltipTargetMouseOut (MouseEvent event )
        {
            if (this.m_questGroupTooltip.parent)
            {
                this.m_questGroupTooltip.parent.removeChild(this.m_questGroupTooltip);
            }
            return;
        }//end

        private Function  getQuestGroupButtonClickCallback (String param1 )
        {
            SagaDialogView self ;
            questName = param1;
            self;
            return void  (AWEvent event )
            {
                SagaManager.instance.showSagaQuest(questName);
                self.closeMe();
                return;
            }//end
            ;
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

        private Object getSagaAsset (String param1 )
        {
            return new this.m_getSagaAssetClass(param1);
        }//end

    }



