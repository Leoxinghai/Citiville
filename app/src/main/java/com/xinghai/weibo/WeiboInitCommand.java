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

import com.xinghai.Debug;


    public class WeiboInitCommand implements IWeiboCommand
    {

        public void  execute (Object param1 )
        {
	    	     Array users =(Array)param1.u;
	    	     int ii ;

	    	     for(;ii<users.length;ii++) {
	    		     user = users.get(ii);
	    		     WeiboPeep npc ;
			     npc = new WeiboPeep(user.npcname, false);
			     npc.setPosition(user.x,user.y);
			     npc.setOuter(Global.world);
			     npc.attach();
			     npc.getStateMachine().removeAllStates();
			     npc.getStateMachine().addActions(new ActionNavigateBeeline(npc, new Vector3(user.x,user.y,0)));

			     ByteArray byteSay =Base64.decode(user.alias );
			     user.alias = byteSay.readMultiByte(byteSay.length,"GBK");
			     npc.alias = user.alias;

	    		     if(user.uid == Config.userid) {
	    			      Global.weiboManager.me = npc;
	    			      Config.alias = user.alias;
	    		     } else {
	    			      Global.weiboManager.m_walkers.put(user.uid,  npc);
	    		     }

	    		     Global.weiboManager.levelUpgrading = false;
	    	       }

	    	     Array pigs =(Array)param1.p;
	    	     ii = 0;

	    	     for(;ii<pigs.length;ii++) {
	    		     pig = pigs.get(ii);

				WeiboPeep enermy ;
				enermy = new WeiboPeep(pig.npcname,false);
				enermy.setPosition(pig.x,pig.y);
				enermy.setP1(pig.p1);
				enermy.setP2(pig.p2);
				enermy.setOuter(Global.world);
				enermy.attach();
				enermy.getStateMachine().removeAllStates();
				enermy.getStateMachine().addActions(new ActionNavigateRandom(enermy));
				Global.weiboManager.m_enermies.push(enermy);

	    	       }


        }

    }



