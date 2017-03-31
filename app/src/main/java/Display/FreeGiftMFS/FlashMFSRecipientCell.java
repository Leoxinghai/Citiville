package Display.FreeGiftMFS;

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
import Display.*;
import Display.aswingui.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.filters.*;
import org.aswing.*;
import org.aswing.geom.*;

    public class FlashMFSRecipientCell extends DataItemCell
    {
        protected FlashMFSRecipient m_recipient ;
        protected boolean m_dummyRecipient =false ;
        protected boolean m_noPortrait =false ;
        protected Sprite m_portraitSprite ;
        protected Sprite m_checkboxSprite ;
        protected boolean m_checked =true ;

        public  FlashMFSRecipientCell ()
        {
            super(new SoftBoxLayout(SoftBoxLayout.X_AXIS, 0, SoftBoxLayout.CENTER));
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            this.m_recipient =(FlashMFSRecipient) param1;
            if (this.m_recipient.zid == 0 || this.m_recipient.name == null)
            {
                this.m_dummyRecipient = true;
            }
            if (this.m_recipient.portraitURL == null)
            {
                this.m_noPortrait = true;
            }
            this.m_checked = this.m_recipient.selected;
            this.buildCell();
            if (this.m_noPortrait == false)
            {
                m_loader = LoadingManager.load(this.m_recipient.portraitURL, this.onPortraitLoad, LoadingManager.PRIORITY_HIGH);
            }
            return;
        }//end

        protected void  buildCell ()
        {
            this.buildBackground();
            if (this.m_dummyRecipient == false)
            {
                this.buildContent();
            }
            else
            {
                this.applyGhosting();
            }
            return;
        }//end

        protected void  buildBackground ()
        {
            DisplayObject _loc_1 =(DisplayObject)new FreeGiftMFSDialog.assetDict.get( "friendPanel");
            MarginBackground _loc_2 =new MarginBackground(_loc_1 ,new Insets(0,0,0,1));
            this.setBackgroundDecorator(_loc_2);
            ASwingHelper.setForcedSize(this, new IntDimension((_loc_1.width + 1), _loc_1.height));
            return;
        }//end

        protected void  buildContent ()
        {
            this.appendAll(ASwingHelper.horizontalStrut(6), this.makePortraitPanel());
            this.appendAll(ASwingHelper.horizontalStrut(2), this.makeNamePanel());
            this.appendAll(ASwingHelper.horizontalStrut(1), this.makeCheckPanel());
            this.addEventListener(MouseEvent.CLICK, this.onMouseClick, false, 0, true);
            this.addEventListener(MouseEvent.ROLL_OVER, this.onRollOver, false, 0, true);
            this.addEventListener(MouseEvent.ROLL_OUT, this.onRollOut, false, 0, true);
            return;
        }//end

        protected JPanel  makePortraitPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_portraitSprite = new Sprite();
            DisplayObject _loc_3 =(DisplayObject)new FreeGiftMFSDialog.assetDict.get( "photoBack");
            MarginBackground _loc_4 =new MarginBackground(_loc_3 );
            ASwingHelper.setForcedSize(_loc_2, new IntDimension(_loc_3.width, _loc_3.height));
            _loc_2.setBackgroundDecorator(_loc_4);
            _loc_2.addChild(this.m_portraitSprite);
            _loc_2.addChild(this.m_portraitSprite);
            _loc_1.appendAll(ASwingHelper.verticalStrut(4), _loc_2);
            return _loc_1;
        }//end

        protected JPanel  makeNamePanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            ASwingHelper.setForcedWidth(_loc_1, 134);
            JLabel _loc_3 =new JLabel(this.m_recipient.name );
            _loc_3.setFont(ASwingHelper.getBoldFont(13));
            _loc_3.setForeground(new ASColor(EmbeddedArt.brownTextColor));
            _loc_2.append(_loc_3);
            _loc_1.appendAll(ASwingHelper.verticalStrut(2), _loc_2);
            return _loc_1;
        }//end

        protected JPanel  makeCheckPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            this.m_checkboxSprite = new Sprite();
            _loc_2 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            _loc_2.addChild(this.m_checkboxSprite);
            DisplayObject _loc_3 =(DisplayObject)new FreeGiftMFSDialog.assetDict.get( "checkbox");
            this.m_checkboxSprite.addChildAt(_loc_3, 0);
            ASwingHelper.setForcedSize(_loc_2, new IntDimension(_loc_3.width + 10, _loc_3.height + 12));
            _loc_1.appendAll(ASwingHelper.verticalStrut(4), _loc_2);
            this.applyEffects();
            return _loc_1;
        }//end

        private void  onRollOver (MouseEvent event )
        {
            Array _loc_3 =null ;
            Array _loc_4 =null ;
            _loc_2 = this.m_checkboxSprite.getChildAt(0);
            if (_loc_2.filters.length == 0)
            {
                _loc_3 = _loc_2.filters;
                _loc_4 = new Array();
                _loc_4 = _loc_4.concat(.get(0.54, 0, 0, 0, 0));
                _loc_4 = _loc_4.concat(.get(0, 0.76, 0, 0, 0));
                _loc_4 = _loc_4.concat(.get(0, 0, 0.25, 0, 0));
                _loc_4 = _loc_4.concat(.get(0, 0, 0, 1, 0));
                _loc_3.push(new ColorMatrixFilter(_loc_4));
                _loc_2.filters = _loc_3;
            }
            return;
        }//end

        private void  onRollOut (MouseEvent event )
        {
            _loc_2 = this.m_checkboxSprite.getChildAt(0);
            _loc_2.filters = new Array();
            return;
        }//end

        private void  onMouseClick (MouseEvent event )
        {
            this.toggleSelection();
            return;
        }//end

        private void  applyEffects ()
        {
            Array tempfilter ;
            Array matrix ;
            DisplayObject checkmarkDO ;
            if (this.m_checked == false)
            {
                try
                {
                    this.m_checkboxSprite.removeChildAt(1);
                }
                catch (e:Error)
                {
                }
                if (this.filters.length == 0)
                {
                    tempfilter = this.filters;
                    matrix = new Array();
                    matrix = matrix.concat(.get(0.83, 0, 0, 0, 0));
                    matrix = matrix.concat(.get(0, 0.83, 0, 0, 0));
                    matrix = matrix.concat(.get(0, 0, 0.83, 0, 0));
                    matrix = matrix.concat(.get(0, 0, 0, 0.8, 0));
                    tempfilter.push(new ColorMatrixFilter(matrix));
                    this.filters = tempfilter;
                }
            }
            else
            {
                checkmarkDO =(DisplayObject) new FreeGiftMFSDialog.assetDict.get("checkmark");
                checkmarkDO.x = checkmarkDO.x + 3;
                (checkmarkDO.y - 1);
                this.m_checkboxSprite.addChildAt(checkmarkDO, 1);
                this.filters = new Array();
            }
            return;
        }//end

        private void  applyGhosting ()
        {
            this.alpha = 0.2;
            return;
        }//end

        private void  toggleSelection ()
        {
            if (this.m_checked == false)
            {
                this.m_checked = true;
                this.m_recipient.selected = true;
                this.applyEffects();
                (Global.flashMFSManager.getManager(FlashMFSManager.TYPE_RECENTLY_PLAYED_MFS) as RecentlyPlayedMFSManager).notifySelected();
            }
            else
            {
                this.m_checked = false;
                this.m_recipient.selected = false;
                this.applyEffects();
                (Global.flashMFSManager.getManager(FlashMFSManager.TYPE_RECENTLY_PLAYED_MFS) as RecentlyPlayedMFSManager).notifyUnselected();
            }
            return;
        }//end

        protected void  onPortraitLoad (Event event )
        {
            DisplayObject _loc_2 =null ;
            if (m_loader && m_loader.content)
            {
                _loc_2 = m_loader.content;
                if (_loc_2 instanceof Bitmap)
                {
                    ((Bitmap)_loc_2).smoothing = true;
                }
                _loc_2.width = 23;
                _loc_2.height = 23;
                this.m_portraitSprite.addChild(_loc_2);
                this.m_portraitSprite.x = 1;
                this.m_portraitSprite.y = 1;
            }
            return;
        }//end

    }




