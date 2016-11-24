CC=gcc
CFLAGS=-Wall -O2 `pkg-config --cflags glib-2.0`

LIB_GLIB=`pkg-config --libs glib-2.0`
LIB_CURL=-lcurl
LIB_TELLDUS=-ltelldus-core

LIB_CHECK=-lcheck
LIB_PTHREAD=-lpthread
LIB_REALTIME=-lrt
LIB_MATH=-lm

TEST_LIBS=$(LIB_CHECK) $(LIB_PTHREAD) $(LIB_REALTIME) $(LIB_MATH)

all: sensor_dispatcher sensor_json_consumer tests

sensor_dispatcher: sensor_dispatcher.o 
	gcc -o sensor_dispatcher sensor_dispatcher.o $(LIB_TELLDUS) $(LIB_GLIB)

sensor_json_consumer: sensor_json_consumer.o json_post.o parse.o
	gcc -o sensor_json_consumer sensor_json_consumer.o json_post.o parse.o $(LIB_GLIB) $(LIB_CURL)

tests: check_parse 

check_parse: check_parse.o parse.o
	gcc -o check_parse check_parse.o parse.o $(TEST_LIBS) $(LIB_GLIB)

check_json_post: check_json_post.o json_post.o
	gcc -o check_json_post check_json_post.o json_post.o $(TEST_LIBS) $(LIB_GLIB) $(LIB_CURL)

clean:
	rm -f *.o *~ sensor_dispatcher sensor_json_consumer check_parse
