package com.iscweb.service.api;

/**
 * The public API should follow a pattern where, for each version, there are three special classes or interfaces:
 * a "controller" (class), an "action handler" (class), and a "contract" (interface). The contract should define
 * the signature of the methods that will be mapped to URIs in the controller. The action handler should implement
 * the contract, and should contain the real implementation of the method. The controller should simply delegate
 * to the action handler. The action handlers should form an inheritance chain, i.e. each version should inherit
 * from the previous version. However, the controllers should NOT inherit from each other.
 * <p>
 * All PublicApiContracts should extend this interface, but should not inherit from each other.
 */
public interface IPublicApiContract {
}
