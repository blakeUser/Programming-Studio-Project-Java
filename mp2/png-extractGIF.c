#include <stdio.h>
#include <stdlib.h>
#include "lib/png.h"
#include <string.h>
#include <errno.h>

int png_extractGIF(const char *png_filename, const char *gif_filename) {

  PNG *png = PNG_open(png_filename, "r");

  if (!png) { return ERROR_INVALID_FILE; }

  FILE * out = fopen(gif_filename, "w");

  printf("PNG Header written.\n");

  long step = 8;

  while (1) {
    // Read chunk and ensure we get a valid result (exit on error):
    PNG_Chunk chunk;
    
    if (PNG_read(png, &chunk) == 0) {
      //PNG_close(png);
      //PNG_close(out);
      return ERROR_INVALID_CHUNK_DATA;
    }

    step = step + chunk.len + 4 + 4 + 4;

    //printf("%d position 0 is  \n", ftell(out->pointer_key));
    //fseek(out->pointer_key, chunk.len, SEEK_CUR);
    //printf("%d position is  \n", ftell(out->pointer_key));
    // Report data about the chunk to the command line:
    //bytesWritten = PNG_write(out, &chunk);
    //printf("PNG chunk %s written (%lu bytes)\n", chunk.type, bytesWritten);
    // Check for the "IEND" chunk to exit:
    if ( strcmp(chunk.type, "uiuc") == 0 ) {

      fseek(out, step, SEEK_SET);
       printf("\n i am all in \n");
      // printf("%d the png position is \n ",  ftell(png->pointer_key));
      // printf("%d the out position is \n ",  ftell(out->pointer_key));

      fwrite(chunk.data, sizeof(char), chunk.len, out);

      //printf("PNG chunk %s written (%lu bytes)\n", chunk.type, bytesWritten);
      //printf("%d the` after out position is \n ",  ftell(out->pointer_key));

      fclose(out);
      PNG_free_chunk(&chunk);
      break;  
    }
  }

  return 0;  // Change the to a zero to indicate success, when your implementaiton is complete.
}


int main(int argc, char *argv[]) {
  // Ensure the correct number of arguments:
  if (argc != 3) {
    printf("Usage: %s <PNG File> <GIF Name>\n", argv[0]);
    return ERROR_INVALID_PARAMS;
  }

  
  return png_extractGIF(argv[1], argv[2]);
}
