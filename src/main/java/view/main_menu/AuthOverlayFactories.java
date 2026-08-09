package view.main_menu;

import view.common.OverlayFactory;

/**
 * Groups the overlay factories used for user authentication on the main menu.
 *
 * @param loginOverlayFactory factory used to create the login overlay
 * @param signupOverlayFactory factory used to create the sign-up overlay
 */
public record AuthOverlayFactories(OverlayFactory loginOverlayFactory, OverlayFactory signupOverlayFactory) {
}
