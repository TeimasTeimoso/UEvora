
JAVA = java
JAVAFLAGS =
JAVAC = javac
JAVACFLAGS =

JCLASSPATH = .:resources/*
CUP = cup
CUPFLAGS = 

JFLEX = jlex 
JFLEXFLAGS = 

CLASSES = parser.class Yylex.class \
	  Main.class TISC.class
	  # etc ...

INTERMEDIATE_FILES = registos.lex.java sym.java parser.java Yylex.java

.INTERMEDIATE: sym.java
.SECONDARY: registos.lex.java Yylex.java

.PHONY: all clean run

%.class: %.java
	CLASSPATH=$(JCLASSPATH) $(JAVAC) $(JAVACFLAGS) $<

all: $(CLASSES)

Main.class: Main.java TISC.class parser.class

parser.java sym.java: registos.cup
	$(CUP) $(CUPFLAGS) $<
	@[ -s $@ ] || { rm -f $@; exit 1; }

parser.class: parser.java Yylex.class
	CLASSPATH=$(JCLASSPATH) $(JAVAC) $(JAVACFLAGS) $<

sym.class: sym.java

registos.lex.java: registos.lex
	$(JFLEX) $(JFLEXFLAGS) $<

Yylex.java:
	ln -sf registos.lex.java $@

Yylex.class: Yylex.java registos.lex.java sym.class
	CLASSPATH=$(JCLASSPATH) $(JAVAC) $(JAVACFLAGS) $<

clean:
	$(RM) $(INTERMEDIATE_FILES)
	$(RM) *.class *~

run:
	CLASSPATH=$(JCLASSPATH) $(JAVA) $(JAVAFLAGS) Main
