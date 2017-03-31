package Classes.ClientDataObjects;

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
import Classes.sim.*;

    public class NPCVisitBatchObject
    {
        protected int m_count ;
        protected String m_operation ="process";
        protected Object m_npcBreakDown ;
        protected String m_destType ;
        protected String m_mechanicType ;

        public  NPCVisitBatchObject (String param1 ,String param2 ,String param3 ,int param4 =1,NPC param5 =null ,String param6 ="process")
        {
            this.m_npcBreakDown = {};
            this.m_count = param4 * NPCUtil.getNPCValue(param2, param3, param5);
            this.m_operation = param6;
            this.m_npcBreakDown.put(param2,  param4);
            this.m_destType = param3;
            this.m_mechanicType = param1;
            return;
        }//end

        public Object  exportTransactionParamsObject ()
        {
            return {count:this.m_count, operation:this.m_operation, npcBreakDown:this.m_npcBreakDown, type:this.m_mechanicType};
        }//end

        public void  addNPCVisit (String param1 ,int param2 =1,NPC param3 =null )
        {
            this.m_count = this.m_count + param2 * NPCUtil.getNPCValue(param1, this.m_destType, param3);
            if (!this.m_npcBreakDown.get(param1))
            {
                this.m_npcBreakDown.put(param1,  0);
            }
            this.m_npcBreakDown.put(param1,  this.m_npcBreakDown.get(param1) + param2);
            return;
        }//end

        public int  count ()
        {
            return this.m_count;
        }//end

        public String  mechanicType ()
        {
            return this.m_mechanicType;
        }//end

    }



