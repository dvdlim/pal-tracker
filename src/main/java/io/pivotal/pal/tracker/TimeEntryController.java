package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {
    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @Autowired
    TimeEntryRepository timeEntryRepository=new InMemoryTimeEntryRepository();

    @PostMapping("/time-entries")
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry=timeEntryRepository.create(timeEntryToCreate);
        return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.CREATED);
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") long l) {
        TimeEntry timeEntry= timeEntryRepository.find(l);
        if (timeEntry != null) {
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
        }
        else
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list(){
        List<TimeEntry> timeEntryList= timeEntryRepository.list();
        return new ResponseEntity<List<TimeEntry>>(timeEntryList, HttpStatus.OK);
    }

    @PutMapping("/time-entries/{id}")
    public @ResponseBody ResponseEntity<TimeEntry> update(@PathVariable("id") long l, @RequestBody TimeEntry expected) {
        TimeEntry timeEntry=timeEntryRepository.update(l,expected);
        if (timeEntry != null) {
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.OK);
        }
        else
            return new ResponseEntity<TimeEntry>(timeEntry, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") long l) {
        timeEntryRepository.delete(l);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
