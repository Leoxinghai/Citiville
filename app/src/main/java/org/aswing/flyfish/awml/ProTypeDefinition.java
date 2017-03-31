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

import org.aswing.util.*;
    public class ProTypeDefinition extends Object
    {
        private String name ;
        private Class editorClass ;
        private Class serializerClass ;

        public  ProTypeDefinition (XML param1 )
        {
            xml = param1;
            this.name = xml.@name;
            try
            {
                this.editorClass = Reflection.getClass(xml.@editor);
            }
            catch (er:Error)
            {
            }
            this.serializerClass = Reflection.getClass(xml.@serializer);
            return;
        }//end  

        public String  getName ()
        {
            return this.name;
        }//end  

        public Class  getEditorClass ()
        {
            return this.editorClass;
        }//end  

        public Class  getSerializerClass ()
        {
            return this.serializerClass;
        }//end  

    }


