package residentEvil.domain.model.binding;

import org.springframework.format.annotation.DateTimeFormat;
import residentEvil.custumValidaton.DateBeforeToday;
import residentEvil.domain.entity.Capital;
import residentEvil.domain.entity.enums.Creator;
import residentEvil.domain.entity.enums.Magnitude;
import residentEvil.domain.entity.enums.Mutation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class VirusAddBindingModel {

    private String name;
    private String description;
    private String sideEffects;
    private Creator creator;
    private boolean isDeadly;
    private boolean isCurable;
    private Mutation mutation;
    private Integer turnoverRate;
    private Integer hoursUntilTurn;
    private Magnitude magnitude;
    private LocalDate releasedOn;
    private List<String> capitals;

    @NotNull(message = "Name can't be null!")
    @Size(min = 3, max = 10, message = "Name has to be between 3 and 10!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Description can't be null!")
    @Size(min = 5, max = 100, message = "Description has to be between 3 and 10!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Size(max = 50, message = "Invalid Side Effects!")
    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    @NotNull(message = "Invalid Creator!")
    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }

    public boolean getDeadly() {
        return isDeadly;
    }

    public void setDeadly(boolean deadly) {
        this.isDeadly = deadly;
    }

    public boolean getCurable() {
        return isCurable;
    }

    public void setCurable(boolean curable) {
        this.isCurable = curable;
    }

    @NotNull(message = "Invalid Mutation!")
    public Mutation getMutation() {
        return mutation;
    }

    public void setMutation(Mutation mutation) {
        this.mutation = mutation;
    }

    @Min(0)
    @Max(100)
    @NotNull(message = "Invalid Turnover Rate!")
    public Integer getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(Integer turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    @Min(1)
    @Max(12)
    @NotNull(message = "Invalid Hours Until Turn!")
    public Integer getHoursUntilTurn() {
        return hoursUntilTurn;
    }

    public void setHoursUntilTurn(Integer hoursUntilTurn) {
        this.hoursUntilTurn = hoursUntilTurn;
    }

    @NotNull(message = "Invalid magnitude!")
    public Magnitude getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Magnitude magnitude) {
        this.magnitude = magnitude;
    }

    @NotNull(message = "Invalid date!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @DateBeforeToday
    public LocalDate getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(LocalDate releasedOn) {
        this.releasedOn = releasedOn;
    }

    @NotNull(message = "Must select Capital!")
    public List<String> getCapitals() {
        return capitals;
    }

    public void setCapitals(List<String> capitals) {
        this.capitals = capitals;
    }
}
