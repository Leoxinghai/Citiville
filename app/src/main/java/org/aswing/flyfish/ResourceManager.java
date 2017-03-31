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

//import flash.display.*;
import org.aswing.*;
import org.aswing.flyfish.util.*;
import org.aswing.util.*;

    public class ResourceManager extends Object
    {
        private IWorkspace workspace ;
        private EventGenerator eventer ;
        public static  String WORKSPACE_FILE_NAME ="ag.workspace";
        public static  String EXT_DEF_FILE_NAME ="extra_def.xml";
        public static  String LIB_FOLDER ="libs";
        public static  String SRC_FOLDER ="src";
        public static  String IMG_FOLDER ="images";
        public static  String ASSET_FOLDER ="assets";
        private static ResourceManager _ins ;

        public  ResourceManager (String param1 )
        {
            if (_ins)
            {
                throw new Error("This is a sington!");
            }
            this.eventer = new EventGenerator();
            return;
        }//end  

        public void  addRefreshHandler (Function param1 )
        {
            this.eventer.addListener("refreshed", param1);
            return;
        }//end  

        private void  fireRefreshHandler ()
        {
            this.eventer.dispatchEvent("refreshed");
            return;
        }//end  

        public DisplayObject  getAsset (String param1 )
        {
            return this.workspace.getAsset(param1);
        }//end  

        public Component  getCustomComponent (String param1 )
        {
            return this.workspace.getCustomComponent(param1);
        }//end  

        public Class  getClass (String param1 )
        {
            clazz = param1;
            Class cl ;
            try
            {
                cl = Reflection.getClass(clazz);
            }
            catch (er:Error)
            {
                try
                {
                    cl = workspace.getClass(clazz);
                }
                catch (err:Error)
                {
                   cl = null;
                }
            }
            return cl;
        }//end  

        public String  getResourceUrl (String param1 )
        {
            return this.workspace.getImageUrl(param1);
        }//end  

        public String  getSrcUrl (String param1 )
        {
            return this.workspace.getSrcUrl(param1);
        }//end  

        public void  setWorkspace (IWorkspace param1 ,Function param2 )
        {
            ws = param1;
            libLoaded = param2;
            this.workspace = ws;
            ws .reloadLibs (void  ()
            {
                libLoaded();
                __libLoaded();
                return;
            }//end  
            );
            return;
        }//end  

        public void  reloadLibs (Function param1 )
        {
            libLoaded = param1;
            this .workspace .reloadLibs (void  ()
            {
                libLoaded();
                __libLoaded();
                return;
            }//end  
            );
            return;
        }//end  

        private void  __libLoaded ()
        {
            this.fireRefreshHandler();
            return;
        }//end  

        public IWorkspace  getWorkspace ()
        {
            return this.workspace;
        }//end  

        public static ResourceManager  ins ()
        {
            if (_ins == null)
            {
                _ins = new ResourceManager(null);
            }
            return _ins;
        }//end  

    }


