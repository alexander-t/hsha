#ifndef __INPUT_H
#define __INPUT_H

struct input
{
   int device_id;
   char *value;
};

typedef struct sensor_value
{
   int sensor_id;
   char *data_type;
   char *value;
} SensorValue;

#endif
