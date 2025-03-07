package it.sysman.chefbook.entity;

import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import it.sysman.chefbook.BasePojoTest;
import it.sysman.chefbook.dto.AutoreDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class AutoreTest extends BasePojoTest {
    @Test
    void validatePojo() {
        var pojoClass = PojoClassFactory.getPojoClass(AutoreDto.class);
        var validator = ValidatorBuilder.create()
                .with(new GetterMustExistRule())
                .with(new SetterMustExistRule())
                .with(new GetterTester())
                .with(new SetterTester())
                .build();
        validator.validate(pojoClass);
    }
}