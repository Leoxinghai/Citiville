package com.xiyu.flash.framework.resources.reanimator;
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

    public class XMLReanimDescriptor implements ReanimDescriptor {

        private ReanimTransform  parseTransform (XmlPullParser xml ) throws Exception {
            ReanimTransform transform =new ReanimTransform ();
    		transform.tX = -999;
    		transform.tY = -999;
    		transform.kX = -999;
    		transform.kY = -999;
    		transform.sX = -999;
    		transform.sY = -999;
    		transform.frame = -999;
    		transform.alpha = -999;
			int iframe =0;
            String tag ="";
//			transform.frame = iframe;
			String value ="";
            while(true) {
                int status = xml.next();
            	if(status == xml.START_TAG) {
            		tag = xml.getName();

            		if(tag.equals("x")) {
            			value = xml.nextText();
            			if(value!= null)
            				transform.tX = (int)Double.parseDouble(value);
            			continue;
            		} else if(tag.equals("y")) {
            			value = xml.nextText();
            			if(value!= null)
            				transform.tY = (int)Double.parseDouble(value);
            			continue;
            		} else if(tag.equals("kx")) {
            			value = xml.nextText();
            			if(value!= null){
                            transform.kX = (((-2 * Math.PI) * Double.parseDouble(value)) / 360);
                			transform.okX =  Double.parseDouble(value);
            			}
            			continue;
            		} else if(tag.equals("ky")) {
            			value = xml.nextText();
            			if(value!= null) {
                            transform.kY = (((-2 * Math.PI) * Double.parseDouble(value)) / 360);
            				transform.okY =  Double.parseDouble(value);
            			}
            			continue;
            		} else if(tag.equals("sx")) {
            			value = xml.nextText();
            			if(value!= null)
            				transform.sX = Double.parseDouble(value);
            			continue;
            		} else if(tag.equals("sy")) {
            			value = xml.nextText();
            			if(value!= null)
            				transform.sY = Double.parseDouble(value);
            			continue;
            		} else if(tag.equals("f")) {
            			value = xml.nextText();
            			if(value!= null)
            				transform.frame = (int)Double.parseDouble(value);
            			continue;
            		} else if(tag.equals("a")) {
            			value = xml.nextText();
            			if(value!= null)
            				transform.alpha = (int)Double.parseDouble(value);
            			continue;
            		} else if(tag.equals("i")) {
            			value = xml.nextText();
            			transform.image = app.imageManager().getImageInst(value);
            			transform.imageID = value;
            			continue;
            		}
            	}

            	if(status == xml.END_TAG) {
            		tag = xml.getName();
            		if(tag.equals("t")) {
            			break;
            		}
            		if(tag.equals("track")) {
            			break;
            		}
            	}
            	if(status == xml.END_DOCUMENT) {
            		break;
            	}
            	status = xml.next();
            }
            //System.out.println("parse." + transform.okX + ":"+transform.sX);
            transform.fillInFrom(this.mLastTrans);
            //System.out.println("parse2." + transform.okX + ":"+transform.sX);
            return (transform);
        }

        private ReanimTransform mLastTrans ;

        private ReanimDefinition  parseDefinition (XmlPullParser xml ) throws Exception {
            ReanimTrack aTrack ;
            ReanimDefinition def =new ReanimDefinition ();
            int status = xml.next();
            String tag ="";
            String value ="";
            while(true) {
            	if(status == xml.START_TAG) {
            		tag = xml.getName();
            		if(tag.equals("fps")) {
            			value = xml.nextText();
            			if(value!= null)
            				def.fps = Integer.parseInt(value);
            			else
            				def.fps = 0;
            			break;
            		}
            	}
            	if(status == xml.END_DOCUMENT) {
            		break;
            	}
            	status = xml.next();
            }

//            int len =xml.track.length();
            int i =0;
            while (true)
            {
            	 status = xml.next();
            	 if(status == xml.START_TAG) {
            		tag = xml.getName();
            		if(tag.equals("track")) {
                    	aTrack=this.parseTrack(xml);
                        def.tracks.push(aTrack);
//--------------------------------------
                        if(aTrack.name != null ) {
//                        	sgzview.xmlStr = "4444 "+aTrack.name;
                        	def.trackNameMap.put(aTrack.name,aTrack);
                        }
                        def.numTracks++;
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

        private ReanimTrack  parseTrack (XmlPullParser xml ) throws Exception {
            ReanimTransform aTrans ;
            ReanimTrack track =new ReanimTrack ();

            int status = xml.next();
            String tag ="";
            while(true) {
            	if(status == xml.START_TAG) {
            		tag = xml.getName();
            		if(tag.equals("name")) {
            			track.name = xml.nextText();
//            			sgzview.xmlStr = "6666"+":"+track.name;
//            			sgzview.xmlStr = "7777"+track.name;
            			break;
            		}
            	}
            	if(status == xml.END_TAG) {
            		tag = xml.getName();
            		if(tag.equals("name")) {
            			break;
            		}
            	}
            	if(status == xml.END_DOCUMENT) {
            		break;
            	}
            	status = xml.next();
            }

            this.mLastTrans = null;
            int i =0;

            try {
            while (true)
            {
//            	if(i>100) {
//            		break;
//            	}

            	if(status == xml.START_TAG) {
            		tag = xml.getName();
            		if(tag.equals("t")) {
                    	aTrans=this.parseTransform(xml);
                        track.transforms.push(aTrans);
                        track.numTransforms++;
                        this.mLastTrans = aTrans;
//                      i++;
//            			break;
            		}
            	}
            	if(status == xml.END_TAG) {
            		tag = xml.getName();
            		if(tag.equals("t")) {
            			continue;
            		}
            	}
            	if(status == xml.END_TAG) {
            		tag = xml.getName();
            		if(tag.equals("track")) {
//                		sgzview.xmlStr = "end_track";
            			break;
            		}
            	}
            	if(status == xml.END_DOCUMENT) {
//            		sgzview.xmlStr = "end_document";
            		break;
            	}
            	status = xml.next();
            	i++;
            };
            } catch(Exception ex0) {
            	return track;
            }
//            sgzview.xmlStr = "3.0";
//        	sgzview.xmlStr = "5555"+track.name+":";
            for(int j =0; j< track.transforms.length();j++) {
//          	sgzview.xmlStr += ((ReanimTransform)track.transforms.elementAt(j)).tX;
//            	sgzview.xmlStr += ";";
            }
            return (track);
        }
        public ReanimDefinition  createReanimData (AppBase _arg1 ) {
        	ReanimDefinition aData = null;
        	try {
        		aData =this.parseDefinition(this.mXML );
        	} catch(Exception ex) {

        	}
            return (aData);
        }


        public  XMLReanimDescriptor (XmlPullParser _mXML ){
            this.mXML = _mXML;
        }

        public  XMLReanimDescriptor (AppBase _app, XmlPullParser _mXML ){
//        	sgzview = _sgzview;
        	app = _app;
            this.mXML = _mXML;
        }

//        private SGZView sgzview;
        private AppBase app;
    }


