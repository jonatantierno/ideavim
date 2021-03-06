/*
 * IdeaVim - Vim emulator for IDEs based on the IntelliJ platform
 * Copyright (C) 2003-2019 The IdeaVim authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

@file:Suppress("RemoveCurlyBracesFromTemplate")

package org.jetbrains.plugins.ideavim.action.motion.leftright

import com.maddyhome.idea.vim.command.CommandState
import com.maddyhome.idea.vim.helper.StringHelper.parseKeys
import com.maddyhome.idea.vim.option.Options
import com.maddyhome.idea.vim.option.Options.KEYMODEL
import com.maddyhome.idea.vim.option.Options.SELECTMODE
import org.jetbrains.plugins.ideavim.VimListConfig
import org.jetbrains.plugins.ideavim.VimListOptionDefault
import org.jetbrains.plugins.ideavim.VimListOptionTestCase
import org.jetbrains.plugins.ideavim.VimListOptionTestConfiguration

class MotionShiftHomeActionTest : VimListOptionTestCase(KEYMODEL, SELECTMODE) {
    @VimListOptionDefault
    fun `test simple home`() {
        val keys = parseKeys("<S-Home>")
        val before = """
            A Discovery

            I found it in a ${c}legendary land
            all rocks and lavender and tufted grass,
            where it was settled on some sodden sand
            hard by the torrent of a mountain pass.
        """.trimIndent()
        val after = """
            A Discovery

            ${c}I found it in a legendary land
            all rocks and lavender and tufted grass,
            where it was settled on some sodden sand
            hard by the torrent of a mountain pass.
        """.trimIndent()
        doTest(keys, before, after, CommandState.Mode.COMMAND, CommandState.SubMode.NONE)
    }

    @VimListOptionDefault
    fun `test default continueselect`() {
        assertTrue(Options.getInstance().getListOption(KEYMODEL)!!.contains("continueselect"))
    }

    @VimListOptionTestConfiguration(VimListConfig(KEYMODEL, ["startsel"]), VimListConfig(SELECTMODE, []))
    fun `test start visual`() {
        val keys = parseKeys("<S-Home>")
        val before = """
            A Discovery

            I found it in a ${c}legendary land
            all rocks and lavender and tufted grass,
            where it was settled on some sodden sand
            hard by the torrent of a mountain pass.
        """.trimIndent()
        val after = """
            A Discovery

            ${s}${c}I found it in a l${se}egendary land
            all rocks and lavender and tufted grass,
            where it was settled on some sodden sand
            hard by the torrent of a mountain pass.
        """.trimIndent()
        doTest(keys, before, after, CommandState.Mode.VISUAL, CommandState.SubMode.VISUAL_CHARACTER)
    }

    @VimListOptionTestConfiguration(VimListConfig(KEYMODEL, ["startsel"]), VimListConfig(SELECTMODE, ["key"]))
    fun `test start select`() {
        val keys = parseKeys("<S-Home>")
        val before = """
            A Discovery

            I found it in a ${c}legendary land
            all rocks and lavender and tufted grass,
            where it was settled on some sodden sand
            hard by the torrent of a mountain pass.
        """.trimIndent()
        val after = """
            A Discovery

            ${s}${c}I found it in a ${se}legendary land
            all rocks and lavender and tufted grass,
            where it was settled on some sodden sand
            hard by the torrent of a mountain pass.
        """.trimIndent()
        doTest(keys, before, after, CommandState.Mode.SELECT, CommandState.SubMode.VISUAL_CHARACTER)
    }

    @VimListOptionTestConfiguration(VimListConfig(KEYMODEL, []), VimListConfig(SELECTMODE, []))
    fun `test continue visual`() {
        val before = """
            A Discovery

            I found it in a ${c}legendary land
            all rocks and lavender and tufted grass,
            where it was settled on some sodden sand
            hard by the torrent of a mountain pass.
        """.trimIndent()
        val after = """
            A Discovery

            ${s}${c}I found it in a legendary land${se}
            all rocks and lavender and tufted grass,
            where it was settled on some sodden sand
            hard by the torrent of a mountain pass.
        """.trimIndent()
        configureByText(before)
        typeText(parseKeys("<S-Home>"))
        assertState(CommandState.Mode.COMMAND, CommandState.SubMode.NONE)
        typeText(parseKeys("\$v", "<S-Home>"))
        myFixture.checkResult(after)
        assertState(CommandState.Mode.VISUAL, CommandState.SubMode.VISUAL_CHARACTER)
    }

    @VimListOptionTestConfiguration(VimListConfig(KEYMODEL, []), VimListConfig(SELECTMODE, []))
    fun `test continue select`() {
        val before = """
            A Discovery

            I found it in a ${c}legendary land
            all rocks and lavender and tufted grass,
            where it was settled on some sodden sand
            hard by the torrent of a mountain pass.
        """.trimIndent()
        val after = """
            A Discovery

            ${s}${c}I found it in a legendary lan${se}d
            all rocks and lavender and tufted grass,
            where it was settled on some sodden sand
            hard by the torrent of a mountain pass.
        """.trimIndent()
        configureByText(before)
        typeText(parseKeys("<S-Home>"))
        assertState(CommandState.Mode.COMMAND, CommandState.SubMode.NONE)
        typeText(parseKeys("\$gh", "<S-Home>"))
        myFixture.checkResult(after)
        assertState(CommandState.Mode.SELECT, CommandState.SubMode.VISUAL_CHARACTER)
    }
}