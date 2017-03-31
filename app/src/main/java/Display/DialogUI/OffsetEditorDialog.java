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
//import flash.display.*;
//import flash.events.*;
//import flash.net.*;
//import flash.utils.*;
import org.aswing.*;
import tool.*;

    public class OffsetEditorDialog extends GenericDialog
    {
        private GameObject m_gameObject ;
        private DisplayObject m_child ;
        private Array m_childArray ;
        private int m_numChildren ;

        public  OffsetEditorDialog ()
        {
            this.m_childArray = new Array();
            super("", "", 0, null, "", "", false);
            return;
        }//end

         protected void  onAssetsLoaded (Event event =null )
        {
            Dictionary _loc_2 =new Dictionary ();
            _loc_2.put("dialog_bg",  new (DisplayObject)m_comObject.dialog_bg());
            m_jpanel = new OffsetDialogView(_loc_2, this);
            finalizeAndShow();
            return;
        }//end

        public void  displayOffsets (GameObject param1 ,boolean param2 =false )
        {
            MapResource _loc_3 =null ;
            Item _loc_4 =null ;
            String _loc_5 =null ;
            CompositeItemImage _loc_6 =null ;
            int _loc_7 =0;
            int _loc_8 =0;
            DisplayObject _loc_9 =null ;
            Road _loc_10 =null ;
            String _loc_11 =null ;
            ItemImageInstance _loc_12 =null ;
            Sprite _loc_13 =null ;
            if (!view)
            {
                return;
            }
            if (!param1)
            {
                this.m_child = null;
                if (param1 != this.m_gameObject)
                {
                    ((OffsetDialogView)view).textArea.removeAll();
                    ((OffsetDialogView)view).textArea.append(new JLabel("..."));
                    ((OffsetDialogView)view).textArea.append(ASwingHelper.verticalStrut(30));
                    ((GenericDialogView)view).title.setText("...");
                    ASwingHelper.prepare(view, m_jwindow);
                }
            }
            else if (param1 instanceof MapResource && (param2 || param1 != this.m_gameObject))
            {
                this.m_child = null;
                _loc_3 =(MapResource) param1;
                _loc_4 = _loc_3.getItem();
                _loc_5 = this.getDirectionFromInt(_loc_3.getDirection());
                if (_loc_4)
                {
                    ((GenericDialogView)view).title.setText(_loc_4.name + " " + _loc_5);
                }
                else
                {
                    ((GenericDialogView)view).title.setText("null");
                }
                _loc_6 =(MapResource).getItemImage() as CompositeItemImage) (param1;
                if (_loc_6)
                {
                    ((OffsetDialogView)view).textArea.removeAll();
                    this.makeChildToolset(true, _loc_6, param1, param2 || OffsetEditor.captureMode);
                    _loc_7 = _loc_6.numChildren;
                    this.m_numChildren = _loc_7;
                    this.m_childArray = new Array();
                    ((OffsetDialogView)view).textArea.removeAll();
                    _loc_8 = 0;
                    while (_loc_8 < _loc_7)
                    {

                        _loc_9 = _loc_6.getChildAt(_loc_8);
                        if (!_loc_8)
                        {
                            this.m_child = _loc_9;
                        }
                        if (_loc_9)
                        {
                            this.m_childArray.push(_loc_9);
                        }
                        this.makeChildToolset(false, _loc_9, param1, param2 || OffsetEditor.captureMode);
                        _loc_8++;
                    }
                    ((OffsetDialogView)view).textArea.append(ASwingHelper.verticalStrut(20));
                }
                else
                {
                    ((OffsetDialogView)view).textArea.removeAll();
                    this.makeChildToolset(true, ((MapResource)param1).getItemImage(), param1, param2 || OffsetEditor.captureMode);
                }
                if (param1 instanceof Road)
                {
                    _loc_10 =(Road) param1;
                    if (_loc_10 != null)
                    {
                        _loc_11 = _loc_10.getOverlayImage();
                        _loc_12 = _loc_10.getItem().getCachedImage(_loc_11);
                        if (_loc_12 != null)
                        {
                            _loc_13 = _loc_10.getOverlay1();
                            if (_loc_13 != null && _loc_13.stage != null && _loc_13.numChildren > 0)
                            {
                                this.makeChildToolset(true, _loc_13.getChildAt(0), param1, param2 || OffsetEditor.captureMode);
                            }
                        }
                        _loc_12 = _loc_10.getItem().getCachedImage(_loc_10.getOverlayImage2());
                        if (_loc_12 != null)
                        {
                            _loc_13 = _loc_10.getOverlay2();
                            if (_loc_13 != null && _loc_13.stage != null && _loc_13.numChildren > 0)
                            {
                                this.makeChildToolset(true, _loc_13.getChildAt(0), param1, param2 || OffsetEditor.captureMode);
                            }
                        }
                    }
                }
                if (OffsetEditor.captureMode)
                {
                    Global.world.drawAlignmentGrid(param1);
                    OffsetEditor.captureMode = false;
                    OffsetEditor.selected = param1;
                }
                ASwingHelper.prepare(((OffsetDialogView)view).textArea, view, m_jwindow);
            }
            this.m_gameObject = param1;
            return;
        }//end

        public void  checkObject ()
        {
            if (this.m_child)
            {
                if (!this.m_child.stage)
                {
                    this.displayOffsets(this.m_gameObject, true);
                }
            }
            return;
        }//end

        private void  makeChildToolset (boolean param1 ,DisplayObject param2 ,GameObject param3 ,boolean param4 =false )
        {
            ((OffsetDialogView)view).textArea.append(param4 ? (new OffsetEditorMover(param2, param3, param1)) : (new JLabel("x=" + param2.x + "/y=" + param2.y)));
            return;
        }//end

         protected void  closeMe (Event event )
        {
            OffsetEditor.active = false;
            return;
        }//end

        public String  getDirectionFromInt (int param1 )
        {
            Array _loc_2 =new Array("SW","SE","NE","NW","N","E","S","W","SWW","NWW","NNW","NNE","NEE","SEE","SSE","SSW");
            return _loc_2.get(param1);
        }//end

        public void  saveToXML ()
        {
            XML _loc_6 =null ;
            ByteArray _loc_7 =null ;
            FileReference _loc_8 =null ;
            String _loc_9 =null ;
            String _loc_10 =null ;
            int _loc_11 =0;
            XMLList _loc_12 =null ;
            int _loc_13 =0;
            XML _loc_14 =null ;
            DisplayObject _loc_15 =null ;
            _loc_1 = (MapResource)this.m_gameObject
            if (_loc_1 == null)
            {
                return;
            }
            _loc_2 = _loc_1.getItem();
            _loc_3 = _loc_2.xml;
            _loc_4 = _loc_1.getState();
            _loc_5 = this.getDirectionFromInt(_loc_1.getDirection());
            if (this.getDirectionFromInt(_loc_1.getDirection()) == "ALL")
            {
                _loc_5 = "SW";
            }
            for(int i0 = 0; i0 < _loc_3.children().size(); i0++)
            {
            	_loc_6 = _loc_3.children().get(i0);

                _loc_9 = _loc_6.@direction.toXMLString();
                _loc_10 = _loc_6.@name.toXMLString();
                if (_loc_9.length == 0 || _loc_9 == "ALL")
                {
                    _loc_9 = "SW";
                }
                if ((_loc_10 == _loc_4 || _loc_10 == "static") && _loc_9 == _loc_5)
                {
                    _loc_11 = this.m_childArray.length;
                    if (_loc_11 >= 1)
                    {
                        _loc_12 = _loc_6.children();
                        _loc_13 = 0;
                        for(int i0 = 0; i0 < _loc_12.size(); i0++)
                        {
                        	_loc_14 = _loc_12.get(i0);

                            _loc_15 = this.m_childArray.get(_loc_13);
                            if (_loc_14.@offsetX != undefined)
                            {
                                _loc_14.@offsetX = _loc_15.x;
                                _loc_14.@offsetY = _loc_15.y;
                                _loc_13++;
                            }
                        }
                    }
                }
            }
            _loc_7 = new ByteArray();
            _loc_7.writeUTFBytes(_loc_3);
            _loc_8 = new FileReference();
            _loc_8.save(_loc_7, _loc_2.name + ".xml");
            return;
        }//end

    }




