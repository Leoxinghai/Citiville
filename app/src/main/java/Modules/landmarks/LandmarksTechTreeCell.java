package Modules.landmarks;

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
import Display.LandmarkUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Events.*;
import Modules.saga.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class LandmarksTechTreeCell extends DataItemCell
    {
        protected WonderDatum m_wonderDatum ;
        protected Dictionary m_assetDict ;
        protected JPanel m_prerequisitesPanel ;
        protected JPanel m_linesAndArrowPanel ;
        protected GenericDialogView m_techtree ;
        protected JPanel m_bodyHolder ;
        protected DisplayObject m_lockAsset ;
        public static  int CELL_HEIGHT =400;
        public static  int CELL_WIDTH =500;

        public  LandmarksTechTreeCell (Dictionary param1 ,GenericDialogView param2 )
        {
            this.m_assetDict = param1;
            this.m_techtree = param2;
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS, 0, SoftBoxLayout.TOP));
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            this.m_wonderDatum =(WonderDatum) param1;
            this.buildCell();
            return;
        }//end

        public void  buildCell ()
        {
            this.removeAll();
            this.m_bodyHolder = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            this.m_prerequisitesPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_linesAndArrowPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.append(this.createHintPanel());
            this.m_bodyHolder.appendAll(ASwingHelper.horizontalStrut(20), this.makePrerequisitesPanel());
            this.m_bodyHolder.appendAll(ASwingHelper.horizontalStrut(-15), this.makeLinesAndArrowPanel());
            this.m_bodyHolder.appendAll(ASwingHelper.horizontalStrut(10), this.makeWonderBox(this.m_wonderDatum));
            this.append(this.m_bodyHolder);
            ASwingHelper.setForcedSize(this, new IntDimension(670, 460));
            ASwingHelper.prepare(this);
            return;
        }//end

        protected JPanel  createHintPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_2 = ZLoc.t("Dialogs","TechTreeHint_"+this.m_wonderDatum.name );
            _loc_3 = ASwingHelper.makeMultilineText(_loc_2 ,650,EmbeddedArt.defaultFontNameBold ,TextFormatAlign.CENTER ,18,EmbeddedArt.brownTextColor );
            _loc_1.append(_loc_3);
            return _loc_1;
        }//end

        protected JPanel  makePrerequisitesPanel ()
        {
            LandmarkDatum _loc_1 =null ;
            SagaActDatum _loc_2 =null ;
            if (this.m_wonderDatum.landmarks != null)
            {
                for(int i0 = 0; i0 < this.m_wonderDatum.landmarks.size(); i0++)
                {
                		_loc_1 = this.m_wonderDatum.landmarks.get(i0);

                    this.m_prerequisitesPanel.append(this.makeLandmarkBox(_loc_1));
                }
            }
            if (this.m_wonderDatum.sagaActs != null)
            {
                for(int i0 = 0; i0 < this.m_wonderDatum.sagaActs.size(); i0++)
                {
                		_loc_2 = this.m_wonderDatum.sagaActs.get(i0);

                    this.m_prerequisitesPanel.append(this.makeSagaActBox(_loc_2));
                }
            }
            return this.m_prerequisitesPanel;
        }//end

        protected boolean  areAllPrerequisitesComplete ()
        {
            LandmarkDatum _loc_1 =null ;
            SagaActDatum _loc_2 =null ;
            if (this.m_wonderDatum.landmarks != null)
            {
                for(int i0 = 0; i0 < this.m_wonderDatum.landmarks.size(); i0++)
                {
                		_loc_1 = this.m_wonderDatum.landmarks.get(i0);

                    if (_loc_1.state != LandmarkDatum.STATE_COMPLETE)
                    {
                        return false;
                    }
                }
            }
            if (this.m_wonderDatum.sagaActs != null)
            {
                for(int i0 = 0; i0 < this.m_wonderDatum.sagaActs.size(); i0++)
                {
                		_loc_2 = this.m_wonderDatum.sagaActs.get(i0);

                    if (_loc_2.state != SagaActDatum.STATE_COMPLETE)
                    {
                        return false;
                    }
                }
            }
            return true;
        }//end

        protected boolean  isWonderPlaced (String param1 )
        {
            ConstructionSite _loc_3 =null ;
            if (Global.world.getObjectsByNames(.get(param1)).length != 0)
            {
                return true;
            }
            _loc_2 =Global.world.getObjectsByClass(ConstructionSite );
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            		_loc_3 = _loc_2.get(i0);

                if (_loc_3.targetName == param1)
                {
                    return true;
                }
            }
            return false;
        }//end

        protected JPanel  makeLinesAndArrowPanel ()
        {
            _loc_1 =(DisplayObject) new this.m_assetDict.get( "wonders_flowArrow");
            AssetPane _loc_2 =new AssetPane(_loc_1 );
            _loc_3 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            _loc_3.appendAll(ASwingHelper.verticalStrut(-40), _loc_2);
            this.m_linesAndArrowPanel.append(_loc_3);
            return this.m_linesAndArrowPanel;
        }//end

        protected JPanel  makeWonderBox (WonderDatum param1 )
        {
            String bonus ;
            JPanel statusPanel ;
            String statusLoc ;
            double fontsize ;
            CustomButton statusButton ;
            AssetPane bonusStat ;
            Function placeCallback ;
            datum = param1;
            wonderBox = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            burst =(DisplayObject) new this.m_assetDict.get( "wonders_burst_lg");
            AssetPane picturePanel =new AssetPane(burst );
            Sprite sprite =new Sprite ();
            wonderStats = Global.gameSettings().getDisplayedStatsByName(datum.name);
            wonderAsset =(DisplayObject) this.m_assetDict.get(wonderStats.get( "picture"));
            this.m_lockAsset =(DisplayObject) new this.m_assetDict.get("wonders_lock_icon");
            sprite.addChild(wonderAsset);
            sprite.x = (burst.width - wonderAsset.width) / 2;
            sprite.y = (burst.height - wonderAsset.height) / 2;
            picturePanel.addChild(sprite);
            textHolder = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.LEFT);
            description = ASwingHelper.makeMultilineText(ZLoc.t("Dialogs","TechTreeWonderHint"),burst.width+40,EmbeddedArt.defaultFontNameBold,TextFormatAlign.LEFT,14,EmbeddedArt.darkBlueTextColor);
            textHolder.append(description);
            int _loc_3 =0;
            _loc_4 = wonderStats.bonus;
            for(int i0 = 0; i0 < wonderStats.bonus.size(); i0++)
            {
            		bonus = wonderStats.bonus.get(i0);


                bonusStat = ASwingHelper.makeMultilineText(ZLoc.t("Items", bonus), burst.width + 40, EmbeddedArt.defaultFontNameBold, TextFormatAlign.LEFT, 14, EmbeddedArt.darkBlueTextColor);
                textHolder.append(bonusStat);
            }
            statusPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            statusLoc = ZLoc.t("Dialogs", "PlaceLandmark");
            statusLoc = TextFieldUtil.formatSmallCapsString(statusLoc);
            fontsize = TextFieldUtil.getLocaleFontSize(18, 14, [{locale:"ja", size:18}]);
            if (this.areAllPrerequisitesComplete() || Global.player.inventory.getItemCountByName(datum.name) > 0)
            {
                if (!this.isWonderPlaced(datum.name))
                {
                    placeCallback =void  ()
            {
                addEventListener(MarketEvent.MARKET_BUY, UI.onMarketClick, false, 0, false);
                MarketEvent _loc_1 =new MarketEvent(MarketEvent.MARKET_BUY ,MarketEvent.GENERIC ,datum.name );
                _loc_1.eventSource = MarketEvent.SOURCE_INVENTORY;
                dispatchEvent(_loc_1);
                statusButton.removeEventListener(MouseEvent.CLICK, placeCallback);
                removeEventListener(MarketEvent.MARKET_BUY, UI.onMarketClick);
                StatsManager.sample(100, StatsCounterType.DIALOG, "tech_tree", datum.name, "place");
                m_techtree.close();
                return;
            }//end
            ;
                    statusButton = new CustomButton(statusLoc, null, "GreenButtonUI");
                    statusButton.addEventListener(MouseEvent.CLICK, placeCallback, false, 0, false);
                    statusButton.setFont(ASwingHelper.makeFont(EmbeddedArt.titleFont, fontsize));
                    ASwingHelper.setForcedSize(statusButton, new IntDimension(125, 30));
                    statusPanel.appendAll(ASwingHelper.horizontalStrut(3), statusButton, ASwingHelper.horizontalStrut(3));
                }
            }
            else
            {
                sprite.addChild(this.m_lockAsset);
                this.m_lockAsset.y = wonderAsset.height * 7 / 10;
                statusButton = new CustomButton(statusLoc, null, "GreyButtonUI");
                statusButton.setEnabled(false);
                statusButton.setFont(ASwingHelper.makeFont(EmbeddedArt.titleFont, fontsize));
                ASwingHelper.setForcedSize(statusButton, new IntDimension(125, 30));
                statusPanel.appendAll(ASwingHelper.horizontalStrut(3), statusButton, ASwingHelper.horizontalStrut(3));
            }
            wonderBox.append(picturePanel);
            wonderBox.append(textHolder);
            wonderBox.append(statusPanel);
            return wonderBox;
        }//end

        protected JPanel  makeLandmarkBox (LandmarkDatum param1 )
        {
            String statusLoc ;
            JTextField statusLabel ;
            CustomButton statusButton ;
            Function placeCallback ;
            Function buildCallback ;
            TextFormat tform ;
            DisplayObject landmarkAsset ;
            DisplayObject checkAsset ;
            DisplayObject teaserAsset ;
            datum = param1;
            statusPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            fontsize = TextFieldUtil.getLocaleFontSize(18,14,.get( {locale size "ja",18) });
            boxDO =(DisplayObject) new this.m_assetDict.get( "wonders_burst_tiny");
            switch(datum.state)
            {
                case LandmarkDatum.STATE_UNPLACED:
                {
                    statusLoc = ZLoc.t("Main", "Start");
                    statusLoc = TextFieldUtil.formatSmallCapsString(statusLoc);
                    placeCallback =void  ()
            {
                addEventListener(MarketEvent.MARKET_BUY, UI.onMarketClick, false, 0, false);
                MarketEvent _loc_1 =new MarketEvent(MarketEvent.MARKET_BUY ,MarketEvent.GENERIC ,datum.name );
                _loc_1.eventSource = MarketEvent.SOURCE_INVENTORY;
                dispatchEvent(_loc_1);
                statusButton.removeEventListener(MouseEvent.CLICK, placeCallback);
                removeEventListener(MarketEvent.MARKET_BUY, UI.onMarketClick);
                StatsManager.sample(100, StatsCounterType.DIALOG, "tech_tree", datum.name, m_wonderDatum.name, "start");
                m_techtree.close();
                return;
            }//end
            ;
                    statusButton = new CustomButton(statusLoc, null, "GreenButtonUI");
                    statusButton.addEventListener(MouseEvent.CLICK, placeCallback, false, 0, false);
                    break;
                }
                case LandmarkDatum.STATE_PLACED:
                {
                    statusLoc = ZLoc.t("Main", "Build");
                    statusLoc = TextFieldUtil.formatSmallCapsString(statusLoc);
                    buildCallback =void  ()
            {
                Item _loc_4 =null ;
                LandmarkBuildDialog _loc_5 =null ;
                boolean _loc_1 =false ;
                _loc_2 =Global.gameSettings().getItemByName(m_wonderDatum.name );
                _loc_3 = _loc_2.requiredLandmarks ;
                if (_loc_3.indexOf(datum.name) != -1)
                {
                    _loc_1 = true;
                }
                if (datum.construction.currentState == ConstructionSite.STATE_AT_GATE && _loc_1)
                {
                    _loc_4 = Global.gameSettings().getItemByName(datum.name);
                    _loc_5 = new LandmarkBuildDialog(datum.construction, _loc_4);
                    UI.displayPopup(_loc_5, false, "landmarkBuildDialog", false);
                }
                else
                {
                    Global.world.centerOnObject(datum.findConstructionSite());
                }
                statusButton.removeEventListener(MouseEvent.CLICK, buildCallback);
                StatsManager.sample(100, StatsCounterType.DIALOG, "tech_tree", datum.name, m_wonderDatum.name, "build");
                m_techtree.close();
                return;
            }//end
            ;
                    statusButton = new CustomButton(statusLoc, null, "GreenButtonUI");
                    statusButton.addEventListener(MouseEvent.CLICK, buildCallback, false, 0, false);
                    break;
                }
                case LandmarkDatum.STATE_COMPLETE:
                {
                    statusLoc = ZLoc.t("Main", "Complete");
                    statusLoc = TextFieldUtil.formatSmallCapsString(statusLoc);
                    break;
                }
                case LandmarkDatum.STATE_COMING_SOON:
                {
                    statusLoc = ZLoc.t("Dialogs", "ComingSoon");
                    statusLoc = TextFieldUtil.formatSmallCapsString(statusLoc);
                    break;
                }
                default:
                {
                    break;
                }
            }
            if (statusButton)
            {
                statusButton.setFont(ASwingHelper.makeFont(EmbeddedArt.titleFont, fontsize));
                ASwingHelper.setForcedSize(statusButton, new IntDimension(125, 30));
                statusPanel.appendAll(ASwingHelper.horizontalStrut(3), statusButton, ASwingHelper.horizontalStrut(3));
            }
            else
            {
                statusLoc = TextFieldUtil.formatSmallCapsString(statusLoc);
                statusLabel = ASwingHelper.makeTextField(statusLoc + " ", EmbeddedArt.titleFont, fontsize, EmbeddedArt.titleColor, 3);
                statusLabel.filters = EmbeddedArt.newtitleSmallFilters;
                tform = new TextFormat();
                tform.size = statusLoc.length > 11 ? (fontsize * 0.89) : (fontsize);
                TextFieldUtil.formatSmallCaps(statusLabel.getTextField(), tform);
                statusPanel.append(statusLabel);
            }
            AssetPane boxPane =new AssetPane(boxDO );
            Sprite sprite =new Sprite ();
            if (datum.name != "dummy")
            {
                landmarkAsset =(DisplayObject) this.m_assetDict.get(Global.gameSettings().getImageByNameRelativeUrl(datum.name, "icon"));
                sprite.addChild(landmarkAsset);
                if (datum.state == LandmarkDatum.STATE_COMPLETE)
                {
                    checkAsset =(DisplayObject) new this.m_assetDict.get("wonders_check_lrg");
                    sprite.addChild(checkAsset);
                }
                sprite.x = (boxDO.width - landmarkAsset.width) / 2;
                sprite.y = (boxDO.height - landmarkAsset.height) / 2;
            }
            else
            {
                teaserAsset =(DisplayObject) new this.m_assetDict.get("wonders_questionMark_icon");
                sprite.addChild(teaserAsset);
                sprite.x = (boxDO.width - teaserAsset.width) / 2;
                sprite.y = (boxDO.height - teaserAsset.height) / 2;
            }
            boxPane.addChild(sprite);
            ASwingHelper.setForcedSize(boxPane, new IntDimension(boxDO.width, boxDO.height));
            boxHolder = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            boxHolder.append(boxPane);
            panel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            landmarkBox = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            ASwingHelper.setForcedSize(landmarkBox, new IntDimension(150, boxDO.height + 35));
            landmarkBox.append(boxHolder);
            landmarkBox.appendAll(ASwingHelper.verticalStrut(3), statusPanel, ASwingHelper.verticalStrut(3));
            panel.append(landmarkBox);
            panel.appendAll(ASwingHelper.horizontalStrut(10));
            return panel;
        }//end

        private Component  makeSagaActImageComponent (SagaActDatum param1 )
        {
            DisplayObject _loc_9 =null ;
            DisplayObject _loc_10 =null ;
            DisplayObject _loc_11 =null ;
            DisplayObject _loc_12 =null ;
            Sprite _loc_2 =new Sprite ();
            _loc_3 =(DisplayObject) new this.m_assetDict.get( "wonders_burst_tiny");
            _loc_2.addChild(_loc_3);
            if (param1.state == SagaActDatum.STATE_UNAVAILABLE || param1.state == SagaActDatum.STATE_LEVEL_LOCKED)
            {
                _loc_9 =(DisplayObject) new this.m_assetDict.get("wonders_questionMark_icon");
                _loc_2.addChild(_loc_9);
            }
            else
            {
                if (param1.iconUrl != null && this.m_assetDict.get(param1.iconUrl))
                {
                    _loc_10 =(DisplayObject) this.m_assetDict.get(param1.iconUrl);
                }
                else
                {
                    _loc_10 =(DisplayObject) new this.m_assetDict.get("wonders_questionMark_icon");
                }
                _loc_2.addChild(_loc_10);
                if (param1.state == SagaActDatum.STATE_COMPLETE)
                {
                    _loc_11 =(DisplayObject) new this.m_assetDict.get("wonders_check_lrg");
                    _loc_2.addChild(_loc_11);
                }
            }
            _loc_4 = _loc_3.width ;
            _loc_5 = _loc_3.height ;
            _loc_6 = _loc_2.numChildren ;
            int _loc_7 =0;
            while (_loc_7 < _loc_6)
            {

                _loc_12 = _loc_2.getChildAt(_loc_7);
                if (_loc_12 != _loc_3)
                {
                    _loc_12.x = (_loc_4 - _loc_12.width) * 0.5;
                    _loc_12.y = (_loc_5 - _loc_12.height) * 0.5;
                }
                _loc_7++;
            }
            JPanel _loc_8 =new JPanel(new CenterLayout ());
            _loc_8.append(new AssetPane(_loc_2));
            ASwingHelper.setForcedHeight(_loc_8, _loc_3.height);
            return _loc_8;
        }//end

        private String  getPrerequisiteSagaName ()
        {
            _loc_1 =Global.gameSettings().getItemByName(this.m_wonderDatum.name );
            return _loc_1 != null ? (_loc_1.prerequisiteSaga) : (null);
        }//end

        private void  attachSagaGoNowButtonClickHandler (IEventDispatcher param1 ,String param2 )
        {
            Function clickHandler ;
            button = param1;
            sagaName = param2;
            clickHandler =void  ()
            {
                SagaManager.instance.showDialogForSaga(sagaName);
                button.removeEventListener(MouseEvent.CLICK, clickHandler);
                m_techtree.close();
                return;
            }//end
            ;
            if (sagaName == null || sagaName.length == 0)
            {
                return;
            }
            button.addEventListener(MouseEvent.CLICK, clickHandler, false, 0, false);
            return;
        }//end

        private Component  makeSagaActGoNowButton (SagaActDatum param1 )
        {
            _loc_2 = TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs","GoNow"));
            CustomButton _loc_3 =new CustomButton(_loc_2 ,null ,"GreenButtonUI");
            _loc_3.setFont(ASwingHelper.makeFont(EmbeddedArt.titleFont, getSagaActStatusFontSize()));
            ASwingHelper.setForcedSize(_loc_3, new IntDimension(125, 30));
            this.attachSagaGoNowButtonClickHandler(_loc_3, this.getPrerequisiteSagaName());
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_4.appendAll(ASwingHelper.horizontalStrut(3), _loc_3, ASwingHelper.horizontalStrut(3));
            return _loc_4;
        }//end

        private Component  makeSagaActStatusComponent (SagaActDatum param1 )
        {
            switch(param1.state)
            {
                case SagaActDatum.STATE_LEVEL_LOCKED:
                {
                    return makeSagaActStatusTextLabel(getSagaActLevelUnlockText(param1));
                }
                case SagaActDatum.STATE_IN_PROGRESS:
                {
                    return this.makeSagaActGoNowButton(param1);
                }
                case SagaActDatum.STATE_COMPLETE:
                {
                    return makeSagaActStatusTextLabel(ZLoc.t("Main", "Complete"));
                }
                default:
                {
                    break;
                }
            }
            return makeSagaActStatusTextLabel(ZLoc.t("Dialogs", "ComingSoon"));
        }//end

        private Component  makeSagaActBox (SagaActDatum param1 )
        {
            _loc_2 = this.makeSagaActImageComponent(param1 );
            _loc_3 = this.makeSagaActStatusComponent(param1 );
            _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            ASwingHelper.setForcedSize(_loc_4, new IntDimension(150, _loc_2.getPreferredHeight() + 35));
            _loc_4.append(_loc_2);
            _loc_4.append(ASwingHelper.verticalStrut(3));
            _loc_4.append(_loc_3);
            _loc_4.append(ASwingHelper.verticalStrut(3));
            _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_5.append(_loc_4);
            _loc_5.append(ASwingHelper.horizontalStrut(10));
            return _loc_5;
        }//end

        private static double  getSagaActStatusFontSize ()
        {
            return TextFieldUtil.getLocaleFontSize(18, 14, [{locale:"ja", size:18}]);
        }//end

        private static String  getSagaActLevelUnlockText (SagaActDatum param1 )
        {
            return ZLoc.t("Dialogs", "UnlockAtLevel", {level:param1.unlockLevel.toString()});
        }//end

        private static Component  makeSagaActStatusTextLabel (String param1 )
        {
            param1 = TextFieldUtil.formatSmallCapsString(param1);
            _loc_2 = getSagaActStatusFontSize();
            _loc_2 = ASwingHelper.shrinkFontSizeToFit(150, param1, EmbeddedArt.titleFont, _loc_2, EmbeddedArt.newtitleSmallFilters);
            _loc_3 = ASwingHelper.makeLabel(param1 ,EmbeddedArt.titleFont ,_loc_2 ,EmbeddedArt.titleColor ,JLabel.CENTER );
            _loc_3.setTextFilters(EmbeddedArt.newtitleSmallFilters);
            return _loc_3;
        }//end

    }



