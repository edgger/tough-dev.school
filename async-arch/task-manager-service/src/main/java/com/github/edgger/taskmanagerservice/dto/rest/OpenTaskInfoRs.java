package com.github.edgger.taskmanagerservice.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenTaskInfoRs {

    private String id;
    private String description;

}
