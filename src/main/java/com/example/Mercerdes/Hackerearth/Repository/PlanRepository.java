package com.example.Mercerdes.Hackerearth.Repository;

import com.example.Mercerdes.Hackerearth.Model.Plan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends MongoRepository<Plan,Integer> {
    List<Plan> findAllByParentId(long parentId);
}
