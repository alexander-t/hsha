#include <stdio.h>
#include <malloc.h>
#include <time.h>
#include <glib.h>
#include <unistd.h>
#include <stdlib.h>
#include <signal.h>
#include <telldus-core.h>

GHashTable *data_type2unit;
GHashTable *device_id2name;

void sensor_callback(const char *protocol, const char *model,
		     int sensor_id, int data_type, const char *value,
		     int timestamp, int callback_id, void *context)
{
   printf("S%d,%s,%s\n", sensor_id,
	  (char *) g_hash_table_lookup(data_type2unit, GINT_TO_POINTER(data_type)),
	  value);
}


void device_callback(int device_id, int method, const char *data,
		     int callback_id, void *context)
{
   printf("D%d,%s,%s\n", device_id,
	  method == TELLSTICK_TURNON ? "on" : "off",
	  (char *) g_hash_table_lookup(device_id2name, GINT_TO_POINTER(device_id)));
}


void exit_handler(int signum)
{
   printf("\nShutting down...");
   g_hash_table_destroy(data_type2unit);
   g_hash_table_destroy(device_id2name);
   tdClose();  
   exit(0);
}

GHashTable *create_unit_table() {
   GHashTable *units = g_hash_table_new(&g_direct_hash, &g_direct_equal);
   g_hash_table_insert(units, GINT_TO_POINTER(1), "temp");
   g_hash_table_insert(units, GINT_TO_POINTER(2), "humidity");
   g_hash_table_insert(units, GINT_TO_POINTER(4), "rainrate");
   g_hash_table_insert(units, GINT_TO_POINTER(8), "raintotal");
   g_hash_table_insert(units, GINT_TO_POINTER(16), "winddirection");
   g_hash_table_insert(units, GINT_TO_POINTER(32), "windaverage");
   g_hash_table_insert(units, GINT_TO_POINTER(64), "windgust");
   return units;
}

GHashTable *create_name_table() {
   GHashTable *names = g_hash_table_new(&g_direct_hash, &g_direct_equal);
   for (int i = 0; i < tdGetNumberOfDevices(); i++)
   {
      int device_id = tdGetDeviceId(i);
      g_hash_table_insert(names, GINT_TO_POINTER(device_id), tdGetName(device_id));
   }
   return names;
}

int main(int argc, char* argv[])
{   
   signal(SIGINT, exit_handler);
   setbuf(stdout, NULL);
         
   tdInit();
   
   data_type2unit = create_unit_table();
   device_id2name = create_name_table();
   
   tdRegisterSensorEvent(&sensor_callback, NULL);
   tdRegisterDeviceEvent(&device_callback, NULL);
      
   while(1)
   {
      sleep(10);
   }  
   return 0;
}

