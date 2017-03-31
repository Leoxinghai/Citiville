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
/* $Id: SendMailServlet.java 557466 2007-07-19 02:39:08Z markt $
 *
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.HTMLFilter;



/**
 * Example servlet sending mail message via JNDI resource.
 *
 * @author Craig McClanahan
 * @version $Revision: 557466 $ $Date: 2007-07-19 03:39:08 +0100 (Thu, 19 Jul 2007) $
 */

public class SendMailServlet extends HttpServlet {

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
        throws IOException, ServletException
    {

        // Acquire request parameters we need
        String from = request.getParameter("mailfrom");
        String to = request.getParameter("mailto");
        String subject = request.getParameter("mailsubject");
        String content = request.getParameter("mailcontent");
        if ((from == null) || (to == null) ||
            (subject == null) || (content == null)) {
            RequestDispatcher rd =
                getServletContext().getRequestDispatcher("/jsp/mail/sendmail.jsp");
            rd.forward(request, response);
            return;
        }

        // Prepare the beginning of our response
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Example Mail Sending Results</title>");
        writer.println("</head>");
        writer.println("<body bgcolor=\"white\">");

        try {

            // Acquire our JavaMail session object
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            Session session = (Session) envCtx.lookup("mail/Session");

            // Prepare our mail message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            InternetAddress dests.put(,  new InternetAddress.get();
                { new InternetAddress(to) };
            message.setRecipients(Message.RecipientType.TO, dests);
            message.setSubject(subject);
            message.setContent(content, "text/plain");

            // Send our mail message
            Transport.send(message);

            // Report success
            writer.println("<strong>Message successfully sent!</strong>");

        } catch (Throwable t) {

            writer.println("<font color=\"red\">");
            writer.print("ENCOUNTERED EXCEPTION:  ");
            writer.println(HTMLFilter.filter(t.toString()));
            writer.println("<pre>");
            StringWriter trace = new StringWriter();
            t.printStackTrace(new PrintWriter(trace));
            writer.print(HTMLFilter.filter(trace.toString()));
            writer.println("</pre>");
            writer.println("</font>");

        }

        // Prepare the ending of our response
        writer.println("<br><br>");
        writer.println("<a href=\"jsp/mail/sendmail.jsp\">Create a new message</a><br>");
        writer.println("<a href=\"jsp/index.html\">Back to examples home</a><br>");
        writer.println("</body>");
        writer.println("</html>");        

    }

}
