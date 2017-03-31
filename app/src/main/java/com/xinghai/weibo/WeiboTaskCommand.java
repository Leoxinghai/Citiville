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
import Transactions.*;

import com.xinghai.Debug;

    public class WeiboTaskCommand implements IWeiboCommand
    {

        public void  execute (Object param1 )
        {
		 Array actionssay =(Array)param1.say;
		 int jj =0;
		 for(;jj<actionssay.length;jj++) {
	              action = actionssay.get(jj);
	              ByteArray byteSay =Base64.decode(action.say );
	              String saying =byteSay.readMultiByte(byteSay.length ,"GBK");
		      Global.ui.chatpanel.addSaying(saying);
		      Global.weiboManager.weiboseq = action.seq;
		 }

                 Global.ui.chatpanel.resetSlider();

		 Array actionsgo =(Array)param1.go;
		 NPC gonpc ;
		 jj = 0;
		 for(;jj<actionsgo.length;jj++) {
		      action2 = actionsgo.get(jj);
		      if(action2.a == "go") {
		          gonpc = Global.weiboManager.m_walkers.get(action2.uid);
		          if(gonpc == null) continue;

			  gonpc.getStateMachine().addActions(new ActionNavigateBeeline(gonpc, new Vector3(action2.x,action2.y,0)) );
			  Global.weiboManager.weiboactionseq = action2.seq;
		      } else if(action2.a == "train")  {
			     Global.world.citySim.trainManager.purchaseWelcomeTrain();
			     Global.weiboManager.weiboactionseq = action2.seq;
		      } else if(action2.a == "scan")  {
			      if(action2.uid == Config.userid) {
				    Global.weiboManager.me.getStateMachine().addActions( new ActionPlayAnimation(Global.weiboManager.me, "scanning", 0.7));
			      } else {
				    gonpc.getStateMachine().addActions( new ActionPlayAnimation(gonpc, "scanning", 2));
		              }
		              Global.weiboManager.weiboactionseq = action2.seq;
		      } else if(action2.a == "logout")  {
		              gonpc = Global.weiboManager.m_walkers.get(action2.uid);
		              if(gonpc == null) continue;
			      gonpc.getStateMachine().addActions( new ActionDie(gonpc));
		              Global.weiboManager.m_walkers.put(action2.uid,  null);
		              Global.weiboManager.weiboactionseq = action2.seq;

		      } else if(action2.a == "login")  {
		             gonpc = Global.weiboManager.m_walkers.get(action2.uid);
		             if(gonpc != null) continue;

	    		     WeiboPeep npc ;
			     npc = new WeiboPeep(action2.npcname, false);
			     npc.setPosition(action2.x,action2.y);
			     npc.setOuter(Global.world);
			     npc.attach();
			     npc.getStateMachine().removeAllStates();
			     npc.getStateMachine().addActions(new ActionNavigateBeeline(npc, new Vector3(action2.x,action2.y,0)));
	    		     Global.weiboManager.m_walkers.put(action2.uid,  npc);

		             Global.weiboManager.weiboactionseq = action2.seq;
		      } else if(action2.a == "topup")  {
			      if(action2.uid == Config.userid) {
			           GameTransactionManager.addTransaction(new TUpdateEnergy());
				   //Global.player.gold = Global.player.gold + action2.x;
			      }
		              Global.weiboManager.weiboactionseq = action2.seq;
	              }

		 }


        }

    }


