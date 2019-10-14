package com.salaboy.conferences.agenda;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salaboy.conferences.agenda.model.AgendaItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Value("${version:0.0.0}")
    private String version;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Set<AgendaItem> agendaItems = new TreeSet<>(new Comparator<AgendaItem>() {
        @Override
        public int compare(AgendaItem t, AgendaItem t1) {
            return t.getTalkTime().compareTo(t1.getTalkTime());
        }
    });


    @GetMapping("/info")
    public String infoWithVersion() {
        return "Agenda v" + version;
    }

    @PostMapping()
    public String newAgendaItem(@RequestBody AgendaItem agendaItem) {
        System.out.println("> New Agenda Received: " + agendaItem);
        agendaItems.add(agendaItem);
        return "Agenda Item Added to Agenda";
    }

    @GetMapping()
    public Set<AgendaItem> getAll() {
        return agendaItems;
    }

    @GetMapping("/{id}")
    public Optional<AgendaItem> getById(@PathVariable("id") String id) {
        return agendaItems.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

}
