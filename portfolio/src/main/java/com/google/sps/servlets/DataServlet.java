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

  // create and initiate a list containg all comments
  private List<String> comments;
  @Override
  public void init(){
    comments = new ArrayList<>();
  }  

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // get input from the form
    String text = request.getParameter( "user-comment");

    // add the comment to the comment list
    comments.add(text);

    // redirect user to the homepage
    response.sendRedirect("/");
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Convert the list to JSON
    String json = toJsonG(comments);
    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }
  
  // format a list to JSON usiing Gson
  private String toJsonG(List array) {
    Gson gson = new Gson();
    String json = gson.toJson(array);
    return json;
  }
}
