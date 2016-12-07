CC=gcc
CFLAGS=-c -std=gnu99 -Wall -O2 `pkg-config --cflags glib-2.0`
LDFLAGS=`pkg-config --libs glib-2.0` -lcurl -ltelldus-core -lcheck -lpthread -lrt -lm

all: captor sensor_json_consumer device_json_consumer tests

captor: captor.o
	$(CC) $(LDFLAGS) $< -o $@

sensor_json_consumer: sensor_json_consumer.o json_post.o parse.o consumer_common.o
	$(CC) $(LDFLAGS) $^ -o $@

device_json_consumer: device_json_consumer.o json_post.o parse.o consumer_common.o
	$(CC) $(LDFLAGS) $^ -o $@

tests: run_tests

run_tests: run_tests.o check_parse.o check_json_post.o parse.o json_post.o
	$(CC) -o $@ $^ $(LDFLAGS)

.c.o:
	$(CC) $(CFLAGS) $< -o $@

clean:
	rm -f *.o *~ captor sensor_json_consumer device_json_consumer run_tests *.xml
