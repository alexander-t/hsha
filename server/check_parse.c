#include <check.h>
#include <stdlib.h>
#include "parse.h"

START_TEST(empty_strings_are_rejected)
{
  ck_assert(parse_sensor_value("") == NULL);
}
END_TEST

START_TEST(nulls_are_rejected)
{
  ck_assert(parse_sensor_value(NULL) == NULL);
}
END_TEST

START_TEST(lines_with_too_few_values_are_rejected)
{
  ck_assert(parse_sensor_value("S1,2") == NULL);
}
END_TEST

START_TEST(parse_correct_string)
{
   SensorValue *parsed_value = parse_sensor_value("S1,2,3");
   ck_assert_msg(parsed_value != NULL, "Guard null check");
   ck_assert_int_eq(parsed_value->sensor_id, 1);
   ck_assert_str_eq(parsed_value->data_type, "2");
   ck_assert_str_eq(parsed_value->value, "3");
}
END_TEST

START_TEST(strings_can_be_terminated_with_newline)
{
   SensorValue *parsed_value = parse_sensor_value("S4,5,6\n");
   ck_assert_msg(parsed_value != NULL, "Guard null check");
   ck_assert_int_eq(parsed_value->sensor_id, 4);
   ck_assert_str_eq(parsed_value->data_type, "5");
   ck_assert_str_eq(parsed_value->value, "6");
}
END_TEST


Suite *parse_consumer_suite(void)
{
   TCase *tc_positive = tcase_create("Positive");
   tcase_add_test(tc_positive, parse_correct_string);
   tcase_add_test(tc_positive, strings_can_be_terminated_with_newline);
   
   TCase *tc_negative = tcase_create("Negative");
   tcase_add_test(tc_negative, empty_strings_are_rejected);
   tcase_add_test(tc_negative, nulls_are_rejected);
   tcase_add_test(tc_negative, lines_with_too_few_values_are_rejected);


   Suite *s = suite_create("parse");
   suite_add_tcase(s, tc_negative);
   suite_add_tcase(s, tc_positive);
   return s;
}

int main(void)
{
   int number_failed;
   Suite *s;
   SRunner *sr;

   s = parse_consumer_suite();
   sr = srunner_create(s);

   srunner_run_all(sr, CK_NORMAL);
   number_failed = srunner_ntests_failed(sr);
   srunner_free(sr);
   return (number_failed == 0) ? EXIT_SUCCESS : EXIT_FAILURE;
}
