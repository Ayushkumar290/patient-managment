package com.am.patientservice.service;

import com.am.patientservice.dto.PatientRequestDTO;
import com.am.patientservice.dto.PatientResponceDTO;
import com.am.patientservice.exception.EmailAlreadyExistsException;
import com.am.patientservice.exception.PatientNotFoundException;
import com.am.patientservice.grpc.BillingServiceGrpcClient;
import com.am.patientservice.mapper.PatientMapper;
import com.am.patientservice.model.Patient;
import com.am.patientservice.repository.PatientRepository;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private BillingServiceGrpcClient billingServiceGrpcClient;


    public List<PatientResponceDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        return patients.stream()
                .map(PatientMapper::toDTO).toList();
    }
    public PatientResponceDTO createPatient(PatientRequestDTO requestDTO){

        if(patientRepository.existsByEmail(requestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient with this email"+
                    "already exists"+ requestDTO.getEmail());
        }

        Patient  newPatient = patientRepository.save(PatientMapper.toModel(requestDTO));
        billingServiceGrpcClient.createBillingAccount(
                newPatient.getId().toString(),newPatient.getName(), newPatient.getEmail());

        return PatientMapper.toDTO(newPatient);

    }
    public PatientResponceDTO updatePatient(UUID id, PatientRequestDTO requestDTO){
        Patient patient = patientRepository.findById(id)
                .orElseThrow(()->new
                        PatientNotFoundException("Patient Not found with  ID" + id));

        if(patientRepository.existsByEmailAndIdNot(requestDTO.getEmail(), id)){
            throw new EmailAlreadyExistsException("A patient with this email"+
                    "already exists"+ requestDTO.getEmail());
        }
        patient.setName(requestDTO.getName());
        patient.setAddress(requestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(requestDTO.getDateOfBirth()));
        patient.setEmail(requestDTO.getEmail());

        Patient upadatedPatient =  patientRepository.save(patient);


        return PatientMapper.toDTO(upadatedPatient);

    }

    public void deletePatient(UUID id){

        patientRepository.deleteById(id);

    }

}
