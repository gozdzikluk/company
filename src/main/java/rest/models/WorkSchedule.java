package rest.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.json.bind.annotation.JsonbProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * WorkSchedule REST model.
 * Represents day of work/ sick leave / vacations day / delegations day.
 *
 * @author Lukasz Gozdziewski
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkSchedule implements Serializable {
    static final long serialVersionUID = 42L;
    /**
     * Keeps work schedule's id.
     */
    private Integer id;
    /**
     * Keeps work day date.
     */

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonbProperty("workday")
    private LocalDate workday;
    /**
     * Keeps start of work time.
     */
    @JsonbProperty("start_time")
    private LocalTime startTime;
    /**
     * Keeps end of work time.
     */
    @JsonbProperty("end_time")
    private LocalTime endTime;
    /**
     * Keeps information about vacation.
     */
    private boolean vacation;
    /**
     * Keeps work time in minutes.
     */
    @JsonbProperty("work_time")
    private int workTime;
    /**
     * Keeps information about over hours.
     */
    private boolean overhours;
    /**
     * Keeps over hours in minutes.
     */
    @JsonbProperty("overhours_time")
    private int overhoursTime;
    /**
     * Keeps information about delay.
     */
    @JsonbProperty("delay")
    private boolean delay;
    /**
     * Keeps deficit time.
     */
    private int deficit;
    /**
     * Keeps information about sick leave.
     */
    @JsonbProperty("sick_leave")
    private boolean sickLeave;
    /**
     * Keeps information about delegation.
     */
    private boolean delegation;
    /**
     * Keeps employee's id.
     */
    private int employeeid;
    /**
     * Keeps acceptation information.
     */
    private boolean accepted;
    /**
     * Keeps information about reject.
     */
    private boolean rejected;
    /**
     * Keeps information about break's during the work day.
     */
    private int breaks;
    /**
     * Keeps information about total breaks time during the work day.
     */
    private int breaktime;
}
