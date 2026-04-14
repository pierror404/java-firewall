# Simple Windows Java&C Firewall
A simple Windows compatible Firewall built in Java and C as a compiling infrastructure for my language EasyWall.\
The project strucure:
```
.
└── my-firewall
    ├── native
    │   ├── firewall.c
    │   ├── firewall_NativeBridge.h
    │   └── windivert.h
    ├── pom.xml
    └── src
        ├── main
        │   └── java
        │       ├── elements
        │       │   ├── ApplicationLayerProtocol.java
        │       │   ├── Direction.java
        │       │   ├── IP.java
        │       │   ├── IProtocol.java
        │       │   ├── IPv4.java
        │       │   ├── IPv4Subnet.java
        │       │   ├── IPv6.java
        │       │   ├── IPv6Subnet.java
        │       │   ├── Layer.java
        │       │   ├── MyPacket.java
        │       │   ├── NetPort.java
        │       │   ├── Network.java
        │       │   ├── NetworkLayerProtocol.java
        │       │   ├── ProtocolFactory.java
        │       │   ├── Subnet.java
        │       │   └── TransportLayerProtocol.java
        │       ├── exceptions
        │       │   ├── IllegalIPv4Exception.java
        │       │   ├── IllegalPortException.java
        │       │   └── IllegalSubnetException.java
        │       ├── firewall
        │       │   ├── FirewallEngine.java
        │       │   ├── NativeBridge.class
        │       │   └── NativeBridge.java
        │       ├── main
        │       │   └── Main.java
        │       ├── rules
        │       │   ├── Action.java
        │       │   ├── Endpoint.java
        │       │   ├── IRule.java
        │       │   ├── ITriggeringRule.java
        │       │   └── Rule.java
        │       └── utils
        │           ├── IPv4SubnetUtils.java
        │           ├── IPv4Utils.java
        │           ├── IPv6Utils.java
        │           ├── MyPacketParser.java
        │           └── RuleUtils.java
        └── test
            └── java
                └── test
                    └── elements
                        ├── IPv4Test.java
                        ├── NetworkTest.java
                        └── SubnetMaskTest.java
```
Requirements to build and run:
- Java 22+
- Maven 3.9.14 (I used it, maybe an older version could be fine too)
- MSVC to compile the C-based part
- x64 architecture
- Windows operating system: for Linux based systems, I'm sorry...I will provide a future version with Linux support too

To build:
```bat
cd my-firewall
mvn clean package
```
To run:
```bat
cd my-firewall
java "-Djava.library.path=target" --enable-native-access=ALL-UNNAMED -jar target\my-firewall-0.0.1-SNAPSHOT.jar
```
I've just tested the classes that are shown in the `test` directory in the project tree, but logically everything should work. Imma test it later :).\
I will also, some day in the future, add the support to linux and macOS systems, but nowadays this can only run on Windows systems.
