package ch.greenleaf.template.embed;

import org.jetbrains.annotations.Nullable;

public record Field(@Nullable String title, @Nullable String value, boolean isInline) {
}
