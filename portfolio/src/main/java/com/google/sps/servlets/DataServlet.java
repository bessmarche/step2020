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

// to see sdmin page /_ah/admin

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/** Servlet that returns the comments data from the database. */
@WebServlet("/data")
public class DataServlet extends HttpServlet {

  /** 
    * doPost process each comment sent by the client and adds it to the database 
   **/
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String comment = request.getParameter( "user-comment");

    //store the comment in the database 
    if(!comment.isEmpty()){
      Entity commentEntity = new Entity("Comment");
      commentEntity.setProperty("text", comment);
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(commentEntity);
    }

    response.sendRedirect("/#form");
  }

  /** 
    * doGet gets the list of comments and comments id from the database and sends it as a list of pair (id,comment) to the client 
   **/
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    int numOfComment = Integer.parseInt(request.getParameter("numberChoice"));
    Query query = new Query("Comment");
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    ArrayList<Entity> comments = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      if (comments.size() == numOfComment) break;
      long id = entity.getKey().getId();
      String text = (String) entity.getProperty("text");
      Entity commentEntity= new Entity("Comment");

      commentEntity.setProperty("id", id);
      commentEntity.setProperty("text", text);
      comments.add(commentEntity);
    }
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