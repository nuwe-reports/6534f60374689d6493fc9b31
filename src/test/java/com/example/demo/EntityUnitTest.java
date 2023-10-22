package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.example.demo.entities.*;

import javax.persistence.PersistenceException;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

	@Autowired
	private TestEntityManager entityManager;

    private Doctor doctor;

    private Patient patient;

    private Room room;

    private Appointment appointment;
    private Appointment appointment2;
    private Appointment appointment3;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
    private LocalDateTime startsAt= LocalDateTime.parse("10:30 31/10/2023", formatter);
    private LocalDateTime finishesAt = LocalDateTime.parse("11:30 31/10/2023", formatter);

    @BeforeEach
    public void setup() {
        doctor = new Doctor("Joan", "Gudé", 37, "jgude37@mail.com");
        patient = new Patient("Rosa", "Jones", 26, "rjones97@mail.com");
        room = new Room("Oncology");

        startsAt= LocalDateTime.parse("10:30 31/10/2023", formatter);
        finishesAt = LocalDateTime.parse("11:30 31/10/2023", formatter);

        appointment = new Appointment(patient, doctor, room, startsAt,finishesAt);
        appointment2 = new Appointment(patient, doctor, new Room("Ophthalmology"), startsAt, finishesAt);
        appointment3 = new Appointment(patient, doctor, room, startsAt, finishesAt);
    }

    //-------------DOCTOR TESTS------------------
    @Test
    public void saveDoctor() {
        Doctor savedDoctor = entityManager.persistAndFlush(doctor);
        assertEquals(doctor, savedDoctor);
    }

    @Test
    public void findDoctorById() {
        Doctor savedDoctor = entityManager.persistAndFlush(doctor);
        Doctor foundDoctor = entityManager.find(Doctor.class, savedDoctor.getId());
        assertEquals(doctor, foundDoctor);
    }

    @Test
    public void updateDoctor() {
        Doctor savedDoctor = entityManager.persistAndFlush(doctor);
        savedDoctor.setFirstName("UpdatedFirstName");
        savedDoctor.setLastName("UpdatedLastName");
        savedDoctor.setAge(20);
        savedDoctor.setEmail("updated@email.com");

        entityManager.persistAndFlush(savedDoctor);
        Doctor updatedDoctor = entityManager.find(Doctor.class, savedDoctor.getId());

        assertEquals("UpdatedFirstName", updatedDoctor.getFirstName());
        assertEquals("UpdatedLastName", updatedDoctor.getLastName());
        assertEquals(20, updatedDoctor.getAge());
        assertEquals("updated@email.com", updatedDoctor.getEmail());
    }

    @Test
    public void changeDoctorIdShouldNotBePersisted(){

        doctor.setId(10);
        assertThrows(PersistenceException.class, () -> entityManager.persistAndFlush(doctor));
    }

    @Test
    public void deleteDoctor() {
        Doctor savedDoctor = entityManager.persistAndFlush(doctor);
        entityManager.remove(savedDoctor);
        Doctor deletedDoctor = entityManager.find(Doctor.class, savedDoctor.getId());
        assertNull(deletedDoctor);
    }

    @Test
    public void getDoctorId() {
        Doctor savedDoctor = entityManager.persistAndFlush(doctor);
        long id = savedDoctor.getId();
        assertEquals(id, savedDoctor.getId());
    }

    @Test
    public void getDoctorFirstName() {
        assertEquals("Joan", doctor.getFirstName());
    }

    @Test
    public void getDoctorLastName() {
        assertEquals("Gudé", doctor.getLastName());
    }

    @Test
    public void getDoctorAge() {
        assertEquals(37, doctor.getAge());
    }

    @Test
    public void getDoctorEmail() {
        assertEquals("jgude37@mail.com", doctor.getEmail());
    }

    //-------------PATIENT TESTS------------------

    @Test
    public void savePatient() {
        Patient savedPatient = entityManager.persistAndFlush(patient);
        assertEquals(patient, savedPatient);
    }

    @Test
    public void findPatientById() {
        Patient savedPatient = entityManager.persistAndFlush(patient);
        Patient foundPatient = entityManager.find(Patient.class, savedPatient.getId());
        assertEquals(patient, foundPatient);
    }

    @Test
    public void updatePatient() {
        Patient savedPatient = entityManager.persistAndFlush(patient);
        savedPatient.setFirstName("UpdatedFirstName");
        savedPatient.setLastName("UpdatedLastName");
        savedPatient.setAge(20);
        savedPatient.setEmail("updated@email.com");

        entityManager.persistAndFlush(savedPatient);
        Patient updatedPatient = entityManager.find(Patient.class, savedPatient.getId());

        assertEquals("UpdatedFirstName", updatedPatient.getFirstName());
        assertEquals("UpdatedLastName", updatedPatient.getLastName());
        assertEquals(20, updatedPatient.getAge());
        assertEquals("updated@email.com", updatedPatient.getEmail());
    }

    @Test
    public void changePatientIdShouldNotBePersisted(){

        patient.setId(10);
        assertThrows(PersistenceException.class, () -> entityManager.persistAndFlush(patient));
    }

    @Test
    public void deletePatient() {
        Patient savedPatient = entityManager.persistAndFlush(patient);
        entityManager.remove(savedPatient);
        Patient deletedPatient = entityManager.find(Patient.class, savedPatient.getId());
        assertNull(deletedPatient);
    }

    @Test
    public void getPatientId() {
        Patient savedPatient = entityManager.persistAndFlush(patient);
        long id = savedPatient.getId();
        assertEquals(id, savedPatient.getId());
    }
    @Test
    public void getPatientFirstName() {
        assertEquals("Rosa", patient.getFirstName());
    }

    @Test
    public void getPatientLastName() {
        assertEquals("Jones", patient.getLastName());
    }

    @Test
    public void getPatientAge() {
        assertEquals(26, patient.getAge());
    }

    @Test
    public void getPatientEmail() {
        assertEquals("rjones97@mail.com", patient.getEmail());
    }

    //-------------ROOM TESTS------------------

    @Test
    public void saveRoom() {
        Room savedRoom = entityManager.persistAndFlush(room);
        assertEquals(room, savedRoom);
    }

    @Test
    public void findRoomByRoomName() {
        Room savedRoom = entityManager.persistAndFlush(room);
        Room foundRoom = entityManager.find(Room.class, savedRoom.getRoomName());
        assertEquals(room, foundRoom);
    }

    @Test
    public void deleteRoom() {
        Room savedRoom = entityManager.persistAndFlush(room);
        entityManager.remove(savedRoom);
        Room deletedRoom = entityManager.find(Room.class, savedRoom.getRoomName());
        assertNull(deletedRoom);
    }

    @Test
    public void getRoomRoomName() {
        Room savedRoom = entityManager.persistAndFlush(room);
        String roomName = savedRoom.getRoomName();
        assertEquals(roomName, savedRoom.getRoomName());
    }

    @Test
    public void shouldNotPersistEmptyRoom(){

        Room room2 = new Room();
        assertThrows(PersistenceException.class, () -> entityManager.persistAndFlush(room2));
    }

    //-------------APPOINTMENT TESTS------------------

    @Test
    public void saveAppointment() {
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);
        assertEquals(appointment, savedAppointment);
    }

    @Test
    public void findAppointmentById() {
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);
        Appointment foundAppointment = entityManager.find(Appointment.class, savedAppointment.getId());
        assertEquals(appointment, savedAppointment);
    }

    @Test
    public void updateAppointment() {
        Patient updatedPatient = new Patient("Updated", "Patient", 30, "updatedPatient@example.com");
        Doctor updatedDoctor = new Doctor("Updated", "Doctor", 50, "updatedDoctor@example.com");
        Room updatedRoom = new Room("UpdatedRoom");
        LocalDateTime updatedStartsAt = LocalDateTime.parse("15:30 30/10/2023", formatter);
        LocalDateTime updatedFinishesAt = LocalDateTime.parse("16:30 30/10/2023", formatter);

        Appointment savedAppointment = entityManager.persistAndFlush(appointment);

        savedAppointment.setPatient(updatedPatient);
        savedAppointment.setDoctor(updatedDoctor);
        savedAppointment.setRoom(updatedRoom);
        savedAppointment.setStartsAt(updatedStartsAt);
        savedAppointment.setFinishesAt(updatedFinishesAt);

        assertEquals(updatedPatient, savedAppointment.getPatient());
        assertEquals(updatedDoctor, savedAppointment.getDoctor());
        assertEquals(updatedRoom, savedAppointment.getRoom());
        assertEquals(updatedStartsAt, savedAppointment.getStartsAt());
        assertEquals(updatedFinishesAt, savedAppointment.getFinishesAt());

        entityManager.persistAndFlush(savedAppointment);

        Appointment updatedAppointment = entityManager.find(Appointment.class, savedAppointment.getId());

        assertEquals(updatedPatient, updatedAppointment.getPatient());
        assertEquals(updatedDoctor, updatedAppointment.getDoctor());
        assertEquals(updatedRoom, updatedAppointment.getRoom());
        assertEquals(updatedStartsAt, updatedAppointment.getStartsAt());
        assertEquals(updatedFinishesAt, updatedAppointment.getFinishesAt());
    }


    @Test
    public void changeAppointmentIdShouldNotBePersisted(){

        appointment.setId(10);
        assertThrows(PersistenceException.class, () -> entityManager.persistAndFlush(appointment));
    }

    @Test
    public void deleteAppointment() {
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);
        entityManager.remove(savedAppointment);
        Appointment deletedAppointment = entityManager.find(Appointment.class, savedAppointment.getId());
        assertNull(deletedAppointment);
    }

    @Test
    public void getAppointmentId() {
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);
        long id = savedAppointment.getId();
        assertEquals(id, savedAppointment.getId());
    }
    @Test
    public void getAppointmentDoctor() {
        assertEquals(doctor, appointment.getDoctor());
    }

    @Test
    public void getAppointmentPatient() {
        assertEquals(patient, appointment.getPatient());
    }

    @Test
    public void getAppointmentRoom() {
        assertEquals(room, appointment.getRoom());
    }

    @Test
    public void getAppointmentStartsAt() {
        assertEquals(startsAt, appointment.getStartsAt());
    }

    @Test
    public void getAppointmentFinishesAt() {
        assertEquals(finishesAt, appointment.getFinishesAt());
    }

    @Test
    public void twoAppointmentsOverlapIfAtSameStartAt(){

        assertTrue(appointment.overlaps(appointment3));
    }

    @Test
    public void twoAppointmentsDoNotOverlapIfNotInSameRoom(){

        assertFalse(appointment.overlaps(appointment2));
    }

    @Test
    public void twoAppointmentsOverlapIfAtSameFinishesAt(){

        appointment3.setStartsAt(LocalDateTime.parse("11:00 31/10/2023", formatter));
        assertTrue(appointment.overlaps(appointment3));
    }

    @Test
    public void twoAppointmentsOverlapIfOneStartsBeforeFirstFinishes(){

        appointment3.setStartsAt(LocalDateTime.parse("11:00 31/10/2023", formatter));
        appointment3.setFinishesAt(LocalDateTime.parse("11:15 31/10/2023", formatter));

        assertTrue(appointment.overlaps(appointment3));
    }

    @Test
    public void twoAppointmentsOverlapIfOneFinishesAfterFirstFinishes(){

        appointment3.setStartsAt(LocalDateTime.parse("10:45 31/10/2023", formatter));
        appointment3.setFinishesAt(LocalDateTime.parse("11:45 31/10/2023", formatter));

        assertTrue(appointment.overlaps(appointment3));
    }
}
