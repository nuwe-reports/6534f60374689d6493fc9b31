
package com.example.demo;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;
import java.time.format.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.example.demo.controllers.*;
import com.example.demo.repositories.*;
import com.example.demo.entities.*;
import com.fasterxml.jackson.databind.ObjectMapper;



/** TODO
 * Implement all the unit test in its corresponding class.
 * Make sure to be as exhaustive as possible. Coverage is checked ;)
 */

@WebMvcTest(DoctorController.class)
class DoctorControllerUnitTest{

    @MockBean
    private DoctorRepository doctorRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldNotGetDoctors() throws Exception {

        List<Doctor> doctors = new ArrayList<Doctor>();
        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetTwoDoctors() throws Exception{

        Doctor doctor = new Doctor("Joan", "Gudé", 37, "jgude37@mail.com");
        Doctor doctor2 = new Doctor("Emily", "Smith", 42, "esmith82@mail.com");

        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);
        doctors.add(doctor2);

        when(doctorRepository.findAll()).thenReturn(doctors);
        mockMvc.perform(get("/api/doctors"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldGetDoctorById() throws Exception{

        Doctor doctor = new Doctor("Joan", "Gudé", 37, "jgude37@mail.com");

        doctor.setId(1);

        Optional<Doctor> optional = Optional.of(doctor);

        assertThat(optional).isPresent();
        assertThat(optional.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(optional);
        mockMvc.perform(get("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetAnyDoctorById() throws Exception{
        long id = 15;
        mockMvc.perform(get("/api/doctors/" + id))
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldDeleteDoctorById() throws Exception{

        Doctor doctor = new Doctor("Joan", "Gudé", 37, "jgude37@mail.com");

        doctor.setId(1);

        Optional<Doctor> optional = Optional.of(doctor);

        assertThat(optional).isPresent();
        assertThat(optional.get().getId()).isEqualTo(doctor.getId());
        assertThat(doctor.getId()).isEqualTo(1);

        when(doctorRepository.findById(doctor.getId())).thenReturn(optional);
        mockMvc.perform(delete("/api/doctors/" + doctor.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteDoctorById() throws Exception{

        long id = 15;

        mockMvc.perform(delete("/api/doctors/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllDoctors() throws Exception{

        mockMvc.perform(delete("/api/doctors"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateDoctor() throws Exception{

        Doctor doctor = new Doctor("Joan", "Gudé", 37, "jgude37@mail.com");

        mockMvc.perform(post("/api/doctor").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(doctor)))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    Doctor createdDoctor = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            Doctor.class);
                    Assertions.assertEquals("Joan", createdDoctor.getFirstName());
                });
    }
}


@WebMvcTest(PatientController.class)
class PatientControllerUnitTest{

    @MockBean
    private PatientRepository patientRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldNotGetPatients() throws Exception {

        List<Patient> patients = new ArrayList<Patient>();
        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldGetTwoPatients() throws Exception{

        Patient patient = new Patient("Albert", "Flanagan", 19, "aflanagan19@mail.com");
        Patient patient2 = new Patient("Rosa", "Jones", 26, "rjones97@mail.com");

        List<Patient> patients = new ArrayList<>();
        patients.add(patient);
        patients.add(patient2);

        when(patientRepository.findAll()).thenReturn(patients);
        mockMvc.perform(get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void shouldGetPatientById() throws Exception{

        Patient patient = new Patient("Albert", "Flanagan", 19, "aflanagan19@mail.com");

        patient.setId(1);

        Optional<Patient> optional = Optional.of(patient);

        assertThat(optional).isPresent();
        assertThat(optional.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(optional);
        mockMvc.perform(get("/api/patients/" + patient.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotGetAnyPatientById() throws Exception{

        long id = 15;
        mockMvc.perform(get("/api/patients/" + id))
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldDeletePatientById() throws Exception{

        Patient patient = new Patient("Albert", "Flanagan", 19, "aflanagan19@mail.com");

        patient.setId(1);

        Optional<Patient> optional = Optional.of(patient);

        assertThat(optional).isPresent();
        assertThat(optional.get().getId()).isEqualTo(patient.getId());
        assertThat(patient.getId()).isEqualTo(1);

        when(patientRepository.findById(patient.getId())).thenReturn(optional);
        mockMvc.perform(delete("/api/patients/" + patient.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeletePatientById() throws Exception{

        long id = 15;
        mockMvc.perform(delete("/api/patients/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteAllPatients() throws Exception{

        mockMvc.perform(delete("/api/patients"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreatePatient() throws Exception{

        Patient patient = new Patient("Albert", "Flanagan", 19, "aflanagan19@mail.com");

        mockMvc.perform(post("/api/patient").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    Patient createdPatient = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            Patient.class);
                    Assertions.assertEquals("Albert", createdPatient.getFirstName());
                });
    }

}

@WebMvcTest(RoomController.class)
class RoomControllerUnitTest{

    @MockBean
    private RoomRepository roomRepository;

    @Autowired 
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void this_is_a_test(){
        // DELETE ME
        assertThat(true).isEqualTo(false);
    }

}
