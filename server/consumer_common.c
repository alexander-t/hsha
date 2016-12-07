#include <stdio.h>
#include <stdlib.h>
#include "consumer_common.h"
#include "json_post.h"

bool is_hub_up(const char *host_part)
{
   char url[URL_SIZE];
   snprintf(url, URL_SIZE, "http://%s", host_part);
   return json_post_get(url, false) == JSON_POST_OK;
}

void sensor_value_free(SensorValue *sv)
{
   free(sv->value);
   free(sv->data_type);
   free(sv);
}

void device_value_free(DeviceValue *dv)
{
   free(dv->value);
   free(dv->device_name);
   free(dv);
}
