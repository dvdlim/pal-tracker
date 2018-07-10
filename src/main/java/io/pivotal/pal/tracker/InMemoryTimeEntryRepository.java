package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    Map<Long,TimeEntry> timeEntryMap= new HashMap<Long, TimeEntry>();
    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(timeEntryMap.size()+1);
        timeEntryMap.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }


    public TimeEntry find(long id) {
        TimeEntry timeEntry=timeEntryMap.get(id);
        return timeEntry;
    }

    public List list() {
        List timeEntryList= new ArrayList(timeEntryMap.values());
        return timeEntryList;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        timeEntry.setId(id);
        timeEntryMap.put(id,timeEntry);
        return timeEntry;
    }

    public void delete(long id) {
        timeEntryMap.remove(id);
    }
}
