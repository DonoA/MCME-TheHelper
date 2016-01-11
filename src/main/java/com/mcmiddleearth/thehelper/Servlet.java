/*
 * This file is part of TheHelper.
 * 
 * TheHelper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * TheHelper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with TheHelper.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 */

package com.mcmiddleearth.thehelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;


/**
 *
 * @author Donovan
 */
public class Servlet {
    public Server server;
    private int BoundPort;
    
    private static boolean errorsOnly;
    
    public Servlet(int PortToBind, boolean err){
        server = new Server(PortToBind);
        this.BoundPort = PortToBind;
        errorsOnly = err;
        server.setHandler(new ServletHandle());
    }
    
    public void start(){
        try{
            if(server != null){
                server.start();
            }
        } catch (Exception e) {
            System.err.printf("Failed to start servlet on port: " + BoundPort + " : " + e.getMessage());
        }
    }
    
    public class ServletHandle extends AbstractHandler{
        
        
        @Override
        public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            if(!Commands.auth[0].equals(request.getRemoteAddr())){
                baseRequest.setHandled(true);
                response.sendError(403, "Forbidden - If you are a developer you must re-authenticate your ip");
                return;
            }
            String args[] = target.split("/");
            if(args.length == 2){
                if(args[1].equalsIgnoreCase(Commands.auth[1])){
                    baseRequest.setHandled(true);
                    response.setBufferSize(10485760);
                    response.setStatus(HttpServletResponse.SC_OK);
                    try {
                        Scanner s = new Scanner(new File("logs" + System.getProperty("file.separator") + "latest.log"));
                        response.getWriter().println("----- Log for Server: -----");
                        String line = "";
                        while(s.hasNextLine()){
                            line = s.nextLine();
                            if(Servlet.errorsOnly){
                                if(line.contains("ERROR")){
                                    while(!line.contains("INFO")){
                                        response.getWriter().println(line);
                                        line = s.nextLine();
                                    }
                                }
                            }else{
                                response.getWriter().println(line);
                            }
                        }
                    } catch (FileNotFoundException ex) {
                        response.sendError(404, ex.toString());
                    }
                    return;
                }else{
                    baseRequest.setHandled(true);
                    response.sendError(403, "Forbidden - Bad Password");
                }
            }
        }
    }
}
