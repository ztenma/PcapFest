
JFLAGS = -g
JC = javac

.SUFFIXES: .java .class

sources = $(wildcard *.java)
classes = $(sources:.java=.class)

all: $(classes)

clean :
	rm -f *.class

%.class : %.java
	$(JC) $(JFLAGS) $<
