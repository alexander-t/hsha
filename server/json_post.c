#include <curl/curl.h>
#include "json_post.h"

CURL *curl;
CURLcode res;
struct curl_slist *http_headers = NULL;

int json_post_init()
{
   res = curl_global_init(CURL_GLOBAL_ALL);
   if (res != CURLE_OK)
   {
      return 0;
   }
   
   curl = curl_easy_init();
   if (curl)
   {
      http_headers = curl_slist_append(http_headers, "Content-Type: application/json");
      // curl_easy_setopt(curl, CURLOPT_VERBOSE, 1L);
      curl_easy_setopt(curl, CURLOPT_HTTPHEADER, http_headers);
   }
   else
   {
      curl_global_cleanup();
      return JSON_POST_INIT_FAILED;;
   }
   return JSON_POST_OK;;
}

int json_post_get(const char* url, bool verbose)
{
   curl_easy_setopt(curl, CURLOPT_CUSTOMREQUEST, "GET");
   curl_easy_setopt(curl, CURLOPT_URL, url);
   curl_easy_setopt(curl, CURLOPT_VERBOSE, verbose ? 1L : 0);
   return curl_easy_perform(curl) == CURLE_OK ? JSON_POST_OK : JSON_POST_GET_FAILED;
}

int json_post_send(const char* url, const char* message)
{
   curl_easy_setopt(curl, CURLOPT_URL, url);
   curl_easy_setopt(curl, CURLOPT_CUSTOMREQUEST, "PUT");
   curl_easy_setopt(curl, CURLOPT_POSTFIELDS, message);
   return curl_easy_perform(curl) == CURLE_OK ? JSON_POST_OK : JSON_POST_PUT_FAILED;
}

void json_post_cleanup()
{
   curl_slist_free_all(http_headers);
   curl_easy_cleanup(curl);
   curl_global_cleanup();
}

void json_post_write_sensor_event(char* dest, size_t max_length, int sensor_id,
				  const char* value, const char* data_type)
{
   snprintf(dest, max_length, "{\"sensor_id\":\"%d\",\"value\":\"%s\",\"data_type\":\"%s\"}",
	    sensor_id, value, data_type);
}
