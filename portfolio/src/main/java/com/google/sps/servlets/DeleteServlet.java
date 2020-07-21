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

/** Servlet to delete the comments data from the database. */
@WebServlet("/delete-data")
public class DeleteServlet extends HttpServlet {
  /** 
    * doPost process each comment sent by the client and adds it to the database 
   **/
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    //iterate trough the entities in the database and delete them
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("Comment");
    for (Entity entity : datastore.prepare(query).asIterable()) {
        datastore.delete(entity.getKey());
    }

    response.sendRedirect("/");
  }

}