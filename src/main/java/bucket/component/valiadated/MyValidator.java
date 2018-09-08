package bucket.component.valiadated;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 17:50 2018/9/8
 */
@Slf4j
public class MyValidator implements ConstraintValidator<CustomValidate, String> {

    @Override
    public void initialize(CustomValidate constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        log.info("validate value is {}", value);
        if (System.currentTimeMillis() % 2 == 0) {
            return true;
        }
        return false;
    }
}
