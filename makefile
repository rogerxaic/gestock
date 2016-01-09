JAVAC=javac
JAVA=java
JFLAGS = -d build/ -s src/
RFLAGS = -cp build
XARGS=xargs $(JAVAC)
RUN=Gestock

all:
	@cd src; find org -name "*.java" | $(XARGS)
	@cd src; find com -name "*.java" | $(XARGS)
	@cd src; find gestock -name "*.java" | $(XARGS)
	@echo ""
	@echo "Vous devriez executer : "
	@echo "make install"
	@echo ""

run: install
	./$(RUN)

errors:
	@cd src; find gestock -name "*.java" | $(XARGS) -Xlint
	@cd src; find org -name "*.java" | $(XARGS)
	@cd src; find com -name "*.java" | $(XARGS)

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
