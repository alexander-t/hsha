#include <stdio.h>
#include <malloc.h>
#include <time.h>
#include <glib.h>
#include <unistd.h>
#include <stdlib.h>
#include <signal.h>
#include <telldus-core.h>

GHashTable *data_type2s;

void sensor_callback(const char *protocol, const char *model,
		     int sensor_id, int data_type, const char *value,
		     int timestamp, int callback_id, void *context)
{
   printf("S%d,%s,%s\n", sensor_id,
	  (char *) g_hash_table_lookup(data_type2s, GINT_TO_POINTER(data_type)), value);
}

void exit_handler(int signum)
{
   printf("\nShutting down...");
   g_hash_table_destroy(data_type2s);
   tdClose();  
   exit(0);
}

int main(int argc, char* argv[])
{   
   signal(SIGINT, exit_handler);
   setbuf(stdout, NULL);
         
   tdInit();
   
   data_type2s = g_hash_table_new(&g_direct_hash, &g_direct_equal);
   g_hash_table_insert(data_type2s, GINT_TO_POINTER(1), "temp");
   g_hash_table_insert(data_type2s, GINT_TO_POINTER(2), "humidity");
   g_hash_table_insert(data_type2s, GINT_TO_POINTER(4), "rainrate");
   g_hash_table_insert(data_type2s, GINT_TO_POINTER(8), "raintotal");
   g_hash_table_insert(data_type2s, GINT_TO_POINTER(16), "winddirection");
   g_hash_table_insert(data_type2s, GINT_TO_POINTER(32), "windaverage");
   g_hash_table_insert(data_type2s, GINT_TO_POINTER(64), "windgust");

   tdRegisterSensorEvent(&sensor_callback, NULL);
   
   while (1)
   {
      sleep(10);
   }  
   return 0;
}

