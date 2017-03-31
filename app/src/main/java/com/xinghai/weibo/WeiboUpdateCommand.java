package com.xinghai.weibo;

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

import com.adobe.crypto.*;
import com.adobe.serialization.json.*;
//import flash.events.*;
//import flash.net.*;
//import flash.utils.*;

import Classes.*;
import Classes.sim.*;
import Classes.Desires.*;
import Classes.actions.*;
import Classes.effects.*;
import Classes.util.*;
import Engine.Helpers.*;
import by.blooddy.crypto.*;



    public class WeiboUpdateCommand implements IWeiboCommand
    {

        public void  execute (Object param1 )
        {
		 Array actions =(Array)param1.a;
		 int jj =0;
		 for(;jj<actions.length;jj++) {
			  action = actions.get(jj);
			  if(action.a == "say") {
			           ByteArray byteSay =Base64.decode(action.say );
			           action.say = byteSay.readMultiByte(byteSay.length,"GBK");
				   Global.ui.chatpanel.addSaying(action.say);

				   Global.weiboManager.weiboseq = action.seq;
			  } else if(action.a == "go") {
				   NPC gonpc =Global.weiboManager.m_walkers.get(action.uid) ;
				   if(gonpc == null) continue;
				   gonpc.getStateMachine().addActions(new ActionNavigateBeeline(gonpc, new Vector3(action.x,action.y,0)) );
				   Global.weiboManager.weiboactionseq = action.seq;
			 }
		  }

        }

    }



