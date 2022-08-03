package com.genspark.jwtsecurity.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class JobStatusObject {
    private String title;
    private String screen;
    private String interview;
    private String hire;
    private String offer;
}
