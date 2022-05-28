package hu.muhari.spring.logistics.model;

import javax.persistence.*;

@Entity
public class Section {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    private Milestone fromMilestone;
    @OneToOne
    private Milestone toMilestone;

    @ManyToOne
    @JoinColumn(name = "transportPlan_id")
    private TransportPlan transportPlan;

    private int numberOfSection;

    public Section() {
    }

    public Section(long id, Milestone fromMilestone, Milestone toMilestone, int numberOfSection, TransportPlan transportPlan) {
        this.id = id;
        this.fromMilestone = fromMilestone;
        this.toMilestone = toMilestone;
        this.numberOfSection = numberOfSection;
        this.transportPlan = transportPlan;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Milestone getFromMilestone() {
        return fromMilestone;
    }

    public void setFromMilestone(Milestone fromMilestone) {
        this.fromMilestone = fromMilestone;
    }

    public Milestone getToMilestone() {
        return toMilestone;
    }

    public void setToMilestone(Milestone toMilestone) {
        this.toMilestone = toMilestone;
    }

    public int getNumberOfSection() {
        return numberOfSection;
    }

    public void setNumberOfSection(int number) {
        this.numberOfSection = number;
    }

    public TransportPlan getTransportPlan() {
        return transportPlan;
    }

    public void setTransportPlan(TransportPlan transportPlan) {
        this.transportPlan = transportPlan;
    }
}
