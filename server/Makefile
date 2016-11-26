CC=gcc
CFLAGS=-std=gnu99 -Wall -O2 `pkg-config --cflags glib-2.0`

LIB_GLIB=`pkg-config --libs glib-2.0`
LIB_CURL=-lcurl
LIB_TELLDUS=-ltelldus-core

LIB_CHECK=-lcheck
LIB_PTHREAD=-lpthread
LIB_REALTIME=-lrt
LIB_MATH=-lm

TEST_LIBS=$(LIB_CHECK) $(LIB_PTHREAD) $(LIB_REALTIME) $(LIB_MATH)

all: captor sensor_json_consumer tests

captor: captor.o 
	gcc -o captor captor.o $(LIB_TELLDUS) $(LIB_GLIB)

sensor_json_consumer: sensor_json_consumer.o json_post.o parse.o
	gcc -o sensor_json_consumer sensor_json_consumer.o json_post.o parse.o $(LIB_GLIB) $(LIB_CURL)

tests: run_tests

run_tests: run_tests.o check_parse.o parse.o check_json_post.o json_post.o
	gcc -o run_tests run_tests.o check_parse.o parse.o check_json_post.o json_post.o $(TEST_LIBS) $(LIB_GLIB) $(LIB_CURL)

clean:
	rm -f *.o *~ captor sensor_json_consumer run_tests *.xml
