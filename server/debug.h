#ifndef __DEBUG_H
#define __DEBUG_H

#define debug_print(fmt, ...)					\
  do { if (DEBUG) fprintf(stderr, "%s:%d:%s(): " fmt, __FILE__, \
			  __LINE__, __func__, __VA_ARGS__); } while (0)
#endif
