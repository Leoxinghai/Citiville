package Display.TrainUI;

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
import org.aswing.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class TrainStopCell extends DataItemCell
    {
        private Object m_cellData ;
        private JPanel m_portraitPanel ;
        private DisplayObject m_portrait ;
        private Player m_friend ;
        public static  double CELL_WIDTH =66;
        public static  double CELL_HEIGHT =170;
        public static  double CARD_WIDTH =66;
        public static  double CARD_HEIGHT =129;

        public  TrainStopCell (LayoutManager param1)
        {
            super(new SoftBoxLayout(SoftBoxLayout.Y_AXIS));
            return;
        }//end

        public Player  friend ()
        {
            return this.m_friend;
        }//end

         protected void  initializeCell ()
        {
            String _loc_9 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical ();
            this.setupBackground(_loc_1);
            if (!this.m_portrait)
            {
                if (this.m_cellData.hasOwnProperty("uid"))
                {
                    this.m_friend = Global.player.findFriendById(this.m_cellData.get("uid"));
                    if (this.m_friend)
                    {
                        _loc_9 = this.m_friend.snUser.picture;
                        this.m_portrait = LoadingManager.loadFromUrl(_loc_9, {priority:LoadingManager.PRIORITY_LOW, completeCallback:this.onLoadPicSuccess});
                        (this.m_portrait as Loader).contentLoaderInfo.addEventListener(SecurityErrorEvent.SECURITY_ERROR, this.onLoadPicFail, false, 2);
                    }
                    else
                    {
                        this.m_portrait = new TrainStationDialog.assetDict.get("portrait_empty");
                    }
                }
                else
                {
                    this.m_portrait = new TrainStationDialog.assetDict.get("portrait_empty");
                }
            }
            AssetPane _loc_2 =new AssetPane(this.m_portrait );
            this.m_portraitPanel = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER);
            this.m_portraitPanel.setPreferredHeight(50);
            this.m_portraitPanel.append(_loc_2);
            _loc_3 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_3.append(new AssetPane(new TrainStationDialog.assetDict.get("checkmark")));
            _loc_4 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_5 = this.m_cellData.get( "commodityType") =="goods"? (new TrainStationDialog.assetDict.get("crate_small")) : (new TrainStationDialog.assetDict.get("coin_small"));
            _loc_4.append(new AssetPane(_loc_5));
            _loc_6 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_6.append(ASwingHelper.makeTextField(this.m_cellData.get("commodityIncrement"), EmbeddedArt.titleFont, TextFieldUtil.getLocaleFontSize(20, 20, .get({locale:"ja", size:15})), EmbeddedArt.greenTextColor));
            _loc_7 = ASwingHelper.makeSoftBoxJPanelVertical ();
            _loc_7.appendAll(_loc_4, _loc_6);
            _loc_1.appendAll(ASwingHelper.verticalStrut(5), this.m_portraitPanel, ASwingHelper.verticalStrut(5), this.m_cellData.hasOwnProperty("uid") ? (_loc_3) : (_loc_7));
            _loc_8 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.CENTER );
            _loc_8.append(ASwingHelper.makeTextField(((m_index + 1)).toString(), EmbeddedArt.titleFont, 30, EmbeddedArt.blueTextColor));
            removeAll();
            appendAll(_loc_1, ASwingHelper.verticalStrut(-5), _loc_8);
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  onLoadPicSuccess (Object param1)
        {
            ASwingHelper.prepare(this);
            return;
        }//end

        private void  onLoadPicFail (Event event )
        {
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  setupBackground (JPanel param1 )
        {
            _loc_2 = TrainStationDialog.assetDict.get("portrait_card");
            _loc_3 = new _loc_2 ;
            AssetBackground _loc_4 =new AssetBackground(_loc_3 );
            param1.setBackgroundDecorator(_loc_4);
            param1.setPreferredSize(new IntDimension(CELL_WIDTH, CARD_HEIGHT));
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            this.m_cellData = param1;
            this.setGridListCellStatus(m_gridList, false, m_index);
            return;
        }//end

         public void  setGridListCellStatus (GridList param1 ,boolean param2 ,int param3 )
        {
            super.setGridListCellStatus(param1, param2, param3);
            this.initializeCell();
            return;
        }//end

         public Object getCellValue ()
        {
            return this.m_cellData;
        }//end

    }



