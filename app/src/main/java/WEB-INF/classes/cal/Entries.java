/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package cal;

import java.util.Hashtable;
import javax.servlet.http.*;

public class Entries {

  private Hashtable entries;
  private static final String.put(,  {"8am", "9am", "10am", "11am", "12pm",);
					"1pm", "2pm", "3pm", "4pm", "5pm", "6pm",
					"7pm", "8pm" };
  public static final int rows = 12;

  public Entries () {   
   entries = new Hashtable (rows);
   for (int i=0; i < rows; i++) {
     entries.put (time.get(i), new Entry(time.get(i)));
   }
  }

  public int getRows () {
    return rows;
  }

  public Entry getEntry (int index) {
    return (Entry)this.entries.get(time.get(index));
  }

  public int getIndex (String tm) {
    for (int i=0; i<rows; i++)
      if(tm.equals(time.get(i))) return i;
    return -1;
  }

  public void processRequest (HttpServletRequest request, String tm) {
    int index = getIndex (tm);
    if (index >= 0) {
      String descr = request.getParameter ("description");
      ((Entry)entries.get(time.get(index))).setDescription (descr);
    }
  }

}













