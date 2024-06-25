#Define variables
BUILD_DIR := build
JAR_DIR := cs8803_bin
SRC_DIR := src
ANTLR_SRC := $(SRC_DIR)/antlr
TIGER_SRC := $(SRC_DIR)/tiger
TIGERCOMPILER_SRC := $(SRC_DIR)/tigercompiler
MAIN_CLASS := tigercompiler.Tigerc
JAR_NAME := tigerc.jar
ANTLR := /usr/local/lib/antlr-4.11.1-complete.jar
GRAMMAR := Tiger.g4

# Source files
JAVA_FILES=$(shell find $(SRC_DIR) -name "*.java")

ANTLR_LIBS := \
	$(BUILD_DIR)/javax \
	$(BUILD_DIR)/org

# ANTLR generated files
ANTLR_SRC_FILES := \
	$(ANTLR_SRC)/TigerLexer.java \
	$(ANTLR_SRC)/TigerParser.java \
	$(ANTLR_SRC)/TigerBaseVisitor.java \
	$(ANTLR_SRC)/TigerVisitor.java

.PHONY: all antlr compile jar clean_antlr clean_build clean

all : jar

# Pattern rule to generate ANTLR files
$(ANTLR_SRC)/%.java: $(GRAMMAR)
	java -jar $(ANTLR) -o $(ANTLR_SRC) -visitor $(GRAMMAR)

# Rule to generate ANTLR files only
antlr: $(ANTLR_SRC_FILES)
	@echo "ANTLR files generated."

# Compile Java files
compile: $(ANTLR_SRC_FILES)
	mkdir -p $(BUILD_DIR)
	javac -d $(BUILD_DIR) -cp $(ANTLR) $(JAVA_FILES)

# Extract ANTLR libraries
$(ANTLR_LIBS):
	mkdir -p $(BUILD_DIR)
	cd $(BUILD_DIR) && jar xf $(ANTLR) && cd ..
	rm -rf $(BUILD_DIR)/META-INF

jar: compile $(ANTLR_LIBS)
	mkdir -p $(JAR_DIR)
	jar -cfe $(JAR_DIR)/$(JAR_NAME) $(MAIN_CLASS) -C $(BUILD_DIR) .

clean_antlr:
	rm -rf $(ANTLR_SRC)/*.java $(ANTLR_SRC)/*.tokens $(ANTLR_SRC)/*.interp

# clean all directories in the build directory except for the ANTLR libraries
clean_build:
	@echo "Cleaning all directories in $(BUILD_DIR) except for ANTLR libraries..."
	@for dir in $(BUILD_DIR)/*; do \
		if [ -d $$dir ] && [ "$$dir" != "$(BUILD_DIR)/javax" ] && [ "$$dir" != "$(BUILD_DIR)/org" ]; then \
			echo "Removing $$dir"; \
			rm -rf $$dir; \
		fi \
	done

	@echo "Cleaning jar file in $(JAR_DIR)..."
	rm -rf $(JAR_DIR)/$(JAR_NAME)


clean: clean_antlr clean_build
	rm -rf $(JAR_DIR) $(BUILD_DIR)