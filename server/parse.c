#include "parse.h"
#include <stdlib.h>
#include <glib.h>
#include <string.h>
#include <stdio.h>
SensorValue *parse_sensor_value(const char *line)
{
   if (line == NULL || line[0] == '\0')
      return NULL;
  
   if (strlen(line) > 0 && line[0] != 'S')
      return NULL;

   // -1 = no first character
   gchar *line_to_parse = g_strndup(&line[1], strlen(line) - 1);
   line_to_parse[strcspn(line_to_parse, "\n")] = '\0';

   gchar** fields = g_strsplit(line_to_parse, ",", NOF_INPUT_TOKENS);
   g_free(line_to_parse);

   // Checking sizeof isn't sufficient. Too short strings will pass that check
   if (sizeof(fields) == NOF_INPUT_TOKENS + 1 && fields[NOF_INPUT_TOKENS] == NULL)
   {
      SensorValue *parsed = malloc(sizeof(SensorValue));
      parsed->sensor_id = atoi(fields[0]);
      parsed->data_type = strdup(fields[1]);
      parsed->value = strdup(fields[2]);
      g_strfreev(fields);
      return parsed;
   }
   else
   {
      g_strfreev(fields);
      return NULL;
   }
}
