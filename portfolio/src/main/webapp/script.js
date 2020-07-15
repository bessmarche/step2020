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

// Functions to show paragraph content when the relevant button is clicked 

function hide() {
  var x = document.getElementById("about");
  var y = document.getElementById("skills");
  var z = document.getElementById("social");
  var i = document.getElementById("greetings");
  y.style.display = "none";
  z.style.display = "none";
  x.style.display = "none";
  i.style.display = "none";
}

function showAbout() {
  var x = document.getElementById("about");
  if (x.style.display === "none") {
    hide();
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}

function showSkill() {
  var x = document.getElementById("skills");
  if (x.style.display === "none") {
    hide();  
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}

function showSocial() {
  var x = document.getElementById("social");
  if (x.style.display === "none") {
    hide();  
    x.style.display = "block";
  } else {
    x.style.display = "none";
  }
}

// function that request the greeting from the server and adds it as a paragraph to the main page

function getGreeting() {
  var x = document.getElementById("greetings");
  if (x.style.display === "none") {
    hide();
    fetch('/data').then(response => response.text()).then((greeting) => {
    x.innerText = greeting;
    });
    x.style.display = "block";
  } else {
    x.style.display = "none"; 
  }
}





