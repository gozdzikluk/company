package rest.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
/**
 * Break REST model.
 *
 * @author Lukasz Gozdziewski
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Break {
    /**
     * Keeps break's id in database.
     */
    private Integer id;
    /**
     * Keeps break's start time.
     */
    private LocalTime start;
    /**
     * Keeps break's end time.
     */
    private LocalTime end;
    /**
     * Keeps break time.
     */
    private int time;
    /**
     * Keeps workSchedule's Id.
     */
    private int workscheduleid;
}
