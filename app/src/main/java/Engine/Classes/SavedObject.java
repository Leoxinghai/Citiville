package Engine.Classes;

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

import Engine.*;
import Engine.Managers.*;
//import flash.utils.*;

    public class SavedObject
    {
        protected double m_id =0;
        protected double m_tempId =-1;
        protected int m_transactionCount =0;
        protected boolean m_deleted =false ;

        public  SavedObject ()
        {
            return;
        }//end

        public double  getId ()
        {
            return this.m_id;
        }//end

        public double  getTempId ()
        {
            return this.m_tempId;
        }//end

        public void  generateTempId ()
        {
            this.m_tempId = Utilities.generateUniqueId();
            return;
        }//end

        public boolean  hasValidId ()
        {
            boolean _loc_1 =true ;
            if (this.m_id == 0)
            {
                StatsManager.count("errors", "has_valid_id_is_zero");
                _loc_1 = false;
            }
            return _loc_1;
        }//end

        public void  setId (double param1 )
        {
            this.m_id = param1;
            return;
        }//end

        public void  setTempId (double param1 )
        {
            this.m_tempId = param1;
            return;
        }//end

        public boolean  hasId ()
        {
            return this.m_id != Constants.INDEX_NONE;
        }//end

        public String  getClassName ()
        {
            _loc_1 = getQualifiedClassName(this).split("::");
            return _loc_1.get((_loc_1.length - 1));
        }//end

        public void  markForDeletion (boolean param1 )
        {
            if (param1 !=null)
            {
                this.m_deleted = true;
            }
            else
            {
                this.m_deleted = false;
            }
            return;
        }//end

        public void  incrementTransactions ()
        {
            this.m_transactionCount++;
            return;
        }//end

        public void  decrementTransactions ()
        {
            this.m_transactionCount--;
            return;
        }//end

        public void  clearTransactions ()
        {
            this.m_transactionCount = 0;
            return;
        }//end

        public boolean  needsToBeSaved ()
        {
            return this.m_transactionCount != 0 || this.m_deleted;
        }//end

        public Object  getSaveObject ()
        {
            Object _loc_1 =new Object ();
            _loc_1.deleted = this.m_deleted;
            _loc_1.id = this.m_id;
            _loc_1.tempId = this.m_tempId;
            _loc_1.className = this.getClassName();
            return _loc_1;
        }//end

        public void  loadObject (Object param1 )
        {
            if (param1 != null)
            {
                this.m_deleted = param1.deleted;
                this.m_id = param1.id;
                this.m_tempId = param1.tempId;
            }
            return;
        }//end

        public static SavedObject  getInstance (String param1 ,String param2 )
        {
            Class SavedObjectClassType ;
            SavedObject instance ;
            className = param1;
            itemName = param2;
            try
            {
                SavedObjectClassType =(Class) getDefinitionByName("Classes." + className);
                if (itemName != null)
                {
                    instance = new SavedObjectClassType(itemName);
                }
            }
            catch (e:ReferenceError)
            {
                ErrorManager.addError("Type instanceof not found " + className, ErrorManager.ERROR_REMOTEOBJECT_FAULT);
            }
            return instance;
        }//end


    }



