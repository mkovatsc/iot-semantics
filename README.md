# IoT Semantics
Practical Semantics for the Internet of Things

## Prerequisites

### swipl

Install the SWI-Prolog environment for basic logics. Should work using apt-get under linux, else use http://www.swi-prolog.org/

### eye

Install the Euler YAP Engine from the `euler` subdirectory using the .bat or .sh script.

### iot-semantics

Compile and install everything through `mvn clean install`.

## Getting Started

### Semantic-IDE

1. Start the Semantic-IDE with `java -jar semantic-ide-1.0-SNAPSHOT.jar`.
2. Go to `http://localhost:8080/`.
3. Create a *Virtual Workspace*.
4. Load a *Configuration* from the menu in the upper right corner.
5. Use the navigation bar on the top to browse.
6. The *Execution Plan Interface* visualizes the requests resulting from a query.

### Live System

1. Get a CoRE Resource Directory (RD) and run it locally, e.g., cf-rd from http://www.eclipse.org/californium .
2. Run the reasoning server with `java -jar reasoning-server-1.0-SNAPSHOT.jar`.
3. Run demonstrator devices that register with the RD and provide RESTdesc descriptions.
4. Optionally connect the Semantic-IDE by creating a workspace that connects to the local reasoning-server (`coap://localhost:5681` by default).
