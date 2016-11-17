#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdbool.h>
#include <glib.h>
#include "input.h"
#include "json_post.h"

#define NOF_INPUT_TOKENS 3
#define MESSAGE_SIZE 4096
#define URL_SIZE 1024

char json_message[MESSAGE_SIZE];

struct PostData
{
   char *host;
   GAsyncQueue *queue;
};

void *json_poster_func(void *data) {
   struct PostData *post_data = (struct PostData *) data;
   GAsyncQueue *queue = post_data->queue;
   struct sensor_value *sensor_value;
   char url[URL_SIZE];

   while (1)
   {
      sensor_value = g_async_queue_pop(queue);
      json_post_write_sensor_event(json_message, MESSAGE_SIZE,
				   sensor_value->sensor_id, sensor_value->value, sensor_value->data_type);
      snprintf(url, URL_SIZE, "http://%s/sensor/%d", post_data->host, sensor_value->sensor_id);
      json_post_send(url, json_message);
      free(sensor_value);
   }
   return NULL;
}

bool is_hub_up(const char *host_part)
{
   char url[URL_SIZE];
   snprintf(url, URL_SIZE, "http://%s", host_part);
   return json_post_get(url, false) == JSON_POST_OK;
}

/**
 * @brief Parses input on the form S<sensor_id>,<value>,<data type>.
 *
 * @param line line of input terminated with '\n'.
 * @return NULL if the line doesn't start with 'S' or if the parsing fails for another reason.
 */
struct sensor_value *parse_sensor_value(const char *line)
{

   if (strlen(line) > 0 && line[0] != 'S')
   {
      return NULL;
   }

   // -2 = no \n and no first character
   char *line_to_parse = g_strndup(&line[1], strlen(line) - 2);
   struct sensor_value *parsed = malloc(sizeof(struct sensor_value));
   const char *comma = ",";
   char *saveptr;
   int i = 0;
   char* token = strtok_r(line_to_parse, comma, &saveptr);
   
   while (token != NULL && ++i <= NOF_INPUT_TOKENS)
   {
      switch (i)
      {
	 case 1:
	    parsed->sensor_id = atoi(token);
	    break;
	 case 2:
	    parsed->data_type = strdup(token);
	    break;
	 case 3:
	    parsed->value = strdup(token);
	    break;
      }
      token = strtok_r(NULL, comma, &saveptr);
   }
   free(line_to_parse);
   return i >= NOF_INPUT_TOKENS ? parsed : NULL;
}

int main(int argc, char **argv) {
   struct sensor_value *input;
   char *line = NULL;
   size_t len = 0;
   GAsyncQueue *queue = g_async_queue_new();
   struct PostData post_data;
   post_data.queue = queue;
   
   if (json_post_init() != JSON_POST_OK)
   {
      fprintf(stderr, "Couldn't initialize json_post!\n");
      return 1;
   }

   if (argc != 2)
   {
      fprintf(stderr, "Missing argument: hostname!\n");
      return 10;
   }

   post_data.host = g_strdup(argv[1]);
   if (!is_hub_up(post_data.host))
   {
      fprintf(stderr, "Couldn't talk to hub at %s!\n", post_data.host);
      return 20;
   }
   
   g_thread_new("Posting thread", json_poster_func, &post_data); 

   while (getline(&line, &len, stdin) != -1)
   {
      input = parse_sensor_value(line);
      if (input != NULL)
      {
	 g_async_queue_push(queue, input);
      }
   }
   free(line);
   json_post_cleanup();
   return 0;
}
