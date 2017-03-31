package com.xiyu.flash.framework.resources.particles;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.Color;
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

import com.xiyu.flash.framework.AppBase;
import com.xiyu.util.*;

import org.xmlpull.v1.XmlPullParser;

import com.thelikes.thegot2run.*;
import com.xiyu.flash.games.pvz.PVZApp;

//import flash.utils.ByteArray;

    public class XMLParticleDescriptor implements ParticleDescriptor {

        public ParticleDefinition createData(AppBase app)
        {
        	ParticleDefinition aData = null;
        	try {
        		aData =this.parseDefinition(this.mXML );
        	} catch(Exception ex) {
        		ex.printStackTrace();
        	}
            return (aData);
        }

        private ParticleEmitterDefinition  parseParticleEmitterDefinition (XmlPullParser xml ) throws Exception {
        	ParticleEmitterDefinition theEmitterDefinition = new ParticleEmitterDefinition();
            int _loc_6;
            int _loc_7;
            int _loc_8;
        	ParticleField _loc_9;
        	ParticleField _loc_10;
            int _loc_4;
			int iframe =0;
            String tag ="";
			String value ="";

            while (true)
            {
                int status = xml.next();
            	if(status == xml.START_TAG) {
            		tag = xml.getName();

            		if(tag.equals("SystemDuration")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mSystemDuration =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("CrossFadeDuration")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mCrossFadeDuration =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("SpawnRate")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mSpawnRate =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("SpawnMinActive")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mSpawnMinActive =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("SpawnMaxActive")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mSpawnMaxActive =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("SpawnMaxLaunched")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mSpawnMaxLaunched =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("EmitterRadius")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mEmitterRadius =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("EmitterOffsetX")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mEmitterOffsetX =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("EmitterOffsetY")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mEmitterOffsetY =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("EmitterBoxX")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mEmitterBoxX =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("EmitterBoxY")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mEmitterBoxY =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("EmitterSkewX")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mEmitterSkewX =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("EmitterSkewY")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mEmitterSkewY =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("EmitterPath")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mEmitterPath =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("ParticleDuration")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mParticleDuration =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("LaunchSpeed")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mLaunchSpeed =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("LaunchAngle")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mLaunchAngle =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("SystemRed")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mSystemRed =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("SystemGreen")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mSystemGreen =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("SystemBlue")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mSystemBlue =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("SystemAlpha")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mSystemAlpha =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("SystemBrightness")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mSystemBrightness =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("ParticleRed")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mParticleRed =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("ParticleGreen")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mParticleGreen =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("ParticleBlue")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mParticleBlue =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("ParticleAlpha")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mParticleAlpha =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("ParticleBrightness")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mParticleBrightness =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("ParticleSpinAngle")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mParticleSpinAngle =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("ParticleSpinSpeed")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mParticleSpinSpeed =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("ParticleScale")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mParticleScale =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("ParticleStretch")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mParticleStretch =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("CollisionReflect")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mCollisionReflect =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("CollisionSpin")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mCollisionSpin =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("ClipTop")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mClipTop =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("ClipBottom")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mClipBottom =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("ClipLeft")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mClipLeft =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("ClipRight")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mClipRight =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("AnimationRate")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mAnimationRate =  readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("ImageRow")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mImageRow =  Integer.parseInt(value);
            			continue;
            		} else if(tag.equals("ImageCol")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mImageCol = Integer.parseInt(value);
            			continue;
            		} else if(tag.equals("ImageFrames")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mImageFrames = Integer.parseInt(value);
            			continue;
            		} else if(tag.equals("Animated")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mAnimated = Boolean.parseBoolean(value);
            			continue;
            		} else if(tag.equals("EmitterType")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mEmitterType =  ParticleEmitterType.fromString(value);
            			continue;
            		} else if(tag.equals("Image")) {
            			value = xml.nextText();
            			if(value!= null) {
            				int offset = value.indexOf("IMAGE_REANIM_");
            				if(offset>=0)
            					value = value.substring("IMAGE_REANIM_".length());
            				offset = value.indexOf("IMAGE_");
            				if(offset>=0)
            					value = value.substring("IMAGE_".length());
            				theEmitterDefinition.mImage =  app.imageManager().getImageInst(value);
            			}
            			theEmitterDefinition.mImageID = value;
            			continue;
            		} else if(tag.equals("Name")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mName =  value;
            			continue;
            		} else if(tag.equals("ParticleFlags")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mParticleFlags =  new ParticleFlags(value);
            			continue;
            		} else if(tag.equals("Field")) {
            				if(theEmitterDefinition.mParticleFields==null) {
            					theEmitterDefinition.mParticleFields = new Array();
            				}
            				theEmitterDefinition.mParticleFields.push(parseField(xml));
            			continue;
            		} else if(tag.equals("SystemFields")) {
            			value = xml.nextText();
            			if(value!= null)
            				theEmitterDefinition.mSystemFields =  new Array(value);
            			continue;
            		}
            	}

            	if(status == xml.END_TAG) {
            		tag = xml.getName();
            		if(tag.equals("Emitter")) {
            			break;
            		}
            		if(tag.equals("settings")) {
            			break;
            		}
            	}
            	if(status == xml.END_DOCUMENT) {
            		break;
            	}
            	status = xml.next();
            }

            //System.out.println("parse." + transform.okX + ":"+transform.sX);
            //transform.fillInFrom(this.mLastTrans);
            //System.out.println("parse2." + transform.okX + ":"+transform.sX);
            theEmitterDefinition.setDefault();
            return (theEmitterDefinition);
        }

        private ParticleDefinition  parseDefinition (XmlPullParser xml ) throws Exception {
        	ParticleEmitterDefinition theEmitter ;
            ParticleDefinition def = new ParticleDefinition ();
            int status = xml.next();
            String tag ="";
            String value ="";


            int i =0;
            while (true)
            {
            	 status = xml.next();
            	 if(status == xml.START_TAG) {
            		tag = xml.getName();
            		if(tag.equals("Emitter")) {
            			theEmitter=this.parseParticleEmitterDefinition(xml);
                        def.mEmitterDefs.push(theEmitter);
                        i++;
            		}
            	}
            	if(status == xml.END_TAG) {
            		tag = xml.getName();
            		if(tag.equals("settings")) {
            			break;
            		}
            	}

            	if(status == xml.END_DOCUMENT) {
            		break;
            	}

            };
//            def.numTracks = i;

            return (def);
        }

        private XmlPullParser mXML ;

        private ParticleField  parseField (XmlPullParser xml ) throws Exception {
            ParticleField field =new ParticleField();
            String tag ="";
            String value ="";
            while(true) {
                int status = xml.next();
            	if(status == xml.START_TAG) {
            		tag = xml.getName();
            		if(tag.equals("FieldType")) {
            			value = xml.nextText();
            			if(value!= null)
            				field.mFieldType = ParticleFieldType.fromString(value);
            			continue;
            		} else if(tag.equals("X")) {
            			value = xml.nextText();
            			if(value!= null)
            				field.mX = readFloatParameterTrack(value);
            			continue;
            		} else if(tag.equals("Y")) {
            			value = xml.nextText();
            			if(value!= null)
            				field.mY = readFloatParameterTrack(value);
            			continue;
            		}
            	}

            	if(status == xml.END_TAG) {
            		tag = xml.getName();
            		if(tag.equals("Field")) {
            			break;
            		}
            	}
            	if(status == xml.END_DOCUMENT) {
            		break;
            	}
            }

            return field;
        }

        private FloatParameterTrack readFloatParameterTrack(String params)
        {
        	FloatParameterTrack theParameterTrack = new FloatParameterTrack();
        	FloatParameterTrackNode node;
            //var _loc_3 = param1.readInt();
        	int i =0;
        	char p;
        	int pLen = params.length();
        	node = new FloatParameterTrackNode();
        	String token;
            while (i<pLen)
            {
            	node.mCurveType = CurveType.fromUInt(1);
            	node.mDistribution = CurveType.fromUInt(0);
            	p = params.charAt(i);
            	node.mTime = 0;
            	while(i<pLen || p == ' ') {
	            	if(p == '[') {
	            		i++;
	            		token = getToken(params,i);
	            		node.mLowValue = Float.parseFloat(token);
	            		i= i+token.length();
	            		i++;

	            		token = getToken(params,i);
	            		if(token.equals(""))
	            			node.mHighValue = node.mLowValue;
	            		else
	            			node.mHighValue = Float.parseFloat(token);
	            		i= i+token.length();
	            		i++;

	            		node.mDistribution = CurveType.fromUInt(1);
	            		if(i<pLen)
	            			p=params.charAt(i);
	            	} else if (p == ']') {
	            		i++;
	            		if(i<pLen)
	            			p=params.charAt(i);
	            	} else if (p == ' ') {
	            		i++;
	            		break;
	            	} else if (p == ',') {
	            		i++;
	            		token = getToken(params,i);
	            		node.mTime = Float.parseFloat(token)/100;
	            		i= i+token.length();
	            		if(i<pLen)
	            			p=params.charAt(i);
	            	} else {
	            		token = getToken(params,i);
	            		if(token.equals("EaseOut")) {
	            			node.mDistribution = CurveType.EASE_OUT;
	            		} else if(token.equals("EaseIn")) {
		            			node.mDistribution = CurveType.EASE_IN;
	            		} else if(token.equals("EaseInOutWeak")) {
	            			node.mDistribution = CurveType.EASE_IN_OUT_WEAK;
	            		} else if(token.equals("EaseInOut")) {
	            			node.mDistribution = CurveType.EASE_IN_OUT;
	            		} else if(token.equals("Bounce")) {
	            			node.mDistribution = CurveType.BOUNCE;
	            		} else if(token.equals("EaseSinWave")) {
	            			node.mDistribution = CurveType.EASE_SIN_WAVE;
	            		} else if(token.equals("FaseInOutWeak")) {
	            			node.mDistribution = CurveType.FAST_IN_OUT_WEAK;
	            		} else if(token.equals("FaseInOut")) {
	            			node.mDistribution = CurveType.FAST_IN_OUT;
	            		} else {
		            		node.mLowValue = Float.parseFloat(token);
		            		node.mHighValue = Float.parseFloat(token);
	            		}
	            		i= i+token.length();
	            		if(i<pLen)
	            			p=params.charAt(i);
	            	}
                }
                theParameterTrack.mNodes.push(node);
        		node = new FloatParameterTrackNode();
            }

            return theParameterTrack;
        }// end function

        private String getToken(String value,int offset) {
        	char p;
        	String token = "";
        	for(int i =offset;i<value.length();i++) {
        		p = value.charAt(i);
        		if(p!=',' && p!=' '&& p!='.get('&& p!=')') {
        			token +=p;
        		} else {
        			break;
        		}
        	}
        	return token;
        }

        public  XMLParticleDescriptor (XmlPullParser _mXML ){
            this.mXML = _mXML;
        }

        public  XMLParticleDescriptor (AppBase _app, XmlPullParser _mXML ){
//        	sgzview = _sgzview;
        	app = _app;
            this.mXML = _mXML;
        }

//        private SGZView sgzview;
        private AppBase app;

        public static void main(String argv[]) {
        	XMLParticleDescriptor desc = new XMLParticleDescriptor(null,null);
        	String params = "[-200 200]";
        	FloatParameterTrack test = desc.readFloatParameterTrack(params);
        	System.out.println(test.mNodes.length());
        }
    }


