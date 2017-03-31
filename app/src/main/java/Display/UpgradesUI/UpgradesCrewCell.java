package Display.UpgradesUI;

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
import Events.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class UpgradesCrewCell extends CrewCell
    {
        protected GridList m_gridList ;
        protected int m_index ;
        protected Object m_crewObject ;
        protected int m_color ;
        protected JPanel m_portraitPanel ;
        protected JPanel m_namePanel ;
        protected JPanel m_statusPanel ;
        protected boolean m_empty =false ;
        protected boolean m_filled =false ;

        public  UpgradesCrewCell (CrewCellFactory param1 ,int param2 )
        {
            this.m_color = param2;
            super(param1, layout);
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            if (param1 == "empty")
            {
                this.m_empty = true;
            }
            else
            {
                this.m_crewObject = param1;
                if (this.m_crewObject.count > 0)
                {
                    this.m_filled = true;
                }
            }
            this.buildCell();
            ASwingHelper.prepare(this);
            dispatchEvent(new Event(UIEvent.PREPARE, true));
            return;
        }//end

         public void  buildCell ()
        {
            Sprite _loc_2 =null ;
            JPanel _loc_3 =null ;
            JPanel _loc_4 =null ;
            this.removeAll();
            JPanel _loc_1 =new JPanel(new BorderLayout ());
            if (this.m_color % 2 == 0)
            {
                _loc_2 = new Sprite();
                _loc_2.graphics.beginFill(EmbeddedArt.lightBlueTextColor);
                _loc_2.graphics.drawRect(0, 0, UpgradesCrewScrollingList.ROW_WIDTH, UpgradesCrewScrollingList.ROW_HEIGHT);
                _loc_2.graphics.endFill();
                ASwingHelper.setBackground(_loc_1, _loc_2);
            }
            _loc_1.setPreferredSize(new IntDimension(UpgradesCrewScrollingList.ROW_WIDTH, UpgradesCrewScrollingList.ROW_HEIGHT));
            _loc_1.setMinimumSize(new IntDimension(UpgradesCrewScrollingList.ROW_WIDTH, UpgradesCrewScrollingList.ROW_HEIGHT));
            _loc_1.setMaximumSize(new IntDimension(UpgradesCrewScrollingList.ROW_WIDTH, UpgradesCrewScrollingList.ROW_HEIGHT));
            if (this.m_empty == false)
            {
                _loc_3 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
                _loc_4 = ASwingHelper.makeFlowJPanel(FlowLayout.RIGHT);
                _loc_3.appendAll(this.makeImagePanel(), this.makeNamePanel());
                _loc_4.append(this.makeCheckPanel());
                _loc_1.append(_loc_3, BorderLayout.WEST);
                _loc_1.append(_loc_4, BorderLayout.EAST);
            }
            this.append(_loc_1);
            return;
        }//end

         public void  setGridListCellStatus (GridList param1 ,boolean param2 ,int param3 )
        {
            if (param1 !=null)
            {
                this.m_gridList = param1;
                this.m_index = param3;
                param1.setTileWidth(UpgradesCrewScrollingList.ROW_WIDTH);
                param1.setTileHeight(UpgradesCrewScrollingList.ROW_HEIGHT);
            }
            return;
        }//end

         protected JPanel  makeImagePanel ()
        {
            DisplayObject image ;
            AssetPane ap ;
            jp = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT);
            image =(DisplayObject) new UpgradesCommonAssets.assetDict.get("blankPortrait");
            if (this.m_filled == true)
            {
                LoadingManager .load (this .m_crewObject .url ,void  (Event event )
            {
                image = event.target.content;
                ap.setAsset(image);
                return;
            }//end
            );
            }
            ap = new AssetPane(image);
            jp.append(ap);
            return jp;
        }//end

        protected JPanel  makeNamePanel ()
        {
            String _loc_3 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP );
            _loc_2 = (String)TextFieldUtil.formatSmallCapsString(this.m_crewObject.name
            if (this.m_filled == true)
            {
                _loc_3 = this.m_crewObject.footerText;
            }
            else
            {
                _loc_3 = ZLoc.t("Dialogs", "HireFriendsToFill");
            }
            _loc_4 = ASwingHelper.makeLabel(_loc_2 ,EmbeddedArt.defaultFontNameBold ,14,EmbeddedArt.blueTextColor );
            _loc_5 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            _loc_5.append(_loc_4);
            _loc_1.append(_loc_5);
            _loc_6 = ASwingHelper.makeLabel(_loc_3 ,EmbeddedArt.defaultFontNameBold ,14,this.m_filled ? (EmbeddedArt.brownTextColor) : (EmbeddedArt.blueTextColor));
            _loc_7 = ASwingHelper.makeFlowJPanel(FlowLayout.LEFT );
            _loc_7.append(_loc_6);
            _loc_1.append(_loc_7);
            return _loc_1;
        }//end

        protected JPanel  makeCheckPanel ()
        {
            DisplayObject _loc_2 =null ;
            Sprite _loc_3 =null ;
            JPanel _loc_4 =null ;
            JPanel _loc_5 =null ;
            AssetIcon _loc_6 =null ;
            CustomButton _loc_7 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            if (this.m_filled)
            {
                _loc_1.setPreferredSize(new IntDimension(60, 53));
                _loc_1.setMinimumSize(new IntDimension(60, 53));
                _loc_1.setMaximumSize(new IntDimension(60, 53));
                _loc_2 =(DisplayObject) new UpgradesCommonAssets.assetDict.get("checkMark");
                _loc_3 = new Sprite();
                _loc_3.addChild(_loc_2);
                _loc_1.addChild(_loc_3);
                _loc_3.x = 50 / 2 - _loc_2.width / 2;
                _loc_3.y = 50 / 2 - _loc_2.height / 2;
            }
            else
            {
                _loc_1.setPreferredHeight(53);
                _loc_1.setMinimumHeight(53);
                _loc_1.setMaximumHeight(53);
                _loc_4 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
                _loc_5 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
                _loc_6 = new AssetIcon(new EmbeddedArt.icon_cash());
                _loc_7 = new CustomButton(TextFieldUtil.formatSmallCapsString(ZLoc.t("Dialogs", "FillForX", {cash:this.m_crewObject.cost})), _loc_6, "CashButtonUI");
                _loc_7.addEventListener(MouseEvent.CLICK, this.onCashBuy, false, 0, true);
                _loc_7.setPreferredHeight(30);
                _loc_7.setMinimumHeight(30);
                _loc_7.setMaximumHeight(30);
                _loc_5.appendAll(_loc_7, ASwingHelper.horizontalStrut(5));
                _loc_4.append(_loc_5);
                _loc_1.append(_loc_4);
            }
            ASwingHelper.prepare(this);
            return _loc_1;
        }//end

        protected void  onCashBuy (Event event )
        {
            boolean _loc_2 =false ;
            Player _loc_3 =null ;
            if (this.m_filled == false && this.m_empty == false)
            {
                _loc_2 = this.m_crewObject.buyCallback(this.m_crewObject.cost);
                if (_loc_2)
                {
                    _loc_3 = Global.player.findFriendById("-1");
                    this.m_crewObject.url = _loc_3.snUser.picture;
                    this.m_crewObject.footerText = _loc_3.firstName;
                    this.m_crewObject.count = 1;
                    this.setCellValue(this.m_crewObject);
                    this.dispatchEvent(new Event(MunicipalUpgradesDialogView.UPDATE_POSITIONS, true));
                }
                else
                {
                    UI.displayPopup(new GetCoinsDialog(ZLoc.t("Dialogs", "ImpulseMarketCash"), "HireFriendsButtonLabel", GenericDialogView.TYPE_GETCASH, null, true));
                }
            }
            return;
        }//end

        public void  rebuildCellAfterFillAll ()
        {
            Player _loc_1 =null ;
            if (this.m_filled == false && this.m_empty == false)
            {
                _loc_1 = Global.player.findFriendById("-1");
                this.m_crewObject.url = _loc_1.snUser.picture;
                this.m_crewObject.footerText = _loc_1.firstName;
                this.m_crewObject.count = 1;
                this.setCellValue(this.m_crewObject);
            }
            return;
        }//end

    }



