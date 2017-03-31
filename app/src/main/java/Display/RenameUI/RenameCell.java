package Display.RenameUI;

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
import Display.MarketUI.*;
import Display.aswingui.*;
import Events.*;
import Modules.franchise.*;
import Modules.stats.experiments.*;
//import flash.display.*;
//import flash.events.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.ext.*;

    public class RenameCell extends JPanel implements GridListCell
    {
        protected int m_state ;
        protected Object m_data ;
        protected Dictionary m_assetDict ;
        protected DisplayObject m_bgAsset ;
        protected String m_bodyTextHeading ;
        protected String m_bodyTextSubHeading ;
        protected String m_bodyTextFooter ;
        protected JPanel m_headerPanel ;
        protected JPanel m_bodyPanel ;
        protected JPanel m_footerPanel ;
        protected boolean m_signatureRequired =true ;
        public static  int STATE_PROPOSE =0;
        public static  int STATE_REQUEST =1;
        public static  int STATE_FINISH =2;
        public static  String EVENT_UPDATE ="RenameCell_Update";
        private static  int BUTTON_WIDTH =200;

        public  RenameCell (Dictionary param1 ,LayoutManager param2 )
        {
            this.m_assetDict = param1;
            super(new BorderLayout());
            this.m_signatureRequired = Global.experimentManager.getVariant(ExperimentDefinitions.EXPERIMENT_UPGRADE_CLERKOFFICE) != ExperimentDefinitions.CLERK_UPGRADE_ENABLED;
            return;
        }//end

        public String  item ()
        {
            return this.m_data.get("item");
        }//end

        public String  itemName ()
        {
            return this.m_data.get("itemName");
        }//end

        public String  itemPicUrl ()
        {
            return this.m_data.get("itemPicUrl");
        }//end

        public String  pendingName ()
        {
            return this.m_data.get("pendingName");
        }//end

        public String  requiredCount ()
        {
            return this.m_data.get("requiredCount");
        }//end

        public String  requiredMax ()
        {
            return this.m_data.get("requiredMax");
        }//end

        public String  requiredCost ()
        {
            return this.m_data.get("requiredCost");
        }//end

        public void  setGridListCellStatus (GridList param1 ,boolean param2 ,int param3 )
        {
            this.m_bgAsset =(DisplayObject) new this.m_assetDict.get("cell_bg");
            this.initBackground();
            ASwingHelper.prepare(param1, this.m_headerPanel, this.m_footerPanel);
            this.setPreferredWidth(param1.getTileWidth());
            this.setPreferredHeight(param1.getTileHeight());
            dispatchEvent(new Event(EVENT_UPDATE));
            ASwingHelper.prepare(this);
            return;
        }//end

        public void  setCellValue (Object param1)
        {
            this.m_data = param1;
            this.m_state = param1.get("state");
            switch(this.m_state)
            {
                case STATE_PROPOSE:
                {
                    this.m_bodyTextHeading = this.itemName;
                    this.m_bodyTextSubHeading = ZLoc.t("Dialogs", "RenameUI_cellPropose_subHeading");
                    if (!this.m_signatureRequired)
                    {
                        this.m_bodyTextSubHeading = "";
                    }
                    break;
                }
                case STATE_REQUEST:
                {
                    this.m_bodyTextHeading = this.pendingName;
                    this.m_bodyTextSubHeading = ZLoc.t("Dialogs", "RenameUI_cellRequest_subHeading", {oldName:this.itemName.toUpperCase()});
                    break;
                }
                case STATE_FINISH:
                {
                    this.m_bodyTextHeading = this.pendingName;
                    this.m_bodyTextSubHeading = ZLoc.t("Dialogs", "RenameUI_cellFinish_subHeading");
                    break;
                }
                default:
                {
                    break;
                }
            }
            this.buildCell();
            return;
        }//end

        public Object getCellValue ()
        {
            return this.m_data;
        }//end

        public Component  getCellComponent ()
        {
            return this;
        }//end

        protected void  setCellText ()
        {
            return;
        }//end

        protected void  buildCell ()
        {
            this.m_headerPanel = this.createHeaderPanel();
            this.m_bodyPanel = this.createBodyPanel();
            this.m_footerPanel = this.createFooterPanel();
            _loc_1 = Math.max(this.m_headerPanel.getHeight ()-20,this.m_bodyPanel.getHeight (),this.m_footerPanel.getHeight ());
            this.m_headerPanel.setPreferredHeight(_loc_1);
            this.m_bodyPanel.setPreferredHeight(_loc_1);
            this.m_footerPanel.setPreferredHeight(_loc_1);
            this.initBackground();
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            _loc_2.appendAll(this.m_headerPanel, ASwingHelper.horizontalStrut(10), this.m_bodyPanel);
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT );
            _loc_3.appendAll(this.m_footerPanel, ASwingHelper.horizontalStrut(10));
            this.append(_loc_2, BorderLayout.WEST);
            this.append(_loc_3, BorderLayout.EAST);
            return;
        }//end

        protected void  initBackground ()
        {
            if (this.m_bgAsset)
            {
                this.setBackgroundDecorator(new AssetBackground(this.m_bgAsset));
            }
            return;
        }//end

        protected JPanel  createHeaderPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER );
            if (this.item != FranchiseManager.CITY_ITEM_NAME)
            {
                _loc_1.append(this.createMarketCellPanel());
            }
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  createMarketCellPanel ()
        {
            MarketCell _loc_1 =new MarketCell ();
            _loc_2 =Global.gameSettings().getItemByName(this.item );
            _loc_1.setAssetDict(this.m_assetDict);
            _loc_1.setCellValue(_loc_2);
            _loc_1.setBuyable(false);
            return _loc_1;
        }//end

        protected JPanel  createBodyPanel ()
        {
            JLabel _loc_2 =null ;
            JLabel _loc_3 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            if (this.m_bodyTextHeading != "")
            {
                _loc_2 = ASwingHelper.makeLabel(TextFieldUtil.formatSmallCapsString(this.m_bodyTextHeading), EmbeddedArt.titleFont, 22, EmbeddedArt.darkBlueTextColor, JLabel.LEFT);
                _loc_1.append(_loc_2);
            }
            if (this.m_bodyTextSubHeading != "")
            {
                _loc_3 = ASwingHelper.makeLabel(this.m_bodyTextSubHeading, EmbeddedArt.defaultFontNameBold, 18, EmbeddedArt.blueTextColor, JLabel.LEFT);
                _loc_1.append(_loc_3);
            }
            _loc_1.append(this.createSignatureStatusPanel());
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected boolean  isProposeEnabled ()
        {
            boolean _loc_1 =true ;
            if (this.item == FranchiseManager.CITY_ITEM_NAME)
            {
                return Global.player.hasSetCityName();
            }
            return _loc_1;
        }//end

        protected JPanel  createFooterPanel ()
        {
            String _loc_2 =null ;
            String _loc_3 =null ;
            String _loc_4 =null ;
            boolean _loc_5 =false ;
            boolean _loc_6 =false ;
            Function _loc_7 =null ;
            Function _loc_8 =null ;
            AssetIcon _loc_10 =null ;
            CustomButton _loc_11 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM );
            switch(this.m_state)
            {
                case STATE_PROPOSE:
                {
                    _loc_2 = ZLoc.t("Dialogs", "RenameUI_cellPropose_btnRename");
                    _loc_3 = "OrangeButtonUI";
                    _loc_7 = this.onProposeClick;
                    _loc_5 = this.isProposeEnabled();
                    break;
                }
                case STATE_REQUEST:
                {
                    _loc_2 = ZLoc.t("Dialogs", "RenameUI_cellRequest_btnPostFeed");
                    _loc_3 = "OrangeButtonUI";
                    _loc_4 = ZLoc.t("Dialogs", "RenameUI_cellRequest_btnPurchase", {cost:this.requiredCost});
                    _loc_7 = this.onRequestClick;
                    _loc_8 = this.onPurchaseClick;
                    _loc_5 = true;
                    _loc_6 = true;
                    break;
                }
                case STATE_FINISH:
                {
                    _loc_2 = ZLoc.t("Dialogs", "RenameUI_cellFinish_btnFinish");
                    _loc_3 = "GreenButtonUI";
                    _loc_7 = this.onFinishClick;
                    _loc_5 = true;
                    break;
                }
                default:
                {
                    break;
                }
            }
            CustomButton _loc_9 =new CustomButton(TextFieldUtil.formatSmallCapsString(_loc_2 ),null ,_loc_3 );
            if (this.m_state == STATE_REQUEST)
            {
                _loc_9.setEnabled(Global.world.viralMgr.canPost("request_signature"));
            }
            else
            {
                _loc_9.setEnabled(_loc_5);
            }
            _loc_9.addEventListener(MouseEvent.CLICK, _loc_7);
            _loc_9.setFont(new ASFont(EmbeddedArt.titleFont, 14, true, false, false, EmbeddedArt.getAdvancedFontProps(EmbeddedArt.titleFont)));
            _loc_1.append(_loc_9);
            if (_loc_4)
            {
                _loc_1.append(ASwingHelper.verticalStrut(5));
                _loc_10 = new AssetIcon(new this.m_assetDict.get("pic_cashBtn"));
                _loc_11 = new CustomButton(TextFieldUtil.formatSmallCapsString(_loc_4), _loc_10, "BigCashButtonUI");
                _loc_11.setEnabled(_loc_6);
                _loc_11.addEventListener(MouseEvent.CLICK, _loc_8);
                _loc_11.setFont(new ASFont(EmbeddedArt.titleFont, 14, true, false, false, EmbeddedArt.getAdvancedFontProps(EmbeddedArt.titleFont)));
                _loc_1.append(_loc_11);
            }
            _loc_1.append(ASwingHelper.verticalStrut(5));
            _loc_1.setMinimumWidth(BUTTON_WIDTH);
            _loc_1.setPreferredWidth(BUTTON_WIDTH);
            _loc_1.setMaximumWidth(BUTTON_WIDTH);
            ASwingHelper.prepare(_loc_1);
            return _loc_1;
        }//end

        protected JPanel  createSignatureStatusPanel ()
        {
            JLabel _loc_2 =null ;
            JLabel _loc_3 =null ;
            JLabel _loc_4 =null ;
            AssetPane _loc_5 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT );
            switch(this.m_state)
            {
                case STATE_PROPOSE:
                {
                    if (this.m_signatureRequired)
                    {
                        _loc_2 = ASwingHelper.makeLabel(this.requiredMax, EmbeddedArt.titleFont, 20, EmbeddedArt.orangeTextColor, JLabel.BOTTOM);
                        _loc_3 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "RenameUI_cellPropose_footer").toUpperCase(), EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.darkBrownTextColor);
                        _loc_1.appendAll(_loc_2, _loc_3);
                    }
                    else
                    {
                        _loc_2 = ASwingHelper.makeLabel(" ", EmbeddedArt.titleFont, 20, EmbeddedArt.orangeTextColor, JLabel.BOTTOM);
                        _loc_1.appendAll(_loc_2, _loc_3);
                    }
                    break;
                }
                case STATE_REQUEST:
                {
                    _loc_2 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "RenameUI_cellRequest_footer1", {count:this.requiredCount, max:this.requiredMax}), EmbeddedArt.titleFont, 20, EmbeddedArt.orangeTextColor);
                    _loc_3 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "RenameUI_cellRequest_footer2").toUpperCase(), EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.darkBrownTextColor);
                    _loc_1.appendAll(_loc_2, _loc_3);
                    break;
                }
                case STATE_FINISH:
                {
                    _loc_5 = new AssetPane(new this.m_assetDict.get("pic_checkGreen"));
                    _loc_2 = ASwingHelper.makeLabel(ZLoc.t("Dialogs", "RenameUI_cellFinish_footer").toUpperCase(), EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.darkBrownTextColor);
                    _loc_1.appendAll(_loc_5, _loc_2);
                }
                default:
                {
                    break;
                }
            }
            return _loc_1;
        }//end

        protected void  onProposeClick (Event event )
        {
            dispatchEvent(new RenameEvent(RenameEvent.PROPOSE_CLICK, this.item, true, true));
            return;
        }//end

        protected void  onRequestClick (Event event )
        {
            dispatchEvent(new RenameEvent(RenameEvent.REQUEST_CLICK, this.item, true, true));
            return;
        }//end

        protected void  onPurchaseClick (Event event )
        {
            dispatchEvent(new RenameEvent(RenameEvent.PURCHASE_CLICK, this.item, true, true));
            return;
        }//end

        protected void  onFinishClick (Event event )
        {
            dispatchEvent(new RenameEvent(RenameEvent.FINISH_CLICK, this.item, true, true));
            return;
        }//end

    }



