package com.example.Mercerdes.Hackerearth.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "Plan")
public class Plan {

    @Id()
    private long id;
    private String name;
    private String start_time;
    private String end_time;
    private String frequency;
    private long parentId;
}
