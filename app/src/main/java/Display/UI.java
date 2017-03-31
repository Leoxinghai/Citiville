package Display;

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
import Classes.gates.*;
import Classes.inventory.*;
import Classes.orders.Valentines2011.*;
import Classes.sim.*;
import Classes.util.*;
import Display.CollectionsUI.*;
import Display.DialogUI.*;
import Display.FactoryUI.*;
import Display.HunterAndPreyUI.*;
import Display.InventoryUI.*;
import Display.MarketUI.*;
import Display.MatchmakingUI.*;
import Display.NeighborUI.*;
import Display.PopulationUI.*;
import Display.Toaster.*;
import Display.UpgradesUI.*;
import Display.ValentineUI.*;
import Display.VisitorCenterUI.*;
import Display.aswingui.*;
import Display.hud.*;
import Engine.*;
import Engine.Events.*;
import Engine.Helpers.*;
import Engine.Init.*;
import Engine.Managers.*;
import Events.*;
import GameMode.*;
import Mechanics.*;
import Mechanics.GameMechanicInterfaces.*;
import Modules.automation.ui.*;
import Modules.bandits.*;
import Modules.franchise.display.*;
import Modules.guide.ui.*;
import Modules.matchmaking.*;
import Modules.pickthings.Display.*;
import Modules.quest.Display.QuestManager.*;
import Modules.quest.Managers.*;
import Modules.realtime.*;
import Modules.socialinventory.*;
import Modules.socialinventory.GameMode.*;
import Modules.stats.experiments.*;
import Modules.stats.types.*;
import Modules.storage.*;
import Modules.storage.modes.*;
import Modules.storage.ui.*;
import Modules.workers.*;
import Transactions.*;
import com.adobe.utils.*;
import com.greensock.*;
import com.zynga.skelly.util.*;
import fl.motion.easing.*;
//import flash.display.*;
//import flash.events.*;
//import flash.external.*;
//import flash.filters.*;
//import flash.geom.*;
//import flash.media.*;
//import flash.net.*;
//import flash.text.*;

//import flash.ui.Mouse;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.geom.*;

import com.xinghai.Debug;
import com.xinghai.chat.*;

