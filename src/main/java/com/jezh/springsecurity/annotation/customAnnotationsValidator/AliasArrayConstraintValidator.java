package com.jezh.springsecurity.annotation.customAnnotationsValidator;

import com.jezh.springsecurity.annotation.customAnnotation.AliasArray;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AliasArrayConstraintValidator implements ConstraintValidator<AliasArray, String> {

   private String [] epithet;

   public void initialize(AliasArray constraint) {
       epithet = constraint.value();
   }

   public boolean isValid(String theAliasArray, ConstraintValidatorContext context) {
       if (theAliasArray != null) {
//           final boolean[] confirmation = {false};
//           Arrays.asList(epithet).forEach(item -> confirmation[0] = theAliasArray.contains(item));
           for (String item : epithet) {
               if (theAliasArray.contains(" " + item + " ") )
                   return true;
           } return false;
       } return true;
   }
}
