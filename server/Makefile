CC=gcc
CFLAGS=-c -std=gnu99 -Wall -O2 `pkg-config --cflags glib-2.0`
TEST_LIBS=-lcheck -lpthread -lrt -lm
LDFLAGS=`pkg-config --libs glib-2.0` -lcurl -ltelldus-core $(TEST_LIBS)

all: captor sensor_json_consumer tests

captor: captor.o
	$(CC) $(LDFLAGS) $< -o $@

sensor_json_consumer: sensor_json_consumer.o json_post.o parse.o
	$(CC) $(LDFLAGS) $^ -o $@

tests: run_tests

.c.o:
	$(CC) $(CFLAGS) $< -o $@

clean:
	rm -f *.o *~ captor sensor_json_consumer run_tests *.xml
