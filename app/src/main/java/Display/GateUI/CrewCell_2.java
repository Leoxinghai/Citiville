package Display.GateUI;

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
//import flash.display.*;
import org.aswing.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class CrewCell_2 extends CrewCell
    {
        protected Function m_layoutInfoPanel ;
        protected int m_color ;
        private  int CELL_WIDTH =550;
        private  int CELL_HEIGHT =56;
        protected JPanel m_imagePanel2 ;
        protected JPanel m_infoPanel2 ;

        public  CrewCell_2 (CrewCellFactory_2 param1 ,int param2 ,LayoutManager param3 =null )
        {
            this.m_color = param2;
            super(param1, param3);
            return;
        }//end

        public String  position ()
        {
            if (m_cellData == null)
            {
                return "";
            }
            if (m_cellData instanceof String)
            {
                return "";
            }
            return m_cellData.name;
        }//end

        public String  friendName ()
        {
            if (m_cellData == null)
            {
                return null;
            }
            if (m_cellData instanceof String)
            {
                return null;
            }
            return m_cellData.friendName;
        }//end

         public void  setGridListCellStatus (GridList param1 ,boolean param2 ,int param3 )
        {
            if (param1 !=null)
            {
                param1.setTileWidth(this.CELL_WIDTH);
                param1.setTileHeight(this.CELL_HEIGHT);
                this.layoutCell();
            }
            return;
        }//end

         protected void  initBackground ()
        {
            m_size = new IntDimension(this.CELL_WIDTH, this.CELL_HEIGHT);
            this.setMinimumSize(m_size);
            this.setPreferredSize(m_size);
            this.setMaximumSize(m_size);
            Sprite _loc_1 =new Sprite ();
            if (this.m_color % 2 == 1)
            {
                _loc_1.graphics.beginFill(EmbeddedArt.whiteTextColor);
            }
            else
            {
                _loc_1.graphics.beginFill(EmbeddedArt.lightBlueTextColor);
            }
            _loc_1.graphics.drawRect(0, 0, this.CELL_WIDTH, this.CELL_HEIGHT);
            _loc_1.graphics.endFill();
            ASwingHelper.setBackground(this, _loc_1);
            return;
        }//end

         public void  setCellValue (Object param1)
        {
            super.setCellValue(param1);
            this.buildCell();
            this.layoutCell();
            return;
        }//end

         public void  buildCell ()
        {
            this.initBackground();
            if (this.position.length > 0)
            {
                this.m_imagePanel2 = makeImagePanel();
            }
            else
            {
                this.m_imagePanel2 = null;
            }
            this.m_infoPanel2 = this.makeInfoPanel();
            return;
        }//end

         protected JPanel  makeInfoPanel ()
        {
            JTextField _loc_4 =null ;
            _loc_1 = ASwingHelper.makeSoftBoxJPanel(SoftBoxLayout.LEFT);
            if (m_cellData instanceof String)
            {
                return _loc_1;
            }
            _loc_2 = ASwingHelper.makeSoftBoxJPanelVertical(SoftBoxLayout.CENTER);
            _loc_3 = ASwingHelper.makeTextField(TextFieldUtil.formatSmallCapsString(this.position)+" ",EmbeddedArt.DEFAULT_FONT_NAME_BOLD,14,EmbeddedArt.darkBlueTextColor);
            if (this.friendName)
            {
                _loc_4 = ASwingHelper.makeTextField(this.friendName + " ", EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.darkBrownTextColor);
            }
            else
            {
                _loc_4 = ASwingHelper.makeTextField(ZLoc.t("Dialogs", "EmptyCrewCell") + " ", EmbeddedArt.defaultFontNameBold, 12, EmbeddedArt.blueTextColor);
            }
            _loc_2.appendAll(_loc_3, _loc_4);
            _loc_1.append(_loc_2);
            return _loc_1;
        }//end

         public void  layoutCell ()
        {
            this.removeAll();
            ASwingHelper.prepare(this.m_imagePanel2, this.m_infoPanel2);
            this.appendAll(this.m_imagePanel2, this.m_infoPanel2);
            ASwingHelper.prepare(this);
            return;
        }//end

    }



