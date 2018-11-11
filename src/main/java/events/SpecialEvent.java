package events;

public class SpecialEvent extends GeneralEvent {
    private String specialEventAbout;
    private SpecialEventStatus specialEventStatus;
    private Creator specialEventCreator;

    public String getSpecialEventAbout() {
        return specialEventAbout;
    }

    public void setSpecialEventAbout(String specialEventAbout) {
        this.specialEventAbout = specialEventAbout;
    }

    public SpecialEventStatus getSpecialEventStatus() {
        return specialEventStatus;
    }

    public void setSpecialEventStatus(SpecialEventStatus specialEventStatus) {
        this.specialEventStatus = specialEventStatus;
    }

    public Creator getSpecialEventCreator() {
        return specialEventCreator;
    }

    public void setSpecialEventCreator(Creator specialEventCreator) {
        this.specialEventCreator = specialEventCreator;
    }

    @Override
    public String toString() {
        return "SpecialEvent{" +
                "specialEventAbout='" + specialEventAbout + '\'' +
                ", specialEventStatus=" + specialEventStatus +
                ", specialEventCreator=" + specialEventCreator +
                '}';
    }
}
