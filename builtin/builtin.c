#include "malloc.h"
#include "stdio.h"
#include "string.h"

struct string {
    int length;
    char s[0];
};

typedef struct string *string;

typedef struct array *array;

int* __mallocObject__(int size) {
    return malloc(size);
}

int* __mallocArray__(int size) {
    int* ret = malloc(sizeof(int) + size * sizeof(int));
    *ret = size;
    return ret + 1;
}

string __mallocString__(int length) {
    string ret = malloc(sizeof(int) + (length + 1) * sizeof(char));
    ret -> length = length;
    ret -> s[length] = '\0';
    return ret;
}

int __getInt__() {
    int a;
    scanf("%d", &a);
    return a;
}

char input[257];

string __getString__() {
    scanf("%s", input);
    string ret = __mallocString__(strlen(input));
    strcpy(ret->s, input);
    return ret;
}

void __print__(string str) {
    printf("%s", str -> s);
}

void __println__(string str) {
    puts(str -> s);
}

void __printInt__(int i) {
    printf("%d", i);
}

void __printlnInt__(int i) {
    printf("%d\n", i);
}

char dStr[13];

string __toString__(int i) {
    char* c = dStr;
    int neg = 0;
    if (i < 0) {
        neg = 1;
        i = -i;
    }
    if (i == 0) *(c++) = '0';
    while (i) {
        *(c++) = i % 10 + '0';
        i /= 10;
    }
    int length = c - dStr;
    string ret = __mallocString__(neg + length);
    char* t = ret -> s;
    if (neg) *(t++) = '-';
    while (c != dStr) {
        *(t++) = *(--c);
    }
    return ret;
}

int __stringLength__(string str) {
    return str -> length;
}

int __stringOrder__(string str, int index) {
    return str -> s[index];
}

int __stringParseInt__(string str) {
    int i;
    sscanf(str -> s, "%d", &i);
    return i;
}

string __stringSubstring__(string str, int l, int r) {
    string ret = __mallocString__(r - l);
    for (int i = 0, j = l; j < r; i++, j++)
        ret -> s[i] = str -> s[j];
    return ret;
}

string __stringConcatenate__(string s1, string s2) {
    string ret = __mallocString__(s1 -> length + s2 -> length);
    char* c = ret -> s;
    for (int i = 0; i < s1 -> length; ++i)
        *(c++) = s1 -> s[i];
    for (int j = 0; j < s2 -> length; ++j)
        *(c++) = s2 -> s[j];
    return ret;
}

char __stringEqual__(string s1, string s2) {
    for (char *a = s1 -> s, *b = s2 -> s; ; a++, b++) {
        if (*a != *b) return 0;
        if (*a == '\0' || *b == '\0') return 1;
    }
}

char __stringNeq__(string s1, string s2) {
    for (char *a = s1 -> s, *b = s2 -> s; ; a++, b++) {
        if (*a != *b) return 1;
        if (*a == '\0' || *b == '\0') return 0;
    }
}

char __stringLess__(string s1, string s2) {
    for (char *a = s1 -> s, *b = s2 -> s; ; a++, b++) {
        if (*a < *b) return 1;
        if (*a > *b || *a == '\0' || *b == '\0') return 0;
    }
}

char __stringLeq__(string s1, string s2) {
    for (char *a = s1 -> s, *b = s2 -> s; ; a++, b++) {
        if (*a > *b) return 0;
        if (*a < *b || *a == '\0' || *b == '\0') return 1;
    }
}

char __stringGreater__(string s1, string s2) {
    for (char *a = s1 -> s, *b = s2 -> s; ; a++, b++) {
        if (*a > *b) return 1;
        if (*a < *b || *a == '\0' || *b == '\0') return 0;
    }
}

char __stringGeq__(string s1, string s2) {
    for (char *a = s1 -> s, *b = s2 -> s; ; a++, b++) {
        if (*a < *b) return 0;
        if (*a > *b || *a == '\0' || *b == '\0') return 1;
    }
}

int __arraySize__(int* a) {
    return *(a - 1);
}