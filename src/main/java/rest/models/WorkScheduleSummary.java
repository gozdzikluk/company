package rest.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * WorkScheduleSummary REST model.
 * <p>
 * Contains summary informations about work schedule.
 *
 * @author Lukasz Gozdziewski
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkScheduleSummary {
    /**
     * Keeps total over hours time.
     */
    private int totalOvertimes;
    /**
     * Keeps total over hours time in minutes.
     */
    private int totalOvertimeMinutes;
    /**
     * Keeps time from.
     */
    private Timestamp from;
    /**
     * Keeps time to.
     */
    private Timestamp to;
    /**
     * Keeps total deficit in minutes.
     */
    private int totalDeficit;
    /**
     * Keeps information about total used vacation.
     */
    private int totalUsedVacation;
    /**
     * Keeps total planned vacation.
     */
    private int totalPlannedVacation;
    /**
     * Keeps total delays.
     */
    private int totalDelays;
    /**
     * Keeps total work time in minutes.
     */
    private Long totalWorkTime;
}
