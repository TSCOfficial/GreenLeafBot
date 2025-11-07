package ch.greenleaf.features.teamoverview;

import org.jetbrains.annotations.Nullable;

public record TeamRole(
	long role_id, @Nullable String icon_name, @Nullable Long icon_id, @Nullable Boolean icon_is_animated
	) {
}
