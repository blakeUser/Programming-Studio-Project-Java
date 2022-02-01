#pragma once
#ifdef __cplusplus
extern "C" {
#endif

typedef struct _emoji_t {
    unsigned char **emo[10];
    unsigned char **str[10];
    unsigned int len[10];
    int curr;
} emoji_t;

void emoji_init(emoji_t *emoji);
void emoji_add_translation(emoji_t *emoji, const unsigned char *source, const unsigned char *translation);
const unsigned char *emoji_translate_file_alloc(emoji_t *emoji, const char *fileName);
void emoji_destroy(emoji_t *emoji);

#ifdef __cplusplus
}
#endif
