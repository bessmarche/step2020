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

package com.google.sps;

import java.io.*; 
import java.util.Collection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;


public final class FindMeetingQuery {
  /*
   ** query IN: collection of events  and details of meeting request. OUT:available times for the meeting
   */
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
      Collection<String> attendees = request.getAttendees();
      long meetingDuration = request.getDuration();
      ArrayList<TimeRange> meetingTimes = new ArrayList<TimeRange>();
      int start = TimeRange.START_OF_DAY;
      int end = TimeRange.END_OF_DAY;
    
    if (attendees.isEmpty()){
        meetingTimes.add(TimeRange.WHOLE_DAY);
        return meetingTimes;
    }

    if (meetingDuration > TimeRange.END_OF_DAY){
        return meetingTimes;
    }

    for(Event event : events){
        //check the event has participants that are in the attendees
        Collection<String> eventAttendees = event.getAttendees();
        if(!(Collections.disjoint(attendees, eventAttendees))){
          int eventStart = event.getWhen().start();
          int eventEnd = event.getWhen().end();
          //add time before thestart of this event
          if (start + meetingDuration <= eventStart){
             meetingTimes.add(TimeRange.fromStartEnd(start, eventStart, false));
          }
          if(start < eventEnd)
                start =  eventEnd;
        }
    }

    //add time between last event and  end of the day
    if (start + meetingDuration <= TimeRange.END_OF_DAY){
             meetingTimes.add(TimeRange.fromStartEnd(start, TimeRange.END_OF_DAY, true));
          }
    return meetingTimes;
  }
}
