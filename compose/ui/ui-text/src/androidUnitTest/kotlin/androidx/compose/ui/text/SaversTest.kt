/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.compose.ui.text

import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontSynthesis
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@Suppress("Deprecation")
class SaversTest {
    private val defaultSaverScope = SaverScope { true }

    @Test
    fun test_TextUnit() {
        val original = 2.sp
        val saved = save(original, TextUnit.Saver, defaultSaverScope)
        val restored: TextUnit? = restore(saved, TextUnit.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_TextUnit_unspecified() {
        val original = TextUnit.Unspecified
        val saved = save(original, TextUnit.Saver, defaultSaverScope)
        val restored: TextUnit? = restore(saved, TextUnit.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_Offset() {
        val original = Offset(10f, 10f)
        val saved = save(original, Offset.Saver, defaultSaverScope)
        val restored: Offset? = restore(saved, Offset.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_Offset_Unspecified() {
        val original = Offset.Unspecified
        val saved = save(original, Offset.Saver, defaultSaverScope)
        val restored: Offset? = restore(saved, Offset.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_Offset_Infinite() {
        val original = Offset.Infinite
        val saved = save(original, Offset.Saver, defaultSaverScope)
        val restored: Offset? = restore(saved, Offset.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_Color() {
        val original = Color.Yellow
        val saved = save(original, Color.Saver, defaultSaverScope)
        val restored: Color? = restore(saved, Color.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_Color_Unspecified() {
        val original = Color.Unspecified
        val saved = save(original, Color.Saver, defaultSaverScope)
        val restored: Color? = restore(saved, Color.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_Shadow() {
        val original = Shadow(color = Color.Blue, offset = Offset(5f, 5f), blurRadius = 2f)
        val saved = save(original, Shadow.Saver, defaultSaverScope)
        val restored: Shadow? = restore(saved, Shadow.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_Shadow_None() {
        val original = Shadow.None
        val saved = save(original, Shadow.Saver, defaultSaverScope)
        val restored: Shadow? = restore(saved, Shadow.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_ParagraphStyle() {
        val original = ParagraphStyle()
        val saved = save(original, ParagraphStyleSaver, defaultSaverScope)
        val restored: ParagraphStyle? = restore(saved, ParagraphStyleSaver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_ParagraphStyle_with_a_nonnull_value() {
        val original = ParagraphStyle(textDirection = TextDirection.Rtl)
        val saved = save(original, ParagraphStyleSaver, defaultSaverScope)
        val restored: ParagraphStyle? = restore(saved, ParagraphStyleSaver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_ParagraphStyle_with_no_null_value() {
        val original = ParagraphStyle(
            textAlign = TextAlign.Justify,
            textDirection = TextDirection.Rtl,
            lineHeight = 10.sp,
            textIndent = TextIndent(firstLine = 2.sp, restLine = 3.sp)
        )
        val saved = save(original, ParagraphStyleSaver, defaultSaverScope)
        val restored: ParagraphStyle? = restore(saved, ParagraphStyleSaver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_SpanStyle() {
        val original = SpanStyle()
        val saved = save(original, SpanStyleSaver, defaultSaverScope)
        val restored: SpanStyle? = restore(saved, SpanStyleSaver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_SpanStyle_with_a_nonnull_value() {
        val original = SpanStyle(baselineShift = BaselineShift.Subscript)
        val saved = save(original, SpanStyleSaver, defaultSaverScope)
        val restored: SpanStyle? = restore(saved, SpanStyleSaver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_SpanStyle_with_no_null_value() {
        val original = SpanStyle(
            color = Color.Red,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            fontSynthesis = FontSynthesis.All,
            // fontFamily =
            fontFeatureSettings = "feature settings",
            letterSpacing = 2.em,
            baselineShift = BaselineShift.Superscript,
            textGeometricTransform = TextGeometricTransform(2f, 3f),
            localeList = LocaleList(
                Locale("sr-Latn-SR"),
                Locale("sr-Cyrl-SR"),
                Locale.current
            ),
            background = Color.Blue,
            textDecoration = TextDecoration.LineThrough,
            shadow = Shadow(color = Color.Red, offset = Offset(2f, 2f), blurRadius = 4f)
        )
        val saved = save(original, SpanStyleSaver, defaultSaverScope)
        val restored: SpanStyle? = restore(saved, SpanStyleSaver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_TextLinkStyles() {
        val original = TextLinkStyles(null)
        val saved = save(original, TextLinkStylesSaver, defaultSaverScope)
        val restored: TextLinkStyles? = restore(saved, TextLinkStylesSaver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_TextLinkStyles_withNonNullValues() {
        val original = TextLinkStyles(
            SpanStyle(color = Color.Red),
            SpanStyle(color = Color.Green),
            SpanStyle(color = Color.Blue),
            SpanStyle(color = Color.Gray)
        )
        val saved = save(original, TextLinkStylesSaver, defaultSaverScope)
        val restored: TextLinkStyles? = restore(saved, TextLinkStylesSaver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_FontWeight() {
        val original = FontWeight(123)
        val saved = save(original, FontWeight.Saver, defaultSaverScope)
        val restored: FontWeight? = restore(saved, FontWeight.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_FontWeight_w100() {
        val original = FontWeight.W100
        val saved = save(original, FontWeight.Saver, defaultSaverScope)
        val restored: FontWeight? = restore(saved, FontWeight.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_BaselineShift() {
        val original = BaselineShift(2f)
        val saved = save(original, BaselineShift.Saver, defaultSaverScope)
        val restored: BaselineShift? = restore(saved, BaselineShift.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_BaselineShift_None() {
        val original = BaselineShift.None
        val saved = save(original, BaselineShift.Saver, defaultSaverScope)
        val restored: BaselineShift? = restore(saved, BaselineShift.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_TextDecoration() {
        val original = TextDecoration.combine(
            listOf(TextDecoration.LineThrough, TextDecoration.Underline)
        )
        val saved = save(original, TextDecoration.Saver, defaultSaverScope)
        val restored: TextDecoration? = restore(saved, TextDecoration.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_TextDecoration_None() {
        val original = TextDecoration.None
        val saved = save(original, TextDecoration.Saver, defaultSaverScope)
        val restored: TextDecoration? = restore(saved, TextDecoration.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun testSaveRestore_lineThrough() {
        val original = TextDecoration.LineThrough
        val saved = save(original, TextDecoration.Saver, defaultSaverScope)
        val restored: TextDecoration? = restore(saved, TextDecoration.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun testSaveRestore_underline() {
        val original = TextDecoration.Underline
        val saved = save(original, TextDecoration.Saver, defaultSaverScope)
        val restored: TextDecoration? = restore(saved, TextDecoration.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_TextGeometricTransform() {
        val original = TextGeometricTransform(1f, 2f)
        val saved = save(original, TextGeometricTransform.Saver, defaultSaverScope)
        val restored: TextGeometricTransform? = restore(saved, TextGeometricTransform.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_TextGeometricTransform_None() {
        val original = TextGeometricTransform.None
        val saved = save(original, TextGeometricTransform.Saver, defaultSaverScope)
        val restored: TextGeometricTransform? = restore(saved, TextGeometricTransform.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_TextIndent() {
        val original = TextIndent(1.sp, 2.sp)
        val saved = save(original, TextIndent.Saver, defaultSaverScope)
        val restored: TextIndent? = restore(saved, TextIndent.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_TextIndent_None() {
        val original = TextIndent.None
        val saved = save(original, TextIndent.Saver, defaultSaverScope)
        val restored: TextIndent? = restore(saved, TextIndent.Saver)

        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_AnnotatedString() {
        val original = AnnotatedString("abc")
        val saved = with(AnnotatedStringSaver) { defaultSaverScope.save(original) }

        assertThat(AnnotatedStringSaver.restore(saved!!)).isEqualTo(original)
    }

    @Test
    fun test_AnnotatedString_withSpanStyles() {
        val original = buildAnnotatedString {
            withStyle(SpanStyle(color = Color.Red)) { append("1") }
            withStyle(SpanStyle(fontStyle = FontStyle.Italic)) { append("2") }
        }

        val saved = with(AnnotatedStringSaver) { defaultSaverScope.save(original) }

        val restored: AnnotatedString = AnnotatedStringSaver.restore(saved!!)!!
        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_AnnotatedString_withParagraphStyles() {
        val original = buildAnnotatedString {
            withStyle(ParagraphStyle(textAlign = TextAlign.Justify)) { append("1") }
            withStyle(ParagraphStyle(textDirection = TextDirection.Rtl)) { append("2") }
        }

        val saved = with(AnnotatedStringSaver) { defaultSaverScope.save(original) }

        val restored: AnnotatedString = AnnotatedStringSaver.restore(saved!!)!!
        assertThat(restored).isEqualTo(original)
    }

    @OptIn(ExperimentalTextApi::class)
    @Test
    fun test_AnnotatedString_withAnnotations() {
        val original = buildAnnotatedString {
            withAnnotation(tag = "Tag1", annotation = "Annotation1") { append("1") }
            withAnnotation(VerbatimTtsAnnotation("verbatim1")) { append("2") }
            withAnnotation(tag = "Tag2", annotation = "Annotation2") { append("3") }
            withAnnotation(VerbatimTtsAnnotation("verbatim2")) { append("4") }
            withAnnotation(UrlAnnotation("url1")) { append("5") }
            withAnnotation(UrlAnnotation("url2")) { append("6") }
            withLink(
                LinkAnnotation.Url(
                    "url3",
                    TextLinkStyles(
                        SpanStyle(color = Color.Red),
                        SpanStyle(color = Color.Green),
                        SpanStyle(color = Color.Blue),
                        SpanStyle(color = Color.White)
                    )
                )
            ) { append("7") }
            withLink(
                LinkAnnotation.Clickable(
                    "tag3",
                    TextLinkStyles(
                        SpanStyle(color = Color.Red),
                        SpanStyle(color = Color.Green),
                        SpanStyle(color = Color.Blue),
                        SpanStyle(background = Color.Gray)
                    ),
                    null
                )
            ) {
                append("8")
            }
        }

        val saved = with(AnnotatedStringSaver) { defaultSaverScope.save(original) }

        val restored: AnnotatedString = AnnotatedStringSaver.restore(saved!!)!!

        assertThat(restored).isEqualTo(original)
    }

    @OptIn(ExperimentalTextApi::class)
    @Test
    fun test_AnnotatedString_withSpanAndParagraphStylesAndAnnotations() {
        val original = buildAnnotatedString {
            withStyle(ParagraphStyle(textAlign = TextAlign.Justify)) { append("1") }
            withStyle(ParagraphStyle(textDirection = TextDirection.Rtl)) { append("2") }
            withStyle(SpanStyle(color = Color.Red)) { append("3") }
            withStyle(SpanStyle(fontStyle = FontStyle.Italic)) { append("4") }
            withAnnotation(tag = "Tag1", annotation = "Annotation1") { append("5") }
            withAnnotation(VerbatimTtsAnnotation("verbatim1")) { append("6") }
            withAnnotation(tag = "Tag2", annotation = "Annotation2") { append("7") }
            withAnnotation(VerbatimTtsAnnotation("verbatim2")) { append("8") }
            withAnnotation(UrlAnnotation("url1")) { append("9") }
            withAnnotation(UrlAnnotation("url2")) { append("10") }
            withLink(
                LinkAnnotation.Url(
                    "url3",
                    TextLinkStyles(
                        SpanStyle(color = Color.Red),
                        SpanStyle(color = Color.Green),
                        SpanStyle(color = Color.Blue),
                        SpanStyle(color = Color.Yellow)
                    )
                )
            ) { append("11") }
            withLink(
                LinkAnnotation.Clickable(
                    "tag3",
                    TextLinkStyles(
                        SpanStyle(color = Color.Red),
                        SpanStyle(color = Color.Green),
                        SpanStyle(color = Color.Blue),
                        SpanStyle(color = Color.Gray)
                    ),
                    null
                )
            ) {
                append("12")
            }
        }

        val saved = with(AnnotatedStringSaver) { defaultSaverScope.save(original) }

        val restored: AnnotatedString = AnnotatedStringSaver.restore(saved!!)!!
        assertThat(restored).isEqualTo(original)
    }

    @Test
    fun test_Locale() {
        val original = Locale("sr-Latn-SR")
        val saved = with(Locale.Saver) { defaultSaverScope.save(original) }

        assertThat(Locale.Saver.restore(saved!!)).isEqualTo(original)
    }

    @Test
    fun test_LocaleList() {
        val original = LocaleList(
            Locale("sr-Latn-SR"),
            Locale("sr-Cyrl-SR"),
            Locale.current
        )
        val saved = with(LocaleList.Saver) { defaultSaverScope.save(original) }

        assertThat(LocaleList.Saver.restore(saved!!)).isEqualTo(original)
    }
}
