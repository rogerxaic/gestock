JAVAC=javac
JAVA=java
JFLAGS = -d build/ -s src/
RFLAGS = -cp build
RUN=Gestock

all:
	@cd src
	@find org -name "*.java" | xargs $(JAVAC)
	@find gestock -name "*.java" | xargs $(JAVAC)
	@cd ..
	@echo ""
	@echo "Vous devriez executer : "
	@echo "make install"
	@echo ""

run: install
	./$(RUN)

errors:
	@mkdir -p build/
	$(JAVAC) $(JFLAGS) src/*/*.ja* -Xlint

install:
	@echo "#!/bin/bash" > $(RUN);
	@echo "" >> $(RUN);
	@echo "java -cp src gestock.Gestock">>$(RUN);
	@chmod +x $(RUN)
	
	@echo ""
	@echo "Vous devriez executer : "
	@echo "./$(RUN)"
	@echo ""

update:
	git pull origin master

push:
	git add .
	git status
	git commit -m "$$$$"
	git push origin master
