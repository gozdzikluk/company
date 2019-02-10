package exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *  Erro Message model.
 *
 * @author Lukasz Gozdziewski
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private String errorMessageValue;
    private String errorMessageKey;
}


