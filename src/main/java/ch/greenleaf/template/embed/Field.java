package ch.greenleaf.template.embed;

import org.jetbrains.annotations.Nullable;

/**
 * Embed field
 * @param title Field title
 * @param value Field value text
 * @param isInline Field inline state
 */
public record Field(@Nullable String title, @Nullable String value, boolean isInline) {
}
