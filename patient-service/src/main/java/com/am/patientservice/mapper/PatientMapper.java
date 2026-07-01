package com.am.patientservice.mapper;

import com.am.patientservice.dto.PatientRequestDTO;
import com.am.patientservice.dto.PatientResponceDTO;
import com.am.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponceDTO toDTO(Patient patient){
        PatientResponceDTO patientDTO =new PatientResponceDTO();
        patientDTO.setId(patient.getId().toString());
        patientDTO.setName(patient.getName());
        patientDTO.setAddress(patient.getAddress());
        patientDTO.setEmail(patient.getEmail());
        patientDTO.setDateOfBirth(patient.getDateOfBirth().toString());

        return patientDTO;
    }
    public static Patient toModel(PatientRequestDTO requestDTO){
        Patient newPatient = new Patient();
        newPatient.setName(requestDTO.getName());
        newPatient.setDateOfBirth(LocalDate.parse(requestDTO.getDateOfBirth()));
        newPatient.setAddress(requestDTO.getAddress());
        newPatient.setRegisteredDate(LocalDate.parse(requestDTO.getRegisteredDate()));
        newPatient.setEmail(requestDTO.getEmail());

        return newPatient;
    }
}
