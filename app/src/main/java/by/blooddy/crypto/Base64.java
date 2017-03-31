package by.blooddy.crypto;

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

//import flash.utils.ByteArray;

        public class Base64 {

                private static  String BASE64_CHARS ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";

                public static  String version ="1.0.0";


                public static String  encode (ByteArray data ,boolean param2 =false ){
                        // Initialise output
                        String output ="";

                        // Create data and output buffers
                        Array dataBuffer ;
                        Array outputBuffer =new Array(4);

                        // Rewind ByteArray
                        data.position = 0;

                        // while there are still bytes to be processed
                        while (data.bytesAvailable > 0) {
                                // Create new data buffer and populate next 3 bytes from data
                                dataBuffer = new Array();
                                for(int i =0;i <3&& data.bytesAvailable > 0; i++) {
                                        dataBuffer.put(i,  data.readUnsignedByte());
                                }

                                // Convert to data buffer Base64 character positions and
                                // store in output buffer
                                outputBuffer.put(0,  (dataBuffer.get(0) & 0xfc) >> 2);
                                outputBuffer.put(1,  ((dataBuffer.get(0) & 0x03) << 4) | ((dataBuffer.get(1)) >> 4));
                                outputBuffer.put(2,  ((dataBuffer.get(1) & 0x0f) << 2) | ((dataBuffer.get(2)) >> 6));
                                outputBuffer.put(3,  dataBuffer.get(2) & 0x3f);

                                // If data buffer was short (i.e not 3 characters) then set
                                // end character indexes in data buffer to index of '=' symbol.
                                // This is necessary because Base64 data is always a multiple of
                                // 4 bytes and is basses with '=' symbols.
                                for(int j =dataBuffer.length ;j <3;j ++){
                                        outputBuffer.put(j + 1,  64);
                                }

                                // Loop through output buffer and add Base64 characters to
                                // encoded data string for each character.
                                for(int k =0;k <outputBuffer.length ;k ++){
                                        output += BASE64_CHARS.charAt(outputBuffer.get(k));
                                }
                        }

                        // Return encoded data
                        return output;
                }


                public static ByteArray  decode (String data ){
                        // Initialise output ByteArray for decoded data
                        ByteArray output =new ByteArray ();

                        // Create data and output buffers
                        Array dataBuffer =new Array(4);
                        Array outputBuffer =new Array(3);

                        // While there are data bytes left to be processed
                        for(int i =0;i <data.length ;i +=4){
                                // Populate data buffer with position of Base64 characters for
                                // next 4 bytes from encoded data
                                for(int j =0;j <4&& i + j < data.length; j++) {
                                        dataBuffer.put(j,  BASE64_CHARS.indexOf(data.charAt(i + j)));
                                }

                        // Decode data buffer back into bytes
                                outputBuffer.put(0,  (dataBuffer.get(0) << 2) + ((dataBuffer.get(1) & 0x30) >> 4));
                                outputBuffer.put(1,  ((dataBuffer.get(1) & 0x0f) << 4) + ((dataBuffer.get(2) & 0x3c) >> 2);               );
                                outputBuffer.put(2,  ((dataBuffer.get(2) & 0x03) << 6) + dataBuffer.get(3));

                                // Add all non-padded bytes in output buffer to decoded data
                                for(int k =0;k <outputBuffer.length ;k ++){
                                        if (dataBuffer.get(k+1) == 64) break;
                                        output.writeByte(outputBuffer.get(k));
                                }
                        }

                        // Rewind decoded data ByteArray
                        output.position = 0;

                        // Return decoded data
                        return output;
                }

                public static ByteArray  decodeToByteArrayB (String data ){
                        // Initialise output ByteArray for decoded data
                        ByteArray output =new ByteArray ();

                        // Create data and output buffers
                        Array dataBuffer =new Array(4);
                        Array outputBuffer =new Array(3);

                        // While there are data bytes left to be processed
                        for(int i =0;i <data.length ;i +=4){
                                // Populate data buffer with position of Base64 characters for
                                // next 4 bytes from encoded data and throw away the non-encoded characters.
                                for(int j =0;j <4&& i + j < data.length; j++) {
                                        dataBuffer.put(j,  BASE64_CHARS.indexOf(data.charAt(i + j)));
                                        while((dataBuffer.get(j) < 0) && (i < data.length())) {
                                                i++;
                                                dataBuffer.put(j,  BASE64_CHARS.indexOf(data.charAt(i + j)));
                                        }
                                }

                        // Decode data buffer back into bytes
                                outputBuffer.put(0,  (dataBuffer.get(0) << 2) + ((dataBuffer.get(1) & 0x30) >> 4));
                                outputBuffer.put(1,  ((dataBuffer.get(1) & 0x0f) << 4) + ((dataBuffer.get(2) & 0x3c) >> 2));
                                outputBuffer.put(2,  ((dataBuffer.get(2) & 0x03) << 6) + dataBuffer.get(3));

                                // Add all non-padded bytes in output buffer to decoded data
                                for(int k =0;k <outputBuffer.length ;k ++){
                                        if (dataBuffer.get(k+1) == 64) break;
                                        output.writeByte(outputBuffer.get(k));
                                }



                        }

                        // Rewind decoded data ByteArray
                        output.position = 0;

                        // Return decoded data
                        return output;
                }


                public  Base64 (){
                        throw new Error("Base64 class is static container only");
                }
        }


