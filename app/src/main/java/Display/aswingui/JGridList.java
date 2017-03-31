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

import org.aswing.*;
import org.aswing.event.*;
import org.aswing.ext.*;

    public class JGridList extends GridList
    {
        protected Function m_cellFactoryFunction ;

        public  JGridList (ListModel param1 ,GridListCellFactory param2 ,int param3 =0,int param4 =2)
        {
            super(param1, param2, param3, param4);
            return;
        }//end

         public void  setModel (ListModel param1 )
        {
            if (param1 !== model)
            {
                if (model)
                {
                    model.removeListDataListener(this);
                    this.clearList();
                }
                model = param1;
                if (model)
                {
                    model.addListDataListener(this);
                    this.buildList();
                }
            }
            return;
        }//end

         public void  intervalAdded (ListDataEvent event )
        {
            _loc_2 = Math.min(event.getIndex0(),event.getIndex1());
            _loc_3 = Math.max(event.getIndex0(),event.getIndex1());
            this.buildList(_loc_2, _loc_3);
            return;
        }//end

        private void  clearList ()
        {
            GridListCell _loc_3 =null ;
            clearSelection();
            _loc_1 = cells.size();
            int _loc_2 =0;
            while (_loc_2 < _loc_1)
            {

                _loc_3 = cells.get(_loc_2);
                removeHandlersFromCell(_loc_3.getCellComponent());
                _loc_2++;
            }
            tileHolder.removeAll();
            cells.clear();
            return;
        }//end

        private void  buildList (int param1 =0,int param2 =2.14748e +009)
        {
            Object _loc_6 =null ;
            GridListCell _loc_7 =null ;
            _loc_3 = getModel();
            boolean _loc_4 =true ;
            if (param2 == int.MAX_VALUE)
            {
                param2 = _loc_3.getSize() - 1;
                _loc_4 = false;
            }
            _loc_5 = param1;
            while (_loc_5 <= param2)
            {

                _loc_6 = _loc_3.getElementAt(_loc_5);
                if (cellFactory)
                {
                    _loc_7 = createNewCell();
                }
                else if (this.m_cellFactoryFunction != null)
                {
                    _loc_7 = this.m_cellFactoryFunction(_loc_6);
                }
                if (_loc_7)
                {
                    cells.append(_loc_7);
                    _loc_7.setCellValue(_loc_6);
                    _loc_7.setGridListCellStatus(this, false, _loc_5);
                    addCellToContainer(_loc_7, _loc_5);
                }
                _loc_5++;
            }
            if (_loc_4)
            {
                selectionModel.insertIndexInterval(param1, param2 - param1 + 1, true);
            }
            tileHolder.revalidate();
            revalidate();
            return;
        }//end

        public void  cellFactoryFunction (Function param1 )
        {
            this.m_cellFactoryFunction = param1;
            return;
        }//end

        public Function  cellFactoryFunction ()
        {
            return this.m_cellFactoryFunction;
        }//end

    }



