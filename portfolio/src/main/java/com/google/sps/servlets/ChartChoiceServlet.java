
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
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/chart-data")
public class ChartChoiceServlet extends HttpServlet {

  private Map<String, Integer> chartVotes = new HashMap<>();
  
  /** 
    * doGet gets the list of votes from the database and sends it to the client 
   **/
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Vote");
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
        String vote =  (String) entity.getProperty("voteValue");
        int currentVotes = chartVotes.containsKey(vote) ? chartVotes.get(vote) : 0; 
        chartVotes.put(vote, currentVotes + 1);
    }
 
    response.setContentType("application/json");
    Gson gson = new Gson();
    String json = gson.toJson(chartVotes);
    response.getWriter().println(json);
  }

  /** 
    * doPost process each vote sent by the client and adds it to the database 
   **/
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String  vote = request.getParameter("vote");
    Entity voteEntity = new Entity("Vote");
    voteEntity.setProperty("voteValue", vote);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(voteEntity);
    //int currentVotes = chartVotes.containsKey(vote) ? chartVotes.get(vote) : 0;
    //chartVotes.put(vote, currentVotes + 1);

    response.sendRedirect("/#chart");
  }
}
