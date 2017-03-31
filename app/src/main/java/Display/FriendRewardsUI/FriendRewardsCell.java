package Display.FriendRewardsUI;

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
import Display.aswingui.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
//import flash.text.*;
//import flash.utils.*;
import org.aswing.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class FriendRewardsCell extends JPanel implements GridListCell
    {
        protected  int HR_MARGIN =40;
        protected Dictionary m_assetDict ;
        protected JPanel m_headerPanel ;
        protected JPanel m_bodyPanel ;
        protected JPanel m_footerPanel ;
        protected Loader m_imageIconLoader ;
        protected JPanel m_imagePanel ;
        protected Object m_data ;
        protected String m_localeKey ;

        public  FriendRewardsCell (Dictionary param1 ,LayoutManager param2 ,String param3 =null )
        {
            this.m_assetDict = param1;
            this.m_localeKey = param3;
            super(new BorderLayout());
            return;
        }//end

        public void  setGridListCellStatus (GridList param1 ,boolean param2 ,int param3 )
        {
            return;
        }//end

        public void  setCellValue (Object param1)
        {
            this.m_data = param1;
            _loc_2 = Global.gameSettings().getImageByName(this.m_data.itemName,"icon");
            this.m_imageIconLoader = LoadingManager.load(_loc_2, this.onImageLoaded, LoadingManager.PRIORITY_HIGH);
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

        protected void  buildCell ()
        {
            DisplayObject m_picture ;
            AssetPane ap ;
            imageHolder = (DisplayObject)newthis.m_assetDict.get("card_available_selected")
            m_picture =(DisplayObject) new EmbeddedArt.hud_no_profile_pic();
            ap = new AssetPane(m_picture);
            friend = Global.player.findFriendById(this.m_data.uid);
            if (friend)
            {
                LoadingManager .load (friend .snUser .picture ,void  (Event event )
            {
                m_picture = event.target.content;
                ap.setAsset(m_picture);
                return;
            }//end
            );
            }
            textHolder = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            senderName = Global.player.getFriendFirstName(this.m_data.uid);
            giftText = TextFieldUtil.formatSmallCapsString(ZLoc.t("Items",this.m_data.itemName+"_friendlyName"));
            nameText = senderName+" "+ZLoc.t("Dialogs",this.m_localeKey+"_actionText_default");
            if (Global.zooManager.isAnimal(this.m_data.itemName))
            {
                nameText = senderName + " " + ZLoc.t("Dialogs", this.m_localeKey + "_actionText_animal");
            }
            txt = nameText;
            int textWidth ;
            fontName = EmbeddedArt.defaultFontNameBold;
            fontAlign = TextFormatAlign.LEFT;
            int fontSize ;
            fontColor = EmbeddedArt.blueTextColor;
            nameLabel = ASwingHelper.makeMultilineText(txt,textWidth,fontName,fontAlign,fontSize,fontColor);
            rTxt = giftText;
            int rTextWidth ;
            rFontName = EmbeddedArt.titleFont;
            rFontAlign = TextFormatAlign.LEFT;
            int rFontSize ;
            rFontColor = EmbeddedArt.darkBlueTextColor;
            rewardLabel = ASwingHelper.makeMultilineText(rTxt,rTextWidth,rFontName,rFontAlign,rFontSize,rFontColor);
            textHolder.appendAll(nameLabel, rewardLabel);
            ASwingHelper.setEasyBorder(textHolder, 0, 15);
            this.m_imagePanel = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            this.m_imagePanel.setPreferredSize(new IntDimension(imageHolder.width, imageHolder.height));
            if (Global.zooManager.isAnimal(this.m_data.itemName))
            {
                ASwingHelper.setSizedBackground(this.m_imagePanel, imageHolder);
            }
            leftContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            leftContainer.appendAll(ap, textHolder);
            rightContainer = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.RIGHT);
            rightContainer.appendAll(this.m_imagePanel, ASwingHelper.horizontalStrut(5));
            hrPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            hrObj = newthis.m_assetDict.get("dialog_div");
            hrObj.width = FriendRewardsDialogView.LIST_WIDTH - this.HR_MARGIN * 2;
            AssetPane horizontalRule =new AssetPane(hrObj );
            hrPanel.append(horizontalRule);
            ASwingHelper.setEasyBorder(hrPanel, 0, 0, 4);
            this.append(leftContainer, BorderLayout.WEST);
            this.append(rightContainer, BorderLayout.EAST);
            this.append(hrPanel, BorderLayout.SOUTH);
            return;
        }//end

        protected void  onImageLoaded (Event event )
        {
            DisplayObject _loc_2 =null ;
            if (this.m_imageIconLoader && this.m_imageIconLoader.content)
            {
                _loc_2 = this.m_imageIconLoader.content;
                _loc_3 = ASwingHelper.scaleToFit(_loc_2,this.m_imagePanel);
                _loc_2.scaleY = ASwingHelper.scaleToFit(_loc_2, this.m_imagePanel);
                _loc_2.scaleX = _loc_3;
                if (_loc_2 instanceof Bitmap)
                {
                    ((Bitmap)_loc_2).smoothing = true;
                }
                this.m_imagePanel.append(new AssetPane(_loc_2));
            }
            return;
        }//end

    }




