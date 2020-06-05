package com.opensource.speedplanner.controller;

import com.opensource.speedplanner.model.PossibleSchedule;
import com.opensource.speedplanner.resource.PossibleScheduleResource;
import com.opensource.speedplanner.service.PossibleScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class PossibleScheduleController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PossibleScheduleService possibleScheduleService;

    private PossibleScheduleResource convertToResource(PossibleSchedule entity){
        return mapper.map(entity, PossibleScheduleResource.class);
    }

    private PossibleSchedule convertToEntity(SavePossibleScheduleResource resource){
        return mapper.map(resource, PossibleSchedule.class);
    }

    @GetMapping("/inscriptionProcesses/{inscriptionProcessId}/possibleSchedules")
    public Page<PossibleScheduleResource> getAllPossibleSchedulesByInscriptionProcessId(
            @PathVariable(name = "inscriptionProcessId") Long inscriptionProcessId, Pageable pageable){
            Page<PossibleSchedule> possibleSchedulePage = possibleScheduleService.
                    getAllPossibleSchedulesByInscriptionProcessId(inscriptionProcessId, pageable);
        List<PossibleScheduleResource> resources =possibleSchedulePage.getContent().stream().
                map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
        }

    @GetMapping("/inscriptionProcesses/{inscriptionProcessId}/possibleSchedules/{possibleScheduleId}")
    public PossibleScheduleResource getByIdAndInscriptionProcessId(@PathVariable(name = "inscriptionProcessId") Long inscriptionProcessId,
                                                                   @PathVariable(name = "possibleScheduleId") Long possibleScheduleId){
        return convertToResource(possibleScheduleService.getByIdAndInscriptionProcessId(possibleScheduleId, inscriptionProcessId));
    }

    @PutMapping("/inscriptionProcesses/{inscriptionProcessId}/possibleSchedules/")
    public PossibleScheduleResource createPossibleSchedule(@PathVariable(name = "inscriptionProcessId") Long inscriptionProcessId,
                                                           @Valid @RequestBody SavePossibleScheduleResource resource){
        return convertToResource(possibleScheduleService.createPossibleSchedule(convertToEntity(resource)));
    }

    @PutMapping("/inscriptionProcesses/{inscriptionProcessId}/possibleSchedules/{possibleScheduleId}")
    public PossibleScheduleResource updatePossibleSchedule(@PathVariable(name = "inscriptionProcessId") Long inscriptionProcessId,
                                                           @PathVariable(name = "possibleScheduleId") Long possibleScheduleId,
                                                           @Valid @RequestBody SavePossibleScheduleResource resource){
        return convertToResource(possibleScheduleService.updatePossibleSchedule(possibleScheduleId, convertToEntity(resource)));
    }

    @DeleteMapping("/inscriptionProcesses/{inscriptionProcessId}/possibleSchedules/{possibleScheduleId}")
    public ResponseEntity<?> deletePossibleSchedule(@PathVariable(name = "inscriptionProcessId") Long inscriptionProcessId,
                                                    @PathVariable(name = "possibleScheduleId") Long possibleScheduleId){
        return possibleScheduleService.deletePossibleSchedule(possibleScheduleId, inscriptionProcessId);
    }
}