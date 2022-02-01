#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

// Return your favorite emoji.  Do not allocate new memory.
// (This should **really** be your favorite emoji, we plan to use this later in the semester. :))
char *emoji_favorite() {
  return "\xF0\x9F\x98\x81";
}

int isEmoji(const char *s) {
  unsigned int val = 0;
  for(size_t  i=0; i<strlen(s); i++) {
    val = (val << 8) | ((unsigned int)(s[i]) & 0xFF);
  }
  return (
    (val >= 14844092 /* U+203C */ && val <= 14912153 /* U+3299 */) ||
    (val >= 4036984960 /* U+1F000 */ && val <= 4036996031 /* U+1FAFF */ )
  );
}


// Count the number of emoji in the UTF-8 string `utf8str`, returning the count.  You should
// consider everything in the ranges starting from (and including) U+1F000 up to (and including) U+1FAFF.
int emoji_count(const unsigned char * utf8str) {
  int counterOfValidEmoji = 0;
  for (size_t i = 0; i < strlen(utf8str) - 3; i++) {
    char * tmpCopyStr = malloc(5);
    //tmpcopystr[5]?
    tmpCopyStr[4] = '\0';
    for (int j = 0; j < 4; j++) {
      tmpCopyStr[j] = utf8str[i + j];
    }
    if (isEmoji(tmpCopyStr) == 1) {
      counterOfValidEmoji++;
    }
    free(tmpCopyStr);
  }
  return counterOfValidEmoji;
}

// Return a random emoji stored in new heap memory you have allocated.  Make sure what
// you return is a valid C-string that contains only one random emoji.
char * emoji_random_alloc() {
  char * random = malloc(5);
  random[0] = '\xF0';
  random[1] = '\x9F';
  int num2 = (rand() % (171 - 128 + 1)) + 128;
  int num3 = (rand() % (191 - 128 + 1)) + 128;
  random[2] = num2; //为什么在这里数字也行，最后都会变成UTF-8 or ASCII？
  random[3] = num3;
  random[4] = '\0';

  return random; //what is gonna happen here?
}


// Modify the UTF-8 string `utf8str` to invert the FIRST character (which may be up to 4 bytes)
// in the string if it the first character is an emoji.  At a minimum:
// - Invert "😊" U+1F60A ("\xF0\x9F\x98\x8A") into ANY non-smiling face.
// - Choose at least five more emoji to invert.
void emoji_invertChar(unsigned char * utf8str) {
  int length = strlen(utf8str);
  utf8str[length] = '\0';
  
  if (strcmp(utf8str, "\xF0\x9F\x98\x8A") == 0) {
        utf8str[3] = '\x91';
  }

  for (size_t i = 0; i < strlen(utf8str) - 3; i++) {
    char * tmpCopyStr = malloc(5);
    tmpCopyStr[4] = '\0';
    for (int j = 0; j < 4; j++) {
      tmpCopyStr[j] = utf8str[i + j];
    }
    if (isEmoji(tmpCopyStr) == 1) {
      utf8str[i + 3] = '\x94';
      utf8str[i + 3] = '\x8F';
      utf8str[i + 3] = '\x9A';
      utf8str[i + 3] = '\xAB';
      utf8str[i + 3] = '\xB2';
      utf8str[i + 3] = '\xB9';
    }
    free(tmpCopyStr);
  }
}

// Modify the UTF-8 string `utf8str` to invert ALL of the character by calling your
// `emoji_invertChar` function on each character.
void emoji_invertAll(unsigned char *utf8str) {
  emoji_invertChar(utf8str);
}

// Reads the full contents of the file `fileName, inverts all emojis, and
// returns a newly allocated string with the inverted file's content.
unsigned char * emoji_invertFile_alloc(const char * fileName) {
  char * buffer = malloc(90);
  long length;
  FILE * f = fopen (fileName, "rb");
  if (f == NULL) {
    free(buffer);
    return NULL;
  }
    if (f) {
      fseek (f, 0, SEEK_END);
      length = ftell (f);
      fseek (f, 0, SEEK_SET);
      buffer = malloc (length);
      if (buffer) {
        fread (buffer, 1, length, f);
      }
      fclose (f);
    }
  buffer[length] = '\0';
  if (buffer[0] == '\0') {
    return NULL;
  } 
  emoji_invertAll(buffer);
  return buffer;
}
