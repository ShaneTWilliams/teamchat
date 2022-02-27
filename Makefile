JAVAC = javac
JAVA = java

JAVA_FX = lib/javafx-sdk-17.0.2/lib
H2 = lib/h2-2.1.210.jar

ROOT_DIR = .
CLIENT_DIR = client
SERVER_DIR = server
COMMON_DIR = common

CLIENT_MAIN = $(CLIENT_DIR).main.Main
CLIENT_MAIN_SRC = $(CLIENT_DIR)/main/Main.java

SERVER_MAIN = $(SERVER_DIR).main.Main
SERVER_MAIN_SRC = $(SERVER_DIR)/main/Main.java

ALL_SRC_FILES = $(shell find $(ROOT_DIR) -name "*.java")
SERVER_SRC_FILES = $(filter-out $(CLIENT_MAIN), $(ALL_SRC_FILES))
CLIENT_SRC_FILES = $(filter-out $(SERVER_MAIN), $(ALL_SRC_FILES))

MODULES = javafx.controls,javafx.fxml
MODULE_PATH = $(JAVA_FX)

CLASSPATH = .:$(H2)

build_client:
	$(JAVAC) -cp $(CLASSPATH) --module-path $(MODULE_PATH) --add-modules $(MODULES) $(CLIENT_SRC_FILES)

build_server:
	$(JAVAC) -cp $(CLASSPATH) $(SERVER_SRC_FILES)

run_client: build_client
	$(JAVA) -cp $(CLASSPATH) --module-path $(MODULE_PATH) --add-modules $(MODULES) $(CLIENT_MAIN) $(ARGS)

run_server: build_server
	$(JAVA) -cp $(CLASSPATH) $(SERVER_MAIN) $(ARGS)

clean_client:
	rm $(CLIENT_DIR)/**/*.class 2> /dev/null || true

clean_server:
	rm $(SERVER_DIR)/**/*.class 2> /dev/null || true

clean_common:
	rm $(COMMON_DIR)/**/*.class 2> /dev/null || true

clean: clean_client clean_server clean_common

clean_db:
	rm $(ROOT_DIR)/*.db 2> /dev/null || true
