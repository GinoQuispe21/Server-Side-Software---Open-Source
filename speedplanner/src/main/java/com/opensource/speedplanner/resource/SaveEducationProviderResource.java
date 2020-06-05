package com.opensource.speedplanner.resource;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SaveEducationProviderResource {
    @NotNull
    @NotBlank
    @Size(max = 30)
    @Column(unique = true)
    private String name;

    @NotNull
    @NotBlank
    private int numberOfCareers;
}