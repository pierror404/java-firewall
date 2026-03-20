package rules;

import java.util.Optional;

import elements.IPv4;
import elements.NetPort;
import elements.Network;

public record Endpoint(Optional<IPv4> ip, Optional<Network> network, Optional<NetPort> netport) {

}
