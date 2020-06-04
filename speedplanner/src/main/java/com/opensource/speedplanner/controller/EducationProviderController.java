package com.opensource.speedplanner.controller;

import com.opensource.speedplanner.model.EducationProvider;
import com.opensource.speedplanner.resource.EducationProviderResource;
import com.opensource.speedplanner.resource.SaveEducationProviderResource;
import com.opensource.speedplanner.service.EducationProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

//@Tag(name="education providers", description = "Education providers API")
@RestController
@RequestMapping("/api")
public class EducationProviderController {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private EducationProviderService educationProviderService;

    //@Operation(summary = "Get Education providers", description = "Get All Education providers by Pages", tags = {"education providers"})
    @PostMapping("/educationProviders")
    public EducationProviderResource createEducationProvider(@Valid @RequestBody SaveEducationProviderResource resource){
        EducationProvider educationProvider = convertToEntity(resource);
        return convertToResource(educationProviderService.createEducationProvider(educationProvider));
    }
    @GetMapping("/educationProviders")
    public Page<EducationProviderResource> getAllEducationProviders(Pageable pageable){
        Page<EducationProvider> educationProviders = educationProviderService.getAllEducationProviders(pageable);
        List<EducationProviderResource> resources = educationProviders.getContent().stream().map(this::convertToResource)
                .collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }
    @GetMapping("/educationProviders/{educationProviderId}")
    public EducationProviderResource getEducationProviderById(@PathVariable Long educationProviderId){
        return convertToResource(educationProviderService.getEducationProviderById(educationProviderId));
    }
    @PutMapping("/educationProviders/{educationProviderId}")
    public EducationProviderResource updateEducationProvider(@PathVariable Long educationProviderId,
                                                             @Valid @RequestBody SaveEducationProviderResource resource){
        EducationProvider educationProvider = convertToEntity(resource);
        return convertToResource(educationProviderService.updateEducationProvider(educationProviderId, educationProvider));
    }
    @DeleteMapping("/educationProviders/{educationProviderId}")
    public ResponseEntity<?> deleteEducationProvider(@PathVariable Long educationProviderId){
        return educationProviderService.deleteEducationProvider(educationProviderId);
    }

    private EducationProvider convertToEntity(SaveEducationProviderResource resource){
        return mapper.map(resource, EducationProvider.class);
    }
    private EducationProviderResource convertToResource(EducationProvider entity){
        return mapper.map(entity, EducationProviderResource.class);
    }
}
