package hu.muhari.spring.logistics.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Milestone {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    private LocalDateTime plannedTime;


    public Milestone() {
    }

    public Milestone(long id, Address address, LocalDateTime plannedTime) {
        this.id = id;
        this.address = address;
        this.plannedTime = plannedTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalDateTime getPlannedTime() {
        return plannedTime;
    }

    public void setPlannedTime(LocalDateTime plannedTime) {
        this.plannedTime = plannedTime;
    }

}
