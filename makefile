JAVAC=javac
JAVA=java
JFLAGS = -d build/ -s src/
RFLAGS = -cp build
RUN=Gestock

all:
	@mkdir -p build
	@$(JAVAC) $(JFLAGS) src/*/*.ja*
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
	@echo "export COLUMNS=\$$(tput cols)" >> $(RUN);
	@echo "export LINES=\$$(tput lines)" >> $(RUN);
	@echo "">>$(RUN);
	@echo "java -cp build battleship.Battleship lc">>$(RUN);
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
