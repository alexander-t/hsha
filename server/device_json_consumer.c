#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdbool.h>
#include <glib.h>
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
   DeviceValue *device_value;
   char url[URL_SIZE];

   while (1)
   {
      device_value = g_async_queue_pop(queue);

      json_post_write_device_event(json_message, MESSAGE_SIZE,
				   device_value->device_id,
				   device_value->value,
				   device_value->device_name);

      snprintf(url, URL_SIZE, "http://%s/device/%d",
	       post_data->host, device_value->device_id);
      json_post_send(url, json_message);
      device_value_free(device_value);
   }
   return NULL;
}

int main(int argc, char **argv) {
   struct device_value *input;
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
      input = parse_device_value(line);
      if (input != NULL)
	 g_async_queue_push(queue, input);
      else
	 printf("%s", line);
   }
   free(line);
   json_post_cleanup();
   return 0;
}
