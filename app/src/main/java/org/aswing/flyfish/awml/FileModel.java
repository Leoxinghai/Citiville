package org.aswing.flyfish.awml;

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

//import flash.display.*;
import org.aswing.*;
import org.aswing.event.*;
import org.aswing.flyfish.css.*;
import org.aswing.flyfish.util.*;

    public class FileModel extends Object
    {
        private String name ;
        private String packageName ;
        private CSSHeader cssHeader ;
        private ComModel root ;
        private Sprite canvas ;
        private String filePath ;
        private boolean relative =false ;
        private Array listenerList ;
        private boolean forCode ;
        private String awml ;
        private boolean valid =true ;
        private Function changeHandler ;
        private boolean saved ;
        private EventGenerator eventG ;
        private FileModelBridge bridge ;

        public  FileModel (FileModelBridge param1 ,boolean param2 =false )
        {
            this.bridge = param1;
            this.forCode = param2;
            this.listenerList = new Array();
            this.saved = false;
            this.eventG = new EventGenerator();
            if (!param2)
            {
                this.canvas = new Sprite();
                this.canvas.mouseEnabled = false;
            }
            return;
        }//end

        public void  setTreeBridge (FileModelBridge param1 )
        {
            this.bridge = param1;
            return;
        }//end

        public FileModelBridge  getTreeBridge ()
        {
            return this.bridge;
        }//end

        public void  reset (ComModel param1 ,String param2 ,String param3 ,CSSHeader param4 )
        {
            this.clear();
            this.root = param1;
            this.name = param2;
            this.packageName = param3;
            this.cssHeader = param4;
            if (this.canvas)
            {
                this.canvas.addChild(param1.getDisplay());
            }
            _loc_5 = param1.getDisplay ();
            param1.getDisplay().addEventListener(MovedEvent.MOVED, this.__rootRangeChanged);
            _loc_5.addEventListener(ResizedEvent.RESIZED, this.__rootRangeChanged);
            this.addListenersToAll(param1);
            param4.headerChangedHandler = this.__cssHeanderChanged;
            if (!this.forCode)
            {
                AsWingManager.callLater(this.__laterRevalidate, 100);
            }
            return;
        }//end

        public void  resetNames (String param1 ,String param2 )
        {
            this.name = param1;
            this.packageName = param2;
            return;
        }//end

        private void  clear ()
        {
            Component _loc_1 =null ;
            if (this.root)
            {
                _loc_1 = this.root.getDisplay();
                _loc_1.removeEventListener(MovedEvent.MOVED, this.__rootRangeChanged);
                _loc_1.removeEventListener(ResizedEvent.RESIZED, this.__rootRangeChanged);
                this.removeListenersFromAll(this.root);
                if (this.canvas)
                {
                    this.canvas.removeChild(this.root.getDisplay());
                }
            }
            if (this.cssHeader)
            {
                this.cssHeader.headerChangedHandler = null;
            }
            return;
        }//end

        private void  __rootRangeChanged (Object param1)
        {
            this.fireRootRangeEvent();
            return;
        }//end

        private void  __laterRevalidate ()
        {
            this.revalidate();
            return;
        }//end

        private void  __cssHeanderChanged (boolean param1 )
        {
            this.callChangeHandler(!param1);
            return;
        }//end

        private void  addListenersToAll (ComModel param1 )
        {
            param1.setChangedHandler(this.__comChanged);
            _loc_2 = param1.getChildCount ();
            int _loc_3 =0;
            while (_loc_3 < _loc_2)
            {

                this.addListenersToAll(param1.getChild(_loc_3));
                _loc_3++;
            }
            return;
        }//end

        private void  removeListenersFromAll (ComModel param1 )
        {
            param1.setChangedHandler(null);
            _loc_2 = param1.getChildCount ();
            int _loc_3 =0;
            while (_loc_3 < _loc_2)
            {

                this.removeListenersFromAll(param1.getChild(_loc_3));
                _loc_3++;
            }
            return;
        }//end

        public LazyLoadRequestList  applyCSS (StyleSheetList param1 )
        {
            if (this.root)
            {
                return this.root.applyCSS(param1);
            }
            return null;
        }//end

        public void  parse (XML param1 )
        {
            _loc_2 = new ComModel ();
            _loc_3 = param1.children ();
            _loc_4 = _loc_3.get(0);
            _loc_5 = _loc_3.get(1);
            _loc_6 = new CSSHeader ();
            if (_loc_4.name().toString() == CSSHeader.TAG)
            {
                _loc_2.parse(_loc_5);
                _loc_6.decode(_loc_4);
            }
            else
            {
                _loc_2.parse(_loc_4);
                _loc_6.decode(_loc_5);
            }
            _loc_7 = param1.@name;
            _loc_8 = param1.@packageName;
            this.reset(_loc_2, _loc_7, _loc_8, _loc_6);
            return;
        }//end

        public void  updateFromAwml ()
        {
            _loc_1 = new XML(this.awml );
            this.parse(_loc_1);
            this.bridge.fireTreeStructureChanged(this, [this.root], null, null);
            return;
        }//end

        public XML  exportXML ()
        {
            _loc_1 = new XML("<AsWingUI></AsWingUI>")("<AsWingUI></AsWingUI>");
            _loc_1.@name = this.getName();
            _loc_1.@packageName = this.packageName;
            _loc_1.appendChild(this.cssHeader.encode());
            _loc_1.appendChild(this.root.encodeXML());
            this.awml = _loc_1.toXMLString();
            return _loc_1;
        }//end

        public String  getAWML ()
        {
            return this.awml;
        }//end

        public boolean  isValid ()
        {
            return this.valid;
        }//end

        public FileModel  clone (boolean param1 =false ,String param2 ,String param3 =null )
        {
            _loc_4 = this.exportXML ();
            if (param2)
            {
                _loc_4.@packageName = param2;
            }
            if (param3)
            {
                _loc_4.@name = param3;
            }
            return parseXML(_loc_4, param1);
        }//end

        public void  setFilePath (String param1 ,boolean param2 )
        {
            this.filePath = param1;
            this.relative = param2;
            return;
        }//end

        public String  getFilePath ()
        {
            return this.filePath;
        }//end

        public boolean  isRelativePath ()
        {
            return this.relative;
        }//end

        public DisplayObject  getDisplay ()
        {
            return this.canvas;
        }//end

        public CSSHeader  getCSSHeader ()
        {
            return this.cssHeader;
        }//end

        public void  revalidate ()
        {
            if (this.root)
            {
                this.root.getDisplay().revalidate();
            }
            return;
        }//end

        public ComModel  getRootComponent ()
        {
            return this.root;
        }//end

        public String  getName ()
        {
            return this.name;
        }//end

        public String  getPackageName ()
        {
            return this.packageName;
        }//end

        public String  toString ()
        {
            _loc_1 = this.isSaved ()? ("") : ("*");
            if (this.packageName == null || this.packageName == "")
            {
                return _loc_1 + this.name;
            }
            return _loc_1 + this.packageName + "." + this.name;
        }//end

        public Array  getPath (ComModel param1 )
        {
            Array _loc_2 =new Array();
            _loc_2.push(param1);
            while (param1 != this.root)
            {

                param1 = param1.getParent();
                _loc_2.push(param1);
            }
            _loc_2.reverse();
            return _loc_2;
        }//end

        public Array  getParentPath (ComModel param1 )
        {
            return this.getPath(param1.getParent());
        }//end

        public void  addComponent (ComModel param1 ,ComModel param2 ,int param3 =-1)
        {
            _loc_4 = this.getPath(param1 );
            param1.addChild(param2, param3);
            if (param3 < 0)
            {
                param3 = param1.getChildCount();
            }
            this.bridge.fireTreeNodesInserted(this, _loc_4, [param3], [param2]);
            param2.setChangedHandler(this.__comChanged);
            this.callChangeHandler();
            return;
        }//end

        public void  removeComponent (ComModel param1 )
        {
            _loc_2 = param1.getParent ();
            _loc_3 = this.getPath(_loc_2 );
            _loc_4 = _loc_2.getChildIndex(param1 );
            _loc_2.removeChild(param1);
            this.bridge.fireTreeNodesRemoved(this, _loc_3, [_loc_4], [param1]);
            param1.setChangedHandler(null);
            this.callChangeHandler();
            return;
        }//end

        public void  refreshNode (ComModel param1 )
        {
            ComModel _loc_2 =null ;
            Array _loc_3 =null ;
            int _loc_4 =0;
            if (param1 == this.root)
            {
                this.bridge.fireTreeNodesChanged(this, this.getPath(this.root), null, null);
            }
            else
            {
                _loc_2 = param1.getParent();
                _loc_3 = this.getPath(_loc_2);
                _loc_4 = _loc_2.getChildIndex(param1);
                this.bridge.fireTreeNodesChanged(this, _loc_3, [_loc_4], [param1]);
            }
            return;
        }//end

        public void  addChangeHandler (Function param1 )
        {
            this.eventG.addListener("changeHandler", param1);
            return;
        }//end

        public void  removeChangeHandler (Function param1 )
        {
            this.eventG.removeListener("changeHandler", param1);
            return;
        }//end

        public void  addRootRangeHandler (Function param1 )
        {
            this.eventG.addListener("rootRangeHandler", param1);
            return;
        }//end

        public void  removeRootRangeHandler (Function param1 )
        {
            this.eventG.removeListener("rootRangeHandler", param1);
            return;
        }//end

        private void  fireRootRangeEvent ()
        {
            this.eventG.dispatchEvent("rootRangeHandler");
            return;
        }//end

        public void  fireChanged (String param1 ,boolean param2 )
        {
            this.awml = param1;
            this.valid = param2;
            this.callChangeHandler(false, true);
            return;
        }//end

        public void  setSaved (boolean param1 )
        {
            this.saved = param1;
            return;
        }//end

        public boolean  isSaved ()
        {
            return this.saved;
        }//end

        private void  __comChanged (ComModel param1 ,boolean param2 )
        {
            if (param2)
            {
                this.refreshNode(param1);
            }
            this.callChangeHandler();
            return;
        }//end

        private void  callChangeHandler (boolean param1 =false ,boolean param2 =false )
        {
            this.saved = param1;
            this.eventG.dispatchEvent("changeHandler", [this, param2]);
            return;
        }//end

        public static FileModel  parseXML (XML param1 ,boolean param2 =false )
        {
            _loc_3 = new FileModel(new FileModelBridge (),param2 );
            _loc_3.parse(param1);
            return _loc_3;
        }//end

        public static void  testParseXML (XML param1 )
        {
            _loc_2 = new ComModel ();
            _loc_3 = param1.children ();
            _loc_4 = _loc_3.get(0);
            _loc_5 = _loc_3.get(1);
            _loc_6 = new CSSHeader ();
            if (_loc_4.name().toString() == CSSHeader.TAG)
            {
                _loc_2.parse(_loc_5);
                _loc_6.decode(_loc_4);
            }
            else
            {
                _loc_2.parse(_loc_4);
                _loc_6.decode(_loc_5);
            }
            return;
        }//end

    }


