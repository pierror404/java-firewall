A simple Firewall built in Java and C as a compiling infrastructure for my language EasyWall.\
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
I will provide every information to compile and run the project later when I will do it myself. \
I've just tested the classes that are shown in the `test` directory in the project tree, but logically everything should work. Imma test it later.
