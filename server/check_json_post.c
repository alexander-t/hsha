#include <check.h>
#include <stdlib.h>
#include "json_post.h"
#include "fff.h"
#include <stdio.h>

DEFINE_FFF_GLOBALS
FAKE_VOID_FUNC(curl_easy_setopt, long, long, void*)

START_TEST(json_post_performs_GET_of_a_url)
{
   const char* url = "http://testedurl.local:8080";
   const bool verbose = false;
   json_post_get(url, verbose);

   ck_assert_int_eq(curl_easy_setopt_fake.call_count, 3);
   ck_assert_str_eq(curl_easy_setopt_fake.arg2_history[0], "GET");
   ck_assert_str_eq(curl_easy_setopt_fake.arg2_history[1], url);
   ck_assert_int_eq(curl_easy_setopt_fake.arg2_history[2], 0);
}
END_TEST

START_TEST(json_post_write_sensor_event_correct_values)
{
   char actual[512];
   char *expected = "{\"sensor_id\":1024,\"value\":\"36.6\",\"data_type\":\"temp\"}";
   json_post_write_sensor_event(actual, 512, 1024, "36.6", "temp");
   ck_assert_str_eq(actual, expected);
}
END_TEST

START_TEST(json_post_write_device_event_correct_values)
{
   char actual[512];
   char *expected = "{\"device_id\":32768,\"value\":\"off\",\"device_name\":\"Motion Sensor #4\"}";
   json_post_write_device_event(actual, 512, 32768, "off", "Motion Sensor #4");
   ck_assert_str_eq(actual, expected);
}
END_TEST

Suite *json_post_suite(void)
{
   TCase *tc_positive = tcase_create("Positive");
   tcase_add_test(tc_positive, json_post_performs_GET_of_a_url);
   tcase_add_test(tc_positive, json_post_write_sensor_event_correct_values);
   tcase_add_test(tc_positive, json_post_write_device_event_correct_values);

   Suite *s = suite_create("json_post_get");
   suite_add_tcase(s, tc_positive);
   return s;
}
