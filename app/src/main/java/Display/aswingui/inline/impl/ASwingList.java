package Display.aswingui.inline.impl;

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

import Display.aswingui.*;
import Display.aswingui.gridlistui.*;
import Display.aswingui.inline.*;
import Display.aswingui.inline.style.*;
import org.aswing.*;
import org.aswing.ext.*;

    public class ASwingList extends ASwingObject implements IASwingList
    {
        private IASwingButton m_firstPageButton ;
        private IASwingButton m_prevPageButton ;
        private IASwingButton m_prevButton ;
        private IASwingButton m_nextButton ;
        private IASwingButton m_nextPageButton ;
        private IASwingButton m_lastPageButton ;
        private int m_rows =2;
        private int m_columns =2;
        private int m_cellHeight =-1;
        private int m_cellWidth =-1;
        private int m_horizontalGap ;
        private int m_verticalGap ;
        private boolean m_selectable ;
        private boolean m_allowMultipleSelection ;
        private Object m_cellFactory ;
        private Object m_dataModel ;
        private String m_orientation ="horizontal";
        private ScrollingList m_component ;

        public  ASwingList (String param1)
        {
            super(param1);
            return;
        }//end

         public void  destroy ()
        {
            this.m_component = null;
            this.m_firstPageButton = null;
            this.m_prevPageButton = null;
            this.m_prevButton = null;
            this.m_nextButton = null;
            this.m_nextPageButton = null;
            this.m_lastPageButton = null;
            super.destroy();
            return;
        }//end

        public IASwingList  style (IASwingStyle param1 )
        {
            m_style = param1;
            return this;
        }//end

        public IASwingList  position (int param1 ,int param2 )
        {
            m_x = param1;
            m_y = param2;
            return this;
        }//end

        public IASwingList  size (int param1 ,int param2 )
        {
            m_width = param1;
            m_height = param2;
            return this;
        }//end

        public IASwingList  firstPageButton (IASwingButton param1 )
        {
            this.m_firstPageButton = param1;
            return this;
        }//end

        public IASwingList  prevPageButton (IASwingButton param1 )
        {
            this.m_prevPageButton = param1;
            return this;
        }//end

        public IASwingList  prevButton (IASwingButton param1 )
        {
            this.m_prevButton = param1;
            return this;
        }//end

        public IASwingList  nextButton (IASwingButton param1 )
        {
            this.m_nextButton = param1;
            return this;
        }//end

        public IASwingList  nextPageButton (IASwingButton param1 )
        {
            this.m_nextPageButton = param1;
            return this;
        }//end

        public IASwingList  lastPageButton (IASwingButton param1 )
        {
            this.m_lastPageButton = param1;
            return this;
        }//end

        public IASwingList  cellFactory (Object param1 )
        {
            this.m_cellFactory = param1;
            return this;
        }//end

        public IASwingList  dataModel (Object param1 )
        {
            this.m_dataModel = param1;
            return this;
        }//end

        public IASwingList  horizontal ()
        {
            this.m_orientation = ScrollingListOrientation.HORIZONTAL;
            return this;
        }//end

        public IASwingList  vertical ()
        {
            this.m_orientation = ScrollingListOrientation.VERTICAL;
            return this;
        }//end

        public IASwingList  rows (int param1 )
        {
            this.m_rows = param1;
            return this;
        }//end

        public IASwingList  columns (int param1 )
        {
            this.m_columns = param1;
            return this;
        }//end

        public IASwingList  cellSize (int param1 ,int param2 )
        {
            this.m_cellWidth = param1;
            this.m_cellHeight = param2;
            return this;
        }//end

        public IASwingList  verticalGap (int param1 )
        {
            this.m_verticalGap = param1;
            return this;
        }//end

        public IASwingList  horizontalGap (int param1 )
        {
            this.m_horizontalGap = param1;
            return this;
        }//end

        public IASwingList  selectable (boolean param1 )
        {
            this.m_selectable = param1;
            return this;
        }//end

        public IASwingList  allowMultipleSelection (boolean param1 )
        {
            this.m_allowMultipleSelection = param1;
            return this;
        }//end

        public Component  component ()
        {
            GridListCellFactory _loc_1 =null ;
            VectorListModel _loc_2 =null ;
            if (!this.m_component)
            {
                this.m_component = new ScrollingList();
                this.m_component.orientation = this.m_orientation;
                this.initialize(this.m_component);
                this.m_component.rows = this.m_rows;
                this.m_component.columns = this.m_columns;
                this.m_component.verticalGap = this.m_verticalGap;
                this.m_component.horizontalGap = this.m_horizontalGap;
                if (this.m_cellWidth >= 0)
                {
                    this.m_component.cellWidth = this.m_cellWidth;
                }
                if (this.m_cellHeight >= 0)
                {
                    this.m_component.cellHeight = this.m_cellHeight;
                }
                if (this.m_firstPageButton)
                {
                    this.m_component.firstPageButton =(JButton) this.m_firstPageButton.component;
                }
                if (this.m_prevPageButton)
                {
                    this.m_component.prevPageButton =(JButton) this.m_prevPageButton.component;
                }
                if (this.m_prevButton)
                {
                    this.m_component.prevButton =(JButton) this.m_prevButton.component;
                }
                if (this.m_nextButton)
                {
                    this.m_component.nextButton =(JButton) this.m_nextButton.component;
                }
                if (this.m_nextPageButton)
                {
                    this.m_component.nextPageButton =(JButton) this.m_nextButton.component;
                }
                if (this.m_lastPageButton)
                {
                    this.m_component.lastPageButton =(JButton) this.m_lastPageButton.component;
                }
                this.m_component.selectable = this.m_selectable;
                this.m_component.allowMultipleSelection = this.m_allowMultipleSelection;
                _loc_1 = null;
                if (this.m_cellFactory instanceof GridListCellFactory)
                {
                    _loc_1 =(GridListCellFactory) this.m_cellFactory;
                }
                else if (this.m_cellFactory instanceof Class)
                {
                    _loc_1 = new GenericGridCellFactory(this.m_cellFactory as Class);
                }
                this.m_component.cellFactory = _loc_1;
                if (this.m_cellFactory instanceof Function)
                {
                    this.m_component.cellFactoryFunction =(Function) this.m_cellFactory;
                }
                _loc_2 = null;
                if (this.m_dataModel instanceof VectorListModel)
                {
                    _loc_2 =(VectorListModel) this.m_dataModel;
                }
                else if (this.m_dataModel instanceof Array)
                {
                    _loc_2 = new VectorListModel((Array)this.m_dataModel);
                }
                else
                {
                    _loc_2 = new VectorListModel();
                }
                this.m_component.dataModel = _loc_2;
            }
            return this.m_component;
        }//end

    }


