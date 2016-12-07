/**
 * @brief Library for sending events by using HTTP + JSON 
 */
#ifndef __JSON_POST_H
#define __JSON_POST_H

#include <stdbool.h>

#define JSON_POST_OK (0)
#define JSON_POST_INIT_FAILED (1)
#define JSON_POST_PUT_FAILED (2)
#define JSON_POST_GET_FAILED (3)

/**
 * @brief Initializees the libary's internal structures.
 */
int json_post_init();

/**
 * @brief Executes a HTTP GET
 *
 * @param url destination
 * @param verbose set to true to get CURL output
 */
int json_post_get(const char* url, bool verbose);

/**
 * @brief Performs a HTTP PUT of the specified data.
 *
 * @param url destination
 * @param message message to put
 */
int json_post_send(const char* url, const char* message);

/**
 * @brief Cleans up all resources.
 */
void json_post_cleanup();

/**
 * @brief Writes a JSON string containing the sensor event.
 *
 * @param dest buffer to write to
 * @param max_length maximum size of the buffer
 * @param sensor_id sensor id
 * @param value the sensor's value
 * @param data_type a textual description of the data type; "temp", "humidity", etc  
 */
void json_post_write_sensor_event(char* dest, size_t max_length, int sensor_id,
				  const char* value, const char* data_type);

/**
 * @brief Writes a JSON string containing the device event.
 *
 * @param dest buffer to write to
 * @param max_length maximum size of the buffer
 * @param device_id device id
 * @param value the device's value (most likely "on" or "off")
 * @param device_name name appearing in tellstick.conf
 */
void json_post_write_device_event(char* dest, size_t max_length, int device_id,
				  const char* value, const char* device_name);
#endif 