import mx.controls.*;


    public class UI extends Sprite
    {
        private  String SEND_GIFT_TO ="sendGiftTo";
        private  String SEND_GIFT ="sendGift";
        private  String ADD_NEIGHBOR ="addNeighbor";
        private  String VISIT_NEIGHBOR ="visitNeighbor";
        private  String SEND_MESSAGE_NEIGHBOR ="sendMessageToNeighbor";
        public double screenWidth =760;
        public double screenHeight =595;
        public double screenScale =1;
        public int m_initialWidth ;
        public int m_initialHeight ;
        public FriendBar m_friendBar ;
        public int m_friendBarX ;
        private Bitmap m_neighborBackground ;
        private String m_actionMenuMode ;
        private FriendBarSlot m_clickedSlot ;
        public Sprite m_bottomUI ;
        public PerformanceTracker performanceTracker ;
        public DisplayObject getCoinsButton ;
        protected int m_cursorId ;
        public GameSprite m_takePhoto ;
        public GameSprite m_home ;
        private BuildModeMenu m_buildMenu ;
        private ToasterManager m_toasterManager ;
        private Sprite m_lightboxOverlay ;
        private Dictionary m_overlaySWFs ;
        private  double WIDTH_MULTIPLIER =1.1;
        private Vector2 m_prevStageSize =null ;
        private SettingsMenu m_settingsMenu ;
        private Sprite m_defaultModeButton ;
        private Sprite m_franchiseModeButton ;

        private SpriteButton m_buyModeButton ;

        private Sprite m_giftModeButton ;
        private String m_selectedActionMode ;
        public ExpandedMenu m_expandedMenu ;
        private boolean m_bExpandedMenuOpen =false ;
        public NeighborActionsMenu m_neighborActionsMenu ;
        public JWindow m_friendWindow ;
        public boolean bNeighborActionsMenuOn =false ;
        private  int NEIGHBOR_ACTION_OFFSET_Y =-115;
        protected ToolTipDialog m_toolTip =null ;
        protected IToolTipTarget m_toolTipTarget =null ;
        protected double m_tooltipTimer ;
        private boolean m_optimizeToolTip =false ;
        private double m_tooltipUpdate =0;
        protected  double DEFAULT_TOOL_TIP_APPEAR_TIME =0.5;
        protected  double MIN_TOOL_TIP_APPEAR_TIME =0.1;
        protected  int TOOLTIP_OFFSET_X =5;
        public Sprite m_friendSprite ;
        protected Sprite m_cityNameHolder ;
        protected JWindow m_cityNameWindow ;
        public CityNamePanel m_cityNamePanel ;
        protected Sprite m_cityHappinessHolder ;
        protected JWindow m_cityHappinessWindow ;

        protected CityHappinessPanel m_cityHappinessPanel ;

        protected NeighborVisitGiftHireMenu neighborActionButtonMenu ;
        protected boolean bNeighborButtonMenuOn ;
        protected Sprite m_leftQuestIconLayer ;
        protected Sprite m_rightQuestIconLayer ;
        private HUD m_hud ;
        private static  int NUM_EMPTY_NEIGHBORS =7;
        private static  int MAX_CURSOR_SIZE =50;
        private static  String UI_BUTTON_CLICKS_COUNTER ="ui_button_clicks";
        private static  double TOOLTIP_UPDATE_DELAY =1;
        public static  double DEFAULT_STATUS_TWEEN_DURATION =1.5;
        public static  int DEFAULT_STATUS_TWEEN_DISPLACEMENT =32;
        public static  int SCREEN_MENU_BUFFER =10;
        public static  int MASK_GAME =0;
        public static  int MASK_GAME_AND_BOTTOMBAR =1;
        public static  int MASK_ALL_UI =2;
        public static  int MASK_ALL_BUT_MARKET =3;
        public static boolean AUTO_CLOSE_MARKET =false ;
        private static Array m_frozenDispObj =null ;
        private static Bitmap m_frozenBitmap ;
        private static boolean m_frozen ;
        private static int m_freezeKey =0;
        private static Shape m_frozenGrayRect ;
        private static  double FROZEN_GRAY_PERCENT =0.33;
        private static Market m_marketWindow =null ;
        public static Catalog m_catalog =null ;
        public static Catalog m_oldCatalog =null ;
        public static Catalog m_newCatalog =null ;
        public static Catalog m_tabbedCatalog =null ;
        public static InventoryView m_inventory =null ;
        public static CollectionView m_collections =null ;
        public static StorageView m_storage =null ;
        private static GiftSellDialog m_giftSellWindow =null ;
        private static SlotDialog m_storageWindow =null ;
        private static Array m_popupQueue =new Array();
        private static Array m_immediateDialogs =new Array();
        private static int m_popupLock =0;
        private static Dialog m_currentDialog ;
        private static FrameLoaderDialog m_frameLoadingDialog =null ;
        public static Array m_cursors =new Array();
        public static Object m_currentCursor =null ;
        public static  int CURSOR_OFFSET_X =10;
        public static  int CURSOR_OFFSET_Y =10;
        public static  int MAX_CURSOR_DISTANCE =120;
        private static String m_buyableItemStr ="";
        private static String m_lastMarketEventItemName ;
        public static FranchiseMenu m_franchiseMenu ;
        private static boolean m_isModalDialogOpen =false ;
        public static Array m_interruptedDialogs =new Array ();
        public static ValentineDialog m_vDlg ;
        public static QuestManagerView m_questManagerView =null ;
        public static HunterDialog m_hunterDlg ;
        public static Array m_waitingPatrols =new Array ();
        public static MunicipalUpgradesDialog m_municipalUpgradesDialog ;
        public static MunInventoryUpgradesDialog m_munInventoryUpgradesDialog ;
        public static BusinessUpgradesDialog m_businessUpgradesDialog ;
        private static boolean m_isWindowModeDefault =false ;
        public static String m_questPopoutname ;
        public static  int DEFAULT_WIDTH =760;
        public static  int DEFAULT_HEIGHT =595;

        public ChatPanel chatpanel ;


        public void  UI ()
        {
            IntPoint p ;
            this.m_bottomUI = new Sprite();
            this.m_overlaySWFs = new Dictionary();
            this.m_leftQuestIconLayer = new Sprite();
            this.m_rightQuestIconLayer = new Sprite();
            Global.ui = this;
            AUTO_CLOSE_MARKET = true;
            this.m_initialWidth = DEFAULT_WIDTH;
            this.m_initialHeight = DEFAULT_HEIGHT;
            this.m_friendSprite = new Sprite();

            this.m_settingsMenu = new SettingsMenu();
            this.m_settingsMenu.y = -30;
            this.m_settingsMenu.x = 698;
            this.m_settingsMenu.addEventListener(UIEvent.SETTINGS_TOGGLE_CLICK, this.settingsToggleClickHandler);
            this.m_friendSprite.addChild(this.m_settingsMenu);


            this.m_toasterManager = new ToasterManager();
            this.m_toasterManager.x = 230;
            this.m_friendSprite.addChild(this.m_toasterManager);
            this.m_neighborBackground =(Bitmap) HUDThemeManager.getAsset(HUDThemeManager.NEIGHBOR_BAR_BG);
            p = HUDThemeManager.getOffset(HUDThemeManager.NEIGHBOR_BAR_BG);
            this.m_neighborBackground.y = p.y;
            this.m_neighborBackground.x = p.x;
            this.m_friendSprite.addChild(this.m_neighborBackground);
            this.m_bottomUI.addEventListener(MouseEvent.MOUSE_DOWN, this.onMaskBitmapEvent, false, 0, true);
            this.m_bottomUI.addEventListener(MouseEvent.MOUSE_OVER, this.onMaskBitmapEvent, false, 0, true);
            this.m_bottomUI.addEventListener(MouseEvent.MOUSE_MOVE, this.onMaskBitmapEvent, false, 0, true);
            this.m_bottomUI.addEventListener(MouseEvent.CLICK, this.onMaskBitmapEvent, false, 0, true);
            this.m_bottomUI.addEventListener(MouseEvent.MOUSE_OVER, this.onMouseOver, false, 0, true);
            this.m_bottomUI.addEventListener(MouseEvent.MOUSE_OUT, this.onMouseOut, false, 0, true);


            chatpanel = new ChatPanel();
            chatpanel.x = -200;
            chatpanel.y = 200;
	    //this.m_bottomUI.addChild(chatpanel);

            this.createModeButtons();
            this.m_friendBar = new FriendBar();
            this.m_friendBar.addEventListener(FriendBarSlotEvent.FRIEND_BAR_SLOT_CLICK, this.onNeighborBarClick, false, 0, true);
            this.m_friendBar.addEventListener(FriendBarSlotEvent.FRIEND_BAR_SLOT_ROLLOVER, this.onNeighborBarRollover, false, 0, true);
            this.m_friendBar.addEventListener(FriendBarEvent.LOADED, this.onFriendBarLoaded, false, 0, true);
            this.m_friendSprite.addEventListener(MouseEvent.ROLL_OUT, this.removeNeighborActions, false, 0, true);

            //this.m_bottomUI.addChild(this.m_friendSprite);


            this.m_friendSprite.y = 522;

            this.m_friendWindow = new JWindow(this.m_friendSprite);
            this.m_friendWindow.setActivable(false);
            this.m_friendWindow.setY(-4);

            ASwingHelper.prepare(this.m_friendWindow);
            this.m_friendWindow.show();


            m_bottomUI.x = 20;
            m_bottomUI.y = 20;

            //addChild(this.m_bottomUI);
            addChild(chatpanel);

            this.m_bottomUI.x = (this.m_initialWidth - this.m_bottomUI.width) / 2;

            this.m_hud = new HUD(this.m_leftQuestIconLayer, this.m_rightQuestIconLayer);
            addChildAt(this.m_hud, 0);
            addChildAt(this.m_leftQuestIconLayer, 1);
            addChildAt(this.m_rightQuestIconLayer, 2);

            GlobalEngine.stage.addEventListener(FullScreenEvent.FULL_SCREEN, this.onFullScreen);
            GlobalEngine.stage.addEventListener(Event.RESIZE, this.onResize);
            this.onResize(null);
            this.m_actionMenuMode = UIEvent.CURSOR;
            Global.stage.addEventListener(MouseEvent.CLICK, this.stageClickHandler);
            try
            {
                if (ExternalInterface.available)
                {
                    ExternalInterface.addCallback("onFeaturePromo", onFeaturePromo);
                }
            }
            catch (err:Error)
            {
            }
            m_isWindowModeDefault = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_DEPRECATED, ExperimentDefinitions.EXPERIMENT_WMODE_DEFAULT);
            this.m_optimizeToolTip = Global.experimentManager.inPerfExperiment(ExperimentDefinitions.EXPERIMENT_OPTIMIZE_FRAMERATE_Q3, ExperimentDefinitions.EXPERIMENT_OPTIMIZE_TOOLTIP_UPDATE);


            return;
        }//end

        private void  stageClickHandler (MouseEvent event )
        {
            this.reconfigureSettingsMenu();
            return;
        }//end

        private void  reconfigureSettingsMenu ()
        {
            if (this.m_settingsMenu.isEnabled)
            {
                this.settingsToggleClickHandler(null);
            }
            else if (!Global.guide.isActive())
            {
                this.m_settingsMenu.setEndPostTutorialTransition();
            }
            return;
        }//end

        public void  showTickerMessage (String param1 ,boolean param2 =false )
        {
            this.m_toasterManager.show(new TickerToaster(param1), !param2);
            return;
        }//end

        protected void  blockCityName (MouseEvent event )
        {
            GMDefault _loc_3 =null ;
            _loc_2 =Global.world.getTopGameMode ();
            if (_loc_2 instanceof GMDefault)
            {
                _loc_3 =(GMDefault) _loc_2;
                _loc_3.turnOffObject();
            }
            event.stopImmediatePropagation();
            event.stopPropagation();
            return;
        }//end

        private void  createModeButtons ()
        {
            double _loc_1 =0.75;
            int _loc_2 =652;
            int _loc_3 =15;
            double _loc_4 =68*_loc_1 ;
            double _loc_5 =68*_loc_1 ;


            this.m_defaultModeButton = new SpriteButton(EmbeddedArt.tool_offstate_btn, EmbeddedArt.tool_onstate_btn, EmbeddedArt.tool_onstate_btn, this.onDefaultModeClick);

            this.m_defaultModeButton.x = _loc_2;
            this.m_defaultModeButton.y = _loc_3;
            this.m_friendSprite.addChild(this.m_defaultModeButton);

            this.m_franchiseModeButton = new SpriteButton(EmbeddedArt.franchise_offstate_btn, EmbeddedArt.franchise_onstate_btn, EmbeddedArt.franchise_onstate_btn, this.onNewsModeClick);

            this.m_franchiseModeButton.x = _loc_2 + _loc_5 - 3;
            this.m_franchiseModeButton.y = _loc_3;
            this.m_friendSprite.addChild(this.m_franchiseModeButton);
            this.m_buyModeButton = new SpriteButton(EmbeddedArt.build_offstate_btn, EmbeddedArt.build_offstate_btn, EmbeddedArt.build_offstate_btn, this.onBuyModeClick, ZLoc.t("Main", "Build"));

            this.m_buyModeButton.x = _loc_2;
            this.m_buyModeButton.y = _loc_3 + _loc_4;
            this.m_friendSprite.addChild(this.m_buyModeButton);
            return;
        }//end

        private void  switchDefaultActionButton (String param1 )
        {
            return;
            if (this.m_defaultModeButton != null)
            {
                this.m_friendSprite.removeChild(this.m_defaultModeButton);
            }

            this.m_defaultModeButton = null;
            this.m_actionMenuMode = param1;
            switch(param1)
            {
                case UIEvent.CURSOR:
                {
                    this.m_defaultModeButton = new SpriteButton(EmbeddedArt.tool_offstate_btn, EmbeddedArt.tool_onstate_btn, EmbeddedArt.tool_onstate_btn, this.onDefaultModeClick);
                    break;
                }
                default:
                {
                    this.m_defaultModeButton = new SpriteButton(EmbeddedArt.tool_cancel_up, EmbeddedArt.tool_cancel_over, EmbeddedArt.tool_cancel_over, this.onDefaultModeClick);
                    break;
                    break;
                }
            }
            this.m_friendSprite.addChild(this.m_defaultModeButton);
            this.m_defaultModeButton.x = 652;
            this.m_defaultModeButton.y = 15;
            this.m_selectedActionMode = param1;
            return;
        }//end

        public boolean  displayExpandedMainMenu (Class param1 ,Catalog param2 )
        {

            if (!Global.world.isDefaultGameMode())
            {
                this.setActionMenuMode(UIEvent.CURSOR, true);
                return false;
            }
            if (Global.isVisiting())
            {
                return false;
            }
            if (this.m_expandedMenu == null || !this.m_bExpandedMenuOpen)
            {
                this.m_expandedMenu = new param1(this.m_bottomUI, false);
            }
            int _loc_3 =0;
            _loc_4 = this.m_expandedMenu.getDesiredPosition(param2 );
            this.m_expandedMenu.setX(_loc_4.x);
            _loc_3 = _loc_4.y;
            this.m_expandedMenu.setY(_loc_3 + 100);
            this.m_expandedMenu.alpha = 0;
            this.m_expandedMenu.show();
            TweenLite.to(this.m_expandedMenu, 0.2, {y:_loc_3, alpha:1});
            this.m_expandedMenu.addEventListener(UIEvent.ACTION_MENU_CLICK, this.onActionMenuClick, false, 0, true);
            this.m_expandedMenu.addEventListener(MouseEvent.MOUSE_DOWN, this.onExpandedMainMenuClick, false, 0, true);
            Global.stage.addEventListener(MouseEvent.MOUSE_DOWN, this.onActionMenuStageClick, false, 0, true);
            return true;
        }//end

        private void  onExpandedMainMenuClick (MouseEvent event )
        {
            event.stopImmediatePropagation();
            event.stopPropagation();
            return;
        }//end

        public void  hideExpandedMainMenu ()
        {
            if (this.m_expandedMenu != null)
            {
                TweenLite.to(this.m_expandedMenu, 0.2, {alpha:0, onComplete:this.onExpandedMenuHideTweenComplete});
                this.m_expandedMenu.removeEventListener(MouseEvent.MOUSE_DOWN, this.onExpandedMainMenuClick);
                Global.stage.removeEventListener(MouseEvent.MOUSE_DOWN, this.onActionMenuStageClick);
            }
            return;
        }//end

        private void  onExpandedMenuHideTweenComplete ()
        {
            this.m_expandedMenu.hide();
            return;
        }//end

        private void  onActionMenuClick (UIEvent event )
        {
            this.setActionMenuMode(event.label);
            this.hideExpandedMainMenu();
            this.m_bExpandedMenuOpen = false;
            return;
        }//end

        public void  updateToolIcon (GameMode param1 )
        {
            this.switchDefaultActionButton(param1.uiMode);
            return;
        }//end

        private void  onActionMenuStageClick (MouseEvent event )
        {
            if (event.target != this.m_expandedMenu && event.target != this.m_defaultModeButton && event.target != this.m_giftModeButton)
            {
                this.hideExpandedMainMenu();
                this.m_bExpandedMenuOpen = false;
            }
            return;
        }//end

        public void  resetActionMenu ()
        {
            this.setActionMenuMode(UIEvent.CURSOR);
            return;
        }//end

        private void  setActionMenuMode (String param1 ,boolean param2 =false )
        {
            boolean _loc_3 =true ;
            switch(param1)
            {
                case UIEvent.MOVE:
                {
                    Global.world.addGameMode(new GMEditMove());
                    Sounds.play("click2");
                    break;
                }
                case UIEvent.ROTATE:
                {
                    Global.world.addGameMode(new GMEditRotate());
                    Sounds.play("click2");
                    break;
                }
                case UIEvent.REMOVE:
                {
                    Global.world.addGameMode(new GMEditSell());
                    Sounds.play("click2");
                    break;
                }
                case UIEvent.AUTO:
                {
                    _loc_3 = ActionAutomationManager.instance.openAutomationUI();
                    if (_loc_3)
                    {
                        Sounds.play("click2");
                    }
                    break;
                }
                case UIEvent.STORE:
                {
                    Global.world.addGameMode(new GMEditStore());
                    Sounds.play("click2");
                    break;
                }
                case UIEvent.HEARTS:
                {
                    Global.world.addGameMode(new GMEditSocialInventory());
                    Sounds.play("click2");
                    break;
                }
                case UIEvent.REMODEL:
                {
                    Global.world.addGameMode(new GMRemodel());
                    Sounds.play("click2");
                    break;
                }
                case UIEvent.CURSOR:
                {
                    Global.world.setDefaultGameMode();
                    if (!param2)
                    {
                        Sounds.play("click2");
                    }
                    break;
                }
                case UIEvent.SHOW_COLLECTIONS:
                {
                    UI.displayCollections();
                    _loc_3 = false;
                    break;
                }
                case UIEvent.SHOW_INVENTORY:
                {
                    UI.displayInventory();
                    _loc_3 = false;
                    break;
                }
                case UIEvent.SHOW_FRANCHISE:
                {
                    _loc_3 = false;
                    if (Global.franchiseManager.getFranchiseCount())
                    {
                        UI.displayNewFranchise(false);
                    }
                    else
                    {
                        displayMessage(ZLoc.t("Dialogs", "NoFranchises"));
                    }
                    break;
                }
                default:
                {
                    break;
                }
            }
            if (_loc_3 == true)
            {
                this.switchDefaultActionButton(param1);
            }
            else
            {
                Sounds.play("click2");
            }
            return;
        }//end

        public ToasterManager  toaster ()
        {
            return this.m_toasterManager;
        }//end

        public CityHappinessPanel  happinessPanel ()
        {
            return this.m_cityHappinessPanel;
        }//end

        public void  showBuildMenu (boolean param1 )
        {
            return;
        }//end

        public void  setEditMode (boolean param1 )
        {
            MapResource _loc_4 =null ;
            _loc_2 =Global.world.isEditMode ;
            if (!_loc_2 && param1)
            {
                Global.world.addGameMode(new GMDefault());
                this.showBuildMenu(true);
            }
            if (_loc_2 && !param1)
            {
                this.showBuildMenu(false);
                Global.world.addGameMode(new GMPlay());
            }
            _loc_3 =Global.world.getObjectsByClass(MapResource );
            for(int i0 = 0; i0 < _loc_3.size(); i0++)
            {
            	_loc_4 = _loc_3.get(i0);

                _loc_4.onEditModeSwitch();
            }
            return;
        }//end

        private void  initializeToolTip (IToolTipTarget param1 )
        {
            if (!this.m_toolTip)
            {
                this.m_toolTip = new ToolTipDialog(param1.getToolTipStatus());
                Global.stage.addChild(this.m_toolTip);
                this.m_toolTip.show();
                boolean _loc_2 =false ;
                this.m_toolTip.mouseChildren = false;
                this.m_toolTip.mouseEnabled = _loc_2;
            }
            return;
        }//end

        public void  setToolTip (IToolTipTarget param1 )
        {
            if (this.m_toolTip)
            {
                this.m_toolTip.loadToolTipForTarget(param1);
            }
            boolean _loc_2 =false ;
            this.m_toolTip.mouseChildren = false;
            this.m_toolTip.mouseEnabled = _loc_2;
            return;
        }//end

        private void  setToolTipHelper (IToolTipTarget param1 )
        {
            this.initializeToolTip(param1);
            _loc_2 = ToolTipSchema.getSchemaForObject(param1);
            this.m_toolTip.setToolTipSchema(_loc_2);
            this.setToolTip(param1);
            return;
        }//end

        public void  cleanUpToolTip ()
        {
            if (this.m_toolTip)
            {
                this.m_toolTip.parent.removeChild(this.m_toolTip);
            }
            this.m_toolTip = null;
            this.m_toolTipTarget = null;
            return;
        }//end

        public void  updateToolTipPosition ()
        {
            Point _loc_1 =null ;
            Vector2 _loc_2 =null ;
            Vector3 _loc_3 =null ;
            double _loc_4 =0;
            Vector3 _loc_5 =null ;
            Vector2 _loc_6 =null ;
            if (this.m_toolTip && this.m_toolTipTarget)
            {
                if (this.m_toolTipTarget.toolTipFollowsMouse())
                {
                    _loc_1 = new Point(Global.stage.mouseX, Global.stage.mouseY);
                    _loc_2 = this.getCurrentCursorDimensions();
                    if (_loc_2)
                    {
                        _loc_1.x = _loc_1.x + _loc_2.x;
                    }
                    _loc_1.x = _loc_1.x + this.TOOLTIP_OFFSET_X;
                    _loc_1.x = _loc_1.x + CURSOR_OFFSET_X;
                    _loc_1.y = _loc_1.y + CURSOR_OFFSET_Y;
                    if (_loc_1.x + CURSOR_OFFSET_X + this.m_toolTip.width > Global.ui.screenWidth)
                    {
                        _loc_1.x = Global.stage.mouseX - CURSOR_OFFSET_X - this.m_toolTip.width;
                    }
                    if (_loc_1.y + CURSOR_OFFSET_Y + this.m_toolTip.height > Global.ui.screenHeight)
                    {
                        _loc_1.y = Global.stage.mouseY - CURSOR_OFFSET_Y - this.m_toolTip.height;
                    }
                }
                else
                {
                    _loc_3 = this.m_toolTipTarget.getDimensions();
                    if (_loc_3)
                    {
                        _loc_4 = Global.gameSettings().getNumber("maxZoom");
                        _loc_5 = this.m_toolTipTarget.getToolTipPosition();
                        _loc_1 = IsoMath.tilePosToPixelPos(_loc_5.x, _loc_5.y);
                        _loc_1 = IsoMath.viewportToStage(_loc_1);
                        _loc_6 = this.m_toolTipTarget.getToolTipScreenOffset();
                        if (_loc_6)
                        {
                            _loc_1.x = _loc_1.x + _loc_6.x;
                            _loc_1.y = _loc_1.y + _loc_6.y;
                        }
                        _loc_1.x = _loc_1.x - this.m_toolTip.width / 2;
                        _loc_1.y = _loc_1.y - (_loc_3.y * GlobalEngine.viewport.getZoom() / _loc_4 + this.m_toolTip.height);
                        _loc_1.y = _loc_1.y - this.m_toolTipTarget.getToolTipFloatOffset() * GlobalEngine.viewport.getZoom() / 4;
                        if (this.m_toolTipTarget instanceof ConstructionSite && (this.m_toolTipTarget as ConstructionSite).isAccelerating && (this.m_toolTipTarget as ConstructionSite).actionBar)
                        {
                            _loc_1.y = (this.m_toolTipTarget as ConstructionSite).actionBar.y - this.m_toolTip.height;
                        }
                        if (this.m_toolTipTarget instanceof ConstructionSite && (this.m_toolTipTarget as ConstructionSite).targetClass == Bridge)
                        {
                            _loc_1.x = _loc_1.x + (_loc_3.x / 2 - this.m_toolTip.width / 3) * GlobalEngine.viewport.getZoom() / _loc_4;
                            _loc_1.y = _loc_1.y + (_loc_3.y / 2 + this.m_toolTip.height) * GlobalEngine.viewport.getZoom() / _loc_4;
                        }
                    }
                }
                if (_loc_1 && (_loc_1.x != this.m_toolTip.x || _loc_1.y != this.m_toolTip.y))
                {
                    this.m_toolTip.x = _loc_1.x;
                    this.m_toolTip.y = _loc_1.y;
                }
                if (this.m_toolTip.y < 0)
                {
                    this.m_toolTip.y = 0;
                }
                else if (this.m_toolTip.y + this.m_toolTip.height > Global.ui.screenHeight)
                {
                    this.m_toolTip.y = Global.ui.screenHeight - this.m_toolTip.height;
                }
                if (this.m_toolTip.x < 0)
                {
                    this.m_toolTip.x = 0;
                }
                else if (this.m_toolTip.x + this.m_toolTip.width > Global.ui.screenWidth)
                {
                    this.m_toolTip.x = Global.ui.screenWidth - this.m_toolTip.width;
                }
            }
            return;
        }//end

        public void  showToolTip (IToolTipTarget param1 )
        {
            if (param1 != this.m_toolTipTarget && param1.getToolTipVisibility())
            {
                this.setToolTipHelper(param1);
                this.m_tooltipTimer = 0;
                this.m_toolTipTarget = param1;
            }
            return;
        }//end

        public void  putUnderToolTip (DisplayObject param1 )
        {
            if (this.m_toolTip && param1)
            {
                if (Global.stage.contains(this.m_toolTip) && Global.stage.contains(param1))
                {
                    if (Global.stage.getChildIndex(this.m_toolTip) < Global.stage.getChildIndex(param1))
                    {
                        Global.stage.swapChildren(this.m_toolTip, param1);
                    }
                }
            }
            return;
        }//end

        public void  hideToolTip (IToolTipTarget param1 )
        {
            if (param1 == this.m_toolTipTarget)
            {
                this.hideAnyToolTip();
            }
            return;
        }//end

        public void  hideAnyToolTip ()
        {
            GameObject _loc_1 =null ;
            this.m_tooltipTimer = 0;
            this.m_toolTipTarget = null;
            if (Global.world.getTopGameMode() instanceof GMObjectMove)
            {
                _loc_1 = (Global.world.getTopGameMode() as GMObjectMove).getSelectedObject();
                if (_loc_1)
                {
                    Global.ui.showToolTip(_loc_1);
                }
            }
            return;
        }//end

        public void  updateToolTip (double param1 )
        {
            if (this.m_toolTipTarget)
            {
                this.m_tooltipTimer = this.m_tooltipTimer + param1;
                if (this.m_optimizeToolTip)
                {
                    this.m_tooltipUpdate = this.m_tooltipUpdate - param1;
                    if (this.m_tooltipUpdate <= 0)
                    {
                        this.setToolTipHelper(this.m_toolTipTarget);
                        this.m_tooltipUpdate = TOOLTIP_UPDATE_DELAY;
                    }
                }
                else
                {
                    this.setToolTipHelper(this.m_toolTipTarget);
                }
                this.updateToolTipPosition();
            }
            else
            {
                this.m_tooltipUpdate = 0;
            }
            _loc_2 =Global.gameSettings().getAttribute("toolTipAppearTime",this.DEFAULT_TOOL_TIP_APPEAR_TIME );
            _loc_2 = _loc_2 > this.MIN_TOOL_TIP_APPEAR_TIME ? (_loc_2) : (this.MIN_TOOL_TIP_APPEAR_TIME);
            if (this.m_toolTipTarget && this.m_tooltipTimer >= _loc_2)
            {
                if (this.m_toolTip && this.m_toolTip.hasData())
                {
                    this.m_toolTip.visible = true;
                }
            }
            else if (this.m_toolTip)
            {
                this.m_toolTip.visible = false;
            }
            if (this.m_toolTipTarget instanceof MapResource && (this.m_toolTipTarget as MapResource).isBeingDragged())
            {
                if (this.m_toolTip && this.m_toolTip.hasData())
                {
                    this.m_toolTip.visible = true;
                }
            }
            return;
        }//end

        public int  getCursorId ()
        {
            return this.m_cursorId;
        }//end

        public void  setCursorId (int param1 )
        {
            this.m_cursorId = param1;
            return;
        }//end

        protected void  createQuickButtons ()
        {
            int _loc_1 =400;
            this.m_home = new SpriteButton(EmbeddedArt.home_Button, EmbeddedArt.home_Button, EmbeddedArt.home_Button, this.onGoHome);
            this.m_home.x = -45;
            this.m_home.y = -45;
            this.m_home.visible = false;
            this.m_home.toolTip = ZLoc.t("Main", "ReturnHome_ToolTip");
            this.m_settingsMenu.addChild(this.m_home);
            _loc_2 = new StrokeTextField(0,0.8);
            _loc_2.toolTipText = ZLoc.t("Main", "GoHome");
            _loc_2.setTextFormat(new TextFormat(EmbeddedArt.defaultFontNameBold, 14, 16777215, true));
            _loc_2.setTextFormat(_loc_2.defaultTextFormat);
            _loc_2.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            _loc_2.autoSize = TextFieldAutoSize.LEFT;
            _loc_2.x = (this.m_home.width - _loc_2.textWidth) / 2;
            _loc_2.y = -_loc_2.textHeight;
            this.m_home.addChild(_loc_2);
            return;
        }//end

        protected void  onMaskBitmapEvent (MouseEvent event )
        {
            DisplayObject _loc_4 =null ;
            int _loc_5 =0;
            int _loc_6 =0;
            _loc_2 = this.m_bottomUI.numChildren ;
            int _loc_3 =0;
            while (_loc_3 < _loc_2)
            {

                _loc_4 = this.m_bottomUI.getChildAt(_loc_3);
                if (_loc_4 == this.m_friendSprite)
                {
                }
                else if (_loc_4.visible == true)
                {
                    return;
                }
                _loc_3++;
            }
            if (this.m_neighborBackground)
            {
                _loc_5 = this.m_neighborBackground.bitmapData.getPixel32(this.m_neighborBackground.mouseX, this.m_neighborBackground.mouseY);
                _loc_6 = _loc_5 >> 24 & 255;
                if (_loc_6 < 32)
                {
                    GlobalEngine.viewport.dispatchEvent(new MouseEvent(event.type, event.bubbles, event.cancelable, GlobalEngine.viewport.mouseX, GlobalEngine.viewport.mouseY, GlobalEngine.viewport, event.ctrlKey, event.altKey, event.shiftKey, event.buttonDown, event.delta));
                }
                else if (UI.hasCursor(this.m_cursorId) == false)
                {
                    this.m_cursorId = UI.setCursor(null);
                }
            }
            return;
        }//end

        protected void  onMouseOver (Event event )
        {
            this.m_tooltipTimer = 0;
            if (!Global.ui.turnOffHighlightedObject())
            {
                if (UI.hasCursor(this.m_cursorId) == false)
                {
                    this.m_cursorId = UI.setCursor(null);
                }
            }
            UI.pushBlankCursor();
            return;
        }//end

        protected void  onMouseOut (Event event )
        {
            if (!this.bNeighborButtonMenuOn)
            {
                this.m_tooltipTimer = 0;
                UI.removeCursor(this.m_cursorId);
                UI.popBlankCursor();
            }
            return;
        }//end

        public void  setFriendBarPos (int param1 )
        {


            return;
        }//end

        private void  onFriendBarLoaded (FriendBarEvent event )
        {
            double _loc_2 =7;
            _loc_3 = FriendBar.SLOT_WIDTH+5;
            _loc_4 = _loc_2*_loc_3 -event.length ;


            return;
        }//end

        private void  onFullscreenClick (MouseEvent event )
        {
            SettingsMenu.handleFullscreenToggle();
            return;
        }//end

        private void  onZoomInClick (MouseEvent event )
        {
            double _loc_2 =0;
            if (Global.world.isZoomEnabled())
            {
                _loc_2 = GlobalEngine.viewport.getZoom();
                _loc_2 = _loc_2 + Global.gameSettings().getNumber("zoomStep");
                _loc_2 = Math.min(Global.gameSettings().getNumber("maxZoom"), _loc_2);
                _loc_2 = Math.max(Global.gameSettings().getNumber("minZoom"), _loc_2);
                StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.SETTINGS, "zoom_in");
                if (GlobalEngine.viewport.getZoom() == _loc_2)
                {
                    return;
                }
                GlobalEngine.viewport.setZoom(_loc_2);
                Global.world.updateWorldObjectCulling(GameWorld.CULL_ZOOM_IN);

            }
            return;
        }//end

        private void  onZoomOutClick (MouseEvent event )
        {
            double _loc_2 =0;
            Vector2 _loc_3 =null ;
            if (Global.world.isZoomEnabled())
            {
                _loc_2 = GlobalEngine.viewport.getZoom();
                _loc_2 = _loc_2 - Global.gameSettings().getNumber("zoomStep");
                _loc_2 = Math.min(Global.gameSettings().getNumber("maxZoom"), _loc_2);
                _loc_2 = Math.max(Global.gameSettings().getNumber("minZoom"), _loc_2);
                StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.SETTINGS, "zoom_out");
                if (GlobalEngine.viewport.getZoom() == _loc_2)
                {
                    return;
                }
                GlobalEngine.viewport.setZoom(_loc_2);
                _loc_3 = GlobalEngine.viewport.getScrollPosition();
                _loc_3 = GameMode.GameMode.limitBackgroundScrolling(_loc_3);
                GlobalEngine.viewport.setScrollPosition(_loc_3);
                Global.world.updateWorldObjectCulling(GameWorld.CULL_ZOOM_OUT);

            }
            return;
        }//end

        private void  onTakePhotoClick (MouseEvent event )
        {
            Global.world.addGameMode(new GMSnapShot(), true);
            return;
        }//end

        private void  onAllAssetsLoaded (Event event )
        {
            dispatchEvent(new Event(Event.COMPLETE));
            return;
        }//end

        private void  onNeighborBarVisitClick (FriendTradeEvent event )
        {
            _loc_2 = event.m_friend.uid ;
            if (_loc_2 != Global.player.uid && _loc_2 != Global.getVisiting())
            {
                if (!resolveIfAssetsHaveNotLoaded(DelayedAssetLoader.GENERIC_DIALOG_ASSETS))
                {
                    if (event.m_friend.m_firstTimeVisit)
                    {

                    }
                    visitNeighbor(_loc_2);
                }
                m_catalog = null;
            }
            if (SocialInventoryManager.isFeatureAvailable())
            {
                if (event.m_friend.helpRequests > 0)
                {
                    StatsManager.sample(100, StatsCounterType.NEIGHBOR_BAR, StatsKingdomType.VISIT_NEIGHBOR, "with_outstanding_help_requests", "", "", "", event.m_friend.helpRequests);
                }
            }
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.NEIGHBOR_LADDER, "visit", MatchmakingManager.instance.getNeighborStatsId(event.m_friend.uid), event.m_friend.uid);
            return;
        }//end

        private void  onNeighborBarGiftClick (FriendTradeEvent event )
        {
            FrameManager.navigateTo("Gifts.php?ref=neighbor_ladder");
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.NEIGHBOR_LADDER, "gift");
            return;
        }//end

        private void  onNeighborBarHireClick (FriendTradeEvent event )
        {
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.NEIGHBOR_LADDER, "hire");
            return;
        }//end

        private void  onNeighborBarFriendClick (FriendTradeEvent event )
        {
            MatchmakingManager.instance.addFriend(event.m_friend.uid);
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.NEIGHBOR_LADDER, "friend", MatchmakingManager.instance.getNeighborStatsId(event.m_friend.uid), event.m_friend.uid);
            return;
        }//end

        private void  onNeighborBarInstantGiftClick (FriendTradeEvent event )
        {
            MatchmakingManager.instance.sendFreeGift(event.m_friend.uid);
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.NEIGHBOR_LADDER, "instant_gift", MatchmakingManager.instance.getNeighborStatsId(event.m_friend.uid), event.m_friend.uid);
            return;
        }//end

        private void  onNeighborBarGetHelpClick (FriendTradeEvent event )
        {
            GenericDialog _loc_3 =null ;
            _loc_2 = MatchmakingManager.instance.getGatedWorldObjects();
            if (_loc_2.length())
            {
                _loc_3 = new BuildingRequestDialog(_loc_2, "ask_for_parts");
            }
            else
            {
                _loc_3 = new ActionHelpDialog("BuildingRequestFail");
            }
            displayPopup(_loc_3);
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.NEIGHBOR_LADDER, "get_help", MatchmakingManager.instance.getNeighborStatsId(event.m_friend.uid), event.m_friend.uid);
            return;
        }//end

        private void  onNeighborBarClick (FriendBarSlotEvent event )
        {
            _loc_2 = this.m_friendSprite.globalToLocal(event.friendPoint );
            this.m_friendWindow.setActive(false);
            if (!event.slot.m_friend.empty && event.slot.m_uid != Global.player.uid)
            {
                if (this.bNeighborButtonMenuOn)
                {
                    if (event.slot.m_friend == this.neighborActionButtonMenu.friend)
                    {
                        this.removeNeighborListeners();
                        this.m_friendSprite.removeChild(this.neighborActionButtonMenu);
                        this.bNeighborButtonMenuOn = false;
                    }
                    else
                    {
                        this.neighborActionButtonMenu.x = _loc_2.x + 40;
                        this.neighborActionButtonMenu.y = event.slot.y + 15;
                        event.stopImmediatePropagation();
                        event.stopPropagation();
                        this.neighborActionButtonMenu.friend = event.slot.m_friend;
                    }
                }
                else
                {
                    this.neighborActionButtonMenu = new NeighborVisitGiftHireMenu(event.slot.m_friend);
                    this.neighborActionButtonMenu.addEventListener(FriendTradeEvent.GIFT, this.onNeighborBarGiftClick, false, 0, true);
                    this.neighborActionButtonMenu.addEventListener(FriendTradeEvent.HIRE, this.onNeighborBarHireClick, false, 0, true);
                    this.neighborActionButtonMenu.addEventListener(FriendTradeEvent.VISIT, this.onNeighborBarVisitClick, false, 0, true);
                    this.neighborActionButtonMenu.addEventListener(FriendTradeEvent.FRIEND, this.onNeighborBarFriendClick, false, 0, true);
                    this.neighborActionButtonMenu.addEventListener(FriendTradeEvent.INSTANT_GIFT, this.onNeighborBarInstantGiftClick, false, 0, true);
                    this.neighborActionButtonMenu.addEventListener(FriendTradeEvent.GET_HELP, this.onNeighborBarGetHelpClick, false, 0, true);
                    this.neighborActionButtonMenu.addEventListener(MouseEvent.MOUSE_OVER, this.onMouseOver, false, 0, true);
                    this.neighborActionButtonMenu.addEventListener(MouseEvent.MOUSE_OUT, this.onMouseOut, false, 0, true);
                    this.neighborActionButtonMenu.x = _loc_2.x + 40;
                    this.neighborActionButtonMenu.y = -this.neighborActionButtonMenu.anchorHeight;
                    this.bNeighborButtonMenuOn = true;
                    this.m_friendSprite.addChild(this.neighborActionButtonMenu);
                }
                StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.NEIGHBOR_LADDER, "on_picture", MatchmakingManager.instance.getNeighborStatsId(event.slot.m_friend.uid));
            }
            return;
        }//end

        private void  onNeighborBarRollover (FriendBarSlotEvent event )
        {
            if (this.neighborActionButtonMenu && this.m_friendSprite.contains(this.neighborActionButtonMenu))
            {
                this.removeNeighborListeners();
                this.m_friendSprite.removeChild(this.neighborActionButtonMenu);
            }
            this.bNeighborButtonMenuOn = false;
            Global.stage.addEventListener(MouseEvent.CLICK, this.onClickedOutsideNeighbors, false, 0, true);
            return;
        }//end

        private void  removeNeighborListeners ()
        {
            this.neighborActionButtonMenu.removeEventListener(FriendTradeEvent.GIFT, this.onNeighborBarGiftClick);
            this.neighborActionButtonMenu.removeEventListener(FriendTradeEvent.HIRE, this.onNeighborBarHireClick);
            this.neighborActionButtonMenu.removeEventListener(FriendTradeEvent.VISIT, this.onNeighborBarVisitClick);
            this.neighborActionButtonMenu.removeEventListener(FriendTradeEvent.FRIEND, this.onNeighborBarFriendClick);
            this.neighborActionButtonMenu.removeEventListener(FriendTradeEvent.INSTANT_GIFT, this.onNeighborBarInstantGiftClick);
            this.neighborActionButtonMenu.removeEventListener(FriendTradeEvent.GET_HELP, this.onNeighborBarGetHelpClick);
            this.neighborActionButtonMenu.removeEventListener(MouseEvent.MOUSE_OVER, this.onMouseOver);
            return;
        }//end

        private void  onClickedOutsideNeighbors (MouseEvent event )
        {
            this.removeNeighborActions(event);
            return;
        }//end

        private void  removeNeighborActions (MouseEvent event )
        {
            if (this.neighborActionButtonMenu && this.m_friendSprite.contains(this.neighborActionButtonMenu))
            {
                this.removeNeighborListeners();
                this.m_friendSprite.removeChild(this.neighborActionButtonMenu);
                this.bNeighborButtonMenuOn = false;
            }
            return;
        }//end

        private void  onMenuClicked (MouseEvent event )
        {
            String _loc_2 =null ;
            URLRequest _loc_3 =null ;
            URLVariables _loc_4 =null ;
            if (Global.disableMenu)
            {
                return;
            }
            _loc_5 = event.targetisContextMenuItem? (event.target as ContextMenuItem) : (null);
            if ((event.target instanceof ContextMenuItem ? (event.target as ContextMenuItem) : (null)) && _loc_5.action)
            {
                switch(_loc_5.action)
                {
                    case this.SEND_GIFT_TO:
                    {
                        _loc_4 = new URLVariables();
                        _loc_4.rId = Global.player.snUser.uid;
                        GlobalEngine.socialNetwork.redirect("gifts.php?ref=neighborBar&giftRecipient=" + this.m_clickedSlot.uid);
                        break;
                    }
                    case this.SEND_GIFT:
                    {
                        _loc_4 = new URLVariables();
                        _loc_4.rId = Global.player.snUser.uid;
                        GlobalEngine.socialNetwork.redirect("gifts.php?ref=neigborBarNoRecipient");
                        break;
                    }
                    case this.ADD_NEIGHBOR:
                    {
                        _loc_4 = new URLVariables();
                        _loc_4.rId = this.m_clickedSlot.uid;
                        _loc_4.src = Global.player.snUser.uid;
                        GlobalEngine.socialNetwork.redirect("invite.php?ref=neighborsInvitePage&", _loc_4);
                        break;
                    }
                    case this.VISIT_NEIGHBOR:
                    {
                        visitNeighbor(this.m_clickedSlot.uid);
                        break;
                    }
                    case this.SEND_MESSAGE_NEIGHBOR:
                    {
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return;
        }//end

        private void  onLoadComplete (Event event )
        {
            event.currentTarget.content.width = 760;
            return;
        }//end

        public double  leftSideQuestOffset ()
        {
            if (this.m_leftQuestIconLayer)
            {
                return this.m_leftQuestIconLayer.x;
            }
            return 0;
        }//end

        private void  onFullScreen (FullScreenEvent event )
        {
            this.onResize(null);
            return;
        }//end

        private void  onResize (Event event )
        {
            double _loc_8 =0;
            double _loc_9 =0;
            double _loc_10 =0;
            double _loc_11 =0;
            double _loc_12 =0;
            Quest _loc_13 =null ;
            if (UI.isScreenFrozen())
            {
                return;
            }
            _loc_2 = GlobalEngine.stage ;
            if (_loc_2.displayState == StageDisplayState.FULL_SCREEN)
            {
                this.screenScale = 1;
                this.screenWidth = _loc_2.fullScreenWidth;
                this.screenHeight = _loc_2.fullScreenHeight;
            }
            else
            {
                this.screenScale = Math.min(1, _loc_2.stageHeight / DEFAULT_HEIGHT);
                this.screenWidth = _loc_2.stageWidth / this.screenScale;
                this.screenHeight = this.screenScale <= 1 ? (DEFAULT_HEIGHT) : (_loc_2.stageHeight);
            }
            _loc_3 = GlobalEngine.viewport.getScrollPosition ();
            if (this.m_prevStageSize)
            {
                _loc_8 = this.screenWidth - this.m_prevStageSize.x;
                _loc_9 = this.screenHeight - this.m_prevStageSize.y;
                _loc_10 = GlobalEngine.viewport.getZoom();
                _loc_11 = _loc_8 * (_loc_10 - 1) / (_loc_10 * 2);
                _loc_12 = _loc_9 * (_loc_10 - 1) / (_loc_10 * 2);
                _loc_3.x = _loc_3.x + _loc_11;
                _loc_3.y = _loc_3.y + _loc_12;
            }
            _loc_3 = GameMode.GameMode.limitBackgroundScrolling(_loc_3);
            GlobalEngine.viewport.setScrollPosition(_loc_3);
            this.m_prevStageSize = new Vector2(this.screenWidth, this.screenHeight);
            this.m_bottomUI.y = this.screenScale < 1 ? (Global.ui.screenHeight - 600) : (_loc_2.stageHeight - 600);
            _loc_4 = this.x ;
            this.x = (this.screenWidth - this.m_initialWidth) / 2;
            _loc_5 = this.x ;
            _loc_6 = _loc_4-_loc_5 ;
            _loc_7 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_ICON_ALIGNMENT );
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_ICON_ALIGNMENT) == 1 || _loc_7 == 3)
            {
                this.m_leftQuestIconLayer.x = this.m_leftQuestIconLayer.x + _loc_6;
            }
            if (_loc_7 == 2 || _loc_7 == 3)
            {
                this.m_rightQuestIconLayer.x = this.m_rightQuestIconLayer.x - _loc_6;
            }
            if (event !== null)
            {
                updateZoom(GlobalEngine.viewport.getZoom(), true);
            }
            Global.hud.onResize();
            if (event != null && Global.guide != null)
            {
                Global.guide.onResize();
            }
            if (Global.questManager)
            {
                for(int i0 = 0; i0 < Global.questManager.getActiveQuests().size(); i0++)
                {
                	_loc_13 = Global.questManager.getActiveQuests().get(i0);

                    if (_loc_13 instanceof GameQuest)
                    {
                        ((GameQuest)_loc_13).onResize();
                    }
                }
            }
            return;
        }//end

        public void  delayPopup (double param1 ,DisplayObject param2 ,boolean param3 =true ,String param4 ="",boolean param5 =false )
        {
            if (param2 == null)
            {
                return;
            }
            param2.name = param4;
            setTimeout(this.doDelayedPopup, param1, param2, param3, param5, Global.isVisiting());
            return;
        }//end

        private void  doDelayedPopup (DisplayObject param1 ,boolean param2 ,boolean param3 ,boolean param4 )
        {
            if (param1 == null)
            {
                return;
            }
            if (Global.isVisiting() != param4)
            {
                return;
            }
            displayPopup(param1, param2, param1.name, param3);
            return;
        }//end

        public void  displayLightbox (boolean param1 ,int param2 =0,boolean param3 =false )
        {
            Point origin ;
            Graphics g ;
            int ind ;
            show = param1;
            level = param2;
            transparent = param3;
            if (show && this.m_lightboxOverlay == null)
            {
                this.m_lightboxOverlay = new Sprite();
                switch(level)
                {
                    case MASK_ALL_BUT_MARKET:
                    {
                        ind = Global.ui.m_bottomUI.getChildIndex(m_catalog);
                        Global.ui.m_bottomUI.addChildAt(this.m_lightboxOverlay, (ind - 1));
                        this.m_lightboxOverlay.addEventListener(MouseEvent.CLICK, this.onMaskClicked, false, 0, true);
                    }
                    case MASK_GAME:
                    {
                        addChild(this.m_lightboxOverlay);
                        setChildIndex(this.m_lightboxOverlay, (numChildren - 1));
                        this.m_lightboxOverlay.addEventListener(MouseEvent.CLICK, this.onMaskClicked, false, 0, true);
                        break;
                    }
                    default:
                    {
                        addChildAt(this.m_lightboxOverlay, 0);
                        break;
                    }
                    case MASK_ALL_UI:
                    {
                        addChildAt(this.m_lightboxOverlay, (numChildren - 1));
                        this.m_lightboxOverlay.addEventListener(MouseEvent.CLICK, this.onMaskClicked, false, 0, true);
                        break;
                    }
                }
                origin = this.m_lightboxOverlay.globalToLocal(new Point(0, 0));
                g = this.m_lightboxOverlay.graphics;
                g.clear();
                if (transparent)
                {
                    g.beginFill(0, 0);
                }
                else
                {
                    g.beginFill(0, 0.75);
                }
                g.drawRect(origin.x, origin.y, this.screenWidth, this.screenHeight);
                g.endFill();
                TweenLite.from(this.m_lightboxOverlay, 0.3, {alpha:0});
            }
            if (!show && this.m_lightboxOverlay != null)
            {
void                 TweenLite .to (this .m_lightboxOverlay ,0.3,{0alpha , onComplete ()
            {
                removeChild(m_lightboxOverlay);
                m_lightboxOverlay.removeEventListener(MouseEvent.CLICK, onMaskClicked);
                m_lightboxOverlay = null;
                return;
            }//end
            });
            }
            return;
        }//end

        private void  onMaskClicked (MouseEvent event )
        {
            event.stopPropagation();
            return;
        }//end

        protected Vector2  getCurrentCursorDimensions ()
        {
            if (m_currentCursor && m_currentCursor.displayObj)
            {
                return new Vector2(m_currentCursor.displayObj.width, m_currentCursor.displayObj.height);
            }
            return null;
        }//end

        private void  settingsToggleClickHandler (UIEvent event )
        {
            _loc_2 =Global.ui.bottomUI.getChildByName("HUDCacheComponent");
            if (_loc_2 != null)
            {
                _loc_2.visible = false;
            }
            this.m_settingsMenu.setInTransition();
            if (this.m_settingsMenu.isEnabled)
            {
                TweenLite.to(this.m_settingsMenu, 0.5, {y:-30, onComplete:this.settingsMenuTweenCompleteHandler});
            }
            else
            {
                TweenLite.to(this.m_settingsMenu, 0.5, {y:-117, onComplete:this.settingsMenuTweenCompleteHandler});
            }
            return;
        }//end

        private void  settingsMenuTweenCompleteHandler ()
        {
            this.m_settingsMenu.isEnabled = !this.m_settingsMenu.isEnabled;
            if (Global.guide.isActive())
            {
                this.m_settingsMenu.setEndTransition();
            }
            else
            {
                this.m_settingsMenu.setEndPostTutorialTransition();
            }
            return;
        }//end

        protected boolean  isHighQualityGraphics ()
        {
            return Global.stage.quality != StageQuality.LOW;
        }//end

        private void  onGoHome (Event event )
        {
            if (Global.player && Global.player.uid)
            {
                GameTransactionManager.addTransaction(new TWorldLoad(Global.player.uid), true, true);
            }
            Global.setVisiting(null);
            this.updateVisitMode(false);
            m_catalog = null;
            if (Global.mission)
            {
                Global.postInitActions.execute();
                Global.mission = null;
            }
            if (Global.guide)
            {
                Global.guide.removeDialogs();
                Global.guide.removeArrows();
                Global.guide.removeMask();
                Global.guide.removeGuideTiles();
            }
            return;
        }//end

        private void  onBuyModeClick (MouseEvent event )
        {
            if (Global.disableMenu)
            {
                return;
            }
            this.hideExpandedMainMenu();
            if (Global.isVisiting())
            {
                return;
            }
            if (Global.world.getTopGameMode() instanceof GMRemodel)
            {
                Global.world.setDefaultGameMode();
            }
            UI.displayCatalog(new CatalogParams());
            Sounds.play("click2");
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.MODE_BUTTON, "market");
            return;
        }//end

        private void  onDefaultModeClick (MouseEvent event )
        {
            this.reconfigureSettingsMenu();
            if (this.m_bExpandedMenuOpen == true)
            {
                this.hideExpandedMainMenu();
                this.m_bExpandedMenuOpen = false;
            }
            else
            {
                this.m_bExpandedMenuOpen = this.displayExpandedMainMenu(ExpandedMainMenu);
                Sounds.play("UI_expandmenu");
            }
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.MODE_BUTTON, "action_menu");
            return;
        }//end

        private void  onGiftModeClick (MouseEvent event )
        {
            if (this.m_bExpandedMenuOpen == true)
            {
                this.hideExpandedMainMenu();
                this.m_bExpandedMenuOpen = false;
            }
            else
            {
                this.m_bExpandedMenuOpen = this.displayExpandedMainMenu(ExpandedInventoryMenu);
                Sounds.play("UI_expandmenu");
            }
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.MODE_BUTTON, "gifts");
            return;
        }//end

        private void  onNewsModeClick (MouseEvent event )
        {
            this.reconfigureSettingsMenu();
            if (this.m_bExpandedMenuOpen == true)
            {
                this.hideExpandedMainMenu();
                this.m_bExpandedMenuOpen = false;
            }
            else
            {
                this.m_bExpandedMenuOpen = this.displayExpandedMainMenu(ExpandedInventoryMenu);
                Sounds.play("UI_expandmenu");
            }
            StatsManager.sample(100, StatsCounterType.HUD_COUNTER, StatsKingdomType.MODE_BUTTON, "storage_menu");
            this.switchDefaultActionButton(UIEvent.CURSOR);
            return;
        }//end

        private void  onCancelModeClick (MouseEvent event )
        {
            this.hideExpandedMainMenu();
            Global.world.addGameMode(new GMPlay());
            return;
        }//end

        public void  updateVisitMode (boolean param1 )
        {
            this.m_home.visible = param1;
            return;
        }//end

        public void  updateNeighborBar (String param1)
        {
            dispatchEvent(new UIEvent(UIEvent.UPDATE_FRIENDBAR, param1));
            return;
        }//end

        public Sprite  bottomUI ()
        {
            return this.m_bottomUI;
        }//end

        public Point  getBottomRight ()
        {
            _loc_1 = this.m_bottomUI.getBounds(Global.stage );
            _loc_2 = new Point(_loc_1.x +_loc_1.width ,_loc_1.y +_loc_1.height -75);
            return _loc_2;
        }//end

        public void  displayOverlaySWF (String param1 ,Point param2 ,Vector2 param3 =null ,double param4 =0,Function param5 =null )
        {
            _loc_6 = LoadingManager.load(param1,this.playOverlaySWF);
            this.m_overlaySWFs.put(_loc_6,  {scale:param3, position:param2, yOffset:param4, callback:param5});
            return;
        }//end

        private void  playOverlaySWF (Event event )
        {
            MovieClip _loc_3 =null ;
            Object _loc_4 =null ;
            Point _loc_5 =null ;
            Vector2 _loc_6 =null ;
            double _loc_7 =0;
            double _loc_8 =0;
            SoundTransform _loc_9 =null ;
            _loc_2 =(Loader) event.target.loader;
            if (_loc_2 != null)
            {
                _loc_3 =(MovieClip) _loc_2.content;
                if (Global.player.options.sfxDisabled)
                {
                    _loc_9 = new SoundTransform();
                    _loc_9.volume = 0;
                    _loc_3.soundTransform = _loc_9;
                }
                _loc_4 =(Object) this.m_overlaySWFs.get(_loc_2);
                _loc_5 =(Point) _loc_4.position;
                _loc_6 =(Vector2) _loc_4.scale;
                _loc_7 =(Number) _loc_4.yOffset;
                _loc_8 = 0;
                if (_loc_6 != null)
                {
                    _loc_8 = _loc_6.x / _loc_2.contentLoaderInfo.width;
                    _loc_2.scaleX = _loc_8;
                    _loc_2.scaleY = _loc_8;
                }
                if (_loc_5 != null)
                {
                    if (_loc_6 != null)
                    {
                        _loc_2.x = _loc_5.x;
                        _loc_2.y = _loc_5.y - _loc_7 * (_loc_2.contentLoaderInfo.height * _loc_8 - _loc_6.y);
                    }
                    else
                    {
                        _loc_2.x = _loc_5.x;
                        _loc_2.y = _loc_5.y;
                    }
                }
                if (_loc_3.origin_mc)
                {
                    _loc_2.x = _loc_2.x - _loc_3.origin_mc.x;
                    _loc_2.y = _loc_2.y - _loc_3.origin_mc.y;
                }
                GlobalEngine.viewport.uiBase.addChild(_loc_2);
                _loc_3.play();
                _loc_3.addEventListener(Event.ENTER_FRAME, this.onEnterFrame);
            }
            return;
        }//end

        private void  onEnterFrame (Event event )
        {
            _loc_2 =(Loader) event.target.parent;
            _loc_3 =(MovieClip) _loc_2.content;
            if (_loc_3.currentFrame >= _loc_3.totalFrames)
            {
                _loc_3.stop();
                if (_loc_2.parent != null)
                {
                    _loc_2.parent.removeChild(_loc_2);
                }
                if (this.m_overlaySWFs.get(_loc_2).callback != null)
                {
                    this.m_overlaySWFs.get(_loc_2).callback();
                }
                delete this.m_overlaySWFs.get(_loc_2);
                _loc_3.removeEventListener(Event.ENTER_FRAME, this.onEnterFrame);
            }
            return;
        }//end

        public void  displayPopupAd (DisplayObject param1 ,String param2 ="popupAd",int param3 =0)
        {
            param1.name = param2;
            addChildAt(param1, 0);
            _loc_4 = param1as Dialog ;
            if ((Dialog)param1)
            {
                _loc_4.show();
            }
            return;
        }//end

        public void  swapPopupAd (String param1 ,boolean param2 =true )
        {
            popupName = param1;
            front = param2;
            try
            {
                if (front)
                {
                    this.setChildIndex(getChildByName(popupName), (this.numChildren - 1));
                }
                else
                {
                    this.setChildIndex(getChildByName(popupName), 0);
                }
            }
            catch (error:Error)
            {
            }
            return;
        }//end

        public void  setExpandMode (boolean param1 )
        {
            if (param1 !=null)
            {
                UI.displayCatalog(new CatalogParams("expansion"));
            }
            return;
        }//end

        public boolean  turnOffHighlightedObject ()
        {
            GMDefault _loc_2 =null ;
            _loc_1 =Global.world.getTopGameMode ();
            if (_loc_1 instanceof GMDefault)
            {
                _loc_2 =(GMDefault) _loc_1;
                _loc_2.turnOffObject();
                return true;
            }
            return false;
        }//end

        public CollectionView  collectionView ()
        {
            return m_collections;
        }//end

        public InventoryView  inventoryView ()
        {
            return m_inventory;
        }//end

        public FranchiseMenu  franchiseMenu ()
        {
            return m_franchiseMenu;
        }//end

        public boolean  isActionMenuOpen ()
        {
            return this.m_bExpandedMenuOpen;
        }//end

        public void  startPickThings (String param1 ="nullVoid")
        {
            _loc_2 = new PickThingsDialog(param1 );
            displayPopup(_loc_2);
            return;
        }//end

        private static boolean  isWindowModeDefault ()
        {
            return false;
        }//end

        public static Dialog  currentPopup ()
        {
            return m_currentDialog;
        }//end

        public static void  setCityName (String param1 )
        {
            if (!param1)
            {
                Global.ui.m_cityNamePanel.cityName = ZLoc.t("Main", "NameSuffix");
            }
            else
            {
                Global.ui.m_cityNamePanel.cityName = param1;
            }
            return;
        }//end

        public static void  populateFriendBarData (Array param1 )
        {
            Friend _loc_3 =null ;
            Object _loc_6 =null ;
            Object _loc_8 =null ;
            Object _loc_9 =null ;
            String _loc_10 =null ;
            Player _loc_11 =null ;
            Dictionary _loc_12 =null ;
            String _loc_13 =null ;
            Array _loc_2 =new Array();
            int _loc_4 =6;
            _loc_5 =Global.gameSettings().getXpromos ();
            if (ZCrossPromoManager.xPromoFriend)
            {
                _loc_2.push(ZCrossPromoManager.xPromoFriend);
                _loc_4 = _loc_4 - 1;
            }
            else
            {
                for(int i0 = 0; i0 < _loc_5.size(); i0++)
                {
                	_loc_8 = _loc_5.get(i0);

                    if (InGameXPromoManager.checkXPromoGates(_loc_8) == true)
                    {
                        _loc_2.push(new XPromoFriend(_loc_8));
                        _loc_4 = _loc_4 - 1;
                        break;
                    }
                }
            }
            _loc_6 = new Object();
            if (RealtimeManager.inExperiment)
            {
                _loc_6 = ExternalInterface.call("getOnlineFriends");
            }
            int _loc_7 =0;
            while (_loc_7 < param1.length())
            {

                _loc_9 = param1.get(_loc_7);
                _loc_10 = _loc_9.uid;
                _loc_11 = Global.player.findFriendById(_loc_10);
                if (_loc_11 != null)
                {
                    _loc_12 = new Dictionary();
                    if (_loc_9.commodities)
                    {
                        for(int i0 = 0; i0 < _loc_9.commodities.storage.size(); i0++)
                        {
                        	_loc_13 = _loc_9.commodities.storage.get(i0);

                            _loc_12.put(_loc_13,  _loc_9.commodities.storage.get(_loc_13));
                        }
                    }
                    _loc_3 = new Friend(_loc_9.uid, _loc_9.gold, _loc_9.xp, _loc_9.level, _loc_9.firstTimeVisit, null, _loc_9.cityname, _loc_11.snUser.picture, _loc_11.snUser.firstName, _loc_12, _loc_9.socialLevel, false, false, _loc_9.rollCall, _loc_9.collect, _loc_9.energyLeft, _loc_9.lastLoginTimestamp, _loc_9.helpRequests, _loc_9.nonSNNeighbor);
                    _loc_3.playerClassType = _loc_9.playerClassType;
                    if (_loc_6 && _loc_6.get(_loc_10))
                    {
                        _loc_3.m_online = true;
                    }
                    _loc_2.push(_loc_3);
                    Global.player.setFriendCityName(_loc_9.uid, _loc_9.cityname);
                    _loc_4 = _loc_4 - 1;
                    _loc_12 = null;
                }
                _loc_7++;
            }
            _loc_7 = 0;
            while (_loc_7 < _loc_4)
            {

                _loc_3 = new Friend(null, 0, 0, 0, false, null, "", null, null, null, 0, true, true);
                _loc_2.push(_loc_3);
                _loc_7++;
            }
            Global.friendbar = _loc_2;
            return;
        }//end

        public static void  addToFriendBarData (Object param1 )
        {
            Friend _loc_2 =null ;
            boolean _loc_4 =false ;
            boolean _loc_5 =false ;
            String _loc_6 =null ;
            Player _loc_7 =null ;
            Dictionary _loc_8 =null ;
            String _loc_9 =null ;
            int _loc_3 =0;
            while (_loc_3 < Global.friendbar.length())
            {

                _loc_4 = param1.xp <= Global.friendbar.get(_loc_3).xp && !Global.friendbar.get(_loc_3).empty;
                _loc_5 = _loc_3 == (Global.friendbar.length - 1);
                if (_loc_4 || _loc_5)
                {
                    _loc_6 = param1.uid;
                    _loc_7 = Global.player.findFriendById(_loc_6);
                    if (_loc_7 != null)
                    {
                        _loc_8 = new Dictionary();
                        if (param1.commodities)
                        {
                            for(int i0 = 0; i0 < param1.commodities.storage.size(); i0++)
                            {
                            	_loc_9 = param1.commodities.storage.get(i0);

                                _loc_8.put(_loc_9,  param1.commodities.storage.get(_loc_9));
                            }
                        }
                        _loc_2 = new Friend(param1.uid, param1.gold, param1.xp, param1.level, param1.firstTimeVisit, null, param1.cityname, _loc_7.snUser.picture, _loc_7.snUser.firstName, _loc_8, param1.socialLevel, false, false);
                        _loc_2.playerClassType = param1.playerClassType;
                        Global.friendbar.splice(_loc_3, 0, _loc_2);
                        Global.player.setFriendCityName(param1.uid, param1.cityname);
                        _loc_8 = null;
                        break;
                    }
                }
                _loc_3++;
            }
            return;
        }//end

        public static void  confirmAddNeighbor ()
        {
            String _loc_2 =null ;
            _loc_1 =Global.stage.getChildByName("reserveLotPopup");
            if (_loc_1 == null)
            {
                _loc_2 = ZLoc.t("Main", "ReserveLot");
                UI.displayPopup(new GenericDialog(_loc_2, "Confirm", GenericDialogView.TYPE_YESNO, onConfirmReserve), true, "reserveLotPopup", true);
            }
            return;
        }//end

        public static void  onConfirmReserve (GenericPopupEvent event )
        {
            if (event.button == GenericPopup.YES)
            {
                GlobalEngine.socialNetwork.redirect("invite.php?ref=neighborsInvitePage");
            }
            return;
        }//end

        public static void  visitNeighbor (String param1 )
        {
            int _loc_2 =-1;
            _loc_3 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_BUY_ON_VISIT );
            if (_loc_3 == ExperimentDefinitions.BUY_ON_VISIT_EXPERIMENT)
            {
                if (param1 == "-1" && !Global.player.hasVisitedCitySam)
                {
                    _loc_2 = parseInt(Global.gameSettings().getCitySamPanToObjectId());
                }
            }
            GameTransactionManager.addTransaction(new TGetVisitMission(param1, null, _loc_2), true);
            return;
        }//end

        public static void  updateZoom (double param1 ,boolean param2 )
        {
            Vector2 _loc_4 =null ;
            _loc_3 = GlobalEngine.viewport.getZoom ();
            GlobalEngine.viewport.setZoom(param1);
            if (param1 < _loc_3)
            {
                Global.world.updateWorldObjectCulling(GameWorld.CULL_ZOOM_OUT);
            }
            else if (param1 > _loc_3)
            {
                Global.world.updateWorldObjectCulling(GameWorld.CULL_ZOOM_IN);
            }
            else
            {
                Global.world.updateWorldObjectCulling(GameWorld.CULL_CHECK_ALL);
                UI.displayNewFranchise(true);
            }
            if (param2)
            {
                _loc_4 = GlobalEngine.viewport.getScrollPosition();
                _loc_4 = GameMode.GameMode.limitBackgroundScrolling(_loc_4);
                GlobalEngine.viewport.setScrollPosition(_loc_4);
            }

            return;
        }//end

        public static SuppliesDialog  displaySupplies (String param1 )
        {
            _loc_2 = new SuppliesDialog("","Supplies",param1 );
            UI.displayPopup(_loc_2);
            return _loc_2;
        }//end

        public static GenericDialog  displayFTUEDialog (String param1 ,String param2 ,int param3 =0,Function param4 =null ,String param5 ="",boolean param6 =false ,String param7 =null )
        {
            _loc_8 = new GuideDialog(param1 ,param2 ,"next");
            _loc_8.name = param5;
            if (param6)
            {
                if (!UI.isScreenFrozen())
                {

                    _loc_8.show();
                }
                else
                {
                    m_popupQueue.splice(0, 0, _loc_8);
                }
            }
            else
            {
                UI.displayPopup(_loc_8);
            }
            return _loc_8;
        }//end

        public static GenericDialog  displayNewsFlash (String param1 ,int param2 =0,String param3 ="",String param4 ="",String param5 ="",boolean param6 =false ,String param7 =null )
        {
            _loc_8 = new NewsFlashDialog(param1 ,param5 ,param4 ,param3 ,param2 ,onNewsFlashClosed );
            _loc_8.name = param5;
            if (param6)
            {
                if (!UI.isScreenFrozen())
                {

                    _loc_8.show();
                }
                else
                {
                    m_popupQueue.splice(0, 0, _loc_8);
                }
            }
            else
            {
                UI.displayPopup(_loc_8);
            }
            return _loc_8;
        }//end

        public static void  onNewsFlashClosed (GenericPopupEvent event )
        {
            event.target.removeEventListener(GenericPopupEvent.SELECTED, onNewsFlashClosed);
            if (event.button != GenericPopup.YES)
            {
                return;
            }
            Global.world.viralMgr.sendNewsViralFeed(Global.player, NewsFlashDialog.GetLastNewsTitle(), NewsFlashDialog.GetLastNewsSubhead(), NewsFlashDialog.GetLastNewsMessage());
            return;
        }//end

        public static GenericDialog  displayCreditBasic (boolean param1 =false )
        {
            String _loc_2 =null ;
            GenericDialog _loc_3 =null ;
            String _loc_4 =null ;
            if (!param1)
            {
                _loc_2 = ZLoc.t("Dialogs", "CreditDialog1_message");
                _loc_3 = new CreditDialog(_loc_2, "CreditDialog1", GenericDialogView.TYPE_PLAY, onCreditBasicClose, "CreditDialog1");
                _loc_3.name = "CreditDialog1";
            }
            else
            {
                _loc_2 = ZLoc.t("Dialogs", "CreditDialog2_message");
                _loc_3 = new CreditDialog(_loc_2, "CreditDialog2", GenericDialogView.TYPE_PLAY, onCreditBasicClose, "CreditDialog2");
                _loc_3.name = "CreditDialog2";
            }
            _loc_4 = _loc_3.name;
            UI.displayPopup(_loc_3, true, _loc_4, true);
            return _loc_3;
        }//end

        private static void  onCreditBasicClose (GenericPopupEvent event )
        {
            GameTransactionManager.addTransaction(new TClearCompensationFlag());
            return;
        }//end

        private static void  onImmediateDialogClose (Event event )
        {
            _loc_2 = m_immediateDialogs.indexOf(event.target);
            if (_loc_2 >= 0)
            {
                m_immediateDialogs.splice(_loc_2, 1);
            }
            return;
        }//end

        public static GenericDialog  displayMessage (String param1 ,int param2 =0,Function param3 =null ,String param4 ="",boolean param5 =false ,String param6 =null ,String param7 ="",int param8 =0,String param9 ="")
        {
            _loc_10 = new GenericDialog(param1 ,param4 ,param2 ,param3 ,param7 ,param6 ,true ,param8 ,"",null ,"",true ,param9 );
            _loc_10.name = param4;
            if (param5)
            {
                if (!UI.isScreenFrozen())
                {
                    Global.stage.addChild(_loc_10);
                    _loc_10.show();
                    m_immediateDialogs.push(_loc_10);
                    _loc_10.addEventListener(Event.CLOSE, onImmediateDialogClose);
                }
                else
                {
                    m_popupQueue.splice(0, 0, _loc_10);
                }
            }
            else
            {
                UI.displayPopup(_loc_10, true, param4, true);
            }
            return _loc_10;
        }//end

        public static GenericDialog  displayDonationMessage (String param1 ,String param2 ,int param3 =0,Function param4 =null ,String param5 ="",boolean param6 =false ,String param7 =null ,String param8 ="",int param9 =0,int param10 =5)
        {
            _loc_11 = newPotatoDonationDialog(param1,param5,param3,param4,param8,param7,true,param9,"Purchase",param2,param10);
            _loc_11.name = param5;
            if (param6)
            {
                if (!UI.isScreenFrozen())
                {
                    Global.stage.addChild(_loc_11);
                    _loc_11.show();
                }
                else
                {
                    m_popupQueue.splice(0, 0, _loc_11);
                }
            }
            else
            {
                UI.displayPopup(_loc_11, true, param5, true);
            }
            return _loc_11;
        }//end

        public static GenericDialog  displayMessageWithBold (String param1 ,String param2 ,int param3 =0,Function param4 =null ,String param5 ="",boolean param6 =false ,String param7 =null ,String param8 ="",int param9 =0)
        {
            _loc_10 = new GenericDialogWithBold(param1 ,param2 ,param5 ,param3 ,param4 ,param8 ,param7 ,true ,param9 );
            _loc_10.name = param5;
            if (param6)
            {
                if (!UI.isScreenFrozen())
                {
                    Global.stage.addChild(_loc_10);
                    _loc_10.show();
                }
                else
                {
                    m_popupQueue.splice(0, 0, _loc_10);
                }
            }
            else
            {
                UI.displayPopup(_loc_10, true, param5, true);
            }
            return _loc_10;
        }//end

        public static void  goToCashPage (GenericPopupEvent event )
        {
            if (event.button == GenericDialogView.YES)
            {
                StatsManager.count("fv_cash_buy_fail", "click_yes_please");
                StatsManager.sendStats(true);
                FrameManager.navigateTo("money.php?ref=tab");
            }
            return;
        }//end

        public static GenericDialog  displayItemPurchaseConfirm (Function param1 ,Item param2 ,boolean param3 =true ,Function param4 =null ,boolean param5 =false ,String param6 ="default",String param7 ="default",String param8 ="default",String param9 ="default")
        {
            ItemUnlockDialog _loc_12 =null ;
            boolean _loc_10 =false ;
            _loc_11 =Global.gameSettings().getImageByName(param2.name ,"icon");
            _loc_13 = UI.goToCashPage;
            if (param4 != null)
            {
                _loc_13 = param4;
            }
            _loc_14 = GenericDialogView.TYPE_YESNO;
            _loc_15 = GenericDialogView.TYPE_GETCASH;
            if (param5 && param2.unlockShareFunc)
            {
                _loc_14 = GenericDialogView.TYPE_YESNO_SHARE;
                _loc_15 = GenericDialogView.TYPE_GETCASH_SHARE;
            }
            if (param3)
            {
                param9 = param9 == "default" ? ("ConfirmPurchase") : (param9);
                param8 = param8 == "default" ? (ZLoc.t("Dialogs", "ConfirmPurchaseMsg", {itemName:param2.localizedName})) : (param8);
                _loc_12 = new ItemUnlockDialog(param8, "confirmPurchase", _loc_14, param1, param9, _loc_11, true, 0, "", null, param2);
            }
            else
            {
                param7 = param7 == "default" ? ("ConfirmPurchaseFail") : (param7);
                param6 = param6 == "default" ? (ZLoc.t("Dialogs", "ConfirmPurchaseFailMsg", {itemName:param2.localizedName})) : (param6);
                _loc_12 = new ItemUnlockDialog(param6, "confirmPurchase", _loc_15, _loc_13, param7, _loc_11, true, 0, "", null, param2);
            }
            _loc_12.name = "confirmPurchase";
            if (_loc_10)
            {
                if (!UI.isScreenFrozen())
                {
                    Global.stage.addChild(_loc_12);
                    _loc_12.show();
                }
                else
                {
                    m_popupQueue.splice(0, 0, _loc_12);
                }
            }
            else
            {
                UI.displayPopup(_loc_12, true, "unlockItem", true);
            }
            return _loc_12;
        }//end

        public static GenericDialog  displayItemUnlock (Function param1 ,Item param2 ,boolean param3 =true ,Function param4 =null ,boolean param5 =false )
        {
            ItemUnlockDialog _loc_8 =null ;
            boolean _loc_6 =false ;
            _loc_7 =Global.gameSettings().getImageByName(param2.name ,"icon");
            _loc_9 = UI.goToCashPage;
            if (param4 != null)
            {
                _loc_9 = param4;
            }
            _loc_10 = GenericDialogView.TYPE_YESNO;
            _loc_11 = GenericDialogView.TYPE_GETCASH;
            if (param5 && param2.unlockShareFunc)
            {
                if (param2.type == "expansion" && Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EXPANSION_TEST) == ExperimentDefinitions.EXPANSION_TEST_ENABLED)
                {
                    _loc_10 = GenericDialogView.TYPE_BUY_SHARE;
                }
                else
                {
                    _loc_10 = GenericDialogView.TYPE_YESNO_SHARE;
                }
                _loc_11 = GenericDialogView.TYPE_GETCASH_SHARE;
            }
            String _loc_12 ="UnlockCashQuestion";
            if (param2.unlock == Item.UNLOCK_LOCKED)
            {
                _loc_12 = "UnlockCashQuestion_Locked";
            }
            if (param3)
            {
                _loc_8 = new ItemUnlockDialog(ZLoc.t("Dialogs", _loc_12, {object:param2.localizedName}), "unlockItem", _loc_10, param1, "unlockItem", _loc_7, true, 0, "", null, param2);
            }
            else
            {
                _loc_8 = new ItemUnlockDialog(ZLoc.t("Dialogs", "UnlockItemQuestion", {object:param2.localizedName}), "unlockItem", _loc_11, _loc_9, "unlockItem", _loc_7, true, 0, "", null, param2);
            }
            _loc_8.name = "unlockItem";
            if (_loc_6)
            {
                if (!UI.isScreenFrozen())
                {
                    Global.stage.addChild(_loc_8);
                    _loc_8.show();
                }
                else
                {
                    m_popupQueue.splice(0, 0, _loc_8);
                }
            }
            else
            {
                UI.displayPopup(_loc_8, true, "unlockItem", true);
            }
            return _loc_8;
        }//end

        public static void  displayItemCashDialog (String param1 ,String param2 ,Item param3 ,Function param4 ,boolean param5 =true )
        {
            _loc_6 =Global.gameSettings().getImageByName(param3.name ,"icon");
            _loc_7 = param2;
            _loc_8 = param1;
            _loc_9 = GenericDialogView.TYPE_YESNO;
            _loc_10 = param4;
            _loc_11 = param1;
            _loc_12 = param3.icon;
            boolean _loc_13 =true ;
            int _loc_14 =0;
            String _loc_15 ="";
            Function _loc_16 =null ;
            _loc_17 = param3;
            _loc_18 = newItemUnlockDialog(_loc_7,_loc_8,_loc_9,_loc_10,_loc_11,_loc_12,true,0,"",null,_loc_17);
            UI.displayPopup(_loc_18, param5, param1, true);
            return;
        }//end

        public static GenericDialog  displayViralShareMessage (String param1 ,String param2 ,int param3 =0,Function param4 =null ,String param5 ="",boolean param6 =false ,String param7 =null ,String param8 ="",int param9 =0,String param10 ="",boolean param11 =true )
        {
            _loc_12 = newGenericDialog(param2,param5,param3,param4,param8,param7,true,param9,param1,null,param10,param11);
            _loc_12.name = param5;
            if (param6)
            {
                if (!UI.isScreenFrozen())
                {
                    Global.stage.addChild(_loc_12);
                    _loc_12.show();
                }
                else
                {
                    m_popupQueue.splice(0, 0, _loc_12);
                }
            }
            else
            {
                UI.displayPopup(_loc_12, true, param5, true);
            }
            return _loc_12;
        }//end

        public static GenericDialog  displayValRewardsMessage (String param1 ,int param2 =0,Function param3 =null ,String param4 ="",boolean param5 =false ,String param6 =null ,String param7 ="",int param8 =0)
        {
            _loc_9 = new ValentinesDayRewardDialog(param1 ,param4 ,param2 ,param3 ,param7 ,param6 ,true ,param8 ,"");
            _loc_9.name = param4;
            if (param5)
            {
                if (!UI.isScreenFrozen())
                {
                    Global.stage.addChild(_loc_9);
                    _loc_9.show();
                }
                else
                {
                    m_popupQueue.splice(0, 0, _loc_9);
                }
            }
            else
            {
                UI.displayPopup(_loc_9, true, param4, true);
            }
            return _loc_9;
        }//end

        public static void  displayStatus (String param1 ,double param2 ,double param3 ,Object param4 ="",Array param5 =null ,double param6 =1.5,int param7 =32,int param8 =16,double param9 =60,Object param10 =16777215)
        {
            Sprite spr ;
            StrokeTextField statusText ;
            Loader iconLoader ;
            message = param1;
            screenX = param2;
            screenY = param3;
            icon = param4;
            iconFilters = param5;
            tweenDuration = param6;
            tweenDisplacement = param7;
            fontSize = param8;
            iconSize = param9;
            msgColor = param10;
            addIcon = function(param11DisplayObject)
            {
                param11 = Utilities.getSmoothableBitmap(param11);
                if (message != "")
                {
                    param11.x = statusText.x - param11.width - 5;
                }
                else
                {
                    param11.x = (-param11.width) / 2;
                }
                param11.y = statusText.y + (statusText.height - param11.height) / 2;
                if (iconFilters != null)
                {
                    param11.filters = iconFilters;
                }
                spr.addChild(param11);
                return;
            }//end
            ;
            spr = new Sprite();
            spr.x = screenX;
            spr.y = screenY;
            spr.mouseEnabled = false;
            spr.mouseChildren = false;
            statusText = new StrokeTextField(2105376, 0.8);
            statusText.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            resource_fmt = newTextFormat(EmbeddedArt.defaultFontNameBold,fontSize,null,true);
            resource_fmt.color = msgColor;
            statusText.defaultTextFormat = resource_fmt;
            statusText.toolTipText = message;
            statusText.x = (-statusText.textWidth) / 2;
            spr.addChild(statusText);
            Global.stage.addChild(spr);
            tweenStatusObject(spr, tweenDuration, tweenDisplacement);
            iconUrl = (String)icon
            if (iconUrl && iconUrl != "")
            {
                iconLoader =LoadingManager .load (iconUrl ,void  (Event event )
            {
                if (iconLoader && iconLoader.content)
                {
                    addIcon(iconLoader.content);
                }
                return;
            }//end
            );
            }
            else if (icon instanceof DisplayObject)
            {
                addIcon((DisplayObject)icon);
            }
            return;
        }//end

        public static void  displayStatusComponent (DisplayObject param1 ,double param2 ,double param3 ,double param4 =1.5,int param5 =32)
        {
            param1.x = param2 - param1.width / 2;
            param1.y = param3;
            param1.alpha = 1;
            param1.visible = true;
            if (param1 instanceof DisplayObjectContainer)
            {
                ((DisplayObjectContainer)param1).mouseEnabled = false;
                ((DisplayObjectContainer)param1).mouseChildren = false;
            }
            Global.stage.addChild(param1);
            tweenStatusObject(param1, param4, param5);
            return;
        }//end

        private static void  tweenStatusObject (DisplayObject param1 ,double param2 ,int param3 )
        {
            dispObj = param1;
            duration = param2;
            displacement = param3;
            double readabilityDelay ;
            remainingDuration = duration-readabilityDelay;
            TweenLite.to(dispObj, remainingDuration, {delay:readabilityDelay, alpha:0.2, ease:Quadratic.easeIn});
void             TweenLite .to (dispObj ,remainingDuration ,{readabilityDelay delay ,0overwrite ,dispObj y .y -displacement , onComplete ()
            {
                if (dispObj.parent)
                {
                    dispObj.parent.removeChild(dispObj);
                }
                return;
            }//end
            });
            if (m_frozenDispObj)
            {
                dispObj .addEventListener (Event .REMOVED_FROM_STAGE ,void  ()
            {
                ArrayUtil.removeValueFromArray(m_frozenDispObj, dispObj);
                return;
            }//end
            , false, 0, true);
            }
            return;
        }//end

        public static void  displayPopup (DisplayObject param1 ,boolean param2 =true ,String param3 ="",boolean param4 =false )
        {
            param1.name = param3;
            m_questPopoutname = param3;
            if (param2)
            {
                param2 = true;
                if (param4 && param3 != "")
                {
                    param2 = !isPopupDuplicated(param3);
                }
                if (param2)
                {
                    m_popupQueue.push(param1);
                }
                pumpPopupQueue();
            }
            else if (!UI.isScreenFrozen())
            {
                if (!isPopupDuplicated(param3))
                {
                    if (m_currentDialog && m_currentDialog.isShown)
                    {
                        m_interruptedDialogs.push(m_currentDialog);
                    }
                    popupOpen(param1);
                }
            }
            else
            {
                m_popupQueue.splice(0, 0, (Dialog)param1);
            }
            return;
        }//end

        public static boolean  isPopupDuplicated (String param1 )
        {
            if (m_currentDialog)
            {
                if (m_currentDialog.name == param1)
                {
                    return true;
                }
            }
            int _loc_2 =0;
            while (_loc_2 < m_popupQueue.length())
            {

                if (m_popupQueue.get(_loc_2).name == param1)
                {
                    return true;
                }
                _loc_2++;
            }
            return false;
        }//end

        public static void  flushDialogs ()
        {
            if (m_currentDialog)
            {
                m_currentDialog.close();
                m_currentDialog.removeEventListener(Event.CLOSE, onPopupClosed);
                m_currentDialog = null;
            }
            while (m_popupQueue.length())
            {

                m_currentDialog = m_popupQueue.pop();
                m_currentDialog.close();
                m_currentDialog.removeEventListener(Event.CLOSE, onPopupClosed);
                m_currentDialog = null;
            }
            while (m_immediateDialogs.length())
            {

                m_currentDialog =(Dialog) m_immediateDialogs.pop();
                m_currentDialog.close();
                m_currentDialog.removeEventListener(Event.CLOSE, onPopupClosed);
                m_currentDialog = null;
            }
            return;
        }//end

        public static void  onPopupClosed (Event event )
        {
            Dialog _loc_2 =null ;
            while (m_interruptedDialogs && m_interruptedDialogs.length > 0)
            {

                _loc_2 = m_interruptedDialogs.pop();
                if (_loc_2.isShown)
                {
                    break;
                }
            }
            m_currentDialog = _loc_2;
            m_isModalDialogOpen = false;
            event.currentTarget.removeEventListener(Event.CLOSE, onPopupClosed);
            pumpPopupQueue();
            return;
        }//end

        public static void  pumpPopupQueue ()
        {
            Dialog _loc_1 =null ;
            int _loc_2 =0;
            boolean allCompleted =InitializationManager.getInstance ().haveAllCompleted ();
            if(m_currentDialog) {
                m_currentDialog.close();
                m_currentDialog = null;
            }

            if (m_popupQueue.length && m_currentDialog == null && allCompleted)
            {
                _loc_2 = 0;
                while (_loc_2 < m_popupQueue.length())
                {

                    _loc_1 = m_popupQueue.get(_loc_2);
                    if (_loc_1.isLockable() && UI.m_popupLock > 0)
                    {
                    }
                    else if (_loc_1)
                    {
                        m_popupQueue.splice(_loc_2, 1);
                        if (Global.isVisiting() == true && _loc_1 instanceof GiftNotify)
                        {
                        }
                        else
                        {
                            popupOpen(_loc_1);
                            break;
                        }
                    }
                    _loc_2++;
                }
            }
            return;
        }//end

        private static void  popupOpen (DisplayObject param1 )
        {
            _loc_2 = param1as Dialog ;
            if (_loc_2)
            {
                param1.addEventListener(Event.CLOSE, onPopupClosed, false, 0, true);
                Global.stage.addChild(param1);

		//add by xinghai
                //Global.circle1.addChild(param1);

                m_currentDialog = _loc_2;
                if (m_currentDialog.isModal)
                {
                    m_isModalDialogOpen = true;
                }
                m_currentDialog.show();
            }
            return;
        }//end

        public static void  popupLock ()
        {
            _loc_2 = m_popupLock+1;
            m_popupLock = _loc_2;
            return;
        }//end

        public static void  popupUnlock ()
        {
            _loc_2 = m_popupLock-1;
            m_popupLock = _loc_2;
            if (m_popupLock <= 0)
            {
                pumpPopupQueue();
                m_popupLock = 0;
            }
            return;
        }//end

        public static void  displayMenu (Array param1 ,Function param2 ,boolean param3 =false ,Point param4 =null )
        {
            ContextMenu _loc_5 =null ;
            double _loc_6 =0;
            if (Global.isVisiting() == false || param3)
            {
                _loc_5 = new ContextMenu(param1);
                _loc_5.addEventListener(MouseEvent.CLICK, param2);
                if (param4 == null)
                {
                    param4 = new Point(Global.stage.mouseX, Global.stage.mouseY);
                }
                _loc_6 = Math.min(param4.y, Global.ui.screenHeight - _loc_5.height - SCREEN_MENU_BUFFER);
                _loc_5.show(param4.x, _loc_6);
            }
            return;
        }//end

        public static boolean  isScreenFrozen ()
        {
            return m_frozenBitmap != null;
        }//end

        public static void  freezeScreen (boolean param1 =false ,boolean param2 =false ,String param3 =null ,int param4 =0)
        {
            Stage _loc_5 =null ;
            String _loc_6 =null ;
            if (!m_frozen)
            {
                m_frozen = true;
                m_freezeKey = param4;
                if (isWindowModeDefault)
                {
                    if (Global.stage.displayState == StageDisplayState.FULL_SCREEN)
                    {
                        Global.stage.displayState = StageDisplayState.NORMAL;
                    }
                    if (param1 == true)
                    {
                        m_frameLoadingDialog = new FrameLoaderDialog(ZLoc.t("Dialogs", param3 ? (param3) : ("FrameLoading")), null);
                        m_frameLoadingDialog.setupDefaultSizeAndPosition();
                        Global.stage.addChild(m_frameLoadingDialog);
                    }
                }
                else if (m_frozenBitmap == null)
                {
                    if (Global.stage.displayState == StageDisplayState.FULL_SCREEN)
                    {
                        Global.stage.displayState = StageDisplayState.NORMAL;
                    }
                    if (param1 == true)
                    {
                        _loc_6 = param3 ? (param3) : ("FrameLoading");
                        m_frameLoadingDialog = new FrameLoaderDialog(ZLoc.t("Dialogs", _loc_6), null);
                        m_frameLoadingDialog.setupDefaultSizeAndPosition();
                    }
                    m_frozenBitmap = new Bitmap(Classes.Managers.FreezeManager.getScreenshot());
                    freezeObjects();
                    _loc_5 = Global.stage;
                    _loc_5.addChild(m_frozenBitmap);
                    if (param1 == true)
                    {
                        _loc_5.addChild(m_frameLoadingDialog);
                    }
                    if (param2 == true)
                    {
                        m_frozenGrayRect = new Shape();
                        m_frozenGrayRect.graphics.beginFill(0, 0.33);
                        m_frozenGrayRect.graphics.moveTo(-10000, -10000);
                        m_frozenGrayRect.graphics.lineTo(10000, -10000);
                        m_frozenGrayRect.graphics.lineTo(10000, 10000);
                        m_frozenGrayRect.graphics.lineTo(-10000, 10000);
                        m_frozenGrayRect.graphics.endFill();
                        _loc_5.addChild(m_frozenGrayRect);
                    }
                }
            }
            Sounds.stopAnyLoopingSounds();
            return;
        }//end

        public static void  thawScreen (int param1 =0)
        {
            Stage _loc_2 =null ;
            DisplayObject _loc_3 =null ;
            DisplayObject _loc_4 =null ;
            boolean _loc_5 =false ;
            if (m_frozen && m_freezeKey == param1)
            {
                if (isWindowModeDefault)
                {
                    if (m_frameLoadingDialog != null)
                    {
                        m_frameLoadingDialog.close();
                        m_frameLoadingDialog = null;
                    }
                    if (m_currentDialog)
                    {
                        _loc_2.setChildIndex(m_currentDialog, (_loc_2.numChildren - 1));
                    }
                    InputManager.enableInput();
                    Global.ui.mouseEnabled = true;
                    Global.ui.mouseChildren = true;
                    m_frozenDispObj.splice(0, m_frozenDispObj.length());
                    pumpPopupQueue();
                }
                else if (m_frozenBitmap)
                {
                    _loc_2 = Global.stage;
                    _loc_2.removeChild(m_frozenBitmap);
                    if (m_frameLoadingDialog != null)
                    {
                        m_frameLoadingDialog.close();
                        m_frameLoadingDialog = null;
                    }
                    if (m_frozenBitmap.bitmapData)
                    {
                        m_frozenBitmap.bitmapData.dispose();
                    }
                    m_frozenBitmap = null;
                    if (m_frozenGrayRect != null && m_frozenGrayRect.parent == _loc_2)
                    {
                        _loc_2.removeChild(m_frozenGrayRect);
                    }
                    m_frozenGrayRect = null;
                    freezeObjects();
                    for(int i0 = 0; i0 < m_frozenDispObj.size(); i0++)
                    {
                    	_loc_3 = m_frozenDispObj.get(i0);

                        if (_loc_3)
                        {
                            Global.stage.addChildAt(_loc_3, Global.stage.numChildren);
                        }
                    }
                    _loc_4 = Global.stage.getChildByName("root1");
                    if (_loc_4)
                    {
                        _loc_2.setChildIndex(_loc_4, 0);
                    }
                    if (m_currentDialog)
                    {
                        _loc_2.setChildIndex(m_currentDialog, (_loc_2.numChildren - 1));
                    }
                    InputManager.enableInput();
                    Global.ui.mouseEnabled = true;
                    Global.ui.mouseChildren = true;
                    m_frozenDispObj.splice(0, m_frozenDispObj.length());
                    pumpPopupQueue();
                }
                m_frozen = false;
                m_freezeKey = 0;
                Global.ui.onResize(null);
            }
            return;
        }//end

        public static void  freezeObjects ()
        {
            Object _loc_3 =null ;
            Object _loc_4 =null ;
            _loc_1 =Global.stage ;
            if (m_frozenDispObj == null)
            {
                m_frozenDispObj = new Array(_loc_1.numChildren);
            }
            int _loc_2 =0;
            _loc_2 = 0;
            while (_loc_2 < _loc_1.numChildren)
            {

                _loc_3 = _loc_1.getChildAt(_loc_2);
                if (_loc_3 instanceof FrameLoaderDialog)
                {
                }
                else
                {
                    m_frozenDispObj.push(_loc_1.getChildAt(_loc_2));
                }
                _loc_2++;
            }
            _loc_2 = _loc_1.numChildren - 1;
            while (_loc_2 >= 0)
            {

                _loc_4 = _loc_1.getChildAt(_loc_2);
                if (_loc_4 instanceof FrameLoaderDialog)
                {
                }
                else
                {
                    _loc_1.removeChildAt(_loc_2);
                }
                _loc_2 = _loc_2 - 1;
            }
            return;
        }//end

        public static void  onStageMouseMove (MouseEvent event )
        {
            if (m_currentCursor && m_currentCursor.displayObj)
            {
                m_currentCursor.displayObj.x = Global.stage.mouseX + CURSOR_OFFSET_X;
                m_currentCursor.displayObj.y = Global.stage.mouseY + CURSOR_OFFSET_Y;
            }
            return;
        }//end

        public static boolean  hasCursor (int param1 )
        {
            Object _loc_4 =null ;
            boolean _loc_2 =false ;
            int _loc_3 =0;
            while (_loc_3 < m_cursors.length())
            {

                _loc_4 = m_cursors.get(_loc_3);
                if (_loc_4.id == param1)
                {
                    _loc_2 = true;
                    break;
                }
                _loc_3++;
            }
            return _loc_2;
        }//end

        public static int  setCursor (Object param1 ,boolean param2 =false )
        {
            DisplayObject _loc_3 =null ;
            double _loc_6 =0;
            if (param1 instanceof DisplayObject)
            {
                _loc_3 =(DisplayObject) param1;
            }
            else if (param1 instanceof Class)
            {
                _loc_3 = new param1;
            }
            _loc_4 = Constants.INDEX_NONE;
            Object _loc_5 ={displayObj _loc_3 ,id.generateUniqueId (),keepSystemCursor };
            if (_loc_3)
            {
                _loc_6 = 0;
                _loc_6 = Math.min(1, MAX_CURSOR_SIZE / _loc_3.width, MAX_CURSOR_SIZE / _loc_3.height);
                _loc_7 = _loc_6;
                _loc_3.scaleY = _loc_6;
                _loc_3.scaleX = _loc_6;
                if (_loc_3 instanceof Bitmap)
                {
                    ((Bitmap)_loc_3).smoothing = true;
                }
                _loc_3.filters = .get(new GlowFilter(0, 0.5, 1, 1, 16, BitmapFilterQuality.HIGH));
            }
            m_cursors.push(_loc_5);
            updateCurrentCursor();
            _loc_4 = _loc_5.id;
            return _loc_4;
        }//end

        public static void  removeCursor (int param1 )
        {
            Object _loc_3 =null ;
            int _loc_2 =0;
            while (_loc_2 < m_cursors.length())
            {

                _loc_3 = m_cursors.get(_loc_2);
                if (_loc_3.id == param1)
                {
                    m_cursors.splice(_loc_2, 1);
                    break;
                }
                _loc_2++;
            }
            updateCurrentCursor();
            return;
        }//end

        public static boolean  cursorIsNull ()
        {
            if (m_currentCursor == null)
            {
                return true;
            }
            if (m_currentCursor.displayObj == null)
            {
                return true;
            }
            return false;
        }//end

        public static void  removeAllCursors ()
        {
            m_cursors = new Array();
            updateCurrentCursor();
            return;
        }//end

        public static void  updateCurrentCursor (boolean param1 =false )
        {
            Object _loc_2 =null ;
            if (m_cursors.length())
            {
                _loc_2 = m_cursors.get((m_cursors.length - 1));
                Debug.debug7("UI.updateCurrentCursor." + _loc_2.keepSystemCursor);
                //if (_loc_2.keepSystemCursor || _loc_2.displayObj == null)
                if (_loc_2.displayObj == null)
                {
                    Mouse.show();
                }
                else
                {
                    Mouse.hide();
                }
            }
            else
            {
                Mouse.show();
            }
            if (_loc_2 != m_currentCursor || param1)
            {
                if (m_currentCursor && m_currentCursor.displayObj)
                {
                    m_currentCursor.displayObj.parent.removeChild(m_currentCursor.displayObj);
                }
                m_currentCursor = _loc_2;
                if (m_currentCursor && m_currentCursor.displayObj)
                {
                    m_currentCursor.displayObj.x = Global.stage.mouseX + CURSOR_OFFSET_X;
                    m_currentCursor.displayObj.y = Global.stage.mouseY + CURSOR_OFFSET_Y;
                    Global.stage.addChild(m_currentCursor.displayObj);
                }
            }
            return;
        }//end

        public static void  goHome ()
        {
            Global.ui.m_home.visible = false;
            Global.setVisiting(null);
            m_catalog = null;
            if (Global.player && Global.player.getId() > 0)
            {
                GameTransactionManager.addTransaction(new TWorldLoad(Global.player.uid), true, true);
            }
            return;
        }//end

        public static void  getNewLevelItems (int param1 ,Array param2 ,Array param3 ,Array param4 )
        {
            XML _loc_6 =null ;
            _loc_5 =Global.gameSettings().getItems ();
            for(int i0 = 0; i0 < _loc_5.size(); i0++)
            {
            	_loc_6 = _loc_5.get(i0);

                if (_loc_6.requiredLevel && _loc_6.requiredLevel == param1)
                {
                    if (_loc_6.@buyable == "true" && _loc_6.descendants("limitedEnd").length() == 0 && _loc_6.@levelUpDisplay != "false")
                    {
                        param3.push(_loc_6.@name);
                    }
                }
                if (_loc_6.requiredLevel && _loc_6.requiredLevel == (param1 + 1))
                {
                    if (_loc_6.@buyable == "true" && _loc_6.descendants("limitedEnd").length() == 0 && _loc_6.@levelUpDisplay != "false")
                    {
                        param2.push(Global.gameSettings().getItemFriendlyName(_loc_6.@name));
                    }
                }
            }
            return;
        }//end

        public static void  getNewLevelItemsXML (int param1 ,Array param2 ,Array param3 )
        {
            XML _loc_5 =null ;
            _loc_4 =Global.gameSettings().getItems ();
            for(int i0 = 0; i0 < _loc_4.size(); i0++)
            {
            	_loc_5 = _loc_4.get(i0);

                if (_loc_5.requiredLevel && _loc_5.requiredLevel == param1)
                {
                    if (_loc_5.@buyable == "true" && _loc_5.descendants("limitedEnd").length() == 0 && _loc_5.@levelUpDisplay != "false")
                    {
                        param3.push(_loc_5.@name);
                    }
                    if (_loc_5.@giftable == "true")
                    {
                        param2.push(_loc_5.@name);
                    }
                }
            }
            return;
        }//end

        public static void  displayLevelUpDialog (int param1 )
        {
            Object _loc_10 =null ;
            GenericDialog _loc_11 =null ;
            Array _loc_2 =new Array();
            Array _loc_3 =new Array();
            Array _loc_4 =new Array();
            Array _loc_5 =new Array();
            getNewLevelItems(param1, _loc_4, _loc_3, _loc_5);
            _loc_6 = ZLoc.t("Levels","Level"+param1 );
            _loc_7 = new LevelUpDialog(param1 ,_loc_3 ,_loc_4 ,_loc_5 );
            displayPopup(_loc_7);
            _loc_7.view.addEventListener(GenericPopupEvent.SELECTED, onLevelUpClosed);
            _loc_8 =Global.gameSettings().getLevelXML(param1 );
            _loc_9 =Global.gameSettings().getLevelXML(param1 ).@notes;
            if (Global.gameSettings().getLevelXML(param1).@notes != "")
            {
                _loc_10 = Utilities.parseLocalizationString(_loc_9);
                _loc_11 = new GenericDialog(ZLoc.t(_loc_10.filename, _loc_10.stringname), "", 0, null, "", "", true);
                displayPopup(_loc_11);
            }
            return;
        }//end

        public static void  onLevelUpClosed (GenericPopupEvent event )
        {
            int _loc_3 =0;
            RecentlyPlayedMFSManager _loc_4 =null ;
            event.target.removeEventListener(GenericPopupEvent.SELECTED, onLevelUpClosed);
            _loc_2 =Global.player.level ;
            if (_loc_2 == 2)
            {
                _loc_3 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_RECENTLY_PLAYED_MFS);
                _loc_4 =(RecentlyPlayedMFSManager) Global.flashMFSManager.getManager(FlashMFSManager.TYPE_RECENTLY_PLAYED_MFS);
                if ((_loc_3 == 2 || _loc_3 == 3) && (_loc_4 && _loc_4.numAvailable > 0))
                {
                    _loc_4.giftingSource = "nux";
                    _loc_4.displayMFS();
                }
                else
                {
                    displayInviteFarmFriendsDialog();
                }
            }
            return;
        }//end

        public static void  displaySocialLevelUpDialog (int param1 ,int param2 )
        {
            GenericDialog _loc_5 =null ;
            Array _loc_3 =new Array();
            Array _loc_4 =new Array();
            _loc_6 =Global.gameSettings().getReputationLevelXML(param1 );
            _loc_7 =Global.gameSettings().getReputationLevelXML(param1 )!= null ? (int(_loc_6.@reward)) : (0);
            _loc_5 = new SocialLevelUpDialog(param1, _loc_7, param2, onSocialLevelUpClosed);
            Global.player.commodities.add(Commodities.GOODS_COMMODITY, _loc_7);
            UI.displayPopup(_loc_5, true, "", true);
            return;
        }//end

        public static void  onSocialLevelUpClosed (GenericPopupEvent event )
        {
            event.target.removeEventListener(GenericPopupEvent.SELECTED, onSocialLevelUpClosed);
            if (event.button != GenericPopup.YES)
            {
                return;
            }
            Global.world.viralMgr.sendReputationUpFeed(Global.player, Global.player.socialLevel);
            return;
        }//end

        public static void  displayInviteFarmFriendsDialog ()
        {
            String _loc_1 =null ;
            String _loc_3 =null ;
            Function _loc_5 =null ;
            _loc_2 = ZLoc.t("Dialogs","IdleFarmFriends_text");
            String _loc_4 ="assets/dialogs/inviteFarmFriends_farmFriends.png";
            _loc_6 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EMAIL_FARMVILLE_FRIENDS );
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EMAIL_FARMVILLE_FRIENDS) == ExperimentDefinitions.EMAIL_FARMVILLE_FRIENDS)
            {
                _loc_1 = ZLoc.t("Dialogs", "IdleFarmFriends_emailTitle");
                _loc_3 = "IdleFarmFriends_emailButton";
                _loc_5 = UI.emailFarmFriendsCallback;
            }
            else
            {
                _loc_1 = ZLoc.t("Dialogs", "IdleFarmFriends_inviteTitle");
                _loc_3 = "IdleFarmFriends_inviteButton";
                _loc_5 = UI.inviteFarmFriendsCallback;
            }
            _loc_7 = new GenericPictureDialog(_loc_2 ,"IdleFarmFriends",GenericDialogView.TYPE_OK ,_loc_5 ,_loc_1 ,"",true ,0,_loc_3 ,_loc_4 );
            displayPopup(_loc_7);
            StatsManager.milestone("add_farm_friends");
            return;
        }//end

        public static void  emailFarmFriendsCallback (GenericPopupEvent event )
        {
            TEmailFarmFriends _loc_2 =null ;
            if (event.button == GenericPopup.YES)
            {
                _loc_2 = new TEmailFarmFriends();
                GameTransactionManager.addTransaction(_loc_2);
                displayFarmFriendsConfirmDialog();
            }
            return;
        }//end

        public static void  inviteFarmFriendsCallback (GenericPopupEvent event )
        {
            if (event.button == GenericPopup.YES)
            {
                redirectToInvitePage("invite_farmville_friends", "farmville", UI.displayFarmFriendsConfirmDialog);
            }
            return;
        }//end

        public static void  displayFarmFriendsConfirmDialog (boolean param1 =true )
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            Function _loc_5 =null ;
            _loc_6 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EMAIL_FARMVILLE_FRIENDS );
            if (Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_EMAIL_FARMVILLE_FRIENDS) == ExperimentDefinitions.EMAIL_FARMVILLE_FRIENDS)
            {
                _loc_2 = "IdleFarmFriendsConfirmEmail";
                _loc_3 = ZLoc.t("Dialogs", "IdleFarmFriendsConfirm_emailText");
                _loc_4 = "IdleFarmFriendsConfirm_emailButton";
                _loc_5 = UI.emailFarmFriendsPostDialog;
            }
            else
            {
                _loc_2 = "IdleFarmFriendsConfirmInvite";
                _loc_3 = ZLoc.t("Dialogs", "IdleFarmFriendsConfirm_inviteText");
                _loc_4 = "IdleFarmFriendsConfirm_inviteButton";
                _loc_5 = UI.inviteFarmFriendsPostDialog;
            }
            _loc_7 = new SamAdviceDialog(_loc_3 ,_loc_2 ,0,_loc_5 ,_loc_2 ,"",true ,0,_loc_4 );
            displayPopup(_loc_7);
            return;
        }//end

        public static void  emailFarmFriendsPostDialog (GenericPopupEvent event )
        {
            if (event.button == GenericPopup.YES)
            {
                redirectToInvitePage("invite_farmville_friends");
            }
            return;
        }//end

        public static void  inviteFarmFriendsPostDialog (GenericPopupEvent event )
        {
            TEmailFarmFriends _loc_2 =null ;
            if (event.button == GenericPopup.YES)
            {
                _loc_2 = new TEmailFarmFriends();
                GameTransactionManager.addTransaction(_loc_2);
            }
            return;
        }//end

        public static void  pushNewItems (GenericPopupEvent event )
        {
            int _loc_2 =0;
            Array _loc_3 =null ;
            Array _loc_4 =null ;
            event.target.removeEventListener(GenericPopupEvent.SELECTED, pushNewItems);
            if (event.button == GenericPopup.ACCEPT)
            {
                _loc_2 = Global.player.level;
                _loc_3 = new Array();
                _loc_4 = new Array();
                getNewLevelItemsXML(_loc_2, _loc_3, _loc_4);
                displayMiniMarketDialog(_loc_4, "motd", "tool");
            }
            return;
        }//end

        public static boolean  resolveIfAssetsHaveNotLoaded (String param1 )
        {
            double _loc_2 =0;
            double _loc_3 =0;
            if (!Global.delayedAssets.isAssetLoaded(param1))
            {
                _loc_2 = Global.ui.mouseX;
                _loc_3 = Global.ui.mouseY;
                displayStatus(ZLoc.t("Dialogs", "AssetsLoading"), _loc_2, _loc_3);
                return true;
            }
            return false;
        }//end

        public static void  hideAutomationCatalog ()
        {
            Catalog _loc_1 =null ;
            if (m_tabbedCatalog && m_tabbedCatalog.asset instanceof AutomationUI)
            {
                _loc_1 = m_tabbedCatalog;
                m_tabbedCatalog = null;
                _loc_1.removeEventListener(MarketEvent.MARKET_BUY, onMarketClick);
                _loc_1.removeEventListener(UIEvent.OPEN_ACTION_MENU, onMarketMenu);
                _loc_1.removeEventListener(CloseEvent.CLOSE, onCatalogClose);
                _loc_1.close();
                if (_loc_1.parent)
                {
                    _loc_1.parent.removeChild(_loc_1);
                }
            }
            return;
        }//end

        public static void  clearTabbedCatalog ()
        {
            if (m_tabbedCatalog == null)
            {
                return;
            }
            m_tabbedCatalog.removeEventListener(MarketEvent.MARKET_BUY, onMarketClick);
            m_tabbedCatalog.removeEventListener(UIEvent.OPEN_ACTION_MENU, onMarketMenu);
            m_tabbedCatalog.removeEventListener(CloseEvent.CLOSE, onCatalogClose);
            m_tabbedCatalog.close();
            if (m_tabbedCatalog.parent)
            {
                m_tabbedCatalog.parent.removeChild(m_tabbedCatalog);
            }
            m_tabbedCatalog = null;
            return;
        }//end

        public static Catalog  displayTabbedCatalog (ItemCatalogUI param1 ,Array param2 ,CatalogParams param3 ,boolean param4 =false )
        {
            if (resolveIfAssetsHaveNotLoaded(DelayedAssetLoader.MARKET_ASSETS))
            {
                return null;
            }
            if (resolveIfAssetsHaveNotLoaded(DelayedAssetLoader.TABBED_MARKET_ASSETS))
            {
                return null;
            }
            Global.marketSessionTracker.startSession();
            if (m_tabbedCatalog == null)
            {
                m_tabbedCatalog = new TabbedCatalog(param1, param2, param3);
            }
            else if (m_tabbedCatalog != null)
            {
                if (param4)
                {
                    m_tabbedCatalog.showArrow();
                }
                if (param3.type != null)
                {
                    m_tabbedCatalog.type = param3.type;
                }
                if (param3.itemName != null)
                {
                    m_tabbedCatalog.item = param3.itemName;
                }
                if (param3.overrideTitle != null)
                {
                    m_tabbedCatalog.overrideTitle = param3.overrideTitle;
                }
                m_tabbedCatalog.closeMarket = param3.closeMarket;
            }
            m_tabbedCatalog.addEventListener(MarketEvent.MARKET_BUY, onMarketClick, false, 0, true);
            m_tabbedCatalog.addEventListener(UIEvent.OPEN_ACTION_MENU, onMarketMenu, false, 0, true);
            m_tabbedCatalog.addEventListener(CloseEvent.CLOSE, onCatalogClose, false, 0, true);
            Global.ui.bottomUI.addChild(m_tabbedCatalog);
            m_tabbedCatalog.show();
            if (m_catalog)
            {
                m_catalog.close();
            }
            UI.showNeighbors(false);
            boolean _loc_5 =true ;
            m_tabbedCatalog.mouseChildren = true;
            m_tabbedCatalog.mouseEnabled = true;
            return m_tabbedCatalog;
        }//end

        public static void  closeTabbedCatalog ()
        {
            if (m_tabbedCatalog && m_tabbedCatalog.parent)
            {
                m_tabbedCatalog.close();
            }
            return;
        }//end

        public static void  refreshCatalogItems (Array param1 ,boolean param2 =false )
        {
            if (param2)
            {
                if (m_oldCatalog)
                {
                    m_oldCatalog.updateCellsByItemNames(param1);
                }
                if (m_newCatalog)
                {
                    m_newCatalog.updateCellsByItemNames(param1);
                }
            }
            if (m_catalog)
            {
                m_catalog.updateCellsByItemNames(param1);
            }
            return;
        }//end

        public static void  refreshCatalogItmsByKeyword (String param1 ,boolean param2 =false )
        {
            _loc_3 =Global.gameSettings().getItemsByKeywordMatch(param1 );
            if (_loc_3 && _loc_3.length())
            {
                refreshCatalogItems(_loc_3, param2);
            }
            return;
        }//end

        public static Catalog  displayCatalog (CatalogParams param1 ,boolean param2 =false ,boolean param3 =false )
        {
            ItemCatalogUI _loc_5 =null ;
            _loc_4 =Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_MARKET_2 );
            chooseMarket(param1.itemName, param1.exclusiveCategory, _loc_4);
            if (resolveIfAssetsHaveNotLoaded(DelayedAssetLoader.MARKET_ASSETS))
            {
                return null;
            }
            if (m_catalog && m_catalog.parent)
            {
                if (param3)
                {
                    m_catalog.exclusiveCategory = param1.exclusiveCategory;
                    m_catalog.type = param1.type;
                    m_catalog.subType = param1.subType;
                    m_catalog.items = param1.customItems;
                    m_catalog.ignoreExcludeExperiments = param1.ignoreExcludeExperiments;
                }
                return m_catalog;
            }
            Global.marketSessionTracker.startSession();
            if (m_catalog == null)
            {
                if (param1.type == null)
                {
                    param1.type = getDefaultCatalogType(_loc_4);
                }
                _loc_5 = new ItemCatalogUI();
                m_oldCatalog = new Catalog(_loc_5, param1);
                if (isNewMarket(param1.itemName, param1.exclusiveCategory, _loc_4))
                {
                    if (param1.type == "farming")
                    {
                        param1.type = "goods";
                    }
                    param1.type = param1.exclusiveCategory ? ("specials") : (param1.type);
                    m_newCatalog = new LargeCatalog(new LargeCatalogUI(), param1);
                }
                chooseMarket(param1.itemName, param1.exclusiveCategory, _loc_4);
                m_catalog.ignoreExcludeExperiments = param1.ignoreExcludeExperiments;
            }
            else if (m_catalog != null)
            {
                m_catalog.ignoreExcludeExperiments = param1.ignoreExcludeExperiments;
                if (m_newCatalog == null && isNewMarket(param1.itemName, param1.exclusiveCategory, _loc_4))
                {
                    if (param1.type == "farming")
                    {
                        param1.type = "goods";
                    }
                    m_newCatalog = new LargeCatalog(new LargeCatalogUI(), param1);
                }
                chooseMarket(param1.itemName, param1.exclusiveCategory, _loc_4);
                if (param2)
                {
                    m_catalog.showArrow();
                }
                if (m_catalog.exclusiveCategory && param1.type != "plot_contract" && param1.type != "ship_contract" && param1.type != "factory_contract")
                {
                    if (param1.type == null)
                    {
                        param1.type = getDefaultCatalogType(_loc_4);
                    }
                }
                m_catalog.exclusiveCategory = param1.exclusiveCategory;
                if (param1.type != null)
                {
                    m_catalog.type = param1.type;
                    m_catalog.items = param1.customItems;
                }
                if (param1.customItems != null)
                {
                    m_catalog.items = param1.customItems;
                }
                if (param1.subType != null)
                {
                    m_catalog.subType = param1.subType;
                }
                if (param1.itemName != null)
                {
                    m_catalog.item = param1.itemName;
                }
                if (!m_catalog.allowsExclusivity(m_catalog.type))
                {
                    m_catalog.exclusiveCategory = false;
                }
                if (param1.overrideTitle != null)
                {
                    m_catalog.overrideTitle = param1.overrideTitle;
                }
                m_catalog.closeMarket = param1.closeMarket;
            }
            m_catalog.addEventListener(MarketEvent.MARKET_BUY, onMarketClick, false, 0, true);
            m_catalog.addEventListener(UIEvent.OPEN_ACTION_MENU, onMarketMenu, false, 0, true);
            m_catalog.addEventListener(CloseEvent.CLOSE, onCatalogClose, false, 0, true);
            Global.ui.bottomUI.addChild(m_catalog);
            m_catalog.show();
            if (m_tabbedCatalog)
            {
                m_tabbedCatalog.close();
            }
            if (_loc_4 != ExperimentDefinitions.MARKET_2_FEATURE || m_catalog == m_oldCatalog)
            {
                UI.showNeighbors(false);
            }
            else
            {
                Global.ui.displayLightbox(true, MASK_ALL_BUT_MARKET, true);

                Global.ui.m_friendSprite.mouseEnabled = false;
                Global.ui.m_friendSprite.mouseChildren = false;
            }

            m_catalog.mouseChildren = true;
            m_catalog.mouseEnabled = true;
            return m_catalog;
        }//end

        public static boolean  isMarketOpen ()
        {
            return m_catalog && m_catalog.isShown;
        }//end

        public static void  closeCatalog ()
        {
            if (m_oldCatalog && m_oldCatalog.parent)
            {
                m_oldCatalog.close();
            }
            if (m_newCatalog && m_newCatalog.parent)
            {
                m_newCatalog.close();
            }
            return;
        }//end

        private static String  getDefaultCatalogType (int param1 )
        {
            if (param1 == ExperimentDefinitions.MARKET_2_FEATURE && Global.player.level > 6 && Global.localizer.langCode != "ja")
            {
                return "specials";
            }
            return "residence";
        }//end

        public static void  chooseMarket (String param1 ,boolean param2 ,int param3 )
        {
            if (param2 == false && Global.player.level > 6 && param3 == ExperimentDefinitions.MARKET_2_FEATURE)
            {
                m_catalog = m_newCatalog;
            }
            else
            {
                m_catalog = m_oldCatalog;
            }
            return;
        }//end

        public static boolean  isNewMarket (String param1 ,boolean param2 ,int param3 )
        {
            if (param2 == false && Global.player.level > 6 && param3 == ExperimentDefinitions.MARKET_2_FEATURE)
            {
                return true;
            }
            return false;
        }//end

        public static void  refreshCatalog (boolean param1 )
        {
            if (!UI.AUTO_CLOSE_MARKET || param1)
            {
                m_catalog.type = m_catalog.type;
                m_catalog.items = m_catalog.items;
            }
            return;
        }//end

        public static void  showNeighbors (boolean param1 =true )
        {
            if (param1 !=null)
            {
                TweenLite.to(Global.ui.m_friendSprite, 0.5, {y:472});
            }
            else
            {
                TweenLite.to(Global.ui.m_friendSprite, 0.5, {y:722});
            }
            return;
        }//end

        public static FranchiseMenu  displayNewFranchise (boolean param1 =false )
        {
            if (resolveIfAssetsHaveNotLoaded(DelayedAssetLoader.FRANCHISE_ASSETS))
            {
                return null;
            }
            if (param1 !=null)
            {
                if (m_franchiseMenu)
                {
                    m_franchiseMenu.y = (Global.ui.screenHeight - m_franchiseMenu.height) / 2;
                    if (m_franchiseMenu.y < Global.hud.getComponent("coins").height)
                    {
                        m_franchiseMenu.y = Global.hud.getComponent("coins").height;
                    }
                    m_franchiseMenu.x = (Global.ui.screenWidth - m_franchiseMenu.width) / 2 - Global.ui.x;
                }
                return m_franchiseMenu;
            }
            if (m_franchiseMenu == null)
            {
                m_franchiseMenu = new FranchiseMenu();
                Global.ui.addChild(m_franchiseMenu);
                m_franchiseMenu.alpha = 0;
                m_franchiseMenu.y = (Global.ui.screenHeight - m_franchiseMenu.height) / 2;
                if (m_franchiseMenu.y < Global.hud.getComponent("coins").height)
                {
                    m_franchiseMenu.y = Global.hud.getComponent("coins").height;
                }
                m_franchiseMenu.x = (Global.ui.screenWidth - m_franchiseMenu.width) / 2 - Global.ui.x;
                m_franchiseMenu.addEventListener(CloseEvent.CLOSE, onFranchiseClose_n, false, 0, true);
                TweenLite.to(m_franchiseMenu, 0.5, {alpha:1});
            }
            return m_franchiseMenu;
        }//end

        public static void  redirectToInvitePage (String param1 ,String param2 ,Function param3 =null )
        {
            _loc_4 = param1"invite.php?ref="+;
            if (param2)
            {
                _loc_4 = _loc_4 + ("&view=" + param2);
            }
            FrameManager.showTray(_loc_4, null, param3);
            return;
        }//end

        private static void  onFranchiseClose_n (Event event )
        {
            Array _loc_2 =null ;
            int _loc_3 =0;
            if (m_franchiseMenu)
            {
                _loc_2 = Global.world.getActiveGameModes();
                _loc_3 = 0;
                while (_loc_3 < _loc_2.length())
                {

                    Global.world.removeGameMode(_loc_2.get(_loc_3));
                    _loc_3++;
                }
                TweenLite.to(m_franchiseMenu, 0.5, {alpha:0, onComplete:endFranchise_n});
                Global.world.addGameMode(new GMPlay());
                m_franchiseMenu.removeEventListener(CloseEvent.CLOSE, onFranchiseClose_n);
            }
            return;
        }//end

        private static void  endFranchise_n ()
        {
            m_franchiseMenu.visible = false;
            m_franchiseMenu = null;
            return;
        }//end

        private static void  onFranchiseClick (Event event )
        {
            Global.world.addGameMode(new GMPlaceMapResource("bus_bakery", Business, false));
            return;
        }//end

        private static void  onFranchiseManagerClick (Event event )
        {
            if (event.type == FranchiseEvent.FRANCHISE_COLLECT)
            {
            }
            else if (event.type == FranchiseEvent.FRANCHISE_REMIND)
            {
            }
            else if (event.type == FranchiseEvent.FRANCHISE_SUPPLY)
            {
            }
            return;
        }//end

        public static boolean  displayCollectionFlyout (String param1 ,Point param2 ,Point param3 =null )
        {
            return CollectionPanelPopout.getInstance().addItemByName(param1, param2, param3);
        }//end

        public static void  displayCollectableFadeIn (String param1 )
        {
            CollectionPanelPopout.getInstance().itemAppear(param1);
            return;
        }//end

        public static void  checkCollectableFadeIn (String param1 )
        {
            CollectionPanelPopout.getInstance().verifyCollection(param1);
            return;
        }//end

        public static Catalog  displayInventory (String param1)
        {
            if (resolveIfAssetsHaveNotLoaded(DelayedAssetLoader.INVENTORY_ASSETS))
            {
                return null;
            }
            if (m_inventory == null)
            {
                m_inventory = new InventoryView(param1);
            }
            else if (m_inventory.asset.shelf.currentItemName != param1 && param1 != null)
            {
                m_inventory.asset.goToItem(param1);
            }
            if (!m_inventory.isShown)
            {
                Global.ui.m_buyModeButton.setEnabled(false);
                m_inventory.addEventListener(MarketEvent.MARKET_BUY, onMarketClick, false, 0, true);
                m_inventory.addEventListener(UIEvent.OPEN_ACTION_MENU, onMarketMenu, false, 0, true);
                m_inventory.addEventListener(CloseEvent.CLOSE, onInventoryClose, false, 0, true);
                Global.ui.addChild(m_inventory);
                m_inventory.show();
            }
            else
            {
                m_inventory.close();
            }
            return m_inventory;
        }//end

        private static void  onInventoryClose (Event event )
        {
            if (m_inventory)
            {
                Global.ui.hideExpandedMainMenu();
                Global.ui.switchDefaultActionButton(UIEvent.CURSOR);
                Global.ui.m_buyModeButton.setEnabled(true);
                m_inventory.removeEventListener(MarketEvent.MARKET_BUY, onMarketClick);
                m_inventory.removeEventListener(UIEvent.OPEN_ACTION_MENU, onMarketMenu);
                m_inventory.removeEventListener(CloseEvent.CLOSE, onInventoryClose);
            }
            return;
        }//end

        public static void  deinitializeInventory ()
        {
            if (m_inventory)
            {
                if (m_inventory.forceStayOpen)
                {
                    return;
                }
                if (m_inventory.isShown)
                {
                    m_inventory.close();
                }
                m_inventory = null;
            }
            return;
        }//end

        public static void  refreshInventory ()
        {
            if (m_inventory && m_inventory.isShown && m_inventory.doRefreshInventory)
            {
                m_inventory.refreshInventory();
            }
            return;
        }//end

        public static Catalog  displayCollections (String param1)
        {
            if (resolveIfAssetsHaveNotLoaded(DelayedAssetLoader.INVENTORY_ASSETS))
            {
                return null;
            }
            if (m_collections == null)
            {
                m_collections = new CollectionView(param1);
            }
            else if (m_collections.asset.shelf.currentItemName != param1)
            {
                m_collections.asset.goToItem(param1);
            }
            if (!m_collections.isShown)
            {
                m_collections.addEventListener(MarketEvent.MARKET_BUY, onMarketClick, false, 0, true);
                m_collections.addEventListener(UIEvent.OPEN_ACTION_MENU, onMarketMenu, false, 0, true);
                m_collections.addEventListener(CloseEvent.CLOSE, onCollectionsClose, false, 0, true);
                Global.ui.addChild(m_collections);
                m_collections.show();
            }
            Global.ui.m_buyModeButton.setEnabled(false);
            return m_collections;
        }//end

        private static void  onCollectionsClose (Event event )
        {
            Array _loc_2 =null ;
            int _loc_3 =0;
            if (m_collections)
            {
                _loc_2 = Global.world.getActiveGameModes();
                _loc_3 = 0;
                while (_loc_3 < _loc_2.length())
                {

                    Global.world.removeGameMode(_loc_2.get(_loc_3));
                    _loc_3++;
                }
                Global.world.addGameMode(new GMPlay());
                Global.ui.hideExpandedMainMenu();
                Global.ui.switchDefaultActionButton(UIEvent.CURSOR);
                Global.ui.m_buyModeButton.setEnabled(true);
                m_collections.removeEventListener(MarketEvent.MARKET_BUY, onMarketClick);
                m_collections.removeEventListener(UIEvent.OPEN_ACTION_MENU, onMarketMenu);
                m_collections.removeEventListener(CloseEvent.CLOSE, onInventoryClose);
            }
            return;
        }//end

        public static void  displayVisitorCenterDialog ()
        {
            VisitorCenterDialog _loc_1 =null ;
            if (Global.world.vistorCenterText)
            {
                _loc_1 = new VisitorCenterDialog(Global.world.vistorCenterText, "VisitorUI", 0, null, "VisitorUI");
            }
            else
            {
                _loc_1 = new VisitorCenterDialog(ZLoc.t("Dialogs", "VisitorUI_message"), "VisitorUI", 0, null, "VisitorUI");
            }
            UI.displayPopup(_loc_1);
            return;
        }//end

        public static ValentineDialog  displayValentineDialog ()
        {
            if (m_vDlg == null)
            {
                m_vDlg = new ValentineDialog();
                Global.ui.addChild(m_vDlg);
                m_vDlg.show();
                m_vDlg.addEventListener(Event.CLOSE, removeValDialog, false, 0, true);
            }
            return m_vDlg;
        }//end

        private static void  removeValDialog (Event event )
        {
            Global.ui.removeChild(m_vDlg);
            m_vDlg.removeEventListener(Event.CLOSE, removeValDialog);
            m_vDlg = null;
            return;
        }//end

        public static MunicipalUpgradesDialog  displayMunicipalUpgradesDialog (String param1 ,Array param2 ,Function param3 ,Function param4 ,Function param5 ,double param6 )
        {
            if (m_municipalUpgradesDialog == null)
            {
                m_municipalUpgradesDialog = new MunicipalUpgradesDialog(param1, param2, param3, param4, param5, param6);
                Global.ui.addChild(m_municipalUpgradesDialog);
                Global.ui.displayLightbox(true, MASK_GAME_AND_BOTTOMBAR, true);
                m_municipalUpgradesDialog.show();
                if (Global.ui.m_toolTip && Global.stage.contains(Global.ui.m_toolTip))
                {
                    Global.stage.removeChild(Global.ui.m_toolTip);
                }
                m_municipalUpgradesDialog.addEventListener(Event.CLOSE, removeMunicipalUpgradesDialog, false, 0, true);
            }
            return m_municipalUpgradesDialog;
        }//end

        private static void  removeMunicipalUpgradesDialog (Event event )
        {
            if (Global.ui.m_toolTip)
            {
                Global.stage.addChild(Global.ui.m_toolTip);
            }
            Global.ui.removeChild(m_municipalUpgradesDialog);
            Global.ui.displayLightbox(false);
            m_municipalUpgradesDialog.removeEventListener(Event.CLOSE, removeMunicipalUpgradesDialog);
            m_municipalUpgradesDialog = null;
            return;
        }//end

        private static void  removeMunInventoryUpgradesDialog (Event event )
        {
            if (Global.ui.m_toolTip)
            {
                Global.stage.addChild(Global.ui.m_toolTip);
            }
            Global.ui.removeChild(m_munInventoryUpgradesDialog);
            Global.ui.displayLightbox(false);
            m_munInventoryUpgradesDialog.removeEventListener(Event.CLOSE, removeMunInventoryUpgradesDialog);
            m_munInventoryUpgradesDialog = null;
            return;
        }//end

        public static MunInventoryUpgradesDialog  displayMunInventoryUpgradesDialog (String param1 ,Array param2 ,Function param3 ,Function param4 ,Function param5 ,double param6 ,AbstractGate param7 )
        {
            if (m_munInventoryUpgradesDialog == null)
            {
                m_munInventoryUpgradesDialog = new MunInventoryUpgradesDialog(param1, param2, param3, param4, param5, param6, param7);
                Global.ui.addChild(m_munInventoryUpgradesDialog);
                Global.ui.displayLightbox(true, MASK_GAME_AND_BOTTOMBAR, true);
                m_munInventoryUpgradesDialog.show();
                if (Global.ui.m_toolTip && Global.stage.contains(Global.ui.m_toolTip))
                {
                    Global.stage.removeChild(Global.ui.m_toolTip);
                }
                m_munInventoryUpgradesDialog.addEventListener(Event.CLOSE, removeMunInventoryUpgradesDialog, false, 0, true);
            }
            return m_munInventoryUpgradesDialog;
        }//end

        public static BusinessUpgradesDialog  displayBusinessUpgradesDialog (MapResource param1 ,Function param2 ,Function param3 ,Function param4 )
        {
            if (m_businessUpgradesDialog == null)
            {
                m_businessUpgradesDialog = new BusinessUpgradesDialog(param1, param2, param3, param4);
                Global.ui.addChild(m_businessUpgradesDialog);
                Global.ui.displayLightbox(true, MASK_GAME_AND_BOTTOMBAR, true);
                m_businessUpgradesDialog.show();
                if (Global.ui.m_toolTip && Global.stage.contains(Global.ui.m_toolTip))
                {
                    Global.stage.removeChild(Global.ui.m_toolTip);
                }
                m_businessUpgradesDialog.addEventListener(Event.CLOSE, removeBusinessUpgradesDialog, false, 0, true);
            }
            return m_businessUpgradesDialog;
        }//end

        private static void  removeBusinessUpgradesDialog (Event event )
        {
            if (Global.ui.m_toolTip)
            {
                Global.stage.addChild(Global.ui.m_toolTip);
            }
            Global.ui.removeChild(m_businessUpgradesDialog);
            Global.ui.displayLightbox(false);
            m_businessUpgradesDialog.removeEventListener(Event.CLOSE, removeBusinessUpgradesDialog);
            m_businessUpgradesDialog = null;
            return;
        }//end

        public static HunterDialog  displayCopsDialog (String param1 )
        {
            m_hunterDlg = new HunterDialog(param1);
            Global.ui.addChild(m_hunterDlg);
            Global.ui.displayLightbox(true, MASK_GAME_AND_BOTTOMBAR, true);
            m_hunterDlg.show();
            m_hunterDlg.addEventListener(Event.CLOSE, removeCopsDialog, false, 0, true);
            if (Global.ui.m_toolTip && Global.stage.contains(Global.ui.m_toolTip))
            {
                Global.stage.removeChild(Global.ui.m_toolTip);
            }
            return m_hunterDlg;
        }//end

        public static HunterDialog  displayHubDialog (String param1 )
        {
            if (param1 !=null)
            {
                return displayCopsDialog(param1);
            }
            return null;
        }//end

        public static void  requestPatrol (String param1 ,MapResource param2 ,String param3 =null ,int param4 =-1)
        {
            boolean _loc_5 =false ;
            Object _loc_6 =null ;
            HunterPreyWorkers _loc_7 =null ;
            HunterData _loc_8 =null ;
            if (m_hunterDlg)
            {
                _loc_5 = false;
                for(int i0 = 0; i0 < m_waitingPatrols.size(); i0++)
                {
                	_loc_6 = m_waitingPatrols.get(i0);

                    if (_loc_6.groupId == param1 && _loc_6.type == param3 && _loc_6.slot == param4)
                    {
                        _loc_5 = true;
                        break;
                    }
                }
                if (_loc_5 == false)
                {
                    m_waitingPatrols.push({groupId:param1, target:param2, type:param3, slot:param4});
                }
            }
            else
            {
                _loc_7 =(HunterPreyWorkers) Global.world.citySim.preyManager.getWorkerManagerByGroup(HunterDialog.groupId).getWorkers(HunterPreyWorkers.FEATURE_HUNTER_PREY_BUCKET);
                _loc_8 = _loc_7.getHunterData(param4);
                if (_loc_8.getCopReference() == null)
                {
                    PreyManager.addPatrol(param1, param2, param3, param4);
                }
            }
            return;
        }//end

        public static Array  waitingPatrols ()
        {
            return m_waitingPatrols;
        }//end

        private static void  removeCopsDialog (Event event )
        {
            Object _loc_2 =null ;
            if (m_hunterDlg)
            {
                Global.stage.addChild(Global.ui.m_toolTip);
                Global.ui.removeChild(m_hunterDlg);
                Global.ui.displayLightbox(false);
                m_hunterDlg.removeEventListener(Event.CLOSE, removeValDialog);
                m_hunterDlg = null;
                if (m_waitingPatrols.length())
                {
                    for(int i0 = 0; i0 < m_waitingPatrols.size(); i0++)
                    {
                    	_loc_2 = m_waitingPatrols.get(i0);

                        PreyManager.addPatrol(_loc_2.groupId, _loc_2.target, _loc_2.type, _loc_2.slot);
                    }
                }
                m_waitingPatrols = new Array();
            }
            return;
        }//end

        public static ValentineCardViewDialog  displayValentineCardViewer (Admirer param1 )
        {
            ValentineCard _loc_3 =null ;
            ValentineCardViewDialog _loc_4 =null ;
            _loc_2 = ValentineManager.getValentinesFromUID(param1.uid);
            for(int i0 = 0; i0 < _loc_2.size(); i0++)
            {
            	_loc_3 = _loc_2.get(i0);

                if (_loc_3.id.charAt(0) != "i")
                {
                    break;
                }
                _loc_3.id = _loc_3.id.slice(1);
            }
            _loc_2.sortOn("id", Array.NUMERIC);
            _loc_4 = new ValentineCardViewDialog(_loc_2, param1);
            UI.displayPopup(_loc_4);
            return _loc_4;
        }//end

        public static Market  displayMarketDialog (String param1 ="unknown",String param2 ="tool",String param3 ="all")
        {
            if (m_marketWindow == null)
            {
                m_marketWindow = new Market(param1, param2, param3);
                m_marketWindow.addEventListener(MarketEvent.MARKET_BUY, onMarketClick);
                m_marketWindow.addEventListener(CloseEvent.CLOSE, onMarketClose);
                displayPopup(m_marketWindow);
                GlobalEngine.socialNetwork.publishFeedStory("VisitMarket", {}, [], true);
            }
            return m_marketWindow;
        }//end

        public static Market  displayMiniMarketDialog (Array param1 ,String param2 ="unknown",String param3 ="tool",Function param4 =null )
        {
            if (m_marketWindow == null)
            {
                m_marketWindow = new MiniMarket(param1, param2, param3);
                m_marketWindow.setCloseClickCallback(param4);
                m_marketWindow.addEventListener(MarketEvent.MARKET_BUY, onMarketClick);
                m_marketWindow.addEventListener(CloseEvent.CLOSE, onMarketClose);
                displayPopup(m_marketWindow);
            }
            return m_marketWindow;
        }//end

        public static void  refreshMarketDialog ()
        {
            if (m_marketWindow != null)
            {
                m_marketWindow.refreshLayout();
            }
            return;
        }//end

        public static Market  getMarketDialog ()
        {
            return m_marketWindow;
        }//end

        public static boolean  isViewOpen (Object param1)
        {
            DisplayObject _loc_3 =null ;
            boolean _loc_4 =false ;
            _loc_2 =Global.ui.numChildren ;
            while (_loc_2--)
            {

                _loc_3 = Global.ui.getChildAt(_loc_2);
                _loc_4 = Boolean(_loc_3 instanceof param1);
                if (_loc_4)
                {
                    return true;
                }
            }
            return false;
        }//end

        public static Catalog  displayItemStorageDialog (ItemStorage param1 )
        {
            if (resolveIfAssetsHaveNotLoaded(DelayedAssetLoader.INVENTORY_ASSETS))
            {
                return null;
            }
            if (m_storage == null || m_storage.storageItem != param1)
            {
                m_storage = new StorageView(param1);
            }
            else
            {
                m_storage.refresh();
            }
            if (!m_storage.isShown)
            {
                Global.ui.m_buyModeButton.setEnabled(false);
                m_storage.addEventListener(MarketEvent.MARKET_BUY, onMarketClick, false, 0, true);
                m_storage.addEventListener(UIEvent.OPEN_ACTION_MENU, onMarketMenu, false, 0, true);
                m_storage.addEventListener(CloseEvent.CLOSE, onItemStorageClose, false, 0, true);
                Global.ui.addChild(m_storage);
                m_storage.show();
            }
            else
            {
                m_storage.close();
            }
            return m_storage;
        }//end

        private static void  onItemStorageClose (Event event =null )
        {
            if (m_storage)
            {
                Global.ui.hideExpandedMainMenu();
                Global.ui.switchDefaultActionButton(UIEvent.CURSOR);
                Global.ui.m_buyModeButton.setEnabled(true);
                m_storage.removeEventListener(MarketEvent.MARKET_BUY, onMarketClick);
                m_storage.removeEventListener(UIEvent.OPEN_ACTION_MENU, onMarketMenu);
                m_storage.removeEventListener(CloseEvent.CLOSE, onStorageClose);
            }
            return;
        }//end

        public static void  displayGiftDialog ()
        {
            if (m_giftSellWindow == null)
            {
                if (Global.player.getGiftCount() > 0)
                {
                    m_giftSellWindow = new GiftSellDialog();
                    m_giftSellWindow.addEventListener(MarketEvent.MARKET_GIFT, onMarketClick);
                    m_giftSellWindow.addEventListener(CloseEvent.CLOSE, onSellGiftClose);
                    UI.displayPopup(m_giftSellWindow);
                }
                else
                {
                    m_giftSellWindow = new GiftSellDialog();
                    StatsManager.count("farmville_sell_gift_screen", "click_sell_all_item", "gifts_empty_impression");
                    UI.displayMessage(ZLoc.t("Dialogs", "NoMoreGiftsToSell"), GenericPopup.TYPE_YESNO, noMoreGiftsHandler, "noMoreGiftsHandler", true);
                }
            }
            return;
        }//end

        public static void  displayStorageDialog (SlotDialog param1 )
        {
            if (m_storageWindow != null)
            {
                m_storageWindow.removeEventListener(MarketEvent.MARKET_GIFT, onMarketClick);
                m_storageWindow.removeEventListener(CloseEvent.CLOSE, onStorageClose);
                m_storageWindow.close();
                m_storageWindow = null;
            }
            m_storageWindow = param1;
            m_storageWindow.addEventListener(MarketEvent.MARKET_GIFT, onMarketClick);
            m_storageWindow.addEventListener(CloseEvent.CLOSE, onStorageClose);
            UI.displayPopup(m_storageWindow);
            return;
        }//end

        public static void  displayOutOfGoodsDialog ()
        {
            boolean inGoodsForCashExperiment ;
            CustomDialog customDlg ;
            Function sendCloseStats ;
            sendCloseStats =void  (GenericPopupEvent event )
            {
                StatsManager.count("dialog_unsampled", "buy_goods_for_cash", "close");
                customDlg.view.removeEventListener(GenericPopupEvent.SELECTED, arguments.callee);
                return;
            }//end
            ;
            inGoodsForCashExperiment = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_GOODS_FOR_CASH) == ExperimentDefinitions.GOODS_FOR_CASH_ENABLED;
            if (inGoodsForCashExperiment)
            {
                StatsManager.count("dialog_unsampled", "buy_goods_for_cash", "view");
            }
            message = ZLoc.t("Dialogs","TrainUI_OutOfGoods_message");
            if (inGoodsForCashExperiment)
            {
                message = message + (" " + ZLoc.t("Dialogs", "TrainUI_GoodsForCash_message"));
            }
            customDlg = new CustomDialog(message, "OutOfGoods", "TrainUI_OutOfGoods", "assets/dialogs/outOfGoods.png", GenericDialogView.ICON_POS_BOTTOM);
            if (inGoodsForCashExperiment)
            {
                customDlg .addEventListener (LoaderEvent .LOADED ,void  (LoaderEvent event )
            {
                event.target.removeEventListener(event.type, arguments.callee);
                customDlg.view.addEventListener(GenericPopupEvent.SELECTED, sendCloseStats);
                return;
            }//end
            );
            }
            customDlg.addButton(ZLoc.t("Dialogs", inGoodsForCashExperiment ? ("TrainUI_BuyGoods") : ("TrainUI_BuyCrop")), Curry.curry(function (param1:GenericDialog, param2:Event) : void
            {
                if (inGoodsForCashExperiment)
                {
                    StatsManager.count("dialog_unsampled", "buy_goods_for_cash", "click");
                    customDlg.view.removeEventListener(GenericPopupEvent.SELECTED, sendCloseStats);
                }
                UI.displayCatalog(new CatalogParams(inGoodsForCashExperiment ? ("goods") : ("farming")).setCloseMarket(true), false, true);
                if (customDlg)
                {
                    customDlg.close();
                }
                return;
            }//end
            , customDlg));
            if (!inGoodsForCashExperiment)
            {
                customDlg .addButton (ZLoc .t ("Dialogs","TrainUI_SendTrain"),Curry .curry (void  (GenericDialog param1 ,Event param2 )
            {
                Global.world.citySim.trainManager.showTrainScheduleCatalog();
                if (customDlg)
                {
                    customDlg.close();
                }
                return;
            }//end
            , customDlg));
            }
            customDlg.buildCustomDialog();
            UI.displayPopup(customDlg);
            return;
        }//end

        private static void  onCatalogClose (CloseEvent event )
        {
            Array _loc_2 =null ;
            GameMode _loc_3 =null ;
            if (Global.isVisiting())
            {
                if (!AUTO_CLOSE_MARKET || AUTO_CLOSE_MARKET && !(Global.world.getTopGameMode() instanceof GMPlaceBizOnLotSite) && Global.isVisiting())
                {
                    Global.franchiseManager.placementMode = false;
                }
            }
            if (m_catalog)
            {
                if (event.bKeepState == false && !UI.AUTO_CLOSE_MARKET)
                {
                    _loc_2 = Global.world.getActiveGameModes();
                    if (Global.isVisiting())
                    {
                        if (Global.player.canEnterGMVisitBuy())
                        {
                            _loc_3 = new GMVisitBuy(Global.world.ownerId);
                        }
                        else
                        {
                            _loc_3 = new GMVisit(Global.world.ownerId);
                        }
                    }
                    else
                    {
                        _loc_3 = new GMPlay();
                    }
                    Global.world.addGameMode(_loc_3, true);
                }

                Global.ui.m_friendSprite.mouseEnabled = true;
                Global.ui.m_friendSprite.mouseChildren = true;
                Global.ui.displayLightbox(false);
                Global.ui.hideExpandedMainMenu();
                m_catalog.removeEventListener(MarketEvent.MARKET_BUY, onMarketClick);
                m_catalog.removeEventListener(UIEvent.OPEN_ACTION_MENU, onMarketMenu);
                m_catalog.removeEventListener(CloseEvent.CLOSE, onCatalogClose);

                m_catalog.mouseEnabled = false;
                m_catalog.mouseChildren = false;
            }
            return;
        }//end

        private static void  onMarketMenu (Event event )
        {
            Catalog _loc_2 =null ;
            if (Global.ui.m_bExpandedMenuOpen == true)
            {
                Global.ui.hideExpandedMainMenu();
                Global.ui.m_bExpandedMenuOpen = false;
            }
            else
            {
                _loc_2 = null;
                if (event.currentTarget && event.currentTarget instanceof Catalog)
                {
                    _loc_2 =(Catalog) event.currentTarget;
                }
                Global.ui.m_bExpandedMenuOpen = Global.ui.displayExpandedMainMenu(ExpandedMainMenu, _loc_2);
            }
            return;
        }//end

        private static void  onMarketClose (Event event )
        {
            if (m_marketWindow)
            {
                m_marketWindow.removeEventListener(CloseEvent.CLOSE, onMarketClose);
                m_marketWindow = null;
            }
            return;
        }//end

        private static void  onStorageClose (Event event )
        {
            if (m_storageWindow)
            {
                m_storageWindow.removeEventListener(CloseEvent.CLOSE, onSellGiftClose);
                m_storageWindow = null;
            }
            return;
        }//end

        private static void  onSellGiftClose (Event event )
        {
            if (m_giftSellWindow)
            {
                m_giftSellWindow.removeEventListener(CloseEvent.CLOSE, onSellGiftClose);
                m_giftSellWindow = null;
            }
            return;
        }//end

        private static void  onNoGiftClose (GenericPopupEvent event )
        {
            m_giftSellWindow = null;
            return;
        }//end

        private static void  noMoreGiftsHandler (GenericPopupEvent event )
        {
            _loc_2 = event.button ==GenericPopup.YES ;
            if (_loc_2 == true)
            {
                StatsManager.count("farmville_sell_gift_screen", "click_sell_all_item", "gifts_empty_accept");
                GlobalEngine.socialNetwork.redirect("gifts.php?ref=empty_gift_box");
            }
            else
            {
                StatsManager.count("farmville_sell_gift_screen", "click_sell_all_item", "gifts_empty_cancel");
            }
            m_giftSellWindow = null;
            return;
        }//end

        public static void  onMarketClick (MarketEvent event )
        {
            GameMode _loc_6 =null ;
            Object _loc_12 =null ;
            StorageType _loc_13 =null ;
            String _loc_14 =null ;
            int _loc_15 =0;
            int _loc_16 =0;
            String _loc_17 =null ;
            Item _loc_18 =null ;
            String _loc_19 =null ;
            String _loc_20 =null ;
            MysteryCrateDialog _loc_21 =null ;
            GMDefault _loc_22 =null ;
            int _loc_23 =0;
            String _loc_24 =null ;
            String _loc_25 =null ;
            GameObject _loc_26 =null ;
            boolean _loc_2 =false ;
            int _loc_3 =0;
            int _loc_4 =0;
            GameMode _loc_5 =null ;
            _loc_6 = Global.world.getTopGameMode();

            Debug.debug6("UI.onMarketClick");

            m_lastMarketEventItemName = event.item;
            if (m_marketWindow)
            {
                m_marketWindow.removeEventListener(MarketEvent.MARKET_BUY, onMarketClick);
                m_marketWindow = null;
            }
            else if (m_giftSellWindow)
            {
                _loc_2 = true;
                m_giftSellWindow.removeEventListener(MarketEvent.MARKET_GIFT, onMarketClick);
                _loc_3 = Global.world.GIFTBOX_ID;
                m_giftSellWindow = null;
            }
            else if (event.eventSource == MarketEvent.SOURCE_INVENTORY)
            {
                if (m_inventory && event.target == m_inventory)
                {
                    Global.ui.m_buyModeButton.setEnabled(true);
                    if (!m_inventory.forceStayOpen)
                    {
                        m_inventory.removeEventListener(MarketEvent.MARKET_BUY, onMarketClick);
                        m_inventory.removeEventListener(UIEvent.OPEN_ACTION_MENU, onMarketMenu);
                        m_inventory.removeEventListener(CloseEvent.CLOSE, onInventoryClose);
                        m_inventory = null;
                    }
                }
                else
                {
                    event.target.removeEventListener(MarketEvent.MARKET_BUY, onMarketClick);
                }
                _loc_2 = true;
                if (Global.player.inventory.getItemCountByName(event.item) <= 0)
                {
                    return;
                }
            }
            else if (m_storage && event.eventSource == MarketEvent.SOURCE_STORAGE)
            {
                Global.ui.m_buyModeButton.setEnabled(true);
                m_storage.removeEventListener(MarketEvent.MARKET_BUY, onMarketClick);
                m_storage.removeEventListener(UIEvent.OPEN_ACTION_MENU, onMarketMenu);
                m_storage.removeEventListener(CloseEvent.CLOSE, onInventoryClose);
                _loc_13 = m_storage.storageType;
                _loc_14 = m_storage.storageKey;
                m_storage = null;
                _loc_2 = true;
                _loc_15 = Global.player.storageComponent.getStorageUnit(_loc_13, _loc_14).getItemCountByName(event.item);
                if (_loc_15 <= 0)
                {
                    onItemStorageClose();
                }
                _loc_5 = new GMPlaceFromStorage(_loc_13, _loc_14, event.item, Decoration);
            }
            else if (m_catalog)
            {
                if (AUTO_CLOSE_MARKET)
                {
                    m_catalog.removeEventListener(MarketEvent.MARKET_BUY, onMarketClick);
                }
            }
            _loc_7 =Global.gameSettings().getItemByName(event.item );
            int _loc_8 =0;
            int _loc_9 =0;
            String _loc_10 =null ;
            int _loc_11 =0;
            switch(event.eventType)
            {
                case MarketEvent.RESIDENCE:
                {
                    if (!_loc_5)
                    {
                        _loc_5 = new GMPlaceMapResource(event.item, Residence, _loc_2);
                    }
                    _loc_10 = _loc_7.populationType;
                    _loc_8 = Global.world.citySim.getTotalPopulation(_loc_10) + _loc_7.populationBase;
                    _loc_9 = Global.world.citySim.getPopulationCap(_loc_10);
				    // add by xinghai
					_loc_8 = 0;
					_loc_9 = 1;

                    if (_loc_8 > _loc_9)
                    {
                        _loc_11 = Global.world.citySim.getRequiredPopulationCap(_loc_7);
                        _loc_12 = {capType:_loc_10, capNeeded:_loc_11};
                        ResourceExplanationDialog.show(ResourceExplanationDialog.TYPE_NOT_ENOUGH_HAPPINESS_RESIDENCE, false, null, _loc_12);
                    }
                    else
                    {
                        Global.world.addGameMode(_loc_5);
                    }
                    break;
                }
                case MarketEvent.ITEM_SKIN:
                {
                    if (!_loc_5)
                    {
                        _loc_5 = new GMSwapResidenceSkin(event.item);
                    }
                    Global.world.addGameMode(_loc_5);
                    break;
                }
                case MarketEvent.ITEM_SKIN_COMBINED:
                {
                    if (!_loc_5)
                    {
                        _loc_5 = new GMPlaceSkinnedResource(event.item, Residence);
                    }
                    _loc_10 = _loc_7.populationType;
                    _loc_8 = Global.world.citySim.getTotalPopulation(_loc_10) + _loc_7.populationBase;
                    _loc_9 = Global.world.citySim.getPopulationCap(_loc_10);
                    if (_loc_8 > _loc_9)
                    {
                        _loc_11 = Global.world.citySim.getRequiredPopulationCap(_loc_7);
                        _loc_12 = {capType:_loc_10, capNeeded:_loc_11};
                        ResourceExplanationDialog.show(ResourceExplanationDialog.TYPE_NOT_ENOUGH_HAPPINESS_RESIDENCE, false, null, _loc_12);
                    }
                    else
                    {
                        Global.world.addGameMode(_loc_5);
                    }
                    break;
                }
                case MarketEvent.BUSINESS:
                {
                    if (!Global.franchiseManager.placementMode && event.item == "biz_lotsite_4x4")
                    {
                        if (!_loc_5)
                        {
                            _loc_5 = new GMPlaceMapResource(event.item, null, _loc_2);
                        }
                        Global.world.addGameMode(_loc_5);
                    }
                    else if (!Global.franchiseManager.placementMode)
                    {
                        _loc_23 = Math.floor(Global.world.citySim.getPopulationCap() * Global.gameSettings().getNumber("businessLimitByPopulationMax"));
                        if ((Global.world.citySim.getTotalBusinesses() + 1) > _loc_23)
                        {
                            _loc_12 = {capType:Population.MIXED, capNeeded:Global.world.citySim.getRequiredPopulationCap(_loc_7)};
                            ResourceExplanationDialog.show(ResourceExplanationDialog.TYPE_NOT_ENOUGH_HAPPINESS_BUSINESS, false, null, _loc_12);
                        }
                        else
                        {
                            if (!_loc_5)
                            {
                                _loc_5 = new GMPlaceMapResource(event.item, Business, _loc_2);
                            }
                            Global.world.addGameMode(_loc_5);
                        }
                    }
                    else if (Global.franchiseManager.placementMode && event.item == "biz_lotsite_4x4")
                    {
                        UI.displayMessage(ZLoc.t("Dialogs", "BusinessZoneRestrict"));
                    }
                    else if (Global.world.getTopGameMode().getLotSite() != null)
                    {
                        if (!_loc_5)
                        {
                            _loc_5 = new GMPlaceBizOnLotSite(Global.world.getTopGameMode().getLotSite(), event.item, Business, _loc_2);
                        }
                        Global.world.addGameMode(_loc_5);
                    }
                    else
                    {
                        _loc_24 = Global.player.getFriendFirstName(Global.getVisiting());
                        _loc_25 = Global.franchiseManager.getFranchiseName(event.item, Global.player.uid);
                        UI.displayMessage(ZLoc.t("Dialogs", "RequestFranchiseConfirm", {friendName:_loc_24, franchiseName:_loc_25}), GenericPopup.TYPE_YESNO, confirmFranchiseHandler, "confirmFranchiseHandler", true);
                    }
                    break;
                }
                case MarketEvent.EXPAND_FARM:
                {
                    if (!_loc_5)
                    {
                        _loc_5 = new GMExpand(event.item);
                    }
                    if (!Global.isVisiting())
                    {
                        Global.world.addGameMode(_loc_5);
                    }
                    break;
                }
                case MarketEvent.CONTRACT:
                {
                    if (!_loc_5)
                    {
                        _loc_5 = new GMPlant(event.item);
                    }
                    if (!Global.isVisiting() && _loc_7.multiplant)
                    {
                        Global.world.addGameMode(_loc_5);
                    }
                    break;
                }
                case MarketEvent.EXTRA:
                {
                    if (!Global.isVisiting())
                    {
                        GameTransactionManager.addTransaction(new TBuyEnergy(event.item, _loc_2));
                    }
                    break;
                }
                case MarketEvent.THEME_COLLECTION:
                {
                    GameTransactionManager.addTransaction(new TSetCurrentThemes(event.item, !Global.world.isThemeCollectionEnabled(_loc_7)), true, true);
                    break;
                }
                case MarketEvent.GOODS:
                {
                    if (!Global.isVisiting())
                    {
                        GameTransactionManager.addTransaction(new TBuyGoods(event.item, _loc_2));
                    }
                    break;
                }
                case MarketEvent.GENERIC:
                {
                    if (!_loc_5)
                    {
                        _loc_5 = new GMPlaceMapResource(event.item, null, _loc_2);
                    }
                    Global.world.addGameMode(_loc_5);
                    break;
                }
                case MarketEvent.NEIGHBORHOOD:
                {
                    if (!_loc_5)
                    {
                        _loc_5 = new GMPlaceMapResource(event.item, null, _loc_2);
                    }
                    Global.world.addGameMode(_loc_5);
                    break;
                }
                case MarketEvent.TRAIN:
                {
                    Global.world.citySim.trainManager.proposeSchedulePurchase(event.item);
                    break;
                }
                case MarketEvent.FBC_PURCHASE:
                {
                    _loc_16 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_STARTER_PACK2);
                    _loc_17 = getMarketTab(event.eventSource);
                    StatsManager.sample(100, StatsCounterType.GAME_ACTIONS, StatsKingdomType.ACTION_MENU, "starter_pack_click" + _loc_16, _loc_17);
                    new StartUpDialogHelper().purchaseStarterPack(_loc_16);
                    break;
                }
                case MarketEvent.HOTEL:
                {
                    if (!_loc_5)
                    {
                        _loc_5 = new GMPlaceMapResource(event.item, Hotel, _loc_2);
                    }
                    Global.world.addGameMode(_loc_5);
                    break;
                }
                case MarketEvent.BRIDGE:
                {
                    if (!_loc_5)
                    {
                        _loc_5 = new GMPlaceBridge(event.item, Bridge, _loc_2);
                    }
                    Global.world.addGameMode(_loc_5);
                    break;
                }
                case MarketEvent.GENERIC_BUNDLE:
                {
                    GameTransactionManager.addTransaction(new TBuyGenericBundle(event.item));
                    break;
                }
                case MarketEvent.THEMED_BUNDLE:
                {
                    GameTransactionManager.addTransaction(new TBuyGenericBundle(event.item));
                    break;
                }
                case MarketEvent.PERMIT_BUNDLE:
                {
                    GameTransactionManager.addTransaction(new TBuyPermitPack(event.item));
                    StatsManager.sample(100, "permit_bundle", "click_to_purchase", event.item);
                    break;
                }
                case MarketEvent.MYSTERY_CRATE:
                {
                    _loc_18 = Global.gameSettings().getItemByName(event.item);
                    _loc_19 = ZLoc.t("Items", _loc_18.name + "_friendlyName");
                    _loc_20 = ZLoc.t("Dialogs", "Mystery_Crate_anticipation", {item_crate:_loc_19});
                    _loc_21 = new MysteryCrateDialog(_loc_20, "mysteryCrateDialog", GenericDialogView.TYPE_CUSTOM_OK, null, "Mystery_Crate", _loc_18.iconRelative, true, GenericDialogView.ICON_POS_LEFT, "", null, ZLoc.t("Dialogs", "Mystery_Crate_ok"));
                    UI.displayPopup(_loc_21, true);
                    GameTransactionManager.addTransaction(new TBuyMysteryCrate(_loc_18, 1, null));
                    break;
                }
                case MarketEvent.MECHANIC:
                {
                    _loc_22 =(GMDefault) _loc_6;
                    if (_loc_22)
                    {
                        _loc_26 = _loc_22.getSelectedObject();
                        if (_loc_26 && _loc_26 instanceof IMechanicUser)
                        {
                            MechanicManager.getInstance().handleAction((IMechanicUser)_loc_26, "catalogConfirmation", [event.item, onCompleteMechanicPurchase]);
                        }
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

        private static void  onCompleteMechanicPurchase (IMechanicUser param1 ,String param2 )
        {
            MechanicManager.getInstance().handleAction(param1, "catalogPurchase", [param2]);
            return;
        }//end

        private static void  confirmFranchiseHandler (GenericPopupEvent event )
        {
            double _loc_3 =0;
            LotSite _loc_4 =null ;
            Business _loc_5 =null ;
            _loc_2 = event.button ==GenericPopup.YES ;
            if (_loc_2 == true)
            {
                _loc_3 = -1;
                _loc_4 = Global.world.citySim.lotManager.getDefaultLotSite();
                if (_loc_4)
                {
                    _loc_3 = _loc_4.getId();
                }
                Global.world.citySim.lotManager.placeOrder(Global.getVisiting(), _loc_3, m_lastMarketEventItemName);
                _loc_5 = Global.franchiseManager.getBusinessFromType(m_lastMarketEventItemName);
                FranchiseViralManager.triggerFranchiseViralFeeds(FranchiseViralManager.VIRAL_APPROVEBUILDINGREQUEST, m_lastMarketEventItemName);
                Global.player.conditionallyUpdateHUD();
            }
            return;
        }//end

        private static String  getMarketTab (String param1 )
        {
            switch(param1)
            {
                case "extras":
                {
                    return "energy";
                }
                case "decorRoads":
                {
                    return "decorations";
                }
                case "new":
                {
                    return "new";
                }
                default:
                {
                    return "";
                    break;
                }
            }
        }//end

        public static void  displayPermitBuyPopup (int param1 ,int param2 ,Function param3 ,Function param4 )
        {
            GenericDialog _loc_5 =null ;
            String _loc_6 ="PermitDialog_message_more";
            _loc_5 = new PermitBuyShareDialog(param1, param2, param3, param4, _loc_6);
            UI.displayPopup(_loc_5, false, "", true);
            return;
        }//end

        public static void  displayNeighborBuyPopup (int param1 ,int param2 ,Function param3 ,Function param4 )
        {
            GenericDialog _loc_5 =null ;
            _loc_5 = new PermitBuyShareDialog(param1, param2, param3, param4, "NeighborBuyDialog_message", "NeighborBuyDialog", null, GenericDialogView.TYPE_INVITEFRIENDS_BUYNEIGHBORS);
            UI.displayPopup(_loc_5, false, "", true);
            return;
        }//end

        public static void  displayFranchiseUnlockBuyPopup (int param1 ,int param2 ,Function param3 ,Function param4 )
        {
            GenericDialog _loc_5 =null ;
            _loc_5 = new PermitBuyShareDialog(param1, param2, param3, param4, "FranchiseUnlockBuyDialog_message", "FranchiseUnlockBuyDialog", "assets/businesses/bus_franchise/franchiselot.png", GenericDialogView.TYPE_INVITEFRIENDS_BUYNEIGHBORS);
            UI.displayPopup(_loc_5, false, "", true);
            return;
        }//end

        public static void  displayImpulseBuyPopup (String param1 ,String param2 ,Item param3 =null )
        {
            DisplayObject _loc_4 =null ;
            if (param2 == null)
            {
                param2 = ImpulseBuy.POPUP_NAME;
            }
            if (param1 == ImpulseBuy.TYPE_MARKET_CASH)
            {
                new GetCashDialogFactory(param1, param2, param3);
            }
            else
            {
                _loc_4 = Global.stage.getChildByName(param2);
                if (_loc_4 == null)
                {
                    displayPopup(new ImpulseBuy(param1), false, param2, true);
                }
            }
            return;
        }//end

        public static void  onFeaturePromo (int param1 )
        {
            StatsManager.count("featurePromo", "feature_" + param1);
            if (Global.isVisiting() == false)
            {
                switch(param1)
                {
                    case 0:
                    {
                        UI.displayMarketDialog("featurePromo", "decoration");
                        break;
                    }
                    case 1:
                    {
                        UI.displayMarketDialog("featurePromo", "change_farm");
                        break;
                    }
                    case 2:
                    {
                        GlobalEngine.socialNetwork.redirect("gifts.php?ref=promo");
                        break;
                    }
                    default:
                    {
                        break;
                    }
                }
            }
            return;
        }//end

        public static void  pushBlankCursor ()
        {
            if (m_cursors.length > 0)
            {
                if (m_cursors.get((m_cursors.length - 1)).displayObj != null)
                {
                    setCursor(null);
                }
            }
            return;
        }//end

        public static void  popBlankCursor ()
        {
            if (m_cursors.length > 0)
            {
                if (m_cursors.get((m_cursors.length - 1)).displayObj == null)
                {
                    removeCursor(m_cursors.get((m_cursors.length - 1)).id);
                }
            }
            return;
        }//end

        public static boolean  isModalDialogOpen ()
        {
            return m_isModalDialogOpen;
        }//end

        public static void  displayInventoryMaxExceededDialog ()
        {
            _loc_1 = ZLoc.t("Dialogs","InventoryCapError_message");
            String _loc_2 ="OkButton";
            String _loc_3 ="InventoryCapError";
            displayMessage(_loc_1, GenericPopup.TYPE_OK, null, _loc_3, true, null, _loc_3);
            return;
        }//end

        public static QuestManagerView  questManagerView ()
        {
            int _loc_1 =0;
            if (!m_questManagerView)
            {
                _loc_1 = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_QUEST_MANAGER);
                if (_loc_1 == ExperimentDefinitions.USE_QUEST_MANAGER)
                {
                    m_questManagerView = new QuestManagerView();
                }
            }
            return m_questManagerView;
        }//end

        public static QuestManagerView  displayQuestManager ()
        {
            if (!m_questManagerView)
            {
                m_questManagerView = new QuestManagerView();
            }
            if (!m_questManagerView.isShown)
            {
                m_questManagerView.addEventListener(CloseEvent.CLOSE, onQuestManagerClose, false, 0, true);
                Global.ui.addChild(m_questManagerView);
                m_questManagerView.show();
            }
            else
            {
                m_questManagerView.close();
            }
            return m_questManagerView;
        }//end

        private static void  onQuestManagerClose (Event event )
        {
            if (m_questManagerView)
            {
                m_questManagerView.removeEventListener(CloseEvent.CLOSE, onQuestManagerClose);
            }
            return;
        }//end

    }


