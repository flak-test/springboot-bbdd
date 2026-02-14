package com.project.code;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.ValidationUtils;

public class BookValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book=(Book) target; //target es de tipo Object (superclase de todas)        Book es subclase de Object        Por eso necesitas cast: Object → Book (downcasting)

        if (book.getPrice()<=0){
            errors.rejectValue("price","price.invalid","El precio no puede ser igual o menor que 0");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title","title.empty","El título no puede estar vacío");

        }

    }
}
