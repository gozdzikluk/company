package rest.models;

import lombok.*;
/**
 * Address REST model.
 *
 * @author Lukasz Gozdziewski
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Address {
    /**
     * Keeps street name.
     */
    private String street;
    /**
     * Keeps number of building/apartment.
     */
    private String number;
    /**
     * Keeps city name.
     */
    private String city;
    /**
     * Keeps zip post code.
     */
    private String zip;
}
