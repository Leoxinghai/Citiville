package Transactions;

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

import Engine.Transactions.*;
//import flash.external.*;
//import flash.net.*;

    public class TSendRollCallFacebookMessage extends Transaction
    {
        private String m_recipient ;
        private int m_buildingId ;
        private String m_userMessage ;

        public  TSendRollCallFacebookMessage (String param1 ,int param2 ,String param3 )
        {
            this.m_recipient = param1;
            this.m_buildingId = param2;
            this.m_userMessage = param3;
            return;
        }//end  

         public void  perform ()
        {
            signedCall("UserService.createRollCallFacebookMessage", this.m_recipient, this.m_buildingId, this.m_userMessage);
            return;
        }//end  

         protected void  onComplete (Object param1 )
        {
            String url ;
            String browser ;
            String strippedUrl ;
            Object variables ;
            URLRequest request ;
            result = param1;
            boolean useNavigateToURL ;
            if (result.get("url"))
            {
                url = result.get("url");
                try
                {
                    if (ExternalInterface.available)
                    {
                        browser = ExternalInterface.call("function a() {return navigator.userAgent;}");
                        if (browser != null && browser.indexOf("Firefox") < 1)
                        {
                            useNavigateToURL;
                        }
                        else
                        {
                            ExternalInterface.call("console.log", "************** using window.open");
                            ExternalInterface.call("window.open", url, "_top", "");
                            useNavigateToURL;
                        }
                    }
                }
                catch (error:SecurityError)
                {
                    useNavigateToURL;
                }
                if (useNavigateToURL)
                {
                    strippedUrl = result.get("strippedUrl");
                    variables = result.get("urlParams");
                    request = new URLRequest(strippedUrl);
                    request.data = variables;
                    try
                    {
                        ExternalInterface.call("console.log", "*************** using navigateToURL");
                        navigateToURL(request, "_blank");
                    }
                    catch (e:Error)
                    {
                    }
                }
            }
            return;
        }//end  

    }



