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
    aboutElement.style.display = "flex";
  } 
}

function showMap() {
  var mapElement = document.getElementById("mapContainer");
  if (mapElement.style.display === "none") {
    hide();  
    mapElement.style.display = "flex";
  } 
}

function showSocial() {
  var formElement = document.getElementById("social");
  if (formElement.style.display === "none") {
    hide();  
    formElement.style.display = "flex";
  }
}

function getComments() {
  var commElement = document.getElementById("form");
  if (commElement.style.display === "none") {
    hide();
    commElement.style.display = "flex";
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
            html+='<li id='+id+'>'+text+'<button class="delete" onclick="deleteComment('+id+')">X</button></li>';
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

// function initialising map
function createMap() {
  var bistroInfo = '<div>'+
      '<h1>Romeow Cat Bistro</h1>'+
      'Cosy little vegan Bistro in a non touristy bit of the city! <br>'+
      'Tasty food, welcoming staff and full of cute kittens! <br>'+
      '<a href="http://www.romeowcatbistrot.com/">'+
      'Romeow Cat Bistro website</a>'+
      '</div>';

  var gelatoInfo = '<div>'+
      '<h1>Gelateria del Viale</h1>'+
      'Traditional Gelato at a stone throw from the beautiful San Crisogono Basilica. <br>'+
      'The owner, Mariagrazia, is one of the sweetest person you`ll ever meet! <br>'+
      'I highly recomend the pistacchio and dark chocolate combination <br>'+
      '<a href="https://www.tripadvisor.com/Restaurant_Review-g187791-d2353669-Reviews-Gelateria_del_Viale-Rome_Lazio.html">'+
      'Check them on TripAdvisor</a>'+
      '</div>';

  var bakeryInfo ='<div>'+
      '<h1>Pasticceria Boccione</h1>'+
      'This family owned bakery is located at the hearth of the Jewish Ghetto in one of the most historical neighborood of the city. <br>'+
      'Their produces offer a unique combination of the Jewish and Roman tradition! <br>'+
      'My favourite cake is their ricotta and cherry pie (100% reccomend)<br>'+
      '<a href="https://www.tripadvisor.com/Attraction_Review-g187791-d2358692-Reviews-Pasticceria_Boccione-Rome_Lazio.html">'+
      'Check them on TripAdvisor</a>'+
      '</div>';

  var bistroWindow = new google.maps.InfoWindow({
    content: bistroInfo
  });
  var gelatoWindow = new google.maps.InfoWindow({
    content: gelatoInfo
  });
  var bakeryWindow = new google.maps.InfoWindow({
    content: bakeryInfo
  });

  const map = new google.maps.Map(
      document.getElementById('map'),
      {center: {lat: 41.888300, lng: 12.481410}, zoom: 14});
   const bistroMarker = new google.maps.Marker({
      position: {lat: 41.870510, lng: 12.481530},
      map: map,
      title: 'Romeow Cat Bistro'
    });
   const gelatoMarker = new google.maps.Marker({
      position: {lat: 41.889930, lng: 12.473980},
      map: map,
      title: 'Best Gelato in Rome'
    }); 
    const bakeryMarker = new google.maps.Marker({
      position: {lat: 41.893230, lng: 12.476930},
      map: map,
      title: 'My favourite bakery'
    }); 

    bistroMarker.addListener('click', function() {
        bistroWindow.open(map, bistroMarker);
    });
    gelatoMarker.addListener('click', function() {
        gelatoWindow.open(map, gelatoMarker);
    });
    bakeryMarker.addListener('click', function() {
        bakeryWindow.open(map, bakeryMarker);
    });
}