CC=gcc
CFLAGS=-Wall -O2 `pkg-config --cflags glib-2.0`

LGLIB=`pkg-config --libs glib-2.0`
LCURL=-lcurl

LIB_CHECK=-lcheck
LIB_PTHREAD=-lpthread
LIB_REALTIME=-lrt
LIB_MATH=-lm

TEST_LIBS=$(LIB_CHECK) $(LIB_PTHREAD) $(LIB_REALTIME) $(LIB_MATH)

all: sensor_json_consumer tests

sensor_json_consumer: sensor_json_consumer.o json_post.o parse.o
	gcc -o sensor_json_consumer sensor_json_consumer.o json_post.o parse.o $(LGLIB) $(LCURL)

tests: check_parse

check_parse: check_parse.o parse.o
	gcc -o check_parse check_parse.o parse.o $(TEST_LIBS) $(LGLIB)

clean:
	rm -f *.o *~ sensor_json_consumer check_parse
