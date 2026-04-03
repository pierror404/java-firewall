package rules;

import java.util.Optional;

import elements.IP;
import elements.NetPort;
import elements.Network;

public record Endpoint(Optional<IP> ip, Optional<Network> network, Optional<NetPort> netport) {

}
