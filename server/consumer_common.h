#ifndef __CONSUMER_COMMON_H
#define __CONSUMER_COMMON_H

#include <stdbool.h>
#include "input.h"

#define URL_SIZE 1024

bool is_hub_up(const char *host_part);
void sensor_value_free(SensorValue *sv);
void device_value_free(DeviceValue *dv);

#endif
  
