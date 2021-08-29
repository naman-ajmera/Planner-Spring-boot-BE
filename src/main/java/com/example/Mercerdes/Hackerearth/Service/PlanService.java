package com.example.Mercerdes.Hackerearth.Service;

import com.example.Mercerdes.Hackerearth.Model.DatabaseSequence;
import com.example.Mercerdes.Hackerearth.Model.Plan;
import com.example.Mercerdes.Hackerearth.Repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class PlanService {

    public static final String SEQUENCE_NAME = "users_sequence";

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private PlanRepository planRepository;

    public void saveWithFrequency(Plan plan, LocalDateTime startDateTime ,LocalDateTime endDateTime, String frequency){
        int frequencyToUpdate = 1;
        //Daily // Monthly
        if(frequency.equals("Weekly")){
            frequencyToUpdate = 7;
        }

        long id = generateSequence(SEQUENCE_NAME);

        endDateTime = endDateTime.plusMonths(2);
        plan.setStart_time(startDateTime.toString());
        plan.setEnd_time(endDateTime.toString());
        plan.setId(id);
        plan.setParentId(id);
        planRepository.save(plan);

        if(frequency.equals("Daily") || frequency.equals("Weekly")) {
            startDateTime = startDateTime.plusDays(frequencyToUpdate);
            while (startDateTime.compareTo(endDateTime) < 0) {
                plan.setId(generateSequence(SEQUENCE_NAME));
                plan.setStart_time(startDateTime.toString());
                plan.setParentId(id);
                planRepository.save(plan);
                startDateTime = startDateTime.plusDays(frequencyToUpdate);
            }
        }
        else{
            startDateTime = startDateTime.plusMonths(frequencyToUpdate);
            while (startDateTime.compareTo(endDateTime) < 0) {
                plan.setId(generateSequence(SEQUENCE_NAME));
                plan.setStart_time(startDateTime.toString());
                plan.setParentId(id);
                planRepository.save(plan);
                startDateTime = startDateTime.plusMonths(frequencyToUpdate);
            }
        }
    };

    public Plan savePlan(Plan plan){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(plan.getStart_time(), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(plan.getEnd_time(), formatter);
        if(plan.getFrequency().equals("None")){
            plan.setStart_time(startDateTime.toString());
            plan.setEnd_time(endDateTime.toString());
            plan.setId(generateSequence(SEQUENCE_NAME));
            plan.setParentId(0);
            planRepository.save(plan);
        }
        //Monthly Weekly Daily
        else{
            saveWithFrequency(plan, startDateTime, endDateTime, plan.getFrequency());
        }
        return plan;
    }

    public List<Plan> getAllPlans(){
        return planRepository.findAll();
    }

    public Plan deletePlanById(int id){
        Plan plan = planRepository.findById(id).get();
        planRepository.deleteById(id);
        return plan;
    }

    public long generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

    public List<Plan> getAllPlansForToday(){
        LocalDate date = LocalDate.now();
        List<Plan> listOfPlansForToday = new ArrayList<>();
        for(Plan plan : planRepository.findAll()){
            if(plan.getStart_time().substring(0,10).equals(date.toString())){
                listOfPlansForToday.add(plan);
            }
        }
        return listOfPlansForToday;
    }

    public Plan updatePlanById(int id, Plan plan){
        Plan planById = planRepository.findById(id).get();
        planById.setStart_time(plan.getStart_time());
        planById.setEnd_time(plan.getEnd_time());
        planById.setName(plan.getName());
        planById.setFrequency(plan.getFrequency());
        if(plan.getParentId()!=0){
            for(Plan curr : planRepository.findAllByParentId(plan.getParentId())){
                planRepository.delete(curr);
            }
        }
        else{
            planRepository.deleteById((int)planById.getId());
        }
        return savePlan(planById);
    }

    public Plan getPlanById(int id){
        return planRepository.findById(id).get();
    }

}