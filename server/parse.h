#ifndef __PARSE_H
#define __PARSE_H

#include "input.h"

#define NOF_INPUT_TOKENS 3

/**
 * @brief Parses input on the form S<sensor_id>,<value>,<data type>.
 *
 * @param line line of input terminated with '\n'.
 * @return NULL if the line doesn't start with 'S' or if the parsing fails for another reason.
 */
SensorValue *parse_sensor_value(const char *line);


/**
 * @brief Parses input on the form D<device_id>,<value>,<device name>
 *
 * @param line line of input terminated with '\n'.
 * @return NULL if the line doesn't start with 'S' or if the parsing fails for another reason.
 */
DeviceValue *parse_device_value(const char* line);
#endif
