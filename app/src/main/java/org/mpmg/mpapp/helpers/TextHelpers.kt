package org.mpmg.mpapp.helpers

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan

object TextHelpers {

    /**
     * Apply type face to pattern in the string pattern
     *
     * @param stringBuilder string builder to apply type face
     * @param start start to look for pattern
     * @param pattern pattern we want to apply a new type face
     * @param typeface type face to be applied
     */
    fun applyTypeFacePattern(
        stringBuilder: SpannableStringBuilder,
        start: Int,
        pattern: String,
        typeface: Typeface
    ) {
        var indexOfPattern = stringBuilder.indexOf(pattern, start)
        while (indexOfPattern >= 0) {
            applyTypeFace(stringBuilder, indexOfPattern, indexOfPattern + pattern.length, typeface)
            indexOfPattern = stringBuilder.indexOf(pattern, indexOfPattern + 1)
        }
    }

    /**
     * Apply color to pattern in the string pattern
     *
     * @param stringBuilder string builder to apply color
     * @param start start to look for pattern
     * @param pattern pattern we want to apply a new color
     * @param color color that will be applied to the pattern
     */
    fun applyColorPattern(
        stringBuilder: SpannableStringBuilder,
        start: Int,
        pattern: String,
        color: Int
    ) {
        var indexOfPattern = stringBuilder.indexOf(pattern, start)
        while (indexOfPattern >= 0) {
            stringBuilder.setSpan(
                ForegroundColorSpan(color),
                indexOfPattern,
                indexOfPattern + pattern.length,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
            indexOfPattern = stringBuilder.indexOf(pattern, indexOfPattern + 1)
        }
    }

    /**
     * Apply typeface to interval
     *
     * @param stringBuilder string builder to apply type face
     * @param start start of the interval
     * @param end end of the interval
     * @param typeface type face to be applied
     */
    private fun applyTypeFace(
        stringBuilder: SpannableStringBuilder,
        start: Int,
        end: Int,
        typeface: Typeface
    ) {
        stringBuilder.setSpan(
            CustomTypefaceSpan(typeface),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}