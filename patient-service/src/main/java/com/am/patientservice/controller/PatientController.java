package com.am.patientservice.controller;

import com.am.patientservice.dto.PatientRequestDTO;
import com.am.patientservice.dto.PatientResponceDTO;
import com.am.patientservice.dto.validators.CreatePatientValidationGroup;
import com.am.patientservice.service.PatientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")

@Tag(name = "Patient" ,description = "API for managing patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    @Operation(summary = "Get Patients")
    public ResponseEntity<List<PatientResponceDTO>> getPatients(){
        List<PatientResponceDTO> patients = patientService.getPatients();

        return ResponseEntity.ok().body(patients);
    }

    @PostMapping
    @Operation(summary = "Create a new Patient")
    public ResponseEntity<PatientResponceDTO> createPatient(
            @Validated({Default.class, CreatePatientValidationGroup.class})
            @RequestBody PatientRequestDTO requestDTO
    ){
        PatientResponceDTO responseDTO =  patientService.createPatient(requestDTO);

        return ResponseEntity.ok().body(responseDTO);

    }
    @PutMapping("/{id}")
    @Operation(summary = "Update a Patient")
    public ResponseEntity<PatientResponceDTO> updatePatient(@PathVariable UUID id,@Validated(Default.class) @RequestBody PatientRequestDTO requestDTO){
        PatientResponceDTO responseDTO = patientService.updatePatient(id, requestDTO);

        return ResponseEntity.ok().body(responseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id){
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
