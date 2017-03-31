package Display.aswingui;

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

import Display.aswingui.inline.*;
import Display.aswingui.inline.util.*;
import Events.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.ext.*;
import org.aswing.geom.*;

    public class ScrollingList extends JPanel
    {
        private JGridList m_list ;
        private VectorListModel m_dataModel ;
        private GridListCellFactory m_cellFactory ;
        private Function m_cellFactoryFunction ;
        private String m_orientation ;
        private int m_rows ;
        private int m_columns ;
        private int m_width ;
        private int m_height ;
        private int m_cellWidth ;
        private int m_cellHeight ;
        private int m_verticalGap ;
        private int m_horizontalGap ;
        private boolean m_selectable ;
        private boolean m_allowMultipleSelection ;
        private int m_selectedIndex =-1;
        private Array m_selectedIndices ;
        private Object m_selectedValue ;
        private Array m_selectedValues ;
        private JButton m_firstPageButton ;
        private JButton m_prevPageButton ;
        private JButton m_prevButton ;
        private JButton m_nextButton ;
        private JButton m_nextPageButton ;
        private JButton m_lastPageButton ;
        private EventHandler m_firstPageHandler ;
        private EventHandler m_prevPageHandler ;
        private EventHandler m_prevCellHandler ;
        private EventHandler m_nextCellHandler ;
        private EventHandler m_nextPageHandler ;
        private EventHandler m_lastPageHandler ;
        private EventHandler m_selectionHandler ;
        private int m_cellBlockIndex ;
        private static ASwingFactory swing ;

        public  ScrollingList ()
        {
            if (!swing)
            {
                swing = ASwingFactory.getInstance();
            }
            this.orientation = ScrollingListOrientation.HORIZONTAL;
            /*
            this.m_firstPageHandler = addEventListener(AWEvent.ACT, this.firstPageButton_clickHandler);
            this.m_prevPageHandler = addEventListener(AWEvent.ACT, this.prevPageButton_clickHandler);
            this.m_prevCellHandler = addEventListener(AWEvent.ACT, this.prevButton_clickHandler);
            this.m_nextCellHandler = addEventListener(AWEvent.ACT, this.nextButton_clickHandler);
            this.m_nextPageHandler = addEventListener(AWEvent.ACT, this.nextPageButton_clickHandler);
            this.m_lastPageHandler = addEventListener(AWEvent.ACT, this.lastPageButton_clickHandler);
            this.m_selectionHandler = addEventListener(SelectionEvent.LIST_SELECTION_CHANGED, this.gridList_selectionHandler, SelectionEvent.LIST_SELECTION_CHANGING, this.gridList_selectionHandler);
            */
            return;
        }//end

        public String  orientation ()
        {
            return this.m_orientation;
        }//end

        public void  orientation (String param1 )
        {
            LayoutManager _loc_2 =null ;
            if (param1 != this.m_orientation)
            {
                this.m_orientation = param1;
                if (this.m_orientation == ScrollingListOrientation.HORIZONTAL)
                {
                    _loc_2 = swing.layout.horizontal.align(SoftBoxLayout.CENTER).manager;
                }
                else
                {
                }
                _loc_2 = swing.layout.vertical.align(SoftBoxLayout.TOP).manager;
                this.setLayout(_loc_2);
            }
            return;
        }//end

        public VectorListModel  dataModel ()
        {
            return this.m_dataModel;
        }//end

        public void  dataModel (VectorListModel param1 )
        {
            if (this.m_dataModel !== param1)
            {
                this.m_dataModel = param1;
                if (this.m_list)
                {
                    this.m_list.setModel(param1);
                }
                else
                {
                    this.updateList();
                }
            }
            return;
        }//end

        public GridList  gridList ()
        {
            return this.m_list;
        }//end

        public Function  cellFactoryFunction ()
        {
            return this.m_cellFactoryFunction;
        }//end

        public void  cellFactoryFunction (Function param1 )
        {
            if (param1 !== this.m_cellFactoryFunction)
            {
                this.m_cellFactoryFunction = param1;
                this.clearList();
                this.updateList();
            }
            return;
        }//end

        public GridListCellFactory  cellFactory ()
        {
            return this.m_cellFactory;
        }//end

        public void  cellFactory (GridListCellFactory param1 )
        {
            if (this.m_cellFactory !== param1)
            {
                this.m_cellFactory = param1;
                this.clearList();
                this.updateList();
            }
            return;
        }//end

        public int  columns ()
        {
            return this.m_columns;
        }//end

        public void  columns (int param1 )
        {
            if (this.m_columns != param1)
            {
                this.m_columns = param1;
                if (this.m_list)
                {
                    this.m_list.setColumns(param1);
                    this.updateSize();
                }
                else
                {
                    this.updateList();
                }
            }
            return;
        }//end

        public int  rows ()
        {
            return this.m_rows;
        }//end

        public void  rows (int param1 )
        {
            if (this.m_rows != param1)
            {
                this.m_rows = param1;
                if (this.m_list)
                {
                    this.m_list.setRows(param1);
                    this.updateSize();
                }
                else
                {
                    this.updateList();
                }
            }
            return;
        }//end

        public int  numElements ()
        {
            return this.m_dataModel ? (this.m_dataModel.size()) : (0);
        }//end

        public int  numCellBlocks ()
        {
            return this.horizontal ? (Math.ceil(this.numElements / this.m_rows)) : (Math.ceil(this.numElements / this.m_columns));
        }//end

        public int  verticalGap ()
        {
            return this.m_verticalGap;
        }//end

        public void  verticalGap (int param1 )
        {
            this.m_verticalGap = param1;
            if (this.m_list && this.horizontal)
            {
                this.m_list.setVGap(param1);
            }
            this.updateSize();
            return;
        }//end

        public int  horizontalGap ()
        {
            return this.m_horizontalGap;
        }//end

        public void  horizontalGap (int param1 )
        {
            this.m_horizontalGap = param1;
            if (this.m_list && !this.horizontal)
            {
                this.m_list.setHGap(param1);
            }
            this.updateSize();
            return;
        }//end

         public void  setPreferredSize (IntDimension param1 )
        {
            super.setPreferredSize(param1);
            this.m_width = param1.width;
            this.m_height = param1.height;
            this.updateSize();
            return;
        }//end

        public int  cellWidth ()
        {
            return this.m_cellWidth;
        }//end

        public void  cellWidth (int param1 )
        {
            if (this.m_cellWidth != param1)
            {
                this.m_cellWidth = param1;
                this.updateSize();
            }
            return;
        }//end

        public int  cellHeight ()
        {
            return this.m_cellHeight;
        }//end

        public void  cellHeight (int param1 )
        {
            if (this.m_cellHeight != param1)
            {
                this.m_cellHeight = param1;
                this.updateSize();
            }
            return;
        }//end

        public JButton  firstPageButton ()
        {
            return this.m_firstPageButton;
        }//end

        public void  firstPageButton (JButton param1 )
        {
            this.m_firstPageButton = param1;
            this.m_firstPageHandler.source = param1;
            this.updateControls();
            return;
        }//end

        public JButton  prevPageButton ()
        {
            return this.m_prevPageButton;
        }//end

        public void  prevPageButton (JButton param1 )
        {
            this.m_prevPageButton = param1;
            this.m_prevPageHandler.source = param1;
            this.updateControls();
            return;
        }//end

        public JButton  prevButton ()
        {
            return this.m_prevButton;
        }//end

        public void  prevButton (JButton param1 )
        {
            this.m_prevButton = param1;
            this.m_prevCellHandler.source = param1;
            this.updateControls();
            return;
        }//end

        public JButton  nextButton ()
        {
            return this.m_nextButton;
        }//end

        public void  nextButton (JButton param1 )
        {
            this.m_nextButton = param1;
            this.m_nextCellHandler.source = param1;
            this.updateControls();
            return;
        }//end

        public JButton  nextPageButton ()
        {
            return this.m_nextPageButton;
        }//end

        public void  nextPageButton (JButton param1 )
        {
            this.m_nextPageButton = param1;
            this.m_nextPageHandler.source = param1;
            this.updateControls();
            return;
        }//end

        public JButton  lastPageButton ()
        {
            return this.m_lastPageButton;
        }//end

        public void  lastPageButton (JButton param1 )
        {
            this.m_lastPageButton = param1;
            this.m_lastPageHandler.source = param1;
            this.updateControls();
            return;
        }//end

        public boolean  selectable ()
        {
            return this.m_selectable;
        }//end

        public void  selectable (boolean param1 )
        {
            if (this.m_selectable != param1)
            {
                this.m_selectable = param1;
                if (this.m_list)
                {
                    this.m_list.setSelectable(param1);
                }
            }
            return;
        }//end

        public boolean  allowMultipleSelection ()
        {
            return this.m_allowMultipleSelection;
        }//end

        public void  allowMultipleSelection (boolean param1 )
        {
            int _loc_2 =0;
            if (this.m_allowMultipleSelection != param1)
            {
                this.m_allowMultipleSelection = param1;
                _loc_2 = param1 ? (GridList.MULTIPLE_SELECTION) : (GridList.SINGLE_SELECTION);
                if (this.m_list)
                {
                    this.m_list.setSelectionMode(_loc_2);
                }
            }
            return;
        }//end

        public int  selectedIndex ()
        {
            return this.m_list ? (this.m_list.getSelectedIndex()) : (-1);
        }//end

        public void  selectedIndex (int param1 )
        {
            if (param1 != this.selectedIndex)
            {
                this.m_selectedIndex = param1;
                if (this.m_list && this.m_selectable)
                {
                    this.m_list.setSelectedIndex(this.m_selectedIndex);
                }
            }
            return;
        }//end

        public Array  selectedIndices ()
        {
            return this.m_list ? (this.m_list.getSelectedIndices()) : ([]);
        }//end

        public void  selectedIndices (Array param1 )
        {
            this.m_selectedIndices = param1;
            if (this.m_list)
            {
                this.m_list.setSelectedIndices(param1);
                this.m_selectedIndices = null;
            }
            return;
        }//end

        public Object  selectedValue ()
        {
            return this.m_list ? (this.m_list.getSelectedValue()) : (null);
        }//end

        public void  selectedValue (Object param1 )
        {
            if (param1 != this.selectedValue)
            {
                this.m_selectedValue = param1;
                if (this.m_list)
                {
                    this.m_list.setSelectedValue(param1);
                }
            }
            return;
        }//end

        public Array  selectedValues ()
        {
            return this.m_list ? (this.m_list.getSelectedValues()) : ([]);
        }//end

        public void  selectedValues (Array param1 )
        {
            this.m_selectedValues = param1;
            if (this.m_list)
            {
                this.m_list.setSelectedValues(this.m_selectedValues);
                this.m_selectedValues = null;
            }
            return;
        }//end

        public GridListCell  selectedCell ()
        {
            GridListCell _loc_1 =null ;
            if (this.m_list && this.selectedIndex >= 0)
            {
                _loc_1 = this.m_list.getCellByIndex(this.m_list.getSelectedIndex());
            }
            return _loc_1;
        }//end

        public Array  selectedCells ()
        {
            Array _loc_2 =null ;
            int _loc_3 =0;
            Array _loc_1 =new Array();
            if (this.m_list)
            {
                _loc_2 = this.selectedIndices;
                for(int i0 = 0; i0 < _loc_2.size(); i0++)
                {
                	_loc_3 = _loc_2.get(i0);

                    if (_loc_3 >= 0)
                    {
                        _loc_1.push(this.m_list.getCellByIndex(_loc_3));
                    }
                }
            }
            return _loc_1;
        }//end

        private void  firstPageButton_clickHandler (AWEvent event )
        {
            this.scroll(event, -this.numCellBlocks);
            return;
        }//end

        private void  prevPageButton_clickHandler (AWEvent event )
        {
            _loc_2 = this.horizontal? (this.m_columns) : (this.m_rows);
            this.scroll(event, -_loc_2);
            return;
        }//end

        private void  prevButton_clickHandler (AWEvent event )
        {
            int _loc_2 =-1;
            this.scroll(event, _loc_2);
            return;
        }//end

        private void  nextButton_clickHandler (AWEvent event )
        {
            int _loc_2 =1;
            this.scroll(event, _loc_2);
            return;
        }//end

        private void  nextPageButton_clickHandler (AWEvent event )
        {
            _loc_2 = this.horizontal? (this.m_columns) : (this.m_rows);
            this.scroll(event, _loc_2);
            return;
        }//end

        private void  lastPageButton_clickHandler (AWEvent event )
        {
            this.scroll(event, this.numCellBlocks);
            return;
        }//end

        private void  gridList_selectionHandler (SelectionEvent event )
        {
            int _loc_2 =0;
            if (event.type == SelectionEvent.LIST_SELECTION_CHANGING)
            {
                _loc_2 = event.getFirstIndex();
                if (this.m_dataModel.get(_loc_2) == null)
                {
                    event.preventDefault();
                }
            }
            else
            {
                this.dispatchEvent(event.clone());
            }
            return;
        }//end

        private void  scroll (AWEvent event ,int param2 )
        {
            int _loc_6 =0;
            event.stopPropagation();
            event.stopImmediatePropagation();
            _loc_3 = this.m_cellBlockIndex+param2;
            _loc_4 = this.horizontal? (this.m_columns) : (this.m_rows);
            _loc_5 = this.numCellBlocks-_loc_4;
            if (_loc_3 < 0)
            {
                _loc_3 = 0;
                param2 = _loc_3 - this.m_cellBlockIndex;
            }
            else if (_loc_3 > _loc_5)
            {
                _loc_3 = _loc_5;
                param2 = _loc_3 - this.m_cellBlockIndex;
            }
            this.m_cellBlockIndex = _loc_3;
            if (this.horizontal)
            {
                _loc_6 = param2 * this.m_list.getTileWidth();
                this.m_list.scrollHorizontal(_loc_6);
            }
            else
            {
                _loc_6 = param2 * this.m_list.getTileHeight();
                this.m_list.scrollVertical(_loc_6);
            }
            this.updateControls();
            return;
        }//end

        private void  updateList ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            int _loc_3 =0;
            if (this.m_cellFactory && this.m_dataModel && this.m_columns && this.m_rows)
            {
                _loc_1 = 0;
                _loc_2 = 0;
                _loc_3 = this.m_allowMultipleSelection ? (GridList.MULTIPLE_SELECTION) : (GridList.SINGLE_SELECTION);
                if (this.horizontal)
                {
                    _loc_1 = this.m_rows;
                }
                else
                {
                    _loc_2 = this.m_columns;
                }
                this.m_list = new JGridList(this.m_dataModel, this.m_cellFactory, _loc_2, _loc_1);
                if (this.m_cellFactoryFunction != null)
                {
                    this.m_list.cellFactoryFunction = this.m_cellFactoryFunction;
                }
                this.m_list.setSelectable(this.m_selectable);
                this.m_list.setSelectionMode(_loc_3);
                this.m_list.setHGap(this.m_horizontalGap);
                this.m_list.setVGap(this.m_verticalGap);
                this.m_selectionHandler.source = this.m_list;
                this.updateSize();
                this.append(this.m_list);
                if (this.m_selectedIndex >= 0)
                {
                    this.m_list.setSelectedIndex(this.m_selectedIndex);
                }
                else if (this.m_selectedValue)
                {
                    this.m_list.setSelectedValue(this.m_selectedValue);
                }
                else if (this.m_selectedIndices && this.m_selectedIndices.length())
                {
                    this.m_list.setSelectedIndices(this.m_selectedIndices);
                    this.m_selectedIndices = null;
                }
                else if (this.m_selectedValues && this.m_selectedValues.length())
                {
                    this.m_list.setSelectedValues(this.m_selectedValues);
                    this.m_selectedValues = null;
                }
            }
            return;
        }//end

        private void  updateControls ()
        {
            boolean _loc_1 =false ;
            int _loc_2 =0;
            int _loc_3 =0;
            boolean _loc_4 =false ;
            if (this.m_list)
            {
                _loc_1 = this.m_cellBlockIndex != 0;
                _loc_2 = this.horizontal ? (this.m_columns) : (this.m_rows);
                _loc_3 = this.numCellBlocks - _loc_2;
                _loc_4 = this.m_cellBlockIndex < _loc_3;
                if (this.m_firstPageButton)
                {
                    this.m_firstPageButton.setEnabled(_loc_1);
                }
                if (this.m_prevPageButton)
                {
                    this.m_prevPageButton.setEnabled(_loc_1);
                }
                if (this.m_prevButton)
                {
                    this.m_prevButton.setEnabled(_loc_1);
                }
                if (this.m_nextButton)
                {
                    this.m_nextButton.setEnabled(_loc_4);
                }
                if (this.m_nextPageButton)
                {
                    this.m_nextPageButton.setEnabled(_loc_4);
                }
                if (this.m_lastPageButton)
                {
                    this.m_lastPageButton.setEnabled(_loc_4);
                }
            }
            return;
        }//end

        private void  updateSize ()
        {
            int _loc_1 =0;
            int _loc_2 =0;
            int _loc_3 =0;
            int _loc_4 =0;
            if (this.m_list)
            {
                if (this.m_width && this.m_height)
                {
                    _loc_1 = this.m_width;
                    _loc_2 = this.m_height;
                    _loc_3 = this.m_width / this.m_columns;
                    _loc_4 = this.m_height / this.m_rows;
                }
                else if (this.m_cellWidth && this.m_cellHeight && this.m_columns && this.m_rows)
                {
                    _loc_1 = this.m_cellWidth * this.m_columns;
                    _loc_2 = this.m_cellHeight * this.m_rows;
                    if (this.horizontal)
                    {
                        _loc_2 = _loc_2 + (this.m_verticalGap * this.m_rows - 1);
                    }
                    else
                    {
                        _loc_1 = _loc_1 + (this.m_horizontalGap * this.m_columns - 1);
                    }
                    _loc_3 = this.m_cellWidth;
                    _loc_4 = this.m_cellHeight;
                }
                if (_loc_1 && _loc_2)
                {
                    this.m_list.setPreferredSize(ASwingUnits.dimensions(_loc_1, _loc_2));
                }
                if (_loc_3 && _loc_4)
                {
                    this.m_list.setTileWidth(_loc_3);
                    this.m_list.setTileHeight(_loc_4);
                }
            }
            return;
        }//end

        private boolean  horizontal ()
        {
            return this.m_orientation == ScrollingListOrientation.HORIZONTAL;
        }//end

        private void  clearList ()
        {
            if (this.m_list)
            {
                this.remove(this.m_list);
                this.m_list.removeAll();
                this.m_list = null;
            }
            return;
        }//end

    }




