package com.example.Mercerdes.Hackerearth.Controller;

import com.example.Mercerdes.Hackerearth.Model.Plan;
import com.example.Mercerdes.Hackerearth.Service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = {"http://localhost:3000/","https://calendar-planner.netlify.app/"})
@RequestMapping("/api")
public class PlanController {

    @Autowired
    private PlanService planService;

    @GetMapping("/getAllPlans")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>( planService.getAllPlans(), HttpStatus.OK);
    }

    @GetMapping("/getPlanById/{id}")
    public ResponseEntity<?> getPlanById(@PathVariable int id){
        return new ResponseEntity<>( planService.getPlanById(id), HttpStatus.OK);
    }

    @PostMapping("/savePlan")
    public ResponseEntity<?> savePlan(@RequestBody Plan plan){
        return new ResponseEntity<>(planService.savePlan(plan), HttpStatus.CREATED);
    }

    @GetMapping("/getAllPlansForToday")
    public ResponseEntity<?> getAllPlansForToday(){
        return new ResponseEntity<>( planService.getAllPlansForToday(), HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deletePlanById(@PathVariable int id){
        return new ResponseEntity<>( planService.deletePlanById(id), HttpStatus.OK);
    }

    @PutMapping("/updatePlanById/{id}")
    public void updatePlanById(@PathVariable int id,@RequestBody Plan plan){
        planService.updatePlanById(id,plan);
    }

}
