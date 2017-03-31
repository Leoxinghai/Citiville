package Display.DialogUI;

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
import Display.aswingui.*;
import Engine.Managers.*;
//import flash.display.*;
//import flash.events.*;
import org.aswing.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class CrewCell extends JPanel implements GridListCell
    {
        protected Object m_cellData ;
        protected IntDimension m_size ;
        protected CrewCellFactory m_factory ;
        protected JPanel m_imagePanel ;
        protected JPanel m_infoPanel ;

        public  CrewCell (CrewCellFactory param1 ,LayoutManager param2 )
        {
            this.m_factory = param1;
            super(param2);
            return;
        }//end

        protected String  bgAssetName ()
        {
            if (this.m_cellData instanceof String)
            {
                return null;
            }
            return this.m_cellData.bgAsset;
        }//end

        protected String  picUrl ()
        {
            if (this.m_cellData instanceof String)
            {
                return null;
            }
            return this.m_cellData.picUrl;
        }//end

        public void  setGridListCellStatus (GridList param1 ,boolean param2 ,int param3 )
        {
            ASwingHelper.prepare(param1);
            _loc_4 = param1.width/param1.getColumns();
            _loc_5 = param1.height/param1.getRows();
            this.setPreferredSize(new IntDimension(_loc_4, _loc_5));
            this.setMaximumSize(new IntDimension(_loc_4, _loc_5));
            this.setMinimumSize(new IntDimension(_loc_4, _loc_5));
            ASwingHelper.prepare(this);
            ASwingHelper.prepare(param1);
            this.layoutCell();
            return;
        }//end

        public void  setCellValue (Object param1)
        {
            this.m_cellData = param1;
            this.buildCell();
            return;
        }//end

        public Object getCellValue ()
        {
            return this.m_cellData;
        }//end

        public Component  getCellComponent ()
        {
            return this;
        }//end

        public void  buildCell ()
        {
            this.initBackground();
            this.m_imagePanel = this.makeImagePanel();
            this.m_infoPanel = this.makeInfoPanel();
            return;
        }//end

        public void  layoutCell ()
        {
            this.removeAll();
            ASwingHelper.prepare(this.m_imagePanel, this.m_infoPanel);
            this.m_imagePanel.setPreferredHeight(this.height);
            this.m_infoPanel.setPreferredSize(new IntDimension(this.getPreferredWidth() - this.m_imagePanel.getPreferredWidth() - 5, this.height));
            this.appendAll(this.m_imagePanel, this.m_infoPanel);
            ASwingHelper.prepare(this);
            return;
        }//end

        protected void  initBackground ()
        {
            DisplayObject _loc_1 =null ;
            MarginBackground _loc_2 =null ;
            if (this.bgAssetName)
            {
                _loc_1 =(DisplayObject) new this.m_factory.assets.get(this.bgAssetName);
                _loc_2 = new MarginBackground(_loc_1, new Insets(0, 0, 0, 0));
                this.setBackgroundDecorator(_loc_2);
                this.m_size = new IntDimension(_loc_1.width, _loc_1.height);
                this.setMinimumSize(this.m_size);
                this.setPreferredSize(this.m_size);
                this.setMaximumSize(this.m_size);
            }
            return;
        }//end

        protected JPanel  makeImagePanel ()
        {
            DisplayObject asset ;
            Sprite image ;
            container = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            asset =(DisplayObject) new EmbeddedArt.hud_no_profile_pic();
            image = new Sprite();
            image.addChild(asset);
            AssetPane imageContainer =new AssetPane(image );
            ASwingHelper.setEasyBorder(imageContainer, 2, 0, 0, 5);
            ASwingHelper.prepare(imageContainer);
            if (this.picUrl)
            {
void                 LoadingManager .loadFromUrl (this .picUrl ,{LoadingManager priority .PRIORITY_HIGH , completeCallback (Event event )
            {
                image.removeChild(asset);
                asset =(DisplayObject) event.target.content;
                image.addChild(asset);
                return;
            }//end
            });
            }
            container.append(imageContainer);
            ASwingHelper.prepare(container);
            return container;
        }//end

        protected JPanel  makeInfoPanel ()
        {
            _loc_1 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.TOP);
            return _loc_1;
        }//end

    }




