CC = gcc
CFLAGS = -g
OUTFILE = ya!c
LIBS = -lm

OFILES = t_exp.o


all: lexer.c parser.c parser.h
	$(CC) $(CFLAGS) apt.c symbolTable.c parser.c lexer.c -o $(OUTFILE) $(LIBS)

parser.c: ya.y
	bison -y -d $< -o $@ -r all

lexer.c: ya.lex
	flex -o $@ $<

%.o: %.c %.h
	$(CC) -c $(CFLAGS) -o $@ $<

clean: 
	rm -rf parser.c lexer.c parser.h
	rm -f *~
	rm -f *.o
	rm -f *.output

clean-all clean_all: clean
	rm -f $(OUTFILE)
