 * Copyright (C) 2006 Claus Wahlers and Max Herkender
 *
 * This software is provided 'as-is', without any express or implied
 * warranty.  In no event will the authors be held liable for any damages
 * arising from the use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 * 1. The origin of this software must not be misrepresented; you must not
 *    claim that you wrote the original software. If you use this software
 *    in a product, an acknowledgment in the product documentation would be
 *    appreciated but is not required.
 * 2. Altered source versions must be plainly marked as such, and must not be
 *    misrepresented as being the original software.
 * 3. This notice may not be removed or altered from any source distribution.
 */

package deng.fzip;

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

import deng.utils.ChecksumUtil;

//import flash.utils.*;

	/**
	 * Represents a file contained in a ZIP archive.
	 */
	public class FZipFile
	{
		protected int _versionHost =0;
		protected String _versionNumber ="2.0";
		protected int _compressionMethod =8;
		protected boolean _encrypted =false ;
		protected int _implodeDictSize =-1;
		protected int _implodeShannonFanoTrees =-1;
		protected int _deflateSpeedOption =-1;
		protected boolean _hasDataDescriptor =false ;
		protected boolean _hasCompressedPatchedData =false ;
		protected Date _date ;
		protected int _adler32 ;
		protected boolean _hasAdler32 =false ;
		protected int _sizeFilename =0;
		protected int _sizeExtra =0;
		protected String _filename ="";
		protected String _filenameEncoding ;
		protected Dictionary _extraFields ;
		protected String _comment ="";
		protected ByteArray _content ;

		internal int _crc32 ;
		internal int _sizeCompressed =0;
		internal int _sizeUncompressed =0;

		protected boolean isCompressed =false ;
		protected Function parseFunc =parseFileHead ;

		// compression methods
		/**
		 * @private
		 */
		public static  int COMPRESSION_NONE =0;
		/**
		 * @private
		 */
		public static  int COMPRESSION_SHRUNK =1;
		/**
		 * @private
		 */
		public static  int COMPRESSION_REDUCED_1 =2;
		/**
		 * @private
		 */
		public static  int COMPRESSION_REDUCED_2 =3;
		/**
		 * @private
		 */
		public static  int COMPRESSION_REDUCED_3 =4;
		/**
		 * @private
		 */
		public static  int COMPRESSION_REDUCED_4 =5;
		/**
		 * @private
		 */
		public static  int COMPRESSION_IMPLODED =6;
		/**
		 * @private
		 */
		public static  int COMPRESSION_TOKENIZED =7;
		/**
		 * @private
		 */
		public static  int COMPRESSION_DEFLATED =8;
		/**
		 * @private
		 */
		public static  int COMPRESSION_DEFLATED_EXT =9;
		/**
		 * @private
		 */
		public static  int COMPRESSION_IMPLODED_PKWARE =10;

		/**
		 * @private
		 */
		protected static boolean HAS_UNCOMPRESS =(describeType(ByteArray ).factory.method.(@name == "uncompress").parameter.length() > 0);
		/**
		 * @private
		 */
		protected static boolean HAS_INFLATE =(describeType(ByteArray ).factory.method.(@name == "inflate").length() > 0);

		/**
		 * Constructor
		 */
		public  FZipFile (String filenameEncoding ="utf-8"){
			_filenameEncoding = filenameEncoding;
			_extraFields = new Dictionary();
			_content = new ByteArray();
			_content.endian = Endian.BIG_ENDIAN;
		}

		/**
		 * The Date and time the file was created.
		 */
		public Date  date (){
			return _date;
		}
		public void  date (Date value ){
			_date = (value != null) ? value : new Date();
		}

		/**
		 * The file name (including relative path).
		 */
		public String  filename (){
			return _filename;
		}
		public void  filename (String value ){
			_filename = value;
		}

		/**
		 * Whether this file has a data descriptor or not (only used internally).
		 */
		internal boolean  hasDataDescriptor (){
			return _hasDataDescriptor;
		}

		/**
		 * The raw, uncompressed file.
		 */
		public ByteArray  content (){
			if(isCompressed) {
				uncompress();
			}
			return _content;
		}
		public void  content (ByteArray data ){
			setContent(data);
		}

		/**
		 * Sets the file's content as ByteArray.
		 *
		 * @param data The new content.
		 * @param doCompress Compress the data after adding.
		 */
		public void  setContent (ByteArray data ,boolean doCompress =true ){
			if(data != null && data.length > 0) {
				data.position = 0;
				data.readBytes(_content, 0, data.length());
				_crc32 = ChecksumUtil.CRC32(_content);
				_hasAdler32 = false;
			} else {
				_content.length = 0;
				_content.position = 0;
				isCompressed = false;
			}
			if(doCompress) {
				compress();
			} else {
				_sizeUncompressed = _sizeCompressed = _content.length;
			}
		}

		/**
		 * The ZIP specification version supported by the software
		 * used to encode the file.
		 */
		public String  versionNumber (){
			return _versionNumber;
		}

		/**
		 * The size of the compressed file (in bytes).
		 */
		public int  sizeCompressed (){
			return _sizeCompressed;
		}

		/**
		 * The size of the uncompressed file (in bytes).
		 */
		public int  sizeUncompressed (){
			return _sizeUncompressed;
		}

		/**
		 * Gets the files content as string.
		 *
		 * @param recompress If <code>true</code>, the raw file content
		 * is recompressed after decoding the string.
		 *
		 * @param charset The character set used for decoding.
		 *
		 * @return The file as string.
		 */
		public String  getContentAsString (boolean recompress =true ,String charset ="utf-8"){
			if(isCompressed) {
				uncompress();
			}
			_content.position = 0;
			String str ;
			// Is readMultiByte completely trustworthy with utf-8?
			// For now, readUTFBytes will take over.
			if(charset == "utf-8") {
				str = _content.readUTFBytes(_content.bytesAvailable);
			} else {
				str = _content.readMultiByte(_content.bytesAvailable, charset);
			}
			_content.position = 0;
			if(recompress) {
				compress();
			}
			return str;
		}

		/**
		 * Sets a string as the file's content.
		 *
		 * @param value The string.
		 * @param charset The character set used for decoding.
		 * @param doCompress Compress the string after adding.
		 */
		public void  setContentAsString (String value ,String charset ="utf-8",boolean doCompress =true ){
			_content.length = 0;
			_content.position = 0;
			isCompressed = false;
			if(value != null && value.length > 0) {
				if(charset == "utf-8") {
					_content.writeUTFBytes(value);
				} else {
					_content.writeMultiByte(value, charset);
				}
				_crc32 = ChecksumUtil.CRC32(_content);
				_hasAdler32 = false;
			}
			if(doCompress) {
				compress();
			} else {
				_sizeUncompressed = _sizeCompressed = _content.length;
			}
		}

		/**
		 * Serializes this zip archive into an IDataOutput stream (such as
		 * ByteArray or FileStream) according to PKZIP APPNOTE.TXT
		 *
		 * @param stream The stream to serialize the zip archive into.
		 * @param includeAdler32 If set to true, include Adler32 checksum.
		 * @param centralDir If set to true, serialize a central directory entry
		 * @param centralDirOffset Relative offset of local header (for central directory only).
		 *
		 * @return The number of bytes written to the stream.
		 */
		public int  serialize (IDataOutput stream ,boolean includeAdler32 ,boolean centralDir =false ,int centralDirOffset =0){
			if(stream == null) { return 0; }
			if(centralDir) {
				// Write central directory file header signature
				stream.writeUnsignedInt(FZip.SIG_CENTRAL_FILE_HEADER);
				// Write "version made by" host (usually 0) and number (always 2.0)
				stream.writeShort((_versionHost << 8) | 0x14);
			} else {
				// Write local file header signature
				stream.writeUnsignedInt(FZip.SIG_LOCAL_FILE_HEADER);
			}
			// Write "version needed to extract" host (usually 0) and number (always 2.0)
			stream.writeShort((_versionHost << 8) | 0x14);
			// Write the general purpose flag
			// - no encryption
			// - normal deflate
			// - no data descriptors
			// - no compressed patched data
			// - unicode as specified in _filenameEncoding
			stream.writeShort((_filenameEncoding == "utf-8") ? 0x0800 : 0);
			// Write compression method (always deflate)
			stream.writeShort(isCompressed ? COMPRESSION_DEFLATED : COMPRESSION_NONE);
			// Write date
			Date d =(_date != null) ? _date : new Date();
			int msdosTime =uint(d.getSeconds ())| (uint(d.getMinutes()) << 5) | (uint(d.getHours()) << 11);
			int msdosDate =uint(d.getDate ())| (uint(d.getMonth() + 1) << 5) | (uint(d.getFullYear() - 1980) << 9);
			stream.writeShort(msdosTime);
			stream.writeShort(msdosDate);
			// Write CRC32
			stream.writeUnsignedInt(_crc32);
			// Write compressed size
			stream.writeUnsignedInt(_sizeCompressed);
			// Write uncompressed size
			stream.writeUnsignedInt(_sizeUncompressed);
			// Prep filename
			ByteArray ba =new ByteArray ();
			ba.endian = Endian.LITTLE_ENDIAN;
			if (_filenameEncoding == "utf-8") {
				ba.writeUTFBytes(_filename);
			} else {
				ba.writeMultiByte(_filename, _filenameEncoding);
			}
			int filenameSize =ba.position ;
			// Prep extra fields
			for(int i0 = 0; i0 < _extraFields .size(); i0++)
			{
					headerId = _extraFields .get(i0);
				if(extraBytes != null) {
					ba.writeShort(uint(headerId));
					ba.writeShort(uint(extraBytes.length()));
					ba.writeBytes(extraBytes);
				}
			}
			if (includeAdler32) {
				if (!_hasAdler32) {
					boolean compressed =isCompressed ;
					if (compressed) { uncompress(); }
					_adler32 = ChecksumUtil.Adler32(_content, 0, _content.length());
					_hasAdler32 = true;
					if (compressed) { compress(); }
				}
				ba.writeShort(0xdada);
				ba.writeShort(4);
				ba.writeUnsignedInt(_adler32);
			}
			int extrafieldsSize =ba.position -filenameSize ;
			// Prep comment (currently unused)
			if(centralDir && _comment.length > 0) {
				if (_filenameEncoding == "utf-8") {
					ba.writeUTFBytes(_comment);
				} else {
					ba.writeMultiByte(_comment, _filenameEncoding);
				}
			}
			int commentSize =ba.position -filenameSize -extrafieldsSize ;
			// Write filename and extra field sizes
			stream.writeShort(filenameSize);
			stream.writeShort(extrafieldsSize);
			if(centralDir) {
				// Write comment size
				stream.writeShort(commentSize);
				// Write disk number start (always 0)
				stream.writeShort(0);
				// Write file attributes (always 0)
				stream.writeShort(0);
				stream.writeUnsignedInt(0);
				// Write relative offset of local header
				stream.writeUnsignedInt(centralDirOffset);
			}
			// Write filename, extra field and comment
			if(filenameSize + extrafieldsSize + commentSize > 0) {
				stream.writeBytes(ba);
			}
			// Write file
			int fileSize =0;
			if(!centralDir && _content.length > 0) {
				if(isCompressed) {
					if(HAS_UNCOMPRESS || HAS_INFLATE) {
						fileSize = _content.length;
						stream.writeBytes(_content, 0, fileSize);
					} else {
						fileSize = _content.length - 6;
						stream.writeBytes(_content, 2, fileSize);
					}
				} else {
					fileSize = _content.length;
					stream.writeBytes(_content, 0, fileSize);
				}
			}
			int size =30+filenameSize +extrafieldsSize +commentSize +fileSize ;
			if(centralDir) {
				size += 16;
			}
			return size;
		}


		/**
		 * @private
		 */
		internal boolean  parse (IDataInput stream ){
			while (stream.bytesAvailable && parseFunc(stream)) {}
			return (parseFunc === parseFileIdle);
		}

		/**
		 * @private
		 */
		protected boolean  parseFileIdle (IDataInput stream ){
			return false;
		}

		/**
		 * @private
		 */
		protected boolean  parseFileHead (IDataInput stream ){
			if(stream.bytesAvailable >= 30) {
				parseHead(stream);
				if(_sizeFilename + _sizeExtra > 0) {
					parseFunc = parseFileHeadExt;
				} else {
					parseFunc = parseFileContent;
				}
				return true;
			}
			return false;
		}

		/**
		 * @private
		 */
		protected boolean  parseFileHeadExt (IDataInput stream ){
			if(stream.bytesAvailable >= _sizeFilename + _sizeExtra) {
				parseHeadExt(stream);
				parseFunc = parseFileContent;
				return true;
			}
			return false;
		}

		/**
		 * @private
		 */
		protected boolean  parseFileContent (IDataInput stream ){
			boolean continueParsing =true ;
			if(_hasDataDescriptor) {
				// If the file has a data descriptor, bail out.
				// We first need to figure out the length of the file.
				// See FZip::parseLocalfile()
				parseFunc = parseFileIdle;
				continueParsing = false;
			} else if(_sizeCompressed == 0) {
				// This entry has no file attached
				parseFunc = parseFileIdle;
			} else if(stream.bytesAvailable >= _sizeCompressed) {
				parseContent(stream);
				parseFunc = parseFileIdle;
			} else {
				continueParsing = false;
			}
			return continueParsing;
		}

		/**
		 * @private
		 */
		protected void  parseHead (IDataInput data ){
			int vSrc =data.readUnsignedShort ();
			_versionHost = vSrc >> 8;
			_versionNumber = Math.floor((vSrc & 0xff) / 10) + "." + ((vSrc & 0xff) % 10);
			int flag =data.readUnsignedShort ();
			_compressionMethod = data.readUnsignedShort();
			_encrypted = (flag & 0x01) !== 0;
			_hasDataDescriptor = (flag & 0x08) !== 0;
			_hasCompressedPatchedData = (flag & 0x20) !== 0;
			if ((flag & 800) !== 0) {
				_filenameEncoding = "utf-8";
			}
			if(_compressionMethod === COMPRESSION_IMPLODED) {
				_implodeDictSize = (flag & 0x02) !== 0 ? 8192 : 4096;
				_implodeShannonFanoTrees = (flag & 0x04) !== 0 ? 3 : 2;
			} else if(_compressionMethod === COMPRESSION_DEFLATED) {
				_deflateSpeedOption = (flag & 0x06) >> 1;
			}
			int msdosTime =data.readUnsignedShort ();
			int msdosDate =data.readUnsignedShort ();
			int sec =(msdosTime & 0x001f);
			int min =(msdosTime & 0x07e0) >> 5;
			int hour =(msdosTime & 0xf800) >> 11;
			int day =(msdosDate & 0x001f);
			int month =(msdosDate & 0x01e0) >> 5;
			int year =((msdosDate & 0xfe00) >> 9) + 1980;
			_date = new Date(year, month - 1, day, hour, min, sec, 0);
			_crc32 = data.readUnsignedInt();
			_sizeCompressed = data.readUnsignedInt();
			_sizeUncompressed = data.readUnsignedInt();
			_sizeFilename = data.readUnsignedShort();
			_sizeExtra = data.readUnsignedShort();
		}

		/**
		 * @private
		 */
		protected void  parseHeadExt (IDataInput data ){
			if (_filenameEncoding == "utf-8") {
				_filename = data.readUTFBytes(_sizeFilename);// Fixes a bug in some players
			} else {
				_filename = data.readMultiByte(_sizeFilename, _filenameEncoding);
			}
			int bytesLeft =_sizeExtra ;
			while(bytesLeft > 4) {
				int headerId =data.readUnsignedShort ();
				int dataSize =data.readUnsignedShort ();
				if(dataSize > bytesLeft) {
					throw new Error("Parse error in file " + _filename + ": Extra field data size too big.");
				}
				if(headerId === 0xdada && dataSize === 4) {
					_adler32 = data.readUnsignedInt();
					_hasAdler32 = true;
				} else if(dataSize > 0) {
					ByteArray extraBytes =new ByteArray ();
					data.readBytes(extraBytes, 0, dataSize);
					_extraFields.put(headerId,  extraBytes);
				}
				bytesLeft -= dataSize + 4;
			}
			if(bytesLeft > 0) {
				data.readBytes(new ByteArray(), 0, bytesLeft);
			}
		}

		/**
		 * @private
		 */
		internal void  parseContent (IDataInput data ){
			if(_compressionMethod === COMPRESSION_DEFLATED && !_encrypted) {
				if(HAS_UNCOMPRESS || HAS_INFLATE) {
					// Adobe Air supports inflate decompression.
					// If we got here, this is an Air application
					// and we can decompress without using the Adler32 hack
					// so we just write out the raw deflate compressed file
					data.readBytes(_content, 0, _sizeCompressed);
				} else if(_hasAdler32) {
					// Add zlib header
					// CMF (compression method and info)
					_content.writeByte(0x78);
					// FLG (compression level, preset dict, checkbits)
					int flg =(~_deflateSpeedOption << 6) & 0xc0;
					flg += 31 - (((0x78 << 8) | flg) % 31);
					_content.writeByte(flg);
					// Add raw deflate-compressed file
					data.readBytes(_content, 2, _sizeCompressed);
					// Add adler32 checksum
					_content.position = _content.length;
					_content.writeUnsignedInt(_adler32);
				} else {
					throw new Error("Adler32 checksum not found.");
				}
				isCompressed = true;
			} else if(_compressionMethod == COMPRESSION_NONE) {
				data.readBytes(_content, 0, _sizeCompressed);
				isCompressed = false;
			} else {
				throw new Error("Compression method " + _compressionMethod + " is not supported.");
			}
			_content.position = 0;
		}

		/**
		 * @private
		 */
		protected void  compress (){
			if(!isCompressed) {
				if(_content.length > 0) {
					_content.position = 0;
					_sizeUncompressed = _content.length;
					if(HAS_INFLATE) {
						_content.deflate();
						_sizeCompressed = _content.length;
					} else if(HAS_UNCOMPRESS) {
						_content.compress.apply(_content, ["deflate"]);
						_sizeCompressed = _content.length;
					} else {
						_content.compress();
						_sizeCompressed = _content.length - 6;
					}
					_content.position = 0;
					isCompressed = true;
				} else {
					_sizeCompressed = 0;
					_sizeUncompressed = 0;
				}
			}
		}

		/**
		 * @private
		 */
		protected void  uncompress (){
			if(isCompressed && _content.length > 0) {
				_content.position = 0;
				if(HAS_INFLATE) {
					_content.inflate();
				} else if(HAS_UNCOMPRESS) {
					_content.uncompress.apply(_content, ["deflate"]);
				} else {
					_content.uncompress();
				}
				_content.position = 0;
				isCompressed = false;
			}
		}

		/**
		 * Returns a string representation of the FZipFile object.
		 */
		public String  toString (){
			return "[FZipFile]"
				+ "\n  name:" + _filename
				+ "\n  date:" + _date
				+ "\n  sizeCompressed:" + _sizeCompressed
				+ "\n  sizeUncompressed:" + _sizeUncompressed
				+ "\n  versionHost:" + _versionHost
				+ "\n  versionNumber:" + _versionNumber
				+ "\n  compressionMethod:" + _compressionMethod
				+ "\n  encrypted:" + _encrypted
				+ "\n  hasDataDescriptor:" + _hasDataDescriptor
				+ "\n  hasCompressedPatchedData:" + _hasCompressedPatchedData
				+ "\n  filenameEncoding:" + _filenameEncoding
				+ "\n  crc32:" + _crc32.toString(16)
				+ "\n  adler32:" + _adler32.toString(16);
		}
	}

