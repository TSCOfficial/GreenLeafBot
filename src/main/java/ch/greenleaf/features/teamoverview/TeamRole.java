package ch.greenleaf.features.teamoverview;

import org.jetbrains.annotations.Nullable;

/**
 * Creates an object with all required and optional fields that a Role has, for the team overview
 * @param role_id Discord's role ID
 * @param custom_name Custom name that is displayed in the embed field title (default: role name)
 * @param icon_name The name of an icon
 * @param icon_id The discord-ID of that icon
 * @param icon_is_animated If that icon is animated or not
 */
public record TeamRole(
	long role_id, @Nullable String custom_name, @Nullable String icon_name, @Nullable Long icon_id, @Nullable Boolean icon_is_animated
	) {
}
