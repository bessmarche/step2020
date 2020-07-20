// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  private List<String> comments;

  @Override
  public void init(){
    comments = new ArrayList<>();
  } 

  /** 
    * doPost process each comment sent by the client and adds it to the database 
   **/
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String comment = request.getParameter( "user-comment");
    
    //store the comment in the database 
    Entity commentEntity = new Entity("Comment");
    commentEntity.setProperty("text", comment);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(commentEntity);
    
    comments.add(comment);
    response.sendRedirect("/");
  }

  /** 
    * doGet sends the list of comments form the client 
   **/
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String json = toJsonG(comments);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }
  
  // toJsonG formats a list to JSON using Gson
  private String toJsonG(List array) {
    Gson gson = new Gson();
    String json = gson.toJson(array);
    return json;
  }
}
