#include <check.h>
#include <stdlib.h>
#include "tests.h"

int main(void)
{
   int number_failed;

   SRunner *sr = srunner_create(parse_consumer_suite());
   srunner_add_suite(sr, json_post_suite());

   srunner_run_all(sr, CK_NORMAL);
   number_failed = srunner_ntests_failed(sr);
   srunner_free(sr);
   return number_failed == 0 ? EXIT_SUCCESS : EXIT_FAILURE;
}
