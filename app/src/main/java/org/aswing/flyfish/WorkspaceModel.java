package org.aswing.flyfish;

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

import org.aswing.util.*;
    public class WorkspaceModel extends Object
    {
        private String lafName ;
        private Array filePaths ;
        private String curFilePath ;

        public  WorkspaceModel ()
        {
            this.lafName = "";
            this.filePaths = new Array();
            this.curFilePath = "";
            return;
        }//end

        public boolean  addFile (String param1 )
        {
            if (ArrayUtils.indexInArray(this.filePaths, param1) < 0)
            {
                this.filePaths.push(param1);
                return true;
            }
            return false;
        }//end

        public void  removeFile (String param1 )
        {
            ArrayUtils.removeFromArray(this.filePaths, param1);
            return;
        }//end

        public void  setCurrentFile (String param1 )
        {
            this.curFilePath = param1;
            return;
        }//end

        public String  getCurrentFile ()
        {
            return this.curFilePath;
        }//end

        public boolean  setLAFName (String param1 )
        {
            if (this.lafName != param1)
            {
                this.lafName = param1;
                return true;
            }
            return false;
        }//end

        public String  getLAFName ()
        {
            return this.lafName;
        }//end

        public Array  getFiles ()
        {
            return this.filePaths.concat();
        }//end

        public String  encode ()
        {
            String _loc_3 =null ;
            XML _loc_1 =new XML("<Option></Option>");
            XML _loc_2 =new XML("<openfiles></openfiles>");
            for(int i0 = 0; i0 < this.filePaths.size(); i0++)
            {
            		_loc_3 = this.filePaths.get(i0);

                _loc_2.appendChild(new XML("<path>" + _loc_3 + "</path>"));
            }
            _loc_2.@current = this.curFilePath;
            _loc_2.@lafName = this.lafName;
            _loc_1.appendChild(_loc_2);
            return _loc_1.toXMLString();
        }//end

        public void  decode (String param1 )
        {
            XML xml ;
            XMLList ws ;
            Array paths ;
            String path ;
            str = param1;
            this.lafName = "";
            this.filePaths = new Array();
            this.curFilePath = "";
            try
            {
                xml = new XML(str);
                ws = xml.openfiles.path;
                paths = new Array();
                int _loc_3 =0;
                _loc_4 = ws;
                for(int i0 = 0; i0 < ws.size(); i0++)
                {
                		path = ws.get(i0);


                    paths.push(path);
                }
                this.filePaths = paths;
                this.filePaths.sort(this.__compareFilePath);
                this.curFilePath = xml.openfiles.get(0).@current;
                this.lafName = xml.openfiles.get(0).@lafName;
            }
            catch (er:Error)
            {
                AGLog.warn("Can not decode " + ResourceManager.WORKSPACE_FILE_NAME + " - " + er);
            }
            return;
        }//end

        private int  __compareFilePath (String param1 ,String param2 )
        {
            _loc_3 = param1.split("/");
            _loc_4 = param2.split("/");
            if (_loc_3.length > _loc_4.length())
            {
                return -1;
            }
            if (_loc_3.length < _loc_4.length())
            {
                return 1;
            }
            if (param1 < param2)
            {
                return 1;
            }
            if (param1 > param2)
            {
                return -1;
            }
            return 0;
        }//end

    }


