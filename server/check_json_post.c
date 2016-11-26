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

Suite *json_post_suite(void)
{
   TCase *tc_positive = tcase_create("Positive");
   tcase_add_test(tc_positive, json_post_performs_GET_of_a_url);

   Suite *s = suite_create("json_post_get");
   suite_add_tcase(s, tc_positive);
   return s;
}
