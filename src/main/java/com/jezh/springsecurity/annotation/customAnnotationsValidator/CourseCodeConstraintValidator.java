package com.jezh.springsecurity.annotation.customAnnotationsValidator;


import com.jezh.springsecurity.annotation.customAnnotation.CourseCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//ConstraintValidator<the Annotation type, the type of data to validate against>
public class CourseCodeConstraintValidator implements ConstraintValidator<CourseCode, String> {

    private String coursePrefixe;
   @Override
   public void initialize(CourseCode constraint) {
      coursePrefixe = constraint.value();
   }

   @Override
//   theCourseCode = HTML Form data, entered by user;
//   context  - the helper class for additional error message
   public boolean isValid(String theCourseCode, ConstraintValidatorContext context) {
       if (theCourseCode != null) {
           Pattern thePattern = Pattern.compile("^" + coursePrefixe + "\\d+");
           Matcher matcher = thePattern.matcher(theCourseCode);
//           return matcher.find();
           return matcher.find()? matcher.group().equals(theCourseCode) : false;
//           return theCourseCode.startsWith(coursePrefixe);
       }
      return true;
   }
}
