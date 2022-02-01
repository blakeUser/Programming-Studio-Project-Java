#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "emoji.h"
#include "emoji-translate.h"



void emoji_init(emoji_t *emoji) {
    for(int i = 0; i < 10; i++) {
        emoji->emo[i] = NULL;
        emoji->str[i] = NULL;
        emoji->len[i] = 0;
    }
    emoji->curr = 0;
}

void emoji_add_translation(emoji_t *emoji, const unsigned char *source, const unsigned char *translation) {
    emoji->emo[emoji->curr] = (char*)malloc(100);
    int k = 0;
    while(source[k]) {
      emoji->emo[emoji->curr][k] = source[k];
      k++;
    }
    emoji->str[emoji->curr] = (char*)malloc(100);
    k = 0;
    while(translation[k]) {
      emoji->str[emoji->curr][k] = translation[k];
      k++;
    }
    emoji->len[emoji->curr] = k;
    // printf("%d\n", k);

    // emoji->str[emoji->curr] = translation;
   
    emoji->curr = emoji->curr + 1;
}

// Translates the emojis contained in the file `fileName`.
// unsigned char *value(unsigned char * str, int k, emoji_t *emoji) {
//     for(int i = 0; i < emoji->curr; i++) {
//       if(str[k] == emoji->emo[i][0] && str[k + 1] == emoji->emo[i][1] && str[k + 2] == emoji->emo[i][2] && str[k + 3] == emoji->emo[i][3]) {
//         return emoji->str[i];
//       }
//     }
//     return NULL;
// }
const unsigned char *emoji_translate_file_alloc(emoji_t *emoji, const char *fileName) {
  unsigned char* str = malloc(150);
  FILE *fd = fopen(fileName, "r");
  char ch;
  int cursor = 0;
  if (fd == NULL) {
    return NULL;
  }

  while((ch=fgetc(fd))!=EOF) {
    str[cursor] = ch;
    cursor++;
  }
  fclose(fd);
  str[cursor] = 0;

  char * res = NULL;
  res = (char*)malloc(100);
  int res_index = 0;
  int k = 0;
  if(!emoji->emo[0]) {
    return str;
  }
  while(str[k]) {
    if(str[k + 1] != NULL&&str[k + 2] != NULL&&str[k + 3] != NULL) {
      const char* tmp = NULL;
      int record = 0;
      int maxx = 0;
      for(int i = 0; i < emoji->curr; i++) {
        int index = 0;
        while(emoji->emo[i][index] && str[k + index]) {
            if(emoji->emo[i][index] != str[k + index]) {
              break;
            }
            else{
              index++;
              if(maxx < index && maxx/4 < index/4) {
                maxx = 4*(index/4);
               
                record = i;
             
                if(maxx >= 4) {
                  tmp = emoji->str[i];
                
                }
              }
            }
        }
      }
      
      if(tmp != NULL) {
        printf("%d\n", record);
        for(int ii = 0 ;ii < emoji->len[record]; ii++) {
          
          res[res_index] = emoji->str[record][ii];
          res_index++;
        }
        k = k + maxx;
       
        maxx = 0;
        tmp = NULL;
      }
      else{
        res[res_index] = str[k];
        k++;
        res_index++;
      }
    }
    else{
        res[res_index] = str[k];
        k++;
        res_index++;
    }
  }
  return res;
}

void emoji_destroy(emoji_t *emoji) {
}