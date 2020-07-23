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


function hide() {
  var x = [ document.getElementById("about"),
            document.getElementById("mapContainer"),
            document.getElementById("social"),
            document.getElementById("form")];
  x.forEach(i => i.style.display = "none");
}

// showAbout shoieSkill showSocial getComment: functions to show paragraph content when the relevant button is clicked 
function showAbout() {
  var aboutElement = document.getElementById("about");
  if (aboutElement.style.display === "none") {
    hide();
    aboutElement.style.display = "block";
  } 
}

function showMap() {
  var mapElement = document.getElementById("mapContainer");
  if (mapElement.style.display === "none") {
    hide();  
    mapElement.style.display = "block";
  } 
}

function showSocial() {
  var formElement = document.getElementById("social");
  if (formElement.style.display === "none") {
    hide();  
    formElement.style.display = "block";
  }
}

function getComments() {
  var commElement = document.getElementById("form");
  if (commElement.style.display === "none") {
    hide();
    commElement.style.display = "block";
  } 
}

// fetchData sends a request every time the number of displayed comment is changed by the user
function fetchData(){
 var commElement = document.getElementById("comments");   
 var n_comments = document.getElementById("numberOfComments").value;
 fetch('/data?numberChoice='+n_comments).then(response => response.text()).then((commentsList)=>{
        var parsedList = JSON.parse(commentsList);
        // add bold and line break tag to each comment
        var html = "";
        parsedList.forEach(x=>{
            var id = x.propertyMap.id;
            var text = x.propertyMap.text;
            html+='<li id='+id+'>'+text+'<button class="delete" onclick="deleteComment('+id+')"></button></li>';
            });
        // add the comments to the html page    
        commElement.innerHTML = html; 
    });
}

// fetchDeleteData sends a POST request every time the delete button is clicked by the user
function fetchDeleteData(){
    fetch('/delete-data?idComment=', {method: 'POST'}).then(() => fetchData());
  }

// deleteComment send a POST request to delete a sigle comment when the user clicks the button
function deleteComment(id){
    // TODO: finish bthis function
    fetch('/delete-data?idComment='+id, {method: 'POST'}).then(() => fetchData());
}