package Display.ValentineUI;

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
import Classes.sim.*;
import Display.*;
import Display.DialogUI.*;
import Display.aswingui.*;
import Engine.Managers.*;
import Modules.stats.types.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
import org.aswing.*;
import org.aswing.border.*;
import org.aswing.geom.*;

    public class AdmirersCell extends DataItemCell
    {
        private Admirer m_admirer ;
        private JPanel m_cellPanel ;
        private boolean m_noPortrait =false ;
        private boolean m_noAdmirer =false ;
        private  int CELL_WIDTH =88;
        private  int CELL_HEIGHT =90;
        private JPanel m_itemIconHolder ;
        private JPanel m_itemIconPane ;
        private JPanel m_itemBgHolder ;
        private Sprite m_heartBorderSprite ;
        private Sprite m_cardCounterSprite ;

        public  AdmirersCell ()
        {
            super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0, SoftBoxLayout.LEFT));
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            this.m_admirer =(Admirer) param1;
            if (this.m_admirer.name == null)
            {
                this.m_noAdmirer = true;
            }
            if (this.m_admirer.portrait == null)
            {
                this.m_noPortrait = true;
            }
            this.buildCell();
            if (this.m_noPortrait == false)
            {
                m_loader = LoadingManager.load(this.m_admirer.portrait, onIconLoad, LoadingManager.PRIORITY_HIGH);
            }
            return;
        }//end

        protected void  buildCell ()
        {
            this.m_cellPanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.buildBackground();
            if (this.m_noAdmirer)
            {
                this.buildAddAdmirerCell();
            }
            else
            {
                this.buildExistingAdmirerCell();
            }
            this.append(this.m_cellPanel);
            if (this.m_noAdmirer == false && ValentineManager.checkUIDForNewCards(this.m_admirer.uid))
            {
                this.addHeartBorder();
            }
            if (this.m_noAdmirer == false)
            {
                this.addCardCounter();
            }
            this.addEventListener(MouseEvent.CLICK, this.onMouseClick, false, 0, true);
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  addHeartBorder ()
        {
            this.m_heartBorderSprite = new Sprite();
            DisplayObject _loc_1 =(DisplayObject)new ValentineDialog.assetDict.get( "heartBorder");
            this.m_heartBorderSprite.addChild(_loc_1);
            (this.m_heartBorderSprite.x + 1);
            this.m_heartBorderSprite.y = this.m_heartBorderSprite.y + 5;
            this.addChild(this.m_heartBorderSprite);
            return;
        }//end

        private void  disableHeartBorder ()
        {
            this.removeChild(this.m_heartBorderSprite);
            return;
        }//end

        private void  addCardCounter ()
        {
            this.m_cardCounterSprite = new Sprite();
            DisplayObject _loc_1 =(DisplayObject)new ValentineDialog.assetDict.get( "counterHeart");
            this.m_cardCounterSprite.addChild(_loc_1);
            this.m_cardCounterSprite.x = this.m_cardCounterSprite.x + 58;
            this.m_cardCounterSprite.y = this.m_cardCounterSprite.y + 58;
            Sprite _loc_2 =new Sprite ();
            TextField _loc_3 =new TextField ();
            TextFormat _loc_4 =new TextFormat(EmbeddedArt.defaultFontNameBold ,12,EmbeddedArt.redTextColor ,true );
            _loc_3.text = this.m_admirer.numCards.toString();
            _loc_3.border = false;
            _loc_3.embedFonts = EmbeddedArt.defaultBoldFontEmbed;
            _loc_3.setTextFormat(_loc_4);
            _loc_2.addChild(_loc_3);
            _loc_5 = _loc_3.getLineMetrics(0);
            _loc_2.x = 11 - _loc_5.width / 2;
            _loc_2.y = 5;
            this.m_cardCounterSprite.addChild(_loc_2);
            this.addChild(this.m_cardCounterSprite);
            return;
        }//end

         protected void  initializeCell ()
        {
            JPanel _loc_1 =null ;
            AssetPane _loc_2 =null ;
            if (this.m_noPortrait == false)
            {
                _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
                _loc_2 = new AssetPane(m_itemIcon);
                this.m_itemIconHolder.setBorder(new EmptyBorder(null, new Insets(0, 0, 5, 0)));
                _loc_1.append(_loc_2);
                this.m_itemIconPane.append(_loc_1);
            }
            return;
        }//end

        private void  buildBackground ()
        {
            this.setPreferredSize(new IntDimension(this.CELL_WIDTH, this.CELL_HEIGHT));
            this.setMinimumSize(new IntDimension(this.CELL_WIDTH, this.CELL_HEIGHT));
            this.setMaximumSize(new IntDimension(this.CELL_WIDTH, this.CELL_HEIGHT));
            this.m_cellPanel.setPreferredSize(new IntDimension(this.CELL_WIDTH, this.CELL_HEIGHT));
            this.m_cellPanel.setMinimumSize(new IntDimension(this.CELL_WIDTH, this.CELL_HEIGHT));
            this.m_cellPanel.setMaximumSize(new IntDimension(this.CELL_WIDTH, this.CELL_HEIGHT));
            DisplayObject _loc_1 =(DisplayObject)new ValentineDialog.assetDict.get( "noportrait");
            MarginBackground _loc_2 =new MarginBackground(_loc_1 ,new Insets(12,9,11,11));
            this.m_cellPanel.setBackgroundDecorator(_loc_2);
            ASwingHelper.prepare(this.m_cellPanel);
            return;
        }//end

        private void  buildAddAdmirerCell ()
        {
            JLabel _loc_1 =new JLabel(ZLoc.t("Dialogs","ValUI_getadmirer"));
            _loc_1.setForeground(new ASColor(10056537));
            _loc_1.setFont(ASwingHelper.getBoldFont(12));
            this.m_cellPanel.append(ASwingHelper.verticalStrut(75));
            this.m_cellPanel.append(_loc_1);
            return;
        }//end

        private void  buildExistingAdmirerCell ()
        {
            JLabel _loc_1 =new JLabel(this.m_admirer.name );
            _loc_1.setForeground(new ASColor(10056537));
            _loc_1.setFont(ASwingHelper.getBoldFont(12));
            this.m_itemIconHolder = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_itemIconPane = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            this.m_itemIconHolder.append(this.m_itemIconPane);
            this.m_itemBgHolder = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.BOTTOM);
            this.m_itemBgHolder.setMinimumSize(new IntDimension(this.m_cellPanel.width, this.m_cellPanel.height - 15));
            this.m_itemBgHolder.setPreferredSize(new IntDimension(this.m_cellPanel.width, this.m_cellPanel.height - 15));
            this.m_itemBgHolder.setMaximumSize(new IntDimension(this.m_cellPanel.width, this.m_cellPanel.height - 15));
            this.m_itemBgHolder.append(this.m_itemIconHolder);
            this.m_cellPanel.append(this.m_itemBgHolder);
            this.m_cellPanel.append(_loc_1);
            return;
        }//end

        private void  onMouseClick (MouseEvent event )
        {
            if (this.m_noAdmirer == true)
            {
                StatsManager.count(StatsCounterType.CARDMAKER, StatsKingdomType.VDAY_2011, StatsPhylumType.ADMIRERS, "person", "blank");
                UI.displayValRewardsMessage(ZLoc.t("Dialogs", "ValUI_getadmirer_popup"), GenericDialogView.TYPE_OK);
            }
            else
            {
                StatsManager.count(StatsCounterType.CARDMAKER, StatsKingdomType.VDAY_2011, StatsPhylumType.ADMIRERS, "person", "friend");
                UI.displayValentineCardViewer(this.m_admirer);
                if (this.m_admirer.newCard == true)
                {
                    ValentineManager.markAllAsSeen(this.m_admirer.uid);
                    this.m_admirer.newCard = false;
                }
                if (this.m_heartBorderSprite && this.m_heartBorderSprite.parent)
                {
                    this.disableHeartBorder();
                }
            }
            return;
        }//end

    }



