package com.example.Mercerdes.Hackerearth.Controller;

import com.example.Mercerdes.Hackerearth.Model.Plan;
import com.example.Mercerdes.Hackerearth.Service.PlanService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value="Returns All Plans", notes="Just make a get request with no parameters",response = Plan.class)
    @GetMapping("/getAllPlans")
    public ResponseEntity<?> getAll(){
        return new ResponseEntity<>( planService.getAllPlans(), HttpStatus.OK);
    }

    @ApiOperation(value="Returns Plan By Id", notes="Just make a get request passing ID in URL",response = Plan.class)
    @GetMapping("/getPlanById/{id}")
    public ResponseEntity<?> getPlanById(@PathVariable int id){
        return new ResponseEntity<>( planService.getPlanById(id), HttpStatus.OK);
    }

    @ApiOperation(value="Saves Plan to DB and returns savedPlan", notes="Just make a post request with data in body",response = Plan.class)
    @PostMapping("/savePlan")
    public ResponseEntity<?> savePlan(@RequestBody Plan plan){
        return new ResponseEntity<>(planService.savePlan(plan), HttpStatus.CREATED);
    }

    @ApiOperation(value="Returns All Plans for Today", notes="Just make a get request with no parameters",response = Plan.class)
    @GetMapping("/getAllPlansForToday")
    public ResponseEntity<?> getAllPlansForToday(){
        return new ResponseEntity<>( planService.getAllPlansForToday(), HttpStatus.OK);
    }

    @ApiOperation(value="Deletes Plan By ID", notes="Just make a delete request with ID in URL",response = Plan.class)
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deletePlanById(@PathVariable int id){
        return new ResponseEntity<>( planService.deletePlanById(id), HttpStatus.OK);
    }

    @ApiOperation(value="Updates Plan by ID", notes="Just make a put request with ID in url and plan in body",response = Plan.class)
    @PutMapping("/updatePlanById/{id}")
    public ResponseEntity<?> updatePlanById(@PathVariable int id,@RequestBody Plan plan){
        return new ResponseEntity<>(planService.updatePlanById(id,plan),HttpStatus.OK);
    }

}
