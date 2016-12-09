#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdbool.h>
#include <glib.h>
#include "debug.h"
#include "input.h"
#include "parse.h"
#include "json_post.h"
#include "consumer_common.h"

#define MESSAGE_SIZE 4096

char json_message[MESSAGE_SIZE];

struct PostData
{
   char *host;
   GAsyncQueue *queue;
};

void *json_poster_func(void *data) {
   struct PostData *post_data = (struct PostData *) data;
   GAsyncQueue *queue = post_data->queue;
   SensorValue *sensor_value;
   char url[URL_SIZE];

   while (1)
   {
      sensor_value = g_async_queue_pop(queue);

      json_post_write_sensor_event(json_message, MESSAGE_SIZE,
				   sensor_value->sensor_id,
				   sensor_value->value,
				   sensor_value->data_type);

      snprintf(url, URL_SIZE, "http://%s/sensor/%d",
	       post_data->host, sensor_value->sensor_id);
      debug_print("%s: %s\n", url, json_message);

      json_post_send(url, json_message);
      sensor_value_free(sensor_value);
   }
   return NULL;
}

int main(int argc, char **argv) {
   struct sensor_value *input;
   char *line = NULL;
   size_t len = 0;
   GAsyncQueue *queue = g_async_queue_new();
   struct PostData post_data;
   post_data.queue = queue;

   setbuf(stdout, NULL);
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

   debug_print("%s\n", "Running");
   while (getline(&line, &len, stdin) != -1)
   {
      debug_print("<< %s", line);
      input = parse_sensor_value(line);
      if (input != NULL)
      {
	 debug_print("%s", "parsed and enqueued\n");
	 g_async_queue_push(queue, input);
      }
      else
      {
	 debug_print(">> %s", line);
	 printf("%s", line);
      }
   }
   free(line);
   json_post_cleanup();
   return 0;
}
