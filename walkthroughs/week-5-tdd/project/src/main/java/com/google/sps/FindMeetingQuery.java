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

    ArrayList<String> attendees =  new ArrayList<String>(request.getAttendees());
    ArrayList<String> allAttendees =  new ArrayList<String>(request.getOptionalAttendees());
    allAttendees.addAll(attendees);
    long meetingDuration = request.getDuration();
    ArrayList<TimeRange> meetingTimes = new ArrayList<TimeRange>();

    if (allAttendees.isEmpty()){
        meetingTimes.add(TimeRange.WHOLE_DAY);
        return meetingTimes;
    }

    if (meetingDuration > TimeRange.END_OF_DAY){
        return meetingTimes;
    }

    ArrayList<TimeRange> optionalMeetingTimes = new ArrayList<TimeRange>();

    meetingTimes = findFreeTimes(events, attendees, meetingDuration);
    optionalMeetingTimes = findFreeTimes(events, allAttendees, meetingDuration);
    
    //check wether to retutn meetingTimes or optionalMeetingTimes
    if(optionalMeetingTimes.isEmpty()){
    return meetingTimes;
    } else {
    return optionalMeetingTimes;
    }
 }

 public ArrayList<TimeRange> findFreeTimes(Collection<Event> events, ArrayList<String> attendees, long meetingDuration){
    int start = TimeRange.START_OF_DAY;
    ArrayList<TimeRange> meetingTimes = new ArrayList<TimeRange>();

    for(Event event : events){
        Collection<String> eventAttendees = event.getAttendees();
        int eventStart = event.getWhen().start();
        int eventEnd = event.getWhen().end();
        TimeRange freeTime = createTime(meetingDuration, event, start, false);

        //NOTE Java Collections does not have a method for intersection hence the use of disjoint + a negation condition.
        if(!(Collections.disjoint(attendees, eventAttendees))){
          if (freeTime != null) meetingTimes.add(freeTime);
          if(start < eventEnd) start = eventEnd;
        } 
    }
    
    if (start + meetingDuration <= TimeRange.END_OF_DAY){
         meetingTimes.add(TimeRange.fromStartEnd(start, TimeRange.END_OF_DAY, true));
    }
    return meetingTimes;
 }

 public TimeRange createTime (long meetingDuration, Event event, int start, boolean includeEnd){
    int eventStart = event.getWhen().start();
    if (start + meetingDuration <= eventStart){
        return TimeRange.fromStartEnd(start, eventStart, includeEnd);
    }
    return null;
 }
}
