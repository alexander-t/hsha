CC=gcc
CFLAGS=-Wall -O2 `pkg-config --cflags glib-2.0`

LGLIB=`pkg-config --libs glib-2.0`
LTELLDUS=-ltelldus-core
LCURL=-lcurl 

all: sensor_json_consumer

sensor_json_consumer: sensor_json_consumer.o json_post.o
	gcc -o sensor_json_consumer sensor_json_consumer.o json_post.o $(LGLIB) $(LCURL)

clean:
	rm -f *.o *~ sensor_json_consumer
