#include <check.h>
#include "parse.h"

START_TEST(sensor_empty_strings_are_rejected)
{
   ck_assert(parse_sensor_value("") == NULL);
}
END_TEST

START_TEST(sensor_nulls_are_rejected)
{
   ck_assert(parse_sensor_value(NULL) == NULL);
}
END_TEST

START_TEST(sensor_lines_with_too_few_values_are_rejected)
{
   ck_assert(parse_sensor_value("S1") == NULL);
   ck_assert(parse_sensor_value("S1,2") == NULL);
}
END_TEST

START_TEST(sensor_parse_correct_string)
{
   SensorValue *parsed_value = parse_sensor_value("S1,2,3");
   ck_assert_msg(parsed_value != NULL, "Guard null check");
   ck_assert_int_eq(parsed_value->sensor_id, 1);
   ck_assert_str_eq(parsed_value->data_type, "2");
   ck_assert_str_eq(parsed_value->value, "3");
}
END_TEST

START_TEST(sensor_strings_can_be_terminated_with_newline)
{
   SensorValue *parsed_value = parse_sensor_value("S4,5,6\n");
   ck_assert_msg(parsed_value != NULL, "Guard null check");
   ck_assert_int_eq(parsed_value->sensor_id, 4);
   ck_assert_str_eq(parsed_value->data_type, "5");
   ck_assert_str_eq(parsed_value->value, "6");
}
END_TEST

START_TEST(device_empty_strings_are_rejected)
{
  ck_assert(parse_device_value("") == NULL);
}
END_TEST

START_TEST(device_nulls_are_rejected)
{
   ck_assert(parse_sensor_value(NULL) == NULL);
}
END_TEST

START_TEST(device_lines_with_too_few_values_are_rejected)
{
   ck_assert(parse_sensor_value("D1") == NULL);
   ck_assert(parse_sensor_value("D1,on") == NULL);
}
END_TEST

START_TEST(device_parse_correct_string)
{
   DeviceValue *parsed_value = parse_device_value("D1,on,Motion Sensor #1");
   ck_assert_msg(parsed_value != NULL, "Guard null check");
   ck_assert_int_eq(parsed_value->device_id, 1);
   ck_assert_str_eq(parsed_value->value, "on");
   ck_assert_str_eq(parsed_value->device_name, "Motion Sensor #1");
}
END_TEST

START_TEST(device_strings_can_be_terminated_with_newline)
{
   DeviceValue *parsed_value = parse_device_value("D10,off,Magnetic Sensor #100\n");
   ck_assert_msg(parsed_value != NULL, "Guard null check");
   ck_assert_int_eq(parsed_value->device_id, 10);
   ck_assert_str_eq(parsed_value->value, "off");
   ck_assert_str_eq(parsed_value->device_name, "Magnetic Sensor #100");
}
END_TEST


Suite *parse_consumer_suite(void)
{
   TCase *tc_sensor = tcase_create("Sensor parsing tests");
   tcase_add_test(tc_sensor, sensor_parse_correct_string);
   tcase_add_test(tc_sensor, sensor_strings_can_be_terminated_with_newline);
   tcase_add_test(tc_sensor, sensor_empty_strings_are_rejected);
   tcase_add_test(tc_sensor, sensor_nulls_are_rejected);
   tcase_add_test(tc_sensor, sensor_lines_with_too_few_values_are_rejected);

   TCase *tc_device = tcase_create("Device parsing tests");

   Suite *s = suite_create("parse");
   tcase_add_test(tc_device, device_empty_strings_are_rejected);
   tcase_add_test(tc_device, device_nulls_are_rejected);
   tcase_add_test(tc_device, device_lines_with_too_few_values_are_rejected);
   tcase_add_test(tc_device, device_parse_correct_string);
   tcase_add_test(tc_device, device_strings_can_be_terminated_with_newline);
   suite_add_tcase(s, tc_sensor);
   suite_add_tcase(s, tc_device);
   return s;
}

