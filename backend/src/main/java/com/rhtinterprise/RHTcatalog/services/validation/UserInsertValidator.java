package com.rhtinterprise.RHTcatalog.services.validation;

import com.rhtinterprise.RHTcatalog.dto.UserInsertDTO;
import com.rhtinterprise.RHTcatalog.entities.User;
import com.rhtinterprise.RHTcatalog.repositories.UserRepository;
import com.rhtinterprise.RHTcatalog.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserInsertValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserInsertDTO userInsertDTO, ConstraintValidatorContext constraintValidatorContext) {

        List<FieldMessage> list = new ArrayList<>();
        User user = userRepository.findByEmail(userInsertDTO.getEmail());

        if (null != user) {
            list.add(new FieldMessage("email", "Email já existe!"));
        }

        for (FieldMessage fieldMessage : list ) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
                    .addPropertyNode(fieldMessage.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
